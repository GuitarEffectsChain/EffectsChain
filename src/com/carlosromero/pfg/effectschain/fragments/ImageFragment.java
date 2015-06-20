package com.carlosromero.pfg.effectschain.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.carlosromero.pfg.effectschain.MainActivity;
import com.carlosromero.pfg.effectschain.R;
import com.carlosromero.pfg.effectschain.Splash_Activity;
 
public class ImageFragment extends Fragment {
	 int fragVal;
	 
	 public static ImageFragment init(int val) {
		 ImageFragment truitonFrag = new ImageFragment();
		 // Supply val input as an argument.
		 Bundle args = new Bundle();
		 args.putInt("val", val);
		 truitonFrag.setArguments(args);
		 return truitonFrag;
	 }
	 
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 fragVal = getArguments() != null ? getArguments().getInt("val") : 1;
	 }
	 
	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 Bundle savedInstanceState) {
		
	 View layoutView;
    
	 switch (fragVal) {
     	 case 0:
		 layoutView = inflater.inflate(R.layout.tuto_fragment1, container,	 false);
		 return layoutView;
     	case 1:
   		 layoutView = inflater.inflate(R.layout.tuto_fragment2, container,	 false);
   		 return layoutView;
     	case 2:
   		 layoutView = inflater.inflate(R.layout.tuto_fragment3, container,	 false);
   		 return layoutView;
     	case 3:
   		 layoutView = inflater.inflate(R.layout.tuto_fragment4, container,	 false);
   		 ImageView img = (ImageView) layoutView.findViewById(R.id.image_tuto1);
   		 
   		 img.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent mainIntent = new Intent().setClass(
	                     getActivity(), MainActivity.class);
	             startActivity(mainIntent);

	            getActivity().finish();
			}
		});
   		 
   		 return layoutView;
   		
	default:
  		 layoutView = inflater.inflate(R.layout.tuto_fragment1, container,	 false);
  		 return layoutView;
	}
}
}