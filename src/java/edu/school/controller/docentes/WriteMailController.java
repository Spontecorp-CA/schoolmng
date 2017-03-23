package edu.school.controller.docentes;

import edu.school.controller.ejb.MailControllerLocal;
import edu.school.ejb.DocenteFacadeLocal;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.ejb.SeccionFacadeLocal;
import edu.school.ejb.SeccionHasDocenteFacadeLocal;
import edu.school.ejb.StatusSupervisorFacadeLocal;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.Curso;
import edu.school.entities.Docente;
import edu.school.entities.EmailAccount;
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

    private String para;
    private String subject;
    private String message;
    private Part file;
    private String directory;
    private String fileLabel;
    private String grupo;
    private List<String> grupos;
    private List<Seccion> secciones;
    private Seccion seccion;
    private Curso grado;
    private List<Curso> grados;

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
            LOGGER.logger.log(Level.SEVERE, "Error definiendo la dirección temporal: ", ex.getMessage());
        }

        fileLabel = "  No ha seleccionado archivo";
        grupos = makeGrupos();
        secciones = new ArrayList<>();
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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
        if (grupo != null) {
            switch (grupo) {
                case Constantes.GRUPO_COLEGIO:
                    break;
                case Constantes.GRUPO_ETAPA:
                    break;
                case Constantes.GRUPO_GRADO:
                    break;
                case Constantes.GRUPO_SECCION:
                    checkSeccion();
                    break;
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
                        break;
                    }
                    if (null != statusSup.getEtapaId()) {
                        nivel = 1; // nivel etapa
                        break;
                    }
                    if (null != statusSup.getCursoId()) {
                        nivel = 2; // nivel curso
                        break;
                    }
                } else {
                    LOGGER.logger.log(Level.INFO, "El usuario {0} no es supervisor", user.getUsr());
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
        emailAccount = emailAccountFacade.find(1); // modificar esto de acuerdo a la cuenta que se tenga acceso
        mail.setUser(emailAccount.getUser());
        mail.setPassword(emailAccount.getPassword());
        if (message == null || message.isEmpty()) {
            message = " ";
        }
        mail.setMessage(message);
        mail.setSubject(subject);
        mail.setRecipient(para);

        String filePath = directory + file.getSubmittedFileName();

        mail.setFilePath(filePath);
        mail.setFileName(file.getSubmittedFileName());

        if (mailController.sendMail(emailAccount, mail)) {
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

    public void checkSeccion() {
        // con el usuario se obtiene el docente
        User user = docenteDashboardController.getUser();
        Periodo periodo = periodoFacade.findByStatus(Constantes.PERIODO_ACTIVO);
        Docente docente = null;
        try {
            docente = docenteFacade.findByCi(user.getCi());

            LOGGER.logger.log(Level.INFO, "El docente que envía la circular es {0}",
                    docente.getDatosPersonaId().getNombre());

            // con el docente se obtiene que sección, grado o etapa está
            Optional<List<Seccion>> optSeccionList = Optional.ofNullable(seccionHasDocenteFacade.findAllByDocente(docente));

            List<Seccion> seccionList;
            if (optSeccionList.isPresent()) {
                seccionList = optSeccionList.get();
                secciones = seccionList.stream()
                        .filter(sec -> sec.getPeriodoId().equals(periodo))
                        .collect(Collectors.toList());

            } else {
                LOGGER.logger.log(Level.WARNING, "El docente no tiene sección asignada");
            }

            // con el curso(grado) y/o etapa se obtiene el supervisor
        } catch (DocenteNotFoundException ex) {
            //Logger.getLogger(WriteMailController.class.getName()).log(Level.SEVERE, null, ex);
            LOGGER.logger.log(Level.FINE, "Al buscar al docente con c.i.{0} dió error: {1}",
                    new Object[]{docente.getUserId().getCi(), ex});
        }
    }

    public void checkSupervisorChain(){
        switch(grupo){
            case Constantes.GRUPO_COLEGIO:
                System.out.println("No necesita permiso, envía de una vez");
                break;
            case Constantes.GRUPO_ETAPA:
                System.out.println("Envía al supervisor de colegio para su posterior envío");
                break;
            case Constantes.GRUPO_GRADO:
                System.out.println("Envía al supervidor de etapa para su revisión y reenvío al "
                        + "supervisor e colegio");
                break;
            default:
                System.out.println("Si es supervisor de grado, envía al de etapa "
                        + "para su revisión y posterior envío");
                System.out.println("Si no es supervisor, identifica al supervisor de "
                        + "grado para su revisión y posterior envío");
        }
    }
}
