package edu.school.ejb;

import edu.school.entities.Circular;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CircularFacadeLocal {
    
    void create(Circular circular);
    
    void batchCreate(Collection<Circular> collection);

    void edit(Circular circular);
    
    void batchEdit(Collection<Circular> collection);

    void remove(Circular circular);

    Circular find(Object id);
    
    Circular findCircularByCodigoCircular(String codigo);

    List<Circular> findAll();

    List<Circular> findRange(int[] range);

    int count();
}
