package edu.school.entities.converters;

import edu.school.ejb.AlumnoFacadeLocal;
import edu.school.entities.Alumno;
import edu.school.entities.Curso;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("edu.school.entities.converters.AlumnoConverter")
public class AlumnoConverter implements Converter{

    @EJB
    private AlumnoFacadeLocal alumnoFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, 
                    String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        try {
            return alumnoFacade.find(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, 
                    Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Alumno) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("El objecto "
                    + value + " es de tipo "
                    + value.getClass().getName()
                    + "; se espera: " + Alumno.class.getName());
        }
    }
    
}
