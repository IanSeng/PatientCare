// shamelessly copied from Apu's Gallery Activity

package com.example.picmymedcode.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.android.picmymedphotohandler.GalleryAdapter;
import com.example.android.picmymedphotohandler.GalleryCells;
import com.example.android.picmymedphotohandler.LoadingImageFiles;
import com.example.android.picmymedphotohandler.PhotoIntentActivity;
import com.example.picmymedcode.R;

import java.util.ArrayList;

public class BodyLocationPhotoManagerActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private GalleryAdapter galleryAdapter;

    private ArrayList<GalleryCells> galleryCells;

    private LoadingImageFiles loadingImageFiles;

    /**
     * Method loads state
     *
     * @param savedInstanceState    Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_location_photo_manager);

        Button takePhotoButton = findViewById(R.id.take_photo_button);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Method handles user clicking add problem button
             *
             * @param v View
             */
            @Override
            public void onClick(View v) {
                Intent photoIntent = new Intent(BodyLocationPhotoManagerActivity.this,PhotoIntentActivity.class);
                startActivity(photoIntent);
            }
        });

        startActivity();
    }

    /**
     * This method performs operation on the data
     * to make it viewable under the defined adapter setting.
     *
     * @return      ArrayList of GalleryCells containing modified data for adapter compatibility
     */
    private ArrayList<GalleryCells> preparedData() {
        ArrayList<GalleryCells> imagesModified = new ArrayList<>();
        ArrayList<Bitmap> bitmaps = loadingImageFiles.convertingToBitmap();

        for(int i = 0; i < bitmaps.size(); i++){
            GalleryCells galleryCells = new GalleryCells();
            galleryCells.setTitle(""+(i + 1));
            galleryCells.setBitmap(bitmaps.get(i));
            galleryCells.setFilepath(loadingImageFiles.absolutePath(i));
            imagesModified.add(galleryCells);
        }
        return imagesModified;
    }

    /**
     * This method initiates all the required things to show the gallery.
     */
    private void startActivity() {
        // Load the image files
        loadingImageFiles = new LoadingImageFiles(getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES));

        // Initialize the recycler view
        recyclerView = (RecyclerView) findViewById(R.id.gallery);
        recyclerView.setHasFixedSize(true);

        // Initialize the layout format and span
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        // Set the layout in the recycler view
        recyclerView.setLayoutManager(layoutManager);

        // Prepare the data for adapter compatibility
        galleryCells = preparedData();
        // Initialize the adapter
        galleryAdapter = new GalleryAdapter(galleryCells, com.example.picmymedcode.View.BodyLocationPhotoManagerActivity.this);

        // Set the adapter to the recycler view
        recyclerView.setAdapter(galleryAdapter);
    }

    /**
     * This method refreshes everything upon resuming.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startActivity();
    }
}


/*
 * GalleryActivity
 *
 * 1.1
 *
 * November 16, 2018
 *
 * Copyright 2018 CMPUT301F18T14. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/**
 * GalleryActivity performs actions on the database and
 * the GalleryAdapter settings to show the Gallery
 *
 * @author  Md Touhidul (Apu) Islam
 * @version 1.1, 16/11/18
 * @since   1.1
 *
 * Ideas Combined from the following sources:
 * 1. https://www.quora.com/How-do-I-display-images-from-a-specific-directory-in-internal-storage-in-RecyclerView
 * 2. https://www.youtube.com/watch?v=jGc0LG2MNKA
 * 3. https://developer.android.com/reference/android/content/Context
 * Used in: GalleryActivity.java, GalleryAdapter.java, GalleryCells.java, LoadingImageFiles.java
 */