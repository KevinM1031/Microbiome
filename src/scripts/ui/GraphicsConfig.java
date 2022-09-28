package scripts.ui;

import java.awt.Color;
import java.util.ArrayList;

import scripts.data.ConfigDataIO;
import scripts.slots.InputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class GraphicsConfig {
	public static UI getNewGraphicsConfigUI(Point pos, InputControl inputCtrl) {
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(GraphicsConfigUIFunc::slot1_Func1, "Brightness");
		Slot slot1 = new InputSlot(event11, ConfigDataIO.background_brightness+"", "", " %", inputCtrl);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(GraphicsConfigUIFunc::slot2_Func1, "UI Opacity");
		Slot slot2 = new InputSlot(event21, ConfigDataIO.UI_opacity+"", "", " %", inputCtrl);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(GraphicsConfigUIFunc::slot3_Func1, "Object Opacity");
		Slot slot3 = new InputSlot(event31, ConfigDataIO.protein_opacity+"", "", " %", inputCtrl);
		
		// SLOT 4
		UIEvent<Returner<String>> event41 = new UIEvent<Returner<String>>(GraphicsConfigUIFunc::slot4_Func1, "Outline Brightness");
		Slot slot4 = new InputSlot(event41, ConfigDataIO.protein_outline_brightness+"", "", " %", inputCtrl);
		
		// SLOT 5
		UIEvent<Returner<String>> event51 = new UIEvent<Returner<String>>(GraphicsConfigUIFunc::slot5_Func1, "Resource Opacity");
		Slot slot5 = new InputSlot(event51, ConfigDataIO.resource_opacity+"", "", " %", inputCtrl);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		
		return new UI(pos, "Graphics Configuration", slots);
	}
}

class GraphicsConfigUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		int n = 100;
		try {
			n = Math.min(100, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.background_brightness = n;
		rtr.set(n+"");
		
		if (n < 50) {
			UI.COLOR_PRIMARY = new Color(UI.DEFAULT_COLOR_SECONDARY[0], UI.DEFAULT_COLOR_SECONDARY[1], UI.DEFAULT_COLOR_SECONDARY[2],
					(int) (ConfigDataIO.UI_opacity/100.0*255));
			UI.COLOR_SECONDARY = new Color(UI.DEFAULT_COLOR_PRIMARY[0], UI.DEFAULT_COLOR_PRIMARY[1], UI.DEFAULT_COLOR_PRIMARY[2],
					(int) (ConfigDataIO.UI_opacity/100.0*255));
		} else {
			UI.COLOR_PRIMARY = new Color(UI.DEFAULT_COLOR_PRIMARY[0], UI.DEFAULT_COLOR_PRIMARY[1], UI.DEFAULT_COLOR_PRIMARY[2],
					(int) (ConfigDataIO.UI_opacity/100.0*255));
			UI.COLOR_SECONDARY = new Color(UI.DEFAULT_COLOR_SECONDARY[0], UI.DEFAULT_COLOR_SECONDARY[1], UI.DEFAULT_COLOR_SECONDARY[2],
					(int) (ConfigDataIO.UI_opacity/100.0*255));
		}
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		int n = 100;
		try {
			n = Math.min(100, Math.max(25, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.UI_opacity = n;
		rtr.set(n+"");
		
		if (ConfigDataIO.background_brightness < 50) {
			UI.COLOR_PRIMARY = new Color(UI.DEFAULT_COLOR_SECONDARY[0], UI.DEFAULT_COLOR_SECONDARY[1], UI.DEFAULT_COLOR_SECONDARY[2],
					(int) (n/100.0*255));
			UI.COLOR_SECONDARY = new Color(UI.DEFAULT_COLOR_PRIMARY[0], UI.DEFAULT_COLOR_PRIMARY[1], UI.DEFAULT_COLOR_PRIMARY[2],
					(int) (n/100.0*255));
		} else {
			UI.COLOR_PRIMARY = new Color(UI.DEFAULT_COLOR_PRIMARY[0], UI.DEFAULT_COLOR_PRIMARY[1], UI.DEFAULT_COLOR_PRIMARY[2],
					(int) (n/100.0*255));
			UI.COLOR_SECONDARY = new Color(UI.DEFAULT_COLOR_SECONDARY[0], UI.DEFAULT_COLOR_SECONDARY[1], UI.DEFAULT_COLOR_SECONDARY[2],
					(int) (n/100.0*255));
		}
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		int n = 100;
		try {
			n = Math.min(100, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.protein_opacity = n;
		rtr.set(n+"");
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		int n = 100;
		try {
			n = Math.min(100, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.protein_outline_brightness = n;
		rtr.set(n+"");
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot5_Func1(Returner<String> rtr) {
		int n = 100;
		try {
			n = Math.min(100, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.resource_opacity = n;
		rtr.set(n+"");
		
		ConfigDataIO.saveConfig();
	}
}