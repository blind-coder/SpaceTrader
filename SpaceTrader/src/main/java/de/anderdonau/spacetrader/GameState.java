/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import java.io.Serializable;

/**
 * Created by blindcoder on= 4;23/14.
 */
public class GameState implements Serializable {
	// Special Enables      // Comment these out to disable code
	public final boolean _STRA_CHEAT_ = true; // Cheat Enable
	public final boolean _STRA_SHIPYARDCREDITS_ = true; // Display Trade Credits in Ship Yard
	public final boolean _INCLUDE_DEBUG_DIALOGS_ = true; // Include code for displaying Debug Alerts

	// Add Ships, Weapons, Shields, and Gadgets that don't show up normally
	public final int EXTRAWEAPONS = 1;   // Number of weapons over standard
	public final int EXTRAGADGETS = 1;   // Number of Gadgets over standard
	public final int EXTRASHIELDS = 1;   // Number of Shields over standard
	public final int EXTRASHIPS = 5;  // Number of Ships over standard

	public final int DEFSEEDX = 521288629;
	public final int DEFSEEDY = 362436069;
	public final int MAXTRADEITEM = 10;
	public final int MAXCREDITS = 99999999;
	public final int MAXPRICE = 99999;
	public final int MAXQUANTITY = 999;

	// Activity level of police, traders or pirates
	public final int MAXACTIVITY = 8;

	// System status: normally this is uneventful, but sometimes a system has a
	// special event occurring. This influences some prices.
	public final int MAXSTATUS = 8;
	public final int UNEVENTFUL = 0;
	public final int WAR = 1;
	public final int PLAGUE = 2;
	public final int DROUGHT = 3;
	public final int BOREDOM = 4;
	public final int COLD = 5;
	public final int CROPFAILURE = 6;
	public final int LACKOFWORKERS = 7;

	// Difficulty levels
	public final int MAXDIFFICULTY = 5;
	public final int BEGINNER = 0;
	public final int EASY = 1;
	public final int NORMAL = 2;
	public final int HARD = 3;
	public final int IMPOSSIBLE = 4;

	// Crewmembers. The commander is always the crewmember with index= 0;
	// Zeethibal is always the last
	public final int MAXCREWMEMBER = 31;

	public final int MAXSKILL = 10;

	// Skills
	public final int PILOTSKILL = 0;
	public final int FIGHTERSKILL = 1;
	public final int TRADERSKILL = 2;
	public final int ENGINEERSKILL = 3;
	public final int MAXSKILLTYPE = 4;
	public final int SKILLBONUS = 3;
	public final int CLOAKBONUS = 2;

	// Tradeitems
	public final int WATER = 0;
	public final int FURS = 1;
	public final int FOOD = 2;
	public final int ORE = 3;
	public final int GAMES = 4;
	public final int FIREARMS = 5;
	public final int MEDICINE = 6;
	public final int MACHINERY = 7;
	public final int NARCOTICS = 8;
	public final int ROBOTS = 9;

	// Ship types
	public final int MAXSHIPTYPE = 10;
	public final int MAXRANGE = 20;
	public final int MANTISTYPE = MAXSHIPTYPE + 2;
	public final int SCARABTYPE = MAXSHIPTYPE + 3;
	public final int BOTTLETYPE = MAXSHIPTYPE + 4;

	// Weapons
	public final int MAXWEAPONTYPE = 3;
	public final int PULSELASERWEAPON = 0;
	public final int PULSELASERPOWER = 15;
	public final int BEAMLASERWEAPON = 1;
	public final int BEAMLASERPOWER = 25;
	public final int MILITARYLASERWEAPON = 2;

	public final int MILITARYLASERPOWER = 35;
	public final int MORGANLASERWEAPON = 3;
	public final int MORGANLASERPOWER = 85; // fixme!

