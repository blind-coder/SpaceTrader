/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import de.anderdonau.spacetrader.GameState;

public class Shields {
	public Shield[] mShields;

	public Shields() {
		mShields = new Shield[GameState.MAXSHIELDTYPE + GameState.EXTRASHIELDS];
		mShields[0] = new Shield("Energy shield", GameState.ESHIELDPOWER, 5000, 5, 70);
		mShields[1] = new Shield("Reflective shield", GameState.RSHIELDPOWER, 20000, 6, 30);
		// The shields below can't be bought
		mShields[2] = new Shield("Lightning shield", GameState.LSHIELDPOWER, 45000, 8, 0);

	}

	public class Shield {
		public String name;
		int power;
		public int price;
		public int techLevel;
		int chance; // Chance that this is fitted in a slot

		public Shield(String name, int power, int price, int techLevel, int chance) {
			this.name = name;
			this.power = power;
			this.price = price;
			this.techLevel = techLevel;
			this.chance = chance;
		}
	}
}
