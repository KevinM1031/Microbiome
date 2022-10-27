package scripts.ui;

import java.util.ArrayList;
import java.util.LinkedList;

import scripts.objects.Genome;
import scripts.objects.Protein;
import scripts.objects.Spore;
import scripts.slots.ActionSlot;
import scripts.slots.InputSlot;
import scripts.slots.LabeledCycleSlot;
import scripts.slots.LongInputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class InsertProteins {
	
	protected static LinkedList<Protein> proteins;
	protected static LinkedList<Spore> spores;
	
	protected static Point insertPos = new Point(0, 0);
	protected static String genome = "";
	protected static int initState = 0, width, height;
	protected static Slot xSlot, ySlot;
	protected static boolean randomInsertPos = true;
	
	public static UI getNewInsertProteinsUI(Point pos, InputControl inputCtrl, String genome_, 
			LinkedList<Protein> proteins_, LinkedList<Spore> spores_, int width_, int height_) {
		
		genome = genome_;
		proteins = proteins_;
		spores = spores_;
		width = width_;
		height = height_;
		
		// SLOT 1
		UIEvent<Returner<String>> event11 = new UIEvent<Returner<String>>(InsertProteinsUIFunc::slot1_Func1, "Genome");
		Slot slot1 = new LongInputSlot(event11, genome, inputCtrl);
		
		// SLOT 2
		UIEvent<Integer> event21 = new UIEvent<Integer>(InsertProteinsUIFunc::slot2_Func1, "Random");
		UIEvent<Integer> event22 = new UIEvent<Integer>(InsertProteinsUIFunc::slot2_Func1, "Specified");
		ArrayList<UIEvent<Integer>> events2 = new ArrayList<UIEvent<Integer>>();
		events2.add(event21);
		events2.add(event22);
		Slot slot2 = new LabeledCycleSlot("Insert Position", events2, 0);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(InsertProteinsUIFunc::slot3_Func1, "x-Coordinate");
		xSlot = new InputSlot(event31, InsertProteins.insertPos.x+"", "", " px", inputCtrl);
		xSlot.setActive(false);
		
		// SLOT 4
		UIEvent<Returner<String>> event41 = new UIEvent<Returner<String>>(InsertProteinsUIFunc::slot4_Func1, "y-Coordinate");
		ySlot = new InputSlot(event41, InsertProteins.insertPos.y+"", "", " px", inputCtrl);
		ySlot.setActive(false);
		
		// SLOT 5
		UIEvent<Integer> event51 = new UIEvent<Integer>(InsertProteinsUIFunc::slot5_Func1, "Protein");
		UIEvent<Integer> event52 = new UIEvent<Integer>(InsertProteinsUIFunc::slot5_Func1, "Spore");
		UIEvent<Integer> event53 = new UIEvent<Integer>(InsertProteinsUIFunc::slot5_Func1, "Oocyst Spore");
		ArrayList<UIEvent<Integer>> events5 = new ArrayList<UIEvent<Integer>>();
		events5.add(event51);
		events5.add(event52);
		events5.add(event53);
		Slot slot5 = new LabeledCycleSlot("Initial State", events5, 0);
		
		// SLOT 6
		UIEvent<Integer> event61 = new UIEvent<Integer>(InsertProteinsUIFunc::slot6_Func1, "Insert");
		Slot slot6 = new ActionSlot(event61);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(xSlot);
		slots.add(ySlot);
		slots.add(slot5);
		slots.add(slot6);
		
		return new UI(pos, "Insert Proteins & Spores", slots);
	}
	
	public static UI getNewInsertProteinsUI(Point pos, InputControl inputCtrl, 
			LinkedList<Protein> proteins, LinkedList<Spore> spores, int width, int height) {
		return getNewInsertProteinsUI(pos, inputCtrl, "", proteins, spores, width, height);
	}
}

class InsertProteinsUIFunc {
	
	public static void slot1_Func1(Returner<String> rtr) {
		String str = "";
		try {
			str = rtr.get();
		} catch(NumberFormatException e) {}
		
		InsertProteins.genome = str;
		rtr.set(str);
	}
	
	public static void slot2_Func1(int n) {
		InsertProteins.randomInsertPos = (n == 0);
		InsertProteins.xSlot.setActive(n != 0);
		InsertProteins.ySlot.setActive(n != 0);
	}
	
	public static void slot3_Func1(Returner<String> rtr) {
		int n = (int) InsertProteins.insertPos.x;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		InsertProteins.insertPos.x = n;
		rtr.set(n+"");
	}
	
	public static void slot4_Func1(Returner<String> rtr) {
		int n = (int) InsertProteins.insertPos.y;
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(0, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		InsertProteins.insertPos.y = n;
		rtr.set(n+"");
	}
	
	public static void slot5_Func1(int n) {
		InsertProteins.initState = n;
	}
	
	public static void slot6_Func1(int n) {
		
		Genome g = new Genome(InsertProteins.genome);
		if (g.getSequence().length() == 0) return;
		
		if (InsertProteins.randomInsertPos)
			InsertProteins.insertPos = new Point(InsertProteins.width*Math.random(), InsertProteins.height*Math.random());

		switch(InsertProteins.initState) {
			case 0:
				InsertProteins.proteins.add(new Protein(InsertProteins.insertPos.clone(), 0, g));
				break;
			case 1:
				InsertProteins.spores.add(new Spore(g, InsertProteins.insertPos.clone(), false, false, true, 0));
				break;
			case 2:
				InsertProteins.spores.add(new Spore(g, InsertProteins.insertPos.clone(), false, true, true, 0));
				break;
		}
	}
}