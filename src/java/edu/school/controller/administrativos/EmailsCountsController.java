package edu.school.controller.administrativos;

import edu.school.controller.DashboardFacade;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.entities.EmailAccount;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class EmailsCountsController extends DashboardFacade implements Serializable{
    
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

    
    public void saveCount(){
        
    }
    
    public void clearFields(){}
}
