/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
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
public class CursoHasAlumnoFacade extends AbstractFacade<CursoHasAlumno> implements CursoHasAlumnoFacadeLocal {

    @PersistenceContext(unitName = "schoolmngPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoHasAlumnoFacade() {
        super(CursoHasAlumno.class);
    }

    @Override
    public List<CursoHasAlumno> findAll(Curso curso) {
        List<CursoHasAlumno> lista = null;
        try {
            String query = "FROM CursoHasAlumno cha WHERE cha.cursoId = :curso";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("curso", curso);
            lista = q.getResultList();
        } catch (NoResultException e) {
        }
        return lista;
    }
    
}
