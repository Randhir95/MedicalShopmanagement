/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Formatter;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JFrame;

/**
 *
 * @author RANDHIR KUMAR
 */
public class KSelectProduct extends JDialog{
    private JList product_list,batch_list,qty_list;
    private JLabel tcname,tmanufacture,tprate,tmrp,tsrate,tbatch,texpiry;
    private ResultSet rset=null,t=null,r=null,m=null,c=null,a=null;
    private String[] str;
    private String cstr,tstr,pstr,mstr;
    private KBilling kp;
    DefaultListModel pmodel,qmodel,bmodel;
    private ArrayList<String> product_id=new ArrayList<String>();
    private ArrayList<String> batch_id=new ArrayList<String>();
    
    public KSelectProduct(KBilling kb)
    {
        super(MDIMainWindow.self,"Select Your Product",true);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(StaticMember.SCREEN_WIDTH/2,StaticMember.SCREEN_HEIGHT-200);
        this.addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent we)
         {
             KSelectProduct.this.dispose();
         }
        });
        this.setLayout(new BorderLayout());
        this.setLocation(5, 75);
        
        kp=kb;
        
        JLabel serch_label=new JLabel("SERCH  :  ",JLabel.CENTER);
        JTextField searchtxt = StaticMember.MyInputBox("");
        pmodel = new DefaultListModel();
        qmodel = new DefaultListModel();
        bmodel = new DefaultListModel();
        JPanel sp=new JPanel(new BorderLayout());
        sp.add(serch_label,BorderLayout.WEST);
        sp.add(searchtxt,BorderLayout.CENTER);
        sp.add(new JLabel("    "),BorderLayout.EAST);
        
        JPanel ph=new JPanel(new GridLayout(1,2));
        JPanel ph2=new JPanel(new BorderLayout());
        ph.setBackground(StaticMember.bcolor);
        JLabel pname=new JLabel("         PRODUCT NAME");
        pname.setFont(StaticMember.labelFont);
        pname.setForeground(StaticMember.flcolor);
        JLabel batch=new JLabel("BATCH NO.");
        batch.setFont(StaticMember.labelFont);
        batch.setForeground(StaticMember.flcolor);
        JLabel quintity=new JLabel("QUINTITY          ");
        quintity.setFont(StaticMember.labelFont);
        quintity.setForeground(StaticMember.flcolor);
        ph2.add(batch,BorderLayout.CENTER);
        ph2.add(quintity,BorderLayout.EAST);
        ph.add(pname);ph.add(ph2);
        JPanel head=new JPanel(new BorderLayout());
        JPanel body=new JPanel(new BorderLayout());
        head.add(sp,BorderLayout.NORTH);
        this.add(head,BorderLayout.NORTH);
                
        //fetch data to data base
       try
        {
            Statement stmt=StaticMember.con.createStatement();
            ResultSet rset=stmt.executeQuery("select a.batch_no,a.mrp,a.import_rate,a.sale_rate,a.expary_date,a.quintity,a.packing,a.product_id product_id,a.manifacture_id manifacture_id,p.product_name pn,p.product_hsn_code as hsn_code,p.product_gst as product_gst from  availableitem a inner join products p on a.product_id=p.product_id and a.quintity>0 order by p.product_name");
            //find total no, of records
            rset.last();
            int tr=rset.getRow();
            str=new String[tr];
            rset.beforeFirst();
            int i=0;
            batch_id.clear();
            product_id.clear();
            pmodel.removeAllElements();
            qmodel.removeAllElements();
            bmodel.removeAllElements();
            
            while(rset.next())
            {
                ResultSet r=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+rset.getString("product_id")+"' order by product_name");
                r.next();
                ResultSet t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+r.getString("type_id")+"'");
                t.next();
                int qty=Integer.parseInt(rset.getString("quintity"));
                pmodel.addElement(rset.getString("pn").toUpperCase()+" "+ t.getString("type_name").toUpperCase());
                qmodel.addElement(qty+"");
                bmodel.addElement(rset.getString("batch_no"));
                product_id.add(rset.getString("product_id"));
                batch_id.add(rset.getString("batch_no"));
            }
        
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.toString(), "OOPs4!", JOptionPane.ERROR_MESSAGE);
        str=new String[0]; 
        }
       
        product_list=new JList(pmodel);
        batch_list=new JList(bmodel);
        qty_list=new JList(qmodel);
        
        product_list.requestFocusInWindow();
        product_list.setFont(StaticMember.textFont);
        batch_list.setFont(StaticMember.textFont);
        qty_list.setFont(StaticMember.textFont);
        product_list.setBackground(StaticMember.bcolor);
        qty_list.setBackground(StaticMember.bcolor);
        batch_list.setBackground(StaticMember.bcolor);
        JPanel lpanel=new JPanel(new GridLayout(1,2));
        JPanel lpanel2=new JPanel(new BorderLayout());
        lpanel2.add(batch_list,BorderLayout.CENTER);lpanel2.add(qty_list,BorderLayout.EAST);
        lpanel.add(product_list);lpanel.add(lpanel2);
        JScrollPane scr=new JScrollPane(lpanel);
        head.add(ph,BorderLayout.CENTER);
        body.add(scr,BorderLayout.CENTER);body.add(new JLabel("      "),BorderLayout.WEST);
        body.add(new JLabel("      "),BorderLayout.EAST);
        this.add(body,BorderLayout.CENTER);
        
        
        //code to access data to list and send data another interface
        product_list.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
               if(e.getKeyChar()=='\n')
               {
                   if(product_list.getSelectedIndex()==-1)
                    {
                        product_list.requestFocusInWindow();
                        return;
                    }
                   codeToSendData();
                   KSelectProduct.this.dispose();
               }
            }
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    searchtxt.requestFocusInWindow();
                }
            }
            public void keyReleased(KeyEvent e) {
                            }       });
        searchtxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                String text = searchtxt.getText().trim();
                //JOptionPane.showMessageDialog(null, text);
                pmodel.removeAllElements();
                qmodel.removeAllElements();
                bmodel.removeAllElements();
                try 
                {
                    Statement stmt=StaticMember.con.createStatement();
                    ResultSet rset=stmt.executeQuery("select a.batch_no,a.mrp,a.import_rate,a.sale_rate,a.expary_date,a.quintity,a.packing,a.product_id product_id,a.manifacture_id manifacture_id,p.product_name pn,p.type_id type_id,p.product_hsn_code as hsn_code,p.product_gst as product_gst from  availableitem a inner join products p on a.product_id=p.product_id and a.quintity>0 and (product_name like'%"+text+"%') order by p.product_name");
                    product_id.clear();
                    batch_id.clear();
                    while(rset.next())
                    {
                        product_id.add(rset.getString("product_id"));
                        ResultSet t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+rset.getString("type_id")+"'");
                        t.next();
                        int qty=Integer.parseInt(rset.getString("quintity"));
                        pmodel.addElement(rset.getString("pn").toUpperCase()+" "+ t.getString("type_name").toUpperCase());
                        qmodel.addElement(qty+"");
                        bmodel.addElement(rset.getString("batch_no"));
                        batch_id.add(rset.getString("batch_no"));
                    }

                    } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), StaticMember.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
                
            }
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    product_list.requestFocusInWindow();
                }
            }
        });
        
        product_list.addListSelectionListener(new ListSelectionListener(){
          public void valueChanged(ListSelectionEvent e){              
               try
                {   
                    if(product_list.getSelectedIndex()!=-1)
                    {   
                        Statement stmt=StaticMember.con.createStatement();
                        ResultSet rset=stmt.executeQuery("select * from  availableitem where quintity>0 and (batch_no like'"+batch_id.get(product_list.getSelectedIndex())+"')");
                        //find total no, of records
                        rset.next();
                        ResultSet r=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+rset.getString("product_id")+"'");
                        r.next();
                        ResultSet c=StaticMember.con.createStatement().executeQuery("select * from company where company_id like'"+r.getString("company_id")+"'");
                        c.next();
                        tcname.setText(c.getString("company_name").toUpperCase());
                        ResultSet m=StaticMember.con.createStatement().executeQuery("select * from manifacture where manifacture_id like'"+rset.getString("manifacture_id")+"'");
                        m.next();
                        tmanufacture.setText(m.getString("manifacture_name").toUpperCase());
                        tprate.setText(rset.getString("import_rate"));
                        tmrp.setText(rset.getString("mrp"));
                        tsrate.setText(rset.getString("sale_rate"));
                        texpiry.setText(rset.getString("expary_date"));
                        qty_list.setSelectedIndex(product_list.getSelectedIndex());
                        batch_list.setSelectedIndex(product_list.getSelectedIndex());
                    }
                }catch(SQLException ex)
                {                    
                    tcname.setText("");
                    JOptionPane.showMessageDialog(KSelectProduct.this, ex.getMessage(), "OOPs5!", JOptionPane.ERROR_MESSAGE);       
                }
          }   });
        //Create Details Information      
        JPanel detailPan=new JPanel(new GridLayout(3,2));
        JLabel cname=new JLabel(" COMPANY :  ",JLabel.RIGHT);
        cname.setFont(StaticMember.labelFont1);
        JLabel manufacture=new JLabel(" MANUFACTRE :  ",JLabel.RIGHT);
        manufacture.setFont(StaticMember.labelFont1);
        JLabel prate=new JLabel("PURCHASE RATE :  ",JLabel.RIGHT);
        prate.setFont(StaticMember.labelFont1);
        JLabel mrp=new JLabel(" MRP :  ",JLabel.RIGHT);
        mrp.setFont(StaticMember.labelFont1);
        JLabel srate=new JLabel(" SALE RATE :  ",JLabel.RIGHT);
        srate.setFont(StaticMember.labelFont1);
        JLabel expiry=new JLabel(" EXPARY :  ",JLabel.RIGHT);
        expiry.setFont(StaticMember.labelFont1);
        
        
        //Output of Detais 
        tcname=new JLabel("");
        tcname.setFont(StaticMember.textFont1);
        tmanufacture=new JLabel("");
        tmanufacture.setFont(StaticMember.textFont1);
        tmrp=new JLabel("");
        tmrp.setFont(StaticMember.textFont1);
        tprate=new JLabel("");
        tprate.setFont(StaticMember.textFont1);
        tsrate=new JLabel("");
        tsrate.setFont(StaticMember.textFont1);
        texpiry=new JLabel("");
        texpiry.setFont(StaticMember.textFont1);
        
        //Adding Component
        
        JPanel p2=new JPanel(new GridLayout(1,2));
        p2.add(cname,BorderLayout.WEST);
        p2.add(tcname,BorderLayout.CENTER);        
        detailPan.add(p2);
        JPanel p5=new JPanel(new GridLayout(1,2));
        p5.add(mrp,BorderLayout.WEST);
        p5.add(tmrp,BorderLayout.CENTER);
        detailPan.add(p5);
        JPanel p3=new JPanel(new GridLayout(1,2));
        p3.add(manufacture,BorderLayout.WEST);
        p3.add(tmanufacture,BorderLayout.CENTER);
        detailPan.add(p3);
        JPanel p4=new JPanel(new GridLayout(1,2));
        p4.add(prate,BorderLayout.WEST);
        p4.add(tprate,BorderLayout.CENTER);
        detailPan.add(p4);
        JPanel p7=new JPanel(new GridLayout(1,2));
        p7.add(expiry,BorderLayout.WEST);
        p7.add(texpiry,BorderLayout.CENTER);
        detailPan.add(p7);
        JPanel p6=new JPanel(new GridLayout(1,2));
        p6.add(srate,BorderLayout.WEST);
        p6.add(tsrate,BorderLayout.CENTER);
        detailPan.add(p6);
        
        this.add(detailPan,BorderLayout.SOUTH);
    }
    
    public void codeToSendData()
   {
       String str[]=new String[13];
       try
       {
            Statement stmt=StaticMember.con.createStatement();
            ResultSet rset=stmt.executeQuery("select * from  availableitem where quintity>0 and (batch_no like'"+batch_id.get(product_list.getSelectedIndex())+"')");
            rset.next();
            ResultSet r=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+rset.getString("product_id")+"' order by product_name");
            r.next();
            ResultSet t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+r.getString("type_id")+"'");
            t.next();
            ResultSet m=StaticMember.con.createStatement().executeQuery("select * from manifacture where manifacture_id like'"+rset.getString("manifacture_id")+"'");
            m.next();
            int qty=Integer.parseInt(rset.getString("quintity"));
            str[0]=r.getString("product_name")+"  "+t.getString("type_name")+"  "+rset.getString("packing");
            str[1]=rset.getString("batch_no");
            str[2]=rset.getString("sale_rate");
            str[3]=r.getString("product_gst");
            str[4]=rset.getString("mrp");
            str[5]=rset.getString("expary_date");
            str[6]=m.getString("manifacture_name");
            str[7]=r.getString("product_id");
            str[8]=m.getString("manifacture_id");
            str[9]=qty+"";
            str[10]=r.getString("product_hsn_code");
            
            
       kp.ksetItemsValue(str);
        }
       catch(SQLException ex)
       {
           JOptionPane.showMessageDialog(KSelectProduct.this, ex.getMessage(), "OOPs!", JOptionPane.ERROR_MESSAGE);       
       }
   }
}
