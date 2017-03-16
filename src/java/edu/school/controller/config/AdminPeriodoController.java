package edu.school.controller.config;

import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.entities.Periodo;
import edu.school.utilities.Constantes;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminPeriodoController implements Serializable {

    @Inject
    private Periodo periodo;
    @EJB
    private PeriodoFacadeLocal periodoFacade;

    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String cancelAction() {
        return "dashboard";
    }

    public void clearFields() {
        nombre = "";
    }

    public void crearPeriodo() {
        
        Optional<Periodo> optPeriodo = Optional.ofNullable(periodoFacade.findByNombre(nombre));
        
        if(optPeriodo.isPresent()){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Periodo ya existe",
                            "El nombre del Periodo ya existe, trate con otro nombre"));
        } else {
            optPeriodo = Optional.ofNullable(periodoFacade.findByStatus(Constantes.PERIODO_ACTIVO));
            if(optPeriodo.isPresent()){
                Periodo periodoOld = optPeriodo.get();
                periodoOld.setStatus(Constantes.PERIODO_INACTIVO);
                periodoFacade.edit(periodoOld);
            }
            periodo = new Periodo();
            periodo.setNombre(nombre);
            periodo.setStatus(Constantes.PERIODO_ACTIVO);

            periodoFacade.create(periodo);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Periodo creado con éxito",
                            "Periodo creado con éxito"));
        }
        
    }
    
    public List<Periodo> getPeriodos(){
        return periodoFacade.findAll();
    }
    
}
