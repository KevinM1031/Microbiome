package scripts;

import java.awt.Graphics;
import java.util.ArrayList;

public class UISet {

	private UI graphicsConfig;
	
	private UI currUI;
	private Point pos;
	private InputControl inputCtrl;
	
	public UISet(Point pos, InputControl inputCtrl) {
		this.pos = pos;
		this.inputCtrl = inputCtrl;
		
		setupGraphicsConfig();
		currUI = graphicsConfig;
	}
	
	public void update(Point mousePos, boolean mousePressed) {
		currUI = currUI.update(mousePos, mousePressed);
	}
	
	public void draw(Graphics G) {
		currUI.draw(G);
	}
	
	private void setupGraphicsConfig() {
		
		// SLOT 1
		UIEvent<Integer> event11 = new UIEvent<Integer>(Func::graphicsConfig_Slot1_Func1, "Default");
		UIEvent<Integer> event12 = new UIEvent<Integer>(Func::graphicsConfig_Slot1_Func2, "Biology Lab");
		UIEvent<Integer> event13 = new UIEvent<Integer>(Func::graphicsConfig_Slot1_Func3, "Chemistry Lab");
		UIEvent<Integer> event14 = new UIEvent<Integer>(Func::graphicsConfig_Slot1_Func4, "X-Ray");
		UIEvent<Integer> event15 = new UIEvent<Integer>(Func::graphicsConfig_Slot1_Func5, "Infrared");
		ArrayList<UIEvent<Integer>> events1 = new ArrayList<UIEvent<Integer>>();
		events1.add(event11);
		events1.add(event12);
		events1.add(event13);
		events1.add(event14);
		events1.add(event15);
		Slot slot1 = new CycleSlot(events1);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(Func::graphicsConfig_Slot2_Func1, "UI Opacity");
		Slot slot2 = new InputSlot(event21, UIInput.UI_opacity+"", "", "%", inputCtrl);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		
		graphicsConfig = new UI(pos, "Graphics Configuration", slots);
	}
}

class Func {
	
	public static void graphicsConfig_Slot1_Func1(int n) {
		UIInput.filter = UIInput.FILTER_DEFAULT;
	}
	
	public static void graphicsConfig_Slot1_Func2(int n) {
		UIInput.filter = UIInput.FILTER_BIO_LAB;
	}
	
	public static void graphicsConfig_Slot1_Func3(int n) {
		UIInput.filter = UIInput.FILTER_CHEM_LAB;
	}
	
	public static void graphicsConfig_Slot1_Func4(int n) {
		UIInput.filter = UIInput.FILTER_X_RAY;
	}
	
	public static void graphicsConfig_Slot1_Func5(int n) {
		UIInput.filter = UIInput.FILTER_INFRARED;
	}
	
	public static void graphicsConfig_Slot2_Func1(Returner<String> rtr) {
		int n = 100;
		try {
			n = Math.min(100, Math.max(25, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		UIInput.UI_opacity = n;
		rtr.set(n+"");
	}
}