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
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;

public class FragmentShortRangeChart extends MyFragment {
	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		final View rootView = inflater.inflate(R.layout.fragment_short_range_chart, container, false);
		final NavigationChart navigationChart = (NavigationChart) rootView.findViewById(
			R.id.ShortRangeChart);
		navigationChart.setGameState(gameState);
		navigationChart.setMain(main);
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
						main.WarpSystem = gameState.SolarSystem[system];
						if (!gameState.AlwaysInfo &&
							(gameState.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
								gameState.SolarSystem[system]) <= gameState.Ship.GetFuel() || gameState
								.WormholeExists(gameState.Mercenary[0].curSystem, system)) &&
							gameState.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
								gameState.SolarSystem[system]) > 0) {
							main.changeFragment(Main.FRAGMENTS.AVERAGE_PRICES);
						} else {
							main.changeFragment(Main.FRAGMENTS.WARP_SYSTEM_INFORMATION);
						}
					} else if (system >= 0) {
						gameState.WarpSystem = system;
						main.WarpSystem = gameState.SolarSystem[system];
						if (!gameState.AlwaysInfo &&
							(gameState.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
								gameState.SolarSystem[system]) <= gameState.Ship.GetFuel() || gameState
								.WormholeExists(gameState.Mercenary[0].curSystem, system)) &&
							gameState.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
								gameState.SolarSystem[system]) > 0) {
							main.changeFragment(Main.FRAGMENTS.AVERAGE_PRICES);

						} else {
							main.changeFragment(Main.FRAGMENTS.WARP_SYSTEM_INFORMATION);
						}
					} else {
						navigationChart.onTouchEvent(motionEvent);
					}
				}
				return false;
			}
		});
		TextView tv = (TextView) rootView.findViewById(R.id.txtShortRangeChartDistToTarget);
		if (gameState.TrackedSystem < 0) {
			tv.setVisibility(View.INVISIBLE);
		} else {
			tv.setVisibility(View.VISIBLE);
			tv.setText(String.format("Distance to %s: %d parsec",
				main.SolarSystemName[gameState.SolarSystem[gameState.TrackedSystem].nameIndex],
				gameState.RealDistance(gameState.SolarSystem[gameState.Mercenary[0].curSystem],
					gameState.SolarSystem[gameState.TrackedSystem])
			));
		}
		return rootView;
	}
}
