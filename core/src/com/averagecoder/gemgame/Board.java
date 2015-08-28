package com.averagecoder.gemgame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class Board {
	
	public enum GemKey{
		/*SQUARE_RED("001"), 
		SQUARE_ORANGE("002"),
		SQUARE_YELLOW("003"), 
		SQUARE_GREEN("004"), 
		SQUARE_BLUE("005"),
		SQUARE_PURPLE("006"), 
		SQUARE_WHITE("007"),
		HEX_RED("008"), 
		HEX_ORANGE("009"), 
		HEX_YELLOW("010"),
		HEX_GREEN("011"), 
		HEX_BLUE("012"),
		HEX_PURPLE("013"), 
		HEX_WHITE("014"), */
		CIRCLE_RED("015"),
		CIRCLE_ORANGE("016"), 
		/*CIRCLE_YELLOW("017"),
		CIRCLE_GREEN("018"), 
		CIRCLE_BLUE("019"), 
		CIRCLE_PURPLE("020"),
		CIRCLE_WHITE("021"),
		TRIG_RED("022"),
		TRIG_ORANGE("023"), 
		TRIG_YELLOW("024"), */
		TRIG_GREEN("025"),
		TRIG_BLUE("026"), 
		TRIG_PURPLE("027"), 
		TRIG_WHITE("028");
		
		private String value;
 
		private GemKey(String value) {
			this.value = value;
		}
		
		private static final List<GemKey> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
	
		public static GemKey randomGem()  {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
	}
	
	static final int TILES_WIDE = 12;
	static final int TILES_HIGH = 24;
	static final int TILE_WIDTH = 32;
	static final int TILE_HEIGHT = 32;
	static final int PADDING_X = (512 - (TILES_WIDE * TILE_WIDTH)) / 2;
	static final int PADDING_Y = (869 - (TILES_HIGH * TILE_HEIGHT)) / 2;
	
	private GemKey[][] gemTiles = new GemKey[TILES_WIDE][TILES_HIGH];
	private TextureAtlas atlas;
	private Array<Vector2> gemsProcessed = new Array<Vector2>();
	private Texture bg;
	
	public Board(TextureAtlas a){
		atlas = a;
		
		for(int y = 0; y < TILES_HIGH; y++){
			for(int x = 0; x < TILES_WIDE; x++){
				GemKey thisGem = GemKey.randomGem();
				gemTiles[x][y] = thisGem;
			}
		}
		
		bg = new Texture(Utils.getPixmapRoundedRectangle((TILES_WIDE * TILE_WIDTH) + 20, (TILES_HIGH * TILE_HEIGHT) + 20, 5, new Vector3((float)210/255, (float)230/255, (float)255/255)));
	}
	
	public int update(float delta, OrthographicCamera camera){
		int totGems = 0;
		
		if(Gdx.input.justTouched()){
			int screenX = Gdx.input.getX();
			int screenY = Gdx.input.getY();
			
			Vector3 v3 = new Vector3(screenX, screenY, 0);
			camera.unproject(v3);
			
			int tileX = ((int)v3.x - PADDING_X) / TILE_WIDTH;
			int tileY = ((int)v3.y - PADDING_Y) / TILE_HEIGHT;

			gemsProcessed.clear();
			GemKey startGem = gemTiles[tileX][tileY];
			totGems = processGems(1, startGem, tileX, tileY);
			if(totGems > 2){
				for(Vector2 key: gemsProcessed){
					gemTiles[(int)key.x][(int)key.y] = null;
				}
			}
			
			fillTiles();
		}
		
		return totGems;
	}
	
	public void draw(SpriteBatch batch){
		batch.draw(bg, PADDING_X - 10, PADDING_Y - 10);
		
		for(int y = 0; y < TILES_HIGH; y++){
			for(int x = 0; x < TILES_WIDE; x++){
				if(gemTiles[x][y] != null){
					String thisGem = "Gems" + gemTiles[x][y].value;
					batch.draw(atlas.findRegion(thisGem), PADDING_X + (x * TILE_WIDTH), PADDING_Y + (y * TILE_HEIGHT));
				}
			}
		}
	}
	
	private int processGems(int totGems, GemKey startGem, int tileX, int tileY){
		gemsProcessed.add(new Vector2(tileX, tileY));
		
		Array<GemKey> borderGems = new Array<GemKey>();
		Array<Vector2> newTile = new Array<Vector2>();
		
		if(tileX + 1 < TILES_WIDE && startGem == gemTiles[tileX + 1][tileY]){
			Vector2 thisPos = new Vector2(tileX + 1, tileY);
			
			if(!gemsProcessed.contains(thisPos, false)){
				borderGems.add(gemTiles[tileX + 1][tileY]);
				newTile.add(thisPos);
				totGems++;
			}
		}
		if(tileX - 1 >= 0 && startGem == gemTiles[tileX - 1][tileY]){
			Vector2 thisPos = new Vector2(tileX - 1, tileY);
			
			if(!gemsProcessed.contains(thisPos, false)){
				borderGems.add(gemTiles[tileX - 1][tileY]);
				newTile.add(thisPos);
				totGems++;
			}
		}
		if(tileY + 1 < TILES_HIGH && startGem == gemTiles[tileX][tileY + 1]){
			Vector2 thisPos = new Vector2(tileX, tileY + 1);
			
			if(!gemsProcessed.contains(thisPos, false)){
				borderGems.add(gemTiles[tileX][tileY + 1]);
				newTile.add(thisPos);
				totGems++;
			}
		}
		if(tileY - 1 >= 0 && startGem == gemTiles[tileX][tileY - 1]){
			Vector2 thisPos = new Vector2(tileX, tileY - 1);
			
			if(!gemsProcessed.contains(thisPos, false)){
				borderGems.add(gemTiles[tileX][tileY - 1]);
				newTile.add(thisPos);
				totGems++;
			}
		}
		
		for(int i = 0; i < borderGems.size; i++){
			totGems = processGems(totGems, borderGems.get(i), (int)newTile.get(i).x, (int)newTile.get(i).y);
		}
		
		return totGems;
	}
	
	private void fillTiles(){
		for(int x = 0; x < TILES_WIDE; x++){
			for(int i = 0; i < TILES_HIGH; i++){
				int lastY = 0;
				for(int y = 1; y < TILES_HIGH; y++){
					if(gemTiles[x][y] != null && gemTiles[x][lastY] == null){
						gemTiles[x][lastY] = gemTiles[x][y];
						gemTiles[x][y] = null;
					}
					lastY = y;
				}
			}
		}
		
		for(int x = 0; x < TILES_WIDE; x++){
			for(int y = 0; y < TILES_HIGH; y++){
				if(gemTiles[x][y] == null){
					GemKey thisGem = GemKey.randomGem();
					gemTiles[x][y] = thisGem;
				}
			}
		}
	}
	
}
