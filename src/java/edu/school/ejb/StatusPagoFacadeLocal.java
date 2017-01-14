package edu.school.ejb;

import edu.school.entities.StatusPago;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.ejb.Local;

@Local
public interface StatusPagoFacadeLocal {

    void create(StatusPago statusPago);
    
    void batchCreate(Collection<StatusPago> collection);

    void edit(StatusPago statusPago);
    
    void batchEdit(Collection<StatusPago> collection);

    void remove(StatusPago statusPago);

    StatusPago find(Object id);
    
    Optional<StatusPago> findXNombre(String nombre);

    List<StatusPago> findAll();

    List<StatusPago> findRange(int[] range);

    int count();
    
}
