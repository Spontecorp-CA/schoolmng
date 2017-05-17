package edu.school.controller;

import edu.school.ejb.AutorizacionFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class NotificacionController implements Serializable{
    
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private AutorizacionFacadeLocal autorizacionFacade;
    
    
}
