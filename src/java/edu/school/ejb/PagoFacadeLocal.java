package edu.school.ejb;

import edu.school.entities.Pago;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PagoFacadeLocal {

    Pago create(Pago pago);

    void batchCreate(Collection<Pago> collection);
    
    void edit(Pago pago);
    
    void batchEdit(Collection<Pago> collection);

    void remove(Pago pago);

    Pago find(Object id);

    List<Pago> findAll();

    List<Pago> findRange(int[] range);

    int count();
    
}
