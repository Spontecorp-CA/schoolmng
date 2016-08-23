package edu.school.test;

import junit.framework.TestResult;
import junit.framework.TestSuite;

public class JunitTestSuite {
    public static void main(String[] a) {
        // add the test's in the suite
        TestSuite suite = new TestSuite(TestJUnit1.class, TestJUnit2.class, TestJUnit3.class);
        TestResult result = new TestResult();
        suite.run(result);
        System.out.println("Number of test cases = " + result.runCount());
    }
}
