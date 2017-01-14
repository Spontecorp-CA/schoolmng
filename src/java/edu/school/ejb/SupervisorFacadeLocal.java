package edu.school.ejb;

import edu.school.entities.Supervisor;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SupervisorFacadeLocal {
    
    void create(Supervisor supervisor);

    void batchCreate(Collection<Supervisor> collection);

    void edit(Supervisor supervisor);

    void batchEdit(Collection<Supervisor> collection);

    void remove(Supervisor supervisor);

    Supervisor find(Object id);

    List<Supervisor> findAll();

    List<Supervisor> findRange(int[] range);

    int count();
}
