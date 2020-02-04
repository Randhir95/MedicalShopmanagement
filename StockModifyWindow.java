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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.BorderFactory;
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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import static komalhealthcare.KModifyWindow.cust_name;

/**
 *
 * @author RANDHIR KUMAR
 */
public class StockModifyWindow extends JDialog{
    private JLabel lsumamt,lgstamt,ltamt;
    private JTextField tbillno,tbilldate,supplier_name,tdlno,temail,taddr,tmob,tdisc,tpacking,mfg,tbatchno,texp,tmrp,tqty,tqtyfree,tprate,tsrate,mfg_name,product_name,gst_no;
    private JTable table;
    DefaultTableModel tableModel;
    private JButton badds,bdelete,bprint,bclose,breset,supp_add_btn;
    private String data[];
    private int old_qty=0,old_free_qty;
    private String rbill="",a_batchno="",stock_batchno="";
    private String pi,product_hsn,product_gst,p_hsn,p_batch,p_batch_no,p_gst,invoice_no;
    private int selitem=0,avi_qty=0,sr_no;
    boolean flag=false;
    private float tamount=0,gst=0;
    private JDateChooser invoicedate;
    private Color c=new Color(194,124,232);
    ArrayList<String> mfg_id=new ArrayList<>();
    ArrayList<String> mfg_items=new ArrayList<>();
    StockModifyWindow self; 
    
