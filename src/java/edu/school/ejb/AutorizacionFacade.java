package edu.school.ejb;

import edu.school.entities.Autorizacion;
import edu.school.entities.Supervisor;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AutorizacionFacade extends AbstractFacade<Autorizacion> implements AutorizacionFacadeLocal{

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;
    
    public AutorizacionFacade() {
        super(Autorizacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public long countBySupervisor(Supervisor supervisor) {
        long cuenta = 0;
        try {
            String query = "SELECT COUNT(a) FROM Autorizacion a "
                    + "WHERE a.supervisorId = :supervisor";
            Query q = getEntityManager().createQuery(query, Autorizacion.class);
            q.setParameter("supervisor", supervisor);
            cuenta = (Long)q.getSingleResult();
        } catch (Exception e) {
        }
        return cuenta;
    }
    
}
