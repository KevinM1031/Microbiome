package scripts;

import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseControl implements MouseListener {
	
	private boolean leftPressed;
	private boolean leftHeld;
	
	private boolean rightPressed;
	private boolean rightHeld;
	
	public MouseControl() {
		leftPressed = leftHeld = rightPressed = rightHeld = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch(e.getButton()) {
			case MouseEvent.BUTTON1: leftPressed = leftHeld = true; break;
			case MouseEvent.BUTTON3: rightPressed = rightHeld = true; break;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch(e.getButton()) {
			case MouseEvent.BUTTON1: leftHeld = false; break;
			case MouseEvent.BUTTON3: rightHeld = false; break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void update() {
		leftPressed = rightPressed = false;
	}
	
	public boolean leftPressed() {
		return leftPressed;
	}
	
	public boolean leftHeld() {
		return leftHeld;
	}
	
	public boolean rightPressed() {
		return rightPressed;
	}
	
	public boolean rightHeld() {
		return rightHeld;
	}
	
	public Point position() {
		java.awt.Point pos = MouseInfo.getPointerInfo().getLocation();
		return new Point(pos.x, pos.y);
	}

}
