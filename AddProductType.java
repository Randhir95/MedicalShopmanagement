/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author dell
 */
public class AddProductType extends JInternalFrame{
    JTextField typename;
    JButton save,close,reset;
    ArrayList<String> type_id=new ArrayList<>();
    ArrayList<String> type_items=new ArrayList<>();
    public AddProductType()
    {
        super("ADD PRODUCTS TYPE",true,true);
        this.setSize(450, 200);
        this.setDefaultCloseOperation(AddProductType.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(470,150);
        StaticMember.lookAndFeel();
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
               StaticMember.ADD_NEW_PRODUCT=false;
              }});
        
        //set the Heading
        JLabel head=new JLabel("CREATE PRODUCTS TYPE",JLabel.CENTER);
        head.setFont(StaticMember.HEAD_W_FONT);
        head.setOpaque(true);
        head.setBackground(StaticMember.HEAD_BG_COLOR1);
        head.setForeground(StaticMember.HEAD_FG_COLOR1);
        this.add(head,BorderLayout.NORTH);
        
        typename=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Product Type Name "," PRODUCT TYPE NAME ");
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        save=StaticMember.MyButton("SAVE","Click On Button To Save The Record");
        
        JPanel mainPanel=new JPanel(new BorderLayout());
        JPanel mainGridPanel=new JPanel(new GridLayout(2,1,10,10));
        JPanel btpanel=new JPanel(new GridLayout(1,3,10,10));
        this.add(mainPanel,BorderLayout.CENTER);
        mainPanel.add(new JLabel("     "),BorderLayout.EAST);mainPanel.add(new JLabel("     "),BorderLayout.WEST);
        mainPanel.add(new JLabel("     "),BorderLayout.NORTH);mainPanel.add(new JLabel("     "),BorderLayout.SOUTH);
        mainPanel.add(mainGridPanel,BorderLayout.CENTER);
        mainGridPanel.add(typename);mainGridPanel.add(btpanel);
        btpanel.add(save);btpanel.add(reset);btpanel.add(close);
        
        typename.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    save.requestFocusInWindow();
                }
             }});
        
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.ADD_NEW_PRODUCT=false;
                    AddProductType.this.dispose();
                }
             }});
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_NEW_PRODUCT=false;
                AddProductType.this.dispose();
                
            }});
        save.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    codeToSave();
             }});
        
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToSave();
            }});
        reset.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    reset();
                }}});
        
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                reset();
            }});
        
    }
    
    //code to save
     public void codeToSave()
     {
         if(typename.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Type Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            typename.requestFocusInWindow();
           return;
        }
        try
        {
            PreparedStatement stmt=StaticMember.con.prepareStatement("insert into type(type_id,type_name) values(?,?)");
            ResultSet type_rset=StaticMember.con.createStatement().executeQuery("select max(type_id) as type_id  from type");
            type_rset.next();
            int t_id=type_rset.getInt("type_id")+1;
            stmt.setInt(1, t_id);
            stmt.setString(2, typename.getText());
            stmt.execute();
            reset();
            JOptionPane.showMessageDialog(this, "Record saved!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            MDIMainWindow.a_n_p_obj.codeToSetType();
            
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
      public void reset()
    {
        typename.setText("");
        typename.requestFocusInWindow();
    }
}
