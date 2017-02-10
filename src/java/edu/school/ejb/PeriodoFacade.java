/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Periodo;
import edu.school.utilities.Constantes;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class PeriodoFacade extends AbstractFacade<Periodo> implements PeriodoFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PeriodoFacade() {
        super(Periodo.class);
    }

    @Override
    public Periodo find(String nombre) {
        Periodo periodo = null;
        try {
            String query = "FROM Periodo p WHERE p.nombre = :nombre";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("nombre", nombre);
            periodo = (Periodo)q.getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(PeriodoFacade.class.getName()).log(Level.WARNING, e.getMessage());
        }
        return periodo;
    }

    @Override
    public Periodo findByStatus(final int status) {
        Periodo periodo = null;
        try {
            String query = "FROM Periodo p WHERE p.status = :status";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("status", status);
            periodo = (Periodo) q.getSingleResult();
        } catch (NoResultException e) {
            Logger.getLogger(PeriodoFacade.class.getName()).log(Level.WARNING, e.getMessage());
        }
        return periodo;
    }
    
}
