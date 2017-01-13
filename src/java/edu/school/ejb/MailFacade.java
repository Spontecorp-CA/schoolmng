package edu.school.ejb;

import edu.school.entities.Mail;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class MailFacade extends AbstractFacade<Mail> implements MailFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;
    
    public MailFacade() {
        super(Mail.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
}
