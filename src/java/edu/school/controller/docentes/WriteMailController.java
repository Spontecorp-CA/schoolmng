package edu.school.controller.docentes;

import edu.school.controller.ejb.MailControllerLocal;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.ejb.StatusSupervisorFacadeLocal;
import edu.school.ejb.SupervisorFacadeLocal;
import edu.school.entities.EmailAccount;
import edu.school.entities.Mail;
import edu.school.entities.StatusSupervisor;
import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.utilities.JsfUtils;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
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

    private String para;
    private String subject;
    private String message;
    private Part file;
    private String directory;
    private String fileLabel;
    private String grupo;
    private List<String> grupos;

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
            Logger.getLogger(WriteMailController.class.getName()).log(Level.SEVERE, null, ex);
        }

        fileLabel = "  No ha seleccionado archivo";
        grupos = makeGrupos();
    }

    private List<String> makeGrupos() {
        int nivel = 0;
        User user = docenteDashboardController.getUser();
        List<Supervisor> supervisores = supervisorFacade.findAllByUser(user);
        if (!supervisores.isEmpty()) {
            for (Supervisor superv : supervisores) {
                StatusSupervisor statusSup = statusSupervisorFacade.findBySupervisor(superv);
                if (statusSup.getColegioId() != null) {
                    nivel = 0;
                    break;
                }
                if (statusSup.getEtapaId() != null) {
                    nivel = 1;
                    break;
                }
                if (statusSup.getCursoId() != null) {
                    nivel = 2;
                    break;
                }
            }
        } else {
            nivel = 4;
        }

        List<String> groups = new ArrayList<>();
        switch (nivel) {
            case 0:
                groups.add("Colegio");
            case 1:
                groups.add("Etapa");
            case 2:
                groups.add("Grado");
            default:
                groups.add("Sección");
                break;
        }
        return groups;
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

}
