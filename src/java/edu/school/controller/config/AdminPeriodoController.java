package edu.school.controller.config;

import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.entities.Periodo;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

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

    public String cancelAction() {
        return "dashboard";
    }

    public void clearFields() {
        nombre = "";
    }

    public void crearPeriodo() {
        if (periodoFacade.find(nombre) == null) {
            periodo = new Periodo();
            periodo.setNombre(nombre);

            periodoFacade.create(periodo);

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Periodo creado con éxito",
                            "Periodo creado con éxito"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Periodo ya existe",
                            "El nombre del Periodo ya existe, trate con otro nombre"));
        }
    }
    
    public List<Periodo> getPeriodos(){
        return periodoFacade.findAll();
    }
    
    public void onRowEdit(RowEditEvent event) {
        periodo = (Periodo) event.getObject();
        
        System.out.println("nuevo período: " + periodo.getNombre());
        
        periodoFacade.edit(periodo);
        FacesMessage msg = new FacesMessage("Período Editado", 
                periodo.getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        periodo = (Periodo) event.getObject();
        FacesMessage msg = new FacesMessage("Edit Cancelled", 
                periodo.getNombre());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, 
                    "Cell Changed", "Old: " + oldValue + ", "
                            + "New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
}
