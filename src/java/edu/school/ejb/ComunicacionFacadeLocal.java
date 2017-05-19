package edu.school.ejb;

import edu.school.entities.Comunicacion;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ComunicacionFacadeLocal {

    void create(Comunicacion comunicacion);
    
    void batchCreate(Collection<Comunicacion> collection);

    void edit(Comunicacion comunicacion);
    
    void batchEdit(Collection<Comunicacion> collection);

    void remove(Comunicacion comunicacion);

    Comunicacion find(Object id);

    List<Comunicacion> findAll();

    List<Comunicacion> findRange(int[] range);

    int count();
    
}
