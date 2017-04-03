package edu.school.ejb;

import edu.school.entities.User;
import edu.school.entities.UserHasRol;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserHasRolFacadeLocal {

    UserHasRol create(UserHasRol userHasRol);
    
    void batchCreate(Collection<UserHasRol> collection);

    void edit(UserHasRol userHasRol);

    void batchEdit(Collection<UserHasRol> collection);
    
    void remove(UserHasRol userHasRol);

    UserHasRol find(Object id);

    List<UserHasRol> findAll();
    
    List<UserHasRol> findAll(User user);

    List<UserHasRol> findRange(int[] range);

    int count();
    
}
