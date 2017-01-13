package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.entities.Nivel;
import edu.school.entities.Periodo;
import edu.school.utilities.Constantes;
import java.util.List;
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
public class CursoFacade extends AbstractFacade<Curso> implements CursoFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoFacade() {
        super(Curso.class);
    }

    @Override
    public Curso find(String codigo, String nombre, Periodo periodo) {
        Curso curso = null;
        try {
            String query = "FROM Curso c WHERE c.codigo = :codigo AND "
                    + "c.nombre = :nombre AND c.periodoInt = :periodo";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("codigo", codigo);
            q.setParameter("nombre", nombre);
            q.setParameter("periodo", periodo);
            curso = (Curso) q.getSingleResult();
        } catch (NoResultException e) {
        }
        return curso;
    }

    @Override
    public List<Curso> findAllOrdered() {
        List<Curso> cursos = null;
        try {
            String query = "FROM Curso c ORDER BY c.periodoInt, c.nivelId";
            Query q = getEntityManager().createQuery(query);
            cursos = q.getResultList();
        } catch (Exception e) {
        }
        return cursos;
    }

    @Override
    public List<Curso> findAll(Nivel nivel) {
        List<Curso> cursos = null;
        try {
            String query = "FROM Curso c WHERE c.nivelId = :nivel";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("nivel", nivel);
            cursos = q.getResultList();
        } catch (Exception e) {
            System.err.println("Error en CursoFacade metodo findAll(Nivel nivel): " 
                    + e.getMessage());
        }
        return cursos;
    }

    @Override
    public List<Curso> findAll(Periodo periodo, Nivel nivel) {
        List<Curso> cursos = null;
        try {
            String query = "FROM Curso c WHERE c.periodoInt = :periodo "
                    + "AND c.nivelId = :nivel";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("periodo", periodo);
            q.setParameter("nivel", nivel);
            cursos = q.getResultList();
        } catch (Exception e) {
        }
        return cursos;
    }

}
