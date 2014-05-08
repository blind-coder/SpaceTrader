/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import java.io.Serializable;

public class CrewMember implements Serializable {
	public int nameIndex;
	public int pilot;
	public int fighter;
	public int trader;
	public int engineer;
	public int curSystem;

	public CrewMember() {
		nameIndex = 1;
		pilot = 1;
		fighter = 1;
		trader = 1;
		engineer = 1;
		curSystem = -1;
	}

	public CrewMember(int nameIndex, int pilot, int fighter, int trader, int engineer, int curSystem) {
		this.nameIndex = nameIndex;
		this.pilot = pilot;
		this.fighter = fighter;
		this.trader = trader;
		this.engineer = engineer;
		this.curSystem = curSystem;
	}
}
