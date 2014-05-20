/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Popup {
	public AlertDialog dialog = null;
	String                          title      = "";
	String                          content    = "";
	String                          hint       = "";
	String                          help       = "";
	public int                             max        = -1;
	String                          positive   = "";
	String                          negative   = "";
	buttonCallback                  cbPositive = null;
	buttonCallback                  cbNegative = null;
	buttonCallback                  cbMax      = null;
	Context                         context    = null;
	DialogInterface.OnClickListener doNothing  = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialogInterface, int i) {}
	};
	View.OnClickListener            showHelp   = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Toast.makeText(Popup.this.context, help, Toast.LENGTH_LONG).show();
			// dialog.dismiss(); // don't dismiss dialog here
		}
	};

	public Popup(Context context, String title, String content, String help, String positive, buttonCallback cbPositive) {
		this.context = context;
		this.title = title;
		this.content = content;
		this.help = help;
		this.positive = positive;
		this.cbPositive = cbPositive;
	}

	public Popup(Context context, String title, String content, String hint, String help, String positive, String negative, buttonCallback cbPositive, buttonCallback cbNegative) {
		this.context = context;
		this.title = title;
		this.content = content;
		this.hint = hint;
		this.help = help;
		this.negative = negative;
		this.positive = positive;
		this.cbPositive = cbPositive;
		this.cbNegative = cbNegative;
	}

	public Popup(Context context, String title, String content, String help, String positive, String negative, buttonCallback cbPositive, buttonCallback cbNegative) {
		this.title = title;
		this.content = content;
		this.help = help;
		this.positive = positive;
		this.negative = negative;
		this.cbPositive = cbPositive;
		this.cbNegative = cbNegative;
		this.context = context;
	}

	public Popup(Context context, String title, String content, String hint, String help, int max, String positive, String negative, buttonCallback cbPositive, buttonCallback cbNegative, buttonCallback cbMax) {
		this.context = context;
		this.title = title;
		this.content = content;
		this.hint = hint;
		this.help = help;
		this.max = max;
		this.positive = positive;
		this.negative = negative;
		this.cbPositive = cbPositive;
		this.cbNegative = cbNegative;
		this.cbMax = cbMax;
	}

	public void showConfirm() {
		AlertDialog.Builder confirm = new AlertDialog.Builder(context);
		confirm.setTitle(title);
		confirm.setMessage(content);
		confirm.setPositiveButton(positive, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialog.dismiss();
				if (cbPositive != null) {
					cbPositive.execute(Popup.this, null);
				}
			}
		}
		);
		confirm.setNegativeButton(negative, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialog.dismiss();
				if (cbNegative != null) {
					cbNegative.execute(Popup.this, null);
				}
			}
		}
		);
		if (help.length() > 0) {
			confirm.setNeutralButton("Help", doNothing);
		}
		dialog = confirm.create();
		dialog.show();
		if (help.length() > 0) {
			//noinspection ConstantConditions
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(showHelp);
		}
	}

	public void showMessage() {
		AlertDialog.Builder confirm = new AlertDialog.Builder(context);
		confirm.setTitle(title);
		confirm.setMessage(content);
		confirm.setPositiveButton(positive, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				dialog.dismiss();
				if (cbPositive != null) {
					cbPositive.execute(Popup.this, null);
				}
			}
		}
		);
		if (help.length() > 0) {
			confirm.setNeutralButton("Help", doNothing);
		}
		dialog = confirm.create();
		dialog.show();
		if (help.length() > 0) {
			//noinspection ConstantConditions
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(showHelp);
		}
	}

	public void showTextInput() {
		final EditText input = new EditText(context);
		final LinearLayout linearLayout = new LinearLayout(context);

		input.setHint(hint);
		input.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
		                                                    LinearLayout.LayoutParams.WRAP_CONTENT
		)
		);
		linearLayout.addView(input);

		AlertDialog.Builder confirm =
			new AlertDialog.Builder(context).setTitle(title).setMessage(content).setView(linearLayout)
				.setPositiveButton(positive, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						if (cbPositive != null) {
							cbPositive.execute(Popup.this, input);
						}
					}
				}
				).setNegativeButton(negative, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
					if (cbNegative != null) {
						cbNegative.execute(Popup.this, input);
					}
				}
			}
			);
		if (help.length() > 0) {
			confirm.setNeutralButton("Help", doNothing);
		}
		AlertDialog dialog = confirm.create();
		dialog.show();
		if (help.length() > 0) {
			//noinspection ConstantConditions
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(showHelp);
		}
	}

	public void showIntegerInput() {
		final EditText input = new EditText(context);
		final LinearLayout linearLayout = new LinearLayout(context);
		final SeekBar seekBar = new SeekBar(context);
		final TextView textView = new TextView(context);
		final Button button = new Button(context);

		input.setHint(hint);
		input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
		button.setText("Max");
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
				if (cbMax != null) {
					cbMax.execute(Popup.this, view);
				}
			}
		}
		);
		seekBar.setMax(max);
		seekBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
		                                                      LinearLayout.LayoutParams.WRAP_CONTENT
		)
		);
		textView.setText(" 0");
		linearLayout.addView(textView);
		linearLayout.addView(seekBar);
		linearLayout.addView(button);

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				textView.setText(String.format("%2d", seekBar.getProgress()));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		}
		);

		AlertDialog.Builder confirm =
			new AlertDialog.Builder(context).setTitle(title).setMessage(content).setView(linearLayout)
				.setPositiveButton(positive, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
						if (cbPositive != null) {
							cbPositive.execute(Popup.this, seekBar);
						}
					}
				}
				).setNegativeButton(negative, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					dialog.dismiss();
					if (cbNegative != null) {
						cbNegative.execute(Popup.this, seekBar);
					}
				}
			}
			);
		if (help.length() > 0) {
			confirm.setNeutralButton("Help", doNothing);
		}
		confirm.create();
		dialog.show();
		if (help.length() > 0) {
			//noinspection ConstantConditions
			dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(showHelp);
		}
	}

	public void show() {
		if (this.cbNegative == null && this.max == -1) {
			this.showMessage();
		} else if (this.cbNegative != null && this.cbPositive != null && this.max == -1) {
			this.showConfirm();
		} else if (this.max == -1) {
			this.showTextInput();
		} else {
			this.showIntegerInput();
		}
	}

	public interface buttonCallback {
		public void execute(Popup popup, View view);
	}
}
