/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class GalacticChart extends View {
	protected final Paint     paint          = new Paint();
	public          int       Multiplicator  = getWidth() / GameState.GALAXYWIDTH;
	public          int       WormholeOffset = 4;
	protected       GameState mGameState     = null;
	protected       int       mDrawWormhole  = -1;
	protected       int       mSelectedSystem = -1;
	protected       int       radius;

	public GalacticChart(Context context) {
		super(context);
	}

	public GalacticChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GalacticChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setGameState(GameState mGameState) {
		this.mGameState = mGameState;
	}

	public int getSystemAt(float posX, float posY) {
		int offsetX = getWidth() / 2;
		int offsetY = getHeight() / 2;

		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			SolarSystem s = mGameState.SolarSystem[i];
			int x = s.x * Multiplicator;
			int y = s.y * Multiplicator;

			if (posX >= x - radius && posX <= x + radius) {
				if (posY >= y - radius && posY <= y + radius) {
					return i;
				}
			}
		}
		return -1;
	}

	public int getWormholeAt(float posX, float posY) {
		int system = getSystemAt(posX - (radius * 2 + WormholeOffset), posY);
		if (system < 0) {
			return -1;
		}

		for (int i = 0; i < GameState.MAXWORMHOLE; i++) {
			SolarSystem s = mGameState.SolarSystem[mGameState.Wormhole[i]];
			if (s.nameIndex == mGameState.SolarSystem[system].nameIndex) {
				i = (i + 1) % GameState.MAXWORMHOLE;
				return i;
			}
		}
		return -1;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mGameState == null) { return; }

		Multiplicator = getWidth() / GameState.GALAXYWIDTH;
		radius = Math.min(getWidth(), getHeight()) / 100;
		SolarSystem CURSYSTEM = mGameState.SolarSystem[mGameState.Mercenary[0].curSystem];
		SolarSystem s;
		int x, y;
		int offsetX = getWidth() / 2;
		int offsetY = getHeight() / 2;

		paint.setTextSize(40);
		paint.setStrokeWidth(0);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setStyle(Paint.Style.FILL);

		if (mSelectedSystem < 0){
			mSelectedSystem = mGameState.Mercenary[0].curSystem;
		}
		s = mGameState.SolarSystem[mSelectedSystem];
		x = s.x * Multiplicator;
		y = s.y * Multiplicator;

		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(2);

		canvas.drawLine(x - (radius * 1.25f), y, x+(radius*1.25f), y, paint);
		canvas.drawLine(x, y-(radius*1.25f), x, y+(radius*1.25f), paint);

		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			s = mGameState.SolarSystem[i];
			x = s.x * Multiplicator;
			y = s.y * Multiplicator;
			if (s.visited) {
				paint.setColor(Color.BLUE);
			} else {
				paint.setColor(Color.GREEN);
			}
			canvas.drawCircle(x, y, radius, paint);
			//canvas.drawText(mGameState.SolarSystemName[s.nameIndex], x, y - radius, paint);
		}
		paint.setColor(Color.BLACK);

		for (int i = 0; i < GameState.MAXWORMHOLE; i++) {
			s = mGameState.SolarSystem[mGameState.Wormhole[i]];

			x = s.x * Multiplicator;
			y = s.y * Multiplicator;
			paint.setColor(Color.BLACK);
			canvas.drawCircle(x + radius * 2 + 4, y, radius, paint);
			for (int r = radius; r >= 0; r--) {
				paint.setARGB(255, (255 / radius) * r, (255 / radius) * r, 0);
				canvas.drawCircle(x + radius * 2 + WormholeOffset, y, r, paint);
			}
		}

		if (mGameState.GetFuel() > 0) {
			x = CURSYSTEM.x * Multiplicator;
			y = CURSYSTEM.y * Multiplicator;
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(5);
			canvas.drawCircle(x, y, mGameState.GetFuel() * Multiplicator, paint);
			paint.setStrokeWidth(0);
		}
		paint.setStyle(Paint.Style.FILL_AND_STROKE);

		if (mGameState.TrackedSystem >= 0) {
			s = mGameState.SolarSystem[mGameState.TrackedSystem];
			int distToTracked = mGameState.RealDistance(CURSYSTEM, s);
			if (distToTracked > 0) {
				canvas.drawLine(CURSYSTEM.x * Multiplicator, CURSYSTEM.y * Multiplicator,
				                s.x * Multiplicator, s.y * Multiplicator, paint
				);
			}
		}

		if (mDrawWormhole >= 0){
			int wormholeFrom = mDrawWormhole-1;
			if (wormholeFrom < 0){
				wormholeFrom += GameState.MAXWORMHOLE;
			}
			SolarSystem from = mGameState.SolarSystem[mGameState.Wormhole[wormholeFrom]];
			SolarSystem to = mGameState.SolarSystem[mGameState.Wormhole[mDrawWormhole]];
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(5);
			canvas.drawLine(from.x * Multiplicator - offsetX + getWidth() / 2 + radius * 2 + WormholeOffset,
			                from.y * Multiplicator - offsetY + getHeight() / 2,
			                to.x * Multiplicator - offsetX + getWidth() / 2,
			                to.y * Multiplicator - offsetY + getHeight() / 2, paint);
			paint.setStrokeWidth(0);
		}
	}
}