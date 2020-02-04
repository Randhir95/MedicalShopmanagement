/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author RANDHIR KUMAR
 */
public class LatterPad extends JInternalFrame{
    JButton print;
    JTextField lrno,adharno;
    JTextArea some_text;
    JLabel lrcap,adhar,someother;
    public LatterPad()
    {
        super("",true,true);
        StaticMember.setSize(this);
        this.setDefaultCloseOperation(LatterPad.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.LATTER_PAD=false;
              }});
        
        StaticMember.labelHeding("LATTER PAD", this);
        
        lrno=StaticMember.MyInputBox("", 50, StaticMember.STRING_TYPE , "ENTER LR NO.");
        adharno=StaticMember.MyInputBox("", 12, StaticMember.INTEGER_TYPE , "ENTER AADHAR NO.");
        lrcap=StaticMember.MyLabel("           LR NO.  :  ", JLabel.RIGHT);
        adhar=StaticMember.MyLabel(" AADHAR NO.  :  ", JLabel.RIGHT);
        someother=StaticMember.MyLabel(" SOME OTHER TEXT (OPTIONAL)  :  ", JLabel.RIGHT);
        some_text=new JTextArea("");
        some_text.setFont(StaticMember.textFont);
        print=StaticMember.button("PRINT");
        
        JPanel main=new JPanel(new BorderLayout());
        JPanel main2=new JPanel(new BorderLayout());
        JPanel mainPanel=new JPanel(new GridLayout(2,3));
        JPanel maincontextPanel=new JPanel(new GridLayout(2,1));
        JPanel lrPanel=new JPanel(new BorderLayout());
        JPanel adharPanel=new JPanel(new BorderLayout());
        JPanel sometextPanel=new JPanel(new BorderLayout());
        JPanel buttonPanel=new JPanel(new BorderLayout());
        lrPanel.add(lrcap,BorderLayout.WEST);lrPanel.add(lrno,BorderLayout.CENTER);
        adharPanel.add(adhar,BorderLayout.WEST);adharPanel.add(adharno,BorderLayout.CENTER);
        sometextPanel.add(someother,BorderLayout.WEST);sometextPanel.add(some_text,BorderLayout.CENTER);
        maincontextPanel.add(adharPanel);maincontextPanel.add(lrPanel);
        mainPanel.add(new JLabel("   "));mainPanel.add(maincontextPanel);mainPanel.add(new JLabel("   "));
        mainPanel.add(new JLabel("   "));mainPanel.add(new JLabel("  "));mainPanel.add(new JLabel("  "));
        main.add(new JLabel("   "),BorderLayout.NORTH);main.add(mainPanel,BorderLayout.CENTER);
        main2.add(main,BorderLayout.NORTH);main2.add(sometextPanel,BorderLayout.CENTER);
        main.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,150));
        buttonPanel.add(print,BorderLayout.EAST);
        this.add(main2,BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    printLatter(some_text.getText(),lrno.getText(),adharno.getText());
            }});
        print.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                printLatter(some_text.getText(),lrno.getText(),adharno.getText());
            }});
        
    }
    
    private void printLatter(String str1,String str2,String str3)
    {
        LatterPadPrintPage lppp=new LatterPadPrintPage(str1,str2,str3);
        if(lppp.printLatterPad())
            JOptionPane.showMessageDialog(null, "Latter Pad Printed!");

         
    }
    
}
