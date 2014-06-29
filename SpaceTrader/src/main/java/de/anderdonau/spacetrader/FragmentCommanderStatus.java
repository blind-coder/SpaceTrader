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
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.CrewMember;
import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.PoliceRecord;
import de.anderdonau.spacetrader.DataTypes.Reputation;
import de.anderdonau.spacetrader.DataTypes.Ship;

public class FragmentCommanderStatus extends MyFragment {
	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
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
			gameState.PirateKills + gameState.PoliceKills + gameState.TraderKills));

		i = 0;
		while (i < GameState.MAXPOLICERECORD && gameState.PoliceRecordScore >= PoliceRecord.minScore[i]) {
			++i;
		}
		--i;
		if (i < 0) {
			++i;
		}
		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusPoliceRecord);
		tv.setText(PoliceRecord.name[i]);

		i = 0;
		while (i < GameState.MAXREPUTATION && gameState.ReputationScore >= Reputation.minScore[i]) {
			++i;
		}
		--i;
		if (i < 0) {
			i = 0;
		}
		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusReputation);
		tv.setText(Reputation.name[i]);

		tv = (TextView) rootView.findViewById(R.id.txtCommanderStatusDifficulty);
		tv.setText(main.levelDesc[GameState.getDifficulty()]);

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

