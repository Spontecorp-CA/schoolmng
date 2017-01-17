package edu.school.ejb;

import edu.school.entities.HasSupervisor;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface HasSupervisorFacadeLocal {

    public void create(HasSupervisor hasSupervisor);

    public void batchCreate(Collection<HasSupervisor> col);

    public void edit(HasSupervisor hasSupervisor);

    public void batchEdit(Collection<HasSupervisor> col);

    public void remove(HasSupervisor hasSupervisor);

    public HasSupervisor find(Object id);

    public List<HasSupervisor> findAll();

    public List<HasSupervisor> findRange(int[] range);

    public int count();

}
