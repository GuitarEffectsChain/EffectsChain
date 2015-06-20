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
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.fragments.HomeFragment;

public class Frag_Delay extends Fragment {

	private int id_preset;
	private Context context;
	private SeekBar skTime, skFeedback, skMix;
	private TextView tTime, tFeedback, tDesc, tMix;
	private TextView lTime, lFeedback, lMix;
	SQLiteDatabase db;
	private String name_preset, msg;
	private ToggleButton tg;	
	SharedPreferences prefs;
	private boolean sentDrag, showError;
	
	public Frag_Delay(Context context, int id){
		this.context = context;
		this.id_preset = id;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_noisegate, container, false);
		rootView.setBackgroundColor(-11906212);
		skTime = (SeekBar) rootView.findViewById(R.id.seekThreshold_noise);
		skFeedback = (SeekBar) rootView.findViewById(R.id.seekAttack_noise);
		skMix = (SeekBar) rootView.findViewById(R.id.seekMix_noise);

		tTime = (TextView) rootView.findViewById(R.id.value_Threshold);
		tFeedback = (TextView) rootView.findViewById(R.id.value_attack_noise);
		tDesc = (TextView) rootView.findViewById(R.id.text1);
		tMix = (TextView) rootView.findViewById(R.id.value_mix_noise);


		lTime = (TextView) rootView.findViewById(R.id.text_Threshold_noise);
		lFeedback = (TextView) rootView.findViewById(R.id.text_Attack_noise);
		lMix = (TextView) rootView.findViewById(R.id.text_mix_noise);
		
		lTime.setTextColor(-6477);
		lFeedback.setTextColor(-6477);
		lMix.setTextColor(-6477);
		tTime.setTextColor(-6477);
		tFeedback.setTextColor(-6477);
		tMix.setTextColor(-6477);
		tg = (ToggleButton) rootView.findViewById(R.id.toggle_ns);
		tDesc.setBackgroundColor(-13684169);
		SQLDataBase base = new SQLDataBase(context, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		skTime.setMax(500);
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		//Toast.makeText(context, Boolean.toString(prefs.getBoolean("sentDrag", false)), Toast.LENGTH_SHORT).show();
		sentDrag = prefs.getBoolean("sentDrag", false);

		showError = prefs.getBoolean("noDialConect", false);
		Cursor c = db.rawQuery("SELECT ID, NAME, DELAY_TIME, DELAY_FEEDBACK, DELAY_MIX, DELAY_ONOFF FROM PRESETS WHERE ID = " + id_preset, null);
			
		if(c.moveToFirst()){
			name_preset = c.getString(1);
			int time = c.getInt(2);
			int feedback = c.getInt(3);
			int mix = c.getInt(4);
			int on = c.getInt(5);
			setToogle(on);
			setSeekBars(time, feedback, mix);
			
			//BDEfectos a = new BDEfectos(cod, nombre, 0, null);
		}
		
		skTime.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageDelay(1, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}				
				updateValue("DELAY_TIME", seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				tTime.setText(Integer.toString(progress) + " ms");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageDelay(1, progress));
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
		
		
		skFeedback.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageDelay(2, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}	
				updateValue("DELAY_FEEDBACK", seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				tFeedback.setText(Integer.toString(progress) + "%");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageDelay(2, progress));
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
				// TODO Auto-generated method stub
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageDelay(3, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}	
				updateValue("DELAY_MIX", seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				tFeedback.setText(Integer.toString(progress) + "%");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageDelay(3, progress));
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
					updateValue("DELAY_ONOFF", 1);
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageDelay(4, 1));
							} catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}					}
				else{
					updateValue("DELAY_ONOFF", 0);
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageDelay(4, 0));
							} catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}						}
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
	
	private String createMessageDelay(int parametro, int value){
		
		msg = "";
		
		msg = "3;" + parametro + ";" + value;
		return msg;
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

	private void setSeekBars(int threshold, int attack, int mix){
		
		tTime.setText(Integer.toString(threshold) + " ms");
		skTime.setProgress(threshold);
		tFeedback.setText(Integer.toString(attack) + "%");
		skFeedback.setProgress(attack);
		tMix.setText(Integer.toString(mix) + "%");
		skMix.setProgress(mix);
			
	}
	
	private void updateValue(String param, int value){
		
		String upd = "UPDATE 'PRESETS' SET " + param + " = " + value + " WHERE ID = " + id_preset;
		db.execSQL(upd);		
	}
	
}
