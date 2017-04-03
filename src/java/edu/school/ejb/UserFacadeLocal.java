package edu.school.ejb;

import edu.school.entities.User;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UserFacadeLocal {

    User create(User user);
    
    void batchCreate(Collection<User> collection);

    void edit(User user);
    
    void batchEdit(Collection<User> collection);

    void remove(User user);

    User find(Object id);
    
    User find(String name);
    
    User findByCi(int ci);

    List<User> findAll();

    List<User> findRange(int[] range);

    int count();
    
}
