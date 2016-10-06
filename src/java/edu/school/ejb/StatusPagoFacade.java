/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.StatusPago;
import java.util.Optional;
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
public class StatusPagoFacade extends AbstractFacade<StatusPago> implements StatusPagoFacadeLocal {

    @PersistenceContext(unitName = "schoolmngPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StatusPagoFacade() {
        super(StatusPago.class);
    }

    @Override
    public Optional<StatusPago> findXNombre(String nombre) {
        Optional<StatusPago> statusOptional = Optional.empty();
        try {
            Query q = getEntityManager()
                    .createQuery("FROM StatusPago st WHERE st.nombre = :nombre");
            q.setParameter("nombre", nombre);
            
            StatusPago statusPago = (StatusPago) q.getSingleResult();
            statusOptional = Optional.ofNullable(statusPago);
        } catch (NoResultException e) {
        }
        return statusOptional;
    }
}
