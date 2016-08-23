/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Comunicacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface ComunicacionFacadeLocal {

    void create(Comunicacion comunicacion);

    void edit(Comunicacion comunicacion);

    void remove(Comunicacion comunicacion);

    Comunicacion find(Object id);

    List<Comunicacion> findAll();

    List<Comunicacion> findRange(int[] range);

    int count();
    
}
