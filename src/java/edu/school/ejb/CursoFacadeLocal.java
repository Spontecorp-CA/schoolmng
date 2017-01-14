package edu.school.ejb;

import edu.school.entities.Curso;
import edu.school.entities.Nivel;
import edu.school.entities.Periodo;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CursoFacadeLocal {

    void create(Curso curso);
    
    void batchCreate(Collection<Curso> collection);

    void edit(Curso curso);
    
    void batchEdit(Collection<Curso> collection);

    void remove(Curso curso);

    Curso find(Object id);
    
    Curso find(String codigo, String nombre, Periodo periodo);
    
    Curso findByCodigo(String codigo);
    
    List<Curso> findAll();
    
    List<Curso> findAll(Nivel nivel);
    
    List<Curso> findAll(Periodo periodo, Nivel nivel);
    
    List<Curso> findAllOrdered();

    List<Curso> findRange(int[] range);

    int count();
    
}
