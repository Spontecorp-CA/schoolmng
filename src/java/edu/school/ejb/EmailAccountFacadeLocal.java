package edu.school.ejb;

import edu.school.entities.EmailAccount;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EmailAccountFacadeLocal {

    void create(EmailAccount emailAccount);

    void edit(EmailAccount emailAccount);

    void remove(EmailAccount emailAccount);

    EmailAccount find(Object id);

    List<EmailAccount> findAll();

    List<EmailAccount> findRange(int[] range);

    int count();
    
}
