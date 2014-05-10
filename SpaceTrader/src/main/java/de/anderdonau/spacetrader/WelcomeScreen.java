/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.SaveGame;
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

		/*
		mGameState = new GameState();
		*/
		setContentView(R.layout.activity_welcome_screen);
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new WelcomeScreenFragment()).commit();
		mCurrentState = "WelcomeScreen";

		//mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);

		// Set up the drawer.
		//mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		//mNavigationDrawerFragment.setMenuVisibility(false);
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
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		//FragmentManager fragmentManager = getFragmentManager();
		//fragmentManager.beginTransaction().replace(R.id.container, WelcomeScreenFragment.newInstance(position + 1)).commit();
	}
/*
	public void onSectionAttached(int number) {
		switch (number) {
			case 1:
				mTitle = getString(R.string.title_section1);
				break;
			case 2:
				mTitle = getString(R.string.title_section2);
				break;
			case 3:
				mTitle = getString(R.string.title_section3);
				break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setTitle(getString(R.string.app_name));
	}
*/

	public void btnLoadOrStartGame(View view) {
		if (foundSaveGame){
			FragmentManager fragmentManager = getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.container, new SystemInformationFragment()).commit();
			mCurrentState = "SystemInformation";
		} else {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, new StartNewGameFragment()).commit();
			mCurrentState = "StartNewGame";
		}
	}
	public void btnStartNewGame(View view){
		EditText t = (EditText) findViewById(R.id.strNameCommander);
		mGameState = new GameState(t.getText().toString());
		this.saveGame();
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, new SystemInformationFragment()).commit();
		mCurrentState = "SystemInformation";
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
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			// getMenuInflater().inflate(R.menu.welcome_screen, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
*/

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
			return rootView;
		}
	}

}
