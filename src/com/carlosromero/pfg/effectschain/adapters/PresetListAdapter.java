package com.carlosromero.pfg.effectschain.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.classes.Preset;

public class PresetListAdapter extends ArrayAdapter<Preset> {

	private Context context;
	private ArrayList<Preset> datos;
	
	public PresetListAdapter(Context context, int resource, ArrayList<Preset> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.datos = objects;
	}

	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datos.size();
	}

	@Override
	public Preset getItem(int position) {
		// TODO Auto-generated method stub
		return datos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 LayoutInflater inflater = (LayoutInflater) context
	                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
	     View rowView = inflater.inflate(R.layout.list_preset_layout, parent, false);
	 
	     TextView labelName = (TextView) rowView.findViewById(R.id.lbl_name_list_preset);
	     TextView labelId = (TextView) rowView.findViewById(R.id.lbl_id_list_preset);
	     ImageView image = (ImageView) rowView.findViewById(R.id.img_list_preset);
	     image.setBackgroundColor(-11906212);
	 
	     labelName.setText(datos.get(position).getName());
	     labelName.setTextColor(-6477);
	     labelId.setText(Integer.toString(datos.get(position).getId()));
	 
	     return rowView;
	}

}
