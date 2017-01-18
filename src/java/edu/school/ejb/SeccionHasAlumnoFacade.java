package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.Seccion;
import edu.school.entities.SeccionHasAlumno;
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
public class SeccionHasAlumnoFacade extends AbstractFacade<SeccionHasAlumno> implements SeccionHasAlumnoFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeccionHasAlumnoFacade() {
        super(SeccionHasAlumno.class);
    }

    @Override
    public List<SeccionHasAlumno> findAll(Seccion seccion) {
        List<SeccionHasAlumno> lista = null;
        try {
            String query = "FROM SeccionHasAlumno cha WHERE cha.seccionId = :seccion";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("seccion", seccion);
            lista = q.getResultList();
        } catch (NoResultException e) {
        }
        return lista;
    }

    @Override
    public SeccionHasAlumno find(Alumno alumno, Periodo periodo) {
        SeccionHasAlumno seccion = null;
        try {
            List<SeccionHasAlumno> lista = findAll(alumno);
            for(SeccionHasAlumno cha : lista){
                if(cha.getSeccionId().getPeriodoId().equals(periodo)){
                    seccion = cha;
                    break;
                }
            }
        } catch (NoResultException e) {
        }
        return seccion;
    }
    
    @Override
    public List<SeccionHasAlumno> findAll(Alumno alumno){
        List<SeccionHasAlumno> lista = null;
        try {
            String query = "FROM SeccionHasAlumno cha WHERE cha.alumnoId = :alumno";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("alumno", alumno);
            lista = q.getResultList();
        } catch (NoResultException e) {
        }
        return lista;
    }
    
    
}
