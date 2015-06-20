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

public class ImportListAdapter extends ArrayAdapter<String> {

	private Context context;
	private ArrayList<String> datos;
	
	public ImportListAdapter(Context context, int resource, ArrayList<String> objects) {
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
	public String getItem(int position) {
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
	 
	     View rowView = inflater.inflate(R.layout.list_import_layout, parent, false);
	 
	     TextView labelName = (TextView) rowView.findViewById(R.id.lbl_name_list_file);
	     ImageView image = (ImageView) rowView.findViewById(R.id.img_list_file);
	 
	     labelName.setText(datos.get(position));
	     labelName.setTextColor(-6477);
	     return rowView;
	}

}
