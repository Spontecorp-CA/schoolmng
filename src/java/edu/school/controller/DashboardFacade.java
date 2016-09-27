package edu.school.controller;

import edu.school.entities.User;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class DashboardFacade {

    @Inject
    private User user;

    public User getUser() {
        user = (User) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("user");
        return user;
    }
    
    public String goUserPage(){
        return "dashboard?faces-redirect=true";
    }
}
