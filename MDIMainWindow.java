/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

/**
 *
 * @author dell
 */
public class MDIMainWindow extends JFrame{
    //create MDIForm Object
    private JMenu file,manage_user,view,about1,customer,employee,product,type,supplier,company,master,leser,report,voucher,acount,stock,billing,retun,
            order,manifacture;
    private JMenuItem create_user,changeupwd,changeApwd,all_product_view,company_view,type_view,manufacture_view,view_retuns,customer_view,view_bill;
    private JMenuItem addcustomer,updatecustomer,delcustomer,addcompany,updatecompany,delcompany,addmanifacture,updatemanifacture,delmanifacture,
            addsupplier,updatesupplier,delsupplier,addtype,updatetype,deltype,addproduct,updateproduct,delproduct,addemployee,updateemployee,
            delemployee,stock_ledger,voucher_ledger,sale_ledger,supplier_voucher1,custum_ledger,cash_ledger,bank_ledger,client_ledger,gst_file_return,
            daily_sale_report,attendance_report,expenses_report,collection_report,cash_report,bank_report,supplier_voucher2,payment_voucher2,
            expenses_voucher2,customer_voucher2,viewretailbill,inside_state,outside_state,retailbill,debit,credit,salestatement,salereturn,
            customer_cd_account,supplier_cd_account,bank,cash,addstock,create_self_company,viewstock,viewsupplier,getbackup,sale_report,stock_report,
            view_user,view_customer,view_employee,bill_modify,kbill,kmodify,view_stock_bill,stockmodify,current_stock,create_order,modify_order,latter_pad;
    public static JDesktopPane desktop;
    private JButton login,logout,stock_entry,retail_billing,view_billing,view_stock,b_modify,stock_modify;
    //private JButton baddproduct,updateStock,ksel,sel,selmodifie,kselmodifie,addstock,viewkbill,viewbill,checkstock,baddcustomer,exit;
    public static JButton b1=new JButton();
    private String str[];
    String[] data,data2;
    private ResultSet rset=null;
    private JLabel lbl1,lbl2,lbl3,user_admin_name;
    static MDIMainWindow self;
   
    public static boolean activeFrame[]=new boolean[55];
    AddManufacture a_m_obj; static UpdateManufacture u_m_obj;static DeleteManufacture d_m_obj;
    static AddNewProducts a_n_p_obj;static UpdateProduct u_p_obj;static DeleteProducts d_p_obj;
    AddNewCompany a_n_c_obj;static UpdateCompany u_c_obj;static DeleteCompany d_c_obj;
    AddProductType a_p_t_obj;static UpdateProductType u_p_t_obj;static DeleteProductType d_p_t_obj;
    AddNewEmployee a_n_e_obj;UpdateEmployee u_e_obj;DeleteEmployee d_e_obj;
    AddSupplier a_s_obj;static UpdateSupplier u_s_obj;static DeleteSupplier d_s_obj;
    static AddNewCustomer a_n_cu_obj;static UpdateCustomer u_cu_obj;static DeleteCustomer d_cu_obj;
    CreateNewUser c_n_u_obj;CreateSelfCompany c_s_c_obj;
    EmployeePayment e_p_obj;SaleRetun s_r_obj;SaleStatement s_s_obj;
    static KBilling k_b_obj; static KBillModify k_b_m_obj; static KModifyWindow k_m_w_obj; static Billing bl_obj;static BillModify bill_m_obj;static ViewBillWindow v_b_w_obj;
    static CreateOrder c_o_obj;static ModifyOrder m_o_obj;OrderWindow o_w_obj;
    static Stock s_obj;static StockModify s_m_obj;ViewStockBill v_s_b_obj; StockReport st_r_obj;
    ViewProducts v_a_p_obj;ViewCompanyDetails v_comp_d_obj;ViewManufactureDetails v_m_d_obj;//ViewStockInvoice v_s_i_obj;
    ViewSupplierDetails v_s_d_obj;VIewCustomers v_cu_d_obj;ViewStock v_s_obj;LatterPad l_p_obj;
    AdminLoginWindow a_l_w_obj;DailySaleReport d_s_r_obj;SalesReport sl_r_obj;SalesLedger sl_l_obj;
    PaymentSubmission pay_s_obj;CustomerLedger cu_l_obj;
    
    
    
    public MDIMainWindow()
    {
        super("KOMAL HEALTH CARE");
        //this.setSize(1475,720);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT);
        this.setResizable(true);
        this.setExtendedState(MDIMainWindow.MAXIMIZED_BOTH);
        
        ImageIcon ii = StaticMember.getResizeImageIcon("/images/menuicon/logo.png", 200,200);
        this.setIconImage(ii.getImage());
        self=this;
        desktop=new JDesktopPane(){    
            ImageIcon icon = StaticMember.getResizeImageIcon("/images/BROMED.jpg",StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT-150);  
            Image image = icon.getImage();
            Image newimage = image.getScaledInstance(StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT-150,Image.SCALE_AREA_AVERAGING);
            protected void paintComponent(Graphics g)    
            {        
                super.paintComponent(g);        
                g.drawImage(newimage,0,0,this);
            } 
        };
       //desktop.setLayout(new GridLayout(1,1));
        this.add(desktop);
       this.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent we)
        {
            if(JOptionPane.showConfirmDialog(null,"Are You Sure To Close Application?","Confirmtion Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION)
                {
                    try 
                    {
                        StaticMember.con.close();
                    }catch (SQLException ex) {}
                    System.exit(0);
                }
            
        }
        });
        
        for(int i=0;i<activeFrame.length;i++)
            activeFrame[i]=false;
        
        /* try
        {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            
        }
        catch(Exception ex)
        {
            
        }*/
        //Create a manuBar
        JMenuBar bar=new JMenuBar();
        //Create A Menu
        //JMenu master=StaticMember.MyMenu("Master ","/images/menuicon/master.png",20);
        
