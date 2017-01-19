package edu.school.ejb;

import edu.school.entities.Seccion;
import edu.school.entities.Curso;
import edu.school.entities.Periodo;
import edu.school.utilities.Constantes;
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
            Logger.getLogger(SeccionFacade.class.getName())
                    .log(Level.WARNING, "No se encontró un curso con código {0}",
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
        } catch (Exception e) {
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
        } catch (Exception e) {
            System.err.println("Error en SeccionFacade metodo findAll(Curso curso): " 
                    + e.getMessage());
        }
        return cursos;
    }

    @Override
    public List<Seccion> findAll(Periodo periodo, Curso curso) {
        List<Seccion> cursos = null;
        try {
            String query = "FROM Seccion c WHERE c.periodoInt = :periodo "
                    + "AND c.cursoId = :curso";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("periodo", periodo);
            q.setParameter("curso", curso);
            cursos = q.getResultList();
        } catch (Exception e) {
        }
        return cursos;
    }

}