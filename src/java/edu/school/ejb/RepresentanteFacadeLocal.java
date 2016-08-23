/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Representante;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface RepresentanteFacadeLocal {

    void create(Representante representante);

    void edit(Representante representante);

    void remove(Representante representante);

    Representante find(Object id);

    List<Representante> findAll();

    List<Representante> findRange(int[] range);

    int count();
    
}
