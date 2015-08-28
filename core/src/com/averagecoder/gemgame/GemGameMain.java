package com.averagecoder.gemgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GemGameMain extends ScreenAdapter{
	
	private GemGame game;
	private Board board;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont font;
	
	private int score = 0;
	
	public GemGameMain(GemGame gemGame){
		game = gemGame;
		batch = game.batch;
		camera = game.camera;
		
		font = new BitmapFont();
		
		board = new Board(game.gems);
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		update(delta);
		draw();
	}

	@Override
	public void dispose() {
		font.dispose();
	}
	
	private void update(float delta){
		score += board.update(delta, camera);
	}
	
	private void draw(){
		camera.update();
		
		Gdx.gl.glClearColor((float)150/255, (float)210/255, 255/255, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		font.setColor((float)0.3, (float)0.3, (float)0.3, (float)1);
		font.draw(batch, "Score: " + score, 50, 869 - 20);
		
		board.draw(batch);
		batch.end();
	}

}
