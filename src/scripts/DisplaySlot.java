package scripts;

import java.awt.Graphics;

public class DisplaySlot extends Slot {

	private String text;
	
	public DisplaySlot(String text) {
		this.text = text;
		
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {

	}
}
