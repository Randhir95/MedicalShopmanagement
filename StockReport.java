/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JInternalFrame;
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

/**
 *
 * @author RANDHIR KUMAR
 */
public class StockReport extends JInternalFrame{
    JTable intable,outtable,curenttable,producttable;
    DefaultTableModel intableModel,outtableModel,curenttableModel,producttableModel;
    JDateChooser todate;
    JDateChooser fromdate;
    JPanel show_data;
    JButton generate_report,save_report;
    public StockReport()
    {
        super("",true,true);
        StaticMember.setSize(this);
        this.setDefaultCloseOperation(StockReport.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.STOCK_REPORT=false;
              }});
        
        JPanel body_panel = new JPanel(new BorderLayout());
        JPanel searching_panel = new JPanel(new GridLayout(1, 8,10,10));
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
        generate_report = StaticMember.MyButton("Generate Report","");
        searching_panel.add(generate_report);
        save_report = StaticMember.MyButton("Save Report","");
        searching_panel.add(save_report);
        //searching_panel.add(new JLabel());
        searching_panel.add(new JPanel());
        body_panel.add(searching_panel, BorderLayout.NORTH);
        show_data=new JPanel(new GridLayout(1,1));
        body_panel.add(show_data);
        this.add(body_panel, BorderLayout.NORTH);
        
        generate_report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(fromdate.getDate()==null || todate.getDate()==null)
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Date Must Be Entered!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                    fromdate.requestFocusInWindow();
                }
                setDataInTable();
            }
        });
        
        save_report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save_report();
            }
        });
        
        JPanel main_table_panel=new JPanel(new BorderLayout());
        JPanel t_panel=new JPanel(new BorderLayout());
        JPanel table_panel=new JPanel(new GridLayout(1,4));
        JLabel in=new JLabel("INWORDS",JLabel.CENTER);
        in.setFont(StaticMember.labelFont);
        in.setBackground(StaticMember.IN_COLOR);
        in.setForeground(Color.WHITE);
        in.setOpaque(true);
        JLabel out=new JLabel("OUTWORDS",JLabel.CENTER);
        out.setFont(StaticMember.labelFont);
        out.setBackground(StaticMember.OUT_COLOR);
        out.setForeground(Color.WHITE);
        out.setOpaque(true);
        JLabel current=new JLabel("CURRENT",JLabel.CENTER);
        current.setFont(StaticMember.labelFont);
        current.setBackground(StaticMember.CURRENT_COLOR);
        current.setForeground(Color.WHITE);
        current.setOpaque(true);
        JLabel close=new JLabel("CLOSING",JLabel.CENTER);
        close.setFont(StaticMember.labelFont);
        close.setBackground(StaticMember.OUT_COLOR);
        close.setForeground(Color.WHITE);
        close.setOpaque(true);
        t_panel.add(new JLabel("                                                                                       "),BorderLayout.WEST);
        table_panel.add(in);
        table_panel.add(out);table_panel.add(current);table_panel.add(close);
        main_table_panel.add(t_panel,BorderLayout.NORTH);t_panel.add(table_panel,BorderLayout.CENTER);
        
       
        String data1[][]=new String[0][];
        String name[]={"PARTICULAR","QUANTITY","RATE (\u20B9)","VALUE (\u20B9)","QUANTITY","RATE (\u20B9)","VALUE (\u20B9)","QUANTITY","RATE (\u20B9)","VALUE (\u20B9)","QUANTITY","RATE (\u20B9)","VALUE (\u20B9)"};
        String pname[]={"PARTICULAR"};

        
        producttable=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        producttableModel = new DefaultTableModel();//create table model
        producttableModel.setDataVector(data1,name);
        producttable.setModel(producttableModel);//set table model in table
        producttable.getTableHeader().setResizingAllowed(false);//stop resizing cell
        producttable.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        producttable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        //table.setEnabled(false);
        producttable.getTableHeader().setFont(StaticMember.headfont);
        producttable.getTableHeader().setBackground(StaticMember.THBcolor);
        producttable.getTableHeader().setForeground(StaticMember.THFcolor);
        producttable.setFont(StaticMember.itemfont);
        producttable.setBackground(StaticMember.TIBcolor);
        producttable.setForeground(StaticMember.TIFcolor);
        producttable.setRowHeight(22);
        producttable.setAlignmentX(JTable.RIGHT_ALIGNMENT);
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        producttable.setAlignmentX(JTable.RIGHT_ALIGNMENT);
        producttable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        producttable.getColumnModel().getColumn(12).setCellRenderer(rightRenderer);
        TableColumn column=null;
        column=producttable.getColumnModel().getColumn(0);
        column.setPreferredWidth(200);
        column=producttable.getColumnModel().getColumn(1);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(2);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(3);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(4);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(5);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(6);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(7);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(8);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(9);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(10);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(11);
        column.setPreferredWidth(30);
        column=producttable.getColumnModel().getColumn(12);
        column.setPreferredWidth(30);
        
        JScrollPane scr=new JScrollPane(producttable);
        main_table_panel.add(scr,BorderLayout.CENTER);
        this.add(main_table_panel,BorderLayout.CENTER);
        setDataInTable();
    }
    
    public void setDataInTable()
    {
        
        producttableModel.getDataVector().removeAllElements();
        producttableModel.addRow(new Object[]{});
        producttableModel.removeRow(0);
        
        String start_date;
        String end_date;
        Calendar s;
        Calendar e;
        s=fromdate.getCalendar();
        e=todate.getCalendar();        
        Formatter fm1=new Formatter();
        fm1.format("%tY-%tm-%td",s,s,s);
        start_date=fm1.toString();
        Formatter fm2=new Formatter();
        fm2.format("%tY-%tm-%td",e,e,e);
        end_date=fm2.toString();
        int row = 0;
        try
        {
            ResultSet pr=StaticMember.con.createStatement().executeQuery("SELECT count(*) as total_rows FROM availableitem a join products as p  on p.product_id=a.product_id");
            pr.next();
            row = pr.getInt("total_rows");
            ResultSet prset = StaticMember.con.createStatement().executeQuery("SELECT * FROM availableitem a join products as p on a.product_id=p.product_id where a.quintity>0");
        
            while (prset.next())            
            {   
                row++;
                ResultSet qrset = StaticMember.con.createStatement().executeQuery("SELECT sum(qty) as total_quantity FROM stock_item where product_id=" + prset.getInt("product_id") + " and added_on<'" + start_date + "'");
                qrset.next();
                
                ResultSet bi_rset = StaticMember.con.createStatement().executeQuery("SELECT sum(bi.quintity) as total_billing_quantity FROM retail_bill_items bi join retail_bill bif on bi.retail_bill_no=bif.retail_bill_no where bi.product_id=" + prset.getInt("product_id") + " and bif.retail_bill_date<'" + start_date + "'");
                bi_rset.next();
                long inwards_quantity = qrset.getLong("total_quantity") - bi_rset.getLong("total_billing_quantity");
                
                qrset = StaticMember.con.createStatement().executeQuery("SELECT sum(quintity) as total_quantity FROM availableitem where product_id=" + prset.getInt("product_id") + " and added_on>='" + start_date + "' and added_on<='" + end_date + "'");
                qrset.next();
                long today_quantity = qrset.getLong("total_quantity");
                
                bi_rset = StaticMember.con.createStatement().executeQuery("SELECT sum(bi.quintity) as total_billing_quantity FROM retail_bill_items bi join retail_bill bif on bi.retail_bill_no=bif.retail_bill_no where bi.product_id=" + prset.getInt("product_id") + " and bif.retail_bill_date>='" + start_date + "' and bif.retail_bill_date<='" + end_date + "'");
                bi_rset.next();
                long outwards_quantity = bi_rset.getLong("total_billing_quantity");
                
                long closing_quantity = inwards_quantity + today_quantity - outwards_quantity;
                
                
                ResultSet pri_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM availableitem where product_id='"+prset.getInt("p.product_id")+"'");
                pri_rset.next();
                ResultSet p_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM products where product_id='"+prset.getInt("product_id")+"'");
                p_rset.next();
                ResultSet t_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM type where type_id='"+prset.getInt("type_id")+"'");
                t_rset.next();
                    
                    float s_rate=pri_rset.getFloat("sale_rate");
                    Formatter fmt_s_rate=new Formatter(); 
                    fmt_s_rate.format("%.2f",s_rate);
                    
                    float today_value=s_rate*today_quantity;
                    Formatter fmt_today_value=new Formatter(); 
                    fmt_today_value.format("%.2f",today_value);
                    
                    float inward_value=s_rate*inwards_quantity;
                    Formatter fmt_inward_value=new Formatter(); 
                    fmt_inward_value.format("%.2f",inward_value);
                    
                    float out_value=s_rate*outwards_quantity;
                    Formatter fmt_out_value=new Formatter(); 
                    fmt_out_value.format("%.2f",out_value);
                    
                    float close_value=s_rate*closing_quantity;
                    Formatter fmt_close_value=new Formatter(); 
                    fmt_close_value.format("%.2f",close_value);
                    
                    String p_name=p_rset.getString("product_name")+" "+t_rset.getString("type_name")+" "+pri_rset.getString("packing");
                    producttableModel.addRow(new Object[]{" "+p_name+" "," "+inwards_quantity+" "," "+fmt_s_rate+" "," "+fmt_inward_value+" "," "+outwards_quantity+" "," "+fmt_s_rate+" "," "+fmt_out_value+" "," "+today_quantity+" "," "+fmt_s_rate+" "," "+fmt_today_value+" "," "+closing_quantity+" "," "+fmt_s_rate+" "," "+fmt_close_value+"  "});
            }
        
        
        }
        catch(SQLException ex){JOptionPane.showMessageDialog(null, ex.toString());}
        catch(NumberFormatException ex){}
        
        
    }
    
    public void save_report() 
    {
        String start_date;
        String end_date;
        Calendar s;
        Calendar e;
        s=fromdate.getCalendar();
        e=todate.getCalendar();        
        Formatter fm1=new Formatter();
        fm1.format("%tY-%tm-%td",s,s,s);
        start_date=fm1.toString();
        Formatter fm2=new Formatter();
        fm2.format("%tY-%tm-%td",e,e,e);
        end_date=fm2.toString();
        int row=0;
        try {
            ResultSet rset = StaticMember.con.createStatement().executeQuery("SELECT count(*) as total_rows FROM availableitem a join products as p  on p.product_id=a.product_id");
            rset.next();
            row = rset.getInt("total_rows");
        } catch (SQLException sqe) {
            
        }
        //String data[]=new String[+1];
        
        //int current_row=0;
        String title[]={"Particular","Quantity","Rate (\u20B9)","Value (\u20B9)","Quantity","Rate (\u20B9)","Value (\u20B9)","Quantity","Rate (\u20B9)","Value (\u20B9)","Quantity","Rate (\u20B9)","Value (\u20B9)"};
        Object excelldata[][]=new Object[row][title.length];
                int row1=-1;
         try
        {
            ResultSet pr=StaticMember.con.createStatement().executeQuery("SELECT count(*) as total_rows FROM availableitem a join products as p  on p.product_id=a.product_id");
            pr.next();
            row = pr.getInt("total_rows");
            ResultSet prset = StaticMember.con.createStatement().executeQuery("SELECT * FROM availableitem a join products as p on a.product_id=p.product_id where a.quintity>0");
        
            while (prset.next())            
            {   
                row++;
                ResultSet qrset = StaticMember.con.createStatement().executeQuery("SELECT sum(qty) as total_quantity FROM stock_item where product_id=" + prset.getInt("product_id") + " and added_on<'" + start_date + "'");
                qrset.next();
                
                ResultSet bi_rset = StaticMember.con.createStatement().executeQuery("SELECT sum(bi.quintity) as total_billing_quantity FROM retail_bill_items bi join retail_bill bif on bi.retail_bill_no=bif.retail_bill_no where bi.product_id=" + prset.getInt("product_id") + " and bif.retail_bill_date<'" + start_date + "'");
                bi_rset.next();
                long inwards_quantity = qrset.getLong("total_quantity") - bi_rset.getLong("total_billing_quantity");
                
                qrset = StaticMember.con.createStatement().executeQuery("SELECT sum(quintity) as total_quantity FROM availableitem where product_id=" + prset.getInt("product_id") + " and added_on>='" + start_date + "' and added_on<='" + end_date + "'");
                qrset.next();
                long today_quantity = qrset.getLong("total_quantity");
                
                bi_rset = StaticMember.con.createStatement().executeQuery("SELECT sum(bi.quintity) as total_billing_quantity FROM retail_bill_items bi join retail_bill bif on bi.retail_bill_no=bif.retail_bill_no where bi.product_id=" + prset.getInt("product_id") + " and bif.retail_bill_date>='" + start_date + "' and bif.retail_bill_date<='" + end_date + "'");
                bi_rset.next();
                long outwards_quantity = bi_rset.getLong("total_billing_quantity");
                
                long closing_quantity = inwards_quantity + today_quantity - outwards_quantity;
                
                
                ResultSet pri_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM availableitem where product_id='"+prset.getInt("p.product_id")+"'");
                pri_rset.next();
                ResultSet p_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM products where product_id='"+prset.getInt("product_id")+"'");
                p_rset.next();
                ResultSet t_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM type where type_id='"+prset.getInt("type_id")+"'");
                t_rset.next();
                    
                    float s_rate=pri_rset.getFloat("sale_rate");
                    Formatter fmt_s_rate=new Formatter(); 
                    fmt_s_rate.format("%.2f",s_rate);
                    
                    float today_value=s_rate*today_quantity;
                    Formatter fmt_today_value=new Formatter(); 
                    fmt_today_value.format("%.2f",today_value);
                    
                    float inward_value=s_rate*inwards_quantity;
                    Formatter fmt_inward_value=new Formatter(); 
                    fmt_inward_value.format("%.2f",inward_value);
                    
                    float out_value=s_rate*outwards_quantity;
                    Formatter fmt_out_value=new Formatter(); 
                    fmt_out_value.format("%.2f",out_value);
                    
                    float close_value=s_rate*closing_quantity;
                    Formatter fmt_close_value=new Formatter(); 
                    fmt_close_value.format("%.2f",close_value);
                    
                    
                String p_name=p_rset.getString("product_name")+" "+t_rset.getString("type_name")+" "+pri_rset.getString("packing");
                row1++;
                excelldata[row1][0]=p_name;
                excelldata[row1][1]=inwards_quantity+"";
                excelldata[row1][2]=s_rate+"";
                excelldata[row1][3]=inward_value+"";
                excelldata[row1][4]=today_quantity+"";
                excelldata[row1][5]=s_rate+"";
                excelldata[row1][6]=today_value+"";
                excelldata[row1][7]=outwards_quantity+"";
                excelldata[row1][8]=s_rate+"";
                excelldata[row1][9]=out_value+"";
                excelldata[row1][10]=closing_quantity+"";
                excelldata[row1][11]=s_rate+"";
                excelldata[row1][12]=close_value+"";
            }
        }
         catch(SQLException ex){JOptionPane.showMessageDialog(null, ex.toString());}
        catch(NumberFormatException ex){
            
        }
         if(StaticMember.writeInExcellFile(title, excelldata,"StockReport.xlsx","Stock Report"))
                JOptionPane.showMessageDialog(MDIMainWindow.self,"Export Completed!",StaticMember.SAVED_TITLE,JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Error In Exporting!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
    }
}
