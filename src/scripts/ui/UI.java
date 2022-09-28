package scripts.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import scripts.data.ConfigDataIO;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Utility;

public class UI {
	
	public static final int BORDER_SIZE = 12;
	public static final int TITLE_HEIGHT = 16;
	public static final int PARTITION_GAP = 6;
	
	private static final int BACKBTN_XOFF = Slot.WIDTH - TITLE_HEIGHT;
	private static final int TITLE_WIDTH = Slot.WIDTH;
	private static final int BACKBTN_SIZE = TITLE_HEIGHT;
	
	public static final int[] DEFAULT_COLOR_PRIMARY = {255, 0, 255};
	public static final int[] DEFAULT_COLOR_SECONDARY = {255, 191, 255};
	public static Color COLOR_PRIMARY, COLOR_SECONDARY;

	private UI prevUI;
	private ArrayList<Slot> slots;
	private Point pos;
	private int width, height;
	private boolean highlightBackBtn, toPrevUI;
	private String title;
	
	public UI(Point pos, UI prevUI, String title, ArrayList<Slot> slots) {
		this.pos = pos;
		this.prevUI = prevUI;
		this.title = title;
		this.slots = slots;
		
		if (ConfigDataIO.background_brightness < 50) {
			COLOR_PRIMARY = new Color(DEFAULT_COLOR_SECONDARY[0], DEFAULT_COLOR_SECONDARY[1], DEFAULT_COLOR_SECONDARY[2],
					(int) (ConfigDataIO.UI_opacity/100.0*255));
			COLOR_SECONDARY = new Color(DEFAULT_COLOR_PRIMARY[0], DEFAULT_COLOR_PRIMARY[1], DEFAULT_COLOR_PRIMARY[2],
					(int) (ConfigDataIO.UI_opacity/100.0*255));
		} else {
			COLOR_PRIMARY = new Color(DEFAULT_COLOR_PRIMARY[0], DEFAULT_COLOR_PRIMARY[1], DEFAULT_COLOR_PRIMARY[2],
					(int) (ConfigDataIO.UI_opacity/100.0*255));
			COLOR_SECONDARY = new Color(DEFAULT_COLOR_SECONDARY[0], DEFAULT_COLOR_SECONDARY[1], DEFAULT_COLOR_SECONDARY[2],
					(int) (ConfigDataIO.UI_opacity/100.0*255));
		}
		
		highlightBackBtn = false;
		width = Slot.WIDTH + BORDER_SIZE*2;
		if (slots != null) {
			height = TITLE_HEIGHT + PARTITION_GAP + BORDER_SIZE*2;
			for(Slot slot : slots) {
				height += slot.getHeight() + PARTITION_GAP;
			}
		}
	}
	
	public UI(Point pos, String title, ArrayList<Slot> slots) {
		this(pos, null, title, slots);
	}
	
	public UI(Point pos, String title) {
		this(pos, title, null);
	}
	
	public UI update(Point mousePos, boolean clicked) {
		
		UI nextUI = null;
		UI tempUI = null;
		
		if (toPrevUI) {
			toPrevUI = false;
			return prevUI;
		}
		
		if (Utility.pointRectInclusion(mousePos, pos.x+BORDER_SIZE+BACKBTN_XOFF, pos.y+BORDER_SIZE, BACKBTN_SIZE, BACKBTN_SIZE)) {
			highlightBackBtn = true;
						
			if(clicked) return prevUI;
			
		} else {
			highlightBackBtn = false;
			
			Point offset = new Point(pos.x+BORDER_SIZE, pos.y+BORDER_SIZE*2+TITLE_HEIGHT);
			
			for(Slot slot : slots) {
				tempUI = slot.update(offset, mousePos, clicked);
				if (tempUI != null) nextUI = tempUI;
				offset.y += slot.getHeight() + PARTITION_GAP;
			}
		}
		
		return (nextUI == null) ? this : nextUI;
	}
	
	public void draw(Graphics G) {
		
		// inner outline
		G.setColor(COLOR_SECONDARY);
		G.drawRect((int) (pos.x+BORDER_SIZE/2), (int) (pos.y+BORDER_SIZE/2), width-BORDER_SIZE, height-BORDER_SIZE);
		
		// outer border
		G.setColor(COLOR_PRIMARY);
		G.drawRect((int)pos.x, (int)pos.y, width, height);
		
		// title
		G.setColor(COLOR_PRIMARY);
		Utility.drawCenteredString(G, title, pos.x+BORDER_SIZE, pos.y+BORDER_SIZE, TITLE_WIDTH, TITLE_HEIGHT);
		
		// back button
		G.setColor((highlightBackBtn) ? COLOR_PRIMARY : COLOR_SECONDARY);
		G.drawRect((int) (pos.x+BORDER_SIZE+BACKBTN_XOFF), (int) (pos.y+BORDER_SIZE), BACKBTN_SIZE, BACKBTN_SIZE);
		G.setColor(COLOR_PRIMARY);
		Utility.drawCenteredString(G, "↩", pos.x+BORDER_SIZE+BACKBTN_XOFF, pos.y+BORDER_SIZE, BACKBTN_SIZE, TITLE_HEIGHT);
		
		// partition lines
		G.setColor(COLOR_SECONDARY);
		G.drawLine((int) (pos.x+BORDER_SIZE/2), (int) (pos.y+TITLE_HEIGHT+BORDER_SIZE*1.5), 
				(int) (pos.x+Slot.WIDTH+BORDER_SIZE*1.5), (int) (pos.y+TITLE_HEIGHT+BORDER_SIZE*1.5));
		
		// slots
		Point offset = new Point(pos.x+BORDER_SIZE, pos.y+BORDER_SIZE*2+TITLE_HEIGHT);
		
		for(Slot slot : slots) {
			slot.draw(offset, G);
			offset.y += slot.getHeight() + PARTITION_GAP;
		}
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setPrevUI(UI prevUI) {
		this.prevUI = prevUI;
	}
	
	public void requestPrevUI() {
		toPrevUI = true;
	}
	
	public void setSlots(ArrayList<Slot> slots) {
		this.slots = slots;
		if (slots == null) return;
		height = TITLE_HEIGHT + PARTITION_GAP + BORDER_SIZE*2;
		for(Slot slot : slots) {
			height += slot.getHeight() + PARTITION_GAP;
		}
	}
	
	public ArrayList<Slot> getSlots() {
		return slots;
	}
	
}
