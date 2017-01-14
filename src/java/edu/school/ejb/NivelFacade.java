package edu.school.ejb;

import edu.school.entities.Nivel;
import edu.school.utilities.Constantes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class NivelFacade extends AbstractFacade<Nivel> implements NivelFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
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
            System.err.println("EL nivel con el nombre " + nombre + " no existe");
            Logger.getLogger(NivelFacade.class.getName())
                    .log(Level.WARNING, "EL nivel con el nombre {0} no existe", nombre);
        }
        return nivel;
    }

    @Override
    public Nivel findByPrefijo(int prefijo) {
        Nivel nivel = null;
        try {
            String query = "FROM Nivel n WHERE n.prefijo = :prefijo";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("prefijo", prefijo);
            nivel = (Nivel) q.getSingleResult();
        } catch (NoResultException e) {
            System.err.println("EL nivel con el prefijo " + prefijo + " no existe");
            Logger.getLogger(NivelFacade.class.getName())
                    .log(Level.WARNING, "EL nivel con el prefijo {0} no existe", prefijo);
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
