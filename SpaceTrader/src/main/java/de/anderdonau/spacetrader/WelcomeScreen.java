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
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.SaveGame;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class WelcomeScreen extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	private NavigationDrawerFragment mNavigationDrawerFragment;

	private static String mCurrentState = "startup";
	private static Context mContext;
	static GameState mGameState;

	private static boolean foundSaveGame = false;

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
		switch (position){
			case 0: //"Buy Cargo"
				break;
			case 1: //"Sell Cargo"
				break;
			case 2: // "Shipyard"
				break;
			case 3: // "Buy Equipment"
				break;
			case 4: // "Sell Equipment"
				break;
			case 5: // "Personnel Roster"
				btnPersonnelRoster(null);
				break;
			case 6: // "Bank"
				break;
			case 7: // "System Information"
				btnSystemInformation(null);
				break;
			case 8: // "Commander Status"
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

	public void btnLoadOrStartGame(View view) {
		FragmentManager fragmentManager = getFragmentManager();
		if (foundSaveGame){
			btnSystemInformation(null);
			fragmentManager.beginTransaction().show(mNavigationDrawerFragment).commit();
		} else {
			fragmentManager.beginTransaction().replace(R.id.container, new StartNewGameFragment()).commit();
			mCurrentState = "StartNewGame";
		}
	}
	public void btnStartNewGame(View view){
		EditText t = (EditText) findViewById(R.id.strNameCommander);
		mGameState = new GameState(t.getText().toString());
		this.saveGame();
		btnSystemInformation(view);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().show(mNavigationDrawerFragment).commit();
	}
	public void btnSystemInformation(View view){
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new SystemInformationFragment()).commit();
		mCurrentState = "SystemInformation";
	}
	public void btnPersonnelRoster(View view){
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new PersonnelRosterFragment()).commit();
		mCurrentState = "PersonnelRoster";
	}

	public void saveGame(){
		SaveGame s = new SaveGame(mGameState);

		FileOutputStream fos = null;
		try {
			fos = mContext.openFileOutput("savegame.txt", Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (fos != null){
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

	@Override
	public boolean onPrepareOptionsMenu (Menu menu){
		try {
			menu.findItem(R.id.hotkey1).setTitle(mGameState.Shortcuts[mGameState.Shortcut1][0]);
			menu.findItem(R.id.hotkey2).setTitle(mGameState.Shortcuts[mGameState.Shortcut2][0]);
			menu.findItem(R.id.hotkey3).setTitle(mGameState.Shortcuts[mGameState.Shortcut3][0]);
			menu.findItem(R.id.hotkey4).setTitle(mGameState.Shortcuts[mGameState.Shortcut4][0]);
		} catch (Exception e){
			return false;
		}
		return true;
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mCurrentState.equals("WelcomeScreen") && !mCurrentState.equals("startup") && !mCurrentState.equals("StartNewGame")){
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.in_game, menu);

		}
		if (mNavigationDrawerFragment != null){
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
			if (foundSaveGame){
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
			SeekBar.OnSeekBarChangeListener levelChangeListener = new SeekBar.OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
					TextView textview = (TextView) rootView.findViewById(R.id.levelDescription);
					String[] levelDesc = new String[]{"Beginner", "Easy", "Normal", "Hard", "Impossible"};
					textview.setText(levelDesc[((SeekBar) rootView.findViewById(R.id.levelBar)).getProgress()]);
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
			textView.setText(mGameState.Activity[Politics.mPolitics[CURSYSTEM.politics].strengthPirates ]);
			textView = (TextView) rootView.findViewById(R.id.strCurrentPressure);
			textView.setText(mGameState.Status[CURSYSTEM.status]);

			Button btn = (Button) rootView.findViewById(R.id.btnSpecialEvent);
			if (CURSYSTEM.special > 0){
				btn.setVisibility(View.VISIBLE);
			} else {
				btn.setVisibility(View.INVISIBLE);
			}
			btn = (Button) rootView.findViewById(R.id.btnMercenaryForHire);
			if (mGameState.GetForHire() > -1){
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

			for (i=0; i<2; ++i) {
				if ((mGameState.JarekStatus == 1 || mGameState.WildStatus == 1) && i < 2){
					if (mGameState.JarekStatus == 1 && i == 0){ /* Jarek is always in 1st crew slot */
						tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew1);
						tv = (TextView) rootView.findViewById(R.id.txtNameCrew1);
						btn = (Button) rootView.findViewById(R.id.btnFireCrew1);
						tl.setVisibility(View.INVISIBLE);
						tv.setText("Jarek's quarters");
						btn.setVisibility(View.INVISIBLE);
						continue;
					} else if (mGameState.JarekStatus == 1 && mGameState.WildStatus == 1 && i == 1){ /* Wild is in 2nd crew slot if Jarek is here, too */
						tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew2);
						tv = (TextView) rootView.findViewById(R.id.txtNameCrew2);
						btn = (Button) rootView.findViewById(R.id.btnFireCrew2);
						tl.setVisibility(View.INVISIBLE);
						tv.setText("Wild's quarters");
						btn.setVisibility(View.INVISIBLE);
						continue;
					} else if (mGameState.WildStatus == 1 && i == 0){/* Wild is in 1st crew slot if Jarek is not here */
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
				}	else {
					tl = (TableLayout) rootView.findViewById(R.id.tableLayoutCrew2);
					tv = (TextView) rootView.findViewById(R.id.txtNameCrew2);
					btn = (Button) rootView.findViewById(R.id.btnFireCrew2);
				}
				ShipTypes.ShipType Ship = mGameState.ShipTypes.ShipTypes[mGameState.Ship.type];
				if (Ship.crewQuarters <= i+1) {
					tl.setVisibility(View.INVISIBLE);
					btn.setVisibility(View.INVISIBLE);
					tv.setText("No quarters available");
					continue;
				}

				if (mGameState.Ship.crew[i+1] < 0) {
					tl.setVisibility(View.INVISIBLE);
					btn.setVisibility(View.INVISIBLE);
					tv.setText("Vacancy");
					continue;
				}

				tl.setVisibility(View.VISIBLE);
				btn.setVisibility(View.VISIBLE);
				DrawMercenary(i, mGameState.Ship.crew[i+1]); /* Crew Idx 0 is the player */
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
		public void DrawMercenary(int i, int idxCrewMember){
			TextView txtPilot;
			TextView txtEngineer;
			TextView txtTrader;
			TextView txtFighter;
			TextView txtName;

			CrewMember c = mGameState.Mercenary[idxCrewMember];

			if (i == 0){
				txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrew1);
				txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrew1);
				txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrew1);
				txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrew1);
				txtName = (TextView) rootView.findViewById(R.id.txtNameCrew1);
			} else if (i == 1){
				txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrew2);
				txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrew2);
				txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrew2);
				txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrew2);
				txtName = (TextView) rootView.findViewById(R.id.txtNameCrew2);
			} else /* if (i == 2) */{
				txtPilot = (TextView) rootView.findViewById(R.id.txtPilotCrewNew);
				txtEngineer = (TextView) rootView.findViewById(R.id.txtEngineerCrewNew);
				txtTrader = (TextView) rootView.findViewById(R.id.txtTraderCrewNew);
				txtFighter = (TextView) rootView.findViewById(R.id.txtFighterCrewNew);
				txtName = (TextView) rootView.findViewById(R.id.txtNameCrewNew);
			}
			txtPilot.setText(String.format("%d", c.pilot));
			txtFighter.setText(String.format("%d",c.fighter));
			txtEngineer.setText(String.format("%d",c.engineer));
			txtTrader.setText(String.format("%d",c.trader));
			txtName.setText(mGameState.MercenaryName[c.nameIndex]);
		}
	}
}
