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
import de.anderdonau.spacetrader.R;

public class ShipTypes {
	public static final int        FLEA         = 0;
	public static final int        GNAT         = 1;
	public static final int        FIREFLY      = 2;
	public static final int        MOSQUITO     = 3;
	public static final int        BUMBLEBEE    = 4;
	public static final int        BEETLE       = 5;
	public static final int        HORNET       = 6;
	public static final int        GRASSHOPPER  = 7;
	public static final int        TERMITE      = 8;
	public static final int        WASP         = 9;
	public static final int        SPACEMONSTER = 10;
	public static final int        DRAGONFLY    = 11;
	public static final int        MANTIS       = 12;
	public static final int        SCARAB       = 13;
	public static final int        BOTTLE       = 14;
	public static       ShipType[] ShipTypes    = {new ShipType("Flea", 10, 0, 0, 0, 1,
		GameState.MAXRANGE, 4, 1, 2000, 5, 2, 25, -1, -1, 0, 1, 0, R.drawable.flea,
		R.drawable.flea_damaged, R.drawable.flea_shield), new ShipType("Gnat", 15, 1, 0, 1, 1, 14, 5, 2,
		10000, 50, 28, 100, 0, 0, 0, 1, 1, R.drawable.gnat, R.drawable.gnat_damaged,
		R.drawable.gnat_shield), new ShipType("Firefly", 20, 1, 1, 1, 1, 17, 5, 3, 25000, 75, 20, 100,
		0, 0, 0, 1, 1, R.drawable.firefly, R.drawable.firefly_damaged, R.drawable.firefly_shield),
		new ShipType("Mosquito", 15, 2, 1, 1, 1, 13, 5, 5, 30000, 100, 20, 100, 0, 1, 0, 1, 1,
			R.drawable.mosquito, R.drawable.mosquito_damaged, R.drawable.mosquito_shield), new ShipType(
		"Bumblebee", 25, 1, 2, 2, 2, 15, 5, 7, 60000, 125, 15, 100, 1, 1, 0, 1, 2, R.drawable.bumblebee,
		R.drawable.bumblebee_damaged, R.drawable.bumblebee_shield), new ShipType("Beetle", 50, 0, 1, 1,
		3, 14, 5, 10, 80000, 50, 3, 50, -1, -1, 0, 1, 2, R.drawable.beetle, R.drawable.beetle_damaged,
		R.drawable.beetle_shield), new ShipType("Hornet", 20, 3, 2, 1, 2, 16, 6, 15, 100000, 200, 6,
		150, 2, 3, 1, 2, 3, R.drawable.hornet, R.drawable.hornet_damaged, R.drawable.hornet_shield),
		new ShipType("Grasshopper", 30, 2, 2, 3, 3, 15, 6, 15, 150000, 300, 2, 150, 3, 4, 2, 3, 3,
			R.drawable.grasshopper, R.drawable.grasshopper_damaged, R.drawable.grasshopper_shield),
		new ShipType("Termite", 60, 1, 3, 2, 3, 13, 7, 20, 225000, 300, 2, 200, 4, 5, 3, 4, 4,
			R.drawable.termite, R.drawable.termite_damaged, R.drawable.termite_shield), new ShipType(
		"Wasp", 35, 3, 2, 2, 3, 14, 7, 20, 300000, 500, 2, 200, 5, 6, 4, 5, 4, R.drawable.wasp,
		R.drawable.wasp_damaged, R.drawable.wasp_shield), new ShipType("Spacemonster", 0, 3, 0, 0, 1, 1,
		8, 1, 500000, 0, 0, 500, 8, 8, 8, 1, 4,
		// These ships can't be bought
		R.drawable.spacemonster, R.drawable.spacemonster_damaged, R.drawable.spacemonster_shield),
		new ShipType("Dragonfly", 0, 2, 3, 2, 1, 1, 8, 1, 500000, 0, 0, 10, 8, 8, 8, 1, 1,
			R.drawable.dragonfly, R.drawable.dragonfly_damaged, R.drawable.dragonfly_shield),
		new ShipType("Mantis", 0, 3, 1, 3, 3, 1, 8, 1, 500000, 0, 0, 300, 8, 8, 8, 1, 2,
			R.drawable.mantis, R.drawable.mantis_damaged, R.drawable.mantis_shield), new ShipType(
		"Scarab", 20, 2, 0, 0, 2, 1, 8, 1, 500000, 0, 0, 400, 8, 8, 8, 1, 3, R.drawable.scarab,
		R.drawable.scarab_damaged, R.drawable.scarab_shield), new ShipType("Bottle", 0, 0, 0, 0, 0, 1,
		8, 1, 100, 0, 0, 10, 8, 8, 8, 1, 1, R.drawable.bottle, R.drawable.bottle_damaged,
		R.drawable.bottle_shield)};

	public static class ShipType {
		public String name;
		public int    cargoBays;   // Number of cargo bays
		public int    weaponSlots; // Number of lasers possible
		public int    shieldSlots; // Number of shields possible
		public int    gadgetSlots; // Number of gadgets possible (e.g. docking computers)
		public int    crewQuarters;  // Number of crewmembers possible
		public int    fuelTanks;   // Each tank contains enough fuel to travel 10 parsecs
		public int    minTechLevel;  // Minimum tech level needed to build ship
		public int    costOfFuel;  // Cost to fill one tank with fuel
		public int    price;     // Base ship cost
		public int    occurrence;   // Percentage of the ships you meet
		public int    hullStrength;  // Hull strength
		public int    police;     // Encountered as police with at least this strength
		public int    pirates;    // idem Pirates
		public int    traders;    // idem Traders
		public int    repairCosts; // Repair costs for 1 point of hull strength.
		public int    size;      // Determines how easy it is to hit this ship
		public int    drawable; // Android resource of the image
		public int    drawable_damaged; // Android resource of the image
		public int    drawable_shield; // Android resource of the image
		int bounty;     // Base bounty

		public ShipType(String name, int cargoBays, int weaponSlots, int shieldSlots, int gadgetSlots, int crewQuarters, int fuelTanks, int minTechLevel, int costOfFuel, int price, int bounty, int occurrence, int hullStrength, int police, int pirates, int traders, int repairCosts, int size, int drawable, int drawable_damaged, int drawable_shield) {
			this.name = name;
			this.cargoBays = cargoBays;
			this.weaponSlots = weaponSlots;
			this.shieldSlots = shieldSlots;
			this.gadgetSlots = gadgetSlots;
			this.crewQuarters = crewQuarters;
			this.fuelTanks = fuelTanks;
			this.minTechLevel = minTechLevel;
			this.costOfFuel = costOfFuel;
			this.price = price;
			this.bounty = bounty;
			this.occurrence = occurrence;
			this.hullStrength = hullStrength;
			this.police = police;
			this.pirates = pirates;
			this.traders = traders;
			this.repairCosts = repairCosts;
			this.size = size;
			this.drawable = drawable;
			this.drawable_damaged = drawable_damaged;
			this.drawable_shield = drawable_shield;
		}
	}
}
