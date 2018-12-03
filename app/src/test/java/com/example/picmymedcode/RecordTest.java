package com.example.picmymedcode;

import android.location.Location;

import com.example.picmymedcode.Model.Geolocation;
import com.example.picmymedcode.Model.Photo;
import com.example.picmymedcode.Model.Record;
import com.example.picmymedcode.Model.BodyLocation;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;

public class RecordTest extends TestCase {

    public void testGetTitle (){
        Record record = new Record ("Record", new Date());
        assertEquals("Record", record.getTitle());
    }

    public void testSetTitle (){
        Record record = new Record ("Record", new Date());
        record.setTitle("Record2");
        assertEquals("Record2", record.getTitle());
    }

    public void testGetGeolocation (){
        Record record = new Record ("Record", new Date());
        assertNull("Wrong Location", record.getGeolocation());
    }


    public void testSetGeolocation (){
        Geolocation location = new Geolocation();
        Record record = new Record ("Record", new Date());
        record.setLocation(location);
        assertFalse("Wrong Location", record.getGeolocation().equals(""));

        ///assertTrue(record.getGeolocation() instanceof );
    }

    public void testSetPhotoList(){
        ArrayList<Photo> photoList = new ArrayList<Photo>();
        Record record = new Record ("Record", new Date());
        record.setPhotoList(photoList);
        assertTrue(record.getPhotoList() instanceof ArrayList);

    }

    public void testGetPhotoList(){
        Record record = new Record ("Record", new Date());
        assertTrue(record.getPhotoList() instanceof ArrayList);
    }

    public void testGetBodyLocation(){
        Record record = new Record ("Record", new Date());

        assertNull(record.getBodyLocation()); //no body location set yet

        record.setBodyLocation(new BodyLocation("ID",1,1));
        assertTrue("Not and instance of BodyLocation.",record.getBodyLocation() instanceof BodyLocation);
    }

    public void testGetTimeStamp(){
        Date date = new Date();
        Record record = new Record ("Record", new Date());
        assertFalse("Wrong TimeStamp",record.getTimeStamp().equals(""));
    }



}
