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

import de.anderdonau.spacetrader.GameState;

public class Ship implements Serializable {
	public int type;
	public int[] cargo  = new int[GameState.MAXTRADEITEM];
	public int[] weapon = new int[GameState.MAXWEAPON];
	public int[] shield = new int[GameState.MAXSHIELD];
	public int[] shieldStrength = new int[GameState.MAXSHIELD];
	public int[] gadget = new int[GameState.MAXGADGET];
	public int[] crew   = new int[GameState.MAXCREW];
	public  int       fuel;
	public  int       hull;
	public  int       tribbles;
	private GameState gameState;

	public Ship(int type, int[] cargo, int[] weapon, int[] shield, int[] shieldStrength, int[] gadget, int[] crew, int fuel, int hull, int tribbles, GameState gameState) {
		this.type = type;
		this.cargo = cargo.clone();
		this.weapon = weapon.clone();
		this.shield = shield.clone();
		this.shieldStrength = shieldStrength.clone();
		this.gadget = gadget.clone();
		this.crew = crew.clone();
		this.fuel = fuel;
		this.hull = hull;
		this.tribbles = tribbles;
		this.gameState = gameState;
	}

	public int getRandomTradeableItem(int Operation) {
		// *************************************************************************
		// Returns the index of a trade good that is on a given ship that can be
		// sold in a given system.
		// *************************************************************************
		boolean looping = true;
		int i = 0, j = -1;

		while (looping && i < 10) {
			j = gameState.GetRandom(GameState.MAXTRADEITEM);
			// It's not as ugly as it may look! If the ship has a particular item, the following
			// conditions must be met for it to be tradeable:
			// if the trader is buying, there must be a valid sale price for that good on the local system
			// if the trader is selling, there must be a valid buy price for that good on the local system
			// if the player is criminal, the good must be illegal
			// if the player is not criminal, the good must be legal
			if ((cargo[j] > 0 && Operation == GameState.TRADERSELL && gameState.BuyPrice[j] > 0) && ((gameState.PoliceRecordScore < GameState.DUBIOUSSCORE && (j == GameState.FIREARMS || j == GameState.NARCOTICS)) || (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE && j != GameState.FIREARMS && j != GameState.NARCOTICS))) {
				looping = false;
			} else if ((cargo[j] > 0 && Operation == GameState.TRADERBUY && gameState.SellPrice[j] > 0) && ((gameState.PoliceRecordScore < GameState.DUBIOUSSCORE && (j == GameState.FIREARMS || j == GameState.NARCOTICS)) || (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE && j != GameState.FIREARMS && j != GameState.NARCOTICS))) {
				looping = false;
			}
			// alles klar?
			else {
				j = -1;
				i++;
			}
		}
		// if we didn't succeed in picking randomly, we'll pick sequentially. We can do this, because
		// this routine is only called if there are tradeable goods.
		if (j == -1) {
			j = 0;
			looping = true;
			while (looping) {
				// see lengthy comment above.
				if ((((cargo[j] > 0) && (Operation == GameState.TRADERSELL) && (gameState.BuyPrice[j] > 0)) || ((cargo[j] > 0) && (Operation == GameState.TRADERBUY) && (gameState.SellPrice[j] > 0))) && ((gameState.PoliceRecordScore < GameState.DUBIOUSSCORE && (j == GameState.FIREARMS || j == GameState.NARCOTICS)) || (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE && j != GameState.FIREARMS && j != GameState.NARCOTICS))) {
					looping = false;
				} else {
					j++;
					if (j == GameState.MAXTRADEITEM) {
						// this should never happen!
						looping = false;
					}
				}
			}
		}
		return j;
	}

