package com.example.activiteaseroomdb;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Interest_Fragment extends Fragment {
     // EditText interestName, periodFrequency, basePeriodSpan, activityLength, numNotifications;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.interest_page, container, false);
        TextView mytextview = view.findViewById(R.id.EditInterestName);
        Interest theInterest = new Interest();
        mytextview.setText(theInterest.getInterestName());

        return view;
    }
}