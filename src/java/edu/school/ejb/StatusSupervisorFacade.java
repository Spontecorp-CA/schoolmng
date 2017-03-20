package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.entities.Etapa;
import edu.school.entities.StatusSupervisor;
import edu.school.entities.Supervisor;
import edu.school.utilities.Constantes;
import edu.school.utilities.LogFiler;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class StatusSupervisorFacade extends AbstractFacade<StatusSupervisor>
        implements StatusSupervisorFacadeLocal {
    
    private static final LogFiler LOGGER = LogFiler.getInstance();

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
            LOGGER.logger.log(Level.WARNING,"No encontró Curso o Etapa", e);
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

    @Override
    public StatusSupervisor findBySupervisor(Supervisor supervisor) {
        StatusSupervisor ss = null;
        Integer status = Constantes.SUPERVISOR_ACTIVO;
        try {
            String query = "FROM StatusSupervisor ss "
                    + "WHERE ss.supervisorId = :supervisor "
                    + "AND ss.status = :status";
            TypedQuery<StatusSupervisor> q = getEntityManager()
                    .createQuery(query, StatusSupervisor.class);
            q.setParameter("supervisor", supervisor);
            q.setParameter("status", status);
            ss = q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró al supervisor {0} en estatus ACTIVO, error: {1}",
                    new Object[]{supervisor.getUserId().getUsr(), e});
        }
        return ss;
    }   

    
}
