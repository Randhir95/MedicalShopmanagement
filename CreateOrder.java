/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
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
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import static komalhealthcare.Billing.cust_name;
import static komalhealthcare.KHC.bill;
import static komalhealthcare.MDIMainWindow.desktop;

/**
 *
 * @author RANDHIR KUMAR
 */
public class CreateOrder extends JInternalFrame{
    static JTextField cust_name;
    JTextField cust_dl_no,cust_address,cust_mob,cust_gstin,product_quintity,packing,product_gst,product_mrp,product_name,product_hsn,product_rate,avi_qty,total_amount;
    JLabel order_date,order_no,grand_total_amount,gst_amount,pay_amount;
    JButton rbsave,reset,print,btplus,btadd;
    JTable table;
    JComboBox pack_list;
    String batch_no,exp,mfg;
    DefaultTableModel tableModel;
    String[] data;
    boolean ch;
    private String rbill="";
    private String p_id,m_id,a_id,p_hsn_code,p_quintity,s,p_packing;
    private String selitemid[]=new String[30];
    private int selitem=0,numberOfRow=0;;
    ArrayList<String> supplier_id=new ArrayList<>();
    ArrayList<String> supplier_items=new ArrayList<>();
    ArrayList<String> product_id=new ArrayList<>();
    ArrayList<String> mfg_id=new ArrayList<>();
    ArrayList<String> avil_id=new ArrayList<>();
    
