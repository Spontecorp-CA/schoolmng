package edu.school.controller.administrativos;

import edu.school.ejb.PagoFacadeLocal;
import edu.school.entities.Pago;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ListaCobrosController implements Serializable {
    
    @EJB
    private PagoFacadeLocal pagoFacade;
    
    static final Comparator<Pago> COMPARATOR_BY_DATE 
            = (Pago p1, Pago p2) -> p2.getFecha().compareTo(p1.getFecha());
    
    public List<Pago> pagos(){
        List<Pago> lista = pagoFacade.findAll();
        lista = lista.stream()
                    .sorted(COMPARATOR_BY_DATE)
                    .collect(Collectors.toList());
        
        return lista;
    }
    
    
}
