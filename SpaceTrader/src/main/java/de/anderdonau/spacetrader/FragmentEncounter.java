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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.anderdonau.spacetrader.DataTypes.MyFragment;
import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

public class FragmentEncounter extends MyFragment {
	public Button btnAttack, btnFlee, btnSubmit, btnBribe, btnIgnore, btnYield, btnBoard, btnPlunder,
		btnSurrender, btnDrink, btnMeet, btnTrade, btnInt;
	public ProgressBar pBarEncounter;
	public RenderShip  EncounterPlayerShip, EncounterOpponentShip;
	public TextView EncounterText;
	public boolean  playerShipNeedsUpdate, opponentShipNeedsUpdate;

	@SuppressWarnings("ConstantConditions")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.gameState = (GameState) getArguments().getSerializable("gamestate");
		final View rootView = inflater.inflate(R.layout.fragment_encounter, container, false);
		Ship Ship = gameState.Ship;
		Ship Opponent = gameState.Opponent;
		int d, i;

		EncounterPlayerShip = (RenderShip) rootView.findViewById(R.id.EncounterPlayerShip);
		EncounterPlayerShip.setGameState(gameState);
		EncounterPlayerShip.setShip(Ship);
		EncounterPlayerShip.setRotate(false);
		EncounterOpponentShip = (RenderShip) rootView.findViewById(R.id.EncounterPlayerOpponent);
		EncounterOpponentShip.setGameState(gameState);
		EncounterOpponentShip.setShip(Opponent);
		EncounterOpponentShip.setRotate(true);

		btnAttack = (Button) rootView.findViewById(R.id.btnAttack);
		btnFlee = (Button) rootView.findViewById(R.id.btnFlee);
		btnSubmit = (Button) rootView.findViewById(R.id.btnSubmit);
		btnBribe = (Button) rootView.findViewById(R.id.btnBribe);
		btnIgnore = (Button) rootView.findViewById(R.id.btnIgnore);
		btnYield = (Button) rootView.findViewById(R.id.btnYield);
		btnBoard = (Button) rootView.findViewById(R.id.btnBoard);
		btnPlunder = (Button) rootView.findViewById(R.id.btnPlunder);
		btnSurrender = (Button) rootView.findViewById(R.id.btnSurrender);
		btnDrink = (Button) rootView.findViewById(R.id.btnDrink);
		btnMeet = (Button) rootView.findViewById(R.id.btnMeet);
		btnTrade = (Button) rootView.findViewById(R.id.btnTrade);
		btnInt = (Button) rootView.findViewById(R.id.btnInt);
		pBarEncounter = (ProgressBar) rootView.findViewById(R.id.pBarEncounter);
		EncounterText = (TextView) rootView.findViewById(R.id.txtEncounterText);

		EncounterButtons();

		playerShipNeedsUpdate = false;
		opponentShipNeedsUpdate = false;

		//EncounterDisplayShips();
		EncounterDisplayNextAction(true);

		String buf;
		if (gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
			buf = "You encounter the Customs Police.\n\n";
		} else {
			buf = String.format("At %d click%s from %s you encounter ", gameState.Clicks,
				gameState.Clicks == 1 ? "" : "s", main.SolarSystemName[main.WarpSystem.nameIndex]);
			if (gameState.ENCOUNTERPOLICE(gameState.EncounterType)) {
				buf += "a police ";
			} else if (gameState.ENCOUNTERPIRATE(gameState.EncounterType)) {
				if (Opponent.type == GameState.MANTISTYPE) {
					buf += "an alien ";
				} else {
					buf += "a pirate ";
				}
			} else if (gameState.ENCOUNTERTRADER(gameState.EncounterType)) {
				buf += "a trader ";
			} else if (gameState.ENCOUNTERMONSTER(gameState.EncounterType)) {
				buf += "";
			} else if (gameState.EncounterType == GameState.MARIECELESTEENCOUNTER) {
				buf += "a drifting ship ";
			} else if (gameState.EncounterType == GameState.CAPTAINAHABENCOUNTER) {
				buf += "the famous Captain Ahab ";
			} else if (gameState.EncounterType == GameState.CAPTAINCONRADENCOUNTER) {
				buf += "Captain Conrad ";
			} else if (gameState.EncounterType == GameState.CAPTAINHUIEENCOUNTER) {
				buf += "Captain Huie ";
			} else if (gameState.EncounterType == GameState.BOTTLEOLDENCOUNTER || gameState.EncounterType == GameState.BOTTLEGOODENCOUNTER) {
				buf += "a floating bottle. ";
			} else {
				buf += "a stolen ";
			}
			if (gameState.EncounterType != GameState.MARIECELESTEENCOUNTER && gameState.EncounterType != GameState.CAPTAINAHABENCOUNTER &&
				gameState.EncounterType != GameState.CAPTAINCONRADENCOUNTER && gameState.EncounterType != GameState.CAPTAINHUIEENCOUNTER &&
				gameState.EncounterType != GameState.BOTTLEOLDENCOUNTER && gameState.EncounterType != GameState.BOTTLEGOODENCOUNTER) {
				buf += ShipTypes.ShipTypes[Opponent.type].name;
			}
			buf += ".\n\n";
		}
		buf += EncounterText.getText().toString();
		EncounterText.setText(buf);

