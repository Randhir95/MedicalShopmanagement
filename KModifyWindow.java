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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createLineBorder;
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
import javax.swing.SwingConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import static komalhealthcare.Billing.cust_name;
import static komalhealthcare.KBilling.cust_name;

/**
 *
 * @author RANDHIR KUMAR
 */
public class KModifyWindow extends JDialog{
    static JTextField cust_name;
    private JTextField cust_dl_no,cust_address,cust_mob,cust_gstin,product_quintity,free_qty,product_disc,product_cgst,product_sgst,product_name,product_hsn,product_mrp,product_rate,avil_qty,total_amount,txtpayment;
    private JLabel bill_date,invoice_no,grand_total_amount,disc_amount,cgst_amt,sgst_amt,pay_status_status,pay_amount,payment,pay_mood_status;
    private JButton bcancel,close,print,btplus,btadd,reset,dispatch_by,sprint;
    private JComboBox pay_mood,pay_status;
    private JTable table;
    private String batch_no,exp,mfg,batch,expary,a_id,p_amt,s_gst,c_gst;
    private DefaultTableModel tableModel;
    private String[] data;
    private String billno;
    private int old_qty=0,old_free_qty;
    private String bill="";
    private String rbill="",sr_no="";
    private String srno[]=new String[30];
    private String pri,p_hsn_code,p_quintity,cheqe_no,s,mri;
    public String p_id;
    private boolean flag=false;
    private int selitem=0,numberOfRow=0;;
    private ArrayList<String> customer_id=new ArrayList<>();
    private ArrayList<String> customer_items=new ArrayList<>();
    public KModifyWindow(String[] bd,String bil)
    {
        super(MDIMainWindow.self,"");
        StaticMember.setSize(this);
        StaticMember.setLocation(this);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent we)
         {
             KModifyWindow.this.dispose();
         }
        });
        
        btadd=StaticMember.AddButton("/images/plus.png",40,40,"Click On Button To Add Recond In Table");
        StaticMember.lookAndFeel();
        
        data=bd;
        billno=bil;
        //create Labels
        
        JMenuBar bar=new JMenuBar();
        JMenu m1=new JMenu("H");
        JMenuItem msave=new JMenuItem("Save",'S');
        msave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,KeyEvent.CTRL_MASK));
        JMenuItem mprint=new JMenuItem("Print",'P');
        mprint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_MASK));
        JMenuItem mi=new JMenuItem("Sel",'F');
        mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,KeyEvent.ALT_MASK));
        JMenuItem si=new JMenuItem("Reset",'R');
        si.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2,KeyEvent.KEY_RELEASED));
        m1.add(mi);
        m1.add(mprint);
        m1.add(msave);
        m1.add(si);
        bar.add(m1);
        this.add(bar);
        mprint.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.printKBill(billno);
            }        });
        /*msave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                saveBill(billno);
                if(JOptionPane.showConfirmDialog(null,"Do You Want To Print Bill ?","Confirmtion Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                {
                    printBill();
                }
            }        });*/
        mi.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new KMSelectProduct(KModifyWindow.this).setVisible(true);
                product_rate.requestFocusInWindow();
            }        });
        si.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                empaty();
                pempaty();
            }        });
        
         JLabel h=new JLabel(" STIMEET MODIFY ",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
       
        
        JLabel sumamount=new JLabel("DISE AMOUNT :",JLabel.RIGHT);
        sumamount.setFont(StaticMember.labelFont);
        JLabel totalgstamount=new JLabel("TOTAL GST AMOUNT :",JLabel.RIGHT);
        totalgstamount.setFont(StaticMember.labelFont);
        JLabel totaldiscamount=new JLabel("PAYABLE AMOUNT :",JLabel.RIGHT);
        totaldiscamount.setFont(StaticMember.labelFont);
        payment=new JLabel("PAY AMOUNT :",JLabel.RIGHT);
        payment.setFont(StaticMember.labelFont);
        JLabel grandtotalamount=new JLabel("GRAND TOTAL AMOUNT :",JLabel.RIGHT);
        grandtotalamount.setFont(StaticMember.labelFont);
        JLabel sgstlabel=new JLabel("SGST AMOUNT :",JLabel.RIGHT);
        sgstlabel.setFont(StaticMember.labelFont);
        JLabel cgstlabel=new JLabel("CGST AMOUNT :",JLabel.RIGHT);
        cgstlabel.setFont(StaticMember.labelFont);
        JLabel paymoodlabel=new JLabel("PAYMENT MODE :",JLabel.RIGHT);
        paymoodlabel.setFont(StaticMember.labelFont);
        JLabel paystatuslabel=new JLabel("PAYMENT STATUS :",JLabel.RIGHT);
        paystatuslabel.setFont(StaticMember.labelFont);
        JLabel dis_label=new JLabel("   DISPATCH BY : ",JLabel.RIGHT);
        dis_label.setFont(StaticMember.labelFont);
        pay_mood_status=new JLabel("");
        pay_mood_status.setFont(StaticMember.textFont);
        pay_status_status=new JLabel("");
        pay_status_status.setFont(StaticMember.textFont);
        
        
        //cteate TextField
        bill_date=StaticMember.MyJLabel(StaticMember.getDate()," DATE ");
        invoice_no=StaticMember.MyJLabel(""," BILL NO ");
        //cteate TextField
        cust_name=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"Enter Customer Name"," PARTY NAME");
        cust_dl_no=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"DL No."," DL NO");
        cust_gstin=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"GSTIN No"," GSTIN NO");
        cust_mob=StaticMember.MyInputBox("",11,StaticMember.INTEGER_TYPE,false," Mobile No."," MOBILE NO");
        cust_address=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"ADDRESS"," ADDRESS");
        product_quintity=StaticMember.MyInputBox("",30,StaticMember.INTEGER_TYPE," Quintity"," QUINTITY");
        product_quintity.setHorizontalAlignment(SwingConstants.CENTER);
        free_qty=StaticMember.MyInputBox("",30,StaticMember.INTEGER_TYPE," Free Qty"," FREE");
        free_qty.setHorizontalAlignment(SwingConstants.CENTER);
        product_disc=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE,"Disc"," DISC %");
        product_disc.setHorizontalAlignment(SwingConstants.RIGHT);
        product_name=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," Product Name"," PRODUCT NAME");
        product_hsn=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"HSN Code"," HSN CODE");
        product_mrp=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE,false," MRP"," MRP");
        product_rate=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE," RATE"," RATE");
        avil_qty=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," AVL QTY"," AVL QTY");
        avil_qty.setHorizontalAlignment(SwingConstants.CENTER);
        total_amount=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," Total Amount "," TOTAL AMOUNT");
        pay_mood_status=StaticMember.MyLabel("");
        pay_status_status=StaticMember.MyLabel("");
        total_amount.setHorizontalAlignment(SwingConstants.RIGHT);
        product_sgst=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE,false,"SGST"," SGST %");
        product_sgst.setHorizontalAlignment(SwingConstants.RIGHT);
        product_cgst=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE,false,"CGST"," CGST %");
        product_cgst.setHorizontalAlignment(SwingConstants.RIGHT);
        
        grand_total_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        grand_total_amount.setFont(StaticMember.textFont);
        disc_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        disc_amount.setFont(StaticMember.textFont);
        sgst_amt=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        sgst_amt.setFont(StaticMember.textFont);
        cgst_amt=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        cgst_amt.setFont(StaticMember.textFont);
        pay_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        pay_amount.setFont(StaticMember.textFont);
        pay_amount.setHorizontalAlignment(SwingConstants.RIGHT);
        String []moodstr={"","CASH","CARD","CHEQUE"};
        pay_mood=new JComboBox(moodstr);
        pay_mood.setFont(StaticMember.textFont);
        pay_mood.setBackground(StaticMember.bcolor);
        String []statusstr={"","ADVANCE","DUE","PAID"};
        pay_status=new JComboBox(statusstr);
        pay_status.setFont(StaticMember.textFont);
        pay_status.setBackground(StaticMember.bcolor);
        
        String aname[]={"PRODUCTS","HSN","BATCH","MAKE","EXP","MRP \u20B9","RATE \u20B9","DISC%","CGST%","SGST %","QTY","FREE","AMOUNT \u20B9"};
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
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(12).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(11).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        TableColumn column=null;
       
        column=table.getColumnModel().getColumn(0);column.setPreferredWidth(230);
        column=table.getColumnModel().getColumn(1);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(2);column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(3);column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(4);column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(5);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(6);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(7);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(8);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(9);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(10);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(11);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(12);column.setPreferredWidth(40);
        
        
        JScrollPane scr=new JScrollPane(table);
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        bcancel=StaticMember.MyButton("DEL ROW","Click On Button To Delete The Row");
        print=StaticMember.MyButton("PRINT","Click On Button To Print The Record");
        sprint=StaticMember.MyButton("PRINT STIMEET","Click On Button To Print Stimeet The Record");
        
        JLabel l2=new JLabel("PRODUCT DESCREPTION",JLabel.CENTER);
        l2.setFont(StaticMember.HEAD_FONT);
        l2.setForeground(Color.WHITE);
        l2.setOpaque(true);
        l2.setBackground(Color.BLUE);
        l2.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        l2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE,1, true),""));
        
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
        JPanel billDatePanel=new JPanel(new GridLayout(1,2,10,10));
        JPanel cust_nameBtnPanel=new JPanel(new BorderLayout());
        JPanel mrp_rate_disc_panel=new JPanel(new GridLayout(1,3,2,2));
        JPanel gst_avl_qty_panel=new JPanel(new GridLayout(1,4,2,2));
        JPanel free_tamt_btn_panel=new JPanel(new BorderLayout());
        JPanel shouth_amount_btn_panel=new JPanel(new BorderLayout());
        JPanel amount_panel=new JPanel(new GridLayout(3,3,5,5));
        JPanel btn_panel=new JPanel(new GridLayout(1,9,10,10));
        JPanel pay_mood_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel pay_stetus_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel gt_amount_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel d_amt_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel pay_amt_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel sgst_amt_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel cgst_amt_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel freeqty_panel=new JPanel(new BorderLayout());
        
        this.add(mainPanel,BorderLayout.CENTER);
        mainPanel.add(new JLabel("  "),BorderLayout.NORTH);mainPanel.add(new JLabel("  "),BorderLayout.EAST);
        mainPanel.add(new JLabel("  "),BorderLayout.WEST);mainPanel.add(new JLabel("  "),BorderLayout.SOUTH);
        mainPanel.add(mainbilldetailPanel,BorderLayout.CENTER);
        mainbilldetailPanel.add(mainbilldetailPanel1,BorderLayout.NORTH);
        mainbilldetailPanel.add(mainbilldetailPanel2,BorderLayout.CENTER);
        mainbilldetailPanel1.add(bdetail1GridPanel,BorderLayout.CENTER);
        bdetail1GridPanel.add(bdetail1);bdetail1GridPanel.add(bdetail2);
        bdetail1.add(billDatePanel);bdetail1.add(cust_nameBtnPanel);bdetail1.add(cust_address);
        cust_nameBtnPanel.add(cust_name,BorderLayout.CENTER);//cust_nameBtnPanel.add(btplus,BorderLayout.EAST);
        billDatePanel.add(bill_date);billDatePanel.add(invoice_no);
        bdetail2.add(cust_mob);bdetail2.add(cust_dl_no);bdetail2.add(cust_gstin);bdetail2.add(new JLabel(" "));
        mainbilldetailPanel1.add(billdetailPanel,BorderLayout.SOUTH);
        billdetailPanel.add(productDetailPanel,BorderLayout.CENTER);billdetailPanel.add(labelPanel,BorderLayout.NORTH);
        labelPanel.add(l2,BorderLayout.CENTER);labelPanel.add(new JLabel("  "),BorderLayout.NORTH);labelPanel.add(new JLabel("  "),BorderLayout.SOUTH);
        productDetailPanel.add(bdetail3,BorderLayout.CENTER);productDetailPanel.add(new JLabel("  "),BorderLayout.SOUTH);
        bdetail3.add(product_name);bdetail3.add(mrp_rate_disc_panel);bdetail3.add(gst_avl_qty_panel);bdetail3.add(free_tamt_btn_panel);
        mrp_rate_disc_panel.add(product_mrp);mrp_rate_disc_panel.add(product_rate);mrp_rate_disc_panel.add(product_disc);
        gst_avl_qty_panel.add(product_cgst);gst_avl_qty_panel.add(product_sgst);gst_avl_qty_panel.add(avil_qty);gst_avl_qty_panel.add(product_quintity);
        freeqty_panel.add(free_qty,BorderLayout.CENTER);freeqty_panel.setPreferredSize(new Dimension(100,50));
        free_tamt_btn_panel.add(freeqty_panel,BorderLayout.WEST);free_tamt_btn_panel.add(total_amount,BorderLayout.CENTER);free_tamt_btn_panel.add(btadd,BorderLayout.EAST);
        mainbilldetailPanel2.add(scr,BorderLayout.CENTER);mainbilldetailPanel2.add(shouth_amount_btn_panel,BorderLayout.SOUTH);
        shouth_amount_btn_panel.add(amount_panel,BorderLayout.CENTER);shouth_amount_btn_panel.add(btn_panel,BorderLayout.SOUTH);
        cgst_amt_panel.add(cgstlabel);cgst_amt_panel.add(cgst_amt);
        sgst_amt_panel.add(sgstlabel);sgst_amt_panel.add(sgst_amt);
        amount_panel.add(cgst_amt_panel);amount_panel.add(sgst_amt_panel);amount_panel.add(gt_amount_panel);
        amount_panel.add(pay_mood_panel); amount_panel.add(pay_mood_status);amount_panel.add(d_amt_panel);
        amount_panel.add(pay_stetus_panel);amount_panel.add(pay_status_status);amount_panel.add(pay_amt_panel);
        gt_amount_panel.add(grandtotalamount);gt_amount_panel.add(grand_total_amount);
        pay_mood_panel.add(paymoodlabel);pay_mood_panel.add(pay_mood);
        d_amt_panel.add(sumamount);d_amt_panel.add(disc_amount);
        pay_stetus_panel.add(paystatuslabel);pay_stetus_panel.add(pay_status);
        pay_amt_panel.add(totaldiscamount);pay_amt_panel.add(pay_amount);
        btn_panel.add(new JLabel("  "));btn_panel.add(new JLabel("  "));btn_panel.add(new JLabel("  "));btn_panel.add(new JLabel("  "));
        btn_panel.add(bcancel);btn_panel.add(print);btn_panel.add(sprint);btn_panel.add(reset);btn_panel.add(close);
        
        
        
        pay_mood.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(pay_mood.getSelectedIndex()!=-1)
                {
                    if(pay_mood.getSelectedItem().toString().equals("CHEQUE"))
                    {
                        pay_mood_status.setText("");
                        pay_mood_status.setText("THANKS! YOU HAVE PAYMENT TO CHEQUE!");
                    }
                    else if(pay_mood.getSelectedItem().toString().equals("CARD"))
                    {
                        pay_mood_status.setText("");
                        pay_mood_status.setText("THANKS! YOU HAVE PAYMENT TO CARD!");
                    }
                    else
                    {
                        pay_mood_status.setText("");
                        pay_mood_status.setText("THANKS! YOU HAVE PAYMENT TO CASH! ");
                    }
                }
            }});
        
        
        pay_status.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(pay_status.getSelectedIndex()!=-1)
                {
                    if(pay_status.getSelectedItem().toString().equals("PAID"))
                    {
                        pay_status_status.setText("PAID");
                        pay_status_status.setForeground(Color.GREEN);
                    }
                    else if(pay_status.getSelectedItem().toString().equals("ADVANCE"))
                    {
                        pay_status_status.setText("ADVANCE");
                        pay_status_status.setForeground(Color.PINK);
                    }
                    else
                    {
                        pay_status_status.setText("DUES");
                        pay_status_status.setForeground(Color.red);
                    }
                }
            }});
           
           cust_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                  setToCustomerDetails();
                  product_name.requestFocusInWindow();
            }}});
        
           cust_mob.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(cust_mob.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(KModifyWindow.this, "Mobile No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
                        cust_mob.setText("");
                        cust_mob.requestFocusInWindow();return;
                    }
                  //new ClientWindow(Billing.this).setVisible(true);
                  product_name.requestFocusInWindow();
            }}});
           product_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                  new KMSelectProduct(KModifyWindow.this).setVisible(true);  
                  product_rate.requestFocusInWindow();
            }}});
            product_rate.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                   product_disc.requestFocusInWindow();
            }}});
            
            product_rate.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                product_rate.setSelectionStart(0);
                product_rate.setSelectionEnd(product_rate.getText().length());
                product_rate.setSelectionColor(Color.BLUE);
                product_rate.setSelectedTextColor(Color.WHITE);
                total_amount.setText(product_rate.getText());
            }
            public void focusLost(FocusEvent e) {
                
            } });
            
            product_disc.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    product_quintity.requestFocusInWindow();
            }}});
            product_disc.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                product_disc.setSelectionStart(0);
                product_disc.setSelectionEnd(product_disc.getText().length());
                product_disc.setSelectionColor(Color.BLUE);
                product_disc.setSelectedTextColor(Color.WHITE);
                //total_amount.setText(product_rate.getText());
            }
            public void focusLost(FocusEvent e) {
                
            } });
            product_quintity.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(product_quintity.getText().equals("0"))
                        product_quintity.requestFocusInWindow();
                    else
                    free_qty.requestFocusInWindow();
            }}
            public void keyReleased(KeyEvent e) {
                try
                {
                int n=Integer.parseInt(product_quintity.getText());
                int q=Integer.parseInt(avil_qty.getText().trim());
                   if(n>q)
                    {
                        JOptionPane.showMessageDialog(null,"Only "+q+" item(s) avialable.");
                        product_quintity.setText("");
                        product_quintity.requestFocusInWindow();
                    }
                }catch(NumberFormatException ex){}
            }});
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
           
            free_qty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(free_qty.getText().equals(""))
                        free_qty.requestFocusInWindow();
                    else
                    {
                        if(flag==true)
                        {
                            updateRecord(billno);
                            empaty();
                            setBillDataInTable(billno);
                        }
                        else
                            btadd.requestFocusInWindow();
                    }
            }}
            public void keyReleased(KeyEvent e) {
                try
                {
                int n=Integer.parseInt(free_qty.getText());
                int nq=Integer.parseInt(product_quintity.getText());
                int q=(Integer.parseInt(avil_qty.getText().trim()))-nq;
                    if(n>q)
                    {
                        JOptionPane.showMessageDialog(null,"Only "+q+" item(s) avialable.");
                        free_qty.setText("");
                        free_qty.requestFocusInWindow();
                    }
                }catch(NumberFormatException ex){}
            }});
            free_qty.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                free_qty.setSelectionStart(0);
                free_qty.setSelectionEnd(free_qty.getText().length());
                free_qty.setSelectionColor(Color.BLUE);
                free_qty.setSelectedTextColor(Color.WHITE);
            }
            public void focusLost(FocusEvent e) {
                
            } });
            
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
                    empaty();
                    saveBill(billno);
                    pempaty();
                    setBillDataInTable(billno);
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
                    empaty();
                    saveBill(billno);
                    pempaty();
                    setBillDataInTable(billno);
                    product_name.requestFocusInWindow();
            }
        });
        bcancel.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    deleteRow(table.getSelectedRow());
            }}});
        bcancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteRow(table.getSelectedRow());
            }
        });
        
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    KModifyWindow.this.dispose();
            }}});
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KModifyWindow.this.dispose();
            }
        });
        reset.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    empaty();
                    pempaty();
            }}});
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                empaty();
                pempaty();
            }
        });
        print.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printKBill(billno);
            }});
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.printKBill(billno);
            }
        });
        
        sprint.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printStemitBill(billno);
            }});
        sprint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.printStemitBill(billno);
            }
        });
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2)
                {
                    if(JOptionPane.showConfirmDialog(KModifyWindow.this,"Are You Sure To Update Row ?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    {   
                        int sel_row=table.getSelectedRow();
                        if(sel_row!=-1)
                        {
                            flag=true;
                            try
                            {
                                float t_amount=Float.parseFloat(table.getValueAt(sel_row,12).toString());
                                float pcgst=Float.parseFloat(table.getValueAt(sel_row,8).toString());
                                float psgst=Float.parseFloat(table.getValueAt(sel_row,9).toString());
                                float disc=0,d;
                                d=Float.parseFloat(table.getValueAt(sel_row, 7).toString().trim());
                                disc=t_amount*d/100; 
                                String gta=grand_total_amount.getText().trim();
                                gta=gta.substring(0,gta.length()-2).trim();
                                String pa=pay_amount.getText().trim();
                                pa=pa.substring(0,pa.length()-2);
                                float cgst=Float.parseFloat(cgst_amt.getText().trim());
                                float sgst=Float.parseFloat(sgst_amt.getText().trim());
                                float cgstamt=t_amount*pcgst/100;
                                float sgstamt=t_amount*psgst/100;
                                String da=disc_amount.getText().trim();
                                da=da.substring(0,da.length()-2).trim();
                                grand_total_amount.setText(String.format("%.02f",Float.parseFloat(gta)-t_amount)+"   \u20B9     ");
                                sum_amount=sum_amount-t_amount;
                                cgst_amt.setText(String.format("%.02f", cgst-cgstamt));
                                sgst_amt.setText(String.format("%.02f", sgst-sgstamt));
                                float d_amt=t_amount-disc;
                                float g_amt=d_amt+cgstamt+sgstamt;
                                pay_amount.setText(String.format("%.02f",Float.parseFloat(pa)-g_amt)+"   \u20B9     ");
                                totalsum=totalsum-g_amt;
                                disc_amount.setText(String.format("%.02f",Float.parseFloat(da)-disc)+"   \u20B9     ");
                                disamount=disamount-disc;
                                old_qty=Integer.parseInt(table.getValueAt(sel_row, 10).toString().trim());
                                old_free_qty=Integer.parseInt(table.getValueAt(sel_row, 11).toString().trim());
                                product_name.setText(table.getValueAt(sel_row, 0).toString().trim());
                                product_mrp.setText(table.getValueAt(sel_row, 5).toString().trim());
                                product_rate.setText(table.getValueAt(sel_row, 6).toString().trim());
                                product_disc.setText(table.getValueAt(sel_row, 7).toString().trim());
                                product_sgst.setText(table.getValueAt(sel_row, 8).toString().trim());
                                product_cgst.setText(table.getValueAt(sel_row, 9).toString().trim());
                                product_quintity.setText(table.getValueAt(sel_row, 10).toString().trim());
                                free_qty.setText(table.getValueAt(sel_row, 11).toString().trim());
                                p_hsn_code=table.getValueAt(sel_row, 1).toString().trim();
                                batch_no=table.getValueAt(sel_row, 2).toString().trim();
                                mfg=table.getValueAt(sel_row, 3).toString().trim();
                                exp=table.getValueAt(sel_row, 4).toString().trim();
                                ResultSet pr=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batch_no+"'");
                                pr.next();
                                avil_qty.setText(pr.getString("quintity"));
                                ResultSet brset=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill_items where k_retail_bill_no="+billno+" and product_id ='"+pr.getInt("product_id")+"'");
                                brset.next();
                                sr_no=brset.getString("sr_no");
                                selitem--;
                            }catch(NumberFormatException ex){}
                            catch(SQLException ex){}
                            tableModel.removeRow(sel_row);
                            product_rate.requestFocusInWindow();
                        }
                    }
                }
            }});
        
        
        
        setBillDataInTable(billno);
        //setBillNo();
    }
    
