package edu.school.entities.converters;

import edu.school.entities.Etapa;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import edu.school.ejb.EtapaFacadeLocal;

@FacesConverter("edu.school.entities.converters.EtapaConverter")
public class EtapaConverter implements Converter{

    @EJB
    private EtapaFacadeLocal etapaFacade;
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, 
            String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        try {
            return etapaFacade.find(Integer.parseInt(value));
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
        if (value instanceof Etapa) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("El objecto "
                    + value + " es de tipo "
                    + value.getClass().getName()
                    + "; se espera: " + Etapa.class.getName());
        }
    }
    
}
