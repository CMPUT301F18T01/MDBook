// Tests the problem objects
package com.example.mdbook;

import android.provider.ContactsContract;

import org.junit.Test;


import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    // Test for ability to add/remove records and problems to pull photos from records
    @Test
    public void testAddRemoveRecord(){
        Record record = new Record("title",new Date());
        Photo photo = new Photo();
        record.addPhoto(photo);

        // test for adding problem to record
        Problem problem = new Problem("TestTitle", "TestDesc");
        assertFalse(problem.getRecords().contains(record));
        problemList.addRecord(record);

        // test for record being added
        assertTrue(problem.getRecords().contains(record));
        // test for problem containing photos of added records
        asserTrue(problem.getPhotos().contains(photo));

        // test for removing record
        problemList.removeRecord(record);
        assertFalse(problem.getRecords().contains(record));
    }
}
