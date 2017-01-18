package edu.school.ejb;

import edu.school.entities.Etapa;
import edu.school.utilities.Constantes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class EtapaFacade extends AbstractFacade<Etapa> implements EtapaFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtapaFacade() {
        super(Etapa.class);
    }

    @Override
    public Etapa findByNombre(String nombre) {
        Etapa nivel = null;
        try {
            String query = "FROM Etapa n WHERE n.nombre = :nombre";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("nombre", nombre);
            nivel = (Etapa) q.getSingleResult();
        } catch (NoResultException e) {
            System.err.println("EL nivel con el nombre " + nombre + " no existe");
            Logger.getLogger(EtapaFacade.class.getName())
                    .log(Level.WARNING, "EL nivel con el nombre {0} no existe", nombre);
        }
        return nivel;
    }

    @Override
    public Etapa findByPrefijo(int prefijo) {
        Etapa nivel = null;
        try {
            String query = "FROM Etapa n WHERE n.prefijo = :prefijo";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("prefijo", prefijo);
            nivel = (Etapa) q.getSingleResult();
        } catch (NoResultException e) {
            System.err.println("EL nivel con el prefijo " + prefijo + " no existe");
            Logger.getLogger(EtapaFacade.class.getName())
                    .log(Level.WARNING, "EL nivel con el prefijo {0} no existe", prefijo);
        }
        return nivel;
    }

//    @Override
//    public boolean exist(Etapa nivel) {
//        try {
//            String query = "From Etapa n WHERE n.nombre = :nombre "
//                    + "AND n.etapa = :etapa";
//            Query q = getEntityManager().createQuery(query);
//            q.setParameter("nombre", nivel.getNombre());
//            q.setParameter("etapa", nivel.getEtapa());
//            Etapa findIt = (Etapa) q.getSingleResult();
//            return true;
//        } catch (NoResultException e) {
//            return false;
//        }
//    }
    
}
