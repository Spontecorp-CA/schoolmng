/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.PagoAlumno;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface PagoAlumnoFacadeLocal {

    void create(PagoAlumno pagoAlumno);

    void edit(PagoAlumno pagoAlumno);

    void remove(PagoAlumno pagoAlumno);

    PagoAlumno find(Object id);

    List<PagoAlumno> findAll();

    List<PagoAlumno> findRange(int[] range);

    int count();
    
}
