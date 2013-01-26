package com.example.hearttest;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class CharacterActivity extends Activity {
	 MediaPlayer mediaPlayer;

	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.character);
	 }
	 public void onResume (){
		 super.onResume();
			final Handler handler = new Handler();
		//	private void initViews() {
			mediaPlayer = MediaPlayer.create(this, R.raw.eightbitmusic);
			mediaPlayer.start();
	 }
	 public void onPause()
	 {
		 super.onPause();
		 if(mediaPlayer != null)
			 mediaPlayer.stop();
	 }
}



