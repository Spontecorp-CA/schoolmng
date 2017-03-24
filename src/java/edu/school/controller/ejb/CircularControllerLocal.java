package edu.school.controller.ejb;

import edu.school.entities.User;
import javax.ejb.Local;

@Local
public interface CircularControllerLocal {

    void checkEnvio(final String grupoAEnviar, final User user);
    
}
