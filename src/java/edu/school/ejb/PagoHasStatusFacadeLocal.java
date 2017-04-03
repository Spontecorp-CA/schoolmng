package edu.school.ejb;

import edu.school.entities.PagoHasStatus;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PagoHasStatusFacadeLocal {

    PagoHasStatus create(PagoHasStatus pagoHasStatus);

    void batchCreate(Collection<PagoHasStatus> lista);
    
    void edit(PagoHasStatus pagoHasStatus);
    
    void batchEdit(Collection<PagoHasStatus> lista);

    void remove(PagoHasStatus pagoHasStatus);

    PagoHasStatus find(Object id);

    List<PagoHasStatus> findAll();

    List<PagoHasStatus> findRange(int[] range);

    int count();
    
}
