package edu.school.ejb;

import edu.school.entities.HasSupervisor;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class HasSupervisorFacade extends AbstractFacade<HasSupervisor> 
        implements HasSupervisorFacadeLocal {
    
    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    public HasSupervisorFacade() {
        super(HasSupervisor.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
