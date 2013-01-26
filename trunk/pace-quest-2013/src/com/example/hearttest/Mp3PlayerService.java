package com.example.hearttest;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;

public class Mp3PlayerService extends Activity {
	private MediaPlayer mediaPlayer;
	private final Handler handler = new Handler();
	private void initViews() {
	mediaPlayer = MediaPlayer.create(this, R.raw.eightbitmusic);
	}
}
