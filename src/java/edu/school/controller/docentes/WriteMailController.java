package edu.school.controller.docentes;

import edu.school.controller.ejb.MailControllerLocal;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.entities.EmailAccount;
import edu.school.entities.Mail;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@ViewScoped
public class WriteMailController implements Serializable{
    
    
    private String para;
    private String subject;
    private String message;    
    private UploadedFile file;
    
    @Inject
    private EmailAccount emailAccount;
    @Inject
    private Mail mail;
    
    @EJB
    private MailControllerLocal mailController;
    @EJB
    private EmailAccountFacadeLocal emailAccountFacade;

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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
    
    public void sendMail(){
        emailAccount = emailAccountFacade.find(1); // modificar esto de acuerdo a la cuenta que se tenga acceso
        mail.setUser(emailAccount.getUser());
        mail.setPassword(emailAccount.getPassword());
        mail.setMessage(message);
        mail.setSubject(subject);
        mail.setRecipient(para);
        
        if(file != null){
            System.out.println(file.getFileName());
        } else {
            System.out.println("No ha cargado el archivo");
        }
        
//        if(mailController.sendMail(emailAccount, mail)){
//            JsfUtils.messageSuccess("Correo enviado con éxito");
//            this.clearFields();
//        } else {
//            JsfUtils.messageSuccess("Ha ocurrido un problema el correo no se ha podido enviar");
//        }
    }
    
    public void uploadedFile(FileUploadEvent event){
        file = event.getFile();
        System.out.println(file.getFileName());
    }
    
    public void clearFields(){
        this.setPara(null);
        this.setSubject(null);
        this.setMessage(null);
    }
    
    private String convertHtml(){
        StringBuffer sb = new StringBuffer();
        sb.append("<html>");
        sb.append("<body>");
        sb.append(message);
        sb.append("</body>");
        sb.append("</html>");
        
        return sb.toString();
    }
}
