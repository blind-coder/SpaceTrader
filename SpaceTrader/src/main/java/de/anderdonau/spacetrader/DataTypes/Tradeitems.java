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

public class Tradeitems {
	public static Tradeitem[] mTradeitems = {
		new Tradeitem("Water", 0, 0, 2, 30, +3, 4, GameState.DROUGHT,GameState.LOTSOFWATER, GameState.DESERT, 30, 50, 1),
		new Tradeitem("Furs", 0, 0, 0, 250, +10, 10, GameState.COLD,GameState.RICHFAUNA, GameState.LIFELESS, 230, 280, 5),
		new Tradeitem("Food", 1, 0, 1, 100, +5, 5, GameState.CROPFAILURE, GameState.RICHSOIL, GameState.POORSOIL, 90, 160, 5),
		new Tradeitem("Ore", 2, 2, 3, 350, +20, 10, GameState.WAR, GameState.MINERALRICH, GameState.MINERALPOOR, 350, 420, 10),
		new Tradeitem("Games", 3, 1, 6, 250, -10, 5, GameState.BOREDOM, GameState.ARTISTIC, -1, 160, 270, 5),
		new Tradeitem("Firearms", 3, 1, 5, 1250, -75, 100, GameState.WAR, GameState.WARLIKE, -1, 600, 1100, 25),
		new Tradeitem("Medicine", 4, 1, 6, 650, -20, 10, GameState.PLAGUE, GameState.LOTSOFHERBS, -1, 400, 700, 25),
		new Tradeitem("Machines", 4, 3, 5, 900, -30, 5, GameState.LACKOFWORKERS, -1, -1, 600, 800, 25),
		new Tradeitem("Narcotics", 5, 0, 5, 3500, -125, 150, GameState.BOREDOM, GameState.WEIRDMUSHROOMS, -1, 2000, 3000, 50),
		new Tradeitem("Robots", 6, 4, 7, 5000, -150, 100, GameState.LACKOFWORKERS, -1, -1, 3500, 5000, 100)
	};

	public Tradeitems() {}

	public static class Tradeitem {
		public String name;
		public int    techProduction;  // Tech level needed for production
		public int    techUsage;     // Tech level needed to use
		int techTopProduction; // Tech level which produces this item the most
		public int priceLowTech;   // Medium price at lowest tech level
		public int priceInc;     // Price increase per tech level
		public int variance;     // Max percentage above or below calculated price
		public int doublePriceStatus;  // Price increases considerably when this event occurs
		public int cheapResource;    // When this resource is available, this trade item is cheap
		public int expensiveResource;  // When this resource is available, this trade item is expensive
		public int minTradePrice;    // Minimum price to buy/sell in orbit
		public int maxTradePrice;    // Minimum price to buy/sell in orbit
		public int roundOff;     // Roundoff price for trade in orbit

		public Tradeitem(String name, int techProduction, int techUsage, int techTopProduction, int priceLowTech, int priceInc, int variance, int doublePriceStatus, int cheapResource, int expensiveResource, int minTradePrice, int maxTradePrice, int roundOff) {
			this.name = name;
			this.techProduction = techProduction;
			this.techUsage = techUsage;
			this.techTopProduction = techTopProduction;
			this.priceLowTech = priceLowTech;
			this.priceInc = priceInc;
			this.variance = variance;
			this.doublePriceStatus = doublePriceStatus;
			this.cheapResource = cheapResource;
			this.expensiveResource = expensiveResource;
			this.minTradePrice = minTradePrice;
			this.maxTradePrice = maxTradePrice;
			this.roundOff = roundOff;
		}
	}
}