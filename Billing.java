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
import static komalhealthcare.KHC.bill;
import static komalhealthcare.MDIMainWindow.desktop;

/**
 *
 * @author Randhir
 */
public class Billing extends JInternalFrame{
    public static JTextField cust_name;
    private JTextField cust_dl_no,cust_address,cust_mob,cust_gstin,product_quintity,free_qty,product_disc,product_name,product_hsn,product_mrp,product_rate,avi_qty,total_amount;
    private JLabel grand_total_amount,disc_amount,pay_amount,pay_status_status,pay_mood_status,invoice_no,bill_date;
    private JButton save,reset,print,btplus,btadd,close;
    private JComboBox pay_mood,pay_status;
    private JTable table;
    private String batch_no,exp,mfg;
    private DefaultTableModel tableModel;
    private String[] data;
    private boolean ch,flag=false;
    private String rbill="";
    private String p_id,m_id,a_id,p_hsn_code,p_quintity;
    private int selitem=0,numberOfRow=0,qty=0;
    private ArrayList<String> customer_id=new ArrayList<>();
    private ArrayList<String> customer_items=new ArrayList<>();
    private ArrayList<String> product_id=new ArrayList<>();
    private ArrayList<String> mfg_id=new ArrayList<>();
    private ArrayList<String> avil_id=new ArrayList<>();
    public Billing(String bd[])
    {
        super("Billing",true,true);
        StaticMember.setSize(this);
        this.setDefaultCloseOperation(Billing.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        
        
        btadd=StaticMember.AddButton("/images/plus.png",40,40,"Click On Button To Add Recond In Table");
        btplus=StaticMember.AddButton("/images/plus.png",40,40,"Click On Button To Add More Customer");
        
        StaticMember.lookAndFeel();
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.BILLING=false;
              }});
        
        data=bd;
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
                StaticMember.printSelBill(rbill);
            }        });
        msave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                saveBill();
                if(JOptionPane.showConfirmDialog(null,"Do You Want To Print Bill ?","Confirmtion Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                {
                    StaticMember.printSelBill(rbill);
                }
            }        });
        mi.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new SelectProduct(Billing.this).setVisible(true);
                product_rate.requestFocusInWindow();
            }        });
        si.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                empaty();
                pempaty();
            }        });
        
         JLabel h=new JLabel(" SALE BILLING",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
       /* //Access Date Frome System
         Calendar cl=Calendar.getInstance();
       Formatter fmt=new Formatter();
       fmt.format("%tY-%tm-%td",cl,cl,cl);
       s=fmt.toString();
       String sdate;
       Formatter fdmt=new Formatter();
       fdmt.format("%td-%tm-%tY",cl,cl,cl);
       sdate=fdmt.toString();*/
       
        JLabel sumamount=new JLabel("DISE AMOUNT :",JLabel.RIGHT);
        sumamount.setFont(StaticMember.labelFont);
        sumamount.setForeground(StaticMember.flcolor);
        JLabel totaldiscamount=new JLabel("PAYABLE AMOUNT :",JLabel.RIGHT);
        totaldiscamount.setFont(StaticMember.labelFont);
        totaldiscamount.setForeground(StaticMember.flcolor);
        JLabel grandtotalamount=new JLabel("GRAND TOTAL AMOUNT :",JLabel.RIGHT);
        grandtotalamount.setFont(StaticMember.labelFont);
        grandtotalamount.setForeground(StaticMember.flcolor);
        JLabel paymoodlabel=new JLabel("PAYMENT MODE :",JLabel.RIGHT);
        paymoodlabel.setFont(StaticMember.labelFont);
        JLabel paystatuslabel=new JLabel("PAYMENT STATUS :",JLabel.RIGHT);
        paystatuslabel.setFont(StaticMember.labelFont);
        
        
        //cteate TextField
        bill_date=StaticMember.MyJLabel(StaticMember.getDate(StaticMember.getDate())," DATE ");
        invoice_no=StaticMember.MyJLabel(""," BILL NO ");
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
        product_mrp=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE,false," MRP"," MRP");
        product_mrp.setHorizontalAlignment(SwingConstants.RIGHT);
        product_rate=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE," RATE"," RATE");
        product_rate.setHorizontalAlignment(SwingConstants.RIGHT);
        avi_qty=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," AVL QTY"," AVL QTY");
        avi_qty.setHorizontalAlignment(SwingConstants.CENTER);
        total_amount=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," Total Amount "," TOTAL AMOUNT");
        pay_mood_status=StaticMember.MyLabel("");
        pay_status_status=StaticMember.MyLabel("");
        total_amount.setHorizontalAlignment(SwingConstants.RIGHT);
        
        grand_total_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        grand_total_amount.setFont(StaticMember.textFont);
        disc_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        disc_amount.setFont(StaticMember.textFont);
        pay_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        pay_amount.setFont(StaticMember.textFont);
        pay_amount.setHorizontalAlignment(SwingConstants.RIGHT);
        String []moodstr={"","CASH","CARD","CHEQUE"};
        pay_mood=new JComboBox(moodstr);
        pay_mood.setFont(StaticMember.textFont);
        String []statusstr={"","ADVANCE","DUE","PAID"};
        pay_status=new JComboBox(statusstr);
        pay_status.setFont(StaticMember.textFont);
        
        String aname[]={"PRODUCTS","HSN","BATCH","MAKE","EXP","MRP \u20B9","RATE \u20B9","DISC%","NET AMT \u20B9","QTY","FREE","AMOUNT \u20B9"};
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
        table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        TableColumn column=null;
       
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(250);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(5);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(6);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(7);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(8);
        column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(9);
        column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(10);
        column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(11);
        column.setPreferredWidth(40);
        
        JScrollPane scr=new JScrollPane(table);
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        save=StaticMember.MyButton("SAVE","Click On Button To Save The Record");
        print=StaticMember.MyButton("PRINT","Click On Button To Print The Record");
        
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
        JPanel avl_qty_free_panel=new JPanel(new GridLayout(1,3,2,2));
        JPanel tamt_btn_panel=new JPanel(new BorderLayout());
        JPanel shouth_amount_btn_panel=new JPanel(new BorderLayout());
        JPanel amount_panel=new JPanel(new GridLayout(3,3,5,5));
        JPanel btn_panel=new JPanel(new GridLayout(1,9,10,10));
        JPanel pay_mood_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel pay_stetus_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel gt_amount_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel d_amt_panel=new JPanel(new GridLayout(1,2,5,5));
        JPanel pay_amt_panel=new JPanel(new GridLayout(1,2,5,5));
        
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
        billDatePanel.add(bill_date);billDatePanel.add(invoice_no);
        
        bdetail2.add(cust_mob);bdetail2.add(cust_dl_no);bdetail2.add(cust_gstin);bdetail2.add(new JLabel(" "));
        mainbilldetailPanel1.add(billdetailPanel,BorderLayout.SOUTH);
        billdetailPanel.add(productDetailPanel,BorderLayout.CENTER);billdetailPanel.add(labelPanel,BorderLayout.NORTH);
        labelPanel.add(l2,BorderLayout.CENTER);labelPanel.add(new JLabel("  "),BorderLayout.NORTH);labelPanel.add(new JLabel("  "),BorderLayout.SOUTH);
        productDetailPanel.add(bdetail3,BorderLayout.CENTER);productDetailPanel.add(new JLabel("  "),BorderLayout.SOUTH);
        
        bdetail3.add(product_name);bdetail3.add(mrp_rate_disc_panel);bdetail3.add(avl_qty_free_panel);bdetail3.add(tamt_btn_panel);
        mrp_rate_disc_panel.add(product_mrp);mrp_rate_disc_panel.add(product_rate);mrp_rate_disc_panel.add(product_disc);
        avl_qty_free_panel.add(avi_qty);avl_qty_free_panel.add(product_quintity);avl_qty_free_panel.add(free_qty);
        tamt_btn_panel.add(total_amount,BorderLayout.CENTER);tamt_btn_panel.add(btadd,BorderLayout.EAST);
        mainbilldetailPanel2.add(scr,BorderLayout.CENTER);mainbilldetailPanel2.add(shouth_amount_btn_panel,BorderLayout.SOUTH);
        shouth_amount_btn_panel.add(amount_panel,BorderLayout.CENTER);shouth_amount_btn_panel.add(btn_panel,BorderLayout.SOUTH);
        amount_panel.add(new JLabel("  "));amount_panel.add(new JLabel("  "));amount_panel.add(gt_amount_panel);
        amount_panel.add(pay_mood_panel); amount_panel.add(pay_mood_status);amount_panel.add(d_amt_panel);
        amount_panel.add(pay_stetus_panel);amount_panel.add(pay_status_status);amount_panel.add(pay_amt_panel);
        gt_amount_panel.add(grandtotalamount);gt_amount_panel.add(grand_total_amount);
        pay_mood_panel.add(paymoodlabel);pay_mood_panel.add(pay_mood);
        d_amt_panel.add(sumamount);d_amt_panel.add(disc_amount);
        pay_stetus_panel.add(paystatuslabel);pay_stetus_panel.add(pay_status);
        pay_amt_panel.add(totaldiscamount);pay_amt_panel.add(pay_amount);
        btn_panel.add(new JLabel("  "));btn_panel.add(new JLabel("  "));btn_panel.add(new JLabel("  "));btn_panel.add(new JLabel("  "));
        btn_panel.add(new JLabel("  "));btn_panel.add(save);btn_panel.add(print);btn_panel.add(reset);btn_panel.add(close);
        
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
                                float t_amount=Float.parseFloat(table.getValueAt(sel_row,11).toString());
                                float disc=0,d;
                                String batch=table.getValueAt(sel_row,2).toString().trim();
                                int qt=(Integer.parseInt(table.getValueAt(sel_row,9).toString().trim()))+(Integer.parseInt(table.getValueAt(sel_row,10).toString().trim()));
                                d=Float.parseFloat(table.getValueAt(sel_row, 7).toString().trim());
                                disc=t_amount*d/100; 
                                String gta=grand_total_amount.getText().trim();
                                gta=gta.substring(0,gta.length()-2).trim();
                                String pa=pay_amount.getText().trim();
                                pa=pa.substring(0,pa.length()-2).trim();
                                String da=disc_amount.getText().trim();
                                da=da.substring(0,da.length()-2).trim();
                                grand_total_amount.setText(String.format("%.02f",Float.parseFloat(gta)-t_amount)+"   \u20B9     ");
                                sum_amount=sum_amount-t_amount;
                                float d_amt=t_amount-disc;
                                pay_amount.setText(String.format("%.02f",Float.parseFloat(pa)-d_amt)+"   \u20B9     ");
                                totalsum=totalsum-d_amt;
                                disc_amount.setText(String.format("%.02f",Float.parseFloat(da)-disc)+"   \u20B9     ");
                                disamount=disamount-disc;
                                updateQuintity(batch,qt,false);
                                selitem--;
                            }catch(NumberFormatException ex)
                            {
                                JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage());
                            }
                            
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
                        JOptionPane.showMessageDialog(Billing.this, "Mobile No. Must Be Entered!", "Missing!", JOptionPane.WARNING_MESSAGE);
                        cust_mob.setText("");
                        cust_mob.requestFocusInWindow();return;
                    }
                  //new ClientWindow(Billing.this).setVisible(true);
                  product_name.requestFocusInWindow();
            }}});
            product_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                  new SelectProduct(Billing.this).setVisible(true);  
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
                //total_amount.setText(product_rate.getText());
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
                int q=Integer.parseInt(avi_qty.getText().trim());
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
            btplus.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    codeToCustomer();
                    cust_name.requestFocusInWindow();
            }}});
            free_qty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(free_qty.getText().equals(""))
                        free_qty.requestFocusInWindow();
                    else
                    btadd.requestFocusInWindow();
            }}
            public void keyReleased(KeyEvent e) {
                try
                {
                int n=Integer.parseInt(free_qty.getText());
                int nq=Integer.parseInt(product_quintity.getText());
                int q=(Integer.parseInt(avi_qty.getText().trim()))-nq;
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
                    if(q==-1 || product_quintity.getText().equals(""))
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
                int fq=Integer.parseInt(free_qty.getText());
                    if(q==-1 || product_quintity.getText().equals(""))
                    {
                        if(fq==0 || free_qty.getText().equals(""))
                        {
                            free_qty.setText("");
                            free_qty.requestFocusInWindow();
                            return;
                        }
                        product_quintity.setText("");
                        product_quintity.requestFocusInWindow();
                        return;
                    }
                    
                    setDataInTable();
                    pempaty();
                    product_name.requestFocusInWindow();
            }
        });
        save.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    saveBill();
                    cust_name.requestFocusInWindow();
            }}});
        save.addActionListener(new ActionListener() {
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
            }
        });
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.BILLING=false;
                    Billing.this.dispose();
            }}});
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.BILLING=false;
                Billing.this.dispose();
            }
        });
        print.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printSelBill(rbill);
            }
            });
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.printSelBill(rbill);
            }
        });
        
        setBillNo();
       
        
    }
    
     //set data in text field an Label
    public void setItemsValue(String[] s)
    {
       
        product_name.setText((s[0]).toUpperCase());
        batch_no=s[1];
        product_rate.setText(s[2]);
        float sgst=(Float.parseFloat((s[3])))/2;
        float cgst=(Float.parseFloat((s[3])))/2;
        Formatter fsgst=new Formatter(); 
        fsgst.format("%.02f",sgst);
        Formatter fcgst=new Formatter(); 
        fcgst.format("%.02f",sgst);
        Formatter fmrp=new Formatter(); 
        fmrp.format("%.02f",Float.parseFloat(s[4]));
        product_mrp.setText(fmrp+"");
        exp=s[5];
        mfg=s[6];
        p_quintity=(s[9]);
        avi_qty.setText(p_quintity);
        product_quintity.setText("0");
        free_qty.setText("0");
        m_id=s[8];
        p_id=s[7];
        p_hsn_code=s[10];
        product_disc.setText("0.0");
        
        
    }
    
    int counter=0;
    int p[]=new int[30];
    float sum_amount=0,totalsum=0,dis=0,disamount=0;
    public void setDataInTable()
    {
        selitem=0;
        if(product_name.getText().equals(""))return;
        int tq=0,q=0,nq=0;
        product_id.add(p_id);
        mfg_id.add(m_id);
        //batch_id.add(a_id);
        try
        {
            ResultSet pr=StaticMember.con.createStatement().executeQuery("select quintity from availableitem where batch_no like'"+batch_no+"'");
            pr.next();
            tq=Integer.parseInt(pr.getString("quintity"));
            q=Integer.parseInt(product_quintity.getText());
        }
        catch(SQLException ex){JOptionPane.showMessageDialog(null, ex.toString());}
        catch(NumberFormatException ex){}
        int i;
            if(q<=tq)
            {
                int sr=1;
                float net_amt=0,rate=0,dis=0,damt=0;
                rate=((Float.parseFloat(product_rate.getText())));
                dis=((Float.parseFloat(product_disc.getText())));
                if(product_quintity.getText().equals("0"))
                    net_amt=0;
                else
                    damt=(rate*q)*dis/100;
                    net_amt=rate-damt;
                tableModel.addRow(new Object[]{" "+product_name.getText()+" "," "+p_hsn_code+"  ","  "+batch_no+" ","  "+mfg+" ","  "+exp+" ","  "+String.format("%.02f",Float.parseFloat(product_mrp.getText()))+" "," "+String.format("%.02f",Float.parseFloat(product_rate.getText()))+"  "," "+String.format("%.02f",Float.parseFloat(product_disc.getText()))+"  "," "+String.format("%.02f",net_amt)+" "," "+product_quintity.getText()+"  "," "+free_qty.getText()+"  "," "+String.format("%.02f",Float.parseFloat(total_amount.getText()))+"  "});
                updateQuintity(batch_no,Integer.parseInt(product_quintity.getText())+Integer.parseInt(free_qty.getText()),true);
                try
                {
                float sa=Float.parseFloat(total_amount.getText());
                float da;
                if(!product_disc.getText().equals(""))
                    da=Float.parseFloat(product_disc.getText());
                else
                    da=0;
                sum_amount=sum_amount+sa;
                dis=sa*da/100;
                totalsum=sum_amount;
                disamount=disamount+dis;
                totalsum=sum_amount-disamount;
            }catch(NumberFormatException ex)    {}
                //set Formatter 
                Formatter fsuma=new Formatter(); //sum amount
                fsuma.format("%.02f",sum_amount);
                Formatter fcgst=new Formatter(); //vat Amount
                Formatter fdisa=new Formatter(); //Discount Amount
                fdisa.format("%.02f",disamount);
                Formatter ftsum=new Formatter(); //total Amount
                ftsum.format("%.02f",totalsum);
                
                grand_total_amount.setText(fsuma+""+"   \u20B9     ");
                disc_amount.setText(fdisa+"   \u20B9     ");
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
            PreparedStatement bsmt=StaticMember.con.prepareStatement("insert into retail_bill(retail_bill_no,retail_bill_date,customer_id,pay_mode,pay_status,pay_amount,retail_bill_time) values(?,?,?,?,?,?,?)");
            ResultSet bill_rset=StaticMember.con.createStatement().executeQuery("select max(retail_bill_no) as retail_bill_no  from retail_bill");
            bill_rset.next();
            int bill=bill_rset.getInt("retail_bill_no")+1;
            bsmt.setInt(1, bill);
            bsmt.setString(2, StaticMember.getDate());
            bsmt.setString(3,customer_id.get(customer_items.indexOf(cust_name.getText())));
            bsmt.setString(4,pay_mood.getSelectedItem().toString());
            bsmt.setString(5,pay_status.getSelectedItem().toString());
            String pa=pay_amount.getText().trim();
            bsmt.setFloat(6, Float.parseFloat((pa.substring(0,pa.length()-2))));
            bsmt.setString(7, StaticMember.getTime());
            bsmt.execute();
            saveBillItems();
            
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    
    public void saveBillItems()
    {
        try
        {
            PreparedStatement ssmt=StaticMember.con.prepareStatement("insert into retail_bill_items(sr_no,retail_bill_no,quintity,free_qty,price,disc,product_id,batch,manifacture_id,mrp,exp_date) values(?,?,?,?,?,?,?,?,?,?,?)");
            ResultSet r=StaticMember.con.createStatement().executeQuery("select max(retail_bill_no)  from retail_bill");
            r.next();
            ResultSet sr=StaticMember.con.createStatement().executeQuery("select max(sr_no) as sr_no  from retail_bill_items");
            sr.next();
            int srno=sr.getInt("sr_no")+1;
            for(int i=0;i<table.getRowCount();i++)
            {
                
                ssmt.setInt(1,srno+i);
                ssmt.setString(2,r.getString(1));
                int qty=Integer.parseInt(table.getValueAt(i, 9).toString().trim());
                ssmt.setInt(3, qty);
                int frqty=Integer.parseInt(table.getValueAt(i, 10).toString().trim());
                ssmt.setInt(4, frqty);
                ssmt.setFloat(5, Float.parseFloat(table.getValueAt(i, 6).toString()));
                ssmt.setFloat(6, Float.parseFloat(table.getValueAt(i, 7).toString()));
                ssmt.setString(7, product_id.get(i));
                ssmt.setString(8, table.getValueAt(i, 2).toString());
                ssmt.setString(9,mfg_id.get(i));
                ssmt.setFloat(10,Float.parseFloat(table.getValueAt(i, 5).toString()));
                ssmt.setString(11,table.getValueAt(i, 4).toString().trim());
                ssmt.execute();
                
            }
            ResultSet b=StaticMember.con.createStatement().executeQuery("select retail_bill_no from retail_bill where retail_bill_time like'"+StaticMember.getTime()+"' AND retail_bill_date like'"+StaticMember.getDate()+"'");
            if(b.next())
            rbill=b.getString("retail_bill_no");
            empaty();
            JOptionPane.showMessageDialog(Billing.this,"Bill Saved!",null,JOptionPane.INFORMATION_MESSAGE);
            
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(Billing.this,ex.getMessage(),null,JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void deleteLasrBill()
    {
        try
        {
            PreparedStatement stmt=StaticMember.con.prepareStatement("delete from retail_bill where retail_bill_no=?");
            ResultSet bill_rset=StaticMember.con.createStatement().executeQuery("select max(retail_bill_no) as retail_bill_no  from retail_bill");
            bill_rset.next();
            int bill=bill_rset.getInt("retail_bill_no");
            stmt.setInt(1, bill);         
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record Delete Successfully!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
            //dreset();
            //setToProduct();
            //product_name.requestFocusInWindow();

        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(Billing.this,ex.getMessage(),null,JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void empaty()
    {
        p_id="";
        batch_no="";
        product_id.clear();
        mfg_id.clear();
        avil_id.clear();
        cust_name.setText("");
        cust_address.setText("");
        cust_gstin.setText("");
        cust_dl_no.setText("");
        invoice_no.setText("");
        grand_total_amount.setText("   \u20B9     ");
        pay_amount.setText("");
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
        avi_qty.setText("");
        total_amount.setText("");
        free_qty.setText("");
        
    }
    
   
   
   public void setBillNo()
   {
       int billno=0;
        try
        {
            ResultSet bill_rset=StaticMember.con.createStatement().executeQuery("select max(retail_bill_no) as retail_bill_no  from retail_bill");
            bill_rset.next();
            billno=bill_rset.getInt("retail_bill_no")+1;
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(Billing.this, ex.getMessage(),"",JOptionPane.ERROR_MESSAGE);
        }
        invoice_no.setText("RGST00"+billno);
        bill_date.setText(StaticMember.getDate());
        cust_name.setFocusable(true);
        
   }
   
   private void updateQuintity(String batch, int qty,boolean f)
   {
       try
       {
            int avlqty=0,nqty=0;
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batch+"'");
            rset.next();
            avlqty=rset.getInt("quintity");
            if(f==true)
                nqty=avlqty-qty;
            else
                nqty=avlqty+qty;
            StaticMember.con.createStatement().execute("update availableitem set quintity="+nqty+" where batch_no like'"+batch+"'");
        }
       catch(SQLException ex){}
   }
    
}
