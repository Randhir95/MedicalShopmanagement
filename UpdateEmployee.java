/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.BorderFactory;
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
 * @author Randhir
 */
public class UpdateEmployee extends JInternalFrame{
    private JTextField utadress,utmob,utemail,utpin_code,uadhar,uempl_name,emp_ac,emp_ifsc,emp_bank_name,employee_id;
    private JButton uclose,ureset,usave;
    private ResultSet rset=null;
    int e_id;
    ArrayList<String> empl_id=new ArrayList<>();
    ArrayList<String> empl_items=new ArrayList<>();
    public UpdateEmployee()
    {
        super("",true,true);
        this.setSize(700,430);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(AddNewEmployee.DISPOSE_ON_CLOSE);
        this.setLocation(300,10);
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.ADD_NEW_PRODUCT=false;
              }});
        
        JLabel title=new JLabel("MODIFY EMPLOYEE",JLabel.CENTER);
        title.setFont(StaticMember.HEAD_W_FONT);
        title.setBackground(StaticMember.HEAD_BG_COLOR1);
        title.setOpaque(true);
        title.setForeground(StaticMember.HEAD_FG_COLOR1);
        this.add(title,BorderLayout.NORTH);
        
        JLabel uname=new JLabel(" EMPLOYEE NAME  : ",JLabel.RIGHT);
        uname.setFont(StaticMember.labelFont1);
        JLabel uadd=new JLabel(" ADDRESS : ",JLabel.RIGHT);
        uadd.setFont(StaticMember.labelFont1);
        JLabel umob=new JLabel(" MOBILE NO. : ",JLabel.RIGHT);
        umob.setFont(StaticMember.labelFont1);
        JLabel uemail=new JLabel(" EMAIL ID : ",JLabel.RIGHT);
        uemail.setFont(StaticMember.labelFont1);
        JLabel ifsc=new JLabel(" IFSC CODE : ",JLabel.RIGHT);
        ifsc.setFont(StaticMember.labelFont);
        JLabel account=new JLabel(" ACCOUNT NO : ",JLabel.RIGHT);
        account.setFont(StaticMember.labelFont);
        JLabel addar=new JLabel(" AADHAR NO : ",JLabel.RIGHT);
        addar.setFont(StaticMember.labelFont);
        JLabel bankname=new JLabel(" BANK NAME : ",JLabel.RIGHT);
        bankname.setFont(StaticMember.labelFont);
        JLabel idlabel=new JLabel("EMPLOYEE Name : ",JLabel.RIGHT);
        idlabel.setFont(StaticMember.labelFont1);
        
        uempl_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,"EMPLOYEE NAME");
        utadress=StaticMember.MyInputBox("",100,StaticMember.STRING_TYPE,"EMPLOYEE ADDRESS");
        utmob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE,"EMPLOYEE MOB NO.");
        utemail=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,"EMPLOYEE EMAIL ID.");
        uadhar=StaticMember.MyInputBox("",12,StaticMember.INTEGER_TYPE,"EMPLOYEE AADHAR NO.");
        emp_ac=StaticMember.MyInputBox("",20,StaticMember.INTEGER_TYPE,"ACCOUNT IFSC CODE");
        emp_bank_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,"EMPLOYEE BANK NAME");
        ArrayList<String> bank_items=new ArrayList<String>();
        StaticMember.setToBankName(emp_bank_name, bank_items);
        employee_id=StaticMember.MyInputBox("",15,StaticMember.STRING_TYPE,"ENTER EMPLOYEE NAME");
        StaticMember.setToEmployee(employee_id,empl_id,empl_items);
        
        uclose=new JButton("CLOSE");
        uclose.setFont(StaticMember.labelFont1);
        usave=new JButton("UPDATE");
        usave.setFont(StaticMember.labelFont1);
        ureset=new JButton("RESET");
        ureset.setFont(StaticMember.labelFont1);
        
        JPanel main=new JPanel(new BorderLayout());
        JPanel mainpanel=new JPanel(new GridLayout(2,1,2,2));
        JPanel selectpanel=new JPanel(new GridLayout(1,2,2,2));
        JPanel detailpanel=new JPanel(new GridLayout(4,2,3,3));
        JPanel infopanel=new JPanel(new GridLayout(4,2,3,3));
        JPanel buttonpanel=new JPanel(new GridLayout(1,3,10,50));
        selectpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Select"));
        detailpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Personal Detail"));
        infopanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Ligle Detail"));
        selectpanel.add(idlabel);selectpanel.add(employee_id);
        detailpanel.add(uname);detailpanel.add(uempl_name);
        detailpanel.add(uadd);detailpanel.add(utadress);
        detailpanel.add(umob);detailpanel.add(utmob);
        detailpanel.add(uemail);detailpanel.add(utemail);
        infopanel.add(addar);infopanel.add(uadhar);
        infopanel.add(account);infopanel.add(emp_ac);
        infopanel.add(ifsc);infopanel.add(emp_ifsc);
        infopanel.add(bankname);infopanel.add(emp_bank_name);
        mainpanel.add(detailpanel);mainpanel.add(infopanel);
        selectpanel.setPreferredSize(new Dimension(700,55));
        main.add(selectpanel,BorderLayout.NORTH);main.add(mainpanel,BorderLayout.CENTER);
        buttonpanel.add(new JLabel("   "));buttonpanel.add(new JLabel("   "));
        buttonpanel.add(usave);buttonpanel.add(uclose);buttonpanel.add(ureset);
        this.add(buttonpanel,BorderLayout.SOUTH);this.add(new JLabel("   "),BorderLayout.WEST);
        this.add(main,BorderLayout.CENTER); this.add(new JLabel("   "),BorderLayout.EAST);
        
        employee_id.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    try
                    {
                        ResultSet er=StaticMember.con.createStatement().executeQuery("select * from employee where employee_id like'"+empl_id.get(empl_items.indexOf(employee_id.getText()))+"'");
                        er.next();
                        uempl_name.setText(er.getString("employee_name"));
                        utadress.setText(er.getString("emp_address"));
                        utmob.setText(er.getString("emp_mob"));
                        utemail.setText(er.getString("emp_email"));
                        emp_ac.setText(er.getString("employee_ac"));
                        uadhar.setText(er.getString("adhar"));
                        emp_ifsc.setText(er.getString("employee_ifsc"));
                        emp_bank_name.setText(er.getString("employee_bank_name"));
                        e_id=Integer.parseInt(empl_id.get(empl_items.indexOf(employee_id.getText())));
                    }catch(SQLException ex){}
                    uempl_name.requestFocusInWindow();
                }
            }});
        uclose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_NEW_PRODUCT=false;
                UpdateEmployee.this.dispose();
           }});
        uclose.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.ADD_NEW_PRODUCT=false;
                    UpdateEmployee.this.dispose();
            }}});
        usave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                codeToUpdate();
           }});
        usave.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    codeToUpdate();
            }});
        ureset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
           }});
        ureset.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    reset();
            }});
        //empl_name,tadress,tmob,temail,tpin_code,adhar
        uempl_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                  e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    utadress.requestFocusInWindow();
            }});
        utadress.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                  e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    utmob.requestFocusInWindow();
            }});
        utmob.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    utemail.requestFocusInWindow();
            }
            public void keyPressed(KeyEvent e) {
            }
            public void keyReleased(KeyEvent e) {
            }});
        utemail.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    uadhar.requestFocusInWindow();
            }});
        
        uadhar.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    emp_ac.requestFocusInWindow();
            }});
        emp_ac.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    emp_ifsc.requestFocusInWindow();
            }});
        emp_ifsc.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                  e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    emp_bank_name.requestFocusInWindow();
            }});
        emp_bank_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                  e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    usave.requestFocusInWindow();
            }});
        
        
    }
    
    public void codeToUpdate()
    {
        
        //validate data
     
        if(uempl_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Employee Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            uempl_name.setText("");
            uempl_name.requestFocusInWindow();
           return;
        }
        
        if(utadress.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Employee Address Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            utadress.setText("");
            utadress.requestFocusInWindow();
           return;
        }
        if(utmob.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Mobile No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            utmob.setText("");
            utmob.requestFocusInWindow();
           return;
        }
        
        
        //code to save
        try
        {
              // compName,compAdd,compMob,compDl,compEmail,compLocation;
            PreparedStatement stmt=StaticMember.con.prepareStatement("update employee set employee_name=?,emp_address=?,emp_email=?,emp_mob=?,employee_ac=?,employee_ifsc=?,employee_bank_name=?,adhar=? where employee_id=?");
            
            stmt.setString(1, uempl_name.getText());
            stmt.setString(2, utadress.getText());
            stmt.setString(3, utemail.getText());
            stmt.setString(4, utmob.getText());
            stmt.setString(5, emp_ac.getText());
            stmt.setString(6, emp_ifsc.getText());
            stmt.setString(7, emp_bank_name.getText());
            stmt.setString(8,uadhar.getText());
            stmt.setInt(9, e_id);
            stmt.executeUpdate();
            StaticMember.setToEmployee(employee_id, empl_id, empl_items);
            JOptionPane.showMessageDialog(this, "Record Updated!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            reset();
          
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    private void reset()
    {
        employee_id.setText("");
        uempl_name.setText("");
        utadress.setText("");
        utmob.setText("");
        utemail.setText("");
        emp_ac.setText("");
        uadhar.setText("");
        emp_ifsc.setText("");
        emp_bank_name.setText("");
        employee_id.requestFocusInWindow();
    }
    
    
}
