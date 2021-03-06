/*
 * ScannerActivity
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
package com.example.QRCode;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;
import com.example.picmymedcode.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import java.io.IOException;

/**
 * ScannerActivity extends AppCompatActivity to handle scanning a QR code
 *
 * @author  Umer, Apu, Ian, Shawna, Eenna, Debra
 * @version 1.2, 02/12/18
 * @since   1.1
 */
public class ScannerActivity extends AppCompatActivity {
    SurfaceView surfaceView;                    // Draws the preview on the surface
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;                  // Setup the automatic camera
    SurfaceHolder surfaceHolder;                // Holds the previewed surface and pass it to camera

    /**
     * Creates the instance
     *
     * @param savedInstanceState    Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        surfaceView = (SurfaceView) findViewById(R.id.cameraView);
        /*  Control whether the surface view's surface is placed on top of another regular
            surface view in the window (but still behind the window itself).
            This is typically used to place overlays on top of an underlying media surface view.
            Note that this must be set before the surface view's containing window is attached to the window manager. */
        surfaceView.setZOrderMediaOverlay(true);
        /* Holding the previewed surface */
        surfaceHolder = surfaceView.getHolder();
        // Building the detector
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        // Checking if the device can read QR
        if(!barcodeDetector.isOperational()){
            Toast.makeText(getApplicationContext(), "Sorry, Couldn't setup the detector", Toast.LENGTH_LONG).show();
            this.finish();
        }
        // Creating the automatic camera to detect the barcode
        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                // Camera facing back
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                // Filter per second
                .setRequestedFps(24)
                // Enabling auto focus for the object to be detected
                .setAutoFocusEnabled(true)
                // Setting the preview size of the Camera
                .setRequestedPreviewSize(1920,1024)
                .build();
        // After creating the surface, call back the camerasource
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            /**
             * Creates the surface.
             *
             * @param holder Passing the SurfaceHolder Object
             */
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    // Checking if the camera is on or off on the device
                    if(ContextCompat.checkSelfPermission(ScannerActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        // Camera starts with the specific surface arrangement
                        cameraSource.start(surfaceView.getHolder());
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }

            /**
             * Handles surface changing
             *
             * @param holder    SurfaceHolder
             * @param format    int
             * @param width     int
             * @param height    int
             */
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            /**
             * Handles surface being destroyed
             *
             * @param holder    SurfaceHolder
             */
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
        // Creates a multiprocessor for managing and detecting barcode
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            /**
             * Handles managing and detecting the barcode
             */
            @Override
            public void release() {
            }

            /**
             * A Parcelable sparse array to store the detected barcode objects
             *
             * @param detections   Detector.Detections<Barcode>
             */
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                // A Parcelable sparse array to store the detected barcode objects
                final SparseArray<Barcode> barcodes =  detections.getDetectedItems();
                if(barcodes.size() > 0){
                    // If the array is not empty, send the 1st item to a different intent
                    Intent intent = new Intent();
                    intent.putExtra("barcode", barcodes.valueAt(0));
                    setResult(RESULT_OK, intent);
                    // Finishing the current activity
                    finish();
                }
            }
        });
    }
}