package edu.school.controller;

import edu.school.ejb.UserFacadeLocal;
import edu.school.entities.User;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public abstract class DashboardFacade {

    @EJB
    private UserFacadeLocal userFacade;

    @Inject
    private User user;

    public User getUser() {
        user = (User) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("user");
        return user;
    }
    
    public abstract String goUserPage();
}
