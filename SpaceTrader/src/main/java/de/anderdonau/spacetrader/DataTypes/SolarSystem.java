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
	public int[] qty = new int[GameState.MAXTRADEITEM];
	// Quantities of tradeitems. These change very slowly over time.
	public int     countDown;     // Countdown for reset of tradeitems.
	public boolean visited;    // Visited Yes or No
	public int     special;      // Special event
	Random rand = new Random();

	public void initializeTradeitems() {
		int i;

		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			if (((i == GameState.NARCOTICS) && (!Politics.mPolitics[this.politics].drugsOK)) ||
				((i == GameState.FIREARMS) && (!Politics.mPolitics[this.politics].firearmsOK)) ||
				(this.techLevel < Tradeitems.mTradeitems[i].techProduction)) {
				this.qty[i] = 0;
				continue;
			}

			this.qty[i] = ((9 + rand.nextInt(5)) - Math.abs(
				Tradeitems.mTradeitems[i].techTopProduction - this.techLevel)) * (1 + this.size);

			// Because of the enormous profits possible, there shouldn't be too many robots or narcotics available
			if (i == GameState.ROBOTS || i == GameState.NARCOTICS) {
				this.qty[i] =
					((this.qty[i] * (5 - GameState.getDifficulty())) / (6 - GameState.getDifficulty())) + 1;
			}

			if (Tradeitems.mTradeitems[i].cheapResource >= 0) {
				if (this.specialResources == Tradeitems.mTradeitems[i].cheapResource) {
					this.qty[i] = (this.qty[i] * 4) / 3;
				}
			}

			if (Tradeitems.mTradeitems[i].expensiveResource >= 0) {
				if (this.specialResources == Tradeitems.mTradeitems[i].expensiveResource) {
					this.qty[i] = (this.qty[i] * 3) >> 2;
				}
			}

			if (Tradeitems.mTradeitems[i].doublePriceStatus >= 0) {
				if (this.status == Tradeitems.mTradeitems[i].doublePriceStatus) {
					this.qty[i] = this.qty[i] / 5;
				}
			}

			this.qty[i] = this.qty[i] - rand.nextInt(10) + rand.nextInt(10);

			if (this.qty[i] < 0) {
				this.qty[i] = 0;
			}
		}
	}
}
