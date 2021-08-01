package scripts;

public class Vector extends Point {

	public Vector(double x, double y) {
		super(x, y);
	}
	
	public void scale(double s) {
		setX(getX()*s);
		setY(getY()*s);
	}

}
