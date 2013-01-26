package com.example.hearttest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Character implements Serializable {
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

	
		private void writeObject(ObjectOutputStream out) throws IOException{

		    out.writeLong(xp);
		    out.writeLong(level);
		    out.writeLong(money);
		    out.writeLong(avg_rest_pulse);
		    out.writeLong(current_pulse);
		    out.writeLong(current_difficulty);
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
		    current_difficulty=in.readLong();

		    current_progress=(Float) in.readObject();
		    name=(String) in.readObject();
		    current_quest=(qnames) in.readObject();
		    items=(List<String>) in.readObject();
		    
}
		
}
