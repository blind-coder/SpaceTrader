/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
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
