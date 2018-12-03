/*
 * RecordTest
 *
 * Version 0.0.1
 *
 * RecordTest
 *
 * 2018-11-15
 *
 * Copyright (c) 2018. All rights reserved.
 */

package com.example.mdbook;

import junit.framework.TestCase;

import java.util.Date;

/**
 * tests record objects and by extension, photo, location and problem containment
 *
 * @see com.example.mdbook.Problem
 * @see com.example.mdbook.GeoLocation
 * @see com.example.mdbook.BodyLocation
 * @see com.example.mdbook.Photo
 *
 * @author Noah Burghardt
 *
 * @author James Aina
 *
 * @version 0.0.1
 **/

public class RecordTest extends TestCase {
    /**
     *  test record ability to hold item
      */
    public void testCreateRecord(){
        String rTitle = "recordTitle";
        String rDesc = "recordDescription";
        Date rDate = new Date();
        Record record = new Record(rTitle, rDate, rDesc);

        //Add items
        //test location picker
        GeoLocation geoLocation = new GeoLocation(0d,0d,"");
        record.setLocation(geoLocation);
        assertEquals(geoLocation, record.getLocation());

        for(int i = 0; i<9;i++){
            record.addPhoto(new Photo());
        }
        Photo photo = new Photo();
        record.addPhoto(photo);
        assertTrue(record.getPhotos().contains(photo));


        BodyLocation bodyLocation = new BodyLocation("arm");
        record.setBodyLocation(bodyLocation);
        assertEquals(bodyLocation, record.getBodyLocation());


        // test for comment, title, date
        record.setComment("test comment");
        assertEquals("test comment", record.getComment());
        assertEquals(rTitle, record.getTitle());
        assertEquals(rDate, record.getDate());
        assertEquals(rDesc, record.getDescription());
    }
}
