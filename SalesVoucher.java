package komalhealthcare;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SalesVoucher  extends JInternalFrame 
{

    JTextField text_label[];
    JDateChooser todate;
    JDateChooser fromdate;
    JButton generate_report, save_report;
    JPanel show_data;
    int price_id;
    JTable table;
    DefaultTableModel tableModel;
    public SalesVoucher() 
    {
        super("", false, true, false, false);
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setSize(StaticMember.SCREEN_WIDTH, StaticMember.SCREEN_HEIGHT);
        this.setLayout(new BorderLayout());
        this.setBackground(StaticMember.WINDOW_BG_COLOR);
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                //StaticMember.SALES_VOUCHER = false;
            }
        });

        JPanel body_panel = new JPanel(new BorderLayout());
        JPanel searching_panel = new JPanel(new GridLayout(1, 8, 10, 10));
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
        save_report.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                String title[]={"Sr.No.","Bill ID","Bill Date","Product Description","HSN Code","MRP","Gross Amount","Discount","GST","Net Amount","Quantity","GST Amount","Total Amount"};
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
                int trow = 0;
                String id = "";
                try {
                    ResultSet rset = StaticMember.con.createStatement().executeQuery("SELECT * FROM billing_info where billing_date>='" + start_date + "' and billing_date<='" + end_date + "'");
                    while (rset.next()) {
                        if (!id.equals("")) {
                            id = id + ",";
                        }
                        id = id + rset.getString("billing_id");
                    }
                    if (id.equals("")) {
                        return;
                    }
                    ResultSet pri = StaticMember.con.createStatement().executeQuery("select count(*) as total_rows from billing_item where billing_id in (" + id + ")");
                    pri.next();
                    trow = pri.getInt("total_rows");
                } catch (SQLException sqe) {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,sqe.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                }
                Object excelldata[][]=new Object[trow][title.length];
                int row=-1;
                try 
                {
                    ResultSet bill_info_rset = StaticMember.con.createStatement().executeQuery("SELECT * FROM billing_info where billing_date>='" + start_date + "' and billing_date<='" + end_date + "'");
                    while (bill_info_rset.next()) 
                    {
                        ResultSet pri_rset = StaticMember.con.createStatement().executeQuery("select * from billing_item bit join product pr on bit.product_id=pr.product_id join product_price pri on bit.price_id=pri.product_price_id join product_category pc on pr.product_id=pc.sr_number where bit.billing_id=" + bill_info_rset.getInt("billing_id"));
                        while (pri_rset.next()) 
                        {
                            float mrp=pri_rset.getFloat("buy_price");
                            mrp+=mrp*pri_rset.getFloat("profit_in_per")/100.0f;
                            String packing=pri_rset.getString("product_packing");
                            if(packing.indexOf(" &")!=-1 && packing.substring(0,packing.indexOf(" &")).equals("Case"))
                                mrp/=pri_rset.getInt("value_of_packing");

                            int today_quantity =pri_rset.getInt("quantity");
                            String utype;
                            if(pri_rset.getInt("value_of_packing")==1)
                                utype=pri_rset.getString("product_packing");
                            else if(packing.substring(0,packing.indexOf(" &")).equals("Case"))
                                utype=pri_rset.getString("product_packing").substring(pri_rset.getString("product_packing").indexOf(" &")+3);
                            else utype=pri_rset.getString("product_packing").substring(0,pri_rset.getString("product_packing").indexOf(" &"));

                            String today_qtys;
                            if(utype.trim().equals("Kg"))
                                today_qtys=(today_quantity/(float)1000)+"";
                            else today_qtys=today_quantity+"";
                            today_qtys=today_qtys+" "+utype;   

                            float discount=pri_rset.getFloat("discount_in_per");
                            float gst=pri_rset.getFloat("gst");
                            float amt=mrp*100.0f/(100+gst);
                            gst=mrp-amt;
                            discount=amt*(discount/100.0f);
                            row++;
                            excelldata[row][0]=row+1;
                            excelldata[row][1]=bill_info_rset.getString("billing_id");
                            excelldata[row][2]=bill_info_rset.getString("billing_date");
                            excelldata[row][3]=pri_rset.getString("product_name");
                            excelldata[row][4]=pri_rset.getString("hsncode");
                            excelldata[row][5]=pri_rset.getString("mrp_price");
                            excelldata[row][6]=String.format("%.02f",amt);
                            excelldata[row][7]=pri_rset.getFloat("discount_in_per")+" %";
                            excelldata[row][8]=pri_rset.getString("gst")+" %";
                            excelldata[row][9]=String.format("%.02f",mrp);
                            excelldata[row][10]=today_qtys;
                            excelldata[row][11]=String.format("%.02f",gst*today_quantity);
                            excelldata[row][12]=String.format("%.02f",mrp*today_quantity);
                        }
                    }
                } catch (SQLException sqe) {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,sqe.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                }
                if(StaticMember.writeInExcellFile(title, excelldata,"SalesVoucher.xlsx","Sales Voucher"))
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Export Completed!",StaticMember.SAVED_TITLE,JOptionPane.INFORMATION_MESSAGE);
                else
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Error In Exporting!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
            }
        });
        searching_panel.add(save_report);
        searching_panel.add(new JPanel());
        body_panel.add(searching_panel, BorderLayout.NORTH);
        show_data = new JPanel(new GridLayout(1, 1));
        body_panel.add(show_data);
        this.add(body_panel, BorderLayout.CENTER);

        generate_report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (fromdate.getDate() == null || todate.getDate() == null) {
                    JOptionPane.showMessageDialog(MDIMainWindow.self, "Date Must Be Entered!", StaticMember.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                    fromdate.requestFocusInWindow();
                }
                generate_report();
            }
        });
        generate_report();
    
    
    String aname[]={"BILL NO","DATE","PRODUCTS DESCRIPTION","HSN","BATCH","QTY","GROSS AMT","DISC %","DISC AMT","GST %","GST AMOUNT","NET AMOUNT \u20B9"};
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
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
        TableColumn column=null;
       
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(50);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(250);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(80);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(80);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(5);
        column.setPreferredWidth(80);
        column=table.getColumnModel().getColumn(6);
        column.setPreferredWidth(50);
        column=table.getColumnModel().getColumn(7);
        column.setPreferredWidth(80);
        column=table.getColumnModel().getColumn(8);
        column.setPreferredWidth(50);
        column=table.getColumnModel().getColumn(9);
        column.setPreferredWidth(80);
        column=table.getColumnModel().getColumn(10);
        column.setPreferredWidth(100);
        JScrollPane scr=new JScrollPane(table);
        
        body_panel.add(scr,BorderLayout.CENTER);
        this.add(body_panel, BorderLayout.CENTER);
        
        
        
        
        generate_report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(fromdate.getDate()==null || todate.getDate()==null)
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Date Must Be Entered!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                    fromdate.requestFocusInWindow();
                }
                resetTable();
                generate_report();
            }
        });
        resetTable();
        generate_report();
    }

    public void generate_report() 
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
        
        try
        {//where retail_bill_date>='"+start_date+"' AND retail_bill_date<='"+end_date+" 
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from  viewsalesreport where retail_bill_date>='"+start_date+"' AND retail_bill_date<='"+end_date+"'");
            while(rset.next())
            {
                float gross_amount=(Float.parseFloat(rset.getString("price"))*Float.parseFloat(rset.getString("quintity")));
                float gst_amt=(gross_amount*Float.parseFloat(rset.getString("gst")))/100;
                float disc_amt=(gross_amount*Float.parseFloat(rset.getString("disc")))/100;
                float net_amt=(gross_amount-disc_amt)+gst_amt;
                Date d;
                d=(rset.getDate("retail_bill_date"));
                Formatter fm3=new Formatter();
                fm3.format("%td-%tm-%tY",d,d,d);
                String date1=fm3.toString();
                tableModel.addRow(new Object[]{" "+date1+" "," "+rset.getString("product_name")+" "," "+rset.getString("hsn_code")+" "," "+rset.getString("batch")+" "," "+rset.getString("quintity")+" "," "+String.format("%.02f",Float.parseFloat(gross_amount+""))+"  ","  "+String.format("%.02f",Float.parseFloat(rset.getString("disc")))+" "," "+String.format("%.02f",Float.parseFloat(disc_amt+""))+" "," "+String.format("%.02f",Float.parseFloat(rset.getString("gst")))+"  "," "+String.format("%.02f",Float.parseFloat(gst_amt+""))+"  ",""+String.format("%.02f",Float.parseFloat(net_amt+""))+""});
                
            }
            
        }
        catch(SQLException ex)
        {
            
        }
    }
    public void resetTable()
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
    }
    
}
