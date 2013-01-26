package com.example.hearttest;

import java.util.List;

import android.util.Log;
import android.widget.Toast;


public class Player {
	public long xp;
	public long level;
	public String name;
	public int abi_strength;
	public int abi_dexterity;
	public int abi_intelligence;
	public List<String> items;
	public long money;
	public qnames current_quest;
	public long avg_rest_pulse;
	public long current_pulse;
	public long current_difficulty;
	public float current_progress;
	
	public Player(String name)
	{
		this.name = name;
		int skills = 12;
		int val = (int) (Math.random() * 5);
		abi_strength = 6 + val;
		skills = skills - val;
		val = (int) (Math.random() * 5);
		abi_dexterity = 6 + val;
		skills = skills - val;
		abi_intelligence = 6 + skills;
		xp = 0;
		level = 1;
		avg_rest_pulse = 1;
		current_quest = qnames.No_Quest;
	}
	
	public void GrantItem()
	{
		String[] pref = {"Copper","Steel","Thermo","Bazalt","Kinetic","Quantum","Hyper","Laser","Argentum","Z-Type"};
		String[] item = {"Knife","Hat","Lens","Armor","Generator","Rifle","Jumper","Kit","Digitizer","Helmet","Visor","Scope","Boots","Vest","Cloak","Container"};
		
		String itm = pref[(int) (Math.random()%pref.length)] + " " +  pref[(int) (Math.random()%pref.length)];
		items.add(itm);
		 Toast a = Toast.makeText(HeartRateMonitor.Application_Context, "Picked up " + itm, Toast.LENGTH_SHORT);
	}
	
	public void GiveXp(long amount)
	{
		xp = amount + xp;
		Log.i("Player", amount + " xp gained, total amount now " + xp); 
		 long next_level = GetNextLevelRequirement();
		 while(xp >= next_level)
		 {
			 xp -= next_level;
			 level++;
			 Toast a = Toast.makeText(HeartRateMonitor.Application_Context, "Level up!", Toast.LENGTH_SHORT);
			 a.show();
		 }
		 
	}
	
	public long GetNextLevelRequirement()
	{
		long number = 0;
		number = (long) level * 500;
		return number;
	}
}
