package scripts.objects;

import java.util.LinkedList;

import scripts.Microbiome;
import scripts.data.ConfigDataIO;
import scripts.data.SaveDataIO;
import scripts.util.Point;
import scripts.util.Utility;
import scripts.util.Vector;

public class Protein {
	
	// constants
	public static final double FRICTION = 0.01;
	public static final double ROTATION_SPEED = 0.05;
	public static final double MAX_ROTATION_SPEED = 0.2;
	public static final double BASE_SPEED = 40;
	public static final int ACTION_INTERVAL = 50;
	public static final double RADIUS_BUFFER_MULT = 3;
	public static final int MAX_PERSISTENCE = 500;
	public static final int MAX_FATIGUE = 0;
	
	public static int INTERACTION_RADIUS;
	
	private static final double PREFERRED_TEMP_RANGE = 5;
	
	private static final int IDLE = 5001;
	private static final int HUNTING = 5002;
	private static final int GATHERING = 5003;
	private static final int ESCAPING = 5004;
	private static final int RETURNING = 5005;
	private static final int WANDERING = 5006;
	
	private LinkedList<AminoAcid> acids;

	// physics behavior
	private Point position;
	private Vector velocity;
	private double speed;
	private double speedAmplifier;
	private double rotation;
	private double rotationGoal;
	private double temperature;
	
	// persuit/movement behavior
	private int age;
	private int nextActionAge;
	private int persistence;
	private int fatigue;
	private boolean alive;
	private int state;
	
	private Polypeptide info;
	private double radius;
	
	private final double predatorVision;
	private final double preyVision;
	private final double resourceVision;
	
	// Energy
	private int energySpent;
	private int huntingEnergy;
	private int heatingEnergy;
	private int prevEnergy;
	private int currEnergy;
	
	private double mass;
	private Storage storage;
	private Genome genome;
	private int sporeCount;
	private double addTemp;
	private double threatReduction;
	private double preferredTemp;
	private double buoyancy;
	
	public Protein(Point position, double rotation, Genome genome, int energy,
			int n, int a, int d, int p, int ph, int cr, int nc, int io, int fr, int age, int sporeCount) {
				
		this.age = age;
		this.nextActionAge = 0;
		this.acids = new LinkedList<AminoAcid>();
		this.alive = true;
		this.state = IDLE;
		
		this.position = position;
		this.velocity = new Vector(0, 0);
		this.rotation = 0;
		this.rotationGoal = 0;
		this.info = genome.translate(this, acids);
		this.genome = genome;
		
		INTERACTION_RADIUS = ConfigDataIO.interaction_radius;
		
		this.persistence = MAX_PERSISTENCE;
		this.fatigue = MAX_FATIGUE;
		
		this.speed = 0;
		this.speedAmplifier = 1;
		this.huntingEnergy = 0;
		this.heatingEnergy = 0;
		this.preferredTemp = SaveDataIO.temperature;
		this.buoyancy = 0;
		this.addTemp = 0;
		this.threatReduction = 0;
		int countAN = 0, countAA = 0, countAD = 0;
		double maxDist = 0, currDist = 0;
		for(AminoAcid acid : acids) {
			acid.checkActivity();
			
			if(acid.typeEquals("ND"))
				this.huntingEnergy += SaveDataIO.ND_energy*acid.getTier();
			
			else if(acid.typeEquals("NP"))
				this.huntingEnergy += SaveDataIO.NP_energy*acid.getTier();
			
			else if(acid.typeEquals("DN")) {
				addTemp += SaveDataIO.DN_temperature*acid.getTier();
				heatingEnergy += SaveDataIO.DN_energy*acid.getTier();
				
			} else if(acid.typeEquals("DD"))
				buoyancy += SaveDataIO.DD_buoyancy*(acid.getTier()-2);
				if (buoyancy < SaveDataIO.DD_min_buoyancy) buoyancy = SaveDataIO.DD_min_buoyancy;
			
			else if(acid.typeEquals("PD")) {
				switch(acid.getTier()) {
					case 1: preferredTemp += SaveDataIO.PD_temperature; break;
					case 2: preferredTemp += SaveDataIO.PD_temperature*2; break;
					case 3: preferredTemp -= SaveDataIO.PD_temperature; break;
					case 4: preferredTemp -= SaveDataIO.PD_temperature*2; break;
				}
			
			} else if(acid.typeEquals("DP")) {
				switch(acid.getTier()) {
				case 1: threatReduction += SaveDataIO.DP_reduction; break;
				case 2: threatReduction += SaveDataIO.DP_reduction*2; break;
				case 3: threatReduction -= SaveDataIO.DP_reduction; break;
				case 4: threatReduction -= SaveDataIO.DP_reduction*2; break;
			}
			
			} else if(acid.typeEquals("AN"))
				countAN += acid.getTier();
			
			else if(acid.typeEquals("AA"))
				countAA += acid.getTier();
			
			else if(acid.typeEquals("AD"))
				countAD += acid.getTier();
			
			this.speed += acid.getSpeed();
			currDist = acid.getPosition().distanceTo(position);
			maxDist = (currDist <= maxDist) ? maxDist : currDist;
		}
		this.radius = maxDist + AminoAcid.TRUE_RADIUS;
		
		this.preyVision = Math.sqrt(countAN)*SaveDataIO.AN_radius;
		this.predatorVision = Math.sqrt(countAA)*SaveDataIO.AA_radius;
		this.resourceVision = Math.sqrt(countAD)*SaveDataIO.AD_radius;
		
		this.storage = new Storage((energy == -1) ? getMaxEnergy()/2 : energy, n, a, d, p, ph, cr, nc, io, fr, getMaxEnergy(),
				info.length()*AminoAcid.BASE_STORAGE, info.length()*AminoAcid.MINERAL_STORAGE, info.length()*AminoAcid.FR_STORAGE);
		
		this.prevEnergy = this.currEnergy = storage.getEnergy();
		updateMass();
		this.addTemp /= mass;
		this.temperature = Environment.getTemperature(position, Microbiome.HEIGHT) + addTemp;
		this.energySpent = 0;
		this.sporeCount = sporeCount;
	}
	
