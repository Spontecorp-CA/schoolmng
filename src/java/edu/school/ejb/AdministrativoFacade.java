package edu.school.ejb;

import static com.sun.xml.ws.spi.db.BindingContextFactory.LOGGER;
import edu.school.entities.Administrativo;
import edu.school.utilities.Constantes;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AdministrativoFacade extends AbstractFacade<Administrativo> implements AdministrativoFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdministrativoFacade() {
        super(Administrativo.class);
    }
    
    @Override
    public Administrativo findByCi(int ci) {
        Administrativo administrativo = null;
        try {
            String query = "FROM Administrativo doc JOIN doc.userId u "
                    + "WHERE u.ci = :ci";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("ci", ci);
            administrativo = (Administrativo) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
        return administrativo;
    }
    
}
