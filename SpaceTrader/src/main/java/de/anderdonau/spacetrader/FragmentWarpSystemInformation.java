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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentWarpSystemInformation extends Fragment {
	WelcomeScreen welcomeScreen;
	GameState gameState;

	public FragmentWarpSystemInformation(WelcomeScreen welcomeScreen, GameState gameState) {
		this.welcomeScreen = welcomeScreen;
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_remote_system_information, container,
		                                       false
		);
		CrewMember COMMANDER = gameState.Mercenary[0];
		SolarSystem CURSYSTEM = gameState.SolarSystem[COMMANDER.curSystem];
		TextView tv;

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoName);
		tv.setText(gameState.SolarSystemName[welcomeScreen.WarpSystem.nameIndex]);

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoTechLevel);
		tv.setText(gameState.techLevel[welcomeScreen.WarpSystem.techLevel]);
		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoGovernment);
		tv.setText(Politics.mPolitics[welcomeScreen.WarpSystem.politics].name);
		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoSize);
		tv.setText(gameState.SystemSize[welcomeScreen.WarpSystem.size]);
		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoPolice);
		tv.setText(gameState.Activity[gameState.Politics.mPolitics[welcomeScreen.WarpSystem.politics].strengthPolice]);
		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoPirates);
		tv.setText(gameState.Activity[gameState.Politics.mPolitics[welcomeScreen.WarpSystem.politics].strengthPirates]);

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoDistance);
		int Distance = gameState.RealDistance(CURSYSTEM, welcomeScreen.WarpSystem);
		if (gameState.WormholeExists(COMMANDER.curSystem, welcomeScreen.WarpSystem))
			tv.setText("Wormhole");
		else
			tv.setText(String.format("%d parsecs", Distance));

		tv = (TextView) rootView.findViewById(R.id.strRemoteSysInfoCosts);
		tv.setText(String.format("%d cr.", gameState.InsuranceMoney() + gameState
			.MercenaryMoney() + (gameState.Debt > 0 ? Math.max(gameState.Debt / 10, 1) :
		                       0) + gameState.WormholeTax(COMMANDER.curSystem,welcomeScreen.WarpSystem)
		)
		);

		if (Distance > 0) {
			if (gameState.WormholeExists(COMMANDER.curSystem, welcomeScreen.WarpSystem
			) || Distance <= gameState.GetFuel()) {
				Button btn = (Button) rootView.findViewById(R.id.btnRemoteSyWarp);
				btn.setVisibility(View.VISIBLE);
				btn = (Button) rootView.findViewById(R.id.btnRemoteSysPriceList);
				btn.setVisibility(View.VISIBLE);
				tv = (TextView) rootView.findViewById(R.id.strRemoteSysOutOfRange);
				tv.setVisibility(View.INVISIBLE);
			} else if (Distance > gameState.GetFuel()) {
				Button btn = (Button) rootView.findViewById(R.id.btnRemoteSyWarp);
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
