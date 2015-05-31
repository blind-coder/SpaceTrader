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

package de.anderdonau.spacetrader;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Random;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.Gadgets;
import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.SaveGame_v110;
import de.anderdonau.spacetrader.DataTypes.SaveGame_v111;
import de.anderdonau.spacetrader.DataTypes.SaveGame_v120;
import de.anderdonau.spacetrader.DataTypes.Shields;
import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;
import de.anderdonau.spacetrader.DataTypes.SpecialEvents;
import de.anderdonau.spacetrader.DataTypes.Tradeitems;
import de.anderdonau.spacetrader.DataTypes.Weapons;

public class GameState implements Serializable {
	public static       boolean       isValid                     = false;
	// Add Ships, Weapons, Shields, and Gadgets that don't show up normally
	public static final int           EXTRAWEAPONS                = 1;
	// Number of weapons over standard
	public static final int           EXTRAGADGETS                = 1;
	// Number of Gadgets over standard
	public static final int           EXTRASHIELDS                = 1;
	// Number of Shields over standard
	// public static final int           EXTRASHIPS                  = 5;
	// Number of Ships over standard
	public static final int           DEFSEEDX                    = 521288629;
	public static final int           DEFSEEDY                    = 362436069;
	public static final int           MAXTRADEITEM                = 10;
	// System status: normally this is uneventful, but sometimes a system has a
	// special event occurring. This influences some prices.
	public static final int           MAXSTATUS                   = 8;
	public static final int           UNEVENTFUL                  = 0;
	public static final int           WAR                         = 1;
	public static final int           PLAGUE                      = 2;
	public static final int           DROUGHT                     = 3;
	public static final int           BOREDOM                     = 4;
	public static final int           COLD                        = 5;
	public static final int           CROPFAILURE                 = 6;
	public static final int           LACKOFWORKERS               = 7;
	// Difficulty levels
	public static final int           BEGINNER                    = 0;
	public static final int           EASY                        = 1;
	public static final int           NORMAL                      = 2;
	public static final int           HARD                        = 3;
	public static final int           IMPOSSIBLE                  = 4;
	// Crewmembers. The commander is always the crewmember with index= 0;
	// Zeethibal is always the last
	public static final int           MAXCREWMEMBER               = 31;
	public static final int           MAXSKILL                    = 10;
	// Skills
	public static final int           PILOTSKILL                  = 0;
	public static final int           FIGHTERSKILL                = 1;
	public static final int           TRADERSKILL                 = 2;
	public static final int           ENGINEERSKILL               = 3;
	public static final int           MAXSKILLTYPE                = 4;
	public static final int           SKILLBONUS                  = 3;
	public static final int           CLOAKBONUS                  = 2;
	// Tradeitems
	public static final int           WATER                       = 0;
	public static final int           FURS                        = 1;
	public static final int           FOOD                        = 2;
	public static final int           ORE                         = 3;
	public static final int           GAMES                       = 4;
	public static final int           FIREARMS                    = 5;
	public static final int           MEDICINE                    = 6;
	public static final int           MACHINERY                   = 7;
	public static final int           NARCOTICS                   = 8;
	public static final int           ROBOTS                      = 9;
	// Ship types
	public static final int           MAXSHIPTYPE                 = 10;
	public static final int           MAXRANGE                    = 20;
	public static final int           MANTISTYPE                  = MAXSHIPTYPE + 2;
	public static final int           SCARABTYPE                  = MAXSHIPTYPE + 3;
	public static final int           BOTTLETYPE                  = MAXSHIPTYPE + 4;
	// Weapons
	public static final int           MAXWEAPONTYPE               = 3;
	public static final int           PULSELASERWEAPON            = 0;
	public static final int           PULSELASERPOWER             = 15;
	public static final int           BEAMLASERWEAPON             = 1;
	public static final int           BEAMLASERPOWER              = 25;
	public static final int           MILITARYLASERWEAPON         = 2;
	public static final int           MILITARYLASERPOWER          = 35;
	public static final int           MORGANLASERWEAPON           = 3;
	public static final int           MORGANLASERPOWER            = 85; // fixme!
	// Shields
	public static final int           MAXSHIELDTYPE               = 2;
	// public static final int           ENERGYSHIELD                = 0;
	public static final int           ESHIELDPOWER                = 100;
	public static final int           REFLECTIVESHIELD            = 1;
	public static final int           RSHIELDPOWER                = 200;
	public static final int           LIGHTNINGSHIELD             = 2;
	public static final int           LSHIELDPOWER                = 350;
	// Hull Upgrade
	public static final int           UPGRADEDHULL                = 50;
	// Gadgets
	public static final int           MAXGADGETTYPE               = 5;
	public static final int           EXTRABAYS                   = 0;
	public static final int           AUTOREPAIRSYSTEM            = 1;
	public static final int           NAVIGATINGSYSTEM            = 2;
	public static final int           TARGETINGSYSTEM             = 3;
	public static final int           CLOAKINGDEVICE              = 4;
	public static final int           FUELCOMPACTOR               = 5; // MAXGADGETTYPE += 1;
	// Police Action
	public static final int           POLICE                      = 0;
	public static final int           POLICEINSPECTION            = 0;
	// Police asks to submit for inspection
	public static final int           POLICEIGNORE                = 1;// Police just ignores you
	public static final int           POLICEATTACK                = 2;
	// Police attacks you (sometimes on sight)
	public static final int           POLICEFLEE                  = 3;// Police is fleeing
	public static final int           MAXPOLICE                   = 9;
	// Pirate Actions
	public static final int           PIRATE                      = 10;
	public static final int           PIRATEATTACK                = 10;// Pirate attacks
	public static final int           PIRATEFLEE                  = 11;// Pirate flees
	public static final int           PIRATEIGNORE                = 12;
	// Pirate ignores you (because of cloak)
	public static final int           PIRATESURRENDER             = 13;// Pirate surrenders
	public static final int           MAXPIRATE                   = 19;
	// Trader Actions
	public static final int           TRADER                      = 20;
	public static final int           TRADERIGNORE                = 20;// Trader passes
	public static final int           TRADERFLEE                  = 21;// Trader flees
	public static final int           TRADERATTACK                = 22;
	// Trader is attacking (after being provoked)
	public static final int           TRADERSURRENDER             = 23;// Trader surrenders
	public static final int           TRADERSELL                  = 24;
	// Trader will sell products in orbit
	public static final int           TRADERBUY                   = 25;
	// Trader will buy products in orbit
	// public static final int           TRADERNOTRADE               = 26;
	// Player has declined to transact with Trader
	public static final int           MAXTRADER                   = 29;
	// Space Monster Actions
	public static final int           SPACEMONSTERATTACK          = 30;
	public static final int           SPACEMONSTERIGNORE          = 31;
	public static final int           MAXSPACEMONSTER             = 39;
	// Dragonfly Actions
	public static final int           DRAGONFLYATTACK             = 40;
	public static final int           DRAGONFLYIGNORE             = 41;
	public static final int           MAXDRAGONFLY                = 49;
	public static final int           MANTIS                      = 50;
	// Scarab Actions
	public static final int           SCARABATTACK                = 60;
	public static final int           SCARABIGNORE                = 61;
	public static final int           MAXSCARAB                   = 69;
	// Famous Captain
	public static final int           FAMOUSCAPTAIN               = 70;
	public static final int           FAMOUSCAPATTACK             = 71;
	public static final int           CAPTAINAHABENCOUNTER        = 72;
	public static final int           CAPTAINCONRADENCOUNTER      = 73;
	public static final int           CAPTAINHUIEENCOUNTER        = 74;
	public static final int           MAXFAMOUSCAPTAIN            = 79;
	// Other Special Encounters
	public static final int           MARIECELESTEENCOUNTER       = 80;
	public static final int           BOTTLEOLDENCOUNTER          = 81;
	public static final int           BOTTLEGOODENCOUNTER         = 82;
	public static final int           POSTMARIEPOLICEENCOUNTER    = 83;
	// The commander's ship
	public static final int           MAXWEAPON                   = 3;
	public static final int           MAXSHIELD                   = 3;
	public static final int           MAXGADGET                   = 3;
	public static final int           MAXCREW                     = 3;
	public static final int           MAXTRIBBLES                 = 100000;
	// Solar systems
	public static final int           MAXSOLARSYSTEM              = 120;
	public static final int           ACAMARSYSTEM                = 0;
	public static final int           BARATASSYSTEM               = 6;
	public static final int           DALEDSYSTEM                 = 17;
	public static final int           DEVIDIASYSTEM               = 22;
	public static final int           GEMULONSYSTEM               = 32;
	public static final int           JAPORISYSTEM                = 41;
	public static final int           KRAVATSYSTEM                = 50;
	public static final int           MELINASYSTEM                = 59;
	public static final int           NIXSYSTEM                   = 67;
	public static final int           OGSYSTEM                    = 70;
	public static final int           REGULASSYSTEM               = 82;
	// public static final int           SOLSYSTEM                   = 92;
	public static final int           UTOPIASYSTEM                = 109;
	public static final int           ZALKONSYSTEM                = 118;
	// Special events
	public static final int           COSTMOON                    = 500000;
	public static final int           MAXSPECIALEVENT             = 37;
	public static final int           ENDFIXED                    = 7;
	public static final int           DRAGONFLYDESTROYED          = 0;
	public static final int           FLYBARATAS                  = 1;
	public static final int           FLYMELINA                   = 2;
	public static final int           FLYREGULAS                  = 3;
	public static final int           MONSTERKILLED               = 4;
	public static final int           MEDICINEDELIVERY            = 5;
	public static final int           MOONBOUGHT                  = 6;
	// ----- fixed locations precede
	public static final int           MOONFORSALE                 = 7;
	public static final int           SKILLINCREASE               = 8;
	public static final int           TRIBBLE                     = 9;
	public static final int           ERASERECORD                 = 10;
	public static final int           BUYTRIBBLE                  = 11;
	public static final int           SPACEMONSTER                = 12;
	public static final int           DRAGONFLY                   = 13;
	public static final int           CARGOFORSALE                = 14;
	public static final int           INSTALLLIGHTNINGSHIELD      = 15;
	public static final int           JAPORIDISEASE               = 16;
	public static final int           LOTTERYWINNER               = 17;
	public static final int           ARTIFACTDELIVERY            = 18;
	public static final int           ALIENARTIFACT               = 19;
	public static final int           AMBASSADORJAREK             = 20;
	public static final int           ALIENINVASION               = 21;
	public static final int           GEMULONINVADED              = 22;
	public static final int           GETFUELCOMPACTOR            = 23;
	public static final int           EXPERIMENT                  = 24;
	public static final int           TRANSPORTWILD               = 25;
	public static final int           GETREACTOR                  = 26;
	public static final int           GETSPECIALLASER             = 27;
	public static final int           SCARAB                      = 28;
	public static final int           GETHULLUPGRADED             = 29;
	// ------ fixed locations follow
	public static final int           SCARABDESTROYED             = 30;
	public static final int           REACTORDELIVERED            = 31;
	public static final int           JAREKGETSOUT                = 32;
	public static final int           GEMULONRESCUED              = 33;
	public static final int           EXPERIMENTSTOPPED           = 34;
	public static final int           EXPERIMENTNOTSTOPPED        = 35;
	public static final int           WILDGETSOUT                 = 36;
	// Max Number of Tribble Buttons
	public static final int           TRIBBLESONSCREEN            = 31;
	// Other special events (Encounters)
	// First is probability in= 1000;that one could happen at all:
	public static final int           CHANCEOFVERYRAREENCOUNTER   = 5;
	public static final int           MAXVERYRAREENCOUNTER        = 6;
	public static final int           MARIECELESTE                = 0;
	public static final int           CAPTAINAHAB                 = 1;
	public static final int           CAPTAINCONRAD               = 2;
	public static final int           CAPTAINHUIE                 = 3;
	public static final int           BOTTLEOLD                   = 4;
	public static final int           BOTTLEGOOD                  = 5;// Already done this encounter?
	public static final int           ALREADYMARIE                = 1;
	public static final int           ALREADYAHAB                 = 2;
	public static final int           ALREADYCONRAD               = 4;
	public static final int           ALREADYHUIE                 = 8;
	public static final int           ALREADYBOTTLEOLD            = 16;
	public static final int           ALREADYBOTTLEGOOD           = 32;
	// Propability in= 1000;that a trader will make offer while in orbit
	public static final int           CHANCEOFTRADEINORBIT        = 100;
	// Political systems (governments)
	public static final int           MAXPOLITICS                 = 17;
	public static final int           ANARCHY                     = 0;
	// Tech levels.
	public static final int           MAXTECHLEVEL                = 8;
	// Cargo Dumping Codes. These identify the operation so we can reuse
	// some of the Sell Cargo code.
	// SELL is obvious, Dump is when in dock, Jettison is when in space.
	public static final int           SELLCARGO                   = 1;
	public static final int           DUMPCARGO                   = 2;
	public static final int           JETTISONCARGO               = 3;
	// System sizes (influences the number of goods available)
	public static final int           MAXSIZE                     = 5;
	// Newspaper Mastheads and Headlines have been moved into String Resources, where
	// they belong. Mastheads starting with codes will have the codes replaced as follows:
	// + -> System Name
	// * -> The System Name
	public static final int           MAXMASTHEADS                = 3;
	// number of newspaper names per Political situation
	public static final int           MAXSTORIES                  = 4;
	// number of canned stories per Political situation
	public static final int           STORYPROBABILITY            = 50 / MAXTECHLEVEL;
	// probability of a story being shown
	public static final int           MAXSPECIALNEWSEVENTS        = 5;
	// maximum number of special news events to keep for a system
	// // News Events that don't exactly match special events
	public static final int           WILDARRESTED                = 90;
	public static final int           CAUGHTLITTERING             = 91;
	public static final int           EXPERIMENTPERFORMED         = 92;
	public static final int           ARRIVALVIASINGULARITY       = 93;
	public static final int           CAPTAINHUIEATTACKED         = 100;
	public static final int           CAPTAINCONRADATTACKED       = 101;
	public static final int           CAPTAINAHABATTACKED         = 102;
	public static final int           CAPTAINHUIEDESTROYED        = 110;
	public static final int           CAPTAINCONRADDESTROYED      = 111;
	public static final int           CAPTAINAHABDESTROYED        = 112;
	// Police record
	public static final int           MAXPOLICERECORD             = 10;
	public static final int           ATTACKPOLICESCORE           = -3;
	public static final int           KILLPOLICESCORE             = -6;
	public static final int           CAUGHTWITHWILDSCORE         = -4;
	public static final int           ATTACKTRADERSCORE           = -2;
	public static final int           PLUNDERTRADERSCORE          = -2;
	public static final int           KILLTRADERSCORE             = -4;
	public static final int           KILLPIRATESCORE             = 1;
	public static final int           PLUNDERPIRATESCORE          = -1;
	public static final int           TRAFFICKING                 = -1;
	public static final int           FLEEFROMINSPECTION          = -2;
	// Police Record Score
	public static final int           PSYCHOPATHSCORE             = -70;
	public static final int           VILLAINSCORE                = -30;
	public static final int           CRIMINALSCORE               = -10;
	public static final int           DUBIOUSSCORE                = -5;
	public static final int           CLEANSCORE                  = 0;
	public static final int           LAWFULSCORE                 = 5;
	public static final int           TRUSTEDSCORE                = 10;
	public static final int           HELPERSCORE                 = 25;
	public static final int           HEROSCORE                   = 75;
	// Reputation (depends on number of kills)
	public static final int           MAXREPUTATION               = 9;
	public static final int           HARMLESSREP                 = 0;
	public static final int           MOSTLYHARMLESSREP           = 10;
	public static final int           POORREP                     = 20;
	public static final int           AVERAGESCORE                = 40;
	public static final int           ABOVEAVERAGESCORE           = 80;
	public static final int           COMPETENTREP                = 150;
	public static final int           DANGEROUSREP                = 300;
	public static final int           DEADLYREP                   = 600;
	public static final int           ELITESCORE                  = 1500;
	// Debt Control
	public static final int           DEBTTOOLARGE                = 100000;
	public static final int           MAXRESOURCES                = 13;
	// public static final int           NOSPECIALRESOURCES          = 0;
	public static final int           MINERALRICH                 = 1;
	public static final int           MINERALPOOR                 = 2;
	public static final int           DESERT                      = 3;
	public static final int           LOTSOFWATER                 = 4;
	public static final int           RICHSOIL                    = 5;
	public static final int           POORSOIL                    = 6;
	public static final int           RICHFAUNA                   = 7;
	public static final int           LIFELESS                    = 8;
	public static final int           WEIRDMUSHROOMS              = 9;
	public static final int           LOTSOFHERBS                 = 10;
	public static final int           ARTISTIC                    = 11;
	public static final int           WARLIKE                     = 12;
	// Wormholes
	public static final int           MAXWORMHOLE                 = 6;
	public static final int           GALAXYWIDTH                 = 150;
	public static final int           GALAXYHEIGHT                = 110;
	public static final int           MINDISTANCE                 = 6;
	public static final int           CLOSEDISTANCE               = 13;
	// Resources. Some systems have special resources, which influences some prices.
	// this is in percentage, and will decrease by one every day.
	public static final int           FABRICRIPINITIALPROBABILITY = 25;
	public static final int           MAXHIGHSCORE                = 3;
	public static final int           KILLED                      = 0;
	public static final int           RETIRED                     = 1;
	public static final int           MOON                        = 2;
	// Array of news events.
	public static       int           CountDown                   = 3;
	// Countdown for reset of tradeitems.
	// Distance from target system,= 0;= arrived
	static              int           Difficulty                  = NORMAL;     // Difficulty level
	public              SolarSystem[] SolarSystem                 = new SolarSystem[MAXSOLARSYSTEM];
	public              int           Credits                     = 1000;// Current credits owned
	public              int           Debt                        = 0;               // Current Debt
	public              int[]         BuyPrice                    = new int[MAXTRADEITEM];
	// Price list current system
	public              int[]         BuyingPrice                 = new int[MAXTRADEITEM];
	// Total price paid for trade goods
	public              int[]         SellPrice                   = new int[MAXTRADEITEM];
	// Price list current system
	public              int[]         ShipPrice                   = new int[MAXSHIPTYPE];
	// Price list current system (recalculate when buy ship screen is entered)
	public              int           PoliceKills                 = 0;// Number of police ships killed
	public              int           TraderKills                 = 0;// Number of trader ships killed
	public              int           PirateKills                 = 0;// Number of pirate ships killed
	public              int           PoliceRecordScore           = 0;     //= 0;= Clean record
	public              int           ReputationScore             = 0;       //= 0;= Harmless
	public              int           MonsterHull                 = 500;// Hull strength of monster
	public              int           SkillPointsLeft             = 16;
	// Skillpoints to distribute at start of game
	public              int           CheatCounter                = 0;
	public              int           Days                        = 0;// Number of days playing
	public              int           EncounterType               = 0;// Type of current encounter
	// Current system on Galactic chart
	public              int           LeaveEmpty                  = 0;
	// Number of cargo bays to leave empty when buying goods
	public              int           NewsSpecialEventCount       = 0;
	// Simplifies tracking what Quests have just been initiated or completed for the News System. This is not important enough to get saved.
	public              int           NoClaim                     = 0;// Days of No-Claim
	public              int           SelectedShipType            = 0;
	// Selected Ship type for Shiptype Info screen
	public              int           TrackedSystem               = -1;
	// The short-range chart will display an arrow towards this system if the value is not -1
	public              int           WarpSystem                  = 0;// Target system for warp
	public              int           Shortcut1                   = 0;
	// default shortcut= 1;= Buy Cargo
	public              int           Shortcut2                   = 1;
	// default shortcut= 2;= Sell Cargo
	public              int           Shortcut3                   = 2;
	// default shortcut= 3;= Shipyard
	public              int           Shortcut4                   = 10;
	// default shortcut= 4;= Short Range Warp
	// the next two values are NOT saved between sessions -- they can only be changed via cheats.
	public              int           ChanceOfVeryRareEncounter   = CHANCEOFVERYRAREENCOUNTER;
	public              int           ChanceOfTradeInOrbit        = CHANCEOFTRADEINORBIT;
	public              int           Clicks                      = 0;
	public              int           DragonflyStatus             = 0;
	//= 0;= Dragonfly not available,= 1;= Go to Baratas,= 2;= Go to Melina,= 3;= Go to Regulas,= 4;= Go to Zalkon,= 5;= Dragonfly destroyed
	public              int           ExperimentStatus            = 0;
	// Experiment;= 0;not given yet,1-11 days from start;= 12;performed,= 13;cancelled
	public              int           FabricRipProbability        = 0;
	// if Experiment == 8; this is the probability of being warped to a random planet.
	public              int           InvasionStatus              = 0;
	// Status Alien invasion of Gemulon;= 0;not given yet;= 1;7=days from start;= 8;too late
	public              int           JaporiDiseaseStatus         = 0;
	//= 0;= No disease,= 1;= Go to Japori (always at least= 10;medicine cannisters),= 2;= Assignment finished or canceled
	public              int           JarekStatus                 = 0;
	// Ambassador Jarek= 0;not delivered;= 1;on board;= 2;delivered
	public              int           MonsterStatus               = 0;
	//= 0;= Space monster isn't available,= 1;= Space monster is in Acamar system,= 2;= Space monster is destroyed
	public              int           ReactorStatus               = 0;
	// Unstable Reactor Status:= 0;not encountered;= 1;20=days of mission (bays of fuel left == 10;- (ReactorStatus/2);= 21;delivered
	public              int           ScarabStatus                = 0;
	// Scarab:= 0;not given yet,= 1;not destroyed,= 2;destroyed, upgrade not performed,= 3;destroyed, hull upgrade performed
	public              int           ShipInfoId                  = 0;
	public              int           VeryRareEncounter           = 0;
	// bit map for which Very Rare Encounter(s) have taken place (see traveler.c, around line= 1850;
	public              int           WildStatus                  = 0;
	// Jonathan Wild:= 0;not delivered;= 1;on board;= 2;delivered
	public              boolean       AlreadyPaidForNewspaper     = false;
	// once you buy a paper on a system, you don't have to pay again.
	public              boolean       AlwaysIgnorePirates         = false;
	// Automatically ignores pirates when it is safe to do so
	public              boolean       AlwaysIgnorePolice          = true;
	// Automatically ignores police when it is safe to do so
	public              boolean       AlwaysIgnoreTradeInOrbit    = false;
	// Automatically ignores Trade in Orbit when it is safe to do so
	public              boolean       AlwaysIgnoreTraders         = false;
	// Automatically ignores traders when it is safe to do so
	public              boolean       AlwaysInfo                  = false;
	// Will always go from SRC to Info
	public              boolean       ArrivedViaWormhole          = false;
	// flag to indicate whether player arrived on current planet via wormhole
	public              boolean       ArtifactOnBoard             = false;// Alien artifact on board
	public              boolean       AttackFleeing               = false;
	// Continue attack on fleeing ship
	public              boolean       AutoAttack                  = false;      // Auto-attack mode
	public              boolean       AutoFlee                    = false;      // Auto-flee mode
	public              boolean       AutoFuel                    = false;
	// Automatically get a full tank when arriving in a new system
	public              boolean       AutoRepair                  = false;
	// Automatically get a full hull repair when arriving in a new system
	public              boolean       CanSuperWarp                = false;
	// Do you have the Portable Singularity on board?
	public              boolean       CommanderFlees              = false;
	public              boolean       Continuous                  = false;
	// Continuous attack/flee mode
	public              boolean       EscapePod                   = false;// Escape Pod in ship
	public              boolean       GameLoaded                  = false;
	// Indicates whether a game is loaded
	public              boolean       IdentifyStartup             = false;
	// Identify commander at game start
	public              boolean       Inspected                   = false;
	// True when the commander has been inspected during the trip
	public              boolean       Insurance                   = false;// Insurance bought
	public              boolean       JustLootedMarie             = false;
	// flag to indicate whether player looted Marie Celeste
	public              boolean       LitterWarning               = false;
	// Warning against littering has been issued.
	public              boolean       MoonBought                  = false;
	// Indicates whether a moon is available at Utopia
	public              boolean       NewsAutoPay                 = false;
	// by default, ask each time someone buys a newspaper
	public              boolean       PossibleToGoThroughRip      = false;
	// if Dr Fehler's experiment happened, we can only go through one space-time rip per warp.
	public              boolean       PriceDifferences            = false;
	// Show price differences instead of absolute prices
	public              boolean       Raided                      = false;
	// True when the commander has been raided during the trip
	public              boolean       RemindLoans                 = true;
	// remind you every five days about outstanding loan balances
	public              boolean       ReserveMoney                = false;
	// Keep enough money for insurance and mercenaries
	public              boolean       SaveOnArrival               = false;
	// Autosave when arriving in a system
	public              boolean       SharePreferences            = true;
	// Share preferences between switched games.
	public              boolean       ShowTrackedRange            = true;
	// display range when tracking a system on Short Range Chart
	public              boolean       TextualEncounters           = false;// Show encounters as text.
	public              boolean       TrackAutoOff                = true;
	// Automatically stop tracking a system when you get to it?
	public              boolean       TribbleMessage              = false;
	// Is true if the Ship Yard on the current system informed you about the tribbles
	public              boolean       BetterGfx                   = false;
	public              int[]         Wormhole                    = new int[GameState.MAXWORMHOLE];
	public              int[]         NewsEvents                  = new int[MAXSPECIALNEWSEVENTS];
	public CrewMember[] Mercenary;
	public Ship         Ship;
	public Ship         Opponent;
	public String       NameCommander;
	public Random rand = new Random();
	CrewMember[] CrewMember;
	public Ship SpaceMonster;
	public Ship Scarab;
	public Ship Dragonfly;
	public Main.FRAGMENTS currentState = Main.FRAGMENTS.NEW_GAME;


