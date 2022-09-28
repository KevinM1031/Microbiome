package scripts.ui;

import java.util.function.Consumer;

public class UIEvent<T> {

	private Consumer<T> event;
	private String label;
	
	public UIEvent(Consumer<T> event, String label) {
		this.event = event;
		this.label = label;
	}
	
	public void run(T t) {
		event.accept(t);
	}
	
	public String getLabel() {
		return label;
	}
	
}
