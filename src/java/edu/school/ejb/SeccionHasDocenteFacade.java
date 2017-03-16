/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Seccion;
import edu.school.entities.SeccionHasDocente;
import edu.school.entities.Docente;
import edu.school.utilities.Constantes;
import edu.school.utilities.LogFiler;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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
public class SeccionHasDocenteFacade extends AbstractFacade<SeccionHasDocente> 
        implements SeccionHasDocenteFacadeLocal {
    
    private static final LogFiler LOGGER = LogFiler.getInstance();

    @PersistenceContext(unitName = Constantes.PERSISTANCE_UNIT)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeccionHasDocenteFacade() {
        super(SeccionHasDocente.class);
    }

    @Override
    public List<SeccionHasDocente> findAllBySeccion(Seccion seccion) {
        List<SeccionHasDocente> lista = null;
        try {
            String query = "FROM SeccionHasDocente chd WHERE chd.cursoId = :seccion";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("seccion", seccion);
            lista = q.getResultList();
        } catch (NoResultException e) {
        }
        return lista;
    }
    
    @Override
    public List<Seccion> findAllByDocente(Docente docente) {
        List<Seccion> lista = null;
        try {
            String query = "FROM SeccionHasDocente chd WHERE chd.docenteId = :docente";
            Query q = getEntityManager().createQuery(query);
            q.setParameter("docente", docente);
            List<SeccionHasDocente> chdList = q.getResultList();
            lista = new ArrayList<>();
            for(SeccionHasDocente chd : chdList){
                lista.add(chd.getSeccionId());
            }
        } catch (NoResultException e) {
            LOGGER.logger.log(Level.WARNING, "No encontró secciones para el docente {0}",
                    (docente.getDatosPersonaId().getNombre() + 
                            " " + docente.getDatosPersonaId().getApellido()));
        }
        return lista;
    }
}