        file=StaticMember.MyMenu("FILE ","/images/menuicon/file.png",20);
        file.setMnemonic(KeyEvent.VK_F);
        file.setFont(StaticMember.menufont);
        file.setForeground(StaticMember.mcolor);
        create_self_company=new JMenuItem("CREATE SELF COMPANY");
        create_self_company.setIcon(StaticMember.getResizeImageIcon("/images/menuicon/create_self_company.png", 20, 20));
        create_self_company.setForeground(StaticMember.mcolor);
        create_user=new JMenuItem("CREATE USER");
        create_user.setIcon(StaticMember.getResizeImageIcon("/images/menuicon/create_user.png", 20, 20));
        create_user.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD,KeyEvent.CTRL_MASK));
        manage_user=StaticMember.MyMenu("MANAGE USER ","/images/menuicon/manage_user.png",20);
        manage_user.setMnemonic(KeyEvent.VK_B);
        changeupwd=new JMenuItem("Change User Password",'c');
        changeupwd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_MASK));
        changeApwd=new JMenuItem("Change Admin Password");
        changeApwd.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_HOME,KeyEvent.CTRL_MASK));
        getbackup=new JMenuItem("GET BACKUP");
        JMenuItem help=new JMenuItem("Help",'E');
        help.setIcon(StaticMember.getResizeImageIcon("/images/menuicon/help.png", 20, 20));
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,KeyEvent.CTRL_MASK));
        JMenuItem mexit=new JMenuItem("Exit",'x'); 
        mexit.setIcon(StaticMember.getResizeImageIcon("/images/menuicon/exit.jpg", 20, 20));
        file.add(create_self_company);
        file.add(create_user);
        manage_user.add(changeupwd);
        manage_user.add(changeApwd);
        manage_user.add(getbackup);
        file.add(manage_user);
        file.add(help);
        file.add(mexit);
        //bar.add(file);//add menu on MenuBar
        latter_pad=new JMenuItem("CREATE LATTER PAD");
        latter_pad.setIcon(StaticMember.getResizeImageIcon("/images/menuicon/invoice.png", 20, 20));
         master=StaticMember.MyMenu("MASTER ","/images/menuicon/master.png",20);
        //master.setIcon(ii);
        master.setMnemonic(KeyEvent.VK_M);
        master.setFont(StaticMember.menufont);
        master.setForeground(StaticMember.mcolor);
        bar.add(master);
        
        
        view=StaticMember.MyMenu("VIEW","/images/menuicon/view.png",20);
        view.setMnemonic(KeyEvent.VK_V);
        view.setFont(StaticMember.menufont);
        view.setForeground(StaticMember.flcolor);
        about1=new JMenu("ABAUT");
        about1.setMnemonic(KeyEvent.VK_B);
        all_product_view=new JMenuItem("View All Products");
        company_view=new JMenuItem("View All Companies");
        viewsupplier=new JMenuItem("View All Supplier");
        manufacture_view=new JMenuItem("View All Manufacture");
        view_retuns=new JMenuItem("Sale Retuns");
        view_bill=new JMenuItem("View All Bill");
        view_user=new JMenuItem("View All Users");
        view_employee=new JMenuItem("View All Employee");
        view_customer=new JMenuItem("View All Customer");
        bar.add(view);
        view.add(all_product_view);
        view.add(company_view);
        view.add(manufacture_view);
        view.add(viewsupplier);
        view.add(view_customer);
        view.add(view_employee);
        view.add(view_user);
        
       
        company=StaticMember.MyMenu("COMPANY ","/images/menuicon/company.png",20);
        company.setMnemonic(KeyEvent.VK_B);
        addcompany=new JMenuItem("CREATE");
        updatecompany=new JMenuItem("MODIFY");
        delcompany=new JMenuItem("REMOVE");
        master.add(company);
        company.add(addcompany);
        company.add(updatecompany);
        company.add(delcompany);
        
        manifacture=StaticMember.MyMenu("MANUFACTURE ","/images/menuicon/manifacture.png",20);//new JMenu("MANUFACTURE");
        manifacture.setMnemonic(KeyEvent.VK_B);
        addmanifacture=new JMenuItem("CREATE");
        updatemanifacture=new JMenuItem("MODIFY");
        delmanifacture=new JMenuItem("REMOVE");
        master.add(manifacture);
        manifacture.add(addmanifacture);
        manifacture.add(updatemanifacture);
        manifacture.add(delmanifacture);
        
        supplier=StaticMember.MyMenu("SUPPLIER ","/images/menuicon/supplier.png",20);//new JMenu("SUPPLIER");
        supplier.setMnemonic(KeyEvent.VK_B);
        addsupplier=new JMenuItem("CREATE");
        updatesupplier=new JMenuItem("MODIFY");
        delsupplier=new JMenuItem("REMOVE");
        master.add(supplier);
        supplier.add(addsupplier);
        supplier.add(updatesupplier);
        supplier.add(delsupplier);
        
        type=StaticMember.MyMenu("TYPE ","/images/menuicon/type.png",20);//new JMenu("PRODUCT TYPE");
        type.setMnemonic(KeyEvent.VK_B);
        addtype=new JMenuItem("CREATE");
        updatetype=new JMenuItem("MODIFY");
        deltype=new JMenuItem("REMOVE");
        master.add(type);
        type.add(addtype);
        type.add(updatetype);
        type.add(deltype);
        
        product=StaticMember.MyMenu("PRODUCT ","/images/menuicon/product.png",20);//new JMenu("PRODUCT");
        product.setMnemonic(KeyEvent.VK_B);
        addproduct=new JMenuItem("CREATE");
        updateproduct=new JMenuItem("MODIFY");
        delproduct=new JMenuItem("REMOVE");
        master.add(product);
        product.add(addproduct);
        product.add(updateproduct);
        product.add(delproduct);
        
        employee=StaticMember.MyMenu("EMPLOYEE ","/images/menuicon/employee.png",20);//new JMenu("EMPLOYEE");
        employee.setMnemonic(KeyEvent.VK_B);
        addemployee=new JMenuItem("CREATE");
        updateemployee=new JMenuItem("MODIFY");
        delemployee=new JMenuItem("REMOVE");
        master.add(employee);
        employee.add(addemployee);
        employee.add(updateemployee);
        employee.add(delemployee);
        
        customer=StaticMember.MyMenu("CUSTOMER ","/images/menuicon/customer.png",20);//new JMenu("CUSTOMER");
        customer.setMnemonic(KeyEvent.VK_B);
        addcustomer=new JMenuItem("CREATE");
        updatecustomer=new JMenuItem("MODIFY");
        delcustomer=new JMenuItem("REMOVE");
        //JMenuItem addcustomer,updatecustomer,delcustomer
        //Jmenu customer,employee,product,type,supplier,company,master
        master.add(customer);
        customer.add(addcustomer);
        customer.add(updatecustomer);
        customer.add(delcustomer);
        
        master.add(manage_user);
        master.add(create_self_company);
        master.add(create_user);
        master.add(latter_pad);
        master.add(help);
        master.add(mexit);
        
        leser=StaticMember.MyMenu("LEDGER ","/images/menuicon/ledger.png",20);//new JMenu("LEDGER");
        leser.setMnemonic(KeyEvent.VK_L);
        leser.setFont(StaticMember.menufont);
        leser.setForeground(StaticMember.mcolor);
        stock_ledger=new JMenuItem("STOCK LEDGER");
        voucher_ledger=new JMenuItem("VOUCHER LEDGER");
        sale_ledger=new JMenuItem("SALES LEDGER");
        supplier_voucher1=new JMenuItem("SUPPLIER VOUCHER");
        custum_ledger=new JMenuItem("CUSTUM LEDGER");
        cash_ledger=new JMenuItem("CASH LEDGER");
        bank_ledger=new JMenuItem("BANK LEDGER");
        client_ledger=new JMenuItem("CUSTOMER LEDGER");
        gst_file_return=new JMenuItem("GST FILE RETURN");
        leser.add(stock_ledger);leser.add(sale_ledger);leser.add(voucher_ledger);
        leser.add(supplier_voucher1);leser.add(custum_ledger);leser.add(cash_ledger);
        leser.add(bank_ledger);leser.add(client_ledger);leser.add(gst_file_return);
        bar.add(leser);
        
        report=StaticMember.MyMenu("REPORT ","/images/menuicon/report.png",20);//new JMenu("REPORT");
        report.setMnemonic(KeyEvent.VK_L);
        report.setFont(StaticMember.menufont);
        report.setForeground(StaticMember.mcolor);
        daily_sale_report=new JMenuItem("DAILY SALES REPORT");
        stock_report=new JMenuItem("STOCK REPORT");
        sale_report=new JMenuItem("SALES REPORT");
        attendance_report=new JMenuItem("ATTENDANCE REPORT");
        expenses_report=new JMenuItem("EXPENSES REPORT");
        collection_report=new JMenuItem("COLLECTION REPORT");
        cash_report=new JMenuItem("CASH REPORT");
        bank_report=new JMenuItem("BANK REPORT");
        report.add(daily_sale_report);
        report.add(stock_report);
        report.add(sale_report);
        report.add(attendance_report);
        report.add(expenses_report);
        report.add(collection_report);
        report.add(cash_report);
        report.add(bank_report);
        bar.add(report);
        
        voucher=StaticMember.MyMenu("VOUCHER ","/images/menuicon/voucher.png",20);//new JMenu("VOUCHER");
        voucher.setMnemonic(KeyEvent.VK_L);
        voucher.setFont(StaticMember.menufont);
        voucher.setForeground(StaticMember.mcolor);
        supplier_voucher2=new JMenuItem("SUPPLIER VOUCHER(Intra state)");
        payment_voucher2=new JMenuItem("PAYMENT VOUCHER");
        expenses_voucher2=new JMenuItem("EXPENSES VOUCHER");
        customer_voucher2=new JMenuItem("CUSTOMER VOUCHER");
        //JMenuItem supplier_voucher2,payment_voucher2,expenses_voucher2,customer_voucher2
        //JMenu voucher
        voucher.add(supplier_voucher2);
        voucher.add(payment_voucher2);
        voucher.add(expenses_voucher2);
        voucher.add(customer_voucher2);
        bar.add(voucher);
        
        
        stock=StaticMember.MyMenu("STOCK ","/images/menuicon/stockentry.png",20);//new JMenu("STOCK");
        stock.setMnemonic(KeyEvent.VK_S);
        stock.setFont(StaticMember.menufont);
        stock.setForeground(StaticMember.mcolor);
        bar.add(stock);
        addstock=new JMenuItem("Add Stock");
        viewstock=new JMenuItem("View Stock");
        view_stock_bill=new JMenuItem("View Stock Bill");
        stockmodify=new JMenuItem("Stock Modify");
        current_stock=new JMenuItem("Current Stock");
        stock.add(addstock);
        stock.add(stockmodify);
        stock.add(current_stock);
        //view.add(viewstock);
        stock.add(view_stock_bill);
        
        acount=StaticMember.MyMenu("ACCOUNT ","/images/menuicon/accounts.png",20);//new JMenu("ACCOUNT");
        acount.setMnemonic(KeyEvent.VK_C);
        acount.setFont(StaticMember.menufont);
        acount.setForeground(StaticMember.mcolor);
        cash=new JMenuItem("CASH");
        bank=new JMenuItem("BANK");
        supplier_cd_account=new JMenuItem("SUPPLIER CD A/C");
        customer_cd_account=new JMenuItem("CUSTOMER CD A/C");
        credit=new JMenuItem("CREDIT");
        debit=new JMenuItem("DEBIT");
        //JMenuItem debit,credit,customer_cd_account,supplier_cd_account,bank,cash,addstock
        //JMenu acount,stock
        acount.add(cash);
        acount.add(bank);
        acount.add(supplier_cd_account);
        acount.add(customer_cd_account);
        acount.add(credit);
        acount.add(debit);
        bar.add(acount);
        
        retun=StaticMember.MyMenu("RETRUN ","/images/menuicon/retun.png",20);//new JMenu("RETUN");
        retun.setMnemonic(KeyEvent.VK_R);
        retun.setFont(StaticMember.menufont);
        retun.setForeground(StaticMember.mcolor);
        salestatement=new JMenuItem("SALE STATEMENT");
        salereturn=new JMenuItem("SALE RETURN");
        bar.add(retun);
        retun.add(salestatement);
        retun.add(salereturn);
        billing=StaticMember.MyMenu("BILLING ","/images/menuicon/invoice.png",20);//new JMenu("BILLING");
        billing.setMnemonic(KeyEvent.VK_B);
        billing.setFont(StaticMember.menufont);
        billing.setForeground(StaticMember.mcolor);
        bar.add(billing);
        viewretailbill=new JMenuItem("VIEW BILL");
        inside_state=new JMenuItem("OUTSIDE STATE");
        outside_state=new JMenuItem("INSIDE STATE");
        retailbill=new JMenuItem("SALE BILL");
        bill_modify=new JMenuItem(" SALE BILL MODIFY");
        kbill=new JMenuItem("STIMEET BILL");
        kmodify=new JMenuItem("STIMEET BILL MODIFY");
         
        //JMenuItem selbill,kselbill,selmodify,kselmodify,inside_state,outside_state,retailbill
        //JMenu billing,retun
        view.add(viewretailbill);
        billing.add(retailbill);
        billing.add(bill_modify);
        billing.add(kbill);
        billing.add(kmodify);
        //billing.add(inside_state);
        //billing.add(outside_state);
        order=StaticMember.MyMenu("ORDER ","/images/menuicon/invoice.png",20);//new JMenu("BILLING");
        order.setMnemonic(KeyEvent.VK_O);
        order.setFont(StaticMember.menufont);
        order.setForeground(StaticMember.mcolor);
        create_order=new JMenuItem("CREATE ORDER");
        modify_order=new JMenuItem("MODIFY ORDER");
        order.add(create_order);
        order.add(modify_order);
        bar.add(order);
        
        //add menuBar on Frame
        this.setJMenuBar(bar);
        
        create_self_company.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                AdminLoginWindow luw=new AdminLoginWindow();
                    StaticMember.setVisibleFrame(luw);
             }        });
        create_user.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                c_n_u_obj=new CreateNewUser();
                StaticMember.setVisibleFrame(c_n_u_obj, StaticMember.CREATE_NEW_USER);
             }});
        
        latter_pad.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                l_p_obj=new LatterPad();
                StaticMember.setVisibleFrame(l_p_obj, StaticMember.LATTER_PAD);
             }});
        
        /*changeApwd.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(!MDIMainWindow.activeFrame[MDIMainWindow.chp])
                {
                    ChangePassword chp=new ChangePassword();
                    desktop.add(chp);
                    chp.setVisible(true);
                    chp.setResizable(false);
                    MDIMainWindow.activeFrame[MDIMainWindow.chp]=true;
                }
             }        });*/
        
        all_product_view.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                v_a_p_obj=new ViewProducts();
                StaticMember.setVisibleFrame(v_a_p_obj, StaticMember.VIEW_ALL_PRODUCT);
             }});
        company_view.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                v_comp_d_obj=new ViewCompanyDetails();
                StaticMember.setVisibleFrame(v_comp_d_obj, StaticMember.VIEW_COMPANY_DETAILS);
              }});
        
        manufacture_view.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                v_m_d_obj=new ViewManufactureDetails();
                StaticMember.setVisibleFrame(v_m_d_obj, StaticMember.VIEW_MANIFACTURE_DETAILS);
             }});
        viewsupplier.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               v_s_d_obj=new ViewSupplierDetails();
               StaticMember.setVisibleFrame(v_s_d_obj, StaticMember.VIEW_SUPPLIER_DETAILS);
           }});
        view_customer.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               v_cu_d_obj=new VIewCustomers();
               StaticMember.setVisibleFrame(v_cu_d_obj, StaticMember.VIEW_CUSTOMER_DETAILS);
           }});
        
        view_retuns.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               s_r_obj=new SaleRetun();
               StaticMember.setVisibleFrame(s_r_obj, StaticMember.SALE_RETUN);
            }
        });
        
        mexit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_MASK));
        mexit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(MDIMainWindow.this,"Are You Sure To Close Application?","Confirmtion Message",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                {//JOptionPane.showConfirmDialog(AddProductType.this,"Are You Sure To Close Application?","Confirmtion Message",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION
                    try 
                    {
                        StaticMember.con.close();
                    }catch (SQLException ex) {}
                    System.exit(0);
                }
               
             }        });
        
        addproduct.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               a_n_p_obj=new AddNewProducts();
               StaticMember.setVisibleFrame(a_n_p_obj, StaticMember.ADD_NEW_PRODUCT);
           }});
        updateproduct.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               u_p_obj=new UpdateProduct();
               StaticMember.setVisibleFrame(u_p_obj, StaticMember.UPDATE_PRODUCT);
           }});
        delproduct.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               d_p_obj=new DeleteProducts();
               StaticMember.setVisibleFrame(d_p_obj, StaticMember.DELETE_PRODUCT);
           }});
        addcompany.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
                a_n_c_obj=new AddNewCompany();
               StaticMember.setVisibleFrame(a_n_c_obj, StaticMember.ADD_NEW_COMPANY);
           }});
        updatecompany.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               u_c_obj=new UpdateCompany();
               StaticMember.setVisibleFrame(u_c_obj, StaticMember.UPDATE_COMPANY);
           }});
        delcompany.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               /*if(StaticMember.DELETE_COMPANY==false)
               {
                    d_c_obj=new DeleteCompany();
                    desktop.add(d_c_obj);
                    d_c_obj.setVisible(true);
                    d_c_obj.setResizable(false);
                    StaticMember.DELETE_COMPANY=true;
                }*/
               d_c_obj=new DeleteCompany();
               StaticMember.setVisibleFrame(d_c_obj, StaticMember.DELETE_COMPANY);
           }});
        addmanifacture.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               a_m_obj=new AddManufacture();
               StaticMember.setVisibleFrame(a_m_obj, StaticMember.ADD_MANUFACTURE);
           }});
        updatemanifacture.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               u_m_obj=new UpdateManufacture();
               StaticMember.setVisibleFrame(u_m_obj, StaticMember.UPDATE_MANUFACTURE);
           }});
        delmanifacture.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               d_m_obj=new DeleteManufacture();
               StaticMember.setVisibleFrame(d_m_obj, StaticMember.DELETE_MANUFACTURE);
           }});
        addtype.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               a_p_t_obj=new AddProductType();
               StaticMember.setVisibleFrame(a_p_t_obj, StaticMember.ADD_PRODUCT_TYPE);
           }});
        updatetype.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               u_p_t_obj=new UpdateProductType();
               StaticMember.setVisibleFrame(u_p_t_obj, StaticMember.UPDATE_TYPE);
           }});
        deltype.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               d_p_t_obj=new DeleteProductType();
               StaticMember.setVisibleFrame(d_p_t_obj, StaticMember.DELETE_TYPE);
           }});
        
        addsupplier.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               a_s_obj=new AddSupplier();
               StaticMember.setVisibleFrame(a_s_obj, StaticMember.ADD_SUPPLIER);
           }});
        
        updatesupplier.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               u_s_obj=new UpdateSupplier();
               StaticMember.setVisibleFrame(u_s_obj, StaticMember.UPDATE_SUPPLIER);
           }});
        delsupplier.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               d_s_obj=new DeleteSupplier();
               StaticMember.setVisibleFrame(d_s_obj, StaticMember.DELETE_SUPPLIER);
           }});
        
        addsupplier.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               a_s_obj=new AddSupplier();
               StaticMember.setVisibleFrame(a_s_obj, StaticMember.ADD_SUPPLIER);
           }});
        
        addcustomer.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               a_n_cu_obj=new AddNewCustomer();
               StaticMember.setVisibleFrame(a_n_cu_obj, StaticMember.ADD_NEW_CUSTOMER);
           }});
        updatecustomer.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               u_cu_obj=new UpdateCustomer();
               StaticMember.setVisibleFrame(u_cu_obj, StaticMember.UPDATE_CUSTOMER);
           }});
        delcustomer.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               d_cu_obj=new DeleteCustomer();
               StaticMember.setVisibleFrame(d_cu_obj, StaticMember.DELETE_CUSTOMER);
           }});
        current_stock.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                v_s_obj=new ViewStock();
                StaticMember.setVisibleFrame(v_s_obj, StaticMember.VIEW_STOCK);
             }});
        addemployee.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               a_n_e_obj=new AddNewEmployee();
               StaticMember.setVisibleFrame(a_n_e_obj, StaticMember.ADD_NEW_EMPLOYEE);
           }});
        updateemployee.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               u_e_obj=new UpdateEmployee();
               StaticMember.setVisibleFrame(u_e_obj, StaticMember.UPDATE_EMPLOYEE);
           }});
        delemployee.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               d_e_obj=new DeleteEmployee();
               StaticMember.setVisibleFrame(d_e_obj, StaticMember.DELETE_EMPLOYEE);
           }});
        addstock.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               s_obj=new Stock(data);
               StaticMember.setVisibleFrame(s_obj, StaticMember.ADD_STOCK);
           }});
        stockmodify.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
                s_m_obj=new StockModify();
                 StaticMember.setVisibleFrame(s_m_obj, StaticMember.STOCK_MODIFY);
           }});
        view_stock_bill.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
                v_s_b_obj=new ViewStockBill();
                 StaticMember.setVisibleFrame(v_s_b_obj, StaticMember.VIEW_STOCK_INVOICE);
           }});
        
        salestatement.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               s_s_obj=new SaleStatement();
               StaticMember.setVisibleFrame(s_s_obj, StaticMember.SALE_STATMENT);
           }});
        
        salereturn.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
                s_r_obj=new SaleRetun();
                 StaticMember.setVisibleFrame(s_r_obj, StaticMember.SALE_RETUN);
           }});
        
        retailbill.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               bl_obj=new Billing(data);
               StaticMember.setVisibleFrame(bl_obj, StaticMember.BILLING);
           }});
        bill_modify.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               bill_m_obj=new BillModify();
               StaticMember.setVisibleFrame(bill_m_obj, StaticMember.BILL_MODIFY);
           }});
        
        kbill.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               k_b_obj=new KBilling(data);
               StaticMember.setVisibleFrame(k_b_obj, StaticMember.K_BILLING);
           }});
        kmodify.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               k_b_m_obj=new KBillModify();
               StaticMember.setVisibleFrame(k_b_m_obj, StaticMember.K_BILLING_M);
               
           }});
        
        viewretailbill.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               v_b_w_obj=new ViewBillWindow();
               StaticMember.setVisibleFrame(v_b_w_obj, StaticMember.VIEW_BILL_WINDOW);
           }});
        daily_sale_report.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               d_s_r_obj=new DailySaleReport();
               StaticMember.setVisibleFrame(d_s_r_obj, StaticMember.DAILY_SALE_REPORT);
           }});
        
        sale_report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sl_r_obj=new SalesReport();
               StaticMember.setVisibleFrame(sl_r_obj, StaticMember.SALES_REPORT);
            }});
        sale_ledger.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               sl_l_obj=new SalesLedger();
               StaticMember.setVisibleFrame(sl_l_obj, StaticMember.SALES_LEDGER);
           }});
        create_order.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               c_o_obj=new CreateOrder(data);
               StaticMember.setVisibleFrame(c_o_obj, StaticMember.CREATE_ORDER);
           }});
        modify_order.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               m_o_obj=new ModifyOrder();
               StaticMember.setVisibleFrame(m_o_obj, StaticMember.MODIFY_ORDER);
           }});
        client_ledger.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
               cu_l_obj=new CustomerLedger();
               StaticMember.setVisibleFrame(cu_l_obj, StaticMember.CUSTOMER_LEDGER);
           }});
        
         
        JPanel spanel=new JPanel(new BorderLayout());
        JLabel l4=new JLabel(" Devloped By :- RANDHIR KUMAR GAYA  MOB - 8579095756 ",JLabel.CENTER);
        l4.setForeground(StaticMember.hc);
        l4.setFont(new Font("Monotype Corsiva",Font.BOLD|Font.ITALIC,20));
        spanel.add(l4,BorderLayout.WEST);
        this.add(spanel,BorderLayout.SOUTH);
        
        
        
        JPanel head_button_panel=new JPanel(new BorderLayout());
        JPanel head_label_panel=new JPanel(new GridLayout(1,3));
        JPanel left_head_panel=new JPanel(new GridLayout(1,2));
        JPanel left_head_button_panel=new JPanel(new GridLayout(1,4));
        JPanel right_head_button_panel=new JPanel(new GridLayout(1,3,5,5));
        
        stock_entry=StaticMember.MyMenuButton(" STOCK ENTRY  ", "/images/menuicon/stockentry.png", 23);//new JButton("LOG IN");  signout
        stock_modify=StaticMember.MyMenuButton(" STOCK MODIFY  ", "/images/menuicon/stockentry.png", 23);
        view_stock=StaticMember.MyMenuButton(" CHECK STOCK  ", "/images/menuicon/stockentry.png", 23);//new JButton("LOG IN");  signout
        retail_billing=StaticMember.MyMenuButton(" SALE BILLING  ", "/images/menuicon/invoice.png", 23);//new JButton("LOG IN");  signout
        b_modify=StaticMember.MyMenuButton(" SALE BILL MODIFY  ", "/images/menuicon/invoice.png", 23);
        view_billing=StaticMember.MyMenuButton(" VIEW BILL  ", "/images/menuicon/invoice.png", 23);//new JButton("LOG IN");  signout
        login=StaticMember.MyMenuButton("Login  ", "/images/menuicon/signin.png", 25);//new JButton("LOG IN");  signout
        logout=StaticMember.MyMenuButton("Login  ", "/images/menuicon/signout.png", 25);
       
        JLabel l=new JLabel("            ");
        l.setOpaque(true);
        l.setBackground(StaticMember.THBcolor);
        JLabel l2=new JLabel("            ");
        l2.setOpaque(true);
        l2.setBackground(StaticMember.THBcolor);
        JLabel l3=new JLabel("            ");
        l3.setOpaque(true);
        l3.setBackground(StaticMember.THBcolor);
        user_admin_name=new JLabel("");
        l.setOpaque(true);
        l.setBackground(StaticMember.THBcolor);
        right_head_button_panel.add(login);//right_head_button_panel.add(logout);
        right_head_button_panel.add(l);
        head_button_panel.add(right_head_button_panel,BorderLayout.EAST);
        head_button_panel.setBackground(StaticMember.THBcolor);
        head_label_panel.setBackground(StaticMember.THBcolor);
        left_head_button_panel.setBackground(StaticMember.THBcolor);
        head_button_panel.setBackground(StaticMember.THBcolor);
        right_head_button_panel.setBackground(StaticMember.THBcolor);
        left_head_button_panel.add(stock_entry);//left_head_button_panel.add(view_stock);
        left_head_button_panel.add(stock_modify);left_head_button_panel.add(retail_billing);
        left_head_button_panel.add(b_modify);
        head_label_panel.add(l2,BorderLayout.WEST);head_label_panel.add(user_admin_name,BorderLayout.CENTER);
        head_label_panel.add(l3,BorderLayout.EAST);
        left_head_panel.add(left_head_button_panel);left_head_panel.add(head_label_panel);
        head_button_panel.add(left_head_panel);
        this.add(head_button_panel,BorderLayout.NORTH);
        
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if(login.getText().equals("Login  "))   
                {
                    LoginUsers luw=new LoginUsers();
                    luw.setVisible(true);

                    if(luw.isSuccess==0)
                    {
                        JOptionPane.showMessageDialog(MDIMainWindow.self,"Invalid Credential!",StaticMember.INVALID_USER_TITLE,JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(luw.isSuccess==1)
                    {
                        active_user_menu();
                        
                        login.setText("Logout  ");
                        login.setIcon(StaticMember.getResizeImageIcon("/images/menuicon/signout.png",25,25));
                    }
                    else if(luw.isSuccess==2)
                    {
                        activeAdminMenu();
                        login.setText("Logout  ");
                        login.setIcon(StaticMember.getResizeImageIcon("/images/menuicon/signout.png",25,25));
                    }
                }
                else
                {
                    if(JOptionPane.showConfirmDialog(MDIMainWindow.this,"Are You Sure To Logout?","Confirm Dialog",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
                    {
                        disposeWindow();
                        deActiveAllMenu();
                        login.setText("Login  ");
                        login.setIcon(StaticMember.getResizeImageIcon("/images/menuicon/signin.png",25,25));
                    }
                }
            } });
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               deActiveAllMenu();
               
            } });
        stock_entry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               s_obj=new Stock(data);
               StaticMember.setVisibleFrame(s_obj, StaticMember.ADD_STOCK);
            } });
        stock_modify.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                s_m_obj=new StockModify();
                StaticMember.setVisibleFrame(s_m_obj, StaticMember.STOCK_MODIFY);
            } });
        retail_billing.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e) {
                bl_obj=new Billing(data);
                StaticMember.setVisibleFrame(bl_obj, StaticMember.BILLING);
            } });
        b_modify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                bill_m_obj=new BillModify();
                StaticMember.setVisibleFrame(bill_m_obj, StaticMember.BILL_MODIFY);
            } });
        view_billing.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                v_b_w_obj=new ViewBillWindow();
                StaticMember.setVisibleFrame(v_b_w_obj, StaticMember.VIEW_BILL);
            } });
        stock_report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                st_r_obj=new StockReport();
                StaticMember.setVisibleFrame(st_r_obj, StaticMember.STOCK_REPORT);
            } });
        credit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pay_s_obj=new PaymentSubmission();
                StaticMember.setVisibleFrame(pay_s_obj, StaticMember.PAYMENT_SUBMISSION);
            }});
        
        getbackup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                String path = "D:/databasebackup/komalhealthcare.sql";
                String username = "root";
                String password = "";
                String dbname = "rac";
                String executeCmd = "C:\\wamp\\bin\\mysql/bin/mysqldump -u " + username + " -p" + password + " --add-drop-database -B " + dbname + " -r " + path;
                Process runtimeProcess;
                try {
        //            System.out.println(executeCmd);//this out put works in mysql shell
                    runtimeProcess = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", executeCmd });
                    System.out.println(executeCmd);
        //            runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                    int processComplete = runtimeProcess.waitFor();
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"processComplete"+processComplete,StaticMember.SAVED_TITLE,JOptionPane.INFORMATION_MESSAGE);
                    //System.out.println("processComplete"+processComplete);
                    if (processComplete == 0) {
                        JOptionPane.showMessageDialog(MDIMainWindow.self,"Backup created successfully",StaticMember.INVALID_USER_TITLE,JOptionPane.INFORMATION_MESSAGE);
                        //System.out.println("Backup created successfully");

                    } else {
                        JOptionPane.showMessageDialog(MDIMainWindow.self,"Could not create the backup",StaticMember.INVALID_USER_TITLE,JOptionPane.INFORMATION_MESSAGE);
                        //System.out.println("Could not create the backup");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
               
            } });
        
        
       deActiveAllMenu();
        
        
       
    }
    
    public void getUserData(String udata)
    {
        
    }
    
    public void active_user_menu()
    {
        viewretailbill.setEnabled(true);
        retailbill.setEnabled(true);
        addstock.setEnabled(true);
        viewstock.setEnabled(true);
        all_product_view.setEnabled(true);
        company_view.setEnabled(true);
        viewsupplier.setEnabled(true);
        manufacture_view.setEnabled(true);
        viewretailbill.setEnabled(true);
        retailbill.setEnabled(true);
        create_self_company.setEnabled(false);
        create_user.setEnabled(false);
        stock_entry.setEnabled(true);
        retail_billing.setEnabled(true);
        view_billing.setEnabled(true);
        view_stock.setEnabled(true);
    }
    
    
    public void deActiv_menu_Item()
    {
        addemployee.setEnabled(false);
        updateemployee.setEnabled(false);
        delemployee.setEnabled(false);
        salestatement.setEnabled(false);
        salereturn.setEnabled(false);
        addproduct.setEnabled(false);
        updateproduct.setEnabled(false);
        delproduct.setEnabled(false);
        addmanifacture.setEnabled(false);
        updatemanifacture.setEnabled(false);
        delmanifacture.setEnabled(false);
        delcompany.setEnabled(false);
        addcompany.setEnabled(false);
        updatecompany.setEnabled(false);
        addsupplier.setEnabled(false);
        updatesupplier.setEnabled(false);
        delsupplier.setEnabled(false);
        addtype.setEnabled(false);
        updatetype.setEnabled(false);
        deltype.setEnabled(false);
        addstock.setEnabled(false);
        viewstock.setEnabled(false);
        all_product_view.setEnabled(false);
        company_view.setEnabled(false);
        viewsupplier.setEnabled(false);
        manufacture_view.setEnabled(false);
        viewretailbill.setEnabled(false);
        retailbill.setEnabled(false);
        kbill.setEnabled(false);
        kmodify.setEnabled(false);
        bill_modify.setEnabled(false);
        stock_report.setEnabled(false);
        stock_modify.setEnabled(false);
        stockmodify.setEnabled(false);
        view_stock_bill.setEnabled(false);
        current_stock.setEnabled(false);
        create_order.setEnabled(false);
        modify_order.setEnabled(false);
    }
    public void activeAdminMenu()
    {   
        manage_user.setEnabled(true);
        viewretailbill.setEnabled(true);
        addstock.setEnabled(true);
        viewstock.setEnabled(true);
        all_product_view.setEnabled(true);
        company_view.setEnabled(true);
        viewsupplier.setEnabled(true);
        manufacture_view.setEnabled(true);
        addemployee.setEnabled(true);
        updateemployee.setEnabled(true);
        delemployee.setEnabled(true);
        salestatement.setEnabled(true);
        salereturn.setEnabled(true);
        addproduct.setEnabled(true);
        updateproduct.setEnabled(true);
        delproduct.setEnabled(true);
        addmanifacture.setEnabled(true);
        updatemanifacture.setEnabled(true);
        delmanifacture.setEnabled(true);
        delcompany.setEnabled(true);
        addcompany.setEnabled(true);
        updatecompany.setEnabled(true);
        addsupplier.setEnabled(true);
        updatesupplier.setEnabled(true);
        delsupplier.setEnabled(true);
        addtype.setEnabled(true);
        updatetype.setEnabled(false);
        deltype.setEnabled(false);
        retailbill.setEnabled(true);
        stock_entry.setEnabled(true);
        retail_billing.setEnabled(true);
        view_billing.setEnabled(true);
        view_stock.setEnabled(true);
        b_modify.setEnabled(true);
        daily_sale_report.setEnabled(true);
        sale_report.setEnabled(true);
        sale_ledger.setEnabled(true);
        stock_report.setEnabled(true);
        addcustomer.setEnabled(true);
        view_user.setEnabled(true);
        view_customer.setEnabled(true);
        view_employee.setEnabled(true);
        updatecustomer.setEnabled(true);
        delcustomer.setEnabled(true);
        updatetype.setEnabled(true);
        deltype.setEnabled(true);
        kbill.setEnabled(true);
        kmodify.setEnabled(true);
        bill_modify.setEnabled(true);
        stock_report.setEnabled(true);
        stock_modify.setEnabled(true);
        stockmodify.setEnabled(true);
        view_stock_bill.setEnabled(true);
        current_stock.setEnabled(true);
        create_order.setEnabled(true);
        modify_order.setEnabled(true);
        credit.setEnabled(true);
        client_ledger.setEnabled(true);
        //deActiveAdminMenuItem();
    }
    public void deActiveAdminMenuItem()
    {
        stock_ledger.setEnabled(false);
        voucher_ledger.setEnabled(false);
        sale_ledger.setEnabled(false);
        supplier_voucher1.setEnabled(false);
        custum_ledger.setEnabled(false);
        cash_ledger.setEnabled(false);
        bank_ledger.setEnabled(false);
        client_ledger.setEnabled(false);
        gst_file_return.setEnabled(false);
        daily_sale_report.setEnabled(false);
        attendance_report.setEnabled(false);
        expenses_report.setEnabled(false);
        collection_report.setEnabled(false);
        cash_report.setEnabled(false);
        bank_report.setEnabled(false);
        supplier_voucher2.setEnabled(false);
        payment_voucher2.setEnabled(false);
        expenses_voucher2.setEnabled(false);
        customer_voucher2.setEnabled(false);
        inside_state.setEnabled(false);
        outside_state.setEnabled(false);
        debit.setEnabled(false);
        credit.setEnabled(false);
        customer_cd_account.setEnabled(false);
        supplier_cd_account.setEnabled(false);
        bank.setEnabled(false);
        cash.setEnabled(false);
        sale_report.setEnabled(false);
        delemployee.setEnabled(false);
        delmanifacture.setEnabled(false);
        delsupplier.setEnabled(false);
        view_bill.setEnabled(false);
        view_retuns.setEnabled(false);
        view_user.setEnabled(false);
        view_customer.setEnabled(false);
        view_employee.setEnabled(false);
        addcustomer.setEnabled(false);
        updatecustomer.setEnabled(false);
        delcustomer.setEnabled(false);
    }
    
    public void deActiveAllMenu()
    {
        deActiveAdminMenuItem();
        deActiv_menu_Item();
        stock_entry.setEnabled(false);
        retail_billing.setEnabled(false);
        view_billing.setEnabled(false);
        view_stock.setEnabled(false);
        b_modify.setEnabled(false);
    }
    
    
    
    
    /*AddManufacture a_m_obj;UpdateManufacture u_m_obj;DeleteManufacture d_m_obj;
    AddNewProducts a_n_p_obj;UpdateProduct u_p_obj;DeleteProducts d_p_obj;
    AddNewCompany a_n_c_obj;UpdateCompany u_c_obj;DeleteCompany d_c_obj;
    AddNewCustomer a_n_cu_obj;UpdateCustomer u_cu_obj;DeleteCustomer d_cu_obj;
    AddProductType a_p_t_obj;UpdateProductType u_p_t_obj;DeleteProductType d_p_t_obj;
    AddNewEmployee a_n_e_obj;//UpdateEmployee u_e_obj;DeleteEmployee d_e_obj;
    AddSupplier a_s_obj;UpdateSupplier u_s_obj;DeleteSupplier d_s_obj;
    ClientWindow c_w_obj;CreateNewUser c_n_u_obj;CreateSelfCompany c_s_c_obj;
    EmployeePayment e_p_obj;KBilling kb_obj;KBillingM kb_m_obj;KModify k_m_obj;
    Modify m_obj;RetailBilling r_b_obj;SaleRetun s_r_obj;SaleStatement s_s_obj;
    Stock s_obj;ViewAllProducts v_a_p_obj;ViewBill v_b_obj;ViewCompanyDetails v_c_d_obj;
    ViewCustomerDetails v_cu_d_obj;ViewKBill v_kb_obj;ViewManufactureDetails v_m_d_obj;
    ViewStockInvoice v_s_i_obj;ViewTypeDetails v_t_d_obj;*/
    public void disposeWindow()
    {
        
        if(StaticMember.BILL_MODIFY)
        {
            StaticMember.BILL_MODIFY=false;
            bill_m_obj.dispose();
        }
        if(StaticMember.ADD_MANUFACTURE)
        {
            StaticMember.ADD_MANUFACTURE=false;
            a_m_obj.dispose();
        }
        if(StaticMember.UPDATE_MANUFACTURE)
        {
            StaticMember.UPDATE_MANUFACTURE=false;
            u_m_obj.dispose();
        }
        if(StaticMember.DELETE_MANUFACTURE)
        {
            StaticMember.DELETE_MANUFACTURE=false;
            d_m_obj.dispose();
        }
        if(StaticMember.ADD_NEW_COMPANY)
        {
            StaticMember.ADD_NEW_COMPANY=false;
            a_n_c_obj.dispose();
        }
        if(StaticMember.UPDATE_COMPANY)
        {
            StaticMember.UPDATE_COMPANY=false;
            u_c_obj.dispose();
        }
        if(StaticMember.DELETE_COMPANY)
        {
            StaticMember.DELETE_COMPANY=false;
            d_c_obj.dispose();
        }
       
        if(StaticMember.ADD_NEW_EMPLOYEE)
        {
            StaticMember.ADD_NEW_EMPLOYEE=false;
            a_n_e_obj.dispose();
        }
        if(StaticMember.ADD_NEW_PRODUCT)
        {
            StaticMember.ADD_NEW_PRODUCT=false;
            a_n_p_obj.dispose();
        }
        if(StaticMember.UPDATE_PRODUCT)
        {
            StaticMember.UPDATE_PRODUCT=false;
            u_p_obj.dispose();
        }
        if(StaticMember.DELETE_PRODUCT)
        {
            StaticMember.DELETE_PRODUCT=false;
            d_p_obj.dispose();
        }
        if(StaticMember.ADD_PRODUCT_TYPE)
        {
            StaticMember.ADD_PRODUCT_TYPE=false;
            a_p_t_obj.dispose();
        }
        if(StaticMember.UPDATE_TYPE)
        {
            StaticMember.UPDATE_TYPE=false;
            u_p_t_obj.dispose();
        }
        if(StaticMember.DELETE_TYPE)
        {
            StaticMember.ADD_PRODUCT_TYPE=false;
            d_p_t_obj.dispose();
        }
        if(StaticMember.ADD_STOCK)
        {
            StaticMember.ADD_STOCK=false;
            s_obj.dispose();
        }
        if(StaticMember.ADD_SUPPLIER)
        {
            StaticMember.ADD_SUPPLIER=false;
            a_s_obj.dispose();
        }
        
        if(StaticMember.CREATE_NEW_USER)
        {
            StaticMember.CREATE_NEW_USER=false;
            c_n_u_obj.dispose();
        }
        if(StaticMember.CREATE_SELF_COMPANY)
        {
            StaticMember.CREATE_SELF_COMPANY=false;
            c_s_c_obj.dispose();
        }
        if(StaticMember.EMPLOYEE_PAYMENT)
        {
            StaticMember.EMPLOYEE_PAYMENT=false;
            e_p_obj.dispose();
        }
        if(StaticMember.SALE_RETUN)
        {
            StaticMember.SALE_RETUN=false;
            s_r_obj.dispose();
        }
        if(StaticMember.SALE_STATMENT)
        {
            StaticMember.SALE_STATMENT=false;
            s_s_obj.dispose();
        }
       
        if(StaticMember.VIEW_ALL_PRODUCT)
        {
            StaticMember.VIEW_ALL_PRODUCT=false;
            v_a_p_obj.dispose();
        }
        if(StaticMember.VIEW_COMPANY_DETAILS)
        {
            StaticMember.VIEW_COMPANY_DETAILS=false;
            v_comp_d_obj.dispose();
        }
        
        if(StaticMember.VIEW_MANIFACTURE_DETAILS)
        {
            StaticMember.VIEW_MANIFACTURE_DETAILS=false;
            v_m_d_obj.dispose();
        }
        if(StaticMember.VIEW_STOCK_INVOICE)
        {
            StaticMember.VIEW_STOCK_INVOICE=false;
            v_s_b_obj.dispose();
        }
        
        if(StaticMember.ADMIN_LOGIN_WINDOW)
        {
            StaticMember.ADMIN_LOGIN_WINDOW=false;
            a_l_w_obj.dispose();
        }
        
        if(StaticMember.VIEW_SUPPLIER_DETAILS)
        {
            StaticMember.VIEW_SUPPLIER_DETAILS=false;
            v_s_d_obj.dispose();
        }
        if(StaticMember.VIEW_BILL_WINDOW)
        {
            StaticMember.VIEW_BILL_WINDOW=false;
            v_b_w_obj.dispose();
        }
        if(StaticMember.DAILY_SALE_REPORT)
        {
            StaticMember.DAILY_SALE_REPORT=false;
            d_s_r_obj.dispose();
        }
        if(StaticMember.VIEW_STOCK)
        {
            StaticMember.VIEW_STOCK=false;
            v_s_obj.dispose();
        }
        if(StaticMember.SALES_REPORT)
        {
            StaticMember.SALES_REPORT=false;
            sl_r_obj.dispose();
        }
        if(StaticMember.SALES_LEDGER)
        {
            StaticMember.SALES_LEDGER=false;
            sl_l_obj.dispose();
        }
        if(StaticMember.STOCK_REPORT)
        {
            StaticMember.STOCK_REPORT=false;
            st_r_obj.dispose();
        }
        if(StaticMember.K_BILLING)
        {
            StaticMember.K_BILLING=false;
            k_b_obj.dispose();
        }
        if(StaticMember.K_BILLING_M)
        {
            StaticMember.K_BILLING_M=false;
            k_b_m_obj.dispose();
        }
        if(StaticMember.STOCK_MODIFY)
        {
            StaticMember.STOCK_MODIFY=false;
            s_m_obj.dispose();
        }
        if(StaticMember.VIEW_STOCK_INVOICE)
        {
            StaticMember.VIEW_STOCK_INVOICE=false;
            v_s_b_obj.dispose();
        }
        
    }
}
