/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.CursoHasAlumno;
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

    List<CursoHasAlumno> findAll();

    List<CursoHasAlumno> findRange(int[] range);

    int count();
    
}
