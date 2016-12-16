/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Nivel;
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
public class NivelFacade extends AbstractFacade<Nivel> implements NivelFacadeLocal {

    @PersistenceContext(unitName = "schoolmngPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NivelFacade() {
        super(Nivel.class);
    }

    @Override
    public Nivel findByNombre(String nombre) {
        Nivel nivel = null;
        try {
            String query = "FROM Nivel n WHERE n.nombre = :nombre";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("nombre", nombre);
            nivel = (Nivel) q.getSingleResult();
        } catch (NoResultException e) {
        }
        return nivel;
    }

    @Override
    public boolean exist(Nivel nivel) {
        try {
            String query = "From Nivel n WHERE n.nombre = :nombre "
                    + "AND n.etapa = :etapa";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("nombre", nivel.getNombre());
            q.setParameter("etapa", nivel.getEtapa());
            Nivel findIt = (Nivel) q.getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }
    
}
