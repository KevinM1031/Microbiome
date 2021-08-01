package scripts;

import java.util.LinkedList;

public class Spore {

	public static final int INCUBATION_TIME = 3800;
	
	private static final double MAX_VELOCITY = 1.0;
	private static final double DECELERATION = 0.99;
	
	private Genome genome;
	private int energy;
	
	private double radius;
	private Point position;
	private Vector velocity;
	private boolean inMotion;
	
	private int age;
	private int incubationTime;
	
	// TODO can do: short+quick | long+quick | short+durable | long+durable
	// durable means there is a chance of creating a spore that has much longer incubation time
	
	public Spore(Genome genome, int energy, Point position) {
		this.genome = genome;
		this.energy = energy;
		this.position = position;
		
		this.radius = Math.max(Math.sqrt(genome.length()/20.0), 1);
		this.age = 0;
		this.incubationTime = INCUBATION_TIME;
		
		double xRand = Math.random()*MAX_VELOCITY*2-MAX_VELOCITY;
		double yRand = Math.random()*MAX_VELOCITY*2-MAX_VELOCITY;
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
	
	public int getAge() {
		return age;
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
