package scripts.util;

public class Vector extends Point {

	public Vector(double x, double y) {
		super(x, y);
	}
	
	public void scale(double s) {
		setX(getX()*s);
		setY(getY()*s);
	}
	
	public double getMagnitude() {
		return Math.sqrt(getX() * getX() + getY() * getY());
	}
	
	public void setMagnitude(double mag) {
		double currMag = getMagnitude();
		if (currMag == 0) return;
		scale(mag/currMag);
	}

}
