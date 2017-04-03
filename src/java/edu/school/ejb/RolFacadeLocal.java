package edu.school.ejb;

import edu.school.entities.Rol;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RolFacadeLocal {

    Rol create(Rol rol);
    
    void batchCreate(Collection<Rol> collection);

    void edit(Rol rol);
    
    void batchEdit(Collection<Rol> collection);

    void remove(Rol rol);

    Rol find(Object id);
    
    Rol find(String name);

    List<Rol> findAll();

    List<Rol> findRange(int[] range);

    int count();
    
}
