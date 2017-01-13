package edu.school.ejb;

import edu.school.entities.Circular;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CircularFacade extends AbstractFacade<Circular> implements CircularFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;
    
    public CircularFacade() {
        super(Circular.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    
}
