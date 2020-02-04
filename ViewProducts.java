/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Randhir
 */
public class ViewProducts extends JInternalFrame{
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField product_name;
    private JButton close;
    private ResultSet rset=null;
    private String listItems[][];
    public ViewProducts()
    {
        super("",true,true);
        this.setDefaultCloseOperation(ViewCompanyDetails.DISPOSE_ON_CLOSE);
        StaticMember.setSize(this);
        this.setLayout(new BorderLayout());
        String Heding[]={"SR","PRODUCT NAME","HSN CODE","COMPANY NAME","TYPE","GST"};
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.VIEW_ALL_PRODUCT=false;
              }});
        
        JLabel h=new JLabel(" VIEW PRODUCT DETAILS ",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        JLabel serch_label=StaticMember.MyLabel("SERCH  :  ",JLabel.RIGHT);
        JTextField searchtxt = StaticMember.MyInputBox("");
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        
        
        table=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        //tableModel.isCellEditable(ERROR, NORMAL)
        tableModel.setDataVector(listItems,Heding);
        table.setModel(tableModel);//set table model in table
        table.getTableHeader().setResizingAllowed(false);//stop resizing cell
        table.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        TableColumn column=null;
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(5);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(130);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(50);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(120);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(70);
       
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        table.setFont(StaticMember.itemfont);
        table.getTableHeader().setFont(StaticMember.headfont);
        table.getTableHeader().setBackground(StaticMember.THBcolor);
        table.getTableHeader().setForeground(StaticMember.THFcolor);
        table.setBackground(StaticMember.TIBcolor);
        table.setForeground(StaticMember.TIFcolor);
        
        table.setRowHeight(22);
        JScrollPane scrl=new JScrollPane(table);
        
        searchtxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String text = searchtxt.getText().trim();
                setDataInTabel(text);
            }
        });
        close.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode()=='\n')
                    ViewProducts.this.dispose();
            }
        });
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ViewProducts.this.dispose();
            }});
        
        JPanel main_panel=new JPanel(new BorderLayout());
        JPanel tabel_panel=new JPanel(new BorderLayout());
        JPanel head_panel=new JPanel(new BorderLayout());
        JPanel serch_panel=new JPanel(new GridLayout(1,6,10,10));
        JPanel btn_panel=new JPanel(new GridLayout(1,10,10,10));
        
        this.add(main_panel,BorderLayout.CENTER);
        main_panel.add(new JLabel("     "),BorderLayout.WEST);main_panel.add(new JLabel("     "),BorderLayout.EAST);
        main_panel.add(new JLabel("     "),BorderLayout.SOUTH);main_panel.add(new JLabel("     "),BorderLayout.NORTH);
        main_panel.add(head_panel,BorderLayout.CENTER);
        head_panel.add(serch_panel,BorderLayout.NORTH);head_panel.add(tabel_panel,BorderLayout.CENTER);
        serch_panel.add(serch_label);serch_panel.add(searchtxt);serch_panel.add(new JLabel("     "));
        serch_panel.add(new JLabel("     "));serch_panel.add(new JLabel("     "));serch_panel.add(new JLabel("     "));
        tabel_panel.add(new JLabel("     "),BorderLayout.NORTH);tabel_panel.add(scrl,BorderLayout.CENTER);
        tabel_panel.add(btn_panel,BorderLayout.SOUTH);
        btn_panel.add(new JLabel(" "));btn_panel.add(new JLabel(" "));btn_panel.add(new JLabel(" "));btn_panel.add(new JLabel(" "));
        btn_panel.add(new JLabel(" "));btn_panel.add(new JLabel(" "));btn_panel.add(new JLabel(" "));btn_panel.add(new JLabel(" "));
        btn_panel.add(new JLabel(" "));btn_panel.add(close);
        setDataInTabel();
    }
    
    private void setDataInTabel(String text)
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
        try 
        {
           ResultSet rset=StaticMember.con.createStatement().executeQuery("select p.product_name pn,p.product_hsn_code as hsn_code,p.product_gst as product_gst, c.company_name as company_name,t.type_name as type_name from  (products p inner join company c on p.company_id=c.company_id) join type t on p.type_id = t.type_id and (product_name like'%"+text+"%') order by p.product_name");
            while(rset.next())
            {
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+rset.getString("pn")+" "," "+rset.getString("hsn_code")+"  "," "+rset.getString("company_name")+" ",rset.getString("type_name")," "+rset.getString("product_gst")+" "});

            }

            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), StaticMember.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
    private void setDataInTabel()
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
        try 
        {
           ResultSet rset=StaticMember.con.createStatement().executeQuery("select p.product_name pn,p.product_hsn_code as hsn_code,p.product_gst as product_gst, c.company_name as company_name,t.type_name as type_name from  (products p inner join company c on p.company_id=c.company_id) join type t on p.type_id = t.type_id order by p.product_name");
            while(rset.next())
            {
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+rset.getString("pn")+" "," "+rset.getString("hsn_code")+"  "," "+rset.getString("company_name")+" ",rset.getString("type_name")," "+rset.getString("product_gst")+" "});

            }

            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), StaticMember.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
}
