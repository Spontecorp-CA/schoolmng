package edu.school.ejb;

import edu.school.entities.Mail;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MailFacadeLocal {
    
    void create(Mail autorizacion);

    void edit(Mail autorizacion);

    void remove(Mail autorizacion);

    Mail find(Object id);

    List<Mail> findAll();

    List<Mail> findRange(int[] range);

    int count();
}
