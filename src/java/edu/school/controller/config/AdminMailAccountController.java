package edu.school.controller.config;

import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.entities.EmailAccount;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminMailAccountController implements Serializable{
    
    @EJB
    private EmailAccountFacadeLocal emailAccountFacade;
    @Inject
    private EmailAccount emailAccount;

    public EmailAccount getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(EmailAccount emailAccount) {
        this.emailAccount = emailAccount;
    }
    
    public void saveEmailAccount(){
        emailAccountFacade.create(emailAccount);
        clearFields();
    }
    
    public void clearFields(){
        emailAccount.setNombre("");
        emailAccount.setUser("");
        emailAccount.setPassword("");
        emailAccount.setSmtp("");
        emailAccount.setPuerto("");
    }
    
    public List<EmailAccount> getEmailAccounts(){
        return emailAccountFacade.findAll();
    }
    
}
