/*
 * ProblemList
 *
 * Version 0.0.1
 *
 * 2018-11-13
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;
// holds problem objects, adds a few extra methods
// might be able to be replaced by a simple ArrayList in Patient class

import java.util.ArrayList;

/**
 * Holds an arrayList of Problems as well as some extra assistant methods.
 * This class might be able to be deprecated in favor of a simple ArrayList.
 * Only provides usefulness if we decided to order/reorder problems.
 *
 * @author Noah Burghardt
 * @see com.example.mdbook.Problem
 * @version 0.0.1
 **/
class ProblemList {
    private ArrayList<Problem> problems;

    public ProblemList(){
        this.problems = new ArrayList<>();
    }
    // checks if list of problems contains given problem

    /**
     * checks if list of problems contains given problem
     * @param problem the problem to be searched for
     * @return boolean reflection whether or not the problem is contained
     */
    public boolean contains(Problem problem) {
        return this.problems.contains(problem);
    }

    /**
     * add problem to problem list
     * @param problem the problem to add
     */
    public void addProblem(Problem problem) {
        this.problems.add(problem);
    }

    /**
     * @return the number of problems in problem list
     */
    public int size() {
        return this.problems.size();
    }

    /**
     * Remove problem from problem list.
     * @param problem The problem to be removed.
     */
    public void removeProblem(Problem problem) {
        this.problems.remove(problem);
    }
}
