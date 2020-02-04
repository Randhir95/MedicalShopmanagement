/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
/**
 *
 * @author RANDHIR KUMAR
 */
public class MSelectAddProduct extends JDialog{
    private JList plist;
    private JButton close;
    private String[] pstr,p_id;
    private String sid,s,id,hsn,gst;
    private ResultSet rset=null,rset1=null,t=null;
    private StockModifyWindow smc;
    DefaultListModel model;
    private ArrayList<String> product_id=new ArrayList<String>();
    
    public MSelectAddProduct(StockModifyWindow mb)
    {
        super(MDIMainWindow.self,"Select Your Product",true);
        //this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
         public void windowClosing(WindowEvent we)
         {
             MSelectAddProduct.this.dispose();
         }
        });
        this.setLayout(new BorderLayout());
        this.setLocation(350, 100);
        //super("Add Stock");
        this.setSize(550, 650);
        this.setLayout(new BorderLayout());
        //this.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        smc=mb;
        
        JLabel serch_label=new JLabel("SERCH  :  ",JLabel.CENTER);
        serch_label.setFont(StaticMember.labelFont);
        JTextField searchtxt = StaticMember.MyInputBox("");
        model = new DefaultListModel();
        JPanel p2=new JPanel(new BorderLayout());
        p2.add(serch_label,BorderLayout.WEST); p2.add(searchtxt,BorderLayout.CENTER);p2.add(new JLabel("     "),BorderLayout.EAST);
        p2.setPreferredSize(new Dimension(450,30));
        this.add(p2,BorderLayout.NORTH);
        
        JPanel p1=new JPanel(new BorderLayout());
        p1.setBackground(StaticMember.bcolor);
        JLabel heding=new JLabel("SELECT PRODUCT",JLabel.CENTER);
        heding.setFont(StaticMember.headfont);
        heding.setForeground(StaticMember.bfcolor);
        p1.add(heding,BorderLayout.NORTH);
        
         try
        {
        Statement stmt=StaticMember.con.createStatement();
        rset=stmt.executeQuery("select * from products order by product_name");
        //find total no, of records
        rset.last();
        int tr=rset.getRow();
        pstr=new String[tr];
        p_id=new String[tr];
        rset.beforeFirst();
        int i=0;
        product_id.clear();
        while(rset.next())
        {
            t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+rset.getString("type_id")+"'");
            t.next();
            product_id.add(rset.getString("Product_id"));
            p_id[i]=rset.getString("Product_id");
            pstr[i++]=rset.getString("product_name")+"  "+t.getString("type_name");
            model.addElement(rset.getString("product_name").toUpperCase()+" "+ t.getString("type_name").toUpperCase());
        }
        
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs!", JOptionPane.ERROR_MESSAGE);
        pstr=new String[0]; 
        }
        model.removeAllElements();
        plist=new JList(model);
        plist.setFont(StaticMember.textFont);
        JScrollPane jp=new JScrollPane(plist);
        JPanel listPanel=new JPanel(new BorderLayout());
        listPanel.add(new JLabel("     "),BorderLayout.SOUTH);listPanel.add(new JLabel("     "),BorderLayout.EAST);
        listPanel.add(new JLabel("     "),BorderLayout.WEST);listPanel.add(jp,BorderLayout.CENTER);
        this.add(listPanel,BorderLayout.CENTER);
       
        searchtxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {

                String text = searchtxt.getText().trim();
                model.removeAllElements();
                try {
                        Statement stmt=StaticMember.con.createStatement();
                        rset=stmt.executeQuery("select * from products where (product_name like'%"+text+"%')order by product_name");
                        //rset.last();
                        //int tr=rset.getRow();
                        //pstr=new String[tr];
                        //p_id=new String[tr];
                        //rset.beforeFirst();
                        product_id.clear();
                        int i=0;
                        while(rset.next())
                        {
                            product_id.add(rset.getString("Product_id"));
                            //p_id[i++]=rset.getString("product_id");
                            t=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+rset.getString("type_id")+"'");
                            t.next();
                            //product_id.add(rset.getString("product_id"));
                            //model.removeAllElements();
                            model.addElement(rset.getString("product_name").toUpperCase()+" "+ t.getString("type_name").toUpperCase());
                        }
                        
                    } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), StaticMember.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
                }
                
            }
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    plist.requestFocusInWindow();
                }
            }
            
        });
        
        plist.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n')
                {
                    if(plist.getSelectedIndex()==-1)
                    {
                        plist.requestFocusInWindow();
                        return;
                    }
                    codeToSendData();
                    MSelectAddProduct.this.dispose();
                }
                
                
            }
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    searchtxt.requestFocusInWindow();
                }
            }
            public void keyReleased(KeyEvent e) {
            }        });
        /*plist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                codeToSendData();
            }
        });*/
        plist.addMouseListener(new MouseListener() {
           public void mouseClicked(MouseEvent e) {
                codeToSendData();
            }
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
            }
        });
       
       p1.add(jp,BorderLayout.CENTER);
        this.add(p1);
    }
    
    public void codeToSendData()
   {
       String str[]=new String[4];
        try{
                ResultSet p_rset=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+product_id.get(plist.getSelectedIndex())+"'");
                p_rset.next();
                
                ResultSet t_rset=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+p_rset.getString("type_id")+"'");
                t_rset.next();
                //JOptionPane.showMessageDialog(null,p_rset.getString("product_id")+" "+p_rset.getString("product_name")+" "+t_rset.getString("type_name"));
                str[0]=p_rset.getString("product_name")+" "+t_rset.getString("type_name");
                str[1]=p_rset.getString("product_id");
                str[2]=p_rset.getString("product_hsn_code");
                str[3]=p_rset.getString("product_gst");
            }catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "OOPs!", JOptionPane.ERROR_MESSAGE);
            }
      

        smc.msetItemsValue(str);
    }
}
