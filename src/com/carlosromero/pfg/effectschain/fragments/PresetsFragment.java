package com.carlosromero.pfg.effectschain.fragments;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.carlosromero.pfg.effectschain.InsertPreset;
import com.carlosromero.pfg.effectschain.MainActivity;
import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.adapters.PresetListAdapter;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.classes.Preset;

public class PresetsFragment extends Fragment {

	private ListView lista;
	private Preset preset;
	private Button btn;
	private Context context;
	private SQLiteDatabase db;
	private ArrayList<Preset> data = new ArrayList<Preset>();
	static int RESULT_INSERT = 3;
	private String name_item = "";
	private PresetListAdapter adapter;
	
	public PresetsFragment(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.presets_fragment_layout, container, false);
		
		lista = (ListView) rootView.findViewById(R.id.list_presets);
		btn = (Button) rootView.findViewById(R.id.btn_presets_add);
		lista.setBackgroundColor(-11906212);
		SQLDataBase base = new SQLDataBase(context, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		Cursor c = db.rawQuery("SELECT NAME, ID FROM PRESETS", null);
	
		if(c.moveToFirst()){
			data.add(new Preset(c.getString(0), c.getInt(1)));
			while(c.moveToNext()){
				data.add(new Preset(c.getString(0), c.getInt(1)));
				Log.i("NAME: ", data.get(0).getName());

			}
		}
		
		for (int i = 0; i < data.size(); i++) {
			Log.i("Presets", "Name: " + data.get(i).getName() + " " + "Id: " + data.get(i).getId());
		}
		
		adapter = new PresetListAdapter(context, 0, data);
		lista.setAdapter(adapter);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				preset = (Preset) lista.getItemAtPosition(position);

				String item = preset.getName();
				String query = "SELECT ID FROM PRESETS WHERE NAME='" + item + "'";
				Cursor c = db.rawQuery(query, null);
				Fragment fragment = null;
				
				if(c.moveToFirst()){
					MainActivity.id_preset_act = c.getInt(0);
					fragment = new HomeFragment(context, c.getInt(0), item);
					FragmentManager fragmentManager = getFragmentManager();	
					fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
					
				}
			}
		});
		
		lista.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				preset = (Preset) lista.getItemAtPosition(position);

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle("Eliminar preset " + preset.getName() + "?");
				
				builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() { 
			                @Override
			                public void onClick(DialogInterface dialog, int which) {

			    				db.delete("PRESETS", "NAME = '" + preset.getName() + "' AND ID =" + preset.getId(), null);
			    				adapter.remove(preset);
			    				adapter.notifyDataSetChanged();
			    				lista.invalidate();
			                }
			            });
				
				builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() { 
	                @Override
	                public void onClick(DialogInterface dialog, int which) {
	                	dialog.dismiss();	    				
	                }
	            });
				
				builder.show();
				return true;
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent mainIntent = new Intent().setClass(getActivity(), InsertPreset.class);
				startActivityForResult(mainIntent, RESULT_INSERT);
	         			
			}
		});
		return rootView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_INSERT){
			
			try {
				
			String query_id = "SELECT MAX(ID) FROM PRESETS";
			String insert = "";
			String name = data.getStringExtra("name");
			Cursor c = db.rawQuery(query_id, null);
			int id_max = 1;
			
			if(c.moveToFirst()){
				id_max = c.getInt(0);
			}
			id_max++;
			Log.i("PRESETSFRAGMENT->", "IDMAX+1: " + id_max);
			insert = "INSERT INTO `PRESETS`(`ID`,`NAME`,`AMP_GAIN`,`AMP_BASS`,`AMP_MID`,`AMP_TREBLE`,`AMP_VOLUME`, `AMP_CHANNEL`, " + 
					 "`COMP_ONOFF`,`COMP_ATTACK`,`COMP_DECAY`,`COMP_RATIO`,`COMP_THRESHOLD`, `DELAY_ONOFF`, `DELAY_TIME`, " +
					 "`DELAY_FEEDBACK`, `DELAY_MIX`, `REVERB_ONOFF`,`REVERB_SIZE`, `REVERB_MIX`) " +
					 "VALUES (" + id_max +", '" + name + "', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
			db.execSQL(insert);
			
			Fragment fragment = null;
			MainActivity.id_preset_act = id_max;
			fragment = new HomeFragment(context, id_max, name);
			FragmentManager fragmentManager = getFragmentManager();	
			fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		
			
			} catch (Exception e) {
			
				Toast.makeText(context, "Error al insertar un nuevo preset", Toast.LENGTH_SHORT).show();
			}
		}
	
	}


}
