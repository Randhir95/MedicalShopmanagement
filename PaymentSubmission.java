/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;



/**
 *
 * @author RANDHIR KUMAR
 */
public class PaymentSubmission extends JInternalFrame{
    JTextField customer_name,mob_no,cust_add,cust_gstno,cust_dlno,payment,sn,customer_detail,bill_amt,dues_amt,bill_no,bill_date;
    JButton close,reset,submit;
    ArrayList<String> customer_id=new ArrayList<>();
    ArrayList<String> customer_items=new ArrayList<>();
    JTable table;
    DefaultTableModel tableModel;
    String listItems[][];
    ResultSet rset=null;
    private JDateChooser invoicedate;
    JPanel mainshouthGridPanel,mainFramePanel;
    String cust_name;
    public PaymentSubmission()
    {
        super("",true,true);
        StaticMember.setSize(this);
        this.setDefaultCloseOperation(PaymentSubmission.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.PAYMENT_SUBMISSION=false;
              }});
        StaticMember.labelHeding("PAYMENT SUBMISSION", this);
        
        customer_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,"ENTER CUSTOMER NAME");
        StaticMember.setCustomerdata(customer_name, customer_id, customer_items);
        
        mob_no=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE,false,"MOBILE NO");
        cust_add=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false,"ADDRESS");
        cust_gstno=StaticMember.MyInputBox("",10,StaticMember.STRING_TYPE,false,"GST NO");
        cust_dlno=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"DL NO.");
        payment=StaticMember.MyInputBox("",60,StaticMember.FLOAT_TYPE,"PAYMENT");
        payment.setHorizontalAlignment(SwingConstants.RIGHT);
        sn=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"SN");
        sn.setHorizontalAlignment(SwingConstants.CENTER);
        customer_detail=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"CUSTOMER DETAILS");
        bill_amt=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"BILL AMOUNT");
        bill_amt.setHorizontalAlignment(SwingConstants.RIGHT);
        dues_amt=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"DUES");
        dues_amt.setHorizontalAlignment(SwingConstants.RIGHT);
        bill_no=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"BILL NO");
        bill_no.setHorizontalAlignment(SwingConstants.CENTER);
        bill_date=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"DATE");
        bill_date.setHorizontalAlignment(SwingConstants.CENTER);
       
        invoicedate=new JDateChooser();
        invoicedate.setFont(StaticMember.labelFont1);
        invoicedate.setBackground(StaticMember.bcolor);
        invoicedate.setOpaque(true);
        
        close=StaticMember.button("CLOSE");
        reset=StaticMember.button("RESET");
        submit=StaticMember.button("SUBMIT");
        JLabel custname=StaticMember.MyLabel("  CUSTOMER NAME  :   ");
        JLabel custmob=StaticMember.MyLabel("            MOBILE NO.  :   ");
        JLabel custdlno=StaticMember.MyLabel("  DL NO.  :   ");
        JLabel custgstno=StaticMember.MyLabel("  GSTIN NO.  :    ");
        JLabel custadd=StaticMember.MyLabel("  ADDRESS  :   ");
        JLabel sr=StaticMember.MyLabel("SR NO.",JLabel.CENTER);
        JLabel billno=StaticMember.MyLabel("BILL NO.",JLabel.CENTER);
        JLabel billdate=StaticMember.MyLabel("BILL DATE",JLabel.CENTER);
        JLabel custdetail=StaticMember.MyLabel("CUSTOMER NAME (ADDRESS)",JLabel.CENTER);
        JLabel billamt=StaticMember.MyLabel("BILL AMT",JLabel.CENTER);
        JLabel duesamt=StaticMember.MyLabel("DUES",JLabel.CENTER);
        JLabel payamt=StaticMember.MyLabel("PAYMENT",JLabel.CENTER);
        JLabel paydate=StaticMember.MyLabel("PAYMENT DATE",JLabel.CENTER);
        
        table=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        String Heding[]={"SR","BILL NO.","BILL DATE","CLIENT NAME","ADDRESS","CLIENT MOB NO.","BILL AMOUNT","PAYMENT","DUE AMOUNT"};
        tableModel.setDataVector(listItems,Heding);
        table.setModel(tableModel);//set table model in table
        table.getTableHeader().setResizingAllowed(false);//stop resizing cell
        table.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        TableColumn column=null;
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(5);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(120);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(150);
        column=table.getColumnModel().getColumn(5);
        column.setPreferredWidth(70);
       
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.setFont(StaticMember.itemfont);
        table.getTableHeader().setFont(StaticMember.headfont);
        table.getTableHeader().setBackground(StaticMember.THBcolor);
        table.getTableHeader().setForeground(StaticMember.THFcolor);
        table.setBackground(StaticMember.TIBcolor);
        table.setForeground(StaticMember.TIFcolor);
        table.setRowHeight(22);
        
        JScrollPane scrl=new JScrollPane(table);
        
        mainFramePanel=new JPanel(new BorderLayout());
        JPanel headMainPanel=new JPanel(new GridLayout(2,1,5,5));
        JPanel rowPanel1=new JPanel(new GridLayout(1,2,10,10));
        JPanel rowPanel2=new JPanel(new GridLayout(1,3,10,10));
        JPanel custnamePanel=new JPanel(new BorderLayout());
        JPanel custaddPanel=new JPanel(new BorderLayout());
        JPanel custgstPanel=new JPanel(new BorderLayout());
        JPanel custdlPanel=new JPanel(new BorderLayout());
        JPanel custmobPanel=new JPanel(new BorderLayout());
        JPanel mainshouthPanel=new JPanel(new BorderLayout());
        mainshouthGridPanel=new JPanel(new GridLayout(2,1,2,2));
        JPanel mainpaymentPanel=new JPanel(new GridLayout(1,4,2,2));
        JPanel bill_datePanel=new JPanel(new GridLayout(1,2,2,2));
        JPanel amtPanel=new JPanel(new GridLayout(1,2,2,2));
        JPanel amtPanel1=new JPanel(new BorderLayout());
        JPanel amtPanel2=new JPanel(new GridLayout(1,2,2,2));
        JPanel srPanel=new JPanel(new BorderLayout());
        JPanel billPanel=new JPanel(new BorderLayout());
        JPanel datePanel=new JPanel(new BorderLayout());
        JPanel custPanel=new JPanel(new BorderLayout());
        JPanel billamtPanel=new JPanel(new BorderLayout());
        JPanel duesPanel=new JPanel(new BorderLayout());
        JPanel payPanel=new JPanel(new BorderLayout());
        JPanel paydatePanel=new JPanel(new BorderLayout());
        JPanel submitPanel=new JPanel(new BorderLayout());
        JPanel buttonGridPanel=new JPanel(new GridLayout(1,5,50,50));
        JPanel buttonPanel=new JPanel(new GridLayout(1,2,20,20));
        
        custnamePanel.add(custname,BorderLayout.WEST);custnamePanel.add(customer_name,BorderLayout.CENTER);
        custmobPanel.add(custmob,BorderLayout.WEST);custmobPanel.add(mob_no,BorderLayout.CENTER);
        custaddPanel.add(custadd,BorderLayout.WEST);custaddPanel.add(cust_add,BorderLayout.CENTER);
        custgstPanel.add(custgstno,BorderLayout.WEST);custgstPanel.add(cust_gstno,BorderLayout.CENTER);
        custdlPanel.add(custdlno,BorderLayout.WEST);custdlPanel.add(cust_dlno,BorderLayout.CENTER);
        rowPanel1.add(custnamePanel);rowPanel1.add(custaddPanel);
        rowPanel2.add(custmobPanel);rowPanel2.add(custgstPanel);rowPanel2.add(custdlPanel);
        headMainPanel.add(rowPanel1);headMainPanel.add(rowPanel2);
        mainFramePanel.add(headMainPanel,BorderLayout.NORTH);
        headMainPanel.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,90));
        headMainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Customer Detail"));
        mainFramePanel.add(scrl,BorderLayout.CENTER);
        srPanel.add(sr,BorderLayout.NORTH);srPanel.add(sn,BorderLayout.CENTER);
        billPanel.add(billno,BorderLayout.NORTH);billPanel.add(bill_no,BorderLayout.CENTER);
        datePanel.add(billdate,BorderLayout.NORTH);datePanel.add(bill_date,BorderLayout.CENTER);
        custPanel.add(custdetail,BorderLayout.NORTH);custPanel.add(customer_detail,BorderLayout.CENTER);
        billamtPanel.add(billamt,BorderLayout.NORTH);billamtPanel.add(bill_amt,BorderLayout.CENTER);
        billamtPanel.setPreferredSize(new Dimension(50,100));
        duesPanel.add(duesamt,BorderLayout.NORTH);duesPanel.add(dues_amt,BorderLayout.CENTER);
        paydatePanel.add(paydate,BorderLayout.NORTH);paydatePanel.add(invoicedate,BorderLayout.CENTER);
        payPanel.add(payamt,BorderLayout.NORTH);payPanel.add(payment,BorderLayout.CENTER);
        submitPanel.add(new JLabel("        "),BorderLayout.NORTH);submitPanel.add(submit,BorderLayout.CENTER);
        bill_datePanel.add(billPanel);bill_datePanel.add(datePanel);
        amtPanel.add(billamtPanel);amtPanel.add(duesPanel);
        amtPanel2.add(paydatePanel);amtPanel2.add(payPanel);
        amtPanel1.add(srPanel,BorderLayout.WEST);amtPanel1.add(bill_datePanel,BorderLayout.CENTER);
        mainpaymentPanel.add(amtPanel1);mainpaymentPanel.add(custPanel);mainpaymentPanel.add(amtPanel);mainpaymentPanel.add(amtPanel2);
        mainshouthPanel.add(mainpaymentPanel,BorderLayout.CENTER);mainshouthPanel.add(submitPanel,BorderLayout.EAST);
        mainpaymentPanel.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,55));
        mainshouthGridPanel.add(mainshouthPanel);mainshouthGridPanel.add(new JLabel("        "));
        mainFramePanel.add(mainshouthGridPanel,BorderLayout.SOUTH);
        buttonPanel.add(reset);buttonPanel.add(close);
        buttonGridPanel.add(new JLabel("   "));buttonGridPanel.add(new JLabel("   "));buttonGridPanel.add(new JLabel("   "));
        buttonGridPanel.add(new JLabel("   "));buttonGridPanel.add(buttonPanel);
        this.add(buttonGridPanel,BorderLayout.SOUTH);
        this.add(mainFramePanel,BorderLayout.CENTER);
        
        customer_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    setToCustomerDetails(customer_id.get(customer_items.indexOf(customer_name.getText())).trim());
                    setDataInTabel(customer_id.get(customer_items.indexOf(customer_name.getText())).trim());
                }
            }});
        
        payment.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                payment.setSelectionStart(0);
                payment.setSelectionEnd(payment.getText().length());
                payment.setSelectionColor(Color.BLUE);
                payment.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {
            }});
        
        table.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount()==2)
                    {
                        if(JOptionPane.showConfirmDialog(MDIMainWindow.self,"Are You Sure To Make Payment ?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                        {   
                            int sel_row=table.getSelectedRow();
                            if(sel_row!=-1)
                            {
                                try
                                {
                                    String bno=(table.getValueAt(sel_row, 1).toString().trim()).substring(6);
                                    
                                    showDataToEdit(customer_id.get(customer_items.indexOf(table.getValueAt(sel_row, 3).toString().trim())),bno);
                                }catch(NumberFormatException ex){}
                            }
                        }
                    }
            }});
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cu_id="";
                /*if(payment.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self, "Payment Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
                    payment.setText("");
                    payment.requestFocusInWindow();
                    return;
                }
                String date1  = ((JTextField)invoicedate.getDateEditor().getUiComponent()).getText();
                if(date1.equals(null))
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self, "Payment Date Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
                    invoicedate.setDate(null);
                    invoicedate.requestFocusInWindow();
                    return;
                }*/
                check();
                cu_id=submitPayment(Float.parseFloat(payment.getText().toString()),(bill_no.getText().trim()).substring(6),Float.parseFloat(bill_amt.getText().trim()),Float.parseFloat(dues_amt.getText().trim()));
                setDataInTabel(cu_id);
                
            } });
        submit.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    check();
                    String cu_id="";
                    cu_id=submitPayment(Float.parseFloat(payment.getText().toString()),(bill_no.getText().trim()).substring(6),Float.parseFloat(bill_amt.getText().trim()),Float.parseFloat(dues_amt.getText().trim()));
                    setDataInTabel(cu_id);
                    
                }
            }});
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PaymentSubmission.this.dispose();
                StaticMember.PAYMENT_SUBMISSION=false;
            } });
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    PaymentSubmission.this.dispose();
                    StaticMember.PAYMENT_SUBMISSION=false;
                }
            }});
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                custDetailReset();
                resetTable();
                amtReset();
                customer_name.requestFocusInWindow();
            } });
        reset.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    custDetailReset();
                    resetTable();
                    amtReset();
                    customer_name.requestFocusInWindow();
                }
            }});
    }
    
    public void setDataInTabel(String cust_id)
    {
        resetTable();
        try
        {
            //JOptionPane.showMessageDialog(null, "hello");
            rset=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill where customer_id like'"+cust_id+"'");
            rset.last();
            rset.beforeFirst();
            while(rset.next())
            {
                ResultSet cust_rset=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+cust_id+"'");
                cust_rset.next();
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+"RGST00"+rset.getString("k_retail_bill_no")+" "," "+rset.getString("k_retail_bill_date")+" "," "+cust_rset.getString("customer_name")+" "," "+cust_rset.getString("customer_address")+" "," "+cust_rset.getString("customer_mob")+"  ","  "+String.format("%.02f",Float.parseFloat(rset.getString("kpay_amount")))+" "," "+String.format("%.02f",Float.parseFloat(rset.getString("payment")))+"  ","  "+String.format("%.02f",Float.parseFloat(rset.getString("dues")))+"  "});
            }
            
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(PaymentSubmission.this, ex.getMessage(),"Opps",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setToCustomerDetails(String cust_id)
    {
        try
        {
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT  * FROM customer where customer_id like'"+cust_id+"'");
            rset.next();
            mob_no.setText(rset.getString("customer_mob").toString());
            cust_add.setText(rset.getString("customer_address").toString());
            cust_dlno.setText(rset.getString("customer_dl").toString());
            cust_gstno.setText(rset.getString("customer_gst").toString());
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(customer_name, customer_items,false);
    }
    
    private void showDataToEdit(String cust_id,String billno)
    {
        try
        {
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM k_retail_bill where k_retail_bill_no like'"+billno+"'");
            rset.next();
            ResultSet cusrset=StaticMember.con.createStatement().executeQuery("SELECT * FROM customer where customer_id like'"+cust_id+"'");
            cusrset.next();
            //payment,sn,customer_detail,bill_amt,dues_amt,bill_no,bill_date;
            payment.setText(String.format("%.02f",Float.parseFloat(rset.getString("payment").trim())));
            bill_amt.setText(String.format("%.02f",Float.parseFloat(rset.getString("kpay_amount").trim())));
            dues_amt.setText(String.format("%.02f",Float.parseFloat(rset.getString("dues").trim())));
            sn.setText(" 1. ");
            cust_name=(cusrset.getString("customer_name").toString().trim())+" ( "+(cusrset.getString("customer_address").toString().trim())+" )";
            customer_detail.setText(cust_name);
            bill_no.setText("RGST00"+rset.getString("k_retail_bill_no"));
            bill_date.setText(rset.getString("k_retail_bill_date"));
            
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,e.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String submitPayment(float amt,String bill,float bamt,float damt)
    {
        String c_id="";
        try
        {
           float dues=0,privous_pamt=0,pamt=0;
           if(amt<=damt)
           {
               Formatter fdues=new Formatter().format("%.02f", damt-amt);
               dues=Float.parseFloat(fdues+"");
               pamt=amt;
           }
           else
           {
                Formatter famount=new Formatter().format("%.02f", amt-damt);
                pamt=bamt;
                dues=damt-damt;
                JOptionPane.showMessageDialog(MDIMainWindow.self, "Plz Collect Refund Amount Rs. "+famount+" ");
               
           }
           Calendar cl=Calendar.getInstance();
           Formatter fmt=new Formatter();
           fmt.format("%tH:%tM:%tS",cl,cl,cl);
           Calendar e;
           e=invoicedate.getCalendar();        
           Formatter fm1=new Formatter();
           fm1.format("%tY-%tm-%td",e,e,e);
           ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM k_retail_bill where k_retail_bill_no like'"+bill+"'");
           rset.next();
           privous_pamt=rset.getFloat("payment");
           PreparedStatement bsmt=StaticMember.con.prepareStatement("update k_retail_bill set payment=?,dues=?,payment_date=?,payment_time=? where k_retail_bill_no=?");
           bsmt.setFloat(1, pamt+privous_pamt);
           bsmt.setFloat(2, dues);
           bsmt.setString(3, fm1+"");
           bsmt.setString(4, fmt+"");
           bsmt.setString(5, bill);
           bsmt.executeUpdate();
           JOptionPane.showMessageDialog(MDIMainWindow.self,"Payment Update");
           c_id=rset.getString("customer_id");
           amtReset();
       }
       catch(SQLException ex)
       {
           JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage());
       }
        return c_id;
    }
    
    private void check()
    {
        String date1  = ((JTextField)invoicedate.getDateEditor().getUiComponent()).getText();
        if(date1.equals(null))
        {
            JOptionPane.showMessageDialog(this, "Payment Date Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            invoicedate.setDate(null);
            invoicedate.requestFocusInWindow();
            return;
        }
        if(payment.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Payment Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            payment.setText("");
            payment.requestFocusInWindow();
            return;
        }
        
    }
    
    private void amtReset()
    {
        customer_detail.setText("");
        payment.setText("");
        bill_amt.setText("");
        sn.setText("");
        dues_amt.setText("");
        bill_no.setText("");
        bill_date.setText("");
    }
    private void custDetailReset()
    {
        customer_name.setText("");
        mob_no.setText("");
        cust_add.setText("");
        cust_gstno.setText("");
        cust_dlno.setText(cust_name);
    }
    private void resetTable()
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
    }
}
