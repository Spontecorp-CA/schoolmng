package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.entities.Etapa;
import edu.school.entities.StatusSupervisor;
import edu.school.utilities.Constantes;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class StatusSupervisorFacade extends AbstractFacade<StatusSupervisor>
        implements StatusSupervisorFacadeLocal {

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StatusSupervisorFacade() {
        super(StatusSupervisor.class);
    }

    @Override
    public StatusSupervisor findByGrupo(Object obj) {
        StatusSupervisor ss = null;

        Integer status = Constantes.SUPERVISOR_ACTIVO;
        
        StringBuilder query = new StringBuilder();
        query.append("FROM StatusSupervisor ss WHERE");
        if (obj instanceof Curso) {
            query.append(" ss.cursoId = :param");
        } else if (obj instanceof Etapa) {
            query.append(" ss.etapaId = :param");
        } else {
            query.append(" ss.colegioId = :param");
        }

        try {
            query.append(" AND ss.status = :status");
            Query q = getEntityManager().createQuery(query.toString());
            q.setParameter("param", obj);
            q.setParameter("status", status);
            ss = (StatusSupervisor) q.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("No encontró resultado");
        }
        return ss;
    }

    @Override
    public List<StatusSupervisor> findAllByStatus(int status) {
        List<StatusSupervisor> ssList = null;
        try {
            String query = "FROM StatusSupervisor ss WHERE ss.status = :status";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("status", status);
            ssList = q.getResultList();
        } catch (NoResultException e) {
            
        }
        return ssList;
    }

    
}
