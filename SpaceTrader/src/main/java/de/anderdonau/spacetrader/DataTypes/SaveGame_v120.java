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
import de.anderdonau.spacetrader.Main;

public class SaveGame_v120 implements Serializable {
	private final static long                 serialVersionUID = 2L;
	public               SG_v101_CrewMember[] Mercenary        =
		new SG_v101_CrewMember[GameState.MAXCREWMEMBER + 1];
	public SG_v101_Ship Opponent;
	public SG_v101_Ship Scarab;
	public SG_v101_Ship Dragonfly;
	public SG_v101_Ship SpaceMonster;
	public SG_v101_Ship Ship;
	public SG_v101_SolarSystem[] SolarSystem = new SG_v101_SolarSystem[GameState.MAXSOLARSYSTEM];
	public String  NameCommander;
	public boolean AlreadyPaidForNewspaper;
	public boolean AlwaysIgnorePirates;
	public boolean AlwaysIgnorePolice;
	public boolean AlwaysIgnoreTradeInOrbit;
	public boolean AlwaysIgnoreTraders;
	public boolean AlwaysInfo;
	public boolean ArrivedViaWormhole;
	public boolean ArtifactOnBoard;
	public boolean AttackFleeing;
	public boolean AutoFuel;
	public boolean AutoRepair;
	public boolean CanSuperWarp;
	public boolean Continuous;
	public boolean EscapePod;
	public boolean GameLoaded;
	public boolean IdentifyStartup;
	public boolean Inspected;
	public boolean Insurance;
	public boolean JustLootedMarie;
	public boolean LitterWarning;
	public boolean MoonBought;
	public boolean NewsAutoPay;
	public boolean PriceDifferences;
	public boolean Raided;
	public boolean RemindLoans;
	public boolean ReserveMoney;
	public boolean SaveOnArrival;
	public boolean SharePreferences;
	public boolean ShowTrackedRange;
	public boolean TextualEncounters;
	public boolean TrackAutoOff;
	public boolean TribbleMessage;
	public boolean BetterGfx;
	public int     Credits;
	public int     Debt;
	public int     MonsterHull;
	public int     PirateKills;
	public int     PoliceKills;
	public int     PoliceRecordScore;
	public int     ReputationScore;
	public int     TraderKills;
	public int[] BuyPrice    = new int[GameState.MAXTRADEITEM];
	public int[] BuyingPrice = new int[GameState.MAXTRADEITEM];
	public int[] SellPrice   = new int[GameState.MAXTRADEITEM];
	public int[] ShipPrice   = new int[GameState.MAXSHIPTYPE];
	public int Clicks;
	public int Days;
	public int DragonflyStatus;
	public int EncounterType;
	public int ExperimentStatus;
	public int FabricRipProbability;
	public int InvasionStatus;
	public int JaporiDiseaseStatus;
	public int JarekStatus;
	public int LeaveEmpty;
	public int MonsterStatus;
	public int NoClaim;
	public int ReactorStatus;
	public int SelectedShipType;
	public int Shortcut1;
	public int Shortcut2;
	public int Shortcut3;
	public int Shortcut4;
	public int TrackedSystem;
	public int VeryRareEncounter;
	public int WarpSystem;
	public int WildStatus;
	public int[] Wormhole = new int[GameState.MAXWORMHOLE];
	public int            Difficulty;
	public int            ScarabStatus;
	public Main.FRAGMENTS currentState;

