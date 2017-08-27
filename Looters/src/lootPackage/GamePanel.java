package lootPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener, KeyListener {
	Timer framerate;
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	final int END_STATE = 2;
	final int PLAYERC_STATE = 6;
	int currentState = MENU_STATE;
	int statPoints = 35;
	ArrayList<String> items = new ArrayList<String>();
	ArrayList<Enemy> enemys = new ArrayList<Enemy>();
	public static BufferedImage logoImg;
	public static BufferedImage menuBackgroundImg;
	public static BufferedImage subLogo;
	public static BufferedImage woodenWallImg;
	public static BufferedImage solidFloorTile;
	public static BufferedImage waterFloorTile;
	public static BufferedImage plusImg;
	public static BufferedImage minusImg;
	public static BufferedImage blockedFloorTile;
	public static BufferedImage playerImg;
	public static BufferedImage attackImg;
	public static BufferedImage invImg;
	public static BufferedImage swordImg;
	public static BufferedImage holeImg;
	public static BufferedImage helpImg;
	public static BufferedImage waterOrbImg;
	Font normal;
	int SpellTime;
	String spell = "";
	JButton plusStrength;
	JButton minusStrength;
	JButton plusSpeed;
	JButton minusSpeed;
	JButton plusHealth;
	JButton minusHealth;
	JButton helpButton;
	boolean inventory = false;
	boolean help = false;
	boolean myTurn;
	int yTile = 0;
	int enemyCount = 0;
	int xTile = 0;
	int rand;
	public String room = "";
	ObjectManager om = new ObjectManager();

	Player player = new Player(0, 0, 50, 50);

	public GamePanel() {
		/*
		 * enemy.health = 10; enemy.p = player; enemy.speed = 20; enemy.damage =
		 * 2; enemy.tactics = "Rage"; enemy.isAlive = true;
		 */
		newEncounter();
		plusStrength = new JButton(new ImageIcon("Plus.png"));
		minusStrength = new JButton(new ImageIcon("Minus.png"));
		plusSpeed = new JButton(new ImageIcon("Plus.png"));
		minusSpeed = new JButton(new ImageIcon("Minus.png"));
		plusHealth = new JButton(new ImageIcon("Plus.png"));
		minusHealth = new JButton(new ImageIcon("Minus.png"));
		helpButton = new JButton("Press Me!");

		myTurn = true;
		plusSpeed.setFocusable(false);
		minusSpeed.setFocusable(false);
		plusStrength.setFocusable(false);
		minusStrength.setFocusable(false);
		plusHealth.setFocusable(false);
		minusHealth.setFocusable(false);
		helpButton.setFocusable(false);
		normal = new Font("Arial", Font.PLAIN, 35);
		framerate = new Timer(1000 / 60, this);
		om.addObject(player);
		setLayout(null);
		
		helpButton.setBounds(new Rectangle(70, 720, 100, 50));
		helpButton.setEnabled(false);
		helpButton.setVisible(false);
		helpButton.addActionListener(this);
		
		plusStrength.setBounds(new Rectangle(700, 220, 45, 45));
		minusStrength.setBounds(new Rectangle(600, 220, 45, 45));
		plusStrength.setEnabled(false);
		plusStrength.setVisible(false);
		minusStrength.setEnabled(false);
		minusStrength.setVisible(false);
		plusStrength.addActionListener(this);
		minusStrength.addActionListener(this);
		plusSpeed.setBounds(new Rectangle(700, 535, 45, 45));
		minusSpeed.setBounds(new Rectangle(600, 535, 45, 45));
		plusSpeed.setEnabled(false);
		plusSpeed.setVisible(false);
		minusSpeed.setEnabled(false);
		minusSpeed.setVisible(false);
		plusSpeed.addActionListener(this);
		minusSpeed.addActionListener(this);
		plusHealth.setBounds(new Rectangle(700, 375, 45, 45));
		minusHealth.setBounds(new Rectangle(600, 375, 45, 45));
		plusHealth.setEnabled(false);
		plusHealth.setVisible(false);
		minusHealth.setEnabled(false);
		minusHealth.setVisible(false);
		plusHealth.addActionListener(this);
		minusHealth.addActionListener(this);
		add(minusStrength);
		add(plusStrength);
		add(minusSpeed);
		add(plusSpeed);
		add(minusHealth);
		add(plusHealth);
		add(helpButton);
		items.add("Sword, 3");

		try {
			holeImg = ImageIO.read(this.getClass().getResourceAsStream("Hole.png"));
			logoImg = ImageIO.read(this.getClass().getResourceAsStream("Logo.png"));
			menuBackgroundImg = ImageIO.read(this.getClass().getResourceAsStream("MenuBackGround.jpg"));
			subLogo = ImageIO.read(this.getClass().getResourceAsStream("Sublogo.png"));
			woodenWallImg = ImageIO.read(this.getClass().getResourceAsStream("WoodenWall.jpg"));
			playerImg = ImageIO.read(this.getClass().getResourceAsStream("Player.png"));
			solidFloorTile = ImageIO.read(this.getClass().getResourceAsStream("solidFloorTile.png"));
			waterFloorTile = ImageIO.read(this.getClass().getResourceAsStream("waterFloorTile.png"));
			blockedFloorTile = ImageIO.read(this.getClass().getResourceAsStream("blockedFloorTile.png"));
			attackImg = ImageIO.read(this.getClass().getResourceAsStream("AttackSelection.png"));
			invImg = ImageIO.read(this.getClass().getResourceAsStream("inventory.png"));
			swordImg = ImageIO.read(this.getClass().getResourceAsStream("Weapon.png"));
			waterOrbImg = ImageIO.read(this.getClass().getResourceAsStream("waterOrb.png"));
			helpImg = ImageIO.read(this.getClass().getResourceAsStream("HelpScreen.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void actionPerformed(ActionEvent e) {
		repaint();
		if (currentState == MENU_STATE) {
			updateMenuState();
		} else if (currentState == GAME_STATE) {
			updateGameState();
		} else if (currentState == END_STATE) {
			updateEndState();
		}
		if (e.getSource() == plusStrength) {
			if (statPoints > 0) {
				System.out.println(statPoints);
				player.strength = player.strength + 1;
				statPoints = statPoints - 1;
			}
		}
		if (e.getSource() == minusStrength) {
			if (player.strength > 0) {
				player.strength = player.strength - 1;
				statPoints = statPoints + 1;
			}
		}
		if (e.getSource() == plusSpeed) {
			if (statPoints > 0) {
				System.out.println(statPoints);
				player.nimbleness = player.nimbleness + 1;
				statPoints = statPoints - 1;
			}
		}
		if (e.getSource() == helpButton) {
			if(help == true){
				help = false;
				plusStrength.setEnabled(true);
				plusStrength.setVisible(true);
				minusStrength.setEnabled(true);
				minusStrength.setVisible(true);
				minusSpeed.setEnabled(true);
				minusSpeed.setVisible(true);
				plusSpeed.setEnabled(true);
				plusSpeed.setVisible(true);
				minusHealth.setEnabled(true);
				minusHealth.setVisible(true);
				plusHealth.setEnabled(true);
				plusHealth.setVisible(true);
			} else if(help == false){
				help = true;
			}
			
				
				
		}
		if (e.getSource() == minusSpeed) {
			if (player.nimbleness > 0) {
				player.nimbleness = player.nimbleness - 1;
				statPoints = statPoints + 1;
			}
		}
		if (e.getSource() == plusHealth) {
			if (statPoints > 0) {
				System.out.println(statPoints);
				player.health = player.health + 1;
				statPoints = statPoints - 1;
			}
		}
		if (e.getSource() == minusHealth) {
			if (player.health > 0) {
				player.health = player.health - 1;
				statPoints = statPoints + 1;
			}
		}
	}

	public void paintComponent(Graphics g) {
		if (currentState == MENU_STATE) {
			drawMenuState(g);
		} else if (currentState == GAME_STATE) {

			drawGameState(g);
		} else if (currentState == END_STATE) {
			drawEndState(g);
		} else if (currentState == PLAYERC_STATE) {
			drawPlayerCState(g);
		}
	}

	public void startGame() {
		framerate.start();
	}

	// Update Methods
	private void updateMenuState() {

	}

	private void updateGameState() {
		om.update();
		SpellTime = SpellTime - 1;

	}

	private void updateEndState() {

	}

	// Key Handler Methods

	public void keyPressed(KeyEvent e) {
		System.out.println(e);
		if (e.getKeyCode() == 10) {
			if (currentState == END_STATE) {
				currentState = MENU_STATE;
			} else if (currentState == MENU_STATE) {
				currentState = PLAYERC_STATE;
			} else if (currentState == GAME_STATE) {
				currentState = MENU_STATE;
			} else if (currentState == PLAYERC_STATE) {
				currentState = GAME_STATE;
			}
		}
		if (e.getKeyCode() == 66) {
			if (inventory == true) {
				inventory = false;
			} else if (inventory == false) {
				inventory = true;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
			player.update();
			System.out.println(enemys.size());
			for (int i = 0; i < enemys.size(); i++) {
				enemys.get(i).parseTactics(enemys.get(i).tactics);
			}
		}
		if (e.getKeyCode() == 37) {
			player.left = true;
			player.update();
			for (int i = 0; i < enemys.size(); i++) {
				enemys.get(i).parseTactics(enemys.get(i).tactics);
			}
		}
		if (e.getKeyCode() == 38) {
			player.up = true;
			player.update();

			for (int i = 0; i < enemys.size(); i++) {
				enemys.get(i).parseTactics(enemys.get(i).tactics);
			}
		}
		if (e.getKeyCode() == 40) {
			player.down = true;
			player.update();
			for (int i = 0; i < enemys.size(); i++) {
				enemys.get(i).parseTactics(enemys.get(i).tactics);
			}
		}

		if (e.getKeyCode() == 90) {
			spell = spell + "Fire";

		}
		if (e.getKeyCode() == 88) {

			spell = spell + "Light";

		}
		if (e.getKeyCode() == 67) {

			spell = spell + "Ice";

		}

		if (e.getKeyCode() == 69) {
			castSpell(e);
		}
		if (e.getKeyCode() == 82) {
			if (player.rotation == 3) {
				player.rotation = 0;
			} else {
				player.rotation++;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 39) {
			player.right = false;
			player.update();
		}
		if (e.getKeyCode() == 37) {
			player.left = false;
			player.update();
		}
		if (e.getKeyCode() == 38) {
			player.up = false;
			player.update();
		}
		if (e.getKeyCode() == 40) {
			player.down = false;
			player.update();
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	// Draw Methods
	public void drawMenuState(Graphics g) {
		g.drawImage(menuBackgroundImg, 0, 0, null);
		g.drawImage(logoImg, 75, 200, null);
		g.drawImage(subLogo, 140, 400, null);
		plusStrength.setEnabled(false);
		plusStrength.setVisible(false);
		minusStrength.setEnabled(false);
		minusStrength.setVisible(false);
		minusSpeed.setEnabled(false);
		minusSpeed.setVisible(false);
		plusSpeed.setEnabled(false);
		plusSpeed.setVisible(false);
		minusHealth.setEnabled(false);
		minusHealth.setVisible(false);
		plusHealth.setEnabled(false);
		plusHealth.setVisible(false);
		helpButton.setVisible(false);
		helpButton.setEnabled(false);
	}

	private void drawPlayerCState(Graphics g) {
		g.drawImage(woodenWallImg, 0, 0, null);
		g.drawImage(GamePanel.playerImg, 50, 175, 450, 475, null);
		helpButton.setVisible(true);
		helpButton.setEnabled(true);
		plusStrength.setEnabled(true);
		plusStrength.setVisible(true);
		minusStrength.setEnabled(true);
		minusStrength.setVisible(true);
		minusSpeed.setEnabled(true);
		minusSpeed.setVisible(true);
		plusSpeed.setEnabled(true);
		plusSpeed.setVisible(true);
		minusHealth.setEnabled(true);
		minusHealth.setVisible(true);
		plusHealth.setEnabled(true);
		plusHealth.setVisible(true);
		g.setColor(Color.BLACK);
		g.setFont(normal);
		g.drawString("" + statPoints, 335, 98);
		g.drawString("" + player.strength, 660, 255);
		g.drawString("" + player.health, 660, 410);
		g.drawString("" + player.nimbleness, 660, 570);
		if (help == true) {
			showHelp(g);
		}

	}

	private void drawEndState(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 800); 
		g.setFont(normal);
		g.setColor(Color.WHITE);
		g.drawString("Game Over", 75, 350);
		plusStrength.setEnabled(false);
		plusStrength.setVisible(false);
		minusStrength.setEnabled(false);
		minusStrength.setVisible(false);
		minusSpeed.setEnabled(false);
		minusSpeed.setVisible(false);
		plusSpeed.setEnabled(false);
		plusSpeed.setVisible(false);
		helpButton.setVisible(false);
		helpButton.setEnabled(false);
	}

	private void drawGameState(Graphics g) {
		if(player.isAlive == false){
			currentState = END_STATE;
		}
		helpButton.setVisible(false);
		helpButton.setEnabled(false);
		plusStrength.setEnabled(false);
		plusStrength.setVisible(false);
		minusStrength.setEnabled(false);
		minusStrength.setVisible(false);
		minusSpeed.setEnabled(false);
		minusSpeed.setVisible(false);
		plusSpeed.setEnabled(false);
		plusSpeed.setVisible(false);
		minusHealth.setEnabled(false);
		minusHealth.setVisible(false);
		plusHealth.setEnabled(false);
		plusHealth.setVisible(false);
		if (player.x >= 350 && player.x <= 450 && player.y >= 350 && player.y <= 450) {
			newRoom();
			player.x = 0;
			player.y = 0;
		}
		xTile = 0;
		yTile = 0;
		for (int e = 0; e < 16; e++) {

			for (int i = 0; i < 16; i++) {

				if (room.charAt(xTile) == '1') {
					g.drawImage(solidFloorTile, i * 50, yTile, null);

				} else if (room.charAt(xTile) == '2') {
					g.drawImage(waterFloorTile, i * 50, yTile, null);

				} else if (room.charAt(xTile) == '3') {
					g.drawImage(blockedFloorTile, i * 50, yTile, null);

				}
				xTile++;

			}
			yTile = yTile + 50;
		}
		g.drawImage(holeImg, 350, 350, 150, 150, null);
		om.draw(g);
		if (inventory == true) {
			showInventory(g);
		}

	}

	public void newRoom() {
		generate();
		newEncounter();
	}

	public void generate() {
		Random random = new Random();
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (rand == 1) {
					room = room + "1";
				} else if (rand == 0) {
					rand = random.nextInt(3);
					if (rand > 1) {
						room = room + "1";
					} else if (rand <= 1) {

						room = room + "2";
					}
				} else if (rand == 2) {
					rand = random.nextInt(3);
					if (rand <= 1) {
						room = room + "1";
					} else if (rand > 1) {

						rand = random.nextInt(3);
						if (rand <= 1) {
							room = room + "1";
						} else if (rand > 1) {

							room = room + "3";
						}
					}
				}
				rand = random.nextInt(3);

			}

		}
		System.out.println(room);

	}

	public void newEncounter() {
		for (int i = 0; i < 3; i++) {
			Random random = new Random();

			Enemy enemy = new Enemy(i * 40 + 200, 400 + random.nextInt(200) - 100, 50, 50);
			enemy.health = om.getScore() + 5;
			enemy.speed = om.getScore() * 2 + 10;
			enemy.damage = om.getScore() / 2 + 3;
			enemy.p = player;
			enemy.tactics = "Rage";
			enemy.type = random.nextInt(3);
			if(om.getScore() > 4){
			enemy.type = random.nextInt(4);	
			}
			enemyCount = enemyCount + 1;
			enemys.add(enemy);
			om.addObject(enemy);

		}
	}

	public void castSpell(KeyEvent e) {
		if (spell.equalsIgnoreCase("FireFire")) {
			if (player.rotation == 0) {
				Projectile fireball = new Projectile("fireImg", 0, 400, 20, player.x, player.y + 50, 50, 50);
				om.addObject(fireball);
			}
			if (player.rotation == 1) {
				Projectile fireball = new Projectile("fireImg", 1, 400, 20, player.x + 50, player.y, 50, 50);
				om.addObject(fireball);
			}
			if (player.rotation == 2) {
				Projectile fireball = new Projectile("fireImg", 2, 400, 20, player.x, player.y - 50, 50, 50);
				om.addObject(fireball);
			}
			if (player.rotation == 3) {
				Projectile fireball = new Projectile("fireImg", 3, 400, 20, player.x - 50, player.y, 50, 50);
				om.addObject(fireball);
			}
			spell = "";
		}
		if (spell.equalsIgnoreCase("LightLight")) {
			if (player.rotation == 0) {
				Projectile fireball = new Projectile("waterOrbImg", 0, 400, 20, player.x, player.y + 50, 50, 50);
				om.addObject(fireball);
			}
			if (player.rotation == 1) {
				Projectile fireball = new Projectile("waterOrbImg", 1, 400, 20, player.x + 50, player.y, 50, 50);
				om.addObject(fireball);
			}
			if (player.rotation == 2) {
				Projectile fireball = new Projectile("waterOrbImg", 2, 400, 20, player.x, player.y - 50, 50, 50);
				om.addObject(fireball);
			}
			if (player.rotation == 3) {
				Projectile fireball = new Projectile("waterOrbImg", 3, 400, 20, player.x - 50, player.y, 50, 50);
				om.addObject(fireball);
			}
			spell = "";
		}
		// Shock
		if (spell.equalsIgnoreCase("FireLight")) {
			if (player.rotation == 0) {
				Projectile lightningball = new Projectile("lightningImg", 0, 200, 25, player.x, player.y + 50, 50, 50);
				om.addObject(lightningball);
			}
			if (player.rotation == 1) {
				Projectile lightningball = new Projectile("lightningImg", 1, 200, 25, player.x + 50, player.y, 50, 50);
				om.addObject(lightningball);
			}
			if (player.rotation == 2) {
				Projectile lightningball = new Projectile("lightningImg", 2, 200, 25, player.x, player.y - 50, 50, 50);
				om.addObject(lightningball);
			}
			if (player.rotation == 3) {
				Projectile lightningball = new Projectile("lightningImg", 3, 200, 25, player.x - 50, player.y, 50, 50);
				om.addObject(lightningball);
			}
			spell = "";
		}
		// Freeze Orb
		if (spell.equalsIgnoreCase("IceIce")) {
			if (player.rotation == 0) {
				Projectile iceOrbball = new Projectile("iceOrbImg", 0, 350, 1, player.x, player.y + 50, 250, 250);
				om.addObject(iceOrbball);
			}
			if (player.rotation == 1) {
				Projectile iceOrbball = new Projectile("iceOrbImg", 1, 350, 1, player.x + 50, player.y - 125, 250, 250);
				om.addObject(iceOrbball);
			}
			if (player.rotation == 2) {
				Projectile iceOrbball = new Projectile("iceOrbImg", 2, 350, 1, player.x, player.y - 50, 250, 250);
				om.addObject(iceOrbball);
			}
			if (player.rotation == 3) {
				Projectile iceOrbball = new Projectile("iceOrbImg", 3, 350, 1, player.x - 50, player.y - 125, 250, 250);
				om.addObject(iceOrbball);
			}
			spell = "";

		} else {
			spell = "";
		}

	}

	public void showInventory(Graphics g) {
		g.drawImage(invImg, 50, 50, 700, 700, null);
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).substring(0, 5).equalsIgnoreCase("Sword")) {
				g.drawImage(swordImg, i * 100 + 100, 250, 100, 100, null);
			}
		}
	}
	public void showHelp(Graphics g) {
		g.drawImage(helpImg, 25, 25, 750, 750, null);
		plusStrength.setEnabled(false);
		plusStrength.setVisible(false);
		minusStrength.setEnabled(false);
		minusStrength.setVisible(false);
		minusSpeed.setEnabled(false);
		minusSpeed.setVisible(false);
		plusSpeed.setEnabled(false);
		plusSpeed.setVisible(false);
		minusHealth.setEnabled(false);
		minusHealth.setVisible(false);
		plusHealth.setEnabled(false);
		plusHealth.setVisible(false);
		
	}
}