    public StockModifyWindow(String[] smw,String bil)
    {
        super(MDIMainWindow.self,"");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(Stock.DISPOSE_ON_CLOSE);
        self=this;
        StaticMember.setLocation(self);
        StaticMember.setSize(this);
        invoice_no=bil;
        data=smw;
        invoicedate=StaticMember.MyDateChooser(" Choose Invoice Date", " INVOICE DATE",0);
        badds=StaticMember.AddButton("/images/plus.png",40,40,"Click On Button To Add Recond In Table");
        StaticMember.lookAndFeel();
        this.addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent we)
         {
             StockModifyWindow.this.dispose();
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
        mreset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                preset();
                empaty();
            }        });
        msave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                //saveInvoice(invoice_no);
            }        });
        sel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new MSelectAddProduct(StockModifyWindow.this.self).setVisible(true);
                tpacking.requestFocusInWindow();
            }        });
        mi.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JInternalFrame AddManufacture=new JInternalFrame("",true,true);
                AddManufacture anm=new AddManufacture();
                MDIMainWindow.desktop.add(anm);
                anm.setVisible(true);
                mi.getModel();
                anm.setResizable(false);
            }        });
        si.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                JInternalFrame AddSupplier=new JInternalFrame("",true,true);
                AddSupplier ans=new AddSupplier();
                MDIMainWindow.desktop.add(ans);
                ans.setVisible(true);
                si.getModel();
                ans.setResizable(false);
            }        });
        
        
        JPanel main_body_panel=new JPanel(new BorderLayout());
        
        JLabel h=new JLabel(" STOCK MODIFY ",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        //Create labels
        JLabel sumamt=new JLabel("SUM AMOUNT : ",JLabel.RIGHT);
        sumamt.setFont(StaticMember.labelFont);
        JLabel gstamt=new JLabel("GST AMOUNT : ",JLabel.RIGHT);
        gstamt.setFont(StaticMember.labelFont);
        JLabel totalamt=new JLabel("TOTAL AMOUNT : ",JLabel.RIGHT);
        totalamt.setFont(StaticMember.labelFont);
        lsumamt=new JLabel("",JLabel.RIGHT);
        lsumamt.setFont(StaticMember.labelFont);
        lgstamt=new JLabel("",JLabel.RIGHT);
        lgstamt.setFont(StaticMember.labelFont);
        ltamt=new JLabel("",JLabel.RIGHT);
        ltamt.setFont(StaticMember.labelFont);
        //Create Textfields
        tbillno=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"Enter Bill No"," INVOICE NO");
        gst_no= StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,false," Enter GST No.","GST NO.");
        tdlno= StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"DL No.","DL NO.");
        supplier_name=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,false," Enter Supplier Name"," SUPPLIER NAME");    
        //StaticMember.setToSupplier(supplier_name, supplier_id, supplier_items);
        taddr=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Supplier Address"," ADDRESS");
        tmob=StaticMember.MyInputBox("",12,StaticMember.INTEGER_TYPE,false," Supplier Mobile No."," MOB NO.");
        temail=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Supplier Email Id"," EMAIL ID");
        product_name= StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,false," Product Name"," PRODUCTS NAME");
        tpacking=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Packing"," PACKING");
        tbatchno=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Batch No"," BATCH NO");
        texp=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE," Expairy"," EXP");
        tdisc=StaticMember.MyInputBox("",40,StaticMember.FLOAT_TYPE," Discount %"," DISC %");
        tmrp=StaticMember.MyInputBox("",40,StaticMember.FLOAT_TYPE," MRP"," MRP");
        tqty=StaticMember.MyInputBox("",5,StaticMember.INTEGER_TYPE," QTY"," QTY");
        tqtyfree=StaticMember.MyInputBox("",5,StaticMember.INTEGER_TYPE," FREE"," FREE");
        tprate=StaticMember.MyInputBox("",40,StaticMember.FLOAT_TYPE," P RATE"," P RATE");
        tsrate=StaticMember.MyInputBox("",40,StaticMember.FLOAT_TYPE," S RATE"," S RATE");
        mfg_name =StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Manufacture"," MANUFACTURE");
        StaticMember.setToManufacture(mfg_name, mfg_id, mfg_items);
        bclose=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        breset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        bprint=StaticMember.MyButton("PRINT","Click On Button To Print The Record");
        
        JLabel l2=new JLabel("PRODUCT DESCREPTION",JLabel.CENTER);
        l2.setFont(StaticMember.HEAD_FONT);
        l2.setForeground(Color.WHITE);
        l2.setOpaque(true);
        l2.setBackground(Color.BLUE);
        l2.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        l2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE,1, true),""));
        
        // Create Table
        String data1[][]=new String[0][];
        String name[]={"PRODUCT NAME","HSN","PAKING","MAKE","BATCH NO","GST %","DIS %","QTY","FREE","EXP","MRP","P RATE","S RATE","AMOUNT"};
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
        column=table.getColumnModel().getColumn(0);column.setPreferredWidth(210);
        column=table.getColumnModel().getColumn(1);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(2);column.setPreferredWidth(70);
        column=table.getColumnModel().getColumn(3);column.setPreferredWidth(70);
        column=table.getColumnModel().getColumn(4);column.setPreferredWidth(70);
        column=table.getColumnModel().getColumn(5);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(6);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(7);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(8);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(9);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(10);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(11);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(13);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(12);column.setPreferredWidth(30);
        
        JScrollPane scr=new JScrollPane(table);
        
        JPanel mainPanel=new JPanel(new BorderLayout());
        JPanel mainbilldetailPanel=new JPanel(new BorderLayout());
        JPanel mainbilldetailPanel1=new JPanel(new BorderLayout());
        JPanel mainbilldetailPanel2=new JPanel(new BorderLayout());
        JPanel labelPanel=new JPanel(new BorderLayout());
        JPanel productDetailPanel=new JPanel(new BorderLayout());
        JPanel billdetailPanel=new JPanel(new BorderLayout());
        JPanel bdetail1GridPanel=new JPanel(new GridLayout(2,1,5,5));
        JPanel bdetail1=new JPanel(new GridLayout(1,3,10,10));
        JPanel bdetail2=new JPanel(new GridLayout(1,4,10,10));
        JPanel bdetail3=new JPanel(new GridLayout(1,4,2,2));
        JPanel bdetailBorderPanel=new JPanel(new BorderLayout());
        JPanel packing_makePanel=new JPanel(new GridLayout(1,2,2,2));
        JPanel batch_qty_expPanel=new JPanel(new GridLayout(1,3,2,2));
        JPanel mrp_prate_sratePanel=new JPanel(new GridLayout(1,3,2,2));
        JPanel qty_freePanel=new JPanel(new GridLayout(1,2,2,2));
        JPanel dis_expPanel=new JPanel(new GridLayout(1,2,2,2));
        JPanel bill_datePanel=new JPanel(new GridLayout(1,2,10,10));
        JPanel supplierPanel=new JPanel(new BorderLayout());
        JPanel amountPanel=new JPanel(new GridLayout(1,4));
        JPanel amtPanel=new JPanel(new GridLayout(3,2,5,5));
        JPanel sumamt_panel=new JPanel(new BorderLayout());
        JPanel gstamt_panel=new JPanel(new BorderLayout());
        JPanel totalamt_panel=new JPanel(new BorderLayout());
        JPanel btnPanel=new JPanel(new GridLayout(1,8,10,10));
        
        this.add(mainPanel,BorderLayout.CENTER);this.add(new JLabel("      "),BorderLayout.SOUTH);
        this.add(new JLabel("   "),BorderLayout.EAST);this.add(new JLabel("   "),BorderLayout.WEST);
        mainbilldetailPanel.add(mainbilldetailPanel1,BorderLayout.CENTER);mainbilldetailPanel.add(mainbilldetailPanel2,BorderLayout.SOUTH);
        mainbilldetailPanel1.add(bdetail1GridPanel,BorderLayout.CENTER);
        mainbilldetailPanel2.add(labelPanel,BorderLayout.NORTH);mainbilldetailPanel2.add(productDetailPanel,BorderLayout.CENTER);
        productDetailPanel.add(new JLabel(" "),BorderLayout.SOUTH);
        labelPanel.add(new JLabel(" "),BorderLayout.SOUTH);labelPanel.add(new JLabel(" "),BorderLayout.NORTH);
        labelPanel.add(l2,BorderLayout.CENTER);productDetailPanel.add(bdetailBorderPanel,BorderLayout.CENTER);
        mainPanel.add(billdetailPanel,BorderLayout.CENTER);mainPanel.add(new JLabel("   "),BorderLayout.EAST);
        mainPanel.add(new JLabel("   "),BorderLayout.WEST);mainPanel.add(new JLabel("      "),BorderLayout.NORTH);
        mainPanel.add(btnPanel,BorderLayout.SOUTH);
        billdetailPanel.add(mainbilldetailPanel,BorderLayout.NORTH);billdetailPanel.add(scr,BorderLayout.CENTER);
        billdetailPanel.add(amountPanel,BorderLayout.SOUTH);
        bdetail1GridPanel.add(bdetail1);bdetail1GridPanel.add(bdetail2);
        supplierPanel.add(supplier_name,BorderLayout.CENTER);
        bill_datePanel.add(invoicedate);bill_datePanel.add(tbillno);
        bdetail1.add(bill_datePanel);bdetail1.add(supplierPanel);bdetail1.add(taddr);
        bdetail2.add(temail);bdetail2.add(tmob);bdetail2.add(tdlno);bdetail2.add(gst_no);
        bdetailBorderPanel.add(bdetail3,BorderLayout.CENTER);bdetailBorderPanel.add(badds,BorderLayout.EAST);
        bdetail3.add(product_name);bdetail3.add(packing_makePanel);bdetail3.add(batch_qty_expPanel);bdetail3.add(mrp_prate_sratePanel);
        packing_makePanel.add(tpacking);packing_makePanel.add(mfg_name);
        batch_qty_expPanel.add(tbatchno);batch_qty_expPanel.add(qty_freePanel);batch_qty_expPanel.add(dis_expPanel);
        mrp_prate_sratePanel.add(tmrp);mrp_prate_sratePanel.add(tprate);mrp_prate_sratePanel.add(tsrate);
        qty_freePanel.add(texp);qty_freePanel.add(tqty);
        dis_expPanel.add(tqtyfree);dis_expPanel.add(tdisc);
        amountPanel.add(new JLabel(" "));amountPanel.add(new JLabel(" "));amountPanel.add(new JLabel(" "));amountPanel.add(amtPanel);
        amtPanel.add(sumamt);amtPanel.add(sumamt_panel);
        amtPanel.add(gstamt);amtPanel.add(gstamt_panel);
        amtPanel.add(totalamt);amtPanel.add(totalamt_panel);
        sumamt_panel.add(lsumamt,BorderLayout.CENTER);sumamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        gstamt_panel.add(lgstamt,BorderLayout.CENTER);gstamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        totalamt_panel.add(ltamt,BorderLayout.CENTER);totalamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        btnPanel.add(new JLabel(" "));btnPanel.add(new JLabel(" "));btnPanel.add(new JLabel(" "));btnPanel.add(new JLabel(" "));
        btnPanel.add(new JLabel(" "));btnPanel.add(bprint);btnPanel.add(breset);btnPanel.add(bclose);
        
        bclose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_STOCK=false;
                StockModifyWindow.this.dispose();
            }
        });
        bclose.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    StaticMember.ADD_STOCK=false;
                    StockModifyWindow.this.dispose();
                }
            }});
        
        breset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reset();
                preset();
            }
        });
        breset.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                     reset();
                    preset();
            }}});
        bprint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.printStockBill(tbillno.getText().trim());
            }
        });
        bprint.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printStockBill(tbillno.getText().trim());
            }});
        
        badds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveInvoice(invoice_no);
                preset();
                empaty();
                setBillDataInTable(invoice_no);
                
        }});
        badds.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    saveInvoice(invoice_no);
                    preset();
                    empaty();
                    setBillDataInTable(invoice_no);
            }}});
        
         
        
        product_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {   
                    try{
                            new MSelectAddProduct(StockModifyWindow.this.self).setVisible(true);
                        }catch(Exception ex)
                        {
                            JOptionPane.showMessageDialog(StockModifyWindow.this, ex.toString(), "OOPs!", JOptionPane.ERROR_MESSAGE);
                        }
                   tpacking.requestFocusInWindow();
            }}});
        tpacking.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
               if(e.getKeyChar()=='\n')
                {
                    if(tpacking.getText().equals(""))
                        tpacking.requestFocusInWindow();
                    else
                         mfg_name.requestFocusInWindow();
                }
            }});
        tpacking.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                tpacking.setSelectionStart(0);
                tpacking.setSelectionEnd(tpacking.getText().length());
                tpacking.setSelectionColor(c);
                tpacking.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
        
        mfg_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(mfg_name.getText().equals(""))
                        mfg_name.requestFocusInWindow();
                    else
                        tbatchno.requestFocusInWindow();
            }}});
        tbatchno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tbatchno.getText().equals(""))
                        tbatchno.requestFocusInWindow();
                    else
                        texp.requestFocusInWindow();
                }
            }});
        tbatchno.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                tbatchno.setSelectionStart(0);
                tbatchno.setSelectionEnd(tbatchno.getText().length());
                tbatchno.setSelectionColor(c);
                tbatchno.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
        
        texp.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                texp.setSelectionStart(0);
                texp.setSelectionEnd(texp.getText().length());
                texp.setSelectionColor(c);
                texp.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
        texp.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(texp.getText().equals(""))
                        texp.requestFocusInWindow();
                    else
                        tqty.requestFocusInWindow();
                }
            }});
        tqty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tqty.getText().equals(""))
                        tqty.requestFocusInWindow();
                    else
                        tqtyfree.requestFocusInWindow();
                }
            }});
        tqty.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                tqty.setSelectionStart(0);
                tqty.setSelectionEnd(tqty.getText().length());
                tqty.setSelectionColor(c);
                tqty.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
        
        tqtyfree.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tqtyfree.getText().equals(""))
                        tqtyfree.requestFocusInWindow();
                    else
                        tdisc.requestFocusInWindow();
                }
            }});
        tqtyfree.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                tqtyfree.setSelectionStart(0);
                tqtyfree.setSelectionEnd(tqty.getText().length());
                tqtyfree.setSelectionColor(c);
                tqtyfree.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
        
        tmrp.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tmrp.getText().equals(""))
                        tmrp.requestFocusInWindow();
                    else
                        tprate.requestFocusInWindow();
                }
            }});
        tmrp.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                tmrp.setSelectionStart(0);
                tmrp.setSelectionEnd(tmrp.getText().length());
                tmrp.setSelectionColor(c);
                tmrp.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
        tprate.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tprate.getText().equals(""))
                        tprate.requestFocusInWindow();
                    else
                        tsrate.requestFocusInWindow();
                }
            }});
        tprate.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                tprate.setSelectionStart(0);
                tprate.setSelectionEnd(tprate.getText().length());
                tprate.setSelectionColor(c);
                tprate.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) { 
                float psrate=Float.parseFloat(tprate.getText());
                float r=psrate*40/100;
                float sr=psrate+r;
                Formatter frate=new Formatter(); 
                frate.format("%.2f",sr);
                tsrate.setText(frate+"");
            } });
        tsrate.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tsrate.getText().equals(""))
                        tsrate.requestFocusInWindow();
                    else
                    {
                        if(flag==true)
                        {
                            updateStockBillAndAvialableItem(invoice_no,sr_no,p_batch_no);
                            empaty();
                            setBillDataInTable(invoice_no);
                            product_name.requestFocusInWindow();
                        }
                        else
                            badds.requestFocusInWindow();
                        
                    }
                    //badds.requestFocusInWindow();
                }
            }});
         tsrate.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                tsrate.setSelectionStart(0);
                tsrate.setSelectionEnd(tsrate.getText().length());
                tsrate.setSelectionColor(c);
                tsrate.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
        
        tdisc.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tdisc.getText().equals(""))
                    {
                        tdisc.setText("0.00");
                        tmrp.requestFocusInWindow();
                    }
                    else
                        tmrp.requestFocusInWindow();
                }
            }});
        
        
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2)
                {
                    if(JOptionPane.showConfirmDialog(StockModifyWindow.this,"Are You Sure To Update Row ?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    {   
                        int sel_row=table.getSelectedRow();
                        if(sel_row!=-1)
                        {
                            flag=true;
                            try
                            {
                                float t_amount=Float.parseFloat(table.getValueAt(sel_row,13).toString());
                                float disc=0,d,pgstamt;
                                gst=Float.parseFloat(table.getValueAt(sel_row, 5).toString().trim());
                                d=Float.parseFloat(table.getValueAt(sel_row, 6).toString().trim());
                                pgstamt=t_amount*gst/100;
                                disc=t_amount*d/100; 
                                String sum_amt_str=lsumamt.getText().trim();
                                String total_amt_str=ltamt.getText().trim();
                                String gst_amt_str=lgstamt.getText().trim();
                                lsumamt.setText(String.format("%.02f",Float.parseFloat(sum_amt_str)-t_amount));
                                sumamount=sumamount-t_amount;
                                lgstamt.setText(String.format("%.02f",Float.parseFloat(gst_amt_str)-pgstamt));
                                sumgst=sumgst-pgstamt;
                                ltamt.setText(String.format("%.02f",Float.parseFloat(total_amt_str)-(t_amount+pgstamt)));
                                totalsum=totalsum-(t_amount+pgstamt); 
                                old_qty=Integer.parseInt(table.getValueAt(sel_row, 7).toString().trim());
                                old_free_qty=Integer.parseInt(table.getValueAt(sel_row, 8).toString().trim());
                                product_name.setText(table.getValueAt(sel_row, 0).toString().trim());
                                tmrp.setText(table.getValueAt(sel_row, 10).toString().trim());
                                tprate.setText(table.getValueAt(sel_row, 11).toString().trim());
                                tsrate.setText(table.getValueAt(sel_row, 12).toString().trim());
                                tdisc.setText(table.getValueAt(sel_row, 6).toString().trim());
                                tqty.setText(table.getValueAt(sel_row, 7).toString().trim());
                                tqtyfree.setText(table.getValueAt(sel_row, 8).toString().trim());
                                tpacking.setText(table.getValueAt(sel_row, 2).toString().trim());
                                p_batch_no=table.getValueAt(sel_row, 4).toString().trim();
                                tbatchno.setText(table.getValueAt(sel_row, 4).toString().trim());
                                mfg_name.setText(table.getValueAt(sel_row, 3).toString().trim());
                                texp.setText(table.getValueAt(sel_row, 9).toString().trim());
                                ResultSet pr=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+p_batch_no+"'");
                                pr.next();
                                a_batchno=pr.getString("batch_no");
                                avi_qty=Integer.parseInt(pr.getString("quintity"));
                                
                                ResultSet brset=StaticMember.con.createStatement().executeQuery("select * from stock_item where inv_no like'"+invoice_no+"' and product_id ='"+pr.getInt("product_id")+"'");
                                brset.next();
                                sr_no=brset.getInt("sr_no");
                                JOptionPane.showMessageDialog(null, sr_no);
                                StaticMember.setToManufacture(mfg_name, mfg_id, mfg_items);
                                selitem--;
                            }catch(NumberFormatException ex){}
                            catch(SQLException ex){}
                            tableModel.removeRow(sel_row);
                            tpacking.requestFocusInWindow();
                        }
                    }
                }
            }});
        
        
        setBillDataInTable(invoice_no);
    }
    
    public void preset()
     {
        product_name.setText("");
        tpacking.setText("");
        tmrp.setText("");
        tprate.setText("");
        tsrate.setText("");
        tpacking.setText("");
        tbatchno.setText("");
        tqty.setText("");
        tqtyfree.setText("");
        texp.setText("");
        mfg_name.setText("");
        //mfg_items.clear();
        //mfg_id.clear();
        tdisc.setText("");
        //StaticMember.setToManufacture(mfg_name,mfg_id,mfg_items);
        //setToManufacture();
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
        pi="";
        selitem=0;
    }
    public void reset()
    {
        tbillno.setText("");
        supplier_name.setText("");
        taddr.setText("");
        tmob.setText("");
        empaty();
    }
    //set data in text field an Label
    public void msetItemsValue(String[] s)
    {
        product_name.setText((s[0]).toUpperCase());
        pi=s[1];
        product_hsn=s[2];
        product_gst=s[3];
        
    }
    //code to set data in table
    float sumamount=0,sumgst=0,totalsum=0;
    
    
    public void setBillDataInTable(String inv)
    {
        selitem=0;
        try
        {
            ResultSet b=StaticMember.con.createStatement().executeQuery("select * from invoice where inv_no like'"+inv+"'");
            b.next();
            ResultSet c=StaticMember.con.createStatement().executeQuery("select * from supplier where supplier_id like'"+b.getString("supplier_id")+"'");
            c.next();
            ((JTextField)invoicedate.getDateEditor().getUiComponent()).setText(b.getString("inv_date"));
            supplier_name.setText(c.getString("supplier_name").toUpperCase());
            taddr.setText(c.getString("supplier_address").toUpperCase());
            tdlno.setText(c.getString("supplier_dl").toUpperCase());
            temail.setText(c.getString("supplier_email").toUpperCase());
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
                selitem++;
            }
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
                product_name.requestFocusInWindow();
                
                //selitem++;
        }catch(SQLException ex)
        {
             JOptionPane.showMessageDialog(StockModifyWindow.this, ex.getMessage(),"opps",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    //Add invoice in data base
    public void saveInvoice(String bill)
    {
        
        try
        {
            if(tbillno.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "Invoce No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
                tbillno.setText("");
                tbillno.requestFocusInWindow();
               return;
            }
             String date1  = ((JTextField)invoicedate.getDateEditor().getUiComponent()).getText();
             if(date1.equals(null))
            {
                JOptionPane.showMessageDialog(this, "Invoce Date Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
                invoicedate.setDate(null);
                invoicedate.requestFocusInWindow();
               return;
            }
            if(supplier_name.getText().equals(""))
            {
                JOptionPane.showMessageDialog(this, "Supplier Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
                supplier_name.setText("");
                supplier_name.requestFocusInWindow();
               return;
            }
            
            PreparedStatement ssmt=StaticMember.con.prepareStatement("insert into stock_item(sr_no,inv_no,product_id,batch_no,qty,disc,total_amtount,manifacture_id,free_qty,added_on,mrp,prate,srate,packing,exp) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            float prate=Float.parseFloat(tprate.getText());
            int q=Integer.parseInt(tqty.getText());
            float t_amt=prate*q;
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select max(sr_no) as sr_no  from stock_item");
            rset.next();
            int sr=rset.getInt("sr_no")+1;
            ssmt.setInt(1,sr);
            ssmt.setString(2,bill);
            ssmt.setString(3,pi);
            ssmt.setString(4,tbatchno.getText().trim());
            ssmt.setInt(5, q);
            ssmt.setFloat(6, Float.parseFloat(tdisc.getText()));
            ssmt.setFloat(7, t_amt);
            ssmt.setString(8,mfg_id.get(mfg_items.indexOf(mfg_name.getText())));
            ssmt.setFloat(9, Integer.parseInt(tqtyfree.getText()));
            ssmt.setString(10,StaticMember.getDate());
            ssmt.setFloat(11,Float.parseFloat(tmrp.getText()));
            ssmt.setFloat(12,Float.parseFloat(tprate.getText()));
            ssmt.setFloat(13,Float.parseFloat(tsrate.getText()));
            ssmt.setString(14,tpacking.getText());
            ssmt.setString(15,texp.getText());
            ssmt.execute();
            float tgst=t_amt*Float.parseFloat(product_gst)/100;
            float totalsumamt=Float.parseFloat(ltamt.getText())+t_amt+tgst;
            StaticMember.con.createStatement().execute("update invoice set total_amtount="+totalsumamt+" where inv_no like'"+bill+"'");
                
            PreparedStatement ssmt1=StaticMember.con.prepareStatement("insert into availableitem(product_id,packing,batch_no,quintity,expary_date,mrp,import_rate,sale_rate,added_on,manifacture_id) values(?,?,?,?,?,?,?,?,?,?)");
            int qq=Integer.parseInt(tqty.getText());
            int fq=Integer.parseInt(tqtyfree.getText());
            String batch=(tbatchno.getText());
            ResultSet b_rset=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batch+"'");
            if(b_rset.next())
            {
                int q2=b_rset.getInt("quintity");
                int q1=qq+q2+fq;
                StaticMember.con.createStatement().execute("update availableitem set quintity="+q1+" where batch_no like'"+batch+"'");
                //continue;
                
            }
            ssmt1.setString(1,pi);
            ssmt1.setString(2,tpacking.getText());
            ssmt1.setString(3,tbatchno.getText());
            ssmt1.setInt(4, Integer.parseInt(tqty.getText())+Integer.parseInt(tqtyfree.getText()));
            ssmt1.setString(5, texp.getText());
            ssmt1.setFloat(6, Float.parseFloat(tmrp.getText()));
            ssmt1.setFloat(7, Float.parseFloat(tprate.getText()));
            ssmt1.setFloat(8, Float.parseFloat(tsrate.getText()));
            ssmt1.setString(9, StaticMember.getDate());
            ssmt1.setString(10, mfg_id.get(mfg_items.indexOf(mfg_name.getText())));
            ssmt1.execute();
            
            JOptionPane.showMessageDialog(StockModifyWindow.this,"Bill Saved!",null,JOptionPane.INFORMATION_MESSAGE);
            empaty();
            MDIMainWindow.s_m_obj.allBill();
            product_name.requestFocusInWindow();
                
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(StockModifyWindow.this, ex.getMessage(),"Opps1",JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(StockModifyWindow.this, ex.getMessage(),"Opps",JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean checkProductsDetails()
    {
        if(product_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Product Name Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            product_name.requestFocusInWindow();return true;
        }
        if(tpacking.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Paking Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tpacking.requestFocusInWindow();return true;
        }
        if(mfg_name.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Manufacture Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            mfg_name.requestFocus();return true;
        }
        if(tbatchno.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Batch No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tbatchno.requestFocusInWindow();return true;
        }
       
        if(tqty.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Quintity Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tqty.requestFocusInWindow();return true;
        }
        if(texp.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Expairy Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            texp.requestFocusInWindow();return true;
        }
        if(tmrp.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "MRP Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tmrp.requestFocusInWindow();return true;
        }
        if(tprate.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Purchege Rate Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tprate.requestFocusInWindow();return true;
        }
        if(tsrate.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Sale Rate Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tsrate.requestFocusInWindow();return true;
        }
        return false;
    }
    
    public void updateStockBillAndAvialableItem(String bill,int srno,String batchno)
    {
        try
        {
            PreparedStatement stockstmt=StaticMember.con.prepareStatement("Update stock_item set total_amtount=?,manifacture_id=?,free_qty=?,batch_no=?,qty=?,disc=?,mrp=?,prate=?,srate=?,packing=?,exp=? where sr_no=?");
            JOptionPane.showMessageDialog(null, "hello");
            float prate=Float.parseFloat(tprate.getText());
            float srate=Float.parseFloat(tsrate.getText());
            float disc=Float.parseFloat(tdisc.getText());
            int newqty=Integer.parseInt(tqty.getText());
            float totalamt=prate*newqty;
            
            stockstmt.setFloat(1, totalamt);
            stockstmt.setString(2, mfg_id.get(mfg_items.indexOf(mfg_name.getText())));
            stockstmt.setInt(3, Integer.parseInt(tqtyfree.getText()));
            stockstmt.setString(4, tbatchno.getText());
            stockstmt.setInt(5, Integer.parseInt(tqty.getText()));
            stockstmt.setFloat(6, Float.parseFloat(tdisc.getText()));
            stockstmt.setFloat(7, Float.parseFloat(tmrp.getText()));
            stockstmt.setFloat(8, Float.parseFloat(tprate.getText()));
            stockstmt.setFloat(9, Float.parseFloat(tsrate.getText()));
            stockstmt.setString(10, tpacking.getText());
            stockstmt.setString(11, texp.getText());
            stockstmt.setInt(12,srno);
            stockstmt.executeUpdate();
            //totalsum
            float tamt=prate*Integer.parseInt(tqty.getText());
            float tgst=tamt*gst/100;
            float totalsumamt=Float.parseFloat(ltamt.getText())+tamt+tgst;
            StaticMember.con.createStatement().execute("update invoice set total_amtount="+totalsumamt+" where inv_no like'"+bill+"'");
            PreparedStatement avissmt=StaticMember.con.prepareStatement("Update availableitem set import_rate=?,sale_rate=?,expary_date=?,mrp=?,manifacture_id=?,packing=?,batch_no=?,quintity=? where batch_no=?");
            avissmt.setFloat(1,prate);
            avissmt.setFloat(2, srate);
            avissmt.setString(3, texp.getText());
            avissmt.setFloat(4, Float.parseFloat(tmrp.getText()));
            avissmt.setString(5, mfg_id.get(mfg_items.indexOf(mfg_name.getText())));
            avissmt.setString(6, tpacking.getText());
            avissmt.setString(7, tbatchno.getText());
            avissmt.setInt(8, Integer.parseInt(tqty.getText())+Integer.parseInt(tqtyfree.getText()));
            avissmt.setString(9,batchno);
            avissmt.execute();
            flag=false;
            sr_no=0;a_batchno="";preset();
            JOptionPane.showMessageDialog(null, "Update Succecfull","wow",JOptionPane.INFORMATION_MESSAGE);
            MDIMainWindow.s_m_obj.allBill();
            product_name.requestFocusInWindow();
            
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"opps2222",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setToManufacture()
    {
        try
        {
            mfg_id.clear();
            mfg_items.clear();
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct * FROM manifacture");
            while(rset.next())
            {
                mfg_id.add(rset.getString("manifacture_id"));
                mfg_items.add((rset.getString("manifacture_name")).toUpperCase());
            }
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(mfg_name, mfg_items,false);
    }
    
    
}
