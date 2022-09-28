package scripts.ui;

import java.util.ArrayList;

import scripts.Microbiome;
import scripts.data.ConfigDataIO;
import scripts.objects.AminoAcid;
import scripts.objects.Protein;
import scripts.slots.InputSlot;
import scripts.slots.LabeledCycleSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class MechanicsConfig {
	public static UI getNewMechanicsConfigUI(Point pos, InputControl inputCtrl) {
		
		
		// SLOT 1
		ArrayList<UIEvent<Integer>> events1 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event11 = new UIEvent<Integer>(MechanicsConfigUIFunc::slot1_Func1, "Disabled");
		UIEvent<Integer> event12 = new UIEvent<Integer>(MechanicsConfigUIFunc::slot1_Func1, "Every second");
		UIEvent<Integer> event13 = new UIEvent<Integer>(MechanicsConfigUIFunc::slot1_Func1, "Every 10 seconds");
		UIEvent<Integer> event14 = new UIEvent<Integer>(MechanicsConfigUIFunc::slot1_Func1, "Every 100 seconds");
		UIEvent<Integer> event15 = new UIEvent<Integer>(MechanicsConfigUIFunc::slot1_Func1, "Every 1000 seconds");
		events1.add(event11);
		events1.add(event12);
		events1.add(event13);
		events1.add(event14);
		events1.add(event15);
		Slot slot1 = new LabeledCycleSlot("Auto Save", events1, ConfigDataIO.auto_save);
				
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(MechanicsConfigUIFunc::slot2_Func1, "Maximum FPS");
		Slot slot2 = new InputSlot(event21, ConfigDataIO.max_fps+"", "", "", inputCtrl);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(MechanicsConfigUIFunc::slot3_Func1, "Interaction Radius");
		Slot slot3 = new InputSlot(event31, ConfigDataIO.interaction_radius+"", "", " px", inputCtrl);
		
		// SLOT 4
		UIEvent<Returner<String>> event41 = new UIEvent<Returner<String>>(MechanicsConfigUIFunc::slot4_Func1, "Effect Radius");
		Slot slot4 = new InputSlot(event41, ConfigDataIO.effect_radius+"", "×", "", inputCtrl);
		
		// SLOT 5
		UIEvent<Returner<String>> event51 = new UIEvent<Returner<String>>(MechanicsConfigUIFunc::slot5_Func1, "Detection Radius");
		Slot slot5 = new InputSlot(event51, ConfigDataIO.detection_radius+"", "×", "", inputCtrl);
	
		// SLOT 6
		UIEvent<Returner<String>> event61 = new UIEvent<Returner<String>>(MechanicsConfigUIFunc::slot6_Func1, "Object Radius");
		Slot slot6 = new InputSlot(event61, ConfigDataIO.object_radius+"", "×", "", inputCtrl);
		
		// SLOT 7
		UIEvent<Returner<String>> event71 = new UIEvent<Returner<String>>(MechanicsConfigUIFunc::slot7_Func1, "Maximum Proteins");
		Slot slot7 = new InputSlot(event71, ConfigDataIO.proteins_limit+"", "", "", inputCtrl);
		
		// SLOT 6
		UIEvent<Returner<String>> event81 = new UIEvent<Returner<String>>(MechanicsConfigUIFunc::slot8_Func1, "Maximum Spores");
		Slot slot8 = new InputSlot(event81, ConfigDataIO.spores_limit+"", "", "", inputCtrl);
		
		// SLOT 6
		UIEvent<Returner<String>> event91 = new UIEvent<Returner<String>>(MechanicsConfigUIFunc::slot9_Func1, "Maximum Resources");
		Slot slot9 = new InputSlot(event91, ConfigDataIO.resources_limit+"", "", "", inputCtrl);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		slots.add(slot6);
		slots.add(slot7);
		slots.add(slot8);
		slots.add(slot9);
		
		return new UI(pos, "Mechanics Configuration", slots);
	}
}

class MechanicsConfigUIFunc {
	
	public static void slot1_Func1(int n) {
		ConfigDataIO.auto_save = n;
		ConfigDataIO.saveConfig();
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		int n = 80;
		try {
			n = Math.min(100, Math.max(20, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.max_fps = n;
		rtr.set(n+"");
		
		Microbiome.setFPS(n);
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		int n = 200;
		try {
			n = Math.min(2000, Math.max(20, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.interaction_radius = n;
		rtr.set(n+"");
		
		Protein.INTERACTION_RADIUS = ConfigDataIO.interaction_radius;
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		double n = 1;
		try {
			n = Math.min(5, Math.max(0.2, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.effect_radius = n;
		rtr.set(n+"");
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot5_Func1(Returner<String> rtr) {
		double n = 1;
		try {
			n = Math.min(5, Math.max(0.2, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.detection_radius = n;
		rtr.set(n+"");
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot6_Func1(Returner<String> rtr) {
		double n = 1;
		try {
			n = Math.min(10, Math.max(1, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.object_radius = n;
		rtr.set(n+"");
		
		AminoAcid.setRadius((int) ConfigDataIO.object_radius);
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot7_Func1(Returner<String> rtr) {
		int n = 1;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(1, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.proteins_limit = n;
		rtr.set(n+"");
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot8_Func1(Returner<String> rtr) {
		int n = 1;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(1, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.spores_limit = n;
		rtr.set(n+"");
		
		ConfigDataIO.saveConfig();
	}
	
	public static void slot9_Func1(Returner<String> rtr) {
		int n = 1;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(1, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		ConfigDataIO.resources_limit = n;
		rtr.set(n+"");
		
		ConfigDataIO.saveConfig();
	}
}