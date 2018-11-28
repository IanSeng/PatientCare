package com.example.picmymedcode.View;

import android.support.test.rule.ActivityTestRule;

import com.example.picmymedcode.Controller.PicMyMedApplication;
import com.example.picmymedcode.Model.CareProvider;
import com.example.picmymedcode.R;
import com.example.picmymedcode.View.CareProviderProblemActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class CareProviderProblemActivityTest {

    private final static String TAG = "CareProviderProblemActivityTest: ";

    CareProvider careProvider = new CareProvider("CP");


    @Rule
    public ActivityTestRule<CareProviderProblemActivity> CareProviderProblemActivityTestRule =
            new ActivityTestRule<CareProviderProblemActivity>  (CareProviderProblemActivity.class) {
                /**
                 * Initializing a patient with a problem before running the activity
                 */
                @Override
                protected void beforeActivityLaunched() {
                    //super.beforeActivityLaunched();
                    PicMyMedApplication picMyMedApplication = new PicMyMedApplication();
                    picMyMedApplication.setLoggedInUser(careProvider);

                }
            };

    /**
     * Testing add problem button to send an intent to AddProblemActivity
     */

    @Test
    public void viewProblems() {
        //Espresso.onView(withId(R.id.search_patient_button)).perform(ViewActions.click());
        // Will be implemented in project 5
    }
    @Test
    public void testClickOnSpecificItemInAdapterView() {
        // Will be implemented in project 5
    }

}