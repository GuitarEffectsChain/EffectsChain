package com.carlosromero.pfg.effectschain.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.Splash_Activity;
import com.carlosromero.pfg.effectschain.TutoActivity;

public class AboutFragment extends Fragment {
	
	TextView title, info;
	Button btn;
	public AboutFragment(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.abou_fragment_layout, container, false);
		rootView.setBackgroundColor(-13684169);
		
		title = (TextView) rootView.findViewById(R.id.lbl_titleAbout);
		info = (TextView) rootView.findViewById(R.id.lbl_infoAbout);

		btn = (Button) rootView.findViewById(R.id.btn_tuto);
				
		title.setTextColor(-6477);
		info.setTextColor(-6477);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent().setClass(
	                     getActivity(), TutoActivity.class);
	             startActivity(mainIntent);

			}
		});
	
		return rootView;
	}

}
