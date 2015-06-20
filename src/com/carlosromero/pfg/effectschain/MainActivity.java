package com.carlosromero.pfg.effectschain;

import java.io.File;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.carlosromero.pfg.effectschain.adapters.NavigationAdapter;
import com.carlosromero.pfg.effectschain.bd.SQLDataBase;
import com.carlosromero.pfg.effectschain.classes.Itm_object;
import com.carlosromero.pfg.effectschain.fragments.AboutFragment;
import com.carlosromero.pfg.effectschain.fragments.CargarFragment;
import com.carlosromero.pfg.effectschain.fragments.EffectsFragment;
import com.carlosromero.pfg.effectschain.fragments.HomeFragment;
import com.carlosromero.pfg.effectschain.fragments.OpcionesFragment;
import com.carlosromero.pfg.effectschain.fragments.PresetsFragment;

public class MainActivity extends ActionBarActivity {
	
	private DrawerLayout NavDrawerLayout;
	private ListView NavList;

	SQLiteDatabase db;
	private File f;
	private String[] titulos;
	private ArrayList<Itm_object> NavItms;
	private TypedArray NavIcons;
	private ActionBarDrawerToggle mDrawerToggle;
	private NavigationAdapter NavAdapter;
	public static int id_preset_act = 1;
	private boolean doubleBackToExitPressedOnce = false;
	private boolean first = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//Creamos carpeta para guardar/cargar ficheros
		String folder_main = "EffectsChainFolder";

        f = new File(Environment.getExternalStorageDirectory(), folder_main);
        
        if (!f.exists()) {
        	Log.w("FOLDER", "Carpeta Existente");
            f.mkdirs();
        }
        
        File[] fList = f.listFiles();
        
        for (int i = 0; i < fList.length; i++) {
        	if(!fList[i].isDirectory()){
			Log.i("Files in:", fList[i].getName());}
		}
		
		NavDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		NavList = (ListView) findViewById(R.id.left_drawer);
		
		View header = getLayoutInflater().inflate(R.layout.header, null);
		
		NavList.addHeaderView(header);
		
		NavIcons = getResources().obtainTypedArray(R.array.nav_options_imgs);
		
		titulos = getResources().getStringArray(R.array.nav_options);
		
		NavItms = new ArrayList<Itm_object>();
		
		for (int i = 0; i < titulos.length; i++) {
			NavItms.add(new Itm_object(titulos[i], NavIcons.getResourceId(i, -1)));
		}
		
		NavAdapter = new NavigationAdapter(this, NavItms);
		NavList.setAdapter(NavAdapter);
		
		mDrawerToggle = new ActionBarDrawerToggle(this, 
												  NavDrawerLayout,
												  R.drawable.ic_action_sort_by_size,
												  R.string.hello_world, 
												  R.string.app_name
												  ){
			
			@Override
			public void onDrawerClosed(View drawerView) {
				getSupportActionBar().setTitle("EffectsChain");
				}
			
			@Override
			public void onDrawerOpened(View drawerView) {
			// TODO Auto-generated method stub
				//getSupportActionBar().setTitle("");
			}
		};
			
		NavDrawerLayout.setDrawerListener(mDrawerToggle);  
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        NavList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mostrarFragment(position);
			}
		});
        
        SQLDataBase base = new SQLDataBase(this, "DB_EF", null, 1);
		db = base.getWritableDatabase();
		
		Cursor c = db.rawQuery("SELECT MIN(ID) FROM PRESETS", null);
		
		
		if(first){
		if(c.moveToFirst()){
		
		String ID_MIN = c.getString(0);
		if(ID_MIN != null){
		
			id_preset_act = Integer.parseInt(ID_MIN);
		}
		else{
			id_preset_act = -1;
			}
		}
		}
		first = false;
        mostrarFragment(1);
		
        
        
	}
 
	@Override
	protected void onResume() {
	    super.onResume();
	    this.doubleBackToExitPressedOnce = false;
	}

	@Override
	public void onBackPressed() {
	    if (doubleBackToExitPressedOnce) {
	        this.finish();
	        return;
	    }
	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Presiona otra vez para salir", Toast.LENGTH_SHORT).show();

	}
	
	private void mostrarFragment(int position){
		Fragment fragment = null;
		
		switch (position) {
		case 0:
			break;
		case 1:
			fragment = new HomeFragment(this, id_preset_act);
			break;
		case 2:
			fragment = new PresetsFragment(this);
			break;
		case 3:
			fragment = new EffectsFragment(this);
			break;
		case 4:
			fragment = new CargarFragment(this); 
			break;
		case 5:
			fragment = new OpcionesFragment();
			break;
		case 6:
			fragment = new AboutFragment();
			break;	
		default:
			Toast.makeText(this, "Opcion: " + titulos[position-1] + " no disponible" , Toast.LENGTH_SHORT).show();
			fragment = new HomeFragment(this, 1);
			position = 1;
			break;
		}
		
		if(fragment != null){
			
		FragmentManager fragmentManager = getFragmentManager();	
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		
		NavList.setItemChecked(position, true);
		NavList.setSelection(position);
		//setTitle(titulos[position-1]);
		NavDrawerLayout.closeDrawer(NavList);
		
		}
		else{
			Log.e("FRAGMENT ERROR", "FRAGMENT NULL");		
		}		
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
