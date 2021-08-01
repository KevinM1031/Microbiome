package scripts;

public class Point {

	public double x;
	public double y;
	
	public Point(double x, double y) {
		setPoint(x, y);
	}
	
	public Point clone() {
		return new Point(x, y);
	}
	
	public void setPoint(double x, double y) {
		setX(x);
		setY(y);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void multX(double x) {
		this.x *= x;
	}
	
	public void multY(double y) {
		this.y *= y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double distanceTo(Point p) {
		return Math.sqrt(Math.pow(x-p.getX(),2) + Math.pow(y-p.getY(),2));
	}
	
	public void add(Point p) {
		x += p.getX();
		y += p.getY();
	}
	
	public void add(Point p, double s) {
		x += p.getX()*s;
		y += p.getY()*s;
	}
	
	public void subtract(Point p) {
		x -= p.getX();
		y -= p.getY();
	}
	
	public void rotate(double radians) {
		double xo = x;
		double yo = y;
		x = Math.cos(radians)*xo - Math.sin(radians)*yo;
		y = Math.sin(radians)*xo + Math.cos(radians)*yo;
	}
	
	public void rotate(Point pivot, double radians) {
		double xo = x-pivot.getX();
		double yo = y-pivot.getY();
		x = Math.cos(radians)*xo - Math.sin(radians)*yo + pivot.getX();
		y = Math.sin(radians)*xo + Math.cos(radians)*yo + pivot.getY();
	}
	
	public void print() {
		System.out.println("(" + x + ", " + y + ")");
	}
}
