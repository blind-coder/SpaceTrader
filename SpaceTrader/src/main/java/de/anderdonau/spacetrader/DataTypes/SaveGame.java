/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import java.io.Serializable;
import java.util.Random;

import de.anderdonau.spacetrader.GameState;

public class SaveGame implements Serializable {
	public CrewMember[] Mercenary = new CrewMember[GameState.MAXCREWMEMBER + 1];
	public Ship Opponent;
	public Ship Ship;
	public SolarSystem[] SolarSystem = new SolarSystem[GameState.MAXSOLARSYSTEM];
	public String NameCommander;
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
	public int Credits;
	public int Debt;
	public int MonsterHull;
	public int PirateKills;
	public int PoliceKills;
	public int PoliceRecordScore;
	public int ReputationScore;
	public int TraderKills;
	public int[] BuyPrice = new int[GameState.MAXTRADEITEM];
	public int[] BuyingPrice = new int[GameState.MAXTRADEITEM];
	public int[] SellPrice = new int[GameState.MAXTRADEITEM];
	public int[] ShipPrice = new int[GameState.MAXSHIPTYPE];
	public int Clicks;
	public int CurForm;
	public int Days;
	public int DragonflyStatus;
	public int EncounterType;
	public int ExperimentStatus;
	public int FabricRipProbability;
	public int GalacticChartSystem;
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
	public int Difficulty;
	public int ScarabStatus;

	public SaveGame(GameState g) {
		int i;
		for (i=0; i<GameState.MAXCREWMEMBER; i++) {
			this.Mercenary[i] = g.Mercenary[i];
		}
		this.Opponent = g.Opponent;
		this.Ship = g.Ship;
		for (i=0; i<GameState.MAXSOLARSYSTEM; i++){
			this.SolarSystem[i] = g.SolarSystem[i];
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
		this.Credits = g.Credits;
		this.Debt = g.Debt;
		this.MonsterHull = g.MonsterHull;
		this.PirateKills = g.PirateKills;
		this.PoliceKills = g.PoliceKills;
		this.PoliceRecordScore = g.PoliceRecordScore;
		this.ReputationScore = g.ReputationScore;
		this.TraderKills = g.TraderKills;

		this.Clicks = g.Clicks;
		this.CurForm = g.CurForm;
		this.Days = g.Days;
		this.DragonflyStatus = g.DragonflyStatus;
		this.EncounterType = g.EncounterType;
		this.ExperimentStatus = g.ExperimentStatus;
		this.FabricRipProbability = g.FabricRipProbability;
		this.GalacticChartSystem = g.GalacticChartSystem;
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

		for (i=0; i<GameState.MAXWORMHOLE; i++){
			this.Wormhole[i] = g.Wormhole[i];
		}
		for (i=0; i<GameState.MAXTRADEITEM; i++){
			this.BuyPrice[i] = g.BuyPrice[i];
			this.BuyingPrice[i] = g.BuyingPrice[i];
			this.SellPrice[i] = g.SellPrice[i];
		}
		for (i=0; i<GameState.MAXSHIPTYPE; i++){
			this.ShipPrice[i] = g.ShipPrice[i];
		}
	}
}
