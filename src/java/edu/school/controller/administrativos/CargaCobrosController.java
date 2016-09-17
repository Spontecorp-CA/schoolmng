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
    
    private Double monto;
    private boolean repeat;
    private Integer meses;

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public Integer getMeses() {
        return meses;
    }

    public void setMeses(Integer meses) {
        this.meses = meses;
    }
    
    public void createPago(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        pago.setMonto(monto);
        //pagoFacade.create(pago);
         
        sb.append("El cobro a crear es  ").append(pago.getMotivo());
        sb.append(" por un monto de ").append(pago.getMonto());
        sb.append(" en la fecha de ").append(df.format(pago.getFecha()));
        sb.append(" con vencimiento ").append(df.format(pago.getFechaVencimiento()));
        if(repeat){
            sb.append(" y se repetirá por ").append(meses).append(" meses.");
        }
        
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
        monto = null;
        meses = null;
        repeat = false;
        pago.setFecha(null);
        pago.setFechaVencimiento(null);
    }
}
