package scripts.ui;

import java.util.ArrayList;

import scripts.data.SampleDataIO;
import scripts.slots.ActionSlot;
import scripts.slots.CycleSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class DeleteSample {
	
	protected static UI recentUI;
	
	public static UI getNewDeleteSampleUI(Point pos, InputControl inputCtrl) {
		recentUI = new UI(pos, "Delete Sample from Archive?");
		
		// SLOT 1
		UIEvent<Integer> event11 = new UIEvent<Integer>(DeleteSampleUIFunc::slot1_Func1, "Confirm");
		Slot slot1 = new ActionSlot(event11);
		
		// SLOT 2
		UIEvent<Integer> event21 = new UIEvent<Integer>(DeleteSampleUIFunc::slot2_Func1, "Cancel");
		Slot slot2 = new ActionSlot(event21);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		recentUI.setSlots(slots);
		
		return recentUI;
	}
}

class DeleteSampleUIFunc {
	
	public static void slot1_Func1(int n) {
		SampleDataIO.samples.remove(ViewSamples.index);
		SampleDataIO.saveSamples();
		((CycleSlot) ViewSamples.recentUI.getSlots().get(1)).getEvents().remove(ViewSamples.index);
		((CycleSlot) ViewSamples.recentUI.getSlots().get(1)).cycleLeft();
		DeleteSample.recentUI.requestPrevUI();
	}
	
	public static void slot2_Func1(int n) {
		DeleteSample.recentUI.requestPrevUI();
	}
}