/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import java.io.Serializable;
import java.util.Random;

import de.anderdonau.spacetrader.GameState;

public class SolarSystem implements Serializable {
	public int nameIndex;
	public int techLevel;     // Tech level
	public int politics;      // Political system
	public int status;      // Status
	public int x;         // X-coordinate (galaxy width = 150)
	public int y;         // Y-coordinate (galaxy height = 100)
	public int specialResources;  // Special resources
	public int size;        // System size
	public int[] qty = new int[GameState.MAXTRADEITEM];  // Quantities of tradeitems. These change very slowly over time.
	public int countDown;     // Countdown for reset of tradeitems.
	public boolean visited;    // Visited Yes or No
	public int special;      // Special event
	Random rand = new Random();

	public SolarSystem() {
	}

	public void initializeTradeitems() {
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			if (((i == GameState.NARCOTICS) && (!Politics.mPolitics[this.politics].drugsOK)) ||
							    ((i == GameState.FIREARMS) && (!Politics.mPolitics[this.politics].firearmsOK)) ||
							    (this.techLevel < GameState.Tradeitems.mTradeitems[i].techProduction)) {
				this.qty[i] = 0;
				continue;
			}

			this.qty[i] = ((9 + rand.nextInt(5)) - Math.abs(GameState.Tradeitems.mTradeitems[i].techTopProduction - this.techLevel)) * (1 + this.size);

			// Because of the enormous profits possible, there shouldn't be too many robots or narcotics available
			if (i == GameState.ROBOTS || i == GameState.NARCOTICS)
				this.qty[i] = ((this.qty[i] * (5 - GameState.getDifficulty())) / (6 - GameState.getDifficulty())) + 1;

			if (GameState.Tradeitems.mTradeitems[i].cheapResource >= 0)
				if (this.specialResources == GameState.Tradeitems.mTradeitems[i].cheapResource)
					this.qty[i] = (this.qty[i] * 4) / 3;

			if (GameState.Tradeitems.mTradeitems[i].expensiveResource >= 0)
				if (this.specialResources == GameState.Tradeitems.mTradeitems[i].expensiveResource)
					this.qty[i] = (this.qty[i] * 3) >> 2;

			if (GameState.Tradeitems.mTradeitems[i].doublePriceStatus >= 0)
				if (this.status == GameState.Tradeitems.mTradeitems[i].doublePriceStatus)
					this.qty[i] = this.qty[i] / 5;

			this.qty[i] = this.qty[i] - rand.nextInt(10) + rand.nextInt(10);

			if (this.qty[i] < 0)
				this.qty[i] = 0;
		}
	}
}
