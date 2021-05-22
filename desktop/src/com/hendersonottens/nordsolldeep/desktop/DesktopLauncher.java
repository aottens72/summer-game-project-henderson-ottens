package com.hendersonottens.nordsolldeep.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.hendersonottens.nordsolldeep.GameRoot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		DisplayMode primaryMode = Lwjgl3ApplicationConfiguration.getDisplayMode();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		//config.setFullscreenMode(primaryMode);
		config.setTitle("Nordsoll Deep");
		config.setWindowedMode(800,480);
		new Lwjgl3Application(new GameRoot(), config);
	}
}
