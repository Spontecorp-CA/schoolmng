package edu.school.controller.docentes;

import edu.school.controller.ejb.CircularControllerLocal;
import edu.school.controller.ejb.MailControllerLocal;
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

        fileLabel = "  No ha seleccionado archivo";
        grupos = makeGrupos();
        secciones = new ArrayList<>();
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
        if (grupoAEnviar != null) {
            if (grupoAEnviar.equals(Constantes.GRUPO_SECCION)) {
                secciones = checkSeccion();
                if(null != etapas){
                    etapas.clear();
                } else {
                    etapas = new ArrayList<>();
                }
                if(null != grados){
                    grados.clear();
                } else {
                    grados = new ArrayList<>();
                }
            }
        }
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
        if (grupoAEnviar != null) {
            if (grupoAEnviar.equals(Constantes.GRUPO_ETAPA)) {
                etapas = etapaFacade.findAll();
                if(null != grados){
                    grados.clear();
                }
                if(null != secciones){
                    secciones.clear();
                }
            }
        }
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
        if (grupoAEnviar != null) {
            if (grupoAEnviar.equals(Constantes.GRUPO_GRADO)) {
                grados = cursoFacade.findAllByEtapa(etapa);
                if (null != etapas) {
                    etapas.clear();
                }
                if (null != secciones) {
                    secciones.clear();
                }
            }
        }
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
            cargoSupervisor = lookupCargoSupervisor();
        }
        return cargoSupervisor;
    }

    public void setCargoSupervisor(String cargoSupervisor) {
        this.cargoSupervisor = cargoSupervisor;
    }

    private List<String> makeGrupos() {
        int nivel = 0;
        User user = docenteDashboardController.getUser();
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
            User user = docenteDashboardController.getUser();

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
        // terminar de construir la circular
    }

    public void checkSupervisorChain() {
//        switch(grupoAEnviar){
//            case Constantes.GRUPO_COLEGIO:
//                System.out.println("No necesita permiso, envía de una vez");
//                break;
//            case Constantes.GRUPO_ETAPA:
//                System.out.println("Envía al supervisor de colegio para su posterior envío");
//                break;
//            case Constantes.GRUPO_GRADO:
//                System.out.println("Envía al supervidor de etapa para su revisión y reenvío al "
//                        + "supervisor e colegio");
//                break;
//            default:
//                System.out.println("Si es supervisor de grado, envía al de etapa "
//                        + "para su revisión y posterior envío");
//                System.out.println("Si no es supervisor, identifica al supervisor de "
//                        + "grado para su revisión y posterior envío");
//        }

        User user = docenteDashboardController.getUser();
        boolean isSupervisor = circularController.isSupervisor(user);
        boolean isSupervisorColegio = circularController.isColegioSupervisor(user);
        //boolean isSupervisorEtapa = circularController.isEtapaSupervisor(user, etapa);

        if (isSupervisor) {
            // debe chequear si es supervisor del colegio envía la circular
            if (isSupervisorColegio) {
                // prepara la circular a enviar
                circular = circularController.makeCircular(grupoAEnviar, "colegio",
                        para, subject, message, file, directory);
                // si está correctamente preparado lo envía
                if (null != circular) {
                    String filePath = directory + file.getSubmittedFileName();
                    List<String> destinatarios = circularController.mailListColegio();
                    // lo envía y muestra los mensajes si fue exitoso o no
                    if (circularController.sendCircular(circular, destinatarios)) {
                        JsfUtils.messageSuccess("Correo enviado con éxito");
                        this.clearFields();

                        Path path = Paths.get(filePath);
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException ex) {
                            Logger.getLogger(WriteMailController.class.getName())
                                    .log(Level.SEVERE, "No pudo borrar el temporal", ex);
                        }
                    } else {
                        JsfUtils.messageError("Ha ocurrido un problema el correo no se ha podido enviar");
                    }
                } else { // problemas preparando el mail
                    JsfUtils.messageError("No se ha podido preparar el correo a enviar");
                }
            } else {
                // envía al supervisor inmediato
            }
        } else {
            // debe buscar su supervisor inmediato para enviarle la circular
        }

    }

    private String lookupCargoSupervisor() {
        String answer = " ";
        User user = docenteDashboardController.getUser();
        Optional<Supervisor> optSupervisor = Optional
                .ofNullable(supervisorFacade.findByUser(user));
        if (optSupervisor.isPresent()) {
            Supervisor supervisor = optSupervisor.get();
            Optional<StatusSupervisor> optStatSup = Optional
                    .ofNullable(statusSupervisorFacade.findBySupervisor(supervisor));
            if (optStatSup.isPresent()) {
                StatusSupervisor statSup = optStatSup.get();
                if (null != statSup.getColegioId()) {
                    answer = "Supervisor del Colegio";
                    tipoSupervisor = Constantes.SUPERVISOR_COLEGIO;
                } else if (null != statSup.getEtapaId()) {
                    etapa = statSup.getEtapaId();
                    answer = "Supervisor de: " + etapa.getNombre();
                    tipoSupervisor = Constantes.SUPERVISOR_ETAPA;
                } else if (null != statSup.getCursoId()) {
                    Curso curso = statSup.getCursoId();
                    answer = "Supervisor de: " + curso.getNombre();
                    tipoSupervisor = Constantes.SUPERVISOR_GRADO;
                }
            }
        }

        return answer;
    }

}
