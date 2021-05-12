package com.mygdx.game.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 800;
		config.height = 600;
		config.resizable = false;
		config.title = "Trash City";
		config.addIcon("Icones/Icon128.png", FileType.Internal);
		config.addIcon("Icones/Icon32.png", FileType.Internal);
		config.addIcon("Icones/Icon16.png", FileType.Internal);
		new LwjglApplication(new MyGdxGame(), config);
	}
}
