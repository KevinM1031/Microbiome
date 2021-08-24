package scripts;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class UI {
	
	private static final int BORDER_SIZE = 12;
	private static final int TITLE_HEIGHT = 16;
	private static final int PARTITION_GAP = 6;
	
	private static final int BACKBTN_XOFF = Slot.WIDTH - TITLE_HEIGHT;
	private static final int TITLE_WIDTH = BACKBTN_XOFF - PARTITION_GAP;
	private static final int BACKBTN_SIZE = TITLE_HEIGHT;

	private UI prevUI;
	private ArrayList<Slot> slots;
	private Point pos;
	private int width, height;
	private boolean highlightBackBtn;
	private String title;
	
	public UI(Point pos, UI prevUI, String title, ArrayList<Slot> slots) {
		this.pos = pos;
		this.prevUI = prevUI;
		this.title = title;
		this.slots = slots;
		
		highlightBackBtn = false;
		width = Slot.WIDTH + BORDER_SIZE*2;
		height = (Slot.HEIGHT+PARTITION_GAP)*slots.size() + TITLE_HEIGHT + PARTITION_GAP + BORDER_SIZE*2;
	}
	
	public UI(Point pos, String title, ArrayList<Slot> slots) {
		this(pos, null, title, slots);
	}
	
	public UI update(Point mousePos, boolean clicked) {
		
		if(prevUI != null && Utility.pointRectInclusion(mousePos, pos.x+BORDER_SIZE+BACKBTN_XOFF, pos.y+BORDER_SIZE, BACKBTN_SIZE, BACKBTN_SIZE)) {
			highlightBackBtn = true;
						
			if(clicked)
				return prevUI;
			
		} else {
			highlightBackBtn = false;
			
			Point offset = new Point(pos.x+BORDER_SIZE, pos.y+BORDER_SIZE*2+TITLE_HEIGHT);
			
			for(Slot slot : slots) {
				slot.update(offset, mousePos, clicked);
				offset.y += Slot.HEIGHT + PARTITION_GAP;
			}
		}
		
		return this;
	}
	
	public void draw(Graphics G) {
		
		// inner outline
		G.setColor(Color.LIGHT_GRAY);
		G.drawRect((int) (pos.x+BORDER_SIZE/2), (int) (pos.y+BORDER_SIZE/2), width-BORDER_SIZE, height-BORDER_SIZE);
		
		// outer border
		G.setColor(Color.BLACK);
		G.drawRect((int)pos.x, (int)pos.y, width, height);
		
		// title
		G.setColor(Color.BLACK);
		Utility.drawCenteredString(G, title, pos.x+BORDER_SIZE, pos.y+BORDER_SIZE, TITLE_WIDTH, TITLE_HEIGHT);
		
		// back button
		G.setColor((highlightBackBtn) ? Color.BLACK : Color.LIGHT_GRAY);
		G.drawRect((int) (pos.x+BORDER_SIZE+BACKBTN_XOFF), (int) (pos.y+BORDER_SIZE), BACKBTN_SIZE, BACKBTN_SIZE);
		G.setColor((prevUI != null) ? Color.BLACK : Color.LIGHT_GRAY);
		Utility.drawCenteredString(G, "⮌", pos.x+BORDER_SIZE+BACKBTN_XOFF, pos.y+BORDER_SIZE, BACKBTN_SIZE, TITLE_HEIGHT);
		
		// partition lines
		G.setColor(Color.LIGHT_GRAY);
		G.drawLine((int) (pos.x+BORDER_SIZE/2), (int) (pos.y+TITLE_HEIGHT+BORDER_SIZE*1.5), 
				(int) (pos.x+Slot.WIDTH+BORDER_SIZE*1.5), (int) (pos.y+TITLE_HEIGHT+BORDER_SIZE*1.5));
		
		// slots
		Point offset = new Point(pos.x+BORDER_SIZE, pos.y+BORDER_SIZE*2+TITLE_HEIGHT);
		
		for(Slot slot : slots) {
			slot.draw(offset, G);
			offset.y += Slot.HEIGHT + PARTITION_GAP;
		}
	}
	
}
