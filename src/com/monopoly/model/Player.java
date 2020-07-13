package com.monopoly.model;

import java.util.LinkedList;

public class Player {

	private String name;
	private int x, y;
	private int width, height;
	private int currentPlace;
	private int money;
	private LinkedList<Square> owned_place; 
	private int inJail;
	private int jailFree;
	private int total;

	
	public Player(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = 35;
		this.height = 35;
		this.currentPlace = 0;
		this.money = 1200;
		this.owned_place = new LinkedList<>();
		this.inJail = 0;
		this.jailFree = 0;
		this.total = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getCurrentPlace() {
		return currentPlace;
	}

	public void setCurrentPlace(int currentPlace) {
		this.currentPlace = currentPlace;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public LinkedList<Square> getOwned_place() {
		return owned_place;
	}

	public void insertToOwnedPlace(Square owned_place) {
		this.owned_place.add(owned_place); 
	}

	public int getInJail() {
		return inJail;
	}

	public void setInJail(int inJail) {
		this.inJail = inJail;
	}

	public int getJailFree() {
		return jailFree;
	}

	public void setJailFree(int jailFree) {
		this.jailFree = jailFree;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}