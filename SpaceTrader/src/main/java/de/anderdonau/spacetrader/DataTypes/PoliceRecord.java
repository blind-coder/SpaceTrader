/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import de.anderdonau.spacetrader.GameState;

public class PoliceRecord {
	public static int[] minScore =
		{-100, GameState.PSYCHOPATHSCORE, GameState.VILLAINSCORE, GameState.CRIMINALSCORE,
		 GameState.DUBIOUSSCORE, GameState.CLEANSCORE, GameState.LAWFULSCORE, GameState.TRUSTEDSCORE,
		 GameState.HELPERSCORE, GameState.HEROSCORE};

	public static String[] name =
		{"Psycho", "Villain", "Crook", "Criminal", "Dubious", "Clean", "Lawful", "Trusted", "Helper",
		 "Hero"};
}
