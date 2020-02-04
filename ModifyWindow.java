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

/**
 *
 * @author RANDHIR KUMAR
 */
public class ModifyWindow extends JDialog{
    private static JTextField cust_name;
    private JTextField cust_dl_no,bill_date,invoice_no,cust_address,cust_mob,cust_gstin,product_quintity,free_qty,product_disc,product_name,product_hsn,product_mrp,product_rate,avi_qty,total_amount;
    private JLabel grand_total_amount,disc_amount,cgst_amt,sgst_amt,pay_status_status,pay_amount,pay_mood_status;
    private JButton bcancel,close,print,reset,btadd;
    private JComboBox pay_mood,pay_status;
    private JTable table;
    private DefaultTableModel tableModel;
    private String[] data;
    private String batch_no;
    private boolean flag=false;
    private String billno="",bill="",rbill="",batchid,p_amt="",sr_no="",m_id="",pri="",p_id="",exp="";
    private int selitem=0,numberOfRow=0,oldqty=0,oldfqty=0;
    
    public ModifyWindow(String[] bd,String bil)
    {
        super(MDIMainWindow.self,"");
        StaticMember.setSize(this);
        StaticMember.setLocation(this);
        this.setLayout(new BorderLayout());
        this.setResizable(false);
        this.addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent we)
         {
             ModifyWindow.this.dispose();
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
                StaticMember.printSelBill(billno);
            }        });
        /*msave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                saveBill(billno);
                if(JOptionPane.showConfirmDialog(null,"Do You Want To Print Bill ?","Confirmtion Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                {
                    StaticMember.printSelBill(billno);
                }
            }        });*/
        mi.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                new MSelectProduct(ModifyWindow.this).setVisible(true);
                product_rate.requestFocusInWindow();
            }        });
        si.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                empaty();
                pempaty();
            }        });
        
         JLabel h=new JLabel(" SALE BILL MODIFY ",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        //Access Date Frome System
         
        
        JLabel sumamount=new JLabel("DISE AMOUNT :",JLabel.RIGHT);
        sumamount.setFont(StaticMember.labelFont);
        JLabel totaldiscamount=new JLabel("PAYABLE AMOUNT :",JLabel.RIGHT);
        totaldiscamount.setFont(StaticMember.labelFont);
        JLabel grandtotalamount=new JLabel("GRAND TOTAL AMOUNT :",JLabel.RIGHT);
        grandtotalamount.setFont(StaticMember.labelFont);
        JLabel paymoodlabel=new JLabel("PAYMENT MODE :",JLabel.RIGHT);
        paymoodlabel.setFont(StaticMember.labelFont);
        paymoodlabel.setForeground(StaticMember.flcolor);
        JLabel paystatuslabel=new JLabel("PAYMENT STATUS :",JLabel.RIGHT);
        paystatuslabel.setFont(StaticMember.labelFont);
        paystatuslabel.setForeground(StaticMember.flcolor);
        pay_mood_status=new JLabel("");
        pay_mood_status.setFont(StaticMember.textFont);
        pay_status_status=new JLabel("");
        pay_status_status.setFont(StaticMember.textFont);
        
        grand_total_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        grand_total_amount.setFont(StaticMember.textFont);
        disc_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        disc_amount.setFont(StaticMember.textFont);
        pay_amount=new JLabel("00.00  \u20B9     ",JLabel.RIGHT);
        pay_amount.setFont(StaticMember.textFont);
        pay_amount.setHorizontalAlignment(SwingConstants.RIGHT);
        
        //cteate TextField
        bill_date=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"Bill Date"," BILL DATE");
        invoice_no=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," Bill No."," BILL NO");
        cust_name=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"Enter Customer Name"," PARTY NAME");
        cust_dl_no=StaticMember.MyInputBox("",40,StaticMember.STRING_TYPE,false,"DL No."," DL NO");
        cust_gstin=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"GSTIN No"," GSTIN NO");
        cust_mob=StaticMember.MyInputBox("",11,StaticMember.INTEGER_TYPE,false," Mobile No."," MOBILE NO");
        cust_address=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"ADDRESS"," ADDRESS");
        product_quintity=StaticMember.MyInputBox("",30,StaticMember.INTEGER_TYPE," Quintity"," QUINTITY");
        free_qty=StaticMember.MyInputBox("",30,StaticMember.INTEGER_TYPE," Free Qty"," FREE");
        product_disc=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE,"Disc"," DISC %");
        product_name=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," Product Name"," PRODUCT NAME");
        product_hsn=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false,"HSN Code"," HSN CODE");
        product_mrp=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE,false," MRP"," MRP");
        product_rate=StaticMember.MyInputBox("",30,StaticMember.FLOAT_TYPE," RATE"," RATE");
        avi_qty=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," AVL QTY"," AVL QTY");
        total_amount=StaticMember.MyInputBox("",30,StaticMember.STRING_TYPE,false," Total Amount "," TOTAL AMOUNT");
        total_amount.setHorizontalAlignment(SwingConstants.RIGHT);
        
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
        table.getColumnModel().getColumn(9).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(10).setCellRenderer(centerRenderer);
        TableColumn column=null;
       
        column=table.getColumnModel().getColumn(0);column.setPreferredWidth(250);
        column=table.getColumnModel().getColumn(1);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(2);column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(3);column.setPreferredWidth(60);
        column=table.getColumnModel().getColumn(4);column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(5);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(6);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(7);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(8);column.setPreferredWidth(40);
        column=table.getColumnModel().getColumn(9);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(10);column.setPreferredWidth(20);
        column=table.getColumnModel().getColumn(11);column.setPreferredWidth(40);
        
        JScrollPane scr=new JScrollPane(table);
        
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        reset=StaticMember.MyButton("RESET","Click On Button To Reset All Field Window");
        bcancel=StaticMember.MyButton("DEL ROW","Click On Button To Delete One Row");
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
        JPanel bdetailBorderPanel=new JPanel(new BorderLayout());
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
        cust_nameBtnPanel.add(cust_name,BorderLayout.CENTER);//cust_nameBtnPanel.add(btplus,BorderLayout.EAST);
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
        btn_panel.add(new JLabel("  "));btn_panel.add(bcancel);btn_panel.add(print);btn_panel.add(reset);btn_panel.add(close);
        
        
        
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
           
           product_name.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                  new MSelectProduct(ModifyWindow.this).setVisible(true);  
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
            
            free_qty.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    if(free_qty.getText().equals("-1"))
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
                    
            }}});
        bcancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        close.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    ModifyWindow.this.dispose();
            }}});
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ModifyWindow.this.dispose();
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
        print.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                    StaticMember.printSelBill(billno);
            }});
        print.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticMember.printSelBill(billno);
            }
        });
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount()==2)
                {
                    if(JOptionPane.showConfirmDialog(MDIMainWindow.self,"Are You Sure To Update Row ?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    {   
                        int sel_row=table.getSelectedRow();
                        if(sel_row!=-1)
                        {
                            flag=true;
                            try
                            {
                                float t_amount=Float.parseFloat(table.getValueAt(sel_row,11).toString());
                                float disc=0,d;
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
                                oldqty=Integer.parseInt(table.getValueAt(sel_row, 9).toString().trim());
                                oldfqty=Integer.parseInt(table.getValueAt(sel_row, 10).toString().trim());
                                product_name.setText(table.getValueAt(sel_row, 0).toString().trim());
                                product_mrp.setText(table.getValueAt(sel_row, 5).toString().trim());
                                product_rate.setText(table.getValueAt(sel_row, 6).toString().trim());
                                product_disc.setText(table.getValueAt(sel_row, 7).toString().trim());
                                product_quintity.setText(table.getValueAt(sel_row, 9).toString().trim());
                                free_qty.setText(table.getValueAt(sel_row, 10).toString().trim());
                                batchid=table.getValueAt(sel_row, 2).toString().trim();
                                ResultSet pr=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batchid+"'");
                                pr.next();
                                avi_qty.setText(pr.getString("quintity"));
                                ResultSet brset=StaticMember.con.createStatement().executeQuery("select * from retail_bill_items where retail_bill_no="+billno+" and product_id ='"+pr.getInt("product_id")+"'");
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
    public void setItemsValue(String[] s)
    {
        product_name.setText((s[0]).toUpperCase());
        batch_no=s[1];
        Formatter frate=new Formatter(); 
        frate.format("%.02f",Float.parseFloat(s[2]));
        product_rate.setText(s[2]);
        Formatter fmrp=new Formatter(); 
        fmrp.format("%.02f",Float.parseFloat(s[3]));
        product_mrp.setText(fmrp+"");
        avi_qty.setText(s[6]);
        product_quintity.setText("0");
        free_qty.setText("0");
        m_id=s[5];
        p_id=s[4];
        product_disc.setText("0.0");
        String pamt=pay_amount.getText().trim();
        p_amt=pamt.substring(0,pamt.length()-2).trim();
        exp=s[7];
    }
    
    //int selcont=0;
    public void setBillDataInTable(String bill)
    {
        selitem=0;
        try
        {//bdate,custname,address,dlno,date,bilno,mno
        ResultSet b=StaticMember.con.createStatement().executeQuery("select * from retail_bill where retail_bill_no like'"+bill+"'");
        b.next();
        ResultSet c=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+b.getString("customer_id")+"'");
        c.next();
        bill_date.setText(b.getString("retail_bill_date"));
        cust_name.setText(c.getString("customer_name").toUpperCase());
        cust_address.setText(c.getString("customer_address").toUpperCase());
        cust_dl_no.setText(c.getString("customer_dl").toUpperCase());
        invoice_no.setText("RGST00"+bill);
        cust_mob.setText(c.getString("customer_mob"));
        cust_gstin.setText(c.getString("customer_gst"));
        pay_mood.setSelectedItem(b.getString("pay_mode"));
        pay_status.setSelectedItem(b.getString("pay_status"));
        
        //ResultSet s=StaticMember.con.createStatement().executeQuery("select * from retail_bill_items where retail_bill_no like'"+bill+"' order by product_id");
        ResultSet s=StaticMember.con.createStatement().executeQuery("select * from (retail_bill_items rbi join products p on rbi.product_id=p.product_id join manifacture m on rbi.manifacture_id=m.manifacture_id join type t on p.type_id=t.type_id) where retail_bill_no like'"+bill+"'");
        int tablerow=0;
        float subamt=0,disamt=0,net_amt=0;
        while(s.next())
        {
            ResultSet avi=StaticMember.con.createStatement().executeQuery("select * from  availableitem where product_id like'"+s.getInt("product_id")+"'");
            avi.next();
            String product_name=s.getString("product_name")+" "+s.getString("type_name")+" "+avi.getString("packing");
            float prate=Float.parseFloat(s.getString("price"));
            float pqty=Float.parseFloat(s.getString("quintity"));
            float disc=Float.parseFloat(s.getString("disc"));
            net_amt=prate-disc;
            float t_amount=prate*pqty;
            
            tableModel.addRow(new Object[]{"  "+product_name+"  ","  "+s.getString("product_hsn_code")+"  ","  "+s.getString("batch")+"  ","  "+s.getString("manifacture_name")+"  ","  "+s.getString("exp_date")+"  ","  "+String.format("%.02f",Float.parseFloat(s.getString("mrp")))+"  ","  "+String.format("%.02f",prate)+"  ","  "+String.format("%.02f",Float.parseFloat(s.getString("disc")))+"  ","  "+String.format("%.02f",net_amt)+"  ","  "+s.getString("quintity")+"  ","  "+s.getString("free_qty")+"  ","  "+String.format("%.02f",t_amount)+"  "});
            
            float amt=t_amount;
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
            fgta.format("%.2f",(subamt-disamt));
            grand_total_amount.setText(fsa+"   \u20B9     ");
            disc_amount.setText(fda+"   \u20B9     ");
            pay_amount.setText(fgta+"   \u20B9     ");
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
            PreparedStatement bsmt=StaticMember.con.prepareStatement("insert into retail_bill_items(sr_no,retail_bill_no,quintity,free_qty,price,disc,product_id,batch,manifacture_id,mrp,exp) values(?,?,?,?,?,?,?,?,?,?,?)");
            ResultSet r=StaticMember.con.createStatement().executeQuery("select max(sr_no) as sr_no  from retail_bill_items");
            r.next();
            int srno=r.getInt("sr_no")+1;
            bsmt.setInt(1, srno);
            bsmt.setString(2,bill);
            int qty=Integer.parseInt(product_quintity.getText().trim());
            bsmt.setInt(3, qty);
            int frqty=Integer.parseInt(free_qty.getText().trim());
            bsmt.setInt(4, frqty);
            float price=Float.parseFloat(product_rate.getText().trim());
            float disc=Float.parseFloat(product_disc.getText().trim());
            float tamt=price*qty;
            float disamt=tamt*disc/100;
            float netamt=tamt-disamt;
            bsmt.setFloat(5, price);
            bsmt.setFloat(6, disc);
            bsmt.setString(7, p_id);
            bsmt.setString(8, batch_no);
            bsmt.setString(9,m_id);
            bsmt.setString(10,product_mrp.getText());
            bsmt.setString(11,exp);
            bsmt.execute();
            updateQuintity(batch_no,qty+frqty,true);
            PreparedStatement ssmt=StaticMember.con.prepareStatement("update retail_bill set pay_amount=? where retail_bill_no=?");    
            float pamt=Float.parseFloat(p_amt);
            ssmt.setFloat(1, pamt+netamt);
            ssmt.setString(2, bill);
            ssmt.execute();
            MDIMainWindow.bill_m_obj.allBill();
            empaty();
            JOptionPane.showMessageDialog(ModifyWindow.this,"Bill Updated!",null,JOptionPane.INFORMATION_MESSAGE);
            
                
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
            PreparedStatement ssmt=StaticMember.con.prepareStatement("Update retail_bill_items set price=?,quintity=?,disc=?,free_qty=? where sr_no=?");
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batchid+"'");
            rset.next();
            int tq=rset.getInt("quintity");
            int resqty=0,restq;
            if(oldqty!=0 || oldfqty!=0)
            {
                int newqty=Integer.parseInt(product_quintity.getText());
                int newfreeqty=Integer.parseInt(free_qty.getText());
                if(newqty>oldqty || newfreeqty>oldfqty)
                {
                    int rqty=newqty-oldqty;
                    int rfqty=newfreeqty-oldfqty;
                    resqty=rqty+rfqty;
                    restq=tq-resqty;
                    StaticMember.con.createStatement().execute("update availableitem set quintity="+restq+" where batch_no like'"+batchid+"'");
                }
                else if(newqty<oldqty || newfreeqty<oldfqty)
                {
                    int rqty=oldqty-newqty;
                    int rfqty=oldfqty-newfreeqty;
                    resqty=rqty+rfqty;
                    restq=tq+resqty;
                    StaticMember.con.createStatement().execute("update availableitem set quintity="+restq+" where batch_no like'"+batchid+"'");
                }
            }
            float price=Float.parseFloat(product_rate.getText());
            float disc=Float.parseFloat(product_disc.getText());
            int newqty=Integer.parseInt(product_quintity.getText());
            int newfreeqty=Integer.parseInt(free_qty.getText());
            int pqty=newqty+newfreeqty;
            ssmt.setFloat(1,price);
            ssmt.setInt(2, newqty);
            ssmt.setFloat(3, disc);
            ssmt.setInt(4, newfreeqty);
            ssmt.setString(5,sr_no);
            ssmt.execute();
            PreparedStatement bsmt=StaticMember.con.prepareStatement("update retail_bill set pay_mode=?,pay_status=?,pay_amount=? where retail_bill_no=?");
            float tot_amt=price*newqty;
            String pamt=pay_amount.getText().trim();
            float pay_amt=Float.parseFloat(pamt.substring(0,pamt.length()-2).trim());
            float dis_amt=tot_amt*disc/100;
            float net_amt=tot_amt-dis_amt;
            float grand_amt=pay_amt+net_amt;
            bsmt.setString(1,pay_mood.getSelectedItem().toString());
            bsmt.setString(2,pay_status.getSelectedItem().toString());
            bsmt.setFloat(3, grand_amt);
            bsmt.setString(4, bill);
            bsmt.execute();
            sr_no="";batchid="";pempaty();
            MDIMainWindow.bill_m_obj.allBill();
            JOptionPane.showMessageDialog(null, "Update Succecfull","wow",JOptionPane.INFORMATION_MESSAGE);
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
        sr_no="";
        pri="";
        batchid="";
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
        flag=false;
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
    
   public void printBill()
   {
        if(billno.equals(""))
            JOptionPane.showMessageDialog(null, "Fistly Save Bill Then Print.");
        else
        {
            RetailBillPage rbp=new RetailBillPage(billno);
            if(rbp.printBill())
                JOptionPane.showMessageDialog(null, "Bill Printed!");

        }
   }
   
   private void deleteRow(int sel_row)
   {
       if(JOptionPane.showConfirmDialog(MDIMainWindow.self,"Are You Sure To Delete Row ?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        {   
            if(sel_row!=-1)
            {
                try
                {
                    float t_amount=Float.parseFloat(table.getValueAt(sel_row,11).toString());
                    float disc=0,d;
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
                    int oldqty=Integer.parseInt(table.getValueAt(sel_row, 9).toString().trim());
                    int oldfqty=Integer.parseInt(table.getValueAt(sel_row, 10).toString().trim());
                    String batchid=table.getValueAt(sel_row, 2).toString().trim();
                    ResultSet pr=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batchid+"'");
                    pr.next();
                    ResultSet brset=StaticMember.con.createStatement().executeQuery("select * from retail_bill_items where retail_bill_no="+billno+" and product_id ='"+pr.getInt("product_id")+"'");
                    brset.next();
                    StaticMember.con.createStatement().execute("Delete from retail_bill_items where sr_no like'"+brset.getString("sr_no")+"'");
                    JOptionPane.showMessageDialog(this, " Delete!", "WOW!",JOptionPane.INFORMATION_MESSAGE );
                    updateQuintity(batchid,oldqty+oldfqty,false);
                    selitem--;
                    
                }catch(NumberFormatException ex){}
                catch(SQLException ex){}
                tableModel.removeRow(sel_row);
                product_rate.requestFocusInWindow();
            }
        }
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
