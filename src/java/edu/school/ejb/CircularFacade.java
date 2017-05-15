package edu.school.ejb;

import edu.school.entities.Circular;
import edu.school.utilities.Constantes;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CircularFacade extends AbstractFacade<Circular> implements CircularFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;
    
    public CircularFacade() {
        super(Circular.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Circular findCircularByCodigoCircular(String codigo){
        
        System.out.println("llegó a buscar la circular");
        
        Circular circular = null;
        try {
            String query = "FROM Circular c WHERE c.codigoCircular = :codigo";
            TypedQuery<Circular> q = em.createQuery(query, Circular.class);
            q.setParameter("codigo", codigo);
            circular = q.getSingleResult();
        } catch (Exception e) {
            System.out.println("no encontró la circular: " + codigo + ": " + e);
        }
                
        return circular;
    }
}
