package edu.school.ejb;

import edu.school.entities.Seccion;
import edu.school.entities.Curso;
import edu.school.entities.Periodo;
import edu.school.utilities.Constantes;
import edu.school.utilities.LogFiler;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class SeccionFacade extends AbstractFacade<Seccion> implements SeccionFacadeLocal {
    
    private static final LogFiler LOGGER = LogFiler.getInstance();

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeccionFacade() {
        super(Seccion.class);
    }

    @Override
    public Seccion find(String codigo, String nombre, Periodo periodo) {
        Seccion curso = null;
        try {
            String query = "FROM Seccion c WHERE c.codigo = :codigo AND "
                    + "c.nombre = :nombre AND c.periodoInt = :periodo";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("codigo", codigo);
            q.setParameter("nombre", nombre);
            q.setParameter("periodo", periodo);
            curso = (Seccion) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró una sección con código"
                    + " {0}, nombre {1} y período {2}", 
                    new Object[]{codigo, nombre, periodo.getNombre()});
        }
        return curso;
    }

    @Override
    public Seccion findByCodigo(String codigo) {
        Seccion curso = null;
        try {
            String query = "FROM Seccion c WHERE c.codigo = :codigo";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("codigo", codigo);
            curso = (Seccion) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No se encontró un curso con código {0}",
                            codigo);
        }
        return curso;
    }
    
    @Override
    public List<Seccion> findAllOrdered() {
        List<Seccion> cursos = null;
        try {
            String query = "FROM Seccion c ORDER BY c.periodoInt, c.cursoId";
            Query q = getEntityManager().createQuery(query);
            cursos = q.getResultList();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró secciones configuradas");
        }
        return cursos;
    }

    @Override
    public List<Seccion> findAll(Curso curso) {
        List<Seccion> cursos = null;
        try {
            String query = "FROM Seccion c WHERE c.cursoId = :curso";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("curso", curso);
            cursos = q.getResultList();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING,"No encontró secciones del curso {0}", 
                    curso.getNombre());
        }
        return cursos;
    }

    @Override
    public List<Seccion> findAllByPeriodoAndCurso(Periodo periodo, Curso curso) {
        List<Seccion> secciones = null;
        try {
            String query = "FROM Seccion c WHERE c.periodoId = :periodo "
                    + "AND c.cursoId = :curso";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("periodo", periodo);
            q.setParameter("curso", curso);
            secciones = q.getResultList();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró secciones del período"
                    + " {0} y curso {1}",
                    new Object[]{periodo.getNombre(), curso.getNombre()});
        }
        return secciones;
    }

    @Override
    public List<Seccion> findAllOrderedByCurso(final Periodo periodo) {
        List<Seccion> secciones = null;
        try {
            String query = "FROM Seccion s"
                    + "	JOIN s.cursoId c"
                    + " JOIN c.etapaId e"
                    + " WHERE s.periodoId = :periodo"
                    + " ORDER BY e.prefijo, c.nombre, s.seccion";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("periodo", periodo);
            secciones = q.getResultList();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró secciones del período"
                    + " {0}.",periodo.getNombre());
        }
        return secciones;
    }

    
}
