package com.example.mukha.picmymedcode.Model;

import java.util.ArrayList;
import java.util.Date;

public class Problem {
    private Date startDate;
    private String title;
    private String description;
    //private RecordList recordList;

    public ArrayList<Record> recordList;

    public Problem(Date startDate, String title, String description) {

        this.startDate = startDate;
        this.title = title;
        this.description = description;
        this.recordList = new ArrayList<Record>();
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Record> getRecordList(){
        return this.recordList;
    }


}