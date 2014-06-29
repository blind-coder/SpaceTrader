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

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.Popup;
import de.anderdonau.spacetrader.DataTypes.Shields;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;
import de.anderdonau.spacetrader.DataTypes.SpecialEvents;

public class FragmentSystemInformation extends MyFragment {
	Main main;
	String[][] NewsPaperNames = {{"* Arsenal", "The Grassroot", "Kick It!"},    /* Anarchy */
		{"The Daily Worker", "The People's Voice", "* Proletariat"},    /* Capitalist */
		{"Planet News", "* Times", "Interstate Update"},      /* Communist */
		{"The Objectivist", "* Market", "The Invisible Hand"},      /* Confederacy */
		{"+ Memo", "News From The Board", "Status Report"},     /* Corporate */
		{"Pulses", "Binary Stream", "The System Clock"},      /* Cybernetic */
		{"The Daily Planet", "* Majority", "Unanimity"},      /* Democracy */
		{"The Command", "Leader's Voice", "* Mandate"},       /* Dictatorship */
		{"State Tribune", "Motherland News", "Homeland Report"},    /* Fascist */
		{"News from the Keep", "The Town Crier", "* Herald"},     /* Feudal */
		{"General Report", "+ Dispatch", "* Sentry"},       /* Military */
		{"Royal Times", "The Loyal Subject", "The Fanfare"},      /* Monarchy */
		{"Pax Humani", "Principle", "* Chorus"},        /* Pacifist */
		{"All for One", "Brotherhood", "The People's Syndicate"},   /* Socialist */
		{"The Daily Koan", "Haiku", "One Hand Clapping"},     /* Satori */
		{"The Future", "Hardware Dispatch", "TechNews"},      /* Technocracy */
		{"The Spiritual Advisor", "Church Tidings", "The Temple Tribune"},  /* Theocracy */};
	String[][] CannedNews     =
		{{"Riots, Looting Mar Factional Negotiations.", "Communities Seek Consensus.",
			"Successful Bakunin Day Rally!", "Major Faction Conflict Expected for the Weekend!"},
			{"Editorial: Taxes Too High!", "Market Indices Read Record Levels!", "Corporate Profits Up!",
				"Restrictions on Corporate Freedom Abolished by Courts!"},
			{"Party Reports Productivity Increase.",
				"Counter-Revolutionary Bureaucrats Purged from Party!", "Party: Bold New Future Predicted!",
				"Politburo Approves New 5-Year Plan!"},
			{"States Dispute Natural Resource Rights!", "States Denied Federal Funds over Local Laws!",
				"Southern States Resist Federal Taxation for Capital Projects!",
				"States Request Federal Intervention in Citrus Conflict!"},
			{"Robot Shortages Predicted for Q4.", "Profitable Quarter Predicted.",
				"CEO: Corporate Rebranding Progressing.", "Advertising Budgets to Increase."},
			{"Olympics: Software Beats Wetware in All Events!", "New Network Protocols To Be Deployed.",
				"Storage Banks to be Upgraded!", "System Backup Rescheduled."},
			{"Local Elections on Schedule!", "Polls: Voter Satisfaction High!",
				"Campaign Spending Aids Economy!", "Police, Politicians Vow Improvements."},
			{"New Palace Planned; Taxes Increase.", "Future Presents More Opportunities for Sacrifice!",
				"Insurrection Crushed: Rebels Executed!", "Police Powers to Increase!"},
			{"Drug Smugglers Sentenced to Death!",
				"Aliens Required to Carry Visible Identification at All Times!",
				"Foreign Sabotage Suspected.", "Stricter Immigration Laws Installed."},
			{"Farmers Drafted to Defend Lord's Castle!", "Report: Kingdoms Near Flashpoint!",
				"Baron Ignores Ultimatum!", "War of Succession Threatens!"},
			{"Court-Martials Up 2% This Year.", "Editorial: Why Wait to Invade?",
				"HQ: Invasion Plans Reviewed.", "Weapons Research Increases Kill-Ratio!"},
			{"King to Attend Celebrations.", "Queen's Birthday Celebration Ends in Riots!",
				"King Commissions New Artworks.", "Prince Exiled for Palace Plot!"},
			{"Dialog Averts Eastern Conflict! ", "Universal Peace: Is it Possible?",
				"Editorial: Life in Harmony.", "Polls: Happiness Quotient High! "},
			{"Government Promises Increased Welfare Benefits!",
				"State Denies Food Rationing Required to Prevent Famine.",
				"'Welfare Actually Boosts Economy,' Minister Says.", "Hoarder Lynched by Angry Mob!"},
			{"Millions at Peace.", "Sun Rises.", "Countless Hearts Awaken.", "Serenity Reigns."},
			{"New Processor Hits 10 ZettaHerz!", "Nanobot Output Exceeds Expectation.",
				"Last Human Judge Retires.", "Software Bug Causes Mass Confusion."},
			{"High Priest to Hold Special Services.", "Temple Restoration Fund at 81%.",
				"Sacred Texts on Public Display.", "Dozen Blasphemers Excommunicated!"}};
	private GameState gameState;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.main = (Main) activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Bundle args = getArguments();
		this.gameState = (GameState) args.getSerializable("gamestate");

