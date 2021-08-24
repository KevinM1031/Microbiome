package scripts;

import java.awt.Graphics;

public class ToggleSlot extends Slot {

	private UIEvent<Boolean> event;
	private boolean currState;
	
	public ToggleSlot(UIEvent<Point> event) {	
		currState = true;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {

		if(Utility.pointRectCollision(mousePos, 0, 0, WIDTH, HEIGHT)) {
			highlight = true;
			
			if(clicked) {
				currState = !currState;
				event.run(currState);
			}
			
		} else {
			highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {

	}
	
}
