package edu.school.ejb;

import edu.school.entities.EmailAccount;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EmailAccountFacade extends AbstractFacade<EmailAccount> implements EmailAccountFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmailAccountFacade() {
        super(EmailAccount.class);
    }
    
}
