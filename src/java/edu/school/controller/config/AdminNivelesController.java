package edu.school.controller.config;

import edu.school.ejb.NivelFacadeLocal;
import edu.school.entities.Nivel;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminNivelesController implements Serializable{

    @EJB
    private NivelFacadeLocal nivelFacade;
    
    @Inject
    private Nivel nivel;

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
    
    public String cancelAction() {
        return "dashboard";
    }

    public void crearNivel() {
        if (nivelFacade.findByNombre(nivel.getNombre()) == null) {
            
            nivelFacade.create(nivel);

            nivel.setNombre("");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Operación exitosa",
                            "Nivel: " +nivel.getNombre() + ", creado con éxito"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Nivel ya existe",
                            "El nombre del Nivel "+ nivel.getNombre()
                                    +" ya existe, trate con otro nombre"));
        }
    }

    public List<Nivel> getNiveles() {
        return nivelFacade.findAll();
    }    
}
