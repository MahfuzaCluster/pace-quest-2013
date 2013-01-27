package com.example.hearttest;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class FirstActivity extends Activity {
	private static int current_state = 0;
	
	final int SPLASH = 1;
	final int NEWCHAR = 2;
	final int HELP = 3;
	final int THEGAME = 4;
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        SwitchState(SPLASH);
	 }
	 
	 public void SwitchState(int state)
	 {
		 switch(state)
		 {
		 case SPLASH:
			 setContentView(R.layout.splash);
			 ImageView v = (ImageView)findViewById(R.id.splashView);
			 v.setOnClickListener(new OnClickListener() {
	        	   @Override
	        	   public void onClick(View v) {
	        		  //playSound(R.raw.clicksound); 
	        		  File file = new File(getExternalFilesDir(null), "player.dat");
	        		  if(file.exists())
		        	      SwitchState(THEGAME);
	        		  else
	        			  SwitchState(NEWCHAR);
	        	   }
	        	  });
			 break;
		 case NEWCHAR:
			 setContentView(R.layout.newchar);
			 Button b = (Button)findViewById(R.id.beginAdventureBtn);
			 final TextView txt = (TextView)findViewById(R.id.newCharNameText);
			 b.setOnClickListener(new OnClickListener() {
	        	   @Override
	        	   public void onClick(View v) {
	        		  //playSound(R.raw.clicksound);
	      			  
	        		  HeartRateMonitor.Current_Player = new Player (txt.getText().toString());
	        	      SwitchState(HELP);
	        	   }
	        	  });
			 break;
		 case HELP:
			 setContentView(R.layout.firsttime);
			 ImageView t = (ImageView)findViewById(R.id.tutorialImage);
			 t.setOnClickListener(new OnClickListener() {
	        	   @Override
	        	   public void onClick(View v) {
	        		  //playSound(R.raw.clicksound);
	        	      SwitchState(THEGAME);
	        	   }
	        	  });
			 break;
		 case THEGAME:
			 Intent i = new Intent(getApplicationContext(),HeartRateMonitor.class);
	   	     i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	   	     getApplicationContext().startActivity(i);
	   	     finish();
			 break;
		 }
		 
		 current_state = state;
	 }
}
