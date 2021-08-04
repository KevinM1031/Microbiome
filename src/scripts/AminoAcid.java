package scripts;

import java.awt.Color;
import java.util.LinkedList;

public class AminoAcid {
	
	public static final int ENERGY_STORAGE = 5000;
	public static final int BASE_STORAGE = 3;
	public static final int MINERAL_STORAGE = 4;
	public static final int FR_STORAGE = 10;
	
	public static final int RADIUS = 1;
	public static final int BORDER = 1;
	public static final int TRUE_RADIUS = RADIUS + BORDER;
	public static final int RESOURCE_PICKUP_RADIUS_BONUS = 2;
	
	public static final double NA_ROTATION_SPEED = 0.5;
	public static final double ND_RADIUS = TRUE_RADIUS*3;
	public static final int ND_ENERGY = 40;
	public static final double NP_RADIUS = TRUE_RADIUS*8;
	public static final int NP_ENERGY = 240;
	public static final int PA_ENERGY = 10;
	public static final int PN_ENERGY = 540;
	public static final double AP_RADIUS = 20;
	public static final double DN_TEMPERATURE = 11;
	public static final double DN_ENERGY = 4;
	public static final double DD_TEMPERATURE = -6;
	public static final double PD_TEMPERATURE = 4;
	public static final double DP_REDUCTION = 10;
	public static final int AN_RADIUS = 24;
	public static final int AA_RADIUS = 25;
	public static final int AD_RADIUS = 31;
	
	private Base base1;
	private Base base2;
	private Base base3;
	
	private Point position;
	private Protein protein;
	
	private boolean active;
	private AminoAcid pair;
	private double speed;
	
	private int branchCount;
	private int mineralCount;
	private int mineralPair;
	private int tier;
	private Color color;
	
	public AminoAcid(Protein protein, Base base1, Base base2, Base base3, Point position) {
		this.base1 = base1;
		this.base2 = base2;
		this.base3 = base3;
		this.protein = protein;
		this.position = position;
		this.active = false;
		this.speed = 0;
		
		this.pair = null;
		this.tier = base3.toInt();
		
		this.branchCount = initBranchCount();
		this.mineralCount = initMineralCount();
		this.mineralPair = initMineralPair();
		this.color = initColor();
	}
	
	public void checkActivity() {
				
		if(typeEquals("PA")) {
			active = checkActivity_PA();
		}
		
		else if(typeEquals("PN")) {
			active = checkActivity_PN();
		}
		
		else if(typeEquals("NP")) {
			active = true;
		}
		
		else if(typeEquals("DA")) {
			active = true;
			speed += tier;
		}
		
		else if(typeEquals("NN") && (pair = getPair_NN()) != null) {
			active = true;
			speed += 2*tier + 2*pair.getTier();
		}
		
		else if(typeEquals("ND") || typeEquals("PP") || typeEquals("DP")) {
			active = true;
		}
		
	}
		
