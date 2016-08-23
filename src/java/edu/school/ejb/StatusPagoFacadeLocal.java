/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.StatusPago;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface StatusPagoFacadeLocal {

    void create(StatusPago statusPago);

    void edit(StatusPago statusPago);

    void remove(StatusPago statusPago);

    StatusPago find(Object id);

    List<StatusPago> findAll();

    List<StatusPago> findRange(int[] range);

    int count();
    
}
