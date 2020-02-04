/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.scene.control.DatePicker;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author Intel
 */
public class SaleStatement extends JInternalFrame{
    private JLabel sdate,enddate;
    private JButton sbutton,ebutton,print,close;
    //UtilDateModel dob,tdob;
    //JDatePickerImpl dob2,tdob2;
    public SaleStatement()
    {
        super("SALE STATEMENT");
        this.setSize(800, 400);
        this.setDefaultCloseOperation(SaleStatement.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(200,90);
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.SALE_STATMENT=false;
              }});
        
        JLabel l1=new JLabel("SALE STATEMENT",JLabel.CENTER);
        l1.setFont(new Font("",Font.BOLD,25));
        l1.setOpaque(true);
        l1.setBackground(StaticMember.bcolor);
        l1.setForeground(Color.RED);
        this.add(l1,BorderLayout.NORTH);
        this.setBackground(StaticMember.bcolor);
        JPanel p2=new JPanel(new GridLayout(1,2,15,15));
        p2.setBackground(StaticMember.bcolor);
        JPanel p3=new JPanel(new BorderLayout());
        p3.setBackground(StaticMember.bcolor);
        JPanel p4=new JPanel(new GridLayout(1,4));
        p4.setBackground(StaticMember.bcolor);
        JPanel dp=new JPanel(new BorderLayout());
        dp.setBackground(StaticMember.bcolor);
        JLabel l2=new JLabel("START DATE",JLabel.CENTER);
        l2.setFont(StaticMember.labelFont);
        l2.setForeground(StaticMember.flcolor);
        dp.add(l2,BorderLayout.WEST);
        /*
        dob=new UtilDateModel();
        JDatePanelImpl dob1=new JDatePanelImpl(dob);
         dob2=new JDatePickerImpl(dob1);
         JFormattedTextField t2=dob2.getJFormattedTextField();
         t2.setFont(StaticMember.labelFont);
         t2.setBackground(StaticMember.bcolor);
         dob2.setBackground(StaticMember.bcolor);
         dob2.setLayout(new FlowLayout(FlowLayout.LEFT));
        dp.add(dob2,BorderLayout.CENTER);

        
        
        JPanel dp1=new JPanel(new BorderLayout());
        dp1.setBackground(StaticMember.bcolor);
        JLabel l3=new JLabel("END DATE",JLabel.CENTER);
        l3.setFont(StaticMember.labelFont);
        l3.setForeground(StaticMember.flcolor);
        dp1.add(l3,BorderLayout.WEST);
        p2.add(dp);
        p2.add(dp1);
        
        tdob=new UtilDateModel();
        JDatePanelImpl tdob1=new JDatePanelImpl(tdob);
         tdob2=new JDatePickerImpl(tdob1);
         JFormattedTextField t1=tdob2.getJFormattedTextField();
         t1.setFont(StaticMember.labelFont);
         t1.setBackground(StaticMember.bcolor);
         tdob2.setBackground(StaticMember.bcolor);
         tdob2.setLayout(new FlowLayout(FlowLayout.LEFT));
         dp1.add(tdob2,BorderLayout.CENTER);
        
        
        print=new JButton("PRINT");
        print.setFont(StaticMember.buttonFont);
        print.setForeground(StaticMember.bfcolor);
        close=new JButton("CLOSE");
        close.setFont(StaticMember.buttonFont);
        close.setForeground(StaticMember.bfcolor);
               
        p3.add(p2,BorderLayout.NORTH);
        
        p4.add(new JLabel(""));
        p4.add(new JLabel(""));
        p4.add(close);
        p4.add(print);
        this.add(p3,BorderLayout.CENTER);
        this.add(p4,BorderLayout.SOUTH);
        
        print.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                printStatement();
           }
        });
        print.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                     printStatement();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                SaleStatement.this.dispose();
                StaticMember.SALE_STATMENT=false;
           }
        });
        close.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                     SaleStatement.this.dispose();
               StaticMember.SALE_STATMENT=false;
            }}
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        
    }
    
    private void printStatement()
    {
        if(!dob.isSelected()&& !tdob.isSelected()) 
        {
            JOptionPane.showMessageDialog(null, "Plz Select Star date & End Date!");
            return;
        }
        if(!dob.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Plz Select Star date!");
            return;
        }
        if(!tdob.isSelected())
        {
            JOptionPane.showMessageDialog(null, "Plz Select End date!");
            return;
        }
        //JOptionPane.showMessageDialog(null, "Retun Printed");
        SStatementPage ob1=new SStatementPage(dob.getYear()+"-"+(dob.getMonth()+1)+"-"+dob.getDay(),tdob.getYear()+"-"+(tdob.getMonth()+1)+"-"+tdob.getDay()); 
        if(ob1.printStatement())
            JOptionPane.showMessageDialog(null, "Sale Statement Printed");
        else
            JOptionPane.showMessageDialog(null, "Error!");
    }*/
    }}