	// Shields
	public final int MAXSHIELDTYPE = 2;
	public final int ENERGYSHIELD = 0;
	public final int ESHIELDPOWER = 100;
	public final int REFLECTIVESHIELD = 1;
	public final int RSHIELDPOWER = 200;
	public final int LIGHTNINGSHIELD = 2;
	public final int LSHIELDPOWER = 350;
	// Hull Upgrade
	public final int UPGRADEDHULL = 50;
	// Gadgets
	public final int MAXGADGETTYPE = 5;
	public final int EXTRABAYS = 0;
	public final int AUTOREPAIRSYSTEM = 1;
	public final int NAVIGATINGSYSTEM = 2;
	public final int TARGETINGSYSTEM = 3;
	public final int CLOAKINGDEVICE = 4;
	public final int FUELCOMPACTOR = 5; // MAXGADGETTYPE += 1;
	// Police Action
	public final int POLICE = 0;
	public final int POLICEINSPECTION = 0;// Police asks to submit for inspection
	public final int POLICEIGNORE = 1;// Police just ignores you
	public final int POLICEATTACK = 2;// Police attacks you (sometimes on sight)
	public final int POLICEFLEE = 3;// Police is fleeing
	public final int MAXPOLICE = 9;
	// Pirate Actions
	public final int PIRATE = 10;
	public final int PIRATEATTACK = 10;// Pirate attacks
	public final int PIRATEFLEE = 11;// Pirate flees
	public final int PIRATEIGNORE = 12;// Pirate ignores you (because of cloak)
	public final int PIRATESURRENDER = 13;// Pirate surrenders
	public final int MAXPIRATE = 19;
	// Trader Actions
	public final int TRADER = 20;
	public final int TRADERIGNORE = 20;// Trader passes
	public final int TRADERFLEE = 21;// Trader flees
	public final int TRADERATTACK = 22;// Trader is attacking (after being provoked)
	public final int TRADERSURRENDER = 23;// Trader surrenders
	public final int TRADERSELL = 24;// Trader will sell products in orbit
	public final int TRADERBUY = 25;// Trader will buy products in orbit
	public final int TRADERNOTRADE = 26;// Player has declined to transact with Trader
	public final int MAXTRADER = 29;
	// Space Monster Actions
	public final int SPACEMONSTERATTACK = 30;
	public final int SPACEMONSTERIGNORE = 31;
	public final int MAXSPACEMONSTER = 39;
	// Dragonfly Actions
	public final int DRAGONFLYATTACK = 40;
	public final int DRAGONFLYIGNORE = 41;
	public final int MAXDRAGONFLY = 49;
	public final int MANTIS = 50;
	// Scarab Actions
	public final int SCARABATTACK = 60;
	public final int SCARABIGNORE = 61;
	public final int MAXSCARAB = 69;
	// Famous Captain
	public final int FAMOUSCAPTAIN = 70;
	public final int FAMOUSCAPATTACK = 71;
	public final int CAPTAINAHABENCOUNTER = 72;
	public final int CAPTAINCONRADENCOUNTER = 73;
	public final int CAPTAINHUIEENCOUNTER = 74;
	public final int MAXFAMOUSCAPTAIN = 79;
	// Other Special Encounters
	public final int MARIECELESTEENCOUNTER = 80;
	public final int BOTTLEOLDENCOUNTER = 81;
	public final int BOTTLEGOODENCOUNTER = 82;
	public final int POSTMARIEPOLICEENCOUNTER = 83;
	// The commander's ship
	public final int MAXWEAPON = 3;
	public final int MAXSHIELD = 3;
	public final int MAXGADGET = 3;
	public final int MAXCREW = 3;
	public final int MAXTRIBBLES = 100000;
	// Solar systems
	public final int ACAMARSYSTEM = 0;
	public final int BARATASSYSTEM = 6;
	public final int DALEDSYSTEM = 17;
	public final int DEVIDIASYSTEM = 22;
	public final int GEMULONSYSTEM = 32;
	public final int JAPORISYSTEM = 41;
	public final int KRAVATSYSTEM = 50;
	public final int MELINASYSTEM = 59;
	public final int NIXSYSTEM = 67;
	public final int OGSYSTEM = 70;
	public final int REGULASSYSTEM = 82;
	public final int SOLSYSTEM = 92;
	public final int UTOPIASYSTEM = 109;
	public final int ZALKONSYSTEM = 118;
	// Special events
	public final int COSTMOON = 500000;
	public final int MAXSPECIALEVENT = 37;
	public final int ENDFIXED = 7;
	public final int MAXTEXT = 9;
	public final int DRAGONFLYDESTROYED = 0;
	public final int FLYBARATAS = 1;
	public final int FLYMELINA = 2;
	public final int FLYREGULAS = 3;
	public final int MONSTERKILLED = 4;
	public final int MEDICINEDELIVERY = 5;
	public final int MOONBOUGHT = 6;  // ----- fixed locations precede
	public final int MOONFORSALE = 7;
	public final int SKILLINCREASE = 8;
	public final int TRIBBLE = 9;
	public final int ERASERECORD = 10;
	public final int BUYTRIBBLE = 11;
	public final int SPACEMONSTER = 12;
	public final int DRAGONFLY = 13;
	public final int CARGOFORSALE = 14;
	public final int INSTALLLIGHTNINGSHIELD = 15;
	public final int JAPORIDISEASE = 16;
	public final int LOTTERYWINNER = 17;
	public final int ARTIFACTDELIVERY = 18;
	public final int ALIENARTIFACT = 19;
	public final int AMBASSADORJAREK = 20;
	public final int ALIENINVASION = 21;
	public final int GEMULONINVADED = 22;
	public final int GETFUELCOMPACTOR = 23;
	public final int EXPERIMENT = 24;
	public final int TRANSPORTWILD = 25;
	public final int GETREACTOR = 26;
	public final int GETSPECIALLASER = 27;
	public final int SCARAB = 28;
	public final int GETHULLUPGRADED = 29;  // ------ fixed locations follow
	public final int SCARABDESTROYED = 30;
	public final int REACTORDELIVERED = 31;
	public final int JAREKGETSOUT = 32;
	public final int GEMULONRESCUED = 33;
	public final int EXPERIMENTSTOPPED = 34;
	public final int EXPERIMENTNOTSTOPPED = 35;
	public final int WILDGETSOUT = 36;
	// Max Number of Tribble Buttons
	public final int TRIBBLESONSCREEN = 31;  // Other special events (Encounters)
	// First is probability in= 1000;that one could happen at all:
	public final int CHANCEOFVERYRAREENCOUNTER = 5;
	public final int MAXVERYRAREENCOUNTER = 6;
	public final int MARIECELESTE = 0;
	public final int CAPTAINAHAB = 1;
	public final int CAPTAINCONRAD = 2;
	public final int CAPTAINHUIE = 3;
	public final int BOTTLEOLD = 4;
	public final int BOTTLEGOOD = 5;  // Already done this encounter?
	public final int ALREADYMARIE = 1;
	public final int ALREADYAHAB = 2;
	public final int ALREADYCONRAD = 4;
	public final int ALREADYHUIE = 8;
	public final int ALREADYBOTTLEOLD = 16;
	public final int ALREADYBOTTLEGOOD = 32;
	// Propability in= 1000;that a trader will make offer while in orbit
	public final int CHANCEOFTRADEINORBIT = 100;
	// Political systems (governments)
	public final int MAXPOLITICS = 17;
	public final int MAXSTRENGTH = 8;
	public final int ANARCHY = 0;
	// Tech levels.
	public final int MAXTECHLEVEL = 8;
	// Cargo Dumping Codes. These identify the operation so we can reuse
	// some of the Sell Cargo code.
	// SELL is obvious, Dump is when in dock, Jettison is when in space.
	public final int SELLCARGO = 1;
	public final int DUMPCARGO = 2;
	public final int JETTISONCARGO = 3;
	// System sizes (influences the number of goods available)
	public final int MAXSIZE = 5;
	// Newspaper Mastheads and Headlines have been moved into String Resources, where
	// they belong. Mastheads starting with codes will have the codes replaced as follows:
	// + -> System Name
	// * -> The System Name
	public final int MAXMASTHEADS = 3;      // number of newspaper names per Political situation
	public final int MAXSTORIES = 4;      // number of canned stories per Political situation
	public final int NEWSINDENT1 = 5;      // pixels to indent= 1;t line of news story
	public final int NEWSINDENT2 = 5;      // pixels to indent= 2;d line of news story

