package scripts;

public class Environment {
	
	public static final long RADIATION = 10;
	public static final double BASE_TEMPTERATURE = 293.15;
	public static int MAX_LIGHT = 1000;
	
	public static int remainingLight = MAX_LIGHT;
	
	public static double getBrightness(Point pos, double height) {
		return Math.pow((height-pos.y)/height, 3) * (double)remainingLight/MAX_LIGHT;
	}
	
	public static double getTemperature(Point pos, double width) {
		return 283.15 + pos.x/width*22;
	}
	
	public static double lowerTempDirection() {
		return Math.PI/2.0;
	}
	
	public static double higherTempDirection() {
		return Math.PI*3.0/2.0;
	}
	
	public static void useLight() {
		if(remainingLight > 0) remainingLight--;
	}
	
	public static void resetLight() {
		remainingLight = MAX_LIGHT;
	}

}
