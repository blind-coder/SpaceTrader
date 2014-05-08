/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package de.anderdonau.spacetrader.DataTypes;

import java.io.Serializable;

import de.anderdonau.spacetrader.GameState;

public class Ship implements Serializable {
	public int type;
	public int[] cargo = new int[GameState.MAXTRADEITEM];
	public int[] weapon = new int[GameState.MAXWEAPON];
	public int[] shield = new int[GameState.MAXSHIELD];
	public int[] shieldStrength = new int[GameState.MAXSHIELD];
	public int[] gadget = new int[GameState.MAXGADGET];
	public int[] crew = new int[GameState.MAXCREW];
	public int fuel;
	public int hull;
	public int tribbles;

	public Ship(int type, int[] cargo, int[] weapon, int[] shield, int[] shieldStrength, int[] gadget, int[] crew, int fuel, int hull, int tribbles) {
		this.type = type;
		this.cargo = cargo;
		this.weapon = weapon;
		this.shield = shield;
		this.shieldStrength = shieldStrength;
		this.gadget = gadget;
		this.crew = crew;
		this.fuel = fuel;
		this.hull = hull;
		this.tribbles = tribbles;
	}
}
