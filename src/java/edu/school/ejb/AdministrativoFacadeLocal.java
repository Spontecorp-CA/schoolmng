/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Administrativo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface AdministrativoFacadeLocal {

    void create(Administrativo adminitrativo);

    void edit(Administrativo adminitrativo);

    void remove(Administrativo adminitrativo);

    Administrativo find(Object id);

    List<Administrativo> findAll();

    List<Administrativo> findRange(int[] range);

    int count();
    
}
