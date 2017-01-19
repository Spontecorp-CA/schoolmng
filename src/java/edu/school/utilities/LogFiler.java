package edu.school.utilities;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LogFiler {

    private static final Logger LOGGER = Logger.getLogger(LogFiler.class.getName());
    private static LogFiler instance = null;
    
    public LogFiler() {
        try {
            Handler consoleHandler = new ConsoleHandler();
            Handler fileHandler = new FileHandler("escuelalog%u%g.log", 1024 * 1024, 3, true);
            Formatter formatter = new SimpleFormatter();

            consoleHandler.setLevel(Level.ALL);
            fileHandler.setFormatter(formatter);
            fileHandler.setLevel(Level.ALL);

            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);
        } catch (IOException | SecurityException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public static LogFiler getInstance() {
        if(instance == null){
            instance = new LogFiler();
        }
        return instance;
    }
    
}
