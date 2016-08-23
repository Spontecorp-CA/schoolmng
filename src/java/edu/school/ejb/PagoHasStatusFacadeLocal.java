/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.PagoHasStatus;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface PagoHasStatusFacadeLocal {

    void create(PagoHasStatus pagoHasStatus);

    void edit(PagoHasStatus pagoHasStatus);

    void remove(PagoHasStatus pagoHasStatus);

    PagoHasStatus find(Object id);

    List<PagoHasStatus> findAll();

    List<PagoHasStatus> findRange(int[] range);

    int count();
    
}
