package com.carlosromero.pfg.effectschain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.carlosromero.pfg.effectschain.adapters.ImportListAdapter;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;

public class ImportActivity extends Activity {
	
	private ListView lista;
	private File f;
	private ArrayList<String> data_files = new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private ImportListAdapter adapter2;
	private String folder_main;
	private SQLiteDatabase db;

	SharedPreferences prefs;
	private boolean loadHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cargar_fragment_layout);
		
		lista = (ListView) findViewById(R.id.list_carga);
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		//Toast.makeText(context, Boolean.toString(prefs.getBoolean("sentDrag", false)), Toast.LENGTH_SHORT).show();
		loadHome = prefs.getBoolean("loadPresetImport", false);
		folder_main = "EffectsChainFolder";

        f = new File(Environment.getExternalStorageDirectory(), folder_main);
        SQLDataBase base = new SQLDataBase(this, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		
		if (!f.exists()) {
       	Log.w("FOLDER", "Carpeta Existente");
           f.mkdirs();
		}
        
        File[] fList = f.listFiles();
        
        for (int i = 0; i < fList.length; i++) {
        	if(!fList[i].isDirectory()){
			Log.i("Files in:", fList[i].getName());
			data_files.add(fList[i].getName().substring(0, fList[i].getName().length() - 4));
			}
		}
		adapter2 = new ImportListAdapter(this, 0, data_files);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data_files);
		lista.setAdapter(adapter2);
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String file_item = lista.getItemAtPosition(position).toString() + ".txt";
				
				f = new File(Environment.getExternalStorageDirectory(), folder_main + "/" + file_item);
		        
				//Get the text file
								//Read text from file
				StringBuilder text = new StringBuilder();

				try {
				    BufferedReader br = new BufferedReader(new FileReader(f));
				    String line;

				    while ((line = br.readLine()) != null) {
				        text.append(line);
				        text.append('\n');
				    }
				    br.close();
				}
				catch (IOException e) {
				    //You'll need to add proper error handling here
				}
				insertNewPresetFromFile(text.toString());
				
				
		
			}
		});
	}
	
	private void insertNewPresetFromFile(String data){
		String [] data_insert = data.split(";");
		
		Log.i("IMPORT", data); //Insercion de los datos en la base de datos
		String query_insert = "INSERT INTO `PRESETS` VALUES (null, '" + data_insert[0] + "', " +
				" " + data_insert[1] + ", " +
				" " + data_insert[2] + ", " +
				" " + data_insert[3] + ", " +
				" " + data_insert[4] + ", " +
				" " + data_insert[5] + ", " +
				" " + data_insert[6] + ", " +
				" " + data_insert[7] + ", " +
				" " + data_insert[8] + ", " +
				" " + data_insert[9] + ", " +
				" " + data_insert[10] + ", " +
				" " + data_insert[11] + ", " +
				" " + data_insert[12] + ", " +
				" " + data_insert[13] + ", " +
				" " + data_insert[14] + ", " +
				" " + data_insert[15] + ", " +
				" " + data_insert[16] + ", " +
				" " + data_insert[17] + ", " +
				" " + data_insert[18] + ");";
		
		db.execSQL(query_insert);
		Log.i("IMPORT", data_insert[0]);
		Toast.makeText(this, "Preset: " + data_insert[0] + " añadido a la base de datos", Toast.LENGTH_SHORT).show();
		
		Cursor c = db.rawQuery("SELECT MAX(ID) FROM PRESETS", null);
		int id = 0;
		if(c.moveToFirst()){
			id = c.getInt(0);
		}
		
		if(loadHome){ //Si es true, carga el preset en la pantalla principal
		Intent intent = new Intent();  
        intent.putExtra("name", data_insert[0]);
        intent.putExtra("id", id);
        setResult(1, intent); 
        
        this.finish();
		}
		
	}
}
