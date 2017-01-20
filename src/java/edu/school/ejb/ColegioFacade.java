/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Colegio;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author jgcastillo
 */
@Stateless
public class ColegioFacade extends AbstractFacade<Colegio> implements ColegioFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ColegioFacade() {
        super(Colegio.class);
    }

    @Override
    public Colegio findByRif(String rif) {
        Colegio colegio = null;
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Colegio> cq = cb.createQuery(Colegio.class);
            Root<Colegio> col = cq.from(Colegio.class);
            cq.select(col)
                    .where(cb.equal(col.get("rif"), rif));
            TypedQuery<Colegio> q = getEntityManager().createQuery(cq);
            colegio = q.getSingleResult();
        } catch (NoResultException e) {
        }
        return colegio;
    }
    
}
