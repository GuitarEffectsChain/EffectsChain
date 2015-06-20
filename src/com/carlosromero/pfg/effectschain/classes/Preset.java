package com.carlosromero.pfg.effectschain.classes;

public class Preset {
	
	private String name;
	private int id;
	
	public Preset(String name, int id){
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
