package edu.school.controller.administrativos;

import edu.school.controller.DashboardFacade;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class AdminDashboardController extends DashboardFacade implements Serializable{

    public String goCargaCobros(){
        return "cargacobros";
    }
}
