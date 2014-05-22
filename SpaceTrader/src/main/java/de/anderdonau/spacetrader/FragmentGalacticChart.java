/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan. 
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna. 
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus. 
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.Popup;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentGalacticChart extends Fragment {
	WelcomeScreen welcomeScreen;
	GameState gameState;

	public FragmentGalacticChart(WelcomeScreen welcomeScreen, GameState gameState) {
		this.welcomeScreen = welcomeScreen;
		this.gameState = gameState;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_galactic_chart, container, false);
		@SuppressWarnings("ConstantConditions") final NavigationChart navigationChart =
			(NavigationChart) rootView.findViewById(R.id.GalacticChart);
		Button button = (Button) rootView.findViewById(R.id.btnJump);
		button.setVisibility(gameState.CanSuperWarp ? View.VISIBLE : View.INVISIBLE);
		navigationChart.setGameState(gameState);
		navigationChart.setShortRange(false);

		TextView tv;
		if (gameState.WarpSystem <= 0) {
			tv = (TextView) rootView.findViewById(R.id.galChartDetails);
			tv.setVisibility(View.INVISIBLE);
			tv = (TextView) rootView.findViewById(R.id.galChartDistance);
			tv.setVisibility(View.INVISIBLE);
			tv = (TextView) rootView.findViewById(R.id.galChartName);
			tv.setVisibility(View.INVISIBLE);
		} else {
			SolarSystem s = gameState.SolarSystem[gameState.WarpSystem];
			tv = (TextView) rootView.findViewById(R.id.galChartDetails);
			tv.setVisibility(View.VISIBLE);
			tv.setText(String.format("%s %s %s", gameState.SystemSize[s.size],
			                         gameState.techLevel[s.techLevel],
			                         gameState.Politics.mPolitics[s.politics].name
			)
			);
			tv = (TextView) rootView.findViewById(R.id.galChartDistance);
			tv.setVisibility(View.VISIBLE);
			tv.setText(String.format("%d parsecs", gameState
				.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
				              s
				)
			)
			);
			tv = (TextView) rootView.findViewById(R.id.galChartName);
			tv.setVisibility(View.VISIBLE);
			tv.setText(gameState.SolarSystemName[s.nameIndex]);
			navigationChart.mSelectedSystem = gameState.WarpSystem;
		}
		navigationChart.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					final int system = navigationChart.getSystemAt(motionEvent.getX(), motionEvent.getY());
					int wormhole = navigationChart.getWormholeAt(motionEvent.getX(), motionEvent.getY());
					if (wormhole >= 0) {
						navigationChart.mDrawWormhole = wormhole;
						navigationChart.invalidate();
						TextView tv;
						tv = (TextView) rootView.findViewById(R.id.galChartDetails);
						tv.setVisibility(View.VISIBLE);
						tv.setText(String.format("Wormhole to %s",
						                         gameState.SolarSystemName[gameState.SolarSystem[gameState.Wormhole[wormhole]].nameIndex]
						)
						);
						tv = (TextView) rootView.findViewById(R.id.galChartDistance);
						tv.setVisibility(View.INVISIBLE);
						tv = (TextView) rootView.findViewById(R.id.galChartName);
						tv.setVisibility(View.INVISIBLE);
					} else if (system >= 0) {
						TextView tv;
						gameState.WarpSystem = system;
						welcomeScreen.WarpSystem = gameState.SolarSystem[system];
						SolarSystem s = gameState.SolarSystem[gameState.WarpSystem];
						tv = (TextView) rootView.findViewById(R.id.galChartDetails);
						tv.setVisibility(View.VISIBLE);
						tv.setText(String.format("%s %s %s", gameState.SystemSize[s.size],
						                         gameState.techLevel[s.techLevel],
						                         gameState.Politics.mPolitics[s.politics].name
						)
						);
						tv = (TextView) rootView.findViewById(R.id.galChartDistance);
						tv.setVisibility(View.VISIBLE);
						tv.setText(String.format("%d parsecs", gameState
							.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
							              s
							)
						)
						);

						tv = (TextView) rootView.findViewById(R.id.galChartName);
						tv.setVisibility(View.VISIBLE);
						tv.setText(gameState.SolarSystemName[s.nameIndex]);
						if (system == navigationChart.mSelectedSystem) {
							Popup popup;
							popup = new Popup(welcomeScreen, "Track system",
							                  "Do you want to track the distance to " + gameState.SolarSystemName[gameState.SolarSystem[system].nameIndex] + "?",
							                  "", "Yes", "No", new Popup.buttonCallback() {
								@Override
								public void execute(Popup popup, View view) {
									gameState.TrackedSystem = system;
									navigationChart.invalidate();
								}
							}, welcomeScreen.cbShowNextPopup
							);
							welcomeScreen.addPopup(popup);
						} else {
							navigationChart.mSelectedSystem = system;
							navigationChart.invalidate();
						}
					} else {
						navigationChart.onTouchEvent(motionEvent);
					}
				}
				return false;
			}
		}
		);
		return rootView;
	}
}
