/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.school.utilities;

import javax.ejb.Local;

/**
 *
 * @author jgcastillo
 */
@Local
public interface LogFilerLocal {

    LogFiler getInstance();
    
}
