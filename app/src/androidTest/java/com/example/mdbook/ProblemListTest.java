/*
 * ProblemListTest
 *
 * Version 0.0.1
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */
package com.example.mdbook;

import junit.framework.TestCase;

/**
 * test the problem list
 *
 * @author Noah Burghardt
 * @author James Aina
 *
 * @see com.example.mdbook.ProblemList
 *
 * @version 0.0.1
 */
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
