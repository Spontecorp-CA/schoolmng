package edu.school.ejb;

import edu.school.entities.Docente;
import edu.school.entities.Periodo;
import edu.school.entities.SeccionHasDocente;
import edu.school.excepciones.DocenteNotFoundException;
import edu.school.utilities.Constantes;
import edu.school.utilities.LogFiler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class DocenteFacade extends AbstractFacade<Docente> implements DocenteFacadeLocal {
    
    private static final LogFiler LOGGER = LogFiler.getInstance();

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

    @Override
    public List<Docente> findAllByPeriodoWSeccionAssigned(Periodo periodo) {
        final List<Docente> docentes = new ArrayList<>();
        try {
            String query = "FROM SeccionHasDocente shd "
                    + "JOIN shd.docenteId d "	
                    + "JOIN shd.seccionId s " 
                    + "WHERE s.periodoId = :periodo";
            TypedQuery<SeccionHasDocente> q = getEntityManager()
                    .createQuery(query, SeccionHasDocente.class);
            q.setParameter("periodo", periodo);
            List<SeccionHasDocente> shdList = q.getResultList();
                shdList.forEach((scHD) -> {
                    docentes.add(scHD.getDocenteId());
                });                     
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró docentes con secciones "
                    + "asignadas en el periodo {0}", periodo.getNombre());
        }
        
        return docentes;
    }
}
