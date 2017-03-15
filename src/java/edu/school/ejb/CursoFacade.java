package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.entities.Etapa;
import edu.school.utilities.Constantes;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CursoFacade extends AbstractFacade<Curso> implements CursoFacadeLocal {
    
    private static final Logger LOGGER = Logger.getLogger(CursoFacade.class.getName());

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;
    
    public CursoFacade() {
        super(Curso.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Curso findByName(String nombre) {
        Curso curso = null;
        try {
            String query = "FROM Curso c WHERE c.nombre = :nombre";
            Query q = getEntityManager().createQuery(query, Curso.class);
            q.setParameter("nombre", nombre);
            curso = (Curso) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "No se ha encontrado un curso con nombre {0}",
                            nombre);
        }
        return curso;
    }

    @Override
    public List<Curso> findAllByEtapa(Etapa etapa) {
        List<Curso> cursos = null;
        try {
            String query = "From Curso c WHERE c.etapaId = :etapa";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("etapa", etapa);
            cursos = q.getResultList();
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "No se han encontrado cursos para la etapa {0}",
                    etapa.getNombre());
        }
        return cursos;
    }

    @Override
    public List<Curso> findAllOrderedByEtapa() {
        List<Curso> cursos = null;
        try {
            String query = "From Curso c JOIN c.etapaId e"
                    + " ORDER BY e.prefijo, c.nombre";
            Query q = getEntityManager().createQuery(query);
            cursos = q.getResultList();
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "No se han encontrado cursos");
        }
        return cursos;
    }
    
    
}
