package lootPackage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player extends GameObject {

	int speed = 50;
	int rotation = 0;
	int strength = 0;
	int health;
	int charisma;
	int knowledge;
	int nimbleness;
	boolean myTurn = true;
	boolean up = false;
	boolean down = false;
	boolean right = false;
	boolean left = false;
	public static BufferedImage attackImg;

	public Player(int x, int y, int width, int height) {
		super();
		try {
			attackImg = ImageIO.read(this.getClass().getResourceAsStream("AttackSelection.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		speed = 50 + nimbleness;
		health = 10 + health;
	}

	public void update() {
		super.update();
		speed = 50 + nimbleness;
		if (health == 0) {
			isAlive = false;
		}
		if (myTurn == true) {
			if (up) {
				y -= speed;
				myTurn = false;
			}
			if (down) {
				y += speed;
				myTurn = false;
			}
			if (right) {
				x += speed;
				myTurn = false;
			}
			if (left) {
				x -= speed;
				myTurn = false;
			}
			if (y > 750) {
				y = 750;
			}
			if (y < 5) {
				y = 5;
			}
			if (x > 750) {
				x = 750;
			}
			if (x < 0) {
				x = 0;
			}
		}
	}

	public void draw(Graphics g) {
		g.drawImage(GamePanel.playerImg, x, y, width, height, null);
		if (rotation == 0) {
			g.drawImage(attackImg, x, y + 50, null);
		}
		if (rotation == 1) {
			g.drawImage(attackImg, x + 50, y, null);
		}
		if (rotation == 2) {
			g.drawImage(attackImg, x, y - 50, null);
		}
		if (rotation == 3) {
			g.drawImage(attackImg, x - 50, y, null);
		}
	}

}
