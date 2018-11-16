/*
 * ProblemTest
 *
 * Version 0.0.1
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */

import junit.framework.TestCase;

package com.example.mdbook;

import junit.framework.TestCase;

/**
 * Tests the problem objects
 *
 * @see  com.example.mdbook.ProblemList
 * @see com.example.mdbook.Record
 *
 * @author Noah Burghardt
 * @author James Aina
 *
 * @version 0.0.1
 **/



public class ProblemTest extends TestCase {

    /**
     *  Test for a title of too long of length
      */
    public void testProblemLongTitle(){

        //create a title of length > 30

        String longTitle = "TitleOfMoreThan30Characters_____";
        String testDesc = "Test Description";
        try {
            Problem problem = new Problem(longTitle, testDesc);
            assert (false);
        }
        catch (IllegalArgumentException e){
            assert(true);
        }
    }

    /**
     *  Test for no title
     *  expecting an illegal argument exception
     */

    public void testProblemNoTitle() throws IllegalArgumentException {
        String testDesc = "Test Description";
        try {
            Problem problem = new Problem("", testDesc);
            assert (false);
        } catch (IllegalArgumentException e){
            assert (true);
        }
    }

    /**
     *  Test for a description of too long of length
     *  expecting an illegal argument exception
     */
    public void testProblemLongDescription(){
        String title = "Title";

        //create a description of length > 300
        String testDesc = "";
        for(int i=0;i<301;i++){
            testDesc += 't';
        }
        try {
            Problem problem = new Problem(title, testDesc);
            assert (false);
        } catch (IllegalArgumentException e){
            assert (true);
        }
    }

    /**
     *  Test for empty description (should be valid)
      */
    public void testProblemNoDescription(){
        String title = "Title";
        String testDesc = "";
        Problem problem = new Problem(title, testDesc);
        assertEquals("", problem.getDescription());
    }

    /**
     *  Test for problem edits
      */
    public void testProblemEdit(){
        Problem problem = new Problem("Title", "Description");
        assertEquals("Description", problem.getDescription());
        problem.setDescription("newDesc");
        assertEquals("newDesc", problem.getDescription());
        problem.addComment("comment");
        assertTrue(problem.getComments().contains("comment"));
    }

    /**
     *   Test for ability to add/remove records and problems to pull photos from records
      */
    public void testAddRemoveRecord(){
        Record record = new Record("title");
        Photo photo = new Photo();
        record.addPhoto(photo);


        //test for adding problem to record
        Problem problem = new Problem("TestTitle", "TestDesc");
        assertFalse(problem.getRecords().contains(record));
        problem.addRecord(record);


        // test for record being added
        assertTrue(problem.getRecords().contains(record));

        //test for problem containing photos of added records
        assertTrue(problem.getPhotos().contains(photo));

        // test for removing record
        problem.removeRecord(record);
        assertFalse(problem.getRecords().contains(record));
    }
}