	public SaveGame_v120(GameState g) {
		int i;
		for (i = 0; i < GameState.MAXCREWMEMBER; i++) {
			this.Mercenary[i] = new SG_v101_CrewMember(g.Mercenary[i].nameIndex, g.Mercenary[i].pilot,
				g.Mercenary[i].fighter, g.Mercenary[i].trader, g.Mercenary[i].engineer,
				g.Mercenary[i].curSystem);
		}
		this.Opponent = new SG_v101_Ship(g.Opponent.type, g.Opponent.cargo.clone(),
			g.Opponent.weapon.clone(), g.Opponent.shield.clone(), g.Opponent.shieldStrength.clone(),
			g.Opponent.gadget.clone(), g.Opponent.crew.clone(), g.Opponent.fuel, g.Opponent.hull,
			g.Opponent.tribbles);
		this.Scarab = new SG_v101_Ship(g.Scarab.type, g.Scarab.cargo.clone(), g.Scarab.weapon.clone(),
			g.Scarab.shield.clone(), g.Scarab.shieldStrength.clone(), g.Scarab.gadget.clone(),
			g.Scarab.crew.clone(), g.Scarab.fuel, g.Scarab.hull, g.Scarab.tribbles);
		this.Dragonfly = new SG_v101_Ship(g.Ship.type, g.Ship.cargo.clone(), g.Ship.weapon.clone(),
			g.Ship.shield.clone(), g.Ship.shieldStrength.clone(), g.Ship.gadget.clone(),
			g.Ship.crew.clone(), g.Ship.fuel, g.Ship.hull, g.Ship.tribbles);
		this.SpaceMonster = new SG_v101_Ship(g.SpaceMonster.type, g.SpaceMonster.cargo.clone(),
			g.SpaceMonster.weapon.clone(), g.SpaceMonster.shield.clone(),
			g.SpaceMonster.shieldStrength.clone(), g.SpaceMonster.gadget.clone(),
			g.SpaceMonster.crew.clone(), g.SpaceMonster.fuel, g.SpaceMonster.hull,
			g.SpaceMonster.tribbles);
		this.Ship = new SG_v101_Ship(g.Ship.type, g.Ship.cargo.clone(), g.Ship.weapon.clone(),
			g.Ship.shield.clone(), g.Ship.shieldStrength.clone(), g.Ship.gadget.clone(),
			g.Ship.crew.clone(), g.Ship.fuel, g.Ship.hull, g.Ship.tribbles);
		for (i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			this.SolarSystem[i] = new SG_v101_SolarSystem(g.SolarSystem[i].nameIndex,
				g.SolarSystem[i].techLevel, g.SolarSystem[i].politics, g.SolarSystem[i].status,
				g.SolarSystem[i].x, g.SolarSystem[i].y, g.SolarSystem[i].specialResources,
				g.SolarSystem[i].size, g.SolarSystem[i].qty.clone(), g.SolarSystem[i].countDown,
				g.SolarSystem[i].visited, g.SolarSystem[i].special);
		}
		this.NameCommander = g.NameCommander;
		this.AlreadyPaidForNewspaper = g.AlreadyPaidForNewspaper;
		this.AlwaysIgnorePirates = g.AlwaysIgnorePirates;
		this.AlwaysIgnorePolice = g.AlwaysIgnorePolice;
		this.AlwaysIgnoreTradeInOrbit = g.AlwaysIgnoreTradeInOrbit;
		this.AlwaysIgnoreTraders = g.AlwaysIgnoreTraders;
		this.AlwaysInfo = g.AlwaysInfo;
		this.ArrivedViaWormhole = g.ArrivedViaWormhole;
		this.ArtifactOnBoard = g.ArtifactOnBoard;
		this.AttackFleeing = g.AttackFleeing;
		this.AutoFuel = g.AutoFuel;
		this.AutoRepair = g.AutoRepair;
		this.CanSuperWarp = g.CanSuperWarp;
		this.Continuous = g.Continuous;
		this.EscapePod = g.EscapePod;
		this.GameLoaded = g.GameLoaded;
		this.IdentifyStartup = g.IdentifyStartup;
		this.Inspected = g.Inspected;
		this.Insurance = g.Insurance;
		this.JustLootedMarie = g.JustLootedMarie;
		this.LitterWarning = g.LitterWarning;
		this.MoonBought = g.MoonBought;
		this.NewsAutoPay = g.NewsAutoPay;
		this.PriceDifferences = g.PriceDifferences;
		this.Raided = g.Raided;
		this.RemindLoans = g.RemindLoans;
		this.ReserveMoney = g.ReserveMoney;
		this.SaveOnArrival = g.SaveOnArrival;
		this.SharePreferences = g.SharePreferences;
		this.ShowTrackedRange = g.ShowTrackedRange;
		this.TextualEncounters = g.TextualEncounters;
		this.TrackAutoOff = g.TrackAutoOff;
		this.TribbleMessage = g.TribbleMessage;
		this.BetterGfx = g.BetterGfx;
		this.Credits = g.Credits;
		this.Debt = g.Debt;
		this.MonsterHull = g.MonsterHull;
		this.PirateKills = g.PirateKills;
		this.PoliceKills = g.PoliceKills;
		this.PoliceRecordScore = g.PoliceRecordScore;
		this.ReputationScore = g.ReputationScore;
		this.TraderKills = g.TraderKills;

		this.Clicks = g.Clicks;
		this.Days = g.Days;
		this.DragonflyStatus = g.DragonflyStatus;
		this.EncounterType = g.EncounterType;
		this.ExperimentStatus = g.ExperimentStatus;
		this.FabricRipProbability = g.FabricRipProbability;
		this.InvasionStatus = g.InvasionStatus;
		this.JaporiDiseaseStatus = g.JaporiDiseaseStatus;
		this.JarekStatus = g.JarekStatus;
		this.LeaveEmpty = g.LeaveEmpty;
		this.MonsterStatus = g.MonsterStatus;
		this.NoClaim = g.NoClaim;
		this.ReactorStatus = g.ReactorStatus;
		this.ScarabStatus = g.ScarabStatus;
		this.SelectedShipType = g.SelectedShipType;
		this.Shortcut1 = g.Shortcut1;
		this.Shortcut2 = g.Shortcut2;
		this.Shortcut3 = g.Shortcut3;
		this.Shortcut4 = g.Shortcut4;
		this.TrackedSystem = g.TrackedSystem;
		this.VeryRareEncounter = g.VeryRareEncounter;
		this.WarpSystem = g.WarpSystem;
		this.WildStatus = g.WildStatus;
		this.Difficulty = GameState.getDifficulty();
		this.currentState = g.currentState;

		for (i = 0; i < GameState.MAXWORMHOLE; i++) {
			this.Wormhole[i] = g.Wormhole[i];
		}
		for (i = 0; i < GameState.MAXTRADEITEM; i++) {
			this.BuyPrice[i] = g.BuyPrice[i];
			this.BuyingPrice[i] = g.BuyingPrice[i];
			this.SellPrice[i] = g.SellPrice[i];
		}
		for (i = 0; i < GameState.MAXSHIPTYPE; i++) {
			this.ShipPrice[i] = g.ShipPrice[i];
		}
	}

