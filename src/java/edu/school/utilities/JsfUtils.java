package edu.school.utilities;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JsfUtils {
    
    public static void messageSuccess(String message){
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO, 
                        "Operación exitosa", 
                        message));
    }
    
    public static void messageWarning(String message){
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(
                        FacesMessage.SEVERITY_WARN, 
                        "Advertencia", 
                        message));
    }
    
    public static void messageError(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Operación con errores",
                        message));
    }
}
