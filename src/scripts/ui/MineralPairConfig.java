package scripts.ui;

import java.util.ArrayList;

import scripts.data.SaveDataIO;
import scripts.objects.Mineral;
import scripts.slots.LabeledCycleSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class MineralPairConfig {
	public static UI getNewMineralPairConfigUI(Point pos, InputControl inputCtrl) {
		
		// SLOT 1
		ArrayList<UIEvent<Integer>> events1 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event11 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot1_Func1, "Photium (Ph)");
		UIEvent<Integer> event12 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot1_Func1, "Cronium (Cr)");
		UIEvent<Integer> event13 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot1_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event14 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot1_Func1, "Irolium (Io)");
		UIEvent<Integer> event15 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot1_Func1, "Frynx (Fr)");
		events1.add(event11);
		events1.add(event12);
		events1.add(event13);
		events1.add(event14);
		events1.add(event15);
		Slot slot1 = new LabeledCycleSlot("NN (Propulsion IIa)", events1, SaveDataIO.NN_mineral_pair - Mineral.Ph);
		
		// SLOT 2
		ArrayList<UIEvent<Integer>> events2 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event21 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot2_Func1, "Photium (Ph)");
		UIEvent<Integer> event22 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot2_Func1, "Cronium (Cr)");
		UIEvent<Integer> event23 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot2_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event24 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot2_Func1, "Irolium (Io)");
		UIEvent<Integer> event25 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot2_Func1, "Frynx (Fr)");
		events2.add(event21);
		events2.add(event22);
		events2.add(event23);
		events2.add(event24);
		events2.add(event25);
		Slot slot2 = new LabeledCycleSlot("NA (Propulsion IIb)", events2, SaveDataIO.NA_mineral_pair - Mineral.Ph);
		
		// SLOT 3
		ArrayList<UIEvent<Integer>> events3 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event31 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot3_Func1, "Photium (Ph)");
		UIEvent<Integer> event32 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot3_Func1, "Cronium (Cr)");
		UIEvent<Integer> event33 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot3_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event34 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot3_Func1, "Irolium (Io)");
		UIEvent<Integer> event35 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot3_Func1, "Frynx (Fr)");
		events3.add(event31);
		events3.add(event32);
		events3.add(event33);
		events3.add(event34);
		events3.add(event35);
		Slot slot3 = new LabeledCycleSlot("ND (Dissolver I)", events3, SaveDataIO.ND_mineral_pair - Mineral.Ph);
		
		// SLOT 4
		ArrayList<UIEvent<Integer>> events4 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event41 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot4_Func1, "Photium (Ph)");
		UIEvent<Integer> event42 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot4_Func1, "Cronium (Cr)");
		UIEvent<Integer> event43 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot4_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event44 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot4_Func1, "Irolium (Io)");
		UIEvent<Integer> event45 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot4_Func1, "Frynx (Fr)");
		events4.add(event41);
		events4.add(event42);
		events4.add(event43);
		events4.add(event44);
		events4.add(event45);
		Slot slot4 = new LabeledCycleSlot("NP (Dissolver II)", events4, SaveDataIO.NP_mineral_pair - Mineral.Ph);
		
		// SLOT 5
		ArrayList<UIEvent<Integer>> events5 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event51 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot5_Func1, "Photium (Ph)");
		UIEvent<Integer> event52 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot5_Func1, "Cronium (Cr)");
		UIEvent<Integer> event53 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot5_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event54 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot5_Func1, "Irolium (Io)");
		UIEvent<Integer> event55 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot5_Func1, "Frynx (Fr)");
		events5.add(event51);
		events5.add(event52);
		events5.add(event53);
		events5.add(event54);
		events5.add(event55);
		Slot slot5 = new LabeledCycleSlot("AN (Attraction/P)", events5, SaveDataIO.AN_mineral_pair - Mineral.Ph);
		
		// SLOT 6
		ArrayList<UIEvent<Integer>> events6 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event61 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot6_Func1, "Photium (Ph)");
		UIEvent<Integer> event62 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot6_Func1, "Cronium (Cr)");
		UIEvent<Integer> event63 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot6_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event64 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot6_Func1, "Irolium (Io)");
		UIEvent<Integer> event65 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot6_Func1, "Frynx (Fr)");
		events6.add(event61);
		events6.add(event62);
		events6.add(event63);
		events6.add(event64);
		events6.add(event65);
		Slot slot6 = new LabeledCycleSlot("AA (Repulsion/P)", events6, SaveDataIO.AA_mineral_pair - Mineral.Ph);
		
		// SLOT 7
		ArrayList<UIEvent<Integer>> events7 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event71 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot7_Func1, "Photium (Ph)");
		UIEvent<Integer> event72 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot7_Func1, "Cronium (Cr)");
		UIEvent<Integer> event73 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot7_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event74 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot7_Func1, "Irolium (Io)");
		UIEvent<Integer> event75 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot7_Func1, "Frynx (Fr)");
		events7.add(event71);
		events7.add(event72);
		events7.add(event73);
		events7.add(event74);
		events7.add(event75);
		Slot slot7 = new LabeledCycleSlot("AD (Attraction/R)", events7, SaveDataIO.AD_mineral_pair - Mineral.Ph);
		
		// SLOT 8
		ArrayList<UIEvent<Integer>> events8 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event81 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot8_Func1, "Photium (Ph)");
		UIEvent<Integer> event82 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot8_Func1, "Cronium (Cr)");
		UIEvent<Integer> event83 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot8_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event84 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot8_Func1, "Irolium (Io)");
		UIEvent<Integer> event85 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot8_Func1, "Frynx (Fr)");
		events8.add(event81);
		events8.add(event82);
		events8.add(event83);
		events8.add(event84);
		events8.add(event85);
		Slot slot8 = new LabeledCycleSlot("AP (Absorption)", events8, SaveDataIO.AP_mineral_pair - Mineral.Ph);
		
		// SLOT 9
		ArrayList<UIEvent<Integer>> events9 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event91 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot9_Func1, "Photium (Ph)");
		UIEvent<Integer> event92 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot9_Func1, "Cronium (Cr)");
		UIEvent<Integer> event93 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot9_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event94 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot9_Func1, "Irolium (Io)");
		UIEvent<Integer> event95 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot9_Func1, "Frynx (Fr)");
		events9.add(event91);
		events9.add(event92);
		events9.add(event93);
		events9.add(event94);
		events9.add(event95);
		Slot slot9 = new LabeledCycleSlot("DN (Temperature)", events1, SaveDataIO.DN_mineral_pair - Mineral.Ph);
		
		// SLOT 10
		ArrayList<UIEvent<Integer>> events10 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event101 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot10_Func1, "Photium (Ph)");
		UIEvent<Integer> event102 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot10_Func1, "Cronium (Cr)");
		UIEvent<Integer> event103 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot10_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event104 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot10_Func1, "Irolium (Io)");
		UIEvent<Integer> event105 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot10_Func1, "Frynx (Fr)");
		events10.add(event101);
		events10.add(event102);
		events10.add(event103);
		events10.add(event104);
		events10.add(event105);
		Slot slot10 = new LabeledCycleSlot("DA (Propulsion I)", events10, SaveDataIO.DA_mineral_pair - Mineral.Ph);
		
		// SLOT 11
		ArrayList<UIEvent<Integer>> events11 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event111 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot11_Func1, "Photium (Ph)");
		UIEvent<Integer> event112 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot11_Func1, "Cronium (Cr)");
		UIEvent<Integer> event113 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot11_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event114 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot11_Func1, "Irolium (Io)");
		UIEvent<Integer> event115 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot11_Func1, "Frynx (Fr)");
		events11.add(event111);
		events11.add(event112);
		events11.add(event113);
		events11.add(event114);
		events11.add(event115);
		Slot slot11 = new LabeledCycleSlot("DD (Buoyancy)", events11, SaveDataIO.DD_mineral_pair - Mineral.Ph);
		
		// SLOT 12
		ArrayList<UIEvent<Integer>> events12 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event121 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot12_Func1, "Photium (Ph)");
		UIEvent<Integer> event122 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot12_Func1, "Cronium (Cr)");
		UIEvent<Integer> event123 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot12_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event124 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot12_Func1, "Irolium (Io)");
		UIEvent<Integer> event125 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot12_Func1, "Frynx (Fr)");
		events12.add(event121);
		events12.add(event122);
		events12.add(event123);
		events12.add(event124);
		events12.add(event125);
		Slot slot12 = new LabeledCycleSlot("DP (Buffer/P)", events12, SaveDataIO.DP_mineral_pair - Mineral.Ph);
		
		// SLOT 13
		ArrayList<UIEvent<Integer>> events13 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event131 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot13_Func1, "Photium (Ph)");
		UIEvent<Integer> event132 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot13_Func1, "Cronium (Cr)");
		UIEvent<Integer> event133 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot13_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event134 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot13_Func1, "Irolium (Io)");
		UIEvent<Integer> event135 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot13_Func1, "Frynx (Fr)");
		events13.add(event131);
		events13.add(event132);
		events13.add(event133);
		events13.add(event134);
		events13.add(event135);
		Slot slot13 = new LabeledCycleSlot("PN (Production/F)", events13, SaveDataIO.PN_mineral_pair - Mineral.Ph);
		
		// SLOT 14
		ArrayList<UIEvent<Integer>> events14 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event141 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot14_Func1, "Photium (Ph)");
		UIEvent<Integer> event142 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot14_Func1, "Cronium (Cr)");
		UIEvent<Integer> event143 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot14_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event144 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot14_Func1, "Irolium (Io)");
		UIEvent<Integer> event145 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot14_Func1, "Frynx (Fr)");
		events14.add(event141);
		events14.add(event142);
		events14.add(event143);
		events14.add(event144);
		events14.add(event145);
		Slot slot14 = new LabeledCycleSlot("PA (Production/S)", events14, SaveDataIO.PA_mineral_pair - Mineral.Ph);
		
		// SLOT 15
		ArrayList<UIEvent<Integer>> events15 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event151 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot15_Func1, "Photium (Ph)");
		UIEvent<Integer> event152 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot15_Func1, "Cronium (Cr)");
		UIEvent<Integer> event153 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot15_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event154 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot15_Func1, "Irolium (Io)");
		UIEvent<Integer> event155 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot15_Func1, "Frynx (Fr)");
		events15.add(event151);
		events15.add(event152);
		events15.add(event153);
		events15.add(event154);
		events15.add(event155);
		Slot slot15 = new LabeledCycleSlot("PD (Attraction/T)", events15, SaveDataIO.PD_mineral_pair - Mineral.Ph);
		
		// SLOT 16
		ArrayList<UIEvent<Integer>> events16 = new ArrayList<UIEvent<Integer>>();
		UIEvent<Integer> event161 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot16_Func1, "Photium (Ph)");
		UIEvent<Integer> event162 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot16_Func1, "Cronium (Cr)");
		UIEvent<Integer> event163 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot16_Func1, "Neuconium (Nc)");
		UIEvent<Integer> event164 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot16_Func1, "Irolium (Io)");
		UIEvent<Integer> event165 = new UIEvent<Integer>(MineralPairConfigUIFunc::slot16_Func1, "Frynx (Fr)");
		events16.add(event161);
		events16.add(event162);
		events16.add(event163);
		events16.add(event164);
		events16.add(event165);
		Slot slot16 = new LabeledCycleSlot("PP (Reproduction)", events16, SaveDataIO.PP_mineral_pair - Mineral.Ph);
		
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
		slots.add(slot11);
		slots.add(slot12);
		slots.add(slot13);
		slots.add(slot14);
		slots.add(slot15);
		slots.add(slot16);
		
		return new UI(pos, "Amino Acid - Mineral Pair Configuration", slots);
	}
}

class MineralPairConfigUIFunc {
	
	public static void slot1_Func1(int n) {
		SaveDataIO.NN_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot2_Func1(int n) {
		SaveDataIO.NA_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot3_Func1(int n) {
		SaveDataIO.ND_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot4_Func1(int n) {
		SaveDataIO.NP_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot5_Func1(int n) {
		SaveDataIO.AN_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot6_Func1(int n) {
		SaveDataIO.AA_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot7_Func1(int n) {
		SaveDataIO.AD_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot8_Func1(int n) {
		SaveDataIO.AP_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot9_Func1(int n) {
		SaveDataIO.DN_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot10_Func1(int n) {
		SaveDataIO.DA_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot11_Func1(int n) {
		SaveDataIO.DD_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot12_Func1(int n) {
		SaveDataIO.DP_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot13_Func1(int n) {
		SaveDataIO.PN_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot14_Func1(int n) {
		SaveDataIO.PA_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot15_Func1(int n) {
		SaveDataIO.PD_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
	public static void slot16_Func1(int n) {
		SaveDataIO.PP_mineral_pair = Mineral.Ph + n;		
		SaveDataIO.updateConfig();
	}
	
}