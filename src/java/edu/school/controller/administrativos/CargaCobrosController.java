package edu.school.controller.administrativos;

import edu.school.ejb.PagoFacadeLocal;
import edu.school.entities.Pago;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
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
    
    private boolean repeat;
    private Integer meses;
    
    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
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
        saveCobros();
        clearFields();
        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Operación exitosa",
                        "Cobro(s) cargado(s) con éxito"));
    }
    
    public void clearFields(){
        pago.setMotivo("");
        pago.setMonto(null);
        pago.setFecha(null);
        pago.setFechaVencimiento(null);
        repeat = false;
        meses = null;
    }
    
    private void saveCobros(){
        pagoFacade.create(pago);
        if(repeat){
            for(int i = 0; i < meses - 1; i++){
                pago.setFecha(fechaOffset(pago.getFecha()));
                pago.setFechaVencimiento(fechaOffset(pago.getFechaVencimiento()));
                pagoFacade.create(pago);
            }
        }
    }
    
    private Date fechaOffset(Date fecha){
        LocalDate ld = LocalDateTime.ofInstant(fecha.toInstant(), 
                        ZoneId.systemDefault()).toLocalDate();
        ld = ld.plusDays(30);
        if(ld.getDayOfWeek() == DayOfWeek.SATURDAY){
            ld = ld.plusDays(2);
        } else if (ld.getDayOfWeek() == DayOfWeek.SUNDAY){
            ld = ld.plusDays(1);
        }
        return Date.from(ld.atStartOfDay()
                            .atZone(ZoneId.systemDefault()).toInstant());
    }
}
