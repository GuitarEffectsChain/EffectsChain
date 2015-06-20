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
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.fragments.HomeFragment;

public class Frag_Compressor extends Fragment {

	private int id_preset;
	private Context context;

	private ToggleButton tg;
	private TextView tThresh, tAttack, tDecay, tRatio, tDesc;
	private TextView lThresh, lAttack, lDecay, lRatio;
	private SeekBar skThresh, skAttack, skDecay, skRatio;
	private String name_preset, msg;
	SQLiteDatabase db;

	SharedPreferences prefs;
	private boolean sentDrag, showError;
		
	public Frag_Compressor(Context context, int id){
		this.context = context;
		this.id_preset = id;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_compressor, container, false);
		rootView.setBackgroundColor(-11906212);
		tThresh = (TextView) rootView.findViewById(R.id.value_thresh_comp);
		tAttack = (TextView) rootView.findViewById(R.id.value_attack_comp);
		tDecay = (TextView) rootView.findViewById(R.id.value_decay);
		tRatio = (TextView) rootView.findViewById(R.id.value_Ratio);
		

		lThresh = (TextView) rootView.findViewById(R.id.text_thresh_c);
		lAttack = (TextView) rootView.findViewById(R.id.text_atttack_c);
		lDecay = (TextView) rootView.findViewById(R.id.text_decayd);
		lRatio = (TextView) rootView.findViewById(R.id.text_ratio);

		tThresh.setTextColor(-6477);
		tAttack.setTextColor(-6477);
		tDecay.setTextColor(-6477);
		tRatio.setTextColor(-6477);
		
		lThresh.setTextColor(-6477);
		lAttack.setTextColor(-6477);
		lDecay.setTextColor(-6477);
		lRatio.setTextColor(-6477);
		
		tDesc = (TextView) rootView.findViewById(R.id.text1);
		tg = (ToggleButton) rootView.findViewById(R.id.toggle_comp);
		tDesc.setBackgroundColor(-13684169);
		skThresh = (SeekBar) rootView.findViewById(R.id.seekthresh_comp);
		skAttack = (SeekBar) rootView.findViewById(R.id.seekattack_comp);
		skDecay = (SeekBar) rootView.findViewById(R.id.seekDecay);
		skRatio = (SeekBar) rootView.findViewById(R.id.seekRatio);
		
		skAttack.setMax(500);
		skDecay.setMax(500);
		skRatio.setMax(30);
				
		
		SQLDataBase base = new SQLDataBase(context, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		//Toast.makeText(context, Boolean.toString(prefs.getBoolean("sentDrag", false)), Toast.LENGTH_SHORT).show();
		sentDrag = prefs.getBoolean("sentDrag", false);

		showError = prefs.getBoolean("noDialConect", false);
		
		Cursor c = db.rawQuery("SELECT ID, NAME, COMP_THRESHOLD, COMP_ATTACK, COMP_DECAY, COMP_RATIO, COMP_ONOFF FROM PRESETS WHERE ID = " + id_preset, null);
			
		if(c.moveToFirst()){
			name_preset = c.getString(1);
			int thresh = c.getInt(2);
			int attack = c.getInt(3);
			int decay = c.getInt(4);
			int ratio = c.getInt(5);
			int on = c.getInt(6);
			setToogle(on);
			setSeekBars(thresh, attack, decay, ratio);
			//BDEfectos a = new BDEfectos(cod, nombre, 0, null);
			
		}
		

		skThresh.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(4, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}		
				updateValue("COMP_THRESHOLD", seekBar.getProgress());
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tThresh.setText(Integer.toString(progress) + "%");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(4, progress));
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
		
		skAttack.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(1, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}
				updateValue("COMP_ATTACK", seekBar.getProgress());
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tAttack.setText(Integer.toString(progress) + " ms");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(1, progress));
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
		skDecay.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(2, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}
				updateValue("COMP_DECAY", seekBar.getProgress());
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tDecay.setText(Integer.toString(progress) + " ms");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(2, progress));
								}
							catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}
				}		}
		});
		skRatio.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(3, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}
				updateValue("COMP_RATIO", seekBar.getProgress());
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tRatio.setText(Integer.toString(progress) + ":1");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(3, progress));
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
					updateValue("COMP_ONOFF", 1);
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(5, 1));
							} catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}				}
				else{
					updateValue("COMP_ONOFF", 0);
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageCompressor(5, 0));
							} catch (Exception e) {
							mostrarError();
							}
					}
					else{
						showDialog();
					}				}
			}
		});
		return rootView;
	}
	
	private String createMessageCompressor(int parametro, int value){
		
		msg = "";
		msg = "2;" + parametro + ";" + value;
	
		return msg;
	}
	
	private void setToogle(int a){
		
		if (a == 0){
			tg.setChecked(false);
		}
		else{
			tg.setChecked(true);
		}
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
	    .show();
		}
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
	    .show();
		}
	}

	
	private void setSeekBars(int thresh, int attack, int decay, int ratio){
		
		skThresh.setProgress(thresh);
		tThresh.setText(Integer.toString(thresh) + "%");
		skAttack.setProgress(attack);
		tAttack.setText(Integer.toString(attack) + " ms");
		skDecay.setProgress(decay);
		tDecay.setText(Integer.toString(decay) + " ms");
		skRatio.setProgress(ratio);
		tRatio.setText(Integer.toString(ratio) + ":1");
			}
	
	private void updateValue(String param, int value){
		
		String upd = "UPDATE 'PRESETS' SET " + param + " = " + value + " WHERE ID = " + id_preset;
		db.execSQL(upd);		
	}
	
	
}
