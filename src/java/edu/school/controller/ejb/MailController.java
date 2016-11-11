package edu.school.controller.ejb;

import edu.school.entities.EmailAccount;
import edu.school.entities.Mail;
import edu.school.utilities.JsfUtils;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.Stateless;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.jboss.logging.Logger;

@Stateless
public class MailController implements MailControllerLocal{
    
    public boolean sendMail(EmailAccount emailAccount, Mail mail){
        
        try {
            Properties prop = new Properties();
            prop.put("mail.smtp.host", emailAccount.getSmtp());
            prop.setProperty("mail.smtp.starttls.enable", "true");
            prop.setProperty("mail.smtp.port", emailAccount.getPuerto());
            prop.setProperty("mail.smtp.user", emailAccount.getUser());
            prop.setProperty("mail.smtp.auth", "true");
            
            Session session = Session.getDefaultInstance(prop, null);
            // parte con el text
            MimeBodyPart text = new MimeBodyPart();
            text.setContent(mail.getMessage(), "text/html; charset=utf-8");
            
            // parte con el adjunto
            MimeBodyPart adjunto = new MimeBodyPart();
            
            if (mail.getFilePath() != null && !mail.getFilePath().equals("")) {
                adjunto.setDataHandler(new DataHandler(
                        new FileDataSource(mail.getFilePath())));
                adjunto.setFileName(mail.getFileName());
            }
            
            // se crea el combinado
            MimeMultipart mimeMultipart = new MimeMultipart();
            if (mail.getFilePath() != null && !mail.getFilePath().equals("")) {
                mimeMultipart.addBodyPart(adjunto);
            }
            
            mimeMultipart.addBodyPart(text);
            
            
            
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAccount.getUser()));
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(mail.getRecipient()));
            message.setSubject(mail.getSubject());
            message.setContent(mimeMultipart);
            
            Transport transport = session.getTransport("smtp");
            transport.connect(emailAccount.getUser(), emailAccount.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(MailController.class).log(Logger.Level.ERROR, "Email no enviado", ex);
            JsfUtils.messageError("Correo no enviado, ha ocurrido un error: " + ex.getMessage());
            return false;
        }
       
    }
}
