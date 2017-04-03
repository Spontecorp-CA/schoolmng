package edu.school.ejb;

import edu.school.entities.Seccion;
import edu.school.entities.Curso;
import edu.school.entities.Docente;
import edu.school.entities.Etapa;
import edu.school.entities.Periodo;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SeccionFacadeLocal {

    Seccion create(Seccion curso);
    
    void batchCreate(Collection<Seccion> collection);

    void edit(Seccion curso);
    
    void batchEdit(Collection<Seccion> collection);

    void remove(Seccion curso);

    Seccion find(Object id);
    
    Seccion find(String codigo, String nombre, Periodo periodo);
    
    Seccion findByCodigo(String codigo);
    
    List<Seccion> findAll();
    
    List<Seccion> findAll(Curso curso);
    
    List<Seccion> findAllByPeriodoAndCurso(Periodo periodo, Curso curso);
    
    List<Seccion> findAllOrdered();

    List<Seccion> findRange(int[] range);

    int count();

    List<Seccion> findAllOrderedByCurso(final Periodo periodo);

    Seccion findByPeriodoAndCursoAndSeccion(final Periodo periodo, 
            final Curso curso, final String seccionNombre);

    List<Seccion> findAllOrderedByEtapa(final Etapa etapa);

    List<Seccion> findAllOrderedByGrado(final Curso curso, final Periodo periodo);

}
