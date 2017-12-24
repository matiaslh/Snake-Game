
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Main extends Frame {
	Random rand = new Random();
	int h = 722, w = 720, x = w / 40, y = x, boundsX = 40, boundsY = 40, highscore = 0;
	int snackX = rand.nextInt(boundsX), snackY = rand.nextInt(boundsY - 3) + 1, dotsAdd = rand.nextInt(3) + 4;
	ArrayList<Segment> snake = new ArrayList<Segment>();
	AL al = new AL(this);
	public boolean close = false, render = true;

	public Main() throws InterruptedException {
		super("Snake");
		try {
			super.setIconImage(ImageIO.read(new File("icon.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		setSize(w + 16, h);
		setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				System.exit(0);
			}
		});
		super.addKeyListener(al);
		retry();
		setBackground(Color.BLACK);
		setForeground(Color.WHITE);
		setFont(new Font("", Font.PLAIN, 20));
		super.getGraphics().drawString("Input difficulty from keyboard (0 is easiest, 9 is hardest)", 112, 310);
		while (true) {
			while (!al.once) {
				Thread.sleep(10);
				if (al.close) {
					quit();
				}
			}
			while (true) {
				update();
				if (al.close) {
					quit();
				}
				if (al.retry) {
					retry();
				}
			}
		}
	}

	public void paint(Graphics g) {
		if (al.once && render) {
			int h = super.getHeight();
			int w = super.getWidth();
			g.clearRect(0, 0, w, h);
			setBackground(Color.BLACK);

			updateSegments(snake, al.dx, al.dy);

			g.setColor(Color.RED);
			for (int i = 0; i < snake.size(); i++) {
				g.fillOval(snake.get(i).x * x + 8, snake.get(i).y * y + 12, x, y);
			}
			g.setColor(Color.GREEN);
			g.fillOval(snackX * x + 8, snackY * y + 12, x, y);

			g.setColor(Color.WHITE);
			g.drawString("Current Score: " + snake.size(), 15, 52);
			g.drawString("High Score: " + highscore, 580, 52);

			g.setColor(Color.BLACK);
			g.drawString("" + dotsAdd, snackX * x + 12, snackY * y + 29);
		}
		if (!render) {
			setBackground(Color.BLACK);
			setForeground(Color.WHITE);
			super.getGraphics().drawString("Press Space to retry. Press Escape to quit.", 174, 230);
		}
	}

	public void updateSegments(ArrayList<Segment> snake, int dx, int dy) {
		for (int i = snake.size() - 1; i > 0; i--) {
			snake.get(i).x = snake.get(i - 1).x;
			snake.get(i).y = snake.get(i - 1).y;
		}
		snake.get(0).x += dx;
		snake.get(0).y += dy;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {
		Main game = new Main();
	}

	public void update() throws InterruptedException {
		// System.out.println(snake.get(0).x+" "+snake.get(0).y);
		Thread.sleep(1000 / al.difficulty);
		if (snake.get(0).x >= boundsX || snake.get(0).x < 0 || snake.get(0).y >= boundsY - 1 || snake.get(0).y < 1) { // checks
																														// if
																														// the
																														// snake
																														// went
																														// out
																														// of
																														// bounds
			render = false;
		}
		if (snake.get(0).x == snackX && snake.get(0).y == snackY) { // check if
																	// the snake
																	// ate a
																	// snack
			for (int i = 0; i < dotsAdd; i++) {
				snake.add(new Segment(-1, -1));
			}
			for (int j = 0; j < snake.size(); j++) {
				do {
					snackX = rand.nextInt(boundsX);
					snackY = rand.nextInt(boundsY - 3) + 1;

				} while (checkHit(snake, snackX, snackY));
			}
			dotsAdd = rand.nextInt(3) + 4;
		}

		checkHitItself(snake);

		paint(super.getGraphics());
		if (!render) {
			while (true) {
				if (al.close) {
					quit();
				}
				if (al.retry) {
					retry();
					break;
				}
			}
		}
	}

	public void checkHitItself(ArrayList<Segment> snake) {
		for (int i = 1; i < snake.size(); i++) {
			if (snake.get(0).x == snake.get(i).x && snake.get(0).y == snake.get(i).y) {
				render = false;
			}
		}
	}

	public boolean checkHit(ArrayList<Segment> snake, int posX, int posY) {
		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i).x == posX && snake.get(i).y == posY) {
				return true;
			}
		}
		return false;
	}

	public void retry() {
		if (snake.size() > highscore) {
			highscore = snake.size();
		}
		snackX = rand.nextInt(boundsX);
		snackY = rand.nextInt(boundsY - 3) + 1;
		dotsAdd = rand.nextInt(3) + 4;
		snake.clear();
		render = true;
		al.dx = 0;
		al.dy = 1;
		for (int i = 0; i < 3; i++) {
			snake.add(new Segment(i + 19, 19));
		}
		al.retry = false;
	}

	public void quit() throws InterruptedException {
		Thread.sleep(1000 / 15);
		dispose();
		System.exit(0);
	}
}