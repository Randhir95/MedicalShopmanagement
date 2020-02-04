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
import java.sql.Statement;
import java.util.ArrayList;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author dell
 */
public class DeleteProductType extends JInternalFrame{
    private JTextField dtypename,did,typelist;
    private JButton delete,close,reset;
    private String sid;
    private ResultSet rset=null;
    ArrayList<String> type_id=new ArrayList<>();
    ArrayList<String> type_items=new ArrayList<>();
    public DeleteProductType()
    {
        super("",true,true);
        this.setSize(450, 280);
        this.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(470,150);
        StaticMember.lookAndFeel();
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.DELETE_TYPE=false;
              }});
        
        JLabel head=new JLabel("REMOVE PRODUCTS TYPE",JLabel.CENTER);
        head.setFont(StaticMember.HEAD_W_FONT);
        head.setOpaque(true);
        head.setBackground(StaticMember.HEAD_BG_COLOR1);
        head.setForeground(StaticMember.HEAD_FG_COLOR1);
        this.add(head,BorderLayout.NORTH);
        
        typelist=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Select Product Type Name "," SELECT TYPE NAME ");
        StaticMember.setToType(typelist, type_id, type_items);
        dtypename=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Product Type Name "," PRODUCT TYPE NAME ");
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        delete=StaticMember.MyButton("DELETE","Click On Button To Delete The Record");
        
        JPanel mainPanel=new JPanel(new BorderLayout());
        JPanel mainGridPanel=new JPanel(new GridLayout(3,1,10,10));
        JPanel btpanel=new JPanel(new GridLayout(1,3,10,10));
        this.add(mainPanel,BorderLayout.CENTER);
        mainPanel.add(new JLabel("     "),BorderLayout.EAST);mainPanel.add(new JLabel("     "),BorderLayout.WEST);
        mainPanel.add(new JLabel("     "),BorderLayout.NORTH);mainPanel.add(new JLabel("     "),BorderLayout.SOUTH);
        mainPanel.add(mainGridPanel,BorderLayout.CENTER);
        mainGridPanel.add(typelist);mainGridPanel.add(dtypename);mainGridPanel.add(btpanel);
        btpanel.add(delete);btpanel.add(reset);btpanel.add(close);
        
        
        typelist.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    try
                    {
                        ResultSet cr=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+type_id.get(type_items.indexOf(typelist.getText()))+"'");
                        cr.next();
                        dtypename.setText(cr.getString("type_name"));
                        dtypename.requestFocusInWindow();
                        sid=cr.getString("type_id");
                    }
                    catch(SQLException ex)
                    {
                        
                    }
                }
             }});
        dtypename.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    delete.requestFocusInWindow();
             }});
        
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.DELETE_TYPE=false;
                    DeleteProductType.this.dispose();
                }
             }});
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.DELETE_TYPE=false;
                    DeleteProductType.this.dispose();
            }       });
        delete.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                codeToDelete();
             }}});
        
        delete.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                codeToDelete();
            }       });
        reset.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                reset(); 
             }}});
        
        reset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                reset(); 
            }       });
    }
    
    //code to Delete
    private void codeToDelete()
    {
         try
        {
          PreparedStatement stmt=StaticMember.con.prepareStatement("delete from type where type_id=?");
        
          stmt.setString(1, sid);         
          stmt.executeUpdate();
          StaticMember.setToType(typelist, type_id, type_items);
          JOptionPane.showMessageDialog(this, "Record Delete Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
          reset();        
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
    public void reset()
    {
        typelist.setText("");
        dtypename.setText("");
        typelist.requestFocusInWindow();
    }
}
