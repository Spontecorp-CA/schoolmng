/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.DatosPersona;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface AlumnoFacadeLocal {

    Alumno create(Alumno alumno);
    
    void batchCreate(Collection<Alumno> alumnoColl);

    void edit(Alumno alumno);
    
     void batchEdit(Collection<Alumno> alumnoColl);

    void remove(Alumno alumno);

    Alumno find(Object id);
    
    Alumno findxDatosPersona(DatosPersona dp);

    List<Alumno> findAll();
    
    List<Alumno> findRange(int[] range);

    int count();
    
}