public void kmsetItemsValue(String[] s)
    {
        product_name.setText((s[0]).toUpperCase());
        Formatter frate=new Formatter(); 
        batch=s[1];
        frate.format("%.02f",Float.parseFloat(s[2]));
        product_rate.setText(s[2]);
        float sgst=(Float.parseFloat((s[3])))/2;
        float cgst=(Float.parseFloat((s[3])))/2;
        Formatter fsgst=new Formatter(); 
        fsgst.format("%.02f",sgst);
        product_sgst.setText(fsgst+"");
        Formatter fcgst=new Formatter(); 
        fcgst.format("%.02f",sgst);
        product_cgst.setText(fcgst+"");
        Formatter fmrp=new Formatter(); 
        fmrp.format("%.02f",Float.parseFloat(s[4]));
        product_mrp.setText(fmrp+"");
        expary=s[5];
        mfg=s[6];
        p_quintity=(s[9]);
        avil_qty.setText(p_quintity);
        product_quintity.setText("0");
        free_qty.setText("0");
        mri=s[8];
        p_id=s[7];
        p_hsn_code=s[10];
        product_disc.setText("0.0");
        p_amt=pay_amount.getText().trim();
        p_amt=p_amt.substring(0,p_amt.length()-2).trim();
    }

    public void setBillDataInTable(String bill)
    {
        selitem=0;
        try
        {//bdate,custname,address,dlno,date,bilno,mno
        ResultSet b=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill where k_retail_bill_no like'"+bill+"'");
        b.next();
        ResultSet c=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+b.getString("customer_id")+"'");
        c.next();
        bill_date.setText(b.getString("k_retail_bill_date"));
        cust_name.setText(c.getString("customer_name").toUpperCase());
        cust_address.setText(c.getString("customer_address").toUpperCase());
        cust_dl_no.setText(c.getString("customer_dl").toUpperCase());
        invoice_no.setText("RGST00"+bill);
        cust_mob.setText(c.getString("customer_mob"));
        cust_gstin.setText(c.getString("customer_gst"));
        pay_mood.setSelectedItem(b.getString("kpay_mode"));
        pay_status.setSelectedItem(b.getString("kpay_stetus"));
        
        ResultSet s=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill_items where k_retail_bill_no like'"+bill+"'");
        int tablerow=0;
        float subamt=0,disamt=0,net_amt=0,cgst_amount=0,sgst_amount=0;
        while(s.next())
        {
            ResultSet p=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+s.getString("product_id")+"'");
            p.next();
            ResultSet m=StaticMember.con.createStatement().executeQuery("select * from manifacture where manifacture_id like'"+s.getString("manifacture_id")+"'");
            m.next();
            ResultSet avi=StaticMember.con.createStatement().executeQuery("select * from  availableitem where product_id like'"+s.getString("product_id")+"'");
            avi.next();
            ResultSet t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+p.getString("type_id")+"'");
            t.next();
            srno[selitem]=s.getString("sr_no");
            String product_name=p.getString("product_name")+" "+t.getString("type_name")+" "+avi.getString("packing");
            float prate=Float.parseFloat(s.getString("kprice"));
            float pqty=Float.parseFloat(s.getString("qty"));
            float disc=Float.parseFloat(s.getString("kdisc"));
            net_amt=prate-disc;
            float t_amount=prate*pqty;
            float gst=Float.parseFloat(p.getString("product_gst"));
            float sgst=gst/2;
            tableModel.addRow(new Object[]{"  "+product_name+"  ","  "+p.getString("product_hsn_code")+"  ","  "+s.getString("batch_no")+"  ","  "+m.getString("manifacture_name")+"  ","  "+s.getString("exp_date")+"  ","  "+String.format("%.02f",Float.parseFloat(s.getString("mrp")))+"  ","  "+String.format("%.02f",prate)+"  ","  "+String.format("%.02f",Float.parseFloat(s.getString("kdisc")))+"  ","  "+String.format("%.02f",sgst)+"  ","  "+String.format("%.02f",sgst)+"  ","  "+s.getString("qty")+"  ","  "+s.getString("free_qty")+"  ","  "+String.format("%.02f",t_amount)+"  "});
            
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
            Formatter fcgst=new Formatter(); //vat Amount
            fcgst.format("%.02f",sgst_amount);
            Formatter fsgst=new Formatter(); //vat Amount
            fsgst.format("%.02f",sgst_amount);
            grand_total_amount.setText(fsa+"   \u20B9     ");
            disc_amount.setText(fda+"   \u20B9     ");
            pay_amount.setText(fgta+"   \u20B9     ");
            cgst_amt.setText(fcgst+"");
            sgst_amt.setText(fsgst+"");
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"set bill data",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    float sum_amount=0,totalsum=0,dis=0,disamount=0;
    
    //save and Print Data 
   
   
    private void saveBill(String bill)
    {
        
        try
        {
            PreparedStatement bsmt=StaticMember.con.prepareStatement("insert into k_retail_bill_items(sr_no,k_retail_bill_no,qty,free_qty,kprice,kdisc,product_id,batch_no,manifacture_id,mrp,exp_date) values(?,?,?,?,?,?,?,?,?,?,?)");
            ResultSet sr=StaticMember.con.createStatement().executeQuery("select max(sr_no) as sr_no  from k_retail_bill_items");
            sr.next();
            bsmt.setInt(1,sr.getInt("sr_no")+1);
            bsmt.setString(2,bill);
            int qty=Integer.parseInt(product_quintity.getText().trim());
            bsmt.setInt(3, qty);
            int frqty=Integer.parseInt(free_qty.getText().trim());
            bsmt.setInt(4, frqty);
            float pgst=Float.parseFloat(product_sgst.getText().trim())*2;
            float price=Float.parseFloat(product_rate.getText().trim());
            float disc=Float.parseFloat(product_disc.getText().trim());
            float tamt=price*qty;
            float sgst=tamt*pgst/100;
            float disamt=tamt*disc/100;
            float netamt=tamt-disamt;
            bsmt.setFloat(5, price);
            bsmt.setFloat(6, disc);
            bsmt.setString(7, p_id);
            bsmt.setString(8, "  "+batch);
            bsmt.setString(9,mri);
            bsmt.setString(10,product_mrp.getText());
            bsmt.setString(11,expary);
            bsmt.execute();
            PreparedStatement ssmt=StaticMember.con.prepareStatement("update k_retail_bill set kpay_amount=? where k_retail_bill_no=?");    
            float pamt=Float.parseFloat(p_amt);
            ssmt.setFloat(1, pamt+netamt+sgst);
            ssmt.setString(2, bill);
            ssmt.execute();
            empaty();
            MDIMainWindow.k_b_m_obj.allBill();
            JOptionPane.showMessageDialog(KModifyWindow.this,"Bill Updated!",null,JOptionPane.INFORMATION_MESSAGE);
            product_name.requestFocusInWindow();
                
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"save bill",JOptionPane.INFORMATION_MESSAGE);
        }catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"save bill3",JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }
    
    public void updateRecord(String bill)
    {
        try
        {
            PreparedStatement ssmt=StaticMember.con.prepareStatement("Update k_retail_bill_items set kprice=?,qty=?,kdisc=?,free_qty=? where sr_no=?");
           
            float price=Float.parseFloat(product_rate.getText());
            float disc=Float.parseFloat(product_disc.getText());
            int newqty=Integer.parseInt(product_quintity.getText());
            int newfreeqty=Integer.parseInt(free_qty.getText());
            float gst=Float.parseFloat(product_cgst.getText())*2;
            int pqty=newqty+newfreeqty;
            ssmt.setFloat(1,price);
            ssmt.setInt(2, newqty);
            ssmt.setFloat(3, disc);
            ssmt.setInt(4, newfreeqty);
            ssmt.setString(5,sr_no);
            ssmt.execute();
            PreparedStatement bsmt=StaticMember.con.prepareStatement("update k_retail_bill set kpay_mode=?,kpay_stetus=?,kpay_amount=? where k_retail_bill_no=?");
            float tot_amt=price*newqty;
            String pa=pay_amount.getText().trim();
            pa=pa.substring(0,pa.length()-2).trim();
            float pay_amt=Float.parseFloat(pa);
            float dis_amt=tot_amt*disc/100;
            float gstamt=tot_amt*gst/100;
            float net_amt=tot_amt-dis_amt;
            float grand_amt=pay_amt+net_amt+gstamt;
            bsmt.setString(1,pay_mood.getSelectedItem().toString());
            bsmt.setString(2,pay_status.getSelectedItem().toString());
            bsmt.setFloat(3, grand_amt);
            bsmt.setString(4, bill);
            bsmt.execute();
            sr_no="";pempaty();
            MDIMainWindow.k_b_m_obj.allBill();
            JOptionPane.showMessageDialog(null, "Update Succecfull","wow",JOptionPane.INFORMATION_MESSAGE);
            product_name.requestFocusInWindow();
            }
            catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
            //empaty();
            //setBillNo();
            //JOptionPane.showMessageDialog(ModifyWindow.this,"Bill Saved!",null,JOptionPane.INFORMATION_MESSAGE);
            
                
    }
    
    public void empaty()
    {
        srno=new String[30];
        pri="";
        batch_no="";
        exp="";
        mfg="";
        cgst_amt.setText("");
        sgst_amt.setText("");
        cust_name.setText("");
        cust_address.setText("");
        cust_gstin.setText("");
        cust_dl_no.setText("");
        invoice_no.setText("");
        grand_total_amount.setText("   \u20B9     ");
        pay_amount.setText("   \u20B9     ");
        disc_amount.setText("   \u20B9     ");
        pay_mood.setSelectedIndex(-1);
        pay_status.setSelectedIndex(-1);
        pay_status_status.setText("");
        pay_mood_status.setText("");
        cust_mob.setText("");
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
        sum_amount=0;
        totalsum=0;
        dis=0;disamount=0;
    }
    
    
    private void setToCustomerDetails()
    {
        try{
                ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT  * FROM customer where customer_id like'"+customer_id.get(customer_items.indexOf(cust_name.getText()))+"'");
                rset.next();
                cust_mob.setText(rset.getString("customer_mob").toString());
                cust_address.setText(rset.getString("customer_address").toString());
                cust_dl_no.setText(rset.getString("customer_dl").toString());
                cust_gstin.setText(rset.getString("customer_gst").toString());
            }catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
            }
            JTextFieldAutoComplete.setupAutoComplete(cust_name, customer_items,false);
    }
    public void pempaty()
    {
        product_cgst.setText("");
        product_disc.setText("");
        product_name.setText("");
        product_quintity.setText("");
        product_rate.setText("");
        product_mrp.setText("");
        avil_qty.setText("");
        total_amount.setText("");
        free_qty.setText("");
        product_sgst.setText("");
    }
    
   private void deleteRow(int sel_row)
   {
       if(JOptionPane.showConfirmDialog(MDIMainWindow.self,"Are You Sure To Delete Row ?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        {   
            if(sel_row!=-1)
            {
                try
                {
                    float t_amount=Float.parseFloat(table.getValueAt(sel_row,12).toString());
                    float pcgst=Float.parseFloat(table.getValueAt(sel_row,8).toString());
                    float psgst=Float.parseFloat(table.getValueAt(sel_row,9).toString());
                    float disc=0,d;
                    d=Float.parseFloat(table.getValueAt(sel_row, 7).toString().trim());
                    disc=t_amount*d/100; 
                    String gta=grand_total_amount.getText().trim();
                    gta=gta.substring(0,gta.length()-2).trim();
                    String pa=pay_amount.getText().trim();
                    pa=pa.substring(0,pa.length()-2);
                    float cgst=Float.parseFloat(cgst_amt.getText().trim());
                    float sgst=Float.parseFloat(sgst_amt.getText().trim());
                    float cgstamt=t_amount*pcgst/100;
                    float sgstamt=t_amount*psgst/100;
                    String da=disc_amount.getText().trim();
                    da=da.substring(0,da.length()-2).trim();
                    grand_total_amount.setText(String.format("%.02f",Float.parseFloat(gta)-t_amount)+"   \u20B9     ");
                    sum_amount=sum_amount-t_amount;
                    cgst_amt.setText(String.format("%.02f", cgst-cgstamt));
                    sgst_amt.setText(String.format("%.02f", sgst-sgstamt));
                    float d_amt=t_amount-disc;
                    float g_amt=d_amt+cgstamt+sgstamt;
                    pay_amount.setText(String.format("%.02f",Float.parseFloat(pa)-g_amt)+"   \u20B9     ");
                    totalsum=totalsum-g_amt;
                    disc_amount.setText(String.format("%.02f",Float.parseFloat(da)-disc)+"   \u20B9     ");
                    disamount=disamount-disc;
                    int oldqty=Integer.parseInt(table.getValueAt(sel_row, 10).toString().trim());
                    int oldfqty=Integer.parseInt(table.getValueAt(sel_row, 11).toString().trim());
                    String batchid=table.getValueAt(sel_row, 2).toString().trim();
                    ResultSet pr=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batchid+"'");
                    pr.next();
                    ResultSet brset=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill_items where k_retail_bill_no="+billno+" and product_id ='"+pr.getInt("product_id")+"'");
                    brset.next();
                    StaticMember.con.createStatement().execute("Delete from k_retail_bill_items where sr_no like'"+brset.getString("sr_no")+"'");
                    JOptionPane.showMessageDialog(this, " Delete!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
                    selitem--;
                    
                }catch(NumberFormatException ex){}
                catch(SQLException ex){}
                tableModel.removeRow(sel_row);
                product_rate.requestFocusInWindow();
            }
        }
   }
    
   public void updatePayment(float amt,String bill,float bamt,float damt)
   {
       try
       {
           float dues=0,amount=0,pamt=0;
           if(amt<=damt)
           {
               Formatter fdues=new Formatter().format("%.02f", damt-amt);
               dues=Float.parseFloat(fdues+"");
               pamt=amt;
           }
           else
           {
               Formatter famount=new Formatter().format("%.02f", amt-damt);
                pamt=bamt;
                dues=damt-damt;
                JOptionPane.showMessageDialog(KModifyWindow.this, "Plz Collect Refund Amount Rs. "+famount+" ");
               
           }
           Calendar cl=Calendar.getInstance();
           Formatter fmt=new Formatter();
           fmt.format("%tH:%tM:%tS",cl,cl,cl);
           Formatter fmd=new Formatter();
           fmd.format("%tY-%tm-%td",cl,cl,cl);
           PreparedStatement bsmt=StaticMember.con.prepareStatement("update k_retail_bill set payment=?,dues=?,payment_date=?,payment_time=? where retail_bill_no=?");
           bsmt.setFloat(1, pamt);
           bsmt.setFloat(2, dues);
           bsmt.setString(3, fmd+"");
           bsmt.setString(4, fmt+"");
           bsmt.setString(5, bill);
           bsmt.executeUpdate();
           JOptionPane.showMessageDialog(KModifyWindow.this,"Payment Update");
       }
       catch(SQLException ex)
       {
           JOptionPane.showMessageDialog(KModifyWindow.this,ex.getMessage());
       }
   }
}