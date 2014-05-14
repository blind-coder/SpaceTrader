/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.Random;

import de.anderdonau.spacetrader.DataTypes.Ship;
import de.anderdonau.spacetrader.DataTypes.ShipTypes;

public class RenderShip extends View {
	protected final Paint paint = new Paint();
	private Ship mShip;

	private boolean rotate = false;
	Bitmap bitmap;
	Bitmap bitmap_damaged;
	Rect src = new Rect();
	Rect dst = new Rect();

	public RenderShip(Context context) {
		super(context);
	}

	public RenderShip(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RenderShip(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	private Bitmap rotateBitmap(Bitmap src){
		Matrix m = new Matrix();
		m.preScale(-1, 1);
		return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
	}
	public void setRotate(boolean rotate) {
		if (rotate != this.rotate){
			bitmap = rotateBitmap(bitmap);
			bitmap_damaged = rotateBitmap(bitmap_damaged);
			this.rotate = rotate;
		}
	}

	public void setShip(Ship ship) {
		this.mShip = ship;
		this.rotate = false;
		if (WelcomeScreen.mGameState == null) {
			bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spacemonster);
			bitmap_damaged = BitmapFactory.decodeResource(getResources(), R.drawable.spacemonster_damaged);
		} else {
			bitmap = BitmapFactory.decodeResource(getResources(),
			                                      WelcomeScreen.mGameState.ShipTypes.ShipTypes[mShip.type].drawable
			);
			bitmap_damaged = BitmapFactory.decodeResource(getResources(),
			                                              WelcomeScreen.mGameState.ShipTypes.ShipTypes[mShip.type].drawable_damaged
			);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		int dmgPercent;
		if (mShip == null) {
			int[] cargo = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
			int[] weapon = {1, -1, -1};
			int[] shield = {1, -1, -1};
			int[] shieldStrength = {100, -1, -1};
			int[] gadget = {-1, -1, -1};
			int[] crew = {1, -1, -1};
			int fuel = 10;
			Random rand = new Random();
			int hull = rand.nextInt(200);
			int tribbles = 0;
			Ship s = new Ship(ShipTypes.SPACEMONSTER, cargo, weapon, shield, shieldStrength, gadget, crew, fuel, hull, tribbles);
			this.setShip(s);
			this.setRotate(true);
		}

		if (WelcomeScreen.mGameState == null){
			dmgPercent = (mShip.hull * 100) / 200;
		} else {
			dmgPercent = (mShip.hull * 100) / WelcomeScreen.mGameState.ShipTypes.ShipTypes[mShip.type].hullStrength;
		}

		src.top = 0;
		src.bottom = bitmap_damaged.getHeight();
		src.left = 0;
		src.right = bitmap_damaged.getWidth();

		dst.top = getHeight()/2 - bitmap.getHeight()/2;
		dst.bottom = dst.top + (src.bottom - src.top);
		dst.left = getWidth()/2 - bitmap.getWidth()/2;
		dst.right = dst.left + (src.right - src.left);
		canvas.drawBitmap(bitmap_damaged, src, dst, paint);

		src.top = 0;
		src.bottom = bitmap.getHeight();
		src.left = 0;
		src.right = bitmap.getWidth()*dmgPercent/100;

		dst.top = getHeight()/2 - bitmap.getHeight()/2;
		dst.bottom = dst.top + (src.bottom - src.top);
		dst.left = getWidth()/2 - bitmap.getWidth()/2;
		dst.right = dst.left + (src.right - src.left);
		canvas.drawBitmap(bitmap, src, dst, paint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int size = 0;
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
		int heigthWithoutPadding = height - getPaddingTop() - getPaddingBottom();

		// set the dimensions
		if (widthWithoutPadding > heigthWithoutPadding) {
			size = heigthWithoutPadding;
		} else {
			size = widthWithoutPadding;
		}

		setMeasuredDimension(size + getPaddingLeft() + getPaddingRight(), size + getPaddingTop() + getPaddingBottom());
	}
}