package com.example.activitease;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.activitease.MainActivity.myDB;


public class Add_Interest_Fragment extends Fragment {

    /**
     * Variables which are input from the add interest page/form.
     * Example: My interest is pitching practice, and I would like to practice four times a week in one hour sessions.
     *
     * interestName = "pitching practice";
     * periodFreq = 4;
     * activityLength = 60.0;
     * periodSpanStr = "week". This String is matched with a double from the getPeriodSpan method.
     */
    private Button addInterestBn;
    private EditText interestName, periodFreq, numNotifications, activityLength;
    private Spinner periodSpan;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_interest_page, container, false);

        // String array populates the spinner (dropdown menu) for the add_interest_page.xml page.
        String[] periodSpanTypes =
                {"--", "Day", "Week", "Month", "Year"};
        Spinner periodSpanSpinner = (Spinner) v.findViewById(R.id.periodSpanInput);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, periodSpanTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        periodSpanSpinner.setAdapter(adapter);





        /*
         * Copies all of the new interest data. All are EditText, except for period span.
         */
        interestName = v.findViewById(R.id.interestName);
        activityLength = v.findViewById(R.id.activityLength);
        periodFreq = v.findViewById(R.id.periodFreq);
        periodSpan = v.findViewById(R.id.periodSpanInput);
        numNotifications = v.findViewById(R.id.numNotifications);



        // Finds the submit button, and an onClick method submits the data into the database.
        addInterestBn = v.findViewById(R.id.submitNewInterestButton);
        addInterestBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Sets the interestName, which is the key for the database.
                String newInterestName = interestName.getText().toString();

                /*
                 * Finds the raw values of the EditTexts and the Spinner, and saves them in
                 * int and String variables.
                 */
                String newActivityLengthTemp = activityLength.getText().toString();
                String newPeriodFreqTemp = periodFreq.getText().toString();
                String newPeriodSpan = periodSpan.getSelectedItem().toString();
                String newNumNotificationsTemp = numNotifications.getText().toString();
                int basePeriodSpan = 0;
                int newActivityLength;
                int newPeriodFreq;
                int newNumNotifications;
                int inputPracticeP=0;

                if((!newActivityLengthTemp.equals("")) && (!newPeriodFreqTemp.equals("")) &&
                (!newNumNotificationsTemp.equals(""))) {
                    newActivityLength = Integer.parseInt(newActivityLengthTemp);
                    newPeriodFreq = Integer.parseInt(newPeriodFreqTemp);
                    newNumNotifications = Integer.parseInt(newNumNotificationsTemp);
                    inputPracticeP = newActivityLength * newPeriodFreq;
                }


                /*
                 * Temporary values of basePeriodSpan, which will have to be revised for later.
                 * basePeriodSpan serves as a numeric representation of how long a day, week, month,
                 * and year are.
                 */
                switch (newPeriodSpan) {
                    case "Day":
                        basePeriodSpan = 1;
                        break;
                    case "Week":
                        basePeriodSpan = 7;
                        break;
                    case "Month":
                        basePeriodSpan = 30;
                        break;
                    case "Year":
                        basePeriodSpan = 365;
                        break;
                }
                // Variable is used to prevent exceeding practicing period
                int practicePeriod = 0;
                // Converting base periods to minutes in terms of days
                if (basePeriodSpan == 1) {
                    practicePeriod = 24*60;
                }
                else if (basePeriodSpan == 7) {
                    practicePeriod = 168*60;
                }
                else if (basePeriodSpan == 30) {
                    practicePeriod = 720*60;
                }
                else if (basePeriodSpan == 365) {
                    practicePeriod = 8760*60;
                }



                int currentInterestCt = MainActivity.myDB.myDao().getInterestCt();

                if (currentInterestCt >= 10)
                    interestName.setError("The maximum number of interests is currently 10. " +
                            "Delete an interest to add another one.");
                else if (isUsed(newInterestName))
                    interestName.setError("This interest's name is already used, " +
                            "please name it something else.");
                else if (newInterestName.equals(""))
                    interestName.setError("Please enter an interest name.");
                else if (Integer.parseInt(newPeriodFreqTemp) <= 0 || Integer.parseInt(newPeriodFreqTemp) <= 0){

                    activityLength.setError("Please enter a number greater than 0.");
                }
                else if (newInterestName.equals(""))
                    interestName.setError("Please enter an interest name");
                else if (newActivityLengthTemp.equals(""))
                    activityLength.setError("Please enter an activity length");
                else if (newPeriodFreqTemp.equals(""))
                    periodFreq.setError("Please enter a period frequency");
                else if (newPeriodSpan.equals("--"))
                    ((TextView)periodSpan.getSelectedView()).setError("Please select a period span");
                else if (newNumNotificationsTemp.equals(""))
                    numNotifications.setError("Please enter a number of notifications");
                    // Create an error message if inputted practice period exceeds limit
                else if (inputPracticeP > practicePeriod) {
                    activityLength.setError("Inputted values can not exceed the span of practice period." +
                            "\n For example, 120 mins or (2 hrs) x 13 times > 1440 mins or (24 hrs) for a day's period.");
                    periodFreq.setError("Inputted values can not exceed the span of practice period." +
                            "\n For example, 120 mins or (2 hrs) x 13 times > 1440 mins or (24 hrs) for a day's period.");
                }

                else {
                    basePeriodSpan = 0;
                    newActivityLength = Integer.parseInt(newActivityLengthTemp);
                    newPeriodFreq = Integer.parseInt(newPeriodFreqTemp);
                    newNumNotifications = Integer.parseInt(newNumNotificationsTemp);

                    switch (newPeriodSpan) {
                        case "Day":
                            basePeriodSpan = 1;
                            break;
                        case "Week":
                            basePeriodSpan = 7;
                            break;
                        case "Month":
                            basePeriodSpan = 30;
                            break;
                        case "Year":
                            basePeriodSpan = 365;
                            break;
                    }


                    // Creates a new Interest object of the given int variables.
                    Interest interest = new Interest(newInterestName, newPeriodFreq, basePeriodSpan,
                            newActivityLength, newActivityLength, newNumNotifications, 0, 0, 0);

                    // Presets the notification times, given the number of notifications.
                    interest.setNotifTimes(newNumNotifications);

                    // The database adds a new interest to the interests table.
                    MainActivity.myDB.myDao().addInterest(interest);

                    // Announces that an interest was successfully added.
                    Toast.makeText(getActivity(), "Interest added successfully", Toast.LENGTH_LONG).show();

                    // Resets the AddInterest form.
                    interestName.setText("");
                    activityLength.setText("");
                    periodFreq.setText("");
                    numNotifications.setText("");
                }
            }
        });

        return v;

    }

    public boolean isUsed(String interestName)
    {
        List<Interest> interests = myDB.myDao().getInterests();
        for (int i = 0; i < interests.size(); i++) {
            if (interests.get(i).getInterestName().equals(interestName))
                return true;
        }
        return false;

    }
}
