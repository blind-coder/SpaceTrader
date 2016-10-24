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

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.Gadgets;
import de.anderdonau.spacetrader.DataTypes.HighScore;
import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.Popup;
import de.anderdonau.spacetrader.DataTypes.PopupQueue;
import de.anderdonau.spacetrader.DataTypes.SaveGame_v110;
import de.anderdonau.spacetrader.DataTypes.SaveGame_v111;
import de.anderdonau.spacetrader.DataTypes.SaveGame_v120;
import de.anderdonau.spacetrader.DataTypes.Shields;
import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;
import de.anderdonau.spacetrader.DataTypes.Tradeitems;
import de.anderdonau.spacetrader.DataTypes.Weapons;

public class Main extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks, Serializable {
	public enum FRAGMENTS {
		AVERAGE_PRICES,
		BANK,
		BUY_CARGO,
		BUY_EQUIPMENT,
		BUY_NEW_SHIP,
		COMMANDER_STATUS,
		DUMP,
		ENCOUNTER,
		GALACTIC_CHART,
		NEW_GAME,
		OPTIONS,
		PERSONNEL_ROSTER,
		PLUNDER,
		SELL_CARGO,
		SELL_EQUIPMENT,
		SHIPYARD,
		SHIP_INFO,
		SHORTCUTS,
		SHORT_RANGE_CHART,
		SYSTEM_INFORMATION,
		VERY_RARE_CHEAT,
		WARP_SYSTEM_INFORMATION
	}

	private Context mContext;
	private MyFragment currentFragment = null;
	private NavigationDrawerFragment mNavigationDrawerFragment;
	public  SolarSystem              WarpSystem;
	private GameState                gameState;
	// InterstitialAd interstitial;

	private PopupQueue           popupQueue      = new PopupQueue();
	final   Popup.buttonCallback cbShowNextPopup = new Popup.buttonCallback() {
		@Override
		public void execute(Popup popup, View view) {
			Main.this.showNextPopup();
		}
	};

	public final String[]   levelDesc        =
		new String[]{"Beginner", "Easy", "Normal", "Hard", "Impossible"};
	final        String[]   Status           = {"under no particular pressure",  // Uneventful
		"at war",        // Ore and Weapons in demand
		"ravaged by a plague",   // Medicine in demand
		"suffering from a drought",    // Water in demand
		"suffering from extreme boredom",  // Games and Narcotics in demand
		"suffering from a cold spell", // Furs in demand
		"suffering from a crop failure", // Food in demand
		"lacking enough workers"   // Machinery and Robots in demand
	};
	final        String[]   SpecialResources =
		{"Nothing special", "Mineral rich", "Mineral poor", "Desert", "Sweetwater oceans", "Rich soil",
			"Poor soil", "Rich fauna", "Lifeless", "Weird mushrooms", "Special herbs",
			"Artistic populace", "Warlike populace"};
	final        String[]   Activity         =
		{"Absent", "Minimal", "Few", "Some", "Moderate", "Many", "Abundant", "Swarms"};
	final        String[]   MercenaryName    =
		{"Jameson", "Alyssa", "Armatur", "Bentos", "C2U2", "Chi'Ti", "Crystal", "Dane", "Deirdre",
			"Doc", "Draco", "Iranda", "Jeremiah", "Jujubal", "Krydon", "Luis", "Mercedez", "Milete",
			"Muri-L", "Mystyc", "Nandi", "Orestes", "Pancho", "PS37", "Quarck", "Sosumi", "Uma", "Wesley",
			"Wonton", "Yorvick", "Zeethibal"};
	final        String[]   SystemSize       = {"Tiny", "Small", "Medium", "Large", "Huge"};
	final        String[]   techLevel        =
		{"Pre-agricultural", "Agricultural", "Medieval", "Renaissance", "Early Industrial",
			"Industrial", "Post-industrial", "Hi-tech"};
	final        String[]   SolarSystemName  =
		{"Acamar", "Adahn", // The alternate personality for The Nameless One in "Planescape: Torment"
			"Aldea", "Andevian", "Antedi", "Balosnee", "Baratas", "Brax",
			// One of the heroes in Master of Magic
			"Bretel", // This is a Dutch device for keeping your pants up.
			"Calondia", "Campor", "Capelle", // The city I lived in while programming this game
			"Carzon", "Castor", // A Greek demi-god
			"Cestus", "Cheron", "Courteney", // After Courteney Cox...
			"Daled", "Damast", "Davlos", "Deneb", "Deneva", "Devidia", "Draylon", "Drema", "Endor",
			"Esmee", // One of the witches in Pratchett's Discworld
			"Exo", "Ferris",   // Iron
			"Festen", // A great Scandinavian movie
			"Fourmi", // An ant, in French
			"Frolix", // A solar system in one of Philip K. Dick's novels
			"Gemulon", "Guinifer", // One way of writing the name of king Arthur's wife
			"Hades", // The underworld
			"Hamlet", // From Shakespeare
			"Helena", // Of Troy
			"Hulst", // A Dutch plant
			"Iodine", // An element
			"Iralius", "Janus", // A seldom encountered Dutch boy's name
			"Japori", "Jarada", "Jason", // A Greek hero
			"Kaylon", "Khefka", "Kira", // My dog's name
			"Klaatu", // From a classic SF movie
			"Klaestron", "Korma", // An Indian sauce
			"Kravat", // Interesting spelling of the French word for "tie"
			"Krios", "Laertes", // A king in a Greek tragedy
			"Largo", "Lave", // The starting system in Elite
			"Ligon", "Lowry", // The name of the "hero" in Terry Gilliam's "Brazil"
			"Magrat", // The second of the witches in Pratchett's Discworld
			"Malcoria", "Melina", "Mentar", // The Psilon home system in Master of Orion
			"Merik", "Mintaka", "Montor", // A city in Ultima III and Ultima VII part 2
			"Mordan", "Myrthe", // The name of my daughter (comment: Pieter Sproncks daughter)
			"Nelvana", "Nix", // An interesting spelling of a word meaning "nothing" in Dutch
			"Nyle", // An interesting spelling of the great river
			"Odet", "Og", // The last of the witches in Pratchett's Discworld
			"Omega", // The end of it all
			"Omphalos", // Greek for navel
			"Orias", "Othello", // From Shakespeare
			"Parade", // This word means the same in Dutch and in English
			"Penthara", "Picard", // The enigmatic captain from ST:TNG
			"Pollux", // Brother of Castor
			"Quator", "Rakhar", "Ran", // A film by Akira Kurosawa
			"Regulas", "Relva", "Rhymus", "Rochani", "Rubicum",
			// The river Ceasar crossed to get into Rome
			"Rutia", "Sarpeidon", "Sefalla", "Seltrice", "Sigma", "Sol", // That's our own solar system
			"Somari", "Stakoron", "Styris", "Talani", "Tamus", "Tantalos", // A king from a Greek tragedy
			"Tanuga", "Tarchannen", "Terosa", "Thera", // A seldom encountered Dutch girl's name
			"Titan", // The largest moon of Jupiter
			"Torin", // A hero from Master of Magic
			"Triacus", "Turkana", "Tyrus", "Umberlee",
			// A god from AD&D, which has a prominent role in Baldur's Gate
			"Utopia", // The ultimate goal
			"Vadera", "Vagra", "Vandor", "Ventax", "Xenon", "Xerxes", // A Greek hero
			"Yew", // A city which is in almost all of the Ultima games
			"Yojimbo", // A film by Akira Kurosawa
			"Zalkon", "Zuul" // From the first Ghostbusters movie
		};
	public       String[][] Shortcuts        =
		{{"B", "Buy Cargo"}, {"S", "Sell Cargo"}, {"Y", "Ship Yard"}, {"E", "Buy Equipment"},
			{"Q", "Sell Equipment"}, {"P", "Personnel"}, {"K", "Bank"}, {"I", "System Info"},
			{"C", "Commander Status"}, {"G", "Galactic Chart"}, {"W", "Warp Chart"}};

	public final int[] planetsDrawableIds  =
		new int[]{R.drawable.world01, R.drawable.world02, R.drawable.world03, R.drawable.world04,
			R.drawable.world05, R.drawable.world06, R.drawable.world07, R.drawable.world08,
			R.drawable.world09, R.drawable.world10, R.drawable.world11, R.drawable.world12,
			R.drawable.world13, R.drawable.world14, R.drawable.world15, R.drawable.world16,
			R.drawable.world17, R.drawable.world18, R.drawable.world19, R.drawable.world20};
	public final int[] lifeLessDrawableIds =
		new int[]{R.drawable.lifeless01, R.drawable.lifeless02, R.drawable.lifeless03,
			R.drawable.lifeless04, R.drawable.lifeless05, R.drawable.lifeless06, R.drawable.lifeless07,
			R.drawable.lifeless08, R.drawable.lifeless09, R.drawable.lifeless10};
	public final int[] desertDrawableIds   =
		new int[]{R.drawable.desert01, R.drawable.desert02, R.drawable.desert03, R.drawable.desert04,
			R.drawable.desert05};

	public Bitmap[] planetsBitmaps;
	public Bitmap[] lifeLessBitmaps;
	public Bitmap[] desertBitmaps;

	Handler  delayHandler  = new Handler();
	Runnable delayRunnable = new Runnable() {
		@Override
		public void run() {
			if (gameState.AutoAttack || gameState.AutoFlee) { // Need to check again, might have pressed Int. button
				if (!ExecuteAction(gameState.CommanderFlees)) {
					if (gameState.Ship.hull > 0) {
						Travel();
					}
				}
			}
		}
	};

	/*
	 * Overrides and Android UI support functions
	 */
	@SuppressWarnings("ConstantConditions")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();

		SharedPreferences sp = getSharedPreferences("spacetrader", MODE_PRIVATE);
		String theme = sp.getString("Theme", "Light");
		if ("Light".equals(theme)) {
			setTheme(R.style.AppTheme_Light);
		} else {
			setTheme(R.style.AppTheme);
		}

		setContentView(R.layout.activity_welcome_screen);
		FragmentManager fragmentManager = getFragmentManager();

