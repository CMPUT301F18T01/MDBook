package com.example.mdbook;

import android.support.test.InstrumentationRegistry;

import junit.framework.TestCase;

import java.io.File;

public class SearchControllerTest extends TestCase {

    public void testStorage(){
        File filesDir = InstrumentationRegistry.getContext().getFilesDir();
        File tempDir = InstrumentationRegistry.getContext().getCacheDir();
        File olddir = new File("/storage/emulated/0/tmp/test.jpg");
        File tmpdir = new File(tempDir, "test.jpg");
        String name = "test2.jpg";
        File newdir = new File(filesDir, name);
        String s = newdir.getAbsolutePath();

        if(tmpdir.renameTo(newdir)){
            assertTrue(true);
        }
        else {
            fail();
        }
    }

}
