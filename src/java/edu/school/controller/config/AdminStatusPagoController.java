package edu.school.controller.config;

import edu.school.ejb.StatusPagoFacadeLocal;
import edu.school.entities.StatusPago;
import edu.school.utilities.JsfUtils;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminStatusPagoController implements Serializable {

    @Inject
    private StatusPago statusPago;
    @EJB
    private StatusPagoFacadeLocal statusPagoFacade;

    public StatusPago getStatusPago() {
        return statusPago;
    }

    public void setStatusPago(StatusPago statusPago) {
        this.statusPago = statusPago;
    }

    public String cancelAction() {
        return "dashboard?faces-redirect=true";
    }

    public void clearFields() {
        statusPago.setNombre(null);
        statusPago.setValor(null);
    }

    public void crearStatusPago() {
        Optional<StatusPago> statusOptional = statusPagoFacade.findXNombre(statusPago.getNombre());
        if(statusOptional.isPresent()){
            JsfUtils.messageError("El nombre del Status de Pago ya existe, trate con otro nombre");
        } else {
            statusPagoFacade.create(statusPago);
            JsfUtils.messageSuccess("Status de Pago creado con éxito");
        }
        
        
    }
    
    public List<StatusPago> getStatusPagos(){
        return statusPagoFacade.findAll();
    }
    
}
