package scripts;

import java.awt.Color;
import java.awt.Graphics;

public class InputSlot extends Slot {
	
	public static final int TEXTBOX_XOFF = WIDTH/4;
	public static final int TEXTBOX_WIDTH = WIDTH-TEXTBOX_XOFF;
	public static final int LABEL_WIDTH = TEXTBOX_XOFF-1;
	
	private static final int MAX_CURSOR_TICK = 25;
	private static final int MAX_INPUT_SIZE = 36;
	
	private String input, prefix, suffix;
	private boolean focused;
	private InputControl inputCtrl;
	private UIEvent<Returner<String>> event;
	private int cursorTick;
	private boolean showCursor;
	private int maxInputSize;
	
	public InputSlot(UIEvent<Returner<String>> event, String defaultInput, String prefix, String suffix, InputControl inputCtrl) {
		input = defaultInput;
		this.prefix = prefix;
		this.suffix = suffix;
		this.inputCtrl = inputCtrl;
		this.event = event;
		
		focused = false;
		cursorTick = MAX_CURSOR_TICK;
		showCursor = false;
		maxInputSize = MAX_INPUT_SIZE - prefix.length() - suffix.length() - 1;
	}
	
	@Override
	public UI update(Point pos, Point mousePos, boolean clicked) {
		
		if(focused) {
			highlight = true;
			char next = inputCtrl.recentKey();
			if(input.length() < maxInputSize) {
				if(next == 8 /*BACKSPACE*/) input = input.substring(0, Math.max(0, input.length()-1));
				else if(next != 0) input += next;
			}
			
			if(cursorTick < MAX_CURSOR_TICK)
				cursorTick++;
			else {
				cursorTick = 0;
				showCursor = !showCursor;
			}
			
			if(clicked && !Utility.pointRectInclusion(mousePos, pos.x+TEXTBOX_XOFF, pos.y, TEXTBOX_WIDTH, HEIGHT)) {
				focused = false;
				Returner<String> rtr = new Returner<String>(input);
				event.run(rtr);
				input = rtr.get();
			}
		
		} else if(Utility.pointRectInclusion(mousePos, pos.x+TEXTBOX_XOFF, pos.y, TEXTBOX_WIDTH, HEIGHT)) {
			highlight = true;
			
			if(clicked) {
				focused = true;
				cursorTick = 0;
				showCursor = true;
			}
			
		} else {
			highlight = false;
		}
		
		return null;
	}
	
	@Override
	public void draw(Point pos, Graphics G) {
		
		G.setColor((highlight) ? Color.BLACK : Color.LIGHT_GRAY);
		G.drawRect((int)pos.x+TEXTBOX_XOFF, (int)pos.y, TEXTBOX_WIDTH, HEIGHT);
		G.setColor(Color.LIGHT_GRAY);
		G.drawRect((int)pos.x, (int)pos.y, LABEL_WIDTH, HEIGHT);
		
		// label text
		G.setColor(Color.BLACK);
		Utility.drawCenteredString(G, event.getLabel(), pos.x, pos.y, LABEL_WIDTH, HEIGHT);
		
		// textbox text
		G.setColor(Color.BLACK);
		String text = prefix + input;
		if(focused) text += showCursor ? " " : "_";
		text += suffix;
		Utility.drawCenteredString(G, text, pos.x+TEXTBOX_XOFF, pos.y, TEXTBOX_WIDTH, HEIGHT);
	}
}
