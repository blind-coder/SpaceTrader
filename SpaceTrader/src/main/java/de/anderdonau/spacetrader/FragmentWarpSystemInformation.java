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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentWarpSystemInformation extends MyFragment {
	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		final View rootView = inflater.inflate(R.layout.fragment_remote_system_information, container,
			false);
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		TextView tv;

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoName);
		tv.setText(main.SolarSystemName[main.WarpSystem.nameIndex]);

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoTechLevel);
		tv.setText(main.techLevel[main.WarpSystem.techLevel]);
		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoGovernment);
		tv.setText(Politics.mPolitics[main.WarpSystem.politics].name);
		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoSize);
		tv.setText(main.SystemSize[main.WarpSystem.size]);
		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoPolice);
		tv.setText(main.Activity[Politics.mPolitics[main.WarpSystem.politics].strengthPolice]);
		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoPirates);
		tv.setText(main.Activity[Politics.mPolitics[main.WarpSystem.politics].strengthPirates]);

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoResources);
		tv.setText(main.WarpSystem.visited ? main.SpecialResources[main.WarpSystem.specialResources] :
			"Unknown");

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoDistance);
		int Distance = gameState.RealDistance(CURSYSTEM, main.WarpSystem);
		if (gameState.WormholeExists(COMMANDER.curSystem, main.WarpSystem)) {
			tv.setText("Wormhole");
		} else {
			tv.setText(String.format("%d parsecs", Distance));
		}

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoCosts);
		tv.setText(String.format("%d cr.",
			gameState.InsuranceMoney() + gameState.MercenaryMoney() + (gameState.Debt > 0 ? Math.max(
				gameState.Debt / 10, 1) : 0) + gameState.WormholeTax(COMMANDER.curSystem, main.WarpSystem)
		));

		if (Distance == 0) {
			Button btn = (Button) rootView.findViewById(R.id.btnRemoteSysWarp);
			btn.setVisibility(View.INVISIBLE);
			btn = (Button) rootView.findViewById(R.id.btnRemoteSysPriceList);
			btn.setVisibility(View.INVISIBLE);
			tv = (TextView) rootView.findViewById(R.id.strRemoteSysOutOfRange);
			tv.setVisibility(View.INVISIBLE);
		} else {
			if (gameState.WormholeExists(COMMANDER.curSystem,
				main.WarpSystem) || Distance <= gameState.Ship.GetFuel()) {
				Button btn = (Button) rootView.findViewById(R.id.btnRemoteSysWarp);
				btn.setVisibility(View.VISIBLE);
				btn = (Button) rootView.findViewById(R.id.btnRemoteSysPriceList);
				btn.setVisibility(View.VISIBLE);
				tv = (TextView) rootView.findViewById(R.id.strRemoteSysOutOfRange);
				tv.setVisibility(View.INVISIBLE);
			} else if (Distance > gameState.Ship.GetFuel()) {
				Button btn = (Button) rootView.findViewById(R.id.btnRemoteSysWarp);
				btn.setVisibility(View.INVISIBLE);
				btn = (Button) rootView.findViewById(R.id.btnRemoteSysPriceList);
				btn.setVisibility(View.INVISIBLE);
				tv = (TextView) rootView.findViewById(R.id.strRemoteSysOutOfRange);
				tv.setVisibility(View.VISIBLE);
			}
		}

			/* TODO: Specific Costs Button
			if (gameState.WormholeExists(gameState.Mercenary[0].curSystem, WarpSystem ) ||
				    gameState.Insurance || gameState.Debt > 0 || gameState.Ship.crew[1] >= 0)
				WarpSystemButtonSpecific.setVisibility(View.VISIBLE);
			*/
		return rootView;
	}
}
