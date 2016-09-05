package edu.school.controller.config;

import edu.school.ejb.UserFacadeLocal;
import edu.school.entities.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class ConfigDashboardController implements Serializable{
    
    @EJB
    private UserFacadeLocal userFacade;
    
    @Inject
    private User user;

    public User getUser() {
        user = (User)FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("user");
        return user;
    }
    
    public String goAdminUsers(){
        return "adminUsers";
    }
}
