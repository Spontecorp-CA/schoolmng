package edu.school.ejb;

import edu.school.entities.CircularStatus;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CircularStatusFacade extends AbstractFacade<CircularStatus> 
        implements CircularStatusFacadeLocal{
    
    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    public CircularStatusFacade() {
        super(CircularStatus.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
