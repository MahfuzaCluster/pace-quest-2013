package com.example.hearttest;

import android.widget.Toast;

enum qnames
{
	UpBeat,
	SuperUp,
	FullOut,
	
}

public abstract class Quests {
	private static double general_multiplier = 1.0f;
	public static void CompleteQuest(qnames id, float difficulty)
	{
		Toast t = Toast.makeText(HeartRateMonitor.Application_Context, "Quest Complete!", Toast.LENGTH_SHORT);
		
		if(difficulty > 1)
		{
			if(difficulty * 10 + Math.random()*10 > 15)
				HeartRateMonitor.Current_Player.GrantItem();
		}
		HeartRateMonitor.Current_Player.GiveXp(GetXPForQuest(id, difficulty));
		t.show();
	}
	
	public static long GetXPForQuest(qnames id, float difficulty)
	{
		switch(id)
		{
		case UpBeat:
			return (long) (400 * difficulty * general_multiplier);
		case SuperUp:
			return (long) (600 * difficulty * general_multiplier);
		case FullOut:
			return (long) (1000 * difficulty * general_multiplier);
		}
		
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
		
		return "null";
	}
	
	public static String GetQuestDescription(qnames id, float difficulty)
	{
		switch(id)
		{
		case UpBeat:
			return "Raise beats per minute above " + HeartRateMonitor.Current_Player.avg_rest_pulse * 1.30 + ((difficulty - 1.f) * 0.5f) + " for 60 seconds.";
		case SuperUp:
			return "Raise your beat above " + HeartRateMonitor.Current_Player.avg_rest_pulse * 1.20 + ((difficulty - 1.f) * 0.5f) + " for two minutes";		
		case FullOut:
			return "Raise your beat above " + HeartRateMonitor.Current_Player.avg_rest_pulse * 1.60 + ((difficulty - 1.f) * 0.5f) + " for 60 seconds";	
		}
		
		return "null";		
	}
	
	public static void GetNewQuest()
	{
		Player p = HeartRateMonitor.Current_Player;
		p.current_quest = qnames.values()[(int) (Math.random() * 3)];	
		
		//TODO: Update description
	}
	
	public static float UpdateQuest(qnames id, float difficulty, float current_progress, float seconds)
	{
		float progress = current_progress;
		Player p = HeartRateMonitor.Current_Player;
		float bar = 0.f;
		switch(id)
		{
		case UpBeat:
			bar = (float) (p.avg_rest_pulse * 1.30 + ((difficulty - 1.f) * 0.5f));
			if(bar <= p.current_pulse)
				progress = current_progress + seconds / 60;	
		case SuperUp:
			bar = (float) (p.avg_rest_pulse * 1.20 + ((difficulty - 1.f) * 0.5f));
			if(bar <= p.current_pulse)
				progress = current_progress + seconds / 120;
		case FullOut:
			bar = (float) (p.avg_rest_pulse * 1.60 + ((difficulty - 1.f) * 0.5f));
			if(bar <= p.current_pulse)
				progress = current_progress + seconds / 60;
		}
		if(progress >= 1)
		{
			CompleteQuest(id, difficulty);
			progress = 0;
		}
		return progress;		
	}
}
