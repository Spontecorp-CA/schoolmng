package edu.school.ejb;

import edu.school.entities.DatosPersona;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface DatosPersonaFacadeLocal {

    void create(DatosPersona datosPersona);

    void batchCreate(Collection<DatosPersona> collection);
    
    void edit(DatosPersona datosPersona);
    
    void batchEdit(Collection<DatosPersona> collection);

    void remove(DatosPersona datosPersona);

    DatosPersona find(Object id);
    
    DatosPersona find(int ci);

    List<DatosPersona> findAll();

    List<DatosPersona> findRange(int[] range);

    int count();
    
}
