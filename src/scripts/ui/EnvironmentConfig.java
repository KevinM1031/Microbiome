package scripts.ui;

import java.util.ArrayList;

import scripts.data.SaveDataIO;
import scripts.slots.InputSlot;
import scripts.slots.LabeledCycleSlot;
import scripts.slots.LinkSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class EnvironmentConfig {
	public static UI getNewEnvironmentConfigUI(Point pos, InputControl inputCtrl, UI advancedConfigUI) {
		
		UI newUI = new UI(pos, "Environment Parameters Configuration");
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(EnvironmentConfigUIFunc::slot1_Func1, "Radiation");
		Slot slot1 = new InputSlot(event11, SaveDataIO.radiation+"", "", " Gy", inputCtrl);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(EnvironmentConfigUIFunc::slot2_Func1, "Temperature");
		Slot slot2 = new InputSlot(event21, SaveDataIO.temperature+"", "", " °K", inputCtrl);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(EnvironmentConfigUIFunc::slot3_Func1, "Temperature Range");
		Slot slot3 = new InputSlot(event31, SaveDataIO.temperature_variance+"", "", " °K", inputCtrl);
		
		// SLOT 4
		UIEvent<Returner<String>> event41 = new UIEvent<Returner<String>>(EnvironmentConfigUIFunc::slot4_Func1, "Sunlight");
		Slot slot4 = new InputSlot(event41, SaveDataIO.sunlight+"", "", " lm*", inputCtrl);
		
		// SLOT 2
		UIEvent<Integer> event51 = new UIEvent<Integer>(EnvironmentConfigUIFunc::slot5_Func1, "None");
		UIEvent<Integer> event52 = new UIEvent<Integer>(EnvironmentConfigUIFunc::slot5_Func1, "Linear");
		UIEvent<Integer> event53 = new UIEvent<Integer>(EnvironmentConfigUIFunc::slot5_Func1, "Quadratic");
		UIEvent<Integer> event54 = new UIEvent<Integer>(EnvironmentConfigUIFunc::slot5_Func1, "Cubic");
		UIEvent<Integer> event55 = new UIEvent<Integer>(EnvironmentConfigUIFunc::slot5_Func1, "Quartic");
		ArrayList<UIEvent<Integer>> events5 = new ArrayList<UIEvent<Integer>>();
		events5.add(event51);
		events5.add(event52);
		events5.add(event53);
		events5.add(event54);
		events5.add(event55);
		Slot slot5 = new LabeledCycleSlot("Sunlight Gradient", events5, SaveDataIO.sunlight_gradient);
		
		// SLOT 6
		Slot slot6 = new LinkSlot(newUI, advancedConfigUI, "Advanced Configuration");
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		slots.add(slot6);
		newUI.setSlots(slots);
		
		return newUI;
	}
}

class EnvironmentConfigUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.radiation = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		double n = 293.15;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(1, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.temperature = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		double n = 1;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(1, Double.parseDouble(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.temperature_variance = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		int n = 1;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(1, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.sunlight = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot5_Func1(int n) {
		SaveDataIO.sunlight_gradient = n;
		SaveDataIO.updateConfig();
	}
}