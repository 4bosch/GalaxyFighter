package com.gdx.galaxyfighter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdx.galaxyfighter.GalaxyFighter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Galaxy Fighter";
		config.width = 240;
		config.height = 400;
		new LwjglApplication(new GalaxyFighter(), config);
	}
}
