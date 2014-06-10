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

public class CrewMember implements Serializable {
	public int nameIndex;
	public int pilot;
	public int fighter;
	public int trader;
	public int engineer;
	public int curSystem;

	public CrewMember() {
		nameIndex = 1;
		pilot = 1;
		fighter = 1;
		trader = 1;
		engineer = 1;
		curSystem = -1;
	}

	public CrewMember(int nameIndex, int pilot, int fighter, int trader, int engineer, int curSystem) {
		this.nameIndex = nameIndex;
		this.pilot = pilot;
		this.fighter = fighter;
		this.trader = trader;
		this.engineer = engineer;
		this.curSystem = curSystem;
	}
}
