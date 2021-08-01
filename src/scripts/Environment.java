package scripts;

public class Environment {
	
	public static final long RADIATION = 10;
	public static final double BASE_TEMPTERATURE = 293.15;
	
	public static double getBrightness(Point pos) {
		return 1;
	}
	
	public static double getTemperature(Point pos) {
		return 283.15 + pos.x/1000*12;
	}
	
	public static double lowerTempDirection() {
		return Math.PI/2.0;
	}
	
	public static double higherTempDirection() {
		return Math.PI*3.0/2.0;
	}

}
