package edu.school.ejb;

import edu.school.entities.Recipiente;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface RecipienteFacadeLocal {

    void create(Recipiente recipiente);
    
    void batchCreate(Collection<Recipiente> recipienteColl);

    void edit(Recipiente recipiente);
    
    void batchEdit(Collection<Recipiente> recipienteColl);

    void remove(Recipiente recipiente);

    Recipiente find(Object id);

    List<Recipiente> findAll();

    List<Recipiente> findRange(int[] range);

    int count();
    
}
