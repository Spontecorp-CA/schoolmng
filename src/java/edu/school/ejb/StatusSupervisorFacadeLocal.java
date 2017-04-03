package edu.school.ejb;

import edu.school.entities.StatusSupervisor;
import edu.school.entities.Supervisor;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface StatusSupervisorFacadeLocal {
    
    StatusSupervisor create(StatusSupervisor supervisor);

    void batchCreate(Collection<StatusSupervisor> collection);

    void edit(StatusSupervisor supervisor);

    void batchEdit(Collection<StatusSupervisor> collection);

    void remove(StatusSupervisor supervisor);

    StatusSupervisor find(Object id);

    List<StatusSupervisor> findAll();

    List<StatusSupervisor> findRange(int[] range);

    int count();

    StatusSupervisor findByGrupo(Object obj);

    List<StatusSupervisor> findAllByStatus(int status);

    StatusSupervisor findBySupervisor(Supervisor supervisor);
}