	public void update(LinkedList<Protein> proteins, LinkedList<Protein> proteinRemoveList, LinkedList<Spore> spores,
			LinkedList<Resource> resources, LinkedList<Resource> resourceRemoveList, int width, int height, long prevUpdateTime) {
		
		double timeElapsed = (System.currentTimeMillis()-prevUpdateTime)/Microbiome.timeSpeed + 1;
		double tempMod = Utility.tempMod(protein.getTemperature());
		double modifier = timeElapsed * tempMod * tier;
		double tempModPA = Utility.tempModPA(protein.getTemperature());
		
		if(active) {
			
			// photosynthesis
			if(typeEquals("PA")) {
				protein.getStorage().addEnergy((int) (PA_ENERGY*modifier*Environment.getBrightness(position, height)));
								
				switch((int) (Math.random()*Math.max(750.0/timeElapsed*tempModPA, 4))) {
					case 1: protein.getStorage().addN(tier); break;
					case 2: protein.getStorage().addA(tier); break;
					case 3: protein.getStorage().addD(tier); break;
					case 4: protein.getStorage().addP(tier); break;
				}
				
				if((int) (Math.random()*300.0/timeElapsed*tempModPA) == 1)
					protein.getStorage().addPh(tier);
				if((int) (Math.random()*800.0/timeElapsed*tempModPA) == 1)
					protein.getStorage().addIo(tier);
				
				switch((int) (Math.random()*Math.max(90000.0/timeElapsed*tempModPA, 3))) {
					case 1: protein.getStorage().addCr(tier); break;
					case 2: protein.getStorage().addNc(tier); break;
				}
			}
			
			// fr generator
			if(typeEquals("PN") && protein.getStorage().getFr() > protein.getStorage().getMaxFr()/2) {
				protein.getStorage().removeFr(1);
				
				protein.getStorage().addEnergy((int) (PN_ENERGY*tier));
				
				switch((int) (Math.random()*50.0)) {
					case 1: protein.getStorage().addN(tier); break;
					case 2: protein.getStorage().addA(tier); break;
					case 3: protein.getStorage().addD(tier); break;
					case 4: protein.getStorage().addP(tier); break;
				}
			}
			
			// rapid movement
			else if(typeEquals("NN")) {
				pair.rotate(position, NA_ROTATION_SPEED*modifier);
			}
			
			// short range versatile dissolver
			else if(typeEquals("ND") && protein.isHunting()) {
				for(Protein protein : proteins) {
					if(protein.equals(this.protein) || protein.getPosition().distanceTo(this.protein.getPosition()) > Protein.INTERACTION_RADIUS)
						continue;
					
					for(AminoAcid acid : protein.getAcids()) {
						
						double dist = position.distanceTo(acid.getPosition());
						if(dist <= getNDRadius() + AminoAcid.TRUE_RADIUS && protein.isAlive()) {
							protein.kill(proteinRemoveList, resources, width, height);
						}
					}
				}
			}
			
			// long range specialized dissolver
			else if(typeEquals("NP") && protein.isHunting()) {
				for(Protein protein : proteins) {
					if(protein.getPosition().distanceTo(this.protein.getPosition()) > Protein.INTERACTION_RADIUS)
						continue;
					
					for(AminoAcid acid : protein.getAcids()) {
						
						double dist = position.distanceTo(acid.getPosition());
						
						if(protein.equals(this.protein) && dist <= getNPRadius() + AminoAcid.TRUE_RADIUS) {
							if(acid.typeEquals("PA") || acid.typeEquals("PN") || acid.typeEquals("AA"))
								protein.kill(proteinRemoveList, resources, width, height);
						}
						
						else if(dist <= getNPRadius() + AminoAcid.TRUE_RADIUS && dist > RADIUS && protein.isAlive())
							protein.kill(proteinRemoveList, resources, width, height);
					}
				}
			}
			
			// spore production
			else if(typeEquals("PP")) {
				if(spores.size() < Microbiome.MAX_SPORES && protein.getGenome().canReplicate(protein.getStorage())) {
					spores.add(protein.getGenome().replicate(protein.getStorage(), position.clone(), tier));
					protein.addSporeCount();
				}
			}
			
			// rapid movement - part b
			else if(typeEquals("NN")) {
				pair.rotate(position, NA_ROTATION_SPEED*modifier);
			}
		}
		
		// pick up resources
		double dist;
		for(Resource resource : resources) {
			
			dist = resource.getRadius() + TRUE_RADIUS + RESOURCE_PICKUP_RADIUS_BONUS;
			if(typeEquals("AP") && protein.isGathering())
				dist += getAPRadius();
			
			if(protein.getStorage().canStore(resource) && position.distanceTo(resource.getPosition()) < dist) {
				
				resource.addAmount(-protein.getStorage().addResource(resource));
				
				if(resource.getType() == Resource.ENERGY) resource.setAmount(0);
				else resource.addAmount(-protein.getStorage().addResource(resource));
				
				if(resource.getAmount() == 0) resourceRemoveList.add(resource);
			}
		}
	}
	
