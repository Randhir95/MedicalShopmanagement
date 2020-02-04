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

public class SalesReport extends JInternalFrame {

    JTextField text_label[];
    JDateChooser todate;
    JTextField temp;
    JDateChooser fromdate;
    JButton generate_report,save_report;
    JPanel show_data;
    JTable table;
    DefaultTableModel tableModel;
    int price_id;
    public SalesReport() {
        super("",true,true);
        this.setDefaultCloseOperation(SalesReport.DISPOSE_ON_CLOSE);
        this.setSize(StaticMember.SCREEN_WIDTH, StaticMember.SCREEN_HEIGHT-150);
        this.setLayout(new BorderLayout());
        this.setBackground(StaticMember.WINDOW_BG_COLOR);
        this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                StaticMember.SALES_REPORT = false;
            }
        });
        StaticMember.labelHeding("SALES REPORT", this);
        
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
        generate_report = StaticMember.MyButton("Generate Report","");
        searching_panel.add(generate_report);
        save_report = StaticMember.MyButton("Save Report","");
        searching_panel.add(new JLabel());
        searching_panel.add(new JPanel());
        north_body_panel.add(new JLabel(" "), BorderLayout.NORTH);
        north_body_panel.add(searching_panel, BorderLayout.CENTER);
        body_panel.add(north_body_panel, BorderLayout.NORTH);
        //show_data=new JPanel(new GridLayout(1,1));
        
        
        String aname[]={"DATE","PRODUCTS DESCRIPTION","HSN","BATCH","QTY","GROSS AMT","DISC %","DISC AMT","GST %","GST AMOUNT","NET AMOUNT \u20B9"};
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
                float gst_amt=(gross_amount*0)/100;
                float disc_amt=(gross_amount*Float.parseFloat(rset.getString("disc")))/100;
                float net_amt=(gross_amount-disc_amt)+gst_amt;
                Date d;
                d=(rset.getDate("retail_bill_date"));
                Formatter fm3=new Formatter();
                fm3.format("%td-%tm-%tY",d,d,d);
                String date1=fm3.toString();
                tableModel.addRow(new Object[]{" "+date1+" "," "+rset.getString("product_name")+" "," "+rset.getString("hsn_code")+" "," "+rset.getString("batch")+" "," "+rset.getString("quintity")+" "," "+String.format("%.02f",Float.parseFloat(gross_amount+""))+"  ","  "+String.format("%.02f",Float.parseFloat(rset.getString("disc")))+" "," "+String.format("%.02f",Float.parseFloat(disc_amt+""))+" "," "+"0.00"+"  "," "+"0.00"+"  ",""+String.format("%.02f",Float.parseFloat(net_amt+""))+""});
                
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
