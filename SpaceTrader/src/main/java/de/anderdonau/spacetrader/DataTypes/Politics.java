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
public class Politics {
	public static Politic[] mPolitics;

	public Politics() {
		mPolitics = new Politic[GameState.MAXPOLITICS];
		mPolitics[0] = new Politic("Anarchy", 0, 0, 7, 1, 0, 5, 7, true, true, GameState.FOOD);
		mPolitics[1] = new Politic("Capitalist State", 2, 3, 2, 7, 4, 7, 1, true, true, GameState.ORE);
		mPolitics[2] = new Politic("Communist State", 6, 6, 4, 4, 1, 5, 5, true, true, -1);
		mPolitics[3] = new Politic("Confederacy", 5, 4, 3, 5, 1, 6, 3, true, true, GameState.GAMES);
		mPolitics[4] = new Politic("Corporate State", 2, 6, 2, 7, 4, 7, 2, true, true, GameState.ROBOTS);
		mPolitics[5] = new Politic("Cybernetic State", 0, 7, 7, 5, 6, 7, 0, false, false, GameState.ORE);
		mPolitics[6] = new Politic("Democracy", 4, 3, 2, 5, 3, 7, 2, true, true, GameState.GAMES);
		mPolitics[7] = new Politic("Dictatorship", 3, 4, 5, 3, 0, 7, 2, true, true, -1);
		mPolitics[8] = new Politic("Fascist State", 7, 7, 7, 1, 4, 7, 0, false, true, GameState.MACHINERY);
		mPolitics[9] = new Politic("Feudal State", 1, 1, 6, 2, 0, 3, 6, true, true, GameState.FIREARMS);
		mPolitics[10] = new Politic("Military State", 7, 7, 0, 6, 2, 7, 0, false, true, GameState.ROBOTS);
		mPolitics[11] = new Politic("Monarchy", 3, 4, 3, 4, 0, 5, 4, true, true, GameState.MEDICINE);
		mPolitics[12] = new Politic("Pacifist State", 7, 2, 1, 5, 0, 3, 1, true, false, -1);
		mPolitics[13] = new Politic("Socialist State", 4, 2, 5, 3, 0, 5, 6, true, true, -1);
		mPolitics[14] = new Politic("State of Satori", 0, 1, 1, 1, 0, 1, 0, false, false, -1);
		mPolitics[15] = new Politic("Technocracy", 1, 6, 3, 6, 4, 7, 2, true, true, GameState.WATER);
		mPolitics[16] = new Politic("Theocracy", 5, 6, 1, 4, 0, 4, 0, true, true, GameState.NARCOTICS);
	}

	public class Politic {
		public String name;
		public int reactionIllegal;   // Reaction level of illegal goods 0 = total acceptance (determines how police reacts if they find you carry them)
		public int strengthPolice;  // Strength level of police force 0 = no police (determines occurrence rate)
		public int strengthPirates; // Strength level of pirates 0 = no pirates
		public int strengthTraders; // Strength levcel of traders 0 = no traders
		public int minTechLevel;      // Mininum tech level needed
		public int maxTechLevel;    // Maximum tech level where this is found
		public int bribeLevel;    // Indicates how easily someone can be bribed 0 = unbribeable/high bribe costs
		public boolean drugsOK;    // Drugs can be traded (if not, people aren't interested or the governemnt is too strict)
		public boolean firearmsOK;   // Firearms can be traded (if not, people aren't interested or the governemnt is too strict)
		public int wanted;       // Tradeitem requested in particular in this type of government

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
