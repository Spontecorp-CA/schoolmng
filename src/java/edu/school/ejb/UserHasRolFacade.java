package edu.school.ejb;

import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import edu.school.utilities.Constantes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UserHasRolFacade extends AbstractFacade<UserHasRol> implements UserHasRolFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserHasRolFacade() {
        super(UserHasRol.class);
    }

    @Override
    public List<UserHasRol> findAll(User user) {
        List<UserHasRol> lista = null;
        try {
            String query= "FROM UserHasRol uhr WHERE uhr.userId = :user";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("user", user);
            lista = q.getResultList();
        } catch (Exception e) {
        }
        return lista;
    }
    
}
