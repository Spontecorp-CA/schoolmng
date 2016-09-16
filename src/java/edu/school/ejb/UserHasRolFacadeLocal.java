/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.ejb;

import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface UserHasRolFacadeLocal {

    void create(UserHasRol userHasRol);

    void edit(UserHasRol userHasRol);

    void remove(UserHasRol userHasRol);

    UserHasRol find(Object id);

    List<UserHasRol> findAll();
    
    List<UserHasRol> findAll(User user);

    List<UserHasRol> findRange(int[] range);

    int count();
    
}
