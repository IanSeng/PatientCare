/*
 * Record
 *
 * 1.2
 *
 * Copyright (C) 2018 CMPUT301F18T14. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.example.picmymedcode.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Record class creates a record object with a title, comment, id, geolocation,
 * photolist, body location, timestamp
 *
 * @author  Umer, Apu, Ian, Shawna, Eenna, Debra
 * @version 1.2, 02/12/18
 * @since   1.1
 */
public class Record implements Serializable {

    private String title;
    private String description;
    private Geolocation geolocation;
    private ArrayList<Photo> photoList;
    private BodyLocation bodyLocation;
    private String timeStamp;

    /**
     * Constructor initializes variables for Record
     *
     * @param title String
     */
    public Record(String title, String timeStamp) {
        this.title = title;
        this.description = "no description";
        this.geolocation = null;
        this.photoList = new ArrayList<Photo>();
        this.bodyLocation = null;
        this.timeStamp = timeStamp;

    }

    /**
     * Method gets the title of a record
     *
     * @return  String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method sets the title of a record
     *
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Method gets comment description
     *
     * @return  String
     */
    public String getDescription (){
        return description;
    }

    /**
     * Method sets description for record
     *
     * @param description String
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Method gets record geolocation
     *
     * @return geolocation
     */
    public Geolocation getGeolocation() {
        return geolocation;
    }

    /**
     * Method sets record location
     *
     * @param location Location
     */
    public void setLocation(Geolocation location) { this.geolocation = location; }

    /**
     * Method sets photo list for the record
     *
     * @param photoList photoList
     */
    public void setPhotoList(ArrayList<Photo> photoList) { this.photoList = photoList; }

    /**
     * Method gets list of photos
     *
     * @return photoList
     */
    public ArrayList<Photo> getPhotoList() {
        return photoList;
    }


    /**
     * Method adds photos to photolist for the record
     *
     * @param photo A photo object
     */
    public void addToPhotoList(Photo photo) throws IllegalArgumentException {
        if (photoList.size() <= 10) {
            this.photoList.add(photo);
        } else {
            throw new IllegalArgumentException("Maximum number of photos added for a record!");
        }
    }

    /**
     * method sets date
     *
     * @param date  Date
     */
    public void setDate(String date) {
        this.timeStamp = date;
    }

    /**
     * method gets the date
     *
     * @return  Date
     */
    public String getDate() {
        return this.timeStamp;
    }

    /**
     * Method gets body locations
     *
     * @return BodyLocation
     */
    public BodyLocation getBodyLocation() {
        return this.bodyLocation;
    }

    /**
     * Method sets the body location
     *
     * @param bodyLocation  BodyLocation
     */
    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }


}

