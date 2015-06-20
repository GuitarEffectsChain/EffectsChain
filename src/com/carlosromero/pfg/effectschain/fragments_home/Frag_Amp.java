package com.carlosromero.pfg.effectschain.fragments_home;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.coms.SocketClient;
import com.carlosromero.pfg.effectschain.fragments.HomeFragment;

public class Frag_Amp extends Fragment {

	private int id_preset;
	private boolean erroMSG = false;
	private Context context;
	private TextView tGain, tBass, tMid, tTreble, tVolume, tDesc;
	private SeekBar skGain, skBass, skMid, skTreble, skVolume;
	private TextView lGain, lBass, lMid, lTreble, lVolume, lChannel;
	private Spinner spinner;
	SQLiteDatabase db;
	private String msg;
	private String name_preset;
	SharedPreferences prefs;
	private boolean sentDrag, showError;
	private ArrayList<String> spin_data = new ArrayList<String>();
	private SpinnerAdapterCustom adapter;
	private int bSpinner;
	
	public Frag_Amp(Context context, int id, SocketClient cliente){
		this.context = context;
		this.id_preset = id;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_amp, container, false);
		rootView.setBackgroundColor(-11906212);
		bSpinner = 0;
		prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		//Toast.makeText(context, Boolean.toString(prefs.getBoolean("sentDrag", false)), Toast.LENGTH_SHORT).show();
		sentDrag = prefs.getBoolean("sentDrag", false);
		showError = prefs.getBoolean("noDialConect", false);
		//TextViews de los porcentages
		tGain = (TextView) rootView.findViewById(R.id.value_Vol);
		tBass = (TextView) rootView.findViewById(R.id.value_Bass);
		tMid = (TextView) rootView.findViewById(R.id.value_Mid);
		tTreble = (TextView) rootView.findViewById(R.id.value_Treble);
		tVolume = (TextView) rootView.findViewById(R.id.value_Volume);
		spinner = (Spinner) rootView.findViewById(R.id.spinner_channel_amp);
		
		lGain = (TextView) rootView.findViewById(R.id.text_Vol);
		lBass = (TextView) rootView.findViewById(R.id.text_Bass);
		lMid = (TextView) rootView.findViewById(R.id.text_Mid);
		lTreble = (TextView) rootView.findViewById(R.id.text_Treble);
		lVolume = (TextView) rootView.findViewById(R.id.text_Volume);
		lChannel = (TextView) rootView.findViewById(R.id.text_channel);
		
		lGain.setTextColor(-6477);
		lBass.setTextColor(-6477);
		lMid.setTextColor(-6477);
		lTreble.setTextColor(-6477);
		lVolume.setTextColor(-6477);
		lChannel.setTextColor(-6477);	
		
		tGain.setTextColor(-6477);
		tBass.setTextColor(-6477);
		tMid.setTextColor(-6477);
		tTreble.setTextColor(-6477);
		tVolume.setTextColor(-6477);
		
		tDesc = (TextView) rootView.findViewById(R.id.text1);
		tDesc.setBackgroundColor(-13684169);
		//Sliders o seekbars para introducir los parametros
		skGain = (SeekBar) rootView.findViewById(R.id.seekVol);
		skBass = (SeekBar) rootView.findViewById(R.id.seekBass);
		skMid = (SeekBar) rootView.findViewById(R.id.seekMid);
		skTreble = (SeekBar) rootView.findViewById(R.id.seekTreble);
		skVolume = (SeekBar) rootView.findViewById(R.id.seekValume);
		
		spin_data.add("Clean");
		spin_data.add("Overdrive");
		spin_data.add("Distortion");
		
		adapter = new SpinnerAdapterCustom(context, 1, spin_data);
		
		spinner.setAdapter(adapter);
		
		SQLDataBase base = new SQLDataBase(context, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		Cursor c = db.rawQuery("SELECT ID, NAME, AMP_GAIN, AMP_BASS, AMP_MID, AMP_TREBLE, AMP_VOLUME, AMP_CHANNEL FROM PRESETS WHERE ID = " + id_preset, null);
			
		if(c.moveToFirst()){
			name_preset = c.getString(1);
			int gain = c.getInt(2);
			int bass = c.getInt(3);
			int mid = c.getInt(4);
			int treble = c.getInt(5);
			int volume = c.getInt(6);
			int channel = c.getInt(7);
			setSeekBars(gain, bass, mid, treble, volume);
			setSpinner(channel);
			//BDEfectos a = new BDEfectos(cod, nombre, 0, null);
		}

		
		skGain.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
				if(HomeFragment.cliente != null){
					try {
						HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(1, seekBar.getProgress()));
						} catch (Exception e) {
						mostrarError();
						}
					}
				
