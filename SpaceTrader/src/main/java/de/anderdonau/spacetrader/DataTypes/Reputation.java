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

public class Reputation {
	public static int[]    minScore =
		{GameState.HARMLESSREP, GameState.MOSTLYHARMLESSREP, GameState.POORREP, GameState.AVERAGESCORE,
			GameState.ABOVEAVERAGESCORE, GameState.COMPETENTREP, GameState.DANGEROUSREP,
			GameState.DEADLYREP, GameState.ELITESCORE};
	public static String[] name     =
		{"Harmless", "Mostly harmless", "Poor", "Average", "Above average", "Competent", "Dangerous",
			"Deadly", "Elite"};
}
