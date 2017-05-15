package edu.school.ejb;

import edu.school.entities.Circular;
import edu.school.entities.CircularStatus;
import edu.school.entities.User;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

    @Override
    public CircularStatus findByCircular(final Circular circular) {
        CircularStatus circularStatus = null;
        try {
            String query = "FROM CircularStatus cs WHERE cs.circularId = :circular";
            TypedQuery<CircularStatus> q = em.createQuery(query, CircularStatus.class);
            q.setParameter("circular", circular);
            circularStatus = q.getSingleResult();
        } catch (Exception e) {
            System.err.println("dio error " + e);
        }
        return circularStatus;
    }

    @Override
    public CircularStatus findByUserAndStatus(final User user, final int status) {
        CircularStatus circularStatus = null;
        try {
            String query = "FROM CircularStatus cs JOIN Circular c "
                    + "WHERE cs.status = :status AND c.userId = :user";
            TypedQuery<CircularStatus> q = getEntityManager().createQuery(query, 
                    CircularStatus.class);
            q.setParameter("status", status);
            q.setParameter("user", user);
            circularStatus = q.getSingleResult();
        } catch (Exception e) {
        }
        return circularStatus;
    }
    
}
