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
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.Ship;

public class FragmentCommanderStatus extends Fragment {
	GameState gameState;

	public FragmentCommanderStatus(GameState gameState) {
		this.gameState = gameState;
	}

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_commander_status, container, false);
		CrewMember COMMANDER = gameState.Mercenary[0];
		Ship Ship = gameState.Ship;
		TextView tv;
		int i;

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusNameCommander);
		tv.setText(gameState.NameCommander);

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusPilot);
		tv.setText(String.format("%d [%d]", COMMANDER.pilot, Ship.PilotSkill()));
		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusFighter);
		tv.setText(String.format("%d [%d]", COMMANDER.fighter, Ship.FighterSkill()));
		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusTrader);
		tv.setText(String.format("%d [%d]", COMMANDER.trader, Ship.TraderSkill()));
		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusEngineer);
		tv.setText(String.format("%d [%d]", COMMANDER.engineer, Ship.EngineerSkill()));

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusKills);
		tv.setText(String.format("%d",
		                         gameState.PirateKills + gameState.PoliceKills + gameState.TraderKills
		)
		);

		i = 0;
		while (i < GameState.MAXPOLICERECORD && gameState.PoliceRecordScore >= gameState.PoliceRecord.minScore[i]) {
			++i;
		}
		--i;
		if (i < 0) { ++i; }
		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusPoliceRecord);
		tv.setText(gameState.PoliceRecord.name[i]);

		i = 0;
		while (i < GameState.MAXREPUTATION && gameState.ReputationScore >= gameState.Reputation.minScore[i]) {
			++i;
		}
		--i;
		if (i < 0) { i = 0; }
		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusReputation);
		tv.setText(gameState.Reputation.name[i]);

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusDifficulty);
		tv.setText(gameState.levelDesc[GameState.getDifficulty()]);

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusDays);
		tv.setText(String.format("%d", gameState.Days));

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusCash);
		tv.setText(String.format("%d cr.", gameState.Credits));

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusDebt);
		tv.setText(String.format("%d cr.", gameState.Debt));

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusWorth);
		tv.setText(String.format("%d cr.", gameState.CurrentWorth()));

		return rootView;
	}
}

