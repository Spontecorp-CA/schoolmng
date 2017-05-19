package edu.school.controller.docentes;

import edu.school.controller.ejb.CircularControllerLocal;
import edu.school.controller.ejb.MailControllerLocal;
import edu.school.ejb.CircularStatusFacadeLocal;
import edu.school.ejb.CursoFacadeLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.ejb.EtapaFacadeLocal;
import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.ejb.SeccionFacadeLocal;
import edu.school.ejb.SeccionHasDocenteFacadeLocal;
import edu.school.ejb.StatusSupervisorFacadeLocal;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.Circular;
import edu.school.entities.CircularStatus;
import edu.school.entities.Curso;
import edu.school.entities.Docente;
import edu.school.entities.EmailAccount;
import edu.school.entities.Etapa;
import edu.school.entities.Mail;
import edu.school.entities.Periodo;
import edu.school.entities.Seccion;
import edu.school.entities.StatusSupervisor;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.excepciones.DocenteNotFoundException;
import edu.school.utilities.Constantes;
import edu.school.utilities.JsfUtils;
import edu.school.utilities.LogFiler;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;

@Named
@ViewScoped
public class WriteMailController implements Serializable {

    @Inject
    private EmailAccount emailAccount;
    @Inject
    private Mail mail;
    @Inject
    private Circular circular;
    @Inject
    private DocenteDashboardController docenteDashboardController;

    @EJB
    private MailControllerLocal mailController;
    @EJB
    private EmailAccountFacadeLocal emailAccountFacade;
    @EJB
    private SupervisorFacadeLocal supervisorFacade;
    @EJB
    private StatusSupervisorFacadeLocal statusSupervisorFacade;
    @EJB
    private DocenteFacadeLocal docenteFacade;
    @EJB
    private SeccionHasDocenteFacadeLocal seccionHasDocenteFacade;
    @EJB
    private SeccionFacadeLocal seccionFacade;
    @EJB
    private PeriodoFacadeLocal periodoFacade;
    @EJB
    private CircularControllerLocal circularController;
    @EJB
    private EtapaFacadeLocal etapaFacade;
    @EJB
    private CursoFacadeLocal cursoFacade;
    @EJB
    private CircularStatusFacadeLocal circularStatusFacade;

    private Etapa etapa;
    private Curso grado;
    private Seccion seccion;
    private List<Etapa> etapas;
    private boolean showEtapasDropbox;
    private boolean enableEtapasDropbox;
    private List<Curso> grados;
    private boolean showGradosDropbox;
    private boolean enableGradosDropbox;
    private List<Seccion> secciones;
    private boolean enableSeccionesDropbox;
    private User user;

    private String codigoCircular = "";
    private String para;
    private String subject;
    private String message;
    private Part file;
    private String directory;
    private String fileLabel;
    private String grupoAEnviar;
    private List<String> grupos;

    private String cargoSupervisor;
    private String tipoSupervisor = "";

    private static final LogFiler LOGGER = LogFiler.getInstance();

    public WriteMailController() {
    }

    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        directory = context.getInitParameter("upload.location");

