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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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

/**
 *
 * @author RANDHIR KUMAR
 */
public class CustomerLedger extends JInternalFrame{
    private JTextField customer_name,total_bill_amt,total_dues_amt,total_pay_amt;
    //private JLabel total_bill_amt,total_dues_amt,total_pay_amt;
    private JDateChooser inv_start_date;
    private JDateChooser inv_end_date;
    private JButton view_ledger,view_all_ledger,print,close;
    private JTable table;
    private DefaultTableModel tableModel;
    private String listItems[][];
    private ResultSet rset=null,arset=null;
    private ArrayList<String> customer_id=new ArrayList<>();
    private ArrayList<String> customer_items=new ArrayList<>();
    public CustomerLedger()
    {
        super("",true,true);
        StaticMember.setSize(this);
        this.setDefaultCloseOperation(CustomerLedger.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.CUSTOMER_LEDGER=false;
              }});
        
        StaticMember.labelHeding("CUSTOMER LEDGER",this);
        
        customer_name=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,"ENTER CUSTOMER NAME");
        StaticMember.setCustomerdata(customer_name, customer_id, customer_items);
        total_bill_amt=StaticMember.MyInputBox("00.00",60,StaticMember.STRING_TYPE,false,"");
        total_dues_amt=StaticMember.MyInputBox("00.00",60,StaticMember.STRING_TYPE,false,"");
        total_pay_amt=StaticMember.MyInputBox("00.00",60,StaticMember.STRING_TYPE,false,"");
        total_bill_amt.setHorizontalAlignment(SwingConstants.RIGHT);
        total_dues_amt.setHorizontalAlignment(SwingConstants.RIGHT);
        total_pay_amt.setHorizontalAlignment(SwingConstants.RIGHT);
        
        inv_start_date=StaticMember.dateChooser();
        inv_end_date=StaticMember.dateChooser();
        
        view_ledger=StaticMember.button("VIEW LEDGER");
        view_all_ledger=StaticMember.button("VIEW ALL LEDGER");
        print=StaticMember.button("PRINT");
        close=StaticMember.button("CLOSE");
        
        JLabel custname=StaticMember.MyLabel(" CUSTOMER NAME ", JLabel.CENTER);
        JLabel sdate=StaticMember.MyLabel(" DATE ", JLabel.CENTER);
        JLabel edate=StaticMember.MyLabel(" TO DATE ", JLabel.CENTER);
        JLabel totalbillamt=StaticMember.MyLabel(" TOTAL BILL AMOUNT  :  ", JLabel.RIGHT);
        JLabel totalduesamt=StaticMember.MyLabel(" TOTAL DUES AMOUNT  :  ", JLabel.RIGHT);
        JLabel totalpayamt=StaticMember.MyLabel(" TOTAL PAY AMOUNT  :  ", JLabel.RIGHT);
        
        table=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        String Heding[]={"SR","BILL NO.","BILL DATE","CUSTOMER DETAILS","PAY TYPE","BILL AMOUNT","PAYMENT","PAY DATE","DUE AMOUNT"};
        tableModel.setDataVector(listItems,Heding);
        table.setModel(tableModel);//set table model in table
        table.getTableHeader().setResizingAllowed(false);//stop resizing cell
        table.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        TableColumn column=null;
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(5);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(300);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(5);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(6);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(7);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(8);
        column.setPreferredWidth(60);
       
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        
        table.setFont(StaticMember.itemfont);
        table.getTableHeader().setFont(StaticMember.headfont);
        table.getTableHeader().setBackground(StaticMember.THBcolor);
        table.getTableHeader().setForeground(StaticMember.THFcolor);
        table.setBackground(StaticMember.TIBcolor);
        table.setForeground(StaticMember.TIFcolor);
        table.setRowHeight(22);
        JScrollPane scrl=new JScrollPane(table);
        
        JPanel mainFramePanel=new JPanel(new BorderLayout());
        JPanel headMain=new JPanel(new BorderLayout());
        JPanel mainHeadPanel=new JPanel(new GridLayout(1,3,30,30));
        JPanel customerPanel=new JPanel(new BorderLayout());
        JPanel datePanel=new JPanel(new GridLayout(1,2,10,10));
        JPanel sdatePanel=new JPanel(new BorderLayout());
        JPanel todatePanel=new JPanel(new BorderLayout());
        JPanel hedButtonPanel=new JPanel(new GridLayout(1,2,20,20));
        JPanel viewAllButtonPanel=new JPanel(new BorderLayout());
        JPanel viewButtonPanel=new JPanel(new BorderLayout());
        JPanel duesPanel=new JPanel(new BorderLayout());
        JPanel billamtPanel=new JPanel(new BorderLayout());
        JPanel payPanel=new JPanel(new BorderLayout());
        JPanel amountPanel=new JPanel(new BorderLayout());
        JPanel amtGridPanel=new JPanel(new GridLayout(1,4,25,25));
        JPanel shouthGridButtonPanel=new JPanel(new GridLayout(1,2,10,10));
        JPanel buttonPanel=new JPanel(new BorderLayout());
        
        customerPanel.add(custname,BorderLayout.NORTH);customerPanel.add(customer_name,BorderLayout.CENTER);
        sdatePanel.add(sdate,BorderLayout.NORTH);sdatePanel.add(inv_start_date,BorderLayout.CENTER);
        todatePanel.add(edate,BorderLayout.NORTH);todatePanel.add(inv_end_date,BorderLayout.CENTER);
        viewAllButtonPanel.add(view_all_ledger,BorderLayout.CENTER);viewAllButtonPanel.add(new JLabel(" "),BorderLayout.NORTH);
        viewButtonPanel.add(view_ledger,BorderLayout.CENTER);viewButtonPanel.add(new JLabel(" "),BorderLayout.NORTH);
        datePanel.add(sdatePanel);datePanel.add(todatePanel);
        hedButtonPanel.add(viewButtonPanel);hedButtonPanel.add(viewAllButtonPanel);
        mainHeadPanel.add(customerPanel);mainHeadPanel.add(datePanel);mainHeadPanel.add(hedButtonPanel);
        headMain.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,80));
        //headMain.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),""));
        headMain.add(mainHeadPanel,BorderLayout.CENTER);headMain.add(new JLabel("  "),BorderLayout.SOUTH);headMain.add(new JLabel("  "),BorderLayout.NORTH);
        headMain.add(new JLabel("        "),BorderLayout.WEST);headMain.add(new JLabel("       "),BorderLayout.EAST);
        mainFramePanel.add(headMain,BorderLayout.NORTH);
        mainFramePanel.add(scrl,BorderLayout.CENTER);
        duesPanel.add(totalduesamt,BorderLayout.WEST);duesPanel.add(total_dues_amt,BorderLayout.CENTER);
        billamtPanel.add(totalbillamt,BorderLayout.WEST);billamtPanel.add(total_bill_amt,BorderLayout.CENTER);
        payPanel.add(totalpayamt,BorderLayout.WEST);payPanel.add(total_pay_amt,BorderLayout.CENTER);
        amtGridPanel.add(new JLabel("  "));amtGridPanel.add(billamtPanel);amtGridPanel.add(payPanel);amtGridPanel.add(duesPanel);
        amountPanel.add(amtGridPanel,BorderLayout.CENTER);amountPanel.add(new JLabel(" "),BorderLayout.NORTH);amountPanel.add(new JLabel(" "),BorderLayout.SOUTH);
        amountPanel.add(new JLabel("          "),BorderLayout.WEST);amountPanel.add(new JLabel("        "),BorderLayout.EAST);
        amountPanel.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,60));
        mainFramePanel.add(amountPanel,BorderLayout.SOUTH);
        shouthGridButtonPanel.add(print);shouthGridButtonPanel.add(close);
        buttonPanel.add(new JLabel("  "),BorderLayout.CENTER);buttonPanel.add(shouthGridButtonPanel,BorderLayout.EAST);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.add(mainFramePanel,BorderLayout.CENTER);
        
        view_all_ledger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewAllLedger();
            } });
        view_all_ledger.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    viewAllLedger();
                }
            }});
        view_ledger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(customer_name.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self, "End Date Must Be Entred !", "Opps", JOptionPane.WARNING_MESSAGE);
                    customer_name.setText("");
                    customer_name.requestFocusInWindow();
                    return;
                }
                viewLedgerInTable(Integer.parseInt(customer_id.get(customer_items.indexOf(customer_name.getText().trim()))));
            } });
        view_ledger.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(customer_name.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(MDIMainWindow.self, "End Date Must Be Entred !", "Opps", JOptionPane.WARNING_MESSAGE);
                        customer_name.setText("");
                        customer_name.requestFocusInWindow();
                        return;
                    }
                    viewLedgerInTable(Integer.parseInt(customer_id.get(customer_items.indexOf(customer_name.getText().trim()))));
                }
            }});
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CustomerLedger.this.dispose();
                StaticMember.CUSTOMER_LEDGER=false;
            } });
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    CustomerLedger.this.dispose();
                    StaticMember.CUSTOMER_LEDGER=false;
                }
            }});
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                printLadger(StaticMember.getDate(inv_start_date),StaticMember.getDate(inv_end_date),Integer.parseInt(customer_id.get(customer_items.indexOf(customer_name.getText().trim()))));
            } });
        print.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    printLadger(StaticMember.getDate(inv_start_date),StaticMember.getDate(inv_end_date),Integer.parseInt(customer_id.get(customer_items.indexOf(customer_name.getText().trim()))));
                }
            }});
    }
    
    private void viewLedgerInTable(int cust_id)
    {
        String start_date,end_date;
        start_date=StaticMember.getDate(inv_start_date);
        end_date=StaticMember.getDate(inv_end_date);
        if(start_date.equals("null-null-null"))
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self, "Start Date Must Be Entred !", "Opps", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(end_date.equals("null-null-null"))
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self, "End Date Must Be Entred !", "Opps", JOptionPane.WARNING_MESSAGE);
            return;
        }
        empatyTable();
        float tdamt=0,tbamt=0,tpamt=0;
        try
        {
            rset=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill where customer_id="+cust_id+" and k_retail_bill_date>='" + start_date + "' and k_retail_bill_date<='" + end_date + "'");
            rset.last();
            rset.beforeFirst();
            while(rset.next())
            {
                ResultSet crset=StaticMember.con.createStatement().executeQuery("SELECT * FROM customer where customer_id like'"+cust_id+"'");
                crset.next();
                String customerDetail=crset.getString("customer_name")+" - "+crset.getString("customer_address")+" - "+crset.getString("customer_mob");
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+"RGST00"+rset.getString("k_retail_bill_no")+" "," "+rset.getString("k_retail_bill_date")+" "," "+customerDetail+" "," "+rset.getString("kpay_mode")+" "," "+StaticMember.myFormat(rset.getString("kpay_amount"))+"  ","  "+StaticMember.myFormat(rset.getString("payment"))+" "," "+rset.getString("payment_date")+"  ","  "+StaticMember.myFormat(rset.getString("dues"))+"  "});
                tdamt+=rset.getFloat("dues");
                tpamt+=rset.getFloat("payment");
                tbamt+=rset.getFloat("kpay_amount");
            }
            total_pay_amt.setText(StaticMember.myFormat(tpamt));
            total_dues_amt.setText(StaticMember.myFormat(tdamt));
            total_bill_amt.setText(StaticMember.myFormat(tbamt));
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), "Opps", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewAllLedger()
    {
        String start_date,end_date;
        start_date=StaticMember.getDate(inv_start_date);
        end_date=StaticMember.getDate(inv_end_date);
        if(start_date.equals("null-null-null"))
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self, "Start Date Must Be Entred !", "Opps", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(end_date.equals("null-null-null"))
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self, "End Date Must Be Entred !", "Opps", JOptionPane.WARNING_MESSAGE);
            return;
        }
        empatyTable();
        float tdamt=0,tbamt=0,tpamt=0;
        try
        {
            arset=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill where k_retail_bill_date>='" + start_date + "' and k_retail_bill_date<='" + end_date + "'");
            arset.last();
            arset.beforeFirst();
            while(arset.next())
            {
                ResultSet crset=StaticMember.con.createStatement().executeQuery("SELECT * FROM customer where customer_id like'"+arset.getString("customer_id")+"'");
                crset.next();
                String customerDetail=crset.getString("customer_name")+" - "+crset.getString("customer_address")+" - "+crset.getString("customer_mob");
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+"RGST00"+arset.getString("k_retail_bill_no")+" "," "+arset.getString("k_retail_bill_date")+" "," "+customerDetail+" "," "+arset.getString("kpay_mode")+" "," "+StaticMember.myFormat(arset.getString("kpay_amount"))+"  ","  "+StaticMember.myFormat(arset.getString("payment"))+" "," "+arset.getString("payment_date")+"  ","  "+StaticMember.myFormat(arset.getString("dues"))+"  "});
                tdamt+=arset.getFloat("dues");
                tpamt+=arset.getFloat("payment");
                tbamt+=arset.getFloat("kpay_amount");
            }
            total_pay_amt.setText(StaticMember.myFormat(tpamt));
            total_dues_amt.setText(StaticMember.myFormat(tdamt));
            total_bill_amt.setText(StaticMember.myFormat(tbamt));
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), "Opps", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void printLadger(String sd,String ed,int cid)
    {
        if(sd.equals("") && ed.equals("") && cid==0)
        {
             JOptionPane.showMessageDialog(null, "Plz Select Star date, End Date & Customer!");
             return;
        }
        else if(sd.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Plz Select Star date!");
             return;
        }
        else if(ed.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Plz Select End Date!");
             return;
        }
        else if(cid==0)
        {
            JOptionPane.showMessageDialog(null, "Plz Select Customer!");
             return;
        }
         else
         {
             CustomerLedgerPrintPage rbp=new CustomerLedgerPrintPage(sd,ed,cid);
            if(rbp.printCustLedger())
                JOptionPane.showMessageDialog(null, "Leger Printed!");

         }
    }
    
     public void empatyTable()
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
    }
}
