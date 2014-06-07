/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import de.anderdonau.spacetrader.DataTypes.ShortcutArrayAdapter;

public class FragmentShortcuts extends Fragment {
	WelcomeScreen welcomeScreen;
	GameState     gameState;

	public FragmentShortcuts(WelcomeScreen welcomeScreen, GameState gameState) {
		this.welcomeScreen = welcomeScreen;
		this.gameState = gameState;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_shortcuts, container, false);
		//noinspection ConstantConditions
		final ListView listView = (ListView) rootView.findViewById(R.id.listView);

		String[] values = {"Shortcut 1", "Shortcut 2", "Shortcut 3", "Shortcut 4"};
		listView.setAdapter(new ShortcutArrayAdapter(welcomeScreen, values, gameState));

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				final int position = i;
				LinearLayout linearLayout = new LinearLayout(welcomeScreen);

				AlertDialog.Builder builder = new AlertDialog.Builder(welcomeScreen);
				builder.setTitle("Select new Shortcut");
				builder.setView(welcomeScreen.getLayoutInflater().inflate(
					R.layout.dialog_select_new_shortcut, linearLayout
				)
				);
				builder.setCancelable(false);
				final AlertDialog alertDialog = builder.create();
				View.OnClickListener listener = new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						int i = -1;
						switch (view.getId()) {
							case R.id.dialogSelectNewShortcut10:
								i++;
							case R.id.dialogSelectNewShortcut9:
								i++;
							case R.id.dialogSelectNewShortcut8:
								i++;
							case R.id.dialogSelectNewShortcut7:
								i++;
							case R.id.dialogSelectNewShortcut6:
								i++;
							case R.id.dialogSelectNewShortcut5:
								i++;
							case R.id.dialogSelectNewShortcut4:
								i++;
							case R.id.dialogSelectNewShortcut3:
								i++;
							case R.id.dialogSelectNewShortcut2:
								i++;
							case R.id.dialogSelectNewShortcut1:
								i++;
							case R.id.dialogSelectNewShortcut0:
								i++;
						}
						switch (position) {
							case 0:
								gameState.Shortcut1 = i;
								break;
							case 1:
								gameState.Shortcut2 = i;
								break;
							case 2:
								gameState.Shortcut3 = i;
								break;
							case 3:
								gameState.Shortcut4 = i;
								break;
						}
						alertDialog.dismiss();
						welcomeScreen.invalidateOptionsMenu();
						welcomeScreen.changeFragment(WelcomeScreen.FRAGMENTS.SHORTCUTS);
					}
				};
				i = gameState.Shortcuts.length;
				for (int j : new int[]{R.id.dialogSelectNewShortcut10, R.id.dialogSelectNewShortcut9,
				                       R.id.dialogSelectNewShortcut8, R.id.dialogSelectNewShortcut7, R.id.dialogSelectNewShortcut6,
				                       R.id.dialogSelectNewShortcut5, R.id.dialogSelectNewShortcut4,
				                       R.id.dialogSelectNewShortcut3, R.id.dialogSelectNewShortcut2,
				                       R.id.dialogSelectNewShortcut1, R.id.dialogSelectNewShortcut0

				}) {
					Button button = (Button) linearLayout.findViewById(j);
					button.setOnClickListener(listener);
					button.setText(gameState.Shortcuts[--i][1]);
				}
				alertDialog.show();
			}
		}
		);
		return rootView;
	}
}
