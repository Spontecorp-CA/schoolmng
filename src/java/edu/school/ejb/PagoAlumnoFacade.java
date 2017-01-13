/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.PagoAlumno;
import edu.school.utilities.Constantes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jgcastillo
 */
@Stateless
public class PagoAlumnoFacade extends AbstractFacade<PagoAlumno> implements PagoAlumnoFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagoAlumnoFacade() {
        super(PagoAlumno.class);
    }

    @Override
    public List<PagoAlumno> findAllxAlumno(Alumno alumno){
        List<PagoAlumno> lista = null;
        try {
            Query q = getEntityManager()
                            .createQuery("FROM PagoAlumno pa "
                                    + "WHERE pa.alumnoId = :alumno");
            q.setParameter("alumno", alumno);
            lista = q.getResultList();
        } catch (Exception e) {
        }
        return lista;
    }
}
