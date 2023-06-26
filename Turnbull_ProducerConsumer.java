//Thessalonica Turnbull
//Advanced Java Spring 2023
//Created April 13, 2023
//
//worked with Kyle Nguyen to complete this program
//
//


import java.util.concurrent.Semaphore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Turnbull_ProducerConsumer extends JPanel implements ActionListener{

static int theBuffer = 0;
static Semaphore s = new Semaphore(1);
static int intNumOfPro = 0;
static int intNumOfConsum = 0;
static int intAveValPro = 0;
static int intAveValCon = 0;

JTextArea numOfPro;
JTextArea numOfConsum;
JTextArea aveValPro;
JTextArea aveValCon;
JButton btnStart;
static JLabel lblStateOfRes;
static JTextArea resultBox;
JButton btnClose;

//constructor -designs layout
public Turnbull_ProducerConsumer(){
	//layout set for each panel individually
	JPanel titlePanel = new JPanel();
	JPanel userInputPanel = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel resultPanel = new JPanel();
	
	Font appFontLarge = new Font("Arial", Font.PLAIN, 50);
	Font appFontSmall = new Font("Arial", Font.PLAIN, 18);

	//title
	JLabel titleLabel = new JLabel("Producer Consumer");
	titleLabel.setFont(appFontLarge);
	titlePanel.add(titleLabel, BorderLayout.NORTH);
  
	//userinput
	JLabel lblNumOfPro = new JLabel("Number of Producers: ");
	lblNumOfPro.setFont(appFontSmall);
	numOfPro = new JTextArea(1, 10);
	JLabel lblNumOfConsum = new JLabel("Number of Consumers: ");
	lblNumOfConsum.setFont(appFontSmall);
	numOfConsum = new JTextArea(1, 10);
	JLabel lblAveValPro = new JLabel("Average Value Produced: ");
	lblAveValPro.setFont(appFontSmall);
	aveValPro = new JTextArea(1, 10);
	JLabel lblAveValCon = new JLabel("Average Value Consumed: ");
	lblAveValCon.setFont(appFontSmall);
	aveValCon = new JTextArea(1, 10);
	
	userInputPanel.setLayout(new GridLayout(8,1));
	userInputPanel.add(lblNumOfPro);
	userInputPanel.add(numOfPro);
	userInputPanel.add(new JLabel()); //empty JLabels to add verticle space between items
	userInputPanel.add(new JLabel());
	userInputPanel.add(lblNumOfConsum);
	userInputPanel.add(numOfConsum);
	userInputPanel.add(new JLabel());
	userInputPanel.add(new JLabel());
	userInputPanel.add(lblAveValPro);
	userInputPanel.add(aveValPro);
	userInputPanel.add(new JLabel());
	userInputPanel.add(new JLabel());
	userInputPanel.add(lblAveValCon);
	userInputPanel.add(aveValCon);
	centerPanel.add(userInputPanel, BorderLayout.CENTER);
	
  
	//results
	btnStart = new JButton("Start");
	btnStart.setFont(appFontSmall);
	resultBox = new JTextArea(7,30);
	resultBox.setFont(appFontSmall);
	resultBox.setText("Results will show in here.");
	btnClose = new JButton("Close");
	btnClose.setFont(appFontSmall);
	
	resultPanel.setLayout(new GridBagLayout()); //use this because issues with other layouts
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.gridx = 0;
	gbc.gridy = 0;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.insets = new Insets(0, 0, 30, 0); // Add spacing between components vertically
	resultPanel.add(btnStart, gbc);
	gbc.gridy = 1; // Update gridy to 1
	gbc.anchor = GridBagConstraints.CENTER; // Set anchor to CENTER
	gbc.insets = new Insets(5, 0, 0, 0); 
	lblStateOfRes = new JLabel("Resources: " + theBuffer);
	lblStateOfRes.setFont(appFontSmall);
	lblStateOfRes.setOpaque(true); // Set opaque to true to enable background color
	lblStateOfRes.setBackground(Color.YELLOW); // Set the desired background color
	lblStateOfRes.repaint();
	resultPanel.add(lblStateOfRes, gbc); 
	gbc.gridy = 2;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.insets = new Insets(10, 0, 0, 0); 
	JScrollPane scrollPane = new JScrollPane(resultBox);
	resultPanel.add(scrollPane, gbc);
	gbc.gridy = 3;
	gbc.anchor = GridBagConstraints.CENTER;
	gbc.insets = new Insets(5, 0, 0, 0);
	resultPanel.add(btnClose, gbc);
	
	
	//put all JPanels into BorderLayout
	this.add(titlePanel);
	this.add(userInputPanel);
	this.add(resultPanel);
	
	
	//actionListener handles
	btnClose.addActionListener(this);
	btnStart.addActionListener(this);
}

@Override
public void actionPerformed(ActionEvent e) {
	if(e.getSource() == btnClose) {//exits program
		System.exit(0);
	}
	if(e.getSource() == btnStart) {//does conversions based on user input/selection
        if (numOfPro.getText().equals("")|| numOfConsum.getText().equals("")
        		|| aveValPro.getText().equals("")|| aveValCon.getText().equals("")) {
        	
            JOptionPane.showMessageDialog(null, "All textboxes must have an amount.", "Getting Error", JOptionPane.ERROR_MESSAGE);
        } else {
        	resultBox.setText(null);
        	intNumOfPro = Integer.parseInt(numOfPro.getText());
        	intNumOfConsum = Integer.parseInt(numOfConsum.getText());
        	intAveValPro = Integer.parseInt(aveValPro.getText());
        	intAveValCon = Integer.parseInt(aveValCon.getText());
        	String[] values = {Integer.toString(intAveValCon), Integer.toString(intNumOfConsum), Integer.toString(intAveValPro), Integer.toString(intAveValCon)};
        	main(values);
        }
	}
	resultBox.setCaretPosition(resultBox.getDocument().getLength()); // ensure scrollbar is at the bottom
}

public static void mySleep(){
   // this function puts the thread "to sleep" for a while,
   // to simulate time spent processing 

   try{
      Thread.sleep((int)(Math.random()*1000));
   }
   catch(InterruptedException e){
      // do nothing
   }
} 

public static void main(String[] args){
 
   Consumer [] c = new Consumer[intNumOfConsum];
   Producer [] p = new Producer[intNumOfPro];

   for(int i = 0; i < intNumOfConsum; i++){
      c[i] = new Consumer(i);
      c[i].start();
   }
   for(int i = 0; i < intNumOfPro; i++){
      p[i] = new Producer(i);
      p[i].start();
   }
}

private static class Producer extends Thread{
   int i;
   public Producer(int i){
     super();
     this.i = i;
   }

   public void run(){
      while(true){
         mySleep();
         resultBox.append("Producer " + i + ": attempting to acquire\n");
         try{
            s.acquire();
            resultBox.append("Producer " + i + ": resource acquired!\n");
            mySleep();
            resultBox.append("Producer " + i + ": theBuffer (pre)  is " + theBuffer + "\n");
            theBuffer += (int) (Math.random()*(intAveValPro * 2)); //inAveValPro*2 because.. ie the user inputs 3 as the average, the program will generate random numbers from 1-6 so that the average is about 3
            recourseTextboxColor();
            resultBox.append("Producer " + i + ": theBuffer (post) is " + theBuffer + "\n");
            resultBox.append("Producer " + i + ": resource released\n");
            s.release();
         }
         catch(InterruptedException e){}
      }   
   }
}

private static class Consumer extends Thread{
   int i;
   public Consumer(int i){
      super();
      this.i = i;
   }

   public void run(){
      while(true){
         mySleep();
         resultBox.append("Consumer " + i + ": attempting to acquire\n");
         try{
            s.acquire();
            resultBox.append("Consumer " + i + ": resource acquired!\n");
            mySleep();
            resultBox.append("Consumer " + i + ": theBuffer is " + theBuffer + "\n");
            int need = (int) (1 + Math.random()*(intAveValCon * 2)); //inttAveValCon*2 because.. ie the user inputs 3 as the average, the program will generate random numbers from 1-6 so that the average is about 3
            resultBox.append("Consumer " + i + ": my need is " + need + "\n");
            if (theBuffer >= need){ 
               theBuffer -= need;
               resultBox.append("Consumer " + i + ": got what I needed!\n");
               resultBox.append("Consumer " + i + ": theBuffer is now " + theBuffer + "\n");
               recourseTextboxColor();
            }
            else{
            	resultBox.append("Consumer " + i + ": resource unavailable\n");
            }
            resultBox.append("Consumer " + i + ": resource released\n");
            s.release();
         }
         catch(InterruptedException e){}
      }
   }
}
public static void recourseTextboxColor() {
	if (theBuffer == 0) {
		lblStateOfRes.setOpaque(true); // Set opaque to true to enable background color
		lblStateOfRes.setBackground(Color.YELLOW); // Set the desired background color
		lblStateOfRes.repaint();
		lblStateOfRes.setText("Resource: " + theBuffer);
	}else if (theBuffer > 0){
		lblStateOfRes.setOpaque(true); // Set opaque to true to enable background color
		lblStateOfRes.setBackground(Color.GREEN); // Set the desired background color
		lblStateOfRes.repaint();
		lblStateOfRes.setText("Resource: " + theBuffer);
	}else {
		lblStateOfRes.setOpaque(true); // Set opaque to true to enable background color
		lblStateOfRes.setBackground(Color.RED); // Set the desired background color
		lblStateOfRes.repaint();
		lblStateOfRes.setText("Resource: " + theBuffer);
	}
}


 
} 
