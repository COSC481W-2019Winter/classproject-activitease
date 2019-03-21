package com.example.activitease;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Home_Page_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);

        TextView mytextview = view.findViewById(R.id.activity1);
        Interest theInterest = new Interest();
        mytextview.setText("Interest 1");
        mytextview = view.findViewById(R.id.activity2);
        mytextview.setText("Interest 2");
        mytextview = view.findViewById(R.id.activity3);
        mytextview.setText("...");

        return view;
    }
}
