package scripts.ui;

import java.util.ArrayList;

import scripts.slots.InterfaceSlot;
import scripts.slots.LinkSlot;
import scripts.slots.Slot;
import scripts.util.Point;

public class Interface {
	public static UI getNewInterfaceUI(Point pos, UI interactUI, UI configUI) {
		UI newUI = new UI(pos, "Simulation Interface");
		
		Slot slot1 = new InterfaceSlot();
		Slot slot2 = new LinkSlot(newUI, interactUI, "Environment Interaction");
		Slot slot3 = new LinkSlot(newUI, configUI, "System Configuration");
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(slot3);
		
		newUI.setSlots(slots);
		return newUI;
	}
}
