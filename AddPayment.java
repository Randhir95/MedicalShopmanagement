/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import static komalhealthcare.MDIMainWindow.desktop;

/**
 *
 * @author RANDHIR KUMAR
 */
public class AddPayment extends JFrame{
    private JButton submit_btn,ebutton,print,close,allbil;
     private JDateChooser tdate;
     JLabel total_amt;
     ResultSet rset=null;
     String listItems[][];
     JTable table;
    DefaultTableModel tableModel;
    //BillWindow b_w_obj;
    public AddPayment()
    {
        super("Add Payment");
        StaticMember.setSize(this);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(AddPayment.DISPOSE_ON_CLOSE);
        
        /*this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.VIEW_BILL_WINDOW=false;
              }});*/
        
        JLabel h=new JLabel(" VIEW BILL",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        JLabel h2=new JLabel("BILLS",JLabel.CENTER);
        h2.setFont(StaticMember.labelFont);
        h2.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h2.setForeground(StaticMember.HEAD_FG_COLOR1);
        h2.setOpaque(true);
        h2.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        JPanel date_panel=new JPanel(new GridLayout(1,4));
        JPanel main_date_panel=new JPanel(new BorderLayout());
        JPanel main_panel=new JPanel(new BorderLayout());
        JPanel main_table_panel=new JPanel(new BorderLayout());
        JPanel submit_panel=new JPanel(new BorderLayout());
        JPanel btn_panel=new JPanel(new GridLayout(1,2,10,10));
        JPanel foot_panel=new JPanel(new GridLayout(1,3,10,10));
        JPanel foot_buton_panel=new JPanel(new GridLayout(1,4,10,10));
        JPanel foot_amt_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel amt_panel=new JPanel(new BorderLayout());
        JLabel l1=new JLabel("SELECT DATE :   ",JLabel.RIGHT);
        l1.setFont(StaticMember.labelFont);
        JLabel l2=new JLabel("TOTAL SELL VALUE :   ",JLabel.RIGHT);
        l2.setFont(StaticMember.labelFont);
        
        total_amt=new JLabel("",JLabel.RIGHT);
        total_amt.setFont(StaticMember.textFont);
        
        tdate=new JDateChooser();
        tdate.setFont(StaticMember.labelFont);
        tdate.setCalendar(Calendar.getInstance());
        submit_btn=new JButton("GENERATE BILL");
        submit_btn.setFont(StaticMember.buttonFont);
        close=new JButton("CLOSE");
        close.setFont(StaticMember.buttonFont);
        allbil=new JButton("ALL BILL");
        allbil.setFont(StaticMember.buttonFont);
        String bdate;
        Calendar cal;
        cal=tdate.getCalendar();
        Formatter bdatefmt=new Formatter();
        bdatefmt.format("%tY-%tm-%td",cal,cal,cal);
        bdate=bdatefmt.toString();
        
        
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
        
        allBill();
        table.setRowHeight(22);
        JScrollPane scrl=new JScrollPane(table);  
          
        date_panel.add(l1);
        date_panel.add(tdate);
        main_date_panel.add(new JLabel("                       "),BorderLayout.NORTH); main_date_panel.add(new JLabel("                       "),BorderLayout.SOUTH);
        btn_panel.add(submit_btn);btn_panel.add(allbil);
        submit_panel.add(btn_panel,BorderLayout.CENTER);submit_panel.add(new JLabel("                    "),BorderLayout.WEST);
        submit_panel.add(new JLabel("                                                                                   "),BorderLayout.EAST);
        main_date_panel.add(date_panel,BorderLayout.CENTER);main_date_panel.add(submit_panel,BorderLayout.EAST);
        main_panel.add(main_date_panel,BorderLayout.NORTH);
        main_table_panel.add(scrl,BorderLayout.CENTER);main_table_panel.add(h2,BorderLayout.NORTH);
        main_panel.add(main_table_panel,BorderLayout.CENTER);
        amt_panel.add(total_amt,BorderLayout.CENTER);amt_panel.add(new JLabel("  \u20B9    "),BorderLayout.EAST);
        foot_amt_panel.add(l2);foot_amt_panel.add(amt_panel);
        foot_buton_panel.add(new JLabel(""));foot_buton_panel.add(new JLabel(""));foot_buton_panel.add(new JLabel(""));
        foot_buton_panel.add(close);
        foot_panel.add(new JLabel(""));foot_panel.add(foot_buton_panel);foot_panel.add(foot_amt_panel);
        this.add(foot_panel,BorderLayout.SOUTH);
        this.add(main_panel,BorderLayout.CENTER);
        
        submit_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                empatyTable();
                serchToDate();
            }});
        
        submit_btn.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                   empatyTable();
                    serchToDate();
                }
            }
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {}});
        
        allbil.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                empatyTable();
                allBill();
            }});
        
        allbil.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                   empatyTable();
                    allBill();
                }
            }});
        
        /*table.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2)
                {
                    String bs=table.getValueAt(table.getSelectedRow(),1).toString().trim();
                    String bill=bs.substring(6);
                    if(bill.equals(null))
                    {
                        JOptionPane.showMessageDialog(null, "BILL ITEMS ARE NOT AVAILABLE");
                    }
                    //BillWindow bwin=new BillWindow(bill);
                    /*if(StaticMember.VIEW_BILL==false)
                    {
                        b_w_obj=new BillWindow(bill);
                         desktop.add(b_w_obj);
                         b_w_obj.setVisible(true);
                         b_w_obj.setResizable(false);
                         StaticMember.VIEW_BILL=true;
                        
                     }*//*
                    
                    //bwin.setVisible(true);
                }
                if(e.getClickCount()==3)
                {
                    if(JOptionPane.showConfirmDialog(MDIMainWindow.self,"Are You Sure To Print Bill?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    {
                        String bs=table.getValueAt(table.getSelectedRow(),1).toString().trim();
                        String bill=bs.substring(6);
                        if(bill.equals(null))
                        {
                            JOptionPane.showMessageDialog(null, "BILL ITEMS ARE NOT AVAILABLE");
                        }
                        else
                        {
                            RetailBillPage rbp=new RetailBillPage(bill);
                            if(rbp.printBill())
                            JOptionPane.showMessageDialog(null, "Bill Printed!");

                        }
                    }
                }
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });*/
        JOptionPane.showMessageDialog(null, "hello");
    }
    
    public void serchToDate()
    {
        float tamt=0;
        String bdate;
        Calendar cal;
        cal=tdate.getCalendar();
        Formatter bdatefmt=new Formatter();
        bdatefmt.format("%tY-%tm-%td",cal,cal,cal);
        bdate=bdatefmt.toString();
        try
        {
            Statement stmt=StaticMember.con.createStatement();
            rset=stmt.executeQuery("select * from k_retail_bill where retail_bill_date like'"+bdate+"'");
            //find total no, of records
            rset.last();
            int tr=rset.getRow();
            rset.beforeFirst();
            while(rset.next())
            {
                ResultSet client_rset=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+rset.getInt("customer_id")+"'");
                client_rset.next();
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+"RGST00"+rset.getString("retail_bill_no")+" "," "+rset.getString("retail_bill_date")+" "," "+client_rset.getString("customer_name")+" "," "+client_rset.getString("customer_address")+" "," "+client_rset.getString("customer_mob")+"  ","  "+String.format("%.02f",Float.parseFloat(rset.getString("pay_amount")))+" "," "+String.format("%.02f",Float.parseFloat(rset.getString("payment")))+"  "+String.format("%.02f",Float.parseFloat(rset.getString("dues")))+"  "});
                float p_amt=Float.parseFloat(rset.getString("pay_amount"));
                tamt+=p_amt;
            }
            total_amt.setText(String.format("%.02f",tamt));
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), "OOPs!", JOptionPane.ERROR_MESSAGE);
        listItems=new String[0][0]; 
        }
    }
    
    public void empatyTable()
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
    }
    
    public void serchToBillNo()
    {
        
    }
    public void allBill()
    {
        try
        {
            float tamt=0;
            Statement stmt=StaticMember.con.createStatement();
            rset=stmt.executeQuery("select * from k_retail_bill");
            //find total no, of records
            rset.last();
            int tr=rset.getRow();
            rset.beforeFirst();
            
            while(rset.next())
            {
                ResultSet client_rset=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+rset.getInt("customer_id")+"'");
                client_rset.next();
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+"RGST00"+rset.getString("retail_bill_no")+" "," "+rset.getString("retail_bill_date")+" "," "+client_rset.getString("customer_name")+" "," "+client_rset.getString("customer_address")+" "," "+client_rset.getString("customer_mob")+"  ","  "+String.format("%.02f",Float.parseFloat(rset.getString("pay_amount")))+" "," "+String.format("%.02f",Float.parseFloat(rset.getString("payment")))+"  "," "+String.format("%.02f",Float.parseFloat(rset.getString("dues")))+"  "});
                float p_amt=Float.parseFloat(rset.getString("pay_amount"));
                tamt+=p_amt;
            }
            total_amt.setText(String.format("%.02f",tamt));
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), "OOPs!", JOptionPane.ERROR_MESSAGE);
        listItems=new String[0][0]; 
        }
    }
}
