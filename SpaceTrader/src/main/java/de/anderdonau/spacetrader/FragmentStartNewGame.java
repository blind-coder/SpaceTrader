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
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

@SuppressWarnings("ConstantConditions")
public class FragmentStartNewGame extends Fragment {
	private GameState gameState;
	private View rootView = null;
	
	public FragmentStartNewGame() {
		gameState = new GameState("Jameson");
	}

	public GameState getGameState() {
		EditText t = (EditText) findViewById(R.id.strNameCommander);
		SeekBar s = (SeekBar) findViewById(R.id.levelBar);
		gameState = new GameState(t.getText().toString());
		gameState.DeterminePrices(gameState.Mercenary[0].curSystem);

		GameState.setDifficulty(s.getProgress());
		s = (SeekBar) findViewById(R.id.skillPilot);
		gameState.Mercenary[0].pilot = s.getProgress()+1;
		s = (SeekBar) findViewById(R.id.skillFighter);
		gameState.Mercenary[0].fighter = s.getProgress()+1;
		s = (SeekBar) findViewById(R.id.skillTrader);
		gameState.Mercenary[0].trader = s.getProgress()+1;
		s = (SeekBar) findViewById(R.id.skillEngineer);
		gameState.Mercenary[0].engineer = s.getProgress()+1;
		return gameState;
	}

	private View findViewById(int r) {
		return rootView.findViewById(r);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_start_new_game, container, false);
		TextView textView = (TextView) rootView.findViewById(R.id.skillPointsLeft);
		textView.setText(String.format("%d", gameState.SkillPointsLeft));

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
				                               gameState.SkillPointsLeft - (skillPilot + skillFighter + skillTrader + skillEngineer)
				)
				);
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
				textview.setText(gameState.levelDesc[((SeekBar) rootView.findViewById(R.id.levelBar))
					.getProgress()]
				);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		};
		((SeekBar) rootView.findViewById(R.id.skillEngineer))
			.setOnSeekBarChangeListener(skillChangeListener);
		((SeekBar) rootView.findViewById(R.id.skillPilot))
			.setOnSeekBarChangeListener(skillChangeListener);
		((SeekBar) rootView.findViewById(R.id.skillFighter))
			.setOnSeekBarChangeListener(skillChangeListener);
		((SeekBar) rootView.findViewById(R.id.skillTrader))
			.setOnSeekBarChangeListener(skillChangeListener);
		((SeekBar) rootView.findViewById(R.id.levelBar))
			.setOnSeekBarChangeListener(levelChangeListener);

		return rootView;
	}
}

