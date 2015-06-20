package com.carlosromero.pfg.effectschain.fragments_home;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.fragments.HomeFragment;

public class Frag_Reverb extends Fragment {

	private int id_preset;
	private Context context;

	private ToggleButton tg;
	private SeekBar skRoom, skMix;
	private TextView tRoom, tMix, tDesc, lRoom, lMix;
	private String msg, name_preset;
	
	SQLiteDatabase db;

	SharedPreferences prefs;
	private boolean sentDrag, showError;
		
	public Frag_Reverb(Context context, int id){
		this.context = context;
		this.id_preset = id;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_reverb, container, false);
		rootView.setBackgroundColor(-11906212);
		skRoom = (SeekBar) rootView.findViewById(R.id.seekroomsize);
		skMix = (SeekBar) rootView.findViewById(R.id.seekmix);

		tRoom = (TextView) rootView.findViewById(R.id.value_roomsize);
		tMix = (TextView) rootView.findViewById(R.id.value_mix);
		tDesc = (TextView) rootView.findViewById(R.id.text1);
		
		lRoom = (TextView) rootView.findViewById(R.id.text_room);
		lMix = (TextView) rootView.findViewById(R.id.text_Mix);
		

		lRoom.setTextColor(-6477);
		lMix.setTextColor(-6477);
		
		tRoom.setTextColor(-6477);
		tMix.setTextColor(-6477);
		
		tg = (ToggleButton) rootView.findViewById(R.id.toggle_reverb);
		
		tDesc.setBackgroundColor(-13684169);
		SQLDataBase base = new SQLDataBase(context, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		//Toast.makeText(context, Boolean.toString(prefs.getBoolean("sentDrag", false)), Toast.LENGTH_SHORT).show();
		sentDrag = prefs.getBoolean("sentDrag", false);

		showError = prefs.getBoolean("noDialConect", false);
		
		Cursor c = db.rawQuery("SELECT ID, NAME, REVERB_SIZE, REVERB_MIX, REVERB_ONOFF FROM PRESETS WHERE ID = " + id_preset, null);
			
		if(c.moveToFirst()){
			name_preset = c.getString(1);
			int room = c.getInt(2);
			int mix = c.getInt(3);
			int on = c.getInt(4);
			setSeekBars(room, mix);
			setToogle(on);
			//BDEfectos a = new BDEfectos(cod, nombre, 0, null);
		}
		
		skRoom.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageReverb(1, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}
				updateValue("REVERB_SIZE", seekBar.getProgress());
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tRoom.setText(Integer.toString(progress) + "%");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageReverb(1, progress));
								}
							catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}
				}
							}
		});
		
		skMix.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageReverb(2, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}
		
				updateValue("REVERB_MIX", seekBar.getProgress());
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tMix.setText(Integer.toString(progress) + "%");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageReverb(2, progress));
								}
							catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}
				}
								
			}
		});
		
		tg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					updateValue("REVERB_ONOFF", 1);
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageReverb(3, 1));
							} catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}				}
				else{
					updateValue("REVERB_ONOFF", 0);
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageReverb(3, 0));
							} catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}					}
			}
		});
		return rootView;
	}
	
	private void setToogle(int a){
		
		if (a == 0){
			tg.setChecked(false);
		}
		else{
			tg.setChecked(true);
		}
	}
	
	private String createMessageReverb(int parametro, int value){
		
		msg = "";
		msg = "4;" + parametro + ";" + value;
		return msg;
	}
	
	private void setSeekBars(int roomsize, int mix){
		
		skRoom.setProgress(roomsize);
		tRoom.setText(Integer.toString(roomsize) + "%");
		skMix.setProgress(mix);
		tMix.setText(Integer.toString(mix) + "%");
		}
	
	private void updateValue(String param, int value){
		
		String upd = "UPDATE 'PRESETS' SET " + param + " = " + value + " WHERE ID = " + id_preset;
		db.execSQL(upd);		
	}
	private void showDialog(){
		if(!showError){
		new AlertDialog.Builder(context)
	    .setTitle("Dispositivo no conectado")
	    .setMessage("Conectate con la plataforma de PC")
	    .setPositiveButton("Aceptar", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		})
	    .setIcon(android.R.drawable.ic_popup_reminder)
	    .show();}
	}
	
	private void mostrarError(){
		if(!showError){
		new AlertDialog.Builder(context)
	    .setTitle("Error al enviar")
	    .setMessage("Conectate con la plataforma de PC")
	    .setPositiveButton("Aceptar", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		})
		.setIcon(android.R.drawable.ic_popup_reminder)
	    .show();}
	}

}
