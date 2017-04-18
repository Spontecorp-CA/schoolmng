package edu.school.ejb;

import edu.school.entities.Circular;
import edu.school.entities.CircularStatus;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface CircularStatusFacadeLocal {
    void create(CircularStatus adminitrativo);

    void batchCreate(Collection<CircularStatus> circularStatusColl);

    void edit(CircularStatus adminitrativo);

    void batchEdit(Collection<CircularStatus> circularStatusColl);

    void remove(CircularStatus adminitrativo);

    CircularStatus find(Object id);

    List<CircularStatus> findAll();

    List<CircularStatus> findRange(int[] range);

    int count();

    CircularStatus findByCircular(final Circular circular);

}
