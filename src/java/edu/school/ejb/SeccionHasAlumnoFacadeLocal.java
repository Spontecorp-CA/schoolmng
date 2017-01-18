package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.Seccion;
import edu.school.entities.SeccionHasAlumno;
import edu.school.entities.Periodo;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface SeccionHasAlumnoFacadeLocal {

    void create(SeccionHasAlumno cursoHasAlumno);
    
    void batchCreate(Collection<SeccionHasAlumno> collection);

    void edit(SeccionHasAlumno cursoHasAlumno);
    
    void batchEdit(Collection<SeccionHasAlumno> collection);

    void remove(SeccionHasAlumno cursoHasAlumno);

    SeccionHasAlumno find(Object id);

    SeccionHasAlumno find(Alumno alumno, Periodo periodo);
    
    List<SeccionHasAlumno> findAll();
    
    List<SeccionHasAlumno> findAll(Seccion curso);
    
    List<SeccionHasAlumno> findAll(Alumno alumno);
    
    List<SeccionHasAlumno> findRange(int[] range);

    int count();
    
}
