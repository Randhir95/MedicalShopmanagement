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

/**
 *
 * @author Randhir
 */
public class Stock extends JInternalFrame{
    private JLabel lsumamt,lgstamt,ltamt;
    public JTextField tbillno,supplier_name,taddr,temail,tdlno,tmob,disc,tpacking,tbatchno,texp,tmrp,tqty,tqtyfree,tprate,tsrate,mfg_name,product_name,gst_no;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton badds,bdelete,bsave,bprint,bclose,breset,supp_add_btn;
    private String[] data;
    private String pi,product_hsn,product_gst,p_hsn,p_gst,rbill;
    private int selitem=0;
    private float tamount=0;
    private JDateChooser invoicedate;
    private Color c=new Color(194,124,232);
    private ArrayList<String> mfg_id=new ArrayList<>();
    private ArrayList<String> mfg_items=new ArrayList<>();
    private ArrayList<String> product_id=new ArrayList<>();
    private ArrayList<String> product_items=new ArrayList<>();
    ArrayList<String> supplier_id=new ArrayList<>();
    ArrayList<String> supplier_items=new ArrayList<>();
    
    Stock self;
    public Stock(String d[])
    {   
        super("",true,true);
        StaticMember.setSize(this);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(Stock.DISPOSE_ON_CLOSE);
        this.setLocation(0, 0);
        self=this;
        
        data=d;
        invoicedate=StaticMember.MyDateChooser(" Choose Invoice Date", " INVOICE DATE",0);
        badds=StaticMember.AddButton("/images/plus.png",40,40,"Click On Button To Add Recond In Table");
        supp_add_btn=StaticMember.AddButton("/images/plus.png",40,40,"Click On Button To Add More Supplier");
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.ADD_STOCK=false;
              }});
        
        
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
                saveInvoice();
            }        });
        sel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new SelectAddProduct(Stock.this.self).setVisible(true);
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
        
        
        JLabel h=new JLabel(" STOCK ENTRY ",JLabel.CENTER);
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
        tbillno=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE,"Enter Bill No"," INVOICE NO");
        gst_no= StaticMember.MyInputBox("",20,StaticMember.STRING_TYPE,false," Enter GST No.","GST NO.");
        tdlno= StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"DL No.","DL NO.");
        supplier_name=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Supplier Name"," SUPPLIER NAME");    
        StaticMember.setToSupplier(supplier_name, supplier_id, supplier_items);
        taddr=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Supplier Address"," ADDRESS");
        tmob=StaticMember.MyInputBox("",12,StaticMember.INTEGER_TYPE,false," Supplier Mobile No."," MOB NO.");
        temail=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false," Supplier Email Id"," EMAIL ID");
        product_name= StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,false," Product Name"," PRODUCTS NAME");
        tpacking=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Packing"," PACKING");
        tbatchno=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Batch No"," BATCH NO");
        texp=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE," Expairy"," EXP");
        disc=StaticMember.MyInputBox("",40,StaticMember.FLOAT_TYPE," Discount %"," DISC %");
        tmrp=StaticMember.MyInputBox("",40,StaticMember.FLOAT_TYPE," MRP"," MRP");
        tqty=StaticMember.MyInputBox("",5,StaticMember.INTEGER_TYPE," QTY"," QTY");
        tqtyfree=StaticMember.MyInputBox("",5,StaticMember.INTEGER_TYPE," FREE"," FREE");
        tprate=StaticMember.MyInputBox("",40,StaticMember.FLOAT_TYPE," P RATE"," P RATE");
        tsrate=StaticMember.MyInputBox("",40,StaticMember.FLOAT_TYPE," S RATE"," S RATE");
        mfg_name =StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE," Enter Manufacture"," MANUFACTURE");
        StaticMember.setToManufacture(mfg_name, mfg_id, mfg_items);
        bclose=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        breset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        bsave=StaticMember.MyButton("SAVE","Click On Button To Save The Record");
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
        JPanel batch_makePanel=new JPanel(new GridLayout(1,2,2,2));
        JPanel packing_qty_expPanel=new JPanel(new GridLayout(1,3,2,2));
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
        JPanel date_panel=new JPanel(new BorderLayout());
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
        supplierPanel.add(supplier_name,BorderLayout.CENTER);supplierPanel.add(supp_add_btn,BorderLayout.EAST);
        date_panel.add(invoicedate,BorderLayout.CENTER);date_panel.setBackground(StaticMember.L_BG_COLOR);date_panel.setOpaque(true);
        bill_datePanel.add(date_panel);bill_datePanel.add(tbillno);
        bdetail1.add(bill_datePanel);bdetail1.add(supplierPanel);bdetail1.add(taddr);
        bdetail2.add(temail);bdetail2.add(tmob);bdetail2.add(tdlno);bdetail2.add(gst_no);
        bdetailBorderPanel.add(bdetail3,BorderLayout.CENTER);bdetailBorderPanel.add(badds,BorderLayout.EAST);
        bdetailBorderPanel.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,50));
        bdetail3.add(product_name);bdetail3.add(batch_makePanel);bdetail3.add(packing_qty_expPanel);bdetail3.add(mrp_prate_sratePanel);
        batch_makePanel.add(tbatchno);batch_makePanel.add(mfg_name);
        packing_qty_expPanel.add(tpacking);packing_qty_expPanel.add(qty_freePanel);packing_qty_expPanel.add(dis_expPanel);
        mrp_prate_sratePanel.add(tmrp);mrp_prate_sratePanel.add(tprate);mrp_prate_sratePanel.add(tsrate);
        qty_freePanel.add(texp);qty_freePanel.add(tqty);
        dis_expPanel.add(tqtyfree);dis_expPanel.add(disc);
        amountPanel.add(new JLabel(" "));amountPanel.add(new JLabel(" "));amountPanel.add(new JLabel(" "));amountPanel.add(amtPanel);
        amtPanel.add(sumamt);amtPanel.add(sumamt_panel);
        amtPanel.add(gstamt);amtPanel.add(gstamt_panel);
        amtPanel.add(totalamt);amtPanel.add(totalamt_panel);
        sumamt_panel.add(lsumamt,BorderLayout.CENTER);sumamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        gstamt_panel.add(lgstamt,BorderLayout.CENTER);gstamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        totalamt_panel.add(ltamt,BorderLayout.CENTER);totalamt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
        btnPanel.add(new JLabel(" "));btnPanel.add(new JLabel(" "));btnPanel.add(new JLabel(" "));btnPanel.add(new JLabel(" "));
        btnPanel.add(bsave);btnPanel.add(bprint);btnPanel.add(breset);btnPanel.add(bclose);
        
        bclose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.ADD_STOCK=false;
                Stock.this.dispose();
            }
        });
        bclose.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    StaticMember.ADD_STOCK=false;
                    Stock.this.dispose();
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
                StaticMember.printStockBill(rbill);
            }
        });
        bprint.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printStockBill(rbill);
            }});
        bsave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveInvoice();
                reset();
            }
        });
        bsave.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                     saveInvoice();
                     reset();
                }
            }});
        
        badds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setdataInTable();
                preset();
                StaticMember.setToManufacture(mfg_name, mfg_id, mfg_items);
                    product_name.requestFocusInWindow();
            }
        });
        badds.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(checkProductsDetails())return;
                    setdataInTable();
                    preset();
                    StaticMember.setToManufacture(mfg_name, mfg_id, mfg_items);
                    product_name.requestFocusInWindow();
            }}});
        
        supp_add_btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JInternalFrame AddSupplier=new JInternalFrame("",true,true);
                AddSupplier ans=new AddSupplier();
                MDIMainWindow.desktop.add(ans);
                ans.setVisible(true);
                supp_add_btn.getModel();
                ans.setResizable(false);
            }
        });
        supp_add_btn.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    JInternalFrame AddSupplier=new JInternalFrame("",true,true);
                    AddSupplier ans=new AddSupplier();
                    MDIMainWindow.desktop.add(ans);
                    ans.setVisible(true);
                    supp_add_btn.getModel();
                    ans.setResizable(false);
                }
            }});
        product_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {   
                    try
                    {
                        new SelectAddProduct(Stock.this.self).setVisible(true);
                    }catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(Stock.this, ex.toString(), "OOPs!", JOptionPane.ERROR_MESSAGE);
                    }
                   tbatchno.requestFocusInWindow();
            }}});
        tbatchno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tbatchno.getText().equals(""))
                        tbatchno.requestFocusInWindow();
                    else
                        mfg_name.requestFocusInWindow();
                }
            }
            public void keyReleased(KeyEvent e){
                if(e.getKeyChar()=='\n')
                    fillData(tbatchno.getText().trim());
            }
        });
        tbatchno.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                tbatchno.setSelectionStart(0);
                tbatchno.setSelectionEnd(tbatchno.getText().length());
                tbatchno.setSelectionColor(c);
                tbatchno.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
        mfg_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(mfg_name.getText().equals(""))
                        mfg_name.requestFocusInWindow();
                    else
                        tpacking.requestFocusInWindow();
            }}});
        tpacking.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(tpacking.getText().equals(""))
                        tpacking.requestFocusInWindow();
                    else
                         texp.requestFocusInWindow();
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
        texp.addFocusListener(new FocusListener(){
            public void focusGained(FocusEvent e) {
                texp.setSelectionStart(0);
                texp.setSelectionEnd(texp.getText().length());
                texp.setSelectionColor(c);
                texp.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {   } });
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
                        disc.requestFocusInWindow();
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
        disc.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(disc.getText().equals(""))
                    {
                        disc.setText("0.00");
                        tmrp.requestFocusInWindow();
                    }
                    else
                        tmrp.requestFocusInWindow();
                }
            }});
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
                        badds.requestFocusInWindow();
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
        
        disc.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(disc.getText().equals(""))
                    {
                        disc.setText("0.00");
                        tmrp.requestFocusInWindow();
                    }
                    else
                        tmrp.requestFocusInWindow();
                }
            }});
        
        tbillno.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    supplier_name.requestFocusInWindow();
            }});
        
        supplier_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    try
                    {
                        ResultSet srset1=StaticMember.con.createStatement().executeQuery("SELECT * FROM supplier where supplier_id like'"+supplier_id.get(supplier_items.indexOf(supplier_name.getText()))+"'");
                        srset1.next();
                        taddr.setText(srset1.getString("supplier_address"));
                        tmob.setText(srset1.getString("supplier_mob"));
                        gst_no.setText(srset1.getString("supplier_gst"));
                        tdlno.setText(srset1.getString("supplier_dl"));
                        temail.setText(srset1.getString("supplier_email"));
                    }catch(SQLException ex){}
                    product_name.requestFocus();
            }}});
        
        
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
        mfg_items.clear();
        mfg_id.clear();
        disc.setText("");
        
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
        selitem=0;
        product_id.clear();
        temail.setText("");
        sumgst=0;
        sumamount=0;
        totalsum=0;
        mfg_id.clear(); 
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
    public void setItemsValue(String[] s)
    {
        product_name.setText((s[0]).toUpperCase());
        pi=s[1];
        product_hsn=s[2];
        product_gst=s[3];
    }
    //code to set data in table
    float sumamount=0,sumgst=0,totalsum=0;
    public void setdataInTable()
    {
        checkProductsDetails();
        try
        {
            ResultSet pr1=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+pi+"'");
            pr1.next();
            p_hsn=pr1.getString("product_hsn_code");
            p_gst=pr1.getString("product_gst");

         }catch(SQLException ex)
         {

         }
        product_id.add(pi);
        float pr=0;int q=0;
        try
        {
            pr=Float.parseFloat(tprate.getText());q=Integer.parseInt(tqty.getText());
            tamount=pr*q;
            tableModel.addRow(new Object[]{" "+product_name.getText()+" "," "+p_hsn+"  "," "+tpacking.getText()+" ",mfg_name.getText()," "+tbatchno.getText()+" ","  "+String.format("%.02f",Float.parseFloat(p_gst))+" "," "+String.format("%.02f",Float.parseFloat(disc.getText()))+"  "," "+tqty.getText()+"  "," "+tqtyfree.getText()+"  "," "+texp.getText()+" "," "+String.format("%.02f",Float.parseFloat(tmrp.getText()))+"  "," "+String.format("%.02f",Float.parseFloat(tprate.getText()))+"  "," "+String.format("%.02f",Float.parseFloat(tsrate.getText()))+"  "," "+String.format("%.02f",tamount)+" "});

            float sa=tamount;
            float da;
            float gst=Float.parseFloat(p_gst);
            float sv=sa*gst/100;
            sumgst=sumgst+sv;
            sumamount=sumamount+sa;
            totalsum=sumgst+sumamount; 
        }catch(NumberFormatException ex)    {}
                
        //set Formatter 
        Formatter fsuma=new Formatter(); //sum amount
        fsuma.format("%.2f",sumamount);
        Formatter fvata=new Formatter(); //Gst Amount
        fvata.format("%.2f",sumgst);
        Formatter fta=new Formatter(); //total Amount
        fta.format("%.2f",totalsum);
        lsumamt.setText(fsuma+"");
        lgstamt.setText(fvata+"");
        ltamt.setText(fta+"");
        selitem++;
    }
    
    
    //Add invoice in data base
    public void saveInvoice()
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
            PreparedStatement bsmt=StaticMember.con.prepareStatement("insert into invoice(inv_no,inv_date,supplier_id,added_on,total_amtount,invoice_time) values(?,?,?,?,?,?)");
            bsmt.setString(1,tbillno.getText().trim());
            bsmt.setString(2,StaticMember.getDate(invoicedate).toString());
            bsmt.setString(3,supplier_id.get(supplier_items.indexOf(supplier_name.getText())));
            bsmt.setString(4, StaticMember.getDate());
            Formatter fta=new Formatter(); //total Amount
            fta.format("%.2f",totalsum);
            bsmt.setString(5, fta.toString());
            bsmt.setString(6, StaticMember.getTime());
            bsmt.execute();
            
            PreparedStatement ssmt=StaticMember.con.prepareStatement("insert into stock_item(sr_no,inv_no,product_id,batch_no,qty,free_qty,disc,total_amtount,manifacture_id,added_on,mrp,srate,prate,packing,exp) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            //selitemid=new String[30];
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select max(sr_no) as sr_no  from stock_item");
            rset.next();
            int sr=rset.getInt("sr_no")+1;
            
            for(int i=0;i<table.getRowCount();i++)
            {
                ssmt.setInt(1,sr+i);
                ssmt.setString(2,tbillno.getText());
                ssmt.setString(3,product_id.get(i));
                ssmt.setString(4,table.getValueAt(i,4).toString());
                ssmt.setInt(5, Integer.parseInt(table.getValueAt(i,7).toString().trim()));
                ssmt.setInt(6, Integer.parseInt(table.getValueAt(i, 8).toString().trim()));
                ssmt.setFloat(7, Float.parseFloat(table.getValueAt(i, 6).toString().trim()));
                ssmt.setFloat(8, Float.parseFloat(table.getValueAt(i, 13).toString().trim()));
                ssmt.setString(9,mfg_id.get(mfg_items.indexOf(table.getValueAt(i, 3).toString())));
                ssmt.setString(10,StaticMember.getDate());
                ssmt.setFloat(11,Float.parseFloat(table.getValueAt(i, 10).toString().trim()));
                ssmt.setFloat(12,Float.parseFloat(table.getValueAt(i, 12).toString().trim()));
                ssmt.setFloat(13,Float.parseFloat(table.getValueAt(i, 11).toString().trim()));
                ssmt.setString(14,table.getValueAt(i,2).toString());
                ssmt.setString(15,table.getValueAt(i,9).toString());
                ssmt.execute();
            }
                
            PreparedStatement ssmt1=StaticMember.con.prepareStatement("insert into availableitem(product_id,packing,batch_no,quintity,expary_date,mrp,import_rate,sale_rate,added_on,manifacture_id) values(?,?,?,?,?,?,?,?,?,?)");
            for(int i=0;i<table.getRowCount();i++)
            {
                int q=Integer.parseInt(table.getValueAt(i,7).toString().trim());
                int fq=Integer.parseInt(table.getValueAt(i,8).toString().trim());
                String batch=(table.getValueAt(i,4).toString().trim());
                ResultSet b_rset=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batch+"'");
                if(b_rset.next())
                {
                    int q2=b_rset.getInt("quintity");
                    int q1=q+q2+fq;
                    
                    StaticMember.con.createStatement().execute("update availableitem set quintity="+q1+" where batch_no like'"+batch+"'");
                    continue;
                }
                
                ssmt1.setString(1,product_id.get(i));
                ssmt1.setString(2,table.getValueAt(i,2).toString());
                ssmt1.setString(3,table.getValueAt(i,4).toString().trim());
                ssmt1.setInt(4, Integer.parseInt(table.getValueAt(i,7).toString().trim())+Integer.parseInt(table.getValueAt(i,8).toString().trim()));
                ssmt1.setString(5, table.getValueAt(i, 9).toString());
                ssmt1.setFloat(6, Float.parseFloat(table.getValueAt(i, 10).toString().trim()));
                ssmt1.setFloat(7, Float.parseFloat(table.getValueAt(i, 11).toString().trim()));
                ssmt1.setFloat(8, Float.parseFloat(table.getValueAt(i, 12).toString().trim()));
                ssmt1.setString(9, StaticMember.getDate());
                ssmt1.setString(10, mfg_id.get(mfg_items.indexOf(table.getValueAt(i, 3).toString())));
                ssmt1.execute();
                
            }
            
            ResultSet b=StaticMember.con.createStatement().executeQuery("select inv_no from invoice where invoice_time like'"+StaticMember.getTime()+"' AND inv_date like'"+StaticMember.getDate(invoicedate)+"'");
            if(b.next())
            rbill=b.getString("inv_no");
            JOptionPane.showMessageDialog(Stock.this,"Bill Saved!",null,JOptionPane.INFORMATION_MESSAGE);
            empaty();
            tbillno.requestFocusInWindow();
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(Stock.this, ex.getMessage(),"Opps",JOptionPane.ERROR_MESSAGE);
        }catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(Stock.this, ex.getMessage(),"Opps",JOptionPane.ERROR_MESSAGE);
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
        if(tqtyfree.getText().equals(""))
        {
            JOptionPane.showMessageDialog(this, " Free Quintity Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
            tqtyfree.requestFocusInWindow();return true;
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
    public void setStockManufacture()
    {
        StaticMember.setToManufacture(mfg_name, mfg_id, mfg_items);
    }
    
   private void fillData(String batch)
   {
       try
       {
           ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batch+"'");
           if(rset.next())
           {//tpacking,tbatchno,texp,tmrp,tqty,tqtyfree,tprate,tsrate,mfg_name,product_name
               tpacking.setText(rset.getString("packing"));
               texp.setText(rset.getString("expary_date"));
               tqty.setText(rset.getString("quintity"));
               tqtyfree.setText("0");
               tprate.setText(rset.getString("import_rate"));
               tsrate.setText(rset.getString("sale_rate"));
               mfg_name.setText(mfg_items.get(mfg_id.indexOf(mfg_id.get(mfg_id.indexOf(rset.getInt("manifacture_id"))))));
           }
           else
              tbatchno.requestFocusInWindow();
       }
       catch(SQLException ex)
       {
           JOptionPane.showMessageDialog(Stock.this, ex.getMessage(),"Opps",JOptionPane.ERROR_MESSAGE);
       }
   }
    
}
