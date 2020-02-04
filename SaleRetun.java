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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Formatter;
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
public class SaleRetun extends JInternalFrame{
    private JButton print,close,view;
    private JDateChooser startDate;
    private JDateChooser endDate;
    private JTable table;
    private JLabel gstframt,gstableamt,gstamt,totalamt;
    private DefaultTableModel tableModel;
    private String listItems[][];
    public SaleRetun()
    {
        super("SALE RETURN",true,true);
        StaticMember.setSize(this);
        this.setDefaultCloseOperation(SaleRetun.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.SALE_RETUN=false;
              }});
        StaticMember.labelHeding("SALE RETURN",this);
        
        JLabel sdate=StaticMember.MyLabel(" START DATE :  ", JLabel.RIGHT);
        startDate=new JDateChooser();
        startDate.setFont(StaticMember.labelFont1);
        
        JLabel edate=StaticMember.MyLabel("  END DATE :  ",JLabel.RIGHT);
        endDate=new JDateChooser();
        endDate.setFont(StaticMember.labelFont1);
        
        print=StaticMember.button("PRINT");
        close=StaticMember.button("CLOSE");
        view=StaticMember.button("VIEW");
        
        gstframt=StaticMember.MyLabel("");
        gstframt.setHorizontalAlignment(SwingConstants.RIGHT);
        gstableamt=StaticMember.MyLabel("");
        gstableamt.setHorizontalAlignment(SwingConstants.RIGHT);
        gstamt=StaticMember.MyLabel("");
        gstamt.setHorizontalAlignment(SwingConstants.RIGHT);
        totalamt=StaticMember.MyLabel("");
        totalamt.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel tlabel=StaticMember.MyLabel(" TOTAL :  ", JLabel.RIGHT);
       
        table=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        String Heding[]={"SR","BILL NO.","BILL DATE","P A R T Y   N A M E","GST FREEE","GSTABEL AMOUNT","GST","TOTAL AMOUNT"};
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
        
       
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        
        table.setFont(StaticMember.itemfont);
        table.getTableHeader().setFont(StaticMember.headfont);
        table.getTableHeader().setBackground(StaticMember.THBcolor);
        table.getTableHeader().setForeground(StaticMember.THFcolor);
        table.setBackground(StaticMember.TIBcolor);
        table.setForeground(StaticMember.TIFcolor);
        table.setRowHeight(22);
        JScrollPane scrl=new JScrollPane(table);
        
        JPanel headpanel=new JPanel(new GridLayout(1,3,100,100));
        JPanel mainheadpanel=new JPanel(new BorderLayout());
        JPanel mainpanel=new JPanel(new BorderLayout());
        JPanel sdpanel=new JPanel(new BorderLayout());
        JPanel edpanel=new JPanel(new BorderLayout());
        JPanel btnpanel=new JPanel(new GridLayout(1,3));
        JPanel shouthpanel=new JPanel(new BorderLayout());
        JPanel shouthbtnpanel=new JPanel(new GridLayout(1,2,10,10));
        JPanel mainamtpanel=new JPanel(new BorderLayout());
        JPanel mainfootpanel=new JPanel(new GridLayout(1,2,5,5));
        JPanel tpanel=new JPanel(new BorderLayout());
        JPanel amtpanel=new JPanel(new GridLayout(1,4,5,5));
        
        amtpanel.add(gstframt);amtpanel.add(gstableamt);amtpanel.add(gstamt);amtpanel.add(totalamt);
        tpanel.add(tlabel,BorderLayout.EAST);tpanel.add(new JLabel("      "),BorderLayout.CENTER);
        mainfootpanel.add(tpanel);mainfootpanel.add(amtpanel);
        mainamtpanel.add(new JLabel("                                                       "),BorderLayout.WEST);mainamtpanel.add(mainfootpanel,BorderLayout.CENTER);
        mainamtpanel.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,50));
        sdpanel.add(sdate,BorderLayout.WEST);sdpanel.add(startDate,BorderLayout.CENTER);
        edpanel.add(edate,BorderLayout.WEST);edpanel.add(endDate,BorderLayout.CENTER);
        btnpanel.add(view);btnpanel.add(new JLabel("        "));btnpanel.add(new JLabel("               "));
        headpanel.add(sdpanel);headpanel.add(edpanel);headpanel.add(btnpanel);
        mainheadpanel.add(new JLabel("        "),BorderLayout.EAST);mainheadpanel.add(new JLabel("         "),BorderLayout.WEST);
        mainheadpanel.add(new JLabel("        "),BorderLayout.NORTH);mainheadpanel.add(new JLabel("         "),BorderLayout.SOUTH);
        mainheadpanel.add(headpanel,BorderLayout.CENTER);
        mainpanel.add(mainheadpanel,BorderLayout.NORTH);
        mainpanel.add(scrl,BorderLayout.CENTER);
        mainpanel.add(mainamtpanel,BorderLayout.SOUTH);
        shouthbtnpanel.add(close);shouthbtnpanel.add(print);
        shouthpanel.add(new JLabel("  "),BorderLayout.CENTER);shouthpanel.add(shouthbtnpanel,BorderLayout.EAST);
        this.add(mainpanel,BorderLayout.CENTER);
        this.add(shouthpanel,BorderLayout.SOUTH);
        
        
        
        
        
        print.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                printRetun(StaticMember.getDate(startDate),StaticMember.getDate(endDate));
           }});
        print.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                printRetun(StaticMember.getDate(startDate),StaticMember.getDate(endDate));
            }});
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                 StaticMember.SALE_RETUN=false;
                SaleRetun.this.dispose();
                
           }
        });
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                     StaticMember.SALE_RETUN=false;
                SaleRetun.this.dispose();}
            }
            });
        view.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                setDataInTable(StaticMember.getDate(startDate),StaticMember.getDate(endDate));
           }
        });
        view.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                     setDataInTable(StaticMember.getDate(startDate),StaticMember.getDate(endDate));
                }
            }});
    }
    
    private void setDataInTable(String sdate , String edate)
    {
        if(condisions(sdate,edate)==false)
        {
            return;
        }
        float gstfree_amt=0,gstable_amt=0,gst_amt=0,total_amt=0;
        try
        {
            empatyTable();
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from retail_bill where (retail_bill_date)>='"+sdate+"' AND (retail_bill_date)<='"+edate+"'");
            rset.last();
            rset.beforeFirst();
            while(rset.next())
            {
                ResultSet cus_rset=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+rset.getString("customer_id")+"'");
                cus_rset.next();
                String customerDetail=cus_rset.getString("customer_name")+" - "+cus_rset.getString("customer_address")+" - "+cus_rset.getString("customer_mob");
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+"RGST00"+rset.getString("retail_bill_no")+" "," "+rset.getString("retail_bill_date")+" "," "+customerDetail+" "," "+StaticMember.myFormat(rset.getString("pay_amount"))+" "," "+" 00.00 "+"  ","  "+" 00.00 "+" "," "+StaticMember.myFormat(rset.getString("pay_amount"))+"  "});
                gstfree_amt+=Float.parseFloat(rset.getString("pay_amount"));
                total_amt+=Float.parseFloat(rset.getString("pay_amount"));
            }
            gstframt.setText(StaticMember.myFormat(gstfree_amt));
            gstableamt.setText("00.00");
            gstamt.setText("00.00");
            totalamt.setText(StaticMember.myFormat(total_amt));
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(SaleRetun.this, ex.getMessage());
        }
    }
    
    private void printRetun(String sdate , String edate)
    {
        if(condisions(sdate,edate)==false)
        {
            return;
        }
        //RetunPage ob=new RetunPage(sdob.getYear()+"-"+(sdob.getMonth()+1)+"-"+sdob.getDay(),stdob.getYear()+"-"+(stdob.getMonth()+1)+"-"+stdob.getDay()); 
        RetunPage ob=new RetunPage(sdate,edate); 
        if(ob.printRetun())
            JOptionPane.showMessageDialog(SaleRetun.this, "Retun Printed");
        else
            JOptionPane.showMessageDialog(SaleRetun.this, "Error!");
    }
    
    private boolean condisions(String sdate , String edate)
    {
        boolean f=true;
        String sdate1  = ((JTextField)startDate.getDateEditor().getUiComponent()).getText();
        String edate1  = ((JTextField)endDate.getDateEditor().getUiComponent()).getText();
         if(sdate1.equals("")&& edate1.equals("")) 
        {
            JOptionPane.showMessageDialog(SaleRetun.this, "Plz Select Star date & End Date!");
            f=false;
        }
        else if(sdate1.equals(""))
        {
            JOptionPane.showMessageDialog(SaleRetun.this, "Plz Select Star date!");
            f=false;
        }
        else if(edate1.equals(""))
        {
            JOptionPane.showMessageDialog(SaleRetun.this, "Plz Select End date!");
             f=false;
        }
         return f;
    }
    
    public void empatyTable()
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
    }
}
