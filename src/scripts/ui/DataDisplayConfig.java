package scripts.ui;

import java.util.ArrayList;

import scripts.data.ConfigDataIO;
import scripts.slots.LabeledCycleSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class DataDisplayConfig {
	public static UI getNewDataDisplayConfigUI(Point pos, InputControl inputCtrl) {
		
		// SLOT 1
		UIEvent<Integer> event11 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot1_Func1, "Hidden");
		UIEvent<Integer> event12 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot1_Func1, "Shown");
		ArrayList<UIEvent<Integer>> events10 = new ArrayList<UIEvent<Integer>>();
		events10.add(event11);
		events10.add(event12);
		Slot slot1 = new LabeledCycleSlot("System FPS", events10, ConfigDataIO.show_fps ? 1 : 0);
		
		// SLOT 2
		UIEvent<Integer> event21 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot2_Func1, "Simple");
		UIEvent<Integer> event22 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot2_Func1, "Default");
		UIEvent<Integer> event23 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot2_Func1, "Detailed");
		ArrayList<UIEvent<Integer>> events20 = new ArrayList<UIEvent<Integer>>();
		events20.add(event21);
		events20.add(event22);
		events20.add(event23);
		Slot slot2 = new LabeledCycleSlot("Object Info Detail", events20, ConfigDataIO.object_info_detail);
		
		// SLOT 3
		UIEvent<Integer> event31 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot3_Func1, "Hidden");
		UIEvent<Integer> event32 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot3_Func1, "Shown");
		UIEvent<Integer> event33 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot3_Func1, "Shown (w/ coordinates)");
		ArrayList<UIEvent<Integer>> events30 = new ArrayList<UIEvent<Integer>>();
		events30.add(event31);
		events30.add(event32);
		events30.add(event33);
		Slot slot3 = new LabeledCycleSlot("Grid Lines", events30, ConfigDataIO.grid_lines);
		
		// SLOT 4
		UIEvent<Integer> event41 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot4_Func1, "Hidden");
		UIEvent<Integer> event42 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot4_Func1, "Shown (5 points)");
		UIEvent<Integer> event43 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot4_Func1, "Shown (15 points)");
		ArrayList<UIEvent<Integer>> events40 = new ArrayList<UIEvent<Integer>>();
		events40.add(event41);
		events40.add(event42);
		events40.add(event43);
		Slot slot4 = new LabeledCycleSlot("Temperatures", events40, ConfigDataIO.temperature_display);
		
		// SLOT 5
		UIEvent<Integer> event51 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot5_Func1, "Hidden");
		UIEvent<Integer> event52 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot5_Func1, "Shown (5 points)");
		UIEvent<Integer> event53 = new UIEvent<Integer>(DataDisplayConfigUIFunc::slot5_Func1, "Shown (15 points)");
		ArrayList<UIEvent<Integer>> events50 = new ArrayList<UIEvent<Integer>>();
		events50.add(event51);
		events50.add(event52);
		events50.add(event53);
		Slot slot5 = new LabeledCycleSlot("Sunlight Levels", events50, ConfigDataIO.sunlight_display);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		
		return new UI(pos, "Data Display Configuration", slots);
	}
}

class DataDisplayConfigUIFunc {
	
	public static void slot1_Func1(int n) {
		ConfigDataIO.show_fps = n == 1;
		ConfigDataIO.saveConfig();
	}
	
	public static void slot2_Func1(int n) {
		ConfigDataIO.object_info_detail = n;
		ConfigDataIO.saveConfig();
	}
	
	public static void slot3_Func1(int n) {
		ConfigDataIO.grid_lines = n;
		ConfigDataIO.saveConfig();
	}
	
	public static void slot4_Func1(int n) {
		ConfigDataIO.temperature_display = n;
		ConfigDataIO.saveConfig();
	}
	
	public static void slot5_Func1(int n) {
		ConfigDataIO.sunlight_display = n;
		ConfigDataIO.saveConfig();
	}
}