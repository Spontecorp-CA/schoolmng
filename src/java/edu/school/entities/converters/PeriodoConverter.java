package edu.school.entities.converters;

import edu.school.ejb.PeriodoFacadeLocal;
import edu.school.entities.Periodo;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("edu.school.entities.converters.PeriodoConverter")
public class PeriodoConverter implements Converter{

    @EJB
    private PeriodoFacadeLocal periodoFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, 
            String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        try {
            return periodoFacade.find(Integer.parseInt(value));
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
        if (value instanceof Periodo) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("El objecto "
                    + value + " es de tipo "
                    + value.getClass().getName()
                    + "; se espera: " + Periodo.class.getName());
        }
    }
    
}
