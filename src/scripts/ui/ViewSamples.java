package scripts.ui;

import java.util.ArrayList;

import scripts.data.Sample;
import scripts.data.SampleDataIO;
import scripts.data.SaveDataIO;
import scripts.slots.CycleSlot;
import scripts.slots.InputSlot;
import scripts.slots.LabeledCycleSlot;
import scripts.slots.LinkActionSlot;
import scripts.slots.LinkSlot;
import scripts.slots.LongInputSlot;
import scripts.slots.Slot;
import scripts.util.Point;
import scripts.util.Returner;

public class ViewSamples {
	
	protected static String sampleName = "";
	protected static String sampleGenome = "";
	protected static Slot genomeInputSlot, deteleSampleSlot, tSlot, sSlot;
	protected static UI insertProteinsUI;
	protected static int index = 0;
	
	protected static UI recentUI;
	
	protected static final int MIN_SAMPLES = 3;
	
	public static UI getNewViewSamplesUI(Point pos, InputControl inputCtrl, UI insertProteinsUI_, UI addSampleUI, UI deleteSampleUI) {
		recentUI = new UI(pos, "Archived Samples");
		insertProteinsUI = insertProteinsUI_;
		
		// SLOT 1
		Slot slot1 = new LinkSlot(recentUI, addSampleUI, "Add New Sample");
		
		// SLOT 2
		ArrayList<UIEvent<Integer>> events20 = new ArrayList<UIEvent<Integer>>();
		for (Sample s : SampleDataIO.samples) {
			UIEvent<Integer> event21 = new UIEvent<Integer>(ViewSamplesUIFunc::slot2_Func1, s.name);
			events20.add(event21);
		}
		if (SampleDataIO.samples.size() >= 1) {
			Sample s = SampleDataIO.samples.get(0);
			sampleName = s.name;
			sampleGenome = s.genome;
		}
		Slot slot2 = new CycleSlot(events20, index);
		
		// SLOT 3
		UIEvent<Returner<String>> event31 = new UIEvent<Returner<String>>(ViewSamplesUIFunc::slot3_Func1, "Genome");
		genomeInputSlot = new LongInputSlot(event31, sampleGenome, inputCtrl);
		
		// SLOT 4
		UIEvent<Integer> event41 = new UIEvent<Integer>(ViewSamplesUIFunc::slot4_Func1, "");
		Slot slot4 = new LinkActionSlot(recentUI, insertProteinsUI, event41, "Insert Sample to Environment");
		
		// SLOT 5
		UIEvent<Integer> event51 = new UIEvent<Integer>(ViewSamplesUIFunc::slot5_Func1, "Inactive");
		UIEvent<Integer> event52 = new UIEvent<Integer>(ViewSamplesUIFunc::slot5_Func1, "Active");
		ArrayList<UIEvent<Integer>> events5 = new ArrayList<UIEvent<Integer>>();
		events5.add(event51);
		events5.add(event52);
		sSlot = new LabeledCycleSlot("Scheduled Insert", events5, (SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1] < 0 ? 0 : 1));
		
		// SLOT 6
		UIEvent<Returner<String>> event61 = new UIEvent<Returner<String>>(ViewSamplesUIFunc::slot6_Func1, "Insert Interval");
		tSlot = new InputSlot(event61, Math.abs(SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1])+"", "", " s", inputCtrl);
		tSlot.setActive(SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1] > 0);
		
		// SLOT 7
		deteleSampleSlot = new LinkSlot(recentUI, deleteSampleUI, "Delete Sample");
		deteleSampleSlot.setActive(index >= ViewSamples.MIN_SAMPLES);
		
		// SUMMARY
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(slot1);
		slots.add(slot2);
		slots.add(genomeInputSlot);
		slots.add(slot4);
		slots.add(sSlot);
		slots.add(tSlot);
		slots.add(deteleSampleSlot);
		recentUI.setSlots(slots);
		
		return recentUI;
	}
}

class ViewSamplesUIFunc {
	
	public static void slot2_Func1(int n) {
		Sample s = SampleDataIO.samples.get(n);
		ViewSamples.sampleName = s.name;
		ViewSamples.sampleGenome = s.genome;
		((LongInputSlot) ViewSamples.genomeInputSlot).setInput(s.genome);
		((InputSlot) ViewSamples.tSlot).setInput(Math.abs(s.schedules[SaveDataIO.getIndex()-1])+"");

		ViewSamples.index = n;
		
		((InputSlot) ViewSamples.tSlot).setActive(SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1] > 0);
		((LabeledCycleSlot) ViewSamples.sSlot).setIndex(SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1] < 0 ? 0 : 1);
		ViewSamples.deteleSampleSlot.setActive(ViewSamples.index >= ViewSamples.MIN_SAMPLES);
	}

	
	public static void slot3_Func1(Returner<String> rtr) {
		SampleDataIO.samples.get(ViewSamples.index).genome = rtr.get();
		rtr.set(rtr.get());
		SampleDataIO.saveSamples();
	}
	
	public static void slot4_Func1(int n) {
		InsertProteins.genome = ViewSamples.sampleGenome;
		((LongInputSlot) ViewSamples.insertProteinsUI.getSlots().get(0)).setInput(ViewSamples.sampleGenome);
	}
	
	public static void slot5_Func1(int n) {
		int interval = Math.abs(SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1]);
		SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1] = interval * ((n == 0) ? -1 : 1);
		ViewSamples.tSlot.setActive(n != 0);
		SampleDataIO.saveSamples();
	}
	
	public static void slot6_Func1(Returner<String> rtr) {
		int n = SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1];
		try {
			n = Math.min(Integer.MAX_VALUE, Math.max(1, Integer.parseInt(rtr.get())));
		} catch(NumberFormatException e) {}
		
		SampleDataIO.samples.get(ViewSamples.index).schedules[SaveDataIO.getIndex()-1] = n;
		rtr.set(Math.abs(n)+"");
		SampleDataIO.saveSamples();
	}
}