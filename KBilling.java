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
public class KBilling extends JInternalFrame{
    static JTextField cust_name;
    private JTextField cust_dl_no,cust_address,cust_mob,cust_gstin,product_quintity,free_qty,avil_qty,product_sgst,product_disc,product_name,product_hsn,product_mrp,product_rate,p_rate,product_cgst,total_amount;
    private JLabel bill_date,billno,grand_total_amount,disc_amount,cgst_amt,sgst_amt,pay_status_status,pay_amount,pay_mood_status;
    private JButton rbsave,reset,print,btplus,btadd,dispatch,close,sprint;
    private JComboBox pay_mood,pay_status;
    private JTable table;
    private String batch_no,exp,mfg;
    private DefaultTableModel tableModel;
    private String[] data;
    private String bill="";
    private String rbill="";
    private String p_id,m_id,p_hsn_code,p_quintity;
    private int selitem=0,numberOfRow=0;;
    private ArrayList<String> customer_id=new ArrayList<>();
    private ArrayList<String> customer_items=new ArrayList<>();
    private ArrayList<String> product_id=new ArrayList<>();
    private ArrayList<String> mfg_id=new ArrayList<>();
    private ArrayList<String> batch_id=new ArrayList<>();
    
    public KBilling(String kbd[])
    {
        super("",true,true);
        StaticMember.setSize(this);
        this.setDefaultCloseOperation(KBilling.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        
        btadd=StaticMember.AddButton("/images/plus.png",40,40,"Click On Button To Add Recond In Table");
        btplus=StaticMember.AddButton("/images/plus.png",40,40,"Click On Button To Add More Customer");
        
        StaticMember.lookAndFeel();
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.K_BILLING=false;
              }});
        
        data=kbd;
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
        si.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,KeyEvent.CTRL_MASK));
        m1.add(mi);
        m1.add(mprint);
        m1.add(msave);
        m1.add(si);
        bar.add(m1);
        this.add(bar);
        mprint.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.printKBill(rbill);
            }        });
        msave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                saveBill();
                if(JOptionPane.showConfirmDialog(null,"Do You Want To Print Bill ?","Confirmtion Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                {
                    StaticMember.printKBill(rbill);
                }
            }        });
        mi.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new KSelectProduct(KBilling.this).setVisible(true);
                product_rate.requestFocusInWindow();
            }        });
        si.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                empaty();
                pempaty();
            }        });
        
         JLabel h=new JLabel(" STIMEET BILLING",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        JLabel producttotalamt=new JLabel("TOTAL AMOUNT \u20B9",JLabel.CENTER);
        producttotalamt.setFont(StaticMember.labelFont);
        JLabel sumamount=new JLabel("DISE AMOUNT :",JLabel.RIGHT);
        sumamount.setFont(StaticMember.labelFont);
        JLabel totalgstamount=new JLabel("TOTAL GST AMOUNT :",JLabel.RIGHT);
        totalgstamount.setFont(StaticMember.labelFont);
        JLabel totaldiscamount=new JLabel("PAYABLE AMOUNT :",JLabel.RIGHT);
        totaldiscamount.setFont(StaticMember.labelFont);
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
        pay_mood_status=new JLabel("");
        pay_mood_status.setFont(StaticMember.textFont);
        pay_status_status=new JLabel("");
        pay_status_status.setFont(StaticMember.textFont);
        
        
        //cteate TextField
        bill_date=StaticMember.MyJLabel(StaticMember.getDate()," DATE ");
        billno=StaticMember.MyJLabel(""," BILL NO ");
        //cteate TextField
        cust_name=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,StaticMember.UPPER_CASE,"Enter Customer Name"," PARTY NAME");
        StaticMember.setCustomerdata(cust_name, customer_id, customer_items);
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
        product_rate.setHorizontalAlignment(SwingConstants.RIGHT);
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
        String []statusstr={"","ADVANCE","DUE","PAID"};
        pay_status=new JComboBox(statusstr);
        pay_status.setFont(StaticMember.textFont);
        
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
        rbsave=StaticMember.MyButton("SAVE","Click On Button To Save The Record");
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
        cust_nameBtnPanel.add(cust_name,BorderLayout.CENTER);cust_nameBtnPanel.add(btplus,BorderLayout.EAST);
        billDatePanel.add(bill_date);billDatePanel.add(billno);
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
        btn_panel.add(rbsave);btn_panel.add(print);btn_panel.add(sprint);btn_panel.add(reset);btn_panel.add(close);
        
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2)
                {
                    if(JOptionPane.showConfirmDialog(MDIMainWindow.self,"Are You Sure To Delete Row ?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    {   
                        int sel_row=table.getSelectedRow();
                        if(sel_row!=-1)
                        {
                            try
                            {
                                float t_amount=Float.parseFloat(table.getValueAt(sel_row,12).toString());
                                float gst1=Float.parseFloat(table.getValueAt(sel_row,8).toString());
                                float cgst=t_amount*gst1/100;
                                String sgst=sgst_amt.getText().trim();
                                sgst=sgst.substring(0,sgst.length()-2).trim();
                                sgstamt=sgstamt-cgst;
                                cgst_amt.setText(String.format("%.02f", Float.parseFloat(sgst)-cgst)+"  \u20B9  ");
                                sgst_amt.setText(String.format("%.02f", Float.parseFloat(sgst)-cgst)+"  \u20B9  ");
                                sumgst=sumgst-(cgst+cgst);
                                float disc=0,d;
                                d=Float.parseFloat(table.getValueAt(sel_row, 7).toString().trim());
                                disc=t_amount*d/100; 
                                String gta=grand_total_amount.getText().trim();
                                gta=gta.substring(0,gta.length()-2).trim();
                                String pa=pay_amount.getText().trim();
                                pa=pa.substring(0,pa.length()-2).trim();
                                String da=disc_amount.getText().trim();
                                da=da.substring(0,da.length()-2).trim();
                                sum_amount=sum_amount-t_amount;
                                grand_total_amount.setText(String.format("%.02f", Float.parseFloat(gta)-t_amount)+"   \u20B9     ");
                                totalsum=(totalsum+disc)-(cgst+cgst+t_amount);
                                pay_amount.setText(String.format("%.02f", (Float.parseFloat(pa)+disc)-(cgst+cgst+t_amount))+"   \u20B9     ");
                                disamount=disamount-disc;
                                disc_amount.setText(String.format("%.02f", Float.parseFloat(da)-disc)+"   \u20B9     ");
                                selitem--;
                        }catch(NumberFormatException ex){}
                        tableModel.removeRow(sel_row);
                    }
                }
            }
            }
            });
        
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
        
        pay_mood_status.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                   if(pay_mood.getSelectedItem().toString().equals("CHEQUE"))
                   {
                       pay_mood_status.setText("");
                       pay_mood_status.setText("THANKS! YOU HAVE PAYMENT TO CHEQUE!");
                   }
            }}});
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
            product_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                  new KSelectProduct(KBilling.this).setVisible(true);  
                  product_rate.requestFocusInWindow();
            }}});
            product_rate.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                   product_disc.requestFocusInWindow();
            }}
            });
            
            product_rate.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                product_rate.setSelectionStart(0);
                product_rate.setSelectionEnd(product_rate.getText().length());
                product_rate.setSelectionColor(Color.BLUE);
                product_rate.setSelectedTextColor(Color.WHITE);
                //total_amount.setText(product_rate.getText());
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
            }
            public void focusLost(FocusEvent e) {
            } });
            product_quintity.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(product_quintity.getText().equals("-1"))
                        product_quintity.requestFocusInWindow();
                    else
                    free_qty.requestFocusInWindow();
            }}
            public void keyReleased(KeyEvent e) {
                try
                {
                int n=Integer.parseInt(product_quintity.getText());
                int q=Integer.parseInt(avil_qty.getText());
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
                    if(free_qty.getText().equals("-1"))
                        free_qty.requestFocusInWindow();
                    else
                    btadd.requestFocusInWindow();
            }}
            public void keyReleased(KeyEvent e) {
                try
                {
                int n=Integer.parseInt(free_qty.getText());
                int nq=Integer.parseInt(product_quintity.getText());
                int q=(Integer.parseInt(avil_qty.getText()))-nq;
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
            
            btplus.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    codeToCustomer();
                    cust_name.requestFocusInWindow();
            }}});
            btplus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    codeToCustomer();
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
                    //product_name.requestFocusInWindow();
            }}});
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                empaty();
                pempaty();
                //RetailBillPage sbp1=new RetailBillPage(bill);
                    //product_name.requestFocusInWindow();
            }
        });
        print.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printKBill(rbill);
            }});
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               StaticMember.printKBill(rbill);
            }
        });
        sprint.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printStemitBill(rbill);
            }});
        sprint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               StaticMember.printStemitBill(rbill);
            }
        });
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    KBilling.this.dispose();
            }});
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               KBilling.this.dispose();
            }
        });
        
       setBillNo();
       
    }   
    
    
     //set data in text field an Label
    public void ksetItemsValue(String[] s)
    {
        product_name.setText((s[0]).toUpperCase());
        batch_no=s[1];
        /*try
        {
            ResultSet rset=StaticMember.con.createStatement().executeQuery("Select * from k_p_rate where cusomer_id="+customer_id.get(customer_items.indexOf(cust_name.getText().trim()))+"AND poduct_id='"+s[7]+"'");
            if(rset.next())
            {
                JOptionPane.showMessageDialog(null, StaticMember.myFormat(rset.getString("price")));
                product_rate.setText(String.format("%.02f", rset.getString("price")));
            }
            else
                product_rate.setText(StaticMember.myFormat(s[2]));
        }catch(SQLException ex)
        {
            
        }*/
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
        exp=s[5];
        mfg=s[6];
        p_quintity=(s[9]);
        product_quintity.setText("0");
        m_id=s[8];
        p_id=s[7];
        p_hsn_code=s[10];
        product_disc.setText("0.0");
        avil_qty.setText(s[9]);
        free_qty.setText("0");
        
        
        
    }
   
    
    float sum_amount=0,totalsum=0,dis=0,sumgst=0,disamount=0,sgstamt=0;
    public void setDataInTable()
    {
        if(product_name.getText().equals(""))return;
        int tq=0,q=0,nq=0;
        product_id.add(p_id);
        mfg_id.add(m_id);
        batch_id.add(batch_no);
        try
        {
            ResultSet pr=StaticMember.con.createStatement().executeQuery("select quintity from availableitem where batch_no like'"+batch_no+"'");
            pr.next();
            tq=Integer.parseInt(pr.getString("quintity"));
            q=Integer.parseInt(product_quintity.getText());
            //nq=Integer.parseInt(tfree.getText());
        }
        catch(SQLException ex){JOptionPane.showMessageDialog(null, ex.toString());}
        catch(NumberFormatException ex){}
        int i;
            if(q<=tq)
            {
                int sr=1;
                float sgst=0,cgst=0;
                sgst=((Float.parseFloat(product_sgst.getText())));
                cgst=((Float.parseFloat(product_cgst.getText())));
                tableModel.addRow(new Object[]{" "+product_name.getText()+" "," "+p_hsn_code+"  ","  "+batch_no+" ","  "+mfg+" ","  "+exp+" ","  "+String.format("%.02f",Float.parseFloat(product_mrp.getText()))+" "," "+String.format("%.02f",Float.parseFloat(product_rate.getText()))+"  "," "+String.format("%.02f",Float.parseFloat(product_disc.getText()))+"  "," "+cgst+" "," "+sgst+"  "," "+product_quintity.getText()+"  "," "+free_qty.getText()+"  "," "+String.format("%.02f",Float.parseFloat(total_amount.getText()))+"  "});
                
            try
            {
                float sa=Float.parseFloat(total_amount.getText());
                float da;
                if(!product_disc.getText().equals(""))
                    da=Float.parseFloat(product_disc.getText());
                else
                    da=0;
                sum_amount=sum_amount+sa;
                float p_gst=(Float.parseFloat(product_sgst.getText().trim()))+(Float.parseFloat(product_cgst.getText().trim()));
                float sum_gst=sa*p_gst/100;
                sumgst=sumgst+sum_gst;
                dis=sa*da/100;
                disamount=disamount+dis;
                totalsum=sumgst+sum_amount;
                totalsum=totalsum-disamount;
            }catch(NumberFormatException ex)    {}
                sgstamt=sumgst/2;
                //sumcgst=sumgst-sumsgst;
               //set Formatter 
                Formatter fsuma=new Formatter(); //sum amount
                fsuma.format("%.02f",sum_amount);
                Formatter fcgst=new Formatter(); //vat Amount
                fcgst.format("%.02f",sgstamt);
                Formatter fsgst=new Formatter(); //vat Amount
                fsgst.format("%.02f",sgstamt);
                Formatter fdisa=new Formatter(); //Discount Amount
                fdisa.format("%.02f",disamount);
                Formatter ftsum=new Formatter(); //total Amount
                ftsum.format("%.02f",totalsum);
                
                grand_total_amount.setText(fsuma+""+"   \u20B9     ");
                disc_amount.setText(fdisa+"   \u20B9     ");
                cgst_amt.setText(fcgst+"  \u20B9  ");
                sgst_amt.setText(fsgst+"  \u20B9  ");
                pay_amount.setText(ftsum+"   \u20B9     ");
                selitem++;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "this Product Is not Avialabel!");
                product_quintity.requestFocusInWindow();
               return;
            }
    }
    //set data in text field an Label
    
    
    //save and Print Data 
   
   
    private void saveBill()
    {
        try
        {
            PreparedStatement bsmt=StaticMember.con.prepareStatement("insert into k_retail_bill(k_retail_bill_no,k_retail_bill_date,customer_id,kpay_mode,kpay_stetus,kpay_amount,k_retail_bill_time,dues,payment) values(?,?,?,?,?,?,?,?,?)");
            ResultSet bill_rset=StaticMember.con.createStatement().executeQuery("select max(k_retail_bill_no) as k_retail_bill_no  from k_retail_bill");
            bill_rset.next();
            int bill=bill_rset.getInt("k_retail_bill_no")+1;
            bsmt.setInt(1, bill);
            bsmt.setString(2,StaticMember.getDate());
            bsmt.setString(3,customer_id.get(customer_items.indexOf(cust_name.getText())));
            bsmt.setString(4,pay_mood.getSelectedItem().toString());
            bsmt.setString(5,pay_status.getSelectedItem().toString());
            String pa=pay_amount.getText().trim();
            pa=pa.substring(0,pa.length()-2).trim();
            bsmt.setFloat(6, Float.parseFloat(pa));
            bsmt.setString(7, StaticMember.getTime());
            bsmt.setFloat(8,Float.parseFloat(pa));
            bsmt.setString(9,("0"));
            bsmt.execute();
            
            PreparedStatement ssmt=StaticMember.con.prepareStatement("insert into k_retail_bill_items(sr_no,k_retail_bill_no,qty,kprice,kdisc,product_id,batch_no,manifacture_id,free_qty,mrp,exp_date) values(?,?,?,?,?,?,?,?,?,?,?)");
            ResultSet sr=StaticMember.con.createStatement().executeQuery("select max(sr_no) as sr_no  from k_retail_bill_items");
            sr.next();
            int srno=sr.getInt("sr_no")+1;
            for(int i=0;i<table.getRowCount();i++)
            {
                ssmt.setInt(1,srno+i);
                ssmt.setInt(2,bill);
                int qty=Integer.parseInt(table.getValueAt(i, 10).toString().trim());
                ssmt.setInt(3, qty);
                float price=Float.parseFloat(table.getValueAt(i, 6).toString());
                ssmt.setFloat(4, Float.parseFloat(table.getValueAt(i,6).toString()));
                ssmt.setFloat(5, Float.parseFloat(table.getValueAt(i, 7).toString()));
                ssmt.setString(6, product_id.get(i));
                ssmt.setString(7, table.getValueAt(i, 2).toString());
                ssmt.setString(8,mfg_id.get(i));
                int freeqty=Integer.parseInt(table.getValueAt(i, 11).toString().trim());
                ssmt.setInt(9,freeqty);
                ssmt.setString(10,table.getValueAt(i, 5).toString().trim());
                ssmt.setString(11,table.getValueAt(i, 4).toString().trim());
                ssmt.execute();
                
            }
                 ResultSet b=StaticMember.con.createStatement().executeQuery("select k_retail_bill_no from k_retail_bill where k_retail_bill_time like'"+StaticMember.getTime()+"' AND k_retail_bill_date like'"+StaticMember.getDate()+"'");
                if(b.next())
                rbill=b.getString("k_retail_bill_no");
                empaty();
            JOptionPane.showMessageDialog(KBilling.this,"Bill Saved!",null,JOptionPane.INFORMATION_MESSAGE);
            
                
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
        product_id.clear();
        mfg_id.clear();
        batch_id.clear();
        p_id="";
        batch_no="";
        exp="";
        mfg="";
        cgst_amt.setText("");
        sgst_amt.setText("");
        cust_name.setText("");
        cust_gstin.setText("");
        cust_dl_no.setText("");
        billno.setText("");
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
        sgstamt=0;
        totalsum=0;
        dis=0;sumgst=0;disamount=0;
        avil_qty.setText("");
        free_qty.setText("");
        setBillNo();
    }
    private void codeToCustomer()
    {
        JInternalFrame AddNewCustomer=new JInternalFrame("",true,true);
        AddNewCustomer ancu=new AddNewCustomer();
                MDIMainWindow.desktop.add(ancu);
                ancu.setVisible(true);
                btplus.getModel();
                ancu.setResizable(false);
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
        
        product_disc.setText("");
        product_name.setText("");
        product_quintity.setText("");
        product_rate.setText("");
        product_mrp.setText("");
        product_sgst.setText("");
        total_amount.setText("");
        product_cgst.setText("");
        avil_qty.setText("");
        free_qty.setText("");
    }
    
   
   public void setBillNo()
   {
       int kbillno=0;
        try
        {
            ResultSet bill_rset=StaticMember.con.createStatement().executeQuery("select max(k_retail_bill_no) as k_retail_bill_no  from k_retail_bill");
            bill_rset.next();
            kbillno=bill_rset.getInt("k_retail_bill_no")+1;
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(KBilling.this, ex.getMessage(),"",JOptionPane.ERROR_MESSAGE);
        }
        billno.setText("RGST00"+kbillno);
   }
}
