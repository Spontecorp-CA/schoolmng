package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.utilities.Constantes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CursoFacade extends AbstractFacade<Curso> implements CursoFacadeLocal {

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
            Logger.getLogger(CursoFacade.class.getName())
                    .log(Level.WARNING, "No se ha encontrado un curso con nombre {0}",
                            nombre);
        }
        return curso;
    }
}