    public CreateOrder(String str[])
    {
        super("",true,true);
        StaticMember.setSize(this);
        this.setDefaultCloseOperation(Billing.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.CREATE_ORDER=false;
              }});
        
        data=str;
        
        JLabel h=new JLabel(" MAKE NEW ORDER",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        
        //Access Date Frome System
         Calendar cl=Calendar.getInstance();
       Formatter fmt=new Formatter();
       fmt.format("%tY-%tm-%td",cl,cl,cl);
       s=fmt.toString();
       String sdate;
       Formatter fdmt=new Formatter();
       fdmt.format("%td-%tm-%tY",cl,cl,cl);
       sdate=fdmt.toString();
       
        JLabel patientname=new JLabel("  SUPPLIER NAME :  ",JLabel.RIGHT);
        patientname.setFont(StaticMember.labelFont);
        patientname.setForeground(StaticMember.flcolor);
        JLabel doctorname=new JLabel("            DL NO. :  ",JLabel.RIGHT);
        doctorname.setFont(StaticMember.labelFont);
        doctorname.setForeground(StaticMember.flcolor);
        JLabel date=new JLabel("          DATE :  ",JLabel.RIGHT);
        date.setFont(StaticMember.labelFont);
        date.setForeground(StaticMember.flcolor);
        JLabel invoiceno=new JLabel("  ORDER NO :  ",JLabel.RIGHT);
        invoiceno.setFont(StaticMember.labelFont);
        invoiceno.setForeground(StaticMember.flcolor);
        JLabel patientaddress=new JLabel("  ADDRESS :  ",JLabel.RIGHT);
        patientaddress.setFont(StaticMember.labelFont);
        patientaddress.setForeground(StaticMember.flcolor);
        JLabel patientmob=new JLabel("  MOBILE NO. :  ",JLabel.RIGHT);
        patientmob.setFont(StaticMember.labelFont);
        patientmob.setForeground(StaticMember.flcolor);
        JLabel patientadhar=new JLabel("       GSTIN NO.       :  ",JLabel.RIGHT);
        patientadhar.setFont(StaticMember.labelFont);
        patientadhar.setForeground(StaticMember.flcolor);
        JLabel productname=new JLabel("PRODUCT NAME",JLabel.CENTER);
        productname.setFont(StaticMember.labelFont);
        productname.setForeground(StaticMember.flcolor);
        JLabel packlist=new JLabel("PACK",JLabel.CENTER);
        packlist.setFont(StaticMember.labelFont);
        packlist.setForeground(StaticMember.flcolor);
        JLabel packlable=new JLabel("PACKING",JLabel.CENTER);
        packlable.setFont(StaticMember.labelFont);
        packlable.setForeground(StaticMember.flcolor);
        JLabel productqty=new JLabel("QUINTITY",JLabel.CENTER);
        productqty.setFont(StaticMember.labelFont);
        productqty.setForeground(StaticMember.flcolor);
        JLabel productmrp=new JLabel("MRP \u20B9",JLabel.CENTER);
        productmrp.setFont(StaticMember.labelFont);
        productmrp.setForeground(StaticMember.flcolor);
        JLabel productrate=new JLabel("RATE \u20B9",JLabel.CENTER);
        productrate.setFont(StaticMember.labelFont);
        productrate.setForeground(StaticMember.flcolor);
        JLabel aviqty=new JLabel("AVL QTY",JLabel.CENTER);
        aviqty.setFont(StaticMember.labelFont);
        aviqty.setForeground(StaticMember.flcolor);
        JLabel productgst=new JLabel("GST",JLabel.CENTER);
        productgst.setFont(StaticMember.labelFont);
        productgst.setForeground(StaticMember.flcolor);
        JLabel totalamt=new JLabel("TOTAL AMOUNT \u20B9",JLabel.CENTER);
        totalamt.setFont(StaticMember.labelFont);
        totalamt.setForeground(StaticMember.flcolor);
        JLabel gstamount=new JLabel("GST AMOUNT :",JLabel.RIGHT);
        gstamount.setFont(StaticMember.labelFont);
        gstamount.setForeground(StaticMember.flcolor);
        JLabel grandtotal=new JLabel("GRAND TOTAL :",JLabel.RIGHT);
        grandtotal.setFont(StaticMember.labelFont);
        grandtotal.setForeground(StaticMember.flcolor);
        JLabel sumamt=new JLabel("SUM AMOUNT :",JLabel.RIGHT);
        sumamt.setFont(StaticMember.labelFont);
        sumamt.setForeground(StaticMember.flcolor);
        
       
        
        //cteate TextField
        order_date=new JLabel(sdate);
        order_date.setFont(StaticMember.textFont);
        order_date.setBorder(createLineBorder(StaticMember.BORDER_COLOR,1));
        order_no=new JLabel("");
        order_no.setFont(StaticMember.textFont);
        order_no.setBorder(createLineBorder(StaticMember.BORDER_COLOR,1));
        cust_name=StaticMember.MyInputBox("",50,StaticMember.STRING_TYPE,"ENTER CUSTOMER NAME");
        cust_name.setBorder(createLineBorder(StaticMember.BORDER_COLOR,1));
        StaticMember.setToSupplier(cust_name, supplier_id, supplier_items);
        cust_dl_no=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"CUSTOMER DL NO");
        cust_address=StaticMember.MyInputBox("",60,StaticMember.STRING_TYPE,false,"CUSTOMER ADDRESS");
        cust_mob=StaticMember.MyInputBox("",10,StaticMember.INTEGER_TYPE,false,"CUSTOMER MOBILE NO");
        cust_gstin=StaticMember.MyInputBox("",16,StaticMember.STRING_TYPE,false,"CUSTOMER GSTIN NO");
        
        product_name=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"PRODUCT NAME");
        packing=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,"PRODUCT NAME");
        product_hsn=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"HSN");
        avi_qty=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"AVL QTY");
        avi_qty.setHorizontalAlignment(SwingConstants.CENTER);
        product_mrp=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"MRP");
        product_mrp.setHorizontalAlignment(SwingConstants.RIGHT);
        product_rate=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"RATE");
        product_rate.setHorizontalAlignment(SwingConstants.RIGHT);
        product_quintity=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,"QTY");
        product_quintity.setHorizontalAlignment(SwingConstants.CENTER);
        product_gst=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"GST");
        product_gst.setHorizontalAlignment(SwingConstants.RIGHT);
        String pack_data[]={"","CASES","PISCES"};
        pack_list=new JComboBox(pack_data);
        pack_list.setSelectedIndex(-1);
        pack_list.setFont(StaticMember.textFont);
        
        grand_total_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        grand_total_amount.setFont(StaticMember.textFont);
        grand_total_amount.setBackground(StaticMember.bcolor);
        gst_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        gst_amount.setFont(StaticMember.textFont);
        pay_amount=new JLabel("00.00",JLabel.RIGHT);
        pay_amount.setFont(StaticMember.textFont);
        pay_amount.setHorizontalAlignment(SwingConstants.RIGHT);
        total_amount=new JTextField("",JTextField.RIGHT);
        total_amount.setHorizontalAlignment(SwingConstants.RIGHT);
        total_amount.setFont(StaticMember.textFont);
        total_amount.setEditable(false);
        
        
        String aname[]={"PRODUCTS","PACKING","HSN","MRP \u20B9","RATE \u20B9","GST %","GST AMT \u20B9","QTY","PACK","AMOUNT \u20B9"};
        String data[][]=new String[0][];
        table=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        //tableModel.isCellEditable(ERROR, NORMAL)
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
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        
        TableColumn column=null;
       
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(250);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(5);
        column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(6);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(7);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(8);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(9);
        column.setPreferredWidth(40);
        
        JScrollPane scr=new JScrollPane(table);
        
        rbsave=new JButton("SAVE");
        rbsave.setFont(StaticMember.buttonFont);
        rbsave.setForeground(StaticMember.bfcolor);
        reset=new JButton("RESET");
        reset.setFont(StaticMember.buttonFont);
        reset.setForeground(StaticMember.bfcolor);
        print=new JButton("PRINT");
        print.setFont(StaticMember.buttonFont);
        print.setForeground(StaticMember.bfcolor);
        btadd=StaticMember.MyMenuButton("", "/images/plus.png", 30);
        btadd.setOpaque(true);
        btadd.setBackground(null);
        btadd.setBorder(null);
        btplus=StaticMember.MyMenuButton("", "/images/plus.png", 30);
        btplus.setOpaque(true);
        btplus.setBackground(null);
        btplus.setBorder(null);
        
        JPanel maininerpanel=new JPanel(new GridLayout(1,3));
        JPanel main=new JPanel(new BorderLayout());
        JPanel name_panel=new JPanel(new BorderLayout());
        JPanel name_button_panel=new JPanel(new BorderLayout());
        JPanel add_panel=new JPanel(new BorderLayout());
        JPanel mob_panel=new JPanel(new BorderLayout());
        JPanel doctor_panel=new JPanel(new BorderLayout());
        JPanel date_panel=new JPanel(new BorderLayout());
        JPanel main_date_panel=new JPanel(new GridLayout(1,2));
        JPanel dispatch_panel=new JPanel(new BorderLayout());
        JPanel dispatch_list_panel=new JPanel(new GridLayout(1,3,3,3));
        JPanel adhar_panel=new JPanel(new BorderLayout());
        JPanel patientpanel=new JPanel(new GridLayout(2,3,3,3));
        JPanel main_patientpanel=new JPanel(new BorderLayout());
        JPanel btpanel=new JPanel(new BorderLayout());
        JPanel product_panel=new JPanel(new BorderLayout());
        JPanel mrp_panel=new JPanel(new BorderLayout());
        JPanel price_panel=new JPanel(new BorderLayout());
        JPanel disc_panel=new JPanel(new BorderLayout());
        JPanel pack_panel=new JPanel(new BorderLayout());
        JPanel aviqty_panel=new JPanel(new BorderLayout());
        JPanel qty_panel=new JPanel(new BorderLayout());
        JPanel amount_panel=new JPanel(new BorderLayout());
        JPanel productmainpanel=new JPanel(new GridLayout(1,3));
        JPanel product_ditail_panel=new JPanel(new GridLayout(1,2,2,2));
        JPanel mrp_price_panel=new JPanel(new GridLayout(1,3,2,2));
        JPanel dis_gst_qty=new JPanel(new GridLayout(1,2,2,2));
        JPanel total_amt_panel=new JPanel(new GridLayout(2,3,3,3));
        JPanel gstamount_qty_panel=new JPanel(new GridLayout(1,3,2,2));
        JPanel totalamount_btadd_panel=new JPanel(new BorderLayout());
        JPanel main_buttonpanel=new JPanel(new GridLayout(1,3));
        JPanel buttonpanel=new JPanel(new GridLayout(1,3,2,2));
        
        JPanel mainamountpanel=new JPanel(new GridLayout(3,1));
        JPanel grand_total_panel=new JPanel(new GridLayout(1,2,2,2));
        JPanel due_amount_panel=new JPanel(new GridLayout(1,2,2,2));
        JPanel pay_amount_panel=new JPanel(new GridLayout(1,2,2,2));
        JPanel pay_amt_panel=new JPanel(new BorderLayout());
        
        patientpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Supplier Detail"));
        patientpanel.setPreferredSize(new Dimension(20,85));
        name_button_panel.add(cust_name,BorderLayout.CENTER);name_button_panel.add(btplus,BorderLayout.EAST);
        name_panel.add(patientname,BorderLayout.WEST);name_panel.add(name_button_panel,BorderLayout.CENTER);
        add_panel.add(patientaddress,BorderLayout.WEST);add_panel.add(cust_address,BorderLayout.CENTER);
        date_panel.add(date,BorderLayout.WEST);date_panel.add(order_date,BorderLayout.CENTER);
        dispatch_panel.add(invoiceno,BorderLayout.WEST);dispatch_panel.add(order_no,BorderLayout.CENTER);
        main_date_panel.add(date_panel);main_date_panel.add(dispatch_panel);
        mob_panel.add(patientmob,BorderLayout.WEST);mob_panel.add(cust_mob,BorderLayout.CENTER);
        doctor_panel.add(doctorname,BorderLayout.WEST);doctor_panel.add(cust_dl_no,BorderLayout.CENTER);
        adhar_panel.add(patientadhar,BorderLayout.WEST);adhar_panel.add(cust_gstin,BorderLayout.CENTER);
        patientpanel.add(main_date_panel);patientpanel.add(name_panel);patientpanel.add(mob_panel);
        patientpanel.add(add_panel);patientpanel.add(adhar_panel);patientpanel.add(doctor_panel); 
        product_panel.add(productname,BorderLayout.NORTH);product_panel.add(product_name,BorderLayout.CENTER);
        mrp_panel.add(productmrp,BorderLayout.NORTH);mrp_panel.add(product_mrp,BorderLayout.CENTER);
        price_panel.add(productrate,BorderLayout.NORTH);price_panel.add(product_rate,BorderLayout.CENTER);
        disc_panel.add(productgst,BorderLayout.NORTH);disc_panel.add(product_gst,BorderLayout.CENTER);
        aviqty_panel.add(aviqty,BorderLayout.NORTH);aviqty_panel.add(avi_qty,BorderLayout.CENTER);
        pack_panel.add(packlist,BorderLayout.NORTH);pack_panel.add(pack_list,BorderLayout.CENTER);
        qty_panel.add(productqty,BorderLayout.NORTH);qty_panel.add(product_quintity,BorderLayout.CENTER);
        amount_panel.add(totalamt,BorderLayout.NORTH);amount_panel.add(total_amount,BorderLayout.CENTER);
        mrp_price_panel.add(mrp_panel);mrp_price_panel.add(price_panel);mrp_price_panel.add(disc_panel);
        gstamount_qty_panel.add(aviqty_panel);gstamount_qty_panel.add(qty_panel);gstamount_qty_panel.add(pack_panel);
        btpanel.add(new JLabel("  "),BorderLayout.NORTH);btpanel.add(btadd,BorderLayout.CENTER);
        totalamount_btadd_panel.add(amount_panel,BorderLayout.CENTER);totalamount_btadd_panel.add(btpanel,BorderLayout.EAST);
        product_ditail_panel.add(mrp_price_panel);//product_ditail_panel.add(dis_gst_qty);
        productmainpanel.add(product_panel);productmainpanel.add(product_ditail_panel);productmainpanel.add(gstamount_qty_panel);productmainpanel.add(totalamount_btadd_panel);
        productmainpanel.setPreferredSize(new Dimension(20,50));
        main.add(productmainpanel,BorderLayout.NORTH);
        main.add(scr,BorderLayout.CENTER);
        //dispatch_list_panel.add(dispatch,BorderLayout.EAST);
        //dispatch_list_panel.add(dis_label,BorderLayout.WEST);
        buttonpanel.add(rbsave);buttonpanel.add(reset);buttonpanel.add(print);
        main_buttonpanel.add(dispatch_list_panel);main_buttonpanel.add(new JLabel());main_buttonpanel.add(buttonpanel);
        grand_total_panel.add(sumamt);grand_total_panel.add(grand_total_amount);
        due_amount_panel.add(gstamount);due_amount_panel.add(gst_amount);
        JLabel l2=new JLabel(" \u20B9"+"     ",JLabel.RIGHT);
        l2.setFont(StaticMember.textFont);
        pay_amt_panel.add(pay_amount,BorderLayout.CENTER);pay_amt_panel.add(l2,BorderLayout.EAST);
        pay_amount_panel.add(grandtotal);pay_amount_panel.add(pay_amt_panel);
        mainamountpanel.add(grand_total_panel);mainamountpanel.add(due_amount_panel);mainamountpanel.add(pay_amount_panel);
        maininerpanel.add(new JLabel(""));maininerpanel.add(new JLabel(""));maininerpanel.add(mainamountpanel);
        maininerpanel.setPreferredSize(new Dimension(200,80));
        main.add(maininerpanel,BorderLayout.SOUTH);
        this.add(main_buttonpanel,BorderLayout.SOUTH);
        this.add(main,BorderLayout.CENTER);
        main_patientpanel.add(patientpanel,BorderLayout.CENTER);
        main_patientpanel.add(h,BorderLayout.NORTH);
        this.add(main_patientpanel,BorderLayout.NORTH);
        
        cust_name.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                  setToSupplierDetails();
                  product_name.requestFocusInWindow();
            }}
            public void keyPressed(KeyEvent e) {}
            public void keyReleased(KeyEvent e) {
            }});
        
            product_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                  new SelectOrderProduct(CreateOrder.this).setVisible(true);  
                  product_quintity.requestFocusInWindow();
            }}});
            
            product_quintity.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(product_quintity.getText().equals("0"))
                        product_quintity.requestFocusInWindow();
                    else
                    pack_list.requestFocusInWindow();
            }}
            });
             product_quintity.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                product_quintity.setSelectionStart(0);
                product_quintity.setSelectionEnd(product_quintity.getText().length());
                product_quintity.setSelectionColor(Color.BLUE);
                product_quintity.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {
                float rate_amt=Float.parseFloat(product_rate.getText());
                int p_qty=Integer.parseInt(product_quintity.getText());
                total_amount.setText(String.format("%.02f",rate_amt*p_qty));
            } });
            
            pack_list.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(pack_list.getSelectedIndex()==-1)
                        pack_list.requestFocusInWindow();
                    else
                    btadd.requestFocusInWindow();
            }}});
            
            btplus.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    //codeToCustomer();
                    cust_name.requestFocusInWindow();
            }}});
            btplus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    //codeToCustomer();
                    cust_name.requestFocusInWindow();
            }
            });
            btadd.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    int q=Integer.parseInt(product_quintity.getText());
                    if(q==0 || product_quintity.getText().equals(""))
                    {
                        product_quintity.setText("");
                        product_quintity.requestFocusInWindow();
                        return;
                    }
                    setDataInTable();
                    pempaty();
                    product_name.requestFocusInWindow();
            }}});
            btadd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int q=Integer.parseInt(product_quintity.getText());
                    if(q==0 || product_quintity.getText().equals(""))
                    {
                        product_quintity.setText("");
                        product_quintity.requestFocusInWindow();
                        return;
                    }
                    setDataInTable();
                    pempaty();
                    product_name.requestFocusInWindow();
            }
        });
        rbsave.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    saveBill();
                    cust_name.requestFocusInWindow();
            }}});
        rbsave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveBill();
                cust_name.requestFocusInWindow();
            }
        });
        
        reset.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    empaty();
                    pempaty();
                    product_name.requestFocusInWindow();
            }}});
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                empaty();
                pempaty();
                product_name.requestFocusInWindow();
            }
        });
        print.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                   StaticMember.printOrderRisipt(rbill);
            }});
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.printOrderRisipt(rbill);
            }
        });
        
        setBillNo();
        
    }
    
    public void setOrderItemsValue(String[] s)
    {
       
        product_name.setText((s[0]).toUpperCase());
        product_rate.setText(String.format("%.02f", Float.parseFloat(s[2])));
        product_mrp.setText(String.format("%.02f", Float.parseFloat(s[4])));
        p_quintity=(s[10]);
        avi_qty.setText(p_quintity);
        product_quintity.setText("0");
        a_id=s[7];
        m_id=s[9];
        p_id=s[8];
        p_hsn_code=s[11];
        product_gst.setText(String.format("%.02f", Float.parseFloat(s[3])));
        p_packing=s[5];
        
    }
    
    float sum_amount=0,totalsum=0,p_gst=0,gstamount=0;
    public void setDataInTable()
    {
        if(product_name.getText().equals(""))return;
        product_id.add(p_id);
        mfg_id.add(m_id);
        avil_id.add(a_id);
        int i;
        int sr=1;
        float net_amt=0,rate=0,gst=0,gst_amt=0;
        rate=((Float.parseFloat(product_rate.getText())));
        gst=((Float.parseFloat(product_gst.getText())));
        gst_amt=rate*gst/100;
        net_amt=rate+gst_amt;
        tableModel.addRow(new Object[]{" "+product_name.getText()+" "," "+p_packing+"  "," "+p_hsn_code+"  ","  "+String.format("%.02f",Float.parseFloat(product_mrp.getText()))+" "," "+String.format("%.02f",Float.parseFloat(product_rate.getText()))+"  "," "+String.format("%.02f",Float.parseFloat(product_gst.getText()))+"  "," "+String.format("%.02f",gst_amt)+" "," "+product_quintity.getText()+"  "," "+pack_list.getSelectedItem().toString()+"  "," "+String.format("%.02f",Float.parseFloat(total_amount.getText()))+"  "});
        try
        {
            float sa=Float.parseFloat(total_amount.getText());
            float pgst;
            pgst=Float.parseFloat(product_gst.getText());
            sum_amount+=sa;
            p_gst=sa*pgst/100;
            totalsum=sum_amount;
            gstamount+=p_gst;
            totalsum=sum_amount+gstamount;
        }catch(NumberFormatException ex)    {}

            grand_total_amount.setText(String.format("%.02f", sum_amount)+"   \u20B9     ");
            gst_amount.setText(String.format("%.02f", gstamount)+"   \u20B9     ");
            pay_amount.setText(String.format("%.02f", totalsum)+"  ");
            //JOptionPane.showMessageDialog(null, cgst+"");
            selitem++;

    }
    
    private void saveBill()
    {
        try
        {
            PreparedStatement bsmt=StaticMember.con.prepareStatement("insert into create_order(order_no,order_date,supplier_id,total_amount,order_time) values(?,?,?,?,?)");
            Calendar cl=Calendar.getInstance();
            Formatter fmt=new Formatter();
            fmt.format("%tH:%tM:%tS",cl,cl,cl);
            ResultSet order_rset=StaticMember.con.createStatement().executeQuery("select max(order_no) as order_no  from create_order");
            order_rset.next();
            int order=order_rset.getInt("order_no")+1;
            bsmt.setInt(1, order);
            bsmt.setString(2,s);
            bsmt.setString(3,supplier_id.get(supplier_items.indexOf(cust_name.getText())));
            bsmt.setFloat(4, Float.parseFloat(pay_amount.getText()));
            bsmt.setString(5, fmt+"");
            bsmt.execute();
            
            PreparedStatement ssmt=StaticMember.con.prepareStatement("insert into order_items(order_no,qty,product_id,pack,availableitem_id) values(?,?,?,?,?)");
            ResultSet r=StaticMember.con.createStatement().executeQuery("select max(order_no) as order_no from create_order");
            r.next();
            ssmt.setString(1,r.getString(1));
            for(int i=0;i<table.getRowCount();i++)
            {
                int qty=Integer.parseInt(table.getValueAt(i, 7).toString().trim());
                ssmt.setInt(2, qty);
                ssmt.setString(3, product_id.get(i));
                ssmt.setString(4, table.getValueAt(i, 8).toString());
                ssmt.setString(5, avil_id.get(i));
                ssmt.execute();
                
            }
            ResultSet b=StaticMember.con.createStatement().executeQuery("select order_no from create_order where order_time like'"+fmt.toString()+"' AND order_date like'"+s+"'");
            if(b.next())
            rbill=b.getString("order_no");
            empaty();
            JOptionPane.showMessageDialog(CreateOrder.this,"Bill Saved!",null,JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    
    
    public void empaty()
    {
        p_id="";
        batch_no="";
        exp="";
        mfg="";
        product_id.clear();
        mfg_id.clear();
        avil_id.clear();
        cust_name.setText("");
        cust_address.setText("");
        cust_gstin.setText("");
        cust_dl_no.setText("");
        //invoice_no.setText("");
        grand_total_amount.setText("   \u20B9     ");
        pay_amount.setText("");
        gst_amount.setText("   \u20B9     ");
        cust_mob.setText("");
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
        
    }
   
    public void pempaty()
    {
        
        product_gst.setText("");
        product_name.setText("");
        product_quintity.setText("");
        product_rate.setText("");
        product_mrp.setText("");
        avi_qty.setText("");
        total_amount.setText("");
        
    }
    public void setBillNo()
   {
       int orderno=0;
        try
        {
            ResultSet order_rset=StaticMember.con.createStatement().executeQuery("select max(order_no) as order_no  from create_order");
            order_rset.next();
            orderno=order_rset.getInt("order_no")+1;
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(CreateOrder.this, ex.getMessage(),"",JOptionPane.ERROR_MESSAGE);
        }
        order_no.setText("ODR00"+orderno);
   }
    
    private void setToSupplierDetails()
    {
        try{
                ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT  * FROM supplier where supplier_id like'"+supplier_id.get(supplier_items.indexOf(cust_name.getText()))+"'");
                rset.next();
                cust_mob.setText(rset.getString("supplier_mob").toString());
                cust_address.setText(rset.getString("supplier_address").toString());
                cust_dl_no.setText(rset.getString("supplier_dl").toString());
                cust_gstin.setText(rset.getString("supplier_gst").toString());
            }catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
            }
            JTextFieldAutoComplete.setupAutoComplete(cust_name, supplier_items,false);
    }
}
