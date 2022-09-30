package scripts.ui;

import java.util.ArrayList;

import scripts.slots.LinkSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class Interaction {
	public static UI getNewInteractionUI(Point pos, UI environmentConfigUI, UI insertProteinsUI, UI viewSamplesUI, UI disposeProteinsUI, UI addVentUI, UI viewSavesUI) {
		UI newUI = new UI(pos, "Environment Interaction");
		
		Slot slot1 = new LinkSlot(newUI, environmentConfigUI, "Environment Parameters");
		Slot slot2 = new LinkSlot(newUI, insertProteinsUI, "Insert Proteins & Spores");
		Slot slot3 = new LinkSlot(newUI, addVentUI, "Insert Mineral Vents");
		Slot slot4 = new LinkSlot(newUI, disposeProteinsUI, "Dispose Objects");
		Slot slot5 = new LinkSlot(newUI, viewSamplesUI, "View Archived Samples");
		Slot slot6 = new LinkSlot(newUI, viewSavesUI, "View Saves");
		
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
