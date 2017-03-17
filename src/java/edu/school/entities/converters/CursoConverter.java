package edu.school.entities.converters;

import edu.school.ejb.CursoFacadeLocal;
import edu.school.entities.Curso;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("edu.school.entities.converters.CursoConverter")
public class CursoConverter implements Converter{

    @EJB
    private CursoFacadeLocal cursoFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        try {
            return cursoFacade.findByName(value);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Curso) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("El objecto "
                    + value + " es de tipo "
                    + value.getClass().getName()
                    + "; se espera: " + Curso.class.getName());
        }
    }
    
}
