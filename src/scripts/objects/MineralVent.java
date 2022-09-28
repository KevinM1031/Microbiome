package scripts.objects;

import java.util.LinkedList;

import scripts.Microbiome;
import scripts.util.Point;
import scripts.util.Vector;

public class MineralVent {
	
	private Point position;
	private double idle;
	
	private double rate;
	private double speed;
	private int amount;
	
	public MineralVent(double x, int height, double rate, double speed, int amount) {
		this.position = new Point(x, height-1);
		this.idle = 0;
		
		this.rate = rate;
		this.speed = speed;
		this.amount = amount;
	}
	
	public void update(LinkedList<Resource> resources, int width, int height, long prevUpdateTime) {
		
		double timeElapsed = ((System.currentTimeMillis()-prevUpdateTime)/Microbiome.timeSpeed + 1);
		
		position.y = height-1;
		
		if(position.x < 0)
			position.setX(0);
		else if(position.x >= width)
			position.setX(width);
		
		if(position.y < 0)
			position.setY(0);
		else if(position.y >= height)
			position.setY(height);
		
		if(idle >= rate) {
			
			Point pos = position.clone();
			pos.x += Math.random()*speed*40 - speed*10;
			pos.y -= Math.random()*speed*40;
			
			double xRand = Math.random()*speed*2 - speed;
			double yRand = Math.random()*-speed - speed;
			Vector velocity = new Vector(xRand, yRand);
			int aRand = (int) (Math.random()*amount + amount*0.5);
			resources.add(new Resource(pos, randomType(), aRand, velocity));
			
			idle = 0;
						
		} else {
			idle += timeElapsed;
		}
		
	}
	
	public double getX() {
		return position.x;
	}
	
	public double getHeight() {
		return position.y+1;
	}
	
	public double getRate() {
		return rate;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public int getAmount() {
		return amount;
	}
	
	private int randomType() {
		switch((int) (Math.random()*20)) {
			case 1: return Mineral.Ph;
			case 2: return Mineral.Cr;
			case 3: return Mineral.Nc;
			case 4: return Mineral.Io;
			default: return Mineral.Fr;
		}
	}
}
