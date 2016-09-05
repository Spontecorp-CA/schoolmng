package edu.school.ejb;

import edu.school.entities.User;
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

    @PersistenceContext(unitName = "schoolmngPU")
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
}
