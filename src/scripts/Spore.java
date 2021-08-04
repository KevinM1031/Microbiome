package scripts;

import java.util.LinkedList;

public class Spore {

	public static final int INCUBATION_TIME = 2800;
	public static final int INCUBATION_TIME_LONG = 19600;
	
	private static final double MAX_SPEED = 1.0;
	private static final double LONG_INCUBATION_CHANCE = 0.18;
	private static final double DECELERATION = 0.99;
	
	private Genome genome;
	private int energy;
	
	private double radius;
	private Point position;
	private Vector velocity;
	private boolean inMotion;
	private boolean longIncubation;
	
	private double age;
	private int incubationTime;
	
	public Spore(Genome genome, int energy, Point position, boolean far, boolean longIncubation) {
		this.genome = genome;
		this.energy = energy;
		this.position = position;
		
		this.radius = Math.max(Math.sqrt(genome.length()/20.0), 1);
		this.age = 0;
		
		if(longIncubation && Math.random() < LONG_INCUBATION_CHANCE) {
			this.incubationTime = INCUBATION_TIME_LONG;
			this.longIncubation = true;
					
		} else {
			this.incubationTime = INCUBATION_TIME;
			this.longIncubation = false;
		}
		
		double speed = far ? MAX_SPEED*4 : MAX_SPEED;
		double xRand = Math.random()*speed*2-speed;
		double yRand = Math.random()*speed*2-speed;
		this.velocity = new Vector(xRand, yRand);
		this.inMotion = true;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public Genome getGenome() {
		return genome;
	}
	
	public boolean longIncubation() {
		return longIncubation;
	}
	
	public int getAge() {
		return (int) age;
	}
	
	public void update(LinkedList<Spore> sporeRemoveList, LinkedList<Protein> proteins, int width, int height, long prevUpdateTime) {
		
		double timeElapsed = ((System.currentTimeMillis()-prevUpdateTime)/Microbiome.timeSpeed + 1);
						
		if(age >= incubationTime) {
			sporeRemoveList.add(this);
			
			if(proteins.size() < Microbiome.MAX_PROTEINS)
				proteins.add(new Protein(position, Math.random()*Math.PI*2, genome, energy));
			return;
		}
		
		if(inMotion) {
			
			velocity.multX(Math.pow(DECELERATION, timeElapsed));
			velocity.multY(Math.pow(DECELERATION, timeElapsed));
			position.add(velocity, timeElapsed);
			
			if(velocity.x < 0.01 && velocity.y < 0.01)
				inMotion = false;
			
			if(position.x < radius) {
				position.setX(radius);
				velocity.multX(-1);
				
			} else if(position.x >= width-radius) {
				position.setX(width-radius);
				velocity.multX(-1);
			} 
			
			if(position.y < radius) {
				position.setY(radius);
				velocity.multY(-1);
				
			} else if(position.y >= height-radius) {
				position.setY(height-radius);
				velocity.multY(-1);
			}
			
		}
		
		age += timeElapsed;
	}
}