				else{
					showDialog();
				}
				
				}				
				updateValue("AMP_GAIN", seekBar.getProgress());
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tGain.setText(Integer.toString(progress) + "%");
				
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(1, progress));
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
		
		skBass.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
					
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						if(!sentDrag){
							if(HomeFragment.cliente != null){
								try {
									HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(2, seekBar.getProgress()));
									} catch (Exception e) {
									mostrarError();
									}
								}
							
							else{
								showDialog();
							}
							
							}				
						updateValue("AMP_BASS", seekBar.getProgress());
					}
					
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {}
					
					@Override
					public void onProgressChanged(SeekBar seekBar, int progress,
							boolean fromUser) {
						tBass.setText(Integer.toString(progress) + "%");
						if(sentDrag){
							if(HomeFragment.cliente != null){
								try {							
									HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(2, progress));
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
		
		skMid.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(3, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}				
				updateValue("AMP_MID", seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tMid.setText(Integer.toString(progress) + "%");	
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(3, progress));
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
		
		skTreble.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(4, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}				
				updateValue("AMP_TREBLE", seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				tTreble.setText(Integer.toString(progress) + "%");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(4, progress));
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
				
		skVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				if(!sentDrag){
					if(HomeFragment.cliente != null){
						try {
							HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(5, seekBar.getProgress()));
							} catch (Exception e) {
							mostrarError();
							}
						}
					
					else{
						showDialog();
					}
					
					}				
				updateValue("AMP_VOLUME", seekBar.getProgress());
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				tVolume.setText(Integer.toString(progress) + "%");
				if(sentDrag){
					if(HomeFragment.cliente != null){
						try {							
							HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(5, progress));
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
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				
			if(bSpinner == 0){
				bSpinner++;
			}else{			
				updateValue("AMP_CHANNEL", position);
				if(HomeFragment.cliente != null){
					try {							
						HomeFragment.cliente.Snd_txt_Msg(createMessageAmp(6, position));
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

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
			
		return rootView;
	}

	
	public class SpinnerAdapterCustom extends ArrayAdapter<String> {

		private List<String> data;
		private Context adaptcontext;
		public SpinnerAdapterCustom(Context context, int resource,
				List<String> objects) {
			
			super(context, resource, objects);
			data = objects;
			adaptcontext = context;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
						
			 LayoutInflater inflater = (LayoutInflater) adaptcontext
		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		     View rowView = inflater.inflate(R.layout.spin_list, parent, false);
		 
		     TextView labelName = (TextView) rowView.findViewById(R.id.lblSpinList);
		     labelName.setText(data.get(position));
		     labelName.setTextColor(-6477);
		 
		     return rowView;
		}
		
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			 LayoutInflater inflater = (LayoutInflater) adaptcontext
		                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		 
		     View rowView = inflater.inflate(R.layout.spin_list, parent, false);
		 
		     TextView labelName = (TextView) rowView.findViewById(R.id.lblSpinList);
		     labelName.setText(data.get(position));
		     labelName.setTextColor(-6477);
		 
		     return rowView;
		}
	}
	
	private void showDialog(){
		if(!showError){
		if(!erroMSG){
			erroMSG = true;
		new AlertDialog.Builder(context)
	    .setTitle("Dispositivo no conectado")
	    .setMessage("Conectate con la plataforma de PC")
	    .setPositiveButton("Aceptar", new OnClickListener() {
			
			@Override 
			public void onClick(DialogInterface dialog, int which) {
				erroMSG = false;
				dialog.dismiss();
			}
		})
	    .setIcon(R.drawable.ic_action_warning)
	    .show();
		}
		}
	}
	
	private void mostrarError(){
		if(!showError){
		if(!erroMSG){
		erroMSG = true;
		new AlertDialog.Builder(context)
	    .setTitle("Error al enviar")
	    .setMessage("Conectate con la plataforma de PC")
	    .setPositiveButton("Aceptar", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				erroMSG = false;
				dialog.dismiss();
			}
		})
		.setIcon(R.drawable.ic_action_warning)
	    .show();
		}
		}
	}

	private String createMessageAmp(int parametro, int value){
		
		msg = "";
		msg = "1;" + parametro + ";" + value;
		
		return msg;
	}
	
	private void setSeekBars(int gain, int bass, int mid, int treble, int volume){
		
		skGain.setProgress(gain);
		tGain.setText(Integer.toString(gain) + "%");
		skBass.setProgress(bass);
		tBass.setText(Integer.toString(bass) + "%");
		skMid.setProgress(mid);
		tMid.setText(Integer.toString(mid) + "%");
		skTreble.setProgress(treble);
		tTreble.setText(Integer.toString(treble) + "%");
		skVolume.setProgress(volume);
		tVolume.setText(Integer.toString(volume) + "%");
		
	}
	
	private void setSpinner(int channel){
		spinner.setSelection(channel);
	}
	
	private void updateValue(String param, int value){
		
		String upd = "UPDATE 'PRESETS' SET " + param + " = " + value + " WHERE ID = " + id_preset;
		db.execSQL(upd);		
	}
	
	

}
