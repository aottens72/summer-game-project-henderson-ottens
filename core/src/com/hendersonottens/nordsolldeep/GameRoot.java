package com.hendersonottens.nordsolldeep;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.hendersonottens.nordsolldeep.Screens.TitleScreen;

public class GameRoot extends Game {
	public SpriteBatch batch;
	private Texture[][] map;

	static public Skin gameSkin;
	private Stage stage;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//stage = new Stage(new ScreenViewport());
		//img = new Texture("badlogic.jpg");

		gameSkin = new Skin(Gdx.files.internal("menu-font-skin/pixthulhu-ui.json"));
		this.setScreen(new TitleScreen(this));
		//render();
	}

	@Override
	public void render () {
		super.render();
//		ScreenUtils.clear(0, 0, 1, 1);
//		stage.act();
//		stage.draw();
//
//		batch.begin();
//		//batch.draw(img, 0, 0);
//		batch.end();
	}

	@Override
	public void dispose () {
	}
}
