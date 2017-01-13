package edu.school.ejb;

import edu.school.entities.AutorizacionStatus;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AutorizacionStatusFacade extends AbstractFacade<AutorizacionStatus> 
            implements AutorizacionStatusFacadeLocal{
    
    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    public AutorizacionStatusFacade() {
        super(AutorizacionStatus.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
