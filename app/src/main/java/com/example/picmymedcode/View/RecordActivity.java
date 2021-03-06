/*
 * RecordActivity
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
package com.example.picmymedcode.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.picmymedphotohandler.GalleryActivity;
import com.example.android.picmymedphotohandler.SlideshowActivity;
import com.example.picmymedcode.Controller.PicMyMedApplication;
import com.example.picmymedcode.Controller.PicMyMedController;
import com.example.picmymedcode.Model.Patient;
import com.example.picmymedcode.Model.Problem;
import com.example.picmymedcode.Model.Record;
import com.example.picmymedcode.Model.User;
import com.example.picmymedcode.R;
import com.example.picmymedmaphandler.View.DrawMapActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * RecordActivity extends AppCompatActivity to create an activity for the user to
 * view and manage problems
 *
 * @author  Umer, Apu, Ian, Shawna, Eenna, Debra
 * @version 1.2, 02/12/18
 * @since   1.1
 */
public class RecordActivity extends AppCompatActivity{
    private static final String FILENAME = "file.sav";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManage;
    public ArrayList<Problem> problemArrayList;
    static int position;
    android.support.v7.widget.Toolbar toolbar;
    SwipeRefreshLayout swipeLayout;


    /**
     * Method initializes RecordActivity state
     *
     * @param savedInstanceState    Bundle
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity_test_scroll);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.recordToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Records");
        //getSupportActionBar().setDisplayShowTitleEnabled(false);

        Patient user = (Patient)PicMyMedApplication.getLoggedInUser();
        problemArrayList = user.getProblemList();
        //loadFromFile();

        position = getIntent().getIntExtra("key",0);
        String name = problemArrayList.get(position).getTitle();

        swipeLayout = findViewById(R.id.record_swipeRefresh);
        // Adding Listener
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            /**
             * Handles user swiping on the screen to refresh the layout
             */
            @Override
            public void onRefresh() {

                if (PicMyMedApplication.isNetworkAvailable(RecordActivity.this)) {
                    // To keep animation for 4 seconds
                    new Handler().postDelayed(new Runnable() {
                        /**
                         * Handles refreshing the app
                         *
                         */
                        @Override public void run() {
                            PicMyMedApplication.getMostRecentChanges();
                            manageRecyclerview();
                            // Stop animation (This will be after 3 seconds)
                            swipeLayout.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "Refreshed!", Toast.LENGTH_LONG).show();
                        }
                    }, 5000); // Delay in millis

                }else {
                    new Handler().postDelayed(new Runnable() {
                        @Override public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeLayout.setRefreshing(false);
                            Toast.makeText(getApplicationContext(), "No internet Connection!", Toast.LENGTH_LONG).show();
                        }
                    }, 500); // Delay in millis
                }

            }
        });


    }

    /**
     * Creates the toolbar options
     *
     * @param menu  Menu
     * @return      OptionsMenu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.record_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handles user selected something in the menu
     *
     * @param item  MenuItem
     * @return      ItemSelected
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_recordIcon:
                Intent intent = new Intent(RecordActivity.this,AddRecordActivity.class);
                intent.putExtra("key",position);
                startActivity(intent);
                break;
            /*case R.id.galleryIcon:
                Intent galleryIntent = new Intent(RecordActivity.this,GalleryActivity.class);
                galleryIntent.putExtra("problemIndex", position);
                galleryIntent.putExtra("intentSender", 1);
                startActivity(galleryIntent);
                break; */
            case R.id.slideshowIcon:
                Intent slideshowIntent = new Intent(RecordActivity.this,SlideshowActivity.class);
                slideshowIntent.putExtra("problemIndex", position);
                startActivity(slideshowIntent);
                break;
            case R.id.view_commentIcon:
                Intent commentIntent = new Intent(RecordActivity.this,CommentActivity.class);
                commentIntent.putExtra("key",position);
                startActivity(commentIntent);
                break;

                //check here why profile
            case R.id.profile:
                Intent profileIntent = new Intent(RecordActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                break;
            case R.id.mapRecordIcon:
                Intent mapIntent = new Intent(RecordActivity.this, DrawMapActivity.class);
                mapIntent.putExtra("problemIndex",position);
                mapIntent.putExtra("callingActivity", "MultiRecordActivity");
                startActivity(mapIntent);
                break;
            case R.id.logout:
                PicMyMedApplication.logoutDialog(RecordActivity.this);
                break;
            case R.id.pushData:
                User user = (Patient)PicMyMedApplication.getLoggedInUser();
                if (PicMyMedApplication.isNetworkAvailable(RecordActivity.this)) {
                    PicMyMedController.updateUser(user, RecordActivity.this);
                    Toast.makeText(getApplicationContext(), "Data is synced!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "You are offline" , Toast.LENGTH_SHORT).show();
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method manages recycler view to view records
     */
    public void manageRecyclerview(){
        Patient user = (Patient)PicMyMedApplication.getLoggedInUser();
        problemArrayList = user.getProblemList();
        mRecyclerView = findViewById(R.id.record_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManage = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManage);
        mAdapter = new RecordAdapter(RecordActivity.this,problemArrayList.get(position).getRecordList(), position);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Method starts the RecordActivity by getting the user and their problems
     */
    protected void onStart() {
        // TODO Auto-generated method stub

        super.onStart();
        manageRecyclerview();
        //load

    }

}
