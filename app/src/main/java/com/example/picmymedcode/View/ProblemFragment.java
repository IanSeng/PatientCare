/*
 * ProblemFragment
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
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.picmymedcode.Controller.PicMyMedApplication;
import com.example.picmymedcode.Controller.PicMyMedController;
import com.example.picmymedcode.Model.Patient;
import com.example.picmymedcode.Model.Problem;
import com.example.picmymedcode.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.location.places.ui.PlacePicker.getLatLngBounds;
/**
 * ProblemFragment extends Fragment
 *
 * @author  Umer, Apu, Ian, Shawna, Eenna, Debra
 * @version 1.2, 02/12/18
 * @since   1.1
 */
public class ProblemFragment extends Fragment  {
    private RecyclerView mRecyclerView;
    private SearchProblemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManage;
    private OnClickListener mListener;
    public Patient patient;
    public ArrayList<Problem> problemArrayList = new ArrayList<Problem>(),filteredDataList;
    public ArrayList<Problem> filteredDataTwo = new ArrayList<Problem>();
    //public static ArrayList<Problem> baseProblemArrayList = new ArrayList<Problem>();
    int PLACE_PICKER_REQUEST = 1;
    public SearchView searchView;
    Button searchLocation;

    View v;

    public ProblemFragment() {
        // Required empty public constructor
    }

    /**
     * sets the state
     *
     * @param inflater              LayoutInflater
     * @param container             ViewGroup
     * @param savedInstanceState    Bundle
     * @return                      v
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_problem, container, false);

        manageRecyclerview();

        searchView = v.findViewById(R.id.searchProblem);



        searchLocation = v.findViewById(R.id.problem_search_location_button);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("query", query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredDataList = filter(problemArrayList, newText);
                mAdapter.setFilter(filteredDataList);
                return true;
            }
        });

        searchLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            try {
                //problemArrayList.clear();
               // problemArrayList.addAll(baseProblemArrayList);
                startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
            //PicMyMedController.searchForProbByBodyLocation(problemArrayList,);


            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d("query", query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    filteredDataList = filter(filteredDataTwo, newText);
                    mAdapter.setFilter(filteredDataList);
                    return true;
                }
            });
            }
        });

        Button searchBody = v.findViewById(R.id.problem_search_bodylocation_button);
        searchBody.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getContext(), "this feature isn't available", Toast.LENGTH_SHORT).show();
            }
        });



        return v;

    }
    private void filter(String text) {
        ArrayList<Problem> filteredList = new ArrayList<>();

        for (int i = 0; i < problemArrayList.size(); i++) {
            if (problemArrayList.get(i).getDescription().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(problemArrayList.get(i));
            }
        }

        mAdapter.setFilter(filteredList);
    }

    /**
     * Manages the view
     *
     */
    public void manageRecyclerview(){

        if (PicMyMedApplication.getLoggedInUser().isPatient()) {
            problemArrayList = ((Patient)PicMyMedApplication.getLoggedInUser()).getProblemList();

        } else {
            problemArrayList = ((Patient)PicMyMedController.getPatient(CareProviderProblemActivity.name)).getProblemList();
        }
        //if (!baseProblemArrayList.isEmpty()) {
          //  baseProblemArrayList.addAll(problemArrayList);
        //}
        mRecyclerView = v.findViewById(R.id.fragment_problem_recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManage = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManage);
        mAdapter = new SearchProblemAdapter(getContext(), problemArrayList);
        mRecyclerView.setAdapter(mAdapter);

    }


    /**
     * Filters the problem list
     *
     * @param problemArrayList  ArrayList
     * @param newText           String
     * @return                  filteredDataList
     */
    private ArrayList<Problem> filter(ArrayList<Problem> problemArrayList, String newText) {
        newText=newText.toLowerCase();
        String text;
        String text2;
        filteredDataList=new ArrayList<>();
        for(Problem dataFromDataList:problemArrayList){
            text=dataFromDataList.getDescription().toLowerCase();
            text2=dataFromDataList.getTitle().toString();
            if(text.contains(newText) || text2.contains(newText)){
                filteredDataList.add(dataFromDataList);
            }
        }

        return filteredDataList;
    }

    /**
     * Handles the activity result
     *
     * @param requestCode   int
     * @param resultCode    int
     * @param data          Intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PLACE_PICKER_REQUEST) {

            if (resultCode == RESULT_OK) {

                Place place = PlacePicker.getPlace(getActivity(),data);
                String locationName = place.getName().toString();
                Location location = new Location("");
                double lat = place.getLatLng().latitude;
                double lon = place.getLatLng().longitude;
                location.setLatitude(lat);
                location.setLongitude(lon);

                filteredDataTwo = PicMyMedController.searchForProbByGeolocation(problemArrayList,location);
                Log.i("DEBUG SEARCH", problemArrayList.toString());
                searchLocation.setText(locationName);
                mAdapter = new SearchProblemAdapter(getContext(), filteredDataTwo);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();


/*
                problemArrayList = PicMyMedController.searchForProbByGeolocation(problemArrayList,location);
                mAdapter.notifyDataSetChanged();*/
            }
        }
    }
}
