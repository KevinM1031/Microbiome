package scripts.ui;

import java.util.ArrayList;

import scripts.data.ConfigDataIO;
import scripts.data.SaveDataIO;
import scripts.slots.InputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class AminoAcidConfig {
	public static UI getNewAminoAcidConfigUI(Point pos, InputControl inputCtrl) {
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(AminoAcidConfigUIFunc::slot1_Func1, "Energy");
		Slot slot1 = new InputSlot(event11, ConfigDataIO.background_brightness+"", "", " nJ/ms", inputCtrl);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		
		return new UI(pos, "Photosynthesis (PA) Output Configuration", slots);
	}
}

class AminoAcidConfigUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		int n = 0;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SaveDataIO.PA_energy = n;
		rtr.set(n+"");
		
		SaveDataIO.updateConfig();
	}
}