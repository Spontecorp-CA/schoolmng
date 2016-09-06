package edu.school.entities.converters;

import edu.school.ejb.RolFacadeLocal;
import edu.school.entities.Rol;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("edu.school.entities.converters.RolConverter")
public class RolConverter implements Converter{
    
    @EJB
    private RolFacadeLocal rolFacade;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, 
                                String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        return rolFacade.find(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, 
                                Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Rol) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("El objecto " 
                    + value + " es de tipo " 
                    + value.getClass().getName() 
                    + "; se espera: " + Rol.class.getName());
        }
    }
    
}
