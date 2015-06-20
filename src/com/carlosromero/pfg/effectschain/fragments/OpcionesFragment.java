package com.carlosromero.pfg.effectschain.fragments;

import com.carlosromero.pfg.effectschain.R;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OpcionesFragment extends PreferenceFragment {

	public OpcionesFragment(){}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferencias);

	}	
	
}
