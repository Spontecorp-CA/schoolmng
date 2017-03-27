package edu.school.controller.ejb;

import edu.school.entities.Mail;
import edu.school.entities.User;
import javax.ejb.Local;
import javax.servlet.http.Part;

@Local
public interface CircularControllerLocal {

    /**
     * Verifica si el usuario que envía es supervisor
     * @param user 
     * @return true si el user es supervisor
     */
    boolean isSupervisor(final User user);
    
    void checkEnvio(final String grupoAEnviar, final User user);

    boolean isColegioSupervisor(final User user);

    Mail prepareMail(final String grupo, final String para, final String subject, 
            final String message, final Part file, final String directory);
    
}
