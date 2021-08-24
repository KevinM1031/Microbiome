package scripts;

import java.awt.Graphics;

public class LinkSlot extends Slot {

	private UI nextUI;
	
	public LinkSlot(UI nextUI) {		
		this.nextUI = nextUI;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if(Utility.pointRectCollision(mousePos, 0, 0, WIDTH, HEIGHT)) {
			highlight = true;
			
			if(clicked)
				return nextUI;
			
		} else {
			highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {

	}
}
