package edu.school.controller;

import edu.school.entities.User;
import java.io.Serializable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class MainTemplateController implements Serializable{
    
    public void verifySession(){
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            User user = (User) context.getSessionMap().get("user");

            if (user == null) {
                context.redirect(context.getRequestContextPath() + "/notallowed.xhtml");
            }    
        } catch (Exception e) {
        }
        
    }
    
}
