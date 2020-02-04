/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Formatter;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import static komalhealthcare.Billing.cust_name;

/**
 *
 * @author Randhir
 */
public class BillWindow extends JDialog{
    private JComboBox billlist,clist;
    private JLabel bdate,dl_no,gst_in,custname,address,doctor_name,date,bilno,mno,samount,vamount,gtamount,damount;
    private JTable billtable;
    private JButton print,close;
    private DefaultTableModel tableModel;
    private String[] bstr,str,str_id,cust_id;
    private ResultSet crset=null;
    String bill_data;
    public BillWindow(String rbill)
    {
        super(MDIMainWindow.self,"VIEW BILL",true);
        StaticMember.setSize(this);
        StaticMember.setLocation(this);
        this.setDefaultCloseOperation(BillWindow.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent we)
        {
            
            BillWindow.this.dispose();

        }});
        
        /*this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                 
                StaticMember.VIEW_BILL=false;
              }});*/
        bill_data=rbill;
        JMenuBar bar=new JMenuBar();
        JMenu m1=new JMenu("H");
        JMenuItem mi=new JMenuItem("print",'p');
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_MASK));
        JMenuItem ci=new JMenuItem("Close",'c');
        ci.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_MASK));
        m1.add(mi);
        m1.add(ci);
        bar.add(m1);
        this.add(bar);
        mi.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.printSelBill(bill_data);
            }        });
        ci.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                BillWindow.this.dispose();
                MDIMainWindow.self.requestFocusInWindow();
            }        });
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        print=StaticMember.MyButton("PRINT","Click On Button To Print The Record");
        
        JLabel l1=new JLabel("BILL",JLabel.CENTER);
        l1.setFont(StaticMember.HEAD_W_FONT);
        l1.setBorder(createLineBorder(Color.magenta,3));
        l1.setForeground(StaticMember.HEAD_FG_COLOR1);
        l1.setBackground(StaticMember.HEAD_BG_COLOR1);
        l1.setOpaque(true);
        this.add(l1,BorderLayout.NORTH);
        
        custname=StaticMember.MyJLabel(""," CUSTOMER NAME ");
        date=StaticMember.MyJLabel(""," DATE ");
        address=StaticMember.MyJLabel(""," ADDRESS ");
        bilno=StaticMember.MyJLabel(""," BILL NO. ");
        dl_no=StaticMember.MyJLabel(""," DL NO. ");
        gst_in=StaticMember.MyJLabel(""," GSTIN NO. ");
        mno=StaticMember.MyJLabel(""," MOBILE NO ");
        
        JLabel sa=new JLabel("SUB AMOUNT :",JLabel.RIGHT);
        sa.setFont(StaticMember.labelFont1);
        samount=new JLabel("",JLabel.RIGHT);
        samount.setFont(StaticMember.textFont1);
        JLabel da=new JLabel("Disc% Amount :".toUpperCase(),JLabel.RIGHT);
        da.setFont(StaticMember.labelFont1);
        da.setForeground(StaticMember.flcolor);
        damount=new JLabel("",JLabel.RIGHT);
        damount.setFont(StaticMember.textFont1);
        JLabel gta=new JLabel("Grand Total Amount :".toUpperCase(),JLabel.RIGHT);
        gta.setFont(StaticMember.labelFont1);
        gtamount=new JLabel("",JLabel.RIGHT);
        gtamount.setFont(StaticMember.textFont1);
        
        
        
        String aname[]={"PRODUCTS","HSN","BATCH","MAKE","EXP","MRP \u20B9","RATE \u20B9","DISC%","NET AMT \u20B9","QTY"," FREE","AMOUNT \u20B9"};
        String data[][]=new String[0][];        
        billtable=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        tableModel.setDataVector(data,aname);
        billtable.setModel(tableModel);//set table model in table
        billtable.getTableHeader().setResizingAllowed(false);//stop resizing cell
        billtable.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        billtable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        billtable.getTableHeader().setFont(StaticMember.headfont);
        billtable.getTableHeader().setBackground(StaticMember.THBcolor);
        billtable.getTableHeader().setForeground(StaticMember.THFcolor);
        billtable.setFont(StaticMember.itemfont);
        billtable.setBackground(StaticMember.TIBcolor);
        billtable.setForeground(StaticMember.TIFcolor);
        billtable.setRowHeight(22);
        billtable.setShowHorizontalLines(false);
        billtable.setShowVerticalLines(false);
        billtable.setAlignmentX(JTable.RIGHT_ALIGNMENT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        billtable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        billtable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        billtable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        billtable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        billtable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        billtable.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        billtable.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        billtable.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        TableColumn column=null;
        column=billtable.getColumnModel().getColumn(0);column.setPreferredWidth(250);
        column=billtable.getColumnModel().getColumn(1);column.setPreferredWidth(40);
        column=billtable.getColumnModel().getColumn(2);column.setPreferredWidth(60);
        column=billtable.getColumnModel().getColumn(3);column.setPreferredWidth(60);
        column=billtable.getColumnModel().getColumn(4);column.setPreferredWidth(30);
        column=billtable.getColumnModel().getColumn(5);column.setPreferredWidth(40);
        column=billtable.getColumnModel().getColumn(6);column.setPreferredWidth(40);
        column=billtable.getColumnModel().getColumn(7);column.setPreferredWidth(40);
        column=billtable.getColumnModel().getColumn(8);column.setPreferredWidth(40);
        column=billtable.getColumnModel().getColumn(9);column.setPreferredWidth(20);
        column=billtable.getColumnModel().getColumn(10);column.setPreferredWidth(20);
        column=billtable.getColumnModel().getColumn(11);column.setPreferredWidth(40);
        
        JScrollPane sp=new JScrollPane(billtable);
        
        JLabel l2=new JLabel("PRODUCT DESCREPTION",JLabel.CENTER);
        l2.setFont(StaticMember.HEAD_FONT);
        l2.setForeground(Color.WHITE);
        l2.setOpaque(true);
        l2.setBackground(Color.BLUE);
        l2.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        l2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE,1, true),""));
        
        JPanel mainPanel=new JPanel(new BorderLayout());
        JPanel mainbilldetailPanel=new JPanel(new BorderLayout());
        JPanel mainbilldetailPanel1=new JPanel(new BorderLayout());
        JPanel bdetail1GridPanel=new JPanel(new GridLayout(2,1,5,5));
        JPanel mainbilldetailPanel2=new JPanel(new BorderLayout());
        JPanel bdetail1=new JPanel(new GridLayout(1,3,10,10));
        JPanel bdetail2=new JPanel(new GridLayout(1,4,10,10));
        JPanel billDatePanel=new JPanel(new GridLayout(1,2,10,10));
        JPanel amt_btn_panel=new JPanel(new BorderLayout());
        JPanel amt_panel=new JPanel(new GridLayout(3,3,5,5));
        JPanel btn_panel=new JPanel(new GridLayout(1,7,10,10));
        JPanel sa_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel da_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel gta_panel=new JPanel(new GridLayout(1,2,5,5));
        
        this.add(mainPanel,BorderLayout.CENTER);
        mainPanel.add(new JLabel("  "),BorderLayout.NORTH);mainPanel.add(new JLabel("  "),BorderLayout.EAST);
        mainPanel.add(new JLabel("  "),BorderLayout.WEST);mainPanel.add(new JLabel("  "),BorderLayout.SOUTH);
        mainPanel.add(mainbilldetailPanel,BorderLayout.CENTER);
        mainbilldetailPanel.add(mainbilldetailPanel1,BorderLayout.NORTH);
        mainbilldetailPanel.add(mainbilldetailPanel2,BorderLayout.CENTER);
        mainbilldetailPanel1.add(bdetail1GridPanel,BorderLayout.CENTER);
        bdetail1GridPanel.add(bdetail1);bdetail1GridPanel.add(bdetail2);
        bdetail1.add(billDatePanel);bdetail1.add(custname);bdetail1.add(address);
        billDatePanel.add(date);billDatePanel.add(bilno);
        bdetail2.add(mno);bdetail2.add(dl_no);bdetail2.add(gst_in);bdetail2.add(new JLabel(" "));
        mainbilldetailPanel2.add(sp,BorderLayout.CENTER);mainbilldetailPanel2.add(l2,BorderLayout.NORTH);
        mainbilldetailPanel2.add(amt_btn_panel,BorderLayout.SOUTH);
        amt_btn_panel.add(amt_panel,BorderLayout.CENTER);amt_btn_panel.add(btn_panel,BorderLayout.SOUTH);
        amt_panel.add(new JLabel(""));amt_panel.add(new JLabel(""));amt_panel.add(sa_panel);
        amt_panel.add(new JLabel(""));amt_panel.add(new JLabel(""));amt_panel.add(da_panel);
        amt_panel.add(new JLabel(""));amt_panel.add(new JLabel(""));amt_panel.add(gta_panel);
        sa_panel.add(sa);sa_panel.add(samount);
        da_panel.add(da);da_panel.add(damount);
        gta_panel.add(gta);gta_panel.add(gtamount);
        btn_panel.add(new JLabel(""));btn_panel.add(new JLabel(""));btn_panel.add(new JLabel(""));
        btn_panel.add(new JLabel(""));btn_panel.add(new JLabel(""));btn_panel.add(print);
        btn_panel.add(close);
        
        viewBillOnPage();
        
        print.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.printSelBill(bill_data);
            }        });
        print.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printSelBill(bill_data);
            }});
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.BILL_WINDOW=false;
                BillWindow.this.dispose();
                
               
            }        });
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.BILL_WINDOW=false;
                   BillWindow.this.dispose();
                  
                }
            }});
        
        
    }
    
    
    private void viewBillOnPage()
    {
        try
        {//bdate,custname,address,dlno,date,bilno,mno
        ResultSet b=StaticMember.con.createStatement().executeQuery("select * from retail_bill where retail_bill_no like'"+bill_data+"'");
        b.next();
        ResultSet c=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+b.getString("customer_id")+"'");
        c.next();
        date.setText(b.getString("retail_bill_date"));
        custname.setText(c.getString("customer_name").toUpperCase());
        address.setText(c.getString("customer_address").toUpperCase());
        dl_no.setText(c.getString("customer_dl").toUpperCase());
        bilno.setText("RGST00"+bill_data);
        mno.setText(c.getString("customer_mob"));
        gst_in.setText(c.getString("customer_gst"));
        
        ResultSet s=StaticMember.con.createStatement().executeQuery("select * from retail_bill_items where retail_bill_no like'"+bill_data+"'");
        
        int tablerow=0;
        float subamt=0,disamt=0,net_amt=0;
        while(s.next())
        {
            ResultSet p=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+s.getString("product_id")+"'");
            p.next();
            ResultSet m=StaticMember.con.createStatement().executeQuery("select * from manifacture where manifacture_id like'"+s.getString("manifacture_id")+"'");
            m.next();
            ResultSet avi=StaticMember.con.createStatement().executeQuery("select * from  availableitem where batch_no like'"+s.getString("batch_no")+"'");
            avi.next();
            ResultSet t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+p.getString("type_id")+"'");
            t.next();
            String product_name=p.getString("product_name")+" "+t.getString("type_name")+" "+avi.getString("packing");
            float prate=Float.parseFloat(s.getString("price"));
            float pqty=Float.parseFloat(s.getString("quintity"));
            float t_amount=prate*pqty;
            net_amt=prate*pqty;
            tableModel.addRow(new Object[]{" "+product_name+" "," "+p.getString("product_hsn_code")+"  "," "+s.getString("batch")+"  "," "+m.getString("manifacture_name")+"  "," "+avi.getString("expary_date")+"  ","  "+String.format("%.02f",Float.parseFloat(avi.getString("mrp")))+" "," "+String.format("%.02f",prate)+"  "," "+String.format("%.02f",Float.parseFloat(s.getString("disc")))+"  "," "+String.format("%.02f",net_amt)+" "," "+s.getString("quintity")+"  "," "+s.getString("free_qty")+"  "," "+String.format("%.02f",t_amount)+"  "});
            float amt=t_amount;
            disamt+=amt*Float.parseFloat(s.getString("disc"))/100;
            subamt+=amt;
            tablerow++;
        }
          //samount,vamount,gtamount,damount  
            Formatter fsa=new Formatter(); 
            fsa.format("%.2f",subamt);
            Formatter fda=new Formatter(); 
            fda.format("%.2f",disamt);
            Formatter fgta=new Formatter(); 
            fgta.format("%.2f",(subamt-disamt));
            samount.setText(fsa+"");
            damount.setText(fda+"");
            gtamount.setText(fgta+"");
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
    }
    
    public void empatyTable()
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
    }
}
