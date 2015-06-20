package com.carlosromero.pfg.effectschain.classes;

import com.carlosromero.pfg.effectschain.bd.SQLDataBase;

import android.content.Context;

public class BDEfectos {

	public int id;
	public String nombre;
	public int num_parametros;
	public String parametros[];
	public String descripcion;
	
	public BDEfectos(int id, String nombre, String d){
		this.id = id;
		this.nombre = nombre;
		this.num_parametros = num_parametros;
		this.parametros = parametros;
		this.descripcion = d;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNum_parametros() {
		return num_parametros;
	}

	public void setNum_parametros(int num_parametros) {
		this.num_parametros = num_parametros;
	}

	public String[] getParametros() {
		return parametros;
	}

	public void setParametros(String[] parametros) {
		this.parametros = parametros;
	}

	
	
	
	
}
