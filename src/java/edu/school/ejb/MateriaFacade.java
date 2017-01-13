/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Materia;
import edu.school.utilities.Constantes;
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
public class MateriaFacade extends AbstractFacade<Materia> implements MateriaFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MateriaFacade() {
        super(Materia.class);
    }

    @Override
    public Materia find(String nombre) {
        Materia materia = null;
        try {
            String query = "FROM Materia m WHERE m.nombre = :nombre";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("nombre", nombre);
            materia = (Materia) q.getSingleResult();
        } catch (NoResultException e) {
        }
        return materia;
    }

}
