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

public class Politics {
	final static boolean drugsOK       = true;
	final static boolean drugsNotOK    = false;
	final static boolean firearmsOK    = true;
	final static boolean firearmsNotOK = false;

	public static Politic[] mPolitics = {new Politic("Anarchy", 0, 0, 7, 1, 0, 5, 7, drugsOK,
		firearmsOK, GameState.FOOD), new Politic("Capitalist State", 2, 3, 2, 7, 4, 7, 1, drugsOK,
		firearmsOK, GameState.ORE), new Politic("Communist State", 6, 6, 4, 4, 1, 5, 5, drugsOK,
		firearmsOK, -1), new Politic("Confederacy", 5, 4, 3, 5, 1, 6, 3, drugsOK, firearmsOK,
		GameState.GAMES), new Politic("Corporate State", 2, 6, 2, 7, 4, 7, 2, drugsOK, firearmsOK,
		GameState.ROBOTS), new Politic("Cybernetic State", 0, 7, 7, 5, 6, 7, 0, drugsNotOK,
		firearmsNotOK, GameState.ORE), new Politic("Democracy", 4, 3, 2, 5, 3, 7, 2, drugsOK,
		firearmsOK, GameState.GAMES), new Politic("Dictatorship", 3, 4, 5, 3, 0, 7, 2, drugsOK,
		firearmsOK, -1), new Politic("Fascist State", 7, 7, 7, 1, 4, 7, 0, drugsNotOK, firearmsOK,
		GameState.MACHINERY), new Politic("Feudal State", 1, 1, 6, 2, 0, 3, 6, drugsOK, firearmsOK,
		GameState.FIREARMS), new Politic("Military State", 7, 7, 0, 6, 2, 7, 0, drugsNotOK, firearmsOK,
		GameState.ROBOTS), new Politic("Monarchy", 3, 4, 3, 4, 0, 5, 4, drugsOK, firearmsOK,
		GameState.MEDICINE), new Politic("Pacifist State", 7, 2, 1, 5, 0, 3, 1, drugsOK, firearmsNotOK,
		-1), new Politic("Socialist State", 4, 2, 5, 3, 0, 5, 6, drugsOK, firearmsOK, -1), new Politic(
		"State of Satori", 0, 1, 1, 1, 0, 1, 0, drugsNotOK, firearmsNotOK, -1), new Politic(
		"Technocracy", 1, 6, 3, 6, 4, 7, 2, drugsOK, firearmsOK, GameState.WATER), new Politic(
		"Theocracy", 5, 6, 1, 4, 0, 4, 0, drugsOK, firearmsOK, GameState.NARCOTICS)};

	public static class Politic {
		public String  name;
		public int     reactionIllegal;
		// Reaction level of illegal goods 0 = total acceptance (determines how police reacts if they find you carry them)
		public int     strengthPolice;
		// Strength level of police force 0 = no police (determines occurrence rate)
		public int     strengthPirates; // Strength level of pirates 0 = no pirates
		public int     strengthTraders; // Strength levcel of traders 0 = no traders
		public int     minTechLevel;    // Mininum tech level needed
		public int     maxTechLevel;    // Maximum tech level where this is found
		public int     bribeLevel;
		// Indicates how easily someone can be bribed 0 = unbribeable/high bribe costs
		public boolean drugsOK;
		// Drugs can be traded (if not, people aren't interested or the governemnt is too strict)
		public boolean firearmsOK;
		// Firearms can be traded (if not, people aren't interested or the governemnt is too strict)
		public int     wanted;          // Tradeitem requested in particular in this type of government

		public Politic(String name, int reactionIllegal, int strengthPolice, int strengthPirates, int strengthTraders, int minTechLevel, int maxTechLevel, int bribeLevel, boolean drugsOK, boolean girearmsOK, int wanted) {
			this.name = name;
			this.reactionIllegal = reactionIllegal;
			this.strengthPolice = strengthPolice;
			this.strengthPirates = strengthPirates;
			this.strengthTraders = strengthTraders;
			this.minTechLevel = minTechLevel;
			this.maxTechLevel = maxTechLevel;
			this.bribeLevel = bribeLevel;
			this.drugsOK = drugsOK;
			this.firearmsOK = girearmsOK;
			this.wanted = wanted;
		}
	}
}
