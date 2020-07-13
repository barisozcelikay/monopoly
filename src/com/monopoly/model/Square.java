package com.monopoly.model;

public class Square {

	private int id;
	private String name;
	private int x, y;
	private int width, height;
	private int cost;
	private int owned;
	private int buildingCost;
	private int houseCount;

	public Square(int id ,String name, int x, int y, int width, int height, int cost, int buildingCost) {
		
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.cost = cost;
		this.owned = -1;
		this.buildingCost = buildingCost;
		this.houseCount = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getOwned() {
		return owned;
	}

	public void setOwned(int owned) {
		this.owned = owned;
	}

	public int getBuildingCost() {
		return buildingCost;
	}

	public void setBuildingCost(int buildingCost) {
		this.buildingCost = buildingCost;
	}

	public int getHouseCount() {
		return houseCount;
	}

	public void setHouseCount(int houseCount) {
		this.houseCount = houseCount;
	}
}
