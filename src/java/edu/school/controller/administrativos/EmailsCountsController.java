package edu.school.controller.administrativos;

import edu.school.controller.DashboardFacade;
import edu.school.ejb.EmailAccountFacadeLocal;
import edu.school.entities.EmailAccount;
import edu.school.utilities.JsfUtils;
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
        if(emailAccount != null){
            System.out.println("La cuenta es: " + emailAccount.getNombre()
                    + "\nusuario: " + emailAccount.getUser()
                    + "\npassword: " + emailAccount.getPassword()
                    + "\nsmtp: " + emailAccount.getSmtp()
                    + "\npuerto: " + emailAccount.getPuerto());
            emailAccountFacade.create(emailAccount);
            JsfUtils.messageSuccess("Cuenta creada con éxito");
        } else {
            JsfUtils.messageWarning("Hay datos que no se han completado");
        }
    }
    
    public void clearFields(){
        emailAccount.setNombre(null);
        emailAccount.setSmtp(null);
        emailAccount.setPassword(null);
        emailAccount.setPuerto(null);
        emailAccount.setUser(null);
    }
}
