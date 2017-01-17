package edu.school.excepciones;

public class DocenteNotFoundException extends Exception{

    @Override
    public String getMessage() {
        return "Docente no encontrado";
    }

    @Override
    public String toString() {
        return "Docente no encontrado";
    }
    
    
}