	public final int STORYPROBABILITY = 50 / MAXTECHLEVEL; // probability of a story being shown
	public final int MAXSPECIALNEWSEVENTS = 5;              // maximum number of special news events to keep for a system

	// // News Events that don't exactly match special events
	public final int WILDARRESTED = 90;
	public final int CAUGHTLITTERING = 91;
	public final int EXPERIMENTPERFORMED = 92;
	public final int ARRIVALVIASINGULARITY = 93;
	public final int CAPTAINHUIEATTACKED = 100;
	public final int CAPTAINCONRADATTACKED = 101;
	public final int CAPTAINAHABATTACKED = 102;
	public final int CAPTAINHUIEDESTROYED = 110;
	public final int CAPTAINCONRADDESTROYED = 111;
	public final int CAPTAINAHABDESTROYED = 112;
	// Police record
	public final int MAXPOLICERECORD = 10;
	public final int ATTACKPOLICESCORE = -3;
	public final int KILLPOLICESCORE = -6;
	public final int CAUGHTWITHWILDSCORE = -4;
	public final int ATTACKTRADERSCORE = -2;
	public final int PLUNDERTRADERSCORE = -2;
	public final int KILLTRADERSCORE = -4;
	public final int ATTACKPIRATESCORE = 0;
	public final int KILLPIRATESCORE = 1;
	public final int PLUNDERPIRATESCORE = -1;
	public final int TRAFFICKING = -1;
	public final int FLEEFROMINSPECTION = -2;
	public final int TAKEMARIENARCOTICS = -4;

