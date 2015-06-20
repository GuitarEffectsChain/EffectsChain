package com.carlosromero.pfg.effectschain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class InsertPreset extends Activity {
	
	TextView tname;
	EditText edit_name;
	Button btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_preset_layuout);
	
		tname = (TextView) findViewById(R.id.lbl_insertpreset);
		tname.setTextColor(-6447);
		
		edit_name = (EditText) findViewById(R.id.edit_name_preset);
		edit_name.setTextColor(-6447);
		
		btn = (Button) findViewById(R.id.btn_add_name_preset);
		btn.setTextColor(-6447);

		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!edit_name.getText().toString().isEmpty()){
					
					Intent intent=new Intent();  
	                intent.putExtra("name", edit_name.getText().toString());  
	                
	                setResult(3, intent); 
					
					finish();
				
				}
				else{
					Toast.makeText(getApplicationContext(), "El preset debe tener un nombre", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

}
