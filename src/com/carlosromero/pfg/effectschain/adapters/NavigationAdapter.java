package com.carlosromero.pfg.effectschain.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.classes.Itm_object;

public class NavigationAdapter extends BaseAdapter {

	private Activity activity;
	ArrayList<Itm_object> arrayitms;
	
	public NavigationAdapter(Activity activity, ArrayList<Itm_object> arrayitms) {
		super();
		this.activity = activity;
		this.arrayitms = arrayitms;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayitms.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arrayitms.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public static class Fila {
		
		TextView titulo_itm;
		ImageView icono;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Fila view;
		LayoutInflater inflator = activity.getLayoutInflater();
		
		if(convertView == null){
			
			view = new Fila();
			Itm_object itm = arrayitms.get(position);
			convertView = inflator.inflate(R.layout.itm, null);
			
			view.titulo_itm = (TextView) convertView.findViewById(R.id.title_item);
			view.titulo_itm.setText(itm.getTitulo());
			
			view.icono = (ImageView) convertView.findViewById(R.id.icon);
			view.icono.setImageResource(itm.getIcono());
			convertView.setTag(view);
		}
		else{
			
			view = (Fila) convertView.getTag();
		}
		
		return convertView;
	}

}
