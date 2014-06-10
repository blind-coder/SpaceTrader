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

public class PoliceRecord {
	public static int[] minScore =
		{-100, GameState.PSYCHOPATHSCORE, GameState.VILLAINSCORE, GameState.CRIMINALSCORE,
			GameState.DUBIOUSSCORE, GameState.CLEANSCORE, GameState.LAWFULSCORE, GameState.TRUSTEDSCORE,
			GameState.HELPERSCORE, GameState.HEROSCORE};

	public static String[] name =
		{"Psycho", "Villain", "Crook", "Criminal", "Dubious", "Clean", "Lawful", "Trusted", "Helper",
			"Hero"};
}
