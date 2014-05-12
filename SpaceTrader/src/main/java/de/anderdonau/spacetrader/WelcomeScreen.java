/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.SaveGame;
import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class WelcomeScreen extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	static GameState mGameState;
	private static String mCurrentState = "startup";
	private static Context mContext;
	private static boolean foundSaveGame = false;
	private NavigationDrawerFragment mNavigationDrawerFragment;

	////////////////////////////////////////////////////
	// Helper functions for Newspaper
	////////////////////////////////////////////////////
	public static void addNewsEvent(int eventFlag) {
		if (mGameState.NewsSpecialEventCount < GameState.MAXSPECIALNEWSEVENTS - 1)
			mGameState.NewsEvents[mGameState.NewsSpecialEventCount++] = eventFlag;
	}
	public static boolean isNewsEvent(int eventFlag){
		int i;

		for (i=0;i<mGameState.NewsSpecialEventCount; i++) {
			if (mGameState.NewsEvents[i] == eventFlag)
				return true;
		}
		return false;
	}
	public static int latestNewsEvent(){
		if (mGameState.NewsSpecialEventCount == 0)
			return -1;
		else
			return mGameState.NewsEvents[mGameState.NewsSpecialEventCount - 1];
	}
	public static void resetNewsEvents(){
		mGameState.NewsSpecialEventCount = 0;
	}
	public static void replaceNewsEvent(int originalEventFlag, int replacementEventFlag){
		int i;

		if (originalEventFlag == -1) {
			addNewsEvent(replacementEventFlag);
		} else {
			for (i=0;i<mGameState.NewsSpecialEventCount; i++) {
				if (mGameState.NewsEvents[i] == originalEventFlag)
					mGameState.NewsEvents[i] = replacementEventFlag;
			}
		}
	}

	////////////////////////////////////////////////////
	// Overrides and Android UI support functions
	////////////////////////////////////////////////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WelcomeScreen.mContext = getApplicationContext();
		assert WelcomeScreen.mContext != null;

		setContentView(R.layout.activity_welcome_screen);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new WelcomeScreenFragment()).commit();
		mCurrentState = "WelcomeScreen";

		// Set up the drawer.
		DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mNavigationDrawerFragment = (NavigationDrawerFragment) fragmentManager.findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawer_layout);
		fragmentManager.beginTransaction().hide(mNavigationDrawerFragment).commit();
	}
	@Override
	public void onBackPressed() {
		if (mCurrentState.equals("WelcomeScreen")) {
			finish();
			return;
		}
		if (mCurrentState.equals("startup")) {
			finish();
			return;
		}
		if (mCurrentState.equals("StartNewGame")) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, new WelcomeScreenFragment()).commit();
			mCurrentState = "WelcomeScreen";
			return;
		}
		Log.d("onBackPressed", "unhandled state: " + mCurrentState);
		saveGame();
		finish();
	}
	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		//FragmentManager fragmentManager = getFragmentManager();
		//fragmentManager.beginTransaction().replace(R.id.container, WelcomeScreenFragment.newInstance(position + 1)).commit();
		switch (position) {
			case 0: //"Buy Cargo"
				break;
			case 1: //"Sell Cargo"
				break;
			case 2: // "Shipyard"
				btnShipyard(null);
				break;
			case 3: // "Buy Equipment"
				break;
			case 4: // "Sell Equipment"
				break;
			case 5: // "Personnel Roster"
				btnPersonnelRoster(null);
				break;
			case 6: // "Bank"
				btnBank(null);
				break;
			case 7: // "System Information"
				btnSystemInformation(null);
				break;
			case 8: // "Commander Status"
				btnCommanderStatus(null);
				break;
			case 9: // "Galactic Chart"
				break;
			case 10: // "Short Range Chart"
				break;
		}
	}
	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(mCurrentState);
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		try {
			menu.findItem(R.id.hotkey1).setTitle(mGameState.Shortcuts[mGameState.Shortcut1][0]);
			menu.findItem(R.id.hotkey2).setTitle(mGameState.Shortcuts[mGameState.Shortcut2][0]);
			menu.findItem(R.id.hotkey3).setTitle(mGameState.Shortcuts[mGameState.Shortcut3][0]);
			menu.findItem(R.id.hotkey4).setTitle(mGameState.Shortcuts[mGameState.Shortcut4][0]);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mCurrentState.equals("WelcomeScreen") && !mCurrentState.equals("startup") && !mCurrentState.equals("StartNewGame")) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.in_game, menu);

		}
		if (mNavigationDrawerFragment != null) {
			if (!mNavigationDrawerFragment.isDrawerOpen()) {
				// Only show items in the action bar relevant to this screen
				// if the drawer is not showing. Otherwise, let the drawer
				// decide what to show in the action bar.
				// getMenuInflater().inflate(R.menu.welcome_screen, menu);
				restoreActionBar();
				return true;
			}
		}
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		/*if (id == R.id.action_settings) {
			return true;
		}
		*/
		return super.onOptionsItemSelected(item);
	}

	////////////////////////////////////////////////////
	// Button Callbacks
	////////////////////////////////////////////////////
	public void btnLoadOrStartGame(View view) {
		FragmentManager fragmentManager = getFragmentManager();
		if (foundSaveGame) {
			btnSystemInformation(null);
			fragmentManager.beginTransaction().show(mNavigationDrawerFragment).commit();
		} else {
			fragmentManager.beginTransaction().replace(R.id.container, new StartNewGameFragment()).commit();
			mCurrentState = "StartNewGame";
		}
	}
	public void btnStartNewGame(View view) {
		EditText t = (EditText) findViewById(R.id.strNameCommander);
		SeekBar s = (SeekBar) findViewById(R.id.levelBar);
		mGameState = new GameState(t.getText().toString());
		GameState.Difficulty = s.getProgress();
		s = (SeekBar) findViewById(R.id.skillPilot);
		mGameState.Mercenary[0].pilot = s.getProgress()+1;
		s = (SeekBar) findViewById(R.id.skillFighter);
		mGameState.Mercenary[0].fighter = s.getProgress()+1;
		s = (SeekBar) findViewById(R.id.skillTrader);
		mGameState.Mercenary[0].trader = s.getProgress()+1;
		s = (SeekBar) findViewById(R.id.skillEngineer);
		mGameState.Mercenary[0].engineer = s.getProgress()+1;
		this.saveGame();
		btnSystemInformation(view);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().show(mNavigationDrawerFragment).commit();
	}
	public void btnSystemInformation(View view) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new SystemInformationFragment()).commit();
		mCurrentState = "SystemInformation";
	}
	public void btnSystemInformationNewspaper(View view){
		if (!mGameState.AlreadyPaidForNewspaper && mGameState.ToSpend() < (GameState.Difficulty + 1)){
			alertDialog("Not enough money!", String.format("A newspaper costs %d credits in this system. You don't have enough money!", GameState.Difficulty + 1), "");
		} else if (!mGameState.AlreadyPaidForNewspaper){
			if (!mGameState.NewsAutoPay && !mGameState.AlreadyPaidForNewspaper){
				ConfirmDialog("Buy newspaper", String.format("The local newspaper costs %d credits. Do you wish to buy a copy?", GameState.Difficulty + 1), "If you can't pay the price of a newspaper, you can't get it.\nIf you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.",
          "Yes", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								SystemInformationShowNewspaper();
							}
						}, "No", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {

							}
						});
			} else {
				SystemInformationShowNewspaper();
			}
		} else {
			SystemInformationShowNewspaper();
		}
	}
	public void SystemInformationShowNewspaper(){
		String news = "";
		String masthead;
		int seed;
		int i, j;
		int reply;
		boolean realNews = false;
		CrewMember COMMANDER = mGameState.Mercenary[0];
		int WarpSystem = COMMANDER.curSystem;
		SolarSystem CURSYSTEM = mGameState.SolarSystem[WarpSystem];

		if (!mGameState.AlreadyPaidForNewspaper){
			mGameState.Credits -= (GameState.Difficulty + 1);
			mGameState.AlreadyPaidForNewspaper = true;
		}
		seed = mGameState.GetRandom((int) Math.pow(2, 31));
		mGameState.rand = new Random((mGameState.Mercenary[0].curSystem & GameState.DEFSEEDX) | (mGameState.Days & GameState.DEFSEEDY));

		if (mGameState.NewsPaperNames[CURSYSTEM.politics][WarpSystem%GameState.MAXMASTHEADS].startsWith("*")){
			masthead = String.format("The %s %s", mGameState.SolarSystemName[CURSYSTEM.nameIndex], mGameState.NewsPaperNames[CURSYSTEM.politics][WarpSystem%GameState.MAXMASTHEADS].substring(2));
		} else if (mGameState.NewsPaperNames[CURSYSTEM.politics][WarpSystem%GameState.MAXMASTHEADS].startsWith("+")){
			masthead = String.format("%s %s", mGameState.SolarSystemName[CURSYSTEM.nameIndex], mGameState.NewsPaperNames[CURSYSTEM.politics][WarpSystem%GameState.MAXMASTHEADS].substring(2));
		} else {
			masthead = String.format("%s", mGameState.NewsPaperNames[CURSYSTEM.politics][WarpSystem%GameState.MAXMASTHEADS]);
		}
		
		// Special Events get to go first, crowding out other news
		if  (isNewsEvent(GameState.CAPTAINHUIEATTACKED))
			news += "\nFamed Captain Huie Attacked by Brigand!";
		if  (isNewsEvent(GameState.EXPERIMENTPERFORMED))
			news += "\nTravelers Report Timespace Damage, Warp Problems!";
		if  (isNewsEvent(GameState.CAPTAINHUIEDESTROYED))
			news += "\nCitizens Mourn Destruction of Captain Huie's Ship!";
		if  (isNewsEvent(GameState.CAPTAINAHABATTACKED))
			news += "\nThug Assaults Captain Ahab!";
		if  (isNewsEvent(GameState.CAPTAINAHABDESTROYED))
			news += "\nDestruction of Captain Ahab's Ship Causes Anger!";
		if  (isNewsEvent(GameState.CAPTAINCONRADATTACKED))
			news += "\nCaptain Conrad Comes Under Attack By Criminal!";
		if  (isNewsEvent(GameState.CAPTAINCONRADDESTROYED))
			news += "\nCaptain Conrad's Ship Destroyed by Villain!";
		if  (isNewsEvent(GameState.MONSTERKILLED))
			news += "\nHero Slays Space Monster! Parade, Honors Planned for Today.";
		if  (isNewsEvent(GameState.WILDARRESTED))
			news += "\nNotorious Criminal Jonathan Wild Arrested!";
		if  (CURSYSTEM.special == GameState.MONSTERKILLED && mGameState.MonsterStatus == 1)
			news += "\nSpace Monster Threatens Homeworld!";
		if  (CURSYSTEM.special == GameState.SCARABDESTROYED && mGameState.ScarabStatus == 1)
			news += "\nWormhole Travelers Harassed by Unusual Ship!";
		if (isNewsEvent(GameState.EXPERIMENTSTOPPED))
			news += "\nScientists Cancel High-profile Test! Committee to Investigate Design.";
		if (isNewsEvent(GameState.EXPERIMENTNOTSTOPPED))
			news += "\nHuge Explosion Reported at Research Facility.";
		if (isNewsEvent(GameState.DRAGONFLY))
			news += "\nExperimental Craft Stolen! Critics Demand Security Review.";
		if (isNewsEvent(GameState.SCARAB))
			news += "\nSecurity Scandal: Test Craft Confirmed Stolen.";
		if (isNewsEvent(GameState.FLYBARATAS))
			news += "\nInvestigators Report Strange Craft.";
		if (isNewsEvent(GameState.FLYMELINA))
			news += "\nRumors Continue: Melina Orbitted by Odd Starcraft.";
		if (isNewsEvent(GameState.FLYREGULAS))
			news += "\nStrange Ship Observed in Regulas Orbit.";
		if (CURSYSTEM.special == GameState.DRAGONFLYDESTROYED && mGameState.DragonflyStatus == 4 && !isNewsEvent(GameState.DRAGONFLYDESTROYED))
			news += "\nUnidentified Ship: A Threat to Zalkon?";
		if (isNewsEvent(GameState.DRAGONFLYDESTROYED))
			news += "\nSpectacular Display as Stolen Ship Destroyed in Fierce Space Battle.";
		if (isNewsEvent(GameState.SCARABDESTROYED))
			news += "\nWormhole Traffic Delayed as Stolen Craft Destroyed.";
		if (isNewsEvent(GameState.MEDICINEDELIVERY))
			news += "\nDisease Antidotes Arrive! Health Officials Optimistic.";
		if (isNewsEvent(GameState.JAPORIDISEASE))
			news += "\nEditorial: We Must Help Japori!";
		if (isNewsEvent(GameState.ARTIFACTDELIVERY))
			news += "\nScientist Adds Alien Artifact to Museum Collection.";
		if (isNewsEvent(GameState.JAREKGETSOUT))
			news += "\nAmbassador Jarek Returns from Crisis.";
		if (isNewsEvent(GameState.WILDGETSOUT))
			news += "\nRumors Suggest Known Criminal J. Wild May Come to Kravat!";
		if (isNewsEvent(GameState.GEMULONRESCUED))
			news += "\nInvasion Imminent! Plans in Place to Repel Hostile Invaders.";
		if (CURSYSTEM.special == GameState.GEMULONRESCUED && !isNewsEvent(GameState.GEMULONRESCUED))
			news += "\nAlien Invasion Devastates Planet!";
		if (isNewsEvent(GameState.ALIENINVASION))
			news += "\nEditorial: Who Will Warn Gemulon?";
		if (isNewsEvent(GameState.ARRIVALVIASINGULARITY))
			news += "\nTravelers Claim Sighting of Ship Materializing in Orbit!";

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
		if (mGameState.PoliceRecordScore <= GameState.VILLAINSCORE) {
			j = mGameState.GetRandom(4);
			switch (j) {
				case 0:
					news += "\nPolice Warning: "+mGameState.NameCommander+" Will Dock At "+mGameState.SolarSystemName[CURSYSTEM.nameIndex]+"!";
					break;
				case 1:
					news += "\nNotorious Criminal "+mGameState.NameCommander+" Sighted In "+mGameState.SolarSystemName[CURSYSTEM.nameIndex]+"!";
					break;
				case 2:
					news += "\nLocals Rally to Deny Spaceport Access to "+mGameState.NameCommander+"!";
					break;
				case 3:
					news += "\nTerror Strikes Locals on Arrival of "+mGameState.NameCommander+"!";
					break;
			}
		}

		if (mGameState.PoliceRecordScore == GameState.HEROSCORE) {
			j = mGameState.GetRandom(3);
			switch (j) {
				case 0:
					news += "\nLocals Welcome Visiting Hero "+mGameState.NameCommander+"!";
					break;
				case 1:
					news += "\nFamed Hero "+mGameState.NameCommander+" to Visit System!";
					break;
				case 2:
					news += "\nLarge Turnout At Spaceport to Welcome "+mGameState.NameCommander+"!";
					break;
			}
		}

		// caught littering?
		if  (isNewsEvent(GameState.CAUGHTLITTERING))
			news += "\nPolice Trace Orbiting Space Litter to "+mGameState.NameCommander+".";
		// and now, finally, useful news (if any)
		// base probability of a story showing up is (50 / MAXTECHLEVEL) * Current Tech Level
		// This is then modified by adding 10% for every level of play less than Impossible
		
		for (i=0; i < GameState.MAXSOLARSYSTEM; i++) {
			if (i != COMMANDER.curSystem &&
		    ((mGameState.RealDistance(CURSYSTEM, mGameState.SolarSystem[i]) <= mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].fuelTanks) ||
		     (mGameState.WormholeExists(COMMANDER.curSystem, i))) &&
			    mGameState.SolarSystem[i].status > 0) {
				// Special stories that always get shown: moon, millionaire
				if (mGameState.SolarSystem[i].special == GameState.MOONFORSALE) {
					news += "\nSeller in "+mGameState.SolarSystemName[i]+" System has Utopian Moon available.";
				}
				if (mGameState.SolarSystem[i].special == GameState.BUYTRIBBLE) {
					news += "\nCollector in "+mGameState.SolarSystemName[i]+" System seeks to purchase Tribbles.";
				}

				// And not-always-shown stories
				if (mGameState.GetRandom(100) <= GameState.STORYPROBABILITY * CURSYSTEM.techLevel + 10 * (5 - GameState.Difficulty)) {
					j = mGameState.GetRandom(6);
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
					switch (mGameState.SolarSystem[i].status) {
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
					news += " in the "+mGameState.SolarSystemName[i]+" System.";
					realNews = true;
				}
			}
		}

		// if there's no useful news, we throw up at least one
		// headline from our canned news list.
		if (!realNews) {
			boolean[] shown = new boolean[GameState.MAXSTORIES];
			for (i=0; i< GameState.MAXSTORIES; i++)
				shown[i]= false;
			for (i=0; i <= mGameState.GetRandom(GameState.MAXSTORIES); i++){
				j = mGameState.GetRandom(GameState.MAXSTORIES);
				if (!shown[j] && news.length() <= 150) {
					news += "\n" + GameState.CannedNews[CURSYSTEM.politics][j];
					shown[j] = true;
				}
			}
		}

		while (news.startsWith("\n"))
			news = news.substring(1);

		mGameState.rand = new Random(seed);
		alertDialog(masthead, news, "The local newspaper is a great way to find out what's going on in the area.\nYou may find out about shortages, wars, or other situations at nearby systems.\nThen again, some will tell you that \"no news is good news.\"");
	}
	public void btnPersonnelRoster(View view) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new PersonnelRosterFragment()).commit();
		mCurrentState = "PersonnelRoster";
	}
	public void btnCommanderStatus(View view) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new CommanderStatusFragment()).commit();
		mCurrentState = "CommanderStatus";
	}
	public void btnCommanderStatusQuests(View view){
		String quests = "";
		if (mGameState.MonsterStatus == 1) {
			quests += "Kill the space monster at Acamar.\n";
		}

		if (mGameState.DragonflyStatus >= 1 && mGameState.DragonflyStatus <= 4) {
			quests += "Follow the Dragonfly to ";
			if (mGameState.DragonflyStatus == 1)
				quests += "Baratas.\n";
			else if (mGameState.DragonflyStatus == 2)
				quests += "Melina.\n";
			else if (mGameState.DragonflyStatus == 3)
				quests += "Regulas.\n";
			else if (mGameState.DragonflyStatus == 4)
				quests += "Zalkon.\n";
		} else if (mGameState.SolarSystem[GameState.ZALKONSYSTEM].special == GameState.INSTALLLIGHTNINGSHIELD) {
			quests += "Get your lightning shield at Zalkon.\n";
		}

		if (mGameState.JaporiDiseaseStatus == 1) {
			quests += "Deliver antidote to Japori.\n";
		}

		if (mGameState.ArtifactOnBoard) {
			quests += "Deliver the alien artifact to professor Berger at some hi-tech system.\n";
		}

		if (mGameState.WildStatus == 1) {
			quests += "Smuggle Jonathan Wild to Kravat.\n";
		}

		if (mGameState.JarekStatus == 1) {
			quests += "Bring ambassador Jarek to Devidia.\n";
		}

		// I changed this, and the reused the code in the Experiment quest.
		// I think it makes more sense to display the time remaining in
		// this fashion. SjG 10 July 2002
		if (mGameState.InvasionStatus >= 1 && mGameState.InvasionStatus < 7) {
			quests += "Inform Gemulon about alien invasion";
			if (mGameState.InvasionStatus == 6){
				quests += " by tomorrow";
			} else {
				quests += String.format(" within %d days", mGameState.InvasionStatus);
			}
			quests += ".\n";
		} else if (mGameState.SolarSystem[GameState.GEMULONSYSTEM].special == GameState.GETFUELCOMPACTOR) {
			quests += "Get your fuel compactor at Gemulon.\n";
		}

		if (mGameState.ExperimentStatus >= 1 && mGameState.ExperimentStatus < 11) {
			quests += "Stop Dr. Fehler's experiment at Daled ";

			if (mGameState.ExperimentStatus == 10){
				quests += "by tomorrow";
			} else {
				quests += String.format("within %d days", 11 - mGameState.ExperimentStatus);
			}
			quests += ".\n";
		}

		if (mGameState.ReactorStatus >= 1 && mGameState.ReactorStatus < 21) {
			quests += "Deliver the unstable reactor to Nix ";
			if (mGameState.ReactorStatus < 2) {
				quests += "for Henry Morgan.\n";
			} else {
				quests += "before it consumes all its fuel.\n";
			}
		}

		if (mGameState.SolarSystem[GameState.NIXSYSTEM].special == GameState.GETSPECIALLASER) {
			quests += "Get your special laser at Nix.\n";
		}

		if (mGameState.ScarabStatus == 1) {
			quests += "Find and destroy the Scarab (which is hiding at the exit to a wormhole).\n";
		}

		if (mGameState.Ship.tribbles > 0) {
			quests += "Get rid of those pesky tribbles.\n";
		}

		if (mGameState.MoonBought) {
			quests += "Claim your moon at Utopia.\n";
		}

		if (quests.length() == 0)
			quests = "There are no open quests.\n";

		alertDialog("Open Quests", quests, "");
	}
	public void btnCommanderStatusSpecialCargo(View view) {
		String buf = "";
		if (mGameState.Ship.tribbles > 0) {
			if (mGameState.Ship.tribbles >= GameState.MAXTRIBBLES){
				buf += "An infestation of tribbles.\n";
			} else {
				buf += String.format("%d cute furry tribble%s.\n", mGameState.Ship.tribbles, mGameState.Ship.tribbles == 1 ? "" : "s");
			}
		}

		if (mGameState.JaporiDiseaseStatus == 1) {
			buf += "10 bays of antidote.\n";
		}
		if (mGameState.ArtifactOnBoard) {
			buf += "An alien artifact.\n";
		}
		if (mGameState.JarekStatus == 2) {
			buf += "A haggling computer.\n";
		}
		if (mGameState.ReactorStatus > 0 && mGameState.ReactorStatus < 21) {
			buf += "An unstable reactor taking up 5 bays.\n";
			buf += String.format("%d bay%s of enriched fuel.\n", 10 - ((mGameState.ReactorStatus - 1) / 2), (10 - ((mGameState.ReactorStatus - 1) / 2)) > 1 ? "s" : "");

		}
		if (mGameState.CanSuperWarp) {
			buf += "A Portable Singularity.\n";
		}

		if (buf.length() == 0){
			buf = "No special cargo.";
		}

		alertDialog("Special Cargo", buf, "");
	}
	public void btnCommanderStatusShip(View view){
		int i, j, k, FirstEmptySlot;
		String buf;

		buf = String.format("Type: %s%s\n", mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].name, mGameState.ScarabStatus == 3 ? "/hardened hull" : "");

		buf += "Equipment:\n";

		for (i=0; i<GameState.MAXWEAPONTYPE+GameState.EXTRAWEAPONS; ++i) {
			j = 0;
			for (k=0; k<GameState.MAXWEAPON; ++k) {
				if (mGameState.Ship.weapon[k] == i)
					++j;
			}
			if (j > 0) {
				buf += String.format("%d %s%s\n", j, mGameState.Weapons.mWeapons[i].name, j > 1 ? "s" : "");
			}
		}

		for (i=0; i<GameState.MAXSHIELDTYPE+GameState.EXTRASHIELDS; ++i) {
			j = 0;
			for (k=0; k<GameState.MAXSHIELD; ++k) {
				if (mGameState.Ship.shield[k] == i)
					++j;
			}
			if (j > 0) {
				buf += String.format("%d %s%s\n", j, mGameState.Shields.mShields[i].name, j > 1 ? "s" : "");
			}
		}
		for (i=0; i<GameState.MAXGADGETTYPE+GameState.EXTRAGADGETS; ++i) {
			j = 0;
			for (k=0; k<GameState.MAXGADGET; ++k) {
				if (mGameState.Ship.gadget[k] == i)
					++j;
			}
			if (j > 0) {
				if (i == GameState.EXTRABAYS) {
					buf += String.format("%d extra cargo bays\n", j*5);;
				} else {
					buf += String.format("%s\n", mGameState.Gadgets.mGadgets[i].name);
				}
			}
		}

		if (mGameState.EscapePod) {
			buf += "An escape pod\n";
		}

		if (mGameState.AnyEmptySlots(mGameState.Ship)) {
			buf += "Unfilled:\n";

			FirstEmptySlot = mGameState.GetFirstEmptySlot(mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].weaponSlots, mGameState.Ship.weapon);
			if (FirstEmptySlot >= 0) {
				buf += String.format("%d weapon slot%s\n", mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].weaponSlots - FirstEmptySlot, (mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].weaponSlots - FirstEmptySlot) == 1 ? "" : "s");
			}

			FirstEmptySlot = mGameState.GetFirstEmptySlot(mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].shieldSlots, mGameState.Ship.shield);
			if (FirstEmptySlot >= 0) {
				buf += String.format("%d shield slot%s\n", mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].shieldSlots - FirstEmptySlot, (mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].shieldSlots - FirstEmptySlot) == 1 ? "" : "s");
			}

			FirstEmptySlot = mGameState.GetFirstEmptySlot(mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].gadgetSlots, mGameState.Ship.gadget);
			if (FirstEmptySlot >= 0) {
				buf += String.format("%d gadget slot%s\n", mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].gadgetSlots - FirstEmptySlot, (mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].gadgetSlots - FirstEmptySlot) == 1 ? "" : "s");
			}
		}
		alertDialog("Ship Status", buf, "");
	}
	public void btnBank(View view) {
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new BankFragment()).commit();
		mCurrentState = "Bank";
	}
	public void btnBankGetLoan(View view){
		if (mGameState.Debt >= mGameState.MaxLoan()){
			alertDialog("Debt too high!", "Your debt is too high to get another loan.", "");
			return;
		}
		inputDialog("Get Loan", String.format("How much do you want?\nYou can borrow up to %d credits.", mGameState.MaxLoan()), "Credits", "", new IFinputDialogCallback() {
			@Override
			public void execute(EditText input) {
				try {
					int amount = Integer.parseInt(input.getText().toString());
					if (amount > 0){
						amount = Math.min(mGameState.MaxLoan(), amount);
						mGameState.Credits += amount;
						mGameState.Debt += amount;
						btnBank(null);
					}
				} catch (NumberFormatException e){
					alertDialog("Error", e.getLocalizedMessage().toString(), "");
				}
			}
		});
	}
	public void btnBankPaybackLoan(View view){
		if (mGameState.Debt <= 0){
			alertDialog("No debt.", "You don't have a loan to pay back.", "");
			return;
		}
		inputDialog("Payback Loan", String.format("You have a debt of %d credits.\nHow much do you want to pay back?", mGameState.Debt), "Credits", "", new IFinputDialogCallback() {
			@Override
			public void execute(EditText input) {
				try {
					int amount = Integer.parseInt(input.getText().toString());
					if (amount > 0){
						amount = Math.min(mGameState.Debt, amount);
						if (amount > mGameState.Credits){
							alertDialog("Not enough credits!", String.format("You only have %d credits. You can't pay back more than that!", mGameState.Credits), "");
							return;
						}
						mGameState.Credits -= amount;
						mGameState.Debt -= amount;
						btnBank(null);
					}
				} catch (NumberFormatException e){
					alertDialog("Error", e.getLocalizedMessage().toString(), "");
				}
			}
		});
	}
	public void btnBankBuyInsurance(View view){
		if (mGameState.Insurance){
			ConfirmDialog("Stop Insurance", "Do you really wish to stop your insurance and lose your no-claim?", "", "Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					mGameState.Insurance = false;
					mGameState.NoClaim = 0;
					btnBank(null);
				}
			}, "No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			});
		} else {
			if (!mGameState.EscapePod){
				alertDialog("No Escape Pod", "Insurance isn't useful for you, since you don't have an escape pod.", "");
				return;
			}
			mGameState.Insurance = true;
			btnBank(view);
		}
	}
	public void btnShipyard(View view){
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new ShipyardFragment()).commit();
		mCurrentState = "Shipyard";
	}
	public void btnShipyardBuyFuel(int amount){
		int MaxFuel;
		int Parsecs;

		MaxFuel = (mGameState.GetFuelTanks() - mGameState.GetFuel()) * mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].costOfFuel;
		amount = Math.min(amount, MaxFuel);
		amount = Math.min(amount, mGameState.Credits);

		Parsecs = amount / mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].costOfFuel;

		mGameState.Ship.fuel += Parsecs;
		mGameState.Credits -= Parsecs * mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].costOfFuel;
		btnShipyard(null);
	}
	public void btnShipyardBuyFuel(View view){
		inputDialog("Buy Fuel", "How much do you want to spend maximally on fuel?", "Credits", "Enter the amount of credits you wish to spend on fuel and tap OK. Your fuel tank will be filled with as much fuel as you can buy with that amount of credits.", new IFinputDialogCallback() {
			@Override
			public void execute(EditText input) {
				try {
					int amount = Integer.parseInt(input.getText().toString());
					btnShipyardBuyFuel(amount);
				} catch (NumberFormatException e){
					alertDialog("Error", e.getLocalizedMessage(), "");
				}
			}
		});
	}
	public void btnShipyardBuyMaxFuel(View view){
		btnShipyardBuyFuel(mGameState.Credits);
	}
	public void btnShipyardBuyRepairs(int amount){
		int MaxRepairs;
		int Percentage;

		MaxRepairs = (mGameState.GetHullStrength() - mGameState.Ship.hull) * mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].repairCosts;
		amount = Math.min(amount, MaxRepairs);
		amount = Math.min(amount, mGameState.Credits);

		Percentage = amount / mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].repairCosts;

		mGameState.Ship.hull += Percentage;
		mGameState.Credits -= Percentage * mGameState.ShipTypes.ShipTypes[mGameState.Ship.type].repairCosts;
		btnShipyard(null);
	}
	public void btnShipyardBuyRepairs(View view){
		inputDialog("Buy Repairs", "How much do you want to spend maximally on repairs?", "Credits", "Enter the amount of credits you wish to spend on repairs and tap OK. Your ship will be repaired as much as possible for the amount of credits.", new IFinputDialogCallback() {
			@Override
			public void execute(EditText input) {
				try {
					int amount = Integer.parseInt(input.getText().toString());
					btnShipyardBuyRepairs(amount);
				} catch (NumberFormatException e){
					alertDialog("Error", e.getLocalizedMessage(), "");
				}
			}
		});
	}
	public void btnShipyardBuyFullRepairs(View view){
		btnShipyardBuyRepairs(mGameState.Credits);
	}
	public void btnShipyardBuyEscapePod(View view){
		ConfirmDialog("Buy Escape Pod", "Do you want to buy an escape pod for 2000 credits?", "When your ship has an escape pod, when it is destroyed, you are automatically ejected from it and you will be picked up by the Space Corps after a few days and dropped on a nearby system. You will lose your ship and cargo, but not your life. If you also have taken an insurance on your ship at the bank, the bank will fully refund your ship's costs. Your crew will also be saved in their own escape pods, but they will return to their home systems.",
      "No", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) { }
			},
			"Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					mGameState.Credits -= 2000;
					mGameState.EscapePod = true;
					btnShipyard(null);
				}
			}
		);
	}

	public void saveGame() {
		SaveGame s = new SaveGame(mGameState);

		FileOutputStream fos = null;
		try {
			fos = mContext.openFileOutput("savegame.txt", Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (fos != null) {
			ObjectOutputStream oos = null;
			try {
				oos = new ObjectOutputStream(fos);
				oos.writeObject(s);
				oos.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// Fragments -
	////////////////////////////////////////////////////////////////////////////
	public static class WelcomeScreenFragment extends Fragment {
		public WelcomeScreenFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_welcome_screen, container, false);
			Button btn = (Button) rootView.findViewById(R.id.btnLoadOrStartGame);
			btn.setText(getString(R.string.wScrStartNewGame));

			FileInputStream fis = null;
			try {
				fis = mContext.openFileInput("savegame.txt");
				foundSaveGame = true;
			} catch (FileNotFoundException e) {
				foundSaveGame = false;
			}
			if (foundSaveGame) {
				ObjectInputStream ois = null;
				try {
					ois = new ObjectInputStream(fis);
					SaveGame s = (SaveGame) ois.readObject();
					mGameState = new GameState(s);
					ois.close();
					fis.close();
					btn.setText(mGameState.NameCommander);
				} catch (Exception e) {
					e.printStackTrace();
					mGameState = new GameState("Jameson");
					foundSaveGame = false;
				}
			}

			return rootView;
		}
	}
	public static class StartNewGameFragment extends Fragment {
		public StartNewGameFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_start_new_game, container, false);
			TextView textView = (TextView) rootView.findViewById(R.id.skillPointsLeft);
			mGameState = new GameState("Jameson");
			textView.setText(String.format("%d", mGameState.SkillPointsLeft));

			SeekBar.OnSeekBarChangeListener skillChangeListener = new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
					int skillPilot = ((SeekBar) rootView.findViewById(R.id.skillPilot)).getProgress();
					int skillFighter = ((SeekBar) rootView.findViewById(R.id.skillFighter)).getProgress();
					int skillTrader = ((SeekBar) rootView.findViewById(R.id.skillTrader)).getProgress();
					int skillEngineer = ((SeekBar) rootView.findViewById(R.id.skillEngineer)).getProgress();
					TextView textView = (TextView) rootView.findViewById(R.id.numSkillEngineer);
					textView.setText(String.format("%d", 1 + skillEngineer));
					textView = (TextView) rootView.findViewById(R.id.numSkillPilot);
					textView.setText(String.format("%d", 1 + skillPilot));
					textView = (TextView) rootView.findViewById(R.id.numSkillFighter);
					textView.setText(String.format("%d", 1 + skillFighter));
					textView = (TextView) rootView.findViewById(R.id.numSkillTrader);
					textView.setText(String.format("%d", 1 + skillTrader));
					textView = (TextView) rootView.findViewById(R.id.skillPointsLeft);
					textView.setText(String.format("%d", mGameState.SkillPointsLeft - (skillPilot + skillFighter + skillTrader + skillEngineer)));
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					int skillPilot = ((SeekBar) rootView.findViewById(R.id.skillPilot)).getProgress();
					int skillFighter = ((SeekBar) rootView.findViewById(R.id.skillFighter)).getProgress();
					int skillTrader = ((SeekBar) rootView.findViewById(R.id.skillTrader)).getProgress();
					int skillEngineer = ((SeekBar) rootView.findViewById(R.id.skillEngineer)).getProgress();
					int sum = skillEngineer + skillFighter + skillPilot + skillTrader;
					if (sum > mGameState.SkillPointsLeft) {
						seekBar.setProgress(seekBar.getProgress() - (sum - mGameState.SkillPointsLeft));
						sum = mGameState.SkillPointsLeft;
					}
					Button btn = (Button) (rootView.findViewById(R.id.btnStartGame));
					btn.setEnabled(sum == mGameState.SkillPointsLeft);
				}
			};
			SeekBar.OnSeekBarChangeListener levelChangeListener = new
SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
					TextView textview = (TextView) rootView.findViewById(R.id.levelDescription);
					textview.setText(mGameState.levelDesc[((SeekBar) rootView.findViewById(R.id.levelBar)).getProgress()]);
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

				}
			};
			((SeekBar) rootView.findViewById(R.id.skillEngineer)).setOnSeekBarChangeListener(skillChangeListener);
			((SeekBar) rootView.findViewById(R.id.skillPilot)).setOnSeekBarChangeListener(skillChangeListener);
			((SeekBar) rootView.findViewById(R.id.skillFighter)).setOnSeekBarChangeListener(skillChangeListener);
			((SeekBar) rootView.findViewById(R.id.skillTrader)).setOnSeekBarChangeListener(skillChangeListener);
			((SeekBar) rootView.findViewById(R.id.levelBar)).setOnSeekBarChangeListener(levelChangeListener);

			return rootView;
		}
	}
	public static class SystemInformationFragment extends Fragment {
		public SystemInformationFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_system_information, container, false);
			SolarSystem CURSYSTEM = mGameState.SolarSystem[mGameState.Mercenary[0].curSystem];
			CURSYSTEM.visited = true;

			TextView textView = (TextView) rootView.findViewById(R.id.strSysInfoName);
			textView.setText(mGameState.SolarSystemName[CURSYSTEM.nameIndex]);
			textView = (TextView) rootView.findViewById(R.id.strSysInfoSize);
			textView.setText(mGameState.SystemSize[CURSYSTEM.size]);
			textView = (TextView) rootView.findViewById(R.id.strSysInfoTechLevel);
			textView.setText(mGameState.techLevel[CURSYSTEM.techLevel]);
			textView = (TextView) rootView.findViewById(R.id.strSysInfoGovernment);
			textView.setText(Politics.mPolitics[CURSYSTEM.politics].name);
			textView = (TextView) rootView.findViewById(R.id.strSysInfoResources);
			textView.setText(mGameState.SpecialResources[CURSYSTEM.specialResources]);
			textView = (TextView) rootView.findViewById(R.id.strSysInfoPolice);
			textView.setText(mGameState.Activity[Politics.mPolitics[CURSYSTEM.politics].strengthPolice]);
			textView = (TextView) rootView.findViewById(R.id.strSysInfoPirates);
			textView.setText(mGameState.Activity[Politics.mPolitics[CURSYSTEM.politics].strengthPirates]);
			textView = (TextView) rootView.findViewById(R.id.strCurrentPressure);
			textView.setText(mGameState.Status[CURSYSTEM.status]);

			Button btn = (Button) rootView.findViewById(R.id.btnSpecialEvent);
			if (CURSYSTEM.special > 0 && mGameState.OpenQuests() < 3) {
				btn.setVisibility(View.VISIBLE);
			} else {
				btn.setVisibility(View.INVISIBLE);
			}

			if ((CURSYSTEM.special < 0) ||
							    (CURSYSTEM.special == GameState.BUYTRIBBLE && mGameState.Ship.tribbles <= 0) ||
							    (CURSYSTEM.special == GameState.ERASERECORD && mGameState.PoliceRecordScore >= GameState.DUBIOUSSCORE) ||
							    (CURSYSTEM.special == GameState.CARGOFORSALE && (mGameState.FilledCargoBays() > mGameState.TotalCargoBays() - 3)) ||
							    ((CURSYSTEM.special == GameState.DRAGONFLY || CURSYSTEM.special == GameState.JAPORIDISEASE ||
											      CURSYSTEM.special == GameState.ALIENARTIFACT || CURSYSTEM.special == GameState.AMBASSADORJAREK ||
											      CURSYSTEM.special == GameState.EXPERIMENT) && (mGameState.PoliceRecordScore < GameState.DUBIOUSSCORE)) ||
							    (CURSYSTEM.special == GameState.TRANSPORTWILD && (mGameState.PoliceRecordScore >= GameState.DUBIOUSSCORE)) ||
							    (CURSYSTEM.special == GameState.GETREACTOR && (mGameState.PoliceRecordScore >= GameState.DUBIOUSSCORE || mGameState.ReputationScore < GameState.AVERAGESCORE || mGameState.ReactorStatus != 0)) ||
							    (CURSYSTEM.special == GameState.REACTORDELIVERED && !(mGameState.ReactorStatus > 0 && mGameState.ReactorStatus < 21)) ||
							    (CURSYSTEM.special == GameState.MONSTERKILLED && mGameState.MonsterStatus < 2) ||
							    (CURSYSTEM.special == GameState.EXPERIMENTSTOPPED && !(mGameState.ExperimentStatus >= 1 && mGameState.ExperimentStatus < 12)) ||
							    (CURSYSTEM.special == GameState.FLYBARATAS && mGameState.DragonflyStatus < 1) ||
							    (CURSYSTEM.special == GameState.FLYMELINA && mGameState.DragonflyStatus < 2) ||
							    (CURSYSTEM.special == GameState.FLYREGULAS && mGameState.DragonflyStatus < 3) ||
							    (CURSYSTEM.special == GameState.DRAGONFLYDESTROYED && mGameState.DragonflyStatus < 5) ||
							    (CURSYSTEM.special == GameState.SCARAB && (mGameState.ReputationScore < GameState.AVERAGESCORE || mGameState.ScarabStatus != 0)) ||
							    (CURSYSTEM.special == GameState.SCARABDESTROYED && mGameState.ScarabStatus != 2) ||
							    (CURSYSTEM.special == GameState.GETHULLUPGRADED && mGameState.ScarabStatus != 2) ||
							    (CURSYSTEM.special == GameState.MEDICINEDELIVERY && mGameState.JaporiDiseaseStatus != 1) ||
							    (CURSYSTEM.special == GameState.JAPORIDISEASE && (mGameState.JaporiDiseaseStatus != 0)) ||
							    (CURSYSTEM.special == GameState.ARTIFACTDELIVERY && !mGameState.ArtifactOnBoard) ||
							    (CURSYSTEM.special == GameState.JAREKGETSOUT && mGameState.JarekStatus != 1) ||
							    (CURSYSTEM.special == GameState.WILDGETSOUT && mGameState.WildStatus != 1) ||
							    (CURSYSTEM.special == GameState.GEMULONRESCUED && !(mGameState.InvasionStatus >= 1 && mGameState.InvasionStatus <= 7)) ||
							    (CURSYSTEM.special == GameState.MOONFORSALE && (mGameState.MoonBought || mGameState.CurrentWorth() < (GameState.COSTMOON * 4) / 5)) ||
							    (CURSYSTEM.special == GameState.MOONBOUGHT && !mGameState.MoonBought)) {
				btn.setVisibility(View.INVISIBLE);
			} else if (mGameState.OpenQuests() > 3 && (CURSYSTEM.special == GameState.TRIBBLE ||
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
				if (CURSYSTEM.special == GameState.MONSTERKILLED && mGameState.MonsterStatus == 2)
					addNewsEvent(GameState.MONSTERKILLED);
				else if (CURSYSTEM.special == GameState.DRAGONFLY)
					addNewsEvent(GameState.DRAGONFLY);
				else if (CURSYSTEM.special == GameState.SCARAB)
					addNewsEvent(GameState.SCARAB);
				else if (CURSYSTEM.special == GameState.SCARABDESTROYED && mGameState.ScarabStatus == 2)
					addNewsEvent(GameState.SCARABDESTROYED);
				else if (CURSYSTEM.special == GameState.FLYBARATAS && mGameState.DragonflyStatus == 1)
					addNewsEvent(GameState.FLYBARATAS);
				else if (CURSYSTEM.special == GameState.FLYMELINA && mGameState.DragonflyStatus == 2)
					addNewsEvent(GameState.FLYMELINA);
				else if (CURSYSTEM.special == GameState.FLYREGULAS && mGameState.DragonflyStatus == 3)
					addNewsEvent(GameState.FLYREGULAS);
				else if (CURSYSTEM.special == GameState.DRAGONFLYDESTROYED && mGameState.DragonflyStatus == 5)
					addNewsEvent(GameState.DRAGONFLYDESTROYED);
				else if (CURSYSTEM.special == GameState.MEDICINEDELIVERY && mGameState.JaporiDiseaseStatus == 1)
					addNewsEvent(GameState.MEDICINEDELIVERY);
				else if (CURSYSTEM.special == GameState.ARTIFACTDELIVERY && mGameState.ArtifactOnBoard)
					addNewsEvent(GameState.ARTIFACTDELIVERY);
				else if (CURSYSTEM.special == GameState.JAPORIDISEASE && mGameState.JaporiDiseaseStatus == 0)
					addNewsEvent(GameState.JAPORIDISEASE);
				else if (CURSYSTEM.special == GameState.JAREKGETSOUT && mGameState.JarekStatus == 1)
					addNewsEvent(GameState.JAREKGETSOUT);
				else if (CURSYSTEM.special == GameState.WILDGETSOUT && mGameState.WildStatus == 1)
					addNewsEvent(GameState.WILDGETSOUT);
				else if (CURSYSTEM.special == GameState.GEMULONRESCUED && mGameState.InvasionStatus > 0 && mGameState.InvasionStatus < 8)
					addNewsEvent(GameState.GEMULONRESCUED);
				else if (CURSYSTEM.special == GameState.ALIENINVASION)
					addNewsEvent(GameState.ALIENINVASION);
				else if (CURSYSTEM.special == GameState.EXPERIMENTSTOPPED && mGameState.ExperimentStatus > 0 && mGameState.ExperimentStatus < 12)
					addNewsEvent(GameState.EXPERIMENTSTOPPED);
				else if (CURSYSTEM.special == GameState.EXPERIMENTNOTSTOPPED)
					addNewsEvent(GameState.EXPERIMENTNOTSTOPPED);
			}

			btn = (Button) rootView.findViewById(R.id.btnMercenaryForHire);
			if (mGameState.GetForHire() > -1) {
				btn.setVisibility(View.VISIBLE);
			} else {
				btn.setVisibility(View.INVISIBLE);
			}
			return rootView;
		}
	}
	public static class PersonnelRosterFragment extends Fragment {
		View rootView;

		public PersonnelRosterFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			int i;
			TableLayout tl;
			TextView tv;
			Button btn;
			rootView = inflater.inflate(R.layout.fragment_personnel_roster, container, false);

			for (i = 0; i < 2; ++i) {
				if ((mGameState.JarekStatus == 1 || mGameState.WildStatus == 1) && i < 2) {
					if (mGameState.JarekStatus == 1 && i == 0) { /* Jarek is always in 1st crew slot */
						tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
						tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
						btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
						tl.setVisibility(View.INVISIBLE);
						tv.setText("Jarek's quarters");
						btn.setVisibility(View.INVISIBLE);
						continue;
					} else if (mGameState.JarekStatus == 1 && mGameState.WildStatus == 1 && i == 1) { /* Wild is in 2nd crew slot if Jarek is here, too */
						tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew2);
						tv = (TextView) rootView.findViewById(R.id.txtNameCrew2);
						btn = (Button) rootView.findViewById(R.id.btnFireCrew2);
						tl.setVisibility(View.INVISIBLE);
						tv.setText("Wild's quarters");
						btn.setVisibility(View.INVISIBLE);
						continue;
					} else if (mGameState.WildStatus == 1 && i == 0) {/* Wild is in 1st crew slot if Jarek is not here */
						tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
						tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
						btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
						tl.setVisibility(View.INVISIBLE);
						tv.setText("Wild's quarters");
						btn.setVisibility(View.INVISIBLE);
						continue;
					}
					Log.e("PersonnelRoster", String.format("Impossible Error: Jarek is %d, Wild is %d, here anyway...", mGameState.JarekStatus, mGameState.WildStatus));
				}

				if (i == 0) {
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
				} else {
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew2);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew2);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew2);
				}
				ShipTypes.ShipType Ship = mGameState.ShipTypes.ShipTypes[mGameState.Ship.type];
				if (Ship.crewQuarters <= i + 1) {
					tl.setVisibility(View.INVISIBLE);
					btn.setVisibility(View.INVISIBLE);
					tv.setText("No quarters available");
					continue;
				}

				if (mGameState.Ship.crew[i + 1] < 0) {
					tl.setVisibility(View.INVISIBLE);
					btn.setVisibility(View.INVISIBLE);
					tv.setText("Vacancy");
					continue;
				}

				tl.setVisibility(View.VISIBLE);
				btn.setVisibility(View.VISIBLE);
				DrawMercenary(i, mGameState.Ship.crew[i + 1]); /* Crew Idx 0 is the player */
			}

			int ForHire = mGameState.GetForHire();
			tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrewNew);
			tv = (TextView) rootView.findViewById(R.id.txtNameCrewNew);
			btn = (Button) rootView.findViewById(R.id.btnHireCrewNew);
			if (ForHire < 0) {
				tl.setVisibility(View.INVISIBLE);
				tv.setText("No one for hire");
				btn.setVisibility(View.INVISIBLE);
			} else {
				tl.setVisibility(View.VISIBLE);
				btn.setVisibility(View.VISIBLE);
				DrawMercenary(2, ForHire);
			}

			return rootView;
		}

		public void DrawMercenary(int i, int idxCrewMember) {
			TextView txtPilot;
			TextView txtEngineer;
			TextView txtTrader;
			TextView txtFighter;
			TextView txtName;

			CrewMember c = mGameState.Mercenary[idxCrewMember];

			if (i == 0) {
				txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrew1);
				txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrew1);
				txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrew1);
				txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrew1);
				txtName = (TextView) rootView.findViewById(R.id.txtNameCrew1);
			} else if (i == 1) {
				txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrew2);
				txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrew2);
				txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrew2);
				txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrew2);
				txtName = (TextView) rootView.findViewById(R.id.txtNameCrew2);
			} else /* if (i == 2) */ {
				txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrewNew);
				txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrewNew);
				txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrewNew);
				txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrewNew);
				txtName = (TextView) rootView.findViewById(R.id.txtNameCrewNew);
			}
			txtPilot.setText(String.format("%d", c.pilot));
			txtFighter.setText(String.format("%d", c.fighter));
			txtEngineer.setText(String.format("%d", c.engineer));
			txtTrader.setText(String.format("%d", c.trader));
			txtName.setText(mGameState.MercenaryName[c.nameIndex]);
		}
	}
	public static class CommanderStatusFragment extends Fragment {
		public CommanderStatusFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_commander_status, container, false);
			CrewMember COMMANDER = mGameState.Mercenary[0];
			Ship Ship = mGameState.Ship;
			TextView tv;
			int i;

			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusNameCommander);
			tv.setText(mGameState.NameCommander);

			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusPilot);
			tv.setText(String.format("%d [%d]", COMMANDER.pilot, mGameState.PilotSkill(Ship)));
			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusFighter);
			tv.setText(String.format("%d [%d]", COMMANDER.fighter, mGameState.FighterSkill(Ship)));
			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusTrader);
			tv.setText(String.format("%d [%d]", COMMANDER.trader, mGameState.TraderSkill(Ship)));
			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusEngineer);
			tv.setText(String.format("%d [%d]", COMMANDER.engineer, mGameState.EngineerSkill(Ship)));

			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusKills);
			tv.setText(String.format("%d", mGameState.PirateKills + mGameState.PoliceKills + mGameState.TraderKills));

			i = 0;
			while (i < GameState.MAXPOLICERECORD && mGameState.PoliceRecordScore >= mGameState.PoliceRecord.minScore[i])
				++i;
			--i;
			if (i < 0)
				++i;
			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusPoliceRecord);
			tv.setText(mGameState.PoliceRecord.name[i]);

			i = 0;
			while (i < GameState.MAXREPUTATION && mGameState.ReputationScore >= mGameState.Reputation.minScore[i])
				++i;
			--i;
			if (i < 0)
				i = 0;
			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusReputation);
			tv.setText(mGameState.Reputation.name[i]);

			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusDifficulty);
			tv.setText(mGameState.levelDesc[GameState.Difficulty]);

			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusDays);
			tv.setText(String.format("%d", mGameState.Days));

			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusCash);
			tv.setText(String.format("%d cr.", mGameState.Credits));

			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusDebt);
			tv.setText(String.format("%d cr.", mGameState.Debt));

			tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusWorth);
			tv.setText(String.format("%d cr.", mGameState.CurrentWorth()));

			return rootView;
		}
	}
	public static class BankFragment extends Fragment {
		public BankFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_bank, container, false);
			CrewMember COMMANDER = mGameState.Mercenary[0];
			Ship Ship = mGameState.Ship;
			TextView tv;
			Button btn;

			btn = (Button) rootView.findViewById(R.id.btnBankGetLoan);
			if (mGameState.Debt <= 0) {
				btn.setText("Get Loan");
			}	else {
				btn.setText("Payback Loan");
			}

			btn = (Button) rootView.findViewById(R.id.btnBankBuyInsurance);
			if (mGameState.Insurance) {
				btn.setText("Stop insurance");
			} else {
				btn.setText("Buy insurance");
			}

			tv = (TextView) rootView.findViewById(R.id.txtBankDebt);
			tv.setText(String.format("%d cr.", mGameState.Debt));
			btn = (Button) rootView.findViewById(R.id.btnBankPaybackLoan);
			if (mGameState.Debt <= 0){
				btn.setVisibility(View.INVISIBLE);
			} else {
				btn.setVisibility(View.VISIBLE);
			}

			tv = (TextView) rootView.findViewById(R.id.txtBankMaxDebt);
			tv.setText(String.format("%d cr.", mGameState.MaxLoan()));

			tv = (TextView) rootView.findViewById(R.id.txtBankShipValue);
			tv.setText(String.format("%d cr.", mGameState.CurrentShipPriceWithoutCargo(true)));

			tv = (TextView) rootView.findViewById(R.id.txtBankNoClaim);
			tv.setText(String.format("%d%%%s", Math.min(mGameState.NoClaim, 90), mGameState.NoClaim==90 ? " (maximum)" : ""));

			tv = (TextView) rootView.findViewById(R.id.txtBankCost);
			tv.setText(String.format("%d cr. daily", mGameState.InsuranceMoney()));

			tv = (TextView) rootView.findViewById(R.id.txtBankCash);
			tv.setText(String.format("%d cr.", mGameState.Credits));

			return rootView;
		}
	}
	public static class ShipyardFragment extends Fragment {
		public ShipyardFragment() { }

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_shipyard, container, false);
			CrewMember COMMANDER = mGameState.Mercenary[0];
			SolarSystem CURSYSTEM = mGameState.SolarSystem[COMMANDER.curSystem];
			Ship Ship = mGameState.Ship;
			TextView tv;
			Button btn;

			if (mGameState.GetFuel() < mGameState.GetFuelTanks()) {
				btn = (Button) rootView.findViewById(R.id.btnShipyardBuyFuel);
				btn.setVisibility(View.VISIBLE);
				btn = (Button) rootView.findViewById(R.id.btnShipyardBuyMaxFuel);
				btn.setVisibility(View.VISIBLE);
			} else {
				btn = (Button) rootView.findViewById(R.id.btnShipyardBuyFuel);
				btn.setVisibility(View.INVISIBLE);
				btn = (Button) rootView.findViewById(R.id.btnShipyardBuyMaxFuel);
				btn.setVisibility(View.INVISIBLE);
			}

			if (Ship.hull < mGameState.GetHullStrength()) {
				btn = (Button) rootView.findViewById(R.id.btnShipyardBuyRepairs);
				btn.setVisibility(View.VISIBLE);
				btn = (Button) rootView.findViewById(R.id.btnShipyardBuyFullRepairs);
				btn.setVisibility(View.VISIBLE);
			} else {
				btn = (Button) rootView.findViewById(R.id.btnShipyardBuyRepairs);
				btn.setVisibility(View.INVISIBLE);
				btn = (Button) rootView.findViewById(R.id.btnShipyardBuyFullRepairs);
				btn.setVisibility(View.INVISIBLE);
			}

			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyNewShip);
			if (CURSYSTEM.techLevel >= mGameState.ShipTypes.ShipTypes[0].minTechLevel) {
				btn.setText("Buy New Ship");
			} else {
				btn.setText("Ship Information");
			}

			btn = (Button) rootView.findViewById(R.id.btnShipyardBuyEscapePod);
			if (mGameState.EscapePod || mGameState.ToSpend() < 2000 || CURSYSTEM.techLevel < mGameState.ShipTypes.ShipTypes[0].minTechLevel) {
				btn.setVisibility(View.INVISIBLE);
			} else {
				btn.setVisibility(View.VISIBLE);
			}

			tv = (TextView) rootView.findViewById(R.id.txtShipyardFuelReserve);
			tv.setText(String.format("You have fuel to fly %d parsec%s.", mGameState.GetFuel(), mGameState.GetFuel() == 1 ? "" : "s"));

			tv = (TextView) rootView.findViewById(R.id.txtShipyardFuelCost);
			if (mGameState.GetFuel() < mGameState.GetFuelTanks()) {
				tv.setText(String.format("A full tank costs %d cr.", (mGameState.GetFuelTanks() - mGameState.GetFuel()) * mGameState.ShipTypes.ShipTypes[Ship.type].costOfFuel));
			} else {
				tv.setText("Your tank cannot hold more fuel.");
			}

			tv = (TextView) rootView.findViewById(R.id.txtShipyardHullStrength);
			tv.setText(String.format("Your hull strength is at %d%%.", (Ship.hull * 100) / mGameState.GetHullStrength()));

			tv = (TextView) rootView.findViewById(R.id.txtShipyardRepairsNeeded);
			if (Ship.hull < mGameState.GetHullStrength()) {
				tv.setText(String.format("Full repair will cost %d cr.", (mGameState.GetHullStrength() - Ship.hull) * mGameState.ShipTypes.ShipTypes[Ship.type].repairCosts));
			} else {
				tv.setText("No repairs are needed.");
			}

			tv = (TextView) rootView.findViewById(R.id.txtShipyardNewShipsForSale);
			if (CURSYSTEM.techLevel >= mGameState.ShipTypes.ShipTypes[0].minTechLevel){
				tv.setText("There are new ships for sale.");
			} else {
				tv.setText("No new ships are for sale.");
			}

			tv = (TextView) rootView.findViewById(R.id.txtShipyardCash);
			tv.setText(String.format("%d cr.", mGameState.Credits));

			tv = (TextView) rootView.findViewById(R.id.txtShipyardBuyEscapePod);
			if (mGameState.EscapePod){
				tv.setText("You have an escape pod installed.");
			} else if (CURSYSTEM.techLevel < mGameState.ShipTypes.ShipTypes[0].minTechLevel){
				tv.setText("No escape pods are for sale.");
			} else if (mGameState.ToSpend() < 2000){
				tv.setText("You need 2000 cr. for an escape pod.");
			} else {
				tv.setText("You can buy an escape pod for 2000 cr.");
			}

			return rootView;
		}
	}

	////////////////////////////////////////////////////////////////////////////
	// Helper Functions
	////////////////////////////////////////////////////////////////////////////
	public void ConfirmDialog(String title, String content, final String help, String positiveText, DialogInterface.OnClickListener positiveCallback, String negativeText, DialogInterface.OnClickListener negativeCallback) {
		AlertDialog.Builder confirm = new AlertDialog.Builder(WelcomeScreen.this);
		confirm.setTitle(title);
		confirm.setMessage(content);
		confirm.setPositiveButton(positiveText, positiveCallback);
		confirm.setNegativeButton(negativeText, negativeCallback);
		if (help.length() > 0){
			confirm.setNeutralButton("Help", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			});
		}
		final AlertDialog dialog = confirm.create();
		dialog.show();
		if (help.length() > 0){
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
				Toast.makeText(WelcomeScreen.this, help, Toast.LENGTH_LONG).show();
				// dialog.dismiss();
				}
			});
		}
	}
	public void alertDialog(String title, String content, final String help){
		AlertDialog.Builder confirm = new AlertDialog.Builder(WelcomeScreen.this);
		confirm.setTitle(title);
		confirm.setMessage(content).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) { }
		});
		if (help.length() > 0){
			confirm.setNeutralButton("Help", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			});
		}
		final AlertDialog dialog = confirm.create();
		dialog.show();
		if (help.length() > 0){
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Toast.makeText(WelcomeScreen.this, help, Toast.LENGTH_LONG).show();
					// dialog.dismiss();
				}
			});
		}
	}
	public interface IFinputDialogCallback {
		public void execute(EditText input);
	}
	public void inputDialog(String title, String content, String hint, final String help, final IFinputDialogCallback cb){
		final EditText input = new EditText(WelcomeScreen.this);
		input.setHint(hint);
		input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
		AlertDialog.Builder confirm = new AlertDialog.Builder(WelcomeScreen.this)
				.setTitle(title)
				.setMessage(content)
				.setView(input)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						cb.execute(input);
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// Do nothing.
					}
				});
		if (help.length() > 0){
			confirm.setNeutralButton("Help", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			});
		}
		AlertDialog dialog = confirm.create();
		dialog.show();
		if (help.length() > 0){
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Toast.makeText(WelcomeScreen.this, help, Toast.LENGTH_LONG).show();
					// dialog.dismiss();
				}
			});
		}
	}
}
