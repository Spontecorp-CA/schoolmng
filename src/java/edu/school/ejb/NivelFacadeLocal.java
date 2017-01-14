package edu.school.ejb;

import edu.school.entities.Nivel;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface NivelFacadeLocal {

    void create(Nivel nivel);
    
    void batchCreate(Collection<Nivel> collection);

    void edit(Nivel nivel);
    
    void batchEdit(Collection<Nivel> collection);

    void remove(Nivel nivel);

    Nivel find(Object id);
    
    Nivel findByNombre(String nombre);
    
    Nivel findByPrefijo(int prefijo);
    
    boolean exist(Nivel nivel);

    List<Nivel> findAll();

    List<Nivel> findRange(int[] range);

    int count();
    
}
