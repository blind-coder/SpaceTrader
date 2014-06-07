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
import android.view.MotionEvent;
import android.view.View;

import de.anderdonau.spacetrader.DataTypes.SolarSystem;

public class NavigationChart extends View {
	protected final Paint     paint           = new Paint();
	public          int       Multiplicator   = 20;
	public          int       WormholeOffset  = 4;
	protected       GameState mGameState      = null;
	protected       int       mDrawWormhole   = -1;
	protected       int       mSelectedSystem = -1;
	protected int radius;
	protected float   mOffsetX        = 0;
	protected float   mOffsetY        = 0;
	protected boolean mOffsetsDefined = false;
	protected boolean isShortRange    = true;
	protected float   mCurrentX       = 0;
	protected float   mCurrentY       = 0;

	public NavigationChart(Context context) {
		super(context);
	}

	public NavigationChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NavigationChart(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setGameState(GameState mGameState) {
		this.mGameState = mGameState;
	}

	public void setShortRange(boolean isShortRange) {
		this.isShortRange = isShortRange;
	}

	public int getSystemAt(float posX, float posY) {
		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			SolarSystem s = mGameState.SolarSystem[i];
			int x = s.x * Multiplicator + (int) mOffsetX;
			int y = s.y * Multiplicator + (int) mOffsetY;

			if (posX >= x - radius && posX <= x + radius) {
				if (posY >= y - radius && posY <= y + radius) {
					return i;
				}
			}
		}
		if (isShortRange) { return -1; }
		return getSystemCloseTo(posX, posY);
	}

	public int getSystemCloseTo(float posX, float posY) {
		SolarSystem s;
		int dist = Integer.MAX_VALUE;
		int retVal = -1;
		posX -= mOffsetX; // offsets are always negative, so we need to substract them
		posY -= mOffsetY; // to get the actual touch position relative to 0,0
		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			s = mGameState.SolarSystem[i];
			int nDist = (int) (Math.pow(posX - s.x * Multiplicator, 2) + Math.pow(
				posY - s.y * Multiplicator, 2
			));
			if (dist > nDist) {
				dist = nDist;
				retVal = i;
			}
		}
		return retVal;
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

	private void sanitizeOffsets() {
		mOffsetX = Math.max(mOffsetX, getWidth() - GameState.GALAXYWIDTH * Multiplicator);
		mOffsetX = Math.min(mOffsetX, 0);
		mOffsetY = Math.max(mOffsetY, getHeight() - GameState.GALAXYHEIGHT * Multiplicator);
		mOffsetY = Math.min(mOffsetY, 0);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mCurrentX = event.getX();
			mCurrentY = event.getY();
			return true;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float x = event.getX();
			float y = event.getY();

			// Update how much the touch moved
			mOffsetX += x - mCurrentX;
			mOffsetY += y - mCurrentY;
			sanitizeOffsets();

			mCurrentX = x;
			mCurrentY = y;

			this.invalidate();
			return true;
		}
		return false;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mGameState == null) { return; }

		SolarSystem CURSYSTEM = mGameState.SolarSystem[mGameState.Mercenary[0].curSystem];
		SolarSystem s;
		int x, y;

		if (isShortRange) {
			radius = Math.min(getWidth(), getHeight()) / 20;
			Multiplicator = 20;
		} else {
			Multiplicator = Math.max(getWidth() / GameState.GALAXYWIDTH,
			                         getHeight() / GameState.GALAXYHEIGHT
			);
			radius = Math.min(getWidth(), getHeight()) / 100;
		}

		if (mSelectedSystem < 0) {
			mSelectedSystem = mGameState.Mercenary[0].curSystem;
		}
		s = mGameState.SolarSystem[mSelectedSystem];
		x = s.x * Multiplicator;
		y = s.y * Multiplicator;

		if (!mOffsetsDefined) {
			/* Short range chart always focuses on CURSYSTEM.
			 * Long range chart may focus on mSelectedSystem by using "Find" button.
			 */
			if (!isShortRange) {
				mOffsetX = -s.x * Multiplicator + getWidth() / 2;
				mOffsetY = -s.y * Multiplicator + getHeight() / 2;
			} else {
				mOffsetX = -CURSYSTEM.x * Multiplicator + getWidth() / 2;
				mOffsetY = -CURSYSTEM.y * Multiplicator + getHeight() / 2;
			}
			mOffsetsDefined = true;
			sanitizeOffsets();
		}
		canvas.translate(mOffsetX, mOffsetY);
		paint.setTextSize(isShortRange ? 40 : 20);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setStyle(Paint.Style.FILL);

		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);

		if (isShortRange) {
			canvas.drawLine(x - (radius * 1.25f), y, x + (radius * 1.25f), y, paint);
			canvas.drawLine(x, y - (radius * 1.25f), x, y + (radius * 1.25f), paint);
		} else {
			canvas.drawLine(x - (radius * 2), y, x + (radius * 2), y, paint);
			canvas.drawLine(x, y - (radius * 2), x, y + (radius * 2), paint);
		}

		paint.setStrokeWidth(0);

		for (int i = 0; i < GameState.MAXSOLARSYSTEM; i++) {
			s = mGameState.SolarSystem[i];
			x = s.x * Multiplicator;
			y = s.y * Multiplicator;
			if (i == mSelectedSystem) {
				paint.setColor(Color.RED);
			} else if (s.visited) {
				paint.setColor(Color.BLUE);
			} else {
				paint.setColor(Color.GREEN);
			}
			canvas.drawCircle(x, y, radius, paint);

			paint.setColor(Color.BLACK);
			canvas.drawText(mGameState.SolarSystemName[s.nameIndex], x, y - radius, paint);
		}

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

		if (mGameState.Ship.GetFuel() > 0) {
			x = CURSYSTEM.x * Multiplicator;
			y = CURSYSTEM.y * Multiplicator;
			paint.setStyle(Paint.Style.STROKE);
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(5);
			canvas.drawCircle(x, y, mGameState.Ship.GetFuel() * Multiplicator, paint);
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

		if (mDrawWormhole >= 0) {
			int wormholeFrom = mDrawWormhole - 1;
			if (wormholeFrom < 0) {
				wormholeFrom += GameState.MAXWORMHOLE;
			}
			SolarSystem from = mGameState.SolarSystem[mGameState.Wormhole[wormholeFrom]];
			SolarSystem to = mGameState.SolarSystem[mGameState.Wormhole[mDrawWormhole]];
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(5);
			canvas.drawLine(from.x * Multiplicator + radius * 2 + WormholeOffset, from.y * Multiplicator,
			                to.x * Multiplicator, to.y * Multiplicator, paint
			);
			paint.setStrokeWidth(0);
		}
	}

}