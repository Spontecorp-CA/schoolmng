package edu.school.entities.converters;

import edu.school.ejb.NivelFacadeLocal;
import edu.school.entities.Nivel;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("edu.school.entities.converters.NivelConverter")
public class NivelConverter implements Converter{

    @EJB
    private NivelFacadeLocal nivelFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, 
            String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        try {
            return nivelFacade.find(Integer.parseInt(value));
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
        if (value instanceof Nivel) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("El objecto "
                    + value + " es de tipo "
                    + value.getClass().getName()
                    + "; se espera: " + Nivel.class.getName());
        }
    }
    
}
