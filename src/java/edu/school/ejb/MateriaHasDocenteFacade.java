package edu.school.ejb;

import edu.school.entities.Docente;
import edu.school.entities.Materia;
import edu.school.entities.MateriaHasDocente;
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
public class MateriaHasDocenteFacade extends AbstractFacade<MateriaHasDocente> implements MateriaHasDocenteFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MateriaHasDocenteFacade() {
        super(MateriaHasDocente.class);
    }

    @Override
    public List<Materia> findAll(Docente docente) {
        List<Materia> lista = null;
        try {
            Query q = getEntityManager()
                    .createQuery("FROM MateriaHasDocente mhd WHERE mhd.docenteId = :docente");
            q.setParameter("docente", docente);
            List<MateriaHasDocente> mhdList = q.getResultList();
            lista = new ArrayList<>();
            for(MateriaHasDocente mhd : mhdList){
                lista.add(mhd.getMateriaId());
            }
        } catch (NoResultException e) {
        }
        return lista;
    }
    
}
