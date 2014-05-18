/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import android.content.Context;
import android.content.SharedPreferences;

import de.anderdonau.spacetrader.GameState;

/**
 * Created by blindcoder on 5/18/14.
 */
public class HighScore {
	String Name;
	int Status; // 0 = killed, 1 = Retired, 2 = Bought moon
	int Days;
	int Worth;
	int Difficulty;
	int pos;
	Context mContext;
	SharedPreferences sp;
	SharedPreferences.Editor e;

	public HighScore(Context mContext, int i){
		this.mContext = mContext;
		this.sp = mContext.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
		this.e = sp.edit();
		this.Status = sp.getInt("Status"+String.valueOf(i), GameState.KILLED);
		this.Days = sp.getInt("Days"+String.valueOf(i), i*10);
		this.Worth = sp.getInt("Worth"+String.valueOf(i), i*500);
		this.Difficulty = sp.getInt("Difficulty"+String.valueOf(i), i);
		this.Name = sp.getString("Name"+String.valueOf(i), "");
		this.pos = i;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.e.putString("Name"+String.valueOf(this.pos), name);
		e.commit();
		Name = name;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		this.e.putInt("Status"+String.valueOf(this.pos), status);
		e.commit();
		Status = status;
	}

	public int getDays() {
		return Days;
	}

	public void setDays(int days) {
		this.e.putInt("Days"+String.valueOf(this.pos), days);
		e.commit();
		Days = days;
	}

	public int getWorth() {
		return Worth;
	}

	public void setWorth(int worth) {
		this.e.putInt("Worth"+String.valueOf(this.pos), worth);
		e.commit();
		Worth = worth;
	}

	public int getDifficulty() {
		return Difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.e.putInt("Difficulty"+String.valueOf(this.pos), difficulty);
		e.commit();
		Difficulty = difficulty;
	}
}