		Bitmap tribble = BitmapFactory.decodeResource(main.getResources(), R.drawable.tribble);
		d = (int) Math.ceil(Math.sqrt(Ship.tribbles / 250));
		d = Math.min(d, GameState.TRIBBLESONSCREEN);
		for (i = 0; i <= d; ++i) {
			int resID = main.getResources().getIdentifier("tribbleButton" + String.valueOf(i), "id",
				main.getPackageName());
			ImageView imageView = (ImageView) rootView.findViewById(resID);
			if (imageView == null) {
				continue;
			}
			ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(
				imageView.getLayoutParams());
			marginParams.setMargins(gameState.GetRandom(container.getWidth() - tribble.getWidth()),
				gameState.GetRandom(container.getHeight() - tribble.getHeight()), 0, 0);
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(marginParams);
			imageView.setLayoutParams(layoutParams);

			imageView.setVisibility(View.VISIBLE);
		}
		for (; i <= GameState.TRIBBLESONSCREEN; ++i) {
			int resID = main.getResources().getIdentifier("tribbleButton" + String.valueOf(i), "id",
				main.getPackageName());
			ImageView imageView = (ImageView) rootView.findViewById(resID);
			if (imageView == null) {
				continue;
			}
			imageView.setVisibility(View.GONE);
		}
		return rootView;
	}

	public void EncounterButtons() {
		btnInt.setVisibility(View.GONE);
		btnAttack.setVisibility(View.GONE);
		btnFlee.setVisibility(View.GONE);
		btnSubmit.setVisibility(View.GONE);
		btnBribe.setVisibility(View.GONE);
		btnYield.setVisibility(View.GONE);
		btnIgnore.setVisibility(View.GONE);
		btnSurrender.setVisibility(View.GONE);
		btnPlunder.setVisibility(View.GONE);
		btnBoard.setVisibility(View.GONE);
		btnMeet.setVisibility(View.GONE);
		btnDrink.setVisibility(View.GONE);
		btnTrade.setVisibility(View.GONE);
		pBarEncounter.setVisibility(View.INVISIBLE);

		if (gameState.AutoAttack || gameState.AutoFlee) {
			btnInt.setVisibility(View.VISIBLE);
			pBarEncounter.setVisibility(View.VISIBLE);
		}
		if (gameState.EncounterType == GameState.POLICEINSPECTION) {
			btnAttack.setVisibility(View.VISIBLE);
			btnFlee.setVisibility(View.VISIBLE);
			btnSubmit.setVisibility(View.VISIBLE);
			btnBribe.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
			btnAttack.setVisibility(View.VISIBLE);
			btnFlee.setVisibility(View.VISIBLE);
			btnYield.setVisibility(View.VISIBLE);
			btnBribe.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.POLICEFLEE || gameState.EncounterType == GameState.TRADERFLEE || gameState.EncounterType == GameState.PIRATEFLEE) {
			btnAttack.setVisibility(View.VISIBLE);
			btnIgnore.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.PIRATEATTACK || gameState.EncounterType == GameState.POLICEATTACK || gameState.EncounterType == GameState.SCARABATTACK) {
			btnAttack.setVisibility(View.VISIBLE);
			btnFlee.setVisibility(View.VISIBLE);
			btnSurrender.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.FAMOUSCAPATTACK) {
			btnAttack.setVisibility(View.VISIBLE);
			btnFlee.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.TRADERATTACK || gameState.EncounterType == GameState.SPACEMONSTERATTACK || gameState.EncounterType == GameState.DRAGONFLYATTACK) {
			btnAttack.setVisibility(View.VISIBLE);
			btnFlee.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.TRADERIGNORE || gameState.EncounterType == GameState.POLICEIGNORE || gameState.EncounterType == GameState.PIRATEIGNORE || gameState.EncounterType == GameState.SPACEMONSTERIGNORE || gameState.EncounterType == GameState.DRAGONFLYIGNORE || gameState.EncounterType == GameState.SCARABIGNORE) {
			btnAttack.setVisibility(View.VISIBLE);
			btnIgnore.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.TRADERSURRENDER || gameState.EncounterType == GameState.PIRATESURRENDER) {
			btnAttack.setVisibility(View.VISIBLE);
			btnPlunder.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.MARIECELESTEENCOUNTER) {
			btnBoard.setVisibility(View.VISIBLE);
			btnIgnore.setVisibility(View.VISIBLE);
		} else if (gameState.ENCOUNTERFAMOUS(gameState.EncounterType)) {
			btnAttack.setVisibility(View.VISIBLE);
			btnIgnore.setVisibility(View.VISIBLE);
			btnMeet.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.BOTTLEOLDENCOUNTER || gameState.EncounterType == GameState.BOTTLEGOODENCOUNTER) {
			btnDrink.setVisibility(View.VISIBLE);
			btnIgnore.setVisibility(View.VISIBLE);
		} else if (gameState.EncounterType == GameState.TRADERSELL || gameState.EncounterType == GameState.TRADERBUY) {
			btnAttack.setVisibility(View.VISIBLE);
			btnIgnore.setVisibility(View.VISIBLE);
			btnTrade.setVisibility(View.VISIBLE);
		}

		/* TODO
		if (!TextualEncounters){
			btnYou.setVisibility(View.INVISIBLE);
			btnOpponent.setVisibility(View.INVISIBLE);
		} else {
			btnYou.setVisibility(View.VISIBLE);
			btnOpponent.setVisibility(View.VISIBLE);
		}
		*/
	}

	public void EncounterDisplayShips() {
		// *************************************************************************
		// Display on the encounter screen the ships (and also wipe it)
		// *************************************************************************
		if (opponentShipNeedsUpdate) {
			EncounterOpponentShip.invalidate();
			opponentShipNeedsUpdate = false;
		}
		if (playerShipNeedsUpdate) {
			EncounterPlayerShip.invalidate();
			playerShipNeedsUpdate = false;
		}
	}

	void EncounterDisplayNextAction(Boolean FirstDisplay) {
		// *************************************************************************
		// Display on the encounter screen what the next action will be
		// *************************************************************************
		if (gameState.EncounterType == GameState.POLICEINSPECTION) {
			EncounterText.setText("The police summon you to submit to an inspection.");
		} else if (gameState.EncounterType == GameState.POSTMARIEPOLICEENCOUNTER) {
			EncounterText.setText(
				"\"We know you removed illegal goods from the Marie Celeste!\nYou must give them up at once!\"");
		} else if (FirstDisplay && gameState.EncounterType == GameState.POLICEATTACK && gameState.PoliceRecordScore > GameState.CRIMINALSCORE) {
			EncounterText.setText("The police hail they want you to surrender.");
		} else if (gameState.EncounterType == GameState.POLICEFLEE ||
			gameState.EncounterType == GameState.TRADERFLEE ||
			gameState.EncounterType == GameState.PIRATEFLEE) {
			EncounterText.setText("Your opponent is fleeing.");
		} else if (gameState.EncounterType == GameState.PIRATEATTACK ||
			gameState.EncounterType == GameState.POLICEATTACK ||
			gameState.EncounterType == GameState.TRADERATTACK ||
			gameState.EncounterType == GameState.SPACEMONSTERATTACK ||
			gameState.EncounterType == GameState.DRAGONFLYATTACK ||
			gameState.EncounterType == GameState.SCARABATTACK ||
			gameState.EncounterType == GameState.FAMOUSCAPATTACK) {
			EncounterText.setText("Your opponent attacks.");
		} else if (gameState.EncounterType == GameState.TRADERIGNORE ||
			gameState.EncounterType == GameState.POLICEIGNORE ||
			gameState.EncounterType == GameState.SPACEMONSTERIGNORE ||
			gameState.EncounterType == GameState.DRAGONFLYIGNORE ||
			gameState.EncounterType == GameState.PIRATEIGNORE ||
			gameState.EncounterType == GameState.SCARABIGNORE) {
			if (gameState.Ship.isCloakedTo(gameState.Opponent)) {
				EncounterText.setText("It doesn't notice you.");
			} else {
				EncounterText.setText("It ignores you.");
			}
		} else if (gameState.EncounterType == GameState.TRADERSELL || gameState.EncounterType == GameState.TRADERBUY) {
			EncounterText.setText("You are hailed with an offer to trade goods.");
		} else if (gameState.EncounterType == GameState.TRADERSURRENDER || gameState.EncounterType == GameState.PIRATESURRENDER) {
			EncounterText.setText("Your opponent hails that he surrenders to you.");
		} else if (gameState.EncounterType == GameState.MARIECELESTEENCOUNTER) {
			EncounterText.setText("The Marie Celeste appears to be completely abandoned.");
		} else if (gameState.ENCOUNTERFAMOUS(
			gameState.EncounterType) && gameState.EncounterType != GameState.FAMOUSCAPATTACK) {
			EncounterText.setText("The Captain requests a brief meeting with you.");
		} else if (gameState.EncounterType == GameState.BOTTLEOLDENCOUNTER || gameState.EncounterType == GameState.BOTTLEGOODENCOUNTER) {
			EncounterText.setText("It appears to be a rare bottle of Captain Marmoset's Skill Tonic!");
		}
	}
}
