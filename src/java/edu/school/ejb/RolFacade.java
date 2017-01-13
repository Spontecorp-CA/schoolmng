package edu.school.ejb;

import edu.school.entities.Rol;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jgcastillo
 */
@Stateless
public class RolFacade extends AbstractFacade<Rol> implements RolFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }

    @Override
    public Rol find(String name) {
        Rol rol = null;
        try {
            String query = "SELECT r FROM Rol r WHERE r.name = :name";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("name", name);
            rol = (Rol)q.getSingleResult();
        } catch (Exception e) {
        }
        return rol;
    }
    
}
