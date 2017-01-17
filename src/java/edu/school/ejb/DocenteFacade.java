package edu.school.ejb;

import edu.school.entities.Docente;
import edu.school.excepciones.DocenteNotFoundException;
import edu.school.utilities.Constantes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DocenteFacade extends AbstractFacade<Docente> implements DocenteFacadeLocal {
    
    private static final Logger LOGGER = Logger.getLogger(DocenteFacade.class.getName());

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DocenteFacade() {
        super(Docente.class);
    }

    @Override
    public Docente findByCi(int ci) throws DocenteNotFoundException{
        Docente docente = null;
        try {
            String query = "FROM Docente doc JOIN doc.userId u "
                    + "WHERE u.ci = :ci";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("ci", ci);
            docente = (Docente)q.getSingleResult();
        } catch (NoResultException e) {
            throw new DocenteNotFoundException();
        }
        return docente;
    }
    
}
