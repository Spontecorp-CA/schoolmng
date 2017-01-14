package edu.school.ejb;

import edu.school.entities.Alumno;
import edu.school.entities.PagoAlumno;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PagoAlumnoFacadeLocal {

    void create(PagoAlumno pagoAlumno);
    
    void batchCreate(Collection<PagoAlumno> lista);

    void edit(PagoAlumno pagoAlumno);
    
    void batchEdit(Collection<PagoAlumno> lista);

    void remove(PagoAlumno pagoAlumno);

    PagoAlumno find(Object id);

    List<PagoAlumno> findAll();
    
    List<PagoAlumno> findAllxAlumno(Alumno alumno);

    List<PagoAlumno> findRange(int[] range);

    int count();
    
}
