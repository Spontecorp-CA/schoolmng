package edu.school.ejb;

import edu.school.entities.AutorizacionStatus;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AutorizacionStatusFacadeLocal {
    
    void create(AutorizacionStatus autorizacionStatus);

    void edit(AutorizacionStatus autorizacionStatus);

    void remove(AutorizacionStatus autorizacionStatus);

    AutorizacionStatus find(Object id);

    List<AutorizacionStatus> findAll();

    List<AutorizacionStatus> findRange(int[] range);

    int count();
}
