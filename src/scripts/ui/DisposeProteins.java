package scripts.ui;

import java.util.ArrayList;
import java.util.LinkedList;

import scripts.objects.Protein;
import scripts.objects.Spore;
import scripts.slots.ActionSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class DisposeProteins {
	
	protected static LinkedList<Protein> proteins;
	protected static LinkedList<Spore> spores;
	
	protected static UI recentUI;
	
	public static UI getNewDisposeProteinsUI(Point pos, InputControl inputCtrl, LinkedList<Protein> proteins_, LinkedList<Spore> spores_) {
		
		proteins = proteins_;
		spores = spores_;
		recentUI = new UI(pos, "Dispose All Proteins & Spores?");
		
		// SLOT 1
		UIEvent<Integer> event11 = new UIEvent<Integer>(DisposeProteinsUIFunc::slot1_Func1, "Confirm (only Proteins)");
		Slot slot1 = new ActionSlot(event11);
		
		// SLOT 2
		UIEvent<Integer> event21 = new UIEvent<Integer>(DisposeProteinsUIFunc::slot2_Func1, "Confirm (only Spores)");
		Slot slot2 = new ActionSlot(event21);
		
		// SLOT 3
		UIEvent<Integer> event31 = new UIEvent<Integer>(DisposeProteinsUIFunc::slot3_Func1, "Confirm (Proteins & Spores)");
		Slot slot3 = new ActionSlot(event31);
		
		// SLOT 4
		UIEvent<Integer> event41 = new UIEvent<Integer>(DisposeProteinsUIFunc::slot4_Func1, "Cancel");
		Slot slot4 = new ActionSlot(event41);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		recentUI.setSlots(slots);
		
		return recentUI;
	}
}

class DisposeProteinsUIFunc {
	
	public static void slot1_Func1(int n) {
		DisposeProteins.proteins.clear();
		DisposeProteins.recentUI.requestPrevUI();
	}
	
	public static void slot2_Func1(int n) {
		DisposeProteins.spores.clear();
		DisposeProteins.recentUI.requestPrevUI();
	}
	
	public static void slot3_Func1(int n) {
		DisposeProteins.proteins.clear();
		DisposeProteins.spores.clear();
		DisposeProteins.recentUI.requestPrevUI();
	}
	
	public static void slot4_Func1(int n) {
		DisposeProteins.recentUI.requestPrevUI();
	}
}
