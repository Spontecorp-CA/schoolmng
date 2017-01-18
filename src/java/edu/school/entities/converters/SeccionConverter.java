package edu.school.entities.converters;

import edu.school.entities.Seccion;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import edu.school.ejb.SeccionFacadeLocal;

@FacesConverter("edu.school.entities.converters.SeccionConverter")
public class SeccionConverter implements Converter {

    @EJB
    private SeccionFacadeLocal seccionFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {
        
        if (value == null || value.length() == 0) {
            return null;
        }
        try {
            return seccionFacade.find(Integer.parseInt(value));
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
        if (value instanceof Seccion) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("El objecto "
                    + value + " es de tipo "
                    + value.getClass().getName()
                    + "; se espera: " + Seccion.class.getName());
        }
    }
}
