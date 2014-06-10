/*
 * Copyright (c) 2014 Benjamin Schieder
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.anderdonau.spacetrader.DataTypes;

import android.content.Context;
import android.content.SharedPreferences;

import de.anderdonau.spacetrader.GameState;

public class HighScore {
	String                   Name;
	int                      Status; // 0 = killed, 1 = Retired, 2 = Bought moon
	int                      Days;
	int                      Worth;
	int                      Difficulty;
	int                      pos;
	Context                  mContext;
	SharedPreferences        sp;
	SharedPreferences.Editor e;

	public HighScore(Context mContext, int i) {
		this.mContext = mContext;
		this.sp = mContext.getSharedPreferences("HighScore", Context.MODE_PRIVATE);
		this.e = sp.edit();
		this.Status = sp.getInt("Status" + String.valueOf(i), GameState.KILLED);
		this.Days = sp.getInt("Days" + String.valueOf(i), i * 10);
		this.Worth = sp.getInt("Worth" + String.valueOf(i), i * 500);
		this.Difficulty = sp.getInt("Difficulty" + String.valueOf(i), i);
		this.Name = sp.getString("Name" + String.valueOf(i), "");
		this.pos = i;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.e.putString("Name" + String.valueOf(this.pos), name);
		e.commit();
		Name = name;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int status) {
		this.e.putInt("Status" + String.valueOf(this.pos), status);
		e.commit();
		Status = status;
	}

	public int getDays() {
		return Days;
	}

	public void setDays(int days) {
		this.e.putInt("Days" + String.valueOf(this.pos), days);
		e.commit();
		Days = days;
	}

	public int getWorth() {
		return Worth;
	}

	public void setWorth(int worth) {
		this.e.putInt("Worth" + String.valueOf(this.pos), worth);
		e.commit();
		Worth = worth;
	}

	public int getDifficulty() {
		return Difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.e.putInt("Difficulty" + String.valueOf(this.pos), difficulty);
		e.commit();
		Difficulty = difficulty;
	}
}
