package com.example.activitease;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


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
                {"Day", "Week", "Month", "Year"};
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
                String newInterestName  = interestName.getText().toString();

                /*
                 * Finds the raw values of the EditTexts and the Spinner, and saves them in
                 * int and String variables.
                 */
                int newActivityLength = Integer.parseInt(activityLength.getText().toString());
                int newPeriodFreq = Integer.parseInt(periodFreq.getText().toString());
                String newPeriodSpan = periodSpan.getSelectedItem().toString();
                int newNumNotifications = Integer.parseInt(numNotifications.getText().toString());

                int basePeriodSpan = 0;

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

                // Creates a new Interest object of the given int variables.
                Interest interest = new Interest(newInterestName, newPeriodFreq, basePeriodSpan,
                                                newActivityLength, newNumNotifications);

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
        });

        return v;

    }
}
