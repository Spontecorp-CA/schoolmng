package edu.school.test;

import junit.framework.AssertionFailedError;
import junit.framework.TestResult;
import org.junit.Test;

public class TestJUnit3 extends TestResult {

    // add the error
    public synchronized void addError(Test test, Throwable t) {
        super.addError((junit.framework.Test) test, t);
    }

    // add the failure
    public synchronized void addFailure(Test test, AssertionFailedError t) {
        super.addFailure((junit.framework.Test) test, t);
    }

    @Test
    public void testAdd() {
        // add any test
    }

    // Marks that the test run should stop.
    @Override
    public synchronized void stop() {
        //stop the test here
    }
}