        Path path = Paths.get(directory);
        try {
            if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
                FileUtils.deleteDirectory(new File(directory));
            } else {
                Files.createDirectory(path);
            }

        } catch (IOException ex) {
            //Logger.getLogger(WriteMailController.class.getName()).log(Level.SEVERE, null, ex);
            LogFiler.logger.log(Level.SEVERE, "Error definiendo la dirección temporal: ", ex.getMessage());
        }
        user = docenteDashboardController.getUser();
        fileLabel = "  No ha seleccionado archivo";
        grupos = makeGrupos();
        secciones = new ArrayList<>();

        checkSavedCircular();
    }

    public String getGrupo() {
        return grupoAEnviar;
    }

    public void setGrupo(String grupo) {
        this.grupoAEnviar = grupo;
    }

    public List<String> getGrupos() {
        return grupos;
    }

    public String getCodigoCircular() {
        return codigoCircular;
    }

    public void setCodigoCircular(String codigoCircular) {
        this.codigoCircular = codigoCircular;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getFileLabel() {
        return fileLabel;
    }

    public void setFileLabel(String fileLabel) {
        this.fileLabel = fileLabel;
    }

    public List<Seccion> getSecciones() {
        return secciones;
    }

    public void setSecciones(List<Seccion> secciones) {
        this.secciones = secciones;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public List<Etapa> getEtapas() {
        return etapas;
    }

    public void setEtapas(List<Etapa> etapas) {
        this.etapas = etapas;
    }

    public Curso getGrado() {
        return grado;
    }

    public void setGrado(Curso grado) {
        this.grado = grado;
    }

    public List<Curso> getGrados() {
        return grados;
    }

    public void setGrados(List<Curso> grados) {
        this.grados = grados;
    }

    public boolean isShowEtapasDropbox() {
        return showEtapasDropbox;
    }

    public void setShowEtapasDropbox(boolean showEtapasDropbox) {
        this.showEtapasDropbox = showEtapasDropbox;
    }

    public boolean isShowGradosDropbox() {
        return showGradosDropbox;
    }

    public boolean isEnableEtapasDropbox() {
        return enableEtapasDropbox;
    }

    public void setEnableEtapasDropbox(boolean enableEtapasDropbox) {
        this.enableEtapasDropbox = enableEtapasDropbox;
    }

    public boolean isEnableGradosDropbox() {
        return enableGradosDropbox;
    }

    public void setEnableGradosDropbox(boolean enableGradosDropbox) {
        this.enableGradosDropbox = enableGradosDropbox;
    }

    public boolean isEnableSeccionesDropbox() {
        return enableSeccionesDropbox;
    }

    public void setEnableSeccionesDropbox(boolean enableSeccionesDropbox) {
        this.enableSeccionesDropbox = enableSeccionesDropbox;
    }

    public void setShowGradosDropbox(boolean showGradosDropbox) {
        this.showGradosDropbox = showGradosDropbox;
    }

    public String getCargoSupervisor() {
        if (null == cargoSupervisor) {
            Optional<StatusSupervisor> optStatSup = circularController.lookupCargoSupervisor(user);
            if (optStatSup.isPresent()) {
                defineTipoAndCargoSupervisor(optStatSup);
            } else {
                cargoSupervisor = "";
            }

        }
        return cargoSupervisor;
    }

    public void setCargoSupervisor(String cargoSupervisor) {
        this.cargoSupervisor = cargoSupervisor;
    }

    private List<String> makeGrupos() {
        int nivel = 0;

        List<Supervisor> supervisores = supervisorFacade.findAllByUser(user);
        if (!supervisores.isEmpty()) {
            for (Supervisor superv : supervisores) {
                Optional<StatusSupervisor> optStatSup
                        = Optional.ofNullable(statusSupervisorFacade.findBySupervisor(superv));

                if (optStatSup.isPresent()) {

                    StatusSupervisor statusSup = optStatSup.get();

                    if (null != statusSup.getColegioId()) {
                        nivel = 0;  // nivel colegio
                        showEtapasDropbox = true;
                        showGradosDropbox = true;
                        break;
                    }
                    if (null != statusSup.getEtapaId()) {
                        nivel = 1; // nivel etapa
                        etapa = statusSup.getEtapaId();
                        showEtapasDropbox = false;
                        showGradosDropbox = true;
                        break;
                    }
                    if (null != statusSup.getCursoId()) {
                        nivel = 2; // nivel curso
                        grado = statusSup.getCursoId();
                        showEtapasDropbox = false;
                        showGradosDropbox = false;
                        break;
                    }
                } else {
                    LogFiler.logger.log(Level.INFO, "El usuario {0} no es supervisor", user.getUsr());
                    nivel = 4; // nivel sección
                }
            }
        } else {
            nivel = 4; // nivel sección
        }

        List<String> groups = new ArrayList<>();
        switch (nivel) {
            case 0:
                groups.add(Constantes.GRUPO_COLEGIO);
            case 1:
                groups.add(Constantes.GRUPO_ETAPA);
            case 2:
                groups.add(Constantes.GRUPO_GRADO);
            default:
                groups.add(Constantes.GRUPO_SECCION);
                break;
        }
        return groups;
    }

    public void ongrupoChange() {
        Optional<StatusSupervisor> optStatSup = circularController.lookupCargoSupervisor(user);
        defineTipoAndCargoSupervisor(optStatSup);

        Periodo periodo = periodoFacade.findByStatus(Constantes.PERIODO_ACTIVO);

        switch (grupoAEnviar) {
            case Constantes.GRUPO_ETAPA:
                if (tipoSupervisor.equals(Constantes.SUPERVISOR_COLEGIO)) {
                    etapas = etapaFacade.findAll();
                } else {
                    etapas = new ArrayList<>();
                }
                grados = new ArrayList<>();
                secciones = new ArrayList();
                break;
            case Constantes.GRUPO_GRADO:
                switch (tipoSupervisor) {
                    case Constantes.SUPERVISOR_COLEGIO:
                        grados = cursoFacade.findAllOrderedByEtapa();
                        break;
                    case Constantes.SUPERVISOR_ETAPA:
                        grados = cursoFacade.findAllByEtapa(etapa);
                        break;
                }
                etapas = new ArrayList<>();
                secciones = new ArrayList<>();
                break;
            case Constantes.GRUPO_SECCION:
                switch (tipoSupervisor) {
                    case Constantes.SUPERVISOR_COLEGIO:
                        secciones = seccionFacade.findAllOrderedBySeccion(periodo);
                        break;
                    case Constantes.SUPERVISOR_ETAPA:
                        secciones = seccionFacade.findAllOrderedByEtapa(etapa);
                        break;
                    case Constantes.SUPERVISOR_GRADO:
                        secciones = seccionFacade.findAllOrderedByGrado(grado, periodo);
                    default:
                        try {
                            Docente docente = docenteFacade.findByCi(user.getCi());
                            secciones = seccionHasDocenteFacade.findAllByDocente(docente);
                        } catch (Exception e) {
                            LogFiler.logger.log(Level.WARNING, "El usuario {0} no es docente", user.getCi());
                        }

                }
                etapas = new ArrayList<>();
                grados = new ArrayList<>();
                break;
        }

    }

    public void sendMail() {
//        emailAccount = emailAccountFacade.find(1); // modificar esto de acuerdo a la cuenta que se tenga acceso
//        mail.setUser(emailAccount.getUser());
//        mail.setPassword(emailAccount.getPassword());
//        if (message == null || message.isEmpty()) {
//            message = " ";
//        }
//        mail.setMessage(message);
//        mail.setSubject(subject);
//        mail.setRecipient(para);
//
//        String filePath = directory + file.getSubmittedFileName();
//
//        mail.setFilePath(filePath);
//        mail.setFileName(file.getSubmittedFileName());
//
//        if (mailController.prepareMail(emailAccount, mail)) {
//            JsfUtils.messageSuccess("Correo enviado con éxito");
//            this.clearFields();
//            Path path = Paths.get(filePath);
//            try {
//                Files.deleteIfExists(path);
//            } catch (IOException ex) {
//                Logger.getLogger(WriteMailController.class.getName())
//                        .log(Level.SEVERE, "No pudo borrar el temporal", ex);
//            }
//        } else {
//            JsfUtils.messageError("Ha ocurrido un problema el correo no se ha podido enviar");
//        }

    }

    public void uploadedFile() {
        try (InputStream input = file.getInputStream()) {
            Path path = Paths.get(directory);
            if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
                Files.createDirectory(path);
            }

            Files.copy(input, new File(path.toUri().getPath(),
                    file.getSubmittedFileName()).toPath());
            fileLabel = file.getSubmittedFileName();

        } catch (IOException e) {
            Logger.getLogger(WriteMailController.class.getName())
                    .log(Level.SEVERE, "Dio un error al cargar el archivo ", e);
        }
    }

    public void clearFields() {
        this.setPara(null);
        this.setSubject(null);
        this.setMessage(null);
        this.file = null;
        this.fileLabel = null;
    }

    public List<Seccion> checkSeccion() {
        List<Seccion> seccionesList = new ArrayList<>();
        Periodo periodo = periodoFacade.findByStatus(Constantes.PERIODO_ACTIVO);

        if (tipoSupervisor.equals("")) {
            // No es supervisor, sólo toma las secciones asiganadas a este docente
            // con el usuario se obtiene el docente
            Docente docente = null;
            try {
                docente = docenteFacade.findByCi(user.getCi());

                // con el docente se obtiene que sección, grado o etapa está
                Optional<List<Seccion>> optSeccionList = Optional.ofNullable(seccionHasDocenteFacade.findAllByDocente(docente));

                List<Seccion> seccionList;
                if (optSeccionList.isPresent()) {
                    seccionList = optSeccionList.get();
                    seccionesList = seccionList.stream()
                            .filter(sec -> sec.getPeriodoId().equals(periodo))
                            .collect(Collectors.toList());

                } else {
                    LogFiler.logger.log(Level.WARNING, "El docente no tiene sección asignada");
                }

                // con el curso(grado) y/o etapa se obtiene el supervisor
            } catch (DocenteNotFoundException ex) {
                //Logger.getLogger(WriteMailController.class.getName()).log(Level.SEVERE, null, ex);
                LogFiler.logger.log(Level.FINE, "Al buscar al docente con c.i.{0} dió error: {1}",
                        new Object[]{docente.getUserId().getCi(), ex});
            }
        } else if (tipoSupervisor.equals(Constantes.SUPERVISOR_COLEGIO)) {
            seccionesList = seccionFacade.findAllOrderedByCurso(periodo);
        } else if (tipoSupervisor.equals(Constantes.SUPERVISOR_ETAPA)) {
            seccionesList = seccionFacade.findAllOrderedByEtapa(etapa);
        } else if (tipoSupervisor.equals(Constantes.SUPERVISOR_GRADO)) {
            seccionesList = seccionFacade.findAllOrderedByGrado(grado, periodo);
        }

        return seccionesList;
    }

    public void saveCircular() {
        if (null != grupoAEnviar) {
            String subgrupo = "";
            switch (grupoAEnviar) {
                case Constantes.GRUPO_COLEGIO:
                    subgrupo = Constantes.GRUPO_COLEGIO;
                    break;
                case Constantes.GRUPO_ETAPA:
                    subgrupo = etapa.getNombre();
                    break;
                case Constantes.GRUPO_GRADO:
                    subgrupo = grado.getNombre();
                    break;
                case Constantes.GRUPO_SECCION:
                    subgrupo = seccion.getCodigo();//getCursoId().getNombre() + "-" + seccion.getSeccion();
                    break;
            }

            circular = circularController.makeCircular(grupoAEnviar, subgrupo, para,
                    subject, message, file, directory, user);
            JsfUtils.messageSuccess("Guardada la circular "
                    + circular.getCodigoCircular() + " con éxito");
        } else {
            JsfUtils.messageWarning("Debe seleccionar a que grupo enviará la circular");
        }
    }

    public void checkSupervisorChain() {
        if (null != grupoAEnviar) {
            circularController.setSeccion(seccion);

            saveCircular();
            List<Supervisor> supervisores = circularController.findAllInmediateSupervisor(user);
            
            System.out.println("WriteMailController.checkSupervisorChain: " 
                    + " el supervisor es: " + supervisores.get(0).getUserId().getUsr());
            
            circularController.checkEnvio(grupoAEnviar, user, supervisores, circular);
        } else {
            JsfUtils.messageWarning("Debe seleccionar a que grupo enviará la circular");
        }
    }

    private void defineTipoAndCargoSupervisor(Optional<StatusSupervisor> optStatSup) {
        if (optStatSup.isPresent()) {
            StatusSupervisor statSup = optStatSup.get();
            if (null != statSup.getColegioId()) {
                cargoSupervisor = "Supervisor del Colegio";
                tipoSupervisor = Constantes.SUPERVISOR_COLEGIO;
            } else if (null != statSup.getEtapaId()) {
                etapa = statSup.getEtapaId();
                cargoSupervisor = "Supervisor de: " + etapa.getNombre();
                tipoSupervisor = Constantes.SUPERVISOR_ETAPA;
            } else if (null != statSup.getCursoId()) {
                Curso curso = statSup.getCursoId();
                cargoSupervisor = "Supervisor de: " + curso.getNombre();
                tipoSupervisor = Constantes.SUPERVISOR_GRADO;
            }
        }
    }

    private void checkSavedCircular() {
        Optional<CircularStatus> optCircularStatus;
        optCircularStatus = Optional.ofNullable(circularStatusFacade
                .findByUserAndStatus(user, Constantes.CIRCULAR_NO_ENVIADA));

        if (optCircularStatus.isPresent()) {
            circular = optCircularStatus.get().getCircularId();
            codigoCircular = circular.getCodigoCircular();

            grupoAEnviar = circular.getGrupoDestino();

            switch (grupoAEnviar) {
                case Constantes.GRUPO_ETAPA:
                    etapa = etapaFacade.findByNombre(circular.getGrupoDestino());
                    break;
                case Constantes.GRUPO_GRADO:
                    grado = cursoFacade.findByName(circular.getGrupoDestino());
                    break;
            }

            para = circular.getDestinatario() == null ? "" : circular.getDestinatario();
            subject = circular.getAsunto() == null ? "" : circular.getAsunto();
            message = circular.getMessage() == null ? "" : circular.getMessage();
        }
    }
}
