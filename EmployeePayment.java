/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Randhir
 */
public class EmployeePayment extends JFrame{
    private JTextField voucher_no,tamt;
    private JComboBox payment_type,employee_id,payer_name,pay_mode;
    private String[] ptstr,empstr,paystr,paymstr;
    private JButton aprove,print,new_reset;
    //private UtilDateModel stdob;
    //private JDatePickerImpl stdob2;
    public EmployeePayment()
    {
        super("EMPLOYEE RELATED");
        this.setSize(800,250);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EmployeePayment.EXIT_ON_CLOSE);
        this.setBackground(StaticMember.bcolor);
        
        JPanel mainpanel=new JPanel(new BorderLayout());
        JLabel l1=new JLabel("Employee Related Payment",JLabel.CENTER);
        l1.setFont(new Font("Perpetua Titling MT",Font.BOLD,25));
        l1.setOpaque(true);
        l1.setBackground(StaticMember.bcolor);
        
        JLabel vn=new JLabel(" Voucher No. : ");
        vn.setFont(StaticMember.labelFont1);
        JLabel ptype=new JLabel(" Payment Type : ");
        ptype.setFont(StaticMember.labelFont1);
        JLabel d1=new JLabel(" Date            : ");
        d1.setFont(StaticMember.labelFont1);
        JLabel my=new JLabel(" Month/Year : ");
        my.setFont(StaticMember.labelFont1);
        JLabel eid=new JLabel(" Employe Id     : ");
        eid.setFont(StaticMember.labelFont1);
        JLabel prn=new JLabel(" Payee Name     : ");
        prn.setFont(StaticMember.labelFont1);
        JLabel paym=new JLabel(" Pay Mode     : ");
        paym.setFont(StaticMember.labelFont1);
        JLabel amt=new JLabel(" Amount        : ");
        amt.setFont(StaticMember.labelFont1);
        voucher_no=new JTextField("");
        voucher_no.setFont(StaticMember.labelFont1);
        tamt=new JTextField("");
        tamt.setFont(StaticMember.labelFont1);
        
        String ptstr[]={"Advance","Bonus","Sailry","Commision"};
        payment_type=new JComboBox(ptstr);
        payment_type.setFont(StaticMember.labelFont1);
        String empstr[]={"1","2","3","4","5","6","7","8"};
        employee_id=new JComboBox(empstr);
        employee_id.setFont(StaticMember.labelFont1);
        String paystr[]={"Randhir","Ajay","Gaurav"};
        payer_name=new JComboBox(paystr);
        payer_name.setFont(StaticMember.labelFont1);
        String paymstr[]={"Cash","Cheque","Deposit"};
        pay_mode=new JComboBox(paymstr);
        pay_mode.setFont(StaticMember.labelFont1);
        //aprove,print,new_reset;
        aprove=new JButton("Approve");
        aprove.setFont(StaticMember.labelFont1);
        print=new JButton("Print");
        print.setFont(StaticMember.labelFont1);
        new_reset=new JButton("New/Reset");
        new_reset.setFont(StaticMember.labelFont1);
        /*
        stdob=new UtilDateModel();
        JDatePanelImpl tdob1=new JDatePanelImpl(stdob);
        stdob2=new JDatePickerImpl(tdob1);
        stdob2.setFont(StaticMember.labelFont1);
        stdob2.setBackground(StaticMember.bcolor);
        JFormattedTextField t1=stdob2.getJFormattedTextField();
        stdob2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        //Create Panel to add components.
        
        JPanel mid=new JPanel(new GridLayout(3,2,10,10));
        mid.setBackground(StaticMember.bcolor);
        JPanel bpanel=new JPanel(new GridLayout(1,3,50,10));
        bpanel.setBackground(StaticMember.bcolor);
        JPanel vnpanel=new JPanel(new BorderLayout());
        vnpanel.setBackground(StaticMember.bcolor);
        JPanel ptpanel=new JPanel(new BorderLayout());
        ptpanel.setBackground(StaticMember.bcolor);
        JPanel datepanel=new JPanel(new BorderLayout());
        datepanel.setBackground(StaticMember.bcolor);
        JPanel mypanel=new JPanel(new BorderLayout());
        mypanel.setBackground(StaticMember.bcolor);
        JPanel pmpanel=new JPanel(new BorderLayout());
        pmpanel.setBackground(StaticMember.bcolor);
        JPanel pnpanel=new JPanel(new BorderLayout());
        pnpanel.setBackground(StaticMember.bcolor);
        JPanel eipanel=new JPanel(new BorderLayout());
        eipanel.setBackground(StaticMember.bcolor);
        JPanel amtpanel=new JPanel(new BorderLayout());
        amtpanel.setBackground(StaticMember.bcolor);
        //this.add(new JLabel("Employee Related Payment",JLabel.CENTER).setFont(StaticMember.digitfont3),BorderLayout.NORTH);
        this.add(l1,BorderLayout.NORTH);
        amtpanel.add(amt,BorderLayout.WEST);amtpanel.add(tamt,BorderLayout.CENTER);
        vnpanel.add(my,BorderLayout.WEST);vnpanel.add(voucher_no,BorderLayout.CENTER);
        ptpanel.add(ptype,BorderLayout.WEST);ptpanel.add(payment_type,BorderLayout.CENTER);
        datepanel.add(d1,BorderLayout.WEST);datepanel.add(stdob2,BorderLayout.CENTER);
        mypanel.add(my,BorderLayout.WEST);mypanel.add(new JLabel(" "),BorderLayout.CENTER);
        pmpanel.add(paym,BorderLayout.WEST);pmpanel.add(pay_mode,BorderLayout.CENTER);
        pnpanel.add(prn,BorderLayout.WEST);pnpanel.add(payer_name,BorderLayout.CENTER);
        eipanel.add(eid,BorderLayout.WEST);eipanel.add(employee_id,BorderLayout.CENTER);
        bpanel.add(aprove);bpanel.add(print);bpanel.add(new_reset);
        mainpanel.add(pnpanel,BorderLayout.NORTH);
        mid.add(datepanel);mid.add(ptpanel);mid.add(pmpanel);mid.add(eipanel);mid.add(mypanel);mid.add(amtpanel);
        mainpanel.add(mid,BorderLayout.CENTER);
        this.add(mainpanel,BorderLayout.CENTER);
        this.add(bpanel,BorderLayout.SOUTH);
    }*/
}}
