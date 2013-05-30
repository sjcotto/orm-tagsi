package com.example.tarea.interfaces;

import com.example.tarea.controller.JpaController;
import com.example.tarea.controller.NativeController;

public class Factory {

	private boolean orm = false;

	public static Factory getInstance() {
		if (ref == null) {
			ref = new Factory();
		}
		return ref;
	}

	private static Factory ref;

	public Api getController() {

		if (orm){
			Api controller = new JpaController();
			return controller;
		}else{
			Api controller = new NativeController();
			return controller;
		}
		
	}

	public boolean isOrm() {
		return orm;
	}

	public Factory setOrm(boolean orm) {
		this.orm = orm;
		return this;
	}

}