	public boolean HasTradeableItems(int warpSystem, int Operation) {
		int i;
		Boolean ret = false, thisRet;
		for (i = 0; i < GameState.MAXTRADEITEM; i++) {
			// trade only if trader is selling and the item has a buy price on the
			// local system, or trader is buying, and there is a sell price on the
			// local system.
			thisRet = false;
			gameState.RecalculateBuyPrices(warpSystem);
			if (cargo[i] > 0 && Operation == GameState.TRADERSELL && gameState.BuyPrice[i] > 0) {
				thisRet = true;
			} else if (cargo[i] > 0 && Operation == GameState.TRADERBUY && gameState.SellPrice[i] > 0) {
				thisRet = true;
			}

			// Criminals can only buy or sell illegal goods, Noncriminals cannot buy
			// or sell such items.
			if (gameState.PoliceRecordScore < GameState.DUBIOUSSCORE && i != GameState.FIREARMS && i != GameState.NARCOTICS) {
				thisRet = false;
			} else if (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE && (i == GameState.FIREARMS || i == GameState.NARCOTICS)) {
				thisRet = false;
			}

			if (thisRet) { ret = true; }
		}

		return ret;
	}

	public int TotalShields() {
		// *************************************************************************
		// Calculate total possible shield strength
		// *************************************************************************
		int i;
		int j;

		j = 0;
		for (i = 0; i < GameState.MAXSHIELD; ++i) {
			if (shield[i] < 0) { break; }
			j += Shields.mShields[shield[i]].power;
		}

		return j;
	}

	public int TotalShieldStrength() {
		// *************************************************************************
		// Calculate total shield strength
		// *************************************************************************
		int i;
		int k;

		k = 0;
		for (i = 0; i < GameState.MAXSHIELD; ++i) {
			if (shield[i] < 0) { break; }
			k += shieldStrength[i];
		}

		return k;
	}

	public int TotalWeapons(int minWeapon, int maxWeapon) {
		// *************************************************************************
		// Calculate total possible weapon strength
		// Modified to allow an upper and lower limit on which weapons work.
		// Weapons equal to or between minWeapon and maxWeapon (e.g., PULSELASERWEAPON)
		// will do damage. Use -1 to allow damage from any weapon, which is almost
		// always what you want. SjG
		// *************************************************************************
		int i;
		int j;

		j = 0;
		for (i = 0; i < GameState.MAXWEAPON; ++i) {
			if (weapon[i] < 0) { break; }

			if ((minWeapon != -1 && weapon[i] < minWeapon) || (maxWeapon != -1 && weapon[i] > maxWeapon)) {
				continue;
			}

			j += Weapons.mWeapons[weapon[i]].power;
		}

		return j;
	}

	public boolean isCloakedTo(Ship Opp) {
		return (HasGadget(GameState.CLOAKINGDEVICE) && (EngineerSkill() > Opp.EngineerSkill()));
	}

	public boolean AnyEmptySlots() {
		int j;

		for (j = 0; j < ShipTypes.ShipTypes[type].weaponSlots; ++j) {
			if (weapon[j] < 0) {
				return true;
			}
		}
		for (j = 0; j < ShipTypes.ShipTypes[type].shieldSlots; ++j) {
			if (shield[j] < 0) {
				return true;
			}
		}
		for (j = 0; j < ShipTypes.ShipTypes[type].gadgetSlots; ++j) {
			if (gadget[j] < 0) {
				return true;
			}
		}
		return false;
	}

