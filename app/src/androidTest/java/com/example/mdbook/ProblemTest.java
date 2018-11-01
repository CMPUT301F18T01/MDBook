// Tests the problem objects
package com.example.mdbook;

import org.junit.Test;


import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ProblemTest {

    // Test for a title of too long of length
    @Test(expected = IllegalArgumentException.class)
    public void testProblemLongTitle(){
        // create a title of length > 30
        String longTitle = "TitleOfMoreThan30Characters_____";
        String testDesc = "Test Description";
        Problem problem = new Problem(longTitle, testDesc);
    }

    // Test for no title
    @Test(expected = IllegalArgumentException.class)
    public void testProblemNoTitle(){
        String testDesc = "Test Description";
        Problem problem = new Problem("", testDesc);
    }

    // Test for a description of too long of length
    @Test(expected = IllegalArgumentException.class)
    public void testProblemLongDescription(){
        String title = "Title";
        // create a description of length > 300
        String testDesc = "";
        for(int i=0;i<301;i++){
            testDesc += 't';
        }
        Problem problem = new Problem(title, testDesc);
    }

    // Test for empty description (should be valid)
    @Test
    public void testProblemNoDescription(){
        String title = "Title";
        String testDesc = "";
        Problem problem = new Problem(title, testDesc);
        assertEquals("", problem.getDescription());
    }

    // Test for problem edits
    @Test
    public void testProblemEdit(){
        Problem problem = new Problem("Title", "Description");
        assertSame("Description", problem.getDescription());
        problem.setDescription("newDesc");
        assertSame("newDesc", problem.getDescription());
        problem.addComment("comment");
        assertSame("comment", problem.getComment());
    }

    // Test for ability to add/remove records
    @Test
    public void testAddRemoveRecord(){
        Record record = new Record("title",new Date(), "photoReference");
        Problem problem = new Problem("TestTitle", "TestDesc");
        assertEquals(problem.getRecords().size(),0);
        problemList.addRecord(record);
        assertEquals(problem.getRecords().size(), 1);
        problemList.removeRecord(record);
        assertEquals(problem.getRecords().size(), 0);
    }

}
