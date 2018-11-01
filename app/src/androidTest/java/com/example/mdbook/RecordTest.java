package com.example.mdbook;
// tests record objects and by extension, photo, location and problem containment

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

public class RecordTest extends TestCase {
    // test record ability to hold items
    public void testCreateRecord(){
        String rTitle = "recordTitle";
        Date rDate = new Date();
        Record record = new Record(rTitle, rDate);

        /* Add items */
        // test location picker
        Location location = LocationPicker.here();
        record.addLocation(location);
        assertEquals(location, record.getLocation());

        // test photos, record should be able to hold at least 10
        for(int i = 0; i<9;i++){
            record.addPhoto(new Photo());
        }
        Photo photo = new Photo();
        record.addPhoto(photo);
        assertTrue(record.getPhotos().contains(photo));

        // test for body location
        BodyLocation bodyLocation = new BodyLocation(0,0);
        record.addBodyLocation(bodyLocation);
        assertEquals(bodyLocation, record.getBodyLocation());


        // test for comment, title, date
        record.addComment("test comment");
        assertEquals("test comment". record.getComment());
        assertEquals(rTitle, record.getTitle());
        assertEquals(rDate, record.getDate());
    }
}
