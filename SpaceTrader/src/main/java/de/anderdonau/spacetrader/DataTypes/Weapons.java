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

import de.anderdonau.spacetrader.GameState;

public class Weapons {
	public static Weapon[] mWeapons = {new Weapon("Pulse laser", GameState.PULSELASERPOWER, 2000, 5,
		50), new Weapon("Beam laser", GameState.BEAMLASERPOWER, 12500, 6, 35), new Weapon(
		"Military laser", GameState.MILITARYLASERPOWER, 35000, 7, 15),
		// The weapons below cannot be bought
		new Weapon("Morgan's laser", GameState.MORGANLASERPOWER, 50000, 8, 0)};

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