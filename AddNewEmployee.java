/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

/**
 *
 * @author Randhir
 */
public class AddNewEmployee extends JInternalFrame{
    private JTextField empl_name,tadress,tmob,temail,tpin_code,adhar,emp_ac,emp_ifsc,emp_bank_name;
    private JButton close,reset,save;
    private ResultSet rset=null;
    ArrayList<String> empl_id=new ArrayList<>();
    ArrayList<String> empl_items=new ArrayList<>();
    ArrayList<String> bank_items=new ArrayList<String>();
    public AddNewEmployee()
    {
        super("",true,true);
        this.setSize(700,410);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(AddNewEmployee.DISPOSE_ON_CLOSE);
        this.setLocation(300,90);
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.ADD_NEW_EMPLOYEE=false;
              }});
        
        JLabel title=new JLabel("CREATE NEW EMPLOYEE",JLabel.CENTER);
        title.setFont(StaticMember.HEAD_W_FONT);
        title.setBackground(StaticMember.HEAD_BG_COLOR1);
        title.setOpaque(true);
        title.setForeground(StaticMember.HEAD_FG_COLOR1);
        this.add(title,BorderLayout.NORTH);
        
        //Create Label and TextField
        JLabel name=new JLabel(" EMPLOYEE NAME : ",JLabel.RIGHT);
        name.setFont(StaticMember.labelFont);
        JLabel add=new JLabel(" ADDRESS  : ",JLabel.RIGHT);
        add.setFont(StaticMember.labelFont);
        JLabel mob=new JLabel(" MOBILE NO : ",JLabel.RIGHT);
        mob.setFont(StaticMember.labelFont);
        JLabel email=new JLabel(" EMAIL ID : ",JLabel.RIGHT);
        email.setFont(StaticMember.labelFont1);
        JLabel ifsc=new JLabel(" IFSC CODE : ",JLabel.RIGHT);
        ifsc.setFont(StaticMember.labelFont);
        JLabel account=new JLabel(" ACCOUNT NO : ",JLabel.RIGHT);
        account.setFont(StaticMember.labelFont);
        JLabel addar=new JLabel(" AADHAR NO : ",JLabel.RIGHT);
        addar.setFont(StaticMember.labelFont);
        JLabel bankname=new JLabel(" BANK NAME : ",JLabel.RIGHT);
        bankname.setFont(StaticMember.labelFont);
        
        empl_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,"ENTER EMPLOYEE NAME");
        StaticMember.setToEmployee(empl_name,empl_id,empl_items);
        tadress=StaticMember.MyInputBox("",100,StaticMember.STRING_TYPE,"ENTER EMPLOYEE ADDRESS");
        tmob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE,"ENTER EMPLOYEE MOB NO.");
        temail=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,"ENTER EMPLOYEE EMAIL ID.");
        adhar=StaticMember.MyInputBox("",12,StaticMember.INTEGER_TYPE,"ENTER EMPLOYEE AADHAR NO.");
        emp_ac=StaticMember.MyInputBox("",20,StaticMember.INTEGER_TYPE,"ENTER EMPLOYEE ACCOUNT NO.");
        emp_ifsc=StaticMember.MyInputBox("",15,StaticMember.STRING_TYPE,"ENTER ACCOUNT IFSC CODE");
        emp_bank_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,"ENTER EMPLOYEE BANK NAME");
        StaticMember.setToBankName(emp_bank_name, bank_items);
        
        close=new JButton("CLOSE");
        close.setFont(StaticMember.buttonFont);
        save=new JButton("SAVE");
        save.setFont(StaticMember.buttonFont);
        reset=new JButton("RESET");
        reset.setFont(StaticMember.buttonFont);
        
        JPanel main=new JPanel(new BorderLayout());
        JPanel mainpanel=new JPanel(new GridLayout(2,1,2,2));
        JPanel detailpanel=new JPanel(new GridLayout(4,2,3,3));
        JPanel infopanel=new JPanel(new GridLayout(4,2,3,3));
        JPanel buttonpanel=new JPanel(new GridLayout(1,3,10,50));
        
        detailpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Personal Detail"));
        infopanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Ligle Detail"));
        
        detailpanel.add(name);detailpanel.add(empl_name);
        detailpanel.add(add);detailpanel.add(tadress);
        detailpanel.add(mob);detailpanel.add(tmob);
        detailpanel.add(email);detailpanel.add(temail);
        infopanel.add(addar);infopanel.add(adhar);
        infopanel.add(account);infopanel.add(emp_ac);
        infopanel.add(ifsc);infopanel.add(emp_ifsc);
        infopanel.add(bankname);infopanel.add(emp_bank_name);
        mainpanel.add(detailpanel);mainpanel.add(infopanel);
        buttonpanel.add(new JLabel("   "));buttonpanel.add(new JLabel("   "));
        buttonpanel.add(save);buttonpanel.add(close);buttonpanel.add(reset);
        this.add(buttonpanel,BorderLayout.SOUTH);this.add(new JLabel("   "),BorderLayout.WEST);
        this.add(mainpanel,BorderLayout.CENTER); this.add(new JLabel("   "),BorderLayout.EAST);
        
        //Handle the Event on button and textfield
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_NEW_EMPLOYEE=false;
                AddNewEmployee.this.dispose();
           }});
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.ADD_NEW_EMPLOYEE=false;
                    AddNewEmployee.this.dispose();
            }}});
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                codeToSave();
           }});
        save.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    codeToSave();
            }});
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
           }});
        reset.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    reset();
            }});
        //empl_name,tadress,tmob,temail,tpin_code,adhar
        empl_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                  e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    tadress.requestFocusInWindow();
            }});
        tadress.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isLowerCase(keyChar)) {
                  e.setKeyChar(Character.toUpperCase(keyChar));
                }
                if(e.getKeyChar()=='\n')
                    tmob.requestFocusInWindow();
            }});
        tmob.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    temail.requestFocusInWindow();
            }});
        temail.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    adhar.requestFocusInWindow();
            }});
        
        adhar.addKeyListener(new KeyAdapter() {
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
                    save.requestFocusInWindow();
            }});
    }
    
    private void reset()
    {
        empl_name.setText("");
        tadress.setText("");
        tmob.setText("");
        temail.setText("");
        emp_ac.setText("");
        adhar.setText("");
        emp_ifsc.setText("");
        emp_bank_name.setText("");
        empl_name.requestFocusInWindow();
    }
        
    public void codeToSave()
    {
        
        //validate data
     
        if(empl_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Employee Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            empl_name.setText("");
            empl_name.requestFocusInWindow();
           return;
        }
        
        if(tadress.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Employee Address Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tadress.setText("");
            tadress.requestFocusInWindow();
           return;
        }
        
        if(tmob.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Mobile No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tmob.setText("");
            tmob.requestFocusInWindow();
           return;
        }
        
        //code to save
        try
        {
              // compName,compAdd,compMob,compDl,compEmail,compLocation;
            PreparedStatement stmt=StaticMember.con.prepareStatement("insert into employee(employee_id,employee_name,emp_address,emp_email,emp_mob,employee_ac,employee_ifsc,employee_bank_name,adhar) values(?,?,?,?,?,?,?,?,?)");
            ResultSet emp_rset=StaticMember.con.createStatement().executeQuery("select max(employee_id) as employee_id  from employee");
            emp_rset.next();
            int emp_id=emp_rset.getInt("employee_id")+1;
            stmt.setInt(1, emp_id);
            stmt.setString(2, empl_name.getText());
            stmt.setString(3, tadress.getText());
            stmt.setString(4, temail.getText());
            stmt.setString(5, tmob.getText());
            stmt.setString(6, emp_ac.getText());
            stmt.setString(7, emp_ifsc.getText());
            stmt.setString(8, emp_bank_name.getText());
            stmt.setString(9,adhar.getText());
            stmt.execute();
            JOptionPane.showMessageDialog(this, "Record saved!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            reset();
          
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!",JOptionPane.ERROR_MESSAGE );
        }
    }
    
    
}
