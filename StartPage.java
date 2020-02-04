/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

/**
 *
 * @author RANDHIR KUMAR
 */
public class StartPage extends JFrame{
    JProgressBar jb;    
    int i=0,num=0;
    public StartPage()
    {
        super("Start");
        this.setSize(300,200);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocation(300,200);
        jb=new JProgressBar(0,200);    
        jb.setBounds(40,40,160,30);         
        jb.setValue(0);    
        jb.setStringPainted(true);    
        jb.setBorderPainted(true);
        jb.setMaximum(200);
        jb.setMinimum(0);
        jb.updateUI();
        this.add(jb);    
          
    }
    /*public void iterate(){    
        while(i<=2000){    
          jb.setValue(i);    
          i=i+1;    
          try
          {
              Thread.sleep(10);
          }catch(Exception e)
          {
              JOptionPane.showMessageDialog(StartPage.this, e.getMessage(), "Opps", JOptionPane.ERROR_MESSAGE);
          }    
        }  
    }*/
    int j;
    public void iterate(){    
        for(i=0;i<=200;i++) 
        {
            for(j=0;j<=10;j++)
            {
                jb.setValue(i+j);
                
            }
              
          //jb.setValue(i+j);  
         /* try
          {
              Thread.sleep(10);
          }catch(Exception e)
          {
              JOptionPane.showMessageDialog(StartPage.this, e.getMessage(), "Opps", JOptionPane.ERROR_MESSAGE);
          }*/    
        }  
    }
    
}
