package com.carlosromero.pfg.effectschain.fragments;

import java.io.File;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.carlosromero.pfg.effectschain.ExportActivity;
import com.carlosromero.pfg.effectschain.ImportActivity;
import com.carlosromero.pfg.effectschain.MainActivity;
import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;

public class CargarFragment extends Fragment {

	private Context context;
	private int RESULT_IMPORT = 1;
	private Button btnExp, btnImp;
	private ListView lista;
	private File f;
	SharedPreferences prefs;
	private boolean loadHome;
	private ArrayList<String> data_files = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	
	public CargarFragment(Context context){
		this.context = context;
		}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.guardarcargar_fragment_layout, container, false);
		rootView.setBackgroundColor(-13684169);
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		//Toast.makeText(context, Boolean.toString(prefs.getBoolean("sentDrag", false)), Toast.LENGTH_SHORT).show();
		loadHome = prefs.getBoolean("loadPresetImport", false);
		btnImp = (Button) rootView.findViewById(R.id.btn_import);
		btnExp = (Button) rootView.findViewById(R.id.btn_export);
		
		btnImp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent mainIntent = new Intent().setClass(getActivity(), ImportActivity.class);
	            //startActivity(mainIntent);
				startActivityForResult(mainIntent, RESULT_IMPORT);
			}
		});
				
		btnExp.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
            Intent mainIntent = new Intent().setClass(getActivity(), ExportActivity.class);
            startActivity(mainIntent);
			}
		});
		
	
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_IMPORT && loadHome){
			String name = data.getStringExtra("name");
			int id = data.getIntExtra("id", -1);
			
			Fragment fragment = null;
			MainActivity.id_preset_act = id;
			fragment = new HomeFragment(context, id, name);
			FragmentManager fragmentManager = getFragmentManager();	
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
			
		}
	
	}
	
}

