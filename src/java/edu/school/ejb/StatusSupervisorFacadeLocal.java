package edu.school.ejb;

import edu.school.entities.StatusSupervisor;
import edu.school.entities.Supervisor;
import edu.school.excepciones.SupervisorNotFoundException;
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

    StatusSupervisor findByGrupo(Object obj) throws SupervisorNotFoundException;

    List<StatusSupervisor> findAllByStatus(int status);

    StatusSupervisor findBySupervisor(Supervisor supervisor);

    List<StatusSupervisor> findColegioSupervisor(final int status);
}
