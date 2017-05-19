package edu.school.ejb;

import edu.school.entities.Circular;
import edu.school.entities.CircularStatus;
import edu.school.entities.User;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface CircularStatusFacadeLocal {
    
    void create(CircularStatus circularStatus);

    void batchCreate(Collection<CircularStatus> circularStatusColl);

    void edit(CircularStatus circularStatus);

    void batchEdit(Collection<CircularStatus> circularStatusColl);

    void remove(CircularStatus circularStatuss);

    CircularStatus find(Object id);

    List<CircularStatus> findAll();

    List<CircularStatus> findRange(int[] range);

    int count();

    CircularStatus findByCircular(final Circular circular);

    CircularStatus findByUserAndStatus(final User user, final int status);

}
