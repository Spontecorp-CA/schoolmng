package edu.school.controller.administrativos;

import edu.school.ejb.PagoFacadeLocal;
import edu.school.entities.Pago;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped
public class CargaCobrosController implements Serializable{
    
    @EJB
    private PagoFacadeLocal pagoFacade;
    
    @Inject 
    private Pago pago;
    
    private String monto;

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }
    
    public void createPago(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        try {
            double montoBs = Double.parseDouble(monto);
            pago.setMonto(montoBs);
        } catch (NumberFormatException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            e.getMessage(),
                            "debe ingresar un número en al campo 'Monto'"));
            return;
        }
        
        pagoFacade.create(pago);
         
        sb.append("El cobro a crear es  ").append(pago.getMotivo());
        sb.append(" por un monto de ").append(pago.getMonto());
        sb.append(" en la fecha de ").append(df.format(pago.getFecha()));
        sb.append(" con vencimiento ").append(df.format(pago.getFechaVencimiento()));
        System.out.println(sb.toString());
        
        clearFields();
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Operación exitosa",
                        "Cobro cargado con éxito"));
    }
    
    public void clearFields(){
        pago.setMotivo("");
        monto = "";
        pago.setFecha(null);
        pago.setFechaVencimiento(null);
    }
}