	////////////////////////////////////////////////////
	// Helper functions for Newspaper
	////////////////////////////////////////////////////
	public void addNewsEvent(int eventFlag) {
		if (NewsSpecialEventCount < GameState.MAXSPECIALNEWSEVENTS - 1) {
			NewsEvents[NewsSpecialEventCount++] = eventFlag;
		}
	}

	public boolean isNewsEvent(int eventFlag) {
		int i;

		for (i = 0; i < NewsSpecialEventCount; i++) {
			if (NewsEvents[i] == eventFlag) {
				return true;
			}
		}
		return false;
	}

	public int latestNewsEvent() {
		if (NewsSpecialEventCount == 0) {
			return -1;
		} else {
			return NewsEvents[NewsSpecialEventCount - 1];
		}
	}

	public void resetNewsEvents() {
		NewsSpecialEventCount = 0;
	}

	public void replaceNewsEvent(int originalEventFlag, int replacementEventFlag) {
		int i;

		if (originalEventFlag == -1) {
			addNewsEvent(replacementEventFlag);
		} else {
			for (i = 0; i < NewsSpecialEventCount; i++) {
				if (NewsEvents[i] == originalEventFlag) {
					NewsEvents[i] = replacementEventFlag;
				}
			}
		}
	}

	public GameState(Context context, String NameCommander) {
		int i, j, k, d, x, y;
		Boolean Redo, CloseFound, FreeWormhole;
		SharedPreferences sp = context.getSharedPreferences("options", Context.MODE_PRIVATE);

		initializeBasic();

		if (NameCommander.length() == 0) {
			this.NameCommander = "Shelby";
		} else {
			this.NameCommander = NameCommander;
		}

		Shortcut1 = sp.getInt("Shortcut1", Shortcut1);
		Shortcut2 = sp.getInt("Shortcut2", Shortcut2);
		Shortcut3 = sp.getInt("Shortcut3", Shortcut3);
		Shortcut4 = sp.getInt("Shortcut4", Shortcut4);

		AlwaysIgnorePolice = sp.getBoolean("AlwaysIgnorePolice", AlwaysIgnorePolice);
		AlwaysIgnorePirates = sp.getBoolean("AlwaysIgnorePirates", AlwaysIgnorePirates);
		AlwaysIgnoreTraders = sp.getBoolean("AlwaysIgnoreTraders", AlwaysIgnoreTraders);
		AlwaysIgnoreTradeInOrbit = sp.getBoolean("AlwaysIgnoreTradeInOrbit", AlwaysIgnoreTradeInOrbit);
		AutoFuel = sp.getBoolean("AutoFuel", AutoFuel);
		AutoRepair = sp.getBoolean("AutoRepair", AutoRepair);
		AlwaysInfo = sp.getBoolean("AlwaysInfo", AlwaysInfo);
		ReserveMoney = sp.getBoolean("ReserveMoney", ReserveMoney);
		Continuous = sp.getBoolean("Continuous", Continuous);
		AttackFleeing = sp.getBoolean("AttackFleeing", AttackFleeing);
		NewsAutoPay = sp.getBoolean("AutoPayNewspaper", NewsAutoPay);
		RemindLoans = sp.getBoolean("RemindLoans", RemindLoans);
		SaveOnArrival = sp.getBoolean("SaveOnArrival", SaveOnArrival);

		// Initialize Galaxy
		i = 0;
		while (i < GameState.MAXSOLARSYSTEM) {
			if (i < GameState.MAXWORMHOLE) {
				// Place the first system somewhere in the centre
				SolarSystem[i].x = (((GameState.CLOSEDISTANCE / 2) - GetRandom(
					GameState.CLOSEDISTANCE)) + ((GameState.GALAXYWIDTH * (1 + 2 * (i % 3))) / 6));
				SolarSystem[i].y = (((GameState.CLOSEDISTANCE / 2) - GetRandom(
					GameState.CLOSEDISTANCE)) + ((GameState.GALAXYHEIGHT * (i < 3 ? 1 : 3)) / 4));
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
					if (SqrDistance(SolarSystem[j], SolarSystem[i]) < SQR(GameState.CLOSEDISTANCE)) {
						CloseFound = true;
					}
				}
			}
			if (Redo) {
				continue;
			}
			if ((i >= GameState.MAXWORMHOLE) && !CloseFound) {
				continue;
			}

			SolarSystem[i].techLevel = (char) (GetRandom(GameState.MAXTECHLEVEL));
			SolarSystem[i].politics = (char) (GetRandom(GameState.MAXPOLITICS));
			if (Politics.mPolitics[SolarSystem[i].politics].minTechLevel > SolarSystem[i].techLevel) {
				continue;
			}
			if (Politics.mPolitics[SolarSystem[i].politics].maxTechLevel < SolarSystem[i].techLevel) {
				continue;
			}

			if (GetRandom(5) >= 3) {
				SolarSystem[i].specialResources = (char) (1 + GetRandom(GameState.MAXRESOURCES - 1));
			} else {
				SolarSystem[i].specialResources = 0;
			}

			SolarSystem[i].size = (char) (GetRandom(GameState.MAXSIZE));

			if (GetRandom(100) < 15) {
				SolarSystem[i].status = 1 + GetRandom(GameState.MAXSTATUS - 1);
			} else {
				SolarSystem[i].status = GameState.UNEVENTFUL;
			}

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
				if (Wormhole[d] == i) {
					break;
				}
				++d;
			}
			j = GetRandom(GameState.MAXSOLARSYSTEM);
			if (WormholeExists(j, -1)) {
				continue;
			}
			x = SolarSystem[i].x;
			y = SolarSystem[i].y;
			SolarSystem[i].x = SolarSystem[j].x;
			SolarSystem[i].y = SolarSystem[j].y;
			SolarSystem[j].x = x;
			SolarSystem[j].y = y;
			if (d < GameState.MAXWORMHOLE) {
				Wormhole[d] = j;
			}
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
		Mercenary[0].pilot = sp.getInt("Pilot", 1);
		Mercenary[0].fighter = sp.getInt("Fighter", 1);
		Mercenary[0].trader = sp.getInt("Trader", 1);
		Mercenary[0].engineer = sp.getInt("Engineer", 1);

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
			if (Mercenary[i].curSystem == GameState.KRAVATSYSTEM) {
				Redo = true;
			}
			if (Redo) {
				continue;
			}

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
			SolarSystem[GameState.NIXSYSTEM].special = GameState.REACTORDELIVERED;
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
		if (i >= GameState.MAXSOLARSYSTEM) {
			SpecialEvents.mSpecialEvent[GameState.ALIENARTIFACT].occurrence = 0;
		}


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
			SolarSystem[GameState.GEMULONSYSTEM].special = GameState.GEMULONRESCUED;
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
			for (j = 0; j < SpecialEvents.mSpecialEvent[i].occurrence; ++j) {
				Redo = true;
				while (Redo) {
					d = 1 + GetRandom(GameState.MAXSOLARSYSTEM - 1);
					if (SolarSystem[d].special < 0) {
						if (FreeWormhole || i != GameState.SCARAB) {
							SolarSystem[d].special = i;
						}
						Redo = false;
					}
				}
			}
		}

		// Initialize Commander
		for (i = 0; i < 200; ++i) {
			Mercenary[0]/*COMMANDER*/.curSystem = GetRandom(GameState.MAXSOLARSYSTEM);
			if (SolarSystem[Mercenary[0].curSystem]/*CURSYSTEM*/.special >= 0) {
				continue;
			}

			// Seek at least an agricultural planet as startplanet (but not too hi-tech)
			if ((i < 100) && ((SolarSystem[Mercenary[0].curSystem]/*CURSYSTEM*/.techLevel <= 0) || (SolarSystem[Mercenary[0].curSystem]/*CURSYSTEM*/.techLevel >= 6))) {
				continue;
			}

			// Make sure at least three other systems can be reached
			d = 0;
			for (j = 0; j < GameState.MAXSOLARSYSTEM; ++j) {
				if (j == Mercenary[0]/*COMMANDER*/.curSystem) {
					continue;
				}
				if (SqrDistance(SolarSystem[j], SolarSystem[Mercenary[0].curSystem]/*CURSYSTEM*/) <= SQR(
					ShipTypes.ShipTypes[1].fuelTanks)) {
					++d;
					if (d >= 3) {
						break;
					}
				}
			}
			if (d < 3) {
				continue;
			}

			break;
		}

		Credits = 1000;
		Debt = 0;
		Days = 0;
		WarpSystem = Mercenary[0]/*COMMANDER*/.curSystem;
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
		MonsterHull = SpaceMonster.getType().hullStrength;
		EscapePod = false;
		Insurance = false;
		RemindLoans = true;
		NoClaim = 0;
		ArtifactOnBoard = false;
		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			BuyingPrice[i] = 0;
		}
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
		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			Ship.cargo[i] = 0;
		}
		Ship.weapon[0] = 0;
		for (i = 1; i < GameState.MAXWEAPON; ++i) {
			Ship.weapon[i] = -1;
		}
		for (i = 0; i < GameState.MAXSHIELD; ++i) {
			Ship.shield[i] = -1;
			Ship.shieldStrength[i] = 0;
		}
		for (i = 0; i < GameState.MAXGADGET; ++i) {
			Ship.gadget[i] = -1;
		}
		Ship.crew[0] = 0;
		for (i = 1; i < GameState.MAXCREW; ++i) {
			Ship.crew[i] = -1;
		}
		Ship.fuel = Ship.GetFuelTanks();
		Ship.hull = Ship.getType().hullStrength;
		Ship.tribbles = 0;

		SkillPointsLeft = 16;
	}

	public GameState(SaveGame_v110 g) {
		int i;
		initializeBasic();
		for (i = 0; i < GameState.MAXCREWMEMBER; i++) {
			this.Mercenary[i] = new CrewMember(g.Mercenary[i].nameIndex, g.Mercenary[i].pilot,
				g.Mercenary[i].fighter, g.Mercenary[i].trader, g.Mercenary[i].engineer,
				g.Mercenary[i].curSystem);
		}
		this.Opponent = new Ship(g.Opponent.type, g.Opponent.cargo.clone(), g.Opponent.weapon.clone(),
			g.Opponent.shield.clone(), g.Opponent.shieldStrength.clone(), g.Opponent.gadget.clone(),
			g.Opponent.crew.clone(), g.Opponent.fuel, g.Opponent.hull, g.Opponent.tribbles, this);
		this.Ship = new Ship(g.Ship.type, g.Ship.cargo.clone(), g.Ship.weapon.clone(),
			g.Ship.shield.clone(), g.Ship.shieldStrength.clone(), g.Ship.gadget.clone(),
			g.Ship.crew.clone(), g.Ship.fuel, g.Ship.hull, g.Ship.tribbles, this);
		for (i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			this.SolarSystem[i] = new SolarSystem();
			this.SolarSystem[i].nameIndex = g.SolarSystem[i].nameIndex;
			this.SolarSystem[i].techLevel = g.SolarSystem[i].techLevel;
			this.SolarSystem[i].politics = g.SolarSystem[i].politics;
			this.SolarSystem[i].status = g.SolarSystem[i].status;
			this.SolarSystem[i].x = g.SolarSystem[i].x;
			this.SolarSystem[i].y = g.SolarSystem[i].y;
			this.SolarSystem[i].specialResources = g.SolarSystem[i].specialResources;
			this.SolarSystem[i].size = g.SolarSystem[i].size;
			this.SolarSystem[i].qty = g.SolarSystem[i].qty.clone();
			this.SolarSystem[i].countDown = g.SolarSystem[i].countDown;
			this.SolarSystem[i].visited = g.SolarSystem[i].visited;
			this.SolarSystem[i].special = g.SolarSystem[i].special;
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
		this.BetterGfx = false;
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
		GameState.setDifficulty(g.Difficulty);

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

	public GameState(SaveGame_v111 g) {
		int i;
		initializeBasic();
		for (i = 0; i < GameState.MAXCREWMEMBER; i++) {
			this.Mercenary[i] = new CrewMember(g.Mercenary[i].nameIndex, g.Mercenary[i].pilot,
				g.Mercenary[i].fighter, g.Mercenary[i].trader, g.Mercenary[i].engineer,
				g.Mercenary[i].curSystem);
		}
		this.Opponent = new Ship(g.Opponent.type, g.Opponent.cargo.clone(), g.Opponent.weapon.clone(),
			g.Opponent.shield.clone(), g.Opponent.shieldStrength.clone(), g.Opponent.gadget.clone(),
			g.Opponent.crew.clone(), g.Opponent.fuel, g.Opponent.hull, g.Opponent.tribbles, this);
		this.Ship = new Ship(g.Ship.type, g.Ship.cargo.clone(), g.Ship.weapon.clone(),
			g.Ship.shield.clone(), g.Ship.shieldStrength.clone(), g.Ship.gadget.clone(),
			g.Ship.crew.clone(), g.Ship.fuel, g.Ship.hull, g.Ship.tribbles, this);
		for (i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			this.SolarSystem[i] = new SolarSystem();
			this.SolarSystem[i].nameIndex = g.SolarSystem[i].nameIndex;
			this.SolarSystem[i].techLevel = g.SolarSystem[i].techLevel;
			this.SolarSystem[i].politics = g.SolarSystem[i].politics;
			this.SolarSystem[i].status = g.SolarSystem[i].status;
			this.SolarSystem[i].x = g.SolarSystem[i].x;
			this.SolarSystem[i].y = g.SolarSystem[i].y;
			this.SolarSystem[i].specialResources = g.SolarSystem[i].specialResources;
			this.SolarSystem[i].size = g.SolarSystem[i].size;
			this.SolarSystem[i].qty = g.SolarSystem[i].qty.clone();
			this.SolarSystem[i].countDown = g.SolarSystem[i].countDown;
			this.SolarSystem[i].visited = g.SolarSystem[i].visited;
			this.SolarSystem[i].special = g.SolarSystem[i].special;
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
		GameState.setDifficulty(g.Difficulty);

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

	public GameState(SaveGame_v120 g) {
		int i;
		initializeBasic();
		for (i = 0; i < GameState.MAXCREWMEMBER; i++) {
			this.Mercenary[i] = new CrewMember(g.Mercenary[i].nameIndex, g.Mercenary[i].pilot,
				g.Mercenary[i].fighter, g.Mercenary[i].trader, g.Mercenary[i].engineer,
				g.Mercenary[i].curSystem);
		}
		this.Opponent = new Ship(g.Opponent.type, g.Opponent.cargo.clone(), g.Opponent.weapon.clone(),
			g.Opponent.shield.clone(), g.Opponent.shieldStrength.clone(), g.Opponent.gadget.clone(),
			g.Opponent.crew.clone(), g.Opponent.fuel, g.Opponent.hull, g.Opponent.tribbles, this);
		this.Scarab = new Ship(g.Scarab.type, g.Scarab.cargo.clone(), g.Scarab.weapon.clone(),
			g.Scarab.shield.clone(), g.Scarab.shieldStrength.clone(), g.Scarab.gadget.clone(),
			g.Scarab.crew.clone(), g.Scarab.fuel, g.Scarab.hull, g.Scarab.tribbles, this);
		this.Dragonfly = new Ship(g.Ship.type, g.Ship.cargo.clone(), g.Ship.weapon.clone(),
			g.Ship.shield.clone(), g.Ship.shieldStrength.clone(), g.Ship.gadget.clone(),
			g.Ship.crew.clone(), g.Ship.fuel, g.Ship.hull, g.Ship.tribbles, this);
		this.SpaceMonster = new Ship(g.SpaceMonster.type, g.SpaceMonster.cargo.clone(),
			g.SpaceMonster.weapon.clone(), g.SpaceMonster.shield.clone(),
			g.SpaceMonster.shieldStrength.clone(), g.SpaceMonster.gadget.clone(),
			g.SpaceMonster.crew.clone(), g.SpaceMonster.fuel, g.SpaceMonster.hull,
			g.SpaceMonster.tribbles, this);
		this.Ship = new Ship(g.Ship.type, g.Ship.cargo.clone(), g.Ship.weapon.clone(),
			g.Ship.shield.clone(), g.Ship.shieldStrength.clone(), g.Ship.gadget.clone(),
			g.Ship.crew.clone(), g.Ship.fuel, g.Ship.hull, g.Ship.tribbles, this);
		for (i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			this.SolarSystem[i] = new SolarSystem();
			this.SolarSystem[i].nameIndex = g.SolarSystem[i].nameIndex;
			this.SolarSystem[i].techLevel = g.SolarSystem[i].techLevel;
			this.SolarSystem[i].politics = g.SolarSystem[i].politics;
			this.SolarSystem[i].status = g.SolarSystem[i].status;
			this.SolarSystem[i].x = g.SolarSystem[i].x;
			this.SolarSystem[i].y = g.SolarSystem[i].y;
			this.SolarSystem[i].specialResources = g.SolarSystem[i].specialResources;
			this.SolarSystem[i].size = g.SolarSystem[i].size;
			this.SolarSystem[i].qty = g.SolarSystem[i].qty.clone();
			this.SolarSystem[i].countDown = g.SolarSystem[i].countDown;
			this.SolarSystem[i].visited = g.SolarSystem[i].visited;
			this.SolarSystem[i].special = g.SolarSystem[i].special;
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
		GameState.setDifficulty(g.Difficulty);
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

	public static int getDifficulty() {
		return Difficulty;
	}

	public static void setDifficulty(int difficulty) {
		Difficulty = difficulty;
		CountDown = difficulty + 3;
	}

	public void initializeBasic() {
		int i;

		CrewMember = new CrewMember[GameState.MAXCREW];
		Mercenary = new CrewMember[GameState.MAXCREWMEMBER + 1];
		for (i = 0; i <= GameState.MAXCREWMEMBER; i++) {
			Mercenary[i] = new CrewMember();
		}
		SolarSystem = new SolarSystem[GameState.MAXSOLARSYSTEM];
		for (i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			SolarSystem[i] = new SolarSystem();
		}


		final int[] cargo = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		final int[] armament = {-1, -1, -1};
		final int[] shielding = {-1, -1, -1};
		final int[] shieldstrength = {0, 0, 0};
		final int[] gadgets = {-1, -1, -1};
		final int[] crew = {0, -1, -1};

		Ship = new Ship(1, // Gnat
			cargo, // No cargo
			armament, // One pulse laser
			shielding, shieldstrength, // No shields
			gadgets, // No gadgets
			crew, // Commander on board
			14, // Full tank
			100, // Full hull strength
			0, this); // No tribbles on board
		crew[0] = 1;
		Opponent = new Ship(1, // Gnat
			cargo, // No cargo
			armament, // One pulse laser
			shielding, shieldstrength, // No shields
			gadgets, // No gadgets
			crew, // Alyssa on board
			14, // Full tank
			100, // Full hull strength
			0, this); // No tribbles on board
		armament[0] = armament[1] = armament[2] = 2;
		crew[0] = MAXCREWMEMBER;
		SpaceMonster = new Ship(MAXSHIPTYPE, // Space monster
			cargo, // No cargo
			armament, // Three military lasers
			shielding, shieldstrength, // No shields
			gadgets, // No gadgets
			crew, // super stats
			1, // Full tank
			500, // Full hull strength
			0, this); // No tribbles on board
		armament[0] = armament[1] = 2;
		armament[2] = -1;
		Scarab = new Ship(MAXSHIPTYPE + 3, // Scarab
			cargo, // No cargo
			armament, // Two military lasers
			shielding, shieldstrength, // No shields
			gadgets, // No gadgets
			crew, // super stats
			1, // Full tank
			400, // Full hull strength
			0, this); // No tribbles on board
		armament[0] = 2;
		armament[1] = 0;
		armament[2] = -1;
		shielding[0] = shielding[1] = shielding[2] = LIGHTNINGSHIELD;
		shieldstrength[0] = shieldstrength[1] = shieldstrength[2] = LSHIELDPOWER;
		gadgets[0] = AUTOREPAIRSYSTEM;
		gadgets[1] = TARGETINGSYSTEM;
		gadgets[2] = -1;
		Dragonfly = new Ship(MAXSHIPTYPE + 1, // Dragonfly
			cargo, // No cargo
			armament, // One military laser and one pulselaser
			shielding, shieldstrength, // Three lightning shields
			gadgets, // Gadgets
			crew, // super stats
			1, // Full tank
			10, // Full hull strength (though this isn't much)
			0, this); // No tribbles on board
	}

	public int GetRandom(int a) {
		return (rand.nextInt(Math.max(1, a)));
	}

	public int SQR(int a) {
		return (a * a);
	}

	public double SqrDistance(SolarSystem a, SolarSystem b) {
		return (SQR(a.x - b.x) + SQR(a.y - b.y));
	}

	public boolean WormholeExists(int a, SolarSystem s) {
		int i;
		//noinspection StatementWithEmptyBody
		for (i = 0; SolarSystem[i] != s; i++) {
		}
		return WormholeExists(a, i);
	}

	public boolean WormholeExists(int a, int b) {
		int i;

		i = 0;
		while (i < MAXWORMHOLE) {
			if (Wormhole[i] == a) {
				break;
			}
			++i;
		}

		if (i < MAXWORMHOLE) {
			if (b < 0) {
				return true;
			} else if (i < MAXWORMHOLE - 1) {
				if (Wormhole[i + 1] == b) {
					return true;
				}
			} else if (Wormhole[0] == b) {
				return true;
			}
		}
		return false;
	}

	public int WormholeTax(int a, SolarSystem b) {
		if (WormholeExists(a, b)) {
			return (Ship.getType().costOfFuel * 25);
		}

		return 0;
	}

	public int RealDistance(SolarSystem a, SolarSystem b) {
		return (int) Math.sqrt(SqrDistance(a, b));
	}

	public int RandomSkill() {
		return 1 + GetRandom(5) + GetRandom(6);
	}

	int GetForHire() {
		int ForHire = -1;
		int i;

		for (i = 1; i < MAXCREWMEMBER; ++i) {
			if (i == Ship.crew[1] || i == Ship.crew[2]) {
				continue;
			}
			if (Mercenary[i].curSystem == Mercenary[0].curSystem) {
				ForHire = i;
				break;
			}
		}
		return ForHire;
	}

	public int AvailableQuarters() {
		return Ship.getType().crewQuarters - (JarekStatus == 1 ? 1 : 0) - (WildStatus == 1 ? 1 : 0);
	}

	public int MercenaryPriceHire(int idx) {
		int price;
		if (idx < 0 || (idx >= MAXCREWMEMBER && WildStatus == 2)) {
			price = 0; // This would be Zeethibal, who joins for free after Wild's quest.
		} else {
			price =
				((Mercenary[idx].pilot + Mercenary[idx].fighter + Mercenary[idx].trader + Mercenary[idx].engineer) * 3);
		}
		return price;
	}

	public int OpenQuests() {
		int r = 0;

		if (MonsterStatus == 1) {
			++r;
		}

		if (DragonflyStatus >= 1 && DragonflyStatus <= 4) {
			++r;
		} else if (SolarSystem[ZALKONSYSTEM].special == INSTALLLIGHTNINGSHIELD) {
			++r;
		}

		if (JaporiDiseaseStatus == 1) {
			++r;
		}

		if (ArtifactOnBoard) {
			++r;
		}

		if (WildStatus == 1) {
			++r;
		}

		if (JarekStatus == 1) {
			++r;
		}

		if (InvasionStatus >= 1 && InvasionStatus < 7) {
			++r;
		} else if (SolarSystem[GEMULONSYSTEM].special == GETFUELCOMPACTOR) {
			++r;
		}

		if (ExperimentStatus >= 1 && ExperimentStatus < 11) {
			++r;
		}

		if (ReactorStatus >= 1 && ReactorStatus < 21) {
			++r;
		}

		if (SolarSystem[NIXSYSTEM].special == GETSPECIALLASER) {
			++r;
		}

		if (ScarabStatus == 1) {
			++r;
		}

		if (Ship.tribbles > 0) {
			++r;
		}

		if (MoonBought) {
			++r;
		}

		return r;
	}

	public int CurrentWorth() {
		return (CurrentShipPrice(false) + Credits - Debt + (MoonBought ? COSTMOON : 0));
	}

	public int CurrentShipPrice(boolean ForInsurance) {
		int i;
		int CurPrice;

		CurPrice = CurrentShipPriceWithoutCargo(ForInsurance);
		for (i = 0; i < MAXTRADEITEM; ++i) {
			CurPrice += BuyingPrice[i];
		}
		return CurPrice;
	}

	public int CurrentShipPriceWithoutCargo(boolean ForInsurance) {
		int i;
		int CurPrice;

		CurPrice =
			// Trade-in value is three-fourths the original price
			((Ship.getType().price * (Ship.tribbles > 0 && !ForInsurance ? 1 : 3)) / 4)
				// subtract repair costs
				- (Ship.GetHullStrength() - Ship.hull) * Ship.getType().repairCosts
				// subtract costs to fill tank with fuel
				- (Ship.getType().fuelTanks - Ship.GetFuel()) * Ship.getType().costOfFuel;
		// Add 2/3 of the price of each item of equipment
		for (i = 0; i < MAXWEAPON; ++i) {
			if (Ship.weapon[i] >= 0) {
				CurPrice += WEAPONSELLPRICE(i);
			}
		}
		for (i = 0; i < MAXSHIELD; ++i) {
			if (Ship.shield[i] >= 0) {
				CurPrice += SHIELDSELLPRICE(i);
			}
		}
		for (i = 0; i < MAXGADGET; ++i) {
			if (Ship.gadget[i] >= 0) {
				CurPrice += GADGETSELLPRICE(i);
			}
		}

		return CurPrice;
	}

	public int WEAPONSELLPRICE(int a) {
		return (BaseSellPrice(Ship.weapon[a], Weapons.mWeapons[Ship.weapon[a]].price));
	}

	public int SHIELDSELLPRICE(int a) {
		return (BaseSellPrice(Ship.shield[a], Shields.mShields[Ship.shield[a]].price));
	}

	public int GADGETSELLPRICE(int a) {
		return (BaseSellPrice(Ship.gadget[a], Gadgets.mGadgets[Ship.gadget[a]].price));
	}

	int BasePrice(int ItemTechLevel, int Price) {
		// *************************************************************************
		// Determine base price of item
		// *************************************************************************
		SolarSystem CURSYSTEM = SolarSystem[Mercenary[0].curSystem];
		return ((ItemTechLevel > CURSYSTEM.techLevel) ? 0 :
			((Price * (100 - Ship.TraderSkill())) / 100));
	}

	int BaseSellPrice(int Index, int Price) {
		// *************************************************************************
		// Determine selling price
		// *************************************************************************
		return (Index >= 0 ? ((Price * 3) / 4) : 0);
	}

	public int AdaptDifficulty(int Level) {
		// *************************************************************************
		// Adapt a skill to the difficulty level
		// *************************************************************************
		if (Difficulty == BEGINNER || Difficulty == EASY) {
			return (Level + 1);
		} else if (Difficulty == IMPOSSIBLE) {
			return Math.max(1, Level - 1);
		} else {
			return Level;
		}
	}

	public void RecalculateSellPrices() {
		// *************************************************************************
		// After erasure of police record, selling prices must be recalculated
		// *************************************************************************
		int i;

		for (i = 0; i < MAXTRADEITEM; ++i) {
			SellPrice[i] = (SellPrice[i] * 100) / 90;
		}
	}

	public int MercenaryMoney() {
		int i, ToPay;

		ToPay = 0;
		for (i = 1; i < MAXCREW; ++i) {
			if (Ship.crew[i] >= 0) {
				ToPay += MercenaryPriceHire(Ship.crew[i]);
			}
		}

		return ToPay;
	}

	public int InsuranceMoney() {
		if (!Insurance) {
			return 0;
		} else {
			return (Math.max(1, (((CurrentShipPriceWithoutCargo(true) * 5) / 2000) * (100 - Math.min(
				NoClaim, 90)) / 100)));
		}
	}

	int ToSpend() {
		// *************************************************************************
		// Money available to spend
		// *************************************************************************
		if (!ReserveMoney) {
			return Credits;
		}
		return Math.max(0, Credits - MercenaryMoney() - InsuranceMoney());
	}

	public int GetFirstEmptySlot(int Slots, int[] Item) {
		int FirstEmptySlot, j;

		FirstEmptySlot = -1;
		for (j = 0; j < Slots; ++j) {
			if (Item[j] < 0) {
				FirstEmptySlot = j;
				break;
			}
		}
		return FirstEmptySlot;
	}

	public int MaxLoan() {
		return (int) (PoliceRecordScore >= CLEANSCORE ? Math.min(25000L, Math.max(1000L,
			((CurrentWorth() / 10L) / 500L) * 500L)) : 500L);
	}

	public int BASESHIPPRICE(int a) {
		// The Difficulty part is commented in the original PalmOS source, too, but I think it maybe should be activated -- BRS
		return (((ShipTypes.ShipTypes[a].price * (100 - Ship
			.TraderSkill())) / 100) /* * (Difficulty < 3 ? 1 : (Difficulty + 3)) / (Difficulty < 3 ? 1 : 5)*/);
	}

	public int BASEWEAPONPRICE(int a) {
		return (BasePrice(Weapons.mWeapons[a].techLevel, Weapons.mWeapons[a].price));
	}

	public int BASESHIELDPRICE(int a) {
		return (BasePrice(Shields.mShields[a].techLevel, Shields.mShields[a].price));
	}

	public int BASEGADGETPRICE(int a) {
		return (BasePrice(Gadgets.mGadgets[a].techLevel, Gadgets.mGadgets[a].price));
	}

	public void DetermineShipPrices() {
		int i;
		de.anderdonau.spacetrader.DataTypes.SolarSystem CURSYSTEM = SolarSystem[Mercenary[0].curSystem];

		for (i = 0; i < MAXSHIPTYPE; ++i) {
			if (ShipTypes.ShipTypes[i].minTechLevel <= CURSYSTEM.techLevel) {
				ShipPrice[i] = BASESHIPPRICE(i) - CurrentShipPrice(false);
				if (ShipPrice[i] == 0) {
					ShipPrice[i] = 1;
				}
			} else {
				ShipPrice[i] = 0;
			}
		}
	}

	public void CreateShip(int Index) {
		int i;

		Ship.type = Index;

		for (i = 0; i < MAXWEAPON; ++i) {
			Ship.weapon[i] = -1;
		}

		for (i = 0; i < MAXSHIELD; ++i) {
			Ship.shield[i] = -1;
			Ship.shieldStrength[i] = 0;
		}

		for (i = 0; i < MAXGADGET; ++i) {
			Ship.gadget[i] = -1;
		}

		for (i = 0; i < MAXTRADEITEM; ++i) {
			Ship.cargo[i] = 0;
			BuyingPrice[i] = 0;
		}

		Ship.fuel = Ship.GetFuelTanks();
		Ship.hull = Ship.getType().hullStrength;
	}

	public void BuyShip(int Index) {
		CreateShip(Index);
		Credits -= ShipPrice[Index];
		if (ScarabStatus == 3) // Scarab hull hardening is not transferrable.
		{
			ScarabStatus = 0;
		}
	}

	public void CreateFlea() {
		// *************************************************************************
		// You get a Flea
		// *************************************************************************
		int i;
		CreateShip(0);
		for (i = 1; i < MAXCREW; ++i) {
			Ship.crew[i] = -1;
		}
		EscapePod = false;
		Insurance = false;
		NoClaim = 0;
	}

	public int StandardPrice(int Good, int Size, int Tech, int Government, int Resources) {
		// *************************************************************************
		// Standard price calculation
		// *************************************************************************
		int Price;

		if (((Good == NARCOTICS) && (!Politics.mPolitics[Government].drugsOK)) || ((Good == FIREARMS) && (!Politics.mPolitics[Government].firearmsOK))) {
			return 0;
		}

		// Determine base price on techlevel of system
		Price =
			Tradeitems.mTradeitems[Good].priceLowTech + (Tech * Tradeitems.mTradeitems[Good].priceInc);

		// If a good is highly requested, increase the price
		if (Politics.mPolitics[Government].wanted == Good) {
			Price = (Price * 4) / 3;
		}

		// High trader activity decreases prices
		Price = (Price * (100 - (2 * Politics.mPolitics[Government].strengthTraders))) / 100;

		// Large system = high production decreases prices
		Price = (Price * (100 - Size)) / 100;

		// Special resources price adaptation
		if (Resources > 0) {
			if (Tradeitems.mTradeitems[Good].cheapResource >= 0) {
				if (Resources == Tradeitems.mTradeitems[Good].cheapResource) {
					Price = (Price * 3) / 4;
				}
			}
			if (Tradeitems.mTradeitems[Good].expensiveResource >= 0) {
				if (Resources == Tradeitems.mTradeitems[Good].expensiveResource) {
					Price = (Price * 4) / 3;
				}
			}
		}

		// If a system can't use something, its selling price is zero.
		if (Tech < Tradeitems.mTradeitems[Good].techUsage) {
			return 0;
		}

		if (Price < 0) {
			return 0;
		}

		return Price;
	}

	public void DeterminePrices(int SystemID) {
		// *************************************************************************
		// Determine prices in specified system (changed from Current System) SjG
		// *************************************************************************
		int i;

		for (i = 0; i < MAXTRADEITEM; ++i) {
			BuyPrice[i] = StandardPrice(i, SolarSystem[SystemID].size, SolarSystem[SystemID].techLevel,
				SolarSystem[SystemID].politics, SolarSystem[SystemID].specialResources);

			if (BuyPrice[i] <= 0) {
				BuyPrice[i] = 0;
				SellPrice[i] = 0;
				continue;
			}

			// In case of a special status, adapt price accordingly
			if (Tradeitems.mTradeitems[i].doublePriceStatus >= 0) {
				if (SolarSystem[SystemID].status == Tradeitems.mTradeitems[i].doublePriceStatus) {
					BuyPrice[i] = (BuyPrice[i] * 3) >> 1;
				}
			}

			// Randomize price a bit
			BuyPrice[i] = BuyPrice[i] + GetRandom(Tradeitems.mTradeitems[i].variance) - GetRandom(
				Tradeitems.mTradeitems[i].variance);

			// Should never happen
			if (BuyPrice[i] <= 0) {
				BuyPrice[i] = 0;
				SellPrice[i] = 0;
				continue;
			}

			SellPrice[i] = BuyPrice[i];
			if (PoliceRecordScore < DUBIOUSSCORE) {
				// Criminals have to pay off an intermediary
				SellPrice[i] = (SellPrice[i] * 90) / 100;
			}
		}

		RecalculateBuyPrices(SystemID);
	}

	public void RecalculateBuyPrices(int SystemID) {
		// *************************************************************************
		// After changing the trader skill, buying prices must be recalculated.
		// Revised to be callable on an arbitrary Solar System
		// *************************************************************************
		int i;

		for (i = 0; i < MAXTRADEITEM; ++i) {
			if (SolarSystem[SystemID].techLevel < Tradeitems.mTradeitems[i].techProduction) {
				BuyPrice[i] = 0;
			} else if (((i == NARCOTICS) && (!Politics.mPolitics[SolarSystem[SystemID].politics].drugsOK)) || ((i == FIREARMS) && (!Politics.mPolitics[SolarSystem[SystemID].politics].firearmsOK))) {
				BuyPrice[i] = 0;
			} else {
				if (PoliceRecordScore < DUBIOUSSCORE) {
					BuyPrice[i] = (SellPrice[i] * 100) / 90;
				} else {
					BuyPrice[i] = SellPrice[i];
				}
				// BuyPrice = SellPrice + 1 to 12% (depending on trader skill (minimum is 1, max 12))
				BuyPrice[i] = (BuyPrice[i] * (103 + (MAXSKILL - Ship.TraderSkill())) / 100);
				if (BuyPrice[i] <= SellPrice[i]) {
					BuyPrice[i] = SellPrice[i] + 1;
				}
			}
		}
	}

	public void PayInterest() {
		// *************************************************************************
		// Pay interest on debt
		// *************************************************************************
		int IncDebt;

		if (Debt > 0) {
			IncDebt = Math.max(1, Debt / 10);
			if (Credits > IncDebt) {
				Credits -= IncDebt;
			} else {
				Debt += (IncDebt - Credits);
				Credits = 0;
			}
		}
	}

	public int STRENGTHPOLICE(SolarSystem a) {
		return PoliceRecordScore < PSYCHOPATHSCORE ? 3 * Politics.mPolitics[a.politics].strengthPolice :
			PoliceRecordScore < VILLAINSCORE ? 2 * Politics.mPolitics[a.politics].strengthPolice :
				Politics.mPolitics[a.politics].strengthPolice;
	}

	public void GenerateOpponent(int Opp) {
		Boolean Redo;
		int i = 0;
		int j;
		int sum;
		int Tries;
		int d, e, f, k, m;
		int Bays;

		Tries = 1;

		final int[] cargo = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		final int[] armament = {-1, -1, -1};
		final int[] shielding = {-1, -1, -1};
		final int[] shieldstrength = {0, 0, 0};
		final int[] gadgets = {-1, -1, -1};
		final int[] crew = {1, -1, -1};

		Opponent = new Ship(1, // Gnat
			cargo, // No cargo
			armament, // One pulse laser
			shielding, shieldstrength, // No shields
			gadgets, // No gadgets
			crew, // Alyssa on board
			14, // Full tank
			100, // Full hull strength
			0, this); // No tribbles on board
		if (Opp == FAMOUSCAPTAIN) {
			// we just fudge for the Famous Captains' Ships...
			Opponent.type = MAXSHIPTYPE - 1;
			for (i = 0; i < MAXSHIELD; i++) {
				Opponent.shield[i] = REFLECTIVESHIELD;
				Opponent.shieldStrength[i] = RSHIELDPOWER;
			}
			for (i = 0; i < MAXWEAPON; i++) {
				Opponent.weapon[i] = MILITARYLASERWEAPON;
			}
			Opponent.gadget[0] = TARGETINGSYSTEM;
			Opponent.gadget[1] = NAVIGATINGSYSTEM;
			Opponent.hull = ShipTypes.ShipTypes[MAXSHIPTYPE - 1].hullStrength;

			// these guys are bad-ass!
			Opponent.crew[0] = MAXCREWMEMBER;
			Mercenary[Opponent.crew[0]].pilot = MAXSKILL;
			Mercenary[Opponent.crew[0]].fighter = MAXSKILL;
			Mercenary[Opponent.crew[0]].trader = MAXSKILL;
			Mercenary[Opponent.crew[0]].engineer = MAXSKILL;
			return;
		}

		if (Opp == MANTIS) {
			Tries = 1 + Difficulty;
		}

		// The police will try to hunt you down with better ships if you are
		// a villain, and they will try even harder when you are considered to
		// be a psychopath (or are transporting Jonathan Wild)

		if (Opp == POLICE) {
			if (PoliceRecordScore < VILLAINSCORE && WildStatus != 1) {
				Tries = 3;
			} else if (PoliceRecordScore < PSYCHOPATHSCORE || WildStatus == 1) {
				Tries = 5;
			}
			Tries = Math.max(1, Tries + Difficulty - NORMAL);
		}

		// Pirates become better when you get richer
		if (Opp == PIRATE) {
			Tries = 1 + (CurrentWorth() / 100000);
			Tries = Math.max(1, Tries + Difficulty - NORMAL);
		}

		j = 0;
		if (Opp == TRADER) {
			Opponent.type = 0;
		} else {
			Opponent.type = 1;
		}

		k = (Difficulty >= NORMAL ? Difficulty - NORMAL : 0);

		while (j < Tries) {
			Redo = true;

			while (Redo) {
				d = GetRandom(100);
				i = 0;
				sum = ShipTypes.ShipTypes[0].occurrence;

				while (sum < d) {
					if (i >= MAXSHIPTYPE - 1) {
						break;
					}
					++i;
					sum += ShipTypes.ShipTypes[i].occurrence;
				}

				if (Opp == POLICE && (ShipTypes.ShipTypes[i].police < 0 || Politics.mPolitics[SolarSystem[WarpSystem].politics].strengthPolice + k < ShipTypes.ShipTypes[i].police)) {
					continue;
				}

				if (Opp == PIRATE && (ShipTypes.ShipTypes[i].pirates < 0 || Politics.mPolitics[SolarSystem[WarpSystem].politics].strengthPirates + k < ShipTypes.ShipTypes[i].pirates)) {
					continue;
				}

				if (Opp == TRADER && (ShipTypes.ShipTypes[i].traders < 0 || Politics.mPolitics[SolarSystem[WarpSystem].politics].strengthTraders + k < ShipTypes.ShipTypes[i].traders)) {
					continue;
				}

				Redo = false;
			}

			if (i > Opponent.type) {
				Opponent.type = i;
			}
			++j;
		}

		if (Opp == MANTIS) {
			Opponent.type = MANTISTYPE;
		} else {
			Tries = Math.max(1, (CurrentWorth() / 150000) + Difficulty - NORMAL);
		}

		// Determine the gadgets
		if (Opponent.getType().gadgetSlots <= 0) {
			d = 0;
		} else if (Difficulty <= HARD) {
			d = GetRandom(Opponent.getType().gadgetSlots + 1);
			if (d < Opponent.getType().gadgetSlots) {
				if (Tries > 4) {
					++d;
				} else if (Tries > 2) {
					d += GetRandom(2);
				}
			}
		} else {
			d = Opponent.getType().gadgetSlots;
		}
		for (i = 0; i < d; ++i) {
			e = 0;
			f = 0;
			while (e < Tries) {
				k = GetRandom(100);
				j = 0;
				sum = Gadgets.mGadgets[0].chance;
				while (k < sum) {
					if (j >= MAXGADGETTYPE - 1) {
						break;
					}
					++j;
					sum += Gadgets.mGadgets[j].chance;
				}
				if (!Opponent.HasGadget(j)) {
					if (j > f) {
						f = j;
					}
				}
				++e;
			}
			Opponent.gadget[i] = f;
		}
		for (i = d; i < MAXGADGET; ++i) {
			Opponent.gadget[i] = -1;
		}

		// Determine the number of cargo bays
		Bays = Opponent.getType().cargoBays;
		for (i = 0; i < MAXGADGET; ++i) {
			if (Opponent.gadget[i] == EXTRABAYS) {
				Bays += 5;
			}
		}

		// Fill the cargo bays
		for (i = 0; i < MAXTRADEITEM; ++i) {
			Opponent.cargo[i] = 0;
		}

		if (Bays > 5) {
			if (Difficulty >= NORMAL) {
				m = 3 + GetRandom(Bays - 5);
				sum = Math.min(m, 15);
			} else {
				sum = Bays;
			}
			if (Opp == POLICE) {
				sum = 0;
			}
			if (Opp == PIRATE) {
				if (Difficulty < NORMAL) {
					sum = (sum * 4) / 5;
				} else {
					sum = sum / Difficulty;
				}
			}
			if (sum < 1) {
				sum = 1;
			}

			i = 0;
			while (i < sum) {
				j = GetRandom(MAXTRADEITEM);
				k = 1 + GetRandom(10 - j);
				if (i + k > sum) {
					k = sum - i;
				}
				Opponent.cargo[j] += k;
				i += k;
			}
		}

		// Fill the fuel tanks
		Opponent.fuel = Opponent.getType().fuelTanks;

		// No tribbles on board
		Opponent.tribbles = 0;

		// Fill the weapon slots (if possible, at least one weapon)
		if (Opponent.getType().weaponSlots <= 0) {
			d = 0;
		} else if (Opponent.getType().weaponSlots <= 1) {
			d = 1;
		} else if (Difficulty <= HARD) {
			d = 1 + GetRandom(Opponent.getType().weaponSlots);
			if (d < Opponent.getType().weaponSlots) {
				if (Tries > 4 && Difficulty >= HARD) {
					++d;
				} else if (Tries > 3 || Difficulty >= HARD) {
					d += GetRandom(2);
				}
			}
		} else {
			d = Opponent.getType().weaponSlots;
		}
		for (i = 0; i < d; ++i) {
			e = 0;
			f = 0;
			while (e < Tries) {
				k = GetRandom(100);
				j = 0;
				sum = Weapons.mWeapons[0].chance;
				while (k < sum) {
					if (j >= MAXWEAPONTYPE - 1) {
						break;
					}
					++j;
					sum += Weapons.mWeapons[j].chance;
				}
				if (j > f) {
					f = j;
				}
				++e;
			}
			Opponent.weapon[i] = f;
		}
		for (i = d; i < MAXWEAPON; ++i) {
			Opponent.weapon[i] = -1;
		}

		// Fill the shield slots
		if (Opponent.getType().shieldSlots <= 0) {
			d = 0;
		} else if (Difficulty <= HARD) {
			d = GetRandom(Opponent.getType().shieldSlots + 1);
			if (d < Opponent.getType().shieldSlots) {
				if (Tries > 3) {
					++d;
				} else if (Tries > 1) {
					d += GetRandom(2);
				}
			}
		} else {
			d = Opponent.getType().shieldSlots;
		}
		for (i = 0; i < d; ++i) {
			e = 0;
			f = 0;

			while (e < Tries) {
				k = GetRandom(100);
				j = 0;
				sum = Shields.mShields[0].chance;
				while (k < sum) {
					if (j >= MAXSHIELDTYPE - 1) {
						break;
					}
					++j;
					sum += Shields.mShields[j].chance;
				}
				if (j > f) {
					f = j;
				}
				++e;
			}
			Opponent.shield[i] = f;

			j = 0;
			k = 0;
			while (j < 5) {
				e = 1 + GetRandom(Shields.mShields[Opponent.shield[i]].power);
				if (e > k) {
					k = e;
				}
				++j;
			}
			Opponent.shieldStrength[i] = k;
		}
		for (i = d; i < MAXSHIELD; ++i) {
			Opponent.shield[i] = -1;
			Opponent.shieldStrength[i] = 0;
		}

		// Set hull strength
		i = 0;
		k = 0;
		// If there are shields, the hull will probably be stronger
		if (Opponent.shield[0] >= 0 && GetRandom(10) <= 7) {
			Opponent.hull = Opponent.getType().hullStrength;
		} else {
			while (i < 5) {
				d = 1 + GetRandom(Opponent.getType().hullStrength);
				if (d > k) {
					k = d;
				}
				++i;
			}
			Opponent.hull = k;
		}

		if (Opp == MANTIS || Opp == FAMOUSCAPTAIN) {
			Opponent.hull = Opponent.getType().hullStrength;
		}


		// Set the crew. These may be duplicates, or even equal to someone aboard
		// the commander's ship, but who cares, it's just for the skills anyway.
		Opponent.crew[0] = MAXCREWMEMBER;
		Mercenary[Opponent.crew[0]].pilot = 1 + GetRandom(MAXSKILL);
		Mercenary[Opponent.crew[0]].fighter = 1 + GetRandom(MAXSKILL);
		Mercenary[Opponent.crew[0]].trader = 1 + GetRandom(MAXSKILL);
		Mercenary[Opponent.crew[0]].engineer = 1 + GetRandom(MAXSKILL);
		if (WarpSystem == KRAVATSYSTEM && WildStatus == 1 && (GetRandom(10) < Difficulty + 1)) {
			Mercenary[Opponent.crew[0]].engineer = MAXSKILL;
		}
		if (Difficulty <= HARD) {
			d = 1 + GetRandom(Opponent.getType().crewQuarters);
			if (Difficulty >= HARD && d < Opponent.getType().crewQuarters) {
				++d;
			}
		} else {
			d = Opponent.getType().crewQuarters;
		}
		for (i = 1; i < d; ++i) {
			Opponent.crew[i] = GetRandom(MAXCREWMEMBER);
		}
		for (i = d; i < MAXCREW; ++i) {
			Opponent.crew[i] = -1;
		}
	}

	public boolean ENCOUNTERFAMOUS(int a) {
		return ((a) >= FAMOUSCAPTAIN && (a) <= MAXFAMOUSCAPTAIN);
	}

	public boolean ENCOUNTERPOLICE(int a) {
		return ((a) >= POLICE && (a) <= MAXPOLICE);
	}

	public boolean ENCOUNTERPIRATE(int a) {
		return ((a) >= PIRATE && (a) <= MAXPIRATE);
	}

	public boolean ENCOUNTERTRADER(int a) {
		return ((a) >= TRADER && (a) <= MAXTRADER);
	}

	public boolean ENCOUNTERMONSTER(int a) {
		return ((a) >= SPACEMONSTERATTACK && (a) <= MAXSPACEMONSTER);
	}

	public boolean ENCOUNTERDRAGONFLY(int a) {
		return ((a) >= DRAGONFLYATTACK && (a) <= MAXDRAGONFLY);
	}

	public boolean ENCOUNTERSCARAB(int a) {
		return ((a) >= SCARABATTACK && (a) <= MAXSCARAB);
	}

	void IncreaseRandomSkill() {
		// *************************************************************************
		// Increase one of the skills of the commander
		// *************************************************************************
		Boolean Redo;
		int d = 0, oldtraderskill;
		CrewMember COMMANDER = Mercenary[0];

		if (COMMANDER.pilot >= MAXSKILL && COMMANDER.trader >= MAXSKILL &&
			COMMANDER.fighter >= MAXSKILL && COMMANDER.engineer >= MAXSKILL) {
			return;
		}

		oldtraderskill = Ship.TraderSkill();

		Redo = true;
		while (Redo) {
			d = (GetRandom(MAXSKILLTYPE));
			if ((d == 0 && COMMANDER.pilot < MAXSKILL) ||
				(d == 1 && COMMANDER.fighter < MAXSKILL) ||
				(d == 2 && COMMANDER.trader < MAXSKILL) ||
				(d == 3 && COMMANDER.engineer < MAXSKILL)) {
				Redo = false;
			}
		}
		if (d == 0) {
			COMMANDER.pilot += 1;
		} else if (d == 1) {
			COMMANDER.fighter += 1;
		} else if (d == 2) {
			COMMANDER.trader += 1;
			if (oldtraderskill != Ship.TraderSkill()) {
				RecalculateBuyPrices(COMMANDER.curSystem);
			}
		} else {
			COMMANDER.engineer += 1;
		}
	}

	void DecreaseRandomSkill(int amount) {
		// *************************************************************************
		// Decrease one of the skills of the commander
		// *************************************************************************
		Boolean Redo;
		int d = 0, oldtraderskill;
		CrewMember COMMANDER = Mercenary[0];

		if (COMMANDER.pilot >= MAXSKILL && COMMANDER.trader >= MAXSKILL &&
			COMMANDER.fighter >= MAXSKILL && COMMANDER.engineer >= MAXSKILL) {
			return;
		}

		oldtraderskill = Ship.TraderSkill();

		Redo = true;
		while (Redo) {
			d = (GetRandom(MAXSKILLTYPE));
			if ((d == 0 && COMMANDER.pilot > amount) ||
				(d == 1 && COMMANDER.fighter > amount) ||
				(d == 2 && COMMANDER.trader > amount) ||
				(d == 3 && COMMANDER.engineer > amount)) {
				Redo = false;
			}
		}
		if (d == 0) {
			COMMANDER.pilot -= amount;
		} else if (d == 1) {
			COMMANDER.fighter -= amount;
		} else if (d == 2) {
			COMMANDER.trader -= amount;
			if (oldtraderskill != Ship.TraderSkill()) {
				RecalculateBuyPrices(COMMANDER.curSystem);
			}
		} else {
			COMMANDER.engineer -= amount;
		}
	}

	void TonicTweakRandomSkill() {
		// *************************************************************************
		// Randomly tweak one of the skills of the commander
		// *************************************************************************
		int oldPilot, oldFighter, oldTrader, oldEngineer;
		CrewMember COMMANDER = Mercenary[0];
		oldPilot = COMMANDER.pilot;
		oldFighter = COMMANDER.fighter;
		oldTrader = COMMANDER.trader;
		oldEngineer = COMMANDER.engineer;

		if (Difficulty < HARD) {
			// add one to a random skill, subtract one from a random skill
			while (oldPilot == COMMANDER.pilot &&
				oldFighter == COMMANDER.fighter &&
				oldTrader == COMMANDER.trader &&
				oldEngineer == COMMANDER.engineer) {
				IncreaseRandomSkill();
				DecreaseRandomSkill(1);
			}
		} else {
			// add one to two random skills, subtract three from one random skill
			IncreaseRandomSkill();
			IncreaseRandomSkill();
			DecreaseRandomSkill(3);
		}
	}

	// *************************************************************************
	// NthLowest Skill. Returns skill with the nth lowest score
	// (i.e., 2 is the second worst skill). If there is a tie, it will return
	// in the order of Pilot, Fighter, Trader, Engineer.
	// *************************************************************************
	public int NthLowestSkill(Ship Sh, int n) {
		int i = 0, lower = 1, retVal = 0;
		boolean looping = true;
		while (looping) {
			retVal = 0;
			if (Mercenary[Sh.crew[0]].pilot == i) {
				if (lower == n) {
					looping = false;
					retVal = PILOTSKILL;
				}
				lower++;
			}
			if (Mercenary[Sh.crew[0]].fighter == i) {
				if (lower == n) {
					looping = false;
					retVal = FIGHTERSKILL;
				}
				lower++;
			}
			if (Mercenary[Sh.crew[0]].trader == i) {
				if (lower == n) {
					looping = false;
					retVal = TRADERSKILL;
				}
				lower++;
			}
			if (Mercenary[Sh.crew[0]].engineer == i) {
				if (lower == n) {
					looping = false;
					retVal = ENGINEERSKILL;
				}

				lower++;
			}
			i++;
		}
		return retVal;
	}
}