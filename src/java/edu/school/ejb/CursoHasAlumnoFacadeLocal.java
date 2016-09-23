/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import edu.school.entities.CursoHasAlumno;
import edu.school.entities.Periodo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface CursoHasAlumnoFacadeLocal {

    void create(CursoHasAlumno cursoHasAlumno);

    void edit(CursoHasAlumno cursoHasAlumno);

    void remove(CursoHasAlumno cursoHasAlumno);

    CursoHasAlumno find(Object id);

    CursoHasAlumno find(Alumno alumno, Periodo periodo);
    
    List<CursoHasAlumno> findAll();
    
    List<CursoHasAlumno> findAll(Curso curso);
    
    List<CursoHasAlumno> findAll(Alumno alumno);
    
    List<CursoHasAlumno> findRange(int[] range);

    int count();
    
}
