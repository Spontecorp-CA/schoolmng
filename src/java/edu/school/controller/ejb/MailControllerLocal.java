package edu.school.controller.ejb;

import edu.school.entities.EmailAccount;
import edu.school.entities.Mail;
import javax.ejb.Local;

@Local
public interface MailControllerLocal {
    
    boolean sendMail(EmailAccount emailAccount, Mail mail);
}
