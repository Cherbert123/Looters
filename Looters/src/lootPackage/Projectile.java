package lootPackage;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Projectile extends GameObject {
	int rotation;
	int range;
	int x;
	int y;
	int width;
	int height;
	int movedSpaces;
	int speed;
	int angle = 15;
	String img;
	public static BufferedImage fireImg;
	public static BufferedImage lightningImg;
	public static BufferedImage iceOrbImg;

	public Projectile(String img, int rotation, int range, int speed, int x, int y, int width, int height) {
		super();
		try {
			fireImg = ImageIO.read(this.getClass().getResourceAsStream("FireBlast.png"));
			lightningImg = ImageIO.read(this.getClass().getResourceAsStream("thunderShock.png"));
			iceOrbImg = ImageIO.read(this.getClass().getResourceAsStream("OrbOfWinter.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.img = img;
		this.range = range;
		this.rotation = rotation;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;

	}

	public void update() {
		super.update();
		if (movedSpaces < range) {
			if (rotation == 0) {
				y = y + speed;
			}
			if (rotation == 1) {
				x = x + speed;
			}
			if (rotation == 2) {
				y = y - speed;
			}
			if (rotation == 3) {
				x = x - speed;

			}
		} else {
			isAlive = false;
		}
		movedSpaces = movedSpaces + speed;
		collisionBox.setBounds(x, y, width, height);
	}

	public void draw(Graphics g) {
		if (img.equalsIgnoreCase("fireImg")) {
			g.drawImage(fireImg, x, y, null);
		}
		if (img.equalsIgnoreCase("lightningImg")) {
			g.drawImage(lightningImg, x, y, null);
		}
		if (img.equalsIgnoreCase("iceOrbImg")) {
			Graphics2D g2 = (Graphics2D) g;
			AffineTransform at = new AffineTransform();
			at.rotate(Math.toRadians(angle++), x + (width / 2), y + (height / 2));
			g2.setTransform(at);
			g2.drawImage(iceOrbImg, x, y, 250, 250, null);

		}

	}
}
