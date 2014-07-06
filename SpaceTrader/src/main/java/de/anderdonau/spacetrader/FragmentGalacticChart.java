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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.Popup;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentGalacticChart extends MyFragment {
	View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//noinspection ConstantConditions
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		rootView = inflater.inflate(R.layout.fragment_galactic_chart, container, false);
		update();
		return rootView;
	}

	@Override
	public boolean update() {
		final NavigationChart navigationChart = (NavigationChart) rootView.findViewById(
			R.id.GalacticChart);
		Button button = (Button) rootView.findViewById(R.id.btnJump);
		button.setVisibility(gameState.CanSuperWarp ? View.VISIBLE : View.INVISIBLE);
		navigationChart.setGameState(gameState);
		navigationChart.setMain(main);
		navigationChart.setShortRange(false);

		TextView tv;
		if (gameState.WarpSystem < 0) {
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
			tv.setText(String.format("%s %s %s", main.SystemSize[s.size], main.techLevel[s.techLevel],
				Politics.mPolitics[s.politics].name));
			tv = (TextView) rootView.findViewById(R.id.galChartDistance);
			tv.setVisibility(View.VISIBLE);
			tv.setText(String.format("%d parsecs", gameState.RealDistance(
				gameState.SolarSystem[gameState.Mercenary[0].curSystem], s)));
			tv = (TextView) rootView.findViewById(R.id.galChartName);
			tv.setVisibility(View.VISIBLE);
			tv.setText(main.SolarSystemName[s.nameIndex]);
			navigationChart.mSelectedSystem = gameState.WarpSystem;
			navigationChart.mOffsetsDefined = false;
			navigationChart.invalidate();
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
							main.SolarSystemName[gameState.SolarSystem[gameState.Wormhole[wormhole]].nameIndex]));
						tv = (TextView) rootView.findViewById(R.id.galChartDistance);
						tv.setVisibility(View.INVISIBLE);
						tv = (TextView) rootView.findViewById(R.id.galChartName);
						tv.setVisibility(View.INVISIBLE);
					} else if (system >= 0) {
						TextView tv;
						gameState.WarpSystem = system;
						main.WarpSystem = gameState.SolarSystem[system];
						SolarSystem s = gameState.SolarSystem[gameState.WarpSystem];
						tv = (TextView) rootView.findViewById(R.id.galChartDetails);
						tv.setVisibility(View.VISIBLE);
						tv.setText(String.format("%s %s %s", main.SystemSize[s.size],
							main.techLevel[s.techLevel], Politics.mPolitics[s.politics].name));
						tv = (TextView) rootView.findViewById(R.id.galChartDistance);
						tv.setVisibility(View.VISIBLE);
						tv.setText(String.format("%d parsecs", gameState.RealDistance(
							gameState.SolarSystem[gameState.Mercenary[0].curSystem], s)));

						tv = (TextView) rootView.findViewById(R.id.galChartName);
						tv.setVisibility(View.VISIBLE);
						tv.setText(main.SolarSystemName[s.nameIndex]);
						if (system == navigationChart.mSelectedSystem) {
							Popup popup;

							if (system == gameState.TrackedSystem) {
								popup = new Popup(main, "Stop tracking system", String.format(
									"Do you want to stop tracking the %s system?",
									main.SolarSystemName[gameState.SolarSystem[system].nameIndex]), "", "Yes", "No",
									new Popup.buttonCallback() {
										@Override
										public void execute(Popup popup, View view) {
											gameState.TrackedSystem = -1;
											navigationChart.invalidate();
										}
									}, main.cbShowNextPopup
								);
							} else {
								popup = new Popup(main, "Track system",
									"Do you want to track the distance to " + main.SolarSystemName[gameState.SolarSystem[system].nameIndex] + "?",
									"", "Yes", "No", new Popup.buttonCallback() {
									@Override
									public void execute(Popup popup, View view) {
										gameState.TrackedSystem = system;
										navigationChart.invalidate();
									}
								}, main.cbShowNextPopup
								);
							}
							main.addPopup(popup);
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
		});
		return true;
	}
}
