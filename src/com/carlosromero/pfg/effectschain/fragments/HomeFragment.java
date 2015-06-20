package com.carlosromero.pfg.effectschain.fragments;

import android.R.color;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosromero.pfg.effectschain.Conectivity;
import com.carlosromero.pfg.effectschain.MainActivity;
import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.coms.SocketClient;
import com.carlosromero.pfg.effectschain.fragments_home.Frag_Amp;
import com.carlosromero.pfg.effectschain.fragments_home.Frag_Compressor;
import com.carlosromero.pfg.effectschain.fragments_home.Frag_Delay;
import com.carlosromero.pfg.effectschain.fragments_home.Frag_Reverb;

public class HomeFragment extends Fragment {

	private ImageButton bt1, bt2, bt3, bt4;
	private Button btnConect;
	private LinearLayout scrollH;
	private TextView txtConect;
	static int RESULT_CONECT = 2;
	String ip, port, name_preset = "";
	Context context;
	TextView presetName;
	SQLiteDatabase db;
	public static SocketClient cliente;
	
	public HomeFragment(Context context, int id){
		this.context = context;
		this.id_preset = id;
	}
	
	public HomeFragment(Context context, int id, String name){
		this.context = context;
		this.id_preset = id;
		this.name_preset = name;
	}
	
	private int id_preset;

	private boolean isTablet() {
        return (context.getResources().getConfiguration().screenLayout
            & Configuration.SCREENLAYOUT_SIZE_MASK)
            >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.home_fragment_layout, container, false);

		//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		//Toast.makeText(context, Boolean.toString(prefs.getBoolean("sentDrag", false)), Toast.LENGTH_SHORT).show();		
		
		bt1 = (ImageButton) rootView.findViewById(R.id.btn_effect1);
		bt2 = (ImageButton) rootView.findViewById(R.id.btn_effect2);
		bt3 = (ImageButton) rootView.findViewById(R.id.btn_effect3);
		bt4 = (ImageButton) rootView.findViewById(R.id.btn_effect4);
		btnConect = (Button) rootView.findViewById(R.id.btn_connect);
		presetName = (TextView) rootView.findViewById(R.id.namePreset);
		txtConect = (TextView) rootView.findViewById(R.id.textConect);
		scrollH = (LinearLayout) rootView.findViewById(R.id.scrollHor);
		
		presetName.setBackgroundColor(-13684169);
		scrollH.setBackgroundColor(-13684169);
				
		SQLDataBase base = new SQLDataBase(context, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		if(id_preset != -1){
			
			id_preset = MainActivity.id_preset_act;
			Cursor c2 = db.rawQuery("SELECT NAME FROM PRESETS WHERE ID = " + id_preset, null);
		
		if(c2.moveToFirst()){
			name_preset = c2.getString(0);
			}	
		else{
			name_preset = "¡Debe crear un preset antes!";
		}
		
		}	
		else{
			name_preset = "¡Debe crear un preset antes!";
		}
		
		Log.i("NAME`PRESET", name_preset);
		presetName.setText(name_preset);
		mostrarFragment(1);
		
		if(cliente != null){
			if(cliente.isConnected()){
			btnConect.setText("Desconectar");
			String txt = "LOAD ALL PRESET";
			txtConect.setText("Conectado");
			txtConect.setBackgroundColor(Color.rgb(00, 255, 128));
			txt = getAllfromPreset();
			cliente.Snd_txt_Msg(txt);
			}
		}
		
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mostrarFragment(1);
			
			}
		});
		
		bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mostrarFragment(2);
			}
		});
		
		bt3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mostrarFragment(3);				
			}
		});
		
		bt4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mostrarFragment(4);				
			}
		});
		
		btnConect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String btnText = btnConect.getText().toString();
				
				if(btnText.equals("Conectar")){
				
				Intent mainIntent = new Intent().setClass(getActivity(), Conectivity.class);
				startActivityForResult(mainIntent, RESULT_CONECT);
				
				}else{
						cliente.Disconnect();
						cliente = null;
						btnConect.setText("Conectar");

	        			  txtConect.setText("Desconectado");
	        			  txtConect.setBackgroundColor(Color.rgb(200, 0, 0));
					}

			}
		});
		
		btnConect.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "LONG", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		
		return rootView;
		
		
	}

	private String getAllfromPreset() {
		String res = "";
		String query = "SELECT NAME, AMP_GAIN, AMP_BASS, AMP_MID, AMP_TREBLE, AMP_VOLUME, AMP_CHANNEL, " +
				   "COMP_ONOFF, COMP_ATTACK, COMP_DECAY, COMP_RATIO, COMP_THRESHOLD, " + 
				   "DELAY_ONOFF, DELAY_TIME, DELAY_FEEDBACK, DELAY_MIX, REVERB_ONOFF, REVERB_SIZE, " +  
				   "REVERB_MIX FROM PRESETS WHERE NAME = '" + name_preset + "'";
		
		Cursor c = db.rawQuery(query, null);
		
		if(c.moveToFirst()){
		
			for (int i = 0; i < c.getColumnCount(); i++) {
				
				if(c.getType(i) == Cursor.FIELD_TYPE_STRING){
					res += c.getString(i) + ";";
				}else{
					res += c.getInt(i) + ";";
				}
			}
		}
				
		return res;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_CONECT){
			ip = data.getStringExtra("ip");
			port = data.getStringExtra("port");
			
			Thread thread = new Thread(new Runnable(){
			    @Override
			    
			    public void run() {
			        	cliente = new SocketClient(ip, Integer.parseInt(port));
			        	
			        	try {
			        		cliente.Connect();	
			        		
			        		if(cliente != null && cliente.isConnected()){

					        	Log.i("THREAD CONNECT", "DESCONECTAR TEXT");
					        	getActivity().runOnUiThread(new Runnable() {
					        		  public void run() {
					        			  btnConect.setText("Desconectar");
					        			  
					        			  txtConect.setText("Conectado");
					        			  txtConect.setBackgroundColor(Color.rgb(00, 255, 128));
					        			  
					        			  String txt = getAllfromPreset();
					        			  cliente.Snd_txt_Msg(txt);
					        		  }
					        		});
				    			}
				       } catch (Exception e) {
			            Log.e("CLIENT CONNECTION ERROR", e.toString());
			        }
			    }
			});
			
			thread.start();
			
			
		}
	}
	
	private void mostrarFragment(int position){
		Fragment fragment = null;
		
		switch (position) {
		case 0:
			break;
		case 1:
			fragment = new Frag_Amp(context, id_preset, cliente);
			break;
		case 3:
			fragment = new Frag_Delay(context, id_preset);
			break;
		case 2:
			fragment = new Frag_Compressor(context, id_preset);
			break;
		case 4:
			fragment = new Frag_Reverb(context, id_preset);
			break;
		default:
			Toast.makeText(getActivity(), "Opcion no disponible" , Toast.LENGTH_SHORT).show();
			fragment = new Frag_Amp(context, id_preset, cliente);
			break;
		}
		
		if(fragment != null){
			
		FragmentManager fragmentManager = getFragmentManager();	
		fragmentManager.beginTransaction().replace(R.id.content_frame_home, fragment).commit();
		
		}
		else{
			Log.e("FRAGMENT ERROR", "FRAGMENT NULL");		
		}		
	}
	
}
