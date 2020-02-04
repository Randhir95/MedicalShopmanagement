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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author RANDHIR KUMAR
 */
public class ViewStockWindow extends JDialog{
    private JLabel tbillno,tbilldate,supplier_name,tdlno,taddr,tmob,gst_no,lsumamt,lgstamt,ltamt,temail,invoicedate;
    private JTable table;
    DefaultTableModel tableModel;
    private JButton print,close,reset;
    private String invoice_no,mfgid;
    private int selitem=0;
    private Color c=new Color(194,124,232);
    ViewStockWindow self; 
    public ViewStockWindow(String bil)
    {
        super(MDIMainWindow.self,"Stock Modify");
        StaticMember.setSize(this);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(Stock.DISPOSE_ON_CLOSE);
        StaticMember.setLocation(this);
        self=this;
        invoice_no=bil;
        
        this.addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent we)
         {
             ViewStockWindow.this.dispose();
         }
        });
        
        JMenuBar bar=new JMenuBar();
        JMenu m1=new JMenu("H");
        JMenuItem mi=new JMenuItem("Create Manufacture",'M');
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,KeyEvent.CTRL_MASK));
        JMenuItem si=new JMenuItem("Create Supplier",'S');
        si.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
        JMenuItem msave=new JMenuItem("Save",'A');
        msave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_MASK));
        JMenuItem mreset=new JMenuItem("Reset",'R');
        mreset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
        JMenuItem sel=new JMenuItem("Sel",'F');
        sel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,KeyEvent.ALT_MASK));
        m1.add(mi);
        m1.add(msave);
        m1.add(mreset);
        m1.add(sel);
        m1.add(si);
        bar.add(m1);
        this.add(bar);
        
        
        JPanel main_body_panel=new JPanel(new BorderLayout());
        
        JLabel h=new JLabel(" STOCK BILL ",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        //Create labels
        tbillno=StaticMember.MyJLabel("", " BILL NO.");
        invoicedate=StaticMember.MyJLabel("", " BILL DATE");
        gst_no=StaticMember.MyJLabel("", " GSTIN NO");
        tdlno=StaticMember.MyJLabel("", " DL NO");
        supplier_name=StaticMember.MyJLabel("", " SUPPLIER NAME");
        taddr=StaticMember.MyJLabel("", " ADDRESS");
        tmob=StaticMember.MyJLabel("", " MOBILE NO");
        temail=StaticMember.MyJLabel("", " EMAIL ID");
        
        lsumamt=new JLabel("".toUpperCase(),JLabel.RIGHT);
        lsumamt.setFont(StaticMember.labelFont1);
        lgstamt=new JLabel("".toUpperCase(),JLabel.RIGHT);
        lgstamt.setFont(StaticMember.labelFont);
        ltamt=new JLabel("".toUpperCase(),JLabel.RIGHT);
        ltamt.setFont(StaticMember.labelFont);
        JLabel sumamt=new JLabel("SUM AMOUNT : ",JLabel.RIGHT);
        sumamt.setFont(StaticMember.labelFont);
        JLabel gstamt=new JLabel("GST AMOUNT : ",JLabel.RIGHT);
        gstamt.setFont(StaticMember.labelFont);
        JLabel totalamt=new JLabel("TOTAL AMOUNT : ",JLabel.RIGHT);
        totalamt.setFont(StaticMember.labelFont);
        
        
        JLabel l2=new JLabel("PRODUCT DESCREPTION",JLabel.CENTER);
        l2.setFont(StaticMember.HEAD_FONT);
        l2.setForeground(Color.WHITE);
        l2.setOpaque(true);
        l2.setBackground(Color.BLUE);
        l2.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        l2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE,1, true),""));
        
        
       // Create Table
        String data1[][]=new String[0][];
        String name[]={"PRODUCT NAME","HSN","PAKING","MAKE","BATCH NO","GST %","DISC %","QTY","FREE","EXP","MRP","P RATE","S RATE","AMOUNT"};
        table=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        tableModel.setDataVector(data1,name);
        table.setModel(tableModel);//set table model in table
        table.getTableHeader().setResizingAllowed(false);//stop resizing cell
        table.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        //table.setEnabled(false);
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
        
        table.getColumnModel().getColumn(13).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(12).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        
        TableColumn column=null;
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(210);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(70);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(70);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(70);
        column=table.getColumnModel().getColumn(5);
        column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(6);
        column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(7);
        column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(8);
        column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(9);
        column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(10);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(11);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(13);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(12);
        column.setPreferredWidth(30);
        
        JScrollPane scr=new JScrollPane(table);
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        print=StaticMember.MyButton("PRINT","Click On Button To Print The Record");
        
        // Create Panel and Add on panel  
        JPanel main_panel=new JPanel(new BorderLayout());
        JPanel main_bill_panel=new JPanel(new BorderLayout());
        JPanel table_panel=new JPanel(new BorderLayout());
        JPanel main_amt_panel=new JPanel(new BorderLayout());
        JPanel main_bdetail1=new JPanel(new GridLayout(2,1,10,10));
        JPanel bdetail1=new JPanel(new GridLayout(1,3,10,10));
        JPanel bdetail2=new JPanel(new GridLayout(1,4,10,10));
        JPanel bill_date_panel=new JPanel(new GridLayout(1,2,10,10));
        JPanel amount_panel=new JPanel(new GridLayout(1,4,10,10));
        JPanel sadata_panel=new JPanel(new GridLayout(3,2,10,10));
        JPanel btn_panel=new JPanel(new GridLayout(1,9,10,10));
        JPanel sumamt_panel=new JPanel(new BorderLayout());
        JPanel gstamt_panel=new JPanel(new BorderLayout());
        JPanel totalamt_panel=new JPanel(new BorderLayout());
        
        this.add(main_panel,BorderLayout.CENTER);
        main_panel.add(new JLabel("    "),BorderLayout.EAST);main_panel.add(new JLabel("    "),BorderLayout.NORTH);
        main_panel.add(new JLabel("    "),BorderLayout.WEST);main_panel.add(new JLabel("    "),BorderLayout.SOUTH);
        main_panel.add(main_bill_panel,BorderLayout.CENTER);
        main_bill_panel.add(main_bdetail1,BorderLayout.NORTH);main_bill_panel.add(table_panel,BorderLayout.CENTER);
        main_bill_panel.add(main_amt_panel,BorderLayout.SOUTH);
        main_bdetail1.add(bdetail1);main_bdetail1.add(bdetail2);
        bdetail1.add(bill_date_panel);bdetail1.add(supplier_name);bdetail1.add(taddr);
        bdetail2.add(temail);bdetail2.add(tmob);bdetail2.add(gst_no);bdetail2.add(tdlno);
        bill_date_panel.add(invoicedate);bill_date_panel.add(tbillno);
        table_panel.add(l2,BorderLayout.NORTH);table_panel.add(scr,BorderLayout.CENTER);
        main_amt_panel.add(amount_panel,BorderLayout.CENTER);main_amt_panel.add(btn_panel,BorderLayout.SOUTH);
        amount_panel.add(new JLabel("   "));amount_panel.add(new JLabel("   "));amount_panel.add(new JLabel("   "));
        amount_panel.add(sadata_panel);
        sadata_panel.add(sumamt);sadata_panel.add(sumamt_panel);sadata_panel.add(gstamt);sadata_panel.add(gstamt_panel);
        sadata_panel.add(totalamt);sadata_panel.add(totalamt_panel);
        sumamt_panel.add(lsumamt,BorderLayout.CENTER);sumamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        gstamt_panel.add(lgstamt,BorderLayout.CENTER);gstamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        totalamt_panel.add(ltamt,BorderLayout.CENTER);totalamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        btn_panel.add(new JLabel("   "));btn_panel.add(new JLabel("   "));btn_panel.add(new JLabel("   "));
        btn_panel.add(new JLabel("   "));btn_panel.add(new JLabel("   "));btn_panel.add(new JLabel("   "));
        btn_panel.add(print);btn_panel.add(reset);btn_panel.add(close);
        
        
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_STOCK=false;
                ViewStockWindow.this.dispose();
            }
        });
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    StaticMember.ADD_STOCK=false;
                    ViewStockWindow.this.dispose();
                }
            }});
        
        
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.printStockBill(invoice_no);
            }
        });
       print.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printStockBill(invoice_no);
            }});
       
        
        setBillDataInTable(invoice_no);
    }
    
    public void setBillDataInTable(String inv)
    {
        selitem=0;
        try
        {//bdate,custname,address,dlno,date,bilno,mno
            ResultSet b=StaticMember.con.createStatement().executeQuery("select * from invoice where inv_no like'"+inv+"'");
            b.next();
            ResultSet c=StaticMember.con.createStatement().executeQuery("select * from supplier where supplier_id like'"+b.getString("supplier_id")+"'");
            c.next();
            invoicedate.setText(b.getString("inv_date"));
            supplier_name.setText(c.getString("supplier_name").toUpperCase());
            taddr.setText(c.getString("supplier_address").toUpperCase());
            //cust_dl_no.setText(c.getString("supplier_dl").toUpperCase());
            tbillno.setText(inv);
            tmob.setText(c.getString("supplier_mob"));
            gst_no.setText(c.getString("supplier_gst"));
            
            ResultSet s=StaticMember.con.createStatement().executeQuery("select * from stock_item where inv_no like'"+inv+"' order by product_id");
            int tablerow=0;
            float subamt=0,disamt=0,net_amt=0,cgst_amount=0,sgst_amount=0;
            while(s.next())
            {
                ResultSet p=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+s.getString("product_id")+"'");
                p.next();
                ResultSet m=StaticMember.con.createStatement().executeQuery("select * from manifacture where manifacture_id like'"+s.getString("manifacture_id")+"'");
                m.next();
                /*ResultSet kavi=StaticMember.con.createStatement().executeQuery("select * from  kavailableitem where product_id like'"+s.getString("product_id")+"'");
                kavi.next();
                ResultSet avi=StaticMember.con.createStatement().executeQuery("select * from  availableitem where product_id like'"+s.getString("product_id")+"'");
                avi.next();*/
                ResultSet t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+p.getString("type_id")+"'");
                t.next();
                String product_name=p.getString("product_name")+" "+t.getString("type_name");
                float prate=Float.parseFloat(s.getString("prate"));
                float pqty=Float.parseFloat(s.getString("qty"));
                float disc=Float.parseFloat(s.getString("disc"));
                net_amt=prate-disc;
                float t_amount=prate*pqty;
                float gst=Float.parseFloat(p.getString("product_gst"));
                float sgst=gst/2;
                tableModel.addRow(new Object[]{" "+product_name+" "," "+p.getString("product_hsn_code")+"  "," "+s.getString("packing")+" ",m.getString("manifacture_name")," "+s.getString("batch_no")+" ","  "+String.format("%.02f",Float.parseFloat(p.getString("product_gst")))+" "," "+String.format("%.02f",Float.parseFloat(s.getString("disc")))+"  "," "+s.getString("qty")+"  "," "+s.getString("free_qty")+"  "," "+s.getString("exp")+" "," "+String.format("%.02f",Float.parseFloat(s.getString("mrp")))+"  "," "+String.format("%.02f",Float.parseFloat(s.getString("prate")))+"  "," "+String.format("%.02f",Float.parseFloat(s.getString("srate")))+"  "," "+String.format("%.02f",Float.parseFloat(s.getString("total_amtount")))+" "});

                float amt=t_amount;
                cgst_amount+=amt*gst/100;
                disamt+=amt*disc/100;
                subamt+=amt;
                tablerow++;
            }
              //samount,vamount,gtamount,damount  
                Formatter fsa=new Formatter(); 
                fsa.format("%.2f",subamt);
                Formatter fda=new Formatter(); 
                fda.format("%.2f",disamt);
                Formatter fgta=new Formatter(); 
                subamt+=cgst_amount;
                fgta.format("%.2f",(subamt-disamt));
                sgst_amount=cgst_amount/2;
                Formatter fgst=new Formatter(); //vat Amount
                fgst.format("%.02f",cgst_amount);
                Formatter fcgst=new Formatter(); //vat Amount
                fcgst.format("%.02f",sgst_amount);
                Formatter fsgst=new Formatter(); //vat Amount
                fsgst.format("%.02f",sgst_amount);
                lsumamt.setText(fsa+"");
                lgstamt.setText(fgst+"");
                ltamt.setText(fgta+"");
                //cgst_amt.setText(fcgst+"");
                //sgst_amt.setText(fsgst+"");
                
        }catch(SQLException ex)
        {
             JOptionPane.showMessageDialog(ViewStockWindow.this, ex.getMessage(),"opps",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void empaty()
    {
        tbillno.setText("");
        supplier_name.setText("");
        gst_no.setText("");
        taddr.setText("");
        tdlno.setText("");
        tmob.setText("");
        lsumamt.setText("");
        lgstamt.setText("");
        ltamt.setText("");
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
        //pid=new String[30];
        selitem=0;
    }
    public void reset()
    {
        //tbillno,tbilldate,tcustomer,taddr,tmob,tpacking,ttax,tmake,tbatchno,texp,tmrp,tqty,tprate,tsrate,ttax2;
        tbillno.setText("");
        supplier_name.setText("");
        
        taddr.setText("");
        tmob.setText("");
        
        empaty();
        
        
        
    }
}
