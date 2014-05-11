/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import de.anderdonau.spacetrader.GameState;

/**
 * Created by blindcoder on 4/24/14.
 */
public class Gadgets {
	public Gadget[] mGadgets;

	public Gadgets() {
		mGadgets = new Gadget[GameState.MAXGADGETTYPE + GameState.EXTRAGADGETS];
		mGadgets[0] = new Gadget("5 extra cargo bays", 2500, 4, 35); // 5 extra holds
		mGadgets[1] = new Gadget("Auto-repair system", 7500, 5, 20); // Increases engineer's effectivity
		mGadgets[2] = new Gadget("Navigating system", 15000, 6, 20); // Increases pilot's effectivity
		mGadgets[3] = new Gadget("Targeting system", 25000, 6, 20); // Increases fighter's effectivity
		mGadgets[4] = new Gadget("Cloaking device", 100000, 7, 5); // If you have a good engineer, nor pirates nor police will notice you
		// The gadgets below can't be bought
		mGadgets[5] = new Gadget("Fuel compactor", 30000, 8, 0);
	}

	public class Gadget {
		public String name;
		public int price;
		int techLevel;
		int chance; // Chance that this is fitted in a slot
		public Gadget(String name, int price, int techLevel, int chance) {
			this.name = name;
			this.price = price;
			this.techLevel = techLevel;
			this.chance = chance;
		}

	}
}
