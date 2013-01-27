package com.example.hearttest;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CharacterActivity extends Activity {
	 MediaPlayer mediaPlayer;
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
	 }
	 public void onResume (){
		 super.onResume();
		 mediaPlayer = MediaPlayer.create(this, R.raw.eightbitmusic);
		 mediaPlayer.start();

		 xpBar = (ProgressBar) findViewById(R.id.xpBar);
		 charName = (TextView) findViewById(R.id.characterName);  
		 str_val = (TextView) findViewById(R.id.AbilityValueStr);  
		 dex_val = (TextView) findViewById(R.id.AbilityValueDex);  
		 int_val = (TextView) findViewById(R.id.AbilityValueInt); 
		 level = (TextView) findViewById(R.id.level);  
		 items = (TextView)findViewById(R.id.itemText);  
		 
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