	public Protein(Point position, double rotation, Genome genome, int energy) {
		this(position, rotation, genome, energy, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public Protein(Point position, double rotation, Genome genome) {
		this(position, rotation, genome, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void move(int width, int height, Vector velocity) {
		double xAdj = 0;
		double yAdj = 0;
		
		double cx, cy, r;
		
		for(AminoAcid a : acids) {
			a.move(velocity);
			
			cx = a.getPosition().x;
			cy = a.getPosition().y;
			r = AminoAcid.TRUE_RADIUS;
			
			if(cx-r < 0 && xAdj < r-cx) xAdj = r-cx;
			else if(cx+r >= width && width-cx-r < xAdj) xAdj = width-cx-r;
			
			if(cy-r < 0 && yAdj < r-cy) yAdj = r-cy;
			else if(cy+r >= height && height-cy-r < yAdj) yAdj = height-cy-r;
		}
		this.position.add(velocity);
		
		velocity = new Vector(xAdj, yAdj);
		
		if(xAdj != 0 || yAdj != 0) {
			for(AminoAcid a : acids)
				a.move(velocity);
			this.position.add(velocity);
		}
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public void setRotation(int width, int height, double radians) {
		rotate(width, height, radians-rotation);
	}
	
	public void rotate(int width, int height, double radians) {
		double xAdj = 0;
		double yAdj = 0;
		
		for(AminoAcid a : acids) {
			a.rotate(radians);
			
			double cx = a.getPosition().x;
			double cy = a.getPosition().y;
			double r = AminoAcid.TRUE_RADIUS;
			
			if(cx-r < 0 && xAdj < r-cx) xAdj = r-cx;
			if(cx+r >= width && width-cx-r > xAdj) xAdj = width-cx-r;
			
			if(cy-r < 0 && yAdj < r-cy) yAdj = r-cy;
			if(cy+r >= height && height-cy-r > yAdj) yAdj = height-cy-r;
		}
		this.rotation += radians;
		
		velocity = new Vector(xAdj, yAdj);
		
		if(xAdj != 0 || yAdj != 0) {
			for(AminoAcid a : acids)
				a.move(velocity);
			this.position.add(velocity);
		}
	}
	
	public LinkedList<AminoAcid> getAcids() {
		return acids;
	}
	
	public int getAcidCount() {
		return info.length();
	}
	
	public double getRadius() {
		return radius;
	}
	
	// useful when obj size changed
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public double getPredatorVision() {
		return predatorVision*ConfigDataIO.detection_radius;
	}
	
	public double getPreyVision() {
		return preyVision*ConfigDataIO.detection_radius;
	}
	
	public double getResourceVision() {
		return resourceVision*ConfigDataIO.detection_radius;
	}
	
	public double getRotationGoal() {
		return rotationGoal;
	}
	
	public Polypeptide getInfo() {
		return info;
	}
	
	public void setInfo(Polypeptide info) {
		this.info = info;
	}
	
	public Storage getStorage() {
		return storage;
	}
	
	public Genome getGenome() {
		return genome;
	}
	
	public int getMaxEnergy() {
		return (int) Math.pow(info.length(), 2)*AminoAcid.ENERGY_STORAGE;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public boolean isHunting() {
		return state == HUNTING;
	}
	
	public boolean isGathering() {
		return state == GATHERING;
	}
	
	public int getAge() {
		return age;
	}
	
	public int getGeneration() {
		return genome.getGeneration();
	}
	
	public int getEnergyUsage() {
		return prevEnergy - currEnergy;
	}
	
	public double getMass() {
		return mass;
	}
	
	public void updateMass() {
		mass = /*(storage.getPh()+storage.getCr()+storage.getNc()+storage.getIo())/25.0 + storage.getFr()/10.0 + */info.length();
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getSpeedAmplifier() {
		return speedAmplifier;
	}
	
	public Vector getVelocity() {
		return velocity;
	}
	
	public double getBuoyancy() {
		return buoyancy;
	}
	
	public void addSporeCount() {
		sporeCount++;
	}
	
	public int getSporeCount() {
		return sporeCount;
	}
	
	public double getTemperature() {
		return temperature;
	}
	
	public double getPreferredTemp() {
		return preferredTemp;
	}
	
	public double getPerceivedThreatLevel() {
		return Math.max(info.getThreatLevel() - threatReduction, 0);
	}
	
	public String getState() {
		String str = "Unknown";
		switch(state) {
			case IDLE: str = "Idle"; break;
			case HUNTING: str = "Hunting"; break;
			case GATHERING: str = "Gathering"; break;
			case ESCAPING: str = "Escaping"; break;
			case RETURNING: str = "Returning"; break;
			case WANDERING: str = "Wandering"; break;
		}
		
		if (storage.getEnergy() < storage.getMaxEnergy()*SaveDataIO.starvation_threshold)
			str += " (starving)";
		
		if (willIgnoreTemp())
			str += " (ignoring temperature)";
		
		return str;
	}
	
	public void kill(LinkedList<Protein> proteinRemoveList, LinkedList<Resource> resources, int width, int height) {
		if(!alive) return;
		alive = false;
		proteinRemoveList.addFirst(this);
		
		for(AminoAcid acid : acids) {
			storage.addResource(acid.getBase1().getBase(), 1);
			storage.addResource(acid.getBase2().getBase(), 1);
			storage.addResource(acid.getBase3().getBase(), 1);
			storage.addResource(acid.getMineralPair(), acid.getMineralCount());
		}
		
		storage.spill(position, info.length()*AminoAcid.TRUE_RADIUS, resources, width, height);
	}
		
	public void update(LinkedList<Protein> proteins, LinkedList<Protein> proteinRemoveList, 
			LinkedList<Resource> resources, LinkedList<Resource> resourceRemoveList, int width, int height, long prevUpdateTime) {
		
		prevEnergy = currEnergy;
		
		updateMass();
		
		temperature = Environment.getTemperature(position, width) + addTemp;
		double timeElapsed = (System.currentTimeMillis()-prevUpdateTime)/Microbiome.timeSpeed + 1;
		double tempMod = Utility.tempMod(temperature);
		double modifier = timeElapsed;
		
		energySpent = (int) (Math.pow(speed/10, 2)*mass + mass + heatingEnergy);
		
		if(isHunting())
			energySpent += huntingEnergy;
				
		if(!storage.removeEnergy((int) (energySpent*modifier*Math.pow(tempMod, 3)))) {
			kill(proteinRemoveList, resources, width, height);
			return;
		}
		
		if(speed > 0 || info.getThreatLevel() > 0) {
			Point p;
			
			// Avoiding predator
			if((p = nearestPredator(proteins)) != null) {
				state = ESCAPING;
				
				// Avoiding wall
				if(!Utility.circleRectCollision(position, getRadius(), 0, 0, width, height)) {
					rotationGoal = Utility.angleToPoint(position, p) + Math.PI;
													
				} else {
					p = new Point(position.x, position.y + getRadius()*RADIUS_BUFFER_MULT);
					p.rotate(position, rotationGoal);
					
					if(Utility.pointRectCollision(p, 0, 0, width, height)) {
						rotationGoal = rotationGoal + Math.random()*2*Math.PI - Math.PI;
					}
				}
				
			// Approaching resource
			} else if(persistence > 0 && (p = nearestResource(resources)) != null) {
				state = GATHERING;
				rotationGoal = Utility.angleToPoint(position, p);
				persistence--;
							
			// Chasing prey
			} else if(persistence > 0 && (p = nearestPrey(proteins)) != null) {
				state = HUNTING;
				rotationGoal = Utility.angleToPoint(position, p);
				persistence--;
				
			// Returning to higher temperature
			} else if(speed > 0 && temperature+PREFERRED_TEMP_RANGE < preferredTemp && !willIgnoreTemp()) {
				state = RETURNING;
				rotationGoal = Environment.higherTempDirection();
				
			// Returning to lower temperature
			} else if(speed > 0 && temperature-PREFERRED_TEMP_RANGE > preferredTemp && !willIgnoreTemp()) {
				state = RETURNING;
				rotationGoal = Environment.lowerTempDirection();
			
			// Wandering
			} else {
				
				state = IDLE;
				p = new Point(position.x, position.y + getRadius()*RADIUS_BUFFER_MULT);
				p.rotate(position, rotationGoal);
				
				// Avoiding wall
				if(Utility.pointRectCollision(p, 0, 0, width, height)) {
					rotationGoal = rotationGoal + Math.random()*2*Math.PI - Math.PI;
 				
				// Spontaneous motion
				} else if(age >= nextActionAge) {
					rotationGoal = rotationGoal + Math.random()*Math.PI - Math.PI/2;
					nextActionAge += ACTION_INTERVAL;
				}
				
				if(fatigue == 0) persistence = MAX_PERSISTENCE;
			}
			
			if(fatigue > 0) {
				fatigue--;
				if(fatigue == 0) persistence = MAX_PERSISTENCE;
			} else if(persistence == 0) fatigue = MAX_FATIGUE;
			
		} else {
			state = IDLE;
		}
				
		double rotationSpeed = (persistence < MAX_PERSISTENCE) ? MAX_ROTATION_SPEED : ROTATION_SPEED;
		rotationSpeed *= modifier;
		
		double radians = (rotationGoal - rotation) % (Math.PI*2.0);
		if(radians > Math.PI) radians -= Math.PI*2.0; 
		else if(radians < -Math.PI) radians += Math.PI*2.0;
		radians = radians * Math.min(1, rotationSpeed);
		if (Math.abs(radians) > 0.0001) rotate(width, height, radians);
				
		// applying buoyancy
		double upRotation = rotation % (Math.PI*2);
		speedAmplifier = tempMod;
		if (upRotation < 0) upRotation += Math.PI*2;
		if (upRotation < Math.PI*1.5 && upRotation > Math.PI/2) 
			speedAmplifier += buoyancy*(Math.PI/2-Math.abs(upRotation-Math.PI)/(Math.PI/2));
		
		velocity = new Vector(0, (speed*modifier*speedAmplifier)/mass);
		velocity.rotate(rotation);
		move(width, height, velocity);
		
		currEnergy = storage.getEnergy();
		age += timeElapsed;
	}
	
	private Point nearestPredator(LinkedList<Protein> proteins) {
		Point p = null;
		double min = Double.MAX_VALUE;
		double curr;
		
		for(Protein protein : proteins) {
			curr = protein.getPosition().distanceTo(position);
			if(curr <= getPredatorVision() && isPredator(protein) && curr < min) {
				min = curr;
				p = new Point(protein.getPosition().x, protein.getPosition().y);
			}
		}
		
		return p;
	}
	
	private Point nearestPrey(LinkedList<Protein> proteins) {
		Point p = null;
		double min = Double.MAX_VALUE;
		double curr;
		
		for(Protein protein : proteins) {
			curr = protein.getPosition().distanceTo(position);
			if(curr <= getPreyVision() && isPrey(protein) && curr < min) {
				min = curr;
				p = new Point(protein.getPosition().x, protein.getPosition().y);
			}
		}
		
		return p;
	}
	
	private Point nearestResource(LinkedList<Resource> resources) {
		Point p = null;
		double min = Double.MAX_VALUE;
		double curr;
		
		for(Resource resource : resources) {
			curr = resource.getPosition().distanceTo(position);
			if(curr <= getResourceVision() && storage.canStore(resource) && curr < min) {
				min = curr;
				p = new Point(resource.getPosition().x, resource.getPosition().y);
			}
		}
		
		return p;
	}
	
	private boolean isPredator(Protein p) {
		return !equals(p) && p.getPerceivedThreatLevel() > info.getThreatLevel()*SaveDataIO.predator_threat_level;
	}
	
	private boolean isPrey(Protein p) {
		return !equals(p) && p.getPerceivedThreatLevel() < info.getThreatLevel()*SaveDataIO.prey_threat_level && 
				(storage.getEnergy() > storage.getMaxEnergy()*SaveDataIO.starvation_threshold || 
						p.getStorage().getEnergy() < storage.getMaxEnergy()*SaveDataIO.prey_min_energy);
	}
	
	private boolean willIgnoreTemp() {
		return storage.getEnergy() > storage.getMaxEnergy()*SaveDataIO.ignore_temperature_upper_bound || 
				storage.getEnergy() < storage.getMaxEnergy()*SaveDataIO.ignore_temperature_lower_bound;
	}
}
