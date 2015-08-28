package com.averagecoder.gemgame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.averagecoder.gemgame.GemGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 512;
		config.height = 869;
		config.title = "Gems";
		new LwjglApplication(new GemGame(), config);
	}
}
