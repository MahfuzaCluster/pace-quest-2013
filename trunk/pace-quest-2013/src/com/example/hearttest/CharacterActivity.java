package com.example.hearttest;
import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CharacterActivity extends Activity {
	 MediaPlayer mediaPlayer;
	 private static ImageButton shareButton = null;
	 private static TextView items = null;
	 private static ProgressBar xpBar = null;
	 private static TextView charName = null;
	 private static TextView str_val = null;
	 private static TextView dex_val = null;
	 private static TextView int_val = null;
	 private static TextView level = null;

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.character);

			 xpBar = (ProgressBar) findViewById(R.id.xpBar);
			 charName = (TextView) findViewById(R.id.characterName);  
			 str_val = (TextView) findViewById(R.id.AbilityValueStr);  
			 dex_val = (TextView) findViewById(R.id.AbilityValueDex);  
			 int_val = (TextView) findViewById(R.id.AbilityValueInt); 
			 level = (TextView) findViewById(R.id.level);  
			 items = (TextView)findViewById(R.id.itemText);  
			 shareButton = (ImageButton)findViewById(R.id.shareButton);
			 shareButton.setOnClickListener(new OnClickListener() {
	        	   @Override
	        	   public void onClick(View v) {
	        		   Player p = HeartRateMonitor.Current_Player;
	        		   Intent intent = new Intent(Intent.ACTION_SEND); 
	        		   intent.setType("text/plain"); 

	        		   intent.putExtra(Intent.EXTRA_SUBJECT, "Pace Quest"); 
	        		   intent.putExtra(Intent.EXTRA_TEXT, "My character " + p.name + " has reached level " + p.level + " in Pace Quest! http://globalgamejam.org/2013/pacequest"); 
	        		   //TODO: Add a link to game
	        		   Intent chooser = Intent.createChooser(intent, "Share your glory!"); 
	        		   startActivity(chooser);
	        		   
	        	   }
	        	  });
	 }
	 public void onResume (){
		 super.onResume();
		 mediaPlayer = MediaPlayer.create(this, R.raw.eightbitmusic);
		 mediaPlayer.start();
		 
		 LoadChar();
	 }
	 public void LoadChar()
	 {
		 Player p = HeartRateMonitor.Current_Player;
		 charName.setText(p.name);
		 str_val.setText(Integer.toString(p.abi_strength));
		 dex_val.setText(Integer.toString(p.abi_dexterity));
		 int_val.setText(Integer.toString(p.abi_intelligence));
		 level.setText("Level " + Long.toString(p.level));
		 int pBar_val = (int) ((float)p.xp / (float)p.GetNextLevelRequirement() * (float)xpBar.getMax());
		 xpBar.setProgress(pBar_val);
		 
		 StringBuffer buf = new StringBuffer();
		 for(int i = 0; i < p.items.size(); i++)
		 {
			 buf.append(p.items.get(i));
			 if(i < p.items.size() - 1)
				 buf.append('\n');
		 }
		 items.setText(buf.toString());
	 }
	 
	 public void onPause()
	 {
		 super.onPause();
		 if(mediaPlayer != null)
			 mediaPlayer.stop();
	 }
}



