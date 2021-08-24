package scripts;

import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InputControl implements MouseListener, KeyListener {
	
	private boolean leftPressed;
	private boolean leftHeld;
	private boolean rightPressed;
	private boolean rightHeld;
	
	private char recentChar;
	private boolean terminate;
	
	public InputControl() {
		leftPressed = leftHeld = rightPressed = rightHeld = false;
		recentChar = 0;
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

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_1: recentChar = '1'; break;
		case KeyEvent.VK_2: recentChar = '2'; break;
		case KeyEvent.VK_3: recentChar = '3'; break;
		case KeyEvent.VK_4: recentChar = '4'; break;
		case KeyEvent.VK_5: recentChar = '5'; break;
		case KeyEvent.VK_6: recentChar = '6'; break;
		case KeyEvent.VK_7: recentChar = '7'; break;
		case KeyEvent.VK_8: recentChar = '8'; break;
		case KeyEvent.VK_9: recentChar = '9'; break;
		case KeyEvent.VK_0: recentChar = '0'; break;
		
		case KeyEvent.VK_A: recentChar = 'A'; break;
		case KeyEvent.VK_B: recentChar = 'B'; break;
		case KeyEvent.VK_C: recentChar = 'C'; break;
		case KeyEvent.VK_D: recentChar = 'D'; break;
		case KeyEvent.VK_E: recentChar = 'E'; break;
		case KeyEvent.VK_F: recentChar = 'F'; break;
		case KeyEvent.VK_G: recentChar = 'G'; break;
		case KeyEvent.VK_H: recentChar = 'H'; break;
		case KeyEvent.VK_I: recentChar = 'I'; break;
		case KeyEvent.VK_J: recentChar = 'J'; break;
		case KeyEvent.VK_K: recentChar = 'K'; break;
		case KeyEvent.VK_L: recentChar = 'L'; break;
		case KeyEvent.VK_M: recentChar = 'M'; break;
		case KeyEvent.VK_N: recentChar = 'N'; break;
		case KeyEvent.VK_O: recentChar = 'O'; break;
		case KeyEvent.VK_P: recentChar = 'P'; break;
		case KeyEvent.VK_Q: recentChar = 'Q'; break;
		case KeyEvent.VK_R: recentChar = 'R'; break;
		case KeyEvent.VK_S: recentChar = 'S'; break;
		case KeyEvent.VK_T: recentChar = 'T'; break;
		case KeyEvent.VK_U: recentChar = 'U'; break;
		case KeyEvent.VK_V: recentChar = 'V'; break;
		case KeyEvent.VK_W: recentChar = 'W'; break;
		case KeyEvent.VK_X: recentChar = 'X'; break;
		case KeyEvent.VK_Y: recentChar = 'Y'; break;
		case KeyEvent.VK_Z: recentChar = 'Z'; break;

		case KeyEvent.VK_SPACE: recentChar = ' '; break;
		case KeyEvent.VK_MINUS: recentChar = '-'; break;
		case KeyEvent.VK_EQUALS: recentChar = '='; break;
		case KeyEvent.VK_SLASH: recentChar = '/'; break;
		case KeyEvent.VK_BACK_SLASH: recentChar = '\\'; break;
		case KeyEvent.VK_COMMA: recentChar = ','; break;
		case KeyEvent.VK_QUOTE: recentChar = '\"'; break;
		case KeyEvent.VK_OPEN_BRACKET: recentChar = '['; break;
		case KeyEvent.VK_CLOSE_BRACKET: recentChar = ']'; break;
		case KeyEvent.VK_PERIOD: recentChar = '.'; break;
		
		case KeyEvent.VK_BACK_SPACE: recentChar = 8; break;
		
		case KeyEvent.VK_ESCAPE: terminate = true; break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public char recentKey() {		
		char out = recentChar;
		recentChar = 0;
		return out;
	}
	
	public boolean terminateRequested() {
		return terminate;
	}

}
