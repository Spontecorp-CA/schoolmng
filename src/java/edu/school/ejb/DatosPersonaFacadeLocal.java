/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.DatosPersona;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface DatosPersonaFacadeLocal {

    void create(DatosPersona datosPersona);

    void edit(DatosPersona datosPersona);

    void remove(DatosPersona datosPersona);

    DatosPersona find(Object id);

    List<DatosPersona> findAll();

    List<DatosPersona> findRange(int[] range);

    int count();
    
}
