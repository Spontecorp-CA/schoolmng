package edu.school.ejb;

import edu.school.entities.Supervisor;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SupervisorFacade extends AbstractFacade<Supervisor> 
        implements SupervisorFacadeLocal {
    
    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    public SupervisorFacade() {
        super(Supervisor.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
}
