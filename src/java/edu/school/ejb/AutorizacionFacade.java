package edu.school.ejb;

import edu.school.entities.Autorizacion;
import edu.school.utilities.Constantes;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AutorizacionFacade extends AbstractFacade<Autorizacion> implements AutorizacionFacadeLocal{

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;
    
    public AutorizacionFacade() {
        super(Autorizacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
