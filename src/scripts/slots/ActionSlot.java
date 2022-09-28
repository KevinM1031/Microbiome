package scripts.slots;

import java.awt.Graphics;

import scripts.ui.UI;
import scripts.ui.UIEvent;
import scripts.util.Point;
import scripts.util.Utility;

public class ActionSlot extends Slot {

	private UIEvent<Integer> event;
	
	public ActionSlot(UIEvent<Integer> event) {
		this.event = event;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if (Utility.pointRectInclusion(mousePos, pos.x, pos.y, WIDTH, HEIGHT) && active) {
			highlight = true;
			
			if(clicked)
				event.run(0);
			
		} else {
			highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		G.setColor((highlight && active) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		G.drawRect((int)pos.x, (int)pos.y, WIDTH, HEIGHT);
		G.setColor((active) ? UI.COLOR_PRIMARY : UI.COLOR_SECONDARY);
		Utility.drawCenteredString(G, event.getLabel(), pos.x, pos.y, WIDTH, HEIGHT);
	}
}
