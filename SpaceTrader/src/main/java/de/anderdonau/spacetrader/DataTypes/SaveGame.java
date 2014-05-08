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
	double Credits;
	double Debt;
	int Days;
	int WarpSystem;
	int SelectedShipType;
	double[] BuyPrice = new double[GameState.MAXTRADEITEM];
	double[] SellPrice = new double[GameState.MAXTRADEITEM];
	double[] ShipPrice = new double[GameState.MAXSHIPTYPE];
	int GalacticChartSystem;
	double PoliceKills;
	double TraderKills;
	double PirateKills;
	double PoliceRecordScore;
	double ReputationScore;
	boolean AutoFuel;
	boolean AutoRepair;
	int Clicks;
	int EncounterType;
	boolean Raided;
	int MonsterStatus;
	int DragonflyStatus;
	int JaporiDiseaseStatus;
	boolean MoonBought;
	double MonsterHull;
	String NameCommander;
	int CurForm;
	Ship Ship;
	Ship Opponent;
	CrewMember[] Mercenary = new CrewMember[GameState.MAXCREWMEMBER + 1];
	SolarSystem[] SolarSystem = new SolarSystem[GameState.MAXSOLARSYSTEM];
	boolean EscapePod;
	boolean Insurance;
	int NoClaim;
	boolean Inspected;
	boolean AlwaysIgnoreTraders;
	int[] Wormhole = new int[GameState.MAXWORMHOLE];
	public static int Difficulty;
	int VersionMajor;
	int VersionMinor;
	double[] BuyingPrice = new double[GameState.MAXTRADEITEM];
	boolean ArtifactOnBoard;
	boolean ReserveMoney;
	boolean PriceDifferences;
	boolean APLscreen;
	int LeaveEmpty;
	boolean TribbleMessage;
	boolean AlwaysInfo;
	boolean AlwaysIgnorePolice;
	boolean AlwaysIgnorePirates;
	boolean TextualEncounters;
	int JarekStatus;
	int InvasionStatus;
	boolean Continuous;
	boolean AttackFleeing;
	int ExperimentStatus;
	int WildStatus;
	int FabricRipProbability;
	int VeryRareEncounter;
	int booleanCollection;
	int ReactorStatus;
	int TrackedSystem;
	int ScarabStatus;
	boolean AlwaysIgnoreTradeInOrbit;
	boolean AlreadyPaidForNewspaper;
	boolean GameLoaded;
	boolean NewsAutoPay;
	boolean ShowTrackedRange;
	boolean JustLootedMarie;
	boolean ArrivedViaWormhole;
	boolean TrackAutoOff;
	boolean RemindLoans;
	boolean CanSuperWarp;
	boolean SaveOnArrival;
	int Shortcut1;
	int Shortcut2;
	int Shortcut3;
	int Shortcut4;
	boolean LitterWarning;
	boolean SharePreferences;
	boolean IdentifyStartup;
	boolean RectangularButtonsOn;
	Random rand = new Random();

	public SaveGame(String NameCommander) {
		/*
		int i, j, k, d, x, y;
		Boolean Redo, CloseFound, FreeWormhole;

		if (NameCommander.length() == 0) {
			this.NameCommander = "Shelby";
		} else {
			this.NameCommander = NameCommander;
		}

		// Initialize Galaxy
		i = 0;
		Random rand = new Random();
		while (i < GameState.MAXSOLARSYSTEM) {
			if (i < GameState.MAXWORMHOLE) {
				// Place the first system somewhere in the centre
				this.SolarSystem[i].x = (((GameState.CLOSEDISTANCE >> 1) - GetRandom(GameState.CLOSEDISTANCE)) + ((GameState.GALAXYWIDTH * (1 + 2 * (i % 3))) / 6));
				this.SolarSystem[i].y = (((GameState.CLOSEDISTANCE >> 1) - GetRandom(GameState.CLOSEDISTANCE)) + ((GameState.GALAXYHEIGHT * (i < 3 ? 1 : 3)) / 4));
				Wormhole[i] = i;
			} else {
				SolarSystem[i].x = (1 + GetRandom(GameState.GALAXYWIDTH - 2));
				SolarSystem[i].y = (1 + GetRandom(GameState.GALAXYHEIGHT - 2));
			}

			CloseFound = false;
			Redo = false;
			if (i >= GameState.MAXWORMHOLE) {
				for (j = 0; j < i; ++j) {
					//  Minimum distance between any two systems not to be accepted
					if (SqrDistance(SolarSystem[j], SolarSystem[i]) <= SQR(GameState.MINDISTANCE + 1)) {
						Redo = true;
						break;
					}

					// There should be at least one system which is closeby enough
					if (SqrDistance(SolarSystem[j], SolarSystem[i]) < SQR(GameState.CLOSEDISTANCE))
						CloseFound = true;
				}
			}
			if (Redo)
				continue;
			if ((i >= GameState.MAXWORMHOLE) && !CloseFound)
				continue;

			SolarSystem[i].techLevel = (char) (GetRandom(GameState.MAXTECHLEVEL));
			SolarSystem[i].politics = (char) (GetRandom(GameState.MAXPOLITICS));
			if (GameState.Politics.mPolitics[SolarSystem[i].politics].minTechLevel > SolarSystem[i].techLevel)
				continue;
			if (GameState.Politics.mPolitics[SolarSystem[i].politics].maxTechLevel < SolarSystem[i].techLevel)
				continue;

			if (GetRandom(5) >= 3)
				SolarSystem[i].specialResources = (char) (1 + GetRandom(GameState.MAXRESOURCES - 1));
			else
				SolarSystem[i].specialResources = 0;

			SolarSystem[i].size = (char) (GetRandom(GameState.MAXSIZE));

			if (GetRandom(100) < 15)
				SolarSystem[i].status = 1 + GetRandom(GameState.MAXSTATUS - 1);
			else
				SolarSystem[i].status = GameState.UNEVENTFUL;

			SolarSystem[i].nameIndex = i;
			SolarSystem[i].special = -1;
			SolarSystem[i].countDown = 0;
			SolarSystem[i].visited = false;

			SolarSystem[i].initializeTradeitems();

			++i;
		}

		// Randomize the system locations a bit more, otherwise the systems with the first
		// names in the alphabet are all in the centre
		for (i = 0; i < GameState.MAXSOLARSYSTEM; ++i) {
			d = 0;
			while (d < GameState.MAXWORMHOLE) {
				if (Wormhole[d] == i)
					break;
				++d;
			}
			j = GetRandom(GameState.MAXSOLARSYSTEM);
			if (WormholeExists(j, -1))
				continue;
			x = SolarSystem[i].x;
			y = SolarSystem[i].y;
			SolarSystem[i].x = SolarSystem[j].x;
			SolarSystem[i].y = SolarSystem[j].y;
			SolarSystem[j].x = x;
			SolarSystem[j].y = y;
			if (d < GameState.MAXWORMHOLE)
				Wormhole[d] = j;
		}

		// Randomize wormhole order
		for (i = 0; i < GameState.MAXWORMHOLE; ++i) {
			j = GetRandom(GameState.MAXWORMHOLE);
			x = Wormhole[i];
			Wormhole[i] = Wormhole[j];
			Wormhole[j] = x;
		}

		// Initialize mercenary list
		Mercenary[0].nameIndex = 0;
		Mercenary[0].pilot = 1;
		Mercenary[0].fighter = 1;
		Mercenary[0].trader = 1;
		Mercenary[0].engineer = 1;

		i = 1;
		while (i <= GameState.MAXCREWMEMBER) {
			Mercenary[i].curSystem = GetRandom(GameState.MAXSOLARSYSTEM);

			Redo = false;
			for (j = 1; j < i; ++j) {
				// Not more than one mercenary per system
				if (Mercenary[j].curSystem == Mercenary[i].curSystem) {
					Redo = true;
					break;
				}
			}
			// can't have another mercenary on Kravat, since Zeethibal could be there
			if (Mercenary[i].curSystem == GameState.KRAVATSYSTEM)
				Redo = true;
			if (Redo)
				continue;

			Mercenary[i].nameIndex = i;
			Mercenary[i].pilot = RandomSkill();
			Mercenary[i].fighter = RandomSkill();
			Mercenary[i].trader = RandomSkill();
			Mercenary[i].engineer = RandomSkill();

			++i;
		}

		// special individuals: Zeethibal, Jonathan Wild's Nephew
		Mercenary[GameState.MAXCREWMEMBER - 1].curSystem = 255;

		// Place special events
		SolarSystem[GameState.ACAMARSYSTEM].special = GameState.MONSTERKILLED;
		SolarSystem[GameState.BARATASSYSTEM].special = GameState.FLYBARATAS;
		SolarSystem[GameState.MELINASYSTEM].special = GameState.FLYMELINA;
		SolarSystem[GameState.REGULASSYSTEM].special = GameState.FLYREGULAS;
		SolarSystem[GameState.ZALKONSYSTEM].special = GameState.DRAGONFLYDESTROYED;
		SolarSystem[GameState.JAPORISYSTEM].special = GameState.MEDICINEDELIVERY;
		SolarSystem[GameState.UTOPIASYSTEM].special = GameState.MOONBOUGHT;
		SolarSystem[GameState.DEVIDIASYSTEM].special = GameState.JAREKGETSOUT;
		SolarSystem[GameState.KRAVATSYSTEM].special = GameState.WILDGETSOUT;

		// Assign a wormhole location endpoint for the Scarab.
		// It's possible that ALL wormhole destinations are already
		// taken. In that case, we don't offer the Scarab quest.
		FreeWormhole = false;
		k = 0;
		j = GetRandom(GameState.MAXWORMHOLE);
		while (SolarSystem[Wormhole[j]].special != -1 &&
						       Wormhole[j] != GameState.GEMULONSYSTEM && Wormhole[j] != GameState.DALEDSYSTEM && Wormhole[j] != GameState.NIXSYSTEM && k < 20) {
			j = GetRandom(GameState.MAXWORMHOLE);
			k++;
		}
		if (k < 20) {
			FreeWormhole = true;
			SolarSystem[Wormhole[j]].special = GameState.SCARABDESTROYED;
		}

		d = 999;
		k = -1;
		for (i = 0; i < GameState.MAXSOLARSYSTEM; ++i) {
			j = RealDistance(SolarSystem[GameState.NIXSYSTEM], SolarSystem[i]);
			if (j >= 70 && j < d && SolarSystem[i].special < 0 &&
							    d != GameState.GEMULONSYSTEM && d != GameState.DALEDSYSTEM) {
				k = i;
				d = j;
			}
		}
		if (k >= 0) {
			SolarSystem[k].special = GameState.GETREACTOR;
			SolarSystem[GameState.NIXSYSTEM].Special = GameState.REACTORDELIVERED;
		}


		i = 0;
		while (i < GameState.MAXSOLARSYSTEM) {
			d = 1 + (GetRandom(GameState.MAXSOLARSYSTEM - 1));
			if (SolarSystem[d].special < 0 && SolarSystem[d].techLevel >= GameState.MAXTECHLEVEL - 1 &&
							    d != GameState.GEMULONSYSTEM && d != GameState.DALEDSYSTEM) {
				SolarSystem[d].special = GameState.ARTIFACTDELIVERY;
				break;
			}
			++i;
		}
		if (i >= GameState.MAXSOLARSYSTEM)
			SpecialEvent[GameState.ALIENARTIFACT].Occurrence = 0;


		d = 999;
		k = -1;
		for (i = 0; i < GameState.MAXSOLARSYSTEM; ++i) {
			j = RealDistance(SolarSystem[GameState.GEMULONSYSTEM], SolarSystem[i]);
			if (j >= 70 && j < d && SolarSystem[i].special < 0 &&
							    k != GameState.DALEDSYSTEM && k != GameState.GEMULONSYSTEM) {
				k = i;
				d = j;
			}
		}
		if (k >= 0) {
			SolarSystem[k].special = GameState.ALIENINVASION;
			SolarSystem[GameState.GEMULONSYSTEM].Special = GameState.GEMULONRESCUED;
		}

		d = 999;
		k = -1;
		for (i = 0; i < GameState.MAXSOLARSYSTEM; ++i) {
			j = RealDistance(SolarSystem[GameState.DALEDSYSTEM], SolarSystem[i]);
			if (j >= 70 && j < d && SolarSystem[i].special < 0) {
				k = i;
				d = j;
			}
		}
		if (k >= 0) {
			SolarSystem[k].special = GameState.EXPERIMENT;
			SolarSystem[GameState.DALEDSYSTEM].special = GameState.EXPERIMENTSTOPPED;
		}


		for (i = GameState.MOONFORSALE; i < GameState.MAXSPECIALEVENT - GameState.ENDFIXED; ++i) {
			for (j = 0; j < SpecialEvent[i].Occurrence; ++j) {
				Redo = true;
				while (Redo) {
					d = 1 + GetRandom(GameState.MAXSOLARSYSTEM - 1);
					if (SolarSystem[d].special < 0) {
						if (FreeWormhole || i != GameState.SCARAB)
							SolarSystem[d].special = i;
						Redo = false;
					}
				}
			}
		}

		// Initialize Commander
		for (i = 0; i < 200; ++i) {
			COMMANDER.CurSystem = GetRandom(GameState.MAXSOLARSYSTEM);
			if (CURSYSTEM.Special >= 0)
				continue;

			// Seek at least an agricultural planet as startplanet (but not too hi-tech)
			if ((i < 100) && ((CURSYSTEM.TechLevel <= 0) || (CURSYSTEM.TechLevel >= 6)))
				continue;

			// Make sure at least three other systems can be reached
			d = 0;
			for (j = 0; j < GameState.MAXSOLARSYSTEM; ++j) {
				if (j == COMMANDER.CurSystem)
					continue;
				if (SqrDistance(SolarSystem[j], CURSYSTEM) <= SQR(Shiptype[1].FuelTanks)) {
					++d;
					if (d >= 3)
						break;
				}
			}
			if (d < 3)
				continue;

			break;
		}

		Credits = 1000;
		Debt = 0;
		Days = 0;
		WarpSystem = COMMANDER.CurSystem;
		PoliceKills = 0;
		TraderKills = 0;
		PirateKills = 0;
		PoliceRecordScore = 0;
		ReputationScore = 0;
		MonsterStatus = 0;
		DragonflyStatus = 0;
		ScarabStatus = 0;
		JaporiDiseaseStatus = 0;
		MoonBought = false;
		MonsterHull = Shiptype[SpaceMonster.Type].HullStrength;
		EscapePod = false;
		Insurance = false;
		RemindLoans = true;
		NoClaim = 0;
		ArtifactOnBoard = false;
		for (i = 0; i < GameState.MAXTRADEITEM; ++i)
			BuyingPrice[i] = 0;
		TribbleMessage = false;
		JarekStatus = 0;
		InvasionStatus = 0;
		ExperimentStatus = 0;
		FabricRipProbability = 0;
		PossibleToGoThroughRip = false;
		ArrivedViaWormhole = false;
		VeryRareEncounter = 0;
		resetNewsEvents();
		WildStatus = 0;
		ReactorStatus = 0;
		TrackedSystem = -1;
		ShowTrackedRange = true;
		JustLootedMarie = false;
		ChanceOfVeryRareEncounter = GameState.CHANCEOFVERYRAREENCOUNTER;
		AlreadyPaidForNewspaper = false;
		CanSuperWarp = false;
		GameLoaded = false;

		// Initialize Ship
		Ship.type = 1;
		for (i = 0; i < GameState.MAXTRADEITEM; ++i)
			Ship.cargo[i] = 0;
		Ship.weapon[0] = 0;
		for (i = 1; i < GameState.MAXWEAPON; ++i)
			Ship.weapon[i] = -1;
		for (i = 0; i < GameState.MAXSHIELD; ++i) {
			Ship.shield[i] = -1;
			Ship.shieldStrength[i] = 0;
		}
		for (i = 0; i < GameState.MAXGADGET; ++i)
			Ship.gadget[i] = -1;
		Ship.crew[0] = 0;
		for (i = 1; i < GameState.MAXCREW; ++i)
			Ship.crew[i] = -1;
		Ship.fuel = GetFuelTanks();
		Ship.hull = Shiptype[Ship.type].HullStrength;
		Ship.tribbles = 0;

		SkillPointsLeft = 16;
		*/
	}

	public int GetRandom(int a) {
		return (rand.nextInt() % (a));
	}

	public int SQR(int a) {
		return (a * a);
	}

	public double SqrDistance(SolarSystem a, SolarSystem b) {
		return (SQR(a.x - b.x) + SQR(a.y - b.y));
	}
}
