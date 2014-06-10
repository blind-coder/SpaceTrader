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

public class Shields {
	public static Shield[] mShields = {new Shield("Energy shield", GameState.ESHIELDPOWER, 5000, 5,
		70), new Shield("Reflective shield", GameState.RSHIELDPOWER, 20000, 6, 30),
		// The shields below can't be bought
		new Shield("Lightning shield", GameState.LSHIELDPOWER, 45000, 8, 0)};

	public static class Shield {
		public String name;
		public int    power;
		public int    price;
		public int    techLevel;
		public int    chance; // Chance that this is fitted in a slot

		public Shield(String name, int power, int price, int techLevel, int chance) {
			this.name = name;
			this.power = power;
			this.price = price;
			this.techLevel = techLevel;
			this.chance = chance;
		}
	}
}
