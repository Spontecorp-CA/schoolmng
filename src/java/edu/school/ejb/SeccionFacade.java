package edu.school.ejb;

import edu.school.entities.Seccion;
import edu.school.entities.Curso;
import edu.school.entities.Docente;
import edu.school.entities.Etapa;
import edu.school.entities.Periodo;
import edu.school.utilities.Constantes;
import edu.school.utilities.LogFiler;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
        Seccion seccion = null;
        try {
            String query = "FROM Seccion c WHERE c.codigo = :codigo AND "
                    + "c.nombre = :nombre AND c.periodoInt = :periodo";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("codigo", codigo);
            q.setParameter("nombre", nombre);
            q.setParameter("periodo", periodo);
            seccion = (Seccion) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró una sección con código"
                    + " {0}, nombre {1} y período {2}", 
                    new Object[]{codigo, nombre, periodo.getNombre()});
        }
        return seccion;
    }

    @Override
    public Seccion findByPeriodoAndCursoAndSeccion(final Periodo periodo, 
            final Curso curso, final String seccionNombre) {
        Seccion seccion = null;
        try {
            String query = "FROM Seccion s WHERE s.periodoId = :periodo AND "
                    + "s.cursoId = :curso AND s.seccion = :seccionNombre";
            TypedQuery<Seccion> q = getEntityManager().createQuery(query, Seccion.class);
            q.setParameter("periodo", periodo);
            q.setParameter("curso", curso);
            q.setParameter("seccionNombre", seccionNombre);
            seccion = q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró una sección del periodo"
                    + " {0}, del curso {1} y sección {2}",
                    new Object[]{periodo.getNombre(), curso.getNombre(), 
                                seccionNombre});
        }
        return seccion;
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
            LogFiler.logger.log(Level.WARNING, "No se encontró una seccion con código {0}",
                            codigo);
        }
        return curso;
    }
    
    @Override
    public List<Seccion> findAllOrdered() {
        List<Seccion> cursos = null;
        try {
            String query = "FROM Seccion c ORDER BY c.periodoId, c.cursoId";
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
            String query = "FROM Seccion s WHERE s.periodoId = :periodo "
                    + "AND s.cursoId = :curso "
                    + "ORDER BY s.seccion";
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

    @Override
    public List<Seccion> findAllOrderedByEtapa(final Etapa etapa) {
        List<Seccion> secciones = null;
        try {
            String query = "FROM Seccion s"
                    + "	JOIN s.cursoId c"
                    + " WHERE c.etapaId = :etapa"
                    + " ORDER BY c.nombre, s.seccion";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("etapa", etapa);
            secciones = q.getResultList();
        } catch (NoResultException e) {
        }
        return secciones;
    }

    @Override
    public List<Seccion> findAllOrderedByGrado(final Curso curso, final Periodo periodo) {
        List<Seccion> secciones = null;
        try {
            String query = "FROM Seccion s"
                    + " WHERE s.cursoId = :curso AND s.periodoId = :periodo"
                    + " ORDER BY s.cursoId.nombre, s.seccion";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("curso", curso);
            q.setParameter("periodo", periodo);
            secciones = q.getResultList();
        } catch (NoResultException e) {
        }
        return secciones;
    }

    @Override
    public List<Seccion> findAllOrderedBySeccion(Periodo periodo) {
        List<Seccion> secciones = null;
        try {
            String query = "FROM Seccion s"
                    + " WHERE s.periodoId = :periodo"
                    + " ORDER BY s.codigo, s.seccion";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("periodo", periodo);
            secciones = q.getResultList();
        } catch (NoResultException e) {
        }
        return secciones;
    }

}
