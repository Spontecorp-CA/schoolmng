package edu.school.ejb;

import edu.school.entities.Periodo;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PeriodoFacadeLocal {

    void create(Periodo periodo);

    void batchCreate(Collection<Periodo> collection);
    
    void edit(Periodo periodo);
    
    void batchEdit(Collection<Periodo> collection);

    void remove(Periodo periodo);

    Periodo find(Object id);
    
    Periodo find(String nombre);

    List<Periodo> findAll();

    List<Periodo> findRange(int[] range);

    int count();
    
}
