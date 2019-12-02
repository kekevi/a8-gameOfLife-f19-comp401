package gameOfLife;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class JTile extends JPanel implements Tile, MouseListener {
	private boolean _alive;
	private int _x;
	private int _y;
	
	public JTile(int x, int y, boolean alive) {
		_x = x;
		_y = y;
		_alive = alive;
		setBackground(DEAD_COLOR);
		addMouseListener(this);
	}
	public JTile(int x, int y) {
		this(x, y, false);
	}
	
	@Override
	public int getXPos() {
		return _x;
	}
	
	@Override
	public int getYPos() {
		return _y;
	}
	
	@Override
	public boolean isAlive() {
		return _alive;
	}

	@Override
	public void setAlive(boolean status) {
		_alive = status;
		update();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		toggleAlive();
		update();
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		//DOES NOTHING
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// DOES NOTHING
	}
	
	@Override
	public void paintComponent(Graphics g) {
		// Super class paintComponent will take care of 
		// painting the background.
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g.create(); //best to make a copy if you are making your own graphic objects
		if (isAlive()) {
			setBackground(ALIVE_COLOR);
		} else {
			setBackground(DEAD_COLOR);
		}
		
	}
	
	private void update() {
		setBackground(isAlive() ? ALIVE_COLOR : DEAD_COLOR);
		
		/* from KMP's JSpot at a7 */
		repaint();

		// Not sure why, but need to schedule a call
		// to repaint for a little bit into the future
		// as well as the one we just did above
		// in order to make sure that we don't end up
		// with visual artifacts due to race conditions.
		
		/*new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
				repaint();
			}
		}).start();*/

	}
}
