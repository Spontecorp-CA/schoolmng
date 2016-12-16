package edu.school.controller.config;

import edu.school.ejb.NivelFacadeLocal;
import edu.school.entities.Nivel;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
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
//        if (nivelFacade.findByNombre(nivel.getNombre()) == null) {
//            
//            nivelFacade.create(nivel);
//
//            nivel.setNombre("");
//            nivel.setPrefijo(null);
//            nivel.setEtapa("");
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_INFO,
//                            "Operación exitosa",
//                            "Nivel: " +nivel.getNombre() + ", creado con éxito"));
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                            "Nivel ya existe",
//                            "El nombre del Nivel "+ nivel.getNombre()
//                                    +" ya existe, trate con otro nombre"));
//        }
        
        if(!nivelFacade.exist(nivel)){
            nivelFacade.create(nivel);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Operación exitosa",
                            "Nivel: " + nivel.getNombre()
                            + ", etapa: " + nivel.getEtapa()
                            + ", creado con éxito"));
            nivel.setNombre("");
            nivel.setPrefijo(null);
            nivel.setEtapa("");
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Nivel ya existe",
                            "El nivel " + nivel.getNombre()
                            + " con etapa " + nivel.getEtapa()
                            + " ya existe, trate con otro nombre y/o etapa"));
        }
    }

    public List<Nivel> getNiveles() {
        List<Nivel> niveles = nivelFacade.findAll();
        niveles = niveles.stream()
                .sorted((n1,n2) -> Integer.compare(n1.getPrefijo(), n2.getPrefijo()))
                .collect(Collectors.toList());
        return niveles;
    }    
}
