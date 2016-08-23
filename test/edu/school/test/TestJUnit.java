package edu.school.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestJUnit {
    String message = "Hello World";
    MessageUtil messageUtil = new MessageUtil(message);
    
    @Test
    public void testPrintMessage(){
        message = "New World";
        assertEquals(message, messageUtil.printMessage());
    }
}
