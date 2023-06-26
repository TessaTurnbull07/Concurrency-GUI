//Thessalonica Turnbull
//Advanced Java Spring 2023
//Created April 13, 2023
//
//worked with Kyle Nguyen to complete this program
//
//

import java.awt.*;
import javax.swing.*;

public class Turnbull_ProducerConsumerJFrame extends JFrame{
	
	//global variables
	private static final int WIDTH = 500;
	private static final int HEIGHT = 600;
	
	public static void main (String [] args) {
		Turnbull_ProducerConsumerJFrame g = new Turnbull_ProducerConsumerJFrame();
	}
	
	//constructor
	public Turnbull_ProducerConsumerJFrame(){
		super("Producer Consumer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel jp = new Turnbull_ProducerConsumer();
		add(jp);
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setVisible(true);
	}
	
}