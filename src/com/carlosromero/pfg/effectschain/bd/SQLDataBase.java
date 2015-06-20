package com.carlosromero.pfg.effectschain.bd;

import java.util.ArrayList;

import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.classes.BDEfectos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLDataBase extends SQLiteOpenHelper {
	
	Context context;
	
			
			String createTableEffects = "CREATE TABLE `EFECTOS` ( " +
			"`ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
			"`NOMBRE_EFECTO`	TEXT, " +
			"`DESCRIPCION`	TEXT " +
			");";
			
			String createTableComs =  "CREATE TABLE `COMS` ( " +
					"`ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
					"`IP`	TEXT, " + 
					"`PORT`	TEXT, " +
					"`TUTO`	INTEGER" +
					");";
				
			String createTablePresets = "CREATE TABLE `PRESETS` ( " + 
					"`ID`	INTEGER NOT NULL, " +
					"`NAME`	TEXT, " +
					"`AMP_GAIN`	INTEGER, " +
					"`AMP_BASS`	INTEGER, " +
					"`AMP_MID`	INTEGER, " +
					"`AMP_TREBLE`	INTEGER, " +
					"`AMP_VOLUME`	INTEGER, " +
					"`AMP_CHANNEL`	INTEGER, " +
					"`COMP_ONOFF`	INTEGER, " +
					"`COMP_ATTACK`	INTEGER, " +
					"`COMP_DECAY`	INTEGER, " +
					"`COMP_RATIO`	INTEGER, " +
					"`COMP_THRESHOLD`	INTEGER, " +
					"`DELAY_ONOFF`	INTEGER, " +
					"`DELAY_TIME`	INTEGER, " +
					"`DELAY_FEEDBACK`	INTEGER, " +
					"`DELAY_MIX`	INTEGER, " +
					"`REVERB_ONOFF`	INTEGER, " +
					"`REVERB_SIZE`	INTEGER, " +
					"`REVERB_MIX`	INTEGER, " +
					"PRIMARY KEY(ID) " +
					");"; 
			
	public SQLDataBase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//db.execSQL("DROP TABLE IF Compra EXISTS");


		db.execSQL("DROP TABLE IF EXISTS EFECTOS");
		db.execSQL(createTableEffects);
		db.execSQL("DROP TABLE IF EXISTS COMS");
		db.execSQL(createTableComs);
		db.execSQL("DROP TABLE IF EXISTS PRESETS");
		db.execSQL(createTablePresets);
		insertEffects(db);
		insertComs(db);
		insertPresets(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		

		db.execSQL("DROP TABLE IF EXISTS EFECTOS");
		db.execSQL(createTableEffects);
		
	}
	
	public void insertEffects(SQLiteDatabase db){

		String[] titulos;
		String[] descripcion;
		ArrayList<BDEfectos> efectos = new ArrayList<BDEfectos>();
		titulos = context.getResources().getStringArray(R.array.list_effects);
		descripcion = context.getResources().getStringArray(R.array.list_desc_effects);
		
		for (int i = 0; i < descripcion.length; i++) {
			efectos.add(new BDEfectos(i+1, titulos[i], descripcion[i]));
			
		}
		
		for (int i = 0; i < efectos.size(); i++) {
			
			String insert = "INSERT INTO `EFECTOS`(`ID`,`NOMBRE_EFECTO`, `DESCRIPCION`) " +
							"VALUES (" + efectos.get(i).id + ", '" + efectos.get(i).nombre + "', '" + efectos.get(i).descripcion + "');";
			db.execSQL(insert);
			
		}
		
		
	}
	
	public void insertComs(SQLiteDatabase db){
		String insert = "INSERT INTO `COMS`('ID','IP','PORT', 'TUTO') VALUES (0, '0.0.0.0', '5555', 0)";
		db.execSQL(insert);		
	}

	public void insertPresets(SQLiteDatabase db){
		String insert = "INSERT INTO `PRESETS`(`ID`,`NAME`,`AMP_GAIN`,`AMP_BASS`,`AMP_MID`,`AMP_TREBLE`,`AMP_VOLUME`, `AMP_CHANNEL`, " + 
				 "`COMP_ONOFF`,`COMP_ATTACK`,`COMP_DECAY`,`COMP_RATIO`,`COMP_THRESHOLD`, `DELAY_ONOFF`, `DELAY_TIME`, " +
				 "`DELAY_FEEDBACK`, `DELAY_MIX`, `REVERB_ONOFF`,`REVERB_SIZE`, `REVERB_MIX`) " +
				 "VALUES (1, 'PRESET_DEFAULT', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
		db.execSQL(insert);	
	}
	
}