package com.averagecoder.gemgame;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector3;

public class Utils {
	public static Pixmap getPixmapRoundedRectangle(int width, int height, int radius, Vector3 color) {
		Pixmap pixmap;
		
		pixmap = new Pixmap(width, height, Format.RGBA8888);
		pixmap.setColor(color.x, color.y, color.z, 1);
		
		// Pink rectangle
		pixmap.fillRectangle(0, radius, pixmap.getWidth(), pixmap.getHeight()-2*radius);
		
		// Green rectangle
		pixmap.fillRectangle(radius, 0, pixmap.getWidth() - 2*radius, pixmap.getHeight());
		 
		
		// Bottom-left circle
		pixmap.fillCircle(radius, radius, radius);
		 
		// Top-left circle
		pixmap.fillCircle(radius, pixmap.getHeight()-radius, radius);
		
		// Bottom-right circle
		pixmap.fillCircle(pixmap.getWidth()-radius, radius, radius);
		
		// Top-right circle
		pixmap.fillCircle(pixmap.getWidth()-radius, pixmap.getHeight()-radius, radius);
		
		return pixmap;
	}
}
