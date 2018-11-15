/**
 * test the problem list
  */

package com.example.mdbook;

import junit.framework.TestCase;


public class ProblemListTest extends TestCase {
    /**
     *  test problem can be added to / removed from problem list
      */

    public void testAddRemoveProblem() {
        Problem problem = new Problem("TestTitle", "TestDesc");
        ProblemList problemList = new ProblemList();
        assertEquals(problemList.size(), 0);
        problemList.addProblem(problem);
        assertEquals(problemList.size(), 1);
        assertTrue(problemList.contains(problem));
        problemList.removeProblem(problem);
        assertEquals(problemList.size(), 0);
    }
}
