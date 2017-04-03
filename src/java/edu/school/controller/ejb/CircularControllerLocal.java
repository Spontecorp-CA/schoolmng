package edu.school.controller.ejb;

import edu.school.entities.Circular;
import edu.school.entities.Curso;
import edu.school.entities.Etapa;
import edu.school.entities.Mail;
import edu.school.entities.Seccion;
import edu.school.entities.StatusSupervisor;
import edu.school.entities.User;
import java.util.List;
import java.util.Optional;
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
    
    boolean isEtapaSupervisor(final User user, final Etapa etapa);
    
    boolean isGradoSupervisor(final User user, final Curso grado);
    
    List<String> mailListColegio();
    
    List<String> mailListEtapa(Etapa etapa);
    
    List<String> mailListGrado(Curso grado);
    
    List<String> mailListSeccion(Seccion seccion);

    Mail prepareMail(final String grupo, final String nombreGrupo, 
            final String para, final String subject, final String message, 
            final Part file, final String directory);
    
    Circular makeCircular(final String grupo, final String nombreGrupo,
            final String para, final String subject, final String message,
            final Part file, final String directory, final User user);

    boolean sendCircular(final Circular circular, final List<String> destinatarios);
    
    Optional<StatusSupervisor> lookupCargoSupervisor(User user);
    
}
