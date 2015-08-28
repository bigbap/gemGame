package com.averagecoder.gemgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GemGame extends Game {
	static final int WIDTH = 512;
	static final int HEIGHT = 869;
	
	SpriteBatch batch;
	TextureAtlas gems;
	AssetManager manager = new AssetManager();
	FPSLogger fpsLogger;
	OrthographicCamera camera;
	Viewport viewport;
	
	public GemGame(){
		fpsLogger = new FPSLogger();
		camera = new OrthographicCamera();
		camera.position.set(WIDTH/2,HEIGHT/2,0);
		viewport = new FitViewport(WIDTH, HEIGHT, camera);
	}
	
	@Override
	public void create () {
		manager.load("gems.pack", TextureAtlas.class);
		manager.finishLoading();
		
		batch = new SpriteBatch();
		gems = manager.get("gems.pack", TextureAtlas.class);
		
		setScreen(new GemGameMain(this));
	}

	@Override
	public void render () {
		//fpsLogger.log();
		
		camera.update();
		
		super.render();
	}
	
	@Override
	public void dispose(){
		batch.dispose();
		gems.dispose();
	}
	
	@Override 
	public void resize(int width, int height){
		viewport.update(width, height);
	}
}
