package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class CycleSlot extends Slot {
	
	private static final int CYCLEBTN_WIDTH = HEIGHT;
	private static final int SELECT_WIDTH = WIDTH-CYCLEBTN_WIDTH*2-2;
	private static final int RIGHTBTN_XOFF = WIDTH-CYCLEBTN_WIDTH;
	private static final int SELECT_XOFF = CYCLEBTN_WIDTH+1;


	ArrayList<UIEvent<Integer>> events = new ArrayList<UIEvent<Integer>>();
	private int currIndex;
	private boolean highlightLeftBtn, highlightRightBtn;
	
	public CycleSlot(ArrayList<UIEvent<Integer>> events) {
		this.events = events;
		currIndex = 0;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if(events.size() > 1 && Utility.pointRectInclusion(mousePos, pos.x, pos.y, CYCLEBTN_WIDTH, HEIGHT)) {
			highlightLeftBtn = true;
			highlight = highlightRightBtn = false;
			
			if(clicked)
				cycleLeft();
			
				
		} else if(events.size() > 1 && Utility.pointRectInclusion(mousePos, pos.x+RIGHTBTN_XOFF, pos.y, CYCLEBTN_WIDTH, HEIGHT)) {
			highlightRightBtn = true;
			highlight = highlightLeftBtn = false;
			
			if(clicked)
				cycleRight();
			
				
		} else {
			highlightLeftBtn = highlightRightBtn = highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		
		// outlines
		G.setColor((highlightLeftBtn) ? Color.BLACK : Color.LIGHT_GRAY);
		G.drawRect((int)pos.x, (int)pos.y, CYCLEBTN_WIDTH, HEIGHT);
		G.setColor((highlightRightBtn) ? Color.BLACK : Color.LIGHT_GRAY);
		G.drawRect((int)pos.x+RIGHTBTN_XOFF, (int)pos.y, CYCLEBTN_WIDTH, HEIGHT);
		G.setColor(Color.LIGHT_GRAY);
		G.drawRect((int)pos.x+SELECT_XOFF, (int)pos.y, SELECT_WIDTH, HEIGHT);
		
		// label text
		G.setColor(Color.BLACK);
		Utility.drawCenteredString(G, events.get(currIndex).getLabel(), pos.x+SELECT_XOFF, pos.y, SELECT_WIDTH, HEIGHT);
		
		// cycle button text
		G.setColor((events.size() > 1) ? Color.BLACK : Color.LIGHT_GRAY);
		Utility.drawCenteredString(G, "◀", pos.x, pos.y, CYCLEBTN_WIDTH, HEIGHT);
		Utility.drawCenteredString(G, "▶", pos.x+RIGHTBTN_XOFF, pos.y, CYCLEBTN_WIDTH, HEIGHT);
	}
	
	private void cycleLeft() {
		currIndex--;
		if(currIndex < 0) currIndex += events.size();
		currIndex %= events.size();
		events.get(currIndex).run(0);
	}
	
	private void cycleRight() {
		currIndex++;
		currIndex %= events.size();
		events.get(currIndex).run(0);
	}
}
