package com.example.hearttest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;
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
	public float current_difficulty;
	public float current_progress;
	

	private void writeObject(ObjectOutputStream out) throws IOException{

	    out.writeLong(xp);
	    out.writeLong(level);
	    out.writeLong(money);
	    out.writeLong(avg_rest_pulse);
	    out.writeLong(current_pulse);
	    out.write(abi_strength);
	    out.write(abi_dexterity);
	    out.write(abi_intelligence);
	    out.writeFloat(current_difficulty);
	    out.writeFloat(current_progress);
	    out.writeObject(name);
	    out.writeObject(current_quest);
	    out.writeObject(items);

	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{

	    xp=in.readLong();
	    level=in.readLong();
	    money=in.readLong();
	    avg_rest_pulse=in.readLong();
	    current_pulse=in.readLong();
	    abi_strength = in.read();
	    abi_dexterity = in.read();
	    abi_intelligence = in.read();
	    
	    current_difficulty=in.readFloat();

	    current_progress= in.readFloat();
	    name=(String) in.readObject();
	    current_quest=(qnames) in.readObject();
	    items=(List<String>) in.readObject();
	    
	}
	
	public void Load(String filename)	
	{
		String Full_Path = null;
		File file = null;
		try
		{
			//Full_Path = 		 HeartRateMonitor.Application_Context.getExternalFilesDir(null) + filename;
			file = new File(HeartRateMonitor.Application_Context.getExternalFilesDir(null), filename);
			if(file.exists() == false)
				return;
		}catch(Exception ex)
		{
			return;
		}
		FileInputStream fIn = null;
		try {
			fIn = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(fIn);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			readObject(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Save(String filename)
	{
		FileOutputStream fOut = null;
		try {
			File file = new File(HeartRateMonitor.Application_Context.getExternalFilesDir(null), filename);
			fOut = new FileOutputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(fOut);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Write the string to the file
		try
		{
			writeObject(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
		current_difficulty = 1.f;
		current_quest = qnames.No_Quest;
		items = new ArrayList<String>();
		
		Load("player.dat");
		
		for(int i = 0; i < 45; i++)
			GrantItem();
	}
	
	public void GrantItem()
	{
		String[] pref = {"Copper","Steel","Thermo","Bazalt","Kinetic","Quantum","Hyper","Laser","Argentum","Z-Type"};
		String[] item = {"Knife","Hat","Lens","Armor","Generator","Rifle","Jumper","Kit","Digitizer","Helmet","Visor","Scope","Boots","Vest","Cloak","Container"};
		
		String itm = pref[(int) (Math.random()*pref.length)] + " " +  item[(int) (Math.random()*item.length)];
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
			 LevelUp();
		 }
	}
	
	public void LevelUp()
	{
		level++;
		String msg = "Level up!";
		
		int val = (int) (Math.random() * 3);
		
		switch(val)
		{
		case 0:
			abi_strength++;
			msg += " +1 Strength.";
			break;
		case 1:
			abi_dexterity++;
			msg += " +1 Dexterity.";
			break;
		case 2:
			abi_intelligence++;
			msg += " +1 Intelligence.";
			break;
		}
		Toast a = Toast.makeText(HeartRateMonitor.Application_Context, msg, Toast.LENGTH_SHORT);
		a.show();
		return;
	}
	
	public long GetNextLevelRequirement()
	{
		long number = 0;
		number = (long) level * 500;
		return number;
	}
}
