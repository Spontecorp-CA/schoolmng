package edu.school.controller;

import edu.school.entities.User;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class MainTemplateController implements Serializable{
    
    public void verifySession(){
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            User user = (User) context.getExternalContext().getSessionMap().get("user");

            if (user == null) {
                context.getExternalContext().redirect("notallowed.xhtml");
            }    
        } catch (Exception e) {
        }
        
    }
    
}