		final View rootView = inflater.inflate(R.layout.fragment_system_information, container, false);
		SolarSystem CURSYSTEM = gameState.SolarSystem[gameState.Mercenary[0].curSystem];
		CURSYSTEM.visited = true;

		TextView textView = (TextView) rootView.findViewById(R.id.strSysInfoName);
		textView.setText(main.SolarSystemName[CURSYSTEM.nameIndex]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoSize);
		textView.setText(main.SystemSize[CURSYSTEM.size]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoTechLevel);
		textView.setText(main.techLevel[CURSYSTEM.techLevel]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoGovernment);
		textView.setText(Politics.mPolitics[CURSYSTEM.politics].name);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoResources);
		textView.setText(main.SpecialResources[CURSYSTEM.specialResources]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoPolice);
		textView.setText(main.Activity[Politics.mPolitics[CURSYSTEM.politics].strengthPolice]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoPirates);
		textView.setText(main.Activity[Politics.mPolitics[CURSYSTEM.politics].strengthPirates]);
		textView = (TextView) rootView.findViewById(R.id.strCurrentPressure);
		textView.setText(main.Status[CURSYSTEM.status]);

		Button btn = (Button) rootView.findViewById(R.id.btnSpecialEvent);
		if (CURSYSTEM.special > 0 && gameState.OpenQuests() < 3) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}

		if ((CURSYSTEM.special < 0) ||
			(CURSYSTEM.special == GameState.BUYTRIBBLE && gameState.Ship.tribbles <= 0) ||
			(CURSYSTEM.special == GameState.ERASERECORD && gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE) ||
			(CURSYSTEM.special == GameState.CARGOFORSALE && (gameState.Ship
				.FilledCargoBays() > gameState.Ship.TotalCargoBays() - 3)) ||
			((CURSYSTEM.special == GameState.DRAGONFLY || CURSYSTEM.special == GameState.JAPORIDISEASE ||
				CURSYSTEM.special == GameState.ALIENARTIFACT || CURSYSTEM.special == GameState.AMBASSADORJAREK ||
				CURSYSTEM.special == GameState.EXPERIMENT) && (gameState.PoliceRecordScore < GameState.DUBIOUSSCORE)) ||
			(CURSYSTEM.special == GameState.TRANSPORTWILD && (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE)) ||
			(CURSYSTEM.special == GameState.GETREACTOR && (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE || gameState.ReputationScore < GameState.AVERAGESCORE || gameState.ReactorStatus != 0)) ||
			(CURSYSTEM.special == GameState.REACTORDELIVERED && !(gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21)) ||
			(CURSYSTEM.special == GameState.MONSTERKILLED && gameState.MonsterStatus < 2) ||
			(CURSYSTEM.special == GameState.EXPERIMENTSTOPPED && !(gameState.ExperimentStatus >= 1 && gameState.ExperimentStatus < 12)) ||
			(CURSYSTEM.special == GameState.FLYBARATAS && gameState.DragonflyStatus < 1) ||
			(CURSYSTEM.special == GameState.FLYMELINA && gameState.DragonflyStatus < 2) ||
			(CURSYSTEM.special == GameState.FLYREGULAS && gameState.DragonflyStatus < 3) ||
			(CURSYSTEM.special == GameState.DRAGONFLYDESTROYED && gameState.DragonflyStatus < 5) ||
			(CURSYSTEM.special == GameState.SCARAB && (gameState.ReputationScore < GameState.AVERAGESCORE || gameState.ScarabStatus != 0)) ||
			(CURSYSTEM.special == GameState.SCARABDESTROYED && gameState.ScarabStatus != 2) ||
			(CURSYSTEM.special == GameState.GETHULLUPGRADED && gameState.ScarabStatus != 2) ||
			(CURSYSTEM.special == GameState.MEDICINEDELIVERY && gameState.JaporiDiseaseStatus != 1) ||
			(CURSYSTEM.special == GameState.JAPORIDISEASE && (gameState.JaporiDiseaseStatus != 0)) ||
			(CURSYSTEM.special == GameState.ARTIFACTDELIVERY && !gameState.ArtifactOnBoard) ||
			(CURSYSTEM.special == GameState.JAREKGETSOUT && gameState.JarekStatus != 1) ||
			(CURSYSTEM.special == GameState.WILDGETSOUT && gameState.WildStatus != 1) ||
			(CURSYSTEM.special == GameState.GEMULONRESCUED && !(gameState.InvasionStatus >= 1 && gameState.InvasionStatus <= 7)) ||
			(CURSYSTEM.special == GameState.MOONFORSALE && (gameState.MoonBought || gameState
				.CurrentWorth() < (GameState.COSTMOON * 4) / 5)) ||
			(CURSYSTEM.special == GameState.MOONBOUGHT && !gameState.MoonBought)) {
			btn.setVisibility(View.INVISIBLE);
		} else if (gameState.OpenQuests() > 3 && (CURSYSTEM.special == GameState.TRIBBLE ||
			CURSYSTEM.special == GameState.SPACEMONSTER ||
			CURSYSTEM.special == GameState.DRAGONFLY ||
			CURSYSTEM.special == GameState.JAPORIDISEASE ||
			CURSYSTEM.special == GameState.ALIENARTIFACT ||
			CURSYSTEM.special == GameState.AMBASSADORJAREK ||
			CURSYSTEM.special == GameState.ALIENINVASION ||
			CURSYSTEM.special == GameState.EXPERIMENT ||
			CURSYSTEM.special == GameState.TRANSPORTWILD ||
			CURSYSTEM.special == GameState.GETREACTOR ||
			CURSYSTEM.special == GameState.SCARAB)) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}

		if (CURSYSTEM.special > -1) {
			if (CURSYSTEM.special == GameState.MONSTERKILLED && gameState.MonsterStatus == 2) {
				gameState.addNewsEvent(GameState.MONSTERKILLED);
			} else if (CURSYSTEM.special == GameState.DRAGONFLY) {
				gameState.addNewsEvent(GameState.DRAGONFLY);
			} else if (CURSYSTEM.special == GameState.SCARAB) {
				gameState.addNewsEvent(GameState.SCARAB);
			} else if (CURSYSTEM.special == GameState.SCARABDESTROYED && gameState.ScarabStatus == 2) {
				gameState.addNewsEvent(GameState.SCARABDESTROYED);
			} else if (CURSYSTEM.special == GameState.FLYBARATAS && gameState.DragonflyStatus == 1) {
				gameState.addNewsEvent(GameState.FLYBARATAS);
			} else if (CURSYSTEM.special == GameState.FLYMELINA && gameState.DragonflyStatus == 2) {
				gameState.addNewsEvent(GameState.FLYMELINA);
			} else if (CURSYSTEM.special == GameState.FLYREGULAS && gameState.DragonflyStatus == 3) {
				gameState.addNewsEvent(GameState.FLYREGULAS);
			} else if (CURSYSTEM.special == GameState.DRAGONFLYDESTROYED && gameState.DragonflyStatus == 5) {
				gameState.addNewsEvent(GameState.DRAGONFLYDESTROYED);
			} else if (CURSYSTEM.special == GameState.MEDICINEDELIVERY && gameState.JaporiDiseaseStatus == 1) {
				gameState.addNewsEvent(GameState.MEDICINEDELIVERY);
			} else if (CURSYSTEM.special == GameState.ARTIFACTDELIVERY && gameState.ArtifactOnBoard) {
				gameState.addNewsEvent(GameState.ARTIFACTDELIVERY);
			} else if (CURSYSTEM.special == GameState.JAPORIDISEASE && gameState.JaporiDiseaseStatus == 0) {
				gameState.addNewsEvent(GameState.JAPORIDISEASE);
			} else if (CURSYSTEM.special == GameState.JAREKGETSOUT && gameState.JarekStatus == 1) {
				gameState.addNewsEvent(GameState.JAREKGETSOUT);
			} else if (CURSYSTEM.special == GameState.WILDGETSOUT && gameState.WildStatus == 1) {
				gameState.addNewsEvent(GameState.WILDGETSOUT);
			} else if (CURSYSTEM.special == GameState.GEMULONRESCUED && gameState.InvasionStatus > 0 && gameState.InvasionStatus < 8) {
				gameState.addNewsEvent(GameState.GEMULONRESCUED);
			} else if (CURSYSTEM.special == GameState.ALIENINVASION) {
				gameState.addNewsEvent(GameState.ALIENINVASION);
			} else if (CURSYSTEM.special == GameState.EXPERIMENTSTOPPED && gameState.ExperimentStatus > 0 && gameState.ExperimentStatus < 12) {
				gameState.addNewsEvent(GameState.EXPERIMENTSTOPPED);
			} else if (CURSYSTEM.special == GameState.EXPERIMENTNOTSTOPPED) {
				gameState.addNewsEvent(GameState.EXPERIMENTNOTSTOPPED);
			}
		}

		btn = (Button) rootView.findViewById(R.id.btnMercenaryForHire);
		if (gameState.GetForHire() > -1) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		return rootView;
	}

	public void showNewspaper() {
		Popup popup;
		if (!gameState.AlreadyPaidForNewspaper && gameState.ToSpend() < (GameState
			.getDifficulty() + 1)) {
			popup = new Popup(main, "Not enough money!", String.format(
				"A newspaper costs %d credits in this system. You don't have enough money!",
				GameState.getDifficulty() + 1), "", "OK", main.cbShowNextPopup);
			main.addPopup(popup);
		} else if (!gameState.AlreadyPaidForNewspaper) {
			if (!gameState.NewsAutoPay && !gameState.AlreadyPaidForNewspaper) {
				popup = new Popup(main, "Buy newspaper", String.format(
					"The local newspaper costs %d credits. Do you wish to buy a copy?",
					GameState.getDifficulty() + 1),
					"If you can't pay the price of a newspaper, you can't get it.\nIf you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.",
					"Yes", "No", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						showNewspaperPopup();
					}
				}, main.cbShowNextPopup
				);
				main.addPopup(popup);
			} else {
				showNewspaperPopup();
			}
		} else {
			showNewspaperPopup();
		}
	}

	void showNewspaperPopup() {
		String news = "";
		String masthead;
		int seed;
		int i, j;
		boolean realNews = false;
		CrewMember COMMANDER = gameState.Mercenary[0];
		int WarpSystem = COMMANDER.curSystem;
		SolarSystem CURSYSTEM = gameState.SolarSystem[WarpSystem];

		if (!gameState.AlreadyPaidForNewspaper) {
			gameState.Credits -= (GameState.getDifficulty() + 1);
			gameState.AlreadyPaidForNewspaper = true;
		}
		seed = gameState.GetRandom((int) Math.pow(2, 31));
		gameState.rand = new Random(
			(gameState.Mercenary[0].curSystem & GameState.DEFSEEDX) | (gameState.Days & GameState.DEFSEEDY));

		if (NewsPaperNames[CURSYSTEM.politics][WarpSystem % GameState.MAXMASTHEADS].startsWith("*")) {
			masthead = String.format("The %s %s", main.SolarSystemName[CURSYSTEM.nameIndex],
				NewsPaperNames[CURSYSTEM.politics][WarpSystem % GameState.MAXMASTHEADS].substring(2));
		} else if (NewsPaperNames[CURSYSTEM.politics][WarpSystem % GameState.MAXMASTHEADS].startsWith(
			"+")) {
			masthead = String.format("%s %s", main.SolarSystemName[CURSYSTEM.nameIndex],
				NewsPaperNames[CURSYSTEM.politics][WarpSystem % GameState.MAXMASTHEADS].substring(2));
		} else {
			masthead = String.format("%s",
				NewsPaperNames[CURSYSTEM.politics][WarpSystem % GameState.MAXMASTHEADS]);
		}

		// Special Events get to go first, crowding out other news
		if (gameState.isNewsEvent(GameState.CAPTAINHUIEATTACKED)) {
			news += "\nFamed Captain Huie Attacked by Brigand!";
		}
		if (gameState.isNewsEvent(GameState.EXPERIMENTPERFORMED)) {
			news += "\nTravelers Report Timespace Damage, Warp Problems!";
		}
		if (gameState.isNewsEvent(GameState.CAPTAINHUIEDESTROYED)) {
			news += "\nCitizens Mourn Destruction of Captain Huie's Ship!";
		}
		if (gameState.isNewsEvent(GameState.CAPTAINAHABATTACKED)) {
			news += "\nThug Assaults Captain Ahab!";
		}
		if (gameState.isNewsEvent(GameState.CAPTAINAHABDESTROYED)) {
			news += "\nDestruction of Captain Ahab's Ship Causes Anger!";
		}
		if (gameState.isNewsEvent(GameState.CAPTAINCONRADATTACKED)) {
			news += "\nCaptain Conrad Comes Under Attack By Criminal!";
		}
		if (gameState.isNewsEvent(GameState.CAPTAINCONRADDESTROYED)) {
			news += "\nCaptain Conrad's Ship Destroyed by Villain!";
		}
		if (gameState.isNewsEvent(GameState.MONSTERKILLED)) {
			news += "\nHero Slays Space Monster! Parade, Honors Planned for Today.";
		}
		if (gameState.isNewsEvent(GameState.WILDARRESTED)) {
			news += "\nNotorious Criminal Jonathan Wild Arrested!";
		}
		if (CURSYSTEM.special == GameState.MONSTERKILLED && gameState.MonsterStatus == 1) {
			news += "\nSpace Monster Threatens Homeworld!";
		}
		if (CURSYSTEM.special == GameState.SCARABDESTROYED && gameState.ScarabStatus == 1) {
			news += "\nWormhole Travelers Harassed by Unusual Ship!";
		}
		if (gameState.isNewsEvent(GameState.EXPERIMENTSTOPPED)) {
			news += "\nScientists Cancel High-profile Test! Committee to Investigate Design.";
		}
		if (gameState.isNewsEvent(GameState.EXPERIMENTNOTSTOPPED)) {
			news += "\nHuge Explosion Reported at Research Facility.";
		}
		if (gameState.isNewsEvent(GameState.DRAGONFLY)) {
			news += "\nExperimental Craft Stolen! Critics Demand Security Review.";
		}
		if (gameState.isNewsEvent(GameState.SCARAB)) {
			news += "\nSecurity Scandal: Test Craft Confirmed Stolen.";
		}
		if (gameState.isNewsEvent(GameState.FLYBARATAS)) {
			news += "\nInvestigators Report Strange Craft.";
		}
		if (gameState.isNewsEvent(GameState.FLYMELINA)) {
			news += "\nRumors Continue: Melina Orbitted by Odd Starcraft.";
		}
		if (gameState.isNewsEvent(GameState.FLYREGULAS)) {
			news += "\nStrange Ship Observed in Regulas Orbit.";
		}
		if (CURSYSTEM.special == GameState.DRAGONFLYDESTROYED && gameState.DragonflyStatus == 4 && !gameState
			.isNewsEvent(GameState.DRAGONFLYDESTROYED)) {
			news += "\nUnidentified Ship: A Threat to Zalkon?";
		}
		if (gameState.isNewsEvent(GameState.DRAGONFLYDESTROYED)) {
			news += "\nSpectacular Display as Stolen Ship Destroyed in Fierce Space Battle.";
		}
		if (gameState.isNewsEvent(GameState.SCARABDESTROYED)) {
			news += "\nWormhole Traffic Delayed as Stolen Craft Destroyed.";
		}
		if (gameState.isNewsEvent(GameState.MEDICINEDELIVERY)) {
			news += "\nDisease Antidotes Arrive! Health Officials Optimistic.";
		}
		if (gameState.isNewsEvent(GameState.JAPORIDISEASE)) {
			news += "\nEditorial: We Must Help Japori!";
		}
		if (gameState.isNewsEvent(GameState.ARTIFACTDELIVERY)) {
			news += "\nScientist Adds Alien Artifact to Museum Collection.";
		}
		if (gameState.isNewsEvent(GameState.JAREKGETSOUT)) {
			news += "\nAmbassador Jarek Returns from Crisis.";
		}
		if (gameState.isNewsEvent(GameState.WILDGETSOUT)) {
			news += "\nRumors Suggest Known Criminal J. Wild May Come to Kravat!";
		}
		if (gameState.isNewsEvent(GameState.GEMULONRESCUED)) {
			news += "\nInvasion Imminent! Plans in Place to Repel Hostile Invaders.";
		}
		if (CURSYSTEM.special == GameState.GEMULONRESCUED && !gameState.isNewsEvent(
			GameState.GEMULONRESCUED) && gameState.InvasionStatus > 0) {
			news += "\nAlien Invasion Devastates Planet!";
		}
		if (gameState.isNewsEvent(GameState.ALIENINVASION)) {
			news += "\nEditorial: Who Will Warn Gemulon?";
		}
		if (gameState.isNewsEvent(GameState.ARRIVALVIASINGULARITY)) {
			news += "\nTravelers Claim Sighting of Ship Materializing in Orbit!";
		}

		// local system status information
		if (CURSYSTEM.status > 0) {
			switch (CURSYSTEM.status) {
				case GameState.WAR:
					news += "\nWar News: Offensives Continue!";
					break;
				case GameState.PLAGUE:
					news += "\nPlague Spreads! Outlook Grim.";
					break;
				case GameState.DROUGHT:
					news += "\nNo Rain in Sight!";
					break;
				case GameState.BOREDOM:
					news += "\nEditors: Won't Someone Entertain Us?";
					break;
				case GameState.COLD:
					news += "\nCold Snap Continues!";
					break;
				case GameState.CROPFAILURE:
					news += "\nSerious Crop Failure! Must We Ration?";
					break;
				case GameState.LACKOFWORKERS:
					news += "\nJobless Rate at All-Time Low!";
					break;
			}
		}
		// character-specific news.
		if (gameState.PoliceRecordScore <= GameState.VILLAINSCORE) {
			j = gameState.GetRandom(4);
			switch (j) {
				case 0:
					news +=
						"\nPolice Warning: " + gameState.NameCommander + " Will Dock At " + main.SolarSystemName[CURSYSTEM.nameIndex] + "!";
					break;
				case 1:
					news +=
						"\nNotorious Criminal " + gameState.NameCommander + " Sighted In " + main.SolarSystemName[CURSYSTEM.nameIndex] + "!";
					break;
				case 2:
					news += "\nLocals Rally to Deny Spaceport Access to " + gameState.NameCommander + "!";
					break;
				case 3:
					news += "\nTerror Strikes Locals on Arrival of " + gameState.NameCommander + "!";
					break;
			}
		}

		if (gameState.PoliceRecordScore == GameState.HEROSCORE) {
			j = gameState.GetRandom(3);
			switch (j) {
				case 0:
					news += "\nLocals Welcome Visiting Hero " + gameState.NameCommander + "!";
					break;
				case 1:
					news += "\nFamed Hero " + gameState.NameCommander + " to Visit System!";
					break;
				case 2:
					news += "\nLarge Turnout At Spaceport to Welcome " + gameState.NameCommander + "!";
					break;
			}
		}

		// caught littering?
		if (gameState.isNewsEvent(GameState.CAUGHTLITTERING)) {
			news += "\nPolice Trace Orbiting Space Litter to " + gameState.NameCommander + ".";
		}
		// and now, finally, useful news (if any)
		// base probability of a story showing up is (50 / MAXTECHLEVEL) * Current Tech Level
		// This is then modified by adding 10% for every level of play less than Impossible

		for (i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			if (i != COMMANDER.curSystem &&
				((gameState.RealDistance(CURSYSTEM,
					gameState.SolarSystem[i]) <= ShipTypes.ShipTypes[gameState.Ship.type].fuelTanks) || (gameState
					.WormholeExists(COMMANDER.curSystem, i))) &&
				gameState.SolarSystem[i].status > 0) {
				// Special stories that always get shown: moon, millionaire
				if (gameState.SolarSystem[i].special == GameState.MOONFORSALE) {
					news += "\nSeller in " + main.SolarSystemName[i] + " System has Utopian Moon available.";
				}
				if (gameState.SolarSystem[i].special == GameState.BUYTRIBBLE) {
					news +=
						"\nCollector in " + main.SolarSystemName[i] + " System seeks to purchase Tribbles.";
				}

				// And not-always-shown stories
				if (gameState.GetRandom(
					100) <= GameState.STORYPROBABILITY * CURSYSTEM.techLevel + 10 * (5 - GameState
					.getDifficulty())) {
					j = gameState.GetRandom(6);
					switch (j) {
						case 0:
							news += "\nReports of ";
							break;
						case 1:
							news += "\nNews of ";
							break;
						case 2:
							news += "\nNew Rumors of ";
							break;
						case 3:
							news += "\nSources say ";
							break;
						case 4:
							news += "\nNotice: ";
							break;
						case 5:
							news += "\nEvidence Suggests ";
							break;
					}
					switch (gameState.SolarSystem[i].status) {
						case GameState.WAR:
							news += "Strife and War";
							break;
						case GameState.PLAGUE:
							news += "Plague Outbreaks";
							break;
						case GameState.DROUGHT:
							news += "Severe Drought";
							break;
						case GameState.BOREDOM:
							news += "Terrible Boredom";
							break;
						case GameState.COLD:
							news += "Cold Weather";
							break;
						case GameState.CROPFAILURE:
							news += "Crop Failures";
							break;
						case GameState.LACKOFWORKERS:
							news += "Labor Shortages";
							break;
					}
					news += " in the " + main.SolarSystemName[i] + " System.";
					realNews = true;
				}
			}
		}

		// if there's no useful news, we throw up at least one
		// headline from our canned news list.
		if (!realNews) {
			boolean[] shown = new boolean[GameState.MAXSTORIES];
			for (i = 0; i < GameState.MAXSTORIES; i++) {
				shown[i] = false;
			}
			for (i = 0; i <= gameState.GetRandom(GameState.MAXSTORIES); i++) {
				j = gameState.GetRandom(GameState.MAXSTORIES);
				if (!shown[j] && news.length() <= 150) {
					news += "\n" + CannedNews[CURSYSTEM.politics][j];
					shown[j] = true;
				}
			}
		}

		while (news.startsWith("\n")) {
			news = news.substring(1);
		}

		gameState.rand = new Random(seed);
		Popup popup;
		popup = new Popup(main, masthead, news,
			"The local newspaper is a great way to find out what's going on in the area.\nYou may find out about shortages, wars, or other situations at nearby systems.\nThen again, some will tell you that \"no news is good news.\"",
			"OK", main.cbShowNextPopup);
		main.addPopup(popup);
	}

	public void special() {
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		SpecialEvents.SpecialEvent Event = SpecialEvents.mSpecialEvent[CURSYSTEM.special];
		Popup popup;

		if (Event.justAMessage) {
			popup = new Popup(main, Event.title, Event.questStringID, "", "OK", main.cbShowNextPopup);
			main.addPopup(popup);
			specialStep2();
		} else {
			popup = new Popup(main, Event.title, Event.questStringID, "", "Yes", "No",
				new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						specialStep2();
					}
				}, main.cbShowNextPopup
			);
			main.addPopup(popup);
		}
	}

	void specialStep2() {
		int i, FirstEmptySlot;
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		SpecialEvents.SpecialEvent Event = SpecialEvents.mSpecialEvent[CURSYSTEM.special];
		Popup popup;

		if (gameState.ToSpend() < Event.price) {
			popup = new Popup(main, "Not Enough Money",
				"You don't have enough cash to spend to accept this offer.", "", "OK",
				main.cbShowNextPopup);
			main.addPopup(popup);
			return;
		}

		switch (CURSYSTEM.special) {
			case GameState.GETREACTOR:
				if (gameState.Ship.FilledCargoBays() > gameState.Ship.TotalCargoBays() - 15) {
					popup = new Popup(main, "Not Enough Bays",
						"You don't have enough empty cargo bays at the moment.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				} else if (gameState.WildStatus == 1) {
					popup = new Popup(main, "Wild Won't Stay Aboard", String.format(
						"Jonathan Wild isn't willing to go with you if you bring that Reactor on board. He'd rather take his chances hiding out here on %s.",
						main.SolarSystemName[CURSYSTEM.nameIndex]), "", "Goodbye Wild", "Leave Reactor",
						new Popup.buttonCallback() {
							@Override
							public void execute(Popup popup, View view) {
								gameState.WildStatus = 0;
								Popup popup1 = new Popup(popup.context, "Say Goodbye to Wild",
									"Since Jonathan Wild is not willing to travel under these conditions, and you're not willing to change the situation, he leaves you and goes into hiding on this system.",
									"", "OK", main.cbShowNextPopup);
								gameState.ReactorStatus = 1;
								main.addPopup(popup1);
							}
						}, main.cbShowNextPopup
					);
					main.addPopup(popup);
					return;
				}
				gameState.ReactorStatus = 1;
				break;
			case GameState.REACTORDELIVERED:
				CURSYSTEM.special = GameState.GETSPECIALLASER;
				gameState.ReactorStatus = 21;
				main.changeFragment(Main.FRAGMENTS.SYSTEM_INFORMATION);
				return;
			case GameState.MONSTERKILLED:
				break;
			case GameState.SCARAB:
				gameState.ScarabStatus = 1;
				break;
			case GameState.SCARABDESTROYED:
				gameState.ScarabStatus = 2;
				CURSYSTEM.special = GameState.GETHULLUPGRADED;
				main.changeFragment(Main.FRAGMENTS.SYSTEM_INFORMATION);
				return;
			case GameState.GETHULLUPGRADED:
				popup = new Popup(main, "Hull Upgraded",
					"Technicians spend the day retrofitting the hull of your ship.",
					"Technicians spent the day replacing welds and bolts, and adding materials to your ship. When they're done, they tell you your ship should be significantly sturdier.",
					"OK", main.cbShowNextPopup);
				main.addPopup(popup);
				gameState.Ship.hull += GameState.UPGRADEDHULL;
				gameState.ScarabStatus = 3;
				break;

			case GameState.EXPERIMENT:
				gameState.ExperimentStatus = 1;
				break;

			case GameState.EXPERIMENTSTOPPED:
				gameState.ExperimentStatus = 13;
				gameState.CanSuperWarp = true;
				break;

			case GameState.EXPERIMENTNOTSTOPPED:
				break;

			case GameState.ARTIFACTDELIVERY:
				gameState.ArtifactOnBoard = false;
				break;

			case GameState.ALIENARTIFACT:
				gameState.ArtifactOnBoard = true;
				break;

			case GameState.FLYBARATAS:
			case GameState.FLYMELINA:
			case GameState.FLYREGULAS:
				++gameState.DragonflyStatus;
				break;

			case GameState.DRAGONFLYDESTROYED:
				CURSYSTEM.special = GameState.INSTALLLIGHTNINGSHIELD;
				main.changeFragment(Main.FRAGMENTS.SYSTEM_INFORMATION);
				return;

			case GameState.GEMULONRESCUED:
				CURSYSTEM.special = GameState.GETFUELCOMPACTOR;
				gameState.InvasionStatus = 0;
				main.changeFragment(Main.FRAGMENTS.SYSTEM_INFORMATION);
				return;

			case GameState.MEDICINEDELIVERY:
				gameState.JaporiDiseaseStatus = 2;
				gameState.IncreaseRandomSkill();
				gameState.IncreaseRandomSkill();
				break;

			case GameState.MOONFORSALE:
				gameState.MoonBought = true;
				popup = new Popup(main, "Moon Bought",
					"You bought a moon in the Utopia system. Go there to claim it.", "", "OK",
					main.cbShowNextPopup);
				main.addPopup(popup);
				break;

			case GameState.MOONBOUGHT:
				main.EndOfGame(GameState.MOON);
				return;

			case GameState.SKILLINCREASE:
				popup = new Popup(main, "Skill Increase", "The alien increases one of your skills.", "",
					"OK", main.cbShowNextPopup);
				main.addPopup(popup);
				gameState.IncreaseRandomSkill();
				break;

			case GameState.TRIBBLE:
				popup = new Popup(main, "A Tribble",
					"You are now the proud owner of a little, cute, furry tribble.",
					"The merchant prince sold you a cute, furry tribble. You can see your new acquisition on the Commander Status screen.",
					"OK", main.cbShowNextPopup);
				main.addPopup(popup);
				gameState.Ship.tribbles = 1;
				break;

			case GameState.BUYTRIBBLE:
				popup = new Popup(main, "No More Tribbles",
					"The alien uses his alien technology to beam over your whole collection of tribbles to his ship.",
					"No more tribbles!", "OK", main.cbShowNextPopup);
				main.addPopup(popup);
				gameState.Credits += (gameState.Ship.tribbles >> 1);
				gameState.Ship.tribbles = 0;
				break;

			case GameState.ERASERECORD:
				popup = new Popup(main, "Clean Record", "The hacker resets your police record to Clean.",
					"", "OK", main.cbShowNextPopup);
				main.addPopup(popup);
				gameState.PoliceRecordScore = GameState.CLEANSCORE;
				gameState.RecalculateSellPrices();
				break;

			case GameState.SPACEMONSTER:
				gameState.MonsterStatus = 1;
				break;

			case GameState.DRAGONFLY:
				gameState.DragonflyStatus = 1;
				break;

			case GameState.AMBASSADORJAREK:
				if (gameState.Ship.crew[ShipTypes.ShipTypes[gameState.Ship.type].crewQuarters - 1] >= 0) {
					popup = new Popup(main, "No Quarters Available",
						"You do not have any crew quarters available for Ambassador Jarek.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				}
				popup = new Popup(main, "Passenger On Board", "You have taken Ambassador Jarek on board.",
					"", "OK", main.cbShowNextPopup);
				main.addPopup(popup);
				gameState.JarekStatus = 1;
				break;

			case GameState.TRANSPORTWILD:
				if (gameState.Ship.crew[ShipTypes.ShipTypes[gameState.Ship.type].crewQuarters - 1] >= 0) {
					popup = new Popup(main, "No Quarters Available",
						"You do not have any crew quarters available for Jonathan Wild.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				}
				if (!gameState.Ship.HasWeapon(GameState.BEAMLASERWEAPON, false)) {
					popup = new Popup(main, "Wild Won't Stay Aboard", String.format(
						"Jonathan Wild isn't about to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here on %s.",
						main.SolarSystemName[CURSYSTEM.nameIndex]), "", "OK", main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				}
				if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
					popup = new Popup(main, "Wild Won't Board Ship",
						"Jonathan Wild doesn't like the looks of that Ion Reactor. He thinks it's too dangerous, and won't get on board.",
						"The Ion Reactor is known to be unstable, and Jonathan Wild is trying to get to safety. He's not willing to get on the ship le the Reactor's on board.",
						"OK", main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				}
				popup = new Popup(main, "Passenger On Board", "You have taken Jonathan Wild on board.", "",
					"OK", main.cbShowNextPopup);
				main.addPopup(popup);
				gameState.WildStatus = 1;
				break;

			case GameState.ALIENINVASION:
				gameState.InvasionStatus = 1;
				break;

			case GameState.JAREKGETSOUT:
				gameState.JarekStatus = 2;
				gameState.RecalculateBuyPrices(COMMANDER.curSystem);
				break;

			case GameState.WILDGETSOUT:
				gameState.WildStatus = 2;
				gameState.Mercenary[GameState.MAXCREWMEMBER - 1].curSystem = GameState.KRAVATSYSTEM;
				// Zeethibal has a 10 in player's lowest score, an 8
				// in the next lowest score, and 5 elsewhere.
				gameState.Mercenary[GameState.MAXCREWMEMBER - 1].pilot = 5;
				gameState.Mercenary[GameState.MAXCREWMEMBER - 1].fighter = 5;
				gameState.Mercenary[GameState.MAXCREWMEMBER - 1].trader = 5;
				gameState.Mercenary[GameState.MAXCREWMEMBER - 1].engineer = 5;
				switch (gameState.NthLowestSkill(gameState.Ship, 1)) {
					case GameState.PILOTSKILL:
						gameState.Mercenary[GameState.MAXCREWMEMBER - 1].pilot = 10;
						break;
					case GameState.FIGHTERSKILL:
						gameState.Mercenary[GameState.MAXCREWMEMBER - 1].fighter = 10;
						break;
					case GameState.TRADERSKILL:
						gameState.Mercenary[GameState.MAXCREWMEMBER - 1].trader = 10;
						break;
					case GameState.ENGINEERSKILL:
						gameState.Mercenary[GameState.MAXCREWMEMBER - 1].engineer = 10;
						break;
				}
				switch (gameState.NthLowestSkill(gameState.Ship, 2)) {
					case GameState.PILOTSKILL:
						gameState.Mercenary[GameState.MAXCREWMEMBER - 1].pilot = 8;
						break;
					case GameState.FIGHTERSKILL:
						gameState.Mercenary[GameState.MAXCREWMEMBER - 1].fighter = 8;
						break;
					case GameState.TRADERSKILL:
						gameState.Mercenary[GameState.MAXCREWMEMBER - 1].trader = 8;
						break;
					case GameState.ENGINEERSKILL:
						gameState.Mercenary[GameState.MAXCREWMEMBER - 1].engineer = 8;
						break;
				}

				if (gameState.PoliceRecordScore < GameState.CLEANSCORE) {
					gameState.PoliceRecordScore = GameState.CLEANSCORE;
				}
				break;

			case GameState.CARGOFORSALE:
				popup = new Popup(main, "Sealed Canisters",
					"You bought the sealed canisters and put them in your cargo bays.", "", "OK",
					main.cbShowNextPopup);
				main.addPopup(popup);
				i = gameState.GetRandom(GameState.MAXTRADEITEM);
				gameState.Ship.cargo[i] += 3;
				gameState.BuyingPrice[i] += Event.price;
				break;

			case GameState.INSTALLLIGHTNINGSHIELD:
				FirstEmptySlot = gameState.GetFirstEmptySlot(
					ShipTypes.ShipTypes[gameState.Ship.type].shieldSlots, gameState.Ship.shield);
				if (FirstEmptySlot < 0) {
					popup = new Popup(main, "Not Enough Slots",
						"You have already filled all of your available slots for this type of item.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				} else {
					popup = new Popup(main, "Lightning Shield",
						"You now have one lightning shield installed on your ship.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					gameState.Ship.shield[FirstEmptySlot] = GameState.LIGHTNINGSHIELD;
					gameState.Ship.shieldStrength[FirstEmptySlot] =
						Shields.mShields[GameState.LIGHTNINGSHIELD].power;
				}
				break;

			case GameState.GETSPECIALLASER:
				FirstEmptySlot = gameState.GetFirstEmptySlot(
					ShipTypes.ShipTypes[gameState.Ship.type].weaponSlots, gameState.Ship.weapon);
				if (FirstEmptySlot < 0) {
					popup = new Popup(main, "Not Enough Slots",
						"You have already filled all of your available slots for this type of item.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				} else {
					popup = new Popup(main, "Morgan's Laser",
						"You now have Henry Morgan's special laser installed on your ship.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					gameState.Ship.weapon[FirstEmptySlot] = GameState.MORGANLASERWEAPON;
				}
				break;

			case GameState.GETFUELCOMPACTOR:
				FirstEmptySlot = gameState.GetFirstEmptySlot(
					ShipTypes.ShipTypes[gameState.Ship.type].gadgetSlots, gameState.Ship.gadget);
				if (FirstEmptySlot < 0) {
					popup = new Popup(main, "Not Enough Slots",
						"You have already filled all of your available slots for this type of item.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				} else {
					popup = new Popup(main, "Fuel Compactor",
						"You now have a fuel compactor installed on your ship.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					gameState.Ship.gadget[FirstEmptySlot] = GameState.FUELCOMPACTOR;
					gameState.Ship.fuel = gameState.Ship.GetFuelTanks();
				}
				break;

			case GameState.JAPORIDISEASE:
				if (gameState.Ship.FilledCargoBays() > gameState.Ship.TotalCargoBays() - 10) {
					popup = new Popup(main, "Not Enough Bays",
						"You don't have enough empty cargo bays at the moment.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					return;
				} else {
					popup = new Popup(main, "Antidote",
						"Ten of your cargo bays now contain antidote for the Japori system.", "", "OK",
						main.cbShowNextPopup);
					main.addPopup(popup);
					gameState.JaporiDiseaseStatus = 1;
				}
				break;
		}
		gameState.Credits -= Event.price;
		CURSYSTEM.special = -1;
		main.changeFragment(Main.FRAGMENTS.SYSTEM_INFORMATION);
	}
}