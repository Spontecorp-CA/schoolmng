package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.DatosPersona;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AlumnoFacade extends AbstractFacade<Alumno> implements AlumnoFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlumnoFacade() {
        super(Alumno.class);
    }

    @Override
    public Alumno findxDatosPersona(DatosPersona dp) {
        Alumno alumno = null;
        try {
            String query = "FROM Alumno a WHERE a.datosPersonaId = :dp";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("dp", dp);
            alumno = (Alumno) q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Alumno con ci " + dp.getCi() + " no encontrado");
        }
        return alumno;
    }
    
}
