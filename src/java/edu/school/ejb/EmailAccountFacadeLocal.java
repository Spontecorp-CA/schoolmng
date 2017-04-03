package edu.school.ejb;

import edu.school.entities.EmailAccount;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EmailAccountFacadeLocal {

    EmailAccount create(EmailAccount emailAccount);
    
    void batchCreate(Collection<EmailAccount> collection);

    void edit(EmailAccount emailAccount);
    
    void batchEdit(Collection<EmailAccount> collection);

    void remove(EmailAccount emailAccount);

    EmailAccount find(Object id);

    List<EmailAccount> findAll();

    List<EmailAccount> findRange(int[] range);

    int count();
    
}
