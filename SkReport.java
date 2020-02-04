package komalhealthcare;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class SkReport extends JFrame {

    JTextField text_label[];
    JDateChooser todate;
    JDateChooser fromdate;
    JButton generate_report,save_report;
    JPanel show_data;
    int price_id;
    public SkReport() {
        super("");
        this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        this.setSize(StaticMember.SCREEN_WIDTH, StaticMember.SCREEN_HEIGHT);
        this.setLayout(new BorderLayout());
        this.setBackground(StaticMember.WINDOW_BG_COLOR);
        /*this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                //StaticMember.STOCK_REPORT = false;
            }
        });*/

        JPanel body_panel = new JPanel(new BorderLayout());
        JPanel searching_panel = new JPanel(new GridLayout(1, 8,10,10));
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
        body_panel.add(searching_panel, BorderLayout.NORTH);
        show_data=new JPanel(new GridLayout(1,1));
        body_panel.add(show_data);
        this.add(body_panel, BorderLayout.CENTER);
        
        generate_report.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(fromdate.getDate()==null || todate.getDate()==null)
                {
                    JOptionPane.showMessageDialog(MDIMainWindow.self,"Date Must Be Entered!",StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
                    fromdate.requestFocusInWindow();
                }
                generate_report();
            }
        });
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
        int row = 0;
        try {
            ResultSet rset = StaticMember.con.createStatement().executeQuery("SELECT count(*) as total_rows FROM availableitem a join products as p  on p.product_id=a.product_id");
            rset.next();
            row = rset.getInt("total_rows");
        } catch (SQLException sqe) {

        }
        Border b2 = BorderFactory.createMatteBorder(0, 0, 0, 3, new Color(184, 207, 229));
        Border b3 = BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(184, 207, 229));
        int min_row = 21;
        JPanel main_panel = new JPanel(new BorderLayout());
        JPanel panel_body = new JPanel(new GridLayout(row < min_row ? min_row : row, 1));

        JPanel panel_row_head = new JPanel(new BorderLayout());
        JLabel head_txt1 = StaticMember.MyLabel("Particular", JLabel.CENTER);
        head_txt1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(184, 207, 229)));
        head_txt1.setPreferredSize(new Dimension(250,45));
        panel_row_head.add(head_txt1, BorderLayout.WEST);

        JPanel panel_row_center1 = new JPanel(new GridLayout(2, 12));
        JLabel temp_l;
        temp_l = new JLabel("");
        temp_l.setBorder(b3);
        panel_row_center1.add(temp_l);
        panel_row_center1.add(StaticMember.MyLabel("Inwards"));
        temp_l = new JLabel("");
        temp_l.setBorder(b2);
        panel_row_center1.add(temp_l);

        temp_l = new JLabel("");
        temp_l.setBorder(b3);
        panel_row_center1.add(temp_l);
        panel_row_center1.add(StaticMember.MyLabel("Current"));
        temp_l = new JLabel("");
        temp_l.setBorder(b2);
        panel_row_center1.add(temp_l);

        temp_l = new JLabel("");
        temp_l.setBorder(b3);
        panel_row_center1.add(temp_l);
        panel_row_center1.add(StaticMember.MyLabel("Outwards"));
        temp_l = new JLabel("");
        temp_l.setBorder(b2);
        panel_row_center1.add(temp_l);

        temp_l = new JLabel("");
        temp_l.setBorder(b3);
        panel_row_center1.add(temp_l);
        panel_row_center1.add(StaticMember.MyLabel("Closing"));
        temp_l = new JLabel("");
        panel_row_center1.add(temp_l);

        temp_l = StaticMember.MyLabel("Quantity", JLabel.CENTER);
        temp_l.setBorder(b3);
        panel_row_center1.add(temp_l);
        panel_row_center1.add(StaticMember.MyLabel("Rate (\u20B9)", JLabel.CENTER));
        temp_l = StaticMember.MyLabel("Value (\u20B9)", JLabel.CENTER);
        temp_l.setBorder(b2);
        panel_row_center1.add(temp_l);

        temp_l = StaticMember.MyLabel("Quantity", JLabel.CENTER);
        temp_l.setBorder(b3);
        panel_row_center1.add(temp_l);
        panel_row_center1.add(StaticMember.MyLabel("Rate (\u20B9)", JLabel.CENTER));
        temp_l = StaticMember.MyLabel("Value in (\u20B9)", JLabel.CENTER);
        temp_l.setBorder(b2);
        panel_row_center1.add(temp_l);

        temp_l = StaticMember.MyLabel("Quantity", JLabel.CENTER);
        temp_l.setBorder(b3);
        panel_row_center1.add(temp_l);
        panel_row_center1.add(StaticMember.MyLabel("Rate (\u20B9)", JLabel.CENTER));
        temp_l = StaticMember.MyLabel("Value (\u20B9)", JLabel.CENTER);
        temp_l.setBorder(b2);
        panel_row_center1.add(temp_l);

        temp_l = StaticMember.MyLabel("Quantity", JLabel.CENTER);
        temp_l.setBorder(b3);
        panel_row_center1.add(temp_l);
        panel_row_center1.add(StaticMember.MyLabel("Rate (\u20B9)", JLabel.CENTER));
        panel_row_center1.add(StaticMember.MyLabel("Value (\u20B9)", JLabel.CENTER));
        panel_row_head.add(panel_row_center1, BorderLayout.CENTER);
        JLabel l1 = new JLabel();
        l1.setPreferredSize(new Dimension(15, 10));
        if (row > min_row) {
            panel_row_head.add(l1, BorderLayout.EAST);
        }
        main_panel.add(panel_row_head, BorderLayout.NORTH);
        try {
            ResultSet prset = StaticMember.con.createStatement().executeQuery("SELECT * FROM availableitem a join products as p on a.product_id=p.product_id where a.quintity>0");
            while (prset.next()) {            
                
                row++;
                ResultSet qrset = StaticMember.con.createStatement().executeQuery("SELECT sum(qty) as total_quantity FROM stock_item where product_id=" + prset.getInt("product_id") + " and added_on<'" + start_date + "'");
                qrset.next();
                ResultSet bi_rset = StaticMember.con.createStatement().executeQuery("SELECT sum(bi.quintity) as total_billing_quantity FROM retail_bill_items bi join retail_bill bif on bi.retail_bill_no=bif.retail_bill_no where bi.product_id=" + prset.getInt("product_id") + " and bif.retail_bill_date<'" + start_date + "'");
                bi_rset.next();
                long inwards_quantity = qrset.getLong("total_quantity") - bi_rset.getLong("total_billing_quantity");

                qrset = StaticMember.con.createStatement().executeQuery("SELECT sum(quintity) as total_quantity FROM availableitem where product_id=" + prset.getInt("product_id") + " and added_on>='" + start_date + "' and added_on<='" + end_date + "'");
                qrset.next();
                long today_quantity = qrset.getLong("total_quantity");

                bi_rset = StaticMember.con.createStatement().executeQuery("SELECT sum(bi.quintity) as total_billing_quantity FROM retail_bill_items bi join retail_bill bif on bi.retail_bill_no=bif.retail_bill_no where bi.product_id=" + prset.getInt("product_id") + " and bif.retail_bill_date>='" + start_date + "' and bif.retail_bill_date<='" + end_date + "'");
                bi_rset.next();
                long outwards_quantity = bi_rset.getLong("total_billing_quantity");

                long closing_quantity = inwards_quantity + today_quantity - outwards_quantity;
                
                
                ResultSet pri_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM availableitem where product_id="+prset.getInt("p.product_id")+" and price_to_date is null");
                    pri_rset.next();
                    float mrp=pri_rset.getFloat("import_rate");
                    mrp+=mrp*pri_rset.getFloat("sale_rate")/100.0f;
                    String packing=prset.getString("packing");
                    if(packing.indexOf(" &")!=-1 && packing.substring(0,packing.indexOf(" &")).equals("Case"))
                        mrp/=prset.getInt("value_of_packing");
                  
                String utype;
                    if(prset.getInt("value_of_packing")==1)
                        utype=prset.getString("packing");
                    else if(packing.substring(0,packing.indexOf(" &")).equals("Case"))
                        utype=prset.getString("packing").substring(prset.getString("packing").indexOf(" &")+3);
                    else utype=prset.getString("packing").substring(0,prset.getString("packing").indexOf(" &"));
                    
                    String inwards_qtys;
                    if(prset.getInt("value_of_packing")==1000)
                        inwards_qtys=(inwards_quantity/(float)1000)+"";
                    else inwards_qtys=inwards_quantity+"";
                    inwards_qtys=inwards_qtys+" "+utype;
                    
                    String today_qtys;
                    if(prset.getInt("value_of_packing")==1000)
                        today_qtys=(today_quantity/(float)1000)+"";
                    else today_qtys=today_quantity+"";
                    today_qtys=today_qtys+" "+utype; 
                    
                    String outwards_qtys;
                    if(prset.getInt("value_of_packing")==1000)
                        outwards_qtys=(outwards_quantity/(float)1000)+"";
                    else outwards_qtys=outwards_quantity+"";
                    outwards_qtys=outwards_qtys+" "+utype;
                    
                    String closing_qtys;
                    if(prset.getInt("value_of_packing")==1000)
                        closing_qtys=(closing_quantity/(float)1000)+"";
                    else closing_qtys=closing_quantity+"";
                    closing_qtys=closing_qtys+" "+utype;
                
                Border b1 = BorderFactory.createMatteBorder(1, 1, 1, 3, new Color(184, 207, 229));
                JPanel panel_row = new JPanel(new BorderLayout());
                JTextField text_label = StaticMember.MyInputBox(prset.getString("product_name"), false);
                text_label.setBorder(b1);
                text_label.setPreferredSize(new Dimension(250, 25));
                panel_row.add(text_label, BorderLayout.WEST);
                JTextField temp;
                JPanel panel_row_center = new JPanel(new GridLayout(1, 12));
                temp = StaticMember.MyInputBox(inwards_qtys, false);
                panel_row_center.add(temp);
                temp = StaticMember.MyInputBox(String.format("%.02f",mrp), false);
                panel_row_center.add(temp);
                temp = StaticMember.MyInputBox(String.format("%.02f",mrp*inwards_quantity), false);
                temp.setBorder(b1);
                panel_row_center.add(temp);

                temp = StaticMember.MyInputBox(today_qtys + "", false);
                panel_row_center.add(temp);
                temp = StaticMember.MyInputBox(String.format("%.02f",mrp), false);
                panel_row_center.add(temp);
                temp = StaticMember.MyInputBox(String.format("%.02f",mrp*today_quantity), false);
                temp.setBorder(b1);
                panel_row_center.add(temp);

                temp = StaticMember.MyInputBox(outwards_qtys + "", false);
                panel_row_center.add(temp);
                temp = StaticMember.MyInputBox(String.format("%.02f",mrp), false);
                panel_row_center.add(temp);
                temp = StaticMember.MyInputBox(String.format("%.02f",mrp*outwards_quantity), false);
                temp.setBorder(b1);
                panel_row_center.add(temp);

                temp = StaticMember.MyInputBox(closing_qtys + "", false);
                panel_row_center.add(temp);
                temp = StaticMember.MyInputBox(String.format("%.02f",mrp), false);
                panel_row_center.add(temp);
                temp = StaticMember.MyInputBox(String.format("%.02f",mrp*closing_quantity), false);
                panel_row_center.add(temp);
                panel_row.add(panel_row_center, BorderLayout.CENTER);

                panel_body.add(panel_row);
            }
        } catch (SQLException sqe) {
            JOptionPane.showMessageDialog(null,sqe.getMessage());
        }
        main_panel.add(row > min_row ? new JScrollPane(panel_body) : panel_body, BorderLayout.CENTER);
        show_data.removeAll();
        show_data.add(main_panel);
        show_data.revalidate();
        show_data.repaint();
    }
    public void save_report() 
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
        int row=0;
        try {
            ResultSet rset = StaticMember.con.createStatement().executeQuery("SELECT count(*) as total_rows FROM product p join product_category as pc on p.pro_cat_id=pc.sr_number");
            rset.next();
            row = rset.getInt("total_rows");
        } catch (SQLException sqe) {
            
        }
        String data[]=new String[+1];
        
        int current_row=0;
        data[current_row++]="Particular~Quantity~Rate (\u20B9)~Value (\u20B9)~Quantity~Rate (\u20B9)~Value (\u20B9)~Quantity~Rate (\u20B9)~Value (\u20B9)~Quantity~Rate (\u20B9)~Value (\u20B9)";
        
        try {
            ResultSet prset = StaticMember.con.createStatement().executeQuery("SELECT * FROM product p join product_category as pc on p.pro_cat_id=pc.sr_number where p.product_quantity>0");
            while (prset.next()) {            
                
                row++;
                ResultSet qrset = StaticMember.con.createStatement().executeQuery("SELECT sum(product_quantity) as total_quantity FROM product_quantity_addedon where product_id=" + prset.getInt("product_id") + " and add_quantity_date<'" + start_date + "'");
                qrset.next();
                ResultSet bi_rset = StaticMember.con.createStatement().executeQuery("SELECT sum(bi.quantity) as total_billing_quantity FROM billing_item bi join billing_info bif on bi.billing_id=bif.billing_id where bi.product_id=" + prset.getInt("product_id") + " and bif.billing_date<'" + start_date + "'");
                bi_rset.next();
                long inwards_quantity = qrset.getLong("total_quantity") - bi_rset.getLong("total_billing_quantity");

                qrset = StaticMember.con.createStatement().executeQuery("SELECT sum(product_quantity) as total_quantity FROM product_quantity_addedon where product_id=" + prset.getInt("product_id") + " and add_quantity_date>='" + start_date + "' and add_quantity_date<='" + end_date + "'");
                qrset.next();
                long today_quantity = qrset.getLong("total_quantity");

                bi_rset = StaticMember.con.createStatement().executeQuery("SELECT sum(bi.quantity) as total_billing_quantity FROM billing_item bi join billing_info bif on bi.billing_id=bif.billing_id where bi.product_id=" + prset.getInt("product_id") + " and bif.billing_date>='" + start_date + "' and bif.billing_date<='" + end_date + "'");
                bi_rset.next();
                long outwards_quantity = bi_rset.getLong("total_billing_quantity");

                long closing_quantity = inwards_quantity + today_quantity - outwards_quantity;
                
                
                ResultSet pri_rset=StaticMember.con.createStatement().executeQuery("SELECT * FROM product_price where product_id="+prset.getInt("p.product_id")+" and price_to_date is null");
                    pri_rset.next();
                    float mrp=pri_rset.getFloat("buy_price");
                    mrp+=mrp*pri_rset.getFloat("profit_in_per")/100.0f;
                    String packing=prset.getString("product_packing");
                    if(packing.indexOf(" &")!=-1 && packing.substring(0,packing.indexOf(" &")).equals("Case"))
                        mrp/=prset.getInt("value_of_packing");
                  
                String utype;
                    if(prset.getInt("value_of_packing")==1)
                        utype=prset.getString("product_packing");
                    else if(packing.substring(0,packing.indexOf(" &")).equals("Case"))
                        utype=prset.getString("product_packing").substring(prset.getString("product_packing").indexOf(" &")+3);
                    else utype=prset.getString("product_packing").substring(0,prset.getString("product_packing").indexOf(" &"));
                    
                    String inwards_qtys;
                    if(prset.getInt("value_of_packing")==1000)
                        inwards_qtys=(inwards_quantity/(float)1000)+"";
                    else inwards_qtys=inwards_quantity+"";
                    inwards_qtys=inwards_qtys+" "+utype;
                    
                    String today_qtys;
                    if(prset.getInt("value_of_packing")==1000)
                        today_qtys=(today_quantity/(float)1000)+"";
                    else today_qtys=today_quantity+"";
                    today_qtys=today_qtys+" "+utype; 
                    
                    String outwards_qtys;
                    if(prset.getInt("value_of_packing")==1000)
                        outwards_qtys=(outwards_quantity/(float)1000)+"";
                    else outwards_qtys=outwards_quantity+"";
                    outwards_qtys=outwards_qtys+" "+utype;
                    
                    String closing_qtys;
                    if(prset.getInt("value_of_packing")==1000)
                        closing_qtys=(closing_quantity/(float)1000)+"";
                    else closing_qtys=closing_quantity+"";
                    closing_qtys=closing_qtys+" "+utype;
                 
                data[current_row]=prset.getString("product_name");
                data[current_row]+="~"+inwards_qtys;
                data[current_row]+="~"+String.format("%.02f",mrp);
                data[current_row]+="~"+String.format("%.02f",mrp*inwards_quantity);
                data[current_row]+="~"+today_qtys;
                data[current_row]+="~"+String.format("%.02f",mrp);
                data[current_row]+="~"+String.format("%.02f",mrp*today_quantity);
                data[current_row]+="~"+outwards_qtys;
                data[current_row]+="~"+String.format("%.02f",mrp);
                data[current_row]+="~"+String.format("%.02f",mrp*outwards_quantity);
                data[current_row]+="~"+closing_qtys;
                data[current_row]+="~"+String.format("%.02f",mrp);
                data[current_row++]+="~"+String.format("%.02f",mrp*closing_quantity);
            }
        } catch (SQLException sqe) {
            JOptionPane.showMessageDialog(null,sqe.getMessage());
        }
        
    }
}
