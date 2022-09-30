package scripts.ui;

import java.util.ArrayList;

import scripts.data.SaveDataIO;
import scripts.slots.InputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class FrynxMetabolismConfig {
	public static UI getNewFrynxMetabolismConfigUI(Point pos, InputControl inputCtrl) {
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot1_Func1, "Energy");
		Slot slot1 = new InputSlot(event11, SaveDataIO.PN_energy+"", "", " nJ", inputCtrl);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot2_Func1, "Niadine (N)");
		Slot slot2 = new InputSlot(event21, (SaveDataIO.PN_N*100)+"", "", " %", inputCtrl);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot3_Func1, "Altanine (A)");
		Slot slot3 = new InputSlot(event31, (SaveDataIO.PN_A*100)+"", "", " %", inputCtrl);
		
		// SLOT 4
		UIEvent<Returner<String>> event41 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot4_Func1, "Dotranine (D)");
		Slot slot4 = new InputSlot(event41, (SaveDataIO.PN_D*100)+"", "", " %", inputCtrl);
		
		// SLOT 5
		UIEvent<Returner<String>> event51 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot5_Func1, "Piranine (P)");
		Slot slot5 = new InputSlot(event51, (SaveDataIO.PN_P*100)+"", "", " %", inputCtrl);

		// SLOT 6
		UIEvent<Returner<String>> event61 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot6_Func1, "Photium (Ph)");
		Slot slot6 = new InputSlot(event61, (SaveDataIO.PN_Ph*100)+"", "", " %", inputCtrl);
		
		// SLOT 7
		UIEvent<Returner<String>> event71 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot7_Func1, "Cronium (Cr)");
		Slot slot7 = new InputSlot(event71, (SaveDataIO.PN_Cr*100)+"", "", " %", inputCtrl);
		
		// SLOT 8
		UIEvent<Returner<String>> event81 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot8_Func1, "Neuconium (Nc)");
		Slot slot8 = new InputSlot(event81, (SaveDataIO.PN_Nc*100)+"", "", " %", inputCtrl);
		
		// SLOT 9
		UIEvent<Returner<String>> event91 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot9_Func1, "Irolium (Io)");
		Slot slot9 = new InputSlot(event91, (SaveDataIO.PN_Io*100)+"", "", " %", inputCtrl);
		
		// SLOT 10
		UIEvent<Returner<String>> event101 = new UIEvent<Returner<String>>(FrynxMetabolismConfigUIFunc::slot10_Func1, "Frynx (Fr)");
		Slot slot10 = new InputSlot(event101, (SaveDataIO.PN_Fr*100)+"", "", " %", inputCtrl);
		
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
		slots.add(slot10);
		
		return new UI(pos, "Frynx Metabolism (PN) Output Configuration", slots);
	}
}

class FrynxMetabolismConfigUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_energy = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_N = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_A = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_D = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot5_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_P = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot6_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_Ph = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot7_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_Cr = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot8_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_Nc = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot9_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_Io = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
	
	public static void slot10_Func1(Returner<String> rtr) {
		double n = 0;
		try {
			n = Math.min(Double.MAX_VALUE, Math.max(0, Double.parseDouble(rtr.get())/100));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PN_Fr = n;
		rtr.set(n*100+"");
		
		SaveDataIO.updateConfig();
	}
}