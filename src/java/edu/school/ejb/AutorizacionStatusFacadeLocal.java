package edu.school.ejb;

import edu.school.entities.AutorizacionStatus;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AutorizacionStatusFacadeLocal {
    
    void create(AutorizacionStatus autorizacionStatus);
    
    void batchCreate(Collection<AutorizacionStatus> collection);

    void edit(AutorizacionStatus autorizacionStatus);
    
    void batchEdit(Collection<AutorizacionStatus> collection);

    void remove(AutorizacionStatus autorizacionStatus);

    AutorizacionStatus find(Object id);

    List<AutorizacionStatus> findAll();

    List<AutorizacionStatus> findRange(int[] range);

    int count();
}
