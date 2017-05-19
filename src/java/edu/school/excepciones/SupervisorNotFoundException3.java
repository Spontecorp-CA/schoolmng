package edu.school.excepciones;

/**
 *
 * @author jgcastillo
 */
public class SupervisorNotFoundException3 extends Exception{
    @Override
    public String getMessage() {
        return "Supervisor no encontrado";
    }

    @Override
    public String toString() {
        return "Supervisor no encontrado";
    }
}
