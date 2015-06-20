package com.carlosromero.pfg.effectschain.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.adapters.ExpandableListAdapter;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.classes.BDEfectos;

public class EffectsFragment extends Fragment {

	Context context;
	private ExpandableListView exp_list;
	private ExpandableListAdapter adapter;
	private List<String> data = new ArrayList<String>();
	private HashMap<String, List<String>> listDataChild;
	private List<String> descripcion = new ArrayList<String>();
	SQLiteDatabase db;
	
	public EffectsFragment(Context context){
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.effects_fragment_layout, container, false);
		
		exp_list = (ExpandableListView) rootView.findViewById(R.id.exp_list);
		exp_list.setBackgroundColor(-13684169);
		SQLDataBase base = new SQLDataBase(context, "DB_EF", null, 1);
				
		db = base.getWritableDatabase();
		
		Cursor c = db.rawQuery("SELECT * FROM EFECTOS", null);
		
		if(c.moveToFirst()){
			String nombre_first = c.getString(1);
			String desc_first = c.getString(2);
			
			descripcion.add(desc_first);
			data.add(nombre_first);
			while(c.moveToNext()){
		        String nombre = c.getString(1);
		        data.add(nombre);
		        String desc = c.getString(2);
		        descripcion.add(desc);
			}
		}


        listDataChild = new HashMap<String, List<String>>();
		List<String> efecto1 = new ArrayList<String>();
		efecto1.add(descripcion.get(0));
		List<String> efecto2 = new ArrayList<String>();
		efecto2.add(descripcion.get(1));
		List<String> efecto3 = new ArrayList<String>();
		efecto3.add(descripcion.get(2));
		List<String> efecto4 = new ArrayList<String>();
		efecto4.add(descripcion.get(3));
	
		listDataChild.put(data.get(0), efecto1);
		listDataChild.put(data.get(1), efecto2);
		listDataChild.put(data.get(2), efecto3);
		listDataChild.put(data.get(3), efecto4);
		
		adapter = new ExpandableListAdapter(context, data, listDataChild);
		
		exp_list.setAdapter(adapter);
		return rootView;
	}
	
}
