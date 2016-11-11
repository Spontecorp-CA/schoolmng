package edu.school.controller.docentes;

import edu.school.controller.DashboardFacade;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class DocenteDashboardController extends DashboardFacade implements Serializable{

    @Override
    public String goUserPage() {
        return "dashboard?faces-redirect=true";
    }
    
    public String goWriteMail(){
        return "writeMail?faces-redirect=true";
    }
    
}
