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
import android.widget.TextView;

public class FragmentShortRangeChart extends Fragment {
	WelcomeScreen welcomeScreen;
	GameState gameState;

	public FragmentShortRangeChart(WelcomeScreen welcomeScreen, GameState gameState) {
		this.welcomeScreen = welcomeScreen;
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_short_range_chart, container, false);
		final NavigationChart navigationChart =
			(NavigationChart) rootView.findViewById(R.id.ShortRangeChart);
		navigationChart.setGameState(gameState);
		navigationChart.setShortRange(true);

		navigationChart.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				navigationChart.mDrawWormhole = -1;
				navigationChart.invalidate();
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					int system = navigationChart.getSystemAt(motionEvent.getX(), motionEvent.getY());
					int wormhole = navigationChart.getWormholeAt(motionEvent.getX(), motionEvent.getY());
					if (wormhole >= 0) {
						system = gameState.Wormhole[wormhole];
						gameState.WarpSystem = system;
						welcomeScreen.WarpSystem = gameState.SolarSystem[system];
						if (!gameState.AlwaysInfo &&
							(gameState
								.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
								              gameState.SolarSystem[system]
								) <= gameState.GetFuel() || gameState
								.WormholeExists(gameState.Mercenary[0].curSystem,
								                system
								)) &&
							gameState
								.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
								              gameState.SolarSystem[system]
								) > 0) {
							welcomeScreen.changeFragment(WelcomeScreen.FRAGMENTS.AVERAGE_PRICES);
						} else {
							welcomeScreen.changeFragment(WelcomeScreen.FRAGMENTS.WARP_SYSTEM_INFORMATION);
						}
					} else if (system >= 0) {
						gameState.WarpSystem = system;
						welcomeScreen.WarpSystem = gameState.SolarSystem[system];
						if (!gameState.AlwaysInfo &&
							(gameState
								.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
								              gameState.SolarSystem[system]
								) <= gameState.GetFuel() || gameState
								.WormholeExists(gameState.Mercenary[0].curSystem,
								                system
								)) &&
							gameState
								.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
								              gameState.SolarSystem[system]
								) > 0) {
							welcomeScreen.changeFragment(WelcomeScreen.FRAGMENTS.AVERAGE_PRICES);

						} else {
							welcomeScreen.changeFragment(WelcomeScreen.FRAGMENTS.WARP_SYSTEM_INFORMATION);
						}
					} else {
						navigationChart.onTouchEvent(motionEvent);
					}
				}
				return false;
			}
		}
		);
		TextView tv = (TextView) rootView.findViewById(R.id.txtShortRangeChartDistToTarget);
		if (gameState.TrackedSystem < 0) {
			tv.setVisibility(View.INVISIBLE);
		} else {
			tv.setVisibility(View.VISIBLE);
			tv.setText(String.format("Distance to %s: %d parsec",
			                         gameState.SolarSystemName[gameState.SolarSystem[gameState.TrackedSystem].nameIndex],
			                         gameState
				                         .RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
				                                       gameState.SolarSystem[gameState.TrackedSystem]
				                         )
			)
			);
		}
		return rootView;
	}
}
