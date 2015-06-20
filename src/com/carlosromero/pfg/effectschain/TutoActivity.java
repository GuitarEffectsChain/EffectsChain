package com.carlosromero.pfg.effectschain;


import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.fragments.ImageFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
 
public class TutoActivity extends FragmentActivity {
	 static final int ITEMS = 4;
	 MyAdapter mAdapter;
	 ViewPager mPager;
	 
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.tuto_layout);
	 mAdapter = new MyAdapter(getSupportFragmentManager());
	 mPager = (ViewPager) findViewById(R.id.viewpager);
	 mPager.setAdapter(mAdapter);
	 
	 }
	 
	 public static class MyAdapter extends FragmentPagerAdapter {
	 public MyAdapter(FragmentManager fragmentManager) {
	 super(fragmentManager);
	 }
	 
	 @Override
	 public int getCount() {
	 return ITEMS;
	 }
	 
	 @Override
	 public Fragment getItem(int position) {
		 return ImageFragment.init(position);
	 	}
	 }

}