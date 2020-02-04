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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Randhir
 */
public class PurcheseVoucher extends JFrame{
    JTextField text_label[];
    JDateChooser todate;
    JTextField temp;
    JDateChooser fromdate;
    JButton generate_report,save_report;
    JPanel show_data;
    JTable table;
    DefaultTableModel tableModel;
    int price_id;
    public PurcheseVoucher()
    {
        super("");
        this.setSize(StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT);
        this.setDefaultCloseOperation(SalesLedger.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
         /*this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                StaticMember.SALES_REPORT = false;
            }
        });*/
         
        JLabel h=new JLabel("PURCHASE VOUCHER",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        JPanel body_panel = new JPanel(new BorderLayout());
        JPanel north_body_panel = new JPanel(new BorderLayout());
        JPanel searching_panel = new JPanel(new GridLayout(1, 8,10,10));
        searching_panel.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        searching_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        searching_panel.add(new JPanel());
        searching_panel.add(StaticMember.MyLabel("From Date  ", JLabel.RIGHT));
        fromdate = new JDateChooser();
        fromdate.setCalendar(Calendar.getInstance());
        searching_panel.add(fromdate);
        searching_panel.add(StaticMember.MyLabel("To Date  ", JLabel.RIGHT));
        todate = new JDateChooser();
        todate.setCalendar(Calendar.getInstance());
        searching_panel.add(todate);
        generate_report = StaticMember.MyButton("Generate Voucher","");
        searching_panel.add(generate_report);
        save_report = StaticMember.MyButton("Save Voucher","");
        searching_panel.add(new JLabel());
        searching_panel.add(new JPanel());
        north_body_panel.add(new JLabel(" "), BorderLayout.NORTH);
        north_body_panel.add(searching_panel, BorderLayout.CENTER);
        body_panel.add(north_body_panel, BorderLayout.NORTH);
        
        String aname[]={"BILL NO.","BILL DATE","HSN","PRODUCTS DESCRIPTION","BATCH","MRP \u20B9","RATE \u20B9","DISC %","GST %","NET AMT \u20B9","QTY","GST AMT \u20B9","TOTAL AMT \u20B9"};
        String data[][]=new String[0][];
        table=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        tableModel.setDataVector(data,aname);
        table.setModel(tableModel);//set table model in table
        table.getTableHeader().setResizingAllowed(false);//stop resizing cell
        table.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.getTableHeader().setFont(StaticMember.headfont);
        table.getTableHeader().setBackground(StaticMember.THBcolor);
        table.getTableHeader().setForeground(StaticMember.THFcolor);
        table.setFont(StaticMember.itemfont);
        table.setBackground(StaticMember.TIBcolor);
        table.setForeground(StaticMember.TIFcolor);
        
        table.setRowHeight(22);
        table.setShowHorizontalLines(false);
        table.setShowVerticalLines(false);
        table.setAlignmentX(JTable.RIGHT_ALIGNMENT);
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        //table.getColumnModel().getColumn(5).setCellRenderer();
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(12).setCellRenderer(rightRenderer);
        //table.getColumnModel().getColumn(13).setCellRenderer(rightRenderer);
        TableColumn column=null;
       
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(10);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(220);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(5);
        column.setPreferredWidth(50);
        column=table.getColumnModel().getColumn(6);
        column.setPreferredWidth(10);
        column=table.getColumnModel().getColumn(7);
        column.setPreferredWidth(10);
        column=table.getColumnModel().getColumn(8);
        column.setPreferredWidth(10);
        column=table.getColumnModel().getColumn(9);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(10);
        column.setPreferredWidth(10);
        column=table.getColumnModel().getColumn(11);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(12);
        column.setPreferredWidth(70);
        JScrollPane scr=new JScrollPane(table);
        
        body_panel.add(scr,BorderLayout.CENTER);
        this.add(body_panel, BorderLayout.CENTER);
    
    
    
        generate_report.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Hello");
                    setDataInTable();
                }
            });
        setDataInTable();
    }   
    public void setDataInTable()
    {
        
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
        
        String start_date;
        String end_date;
        Calendar s;
        Calendar e;
        s=fromdate.getCalendar();
        e=todate.getCalendar();        
        Formatter fm1=new Formatter();
        fm1.format("%tM-%td-%tY",s,s,s);
        start_date=fm1.toString();
        Formatter fm2=new Formatter();
        fm2.format("%tM-%td-%tY",e,e,e);
        end_date=fm2.toString();
        int row = 0;
        
        try
        {
            //ResultSet pr=StaticMember.con.createStatement().executeQuery("SELECT count(*) as total_rows FROM stock_item a join products as p  on p.product_id=a.product_id");
            //pr.next();
            //row = pr.getInt("total_rows");// where inv_date>=" + start_date + " and inv_date<='" + end_date + "'
            ResultSet inv_rset = StaticMember.con.createStatement().executeQuery("SELECT * FROM viewpurchesvoucher");
            
            while (inv_rset.next())            
            {   
                //row++;
                
                ResultSet sup_rset = StaticMember.con.createStatement().executeQuery("SELECT * FROM supplier where supplier_id='"+inv_rset.getString("supplier_id")+"'");
                sup_rset.next();
                
                ResultSet p_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM products where product_id='"+inv_rset.getInt("product_id")+"'");
                p_rset.next();
                ResultSet t_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM type where type_id='"+p_rset.getInt("type_id")+"'");
                t_rset.next();
                
                ResultSet m_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM manifacture where manifacture_id='"+inv_rset.getInt("manifacture_id")+"'");
                m_rset.next();
                
                float p_rate=inv_rset.getFloat("prate");
                    Formatter fmt_p_rate=new Formatter(); 
                    fmt_p_rate.format("%.2f",p_rate);
                    
                    float mrp=inv_rset.getFloat("mrp");
                    Formatter fmt_mrp=new Formatter(); 
                    fmt_mrp.format("%.2f",mrp);
                    
                    float disc=inv_rset.getFloat("disc");
                    Formatter fmt_disc=new Formatter(); 
                    fmt_disc.format("%.2f",disc);
                    
                    float gst=inv_rset.getFloat("tax");
                    Formatter fmt_gst=new Formatter(); 
                    fmt_gst.format("%.2f",gst);
                    
                    int qty=inv_rset.getInt("qty");
                    
                    float dis=(p_rate*disc)/100;
                    
                    float t_dis=dis*qty;
                    
                    float gst_amt=((p_rate*gst)/100)*qty;
                    Formatter fmt_gst_amt=new Formatter(); 
                    fmt_gst_amt.format("%.2f",gst_amt);
                    
                    float net_amt=p_rate-dis;
                    Formatter fmt_net_amt=new Formatter(); 
                    fmt_net_amt.format("%.2f",net_amt);
                    
                    float total_amt=p_rate*qty-t_dis+gst_amt;
                    Formatter fmt_total_amt=new Formatter(); 
                    fmt_total_amt.format("%.2f",total_amt);
                    
                    String h=p_rset.getString("product_hsn_code");
                    String hsn_code=h.substring(0,4);
                    
                    
                    String p_name=p_rset.getString("product_name")+" "+t_rset.getString("type_name")+" "+inv_rset.getString("packing");
                    JOptionPane.showMessageDialog(null, p_rset.getString("product_name"));
                   //"BILL NO.","BILL DATE","HSN","PRODUCTS DESCRIPTION","BATCH","MRP \u20B9","RATE \u20B9","DISC %","GST %","NET AMT \u20B9","QTY","GST AMT \u20B9","TOTAL AMT \u20B9"
                    tableModel.addRow(new Object[]{" "+inv_rset.getString("inv_no")+" "," "+inv_rset.getString("inv_date")+" "," "+hsn_code+" "," "+p_name+" "," "+inv_rset.getString("batch_no")+" "," "+fmt_mrp+" "," "+fmt_p_rate+" "," "+fmt_disc+" "," "+fmt_gst+" "," "+fmt_net_amt+" "," "+qty+" "," "+fmt_gst_amt+" "," "+fmt_total_amt+"  "});
            
            }
            
        }
        catch(SQLException ex)
        {
            
        }
    }
}
