package com.example.hearttest;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

enum qnames
{
	UpBeat,
	SuperUp,
	FullOut,
	No_Quest
}

public abstract class Quests {
	private static double general_multiplier = 1.0f;
	public static void CompleteQuest(qnames id, float difficulty)
	{
		Toast t = Toast.makeText(HeartRateMonitor.Application_Context, "Quest Complete!", Toast.LENGTH_SHORT);
		
		if(difficulty > 1.f)
		{
			if(difficulty * 10 + Math.random()*10 > 15)
				HeartRateMonitor.Current_Player.GrantItem();
		}
		Log.i("Quests", "Quest completed, giving xp for " + id + " at difficulty " + difficulty);
		HeartRateMonitor.Current_Player.GiveXp(GetXPForQuest(id, difficulty));
		t.show();

		HeartRateMonitor.Current_Player.Save("player.dat");
	}
	
	public static long GetXPForQuest(qnames id, float difficulty)
	{
		long xp = 0; 
		switch(id)
		{
		case UpBeat:
			xp = (long) (400.f * difficulty * general_multiplier);
			Log.i("Quests", "400.f * " + difficulty + " * " + general_multiplier + " = " + xp );
			return xp;
		case SuperUp:
			xp = (long) (600.f * difficulty * general_multiplier);
			Log.i("Quests", "600.f * " + difficulty + " * " + general_multiplier + " = " + xp );
			return xp;
		case FullOut:
			xp = (long) (1000.f * difficulty * general_multiplier);
			Log.i("Quests", "1000.f * " + difficulty + " * " + general_multiplier + " = " + xp );
			return xp;
		}

		Log.i("Quests", "Quest not recognized.");
		return 0;
	}
	
	public static String GetQuestTitle(qnames id, float difficulty)
	{
		switch(id)
		{
		case UpBeat:
			return "The Chase";
		case SuperUp:
			return "Fight the Fire";
		case FullOut:
			return "Nuclear Blast!";
		}
		
		return "";
	}
	
	public static String GetQuestDescription(qnames id, float difficulty)
	{
		switch(id)
		{
		case UpBeat:
			return "Raise beats per minute above " + (HeartRateMonitor.Current_Player.avg_rest_pulse * 1.30 + ((difficulty - 1.f) * 0.5f)) + " for 60 seconds.";
		case SuperUp:
			return "Raise your beat above " + (HeartRateMonitor.Current_Player.avg_rest_pulse * 1.20 + ((difficulty - 1.f) * 0.5f)) + " for two minutes";		
		case FullOut:
			return "Raise your beat above " + (HeartRateMonitor.Current_Player.avg_rest_pulse * 1.60 + ((difficulty - 1.f) * 0.5f)) + " for 60 seconds";	
		}
		
		return "";		
	}
	
	public static void GetNewQuest()
	{
		Player p = HeartRateMonitor.Current_Player;
		p.current_quest = qnames.values()[(int) (Math.random() * 3)];
		p.current_difficulty = (float) (Math.random() * 0.2f + 0.9f);
		
		TextView description = (TextView)((Activity)(HeartRateMonitor.Application_Context)).findViewById(R.id.textView1);
		description.setText(Quests.GetQuestDescription(HeartRateMonitor.Current_Player.current_quest, HeartRateMonitor.Current_Player.current_difficulty));
	}
	
	public static float UpdateQuest(qnames id, float difficulty, float current_progress, float seconds)
	{
		float progress = current_progress;
		Player p = HeartRateMonitor.Current_Player;
		float bar = 0.f;
		switch(id)
		{
		case UpBeat:
			bar = (float) ((float)p.avg_rest_pulse * 1.30f + ((difficulty - 1.f) * 0.5f));
			if(bar <= p.current_pulse)
				progress = current_progress + seconds / 60.f;	
			Log.i("Quests", "bar is " + bar + " and seconds were " + seconds);
		case SuperUp:
			bar = (float) ((float)p.avg_rest_pulse * 1.20f + ((difficulty - 1.f) * 0.5f));
			if(bar <= p.current_pulse)
				progress = current_progress + seconds / 120.f;
			Log.i("Quests", "bar is " + bar + " and seconds were " + seconds);
		case FullOut:
			bar = (float) ((float)p.avg_rest_pulse * 1.60f + ((difficulty - 1.f) * 0.5f));
			if(bar <= p.current_pulse)
				progress = current_progress + seconds / 60.f;
			Log.i("Quests", "bar is " + bar + " and seconds were " + seconds);
		}
		if(progress >= 1)
		{
			CompleteQuest(id, difficulty);
			progress = 0;
		}

		return progress;		
	}
}
