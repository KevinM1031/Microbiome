package scripts;

public class Utility {

	public static boolean pointRectCollision(Point p, double x, double y, double w, double h) {
		return p.x < x || p.x > w || p.y < y || p.y > h;
	}
	
	public static boolean circleRectCollision(Point p, double r, double x, double y, double w, double h) {
		return p.x-r < x || p.x+r > w || p.y-r < y || p.y+r > h;
	}
	
	public static boolean pointCircleCollision(Point p, double r, double x, double y) {
		return p.distanceTo(new Point(x, y)) <= r;
	}
	
	public static double angleToPoint(Point p0, Point p) {
		 return Math.atan2(p.getY()-p0.getY(), p.getX()-p0.getX()) - Math.PI/2.0;
	}
	
	public static String trim(String str) {
		String out = "";
		for(char c : str.toCharArray())
			if(c != ' ') out += c;
		return out;
	}
	
	public static double tempMod(double temp) {
		return Math.pow(temp / Environment.BASE_TEMPTERATURE, 2);
	}
	
	public static double tempModPA(double temp) {
		return (temp >= Environment.BASE_TEMPTERATURE) ?
				Math.pow(temp / Environment.BASE_TEMPTERATURE, 16) :
				Math.pow((temp - 2*Environment.BASE_TEMPTERATURE) / Environment.BASE_TEMPTERATURE, 16);
	}
}
