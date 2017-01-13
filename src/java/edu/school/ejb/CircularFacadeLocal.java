package edu.school.ejb;

import edu.school.entities.Circular;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CircularFacadeLocal {
    
    void create(Circular autorizacion);

    void edit(Circular autorizacion);

    void remove(Circular autorizacion);

    Circular find(Object id);

    List<Circular> findAll();

    List<Circular> findRange(int[] range);

    int count();
}
