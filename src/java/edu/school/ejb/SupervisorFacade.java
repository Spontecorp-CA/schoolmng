package edu.school.ejb;

import edu.school.entities.Supervisor;
import edu.school.entities.User;
import edu.school.utilities.Constantes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

    @Override
    public Supervisor findByUser(User user) {
        Supervisor supervisor = null;
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Supervisor> criteria = cb.createQuery(Supervisor.class);
            Root<Supervisor> superv = criteria.from(Supervisor.class);
            criteria.select(superv)
                    .where(cb.equal(superv.get("userId"), user));
            TypedQuery<Supervisor> q = getEntityManager().createQuery(criteria);
            supervisor = q.getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(SupervisorFacade.class.getName())
                    .log(Level.WARNING, "El usuario {0} no es supervisor", user.getUsr());
        }
        return supervisor;
    }

    @Override
    public List<Supervisor> findByCi(Integer ci) {
        List<Supervisor> supervisores = null;
        try {
            String query = "FROM Supervisor s JOIN s.userId u WHERE u.ci = :ci";
            TypedQuery<Supervisor> q = getEntityManager().createQuery(query, Supervisor.class);
            q.setParameter("ci", ci);
            supervisores = q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(SupervisorFacade.class.getName())
                    .log(Level.SEVERE, "El usuario con C.I {0} no es supervisor", ci);
        }
        return supervisores;
    }

    @Override
    public List<Supervisor> findAllByUser(User user) {
        List<Supervisor> supervisores = null;
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Supervisor> criteria = cb.createQuery(Supervisor.class);
            Root<Supervisor> superv = criteria.from(Supervisor.class);
            criteria.select(superv)
                    .where(cb.equal(superv.get("userId"), user));
            TypedQuery<Supervisor> q = getEntityManager().createQuery(criteria);
            supervisores = q.getResultList();
        } catch (NoResultException e) {
            Logger.getLogger(SupervisorFacade.class.getName())
                    .log(Level.WARNING, "El usuario {0} no es supervisor", user.getUsr());
        }
        return supervisores;
    }

    
}
