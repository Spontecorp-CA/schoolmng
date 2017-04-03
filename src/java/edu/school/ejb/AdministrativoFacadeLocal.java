/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.Administrativo;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface AdministrativoFacadeLocal {

    Administrativo create(Administrativo adminitrativo);
    
    void batchCreate(Collection<Administrativo> administrativoColl);

    void edit(Administrativo adminitrativo);
    
    void batchEdit(Collection<Administrativo> administrativoColl);

    void remove(Administrativo adminitrativo);

    Administrativo find(Object id);

    List<Administrativo> findAll();

    List<Administrativo> findRange(int[] range);

    int count();

    Administrativo findByCi(int ci);
    
}