	public Protein getProtein() {
		return protein;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public Base getBase1() {
		return base1;
	}
	
	public Base getBase2() {
		return base2;
	}
	
	public Base getBase3() {
		return base3;
	}
	
	public int getBranchCount() {
		return branchCount;
	}
	
	public int getMineralPair() {
		return mineralPair;
	}
	
	public int getMineralCount() {
		return mineralCount;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getTier() {
		return tier;
	}
	
	public double getNDRadius() {
		return ND_RADIUS*Utility.tempMod(protein.getTemperature())*Math.sqrt(tier);
	}
	
	public double getNPRadius() {
		return NP_RADIUS*Utility.tempMod(protein.getTemperature())*Math.sqrt(tier);
	}
	
	public double getAPRadius() {
		return AP_RADIUS*Utility.tempMod(protein.getTemperature())*Math.sqrt(tier);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public String getCode() {
		return "" + base1.toChar() + base2.toChar() + base3.toChar();
	}
	
	public boolean typeEquals(String type) {
		return base1.equals(type.charAt(0)) && base2.equals(type.charAt(1));
	}
	
	public void rotate(double radians) {
		position.rotate(protein.getPosition(), radians);
	}
	
	public void rotate(Point pivot, double radians) {
		position.rotate(pivot, radians);
	}
	
	public void move(Vector velocity) {
		position.add(velocity);
	}
	
	private boolean checkActivity_PA() {
		int neighbor = 0;
		for(AminoAcid acid : protein.getAcids()) {
			if(position.distanceTo(acid.getPosition()) < TRUE_RADIUS*2 && acid.typeEquals("PA"))
				neighbor++;
		}
		return neighbor >= 2;
	}
	
	private boolean checkActivity_PN() {
		int neighbor = 0;
		for(AminoAcid acid : protein.getAcids()) {
			if(position.distanceTo(acid.getPosition()) < TRUE_RADIUS*2 && acid.typeEquals("PN"))
				neighbor++;
		}
		return neighbor >= 2;
	}
	
	private AminoAcid getPair_NN() {
		boolean foundNA = false;
		boolean foundNN = false;
		
		for(AminoAcid acid : protein.getAcids()) {
			if(position.distanceTo(acid.getPosition()) < TRUE_RADIUS*2 && acid.typeEquals("NA")) {
				
				for(AminoAcid acid2 : protein.getAcids()) {
					if(acid.getPosition().distanceTo(acid2.getPosition()) < TRUE_RADIUS*2) {
						if(acid2.typeEquals("NA") && !foundNA) foundNA = true;
						else if(acid2.typeEquals("NN") && !foundNN) foundNN = true;
						else return null;
					}
				}
				return acid;
			}
		}
		return null;
	}
	
	private Color initColor() {
		switch(getCode().substring(0, 2)) {
			case "DA": return Color.getHSBColor(0.58f, 0.69f, 1f); // light blue
			case "NN": return Color.getHSBColor(0.65f, 0.87f, 1f); // light indigo
			case "NA": return Color.getHSBColor(0.5f, 0.45f, 0.75f); // light cyan
			case "AN": return Color.getHSBColor(0.87f, 0.73f, 0.69f); // sdark pink
			case "AA": return Color.getHSBColor(0.71f, 0.78f, 0.63f); // light purple
			case "AD": return Color.getHSBColor(0.07f, 0.59f, 0.96f); // light orange
			case "PP": return Color.getHSBColor(0.86f, 0.25f, 1f); // light magenta
			case "AP": return Color.getHSBColor(0f, 0f, 0.65f); // light gray
			case "PA": return Color.getHSBColor(0.33f, 1f, 0.9f); // green
			case "PN": return Color.getHSBColor(0f, 0f, 0.45f); // gray
			case "PD": return Color.getHSBColor(0f, 0f, 0.15f); // vdark gray
			case "ND": return Color.getHSBColor(0.06f, 1f, 0.92f); // orange
			case "NP": return Color.getHSBColor(0.94f, 1f, 0.92f); // red
			case "DN": return Color.getHSBColor(0.14f, 1f, 1f); // yellow
			case "DD": return Color.getHSBColor(0.66f, 0.5f, 1f); // light indigo
			case "DP": return Color.getHSBColor(0f, 0f, 0.25f); // dark gray
			default: return Color.BLACK;
		}
	}
	
	private int initMineralPair() {
		return getMineralPair(getCode().substring(0, 2));
	}
	
	private int initBranchCount() {
		switch(base1.getBase()) {
			case Base.N: return 2;
			case Base.A: return 3;
			case Base.D: return 4;
			case Base.P: return 6;
			default: return 0;
		}
	}
	
	private int initMineralCount() {
		switch(base3.getBase()) {
			case Base.N: return 0;
			case Base.A: return 1;
			case Base.D: return 2;
			case Base.P: return 3;
			default: return 0;
		}
	}
	
	public static int getMineralPair(String first2Bases) {
		switch(first2Bases) {
			case "DA": return Mineral.Io;
			case "NN": return Mineral.Io;
			case "NA": return Mineral.Nc;
			case "AN": return Mineral.Ph;
			case "AA": return Mineral.Ph;
			case "AD": return Mineral.Ph;
			case "PP": return Mineral.Io;
			case "AP": return Mineral.Fr;
			case "PA": return Mineral.Ph;
			case "PN": return Mineral.Fr;
			case "PD": return Mineral.Fr;
			case "ND": return Mineral.Cr;
			case "NP": return Mineral.Cr;
			case "DN": return Mineral.Nc;
			case "DD": return Mineral.Nc;
			case "DP": return Mineral.Io;
			default: return 0;
		}
	}
}