	// Police Record Score
	public final int PSYCHOPATHSCORE = -70;
	public final int VILLAINSCORE = -30;
	public final int CRIMINALSCORE = -10;
	public final int DUBIOUSSCORE = -5;
	public final int CLEANSCORE = 0;
	public final int LAWFULSCORE = 5;
	public final int TRUSTEDSCORE = 10;
	public final int HELPERSCORE = 25;
	public final int HEROSCORE = 75;
	// Reputation (depends on number of kills)
	public final int MAXREPUTATION = 9;
	public final int HARMLESSREP = 0;
	public final int MOSTLYHARMLESSREP = 10;
	public final int POORREP = 20;
	public final int AVERAGESCORE = 40;
	public final int ABOVEAVERAGESCORE = 80;
	public final int COMPETENTREP = 150;
	public final int DANGEROUSREP = 300;
	public final int DEADLYREP = 600;
	public final int ELITESCORE = 1500;
	// Debt Control
	public final int DEBTWARNING = 75000;
	public final int DEBTTOOLARGE = 100000;
	public final int MAXRESOURCES = 13;
	public final int NOSPECIALRESOURCES = 0;
	public final int MINERALRICH = 1;
	public final int MINERALPOOR = 2;
	public final int DESERT = 3;
	public final int LOTSOFWATER = 4;
	public final int RICHSOIL = 5;
	public final int POORSOIL = 6;
	public final int RICHFAUNA = 7;
	public final int LIFELESS = 8;
	public final int WEIRDMUSHROOMS = 9;
	public final int LOTSOFHERBS = 10;
	public final int ARTISTIC = 11;
	public final int WARLIKE = 12;
	// Wormholes
	public final int MAXWORMHOLE = 6;
	public final int GALAXYWIDTH = 150;
	public final int GALAXYHEIGHT = 110;
	public final int SHORTRANGEWIDTH = 160;
	public final int SHORTRANGEHEIGHT = 160;
	public final int SHORTRANGEBOUNDSX = 10;
	public final int BOUNDSX = 5;
	public final int BOUNDSY = 20;
	public final int MINDISTANCE = 6;
	public final int CLOSEDISTANCE = 13;
	public final int WORMHOLEDISTANCE = 3;
	public final int EXTRAERASE = 3;  // (There are new functions for crunching down booleans and
	//  nibbles, which gain us a little room...)
	public final int MAXFORFUTUREUSE = 96;
	// Resources. Some systems have special resources, which influences some prices.
	// this is in percentage, and will decrease by one every day.
	public final int FABRICRIPINITIALPROBABILITY = 25;
	public final int MAXHIGHSCORE = 3;
	public final int KILLED = 0;
	public final int RETIRED = 1;
	public final int MOON = 2;
	// Newspaper Defines

