/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.entities.CursoHasDocente;
import edu.school.entities.Docente;
import edu.school.utilities.Constantes;
import java.util.ArrayList;
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
public class CursoHasDocenteFacade extends AbstractFacade<CursoHasDocente> implements CursoHasDocenteFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoHasDocenteFacade() {
        super(CursoHasDocente.class);
    }

    @Override
    public List<CursoHasDocente> findAll(Curso curso) {
        List<CursoHasDocente> lista = null;
        try {
            String query = "FROM CursoHasDocente chd WHERE chd.cursoId = :curso";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("curso", curso);
            lista = q.getResultList();
        } catch (NoResultException e) {
        }
        return lista;
    }
    
    @Override
    public List<Curso> findAll(Docente docente) {
        List<Curso> lista = null;
        try {
            String query = "FROM CursoHasDocente chd WHERE chd.docenteId = :docente";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("docente", docente);
            List<CursoHasDocente> chdList = q.getResultList();
            lista = new ArrayList<>();
            for(CursoHasDocente chd : chdList){
                lista.add(chd.getCursoId());
            }
        } catch (NoResultException e) {
        }
        return lista;
    }
}
