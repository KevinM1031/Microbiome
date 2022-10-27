package scripts.ui;

import java.util.ArrayList;
import java.util.LinkedList;

import scripts.data.SaveDataIO;
import scripts.objects.Block;
import scripts.objects.MineralVent;
import scripts.objects.Protein;
import scripts.objects.Resource;
import scripts.objects.Spore;
import scripts.slots.ActionSlot;
import scripts.slots.CycleSlot;
import scripts.slots.InputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class ViewSaves {
	
	protected static Slot dSlot, sSlot;
	protected static int index, height;
	
	protected static LinkedList<Protein> proteins;
	protected static LinkedList<Spore> spores;
	protected static LinkedList<Resource> resources;
	protected static LinkedList<MineralVent> mineralVents;
	protected static LinkedList<Block> blocks;
	
	public static UI getNewViewSavesUI(Point pos, InputControl inputCtrl,
			LinkedList<Protein> proteins_, LinkedList<Spore> spores_, 
			LinkedList<Resource> resources_, LinkedList<MineralVent> mineralVents_, 
			LinkedList<Block> blocks_, int height_) {
		
		proteins = proteins_;
		spores = spores_;
		resources = resources_;
		mineralVents = mineralVents_;
		blocks = blocks_;
		height = height_;
		index = SaveDataIO.getIndex()-1;
		
		UI newUI = new UI(pos, "Saves");
		
		// SLOT 1
		ArrayList<UIEvent<Integer>> events1 = new ArrayList<UIEvent<Integer>>();
		for (int i = 0; i < SaveDataIO.MAX_SAVES; i++) {
			UIEvent<Integer> event11 = new UIEvent<Integer>(ViewSavesUIFunc::slot1_Func1, "Save #" + (i+1));
			events1.add(event11);
		}
		Slot slot1 = new CycleSlot(events1, index);
		
		// SLOT 2
		UIEvent<Returner<String>> event21 = new UIEvent<Returner<String>>(ViewSavesUIFunc::slot2_Func1, "Description");
		dSlot = new InputSlot(event21, SaveDataIO.saveDescriptions[index], "", "", inputCtrl);
		
		// SLOT 3
		UIEvent<Integer> event31 = new UIEvent<Integer>(ViewSavesUIFunc::slot3_Func1, "Select");
		sSlot = new ActionSlot(event31);
		sSlot.setActive(false);
		
		// SLOT 4
		UIEvent<Integer> event41 = new UIEvent<Integer>(ViewSavesUIFunc::slot4_Func1, "Load");
		Slot slot4 = new ActionSlot(event41);
		
		// SLOT 5
		UIEvent<Integer> event51 = new UIEvent<Integer>(ViewSavesUIFunc::slot5_Func1, "Overwrite");
		Slot slot5 = new ActionSlot(event51);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(dSlot);
		slots.add(sSlot);
		slots.add(slot4);
		slots.add(slot5);
		newUI.setSlots(slots);
		
		return newUI;
	}
}

class ViewSavesUIFunc {
	
	public static void slot1_Func1(int n) {
		ViewSaves.index = n;
		((InputSlot) ViewSaves.dSlot).setInput(SaveDataIO.saveDescriptions[n]);
		ViewSaves.sSlot.setActive(SaveDataIO.getIndex() != ViewSaves.index+1);
	}
	
	public static void slot2_Func1(Returner<String> rtr) {
		SaveDataIO.saveDescriptions[ViewSaves.index] = rtr.get();
		rtr.set(rtr.get());
		SaveDataIO.updateDescriptions();
	}
	
	public static void slot3_Func1(int n) {
		SaveDataIO.reloadSave(ViewSaves.index+1, true, ViewSaves.proteins, ViewSaves.spores, ViewSaves.resources, 
				ViewSaves.mineralVents, ViewSaves.blocks, ViewSaves.height);
		ViewSaves.sSlot.setActive(SaveDataIO.getIndex() != ViewSaves.index+1);
		SaveDataIO.updateDescriptions();
	}
	
	public static void slot4_Func1(int n) {
		SaveDataIO.reloadSave(ViewSaves.index+1, false, ViewSaves.proteins, ViewSaves.spores, ViewSaves.resources, 
				ViewSaves.mineralVents, ViewSaves.blocks, ViewSaves.height);
	}
	
	public static void slot5_Func1(int n) {
		SaveDataIO.updateSave(ViewSaves.index+1, ViewSaves.proteins, ViewSaves.spores, ViewSaves.resources, 
				ViewSaves.mineralVents, ViewSaves.blocks);
	}
}