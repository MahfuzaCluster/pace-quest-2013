package com.example.hearttest;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

	public static String GetQuestStory(qnames id)
	{
		String[] agg_stories = {
				"You sprint towards the enemies, fast and focused, and in one smooth motion you smash the first one in the face with your fist. The next one gets a taste of your weapon and goes down. The rest is a flurry of hits and shouts, the sound of your heart pumping adrenaline through your veins.",
				"It's them again! You catch a glimpse of a the plume of an imperial soldier's helmet and suddenly there's no time to wait any longer. You run past the corner and take the two guards totally by surprise, they are down for the count before they know it. ",
				"Your weapons seems like an immovable force as you make your way through enemy lines. Soldiers fall and scream, but you are untouchable, determined to get to the bottom of this, what ever it takes.",
				"The fire is spreading fast, you think you can even hear the flames roaring behind you. The door outside should be just a few corners away, now run!",
				"Run, run, run! Warning shouts ring the colony streets, the army is coming in with tanks. The rebellion has finally gotten the government's attention all right!",
				"Outside the biodome everything seems harder to do. The artificial gravity isn't helping you fit in, and the mask feels like it just doesn't give enough oxygen if you get out of breath. This mission needs to be over and fast."
				};
		switch(id)
		{
		//Aggressive
		case UpBeat:			
		case SuperUp:	
		case FullOut:
			return agg_stories[(int) (HeartRateMonitor.Current_Player.xp % agg_stories.length)];	
		}
		return "";
	}
	public static String GetQuestDescription(qnames id, float difficulty)
	{
		switch(id)
		{
		case UpBeat:
			return "Raise beats per minute above " + String.format("%.0f",(HeartRateMonitor.Current_Player.avg_rest_pulse * 1.30 + ((difficulty - 1.f) * 0.5f))) + " for 60 seconds.";
		case SuperUp:
			return "Raise your beat above " + String.format("%.0f",(HeartRateMonitor.Current_Player.avg_rest_pulse * 1.20 + ((difficulty - 1.f) * 0.5f))) + " for two minutes";		
		case FullOut:
			return "Raise your beat above " + String.format("%.0f",(HeartRateMonitor.Current_Player.avg_rest_pulse * 1.60 + ((difficulty - 1.f) * 0.5f))) + " for 60 seconds";	
		}
		
		return "";		
	}
	
	public static void GetNewQuest()
	{
		Player p = HeartRateMonitor.Current_Player;
		p.current_quest = qnames.values()[(int) (Math.random() * 3)];
		p.current_difficulty = (float) (Math.random() * 0.2f + 0.9f);
		
		TextView description = (TextView)((Activity)(HeartRateMonitor.Application_Context)).findViewById(R.id.textView1);
		description.setText(Quests.GetQuestStory(HeartRateMonitor.Current_Player.current_quest) + "\n\n" 
				+ Quests.GetQuestDescription(HeartRateMonitor.Current_Player.current_quest, HeartRateMonitor.Current_Player.current_difficulty));
		

        Button charButton = (Button)((Activity)(HeartRateMonitor.Application_Context)).findViewById(R.id.charButton);
        charButton.setText(HeartRateMonitor.Current_Player.name + " (level " + HeartRateMonitor.Current_Player.level + ")");
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
