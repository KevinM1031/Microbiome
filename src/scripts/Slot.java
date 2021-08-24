package scripts;

import java.awt.Graphics;

public abstract class Slot {
	
	public static final int WIDTH = 420;
	public static final int HEIGHT = 24;
	
	Point pos;
	boolean highlight;
	
	public Slot() {
		highlight = false;
	}
	
	public abstract UI update(Point pos, Point mousePos, boolean clicked);
	
	public abstract void draw(Point pos, Graphics G);
}
