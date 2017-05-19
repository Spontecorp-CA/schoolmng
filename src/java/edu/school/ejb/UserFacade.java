package edu.school.ejb;

import edu.school.entities.User;
import edu.school.utilities.Constantes;
import edu.school.utilities.LogFiler;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jgcastillo
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    @Override
    public User find(String usuario){
        User user = null;
        try {
            String query = "FROM User u WHERE u.usr = :usuario";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("usuario", usuario);
            user = (User)q.getSingleResult();
        } catch (NoResultException e) {
        }
        return user;
    }

    @Override
    public User findByCi(int ci) {
        User user = null;
        try {
            String query = "FROM User u WHERE u.ci = :ci";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("ci", ci);
            user = (User) q.getSingleResult();
        } catch (NoResultException e) {
            LogFiler.logger.log(Level.WARNING, "La CI: {0} no se encuentra registrada", ci);
        }
        return user;
    }
    
    
}
