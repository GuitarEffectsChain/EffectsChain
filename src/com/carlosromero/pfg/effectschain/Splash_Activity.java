package com.carlosromero.pfg.effectschain;

import java.util.Timer;
import java.util.TimerTask;

import com.carlosromero.pfg.effectschain.bd.SQLDataBase;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
	 

public class Splash_Activity extends Activity{
	    // Set the duration of the splash screen
	   private static final long SPLASH_SCREEN_DELAY = 1000;
	   SQLiteDatabase db;
	   private int tuto;
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
     // Hide title bar
     requestWindowFeature(Window.FEATURE_NO_TITLE);

     setContentView(R.layout.splash_screen_layout);

     SQLDataBase base = new SQLDataBase(this, "DB_EF", null, 1);
	 db = base.getWritableDatabase();
		
	 Cursor c = db.rawQuery("SELECT TUTO FROM COMS", null);
     
	 if(c.moveToFirst()){
		tuto = c.getInt(0);
	  }
	 
     TimerTask task = new TimerTask() {
         @Override
         public void run() {
        	 Intent mainIntent = null; 
        	 if(tuto == 0){
             mainIntent = new Intent().setClass(
                     Splash_Activity.this, TutoActivity.class);
             
             updateTutoDB();
             }
        	 else{
        	 mainIntent = new Intent().setClass(
                         Splash_Activity.this, MainActivity.class);
                 	 
        	 }
             startActivity(mainIntent);

            finish();
         }

	};

     Timer timer = new Timer();
     timer.schedule(task, SPLASH_SCREEN_DELAY);
 }
	
	public void updateTutoDB(){
		String upd = "UPDATE 'COMS' SET TUTO = 1";
		db.execSQL(upd);
	}
}

