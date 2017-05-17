/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Autorizacion;
import edu.school.entities.Supervisor;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AutorizacionFacadeLocal {
    
    void create(Autorizacion autorizacion);
    
    void batchCreate(Collection<Autorizacion> collection);

    void edit(Autorizacion autorizacion);

    void batchEdit(Collection<Autorizacion> collection);
    
    void remove(Autorizacion autorizacion);

    Autorizacion find(Object id);

    List<Autorizacion> findAll();

    List<Autorizacion> findRange(int[] range);

    int count();

    long countBySupervisor(Supervisor supervisor);
}
