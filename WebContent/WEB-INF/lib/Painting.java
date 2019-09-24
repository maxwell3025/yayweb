package paint;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Painting extends Applet implements MouseListener,
		MouseMotionListener, MouseWheelListener, KeyListener, WindowListener {
	private int x;
	private int y;
	private int size = 10;
	private boolean started = false;
	private boolean writing = true;
	private int speed = 0;
	private boolean realistic = false;
	private int bx = 0;
	private int by = 0;
	private int distance = 10;
	private int color = 1;
	private boolean clear;
	private int held = 0;
	private boolean loaded = false;
	private int[] diffsx = new int[5];
	private int[] diffsy = new int[5];
	private boolean blanked = false;

	public Painting() {
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		addMouseListener(this);

		setSize(1920, 1080);
		setVisible(true);
	}

	//public static void main(String[] args) {
	//	Painting p = new Painting();
	//}

	public void paint(Graphics g) {
		if (blanked) {
			blanked = false;
			g.clearRect(0, 0, 10000, 10000);
		}
		if (started && writing) {
			if (color == 1) {
				g.setColor(Color.BLACK);
			}
			if (color == 2) {
				g.setColor(Color.RED);
			}
			if (color == 3) {
				g.setColor(Color.YELLOW);
			}
			if (color == 4) {
				g.setColor(Color.GREEN);
			}
			if (color == 5) {
				g.setColor(Color.BLUE);
			}
			if (held > 1) {
				for (double dis = 0; dis < 1; dis = dis + 0.01) {
					g.fillOval(x - (int) (size / 2 + (dis * (x - bx))), y
							- (int) (size / 2 + (dis * (y - by))), (int) size
							- distance, (int) size - distance);
				}
			} else {
				g.fillOval(x - size / 2, y - size / 2, size - distance, size
						- distance);
			}

		}
		if (!writing) {
			g.setColor(getBackground());
			g.fillOval(x - size / 2, y - size / 2, size, size);
		}
		if (clear) {
			g.clearRect(0, 0, 1920, 1080);
			clear = false;
		}
		g.fillOval(550 - size / 2, 92 - size / 2, size, size);
		g.setColor(getBackground());
		g.drawOval(550 - size / 2, 92 - size / 2, size, size);
		g.drawOval((550 - size / 2) - 1, (92 - size / 2) - 1, size + 2,
				size + 2);
		g.drawOval((550 - size / 2) - 2, (92 - size / 2) - 2, size + 4,
				size + 4);
		g.drawOval((550 - size / 2) - 3, (92 - size / 2) - 3, size + 6,
				size + 6);
		g.setColor(Color.BLACK);
		g.fillRect(0, 42, 100, 100);
		g.setColor(Color.RED);
		g.fillRect(100, 42, 100, 100);
		g.setColor(Color.YELLOW);
		g.fillRect(200, 42, 100, 100);
		g.setColor(Color.GREEN);
		g.fillRect(300, 42, 100, 100);
		g.setColor(Color.BLUE);
		g.fillRect(400, 42, 100, 100);

		if (!loaded) {
			for (int i = 0; i < 10; i++) {
				spray(g);
				loaded = true;
				repaint();
			}
		}
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void mouseDragged(MouseEvent e) {
		bx = x;
		by = y;
		x = e.getX();
		y = e.getY();
		for (int avgx = 0; avgx < 5; avgx++) {

		}
		started = true;
		if (realistic) {
			distance = (int) Math
					.sqrt((size / 10 * Math
							.sqrt((double) (((x - bx) * (x - bx)) + ((y - by) * (y - by))))));
			if (distance > size * 0.5) {
				distance = size / 2;
			} else {

			}
		} else {
			distance = 0;
		}
		if (e.getY() > 142) {
			repaint();
			held++;

		}
	}

	public void mouseMoved(MouseEvent e) {

	}

	public boolean between(int a, int b, int c) {
		return a < c && c < b;
	}

	public boolean WithinRect(int x1, int y1, int width, int height, int x3,
			int y3) {
		return between(x1, x3, x1 + width) && between(y1, y3, y1 + height);
	}

	public void mouseReleased(MouseEvent e) {
		held = 0;

	}

	public void spray(Graphics g, double size, int x, int y) {
		for (double a = 0 - size / 2; a < size / 2; a = a + size / 100) {
			for (double b = 0 - size / 2; b < size / 2; b = b + size / 100) {
				if (Math.random() > a * a + b * b) {
					g.fillRect((int) (x + a * size / 2), (int) (y + a * size
							/ 2), 1, 1);
				}
			}
		}
	}

	public void spray(Graphics g) {
		g.drawRect(300, 300, 10, 10);
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseWheelMoved(MouseWheelEvent w) {
		size = size - w.getWheelRotation();
		repaint();
	}

	public void keyTyped(KeyEvent k) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(KeyEvent k) {
		if (k.getKeyCode() == KeyEvent.VK_SPACE) {

			writing = !writing;

		}
		if (k.getKeyCode() == KeyEvent.VK_R) {
			realistic = !realistic;
		}
		if (k.getKeyCode() == KeyEvent.VK_DELETE && k.isAltDown()
				&& k.isShiftDown() && k.isControlDown()) {
			clear = true;

			repaint();

		}

	}

	public void keyReleased(KeyEvent k) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		if (between(0, 100, e.getX()) && e.getY() < 142) {
			color = 1;
		}
		if (between(100, 200, e.getX()) && e.getY() < 142) {
			color = 2;
		}
		if (between(200, 300, e.getX()) && e.getY() < 142) {
			color = 3;
		}
		if (between(300, 400, e.getX()) && e.getY() < 142) {
			color = 4;
		}
		if (between(400, 500, e.getX()) && e.getY() < 142) {
			color = 5;
		}
		if (between(600, 700, e.getX()) && e.getY() < 142) {

		}

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