	public int Credits = 1000;            // Current credits owned
	public int Debt = 0;               // Current Debt
	public int[] BuyPrice = new int[MAXTRADEITEM];    // Price list current system
	public int[] BuyingPrice = new int[MAXTRADEITEM]; // Total price paid for trade goods
	public int[] SellPrice = new int[MAXTRADEITEM];   // Price list current system
	public int[] ShipPrice = new int[MAXSHIPTYPE];    // Price list current system (recalculate when buy ship screen is entered)
	public int PoliceKills = 0;           // Number of police ships killed
	public int TraderKills = 0;           // Number of trader ships killed
	public int PirateKills = 0;           // Number of pirate ships killed
	public int PoliceRecordScore = 0;     //= 0;= Clean record
	public int ReputationScore = 0;       //= 0;= Harmless
	public int MonsterHull = 500;         // Hull strength of monster
	public int SkillPointsLeft = 16;      // Skillpoints to distribute at start of game

	public int Days = 0;                   // Number of days playing
	public int WarpSystem = 0;             // Target system for warp
	public int SelectedShipType = 0;       // Selected Ship type for Shiptype Info screen
	public int CheatCounter = 0;
	public int GalacticChartSystem = 0;    // Current system on Galactic chart
	public int EncounterType = 0;          // Type of current encounter
	public int CurForm = 0;                // Form to return to
	public int NoClaim = 0;                // Days of No-Claim
	public int LeaveEmpty = 0;             // Number of cargo bays to leave empty when buying goods
	public int NewsSpecialEventCount = 0;  // Simplifies tracking what Quests have just been initiated or completed for the News System. This is not important enough to get saved.
	public int TrackedSystem = -1;         // The short-range chart will display an arrow towards this system if the value is not -1

	public int Shortcut1 = 0;        // default shortcut= 1;= Buy Cargo
	public int Shortcut2 = 1;        // default shortcut= 2;= Sell Cargo
	public int Shortcut3 = 2;        // default shortcut= 3;= Shipyard
	public int Shortcut4 = 10;       // default shortcut= 4;= Short Range Warp

	public String[][] Shortcuts = {{"B", "Buy Cargo"}, {"S", "Sell Cargo"}, {"Y", "Ship Yard"}, {"E", "Buy Equipment"}, {"Q", "Sell Equipment"}, {"P", "Personnel"}, {"K", "Bank"}, {"I", "System Info"}, {"C", "Commander Status"}, {"G", "Galactic Chart"}, {"W", "Warp Chart"}};

	// the next two values are NOT saved between sessions -- they can only be changed via cheats.
	public int ChanceOfVeryRareEncounter = CHANCEOFVERYRAREENCOUNTER;
	public int ChanceOfTradeInOrbit = CHANCEOFTRADEINORBIT;