	public int TotalCargoBays() {
		// *************************************************************************
		// Calculate total cargo bays
		// *************************************************************************
		int Bays;
		int i;

		Bays = ShipTypes.ShipTypes[type].cargoBays;
		for (i = 0; i < GameState.MAXGADGET; ++i) {
			if (gadget[i] == GameState.EXTRABAYS) {
				Bays += 5;
			}
		}
		if (this != gameState.Ship) {
			return Bays;
		}

		if (gameState.JaporiDiseaseStatus == 1) {
			Bays -= 10;
		}
		if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			Bays -= (5 + 10 - (gameState.ReactorStatus - 1) / 2);
		}
		return Bays;
	}

	public int FilledCargoBays() {
		// *************************************************************************
		// Calculate total filled cargo bays
		// *************************************************************************
		int sum, i;

		sum = 0;
		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			sum = sum + cargo[i];
		}

		return sum;
	}

	public int FighterSkill() {
		// *************************************************************************
		// Fighter skill
		// *************************************************************************
		int i;
		int MaxSkill;

		MaxSkill = gameState.Mercenary[crew[0]].fighter;

		for (i = 1; i < GameState.MAXCREW; ++i) {
			if (crew[i] < 0) { break; }
			if (gameState.Mercenary[crew[i]].fighter > MaxSkill) {
				MaxSkill = gameState.Mercenary[crew[i]].fighter;
			}
		}

		if (HasGadget(GameState.TARGETINGSYSTEM)) { MaxSkill += GameState.SKILLBONUS; }

		return gameState.AdaptDifficulty(MaxSkill);
	}

	public int PilotSkill() {
		// *************************************************************************
		// Pilot skill
		// *************************************************************************
		int i;
		int MaxSkill;

		MaxSkill = gameState.Mercenary[crew[0]].pilot;

		for (i = 1; i < GameState.MAXCREW; ++i) {
			if (crew[i] < 0) { break; }
			if (gameState.Mercenary[crew[i]].pilot > MaxSkill) {
				MaxSkill = gameState.Mercenary[crew[i]].pilot;
			}
		}

		if (HasGadget(GameState.NAVIGATINGSYSTEM)) { MaxSkill += GameState.SKILLBONUS; }
		if (HasGadget(GameState.CLOAKINGDEVICE)) { MaxSkill += GameState.CLOAKBONUS; }

		return gameState.AdaptDifficulty(MaxSkill);
	}

	public int TraderSkill() {
		// *************************************************************************
		// Trader skill
		// *************************************************************************
		int i;
		int MaxSkill;

		MaxSkill = gameState.Mercenary[crew[0]].trader;

		for (i = 1; i < GameState.MAXCREW; ++i) {
			if (crew[i] < 0) { break; }
			if (gameState.Mercenary[crew[i]].trader > MaxSkill) {
				MaxSkill = gameState.Mercenary[crew[i]].trader;
			}
		}

		if (gameState.JarekStatus >= 2) { ++MaxSkill; }

		return gameState.AdaptDifficulty(MaxSkill);
	}

	public int EngineerSkill() {
		// *************************************************************************
		// Engineer skill
		// *************************************************************************
		int i;
		int MaxSkill;

		MaxSkill = gameState.Mercenary[crew[0]].engineer;

		for (i = 1; i < GameState.MAXCREW; ++i) {
			if (crew[i] < 0) { break; }
			if (gameState.Mercenary[crew[i]].engineer > MaxSkill) {
				MaxSkill = gameState.Mercenary[crew[i]].engineer;
			}
		}

		if (HasGadget(GameState.AUTOREPAIRSYSTEM)) { MaxSkill += GameState.SKILLBONUS; }

		return gameState.AdaptDifficulty(MaxSkill);
	}

	public int GetHullStrength() {
		if (this == gameState.Ship && gameState.ScarabStatus == 3) {
			return ShipTypes.ShipTypes[type].hullStrength + GameState.UPGRADEDHULL;
		} else {
			return ShipTypes.ShipTypes[type].hullStrength;
		}
	}

	public int GetFuelTanks() {
		// *************************************************************************
		// Determine size of fueltanks
		// *************************************************************************
		return (HasGadget(GameState.FUELCOMPACTOR) ? 18 : ShipTypes.ShipTypes[type].fuelTanks);
	}

	public int GetFuel() {
		return Math.min(fuel, GetFuelTanks());
	}

	public boolean HasGadget(int g) {
		int i;

		for (i = 0; i < GameState.MAXGADGET; ++i) {
			if (gadget[i] < 0) { continue; }
			if (gadget[i] == g) { return true; }
		}

		return false;
	}

	public int HasShield(int g) {
		// *************************************************************************
		// Determines whether a certain shield type is on board
		// *************************************************************************
		int i, retVal;

		retVal = 0;
		for (i = 0; i < GameState.MAXSHIELD; ++i) {
			if (shield[i] < 0) { continue; }
			if (shield[i] == g) { retVal++; }
		}

		return retVal;
	}

	public boolean HasWeapon(int g, boolean exactCompare) {
		// *************************************************************************
		// Determines whether a certain weapon type is on board. If exactCompare is
		// false, then better weapons will also return TRUE
		// *************************************************************************
		int i;

		for (i = 0; i < GameState.MAXWEAPON; ++i) {
			if (weapon[i] < 0) { continue; }
			if ((weapon[i] == g) || (weapon[i] > g && !exactCompare)) { return true; }
		}

		return false;
	}

	public ShipTypes.ShipType getType() {
		return ShipTypes.ShipTypes[type];
	}
}
