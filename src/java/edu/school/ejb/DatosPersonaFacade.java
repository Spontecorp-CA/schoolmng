/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.DatosPersona;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author jgcastillo
 */
@Stateless
public class DatosPersonaFacade extends AbstractFacade<DatosPersona> implements DatosPersonaFacadeLocal {

    @PersistenceContext(unitName = "schoolmngPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatosPersonaFacade() {
        super(DatosPersona.class);
    }

    @Override
    public DatosPersona find(int ci) {
        DatosPersona datosPersona = null;
        try {
            String query = "FROM DatosPersona dp WHERE dp.ci = :ci";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("ci", ci);
            datosPersona = (DatosPersona)q.getSingleResult();
        } catch (Exception e) {
        }
        return datosPersona;
    }
    
}
