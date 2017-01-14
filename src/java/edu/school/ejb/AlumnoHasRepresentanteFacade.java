/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.AlumnoHasRepresentante;
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
public class AlumnoHasRepresentanteFacade extends AbstractFacade<AlumnoHasRepresentante> 
        implements AlumnoHasRepresentanteFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AlumnoHasRepresentanteFacade() {
        super(AlumnoHasRepresentante.class);
    }

    @Override
    public List<AlumnoHasRepresentante> findAll(Alumno alumno) {
        List<AlumnoHasRepresentante> list = null;
        try {
            String query = "FROM AlumnoHasRepresentante ahr WHERE ahr.alumnoId = :alumno";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("alumno", alumno);
            list = q.getResultList();
        } catch (Exception e) {
        }
        return list;
    }
    
}
