package com.carlosromero.pfg.effectschain;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.carlosromero.pfg.effectschain.bd.SQLDataBase;

public class Conectivity extends Activity {

	EditText edIp, edPort;
	TextView lblIp, lblPort;
	Button btn;
	String ip, port;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.conectivity_layout);
		
		lblIp = (TextView) findViewById(R.id.label_ip);
		lblPort = (TextView) findViewById(R.id.label_port);

		lblIp.setTextColor(-6447);
		lblPort.setTextColor(-6447);
		
		edIp = (EditText) findViewById(R.id.editIp);
		edIp.setTextColor(-6447);
		
		edPort = (EditText) findViewById(R.id.editPort);
		edPort.setTextColor(-6447);
		
		btn = (Button) findViewById(R.id.btn_conectivity);
		
		SQLDataBase base = new SQLDataBase(this, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		Cursor c = db.rawQuery("SELECT * FROM COMS", null);
		
		if(c.moveToFirst()){
			ip = c.getString(1);
			Log.i("****", c.getString(0));
            port = c.getString(2);
            Log.i("****", port);
		}
		
		edIp.setText(ip);
		edPort.setText(port);
		
		btn.setTextColor(-6447);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					
				
				String insertIP = "UPDATE 'COMS' SET IP = '" + edIp.getText().toString() + "' WHERE ID = 0";
				String insertPORT = "UPDATE 'COMS' SET PORT = '" + edPort.getText().toString() + "' WHERE ID = 0";
				
				db.execSQL(insertIP);
				db.execSQL(insertPORT);
				
				//Toast.makeText(getApplicationContext(), "Parametros actualizados", Toast.LENGTH_SHORT).show();
				
				Intent intent=new Intent();  
                intent.putExtra("ip", edIp.getText().toString());  
                intent.putExtra("port", edPort.getText().toString());  
                
                setResult(2,intent); 
				
				finish();
				
				} catch (Exception e) {
					
					Toast.makeText(getApplicationContext(), "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
					// TODO: handle exception
				}
						
			}
		});
	}
}
