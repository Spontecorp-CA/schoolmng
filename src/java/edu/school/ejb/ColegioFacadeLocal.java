/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Colegio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface ColegioFacadeLocal {

    void create(Colegio colegio);

    void edit(Colegio colegio);

    void remove(Colegio colegio);

    Colegio find(Object id);

    List<Colegio> findAll();

    List<Colegio> findRange(int[] range);

    int count();
    
}
