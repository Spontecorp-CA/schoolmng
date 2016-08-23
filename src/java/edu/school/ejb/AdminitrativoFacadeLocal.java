/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Adminitrativo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface AdminitrativoFacadeLocal {

    void create(Adminitrativo adminitrativo);

    void edit(Adminitrativo adminitrativo);

    void remove(Adminitrativo adminitrativo);

    Adminitrativo find(Object id);

    List<Adminitrativo> findAll();

    List<Adminitrativo> findRange(int[] range);

    int count();
    
}
