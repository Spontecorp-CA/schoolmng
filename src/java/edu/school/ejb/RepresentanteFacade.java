/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.DatosPersona;
import edu.school.entities.Representante;
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
public class RepresentanteFacade extends AbstractFacade<Representante> implements RepresentanteFacadeLocal {

    @PersistenceContext(unitName = "schoolmngPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RepresentanteFacade() {
        super(Representante.class);
    }

    @Override
    public Representante find(DatosPersona datosPersona) {
        Representante representante = null;
        try {
            String query = "FROM Representante rep WHERE rep.datosPersonaId = :datosPersona";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("datosPersona", datosPersona);
            representante = (Representante)q.getSingleResult();
        } catch (NoResultException e) {
        }
        return representante;
    }
    
}
