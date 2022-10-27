package scripts.util;

public class Line {

	public Point a, b;
	
	public Line(Point a, Point b) {
		this.a = a;
		this.b = b;
	}
	
	public boolean intersectsWith(Line l) {
		return ((a.x < l.a.x && b.x > l.b.x) || (a.x > l.a.x && b.x < l.b.x)) &&
				((a.y < l.a.y && b.y > l.b.y) || (a.y > l.a.y && b.y < l.b.y));
	}
}
