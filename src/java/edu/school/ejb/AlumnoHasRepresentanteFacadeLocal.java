/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.AlumnoHasRepresentante;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface AlumnoHasRepresentanteFacadeLocal {

    void create(AlumnoHasRepresentante alumnoHasRepresentante);

    void edit(AlumnoHasRepresentante alumnoHasRepresentante);

    void remove(AlumnoHasRepresentante alumnoHasRepresentante);

    AlumnoHasRepresentante find(Object id);

    List<AlumnoHasRepresentante> findAll();
    
    List<AlumnoHasRepresentante> findAll(Alumno alumno);

    List<AlumnoHasRepresentante> findRange(int[] range);

    int count();
    
}
