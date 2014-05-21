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

import de.anderdonau.spacetrader.DataTypes.Politics;
import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class FragmentSystemInformation extends Fragment {
	private GameState gameState;

	public FragmentSystemInformation(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_system_information, container, false);
		SolarSystem CURSYSTEM = gameState.SolarSystem[gameState.Mercenary[0].curSystem];
		CURSYSTEM.visited = true;

		TextView textView = (TextView) rootView.findViewById(R.id.strSysInfoName);
		textView.setText(gameState.SolarSystemName[CURSYSTEM.nameIndex]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoSize);
		textView.setText(gameState.SystemSize[CURSYSTEM.size]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoTechLevel);
		textView.setText(gameState.techLevel[CURSYSTEM.techLevel]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoGovernment);
		textView.setText(Politics.mPolitics[CURSYSTEM.politics].name);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoResources);
		textView.setText(gameState.SpecialResources[CURSYSTEM.specialResources]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoPolice);
		textView.setText(gameState.Activity[Politics.mPolitics[CURSYSTEM.politics].strengthPolice]);
		textView = (TextView) rootView.findViewById(R.id.strSysInfoPirates);
		textView.setText(gameState.Activity[Politics.mPolitics[CURSYSTEM.politics].strengthPirates]);
		textView = (TextView) rootView.findViewById(R.id.strCurrentPressure);
		textView.setText(gameState.Status[CURSYSTEM.status]);

		Button btn = (Button) rootView.findViewById(R.id.btnSpecialEvent);
		if (CURSYSTEM.special > 0 && gameState.OpenQuests() < 3) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}

		if ((CURSYSTEM.special < 0) ||
			(CURSYSTEM.special == GameState.BUYTRIBBLE && gameState.Ship.tribbles <= 0) ||
			(CURSYSTEM.special == GameState.ERASERECORD && gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE) ||
			(CURSYSTEM.special == GameState.CARGOFORSALE && (gameState.FilledCargoBays() > gameState
				.TotalCargoBays() - 3)) ||
			((CURSYSTEM.special == GameState.DRAGONFLY || CURSYSTEM.special == GameState.JAPORIDISEASE ||
				CURSYSTEM.special == GameState.ALIENARTIFACT || CURSYSTEM.special == GameState.AMBASSADORJAREK ||
				CURSYSTEM.special == GameState.EXPERIMENT) && (gameState.PoliceRecordScore < GameState.DUBIOUSSCORE)) ||
			(CURSYSTEM.special == GameState.TRANSPORTWILD && (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE)) ||
			(CURSYSTEM.special == GameState.GETREACTOR && (gameState.PoliceRecordScore >= GameState.DUBIOUSSCORE || gameState.ReputationScore < GameState.AVERAGESCORE || gameState.ReactorStatus != 0)) ||
			(CURSYSTEM.special == GameState.REACTORDELIVERED && !(gameState.ReactorStatus > 0 && gameState.ReactorStatus < 21)) ||
			(CURSYSTEM.special == GameState.MONSTERKILLED && gameState.MonsterStatus < 2) ||
			(CURSYSTEM.special == GameState.EXPERIMENTSTOPPED && !(gameState.ExperimentStatus >= 1 && gameState.ExperimentStatus < 12)) ||
			(CURSYSTEM.special == GameState.FLYBARATAS && gameState.DragonflyStatus < 1) ||
			(CURSYSTEM.special == GameState.FLYMELINA && gameState.DragonflyStatus < 2) ||
			(CURSYSTEM.special == GameState.FLYREGULAS && gameState.DragonflyStatus < 3) ||
			(CURSYSTEM.special == GameState.DRAGONFLYDESTROYED && gameState.DragonflyStatus < 5) ||
			(CURSYSTEM.special == GameState.SCARAB && (gameState.ReputationScore < GameState.AVERAGESCORE || gameState.ScarabStatus != 0)) ||
			(CURSYSTEM.special == GameState.SCARABDESTROYED && gameState.ScarabStatus != 2) ||
			(CURSYSTEM.special == GameState.GETHULLUPGRADED && gameState.ScarabStatus != 2) ||
			(CURSYSTEM.special == GameState.MEDICINEDELIVERY && gameState.JaporiDiseaseStatus != 1) ||
			(CURSYSTEM.special == GameState.JAPORIDISEASE && (gameState.JaporiDiseaseStatus != 0)) ||
			(CURSYSTEM.special == GameState.ARTIFACTDELIVERY && !gameState.ArtifactOnBoard) ||
			(CURSYSTEM.special == GameState.JAREKGETSOUT && gameState.JarekStatus != 1) ||
			(CURSYSTEM.special == GameState.WILDGETSOUT && gameState.WildStatus != 1) ||
			(CURSYSTEM.special == GameState.GEMULONRESCUED && !(gameState.InvasionStatus >= 1 && gameState.InvasionStatus <= 7)) ||
			(CURSYSTEM.special == GameState.MOONFORSALE && (gameState.MoonBought || gameState
				.CurrentWorth() < (GameState.COSTMOON * 4) / 5)) ||
			(CURSYSTEM.special == GameState.MOONBOUGHT && !gameState.MoonBought)) {
			btn.setVisibility(View.INVISIBLE);
		} else if (gameState.OpenQuests() > 3 && (CURSYSTEM.special == GameState.TRIBBLE ||
			CURSYSTEM.special == GameState.SPACEMONSTER ||
			CURSYSTEM.special == GameState.DRAGONFLY ||
			CURSYSTEM.special == GameState.JAPORIDISEASE ||
			CURSYSTEM.special == GameState.ALIENARTIFACT ||
			CURSYSTEM.special == GameState.AMBASSADORJAREK ||
			CURSYSTEM.special == GameState.ALIENINVASION ||
			CURSYSTEM.special == GameState.EXPERIMENT ||
			CURSYSTEM.special == GameState.TRANSPORTWILD ||
			CURSYSTEM.special == GameState.GETREACTOR ||
			CURSYSTEM.special == GameState.SCARAB)) {
			btn.setVisibility(View.INVISIBLE);
		} else {
			btn.setVisibility(View.VISIBLE);
		}

		if (CURSYSTEM.special > -1) {
			if (CURSYSTEM.special == GameState.MONSTERKILLED && gameState.MonsterStatus == 2) {
				gameState.addNewsEvent(GameState.MONSTERKILLED);
			} else if (CURSYSTEM.special == GameState.DRAGONFLY) {
				gameState.addNewsEvent(GameState.DRAGONFLY);
			} else if (CURSYSTEM.special == GameState.SCARAB) {
				gameState.addNewsEvent(GameState.SCARAB);
			} else if (CURSYSTEM.special == GameState.SCARABDESTROYED && gameState.ScarabStatus == 2) {
				gameState.addNewsEvent(GameState.SCARABDESTROYED);
			} else if (CURSYSTEM.special == GameState.FLYBARATAS && gameState.DragonflyStatus == 1) {
				gameState.addNewsEvent(GameState.FLYBARATAS);
			} else if (CURSYSTEM.special == GameState.FLYMELINA && gameState.DragonflyStatus == 2) {
				gameState.addNewsEvent(GameState.FLYMELINA);
			} else if (CURSYSTEM.special == GameState.FLYREGULAS && gameState.DragonflyStatus == 3) {
				gameState.addNewsEvent(GameState.FLYREGULAS);
			} else if (CURSYSTEM.special == GameState.DRAGONFLYDESTROYED && gameState.DragonflyStatus == 5) {
				gameState.addNewsEvent(GameState.DRAGONFLYDESTROYED);
			} else if (CURSYSTEM.special == GameState.MEDICINEDELIVERY && gameState.JaporiDiseaseStatus == 1) {
				gameState.addNewsEvent(GameState.MEDICINEDELIVERY);
			} else if (CURSYSTEM.special == GameState.ARTIFACTDELIVERY && gameState.ArtifactOnBoard) {
				gameState.addNewsEvent(GameState.ARTIFACTDELIVERY);
			} else if (CURSYSTEM.special == GameState.JAPORIDISEASE && gameState.JaporiDiseaseStatus == 0) {
				gameState.addNewsEvent(GameState.JAPORIDISEASE);
			} else if (CURSYSTEM.special == GameState.JAREKGETSOUT && gameState.JarekStatus == 1) {
				gameState.addNewsEvent(GameState.JAREKGETSOUT);
			} else if (CURSYSTEM.special == GameState.WILDGETSOUT && gameState.WildStatus == 1) {
				gameState.addNewsEvent(GameState.WILDGETSOUT);
			} else if (CURSYSTEM.special == GameState.GEMULONRESCUED && gameState.InvasionStatus > 0 && gameState.InvasionStatus < 8) {
				gameState.addNewsEvent(GameState.GEMULONRESCUED);
			} else if (CURSYSTEM.special == GameState.ALIENINVASION) {
				gameState.addNewsEvent(GameState.ALIENINVASION);
			} else if (CURSYSTEM.special == GameState.EXPERIMENTSTOPPED && gameState.ExperimentStatus > 0 && gameState.ExperimentStatus < 12) {
				gameState.addNewsEvent(GameState.EXPERIMENTSTOPPED);
			} else if (CURSYSTEM.special == GameState.EXPERIMENTNOTSTOPPED) {
				gameState.addNewsEvent(GameState.EXPERIMENTNOTSTOPPED);
			}
		}

		btn = (Button) rootView.findViewById(R.id.btnMercenaryForHire);
		if (gameState.GetForHire() > -1) {
			btn.setVisibility(View.VISIBLE);
		} else {
			btn.setVisibility(View.INVISIBLE);
		}
		return rootView;
	}

}