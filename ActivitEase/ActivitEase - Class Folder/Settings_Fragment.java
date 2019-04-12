package com.example.activitease;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class Settings_Fragment extends Fragment {
	@Nullable


	@Override


	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.settings_page, container, false);
		String[] colors  =
				{"Yellow", "Red", "Green", "Blue"};
		Spinner colorSpinner = (Spinner) v.findViewById(R.id.colorSpinner);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, colors);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		colorSpinner.setAdapter(adapter);

		return v;
	}
}