	public class SG_v101_CrewMember implements Serializable {
		public int nameIndex;
		public int pilot;
		public int fighter;
		public int trader;
		public int engineer;
		public int curSystem;

		private SG_v101_CrewMember(int nameIndex, int pilot, int fighter, int trader, int engineer, int curSystem) {
			this.nameIndex = nameIndex;
			this.pilot = pilot;
			this.fighter = fighter;
			this.trader = trader;
			this.engineer = engineer;
			this.curSystem = curSystem;
		}
	}

	public class SG_v101_Ship implements Serializable {
		public int type;
		public int[] cargo          = new int[GameState.MAXTRADEITEM];
		public int[] weapon         = new int[GameState.MAXWEAPON];
		public int[] shield         = new int[GameState.MAXSHIELD];
		public int[] shieldStrength = new int[GameState.MAXSHIELD];
		public int[] gadget         = new int[GameState.MAXGADGET];
		public int[] crew           = new int[GameState.MAXCREW];
		public int fuel;
		public int hull;
		public int tribbles;

		private SG_v101_Ship(int type, int[] cargo, int[] weapon, int[] shield, int[] shieldStrength, int[] gadget, int[] crew, int fuel, int hull, int tribbles) {
			this.type = type;
			this.cargo = cargo;
			this.weapon = weapon;
			this.shield = shield;
			this.shieldStrength = shieldStrength;
			this.gadget = gadget;
			this.crew = crew;
			this.fuel = fuel;
			this.hull = hull;
			this.tribbles = tribbles;
		}
	}

	public class SG_v101_SolarSystem implements Serializable {
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

		private SG_v101_SolarSystem(int nameIndex, int techLevel, int politics, int status, int x, int y, int specialResources, int size, int[] qty, int countDown, boolean visited, int special) {
			this.nameIndex = nameIndex;
			this.techLevel = techLevel;
			this.politics = politics;
			this.status = status;
			this.x = x;
			this.y = y;
			this.specialResources = specialResources;
			this.size = size;
			this.qty = qty;
			this.countDown = countDown;
			this.visited = visited;
			this.special = special;
		}
	}
}