		// Set up the drawer.
		DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mNavigationDrawerFragment = (NavigationDrawerFragment) fragmentManager.findFragmentById(
			R.id.navigation_drawer);
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, drawer_layout);
		fragmentManager.beginTransaction().hide(mNavigationDrawerFragment).commit();

		populateBitmaps();
		boolean gameLoaded = false;

		try {
			File path = new File(Environment.getExternalStorageDirectory().toString() + "/SpaceTrader");
			File f = new File(path, "savegame.txt");
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			SaveGame_v110 s = (SaveGame_v110) ois.readObject();
			gameState = new GameState(s);
			GameState.isValid = true;
			gameLoaded = true;
			ois.close();
			fis.close();
			changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
		} catch (Exception ignored) {
		}

		if (!gameLoaded) {
			try {
				File path = new File(Environment.getExternalStorageDirectory().toString() + "/SpaceTrader");
				File f = new File(path, "savegame.txt");
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				SaveGame_v111 s = (SaveGame_v111) ois.readObject();
				gameState = new GameState(s);
				GameState.isValid = true;
				gameLoaded = true;
				ois.close();
				fis.close();
				changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
			} catch (Exception ignored) {
			}
		}

		if (!gameLoaded) {
			try {
				File path = new File(Environment.getExternalStorageDirectory().toString() + "/SpaceTrader");
				File f = new File(path, "savegame.txt");
				FileInputStream fis = new FileInputStream(f);
				ObjectInputStream ois = new ObjectInputStream(fis);
				SaveGame_v120 s = (SaveGame_v120) ois.readObject();
				gameState = new GameState(s);
				WarpSystem = gameState.SolarSystem[gameState.WarpSystem];
				GameState.isValid = true;
				gameLoaded = true;
				ois.close();
				fis.close();
				changeFragment(gameState.currentState);
			} catch (Exception ignored) {
			}
		}

		if (!gameLoaded) {
			gameState = new GameState(this, "Jameson");
			changeFragment(FRAGMENTS.NEW_GAME);
		}
	}

	@Override
	public void onPause() {
		if (gameState.currentState == FRAGMENTS.ENCOUNTER) {
			EncounterButtonIntCallback(null);
		}
		saveGame();
		super.onPause();
	}

	public void populateBitmaps() {
		planetsBitmaps = new Bitmap[planetsDrawableIds.length];
		for (int i = 0; i < planetsDrawableIds.length; i++) {
			planetsBitmaps[i] = BitmapFactory.decodeResource(getResources(), planetsDrawableIds[i]);
		}
		desertBitmaps = new Bitmap[desertDrawableIds.length];
		for (int i = 0; i < desertDrawableIds.length; i++) {
			desertBitmaps[i] = BitmapFactory.decodeResource(getResources(), desertDrawableIds[i]);
		}
		lifeLessBitmaps = new Bitmap[lifeLessDrawableIds.length];
		for (int i = 0; i < lifeLessDrawableIds.length; i++) {
			lifeLessBitmaps[i] = BitmapFactory.decodeResource(getResources(), lifeLessDrawableIds[i]);
		}
	}

	@Override
	public void onBackPressed() {
		switch (gameState.currentState) {
			case NEW_GAME:
				finish();
				break;
			case ENCOUNTER:
			case SYSTEM_INFORMATION:
				saveGame();
				finish();
				break;
			case PLUNDER:
			case DUMP:
				break;
			case AVERAGE_PRICES:
				changeFragment(FRAGMENTS.SHORT_RANGE_CHART);
				break;
			case BUY_NEW_SHIP:
				changeFragment(FRAGMENTS.SHIPYARD);
				break;
			case SHIP_INFO:
				changeFragment(FRAGMENTS.BUY_NEW_SHIP);
				break;
			case WARP_SYSTEM_INFORMATION:
				changeFragment(FRAGMENTS.SHORT_RANGE_CHART);
				break;
			case BANK:
			case BUY_CARGO:
			case BUY_EQUIPMENT:
			case COMMANDER_STATUS:
			case GALACTIC_CHART:
			case OPTIONS:
			case PERSONNEL_ROSTER:
			case SELL_CARGO:
			case SELL_EQUIPMENT:
			case SHIPYARD:
			case SHORTCUTS:
			case SHORT_RANGE_CHART:
			default:
				changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
				break;
			case VERY_RARE_CHEAT:
				changeFragment(FRAGMENTS.GALACTIC_CHART);
				break;
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		if (gameState.currentState == FRAGMENTS.ENCOUNTER || gameState.currentState == FRAGMENTS.NEW_GAME || gameState == null) {
			return;
		}
		switch (position) {
			case 0: //"Buy Cargo"
				changeFragment(FRAGMENTS.BUY_CARGO);
				break;
			case 1: //"Sell Cargo"
				changeFragment(FRAGMENTS.SELL_CARGO);
				break;
			case 2: // "Shipyard"
				changeFragment(FRAGMENTS.SHIPYARD);
				break;
			case 3: // "Buy Equipment"
				changeFragment(FRAGMENTS.BUY_EQUIPMENT);
				break;
			case 4: // "Sell Equipment"
				changeFragment(FRAGMENTS.SELL_EQUIPMENT);
				break;
			case 5: // "Personnel Roster"
				changeFragment(FRAGMENTS.PERSONNEL_ROSTER);
				break;
			case 6: // "Bank"
				changeFragment(FRAGMENTS.BANK);
				break;
			case 7: // "System Information"
				changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
				break;
			case 8: // "Commander Status"
				changeFragment(FRAGMENTS.COMMANDER_STATUS);
				break;
			case 9: // "Galactic Chart"
				changeFragment(FRAGMENTS.GALACTIC_CHART);
				break;
			case 10: // "Short Range Chart"
				changeFragment(FRAGMENTS.SHORT_RANGE_CHART);
				break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		assert actionBar != null;
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(getString(R.string.app_name));
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		try {
			menu.findItem(R.id.hotkey1).setTitle(Shortcuts[gameState.Shortcut1][0]);
			menu.findItem(R.id.hotkey2).setTitle(Shortcuts[gameState.Shortcut2][0]);
			menu.findItem(R.id.hotkey3).setTitle(Shortcuts[gameState.Shortcut3][0]);
			menu.findItem(R.id.hotkey4).setTitle(Shortcuts[gameState.Shortcut4][0]);
		} catch (Exception ignored) {
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (mNavigationDrawerFragment != null) {
			if (!mNavigationDrawerFragment.isDrawerOpen()) {
				MenuInflater inflater = getMenuInflater();
				if (gameState.currentState == FRAGMENTS.NEW_GAME || gameState.currentState == FRAGMENTS.ENCOUNTER) {
					inflater.inflate(R.menu.help_menu, menu);
				} else {
					inflater.inflate(R.menu.in_game, menu);
				}
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
		String call = "";
		Popup popup;
		DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (id != R.id.submenuGame && id != R.id.submenuHelp) {
			drawer_layout.closeDrawers();
		}
		switch (id) {
			case R.id.hotkey1:
				call = Shortcuts[gameState.Shortcut1][0];
				break;
			case R.id.hotkey2:
				call = Shortcuts[gameState.Shortcut2][0];
				break;
			case R.id.hotkey3:
				call = Shortcuts[gameState.Shortcut3][0];
				break;
			case R.id.hotkey4:
				call = Shortcuts[gameState.Shortcut4][0];
				break;
			case R.id.menuOptions:
				changeFragment(FRAGMENTS.OPTIONS);
				return true;
			case R.id.menuNewGame:
				popup = new Popup(this, "Really start new game?",
					"If you start a new game your current game will be deleted!\nYou will not be added to the high score table!",
					"", "Yes", "No", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						mContext.deleteFile("savegame.txt");
						File p = new File(
							Environment.getExternalStorageDirectory().toString() + "/SpaceTrader");
						//noinspection ResultOfMethodCallIgnored
						p.mkdirs();
						File f = new File(p, "savegame.txt");
						//noinspection ResultOfMethodCallIgnored
						f.delete();
						GameState.isValid = false;
						changeFragment(FRAGMENTS.NEW_GAME);
						popupQueue.clear();
					}
				}, cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuRetire:
				popup = new Popup(this, "Retire", "Do you really want to retire?", "", "Yes", "No",
					new Popup.buttonCallback() {
						@Override
						public void execute(Popup popup, View view) {
							popupQueue.clear();
							EndOfGame(GameState.RETIRED);
						}
					}, cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuShortcuts:
				changeFragment(FRAGMENTS.SHORTCUTS);
				return true;
			case R.id.menuHighscores:
				ViewHighScores();
				return true;
			case R.id.menuClearHighscore:
				return true;
			case R.id.menuHelpCurrentScreen:
				String helpText = "No help available.";
				switch (gameState.currentState) {
					case AVERAGE_PRICES:
						helpText =
							"This screen shows the average prices you get for goods in the target system. If the trade good is shown in a bold font, it means the average price mentioned here is better than the buying price in the current system. \nNote that these prices do take into account the system size, government and tech level. Special resources (like rich soil) are also taken into account if you know about them. Special situations (like war) are temporary and are therefore not taken into account. \nRemember that criminals can't sell their goods directly, but have to use an intermediary, who keeps 10 percent of the selling price for himself. This is also not taken into account in the calculation of the average prices. \nThe button \"Price Differences\" switches to an overview of the differences between the average prices in the target system and the buying prices in the current system. When price differences are shown, this button is replaced by an \"Absolute Prices\" button, which, if tapped, will switch back to absolute prices.\nThe buttons labeled @<- and @-> can be used to scroll through the systems which are within range.\nTo return to the target system information screen, tap the System Information button and to return to the short range chart, tap the Shot Range Chart button. You can also immediately go into warp by tapping the Warp button.";
						break;
					case BANK:
						helpText =
							"At the bank you can get a loan, if you are really in need of cash. As long as your police record is at least clean, you can have a loan of at least 1000 credits, more if you are rich yourself. When you don't have a clean police record, the bank will maximally lend you 500 credits.\nWhen your debt is more than the amount the bank is willing to lend you, you won't get a new loan. There is an interest of 10%% to be paid daily, which will be subtracted from your cash right before you warp to another system. If you don't have enough cash, this will simply add to your debt. Settle your debts as quickly as possible, because as long as you have debts, you won't be able to buy a new ship or new equipment for your ship.\nAt the bank, you can also buy insurance for your ship. This is only useful if you also have an escape pod on your ship, because the insurance pays out when your ship gets destroyed and you manage to escape in a pod. The cost of the insurance is to be paid daily: 0.25 percent of the trade-in value of your current ship, including its equipment (but excluding cargo). For each day you fly without claim, 1%% is subtracted from the amount to be paid. Note that if you stop your insurance, your \"no claim\" returns to zero percent. Also note that if the trade-in value of your ship changes, your insurance costs also change.";
						break;
					case BUY_CARGO:
						helpText =
							"Use this screen to buy cargo. The leftmost column shows quantities available. The second column shows the name of the goods. The fourth column shows the price. To buy goods, either tap the quantity cell, after which you can specify how much you want to buy, or tap the \"Max\" cell, which will automatically buy the maximum number of items, limited by the quantity available, the number of empty cargo holds you have, and your cash position. If you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.";
						break;
					case BUY_EQUIPMENT:
						helpText =
							"Tap the Buy button to the left of a piece of equipment to buy it for the price to the right of it. Note that each ship type can only carry a limited number of items in each equipment category.\nThe three categories are weapons, shields and gadgets. The weapons category contains the three types of lasers, the shields category contains the two types of shields, and the gadget category contains the following gadgets: 1) 5 extra cargo bays; 2)  auto-repair system (which helps your engineering functions); 3) navigating system (which helps you pilot your ship); 4) targeting system (which helps you fighting); and 5) cloaking device (which allows you to travel undetected, unless you attack yourself or your opponent has a better engineer than you).\nIf you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.";
						break;
					case BUY_NEW_SHIP:
						helpText =
							"Information on a ship type you can get buy tapping the Info button to the right of it. Buy a new ship by tapping the corresponding Buy button. The price quoted for the ship takes into account the discount you get for trading in your current ship, including its  equipment and cargo. The ship delivered to you will lack any equipment and cargo. Note that if you are carrying cargo which the current system isn't interested in, you lose that cargo also without receiving any compensation for it.\nIf you have an escape pod it will be transferred to your new ship. Insurance will also be transferred, including your no-claim.\nIf you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.";
						break;
					case COMMANDER_STATUS:
						helpText =
							"On the Commander Status screen you can examine your skills, your reputation and your total worth. Note that if your police record indicates that you are a criminal or worse, you have to use an intermediary to sell goods, who charges 10 percent of the selling price for his services. The numbers within brackets with the skills are the skill values which take into account mercenaries and equipment on your ship.\nThe Ship button allows you to examine your current ship. The Quests button allows you to see details on the quests you are currently on.";
						break;
					case DUMP:
						helpText = "If you want to dump cargo, you have to pay for its correct disposal.";
						break;
					case ENCOUNTER:
						break;
					case GALACTIC_CHART:
						helpText =
							"Tapping a system on the galactic chart shows information on that system at the bottom of the screen. Tapping on a wormhole will display a line indicating the system to which the wormhole goes, as well as the name of both systems.\nTapping a system twice will cause that system to be tracked. On the Short-Range chart, a line will indicate the direction to the tracked system. Tapping on a tracked system will turn off tracking.\nSystems you have already visited are blue, unvisited systems are green, and wormholes are black.\nThe Find button allows you to enter a system name, on which the chart will then focus; you also have the option to track the system.";
						break;
					case NEW_GAME:
						helpText =
							"Welcome, Space Trader! Please enter your name and desired level of difficulty here.\n" +
								"Also, you have 16 skill points to spare, which you must spend on the four skills a Space Trader must have:\n" +
								"- The Pilot skill determines how good you are at evading shots fired in your general direction and fleeing from other ships.\n" +
								"- The Fighter skill is the opposite of the Pilot skill: it defines how good you are at hitting other ships.\n" +
								"- The Trader skill is feared at space ports as it determines how good you are at haggling prices.\n" +
								"- The Engineer skill determines how capable you are at keeping your ship in shape and using advanced technology like the cloaking device.\n" +
								"After you have entered these information, you are ready to start being a Space Trader by hitting 'Start game'. If this is your first game, you will be presented with a 'First Steps' tutorial.\n\n" +
								"Good luck and have fun!";
						break;
					case OPTIONS:
						helpText =
							"\"Always ignore when it is safe\" will fly past encounters where you can safely ignore your opponent. This means you won't encounter any peaceful ships along the way. You also won't see ships which immediately start to flee from you, and neither will you see any ships which ignore you because you have a cloaking device. You can set this option separately for police, pirates,  traders, and traders wanting to make deals in orbit. As long as you play as a peaceful trader, you can check the first three, but you might want to make sure the fourth is unchecked. When you become a bounty hunter, you should uncheck the \"pirates\" one. When you become a pirate, you should uncheck the \"traders\" one. If you want to attack everybody and anybody (in other words, when you are a real evil psychopath), you should uncheck all four.\n\"Get full tank on arrival\", if checked, will automatically get a full tank of fuel when docking at a new system, if, of course, you have enough credits.\n\"Get full hull repair on arrival\" will automatically get your hull repaired to 100% when docking at a new system, if, of course, you have enough credits.\n\"Reserve money for warp costs\" will not spend all your money when buying cargo, equipment or a new ship, but will reserve at least enough to pay your mercenaries and insurance. It won't take into account interest, because your debt will simply increase if you can't pay interest, and it won't take into account wormhole tax, because it's not known whether or not you will fly through a wormhole. It also won't protect you when buying fuel or repairs.\n\"Always go from Chart to Info\", if checked, will always present the target system Info screen when you tap that system on the Short Range Chart. If not checked, the game will remember whether you were last on the Info screen or on the Average Price List, and will go to the screen you last accessed (if you are allowed to go to that screen).\n\"Continuous attack and flight\" automatically executes, once started, an attack or an attempt to flee every second, until either the player chooses a new action, or one of the ships gets destroyed, or one of the ships manages to escape, or the opponent changes his attitude (for instance, switches from attacking to fleeing or surrendering). You can also interrupt the repetition by tapping the \"Int.\" button or by simply selecting another action.\n\"Continue attacking fleeing ship\", if checked, will automatically continue the automatic attack, if it is activated, on a ship that stops attacking you and starts fleeing.\n\"Cargo bays to leave empty\" is the number of cargo bays you want to leave empty when buying trade goods. This is useful if you like to use the \"Max\" button but still would like to leave some bays empty in case you can pick up cargo while flying to another system.\n\"Always pay for newspaper\" will allow you to automatically pay when you click on the \"News\" button when viewing system information. If you leave this unchecked, you will be asked whether you want to spend the money on the paper.\n\"Stop tracking systems on arrival\" allows you to automatically stop tracking a system when you arrive at that system. If you uncheck this, you will continue to track the system until you turn off system tracking or select a different system to track in the Galactic Chart.\n";
						break;
					case PERSONNEL_ROSTER:
						helpText =
							"On the Personnel Roster screen, there are three areas. The upper two are reserved for mercenaries in your service. If you have hired someone, he or she is visible in one of these areas. To fire someone in your service, just tap the corresponding Fire button.\nThe bottom area is reserved for a mercenary who is for hire in the current system. To hire him or her, just tap the Hire button. Note that if you fire someone, he or she will probably decide to return to his or her home system.";
						break;
					case PLUNDER:
						helpText =
							"You are allowed to plunder your opponent's cargo to your heart's content. Just steal whatever is to your liking. This works exactly as the Buy Cargo option when you are docked, except that you don't have to pay anything. You are, of course, limited to the amount your own cargo bays can hold. If you holds are already full, you can jettison selections from your cargo into space by tapping the Dump button. Tap the Done button when you are finished.";
						break;
					case SELL_CARGO:
						helpText =
							"Use this screen to sell cargo. The leftmost column shows quantities you have stored in your cargo holds. The second column shows the name of the goods. If the name of the goods is in bold, it means you can sell these goods with a profit. The fourth column shows the price you can get for your goods. To sell, either tap the quantity cell, after which you can specify how much you want to sell, or tap the \"All\" cell, which will automatically sell all goods you own of the selected item.";
						break;
					case SELL_EQUIPMENT:
						helpText =
							"To sell a piece of equipment for the price indicated to the right of it, tap the Sell button to the left of it.";
						break;
					case SHIPYARD:
						helpText =
							"At the Ship Yard, you can buy fuel, get your hull repaired, buy an escape pod, or even buy a new ship. When you buy a new ship, the total worth of your current ship (including equipment and cargo) is subtracted from the price of a new ship.\nAn escape pod will automatically eject you and your crew from your ship when it gets destroyed.\nIf you want to automatically buy a full tank and/or automatically get a full hull repair when you dock at a new system, you can check the appropriate options in the Options menu, available through the game menu.\nWhen buying a new ship or an escape pod, if you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.";
						break;
					case SHIP_INFO:
						helpText = "The Ship Information screen shows the specs of the selected ship type.";
						break;
					case SHORTCUTS:
						break;
					case SHORT_RANGE_CHART:
						helpText =
							"Tap the system you wish to warp to. The game will show you what is known about that system, or the average price list for that system (depending on your preferences and what you viewed last). You can warp from that screen.\nSystems you have visited are blue, other systems are green, and wormholes are black.\nThe wide circle shows the range you can fly on your current fuel tanks. If it seems a bit small, you should visit the Ship Yard to refill your tanks.\nA wormhole is a hole into the space-time continuum which leads somewhere else in the galaxy. Before you can warp through the wormhole, you must first fly to the system that owns it, which is displayed to the left of it. From that system, you can tap the wormhole and warp immediately to the system at the other side.\nIf you are Tracking a system (which you can do from the Galactic Chart), there will be a line from your current system in the direction of the system being tracked, and the distance will be displayed at the top of the screen.";
						break;
					case SYSTEM_INFORMATION:
						helpText =
							"This screen shows information on the system where you are currently docked. You can click on the News button to buy a newspaper, which will have headlines about local and nearby events. If there is a Special button visible, tap it to get a special offer, only available in this system. If there is a mercenary available in this system, a Mercenary For Hire button is visible. Tapping it will take you to the Personnel Roster.";
						break;
					case VERY_RARE_CHEAT:
						break;
					case WARP_SYSTEM_INFORMATION:
						break;
				}
				popup = new Popup(this, "Tips", helpText, "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuHelpMenu:
				popup = new Popup(this, "Tips",
					"The menu consists of three main menu choices:\n\"Commands\", \"Game\", and \"Help\".\n\n" +
						"\"Commands\" allows you to issue commands while you are docked at a system. You can use this to switch between the main screens.\n\n" +
						"\"\"Game\" gives access to game functions:\n" +
						"- \"New Game\" starts a new game.\n" +
						"- \"Retire\" ends the game by retiring the commander. Your score is calculated and you can enter the high-score table if you qualify. However, the preferred way to end a game is by claiming a moon, which is something you have to work for.\n" +
						"- \"Options\" gives access to the game preferences.\n" +
						"- \"Shortcuts\" allows you to set new preferences for the four shortcut buttons in the top right corner of many screens.\n" +
						"- \"High Scores\" shows the high-score list.\n" +
						"- \"Clear High Scores\" wipes the current high-score list.", "", "OK", cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuHelpHowToPlay:
				popup = new Popup(this, "How to play",
					"Space Trader is a strategy game in which the ultimate goal is to make enough cash to buy your own moon, to which you can retire. The most straightforward way to make cash is to trade goods between solar systems, hopefully making a profit. However, you can also decide to become a pirate and rob innocent traders of their wares. You can also earn an income by bounty hunting.\n\n" +
						"The Help menu in the game offers basic information, enough to play the game. The menu choice \"Current Screen\" always gives information on the screen which is currently shown. The rest of the menu choices give a basic overview of the game, of which this particular text is the first. The First Steps choice is especially interesting for a first-time player, since it describes all the steps you need to perform your first days as a trader.\n\n" +
						"You have to change screens often. All main screens are accessible through the menu. The four choices you have to use the most (Buy Cargo, Sell Cargo, Ship Yard and Short Range Chart) have their own shortcut button at the top right corner of every screen. These shortcut functions can be changed from the Shortcuts menu option in the Game menu.\n\n" +
						"At the start of the game you have a small spaceship of the Gnat type, armed with a simple pulse laser, and 1000 credits to start your ventures. While docked, you can buy or sell trade goods; buy or sell equipment for your ship; buy fuel, repairs or even a new ship at the Ship Yard; hire mercenaries; visit the bank to get a loan; get information on your status, the galaxy or nearby solar systems; and activate the warp to another system.\n\n" +
						"When you have activated the warp, you materialise nearby the target system you selected. The last distance you have to travel on your impulse engines. During that time, you may encounter pirates, police ships, or other traders.",
					"", "OK", cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuHelpTrading:
				popup = new Popup(this, "Trading",
					"Trading is the safest way to make money. You trade by buying goods at one solar system, and sell them at another solar system. Of course, you should try to make a profit. There are several ways to ensure you can indeed sell your goods for a higher price than you bought them.\n\n" +
						"The prices a system pays for goods are determined by several factors. First and foremost, there is the tech level of a system. Low-tech systems have relatively cheap natural resources (water, furs, food and ore), while high-tech systems have relatively cheap non-natural goods. In general, prices for natural goods increase with the level of technological development, while the other prices decrease. Note that the tech level also influences which goods are useful to the inhabitants of a system, and which they won't buy at all.\n\n" +
						"Other influences are the type of government a system has (for instance, in an anarchy there is almost always a food shortage and a military state will never buy narcotics), the size of a system (the smaller the system, the greater the demand for imported goods), and extraordinary natural resources (or the lack of them). Lastly, special events may have a tremendous influence on prices: for instance, when a system is visited by a cold spell, furs are especially in high demand.\n\n" +
						"On the Short Range Chart, you can tap a system and ask for the Average Price List for that system. This list only takes into account the size, tech level and government of a system (and the special resources if you know about them), but may be a good indication on what price you can expect to get for your goods\n\n." +
						"Note that if you are a criminal (or worse), you have to use an intermediary to sell your goods, and this intermediary will take 10%% of the profits.",
					"", "OK", cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuHelpTravelling:
				popup = new Popup(this, "Travelling",
					"To travel to another system, go to the Short Range Chart. The system where you currently are is in the centre of the screen. The wide circle shows how far you can travel on your current fuel tanks. If the circle is absent, you probably have no fuel and you should go to the Ship Yard to buy some.\n\n" +
						"When you tap a system that is within reach, you get shown some information on that system, and a big Warp button, with which you can activate a warp. When you tap the Warp button, you get warped to the target system. You do not materialize on the system itself, but nearby. You have to travel the last few clicks on your impulse engines (which costs no fuel - fuel is only used to warp)\n\n." +
						"During that time, you may meet police, pirates or other traders. The chance to meet any of them is determined by the government type of the system you are flying to. If you have a weak ship, you should probably stay away from systems which have lots of pirates.\n\n" +
						"Police ships will usually let a lawful trader pass by. If they suspect you may be trafficking illegal goods (that is, firearms or narcotics), they may ask you to submit to an inspection. If you don't have any illegal goods on board, just comply. If you do, and you let them inspect you, they will impound your goods and fine you. If you don't want to submit to inspection, you can try to flee from them (in which case they will attack you), attack them, or try to bribe them.\n\n" +
						"Pirates will usually attack you on sight. You can also attack them, flee from them, or surrender to them. If you surrender, they will steal from your cargo bays. If you don't have anything in your cargo bays, they will blow up your ship unless you pay them off with cash. Destroying a pirate will earn you a bounty.\n\n" +
						"Traders will usually ignore you. However, you can become a pirate yourself and attack them. Sometimes, a trader who finds you too strong an opponent and who can't manage to flee from you, will surrender to you and let you steal from his cargo bays.",
					"", "OK", cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuHelpShipEquipment:
				popup = new Popup(this, "Ship Equipment",
					"There are several types of ships available to you. You start out in a Gnat, which is the cheapest ship but one (the cheapest is the Flea, which is mainly used if you need to jump over a large distance, since it can travel up to 20 parsecs on one tank). At the Ship Yard, you can buy a new ship if you like and one is available. The availability of ships depends on the tech level of the system.\n\n" +
						"Ship equipment falls into three groups. Each ship can equip zero or more of each group. The ship type determines exactly how many. For instance, your Gnat can equip one weapon, zero shields and one gadget.\n\n" +
						"The first group consists of weapons. Three kinds of lasers are available, and the more lasers, or the more expensive lasers you equip, the more damage you do. The second group consists of shields. Two kinds of shields are available, and the more shields, or the more expensive shields you equip, the better you are defended against attacks. The last group consists of gadgets.\n\n" +
						"As gadgets, you can buy 5 extra cargo bays, a targeting system, a navigating system, an auto-repair system, or a cloaking device. Of the extra cargo bays you can equip more than one: of the others you don't have use for more than one. The cloaking device helps you fly undetected through space; the other three systems increase one of your skills (see Skills).\n\n" +
						"Besides equipment slots, a ship has also one, two or three crew quarters. If you have more than one, you might hire mercenaries to accompany you on your trips." +
						"Finally, at the Ship Yard you can get your ship equipped with an escape pod, and at the bank you can get your ship insured, so you get compensated when you have to use your pod." +
						"When you buy a new ship, you trade in your old one, including all its equipment. Don't worry, the price you pay for your new ship takes this into account. You may even get money for the trade. Mercenaries will stay on your ship, unless your new ship hasn't got enough crew quarters. In that case, you have to fire them.",
					"", "OK", cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuHelpSkills:
				popup = new Popup(this, "Skills",
					"As a trader, you have need of several skills. You can set your skills on the New Commander screen at the start of the game.\n\n" +
						"The Pilot skill determines how well you fly your ship. Good pilots have an easier time escaping from a fight and dodging laser shots.\n\n" +
						"The Fighter skill determines how well you handle your weapons. While the actual damage you do with a weapon is solely determined by the weapon's power, the fighter skill determines whether you hit or not.\n\n" +
						"The Trader skill influences the price you have to pay for goods and equipment. A good trader pays considerably less than a bad trader.\n\n" +
						"Finally, the Engineer skill determines how well you keep your ship in shape. Especially, an engineer manages to repair your hull and shield while traveling and during a fight. He may even reduce the damage done by an opponent to zero. A good engineer can also upgrade your weaponry a bit, so you do more damage.\n\n" +
						"If you fly a ship with extra crew quarters, you can hire mercenaries. These travel with you, for a certain sum of credits per day. The net effect of having a mercenary on board is that if the mercenary is better in a certain skill than you are, he will take over the tasks for which that skill is needed. So, if you are lacking a certain skill, a mercenary can compensate for that.\n\n" +
						"Another way to increase certain skills is to buy gadgets. Especially, a navigating system increases your pilot skill, an auto-repair system increases your engineer skill, and a targeting system increases your fighter skill.",
					"", "OK", cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuHelpFirstSteps:
				showFirstStepsHelp();
				return true;
			case R.id.menuHelpAcknowledgements:
				popup = new Popup(this, "Acknowledgements",
					"Following is the ORIGINAL Acknowledgments text of Space Trader by Pieter Spronck. Much of it still applies, obviously, but not everything. I'm keeping it to acknowledge all of the work that has gone into Space Trader before the Android port.\n\n" +
						"This first version of \"Space Trader\" has been designed and programmed by me, Pieter Spronck, between July and September 2000. The game has been enhanced several times since then. It has been released as freeware under a GNU General Public License (GPL).\n" +
						"I used CodeWarrior for PalmPilot, release 6. Since it was my first project with this environment, I often consulted the example code delivered with it. I also made some use of Matt Lee's code for his DopeWars program.\n" +
						"A derivative work of DopeWars was SolarWars, a program by David J. Webb. This program is very similar to DopeWars, except that it has a space trading theme instead of a drug theme. Playing SolarWars, I was reminded of the eighties game Elite. While Elite was more like a 3D space combat program, the trading between solar systems was central to it, especially because that was the best way to make money and buy better equipment for your ship.\n" +
						"I thought it would be fun to have a program for the PalmPilot which was a trading game like SolarWars, but which would resemble the trading, development and even the combat of Elite more. Thus Space Trader was born. I haven't tried to hide my source of inspiration, and you'll find some ideas in the game which are directly derived from Elite. Consider it a tribute.\n" +
						"A great many thanks and a lot of admiration goes out to Alexander Lawrence (al_virtual@yahoo.com), who created the beautiful pictures which illustrate the game, including the ship designs. It's almost worth ditching your black&white Palm for to get a color one!\n" +
						"Sam Anderson (rulez2@home.com) converted Space Trader to a multi-segmented application (version 1.1.2). Sam also made a few small changes to the code, fixing bugs and correcting grammatical errors.  I wish to extend my thanks to him for that. Without Sam, players using Palm OS versions 2.x and 4.x would have had a lot more problems with this game.\n" +
						"Samuel Goldstein (palm@fogbound.net) added most of the new functionalities for version 1.2.0. Among these great additions are four new quests, special encounters, the \"news\", trading with fellow traders in space, better black&white pictures, and many handy new features. Samuel brought new life to this game, and even I found it to be a lot of fun again. Many heartfelt thanks go out to Samuel, from me, and I expect from many players too.\n" +
						"DrWowe solved the irritating \"Special\" bug which plagued Space Trader for over two years.\n" +
						"Many thanks also go out to the Space Trader beta testers, who pointed out several bugs and who suggested many ideas to better the game, a lot of which have been implemented:\n" +
						"Michael Andersson, John Austin, Ben Belatrix, Lee W. Benjamin, Russell K Bulmer (mtg101), Chris Casperson (Neo987), Danny Chan, Christophe \"The Frenchy\" Chidoyan, Lysander Destellirer, Charles Dill, Zion A. Dutro, Kevin and Daniel Eaton, Jen Edwards, Roni Eskola, Sean M. Goodman, Ken Gray, Tom Heisey, Peter Hendzlik, Anders Hustvedt, Jonathan Jensen, Peter Kirk, Lackyboy, Alexander Lawrence, Eric Lundquist, Eric Munsing, ossido, Brandon Philips, Dylan Sauce, Neil Shapiro, Ted Timmons, Subway of Trammel, Sascha Warnem, Aitor Zabala\n" +
						"Thank you all. You were a tremendous help, and I am very grateful for that.\n" +
						"Finally, I wish to thank all people who sent their comments to me since the first release of the game. Many of your suggestions have been incorporated in the game, and made it a lot better. Suggestions I haven't used, I have at least stored to inspire me when creating sequel game. Unfortunately, my life is so busy now that I have very little time to respond to emails, or even read them.\n" +
						"An extensive FAQ for the game is available at the Space Trader home page at http://www.spronck.net/picoverse/spacetrader.",
					"", "OK", cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuHelpCredits:
				popup = new Popup(this, "Credits", "Android port Copyright 2014 by Benjamin Schieder\n" +
					"Linux port Copyright 2010 by Benjamin Schieder\n" +
					"Tribble Sprite Art by Kiriki-chan (http://kiriki-chan.deviantart.com/)\n" +
					"New Spaceship parts by Skorpio (http://opengameart.org/users/skorpio)\n" +
					"Original Copyright 2000-2002 by Pieter Spronck\n" +
					"Design and programming: Pieter Spronck\"\n" +
					"Additional design and programming: Samuel Goldstein, Sam Anderson\n" +
					"Graphics: Alexander Lawrence\n" +
					"Additional graphics: Samuel Goldstein, Pieter Spronck\n" +
					"Special thanks to: David Braben and Ian Bell for \"Elite\"\n" +
					"David J. Webb for \"Solar Wars\"\n" +
					"Matt Lee for \"Dope Wars\"\n" +
					"DrWowe for solving the \"Special\" bug\n" +
					"All the beta testers\nAnd all the players that sent me their ideas\n" +
					"Space Trader is released under a GNU General Public License", "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return true;
			case R.id.menuTwitter:
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
					"https://twitter.com/AndSpaceTrader"));
				startActivity(browserIntent);
				return true;
			case R.id.menuHelpLicense:
				popup = new Popup(this, "License", "The game code is licensed under the GPLv2", "", "OK",
					cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return true;
		}
		if (call.equals("B")) {
			changeFragment(FRAGMENTS.BUY_CARGO);
		} else if (call.equals("S")) {
			changeFragment(FRAGMENTS.SELL_CARGO);
		} else if (call.equals("Y")) {
			changeFragment(FRAGMENTS.SHIPYARD);
		} else if (call.equals("E")) {
			changeFragment(FRAGMENTS.BUY_EQUIPMENT);
		} else if (call.equals("Q")) {
			changeFragment(FRAGMENTS.SELL_EQUIPMENT);
		} else if (call.equals("P")) {
			changeFragment(FRAGMENTS.PERSONNEL_ROSTER);
		} else if (call.equals("K")) {
			changeFragment(FRAGMENTS.BANK);
		} else if (call.equals("I")) {
			changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
		} else if (call.equals("C")) {
			changeFragment(FRAGMENTS.COMMANDER_STATUS);
		} else if (call.equals("G")) {
			if (gameState.currentState == FRAGMENTS.GALACTIC_CHART) {
				changeFragment(FRAGMENTS.SHORT_RANGE_CHART);
			} else {
				changeFragment(FRAGMENTS.GALACTIC_CHART);
			}
		} else if (call.equals("W")) {
			if (gameState.currentState == FRAGMENTS.SHORT_RANGE_CHART) {
				changeFragment(FRAGMENTS.GALACTIC_CHART);
			} else {
				changeFragment(FRAGMENTS.SHORT_RANGE_CHART);
			}
		} else {
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	public void showFirstStepsHelp() {
		Popup popup = new Popup(this, "First Steps", "Welcome Space Trader!\n\n" +
			"You start by docking on some system. The specifics of that system are shown on the System Information screen. Take special note of any special resources the system might have. These influence the price you have to pay for certain goods. For instance, a system which has rich soil, usually sells food cheap, while a relatively lifeless system has little fauna and therefore expensive furs.\n\n" +
			"Also take note of any special events in the system. Special events usually means that certain things are expensive to buy, so you should stay clear from them in this system, but since special events last several days, it might be worth your while to return here later to sell something they especially need.\n\n" +
			"If there is a Special button on the System Information screen, tap it to see what the special offer is. You can always refuse, but it is good to know what special thing is available here.\n\n" +
			"After you have examined the system on the System Information screen, if you have cargo, go to the Sell Cargo screen to sell it. Then, switch to the Ship Yard to buy a full tank of fuel, and repair your hull if you think it's necessary. If you want, you can let the program take care of the Ship Yard automatically when you arrive in a new system, by checking the appropriate choices in the Options menu.\n\n" +
			"Then switch to the Short Range Chart to select your next target. Tap any system within the maximum range circle to get information on that system. Try to select a system which hasn't got too many pirates (unless to aspire a career as a bounty hunter), and which has a tech level which is opposite the tech level of your current system. That is, from an agricultural system you best travel to an industrial system to sell natural goods, while from an industrial system you best sell technologies to more backward systems. Use the Average Price List button to get an indication on the prices you might expect to sell your goods for. Goods that are displayed bold have an average selling price that is higher than the price you have to pay for those goods in the current system. Note that this isn't a guarantee, but it's better than nothing.\n\n" +
			"When you have selected a system, you know what you want to sell there, and you can switch to the Buy Cargo screen to get some goods. Remember that Firearms and Narcotics are illegal goods, and you could get in trouble with the police if you traffick those. After having filled your cargo bays, return to the Short Range Chart, and Warp to the selected system.\n\n" +
			"While in flight, flee from pirates, ignore traders and submit to police inspections if they ask you to (unless you are carrying illegal goods, in which case you must decide for yourself how you best handle them). Later on in the game, when you are ready for it, you might wish to become a pirate yourself and attack traders, or become a bounty hunter and attack pirates. However, with full cargo holds you best try to arrive on the target system in one piece, so you can sell your goods and make a profit.\n\n" +
			"There are many more things to Space Trader, but you can discover these by examining the screens, reading the help screens, reading the documentation, and simply by playing the game.\nHave fun!",
			"", "OK", cbShowNextPopup
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	/*
	 * Popup functions must be here to be in the right context.
	 */
	public void showNextPopup() {
		if (popupQueue.isEmpty()) {
			return;
		}

		Popup popup = popupQueue.peek();
		if (popup.dialog != null && popup.dialog.isShowing()) {
			return;
		}
		if (popup.wasShown) {
			popupQueue.pop();
		}
		if (popupQueue.isEmpty()) {
			return;
		}

		popup = popupQueue.peek();
		popup.show();
	}

	public void addPopup(Popup popup) {
		popupQueue.push(popup);
		showNextPopup();
	}

	/*
	 * Heart and Soul of UI: changer of fragments.
	 */
	public void changeFragment(FRAGMENTS fragment) {
		hide_keyboard(this);
		if (fragment == gameState.currentState && currentFragment != null) {
			// Sometimes this seems to be unset. Try to workaround it.
			currentFragment.gameState = gameState;
			if (currentFragment.update()) {
				return;
			}
		}
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		if (fragment == FRAGMENTS.NEW_GAME || fragment == FRAGMENTS.ENCOUNTER) {
			transaction.hide(mNavigationDrawerFragment);
		} else {
			transaction.show(mNavigationDrawerFragment);
		}

		Bundle args = new Bundle();
		args.putSerializable("gamestate", gameState);

		switch (fragment) {
			case AVERAGE_PRICES:
				currentFragment = new FragmentAveragePrices();
				break;
			case BANK:
				currentFragment = new FragmentBank();
				break;
			case BUY_CARGO:
				currentFragment = new FragmentBuyCargo();
				break;
			case BUY_EQUIPMENT:
				currentFragment = new FragmentBuyEquipment();
				break;
			case BUY_NEW_SHIP:
				currentFragment = new FragmentBuyNewShip();
				break;
			case COMMANDER_STATUS:
				currentFragment = new FragmentCommanderStatus();
				break;
			case DUMP:
				currentFragment = new FragmentDumpCargo();
				break;
			case ENCOUNTER:
				currentFragment = new FragmentEncounter();
				break;
			case GALACTIC_CHART:
				currentFragment = new FragmentGalacticChart();
				break;
			case NEW_GAME:
				currentFragment = new FragmentStartNewGame();
				break;
			case OPTIONS:
				currentFragment = new FragmentOptions();
				break;
			case PERSONNEL_ROSTER:
				currentFragment = new FragmentPersonnelRoster();
				break;
			case PLUNDER:
				currentFragment = new FragmentPlunderCargo();
				break;
			case SELL_CARGO:
				currentFragment = new FragmentSellCargo();
				break;
			case SELL_EQUIPMENT:
				currentFragment = new FragmentSellEquipment();
				break;
			case SHIPYARD:
				currentFragment = new FragmentShipyard();
				break;
			case SHIP_INFO:
				currentFragment = new FragmentShipInfo();
				break;
			case SHORTCUTS:
				currentFragment = new FragmentShortcuts();
				break;
			case SHORT_RANGE_CHART:
				currentFragment = new FragmentShortRangeChart();
				break;
			case SYSTEM_INFORMATION:
				currentFragment = new FragmentSystemInformation();
				break;
			case VERY_RARE_CHEAT:
				currentFragment = new FragmentVeryRare();
				break;
			case WARP_SYSTEM_INFORMATION:
				currentFragment = new FragmentWarpSystemInformation();
				break;
			default:
				return;
		}

		currentFragment.setArguments(args);
		transaction.replace(R.id.container, currentFragment);
		transaction.commit();
		gameState.currentState = fragment;

		invalidateOptionsMenu();
	}

	/*
	 * Button Callbacks
	 */
	// FragmentStartNewGame
	@SuppressWarnings("UnusedParameters")
	public void StartNewGameStartGameCallback(View view) {
		gameState = ((FragmentStartNewGame) currentFragment).getGameState();
		if (gameState.Mercenary[0].pilot + gameState.Mercenary[0].fighter +
				gameState.Mercenary[0].engineer + gameState.Mercenary[0].trader > 20){
			return;
		}
		GameState.isValid = true;
		this.saveGame();
		changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
		SharedPreferences sp = getSharedPreferences("spacetrader", MODE_PRIVATE);
		if (sp.getBoolean("firstTime", true)) {
			showFirstStepsHelp();
			SharedPreferences.Editor ed = sp.edit();
			ed.putBoolean("firstTime", false);
			ed.commit();
		}
	}

	// FragmentSystemInformation
	@SuppressWarnings("UnusedParameters")
	public void SystemInformationNewspaperCallback(View view) {
		((FragmentSystemInformation) currentFragment).showNewspaper();
	}

	@SuppressWarnings("UnusedParameters")
	public void SystemInformationPersonnelRosterCallback(View view) {
		changeFragment(FRAGMENTS.PERSONNEL_ROSTER);
	}

	@SuppressWarnings("UnusedParameters")
	public void SystemInformationSpecialCallback(View view) {
		((FragmentSystemInformation) currentFragment).special();
	}

	// FragmentCommanderStatus
	@SuppressWarnings("UnusedParameters")
	public void CommanderStatusQuestsCallback(View view) {
		String quests = "";
		if (gameState.MonsterStatus == 1) {
			quests += "Kill the space monster at Acamar.\n";
		}

		if (gameState.DragonflyStatus >= 1 && gameState.DragonflyStatus <= 4) {
			quests += "Follow the Dragonfly to ";
			if (gameState.DragonflyStatus == 1) {
				quests += "Baratas.\n";
			} else if (gameState.DragonflyStatus == 2) {
				quests += "Melina.\n";
			} else if (gameState.DragonflyStatus == 3) {
				quests += "Regulas.\n";
			} else if (gameState.DragonflyStatus == 4) {
				quests += "Zalkon.\n";
			}
		} else if (gameState.SolarSystem[GameState.ZALKONSYSTEM].special == GameState.INSTALLLIGHTNINGSHIELD) {
			quests += "Get your lightning shield at Zalkon.\n";
		}

		if (gameState.JaporiDiseaseStatus == 1) {
			quests += "Deliver antidote to Japori.\n";
		}

		if (gameState.ArtifactOnBoard) {
			quests += "Deliver the alien artifact to professor Berger at some hi-tech system.\n";
		}

		if (gameState.WildStatus == 1) {
			quests += "Smuggle Jonathan Wild to Kravat.\n";
		}

		if (gameState.JarekStatus == 1) {
			quests += "Bring ambassador Jarek to Devidia.\n";
		}

		// I changed this, and the reused the code in the Experiment quest.
		// I think it makes more sense to display the time remaining in
		// this fashion. SjG 10 July 2002
		if (gameState.InvasionStatus >= 1 && gameState.InvasionStatus < 7) {
			quests += "Inform Gemulon about alien invasion";
			if (gameState.InvasionStatus == 6) {
				quests += " by tomorrow";
			} else {
				quests += String.format(" within %d days", 6 - gameState.InvasionStatus);
			}
			quests += ".\n";
		} else if (gameState.SolarSystem[GameState.GEMULONSYSTEM].special == GameState.GETFUELCOMPACTOR) {
			quests += "Get your fuel compactor at Gemulon.\n";
		}

		if (gameState.ExperimentStatus >= 1 && gameState.ExperimentStatus < 11) {
			quests += "Stop Dr. Fehler's experiment at Daled ";

			if (gameState.ExperimentStatus == 10) {
				quests += "by tomorrow";
			} else {
				quests += String.format("within %d days", 11 - gameState.ExperimentStatus);
			}
			quests += ".\n";
		}

		if (gameState.ReactorStatus >= 1 && gameState.ReactorStatus < 21) {
			quests += "Deliver the unstable reactor to Nix ";
			if (gameState.ReactorStatus < 2) {
				quests += "for Henry Morgan.\n";
			} else {
				quests += "before it consumes all its fuel.\n";
			}
		}

		if (gameState.SolarSystem[GameState.NIXSYSTEM].special == GameState.GETSPECIALLASER) {
			quests += "Get your special laser at Nix.\n";
		}

		if (gameState.ScarabStatus == 1) {
			quests += "Find and destroy the Scarab (which is hiding at the exit to a wormhole).\n";
		}

		if (gameState.Ship.tribbles > 0) {
			quests += "Get rid of those pesky tribbles.\n";
		}

		if (gameState.MoonBought) {
			quests += "Claim your moon at Utopia.\n";
		}

		if (quests.length() == 0) {
			quests = "There are no open quests.\n";
		}

		Popup popup = new Popup(this, "Open Quests", quests, "", "OK", cbShowNextPopup);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void CommanderStatusSpecialCargoCallback(View view) {
		String buf = "";
		if (gameState.Ship.tribbles > 0) {
			if (gameState.Ship.tribbles >= GameState.MAXTRIBBLES) {
				buf += "An infestation of tribbles.\n";
			} else {
				buf += String.format("%d cute furry tribble%s.\n", gameState.Ship.tribbles,
					gameState.Ship.tribbles == 1 ? "" : "s");
			}
		}

		if (gameState.JaporiDiseaseStatus == 1) {
			buf += "10 bays of antidote.\n";
		}
		if (gameState.ArtifactOnBoard) {
			buf += "An alien artifact.\n";
		}
		if (gameState.JarekStatus == 2) {
			buf += "A haggling computer.\n";
		}
		if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			buf += "An unstable reactor taking up 5 bays.\n";
			buf += String.format("%d bay%s of enriched fuel.\n", 10 - ((gameState.ReactorStatus - 1) / 2),
				(10 - ((gameState.ReactorStatus - 1) / 2)) > 1 ? "s" : "");

		}
		if (gameState.CanSuperWarp) {
			buf += "A Portable Singularity.\n";
		}

		if (buf.length() == 0) {
			buf = "No special cargo.";
		}

		Popup popup = new Popup(this, "Special Cargo", buf, "", "OK", cbShowNextPopup);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void CommanderStatusShipCallback(View view) {
		int i, j, k, FirstEmptySlot;
		String buf;

		buf = String.format("Type: %s%s\n", gameState.Ship.getType().name,
			gameState.ScarabStatus == 3 ? "/hardened hull" : "");

		buf += "Equipment:\n";

		for (i = 0; i < GameState.MAXWEAPONTYPE + GameState.EXTRAWEAPONS; ++i) {
			j = 0;
			for (k = 0; k < GameState.MAXWEAPON; ++k) {
				if (gameState.Ship.weapon[k] == i) {
					++j;
				}
			}
			if (j > 0) {
				buf += String.format("%d %s%s\n", j, Weapons.mWeapons[i].name, j > 1 ? "s" : "");
			}
		}

		for (i = 0; i < GameState.MAXSHIELDTYPE + GameState.EXTRASHIELDS; ++i) {
			j = 0;
			for (k = 0; k < GameState.MAXSHIELD; ++k) {
				if (gameState.Ship.shield[k] == i) {
					++j;
				}
			}
			if (j > 0) {
				buf += String.format("%d %s%s\n", j, Shields.mShields[i].name, j > 1 ? "s" : "");
			}
		}
		for (i = 0; i < GameState.MAXGADGETTYPE + GameState.EXTRAGADGETS; ++i) {
			j = 0;
			for (k = 0; k < GameState.MAXGADGET; ++k) {
				if (gameState.Ship.gadget[k] == i) {
					++j;
				}
			}
			if (j > 0) {
				if (i == GameState.EXTRABAYS) {
					buf += String.format("%d extra cargo bays\n", j * 5);
				} else {
					buf += String.format("%s\n", Gadgets.mGadgets[i].name);
				}
			}
		}

		if (gameState.EscapePod) {
			buf += "An escape pod\n";
		}

		if (gameState.Ship.AnyEmptySlots()) {
			buf += "Unfilled:\n";

			FirstEmptySlot = gameState.GetFirstEmptySlot(gameState.Ship.getType().weaponSlots,
				gameState.Ship.weapon);
			if (FirstEmptySlot >= 0) {
				buf += String.format("%d weapon slot%s\n",
					gameState.Ship.getType().weaponSlots - FirstEmptySlot,
					(gameState.Ship.getType().weaponSlots - FirstEmptySlot) == 1 ? "" : "s");
			}

			FirstEmptySlot = gameState.GetFirstEmptySlot(gameState.Ship.getType().shieldSlots,
				gameState.Ship.shield);
			if (FirstEmptySlot >= 0) {
				buf += String.format("%d shield slot%s\n",
					gameState.Ship.getType().shieldSlots - FirstEmptySlot,
					(gameState.Ship.getType().shieldSlots - FirstEmptySlot) == 1 ? "" : "s");
			}

			FirstEmptySlot = gameState.GetFirstEmptySlot(gameState.Ship.getType().gadgetSlots,
				gameState.Ship.gadget);
			if (FirstEmptySlot >= 0) {
				buf += String.format("%d gadget slot%s\n",
					gameState.Ship.getType().gadgetSlots - FirstEmptySlot,
					(gameState.Ship.getType().gadgetSlots - FirstEmptySlot) == 1 ? "" : "s");
			}
		}
		Popup popup = new Popup(this, "Ship Status", buf, "", "OK", cbShowNextPopup);
		popupQueue.push(popup);
		showNextPopup();
	}

	// FragmentBank
	@SuppressWarnings("UnusedParameters")
	public void btnBankGetLoan(View view) {
		Popup popup;
		if (gameState.Debt >= gameState.MaxLoan()) {
			popup = new Popup(this, "Debt too high!", "Your debt is too high to get another loan.", "",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}
		popup = new Popup(this, "Get Loan", String.format(
			"How much do you want?\nYou can borrow up to %d credits.", gameState.MaxLoan()), "Credits",
			"", gameState.MaxLoan(), "Get loan", "Don't get loan", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				SeekBar seekBar = (SeekBar) view;
				int amount = seekBar.getProgress();
				if (amount > 0) {
					amount = Math.min(gameState.MaxLoan(), amount);
					gameState.Credits += amount;
					gameState.Debt += amount;
					changeFragment(FRAGMENTS.BANK);
				}
			}
		}, cbShowNextPopup, new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				gameState.Credits += gameState.MaxLoan();
				gameState.Debt += gameState.MaxLoan();
				changeFragment(FRAGMENTS.BANK);
				showNextPopup();
			}
		}
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void btnBankPaybackLoan(View view) {
		Popup popup;
		if (gameState.Debt <= 0) {
			popup = new Popup(this, "No debt.", "You don't have a loan to pay back.", "", "OK",
				cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}
		popup = new Popup(this, "Payback Loan", String.format(
			"You have a debt of %d credits.\nHow much do you want to pay back?", gameState.Debt),
			"Credits", "", gameState.Debt, "Pay back", "Don't pay back", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				SeekBar seekBar = (SeekBar) view;
				int amount = seekBar.getProgress();
				if (amount > 0) {
					amount = Math.min(gameState.Debt, amount);
					if (amount > gameState.Credits) {
						Popup popup1 = new Popup(popup.context, "Not enough credits!", String.format(
							"You only have %d credits. You can't pay back more than that!", gameState.Credits),
							"", "OK", cbShowNextPopup
						);
						popupQueue.push(popup1);
						return;
					}
					gameState.Credits -= amount;
					gameState.Debt -= amount;
					changeFragment(FRAGMENTS.BANK);
				}
			}
		}, cbShowNextPopup, new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				int amount = Math.min(gameState.Debt, gameState.Credits);
				gameState.Credits -= amount;
				gameState.Debt -= amount;
				changeFragment(FRAGMENTS.BANK);
			}
		}
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void btnBankBuyInsurance(View view) {
		Popup popup;
		if (gameState.Insurance) {
			popup = new Popup(this, "Stop Insurance",
				"Do you really wish to stop your insurance and lose your no-claim?", "", "Yes", "No",
				new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						gameState.Insurance = false;
						gameState.NoClaim = 0;
						changeFragment(FRAGMENTS.BANK);
					}
				}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			if (!gameState.EscapePod) {
				popup = new Popup(this, "No Escape Pod",
					"Insurance isn't useful for you, since you don't have an escape pod.", "", "OK",
					cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
			gameState.Insurance = true;
			changeFragment(FRAGMENTS.BANK);
		}
	}

	// FragmentShipyard
	public void btnShipyardBuyFuel(int amount) {
		int MaxFuel;
		int Parsecs;

		MaxFuel = (gameState.Ship.GetFuelTanks() - gameState.Ship.GetFuel()) * gameState.Ship
			.getType().costOfFuel;
		amount = Math.min(amount, MaxFuel);
		amount = Math.max(0, Math.min(amount, gameState.Credits));

		Parsecs = amount / gameState.Ship.getType().costOfFuel;

		gameState.Ship.fuel += Parsecs;
		gameState.Credits -= Parsecs * gameState.Ship.getType().costOfFuel;
		changeFragment(FRAGMENTS.SHIPYARD);
	}

	@SuppressWarnings("UnusedParameters")
	public void btnShipyardBuyFuel(View view) {
		Popup popup;
		popup = new Popup(this, "Buy Fuel", "How much do you want to spend maximally on fuel?",
			"Credits",
			"Enter the amount of credits you wish to spend on fuel and tap OK. Your fuel tank will be filled with as much fuel as you can buy with that amount of credits.",
			gameState.Credits, "Buy fuel", "Don't buy fuel", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				SeekBar seekBar = (SeekBar) view;
				try {
					int amount = seekBar.getProgress();
					btnShipyardBuyFuel(amount);
				} catch (NumberFormatException e) {
					Popup popup1 = new Popup(popup.context, "Error", e.getLocalizedMessage(), "", "OK",
						cbShowNextPopup);
					popupQueue.push(popup1);
				}
			}
		}, cbShowNextPopup, new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				btnShipyardBuyMaxFuel(null);
			}
		}
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void btnShipyardBuyMaxFuel(View view) {
		btnShipyardBuyFuel(gameState.Credits);
	}

	public void btnShipyardBuyRepairs(int amount) {
		int MaxRepairs;
		int Percentage;

		MaxRepairs = (gameState.Ship.GetHullStrength() - gameState.Ship.hull) * gameState.Ship
			.getType().repairCosts;
		amount = Math.min(amount, MaxRepairs);
		amount = Math.max(0, Math.min(amount, gameState.Credits));

		Percentage = amount / gameState.Ship.getType().repairCosts;

		gameState.Ship.hull += Percentage;
		gameState.Credits -= Percentage * gameState.Ship.getType().repairCosts;
		changeFragment(FRAGMENTS.SHIPYARD);
	}

	@SuppressWarnings("UnusedParameters")
	public void btnShipyardBuyRepairs(View view) {
		Popup popup;
		popup = new Popup(this, "Buy Repairs", "How much do you want to spend maximally on repairs?",
			"Credits",
			"Enter the amount of credits you wish to spend on repairs and tap OK. Your ship will be repaired as much as possible for the amount of credits.",
			gameState.Credits, "Buy Repairs", "Don't buy repairs", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				SeekBar seekBar = (SeekBar) view;
				int amount = seekBar.getProgress();
				btnShipyardBuyRepairs(amount);
			}
		}, cbShowNextPopup, new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				btnShipyardBuyRepairs(popup.max);
			}
		}
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void btnShipyardBuyFullRepairs(View view) {
		btnShipyardBuyRepairs(gameState.Credits);
	}

	@SuppressWarnings("UnusedParameters")
	public void btnShipyardBuyEscapePod(View view) {
		Popup popup;
		popup = new Popup(this, "Buy Escape Pod", "Do you want to buy an escape pod for 2000 credits?",
			"When your ship has an escape pod, when it is destroyed, you are automatically ejected from it and you will be picked up by the Space Corps after a few days and dropped on a nearby system. You will lose your ship and cargo, but not your life. If you also have taken an insurance on your ship at the bank, the bank will fully refund your ship's costs. Your crew will also be saved in their own escape pods, but they will return to their home systems.",
			"Buy pod", "Don't buy pod", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				gameState.Credits -= 2000;
				gameState.EscapePod = true;
				changeFragment(FRAGMENTS.SHIPYARD);
			}
		}, cbShowNextPopup
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	// FragmentBuyNewShip
	@SuppressWarnings("UnusedParameters")
	public void btnBuyNewShip(View view) {
		changeFragment(FRAGMENTS.BUY_NEW_SHIP);
	}

	public void btnBuyNewShipInfo(View view) {
		gameState.ShipInfoId = -1;
		switch (view.getId()) {
			case R.id.btnInfoWasp:
				gameState.ShipInfoId++;
			case R.id.btnInfoTermite:
				gameState.ShipInfoId++;
			case R.id.btnInfoGrasshopper:
				gameState.ShipInfoId++;
			case R.id.btnInfoHornet:
				gameState.ShipInfoId++;
			case R.id.btnInfoBeetle:
				gameState.ShipInfoId++;
			case R.id.btnInfoBumblebee:
				gameState.ShipInfoId++;
			case R.id.btnInfoMosquito:
				gameState.ShipInfoId++;
			case R.id.btnInfoFirefly:
				gameState.ShipInfoId++;
			case R.id.btnInfoGnat:
				gameState.ShipInfoId++;
			case R.id.btnInfoFlea:
				gameState.ShipInfoId++;
		}
		changeFragment(FRAGMENTS.SHIP_INFO);
	}

	public void btnBuyNewShipStep1(View view) {
		int Index;
		int i, j;
		int extra = 0;
		boolean hasCompactor = false;
		boolean hasMorganLaser = false;

		switch (view.getId()) {
			case R.id.btnBuyFlea:
				Index = 0;
				break;
			case R.id.btnBuyGnat:
				Index = 1;
				break;
			case R.id.btnBuyFirefly:
				Index = 2;
				break;
			case R.id.btnBuyMosquito:
				Index = 3;
				break;
			case R.id.btnBuyBumblebee:
				Index = 4;
				break;
			case R.id.btnBuyBeetle:
				Index = 5;
				break;
			case R.id.btnBuyHornet:
				Index = 6;
				break;
			case R.id.btnBuyGrasshopper:
				Index = 7;
				break;
			case R.id.btnBuyTermite:
				Index = 8;
				break;
			case R.id.btnBuyWasp:
				Index = 9;
				break;
			default:
				Index = 0;
		}
		j = 0;
		for (i = 0; i < GameState.MAXCREW; ++i) {
			if (gameState.Ship.crew[i] >= 0) {
				++j;
			}
		}
		if (j > ShipTypes.ShipTypes[Index].crewQuarters) {
			Popup popup = new Popup(this, "Too Many Crewmembers",
				"The new ship you picked doesn't have enough quarters for all of your crewmembers. First you will have to fire one or more of them.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.ShipPrice[Index] == 0) {
			Popup popup = new Popup(this, "Ship Not Available",
				"That type of ship is not available in the current system.", "", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		} else if ((gameState.ShipPrice[Index] > 0) && (gameState.Debt > 0)) {
			Popup popup;
			popup = new Popup(this, "You Are In Debt", "You can't buy that as long as you have debts.",
				"Before you can buy a new ship or new equipment, you must settle your debts at the bank.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		} else if (gameState.ShipPrice[Index] > gameState.ToSpend()) {
			Popup popup = new Popup(this, "Not Enough Money",
				"You don't have enough money to buy this ship.", "", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		} else if ((gameState.JarekStatus == 1) && (gameState.WildStatus == 1) && (ShipTypes.ShipTypes[Index].crewQuarters < 3)) {
			Popup popup = new Popup(this, "Passengers Needs Quarters",
				"You must get a ship with enough crew quarters so that Ambassador Jarek and Jonathan Wild can stay on board.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		} else if ((gameState.JarekStatus == 1) && (ShipTypes.ShipTypes[Index].crewQuarters < 2)) {
			Popup popup;
			popup = new Popup(this, "Passenger Needs Quarters",
				"You must get a ship with enough crew quarters so that Ambassador Jarek can stay on board.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		} else if ((gameState.WildStatus == 1) && (ShipTypes.ShipTypes[Index].crewQuarters < 2)) {
			Popup popup;
			popup = new Popup(this, "Passenger Needs Quarters",
				"You must get a ship with enough crew quarters so that Jonathan Wild can stay on board.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		} else if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			Popup popup;
			popup = new Popup(this, "Shipyard Engineer",
				"Sorry! We can't take your ship as a trade-in. That Ion Reactor looks dangerous, and we have no way of removing it. Come back when you've gotten rid of it.",
				"You can't sell your ship as long as you have an Ion Reactor on board. Deliver the Reactor to Nix, and then you'll be able to get a new ship.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		i = gameState.Ship.HasShield(GameState.LIGHTNINGSHIELD);
		if (i > 0) {
			if (ShipTypes.ShipTypes[Index].shieldSlots < i) {
				// can't transfer the Lightning Shields. How often would this happen?
				Popup popup = new Popup(this, "Can't Transfer Item", String.format(
					"If you trade your ship in for a %s, you won't be able to transfer your Lightning Shield because the new ship has insufficient shield slots!",
					ShipTypes.ShipTypes[Index].name), "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				extra += i * 30000;
			}
		}

		if (gameState.Ship.HasGadget(GameState.FUELCOMPACTOR)) {
			if (ShipTypes.ShipTypes[Index].gadgetSlots == 0) {
				// can't transfer the Fuel Compactor
				Popup popup = new Popup(this, "Can't Transfer Item", String.format(
					"If you trade your ship in for a %s, you won't be able to transfer your Fuel Compactor because the new ship has insufficient gadget slots!",
					ShipTypes.ShipTypes[Index].name), "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				hasCompactor = true;
				extra += 20000;
			}
		}

		if (gameState.Ship.HasWeapon(GameState.MORGANLASERWEAPON, true)) {
			if (ShipTypes.ShipTypes[Index].weaponSlots == 0) {
				// can't transfer the Laser
				Popup popup = new Popup(this, "Can't Transfer Item", String.format(
					"If you trade your ship in for a %s, you won't be able to transfer Morgans Laser because the new ship has insufficient weapon slots!",
					ShipTypes.ShipTypes[Index].name), "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				extra += 33333;
				hasMorganLaser = true;
			}
		}

		if (gameState.ShipPrice[Index] + extra > gameState.ToSpend()) {
			Popup popup = new Popup(this, "Not Enough Money",
				"You won't have enough money to buy this ship and pay the cost to transfer all of your unique equipment. You should choose carefully which items you wish to transfer!",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		}
		extra = 0;

		btnBuyNewShipStep1CheckLightningShields(Index, extra, 0, 0, hasCompactor, hasMorganLaser);
	}

	public void btnBuyNewShipStep1CheckLightningShields(final int Index, final int ex, final int cntLightning, final int numLightning, final boolean hasCompactor, final boolean hasMorganLaser) {
		final int[] extra = new int[1];
		extra[0] = ex;
		if (cntLightning < gameState.Ship.HasShield(
			GameState.LIGHTNINGSHIELD) && ShipTypes.ShipTypes[Index].shieldSlots - (numLightning + 1) > 0) {
			if (gameState.ShipPrice[Index] + extra[0] <= gameState.ToSpend()) {
				Popup popup;
				popup = new Popup(this, "Transfer Lightning Shield",
					"I see you have a lightning shield. I'll transfer it to your new ship for 30000 credits.",
					"For the sum of 30000 credits, you get to keep your unique lightning shield! This may seem to be a lot of money, but you must remember that this is the exact amount the shield is currently worth, and it has already been subtracted from the price for which the new ship is offered. So actually, this is a very good deal.",
					"Transfer shield", "Leave shield", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						extra[0] += 30000;
						btnBuyNewShipStep1CheckLightningShields(Index, extra[0], cntLightning + 1,
							numLightning + 1, hasCompactor, hasMorganLaser);
					}
				}, new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						btnBuyNewShipStep1CheckLightningShields(Index, extra[0], cntLightning + 1, numLightning,
							hasCompactor, hasMorganLaser);
					}
				}
				);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				Popup popup = new Popup(this, "Can't Transfer Item",
					"Unfortunately, if you make this trade, you won't be able to afford to transfer your Lightning Shield to the new ship!",
					"", "OK", cbShowNextPopup);
				btnBuyNewShipStep1CheckLightningShields(Index, extra[0], cntLightning + 1, numLightning,
					hasCompactor, hasMorganLaser);
				popupQueue.push(popup);
				showNextPopup();
			}
		} else {
			btnBuyNewShipStep1CheckFuelCompactor(Index, extra[0], numLightning, hasCompactor,
				hasMorganLaser);
		}
	}

	public void btnBuyNewShipStep1CheckFuelCompactor(final int Index, final int ex, final int addLightning, final boolean hasCompactor, final boolean hasMorganLaser) {
		final int[] extra = new int[1];
		extra[0] = ex;
		if (hasCompactor && ShipTypes.ShipTypes[Index].gadgetSlots > 0) {
			if (gameState.ShipPrice[Index] + extra[0] <= gameState.ToSpend()) {
				Popup popup;
				popup = new Popup(this, "Transfer Fuel Compactor",
					"I see you have a fuel compactor. I'll transfer it to your new ship for 20000 credits.",
					"For the sum of 20000 credits, you get to keep your unique fuel compactor! This may seem to be a lot of money, but you must remember that this is the exact amount the fuel compactor is currently worth, and it has already been subtracted from the price for which the new ship is offered. So actually, this is a very good deal.",
					"Transfer Fuel Compactor", "Leave Fuel Compactor", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						extra[0] += 20000;
						btnBuyNewShipStep1CheckMorgansLaser(Index, extra[0], addLightning, true,
							hasMorganLaser);
					}
				}, new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						btnBuyNewShipStep1CheckMorgansLaser(Index, extra[0], addLightning, false,
							hasMorganLaser);
					}
				}
				);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				Popup popup;
				popup = new Popup(this, "Can't Transfer Item",
					"Unfortunately, if you make this trade, you won't be able to afford to transfer your Fuel Compactor to the new ship!",
					"", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				btnBuyNewShipStep1CheckMorgansLaser(Index, extra[0], addLightning, false, hasMorganLaser);
			}
		}
		btnBuyNewShipStep1CheckMorgansLaser(Index, extra[0], addLightning, false, hasMorganLaser);
	}

	public void btnBuyNewShipStep1CheckMorgansLaser(final int Index, int ex, final int addLightning, final boolean addCompactor, boolean hasMorganLaser) {
		final int[] extra = new int[1];
		extra[0] = ex;
		if (hasMorganLaser && ShipTypes.ShipTypes[Index].weaponSlots > 0) {
			if (gameState.ShipPrice[Index] + extra[0] <= gameState.ToSpend()) {
				Popup popup;
				popup = new Popup(this, "Transfer Morgan's Laser",
					"I see you have a customized laser. I'll transfer it to your new ship for 33333 credits.",
					"For the sum of 33333 credits, you get to keep the laser given to you by Henry Morgan! This may seem to be a lot of money, but you must remember that this is the exact amount the laser is currently worth, and it has already been subtracted from the price for which the new ship is offered. So actually, this is a very good deal.",
					"Transfer Laser", "Leave Laser", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						extra[0] += 33333;
						btnBuyNewShipStep2(Index, extra[0], addLightning, addCompactor, true);
					}
				}, new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						btnBuyNewShipStep2(Index, extra[0], addLightning, addCompactor, false);
					}
				}
				);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				Popup popup;
				popup = new Popup(this, "Can't Transfer Item",
					"Unfortunately, if you make this trade, you won't be able to afford to transfer Morgan's Laser to the new ship!",
					"", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				btnBuyNewShipStep2(Index, extra[0], addLightning, addCompactor, false);
			}
		}
		btnBuyNewShipStep2(Index, extra[0], addLightning, addCompactor, false);
	}

	public void btnBuyNewShipStep2(final int Index, final int extra, final int addLightning, final boolean addCompactor, final boolean addMorganLaser) {
		Popup popup = new Popup(this, "Buy New Ship", String.format(
			"Are you sure you wish to trade in your %s for a new %s%s?", gameState.Ship.getType().name,
			ShipTypes.ShipTypes[Index].name, (addCompactor || addLightning > 0 || addMorganLaser) ?
				", and transfer your unique equipment to the new ship" : ""
		), "", "Buy ship", "Don't buy ship", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				gameState.BuyShip(Index);
				gameState.Credits -= extra;
				if (addCompactor) {
					gameState.Ship.gadget[0] = GameState.FUELCOMPACTOR;
				}
				for (int i = 0; i < addLightning; i++) {
					gameState.Ship.shield[i] = GameState.LIGHTNINGSHIELD;
				}
				if (addMorganLaser) {
					gameState.Ship.weapon[0] = GameState.MORGANLASERWEAPON;
				}
				gameState.Ship.tribbles = 0;
				btnBuyNewShip(null);
			}
		}, cbShowNextPopup
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	// FragmentBuyEquipment
	public void BuyEquipmentButtonCallback(View view) {
		int Index;

		Index = -1;
		switch (view.getId()) {
			case R.id.btnBuyCloakingSystem:
				Index++;
			case R.id.btnBuyTargetingSystem:
				Index++;
			case R.id.btnBuyNavigationSystem:
				Index++;
			case R.id.btnBuyAutoRepairSystem:
				Index++;
			case R.id.btnBuy5CargoBays:
				Index++;
			case R.id.btnBuyReflectiveShield:
				Index++;
			case R.id.btnBuyEnergyShield:
				Index++;
			case R.id.btnBuyMilitaryLaser:
				Index++;
			case R.id.btnBuyBeamLaser:
				Index++;
			case R.id.btnBuyPulseLaser:
				Index++;
				break;
			default:
				return;
		}
		if (Index < GameState.MAXWEAPONTYPE) {
			BuyItem(gameState.Ship.getType().weaponSlots, gameState.Ship.weapon,
				gameState.BASEWEAPONPRICE(Index), Weapons.mWeapons[Index].name, Index);
		} else if (Index >= GameState.MAXWEAPONTYPE && Index < (GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE)) {
			BuyItem(gameState.Ship.getType().shieldSlots, gameState.Ship.shield,
				gameState.BASESHIELDPRICE(Index - GameState.MAXWEAPONTYPE),
				Shields.mShields[Index - GameState.MAXWEAPONTYPE].name, Index - GameState.MAXWEAPONTYPE);
		} else if (Index >= GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE && Index < GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE + GameState.MAXGADGETTYPE) {
			if (gameState.Ship.HasGadget(
				Index - (GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE)) && GameState.EXTRABAYS != (Index - (GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE))) {
				Popup popup;
				popup = new Popup(this, "You Already Have One",
					"It's not useful to buy more than one of this item.", "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
			BuyItem(gameState.Ship.getType().gadgetSlots, gameState.Ship.gadget,
				gameState.BASEGADGETPRICE(Index - (GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE)),
				Gadgets.mGadgets[Index - (GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE)].name,
				Index - (GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE));
		}
		changeFragment(FRAGMENTS.BUY_EQUIPMENT);
	}

	public void BuyItem(int Slots, final int[] Item, final int Price, String Name, final int ItemIndex) {
		// *************************************************************************
		// Buy an item: Slots is the number of slots, Item is the array in the
		// Ship record which contains the item type, Price is the costs,
		// Name is the name of the item and ItemIndex is the item type number
		// *************************************************************************
		final int FirstEmptySlot;
		Popup popup;

		FirstEmptySlot = gameState.GetFirstEmptySlot(Slots, Item);

		if (Price <= 0) {
			popup = new Popup(this, "Not Available", "That item is not available in this system.",
				"Each item is only available in a system which has the technological development needed to produce it.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else if (gameState.Debt > 0) {
			popup = new Popup(this, "You Have A Debt", "You can't buy that as long as you have debts.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else if (Price > gameState.ToSpend()) {
			popup = new Popup(this, "Not enough money", "You do not have enough money to buy this item.",
				"If you can't pay the price mentioned to the right of an item, you can't get it. If you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else if (FirstEmptySlot < 0) {
			popup = new Popup(this, "Not Enough Slots",
				"You have already filled all of your available slots for this type of item.", "", "OK",
				cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			popup = new Popup(this, "Buy " + Name, String.format(
				"Do you wish to buy this item for %d credits?", Price),
				"Tap Yes if you want to buy the item in the title for the price mentioned.", "Buy",
				"Don't buy", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					Item[FirstEmptySlot] = ItemIndex;
					gameState.Credits -= Price;
					changeFragment(FRAGMENTS.BUY_EQUIPMENT);
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		}
	}

	// FragmentSellEquipment
	public void btnSellEquipmentOnClick(View view) {
		int idx = -1;
		switch (view.getId()) {
			case R.id.btnSellEquipmentGadget3:
				idx++;
			case R.id.btnSellEquipmentGadget2:
				idx++;
			case R.id.btnSellEquipmentGadget1:
				idx++;
			case R.id.btnSellEquipmentShield3:
				idx++;
			case R.id.btnSellEquipmentShield2:
				idx++;
			case R.id.btnSellEquipmentShield1:
				idx++;
			case R.id.btnSellEquipmentWeapon3:
				idx++;
			case R.id.btnSellEquipmentWeapon2:
				idx++;
			case R.id.btnSellEquipmentWeapon1:
				idx++;
				break;
			default:
				return;
		}
		final int Index = idx;
		Popup popup;
		popup = new Popup(this, "Sell Item", "Are you sure you want to sell this item?",
			"Selling an item will return to you about 75% of what you first paid for it. If you sell a ship as a whole, all items on it will automatically be sold.",
			"Sell Item", "Don't sell item", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				if (Index < GameState.MAXWEAPONTYPE) {
					gameState.Credits += gameState.WEAPONSELLPRICE(Index);
					//noinspection ManualArrayCopy
					for (int i = Index + 1; i < GameState.MAXWEAPON; ++i) {
						gameState.Ship.weapon[i - 1] = gameState.Ship.weapon[i];
					}
					gameState.Ship.weapon[GameState.MAXWEAPON - 1] = -1;
				} else if (Index >= GameState.MAXWEAPONTYPE && Index < (GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE)) {
					gameState.Credits += gameState.SHIELDSELLPRICE(Index - GameState.MAXWEAPON);
					for (int i = Index - GameState.MAXWEAPON + 1; i < GameState.MAXSHIELD; ++i) {
						gameState.Ship.shield[i - 1] = gameState.Ship.shield[i];
						gameState.Ship.shieldStrength[i - 1] = gameState.Ship.shieldStrength[i];
					}
					gameState.Ship.shield[GameState.MAXSHIELD - 1] = -1;
					gameState.Ship.shieldStrength[GameState.MAXSHIELD - 1] = 0;
				} else if (Index >= GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE && Index < GameState.MAXWEAPONTYPE + GameState.MAXSHIELDTYPE + GameState.MAXGADGETTYPE) {
					if (gameState.Ship.gadget[Index - GameState.MAXWEAPON - GameState.MAXSHIELD] == GameState.EXTRABAYS) {
						if (gameState.Ship.FilledCargoBays() > gameState.Ship.TotalCargoBays() - 5) {
							Popup popup1;
							popup1 = new Popup(popup.context, "Cargo Bays Full",
								"The extra cargo bays are still filled with goods. You can only sell them when they're empty.",
								"First you need to sell some trade goods. When you have at least 5 empty bays, you can sell the extra cargo bays.",
								"OK", cbShowNextPopup);
							popupQueue.push(popup1);
							showNextPopup();
							return;
						}
					}
					gameState.Credits += gameState.GADGETSELLPRICE(
						Index - GameState.MAXWEAPON - GameState.MAXSHIELD);
					//noinspection ManualArrayCopy
					for (int i =
						Index - GameState.MAXWEAPON - GameState.MAXSHIELD + 1; i < GameState.MAXGADGET; ++i) {
						gameState.Ship.gadget[i - 1] = gameState.Ship.gadget[i];
					}
					gameState.Ship.gadget[GameState.MAXGADGET - 1] = -1;
				}
				changeFragment(FRAGMENTS.SELL_EQUIPMENT);
			}
		}, cbShowNextPopup
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	// FragmentBuyCargo
	public void btnBuyCargoCallback(View view) {
		int idx;
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		Popup popup;

		idx = -1;
		switch (view.getId()) {
			case R.id.btnPriceListBuy10:
			case R.id.btnBuyCargo10:
				idx++;
			case R.id.btnPriceListBuy9:
			case R.id.btnBuyCargo9:
				idx++;
			case R.id.btnPriceListBuy8:
			case R.id.btnBuyCargo8:
				idx++;
			case R.id.btnPriceListBuy7:
			case R.id.btnBuyCargo7:
				idx++;
			case R.id.btnPriceListBuy6:
			case R.id.btnBuyCargo6:
				idx++;
			case R.id.btnPriceListBuy5:
			case R.id.btnBuyCargo5:
				idx++;
			case R.id.btnPriceListBuy4:
			case R.id.btnBuyCargo4:
				idx++;
			case R.id.btnPriceListBuy3:
			case R.id.btnBuyCargo3:
				idx++;
			case R.id.btnPriceListBuy2:
			case R.id.btnBuyCargo2:
				idx++;
			case R.id.btnPriceListBuy1:
			case R.id.btnBuyCargo1:
				idx++;
				break;
			default:
				popup = new Popup(this, "Error", "No cargo selected.", "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
		}

		final int Index = idx;
		if (gameState.Debt > GameState.DEBTTOOLARGE) {
			popup = new Popup(this, "You Have A Debt", "You can't buy that as long as you have debts.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (CURSYSTEM.qty[Index] <= 0 || gameState.BuyPrice[Index] <= 0) {
			popup = new Popup(this, "Nothing Available", "None of these goods are available.", "", "OK",
				cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.Ship.TotalCargoBays() - gameState.Ship
			.FilledCargoBays() - gameState.LeaveEmpty <= 0) {
			popup = new Popup(this, "No Empty Bays",
				"You don't have any empty cargo holds available at the moment", "", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.ToSpend() < gameState.BuyPrice[Index]) {
			popup = new Popup(this, "Not Enough Money",
				"You don't have enough money to spend on any of these goods.",
				"At the bottom of the Buy Cargo screen, you see the credits you have available. You don't seem to have enough to buy at least one of the selected items. If you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		popup = new Popup(this, "Buy Cargo", String.format(
			"How many do you want to buy?\nAt %d cr. each you can afford %d.", gameState.BuyPrice[idx],
			Math.min(gameState.ToSpend() / gameState.BuyPrice[Index], CURSYSTEM.qty[Index])), "Amount",
			"Specify the amount to buy and tap the OK button. If you specify more than there is available, or than you can afford, or than your cargo bays can hold, the maximum possible amount will be bought. If you don't want to buy anything, tap the Cancel button.",
			Math.min(gameState.ToSpend() / gameState.BuyPrice[Index], CURSYSTEM.qty[Index]), "Buy",
			"Don't buy", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				int Amount;
				SeekBar seekBar = (SeekBar) view;
				Amount = seekBar.getProgress();
				if (Amount > 0) {
					BuyCargo(Index, Amount);
					if (gameState.currentState == FRAGMENTS.AVERAGE_PRICES) {
						changeFragment(FRAGMENTS.AVERAGE_PRICES);
					} else {
						changeFragment(FRAGMENTS.BUY_CARGO);
					}
				}
			}
		}, cbShowNextPopup, new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				BuyCargo(Index, popup.max);
				if (gameState.currentState == FRAGMENTS.AVERAGE_PRICES) {
					changeFragment(FRAGMENTS.AVERAGE_PRICES);
				} else {
					changeFragment(FRAGMENTS.BUY_CARGO);
				}
			}
		}
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	public void btnBuyCargoAllCallback(View view) {
		int idx;
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		Popup popup;

		idx = -1;
		switch (view.getId()) {
			case R.id.btnBuyCargoAll10:
				idx++;
			case R.id.btnBuyCargoAll9:
				idx++;
			case R.id.btnBuyCargoAll8:
				idx++;
			case R.id.btnBuyCargoAll7:
				idx++;
			case R.id.btnBuyCargoAll6:
				idx++;
			case R.id.btnBuyCargoAll5:
				idx++;
			case R.id.btnBuyCargoAll4:
				idx++;
			case R.id.btnBuyCargoAll3:
				idx++;
			case R.id.btnBuyCargoAll2:
				idx++;
			case R.id.btnBuyCargoAll1:
				idx++;
				break;
			default:
				popup = new Popup(this, "Error", "No cargo selected.", "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
		}

		final int Index = idx;
		if (gameState.Debt > GameState.DEBTTOOLARGE) {
			popup = new Popup(this, "You Have A Debt", "You can't buy that as long as you have debts.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (CURSYSTEM.qty[Index] <= 0 || gameState.BuyPrice[Index] <= 0) {
			popup = new Popup(this, "Nothing Available", "None of these goods are available.", "", "OK",
				cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.Ship.TotalCargoBays() - gameState.Ship
			.FilledCargoBays() - gameState.LeaveEmpty <= 0) {
			popup = new Popup(this, "No Empty Bays",
				"You don't have any empty cargo holds available at the moment", "", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.ToSpend() < gameState.BuyPrice[Index]) {
			popup = new Popup(this, "Not Enough Money",
				"You don't have enough money to spend on any of these goods.",
				"At the bottom of the Buy Cargo screen, you see the credits you have available. You don't seem to have enough to buy at least one of the selected items. If you have \"Reserve Money\" checked in the Options menu, the game will reserve at least enough money to pay for insurance and mercenaries.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		BuyCargo(Index, 999);
		changeFragment(FRAGMENTS.BUY_CARGO);
	}

	// FragmentSellCargo
	public void btnSellCargoCallback(View view) {
		int Index;
		Popup popup;

		Index = -1;
		switch (view.getId()) {
			case R.id.btnSellCargo10:
				Index++;
			case R.id.btnSellCargo9:
				Index++;
			case R.id.btnSellCargo8:
				Index++;
			case R.id.btnSellCargo7:
				Index++;
			case R.id.btnSellCargo6:
				Index++;
			case R.id.btnSellCargo5:
				Index++;
			case R.id.btnSellCargo4:
				Index++;
			case R.id.btnSellCargo3:
				Index++;
			case R.id.btnSellCargo2:
				Index++;
			case R.id.btnSellCargo1:
				Index++;
				break;
			default:
				popup = new Popup(this, "Error", "No cargo selected.", "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
		}


		if (gameState.Ship.cargo[Index] <= 0) {
			popup = new Popup(this, "None To Sell", "You have none of these goods in your cargo bays.",
				"On the Sell Cargo screen, the leftmost button shows the number of cargo bays you have which contain these goods. If that amount is zero, you can't sell anything.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}
		if (gameState.SellPrice[Index] <= 0) {
			popup = new Popup(this, "Not Interested",
				"Nobody in this system is interested in buying these goods.",
				"Notice that on the Sell Cargo screen, it says \"no trade\" next to these goods. This means that people aren't interested in buying them, either because of their political system, or because their tech level isn't high enough to make use of them.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		final int idx = Index;
		popup = new Popup(this, "Sell Cargo", String.format(
			"How many do you want to sell?\nYou can sell up to %d at %d cr. each.\nYour %s per unit is %d cr.\nYou paid about %d cr. each.",
			gameState.Ship.cargo[Index], gameState.SellPrice[Index],
			gameState.BuyingPrice[Index] / gameState.Ship.cargo[Index] > gameState.SellPrice[Index] ?
				"loss" : "profit",
			gameState.BuyingPrice[Index] / gameState.Ship.cargo[Index] > gameState.SellPrice[Index] ?
				gameState.BuyingPrice[Index] / gameState.Ship.cargo[Index] - gameState.SellPrice[Index] :
				gameState.SellPrice[Index] - gameState.BuyingPrice[Index] / gameState.Ship.cargo[Index],
			gameState.BuyingPrice[Index] / gameState.Ship.cargo[Index]
		), "Amount",
			"If you are selling items, specify the amount to sell and tap the OK button. If you specify more than you have in your cargo bays, the maximum possible amount will be sold. If you don't want to sell anything, tap the Cancel button.",
			gameState.Ship.cargo[Index], "Sell cargo", "Don't sell cargo", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				int Amount;
				SeekBar seekBar = (SeekBar) view;
				Amount = seekBar.getProgress();
				if (Amount > 0) {
					SellCargo(idx, Amount, GameState.SELLCARGO);
					changeFragment(FRAGMENTS.SELL_CARGO);
				}
			}
		}, cbShowNextPopup, new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				SellCargo(idx, 999, GameState.SELLCARGO);
				changeFragment(FRAGMENTS.SELL_CARGO);
			}
		}
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	public void btnSellCargoAllCallback(View view) {
		Popup popup;
		int Index;

		Index = -1;
		switch (view.getId()) {
			case R.id.btnSellCargoAll10:
				Index++;
			case R.id.btnSellCargoAll9:
				Index++;
			case R.id.btnSellCargoAll8:
				Index++;
			case R.id.btnSellCargoAll7:
				Index++;
			case R.id.btnSellCargoAll6:
				Index++;
			case R.id.btnSellCargoAll5:
				Index++;
			case R.id.btnSellCargoAll4:
				Index++;
			case R.id.btnSellCargoAll3:
				Index++;
			case R.id.btnSellCargoAll2:
				Index++;
			case R.id.btnSellCargoAll1:
				Index++;
				break;
			default:
				popup = new Popup(this, "Error", "No cargo selected.", "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
		}

		if (gameState.Ship.cargo[Index] <= 0) {
			popup = new Popup(this, "None To Sell", "You have none of these goods in your cargo bays.",
				"On the Sell Cargo screen, the leftmost button shows the number of cargo bays you have which contain these goods. If that amount is zero, you can't sell anything.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}
		if (gameState.SellPrice[Index] <= 0) {
			popup = new Popup(this, "Not Interested",
				"Nobody in this system is interested in buying these goods.",
				"Notice that on the Sell Cargo screen, it says \"no trade\" next to these goods. This means that people aren't interested in buying them, either because of their political system, or because their tech level isn't high enough to make use of them.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		SellCargo(Index, 999, GameState.SELLCARGO);
		changeFragment(FRAGMENTS.SELL_CARGO);
	}

	// FragmentWarpSystemInformation
	@SuppressWarnings("UnusedParameters")
	public void btnAveragePricesForm(View view) {
		changeFragment(FRAGMENTS.AVERAGE_PRICES);
	}

	// FragmentAveragePrices
	@SuppressWarnings("UnusedParameters")
	public void btnToggleAverageDiffPrices(View view) {
		gameState.PriceDifferences = !gameState.PriceDifferences;
		changeFragment(FRAGMENTS.AVERAGE_PRICES);
	}

	public void btnNextSystem(View view) {
		int nextSystem;
		nextSystem = NextSystemWithinRange(WarpSystem, view.getId() == R.id.btnPriceListPrev);
		if (nextSystem < 0 || nextSystem >= gameState.SolarSystem.length) {
			Toast.makeText(this, "Couldn't find another system within range!", Toast.LENGTH_SHORT).show();
			return;
		}
		if (nextSystem == gameState.WarpSystem) {
			Toast.makeText(this, "No other system in range!", Toast.LENGTH_SHORT).show();
			return;
		}
		gameState.WarpSystem = nextSystem;
		WarpSystem = gameState.SolarSystem[gameState.WarpSystem];
		changeFragment(gameState.currentState);
	}

	@SuppressWarnings("UnusedParameters")
	public void btnShortRangeChart(View view) {
		changeFragment(FRAGMENTS.SHORT_RANGE_CHART);
	}

	@SuppressWarnings("UnusedParameters")
	public void btnWarpSystemInformation(View view) {
		changeFragment(FRAGMENTS.WARP_SYSTEM_INFORMATION);
	}

	@SuppressWarnings("UnusedParameters")
	public void btnDoWarp(View view) {
		DoWarp(false);
	}

	public int NextSystemWithinRange(SolarSystem Current, boolean Back) {
		int i;
		//noinspection StatementWithEmptyBody
		for (i = 0; gameState.SolarSystem[i] != Current; i++) {
		}
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];

		if (Back) {
			--i;
		} else {
			++i;
		}

		while (true) {
			if (i < 0) {
				i = GameState.MAXSOLARSYSTEM - 1;
			} else if (i >= GameState.MAXSOLARSYSTEM) {
				i = 0;
			}
			if (gameState.SolarSystem[i] == Current) {
				break;
			}

			if (gameState.WormholeExists(COMMANDER.curSystem, i)) {
				return i;
			} else if (gameState.RealDistance(CURSYSTEM, gameState.SolarSystem[i]) <= gameState.Ship
				.GetFuel() && gameState.RealDistance(CURSYSTEM, gameState.SolarSystem[i]) > 0) {
				return i;
			}

			if (Back) {
				--i;
			} else {
				++i;
			}
		}

		return -1;
	}

	// FragmentOptions
	public void btnSetOption(View view) {
		CheckBox checkBox = (CheckBox) view;

		switch (checkBox.getId()) {
			case R.id.chkBoxIgnorePolice:
				gameState.AlwaysIgnorePolice = checkBox.isChecked();
				break;
			case R.id.chkBoxIgnorePiraces:
				gameState.AlwaysIgnorePirates = checkBox.isChecked();
				break;
			case R.id.chkBoxIgnoreTraders:
				gameState.AlwaysIgnoreTraders = checkBox.isChecked();
				break;
			case R.id.chkBoxIgnoreTradeOffers:
				gameState.AlwaysIgnoreTradeInOrbit = checkBox.isChecked();
				break;
			case R.id.chkBoxAutoFuel:
				gameState.AutoFuel = checkBox.isChecked();
				break;
			case R.id.chkBoxAutoRepair:
				gameState.AutoRepair = checkBox.isChecked();
				break;
			case R.id.chkBoxAlwaysInfo:
				gameState.AlwaysInfo = checkBox.isChecked();
				break;
			case R.id.chkBoxReserveMoney:
				gameState.ReserveMoney = checkBox.isChecked();
				break;
			case R.id.chkBoxContinuous:
				gameState.Continuous = checkBox.isChecked();
				break;
			case R.id.chkBoxAttackFleeing:
				gameState.AttackFleeing = checkBox.isChecked();
				break;
			case R.id.chkBoxAutoPayNewspaper:
				gameState.NewsAutoPay = checkBox.isChecked();
				break;
			case R.id.chkBoxDebtReminder:
				gameState.RemindLoans = checkBox.isChecked();
				break;
			case R.id.chkBoxSaveOnArrival:
				gameState.SaveOnArrival = checkBox.isChecked();
				break;
			case R.id.chkBoxBetterGfx:
				gameState.BetterGfx = checkBox.isChecked();
				break;
		}
	}

	public void btnChangeTheme(View view) {
		SharedPreferences sp = getSharedPreferences("spacetrader", MODE_PRIVATE);
		SharedPreferences.Editor ed = sp.edit();
		String theme = sp.getString("Theme", "Light");
		if (view.getId() == R.id.btnDarkTheme) {
			if ("Dark".equals(theme)) {
				Toast.makeText(this, "This theme is already selected.", Toast.LENGTH_SHORT).show();
				return;
			}
			ed.putString("Theme", "Dark");
		} else {
			if ("Light".equals(theme)) {
				Toast.makeText(this, "This theme is already selected.", Toast.LENGTH_SHORT).show();
				return;
			}
			ed.putString("Theme", "Light");
		}
		ed.commit();
		Popup popup = new Popup(this, "Change Theme",
			"Space Trader must be restarted to change the theme. Do you want to do that now?", "",
			"Restart now", "Restart later", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				saveGame();
				Intent mStartActivity = new Intent(getApplicationContext(), Main.class);
				int mPendingIntentId = Math.abs(gameState.rand.nextInt());
				//noinspection ConstantConditions
				PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(),
					mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
				AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(
					Context.ALARM_SERVICE);
				mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
				System.exit(0);
			}
		}, cbShowNextPopup
		);
		addPopup(popup);
		showNextPopup();
	}

	// FragmentPlunder
	public void PlunderCargo(int Index, int Amount) {
		// *************************************************************************
		// Plunder amount of cargo
		// *************************************************************************
		int ToPlunder;
		Popup popup;

		if (gameState.Opponent.cargo[Index] <= 0) {
			popup = new Popup(this, "Victim hasn't got any", "Your victim hasn't got any of these goods.",
				"You can only steal what your victim actually has.", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.Ship.TotalCargoBays() - gameState.Ship.FilledCargoBays() <= 0) {
			popup = new Popup(this, "Cargo Bays Full",
				"You have no empty cargo bays. Dump some cargo or leave the victims cargo in his bays.", "",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		ToPlunder = Math.min(Amount, gameState.Opponent.cargo[Index]);
		ToPlunder = Math.min(ToPlunder,
			gameState.Ship.TotalCargoBays() - gameState.Ship.FilledCargoBays());

		gameState.Ship.cargo[Index] += ToPlunder;
		gameState.Opponent.cargo[Index] -= ToPlunder;

		if (gameState.EncounterType == GameState.MARIECELESTEENCOUNTER && Index == GameState.NARCOTICS && Amount > 0) {
			gameState.JustLootedMarie = true;
		}
		changeFragment(FRAGMENTS.PLUNDER);
	}

	public void btnPlunderAllCargoQty(View view) {
		int Index = -1;

		switch (view.getId()) {
			case R.id.btnPlunderCargoAll10:
				Index++;
			case R.id.btnPlunderCargoAll9:
				Index++;
			case R.id.btnPlunderCargoAll8:
				Index++;
			case R.id.btnPlunderCargoAll7:
				Index++;
			case R.id.btnPlunderCargoAll6:
				Index++;
			case R.id.btnPlunderCargoAll5:
				Index++;
			case R.id.btnPlunderCargoAll4:
				Index++;
			case R.id.btnPlunderCargoAll3:
				Index++;
			case R.id.btnPlunderCargoAll2:
				Index++;
			case R.id.btnPlunderCargoAll1:
				Index++;
				break;
		}
		if (gameState.Opponent.cargo[Index] <= 0) {
			Popup popup;
			popup = new Popup(this, "Victim hasn't got any", "Your victim hasn't got any of these goods.",
				"You can only steal what your victim actually has.", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			PlunderCargo(Index, 999);
		}
	}

	public void btnPlunderCargoQty(View view) {
		int Index = -1;

		switch (view.getId()) {
			case R.id.btnPlunderCargo10:
				Index++;
			case R.id.btnPlunderCargo9:
				Index++;
			case R.id.btnPlunderCargo8:
				Index++;
			case R.id.btnPlunderCargo7:
				Index++;
			case R.id.btnPlunderCargo6:
				Index++;
			case R.id.btnPlunderCargo5:
				Index++;
			case R.id.btnPlunderCargo4:
				Index++;
			case R.id.btnPlunderCargo3:
				Index++;
			case R.id.btnPlunderCargo2:
				Index++;
			case R.id.btnPlunderCargo1:
				Index++;
				break;
		}
		if (gameState.Opponent.cargo[Index] <= 0) {
			Popup popup;
			popup = new Popup(this, "Victim hasn't got any", "Your victim hasn't got any of these goods.",
				"You can only steal what your victim actually has.", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			final int idx = Index;
			Popup popup;
			popup = new Popup(this, "Plunder", String.format(
				"Stealing %s.\nYour victim has %d of these goods. How many do you want to steal?",
				Tradeitems.mTradeitems[idx].name, gameState.Opponent.cargo[idx]), "Amount", "",
				gameState.Opponent.cargo[idx], "Steal", "Don't steal", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					SeekBar seekBar = (SeekBar) view;
					int Amount = seekBar.getProgress();
					if (Amount > 0) {
						PlunderCargo(idx, Amount);
					}
				}
			}, cbShowNextPopup, new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					PlunderCargo(idx, popup.max);
				}
			}
			);
			popupQueue.push(popup);
			showNextPopup();
		}
	}

	@SuppressWarnings("UnusedParameters")
	public void btnDumpForm(View view) {
		changeFragment(FRAGMENTS.DUMP);
	}

	@SuppressWarnings("UnusedParameters")
	public void btnPlunderDone(View view) {
		Travel();
	}

	// FragmentDump
	public void btnDumpAllCargoQty(View view) {
		int Index = -1;

		switch (view.getId()) {
			case R.id.btnDumpCargoAll10:
				Index++;
			case R.id.btnDumpCargoAll9:
				Index++;
			case R.id.btnDumpCargoAll8:
				Index++;
			case R.id.btnDumpCargoAll7:
				Index++;
			case R.id.btnDumpCargoAll6:
				Index++;
			case R.id.btnDumpCargoAll5:
				Index++;
			case R.id.btnDumpCargoAll4:
				Index++;
			case R.id.btnDumpCargoAll3:
				Index++;
			case R.id.btnDumpCargoAll2:
				Index++;
			case R.id.btnDumpCargoAll1:
				Index++;
				break;
		}
		if (gameState.Ship.cargo[Index] <= 0) {
			Popup popup;
			popup = new Popup(this, "None to dump", "You have none of these goods.",
				"On the Dump Cargo screen, the leftmost button shows the number of cargo bays you have which contain these goods. If that amount is zero, you can't dump any.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			SellCargo(Index, 999, GameState.JETTISONCARGO);
		}
		changeFragment(gameState.currentState);
	}

	public void btnDumpCargoQty(View view) {
		int Index = -1;
		Popup popup;

		switch (view.getId()) {
			case R.id.btnDumpCargo10:
				Index++;
			case R.id.btnDumpCargo9:
				Index++;
			case R.id.btnDumpCargo8:
				Index++;
			case R.id.btnDumpCargo7:
				Index++;
			case R.id.btnDumpCargo6:
				Index++;
			case R.id.btnDumpCargo5:
				Index++;
			case R.id.btnDumpCargo4:
				Index++;
			case R.id.btnDumpCargo3:
				Index++;
			case R.id.btnDumpCargo2:
				Index++;
			case R.id.btnDumpCargo1:
				Index++;
				break;
		}
		if (gameState.Ship.cargo[Index] <= 0) {
			popup = new Popup(this, "None to dump", "You have none of these goods.",
				"On the Dump Cargo screen, the leftmost button shows the number of cargo bays you have which contain these goods. If that amount is zero, you can't dump any.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			final int idx = Index;
			popup = new Popup(this, "Discard Cargo", String.format(
				"Discarding %s.\nYou can jettison up to %d units. You paid about %d cr. per unit. It costs nothing to jettison cargo. How many to you want to dump?",
				Tradeitems.mTradeitems[idx].name, gameState.Ship.cargo[idx],
				gameState.BuyingPrice[idx] / gameState.Ship.cargo[idx]), "Amount", "",
				gameState.Ship.cargo[idx], "Discard", "Keep", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					SeekBar seekBar = (SeekBar) view;
					int Amount = seekBar.getProgress();
					if (Amount > 0) {
						SellCargo(idx, Amount, GameState.JETTISONCARGO);
						changeFragment(gameState.currentState);
					}
				}
			}, cbShowNextPopup, new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					SellCargo(idx, popup.max, GameState.JETTISONCARGO);
					changeFragment(gameState.currentState);
				}
			}
			);
			popupQueue.push(popup);
			showNextPopup();
		}
	}

	@SuppressWarnings("UnusedParameters")
	public void btnDumpDone(View view) {
		changeFragment(FRAGMENTS.PLUNDER);
	}

	// FragmentGalacticChart
	@SuppressWarnings("UnusedParameters")
	public void btnGalacticChartFind(View view) {
		Popup popup;
		popup = new Popup(this, "Find System", "Please enter the system name to find:", "System", "",
			"Find", "Cancel", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				EditText editText = (EditText) view;
				//noinspection ConstantConditions
				String buf = editText.getText().toString();
				if (buf.length() < 2) {
					return;
				}
				if (buf.equals("Cheetah")) {
					if (++gameState.CheatCounter < 3) {
						Popup popup1 = new Popup(popup.context, "Cheetah!", String.format("Strike %d!",
							gameState.CheatCounter), "", "OK", cbShowNextPopup);
						popupQueue.push(popup1);
						showNextPopup();
					}

					if (gameState.CheatCounter == 3) {
						Popup popup1 = new Popup(popup.context, "Cheat mode enabled",
							"Cheat mode has been enabled. You will NOT be added to the highscore list!",
							"Winners never cheat. Cheaters never win.", "OK", cbShowNextPopup);
						popupQueue.push(popup1);
						showNextPopup();
					}
					if (gameState.CheatCounter > 3) {
						gameState.CheatCounter = 3;
					}

				}
				if (gameState.CheatCounter >= 3) {
					if (buf.equals("Moolah")) {
						gameState.Credits += 100000;
						return;
					} else if (buf.startsWith("Go ") && buf.length() > 3) {
						int i = 0;
						while (i < GameState.MAXSOLARSYSTEM) {
							if (SolarSystemName[i].equals(buf.substring(3))) {
								break;
							}
							++i;
						}
						if (i < GameState.MAXSOLARSYSTEM) {
							gameState.Mercenary[0].curSystem = i;
							gameState.RecalculateBuyPrices(i);
							changeFragment(FRAGMENTS.GALACTIC_CHART);
							return;
						}
					} else if (buf.equals("Quests")) {
						String questbuf = "";

						for (int i = 0; i < GameState.MAXSOLARSYSTEM; ++i) {
							SolarSystem s = gameState.SolarSystem[i];
							switch (s.special) {
								case GameState.DRAGONFLY:
									questbuf += String.format("Dragonfly: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.SPACEMONSTER:
									questbuf += String.format("Spacemonster: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.JAPORIDISEASE:
									questbuf += String.format("Disease: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.ALIENARTIFACT:
									questbuf += String.format("Artifact: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.ARTIFACTDELIVERY:
									if (gameState.ArtifactOnBoard) {
										questbuf += String.format("Berger: %s\n", SolarSystemName[s.nameIndex]);
									}
									break;
								case GameState.TRIBBLE:
									questbuf += String.format("Tribbles: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.GETREACTOR:
									questbuf += String.format("Get reactor: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.AMBASSADORJAREK:
									questbuf += String.format("Jarek: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.ALIENINVASION:
									questbuf += String.format("Invasion: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.EXPERIMENT:
									questbuf += String.format("Experiment: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.TRANSPORTWILD:
									questbuf += String.format("Wild: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.SCARAB:
									questbuf += String.format("Scarab: %s\n", SolarSystemName[s.nameIndex]);
									break;
								case GameState.SCARABDESTROYED:
									if (gameState.ScarabStatus > 0 && gameState.ScarabStatus < 2) {
										questbuf += String.format("Scarab: %s\n", SolarSystemName[s.nameIndex]);
									}
									break;
							}
						}
						Popup popup1 = new Popup(popup.context, "Quests", questbuf, "", "OK", cbShowNextPopup);
						popupQueue.push(popup1);
						showNextPopup();
						return;
					} else if (buf.equals("Very rare")) {
						changeFragment(FRAGMENTS.VERY_RARE_CHEAT);
						return;
					}
				}
				int i = 0;
				while (i < GameState.MAXSOLARSYSTEM) {
					if (buf.equalsIgnoreCase(SolarSystemName[i])) {
						break;
					}
					++i;
				}
				if (i >= GameState.MAXSOLARSYSTEM) {
					i = gameState.Mercenary[0].curSystem;
				}
				gameState.WarpSystem = i;
				WarpSystem = gameState.SolarSystem[i];
				changeFragment(FRAGMENTS.GALACTIC_CHART);
			}
		}, cbShowNextPopup
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void SuperWarpButtonCallback(View view) {
		Popup popup;
		if (gameState.TrackedSystem < 0) {
			popup = new Popup(this, "No System Selected",
				"To use the Portable Singularity, track a system before clicking on this button. (You can't use the Singularity to enter a Wormhole).",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else if (gameState.TrackedSystem == gameState.Mercenary[0].curSystem) {
			popup = new Popup(this, "Cannot Jump",
				"You are tracking the system where you are currently located. It's useless to jump to your current location.",
				"Track another system than the one where you are currently are located, then tap the Singularity button to jump.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			popup = new Popup(this, "Use Singularity?",
				"Do you wish to use the Portable Singularity to transport immediately to " + SolarSystemName[gameState.SolarSystem[gameState.TrackedSystem].nameIndex] + "?",
				"", "Jump!", "Stay", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					gameState.WarpSystem = gameState.TrackedSystem;
					WarpSystem = gameState.SolarSystem[gameState.TrackedSystem];
					gameState.CanSuperWarp = false;
					DoWarp(true);
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		}
	}

	// FragmentVeryRareCheats
	public void btnVeryRareCheckboxCallback(View view) {
		CheckBox checkBox = (CheckBox) view;
		switch (checkBox.getId()) {
			case R.id.chkBoxCheatAhab:
				if (checkBox.isChecked()) {
					gameState.VeryRareEncounter |= GameState.ALREADYAHAB;
				} else {
					gameState.VeryRareEncounter ^= GameState.ALREADYAHAB;
				}
				break;
			case R.id.chkBoxCheatHuie:
				if (checkBox.isChecked()) {
					gameState.VeryRareEncounter |= GameState.ALREADYHUIE;
				} else {
					gameState.VeryRareEncounter ^= GameState.ALREADYHUIE;
				}
				break;
			case R.id.chkBoxCheatConrad:
				if (checkBox.isChecked()) {
					gameState.VeryRareEncounter |= GameState.ALREADYCONRAD;
				} else {
					gameState.VeryRareEncounter ^= GameState.ALREADYCONRAD;
				}
				break;
			case R.id.chkBoxCheatGoodTonic:
				if (checkBox.isChecked()) {
					gameState.VeryRareEncounter |= GameState.ALREADYBOTTLEGOOD;
				} else {
					gameState.VeryRareEncounter ^= GameState.ALREADYBOTTLEGOOD;
				}
				break;
			case R.id.chkBoxCheatBadTonic:
				if (checkBox.isChecked()) {
					gameState.VeryRareEncounter |= GameState.ALREADYBOTTLEOLD;
				} else {
					gameState.VeryRareEncounter ^= GameState.ALREADYBOTTLEOLD;
				}
				break;
			case R.id.chkBoxCheatMarieCeleste:
				if (checkBox.isChecked()) {
					gameState.VeryRareEncounter |= GameState.ALREADYMARIE;
				} else {
					gameState.VeryRareEncounter ^= GameState.ALREADYMARIE;
				}
				break;
		}
	}

	// FragmentPersonnelRoster
	@SuppressWarnings("UnusedParameters")
	public void btnPersonnelRosterHireCallback(View view) {
		int ForHire = gameState.GetForHire();
		int FirstFree = -1;
		int oldtraderskill;
		Ship Ship = gameState.Ship;

		oldtraderskill = Ship.TraderSkill();
		if (Ship.crew[1] == -1) {
			FirstFree = 1;
		} else if (Ship.crew[2] == -1) {
			FirstFree = 2;
		}

		if ((FirstFree < 0) || (gameState.AvailableQuarters() <= FirstFree)) {
			Popup popup;
			popup = new Popup(this, "No Free Quarters",
				"There are currently no free crew quarters on your ship.",
				"If you hire someone, you must give him or her quarters on your ship. Depending on the type of ship, you can hire zero, one or two mercenaries.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		} else {
			Ship.crew[FirstFree] = ForHire;
		}
		changeFragment(FRAGMENTS.PERSONNEL_ROSTER);
		if (oldtraderskill != Ship.TraderSkill()) {
			gameState.RecalculateBuyPrices(gameState.Mercenary[0].curSystem);
		}
	}

	public void btnPersonnelRosterFireCallback(View view) {
		int i;

		switch (view.getId()) {
			case R.id.btnFireCrew1:
				i = 1;
				break;
			case R.id.btnFireCrew2:
				i = 2;
				break;
			default:
				return;
		}

		if (gameState.WildStatus == 1) {
			i--;
		}
		if (gameState.JarekStatus == 1) {
			i--;
		}

		final int j = i;
		Popup popup;
		popup = new Popup(this, "Fire Mercenary", "Are you sure you wish to fire this mercenary?",
			"If you fire a mercenary, he or she returns to his or her home system", "Yes", "No",
			new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					Ship Ship = gameState.Ship;
					int oldtraderskill;
					oldtraderskill = Ship.TraderSkill();
					if (j == 1) {
						Ship.crew[1] = Ship.crew[2];
					}
					Ship.crew[2] = -1;
					changeFragment(FRAGMENTS.PERSONNEL_ROSTER);
					if (oldtraderskill != Ship.TraderSkill()) {
						gameState.RecalculateBuyPrices(gameState.Mercenary[0].curSystem);
					}
				}
			}, cbShowNextPopup
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	// FragmentEncounter
	public void DoWarp(boolean viaSingularity) {
		int i, Distance;
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		Popup popup;

		// if Wild is aboard, make sure ship is armed!
		if (gameState.WildStatus == 1) {
			if (!gameState.Ship.HasWeapon(GameState.BEAMLASERWEAPON, false)) {
				popup = new Popup(this, "Wild Won't Stay Aboard",
					"Jonathan Wild isn't willing to go with you if you are not armed with at least a Beam Laser.",
					"", "Stay here", "Goodbye Wild", cbShowNextPopup, new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						gameState.WildStatus = 0;
						Popup popup1;
						popup1 = new Popup(popup.context, "Say Goodbye to Wild",
							"Since Jonathan Wild is not willing to travel under these conditions, and you're not willing to change the situation, he leaves you and goes into hiding on this system.",
							"", "OK", cbShowNextPopup);
						popupQueue.push(popup1);
						showNextPopup();
					}
				}
				);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
		}

		// Check for Large Debt
		if (gameState.Debt > GameState.DEBTTOOLARGE) {
			popup = new Popup(this, "Large Debt",
				"Your debt is too large.  You are not allowed to leave this system until your debt is lowered.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		// Check for enough money to pay Mercenaries
		if (gameState.MercenaryMoney() > gameState.Credits) {
			popup = new Popup(this, "Pay Mercenaries",
				"You don't have enough cash to pay your mercenaries to come with you on this trip. Fire them or make sure you have enough cash.",
				"You must pay your mercenaries daily, that is, before you warp to another system. If you don't have the cash, you must either sell something so you have enough cash, or fire the mercenaries you can't pay. Until then, warping is out of the question.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		// Check for enough money to pay Insurance
		if (gameState.Insurance) {
			if (gameState.InsuranceMoney() + gameState.MercenaryMoney() > gameState.Credits) {
				popup = new Popup(this, "Not Enough Money",
					"You don't have enough cash to pay for your insurance.",
					"You can't leave if you haven't paid your insurance. If you have no way to pay, you should stop your insurance at the bank.",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
		}

		// Check for enough money to pay Wormhole Tax
		if (gameState.InsuranceMoney() + gameState.MercenaryMoney() + gameState.WormholeTax(
			COMMANDER.curSystem, WarpSystem) > gameState.Credits) {
			popup = new Popup(this, "Wormhole Tax",
				"You don't have enough money to pay for the wormhole tax.",
				"Wormhole tax must be paid when you want to warp through a wormhole. It depends on the type of your ship.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (!viaSingularity) {
			gameState.Credits -= gameState.WormholeTax(COMMANDER.curSystem, WarpSystem);
			gameState.Credits -= gameState.MercenaryMoney();
			gameState.Credits -= gameState.InsuranceMoney();
		}

		for (i = 0; i < GameState.MAXSHIELD; ++i) {
			if (gameState.Ship.shield[i] < 0) {
				break;
			}
			gameState.Ship.shieldStrength[i] = Shields.mShields[gameState.Ship.shield[i]].power;
		}

		CURSYSTEM.countDown = GameState.CountDown;
		if (gameState.WormholeExists(COMMANDER.curSystem, WarpSystem) || viaSingularity) {
			gameState.ArrivedViaWormhole = true;
		} else {
			Distance = gameState.RealDistance(CURSYSTEM, WarpSystem);
			gameState.Ship.fuel -= Math.min(Distance, gameState.Ship.GetFuel());
			gameState.ArrivedViaWormhole = false;
		}

		gameState.resetNewsEvents();
		if (!viaSingularity) {
			// normal warp.
			gameState.PayInterest();
			IncDays(1);
			if (gameState.Insurance) {
				++gameState.NoClaim;
			}
		} else {
			// add the singularity news story
			gameState.addNewsEvent(GameState.ARRIVALVIASINGULARITY);
		}
		gameState.Clicks = 21;
		gameState.Raided = false;
		gameState.Inspected = false;
		gameState.LitterWarning = false;
		gameState.MonsterHull = (gameState.MonsterHull * 105) / 100;
		if (gameState.MonsterHull > gameState.SpaceMonster.getType().hullStrength) {
			gameState.MonsterHull = gameState.SpaceMonster.getType().hullStrength;
		}
		if (gameState.Days % 3 == 0) {
			if (gameState.PoliceRecordScore > GameState.CLEANSCORE) {
				--gameState.PoliceRecordScore;
			}
		}
		if (gameState.PoliceRecordScore < GameState.DUBIOUSSCORE) {
			if (GameState.getDifficulty() <= GameState.NORMAL) {
				++gameState.PoliceRecordScore;
			} else if (gameState.Days % GameState.getDifficulty() == 0) {
				++gameState.PoliceRecordScore;
			}
		}

		gameState.PossibleToGoThroughRip = true;

		gameState.DeterminePrices(gameState.WarpSystem);
		Travel();
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonTradeCallback(View view) {
		final int i;

		if (gameState.EncounterType == GameState.TRADERBUY) {
			i = gameState.Ship.getRandomTradeableItem(GameState.TRADERBUY);

			if (i == GameState.NARCOTICS || i == GameState.FIREARMS) {
				if (gameState.GetRandom(100) <= 45) {
					gameState.SellPrice[i] *= 0.8;
				} else {
					gameState.SellPrice[i] *= 1.1;
				}
			} else {
				if (gameState.GetRandom(100) <= 10) {
					gameState.SellPrice[i] *= 0.9;
				} else {
					gameState.SellPrice[i] *= 1.1;
				}
			}

			gameState.SellPrice[i] /= Tradeitems.mTradeitems[i].roundOff;
			++gameState.SellPrice[i];
			gameState.SellPrice[i] *= Tradeitems.mTradeitems[i].roundOff;
			if (gameState.SellPrice[i] < Tradeitems.mTradeitems[i].minTradePrice) {
				gameState.SellPrice[i] = Tradeitems.mTradeitems[i].minTradePrice;
			}
			if (gameState.SellPrice[i] > Tradeitems.mTradeitems[i].maxTradePrice) {
				gameState.SellPrice[i] = Tradeitems.mTradeitems[i].maxTradePrice;
			}

			String buf = String.format(
				"The trader wants to buy %s, and offers %d cr. each.\nYou have %d units available and paid about %d cr. per unit.\nHow many do you wish to sell?",
				Tradeitems.mTradeitems[i].name, gameState.SellPrice[i], gameState.Ship.cargo[i],
				gameState.BuyingPrice[i] / gameState.Ship.cargo[i]);

			Popup popup;
			popup = new Popup(this, "Trade offer", buf, "Amount", "", gameState.Ship.cargo[i], "Trade",
				"Don't trade", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					int Amount;
					SeekBar seekBar = (SeekBar) view;
					Amount = seekBar.getProgress();
					Amount = Math.max(0, Math.min(gameState.Ship.cargo[i], Amount));
					Amount = Math.min(Amount, gameState.Opponent.getType().cargoBays);
					if (Amount > 0) {
						gameState.BuyingPrice[i] =
							gameState.BuyingPrice[i] * (gameState.Ship.cargo[i] - Amount) / gameState.Ship.cargo[i];
						gameState.Ship.cargo[i] -= Amount;
						gameState.Opponent.cargo[i] = Amount;
						gameState.Credits += Amount * gameState.SellPrice[i];
						Popup popup1;
						popup1 = new Popup(popup.context, "Trade Completed", String.format(
							"%s %s. It's been a pleasure doing business with you.", "Thanks for selling us the",
							Tradeitems.mTradeitems[i].name), "", "OK", cbShowNextPopup);
						popupQueue.push(popup1);
						showNextPopup();
					}
					Travel();
				}
			}, cbShowNextPopup, new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					int Amount = popup.max;
					gameState.BuyingPrice[i] =
						gameState.BuyingPrice[i] * (gameState.Ship.cargo[i] - Amount) / gameState.Ship.cargo[i];
					gameState.Ship.cargo[i] -= Amount;
					gameState.Opponent.cargo[i] = Amount;
					gameState.Credits += Amount * gameState.SellPrice[i];
					Popup popup1;
					popup1 = new Popup(popup.context, "Trade Completed", String.format(
						"%s %s. It's been a pleasure doing business with you.", "Thanks for selling us the",
						Tradeitems.mTradeitems[i].name), "", "OK", cbShowNextPopup);
					popupQueue.push(popup1);
					showNextPopup();
					Travel();
				}
			}
			);
			popupQueue.push(popup);
			showNextPopup();
		} else if (gameState.EncounterType == GameState.TRADERSELL) {
			i = gameState.Opponent.getRandomTradeableItem(GameState.TRADERSELL);
			if (i == GameState.NARCOTICS || i == GameState.FIREARMS) {
				if (gameState.GetRandom(100) <= 45) {
					gameState.BuyPrice[i] *= 1.1;
				} else {
					gameState.BuyPrice[i] *= 0.8;
				}
			} else {
				if (gameState.GetRandom(100) <= 10) {
					gameState.BuyPrice[i] *= 1.1;
				} else {
					gameState.BuyPrice[i] *= 0.9;
				}
			}

			gameState.BuyPrice[i] /= Tradeitems.mTradeitems[i].roundOff;
			gameState.BuyPrice[i] *= Tradeitems.mTradeitems[i].roundOff;
			if (gameState.BuyPrice[i] < Tradeitems.mTradeitems[i].minTradePrice) {
				gameState.BuyPrice[i] = Tradeitems.mTradeitems[i].minTradePrice;
			}
			if (gameState.BuyPrice[i] > Tradeitems.mTradeitems[i].maxTradePrice) {
				gameState.BuyPrice[i] = Tradeitems.mTradeitems[i].maxTradePrice;
			}

			if (gameState.Opponent.cargo[i] == 0) { // cop-out, yeah. Make it a TODO
				gameState.Opponent.cargo[i] = 1 + (gameState.GetRandom(10));
				gameState.BuyPrice[i] = 1;
			}

			String buf = String.format(
				"The trader wants to sell %s for the price of %d cr. each.\n The trader has %d units for sale. You can afford %d units.\nHow many do you wish to buy?",
				Tradeitems.mTradeitems[i].name, gameState.BuyPrice[i], gameState.Opponent.cargo[i],
				gameState.Credits / gameState.BuyPrice[i]);

			Popup popup;
			popup = new Popup(this, "Trade Offer", buf, "Amount", "", gameState.Opponent.cargo[i],
				"Trade", "Don't trade", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					SeekBar seekBar = (SeekBar) view;
					int Amount;
					Amount = seekBar.getProgress();
					Amount = Math.max(0, Math.min(gameState.Opponent.cargo[i], Amount));
					Amount = Math.min(Amount, (gameState.Credits / gameState.BuyPrice[i]));
					if (Amount > 0) {
						gameState.Ship.cargo[i] += Amount;
						gameState.Opponent.cargo[i] -= Amount;
						gameState.BuyingPrice[i] += (Amount * gameState.BuyPrice[i]);
						gameState.Credits -= (Amount * gameState.BuyPrice[i]);

						Popup popup1;
						popup1 = new Popup(popup.context, "Trade Completed", String.format(
							"%s %s. It's been a pleasure doing business with you.", "Thanks for buying the",
							Tradeitems.mTradeitems[i].name), "", "OK", cbShowNextPopup);
						popupQueue.push(popup1);
						showNextPopup();
					}
					Travel();
				}
			}, cbShowNextPopup, new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					int Amount = popup.max;
                    Amount = Math.max(0, Math.min(gameState.Opponent.cargo[i], Amount));
                    Amount = Math.min(Amount, (gameState.Credits / gameState.BuyPrice[i]));
					if (Amount > 0) {
						gameState.Ship.cargo[i] += Amount;
						gameState.Opponent.cargo[i] -= Amount;
						gameState.BuyingPrice[i] += (Amount * gameState.BuyPrice[i]);
						gameState.Credits -= (Amount * gameState.BuyPrice[i]);

						Popup popup1;
						popup1 = new Popup(popup.context, "Trade Completed", String.format(
							"%s %s. It's been a pleasure doing business with you.", "Thanks for buying the",
							Tradeitems.mTradeitems[i].name), "", "OK", cbShowNextPopup);
						popupQueue.push(popup1);
						showNextPopup();
					}
					Travel();
				}
			}
			);
			popupQueue.push(popup);
			showNextPopup();
		}
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonYieldCallback(View view) {
		String buf = "";
		Popup popup;

		if (gameState.WildStatus == 1) {
			buf = String.format(
				"%sIf you surrender, you will spend some time in prison and will have to pay a hefty fine.\n%sAre you sure you want to do that?",
				"You have Jonathan Wild on board!\n", "Wild will be arrested, too.\n");
		} else if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			buf = String.format(
				"%sIf you surrender, you will spend some time in prison and will have to pay a hefty fine.\n%sAre you sure you want to do that?",
				"You have an illegal Reactor on board!\n ", "They will destroy the reactor.\n");
		}

		if (gameState.WildStatus == 1 || (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21)) {
			popup = new Popup(this, "Surrender", buf, "", "Yes", "No", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					Arrested();
					Travel();
				}
			}, cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			// Police Record becomes dubious, if it wasn't already.
			if (gameState.PoliceRecordScore > GameState.DUBIOUSSCORE) {
				gameState.PoliceRecordScore = GameState.DUBIOUSSCORE;
			}
			gameState.Ship.cargo[GameState.NARCOTICS] = 0;
			gameState.Ship.cargo[GameState.FIREARMS] = 0;

			popup = new Popup(this, "Contraband Removed",
				"The Customs Police confiscated all of your illegal cargo, but since you were cooperative, you avoided stronger fines or penalties.",
				"The Customs Police took all the illegal goods from your ship, and sent you on your way.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			Travel();
		}
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonBoardCallback(View view) {
		if (gameState.EncounterType == GameState.MARIECELESTEENCOUNTER) {
			// take the cargo of the Marie Celeste?
			Popup popup;
			popup = new Popup(this, "Board Marie Celeste",
				"The ship is empty: there is nothing in the ship's log, but the crew has vanished, leaving food on the tables and cargo in the holds. Do you wish to offload the cargo to your own holds?",
				"The Marie Celeste is completely abandoned, and drifting through space. The ship's log is unremarkable except for a Tribble infestation a few months ago, and the note that the last system visited was Lowry.\nThe crew's quarters are in good shape, with no signs of struggle. There is still food sitting on the table and beer in the mugs in the mess hall. Except for the fact that it's abandoned, the ship is normal in every way.\nBy Intergalactic Salvage Law, you have the right to claim the cargo as your own if you decide to.",
				"Yes", "No", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					changeFragment(FRAGMENTS.PLUNDER);
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		}
		// Travel(); // is called from Done button in PlunderForm
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonPlunderCallback(View view) {
		gameState.AutoAttack = false;
		gameState.AutoFlee = false;

		if (gameState.ENCOUNTERTRADER(gameState.EncounterType)) {
			gameState.PoliceRecordScore += GameState.PLUNDERTRADERSCORE;
		} else {
			gameState.PoliceRecordScore += GameState.PLUNDERPIRATESCORE;
		}
		changeFragment(FRAGMENTS.PLUNDER);
		// Travel(); // is called from Done button in PlunderForm
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonMeetCallback(View view) {
		Popup popup;
		if (gameState.EncounterType == GameState.CAPTAINAHABENCOUNTER) {
			// Trade a reflective shield for skill points in piloting?
			popup = new Popup(this, "Meet Captain Ahab",
				"Captain Ahab is in need of a spare shield for an upcoming mission. He offers to trade you some piloting lessons for your reflective shield. Do you wish to trade?",
				"Captain Ahab is in need of a spare shield for an upcoming mission. Since he's in a rush, he'd rather not stop to get one on-planet.\nThe deal he's offering is a trade, rather than cash, for the shield. He'll trade you some piloting lessons in exchange for your reflective shield (he only needs one, so if you have more than one, you'll keep the others.\nCaptain Ahab is one of the greatest pilots of all time, and still holds the speed record for cross-galaxy transport.",
				"Trade", "Keep", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					// remove the last reflective shield
					int i = GameState.MAXSHIELD - 1;
					while (i >= 0) {
						if (gameState.Ship.shield[i] == GameState.REFLECTIVESHIELD) {
							for (int m = i + 1; m < GameState.MAXSHIELD; ++m) {
								gameState.Ship.shield[m - 1] = gameState.Ship.shield[m];
								gameState.Ship.shieldStrength[m - 1] = gameState.Ship.shieldStrength[m];
							}
							gameState.Ship.shield[GameState.MAXSHIELD - 1] = -1;
							gameState.Ship.shieldStrength[GameState.MAXSHIELD - 1] = 0;
							i = -1;
						}
						i--;
					}
					// add points to piloting skill
					// two points if you're on beginner-normal, one otherwise
					if (GameState.getDifficulty() < GameState.HARD) {
						gameState.Mercenary[0].pilot += 2;
					} else {
						gameState.Mercenary[0].pilot += 1;
					}

					if (gameState.Mercenary[0].pilot > GameState.MAXSKILL) {
						gameState.Mercenary[0].pilot = GameState.MAXSKILL;
					}
					Popup popup1;
					popup1 = new Popup(popup.context, "Training completed",
						"After a few hours of training with a top expert, you feel your abilities have improved significantly.",
						"Under the watchful eye of the Captain, you demonstrate your abilities. The Captain provides some helpful pointers and tips, and teaches you a few new techniques. The few hours pass quickly, but you feel you've gained a lot from the experience.",
						"OK", cbShowNextPopup);
					popupQueue.push(popup1);
					showNextPopup();
					Travel();
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		} else if (gameState.EncounterType == GameState.CAPTAINCONRADENCOUNTER) {
			// Trade a military laser for skill points in engineering?
			popup = new Popup(this, "Meet Captain Conrad",
				"Captain Conrad is in need of a military laser. She offers to trade you some engineering training for your military laser. Do you wish to trade?",
				"Captain Conrad is in need of a military laser to test a new shield design she's been working on. Unfortunately, she's used up her R&D budget for the year.\nThe deal she's offering is a trade, rather than cash, for the laser. She'll trade you some engineering lessons in exchange for your military laser (she only needs one, so if you have more than one, you'll keep the others.",
				"Trade", "Keep", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					// remove the last military laser
					int i = GameState.MAXWEAPON - 1;
					while (i >= 0) {
						if (gameState.Ship.weapon[i] == GameState.MILITARYLASERWEAPON) {
							//noinspection ManualArrayCopy
							for (int m = i + 1; m < GameState.MAXWEAPON; ++m) {
								gameState.Ship.weapon[m - 1] = gameState.Ship.weapon[m];
							}
							gameState.Ship.weapon[GameState.MAXWEAPON - 1] = -1;
							i = -1;
						}
						i--;
					}
					// add points to engineering skill
					// two points if you're on beginner-normal, one otherwise
					if (GameState.getDifficulty() < GameState.HARD) {
						gameState.Mercenary[0].engineer += 2;
					} else {
						gameState.Mercenary[0].engineer += 1;
					}

					if (gameState.Mercenary[0].engineer > GameState.MAXSKILL) {
						gameState.Mercenary[0].engineer = GameState.MAXSKILL;
					}
					Popup popup1;
					popup1 = new Popup(popup.context, "Training completed",
						"After a few hours of training with a top expert, you feel your abilities have improved significantly.",
						"Under the watchful eye of the Captain, you demonstrate your abilities. The Captain provides some helpful pointers and tips, and teaches you a few new techniques. The few hours pass quickly, but you feel you've gained a lot from the experience.",
						"OK", cbShowNextPopup);
					popupQueue.push(popup1);
					showNextPopup();
					Travel();
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		} else if (gameState.EncounterType == GameState.CAPTAINHUIEENCOUNTER) {
			// Trade a military laser for skill points in trading?
			popup = new Popup(this, "Meet Captain Huie",
				"Captain Huie is in need of a military laser. She offers to exchange some bargaining training for your military laser. Do you wish to trade?",
				"Captain Huie is in need of a military laser for an upcoming mission, but would rather hold onto her cash to buy her cargo.\nThe deal she's offering is a trade, rather than cash, for the laser. She'll give you some secrets of doing business in exchange for your military laser.\nCaptain Huie is known far and wide for driving a hard bargain; she was Trade Commissioner of the Galactic Council for over twenty years.",
				"Trade", "Keep", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					// remove the last military laser
					int i = GameState.MAXWEAPON - 1;
					while (i >= 0) {
						if (gameState.Ship.weapon[i] == GameState.MILITARYLASERWEAPON) {
							//noinspection ManualArrayCopy
							for (int m = i + 1; m < GameState.MAXWEAPON; ++m) {
								gameState.Ship.weapon[m - 1] = gameState.Ship.weapon[m];
							}
							gameState.Ship.weapon[GameState.MAXWEAPON - 1] = -1;
							i = -1;
						}
						i--;
					}
					// add points to trading skill
					// two points if you're on beginner-normal, one otherwise
					if (GameState.getDifficulty() < GameState.HARD) {
						gameState.Mercenary[0].trader += 2;
					} else {
						gameState.Mercenary[0].trader += 1;
					}

					if (gameState.Mercenary[0].trader > GameState.MAXSKILL) {
						gameState.Mercenary[0].trader = GameState.MAXSKILL;
					}
					gameState.RecalculateBuyPrices(gameState.Mercenary[0].curSystem);
					Popup popup1;
					popup1 = new Popup(popup.context, "Training completed",
						"After a few hours of training with a top expert, you feel your abilities have improved significantly.",
						"Under the watchful eye of the Captain, you demonstrate your abilities. The Captain provides some helpful pointers and tips, and teaches you a few new techniques. The few hours pass quickly, but you feel you've gained a lot from the experience.",
						"OK", cbShowNextPopup);
					popupQueue.push(popup1);
					showNextPopup();
					Travel();
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		}
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonDrinkCallback(View view) {
		Popup popup;
		popup = new Popup(this, "Drink Contents?",
			"You have come across an extremely rare bottle of Captain Marmoset's Amazing Skill Tonic! The \"use-by\" date is illegible, but might still be good. Would you like to drink it?",
			"Floating in orbit, you come across a bottle of Captain Marmoset's Amazing Skill Tonic. This concoction has been extremely hard to find since the elusive Captain Marmoset left on a mission to the heart of a comet.\nIn the old days, this stuff went for thousands of credits a bottle, since people reported significant gains in their abilitiesafter quaffing a bottle.\nThe \"best used by\" date stamped on the bottle has become illegible. The tonic might still be good. Then again, it's not clear what happens when the Tonic breaks down...",
			"Drink it", "Leave it", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				if (gameState.EncounterType == GameState.BOTTLEGOODENCOUNTER) {
					// two points if you're on beginner-normal, one otherwise
					gameState.IncreaseRandomSkill();
					if (GameState.getDifficulty() < GameState.HARD) {
						gameState.IncreaseRandomSkill();
					}
					Popup popup1;
					popup1 = new Popup(popup.context, "Tonic consumed",
						"Mmmmm. Captain Marmoset's Amazing Skill Tonic not only fills you with energy, but tastes like a fine single-malt.",
						"Captain Marmoset's Amazing Skill Tonic goes down very smoothly. You feel a slight tingling in your fingertips.",
						"OK", cbShowNextPopup);
					popupQueue.push(popup1);
					showNextPopup();
				} else if (gameState.EncounterType == GameState.BOTTLEOLDENCOUNTER) {
					// Quaff the out of date bottle of Skill Tonic?
					gameState.TonicTweakRandomSkill();
					Popup popup1;
					popup1 = new Popup(popup.context, "Tonic consumed",
						"While you don't know what it was supposed to taste like, you get the feeling that this dose of tonic was a bit off.",
						"Captain Marmoset's Amazing Skill Tonic tasted very strange, like slightly salty red wine. You feel a bit dizzy, and your teeth itch for a while.",
						"OK", cbShowNextPopup);
					popupQueue.push(popup1);
					showNextPopup();
				}
				Travel();
			}
		}, cbShowNextPopup
		);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonBribeCallback(View view) {
		gameState.AutoAttack = false;
		gameState.AutoFlee = false;
		String text = "", hint = "", title = "";
		Popup popup;
		if (Politics.mPolitics[WarpSystem.politics].bribeLevel <= 0) {
			title = "No bribe";
			text = "These police officers can't be bribed.";
			hint =
				"Certain governments have such an incorruptible police force that you can't bribe them. Other times, the police are corruptible, but their supervisors know what's going on, so they won't risk it.";
		}
		if (gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
			title = "No bribe";
			text =
				"We'd love to take your money, but Space Command already knows you've got illegal goods onboard.";
			hint =
				"Certain governments have such an incorruptible police force that you can't bribe them. Other times, the police are corruptible, but their supervisors know what's going on, so they won't risk it.";
		}
		if (!title.equals("")) {
			popup = new Popup(this, title, text, hint, "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.EncounterType == GameState.POLICEINSPECTION && gameState.Ship.cargo[GameState.FIREARMS] <= 0 && gameState.Ship.cargo[GameState.NARCOTICS] <= 0 && gameState.WildStatus != 1) {
			popup = new Popup(this, "You Have Nothing Illegal",
				"Are you sure you want to do that? You are not carrying illegal goods, so you have nothing to fear!",
				"", "Bribe", "Don't bribe", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					EncounterButtonBrideCallbackStep2();
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}
		EncounterButtonBrideCallbackStep2();
	}

	public void EncounterButtonBrideCallbackStep2() {
		int Bribe;
		// Bribe depends on how easy it is to bribe the police and commander's current worth
		Bribe = gameState.CurrentWorth() / ((10 + 5 * (GameState.IMPOSSIBLE - GameState
			.getDifficulty())) * Politics.mPolitics[WarpSystem.politics].bribeLevel);
		if (Bribe % 100 != 0) {
			Bribe += (100 - (Bribe % 100));
		}
		if (gameState.WildStatus == 1 || (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21)) {
			if (GameState.getDifficulty() <= GameState.NORMAL) {
				Bribe *= 2;
			} else {
				Bribe *= 3;
			}
		}
		Bribe = Math.max(100, Math.min(Bribe, 10000));

		final int b = Bribe;
		Popup popup;
		popup = new Popup(this, "Offer Bribe", String.format(
			"These police officers are willing to forego inspection for the amount for %d credits.",
			Bribe), "", "Pay", "Forget it", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				if (gameState.Credits < b) {
					Popup popup1;
					popup1 = new Popup(popup.context, "Not enough cash",
						"You don't have enough cash for a bribe.", "", "OK", cbShowNextPopup);
					popupQueue.push(popup1);
					showNextPopup();
				} else {
					gameState.Credits -= b;
					Travel();
				}
			}
		}, cbShowNextPopup);
		popupQueue.push(popup);
		showNextPopup();
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonSurrenderCallback(View view) {
		Popup popup;
		gameState.AutoAttack = false;
		gameState.AutoFlee = false;

		if (gameState.Opponent.type == GameState.MANTISTYPE) {
			if (gameState.ArtifactOnBoard) {
				popup = new Popup(this, "Surrender",
					"If you surrender to the aliens, they will steal the artifact. Are you sure you wish to do that?",
					"The aliens are only after the artifact. They will let you live, and even let you keep your cargo, but you won't be able to finish your quest.",
					"Surrender", "Fight", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						Popup popup1;
						popup1 = new Popup(popup.context, "Artifact Relinquished",
							"The aliens take the artifact from you.",
							"The aliens have taken the artifact from you. Well, it's rightfully theirs, so you probably shouldn't complain. You won't receive any reward from professor Berger, though.",
							"OK", cbShowNextPopup);
						popupQueue.push(popup1);
						gameState.ArtifactOnBoard = false;
					}
				}, cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				popup = new Popup(this, "To the death!", "Surrender? Hah! We want your HEAD!", "", "OK",
					cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
		} else if (gameState.ENCOUNTERPOLICE(gameState.EncounterType)) {
			if (gameState.PoliceRecordScore <= GameState.PSYCHOPATHSCORE) {
				popup = new Popup(this, "To the death!", "Surrender? Hah! We want your HEAD!", "", "OK",
					cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return;
			} else {
				String buf;
				if (gameState.WildStatus == 1) {
					buf = String.format(
						"%sIf you surrender, you will spend some time in prison and will have to pay a hefty fine. %sAre you sure you want to do that?",
						"You have Jonathan Wild on board! ", "Wild will be arrested, too. ");
				} else if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
					buf = String.format(
						"%sIf you surrender, you will spend some time in prison and will have to pay a hefty fine. %sAre you sure you want to do that?",
						"You have an illegal Reactor on board! ", "They will destroy the reactor. ");
				} else {
					buf = String.format(
						"%sIf you surrender, you will spend some time in prison and will have to pay a hefty fine. %sAre you sure you want to do that?",
						"", "");
				}

				popup = new Popup(this, "Surrender", buf, "", "Surrender", "Fight",
					new Popup.buttonCallback() {
						@Override
						public void execute(Popup popup, View view) {
							Arrested();
						}
					}, cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
		} else {
			int Bays, Blackmail, i, TotalCargo;
			gameState.Raided = true;

			TotalCargo = 0;
			for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
				TotalCargo += gameState.Ship.cargo[i];
			}
			if (TotalCargo <= 0) {
				Blackmail = Math.min(25000, Math.max(500, gameState.CurrentWorth() / 20));
				popup = new Popup(this, "Pirates Find No Cargo",
					"The pirates are very angry that they find no cargo on your ship. To stop them from destroying you, you have no choice but to pay them an amount equal to 5% of your current worth.",
					"If you have nothing in your cargo holds, the pirates will blow up your ship unless you pay them some money, equal to 5% of your current worth, which will be subtracted from your cash, unless you don't have enough of that, in which case it will be added to your debt. At least it's better than dying.",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				if (gameState.Credits >= Blackmail) {
					gameState.Credits -= Blackmail;
				} else {
					gameState.Debt += (Blackmail - gameState.Credits);
					gameState.Credits = 0;
				}
			} else {
				popup = new Popup(this, "Looting",
					"The pirates board your ship and transfer as much of your cargo to their own ship as their cargo bays can hold.",
					"The pirates steal from you what they can carry, but at least you get out of it alive.",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();

				Bays = gameState.Opponent.getType().cargoBays;
				for (i = 0; i < GameState.MAXGADGET; ++i) {
					if (gameState.Opponent.gadget[i] == GameState.EXTRABAYS) {
						Bays += 5;
					}
				}
				for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
					Bays -= gameState.Opponent.cargo[i];
				}

				// Pirates steal everything
				if (Bays >= TotalCargo) {
					for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
						gameState.Ship.cargo[i] = 0;
						gameState.BuyingPrice[i] = 0;
					}
				} else {
					// Pirates steal a lot
					while (Bays > 0) {
						i = gameState.GetRandom(GameState.MAXTRADEITEM);
						if (gameState.Ship.cargo[i] > 0) {
							gameState.BuyingPrice[i] =
								(gameState.BuyingPrice[i] * (gameState.Ship.cargo[i] - 1)) / gameState.Ship.cargo[i];
							--gameState.Ship.cargo[i];
							--Bays;
						}
					}
				}
			}
			if ((gameState.WildStatus == 1) && (gameState.Opponent.getType().crewQuarters > 1)) {
				// Wild hops onto Pirate Ship
				gameState.WildStatus = 0;
				popup = new Popup(this, "Wild Goes with Pirates",
					"The Pirate Captain turns out to be an old associate of Jonathan Wild's, and invites him to go to Kravat aboard the Pirate ship. Wild accepts the offer and thanks you for the ride.",
					"Jonathan Wild figures that it's probably safer to get a ride home with his old associate than stay on your ship. After all, if you surrender to pirates, what's to stop you from surrendering to the police?",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			} else if (gameState.WildStatus == 1) {
				// no room on pirate ship
				popup = new Popup(this, "Wild Chats with Pirates",
					"The Pirate Captain turns out to be an old associate of Jonathan Wild's. They talk about old times, and you get the feeling that Wild would switch ships if the Pirates had any quarters available.",
					"Jonathan Wild would have preferred to get a ride home with his old associate than stay in your ship. After all, if you surrender to pirates, what's to stop you from surrendering to the police? But the Pirates have no quarters available, so he grudgingly stays aboard your ship.",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			}
			if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
				// pirates puzzled by reactor
				popup = new Popup(this, "Pirates Examine Reactor",
					"The Pirates poke around the Ion Reactor while trying to figure out if it's valuable. They finally conclude that the Reactor is worthless, not to mention dangerous, and leave it on your ship.",
					"The good news is that you still have the Ion Reactor. The bad news is that you still have to worry about managing its depleting fuel store.",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			}
		}
		Travel();
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonAttackCallback(View view) {
		Popup popup;
		gameState.AutoAttack = false;
		gameState.AutoFlee = false;

		if (gameState.Ship.TotalWeapons(-1, -1) <= 0) {
			popup = new Popup(this, "No Weapons", "You can't attack without weapons!",
				"You either are flying a ship without any weapon slots, so your only option is to flee from fights, or you haven't bought any weapons yet. Sorry, no weapons, no attacking.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.EncounterType == GameState.POLICEINSPECTION &&
			gameState.Ship.cargo[GameState.FIREARMS] <= 0 &&
			gameState.Ship.cargo[GameState.NARCOTICS] <= 0) {
			popup = new Popup(this, "You Have Nothing Illegal",
				"Are you sure you want to do that? You are not carrying illegal goods, so you have nothing to fear!",
				"", "Attack", "Stay", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					EncounterButtonAttackCallbackStep2();
					showNextPopup();
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}
		EncounterButtonAttackCallbackStep2();
	}

	public void EncounterButtonAttackCallbackStep2() {
		Popup popup;
		if (gameState.ENCOUNTERPOLICE(
			gameState.EncounterType) || gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
			if (gameState.PoliceRecordScore > GameState.CRIMINALSCORE) {
				popup = new Popup(this, "Attack Police",
					"Are you sure you wish to attack the police? This will turn you into a criminal!",
					"If you attack the police, they know you are a die-hard criminal and will immediately label you as such.",
					"Attack", "Don't attack", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						if (gameState.PoliceRecordScore > GameState.CRIMINALSCORE) {
							gameState.PoliceRecordScore = GameState.CRIMINALSCORE;
						}

						gameState.PoliceRecordScore += GameState.ATTACKPOLICESCORE;

						if (gameState.EncounterType == GameState.POLICEIGNORE || gameState.EncounterType == GameState.POLICEINSPECTION || gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
							gameState.EncounterType = GameState.POLICEATTACK;
						}
						EncounterButtonAttackCallbackStartAttack();
					}
				}, cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
			/* Duplicated from Yes Callback */
			if (gameState.PoliceRecordScore > GameState.CRIMINALSCORE) {
				gameState.PoliceRecordScore = GameState.CRIMINALSCORE;
			}

			gameState.PoliceRecordScore += GameState.ATTACKPOLICESCORE;

			if (gameState.EncounterType == GameState.POLICEIGNORE ||
				gameState.EncounterType == GameState.POLICEINSPECTION ||
				gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
				gameState.EncounterType = GameState.POLICEATTACK;
			}
		} else if (gameState.ENCOUNTERPIRATE(gameState.EncounterType)) {
			if (gameState.EncounterType == GameState.PIRATEIGNORE) {
				gameState.EncounterType = GameState.PIRATEATTACK;
			}
		} else if (gameState.ENCOUNTERTRADER(gameState.EncounterType)) {
			if (gameState.EncounterType == GameState.TRADERIGNORE || gameState.EncounterType == GameState.TRADERBUY || gameState.EncounterType == GameState.TRADERSELL) {
				if (gameState.PoliceRecordScore >= GameState.CLEANSCORE) {
					popup = new Popup(this, "Attack Trader",
						"Are you sure you wish to attack the trader? This will immediately set your police record to dubious!",
						"While attacking a trader is not considered to be as bad as attacking the police (since no police is present, they cannot judge the exact circumstances of the attack), it will make the police suspicious of you.",
						"Attack", "Don't attack", new Popup.buttonCallback() {
						@Override
						public void execute(Popup popup, View view) {
							gameState.PoliceRecordScore = GameState.DUBIOUSSCORE;
							if (gameState.EncounterType != GameState.TRADERFLEE) {
								if (gameState.Opponent.TotalWeapons(-1, -1) <= 0) {
									gameState.EncounterType = GameState.TRADERFLEE;
								} else if (gameState.GetRandom(
									GameState.ELITESCORE) <= (gameState.ReputationScore * 10) / (1 + gameState.Opponent.type)) {
									gameState.EncounterType = GameState.TRADERFLEE;
								} else {
									gameState.EncounterType = GameState.TRADERATTACK;
								}
							}
							EncounterButtonAttackCallbackStartAttack();
						}
					}, cbShowNextPopup
					);
					popupQueue.push(popup);
					showNextPopup();
					return;
				} else {
					gameState.PoliceRecordScore += GameState.ATTACKTRADERSCORE;
				}
			}
			/* Duplicated from Yes callback */
			if (gameState.EncounterType != GameState.TRADERFLEE) {
				if (gameState.Opponent.TotalWeapons(-1, -1) <= 0) {
					gameState.EncounterType = GameState.TRADERFLEE;
				} else if (gameState.GetRandom(
					GameState.ELITESCORE) <= (gameState.ReputationScore * 10) / (1 + gameState.Opponent.type)) {
					gameState.EncounterType = GameState.TRADERFLEE;
				} else {
					gameState.EncounterType = GameState.TRADERATTACK;
				}
			}
		} else if (gameState.ENCOUNTERMONSTER(gameState.EncounterType)) {
			if (gameState.EncounterType == GameState.SPACEMONSTERIGNORE) {
				gameState.EncounterType = GameState.SPACEMONSTERATTACK;
			}
		} else if (gameState.ENCOUNTERDRAGONFLY(gameState.EncounterType)) {
			if (gameState.EncounterType == GameState.DRAGONFLYIGNORE) {
				gameState.EncounterType = GameState.DRAGONFLYATTACK;
			}
		} else if (gameState.ENCOUNTERSCARAB(gameState.EncounterType)) {
			if (gameState.EncounterType == GameState.SCARABIGNORE) {
				gameState.EncounterType = GameState.SCARABATTACK;
			}
		} else if (gameState.ENCOUNTERFAMOUS(gameState.EncounterType)) {
			if (gameState.EncounterType != GameState.FAMOUSCAPATTACK) {
				popup = new Popup(this, "Really attack?",
					"Famous Captains get famous by, among other things, destroying everyone who attacks them. Do you really want to attack?",
					"You grew up on stories of the adventures of the Great Captains. You heard how they explored the galaxy, invented technologies... and destroyed many, many pirates and villains in combat. Are you sure you want to attack one of these greats?",
					"Yes", "No", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						if (gameState.PoliceRecordScore > GameState.VILLAINSCORE) {
							gameState.PoliceRecordScore = GameState.VILLAINSCORE;
						}
						gameState.PoliceRecordScore += GameState.ATTACKTRADERSCORE;
						if (gameState.EncounterType == GameState.CAPTAINHUIEENCOUNTER) {
							gameState.addNewsEvent(GameState.CAPTAINHUIEATTACKED);
						} else if (gameState.EncounterType == GameState.CAPTAINAHABENCOUNTER) {
							gameState.addNewsEvent(GameState.CAPTAINAHABATTACKED);
						} else if (gameState.EncounterType == GameState.CAPTAINCONRADENCOUNTER) {
							gameState.addNewsEvent(GameState.CAPTAINCONRADATTACKED);
						}

						gameState.EncounterType = GameState.FAMOUSCAPATTACK;
						EncounterButtonAttackCallbackStartAttack();
					}
				}, cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
		}
		EncounterButtonAttackCallbackStartAttack();
	}

	public void EncounterButtonAttackCallbackStartAttack() {
		if (gameState.Continuous) {
			gameState.AutoAttack = true;
		}
		if (ExecuteAction(false)) {
			return;
		}
		if (gameState.Ship.hull <= 0) {
			return;
		}
		Travel();
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonIgnoreCallback(View view) {
		gameState.AutoAttack = false;
		gameState.AutoFlee = false;

		Travel();
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonFleeCallback(View view) {
		Popup popup;
		gameState.AutoAttack = false;
		gameState.AutoFlee = false;

		if (gameState.EncounterType == GameState.POLICEINSPECTION && gameState.Ship.cargo[GameState.FIREARMS] <= 0 &&
			gameState.Ship.cargo[GameState.NARCOTICS] <= 0 && gameState.WildStatus != 1 &&
			(gameState.ReactorStatus == 0 || gameState.ReactorStatus == 21)) {
			popup = new Popup(this, "You Have Nothing Illegal",
				"Are you sure you want to do that? You are not carrying illegal goods, so you have nothing to fear!",
				"", "Flee", "Stay", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					EncounterButtonFleeCallbackStep2();
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}
		EncounterButtonFleeCallbackStep2();
	}

	public void EncounterButtonFleeCallbackStep2() {
		Popup popup;
		if (gameState.EncounterType == GameState.POLICEINSPECTION) {
			gameState.EncounterType = GameState.POLICEATTACK;
			if (gameState.PoliceRecordScore > GameState.DUBIOUSSCORE) {
				gameState.PoliceRecordScore =
					GameState.DUBIOUSSCORE - (GameState.getDifficulty() < GameState.NORMAL ? 0 : 1);
			} else {
				gameState.PoliceRecordScore += GameState.FLEEFROMINSPECTION;
			}
		} else if (gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
			popup = new Popup(this, "Criminal Act!",
				"Are you sure you want to do that? The Customs Police know you have engaged in criminal activity, and will report it!",
				"", "Flee", "Stay", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					gameState.EncounterType = GameState.POLICEATTACK;
					if (gameState.PoliceRecordScore >= GameState.CRIMINALSCORE) {
						gameState.PoliceRecordScore = GameState.CRIMINALSCORE;
					} else {
						gameState.PoliceRecordScore += GameState.ATTACKPOLICESCORE;
					}
					EncounterButtonFleeCallbackStartFleeing();
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}
		EncounterButtonFleeCallbackStartFleeing();
	}

	public void EncounterButtonFleeCallbackStartFleeing() {
		if (gameState.Continuous) {
			gameState.AutoFlee = true;
		}
		if (ExecuteAction(true)) {
			return;
		}
		if (gameState.Ship.hull <= 0) {
			return;
		}
		Travel();
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonSubmitCallback(View view) {
		Popup popup;
		gameState.AutoAttack = false;
		gameState.AutoFlee = false;

		String buf, buf2;

		if (gameState.EncounterType == GameState.POLICEINSPECTION && (gameState.Ship.cargo[GameState.FIREARMS] > 0 ||
			gameState.Ship.cargo[GameState.NARCOTICS] > 0 || gameState.WildStatus == 1 ||
			(gameState.ReactorStatus > 1 && gameState.ReactorStatus < 21))) {
			if (gameState.WildStatus == 1) {
				if (gameState.Ship.cargo[GameState.FIREARMS] > 0 || gameState.Ship.cargo[GameState.NARCOTICS] > 0) {
					buf = "Jonathan Wild and illegal goods";
				} else {
					buf = "Jonathan Wild";
				}
				buf2 = "You will be arrested!";
			} else if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
				if (gameState.Ship.cargo[GameState.FIREARMS] > 0 || gameState.Ship.cargo[GameState.NARCOTICS] > 0) {
					buf = "an illegal Ion Reactor and other illegal goods";
				} else {
					buf = "an illegal Ion Reactor";
				}
				buf2 = "You will be arrested!";
			} else {
				buf = "illegal goods";
				buf2 = "";
			}
			popup = new Popup(this, "You Have Illegal Goods", String.format(
				"Are you sure you want to let the police search you? You are carrying %s! %s", buf, buf2),
				"Only when you are carrying illegal goods, the police will do something you don't like, so if you aren't carrying anything illegal, you usually should just submit, and not try to attack, flee or bribe.\nIf you are carrying illegal goods and the police searches you, they will impound the goods and fine you. You normally don't want to let the police search you when you are carrying illegal goods (firearms and narcotics), unless you are afraid they might kill you if you try to do something else.",
				"Yes", "No", new Popup.buttonCallback() {
				@Override
				public void execute(Popup popup, View view) {
					if ((gameState.Ship.cargo[GameState.FIREARMS] > 0) || (gameState.Ship.cargo[GameState.NARCOTICS] > 0)) {
						int Fine;
						// If you carry illegal goods, they are impounded and you are fined
						gameState.Ship.cargo[GameState.FIREARMS] = 0;
						gameState.BuyingPrice[GameState.FIREARMS] = 0;
						gameState.Ship.cargo[GameState.NARCOTICS] = 0;
						gameState.BuyingPrice[GameState.NARCOTICS] = 0;
						Fine = gameState.CurrentWorth() / ((GameState.IMPOSSIBLE + 2 - GameState
							.getDifficulty()) * 10);
						if (Fine % 50 != 0) {
							Fine += (50 - (Fine % 50));
						}
						Fine = Math.max(100, Math.min(Fine, 10000));
						if (gameState.Credits >= Fine) {
							gameState.Credits -= Fine;
						} else {
							gameState.Debt += (Fine - gameState.Credits);
							gameState.Credits = 0;
						}

						Popup popup1;
						popup1 = new Popup(popup.context, "Caught", String.format(
							"The police discovers illegal goods in your cargo holds. These goods are impounded and you are fined %d credits.",
							Fine),
							"Firearms and narcotics are illegal goods, and you lose these. You are fined a percentage of your total worth. This is subtracted from your credits. If you don't have enough credits, it increases your debt.",
							"OK", cbShowNextPopup
						);
						popupQueue.push(popup1);
						showNextPopup();
						gameState.PoliceRecordScore += GameState.TRAFFICKING;
					}
					if (gameState.WildStatus == 1) {
						// Jonathan Wild Captured, and your status damaged.
						Arrested();
						return;
					}
					if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
						// Police confiscate the Reactor.
						// Of course, this can only happen if somehow your
						// reactor on board -- otherwise you'll be arrested
						// before we get to this point. (no longer true - 25 August 2002)
						Popup popup1;
						popup1 = new Popup(popup.context, "Police Confiscate Reactor",
							"The Police confiscate the Ion Reactor as evidence of your dealings with unsavory characters.",
							"The bad news is that you've lost the Ion Reactor. The good news is that you no longer have to worry about managing its depleting fuel store.",
							"OK", cbShowNextPopup);
						popupQueue.push(popup1);
						showNextPopup();
						gameState.ReactorStatus = 0;
					}
					Travel();
					showNextPopup();
				}
			}, cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		} else {
			popup = new Popup(this, "Nothing Found",
				"The police find nothing illegal in your cargo holds, and apologize for the inconvenience.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.PoliceRecordScore -= GameState.TRAFFICKING;
			Travel();
		}
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonIntCallback(View view) {
		gameState.AutoFlee = gameState.AutoAttack = false;
		((FragmentEncounter) currentFragment).btnInt.setVisibility(View.INVISIBLE);
		((FragmentEncounter) currentFragment).pBarEncounter.setVisibility(View.INVISIBLE);
	}

	@SuppressWarnings("UnusedParameters")
	public void EncounterButtonTribbleCallback(View view) {
		Toast.makeText(this, "Squeek! Squeek!", Toast.LENGTH_SHORT).show();
	}

	public void IncDays(int Amount) {
		gameState.Days += Amount;
		if (gameState.InvasionStatus > 0 && gameState.InvasionStatus < 8) {
			gameState.InvasionStatus += Amount;
			if (gameState.InvasionStatus >= 8) {
				gameState.SolarSystem[GameState.GEMULONSYSTEM].special = GameState.GEMULONINVADED;
				gameState.SolarSystem[GameState.GEMULONSYSTEM].techLevel = 0;
				gameState.SolarSystem[GameState.GEMULONSYSTEM].politics = GameState.ANARCHY;
			}
		}

		if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			gameState.ReactorStatus += Amount;
			if (gameState.ReactorStatus > 20) {
				gameState.ReactorStatus = 20;
			}
		} // TODO is there something missing here?

		if (gameState.ExperimentStatus > 0 && gameState.ExperimentStatus < 12) {
			gameState.ExperimentStatus += Amount;
			if (gameState.ExperimentStatus > 11) {
				gameState.FabricRipProbability = GameState.FABRICRIPINITIALPROBABILITY;
				gameState.SolarSystem[GameState.DALEDSYSTEM].special = GameState.EXPERIMENTNOTSTOPPED;
				// in case Amount > 1
				gameState.ExperimentStatus = 12;
				Popup popup = new Popup(this, "Experiment Performed",
					"The galaxy is abuzz with news of a terrible malfunction in Dr. Fehler's laboratory. Evidently, he was not warned in time and he performed his experiment... with disastrous results!",
					"", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				gameState.addNewsEvent(GameState.EXPERIMENTPERFORMED);
			}
		} else if (gameState.ExperimentStatus == 12 && gameState.FabricRipProbability > 0) {
			gameState.FabricRipProbability -= Amount;
		}
	}

	public void Travel() {
		int EncounterTest, StartClicks, i, j, Repairs, rareEncounter, previousTribbles, FirstEmptySlot;
		boolean Pirate, Trader, Police, Mantis, TryAutoRepair, FoodOnBoard;
		boolean HaveMilitaryLaser, HaveReflectiveShield;
		Ship Ship = gameState.Ship;
		CrewMember COMMANDER = gameState.Mercenary[0];
		Popup popup;

		Pirate = false;
		Trader = false;
		Police = false;
		Mantis = false;
		HaveMilitaryLaser = gameState.Ship.HasWeapon(GameState.MILITARYLASERWEAPON, true);
		HaveReflectiveShield = gameState.Ship.HasShield(GameState.REFLECTIVESHIELD) > 0;

		// if timespace is ripped, we may switch the warp system here.
		if (gameState.PossibleToGoThroughRip && gameState.ExperimentStatus == 12 && gameState.FabricRipProbability > 0 &&
			(gameState.GetRandom(
				100) < gameState.FabricRipProbability || gameState.FabricRipProbability == 25)) {
			popup = new Popup(this, "Timespace Fabric Rip",
				"You have flown through a tear in the timespace continuum caused by Dr. Fehler's failed experiment. You may not have reached your planned destination!",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.WarpSystem = gameState.GetRandom(GameState.MAXSOLARSYSTEM);
			WarpSystem = gameState.SolarSystem[gameState.WarpSystem];
		}

		gameState.PossibleToGoThroughRip = false;

		StartClicks = gameState.Clicks;
		--gameState.Clicks;

		while (gameState.Clicks > 0) {
			// Engineer may do some repairs
			Repairs = gameState.GetRandom(Ship.EngineerSkill()) >> 1;
			Ship.hull += Repairs;
			if (Ship.hull > gameState.Ship.GetHullStrength()) {
				Repairs = Ship.hull - gameState.Ship.GetHullStrength();
				Ship.hull = gameState.Ship.GetHullStrength();
			} else {
				Repairs = 0;
			}

			// Shields are easier to repair
			Repairs = 2 * Repairs;
			for (i = 0; i < GameState.MAXSHIELD; ++i) {
				if (Ship.shield[i] < 0) {
					break;
				}
				Ship.shieldStrength[i] += Repairs;
				if (Ship.shieldStrength[i] > Shields.mShields[Ship.shield[i]].power) {
					Repairs = Ship.shieldStrength[i] - Shields.mShields[Ship.shield[i]].power;
					Ship.shieldStrength[i] = Shields.mShields[Ship.shield[i]].power;
				} else {
					Repairs = 0;
				}
			}

			// Encounter with space monster
			if ((gameState.Clicks == 1) && (gameState.WarpSystem == GameState.ACAMARSYSTEM) && (gameState.MonsterStatus == 1)) {
				gameState.Opponent = gameState.SpaceMonster;
				gameState.Opponent.hull = gameState.MonsterHull;
				gameState.Mercenary[gameState.Opponent.crew[0]].pilot = 8 + GameState.getDifficulty();
				gameState.Mercenary[gameState.Opponent.crew[0]].fighter = 8 + GameState.getDifficulty();
				gameState.Mercenary[gameState.Opponent.crew[0]].trader = 1;
				gameState.Mercenary[gameState.Opponent.crew[0]].engineer = 1 + GameState.getDifficulty();
				if (Ship.isCloakedTo(gameState.Opponent)) {
					gameState.EncounterType = GameState.SPACEMONSTERIGNORE;
				} else {
					gameState.EncounterType = GameState.SPACEMONSTERATTACK;
				}
				changeFragment(FRAGMENTS.ENCOUNTER);
				return;
			}

			// Encounter with the stolen Scarab
			if (gameState.Clicks == 20 && WarpSystem.special == GameState.SCARABDESTROYED &&
				gameState.ScarabStatus == 1 && gameState.ArrivedViaWormhole) {
				gameState.Opponent = gameState.Scarab;
				gameState.Mercenary[gameState.Opponent.crew[0]].pilot = 5 + GameState.getDifficulty();
				gameState.Mercenary[gameState.Opponent.crew[0]].fighter = 6 + GameState.getDifficulty();
				gameState.Mercenary[gameState.Opponent.crew[0]].trader = 1;
				gameState.Mercenary[gameState.Opponent.crew[0]].engineer = 6 + GameState.getDifficulty();
				if (Ship.isCloakedTo(gameState.Opponent)) {
					gameState.EncounterType = GameState.SCARABIGNORE;
				} else {
					gameState.EncounterType = GameState.SCARABATTACK;
				}
				changeFragment(FRAGMENTS.ENCOUNTER);
				return;
			}
			// Encounter with stolen Dragonfly
			if ((gameState.Clicks == 1) && (WarpSystem == gameState.SolarSystem[GameState.ZALKONSYSTEM]) && (gameState.DragonflyStatus == 4)) {
				gameState.Opponent = gameState.Dragonfly;
				gameState.Mercenary[gameState.Opponent.crew[0]].pilot = 4 + GameState.getDifficulty();
				gameState.Mercenary[gameState.Opponent.crew[0]].fighter = 6 + GameState.getDifficulty();
				gameState.Mercenary[gameState.Opponent.crew[0]].trader = 1;
				gameState.Mercenary[gameState.Opponent.crew[0]].engineer = 6 + GameState.getDifficulty();
				if (Ship.isCloakedTo(gameState.Opponent)) {
					gameState.EncounterType = GameState.DRAGONFLYIGNORE;
				} else {
					gameState.EncounterType = GameState.DRAGONFLYATTACK;
				}
				changeFragment(FRAGMENTS.ENCOUNTER);
				return;
			}

			if (WarpSystem == gameState.SolarSystem[GameState.GEMULONSYSTEM] && gameState.InvasionStatus > 7) {
				if (gameState.GetRandom(10) > 4) {
					Mantis = true;
				}
			} else {
				// Check if it is time for an encounter
				EncounterTest = gameState.GetRandom(44 - (2 * GameState.getDifficulty()));

				// encounters are half as likely if you're in a flea.
				if (Ship.type == 0) {
					EncounterTest *= 2;
				}

				if (EncounterTest < Politics.mPolitics[WarpSystem.politics].strengthPirates && !gameState.Raided) // When you are already raided, other pirates have little to gain
				{
					Pirate = true;
				} else if (EncounterTest < Politics.mPolitics[WarpSystem.politics].strengthPirates + gameState
					.STRENGTHPOLICE(WarpSystem))
				// StrengthPolice adapts itself to your criminal record: you'll
				// encounter more police if you are a hardened criminal.
				{
					Police = true;
				} else if (EncounterTest < Politics.mPolitics[WarpSystem.politics].strengthPirates +
					gameState.STRENGTHPOLICE(WarpSystem) +
					Politics.mPolitics[WarpSystem.politics].strengthTraders) {
					Trader = true;
				} else if (gameState.WildStatus == 1 && WarpSystem == gameState.SolarSystem[GameState.KRAVATSYSTEM]) {
					// if you're coming in to Kravat & you have Wild onboard, there'll be swarms o' cops.
					rareEncounter = gameState.GetRandom(100);
					if (GameState.getDifficulty() <= GameState.EASY && rareEncounter < 25) {
						Police = true;
					} else if (GameState.getDifficulty() == GameState.NORMAL && rareEncounter < 33) {
						Police = true;
					} else if (GameState.getDifficulty() > GameState.NORMAL && rareEncounter < 50) {
						Police = true;
					}
				}
				if (!(Trader || Police || Pirate)) {
					if (gameState.ArtifactOnBoard && gameState.GetRandom(20) <= 3) {
						Mantis = true;
					}
				}
			}

			// Encounter with police
			if (Police) {
				gameState.GenerateOpponent(GameState.POLICE);
				gameState.EncounterType = GameState.POLICEIGNORE;
				// If you are cloaked, they don't see you
				if (Ship.isCloakedTo(gameState.Opponent)) {
					gameState.EncounterType = GameState.POLICEIGNORE;
				} else if (gameState.PoliceRecordScore < GameState.DUBIOUSSCORE) {
					// If you're a criminal, the police will tend to attack
					if (gameState.Opponent.TotalWeapons(-1, -1) <= 0) {
						if (gameState.Opponent.isCloakedTo(Ship)) {
							gameState.EncounterType = GameState.POLICEIGNORE;
						} else {
							gameState.EncounterType = GameState.POLICEFLEE;
						}
					}
					if (gameState.ReputationScore < GameState.AVERAGESCORE) {
						gameState.EncounterType = GameState.POLICEATTACK;
					} else if (gameState.GetRandom(
						GameState.ELITESCORE) > (gameState.ReputationScore / (1 + gameState.Opponent.type))) {
						gameState.EncounterType = GameState.POLICEATTACK;
					} else if (gameState.Opponent.isCloakedTo(Ship)) {
						gameState.EncounterType = GameState.POLICEIGNORE;
					} else {
						gameState.EncounterType = GameState.POLICEFLEE;
					}
				} else if (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE &&
					gameState.PoliceRecordScore < GameState.CLEANSCORE && !gameState.Inspected) {
					// If you're reputation is dubious, the police will inspect you
					gameState.EncounterType = GameState.POLICEINSPECTION;
					gameState.Inspected = true;
				} else if (gameState.PoliceRecordScore < GameState.LAWFULSCORE) {
					// If your record is clean, the police will inspect you with a chance of 10% on Normal
					if (gameState.GetRandom(12 - GameState.getDifficulty()) < 1 && !gameState.Inspected) {
						gameState.EncounterType = GameState.POLICEINSPECTION;
						gameState.Inspected = true;
					}
				} else {
					// If your record indicates you are a lawful trader, the chance on inspection drops to 2.5%
					if (gameState.GetRandom(40) == 1 && !gameState.Inspected) {
						gameState.EncounterType = GameState.POLICEINSPECTION;
						gameState.Inspected = true;
					}
				}

				// if you're suddenly stuck in a lousy ship, Police won't flee even if you
				// have a fearsome reputation.
				if (gameState.EncounterType == GameState.POLICEFLEE && gameState.Opponent.type > Ship.type) {
					if (gameState.PoliceRecordScore < GameState.DUBIOUSSCORE) {
						gameState.EncounterType = GameState.POLICEATTACK;
					} else {
						gameState.EncounterType = GameState.POLICEINSPECTION;
					}
				}

				// If they ignore you and you can't see them, the encounter doesn't take place
				if (gameState.EncounterType == GameState.POLICEIGNORE && gameState.Opponent.isCloakedTo(
					Ship)) {
					--gameState.Clicks;
					continue;
				}


				// If you automatically don't want to confront someone who ignores you, the
				// encounter may not take place
				if (gameState.AlwaysIgnorePolice && (gameState.EncounterType == GameState.POLICEIGNORE || gameState.EncounterType == GameState.POLICEFLEE)) {
					--gameState.Clicks;
					continue;
				}
				changeFragment(FRAGMENTS.ENCOUNTER);
				return;
			}
			// Encounter with pirate
			else if (Pirate || Mantis) {
				if (Mantis) {
					gameState.GenerateOpponent(GameState.MANTIS);
				} else {
					gameState.GenerateOpponent(GameState.PIRATE);
				}

				// If you have a cloak, they don't see you
				if (Ship.isCloakedTo(gameState.Opponent)) {
					gameState.EncounterType = GameState.PIRATEIGNORE;
				}

				// Pirates will mostly attack, but they are cowardly: if your rep is too high, they tend to flee
				else if (gameState.Opponent.type >= 7 || gameState.GetRandom(
					GameState.ELITESCORE) > (gameState.ReputationScore * 4) / (1 + gameState.Opponent.type)) {
					gameState.EncounterType = GameState.PIRATEATTACK;
				} else {
					gameState.EncounterType = GameState.PIRATEFLEE;
				}

				if (Mantis) {
					gameState.EncounterType = GameState.PIRATEATTACK;
				}

				// if Pirates are in a better ship, they won't flee, even if you have a very scary
				// reputation.
				if (gameState.EncounterType == GameState.PIRATEFLEE && gameState.Opponent.type > Ship.type) {
					gameState.EncounterType = GameState.PIRATEATTACK;
				}

				// If they ignore you or flee and you can't see them, the encounter doesn't take place
				if ((gameState.EncounterType == GameState.PIRATEIGNORE || gameState.EncounterType == GameState.PIRATEFLEE) && gameState.Opponent
					.isCloakedTo(Ship)) {
					--gameState.Clicks;
					continue;
				}
				if (gameState.AlwaysIgnorePirates && (gameState.EncounterType == GameState.PIRATEIGNORE || gameState.EncounterType == GameState.PIRATEFLEE)) {
					--gameState.Clicks;
					continue;
				}
				changeFragment(FRAGMENTS.ENCOUNTER);
				return;
			}
			// Encounter with trader
			else if (Trader) {
				gameState.GenerateOpponent(GameState.TRADER);
				gameState.EncounterType = GameState.TRADERIGNORE;
				// If you are cloaked, they don't see you
				if (Ship.isCloakedTo(gameState.Opponent)) {
					gameState.EncounterType = GameState.TRADERIGNORE;
				}
				// If you're a criminal, traders tend to flee if you've got at least some reputation
				else if (gameState.PoliceRecordScore <= GameState.CRIMINALSCORE) {
					if (gameState.GetRandom(
						GameState.ELITESCORE) <= (gameState.ReputationScore * 10) / (1 + gameState.Opponent.type)) {
						if (gameState.Opponent.isCloakedTo(Ship)) {
							gameState.EncounterType = GameState.TRADERIGNORE;
						} else {
							gameState.EncounterType = GameState.TRADERFLEE;
						}
					}
				}

				// Will there be trade in orbit?
				if (gameState.EncounterType == GameState.TRADERIGNORE && (gameState.GetRandom(
					1000) < gameState.ChanceOfTradeInOrbit)) {
					if (gameState.Ship.FilledCargoBays() < gameState.Ship
						.TotalCargoBays() && gameState.Opponent.HasTradeableItems(gameState.WarpSystem,
						GameState.TRADERSELL)) {
						gameState.EncounterType = GameState.TRADERSELL;
					}

					// we fudge on whether the trader has capacity to carry the stuff he's buying.
					if (Ship.HasTradeableItems(gameState.WarpSystem,
						GameState.TRADERBUY) && gameState.EncounterType != GameState.TRADERSELL) {
						gameState.EncounterType = GameState.TRADERBUY;
					}
				}

				// If they ignore you and you can't see them, the encounter doesn't take place
				if ((gameState.EncounterType == GameState.TRADERIGNORE || gameState.EncounterType == GameState.TRADERFLEE ||
					gameState.EncounterType == GameState.TRADERSELL || gameState.EncounterType == GameState.TRADERBUY) && gameState.Opponent
					.isCloakedTo(Ship)) {
					--gameState.Clicks;
					continue;
				}
				// pay attention to user's prefs with regard to ignoring traders
				if (gameState.AlwaysIgnoreTraders && (gameState.EncounterType == GameState.TRADERIGNORE || gameState.EncounterType == GameState.TRADERFLEE)) {
					--gameState.Clicks;
					continue;
				}
				// pay attention to user's prefs with regard to ignoring trade in orbit
				if (gameState.AlwaysIgnoreTradeInOrbit && (gameState.EncounterType == GameState.TRADERBUY || gameState.EncounterType == GameState.TRADERSELL)) {
					--gameState.Clicks;
					continue;
				}
				changeFragment(FRAGMENTS.ENCOUNTER);
				return;
			}
			// Very Rare Random Events:
			// 1. Encounter the abandoned Marie Celeste, which you may loot.
			// 2. Captain Ahab will trade your Reflective Shield for skill points in Piloting.
			// 3. Captain Conrad will trade your Military Laser for skill points in Engineering.
			// 4. Captain Huie will trade your Military Laser for points in Trading.
			// 5. Encounter an out-of-date bottle of Captain Marmoset's Skill Tonic. This
			//    will affect skills depending on game difficulty level.
			// 6. Encounter a good bottle of Captain Marmoset's Skill Tonic, which will invoke
			//    IncreaseRandomSkill one or two times, depending on game difficulty.
			else if ((gameState.Days > 10) && (gameState.GetRandom(
				1000) < gameState.ChanceOfVeryRareEncounter)) {
				rareEncounter = gameState.GetRandom(GameState.MAXVERYRAREENCOUNTER);

				switch (rareEncounter) {
					case GameState.MARIECELESTE:
						if ((gameState.VeryRareEncounter & GameState.ALREADYMARIE) != GameState.ALREADYMARIE) {
							gameState.VeryRareEncounter |= GameState.ALREADYMARIE;
							gameState.EncounterType = GameState.MARIECELESTEENCOUNTER;
							gameState.GenerateOpponent(GameState.TRADER);
							for (i = 0; i < GameState.MAXTRADEITEM; i++) {
								gameState.Opponent.cargo[i] = 0;
							}
							gameState.Opponent.cargo[GameState.NARCOTICS] = Math.min(
								gameState.Opponent.getType().cargoBays, 5);
							changeFragment(FRAGMENTS.ENCOUNTER);
							return;
						}
						break;

					case GameState.CAPTAINAHAB:
						if (HaveReflectiveShield && COMMANDER.pilot < 10 &&
							gameState.PoliceRecordScore > GameState.CRIMINALSCORE &&
							(gameState.VeryRareEncounter & GameState.ALREADYAHAB) != GameState.ALREADYAHAB) {
							gameState.VeryRareEncounter |= GameState.ALREADYAHAB;
							gameState.EncounterType = GameState.CAPTAINAHABENCOUNTER;
							gameState.GenerateOpponent(GameState.FAMOUSCAPTAIN);
							changeFragment(FRAGMENTS.ENCOUNTER);
							return;
						}
						break;

					case GameState.CAPTAINCONRAD:
						if (HaveMilitaryLaser && COMMANDER.engineer < 10 &&
							gameState.PoliceRecordScore > GameState.CRIMINALSCORE &&
							(gameState.VeryRareEncounter & GameState.ALREADYCONRAD) != GameState.ALREADYCONRAD) {
							gameState.VeryRareEncounter &= GameState.ALREADYCONRAD;
							gameState.EncounterType = GameState.CAPTAINCONRADENCOUNTER;
							gameState.GenerateOpponent(GameState.FAMOUSCAPTAIN);
							changeFragment(FRAGMENTS.ENCOUNTER);
							return;
						}
						break;

					case GameState.CAPTAINHUIE:
						if (HaveMilitaryLaser && COMMANDER.trader < 10 &&
							gameState.PoliceRecordScore > GameState.CRIMINALSCORE &&
							(gameState.VeryRareEncounter & GameState.ALREADYHUIE) != GameState.ALREADYHUIE) {
							gameState.VeryRareEncounter |= GameState.ALREADYHUIE;
							gameState.EncounterType = GameState.CAPTAINHUIEENCOUNTER;
							gameState.GenerateOpponent(GameState.FAMOUSCAPTAIN);
							changeFragment(FRAGMENTS.ENCOUNTER);
							return;
						}
						break;
					case GameState.BOTTLEOLD:
						if ((gameState.VeryRareEncounter & GameState.ALREADYBOTTLEOLD) != GameState.ALREADYBOTTLEOLD) {
							gameState.VeryRareEncounter |= GameState.ALREADYBOTTLEOLD;
							gameState.EncounterType = GameState.BOTTLEOLDENCOUNTER;
							gameState.GenerateOpponent(GameState.TRADER);
							gameState.Opponent.type = GameState.BOTTLETYPE;
							gameState.Opponent.hull = 10;
							changeFragment(FRAGMENTS.ENCOUNTER);
							return;
						}
						break;
					case GameState.BOTTLEGOOD:
						if ((gameState.VeryRareEncounter & GameState.ALREADYBOTTLEGOOD) != GameState.ALREADYBOTTLEGOOD) {
							gameState.VeryRareEncounter |= GameState.ALREADYBOTTLEGOOD;
							gameState.EncounterType = GameState.BOTTLEGOODENCOUNTER;
							gameState.GenerateOpponent(GameState.TRADER);
							gameState.Opponent.type = GameState.BOTTLETYPE;
							gameState.Opponent.hull = 10;
							changeFragment(FRAGMENTS.ENCOUNTER);
							return;
						}
						break;
				}
			}
			--gameState.Clicks;
		}

		// ah, just when you thought you were gonna get away with it...
		if (gameState.JustLootedMarie) {
			gameState.GenerateOpponent(GameState.POLICE);
			gameState.EncounterType = GameState.POSTMARIEPOLICEENCOUNTER;
			gameState.JustLootedMarie = false;
			gameState.Clicks++;
			changeFragment(FRAGMENTS.ENCOUNTER);
			return;
		}

		// Arrival in the target system
		if (StartClicks > 20) {
			popup = new Popup(this, "Uneventful trip",
				"After an uneventful trip, you arrive at your destination.",
				"Be glad you didn't encounter any pirates.", "OK", cbShowNextPopup);
		} else {
			popup = new Popup(this, "Arrival", "You arrive at your destination.",
				"Another trip you have survived.", "OK", cbShowNextPopup);
		}
		popupQueue.push(popup);
		showNextPopup();

		// Check for Large Debt - 06/30/01 SRA
		if (gameState.Debt >= 75000) {
			popup = new Popup(this, "Warning: Large Debt",
				"Your debt is getting too large. Reduce it quickly or your ship will be put on a chain!",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		}
		// Debt Reminder
		if (gameState.Debt > 0 && gameState.RemindLoans && gameState.Days % 5 == 0) {
			popup = new Popup(this, "Loan Notification", String.format(
				"The Bank's Loan Officer reminds you that your debt continues to accrue interest. You currently owe %d credits.",
				gameState.Debt),
				"The Bank Officer will contact you every five days to remind you of your debt. You can turn off these warnings on the second page of Game Options.",
				"OK", cbShowNextPopup
			);
			popupQueue.push(popup);
			showNextPopup();
		}

		Arrival();
		// Reactor warnings:
		// now they know the quest has a time constraint!
		if (gameState.ReactorStatus == 2) {
			popup = new Popup(this, "Reactor Warning",
				"You notice the Ion Reactor has begun to consume fuel rapidly. In a single day, it has burned up nearly half a bay of fuel!",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		}
		// better deliver it soon!
		else if (gameState.ReactorStatus == 16) {
			popup = new Popup(this, "Reactor Warning",
				"The Ion Reactor is emitting a shrill whine, and it's shaking. The display indicates that it is suffering from fuel starvation.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		}
		// last warning!
		else if (gameState.ReactorStatus == 18) {
			popup = new Popup(this, "Reactor Warning",
				"The Ion Reactor is smoking and making loud noises. The display warns that the core is close to the melting temperature.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		}
		if (gameState.ReactorStatus == 20) {
			popup = new Popup(this, "Reactor Meltdown!",
				"Just as you approach the docking ay, the reactor explodes into a huge radioactive fireball!",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.ReactorStatus = 0;
			if (gameState.EscapePod) {
				EscapeWithPod();
				return;
			} else {
				popup = new Popup(this, "You lose", "Your ship has been destroyed.", "", "OK",
					cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				btnDestroyed();
				return;
			}
		}

		if (gameState.TrackAutoOff && gameState.TrackedSystem == COMMANDER.curSystem) {
			gameState.TrackedSystem = -1;
		}

		FoodOnBoard = false;
		previousTribbles = Ship.tribbles;

		if (Ship.tribbles > 0 && gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			Ship.tribbles /= 2;
			if (Ship.tribbles < 10) {
				Ship.tribbles = 0;
				popup = new Popup(this, "All the Tribbles Died",
					"The radiation from the Ion Reactor is deadly to Tribbles. All of the Tribbles on board your ship have died.",
					"", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				popup = new Popup(this, "Half the Tribbles Died",
					"The radiation from the Ion Reactor seems to be deadly to Tribbles. Half the Tribbles on board died.",
					"Radiation poisoning seems particularly effective in killing Tribbles. Unfortunately, their fur falls out when they're irradiated, so you can't salvage anything to sell.",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			}
		} else if (Ship.tribbles > 0 && Ship.cargo[GameState.NARCOTICS] > 0) {
			Ship.tribbles = 1 + gameState.GetRandom(3);
			j = 1 + gameState.GetRandom(3);
			i = Math.min(j, Ship.cargo[GameState.NARCOTICS]);
			gameState.BuyingPrice[GameState.NARCOTICS] =
				(gameState.BuyingPrice[GameState.NARCOTICS] * (Ship.cargo[GameState.NARCOTICS] - i)) / Ship.cargo[GameState.NARCOTICS];
			Ship.cargo[GameState.NARCOTICS] -= i;
			Ship.cargo[GameState.FURS] += i;
			popup = new Popup(this, "Tribbles ate Narcotics",
				"Tribbles ate your narcotics, and it killed most of them. At least the furs remained.", "",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		} else if (Ship.tribbles > 0 && Ship.cargo[GameState.FOOD] > 0) {
			Ship.tribbles += 100 + gameState.GetRandom(Ship.cargo[GameState.FOOD] * 100);
			i = gameState.GetRandom(Ship.cargo[GameState.FOOD]);
			gameState.BuyingPrice[GameState.FOOD] =
				(gameState.BuyingPrice[GameState.FOOD] * i) / Ship.cargo[GameState.FOOD];
			Ship.cargo[GameState.FOOD] = i;
			popup = new Popup(this, "Tribbles Ate Food",
				"You find that, instead of food, some of your cargo bays contain only tribbles!",
				"Alas, tribbles are hungry and fast-multiplying animals. You shouldn't expect to be able to hold them out of your cargo bays. You should find a way to get rid of them.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			FoodOnBoard = true;
		}

		if (Ship.tribbles > 0 && Ship.tribbles < GameState.MAXTRIBBLES) {
			Ship.tribbles += 1 + gameState.GetRandom(Math.max(1,
				(Ship.tribbles >> (FoodOnBoard ? 0 : 1))));
		}

		if (Ship.tribbles > GameState.MAXTRIBBLES) {
			Ship.tribbles = GameState.MAXTRIBBLES;
		}

		String buf;
		if ((previousTribbles < 100 && Ship.tribbles >= 100) ||
			(previousTribbles < 1000 && Ship.tribbles >= 1000) ||
			(previousTribbles < 10000 && Ship.tribbles >= 10000) ||
			(previousTribbles < 50000 && Ship.tribbles >= 50000)) {
			if (Ship.tribbles >= GameState.MAXTRIBBLES) {
				buf = "a dangerous number of";
			} else {
				buf = String.format("%d", Ship.tribbles);
			}
			popup = new Popup(this, "Space Port Inspector",
				"Excuse me, but do you realize you have " + buf + " tribbles on board your ship?",
				"You might want to do something about those Tribbles...", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
		}

		gameState.TribbleMessage = false;

		Ship.hull += gameState.GetRandom(Ship.EngineerSkill());
		if (Ship.hull > Ship.GetHullStrength()) {
			Ship.hull = Ship.GetHullStrength();
		}

		TryAutoRepair = true;
		if (gameState.AutoFuel) {
			btnShipyardBuyFuel(9999);
			if (Ship.GetFuel() < Ship.GetFuelTanks()) {
				if (gameState.AutoRepair && Ship.hull < Ship.GetHullStrength()) {
					popup = new Popup(this, "Not Enough Money",
						"You don't have enough money to get a full tank or full hull repairs.",
						"In the Options menu you have indicated that you wish to buy full tanks and full hull repairs automatically when you arrive in  new system, but you don't have the money for that. At least make sure that you buy full tanks after you have made some money.",
						"OK", cbShowNextPopup);
					TryAutoRepair = false;
				} else {
					popup = new Popup(this, "No Full Tanks",
						"You do not have enough money to buy full tanks.",
						"You have checked the automatic buying of full fuel tanks in the Options menu, but you don't have enough money to buy those tanks. Don't forget to buy them as soon as you have made some money.",
						"OK", cbShowNextPopup);
				}
				popupQueue.push(popup);
				showNextPopup();
			}
		}

		if (gameState.AutoRepair && TryAutoRepair) {
			btnShipyardBuyRepairs(99999);
			if (Ship.hull < Ship.GetHullStrength()) {
				popup = new Popup(this, "No Full Repairs",
					"You don't have enough money to get your hull fully repaired.",
					"You have automatic full hull repairs checked in the Options menu, but you don't have the money for that. If you still want the repairs, don't forget to make them before you leave the system.",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			}
		}

  /* This Easter Egg gives the commander a Lighting Shield */
		if (COMMANDER.curSystem == GameState.OGSYSTEM) {
			i = 0;
			boolean EasterEgg = false;
			while (i < GameState.MAXTRADEITEM) {
				if (Ship.cargo[i] != 1) {
					break;
				}
				++i;
			}
			if (i >= GameState.MAXTRADEITEM) {
				FirstEmptySlot = gameState.GetFirstEmptySlot(Ship.getType().shieldSlots, Ship.shield);
			} else {
				FirstEmptySlot = -1;
			}

			if (FirstEmptySlot >= 0) {
				popup = new Popup(this, "Easter",
					"Congratulations! An eccentric Easter Bunny decides to exchange your trade goods for a special present!",
					"Look up your ship's equipment.", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				Ship.shield[FirstEmptySlot] = GameState.LIGHTNINGSHIELD;
				Ship.shieldStrength[FirstEmptySlot] = Shields.mShields[GameState.LIGHTNINGSHIELD].power;
				EasterEgg = true;
			}

			if (EasterEgg) {
				for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
					Ship.cargo[i] = 0;
					gameState.BuyingPrice[i] = 0;
				}
			}
		}

		// It seems a glitch may cause cargo bays to become negative - no idea how...
		for (i = 0; i < GameState.MAXTRADEITEM; ++i) {
			if (Ship.cargo[i] < 0) {
				Ship.cargo[i] = 0;
			}
		}

		changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
	}

	public void Arrested() {
		// *************************************************************************
		// You get arrested
		// *************************************************************************
		int Fine, Imprisonment;
		int i;

		Fine = ((1 + (((gameState.CurrentWorth() * Math.min(80,
			-gameState.PoliceRecordScore)) / 100) / 500)) * 500);
		if (gameState.WildStatus == 1) {
			Fine *= 1.05;
		}
		Imprisonment = Math.max(30, -gameState.PoliceRecordScore);

		String buf;
		buf =
			"Arrested\n\nYou are arrested and taken to the space station, where you are brought before a court of law.\n\n";
		buf += "Verdict\n\n";
		buf += String.format("You are convicted to %d days in prison and a fine of %d credits.",
			Imprisonment, Fine);

		if (gameState.Ship.cargo[GameState.NARCOTICS] > 0 || gameState.Ship.cargo[GameState.FIREARMS] > 0) {
			buf +=
				"\n\nIllegal Goods Impounded\n\nThe police also impound all of the illegal goods you have on board.";
			gameState.Ship.cargo[GameState.NARCOTICS] = 0;
			gameState.Ship.cargo[GameState.FIREARMS] = 0;
		}

		if (gameState.Insurance) {
			buf +=
				"\n\nInsurance Lost\n\nSince you cannot pay your insurance while you're in prison, it is retracted.";
			gameState.Insurance = false;
			gameState.NoClaim = 0;
		}

		if (gameState.Ship.crew[1] >= 0) {
			buf += "\n\nMercenaries Leave\n\nAny mercenaries who were traveling with you have left.";
			// "You can't pay your mercenaries while you are imprisoned, and so they have sought new employment."
			for (i = 1; i < GameState.MAXCREW; ++i) {
				gameState.Ship.crew[i] = -1;
			}
		}

		if (gameState.JaporiDiseaseStatus == 1) {
			buf +=
				"\n\nAntidote Taken\n\nThe Space Corps removed the antidote for Japori from your ship and delivered it, fulfilling your assignment.";
			gameState.JaporiDiseaseStatus = 2;
		}

		if (gameState.JarekStatus == 1) {
			buf +=
				"\n\nJarek Taken Home\n\nThe Space Corps decides to give ambassador Jarek a lift home to Devidia.";
			gameState.JarekStatus = 0;
		}

		if (gameState.WildStatus == 1) {
			buf += "\n\nWild Arrested\n\nJonathan Wild is arrested, and taken away to stand trial.";
			gameState.addNewsEvent(GameState.WILDARRESTED);
			gameState.WildStatus = 0;
		}

		if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			buf +=
				"\n\nPolice Confiscate Reator\n\nThe Police confiscate the Ion Reactor as evidence of your dealings with unsavory characters.";
			// "The bad news is that you've lost the Ion Reactor. The good news is that you no longer have to worry about managing its depleting fuel store."
			gameState.ReactorStatus = 0;
		}

		Arrival();
		IncDays(Imprisonment);

		if (gameState.Credits >= Fine) {
			gameState.Credits -= Fine;
		} else {
			gameState.Credits += gameState.CurrentShipPrice(true);

			if (gameState.Credits >= Fine) {
				gameState.Credits -= Fine;
			} else {
				gameState.Credits = 0;
			}

			buf +=
				"\n\nShip Sold\n\nBecause you don't have the credits to pay your fine, your ship is sold.";

			if (gameState.Ship.tribbles > 0) {
				buf += "\n\nTribbles sold\n\nThe tribbles were sold with your ship.";
				gameState.Ship.tribbles = 0;
			}

			buf +=
				"\n\nFlea Received\n\nWhen you leave prison, the police have left a second-hand Flea for you so you can continue your travels.";

			gameState.CreateFlea();
		}

		gameState.PoliceRecordScore = GameState.DUBIOUSSCORE;

		if (gameState.Debt > 0) {
			if (gameState.Credits >= gameState.Debt) {
				gameState.Credits -= gameState.Debt;
				gameState.Debt = 0;
			} else {
				gameState.Debt -= gameState.Credits;
				gameState.Credits = 0;
			}
		}

		for (i = 0; i < Imprisonment; ++i) {
			gameState.PayInterest();
		}

		Popup popup;
		popup = new Popup(this, "Arrested", buf, "", "OK", cbShowNextPopup);
		changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
		popupQueue.push(popup);
		showNextPopup();
	}

	public void ShuffleStatus() {
		int i;

		for (i = 0; i < GameState.MAXSOLARSYSTEM; ++i) {
			if (gameState.SolarSystem[i].status > 0) {
				if (gameState.GetRandom(100) < 15) {
					gameState.SolarSystem[i].status = GameState.UNEVENTFUL;
				}
			} else if (gameState.GetRandom(100) < 15) {
				gameState.SolarSystem[i].status = 1 + gameState.GetRandom(GameState.MAXSTATUS - 1);
			}
		}
	}

	public void ChangeQuantities() {
		int i, j;

		for (i = 0; i < GameState.MAXSOLARSYSTEM; ++i) {
			if (gameState.SolarSystem[i].countDown > 0) {
				--gameState.SolarSystem[i].countDown;
				if (gameState.SolarSystem[i].countDown > GameState.CountDown) {
					gameState.SolarSystem[i].countDown = GameState.CountDown;
				} else if (gameState.SolarSystem[i].countDown <= 0) {
					gameState.SolarSystem[i].initializeTradeitems();
				} else {
					for (j = 0; j < GameState.MAXTRADEITEM; ++j) {
						if (((j == GameState.NARCOTICS) && (!Politics.mPolitics[gameState.SolarSystem[i].politics].drugsOK)) ||
							((j == GameState.FIREARMS) && (!Politics.mPolitics[gameState.SolarSystem[i].politics].firearmsOK)) ||
							(gameState.SolarSystem[i].techLevel < Tradeitems.mTradeitems[j].techProduction)) {
							gameState.SolarSystem[i].qty[j] = 0;
						} else {
							gameState.SolarSystem[i].qty[j] =
								gameState.SolarSystem[i].qty[j] + gameState.GetRandom(5) - gameState.GetRandom(5);
							if (gameState.SolarSystem[i].qty[j] < 0) {
								gameState.SolarSystem[i].qty[j] = 0;
							}
						}
					}
				}
			}
		}
	}

	public boolean ExecuteAction(final Boolean CommanderFlees) {
		// *************************************************************************
		// A fight round
		// Return value indicates whether fight continues into another round
		// *************************************************************************
		Boolean CommanderGotHit, OpponentGotHit;
		long OpponentHull, ShipHull;
		int i;
		int PrevEncounterType;
		Ship Ship = gameState.Ship;
		Ship Opponent = gameState.Opponent;
		Popup popup;

		CommanderGotHit = false;
		OpponentHull = Opponent.hull;
		ShipHull = Ship.hull;

		// Fire shots
		if (gameState.EncounterType == GameState.PIRATEATTACK || gameState.EncounterType == GameState.POLICEATTACK ||
			gameState.EncounterType == GameState.TRADERATTACK || gameState.EncounterType == GameState.SPACEMONSTERATTACK ||
			gameState.EncounterType == GameState.DRAGONFLYATTACK || gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER ||
			gameState.EncounterType == GameState.SCARABATTACK || gameState.EncounterType == GameState.FAMOUSCAPATTACK) {
			CommanderGotHit = ExecuteAttack(Opponent, Ship, CommanderFlees, true);
		}

		OpponentGotHit = false;

		if (!CommanderFlees) {
			if (gameState.EncounterType == GameState.POLICEFLEE || gameState.EncounterType == GameState.TRADERFLEE || gameState.EncounterType == GameState.PIRATEFLEE) {
				OpponentGotHit = ExecuteAttack(Ship, Opponent, true, false);
			} else {
				OpponentGotHit = ExecuteAttack(Ship, Opponent, false, false);
			}
		}

		if (CommanderGotHit) {
			((FragmentEncounter) currentFragment).playerShipNeedsUpdate = true;
			Bitmap tribble = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.tribble);
			ViewGroup container = (ViewGroup) findViewById(R.id.container);
			for (i = 0; i <= GameState.TRIBBLESONSCREEN; ++i) {
				int resID = mContext.getResources().getIdentifier("tribbleButton" + String.valueOf(i), "id",
					mContext.getPackageName());
				ImageView imageView = (ImageView) container.findViewById(resID);
				if (imageView == null) {
					continue;
				}
				//noinspection ConstantConditions
				ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(
					imageView.getLayoutParams());
				marginParams.setMargins(gameState.GetRandom(container.getWidth() - tribble.getWidth()),
					gameState.GetRandom(container.getHeight() - tribble.getHeight()), 0, 0);
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
				imageView.setLayoutParams(layoutParams);
			}
		}
		if (OpponentGotHit) {
			((FragmentEncounter) currentFragment).opponentShipNeedsUpdate = true;
		}

		// Determine whether someone gets destroyed
		if (Ship.hull <= 0 && Opponent.hull <= 0) {
			gameState.AutoAttack = false;
			gameState.AutoFlee = false;

			if (gameState.EscapePod) {
				EscapeWithPod();
			} else {
				popup = new Popup(this, "Both Destroyed",
					"You and your opponent have managed to destroy each other.", "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				btnDestroyed();
			}
			return false;
		} else if (Opponent.hull <= 0) {
			gameState.AutoAttack = false;
			gameState.AutoFlee = false;

			if (gameState.ENCOUNTERPIRATE(
				gameState.EncounterType) && Opponent.type != GameState.MANTISTYPE && gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE) {
				popup = new Popup(this, "Bounty received", String.format("You earned a bounty of %d cr.",
					GetBounty(Opponent)), "", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			} else {
				popup = new Popup(this, "You win", "You have destroyed your opponent.", "", "OK",
					cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
			}
			if (gameState.ENCOUNTERPOLICE(gameState.EncounterType)) {
				++gameState.PoliceKills;
				gameState.PoliceRecordScore += GameState.KILLPOLICESCORE;
			} else if (gameState.ENCOUNTERFAMOUS(gameState.EncounterType)) {
				if (gameState.ReputationScore < GameState.DANGEROUSREP) {
					gameState.ReputationScore = GameState.DANGEROUSREP;
				} else {
					gameState.ReputationScore += 100;
				}
				// bump news flag from attacked to ship destroyed
				gameState.replaceNewsEvent(gameState.latestNewsEvent(), gameState.latestNewsEvent() + 10);
			} else if (gameState.ENCOUNTERPIRATE(gameState.EncounterType)) {
				if (Opponent.type != GameState.MANTISTYPE) {
					gameState.Credits += GetBounty(Opponent);
					gameState.PoliceRecordScore += GameState.KILLPIRATESCORE;
					Scoop();
				}
				++gameState.PirateKills;
			} else if (gameState.ENCOUNTERTRADER(gameState.EncounterType)) {
				++gameState.TraderKills;
				gameState.PoliceRecordScore += GameState.KILLTRADERSCORE;
				Scoop();
			} else if (gameState.ENCOUNTERMONSTER(gameState.EncounterType)) {
				++gameState.PirateKills;
				gameState.PoliceRecordScore += GameState.KILLPIRATESCORE;
				gameState.MonsterStatus = 2;
			} else if (gameState.ENCOUNTERDRAGONFLY(gameState.EncounterType)) {
				++gameState.PirateKills;
				gameState.PoliceRecordScore += GameState.KILLPIRATESCORE;
				gameState.DragonflyStatus = 5;
			} else if (gameState.ENCOUNTERSCARAB(gameState.EncounterType)) {
				++gameState.PirateKills;
				gameState.PoliceRecordScore += GameState.KILLPIRATESCORE;
				gameState.ScarabStatus = 2;
			}
			gameState.ReputationScore += 1 + (Opponent.type >> 1);
			return (false);
		} else if (Ship.hull <= 0) {
			gameState.AutoAttack = false;
			gameState.AutoFlee = false;

			if (gameState.EscapePod) {
				EscapeWithPod();
			} else {
				popup = new Popup(this, "You Lose", "Your ship has been destroyed by your opponent.", "",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				btnDestroyed();
			}
			return (false);
		}

		// Determine whether someone gets away.
		if (CommanderFlees) {
			if (GameState.getDifficulty() == GameState.BEGINNER) {
				gameState.AutoAttack = false;
				gameState.AutoFlee = false;

				popup = new Popup(this, "Escaped", "You have managed to escape your opponent.",
					"Just because this is Beginner level.", "OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				if (gameState.ENCOUNTERMONSTER(gameState.EncounterType)) {
					gameState.MonsterHull = Opponent.hull;
				}

				return (false);
			} else if ((gameState.GetRandom(7) + (Ship.PilotSkill() / 3)) * 2 >= gameState.GetRandom(
				Opponent.PilotSkill()) * (2 + GameState.getDifficulty())) {
				gameState.AutoAttack = false;
				gameState.AutoFlee = false;
				if (CommanderGotHit) {
					popup = new Popup(this, "You Escaped", "You got hit, but still managed to escape.", "",
						"OK", cbShowNextPopup);
					popupQueue.push(popup);
					showNextPopup();
				} else {
					popup = new Popup(this, "Escaped", "You have managed to escape your opponent.", "", "OK",
						cbShowNextPopup);
					popupQueue.push(popup);
					showNextPopup();
				}
				if (gameState.ENCOUNTERMONSTER(gameState.EncounterType)) {
					gameState.MonsterHull = Opponent.hull;
				}

				return (false);
			}
		} else if (gameState.EncounterType == GameState.POLICEFLEE || gameState.EncounterType == GameState.TRADERFLEE ||
			gameState.EncounterType == GameState.PIRATEFLEE || gameState.EncounterType == GameState.TRADERSURRENDER ||
			gameState.EncounterType == GameState.PIRATESURRENDER) {
			if (gameState.GetRandom(Ship.PilotSkill()) * 4 <= gameState.GetRandom(
				(7 + (Opponent.PilotSkill() / 3))) * 2) {
				gameState.AutoAttack = false;
				gameState.AutoFlee = false;
				popup = new Popup(this, "Opponent Escaped", "Your opponent has managed to escape.", "",
					"OK", cbShowNextPopup);
				popupQueue.push(popup);
				showNextPopup();
				return (false);
			}
		}

		// Determine whether the opponent's actions must be changed
		PrevEncounterType = gameState.EncounterType;

		if (Opponent.hull < OpponentHull) {
			if (gameState.ENCOUNTERPOLICE(gameState.EncounterType)) {
				if (Opponent.hull < OpponentHull >> 1) {
					if (Ship.hull < ShipHull >> 1) {
						if (gameState.GetRandom(10) > 5) {
							gameState.EncounterType = GameState.POLICEFLEE;
						}
					} else {
						gameState.EncounterType = GameState.POLICEFLEE;
					}
				}
			} else if (gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
				gameState.EncounterType = GameState.POLICEATTACK;
			} else if (gameState.ENCOUNTERPIRATE(gameState.EncounterType)) {
				if (Opponent.hull < (OpponentHull * 2) / 3) {
					if (Ship.hull < (ShipHull * 2) / 3) {
						if (gameState.GetRandom(10) > 3) {
							gameState.EncounterType = GameState.PIRATEFLEE;
						}
					} else {
						gameState.EncounterType = GameState.PIRATEFLEE;
						if (gameState.GetRandom(10) > 8 && Opponent.type < GameState.MAXSHIPTYPE) {
							gameState.EncounterType = GameState.PIRATESURRENDER;
						}
					}
				}
			} else if (gameState.ENCOUNTERTRADER(gameState.EncounterType)) {
				if (Opponent.hull < (OpponentHull * 2) / 3) {
					if (gameState.GetRandom(10) > 3) {
						gameState.EncounterType = GameState.TRADERSURRENDER;
					} else {
						gameState.EncounterType = GameState.TRADERFLEE;
					}
				} else if (Opponent.hull < (OpponentHull * 9) / 10) {
					if (Ship.hull < (ShipHull * 2) / 3) {
						// If you get damaged a lot, the trader tends to keep shooting
						if (gameState.GetRandom(10) > 7) {
							gameState.EncounterType = GameState.TRADERFLEE;
						}
					} else if (Ship.hull < (ShipHull * 9) / 10) {
						if (gameState.GetRandom(10) > 3) {
							gameState.EncounterType = GameState.TRADERFLEE;
						}
					} else {
						gameState.EncounterType = GameState.TRADERFLEE;
					}
				}
			}
		}

		if (PrevEncounterType != gameState.EncounterType) {
			if (!(gameState.AutoAttack && (gameState.EncounterType == GameState.TRADERFLEE || gameState.EncounterType == GameState.PIRATEFLEE || gameState.EncounterType == GameState.POLICEFLEE))) {
				gameState.AutoAttack = false;
			}
			gameState.AutoFlee = false;
		}

		((FragmentEncounter) currentFragment).playerShipNeedsUpdate = true;
		((FragmentEncounter) currentFragment).opponentShipNeedsUpdate = true;

		((FragmentEncounter) currentFragment).EncounterDisplayShips();
		((FragmentEncounter) currentFragment).EncounterButtons();

		String buf = "The ";
		String buf2 = "";
		if (gameState.ENCOUNTERPOLICE(PrevEncounterType)) {
			buf2 = "police ship";
		} else if (gameState.ENCOUNTERPIRATE(PrevEncounterType)) {
			if (Opponent.type == GameState.MANTISTYPE) {
				buf2 = "alien ship";
			} else {
				buf2 = "pirate ship";
			}
		} else if (gameState.ENCOUNTERTRADER(PrevEncounterType)) {
			buf2 = "trader ship";
		} else if (gameState.ENCOUNTERMONSTER(PrevEncounterType)) {
			buf2 = "monster";
		} else if (gameState.ENCOUNTERDRAGONFLY(PrevEncounterType)) {
			buf2 = "Dragonfly";
		} else if (gameState.ENCOUNTERSCARAB(PrevEncounterType)) {
			buf2 = "Scarab";
		} else if (gameState.ENCOUNTERFAMOUS(PrevEncounterType)) {
			buf2 = "Captain";
		}

		buf += buf2;
		if (CommanderGotHit) {
			buf += " hits you.";
		} else if (!(PrevEncounterType == GameState.POLICEFLEE ||
			PrevEncounterType == GameState.TRADERFLEE ||
			PrevEncounterType == GameState.PIRATEFLEE)) {
			buf += " missed you.";
		} else {
			buf = "";
		}

		if (OpponentGotHit) {
			buf += "\nYou hit the " + buf2 + ".";
		}

		if (!CommanderFlees && !OpponentGotHit) {
			buf += "\n" + "You missed the " + buf2 + ".";
		}

		if (PrevEncounterType == GameState.POLICEFLEE || PrevEncounterType == GameState.TRADERFLEE || PrevEncounterType == GameState.PIRATEFLEE) {
			buf += "\nThe " + buf2 + " didn't get away.";
		}

		if (CommanderFlees) {
			buf += "\nThe " + buf2 + " is still following you.";
		}

		((FragmentEncounter) currentFragment).EncounterDisplayNextAction(false);

		//noinspection ConstantConditions
		buf = ((FragmentEncounter) currentFragment).EncounterText.getText().toString() + "\n" + buf;
		((FragmentEncounter) currentFragment).EncounterText.setText(buf);

		if (gameState.Continuous && (gameState.AutoAttack || gameState.AutoFlee)) {
			// Make sure there's always just one delayRunnable queued.
			// Otherwise several are queued if player keeps tapping Attack/Flee buttons.
			delayHandler.removeCallbacksAndMessages(null);
			gameState.CommanderFlees = CommanderFlees;
			delayHandler.postDelayed(delayRunnable, 1000);
		}
		return true;
	}

	@SuppressWarnings("ConstantConditions")
	public boolean ExecuteAttack(Ship Attacker, Ship Defender, boolean Flees, boolean CommanderUnderAttack) {
		// *************************************************************************
		// An attack: Attacker attacks Defender, Flees indicates if Defender is fleeing
		// *************************************************************************
		int Damage, prevDamage;
		int i;

		// On beginner level, if you flee, you will escape unharmed.
		if (GameState.getDifficulty() == GameState.BEGINNER && CommanderUnderAttack && Flees) {
			return false;
		}

		// Fighterskill attacker is pitted against pilotskill defender; if defender
		// is fleeing the attacker has a free shot, but the chance to hit is smaller
		if (gameState.GetRandom(Attacker.FighterSkill() + Defender.getType().size) < (Flees ? 2 :
			1) * gameState.GetRandom(5 + (Defender.PilotSkill() >> 1)))
		// Misses
		{
			return false;
		}

		if (Attacker.TotalWeapons(-1, -1) <= 0) {
			Damage = 0;
		} else if (Defender.type == GameState.SCARABTYPE) {
			if (Attacker.TotalWeapons(GameState.PULSELASERWEAPON,
				GameState.PULSELASERWEAPON) <= 0 && Attacker.TotalWeapons(GameState.MORGANLASERWEAPON,
				GameState.MORGANLASERWEAPON) <= 0) {
				Damage = 0;
			} else {
				Damage = gameState.GetRandom(((Attacker.TotalWeapons(GameState.PULSELASERWEAPON,
					GameState.PULSELASERWEAPON) + Attacker.TotalWeapons(GameState.MORGANLASERWEAPON,
					GameState.MORGANLASERWEAPON)) * (100 + 2 * Attacker.EngineerSkill()) / 100));
			}
		} else {
			Damage = gameState.GetRandom((Attacker.TotalWeapons(-1, -1) * (100 + 2 * Attacker
				.EngineerSkill()) / 100));
		}

		if (Damage <= 0L) {
			return false;
		}

		// Reactor on board -- damage is boosted!
		if (CommanderUnderAttack && gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			if (GameState.getDifficulty() < GameState.NORMAL) {
				Damage *= 1 + (GameState.getDifficulty() + 1) * 0.25;
			} else {
				Damage *= 1 + (GameState.getDifficulty() + 1) * 0.33;
			}
		}

		// First, shields are depleted
		for (i = 0; i < GameState.MAXSHIELD; ++i) {
			if (Defender.shield[i] < 0) {
				break;
			}
			if (Damage <= Defender.shieldStrength[i]) {
				Defender.shieldStrength[i] -= Damage;
				Damage = 0;
				break;
			}
			Damage -= Defender.shieldStrength[i];
			Defender.shieldStrength[i] = 0;
		}

		prevDamage = Damage;

		// If there still is damage after the shields have been depleted,
		// this is subtracted from the hull, modified by the engineering skill
		// of the defender.
		if (Damage > 0) {
			Damage -= gameState.GetRandom(Defender.EngineerSkill());
			if (Damage <= 0) {
				Damage = 1;
			}
			// At least 2 shots on Normal level are needed to destroy the hull
			// (3 on Easy, 4 on Beginner, 1 on Hard or Impossible). For opponents,
			// it is always 2.
			if (CommanderUnderAttack && gameState.ScarabStatus == 3) {
				Damage = Math.min(Damage,
					(gameState.Ship.GetHullStrength() / (CommanderUnderAttack ? Math.max(1,
						(GameState.IMPOSSIBLE - GameState.getDifficulty())) : 2))
				);
			} else {
				Damage = Math.min(Damage,
					(Defender.getType().hullStrength / (CommanderUnderAttack ? Math.max(1,
						(GameState.IMPOSSIBLE - GameState.getDifficulty())) : 2))
				);
			}
			Defender.hull -= Damage;
			if (Defender.hull < 0) {
				Defender.hull = 0;
			}
		}

		if (Damage != prevDamage) {
			if (CommanderUnderAttack) {
				((FragmentEncounter) currentFragment).playerShipNeedsUpdate = true;
			} else {
				((FragmentEncounter) currentFragment).opponentShipNeedsUpdate = true;
			}
		}

		return true;
	}

	public int GetBounty(Ship sh) {
		// *************************************************************************
		// calculate bounty
		// *************************************************************************
		int bounty = EnemyShipPrice(sh);

		bounty /= 200;
		bounty /= 25;
		bounty *= 25;
		if (bounty <= 0) {
			bounty = 25;
		}
		if (bounty > 2500) {
			bounty = 2500;
		}

		return bounty;
	}

	public int EnemyShipPrice(Ship Sh) {
		int i;
		int CurPrice;

		CurPrice = Sh.getType().price;
		for (i = 0; i < GameState.MAXWEAPON; ++i) {
			if (Sh.weapon[i] >= 0) {
				CurPrice += Weapons.mWeapons[Sh.weapon[i]].price;
			}
		}
		for (i = 0; i < GameState.MAXSHIELD; ++i) {
			if (Sh.shield[i] >= 0) {
				CurPrice += Shields.mShields[Sh.shield[i]].price;
			}
		}
		// Gadgets aren't counted in the price, because they are already taken into account in
		// the skill adjustment of the price.

		CurPrice = CurPrice * (2 * Sh.PilotSkill() + Sh.EngineerSkill() + 3 * Sh.FighterSkill()) / 60;

		return CurPrice;
	}

	void EscapeWithPod() {
		// *************************************************************************
		// Your escape pod ejects you
		// *************************************************************************
		Popup popup;
		gameState.AutoAttack = gameState.AutoFlee = false;

		popup = new Popup(this, "Escape Pod activated",
			"Just before the final demise of your ship, your escape pod gets activated and ejects you. After a few days, the Space Corps picks you up and drops you off at a nearby space port.",
			"", "OK", cbShowNextPopup);
		popupQueue.push(popup);
		showNextPopup();

		if (gameState.ScarabStatus == 3) {
			gameState.ScarabStatus = 0;
		}

		Arrival();

		if (gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21) {
			popup = new Popup(this, "Reactor Destroyed",
				"The destruction of your ship was made much more spectacular by the added explosion of the Ion Reactor.",
				"", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.ReactorStatus = 0;
		}

		if (gameState.JaporiDiseaseStatus == 1) {
			popup = new Popup(this, "Antidote destroyed",
				"The antidote for the Japori system has been destroyed with your ship. You should get some more.",
				"The antidote for the Japori system was destroyed with your ship. But they probably have some new antidote in the system where you originally got it.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.JaporiDiseaseStatus = 0;
		}

		if (gameState.ArtifactOnBoard) {
			popup = new Popup(this, "Artifact Lost",
				"The alien artifact has been lost in the wreckage of your ship.",
				"You couldn't take the artifact with you in the escape pod, so now it's lost in the wreckage. The aliens will probably pick it up there.",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.ArtifactOnBoard = false;
		}

		if (gameState.JarekStatus == 1) {
			popup = new Popup(this, "Jarek Taken Home",
				"The Space Corps decides to give ambassador Jarek a lift home to Devidia.", "", "OK",
				cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.JarekStatus = 0;
		}

		if (gameState.WildStatus == 1) {
			popup = new Popup(this, "Wild Arrested",
				"Jonathan Wild is arrested, and taken away to stand trial.", "", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.PoliceRecordScore += GameState.CAUGHTWITHWILDSCORE;
			gameState.addNewsEvent(GameState.WILDARRESTED);
			gameState.WildStatus = 0;
		}

		if (gameState.Ship.tribbles > 0) {
			popup = new Popup(this, "Tribbles killed", "Your tribbles all died in the explosion.",
				"Don't be too sad. They were incredibly annoying, weren't they?", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.Ship.tribbles = 0;
		}

		if (gameState.Insurance) {
			popup = new Popup(this, "Insurance",
				"Since your ship was insured, the bank pays you the total worth of the destroyed ship.", "",
				"OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			gameState.Credits += gameState.CurrentShipPriceWithoutCargo(true);
		}

		popup = new Popup(this, "Flea built",
			"In 3 days and with 500 credits, you manage to convert your pod into a Flea.",
			"Your ship has been destroyed, but luckily, you are clever enough to convert your pod into a Flea type of ship, so you can continue your journey, or trade it in for a better ship.",
			"OK", cbShowNextPopup);
		popupQueue.push(popup);
		showNextPopup();

		if (gameState.Credits > 500) {
			gameState.Credits -= 500;
		} else {
			gameState.Debt += (500 - gameState.Credits);
			gameState.Credits = 0;
		}

		IncDays(3);

		gameState.CreateFlea();

		gameState.AutoAttack = gameState.AutoFlee = false;
		changeFragment(FRAGMENTS.SYSTEM_INFORMATION);
	}

	public void Scoop() {
		// *************************************************************************
		// You can pick up canisters left by a destroyed ship
		// *************************************************************************
		Popup popup;
		int d;

		// Chance 50% to pick something up on Normal level, 33% on Hard level, 25% on
		// Impossible level, and 100% on Easy or Beginner
		if (GameState.getDifficulty() >= GameState.NORMAL) {
			if (gameState.GetRandom(GameState.getDifficulty()) != 1) {
				return;
			}
		}

		// More chance to pick up a cheap good
		d = gameState.GetRandom(GameState.MAXTRADEITEM);
		if (d >= 5) {
			d = gameState.GetRandom(GameState.MAXTRADEITEM);
		}

		final int item = d;
		popup = new Popup(this, "Scoop Canister", String.format(
			"A canister from the destroyed ship, labeled %s, drifts within range of your scoops.",
			Tradeitems.mTradeitems[d].name), "", "Pick up", "Let go", new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				if (gameState.Ship.FilledCargoBays() >= gameState.Ship.TotalCargoBays()) {
					Popup popup1;
					popup1 = new Popup(popup.context, "No Room To Scoop",
						"You don't have any room in your cargo holds. Do you wish to jettison goods to make room, or just let it go?",
						"", "Make room", "Let go", new Popup.buttonCallback() {
						@Override
						public void execute(Popup popup, View view) {
							for (int i = 0; i < GameState.MAXTRADEITEM; i++) {
								gameState.Opponent.cargo[i] = 0;
							}
							gameState.Opponent.cargo[item] = 1;
							changeFragment(FRAGMENTS.PLUNDER); // Travel() is called from PlunderDoneButton
						}
					}, cbShowNextPopup
					);
					popupQueue.push(popup1);
					showNextPopup();
					return;
				}

				if (gameState.Ship.FilledCargoBays() < gameState.Ship.TotalCargoBays()) {
					++gameState.Ship.cargo[item];
				}
			}
		}, cbShowNextPopup);
		popupQueue.push(popup);
		showNextPopup();
	}

	public void Arrival() {
		gameState.Clicks = 0;
		gameState.Mercenary[0].curSystem = gameState.WarpSystem;
		ShuffleStatus();
		ChangeQuantities();

		gameState.DeterminePrices(gameState.WarpSystem);
		gameState.AlreadyPaidForNewspaper = false;

		if (gameState.SaveOnArrival) {
			saveGame();
		}

		/*
		SharedPreferences settings = getSharedPreferences("spacetrader", MODE_PRIVATE);
		final boolean hideAds = settings.getBoolean("hideAds", false);

		if (!hideAds) {
			boolean isOnline = false;
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnected()) {
				isOnline = true;
			}
			if (isOnline && (gameState.Days % 10 == 0)) { // Show an ad every 10 days
				interstitial = new InterstitialAd(this);
				interstitial.setAdUnitId("ca-app-pub-2751649723763471/1614767347");

				AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
					.build();

				interstitial.loadAd(adRequest);

				final AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("Please wait")
					.setCancelable(false).create();
				alertDialog.show();
				final Handler waiter = new Handler();
				final Runnable runnable = new Runnable() {
					int count = 0;

					@Override
					public void run() {
						if (interstitial.isLoaded()) {
							alertDialog.dismiss();
							interstitial.show();
						} else {
							this.count++;
							if (this.count < 500) {
								waiter.postDelayed(this, 100);
							} else {
								alertDialog.dismiss();
							}
						}
					}
				};
				waiter.postDelayed(runnable, 100);
			}
		}
		*/
	}

	public void BuyCargo(int Index, int Amount) {
		// *************************************************************************
		// Buy amount of cargo
		// *************************************************************************
		int ToBuy;
		SolarSystem CURSYSTEM = gameState.SolarSystem[gameState.Mercenary[0].curSystem];

		ToBuy = Math.min(Amount, CURSYSTEM.qty[Index]);
		ToBuy = Math.min(ToBuy,
			gameState.Ship.TotalCargoBays() - gameState.Ship.FilledCargoBays() - gameState.LeaveEmpty);
		ToBuy = Math.min(ToBuy, gameState.ToSpend() / gameState.BuyPrice[Index]);

		gameState.Ship.cargo[Index] += ToBuy;
		gameState.Credits -= ToBuy * gameState.BuyPrice[Index];
		gameState.BuyingPrice[Index] += ToBuy * gameState.BuyPrice[Index];
		CURSYSTEM.qty[Index] -= ToBuy;
	}

	public void SellCargo(final int Index, int Amount, int Operation) {
		// *************************************************************************
		// Sell or Jettison amount of cargo
		// Operation is SELLCARGO, DUMPCARGO, or JETTISONCARGO
		// *************************************************************************
		int ToSell;
		Ship Ship = gameState.Ship;
		Popup popup;

		if (Ship.cargo[Index] <= 0) {
			if (Operation == GameState.SELLCARGO) {
				popup = new Popup(this, "None To Sell", "You have none of these goods in your cargo bays.",
					"", "OK", cbShowNextPopup);
			} else {
				popup = new Popup(this, "None To Dump", "You have none of these goods in your cargo bays.",
					"", "OK", cbShowNextPopup);
			}
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		if (gameState.SellPrice[Index] <= 0 && Operation == GameState.SELLCARGO) {
			popup = new Popup(this, "Not Interested",
				"Nobody in this system is interested in buying these goods.", "", "OK", cbShowNextPopup);
			popupQueue.push(popup);
			showNextPopup();
			return;
		}

		ToSell = Math.min(Amount, Ship.cargo[Index]);
		final int ToJettison = ToSell;

		if (Operation == GameState.JETTISONCARGO) {
			if (gameState.PoliceRecordScore > GameState.DUBIOUSSCORE && !gameState.LitterWarning) {
				gameState.LitterWarning = true;
				popup = new Popup(this, "Space Littering",
					"Dumping cargo in space is considered littering. If the police finds your dumped goods and tracks them to you, this will influence your record. Do you really wish to dump?",
					"Space litterers will at least be considered dubious. If you are already a dubious character, space littering will only add to your list of offences.",
					"Yes", "No", new Popup.buttonCallback() {
					@Override
					public void execute(Popup popup, View view) {
						gameState.Ship.cargo[Index] -= ToJettison;
						if (gameState.GetRandom(10) < GameState.getDifficulty() + 1) {
							if (gameState.PoliceRecordScore > GameState.DUBIOUSSCORE) {
								gameState.PoliceRecordScore = GameState.DUBIOUSSCORE;
							} else {
								--gameState.PoliceRecordScore;
							}
							gameState.addNewsEvent(GameState.CAUGHTLITTERING);

							gameState.Ship.cargo[Index] -= ToJettison;
							if (gameState.GetRandom(10) < GameState.getDifficulty() + 1) {
								if (gameState.PoliceRecordScore > GameState.DUBIOUSSCORE) {
									gameState.PoliceRecordScore = GameState.DUBIOUSSCORE;
								} else {
									--gameState.PoliceRecordScore;
								}
								gameState.addNewsEvent(GameState.CAUGHTLITTERING);
							}
							changeFragment(gameState.currentState);
						}
					}
				}, cbShowNextPopup
				);
				popupQueue.push(popup);
				showNextPopup();
				return;
			}
		}

		if (Operation == GameState.DUMPCARGO) {
			ToSell = Math.min(ToSell, gameState.ToSpend() / 5 * (GameState.getDifficulty() + 1));
		}

		gameState.BuyingPrice[Index] =
			(gameState.BuyingPrice[Index] * (Ship.cargo[Index] - ToSell)) / Ship.cargo[Index];
		Ship.cargo[Index] -= ToSell;
		if (Operation == GameState.SELLCARGO) {
			gameState.Credits += ToSell * gameState.SellPrice[Index];
		}
		if (Operation == GameState.DUMPCARGO) {
			gameState.Credits -= ToSell * 5 * (GameState.getDifficulty() + 1);
		}
	}

	public void saveGame() {
		if (!GameState.isValid) {
			return;
		}
		SaveGame_v120 sv120 = new SaveGame_v120(gameState);

		String state = Environment.getExternalStorageState();
		if (!Environment.MEDIA_MOUNTED.equals(state)) {
			Toast.makeText(this, "Cannot save! No medium found!", Toast.LENGTH_LONG).show();
			return;
		}

		FileOutputStream fos = null;
		File f;
		try {
			File path = new File(Environment.getExternalStorageDirectory().toString() + "/SpaceTrader");
			//noinspection ResultOfMethodCallIgnored
			path.mkdirs();
			f = new File(path, "savegame.txt");
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (fos != null) {
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(fos);
				oos.writeObject(sv120);
				oos.close();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		SharedPreferences sp = getSharedPreferences("options", MODE_PRIVATE);
		SharedPreferences.Editor ed = sp.edit();

		ed.putInt("Shortcut1", gameState.Shortcut1);
		ed.putInt("Shortcut2", gameState.Shortcut2);
		ed.putInt("Shortcut3", gameState.Shortcut3);
		ed.putInt("Shortcut4", gameState.Shortcut4);

		ed.putBoolean("AlwaysIgnorePolice", gameState.AlwaysIgnorePolice);
		ed.putBoolean("AlwaysIgnorePirates", gameState.AlwaysIgnorePirates);
		ed.putBoolean("AlwaysIgnoreTraders", gameState.AlwaysIgnoreTraders);
		ed.putBoolean("AlwaysIgnoreTradeInOrbit", gameState.AlwaysIgnoreTradeInOrbit);
		ed.putBoolean("AutoFuel", gameState.AutoFuel);
		ed.putBoolean("AutoRepair", gameState.AutoRepair);
		ed.putBoolean("AlwaysInfo", gameState.AlwaysInfo);
		ed.putBoolean("ReserveMoney", gameState.ReserveMoney);
		ed.putBoolean("Continuous", gameState.Continuous);
		ed.putBoolean("AttackFleeing", gameState.AttackFleeing);
		ed.putBoolean("AutoPayNewspaper", gameState.NewsAutoPay);
		ed.putBoolean("RemindLoans", gameState.RemindLoans);
		ed.putBoolean("SaveOnArrival", gameState.SaveOnArrival);

		ed.commit();
	}

	public void btnDestroyed() {
		mContext.deleteFile("savegame.txt");
		File path = new File(Environment.getExternalStorageDirectory().toString() + "/SpaceTrader");
		File f = new File(path, "savegame.txt");
		//noinspection ResultOfMethodCallIgnored
		f.delete();
		GameState.isValid = false;
		EndOfGame(GameState.KILLED);
	}

	int GetScore(int EndStatus, int Days, int Worth, int Level) {
		int d;

		Worth = (Worth < 1000000 ? Worth : 1000000 + ((Worth - 1000000) / 10));

		if (EndStatus == GameState.KILLED) {
			return (Level + 1) * ((Worth * 90) / 50000);
		} else if (EndStatus == GameState.RETIRED) {
			return (Level + 1) * ((Worth * 95) / 50000);
		} else {
			d = ((Level + 1) * 100) - Days;
			if (d < 0) {
				d = 0;
			}
			return (Level + 1) * ((Worth + (d * 1000)) / 500);
		}
	}

	void EndOfGame(final int EndStatus) {
		int i, j;
		Boolean Scored;
		final long score;
		long l;
		HighScore[] Hscores = new HighScore[GameState.MAXHIGHSCORE];
		Popup popup;

		Scored = false;
		score = GetScore(EndStatus, gameState.Days, gameState.CurrentWorth(),
			GameState.getDifficulty());

		for (i = 0; i < GameState.MAXHIGHSCORE; i++) {
			Hscores[i] = new HighScore(getApplicationContext(), i);
		}

		for (i = 0; i < GameState.MAXHIGHSCORE; i++) {
			l = GetScore(Hscores[i].getStatus(), Hscores[i].getDays(), Hscores[i].getWorth(),
				Hscores[i].getDifficulty());

			if ((score > l) || (score == l && gameState.CurrentWorth() > Hscores[i].getWorth()) ||
				(score == l && gameState.CurrentWorth() == Hscores[i].getWorth() &&
					gameState.Days > Hscores[i].getDays())) {
				Scored = true;

				if (!(gameState.GameLoaded) && gameState.CheatCounter < 3) {
					for (j = GameState.MAXHIGHSCORE - 1; j > i; --j) {
						Hscores[j].setName(Hscores[j - 1].getName());
						Hscores[j].setStatus(Hscores[j - 1].getStatus());
						Hscores[j].setDays(Hscores[j - 1].getDays());
						Hscores[j].setWorth(Hscores[j - 1].getWorth());
						Hscores[j].setDifficulty(Hscores[j - 1].getDifficulty());
					}

					Hscores[i].setName(gameState.NameCommander);
					Hscores[i].setStatus(EndStatus);
					Hscores[i].setDays(gameState.Days);
					Hscores[i].setWorth(gameState.CurrentWorth());
					Hscores[i].setDifficulty(GameState.getDifficulty());
				}
				break;
			}
		}

		String buf, buf2;
		if (Scored && gameState.GameLoaded) {
			buf = "Without loading a savegame, you";
			buf2 = "would have made the high-score list.";
		} else if (Scored && gameState.CheatCounter >= 3) {
			buf = "Cheat mode was active.";
			buf2 = "You are not added to the high-score list.";
		} else if (Scored) {
			buf = "Congratulations!";
			buf2 = "You have made the high-score list!";
		} else {
			buf = "Alas! This is not enough to enter";
			buf2 = "the high-score list.";
		}

		popup = new Popup(this, "Final score", String.format(
			"You achieved a score of %d.%d%%.\nAfter %d Days you %s.\n%s\n%s", (score / 50),
			((score % 50) / 5), gameState.Days, (EndStatus == GameState.KILLED ? "got killed" :
				(EndStatus == GameState.RETIRED ? "retired on a barren moon" :
					"retired on an utopian moon")), buf, buf2
		), "", "OK", "Share", cbShowNextPopup, new Popup.buttonCallback() {
			@Override
			public void execute(Popup popup, View view) {
				String subject = "Space Trader";
				String body = String.format(
					"I achieved a score of %d.%d%%. After %d Days I %s. @AndSpaceTrader", (score / 50),
					((score % 50) / 5), gameState.Days, (EndStatus == GameState.KILLED ? "got killed" :
						(EndStatus == GameState.RETIRED ? "retired on a barren moon" :
							"retired on an utopian moon"))
				);
				onShareClick(subject, body);
			}
		});
		popupQueue.push(popup);
		showNextPopup();

		if (Scored && !gameState.GameLoaded) {
			ViewHighScores();
		}

		GameState.isValid = false;
		changeFragment(FRAGMENTS.NEW_GAME);
	}

	public void onShareClick(String subject, String body) {
		Intent shareIntent = new Intent();
		shareIntent.setAction(Intent.ACTION_SEND);

		shareIntent.putExtra(Intent.EXTRA_TEXT, body);
		shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		shareIntent.setType("text/plain");

		startActivity(shareIntent);
	}

	void ViewHighScores() {
		int i;
		int Percentage;
		String msg = "";
		Popup popup;

		HighScore[] Hscores = new HighScore[GameState.MAXHIGHSCORE];
		for (i = 0; i < GameState.MAXHIGHSCORE; i++) {
			Hscores[i] = new HighScore(getApplicationContext(), i);
		}

		for (i = 0; i < GameState.MAXHIGHSCORE; ++i) {
			if (Hscores[i].getName().isEmpty()) {
				msg += "Empty\n\n\n\n";
				continue;
			}

			Percentage = GetScore(Hscores[i].getStatus(), Hscores[i].getDays(), Hscores[i].getWorth(),
				Hscores[i].getDifficulty());
			msg += String.format("%d. %-20s %3d.%d%%\n", i + 1, Hscores[i].getName(), (Percentage / 50),
				((Percentage % 50) / 5));

			if (Hscores[i].getStatus() == GameState.MOON) {
				msg += "Claimed moon";
			} else if (Hscores[i].getStatus() == GameState.RETIRED) {
				msg += "Retired";
			} else {
				msg += "Was killed";
			}
			msg += String.format(" in %d day%s, worth %d credits on %s level\n\n", Hscores[i].getDays(),
				Hscores[i].getDays() == 1 ? "" : "s", Hscores[i].getWorth(),
				levelDesc[Hscores[i].getDifficulty()]);
		}
		popup = new Popup(this, "Highscores", msg, "", "OK", cbShowNextPopup);
		popupQueue.push(popup);
		showNextPopup();
	}

	public static void hide_keyboard(Activity activity) {
		// https://stackoverflow.com/a/17789187
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
			INPUT_METHOD_SERVICE);
		//Find the currently focused view, so we can grab the correct window token from it.
		View view = activity.getCurrentFocus();
		//If no view currently has focus, create a new one, just so we can grab a window token from it
		if (view == null) {
			view = new View(activity);
		}
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
