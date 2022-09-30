package scripts.ui;

import java.util.ArrayList;

import scripts.data.SaveDataIO;
import scripts.slots.InputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class MutationConfig {
	public static UI getNewMutationConfigUI(Point pos, InputControl inputCtrl) {
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(MutationConfigUIFunc::slot1_Func1, "Point Mutation (P)");
		Slot slot1 = new InputSlot(event11, (SaveDataIO.point_mutation*100)+"", "", " %", inputCtrl);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(MutationConfigUIFunc::slot2_Func1, "Group Mutation (I)");
		Slot slot2 = new InputSlot(event21, (SaveDataIO.region_insertion*100)+"", "", " %", inputCtrl);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(MutationConfigUIFunc::slot3_Func1, "Group Deletion (D)");
		Slot slot3 = new InputSlot(event31, (SaveDataIO.region_deletion*100)+"", "", " %", inputCtrl);
		
		// SLOT 4
		UIEvent<Returner<String>> event41 = new UIEvent<Returner<String>>(MutationConfigUIFunc::slot4_Func1, "Group Insertion (C)");
		Slot slot4 = new InputSlot(event41, (SaveDataIO.region_extension*100)+"", "", " %", inputCtrl);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		
		return new UI(pos, "Mutation Specifics Configuration", slots);
	}
}

class MutationConfigUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.point_mutation = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.region_insertion = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.region_deletion = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.region_extension = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
}