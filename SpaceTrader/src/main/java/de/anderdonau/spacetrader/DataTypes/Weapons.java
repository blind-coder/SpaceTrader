/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import de.anderdonau.spacetrader.GameState;

public class Weapons {
	public static Weapon[] mWeapons = {
		new Weapon("Pulse laser", GameState.PULSELASERPOWER, 2000, 5, 50),
		new Weapon("Beam laser", GameState.BEAMLASERPOWER, 12500, 6, 35),
		new Weapon("Military laser", GameState.MILITARYLASERPOWER, 35000, 7, 15),
		// The weapons below cannot be bought
		new Weapon("Morgan's laser", GameState.MORGANLASERPOWER, 50000, 8, 0)
	};

	public Weapons() { }

	public static class Weapon {
		public String name;
		public int    power;
		public int    price;
		public int    techLevel;
		public int    chance; // Chance that this is fitted in a slot

		public Weapon(String name, int power, int price, int techLevel, int chance) {
			this.name = name;
			this.power = power;
			this.price = price;
			this.techLevel = techLevel;
			this.chance = chance;
		}
	}
}