package lootPackage;

import java.util.Random;
import java.util.Timer;

import javax.swing.JFrame;



public class Looters {
	GamePanel gprun;

	JFrame frame;
	static final int WIDTH = 816;
	static final int HEIGHT = 834;
	
public static void main(String[] args) {
Looters toRun = new Looters();

	
	toRun.setup();
}
public Looters(){
	gprun = new GamePanel();
	
	frame = new JFrame();

}
public void setup(){
	frame.isResizable();
	frame.setSize(WIDTH, HEIGHT);
	frame.add(gprun);
	frame.addKeyListener(gprun);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	gprun.startGame();
	gprun.generate();


}

}
