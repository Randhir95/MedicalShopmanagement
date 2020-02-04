/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author Randhir
 */
public class CreateNewUser extends JInternalFrame{
    private JTextField user_name,user_id,user_address,user_mob,user_email;
    private JPasswordField user_password,user_conform_password;
    private JButton create,reset;
    public CreateNewUser()
    {
        super("CREATE NEW USER",true,true);
        this.setSize(700,320);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(320,100);
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.CREATE_NEW_USER=false;
              }});
        
        JLabel username=new JLabel("USER NAME : ",JLabel.RIGHT);
        username.setFont(StaticMember.labelFont);
        JLabel userid=new JLabel("USER ID : ",JLabel.RIGHT);
        userid.setFont(StaticMember.labelFont);
        JLabel userpassword=new JLabel("USER PASSWORD : ",JLabel.RIGHT);
        userpassword.setFont(StaticMember.labelFont);
        JLabel userconformpassword=new JLabel("USER CONFIRM PASSWORD : ",JLabel.RIGHT);
        userconformpassword.setFont(StaticMember.labelFont);
        JLabel useraddress=new JLabel("USER ADDRESS : ",JLabel.RIGHT);
        useraddress.setFont(StaticMember.labelFont);
        JLabel usermob=new JLabel("USER MOB : ",JLabel.RIGHT);
        usermob.setFont(StaticMember.labelFont);
        JLabel useremail=new JLabel("USER EMAIL ID : ",JLabel.RIGHT);
        useremail.setFont(StaticMember.labelFont);
        
        user_name=new JTextField("");
        user_name.setFont(StaticMember.textFont);
        user_name.setBackground(StaticMember.bcolor);
        user_id=new JTextField("");
        user_id.setFont(StaticMember.textFont);
        user_id.setBackground(StaticMember.bcolor);
        user_address=new JTextField("");
        user_address.setFont(StaticMember.textFont);
        user_address.setBackground(StaticMember.bcolor);
        user_mob=new JTextField("");
        user_mob.setFont(StaticMember.textFont);
        user_mob.setBackground(StaticMember.bcolor);
        user_password=new JPasswordField("");
        user_password.setFont(StaticMember.textFont);
        user_password.setBackground(StaticMember.bcolor);
        user_conform_password=new JPasswordField("");
        user_conform_password.setFont(StaticMember.textFont);
        user_conform_password.setBackground(StaticMember.bcolor);
        user_email=new JTextField("");
        user_email.setFont(StaticMember.textFont);
        user_email.setBackground(StaticMember.bcolor);
        
        create=new JButton("CREATE");
        create.setFont(StaticMember.buttonFont);
        reset=new JButton("RESET");
        reset.setFont(StaticMember.buttonFont);
        
        JPanel Buttonpanel=new JPanel(new GridLayout(1,5,10,10));
        JPanel mainpanel=new JPanel(new GridLayout(7,2,5,5));
        mainpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"User Detail"));
        mainpanel.add(username);mainpanel.add(user_name);mainpanel.add(userid);mainpanel.add(user_id);
        mainpanel.add(userpassword);mainpanel.add(user_password);mainpanel.add(userconformpassword);mainpanel.add(user_conform_password);
        mainpanel.add(useraddress);mainpanel.add(user_address);
        mainpanel.add(usermob);mainpanel.add(user_mob);mainpanel.add(useremail);mainpanel.add(user_email);
        Buttonpanel.add(new JLabel(""));Buttonpanel.add(new JLabel(""));Buttonpanel.add(new JLabel(""));Buttonpanel.add(create);Buttonpanel.add(reset);
        this.add(mainpanel,BorderLayout.CENTER);
        this.add(Buttonpanel,BorderLayout.SOUTH);
        
        
        user_name.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                user_id.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        user_id.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                user_password.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        user_password.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                user_conform_password.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        /*user_conform_password.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) { }
            public void focusLost(FocusEvent e) { 
                String pass=user_password.getText(),cpss=user_conform_password.getText();
                if(!pass.equals(cpss))
                {
                    JOptionPane.showMessageDialog(null, "PlZ Enter Valid Password!", "Error!", JOptionPane.WARNING_MESSAGE);
                    //user_password.setText("");
                    user_conform_password.setText("");
                    user_conform_password.requestFocusInWindow();
                }
            } });*/
        user_conform_password.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    String pass=user_password.getText(),cpss=user_conform_password.getText();
                    if(!pass.equals(cpss))
                    {
                        JOptionPane.showMessageDialog(null, "PlZ Enter Valid Password!", "Error!", JOptionPane.WARNING_MESSAGE);
                        //user_password.setText("");
                        user_conform_password.setText("");
                        user_conform_password.requestFocusInWindow();
                    }
                    else
                    user_address.requestFocusInWindow();
                }
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        
        user_address.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                    e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                user_mob.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        user_mob.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                user_email.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        user_email.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                create.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        create.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    saveData();
                    user_name.requestFocusInWindow();
            }}
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        reset.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                reset();
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveData();
                user_name.requestFocusInWindow();
            }});
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
            }});
    }
    
    public void reset()
    {
        user_name.setText("");
        user_id.setText("");
        user_password.setText("");
        user_conform_password.setText("");
        user_address.setText("");
        user_mob.setText("");
        user_email.setText("");
        user_name.requestFocusInWindow();
    }
    
    public void saveData()
    {
        //validate data  
     
        if(user_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "User Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            user_name.requestFocusInWindow();
           return;
        }
        if(user_id.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "User Id Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            user_id.requestFocusInWindow();
            return;
        }
        
        
        //code to save
        try
        {
          //char[] passchar =user_password.getPassword();
          PreparedStatement stmt=StaticMember.con.prepareStatement("insert into users(user_name,user_id,user_password,user_address,user_mob,user_email) values(?,?,?,?,?,?)");
          stmt.setString(1, user_name.getText());
          stmt.setString(2, user_id.getText());
          stmt.setString(3, user_password.getText());
          stmt.setString(4, user_address.getText());
          stmt.setString(5, user_mob.getText());
          stmt.setString(6, user_email.getText());
          stmt.execute();
          JOptionPane.showMessageDialog(this, "Record saved!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
          reset();
          user_name.requestFocus();
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
}
