/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Randhir
 */
public class LoginUsers extends JDialog{
    JTextField user_id,admin_id;
    JPasswordField user_password,admin_password;
    JButton login,close;
    Image img;
    String str;
    public int isSuccess=0;
    private JPanel tabpanel,utabpanel;
    private MDIMainWindow mmdi;
    //MDIMainWindow mi;
    public LoginUsers()
    {
        super(MDIMainWindow.self,"LOG IN",true);
        this.setSize(500,280);
        //this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setLocation(600,200);
        this.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent we)
        {
            if(JOptionPane.showConfirmDialog(MDIMainWindow.self,"Are You Sure To Close?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
            {
                LoginUsers.this.dispose();

            }
            //System.exit(0);
        }
        });
        
        
        StaticMember.lookAndFeel();
        //this.mmdi=mdi;
     
        JLabel l1=new JLabel("LOG IN",JLabel.CENTER);
        l1.setFont(StaticMember.headFont);
        l1.setBackground(StaticMember.HEAD_BG_COLOR);
        l1.setOpaque(true);
        l1.setForeground(Color.WHITE);
        this.add(l1,BorderLayout.NORTH);
        
       /* JPanel main_body_panel=new JPanel(new BorderLayout()){

            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                try{img = ImageIO.read(getClass().getResource("/images/javabackground.jpg"));}
                catch(Exception e){System.out.println(e.getMessage());}
                if(img != null) g.drawImage(img, 0,0,this.getWidth(),this.getHeight(),this);
            }
        };*/
        
        admin_id=StaticMember.MyInputBox("", 10, StaticMember.STRING_TYPE, StaticMember.UPPER_CASE, "Enter LogIn ID", "",0);
        admin_password=StaticMember.MyPasswordBox("", 10, "Enter Password", " ",0);
        login=StaticMember.resizedButton("", "/images/menuicon/signin.png", 45);
        close=StaticMember.resizedButton("", "/images/menuicon/exit.jpg", 45);
        JLabel label1=new JLabel("");
        label1.setIcon(StaticMember.getResizeImageIcon("/images/user.png", 45, 45));
        label1.setBackground(StaticMember.L_BG_COLOR);
        label1.setOpaque(true);
        JLabel label2=new JLabel("");
        label2.setIcon(StaticMember.getResizeImageIcon("/images/key2.png", 45, 45));
        label2.setBackground(StaticMember.L_BG_COLOR);
        label2.setOpaque(true);
        
        JPanel main_panel=new JPanel(new BorderLayout());
        JPanel bt_panel=new JPanel(new BorderLayout());
        JPanel id_panel=new JPanel(new BorderLayout());
        JPanel pass_panel=new JPanel(new BorderLayout());
        JPanel button_panel=new JPanel(new GridLayout(1,6,10,10));
        JPanel text_panel=new JPanel(new GridLayout(3,1,10,10));
        pass_panel.setBackground(StaticMember.L_BG_COLOR);
        //pass_panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        //id_panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        
        this.add(main_panel,BorderLayout.CENTER);
        main_panel.add(new JLabel("         "),BorderLayout.WEST);main_panel.add(new JLabel("         "),BorderLayout.EAST);
        main_panel.add(new JLabel("         "),BorderLayout.NORTH);main_panel.add(new JLabel("         "),BorderLayout.SOUTH);
        main_panel.add(text_panel,BorderLayout.CENTER);
        id_panel.add(label1,BorderLayout.WEST);id_panel.add(admin_id,BorderLayout.CENTER);
        pass_panel.add(label2,BorderLayout.WEST);pass_panel.add(admin_password,BorderLayout.CENTER);
        bt_panel.add(login,BorderLayout.WEST);bt_panel.add(close,BorderLayout.EAST);
        button_panel.add(new JLabel(" "));button_panel.add(new JLabel(" "));
        button_panel.add(login);button_panel.add(close);
        button_panel.add(new JLabel(" "));button_panel.add(new JLabel(" "));
        text_panel.add(id_panel);text_panel.add(pass_panel);text_panel.add(button_panel);
        
       
        admin_id.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    admin_password.requestFocusInWindow();
            }});
        admin_password.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    login.requestFocusInWindow();
            }});
        
        
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 LoginUsers.this.dispose();
            }});
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signIn();
            } });
        login.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                signIn();
            }});
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                LoginUsers.this.dispose();
            }});
        
    }
    public void sendUserData()
    {
        String sdata;
        sdata=user_id.getText();
        mmdi.getUserData(sdata);
    }
    public void sendAdminData(String adata)
    {
        
        adata=admin_id.getText();
    }
    
    public void signIn()
    {
        if(admin_id.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Admin Id Must Be Entered!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                    admin_id.requestFocusInWindow();
                    return;
                }
                if((new String(admin_password.getPassword())).equals(""))
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Password Must Be Entered!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                    admin_password.requestFocusInWindow();
                    return;
                }
                ResultSet rset;
                try {
                    rset = StaticMember.con.createStatement().executeQuery("SELECT * FROM self_company where admin_id like'"+admin_id.getText()+"'");
                    if(rset.next())
                    {
                        String pwd=new String(admin_password.getPassword());
                        if(rset.getString("admin_password").equals(pwd))
                        {
                            isSuccess=2;
                            
                        }    
                        else
                          isSuccess=0;  
                    }
                    else isSuccess=0;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                }
                LoginUsers.this.dispose();
    }
    public void logIn()
    {
        if(user_id.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"User Id Must Be Entered!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                    user_id.requestFocusInWindow();
                    return;
                }
                if((new String(user_password.getPassword())).equals(""))
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Password Must Be Entered!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                    user_password.requestFocusInWindow();
                    return;
                }
                ResultSet rset;
                try {
                    rset = StaticMember.con.createStatement().executeQuery("SELECT * FROM users where user_id like'"+user_id.getText()+"'");
                    if(rset.next())
                    {
                        String pwd=new String(user_password.getPassword());
                        if(rset.getString("user_password").equals(pwd))
                        {
                            isSuccess=1;
                            
                        }
                        else
                          isSuccess=0;  
                    }
                    else isSuccess=0;
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                }
                LoginUsers.this.dispose();
    }
}
