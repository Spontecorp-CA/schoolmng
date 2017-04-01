package edu.school.utilities;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

public final class JsfUtils {
    
    private JsfUtils(){}
    
    public static void messageSuccess(String message){
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(
                        FacesMessage.SEVERITY_INFO, 
                        "Operación exitosa", 
                        message));
    }
    
    public static void messageWarning(String message){
        FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(
                        FacesMessage.SEVERITY_WARN, 
                        "Advertencia", 
                        message));
    }
    
    public static void messageError(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        "Operación con errores",
                        message));
    }
    
    public static SelectItem[] getSelectItems(List<?> list) {
        int size = list.size() + 1;
        SelectItem[] items = new SelectItem[size];
        items[0] = new SelectItem("", "---");
        int i = 1;
        for (Object obj : list) {
            items[i] = new SelectItem(obj, obj.toString());
            i++;
        }
        return items;
    }
}
