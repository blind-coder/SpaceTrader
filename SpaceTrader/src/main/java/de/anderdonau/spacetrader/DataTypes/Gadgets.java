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

public class Gadgets {
	public static Gadget[] mGadgets = {new Gadget("5 extra cargo bays", 2500, 4, 35), // 5 extra holds
		new Gadget("Auto-repair system", 7500, 5, 20),
		// Increases engineer's effectivity
		new Gadget("Navigating system", 15000, 6, 20),
		// Increases pilot's effectivity
		new Gadget("Targeting system", 25000, 6, 20),
		// Increases fighter's effectivity
		new Gadget("Cloaking device", 100000, 7, 5),
		// If you have a good engineer, nor pirates nor police will notice you
		// The gadgets below can't be bought
		new Gadget("Fuel compactor", 30000, 8, 0)};

	public static class Gadget {
		public String name;
		public int    price;
		public int    techLevel;
		public int    chance; // Chance that this is fitted in a slot

		public Gadget(String name, int price, int techLevel, int chance) {
			this.name = name;
			this.price = price;
			this.techLevel = techLevel;
			this.chance = chance;
		}

	}
}
