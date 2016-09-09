package edu.school.controller.representantes;

import edu.school.controller.DashboardFacade;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class representateDashboardController extends DashboardFacade 
        implements Serializable{

    @Override
    public String goUserPage() {
        return null;
    }
    
}
