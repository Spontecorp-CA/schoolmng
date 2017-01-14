package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.Periodo;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CursoHasAlumnoFacadeLocal {

    void create(CursoHasAlumno cursoHasAlumno);
    
    void batchCreate(Collection<CursoHasAlumno> collection);

    void edit(CursoHasAlumno cursoHasAlumno);
    
    void batchEdit(Collection<CursoHasAlumno> collection);

    void remove(CursoHasAlumno cursoHasAlumno);

    CursoHasAlumno find(Object id);

    CursoHasAlumno find(Alumno alumno, Periodo periodo);
    
    List<CursoHasAlumno> findAll();
    
    List<CursoHasAlumno> findAll(Curso curso);
    
    List<CursoHasAlumno> findAll(Alumno alumno);
    
    List<CursoHasAlumno> findRange(int[] range);

    int count();
    
}
