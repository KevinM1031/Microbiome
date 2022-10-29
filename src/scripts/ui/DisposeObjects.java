package scripts.ui;

import java.util.ArrayList;
import java.util.LinkedList;

import scripts.objects.Block;
import scripts.objects.MineralVent;
import scripts.objects.Protein;
import scripts.objects.Resource;
import scripts.objects.Spore;
import scripts.slots.ActionSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class DisposeObjects {
	
	protected static LinkedList<Protein> proteins;
	protected static LinkedList<Spore> spores;
	protected static LinkedList<Resource> resources;
	protected static LinkedList<MineralVent> vents;
	protected static LinkedList<Block> blocks;
	
	protected static UI recentUI;
	
	public static UI getNewDisposeObjectsUI(Point pos, InputControl inputCtrl, LinkedList<Protein> proteins_, LinkedList<Spore> spores_, 
			LinkedList<Resource> resources_, LinkedList<MineralVent> vents_, LinkedList<Block> blocks_) {
		
		proteins = proteins_;
		spores = spores_;
		resources = resources_;
		vents = vents_;
		blocks = blocks_;
		recentUI = new UI(pos, "Dispose Objects");
		
		// SLOT 1
		UIEvent<Integer> event11 = new UIEvent<Integer>(DisposeObjectsUIFunc::slot1_Func1, "Dispose All Proteins");
		Slot slot1 = new ActionSlot(event11);
		
		// SLOT 2
		UIEvent<Integer> event21 = new UIEvent<Integer>(DisposeObjectsUIFunc::slot2_Func1, "Dispose All Spores");
		Slot slot2 = new ActionSlot(event21);
		
		// SLOT 3
		UIEvent<Integer> event31 = new UIEvent<Integer>(DisposeObjectsUIFunc::slot3_Func1, "Dispose All Resources");
		Slot slot3 = new ActionSlot(event31);
		
		// SLOT 4
		UIEvent<Integer> event41 = new UIEvent<Integer>(DisposeObjectsUIFunc::slot4_Func1, "Dispose All Mineral Vents");
		Slot slot4 = new ActionSlot(event41);
		
		// SLOT 5
		UIEvent<Integer> event51 = new UIEvent<Integer>(DisposeObjectsUIFunc::slot5_Func1, "Dispose All Blocks");
		Slot slot5 = new ActionSlot(event51);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		recentUI.setSlots(slots);
		
		return recentUI;
	}
}

class DisposeObjectsUIFunc {
	
	public static void slot1_Func1(int n) {
		DisposeObjects.proteins.clear();
	}
	
	public static void slot2_Func1(int n) {
		DisposeObjects.spores.clear();
	}
	
	public static void slot3_Func1(int n) {
		DisposeObjects.resources.clear();
	}
	
	public static void slot4_Func1(int n) {
		DisposeObjects.vents.clear();
	}
	
	public static void slot5_Func1(int n) {
		DisposeObjects.blocks.clear();
	}
}
