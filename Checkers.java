//Hannah Mitchell
//May 3rd 2018
//Homework assignment 5:
//Two checkers move in opposite directions along a board
//The stop and go button affects their movement
//A mouse press and release affects their movement

import java.awt.*;
import java.applet.*; 
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Checkers extends Applet implements Runnable, MouseListener
{
   private int redX, redY;  
   private int blackX, blackY;
   private Boolean running;
   
   //button variables
   private JPanel panel;
   private JButton goButton, stopButton;
   private GoButtonHandler goButtonHandler;
   private StopButtonHandler stopButtonHandler;
   
   // start method of Applet is called when user re-enters the web page 
   // containing the applet
   public void init()
   { 
      //set original checker coordinates
      redX = 25;
      redY = 25;
      blackX = 175;
      blackY = 475;

      //set go button
      goButton = new JButton("Go");
      goButtonHandler = new GoButtonHandler();
      goButton.addActionListener(goButtonHandler);

      //set stop button
      stopButton = new JButton("Stop");
      stopButtonHandler = new StopButtonHandler();
      stopButton.addActionListener(stopButtonHandler);

      //add buttons to panel
      panel = new JPanel();
      panel.setLayout(new GridLayout(1,2));
      panel.add(goButton);
      panel.add(stopButton);

      //for checkers to pause when applet is clicked
      addMouseListener(this);

      //add panel to applet
      setLayout(new BorderLayout());
      add("South", panel);

      //thread setup
      running = true;
      Thread t = new Thread (this);
      t.start();  // spawn the thread
   }

   public void run()
   {
      int dy = 150;
      
      //runs continuously unless program exited
      while (true)
      {
            for (int i = 0; i < 3; i++)
            {               
               //sleep if stop button pressed or mouse pressed
               while (running == false)
               {
                  try
                  {
                     Thread.sleep(100);
                  } catch (InterruptedException e) { }
               }
               
               //set new checker coordinates
               redY += dy;
               blackY += (dy / -1);
               repaint();
               
               //pause before next movement
               try
               {
                  Thread.sleep(500);
               } catch (InterruptedException e) { }
            }

            // lets checkers switch between up and down
            dy = -dy;   
      }
   }   
   
   public void paint(Graphics g)
   {
      super.paint(g);
      
      //gray squares that make checkerboard
      g.setColor(Color.gray);
      g.fillRect(0, 0, 150, 150);
      g.fillRect(0, 300, 150, 150);
      g.fillRect(150, 150, 150, 150);
      g.fillRect(150, 450, 150, 150);
      
      //red and black checkers
      g.setColor(Color.red);
      g.fillOval(redX, redY, 100, 100);
      g.setColor(Color.black);
      g.fillOval(blackX, blackY, 100, 100);
   }

   //click events
   public void mousePressed(MouseEvent e) 
   {
      running = false;
   }

   public void mouseReleased(MouseEvent e) 
   {
      running = true;
   }

   //no actions for following overloaded MouseListener methods
   public void mouseEntered(MouseEvent e) { } //do nothing
   public void mouseExited(MouseEvent e) { }  //do nothing
   public void mouseClicked(MouseEvent e) { } //do nothing

   //button events
   private class GoButtonHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         running = true;
      }
   }

   private class StopButtonHandler implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         running = false;
      }
   }
}