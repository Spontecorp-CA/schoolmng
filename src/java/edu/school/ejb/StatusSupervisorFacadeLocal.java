package edu.school.ejb;

import edu.school.entities.StatusSupervisor;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface StatusSupervisorFacadeLocal {
    
    void create(StatusSupervisor supervisor);

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
}