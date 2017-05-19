package edu.school.controller.strategy;

import javax.ejb.Local;

/**
 * ver la forma de implemntación aqui:
 * http://stackoverflow.com/questions/9365049/can-the-strategy-pattern-be-implemented-using-java-annotations
 * y aqui:
 * https://www.nicolaferraro.me/2016/02/24/strategy-pattern-using-cdi/
 * 
 * @author jgcastillo
 */
@Local
public interface NotificacionService {
    
    public int notifica();
}
