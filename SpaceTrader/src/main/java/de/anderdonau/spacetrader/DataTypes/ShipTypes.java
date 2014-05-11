/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import de.anderdonau.spacetrader.GameState;

public class ShipTypes {
	public ShipType[] ShipTypes;

	public ShipTypes() {
		this.ShipTypes = new ShipType[GameState.MAXSHIPTYPE + GameState.EXTRASHIPS];
		this.ShipTypes[0] = new ShipType("Flea", 10, 0, 0, 0, 1, GameState.MAXRANGE, 4, 1, 2000, 5, 2, 25, -1, -1, 0, 1, 0);
		this.ShipTypes[1] = new ShipType("Gnat", 15, 1, 0, 1, 1, 14, 5, 2, 10000, 50, 28, 100, 0, 0, 0, 1, 1);
		this.ShipTypes[2] = new ShipType("Firefly", 20, 1, 1, 1, 1, 17, 5, 3, 25000, 75, 20, 100, 0, 0, 0, 1, 1);
		this.ShipTypes[3] = new ShipType("Mosquito", 15, 2, 1, 1, 1, 13, 5, 5, 30000, 100, 20, 100, 0, 1, 0, 1, 1);
		this.ShipTypes[4] = new ShipType("Bumblebee", 25, 1, 2, 2, 2, 15, 5, 7, 60000, 125, 15, 100, 1, 1, 0, 1, 2);
		this.ShipTypes[5] = new ShipType("Beetle", 50, 0, 1, 1, 3, 14, 5, 10, 80000, 50, 3, 50, -1, -1, 0, 1, 2);
		this.ShipTypes[6] = new ShipType("Hornet", 20, 3, 2, 1, 2, 16, 6, 15, 100000, 200, 6, 150, 2, 3, 1, 2, 3);
		this.ShipTypes[7] = new ShipType("Grasshopper", 30, 2, 2, 3, 3, 15, 6, 15, 150000, 300, 2, 150, 3, 4, 2, 3, 3);
		this.ShipTypes[8] = new ShipType("Termite", 60, 1, 3, 2, 3, 13, 7, 20, 225000, 300, 2, 200, 4, 5, 3, 4, 4);
		this.ShipTypes[9] = new ShipType("Wasp", 35, 3, 2, 2, 3, 14, 7, 20, 300000, 500, 2, 200, 5, 6, 4, 5, 4);
		// The ships below can't be bought
		this.ShipTypes[10] = new ShipType("Spacemonster", 0, 3, 0, 0, 1, 1, 8, 1, 500000, 0, 0, 500, 8, 8, 8, 1, 4);
		this.ShipTypes[11] = new ShipType("Dragonfly", 0, 2, 3, 2, 1, 1, 8, 1, 500000, 0, 0, 10, 8, 8, 8, 1, 1);
		this.ShipTypes[12] = new ShipType("Mantis", 0, 3, 1, 3, 3, 1, 8, 1, 500000, 0, 0, 300, 8, 8, 8, 1, 2);
		this.ShipTypes[13] = new ShipType("Scarab", 20, 2, 0, 0, 2, 1, 8, 1, 500000, 0, 0, 400, 8, 8, 8, 1, 3);
		this.ShipTypes[14] = new ShipType("Bottle", 0, 0, 0, 0, 0, 1, 8, 1, 100, 0, 0, 10, 8, 8, 8, 1, 1);
	}

	public class ShipType {
		String name;
		public int cargoBays;   // Number of cargo bays
		int weaponSlots; // Number of lasers possible
		int shieldSlots; // Number of shields possible
		int gadgetSlots; // Number of gadgets possible (e.g. docking computers)
		public int crewQuarters;  // Number of crewmembers possible
		public int fuelTanks;   // Each tank contains enough fuel to travel 10 parsecs
		int minTechLevel;  // Minimum tech level needed to build ship
		public int costOfFuel;  // Cost to fill one tank with fuel
		public int price;     // Base ship cost
		int bounty;     // Base bounty
		int occurrence;   // Percentage of the ships you meet
		public int hullStrength;  // Hull strength
		int police;     // Encountered as police with at least this strength
		int pirates;    // idem Pirates
		int traders;    // idem Traders
		public int repairCosts; // Repair costs for 1 point of hull strength.
		int size;      // Determines how easy it is to hit this ship

		public ShipType(String name, int cargoBays, int weaponSlots, int shieldSlots, int gadgetSlots, int crewQuarters, int fuelTanks, int minTechLevel, int costOfFuel, int price, int bounty, int occurrence, int hullStrength, int police, int pirates, int traders, int repairCosts, int size) {
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
		}
	}
}
