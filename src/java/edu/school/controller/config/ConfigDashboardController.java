package edu.school.controller.config;

import edu.school.controller.DashboardFacade;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class ConfigDashboardController extends DashboardFacade implements Serializable{

    @Override
    public String goUserPage() {
        return "adminUsers";
    }
    
}
