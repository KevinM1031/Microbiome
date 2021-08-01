package scripts;

public class Microbiome {

	private static final String TITLE = "SkyTime";
	private final static boolean RUNNING = true;
	
	public static final int WIDTH = 1500, HEIGHT = 1000;
	public static final long seed = 123456789;
	public static final long FPS = 80;
	public static final long delayInMS = 1000/FPS;
	public static final double timeSpeed = 12;
	
	public static final int MAX_RESOURCES = 200;
	public static final int MAX_SPORES = 10000;
	public static final int MAX_PROTEINS = 10000;
	
	public static void main(String[] args) {
		defaultDriver();
	}
	
	private static void defaultDriver() {
		Window W = new Window(TITLE, WIDTH, HEIGHT, new Painter(WIDTH, HEIGHT));
		
		long t1, t2 = System.currentTimeMillis(), updateCount = 0;
		while(RUNNING) {
			
			t1 = System.currentTimeMillis();
			
			W.update();
			
			updateCount++;
			if(System.currentTimeMillis()-t2 >= 1000) {
				System.out.println("[SYSTEM] FPS: " + updateCount 
						+ " (optimal: " + FPS + ")");
				updateCount = 0;
				t2 = System.currentTimeMillis();
			}
			
			try {
				Thread.sleep(delayInMS - (System.currentTimeMillis()-t1)%delayInMS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}