package edu.school.ejb;

import edu.school.entities.Mail;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MailFacadeLocal {
    
    Mail create(Mail autorizacion);
    
    void batchCreate(Collection<Mail> collection);

    void edit(Mail autorizacion);
    
    void batchEdit(Collection<Mail> collection);

    void remove(Mail autorizacion);

    Mail find(Object id);

    List<Mail> findAll();

    List<Mail> findRange(int[] range);

    int count();
}
