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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;

@SuppressWarnings("ConstantConditions")
public class FragmentStartNewGame extends MyFragment {
	private View rootView = null;

	public GameState getGameState() {
		SharedPreferences sp = main.getSharedPreferences("options", Context.MODE_PRIVATE);
		SharedPreferences.Editor ed = sp.edit();

		EditText t = (EditText) findViewById(R.id.strNameCommander);
		SeekBar s = (SeekBar) findViewById(R.id.levelBar);
		gameState = new GameState(main, t.getText().toString());
		gameState.DeterminePrices(gameState.Mercenary[0].curSystem);

		ed.putString("Name", t.getText().toString());

		GameState.setDifficulty(s.getProgress());
		ed.putInt("Difficulty", s.getProgress());

		if (s.getProgress() < GameState.NORMAL) {
			if (gameState.SolarSystem[gameState.Mercenary[0].curSystem].special < 0) {
				gameState.SolarSystem[gameState.Mercenary[0].curSystem].special = GameState.LOTTERYWINNER;
			}
		}

		s = (SeekBar) findViewById(R.id.skillPilot);
		gameState.Mercenary[0].pilot = s.getProgress() + 1;
		ed.putInt("Pilot", s.getProgress());
		s = (SeekBar) findViewById(R.id.skillFighter);
		gameState.Mercenary[0].fighter = s.getProgress() + 1;
		ed.putInt("Fighter", s.getProgress());
		s = (SeekBar) findViewById(R.id.skillTrader);
		gameState.Mercenary[0].trader = s.getProgress() + 1;
		ed.putInt("Trader", s.getProgress());
		s = (SeekBar) findViewById(R.id.skillEngineer);
		gameState.Mercenary[0].engineer = s.getProgress() + 1;
		ed.putInt("Engineer", s.getProgress());
		ed.commit();
		return gameState;
	}

	private View findViewById(int r) {
		return rootView.findViewById(r);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		SharedPreferences sp = main.getSharedPreferences("options", Context.MODE_PRIVATE);

		rootView = inflater.inflate(R.layout.fragment_start_new_game, container, false);
		TextView textView = (TextView) rootView.findViewById(R.id.skillPointsLeft);
		textView.setText(String.format("%d", gameState.SkillPointsLeft));
		textView = (TextView) rootView.findViewById(R.id.strNameCommander);
		textView.setText(sp.getString("Name", "Jameson"));
		SeekBar seekBar;

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
				textView.setText(String.format("%d",
					gameState.SkillPointsLeft - (skillPilot + skillFighter + skillTrader + skillEngineer)));
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
				if (sum > gameState.SkillPointsLeft) {
					seekBar.setProgress(seekBar.getProgress() - (sum - gameState.SkillPointsLeft));
					sum = gameState.SkillPointsLeft;
				}
				Button btn = (Button) (rootView.findViewById(R.id.btnStartGame));
				btn.setEnabled(sum == gameState.SkillPointsLeft);
			}
		};
		SeekBar.OnSeekBarChangeListener levelChangeListener = new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				TextView textview = (TextView) rootView.findViewById(R.id.levelDescription);
				textview.setText(main.levelDesc[((SeekBar) rootView.findViewById(R.id.levelBar))
					.getProgress()]);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		};

		seekBar = (SeekBar) rootView.findViewById(R.id.skillEngineer);
		seekBar.setOnSeekBarChangeListener(skillChangeListener);
		seekBar.setProgress(sp.getInt("Engineer", 0));

		seekBar = (SeekBar) rootView.findViewById(R.id.skillPilot);
		seekBar.setOnSeekBarChangeListener(skillChangeListener);
		seekBar.setProgress(sp.getInt("Pilot", 0));

		seekBar = (SeekBar) rootView.findViewById(R.id.skillFighter);
		seekBar.setOnSeekBarChangeListener(skillChangeListener);
		seekBar.setProgress(sp.getInt("Fighter", 0));

		seekBar = (SeekBar) rootView.findViewById(R.id.skillTrader);
		seekBar.setOnSeekBarChangeListener(skillChangeListener);
		seekBar.setProgress(sp.getInt("Trader", 0));

		skillChangeListener.onStopTrackingTouch(seekBar);

		seekBar = (SeekBar) rootView.findViewById(R.id.levelBar);
		seekBar.setOnSeekBarChangeListener(levelChangeListener);
		seekBar.setProgress(sp.getInt("Difficulty", 2));

		return rootView;
	}
}

