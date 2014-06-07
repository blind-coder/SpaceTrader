/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import de.anderdonau.spacetrader.GameState;

public class Reputation {
	public int[]    minScore =
		{GameState.HARMLESSREP, GameState.MOSTLYHARMLESSREP, GameState.POORREP, GameState.AVERAGESCORE,
		 GameState.ABOVEAVERAGESCORE, GameState.COMPETENTREP, GameState.DANGEROUSREP,
		 GameState.DEADLYREP, GameState.ELITESCORE};
	public String[] name     =
		{"Harmless", "Mostly harmless", "Poor", "Average", "Above average", "Competent", "Dangerous",
		 "Deadly", "Elite"};
}
