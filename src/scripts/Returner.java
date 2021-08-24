package scripts;

public class Returner<T> {

	private T value;
	
	public Returner(T value) {
		this.value = value;
	}
	
	public void set(T value) {
		this.value = value;
	}
	
	public T get() {
		return value;
	}
}
