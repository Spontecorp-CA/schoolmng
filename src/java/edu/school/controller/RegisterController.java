package edu.school.controller;

import edu.school.ejb.DatosPersonaFacadeLocal;
import edu.school.ejb.UserFacadeLocal;
import edu.school.entities.DatosPersona;
import edu.school.entities.User;
import edu.school.utilities.Utilities;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class RegisterController implements Serializable{
    
    @EJB
    private UserFacadeLocal userFacade;
    @EJB
    private DatosPersonaFacadeLocal datosPersonaFacade;
    
    @Inject
    private User user;
    @Inject
    private DatosPersona datosPersona;
    
    private int ci;
    private String email;
    private String password;
    
    private final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public int getCi() {
        return ci;
    }

    public void setCi(int ci) {
        this.ci = ci;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEMAIL_PATTERN() {
        return EMAIL_PATTERN;
    }
    
    public void verifyCI(){
        user = userFacade.find(ci);
        if(user == null){
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN, 
                            "Usuario no registrado", 
                            "Comuníquese con la administración del colegio"));
            return;
        }
        
        user.setUsr(email.trim());
        user.setPsw(Utilities.getSecurePassword(password));
        
        datosPersona = datosPersonaFacade.find(ci);
        datosPersona.setEmail(email);
        
        userFacade.edit(user);
        datosPersonaFacade.edit(datosPersona);
        
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO,
                        "Usuario registrado con éxito",
                        "Usuario registrado con éxito"));
    }
}
