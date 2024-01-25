package main;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main {
	static Panel mainPanel;
	static JFrame frame;
	
	public static void main(String[] args) {
		create();
	}
	
	private static void create() {
		
		
		frame = new JFrame("Falling Sand");
		mainPanel = new Panel(frame);
		frame.add(mainPanel);
		
		frame.setFocusable(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	

}
