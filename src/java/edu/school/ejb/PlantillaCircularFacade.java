package edu.school.ejb;

import edu.school.entities.PlantillaCircular;
import edu.school.utilities.Constantes;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PlantillaCircularFacade extends AbstractFacade<PlantillaCircular> 
        implements PlantillaCircularFacadeLocal{
    
    private static final Logger LOGGER = Logger.getLogger(PlantillaCircularFacade.class.getName());
    
    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    public PlantillaCircularFacade() {
        super(PlantillaCircular.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public PlantillaCircular findByStatus(int status) {
        PlantillaCircular opPlantillaCircular = null;
        try {
            String query = "From PlantillaCircular pc WHERE pc.status = :status";
            Query q = getEntityManager().createQuery(query, PlantillaCircular.class);
            q.setParameter("status", status);
            opPlantillaCircular = (PlantillaCircular)q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.WARNING, "No se encontr\u00f3 PlantilllaCircular con el status {0}", status);
        }
        return opPlantillaCircular;
    }
    
    

}
