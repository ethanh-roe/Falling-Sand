package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel {

	private static int FILL_SIZE = 1;
	private static int SCREEN_WIDTH = 800;
	private static int SCREEN_HEIGHT = 800;

	private JFrame frame;

	private int[][] grid;

	private Color[][] colorGrid;

	public int mouseX;
	public int mouseY;

	public Color sandColor;

	public StopWatch timer;

	public Panel(JFrame f) {
		this.frame = f;

		sandColor = new Color(66, 135, 235);
		colorGrid = new Color[SCREEN_HEIGHT / 10][SCREEN_WIDTH / 10];

		timer = new StopWatch();

		Dimension d = new Dimension(SCREEN_HEIGHT, SCREEN_WIDTH);
		this.setPreferredSize(d);

		this.addMouseListener(new MyMouseListener());

		this.addMouseMotionListener(new MyMouseListener());

		frame.addKeyListener(new MyKeyListener());

		frame.setFocusable(true);

		initGrid();
	}

	private void initGrid() {
		grid = new int[SCREEN_HEIGHT / 10][SCREEN_WIDTH / 10];
		for (int r = 0; r < SCREEN_HEIGHT / 10; r++) {
			for (int c = 0; c < SCREEN_WIDTH / 10; c++) {
				grid[r][c] = 0;
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, SCREEN_HEIGHT, SCREEN_WIDTH);
		g.setColor(Color.white);
		g.fillRect(0, SCREEN_HEIGHT - 10, SCREEN_WIDTH, 10);
		paintSand(g);
	}

	private void paintSand(Graphics g) {
		for (int r = 0; r < SCREEN_HEIGHT / 10; r++) {
			for (int c = 0; c < SCREEN_WIDTH / 10; c++) {
				if (grid[r][c] == 1) {
					g.setColor(colorGrid[r][c]);
					g.fillRect(r * 10, (c * 10) - 10, 10, 10);
				}

			}
		}
	}

	private void sandGravity() {
		for (int r = 0; r < SCREEN_HEIGHT / 10; r++) {
			for (int c = 0; c < SCREEN_WIDTH / 10; c++) {
				if (grid[r][c] == 1 && c < (SCREEN_WIDTH / 10) - 1 && c > 0 && grid[r][c + 1] != 1) {

					grid[r][c] = 0;
					grid[r][c + 1] = 1;

					colorGrid[r][c + 1] = colorGrid[r][c];
					colorGrid[r][c] = null;
					c++;

				} else if (grid[r][c] == 1 && c != (SCREEN_WIDTH / 10) - 1 && c != 0 && grid[r][c + 1] == 1) {

					if (r > 0 && r < (SCREEN_HEIGHT / 10) - 1 && grid[r - 1][c + 1] != 1 && grid[r + 1][c + 1] != 1) {

						int rand = (int) (Math.random() * 2);

						if (rand == 1) {

							grid[r][c] = 0;
							grid[r - 1][c + 1] = 1;

							colorGrid[r - 1][c + 1] = colorGrid[r][c];
							colorGrid[r][c] = null;

						} else {

							grid[r][c] = 0;
							grid[r + 1][c + 1] = 1;

							colorGrid[r + 1][c + 1] = colorGrid[r][c];
							colorGrid[r][c] = null;

						}

					} else if (r > 0 && grid[r - 1][c + 1] != 1) {

						grid[r][c] = 0;
						grid[r - 1][c + 1] = 1;

						colorGrid[r - 1][c + 1] = colorGrid[r][c];
						colorGrid[r][c] = null;

					} else if (r < (SCREEN_HEIGHT / 10) - 1 && grid[r + 1][c + 1] != 1
							&& r != (SCREEN_WIDTH / 10) - 1) {

						grid[r][c] = 0;
						grid[r + 1][c + 1] = 1;

						colorGrid[r + 1][c + 1] = colorGrid[r][c];
						colorGrid[r][c] = null;

					}
				}
				repaint();
			}
		}
	}

	private void fill(int x, int y) {
		// fill center
		grid[x][y] = 1;
		colorGrid[x][y] = sandColor;

		for (int i = 1; i < FILL_SIZE; i++) {
			// left
			grid[x - i][y] = 1;
			colorGrid[x - i][y] = sandColor;
			// right
			grid[x + i][y] = 1;
			colorGrid[x + i][y] = sandColor;
			// down
			grid[x][y + i] = 1;
			colorGrid[x][y + i] = sandColor;
			// up
			grid[x][y - i] = 1;
			colorGrid[x][y - i] = sandColor;
		}
	}

	private void changeColor() {
		int r = sandColor.getRed();
		int g = sandColor.getGreen();
		int b = sandColor.getBlue();

		if (r >= 205) {
			r = 204;
		} else if (r <= 50) {
			r = 51;
		}

		if (g >= 205) {
			g = 204;
		} else if (g <= 50) {
			g = 51;
		}

		if (b >= 205) {
			b = 204;
		} else if (b <= 50) {
			b = 51;
		}

		r = (int) (Math.random() * ((r + 50) - (r - 50))) + r - 50;
		g = (int) (Math.random() * ((g + 50) - (g - 50))) + g - 50;
		b = (int) (Math.random() * ((b + 50) - (b - 50))) + b - 50;
		sandColor = new Color(r, g, b);
	}

	private void clearGrid() {
		for (int r = 0; r < SCREEN_HEIGHT / 10; r++) {
			for (int c = 0; c < SCREEN_WIDTH / 10; c++) {
				grid[r][c] = 0;
			}
		}
		repaint();
	}

	private class MyMouseListener implements MouseListener, MouseMotionListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			if (mouseX >= SCREEN_WIDTH || mouseY >= SCREEN_HEIGHT) {
				return;
			}
			if (grid[mouseX / 10][mouseY / 10] == 0) {
				fill(mouseX / 10, mouseY / 10);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			if (mouseX >= SCREEN_WIDTH || mouseX <= 0 || mouseY >= SCREEN_HEIGHT || mouseY <= 0) {
				return;
			}
			if (grid[mouseX / 10][mouseY / 10] == 0) {
				fill(mouseX / 10, mouseY / 10);
			}

		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private class StopWatch {
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				changeColor();
				sandGravity();

			}
		};

		public StopWatch() {
			Timer timer = new Timer(50, taskPerformer);
			timer.setRepeats(true);
			timer.start();
		}
	}

	private class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_R) {
				clearGrid();
			}
			if (e.getKeyCode() == KeyEvent.VK_EQUALS && FILL_SIZE < 5) {
				FILL_SIZE++;
				System.out.println(FILL_SIZE);
			}
			if (e.getKeyCode() == KeyEvent.VK_MINUS && FILL_SIZE > 1) {
				FILL_SIZE--;
				System.out.println(FILL_SIZE);
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

}
