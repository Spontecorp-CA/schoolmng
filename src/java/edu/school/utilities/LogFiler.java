package edu.school.utilities;

import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;


public class LogFiler{

    public static final Logger logger = Logger.getLogger(LogFiler.class.getName());
    private static LogFiler instance = null;
    
    private LogFiler() {
        try {
            Handler consoleHandler = new ConsoleHandler();
            Handler fileHandler = new FileHandler("escuelalog%u%g.log", 1024 * 1024, 3, true);
            Formatter formatter = new SchoolFormatter();

            consoleHandler.setFormatter(formatter);
            consoleHandler.setLevel(Level.FINE);
            fileHandler.setFormatter(formatter);
            fileHandler.setLevel(Level.FINE);

            logger.addHandler(consoleHandler);
            logger.addHandler(fileHandler);
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, "Error al crear el log handler: ", ex.getMessage());
        }
    }

    public static LogFiler getInstance() {
        if(instance == null){
            instance = new LogFiler();
        }
        return instance;
    }
    
    private class SchoolFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            return record.getThreadID() + "::" + record.getSourceClassName() + "::"
                    + record.getSourceMethodName() + "::"
                    + new Date(record.getMillis()) + "::"
                    + record.getMessage() + "\n";
        }
        
    }
    
}