	public int MonsterStatus = 0;       //= 0;= Space monster isn't available,= 1;= Space monster is in Acamar system,= 2;= Space monster is destroyed
	public int DragonflyStatus = 0;     //= 0;= Dragonfly not available,= 1;= Go to Baratas,= 2;= Go to Melina,= 3;= Go to Regulas,= 4;= Go to Zalkon,= 5;= Dragonfly destroyed
	public int JaporiDiseaseStatus = 0; //= 0;= No disease,= 1;= Go to Japori (always at least= 10;medicine cannisters),= 2;= Assignment finished or canceled
	public int Difficulty = NORMAL;     // Difficulty level
	public int JarekStatus = 0;         // Ambassador Jarek= 0;not delivered;= 1;on board;= 2;delivered
	public int InvasionStatus = 0;      // Status Alien invasion of Gemulon;= 0;not given yet;= 1;7=days from start;= 8;too late
	public int ExperimentStatus = 0;    // Experiment;= 0;not given yet,1-11 days from start;= 12;performed,= 13;cancelled
	public int FabricRipProbability = 0; // if Experiment == 8; this is the probability of being warped to a random planet.
	public int VeryRareEncounter = 0;     // bit map for which Very Rare Encounter(s) have taken place (see traveler.c, around line= 1850;
	public int WildStatus = 0;    // Jonathan Wild:= 0;not delivered;= 1;on board;= 2;delivered
	public int ReactorStatus = 0;     // Unstable Reactor Status:= 0;not encountered;= 1;20=days of mission (bays of fuel left == 10;- (ReactorStatus/2);= 21;delivered
	public int ScarabStatus = 0;    // Scarab:= 0;not given yet,= 1;not destroyed,= 2;destroyed, upgrade not performed,= 3;destroyed, hull upgrade performed

	public boolean AutoFuel = false;            // Automatically get a full tank when arriving in a new system
	public boolean AutoRepair = false;          // Automatically get a full hull repair when arriving in a new system
	public int Clicks = 0;                  // Distance from target system,= 0;= arrived
	public boolean Raided = false;              // True when the commander has been raided during the trip
	public boolean Inspected = false;           // True when the commander has been inspected during the trip
	public boolean MoonBought = false;          // Indicates whether a moon is available at Utopia
	public boolean EscapePod = false;           // Escape Pod in ship
	public boolean Insurance = false;           // Insurance bought
	public boolean AlwaysIgnoreTraders = false; // Automatically ignores traders when it is safe to do so
	public boolean AlwaysIgnorePolice = true;   // Automatically ignores police when it is safe to do so
	public boolean AlwaysIgnorePirates = false; // Automatically ignores pirates when it is safe to do so
	public boolean AlwaysIgnoreTradeInOrbit = false; // Automatically ignores Trade in Orbit when it is safe to do so
	public boolean ArtifactOnBoard = false;     // Alien artifact on board
	public boolean ReserveMoney = false;        // Keep enough money for insurance and mercenaries
	public boolean PriceDifferences = false;    // Show price differences instead of absolute prices
	public boolean APLscreen = false;           // Is true is the APL screen was last shown after the SRC
	public boolean TribbleMessage = false;      // Is true if the Ship Yard on the current system informed you about the tribbles
	public boolean AlwaysInfo = false;          // Will always go from SRC to Info
	public boolean TextualEncounters = false;   // Show encounters as text.
	public boolean AutoAttack = false;      // Auto-attack mode
	public boolean AutoFlee = false;      // Auto-flee mode
	public boolean Continuous = false;      // Continuous attack/flee mode
	public boolean AttackFleeing = false;     // Continue attack on fleeing ship
	public boolean PossibleToGoThroughRip = false; // if Dr Fehler's experiment happened, we can only go through one space-time rip per warp.
	public boolean UseHWButtons = false;   // by default, don't use Hardware W buttons
	public boolean NewsAutoPay = false;    // by default, ask each time someone buys a newspaper
	public boolean ShowTrackedRange = true;  // display range when tracking a system on Short Range Chart
	public boolean JustLootedMarie = false;    // flag to indicate whether player looted Marie Celeste
	public boolean ArrivedViaWormhole = false; // flag to indicate whether player arrived on current planet via wormhole
	public boolean AlreadyPaidForNewspaper = false; // once you buy a paper on a system, you don't have to pay again.
	public boolean TrackAutoOff = true;    // Automatically stop tracking a system when you get to it?
	public boolean RemindLoans = true;     // remind you every five days about outstanding loan balances
	public boolean CanSuperWarp = false;   // Do you have the Portable Singularity on board?
	public boolean GameLoaded = false;     // Indicates whether a game is loaded
	public boolean LitterWarning = false;    // Warning against littering has been issued.
	public boolean SharePreferences = true;  // Share preferences between switched games.
	public boolean IdentifyStartup = false;  // Identify commander at game start
	public boolean SaveOnArrival = false;    // Autosave when arriving in a system
}
