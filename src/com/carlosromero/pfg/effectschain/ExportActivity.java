package com.carlosromero.pfg.effectschain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.carlosromero.pfg.effectschain.adapters.PresetListAdapter;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.classes.Preset;

public class ExportActivity extends Activity {

	private ListView lista;
	private SQLiteDatabase db;
	private ArrayList<Preset> data = new ArrayList<Preset>();
	private PresetListAdapter adapter;
	private String export_file_string;
	private File f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guardar_preset_layout);
		lista = (ListView) findViewById(R.id.list_export_presets);
		lista.setBackgroundColor(-11906212);
		
		SQLDataBase base = new SQLDataBase(this, "DB_EF", null, 1);
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
		
		adapter = new PresetListAdapter(this, 0, data);
		lista.setAdapter(adapter);

		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(isExternalStorageWritable()){
				String folder_main = "EffectsChainFolder";

		        f = new File(Environment.getExternalStorageDirectory(), folder_main);
		        
		       if (!f.exists()) {
		       	Log.w("FOLDER", "Carpeta Existente");
		           f.mkdirs();
		       }
				
				export_file_string = "";
				int id_pres = data.get(position).getId();
				String query = "SELECT NAME, AMP_GAIN, AMP_BASS, AMP_MID, AMP_TREBLE, AMP_VOLUME, AMP_CHANNEL, " +
						   "COMP_ONOFF, COMP_ATTACK, COMP_DECAY, COMP_RATIO, COMP_THRESHOLD, " + 
						   "DELAY_ONOFF, DELAY_TIME, DELAY_FEEDBACK, DELAY_MIX, REVERB_ONOFF, REVERB_SIZE, " +  
						   "REVERB_MIX FROM PRESETS WHERE ID = " + id_pres;
				
				Cursor c = db.rawQuery(query, null);
				
				if(c.moveToFirst()){
				
					for (int i = 0; i < c.getColumnCount(); i++) {
						
						if(c.getType(i) == Cursor.FIELD_TYPE_STRING){
							export_file_string += c.getString(i) + ";";
						}else
						{
							export_file_string += c.getInt(i) + ";";
						}
					}
					writeToFile(c.getString(0), export_file_string);
				}
				
				
			}
			else{
				
				Toast.makeText(getApplicationContext(), "No hay permisos para escribir", Toast.LENGTH_SHORT).show();
				
			}
		}
		});
		
	}
	
	private void writeToFile(String name, String data) {
		try {
			
			File sdCard = Environment.getExternalStorageDirectory();
			File directory = new File (sdCard.getAbsolutePath() + "/EffectsChainFolder");
			File file = new File(directory, name + ".txt");
		
			FileOutputStream fOut = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(data);
			osw.flush();
			osw.close();

			Toast.makeText(getApplicationContext(), "Archivo " + name + " generado", Toast.LENGTH_SHORT).show();
			
		} catch (Exception e) {
			Log.e("ERROR ESCRIBIENDO FICHERO", e.toString());
			Toast.makeText(getApplicationContext(), "Error al escribir", Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
}
