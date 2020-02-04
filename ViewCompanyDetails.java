/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author abc
 */
public class ViewCompanyDetails extends JInternalFrame{
     private JButton close;
   private ResultSet rset=null;
   private String listItems[][];
    DefaultTableModel tableModel;
    JTable CompanyDetails;
    public ViewCompanyDetails()
    {
        super("Company List",true,true);
        this.setDefaultCloseOperation(ViewCompanyDetails.DISPOSE_ON_CLOSE);
        StaticMember.setSize(this);
        this.setLayout(new BorderLayout());
        String Heding[]={"SR","COMPANY NAME","MOBILE NO..","ADDRESS","EMAIL ID","DL NO.","GSTIN NO.","BANK NAME"};
        
        this.addInternalFrameListener(new InternalFrameAdapter(){
             public void internalFrameClosing(InternalFrameEvent e) {
                StaticMember.VIEW_COMPANY_DETAILS=false;
              }});
        
        JLabel h=new JLabel(" VIEW COMPANY DETAILS ",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        JLabel serch_label=StaticMember.MyLabel("SERCH  :  ",JLabel.RIGHT);
        JTextField searchtxt = StaticMember.MyInputBox("");
        close=StaticMember.MyButton("CLOSE","Click On Button To Close This Window");
        
        
         CompanyDetails=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                            };
        tableModel = new DefaultTableModel();//create table model
        //tableModel.isCellEditable(ERROR, NORMAL)
        tableModel.setDataVector(listItems,Heding);
        CompanyDetails.setModel(tableModel);//set table model in table
        CompanyDetails.getTableHeader().setResizingAllowed(false);//stop resizing cell
        CompanyDetails.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        TableColumn column=null;
        column=CompanyDetails.getColumnModel().getColumn(0);
        column.setPreferredWidth(5);
        column=CompanyDetails.getColumnModel().getColumn(1);
        column.setPreferredWidth(130);
        column=CompanyDetails.getColumnModel().getColumn(2);
        column.setPreferredWidth(50);
        column=CompanyDetails.getColumnModel().getColumn(4);
        column.setPreferredWidth(120);
        column=CompanyDetails.getColumnModel().getColumn(5);
        column.setPreferredWidth(70);
        column=CompanyDetails.getColumnModel().getColumn(6);
        column.setPreferredWidth(80);
        column=CompanyDetails.getColumnModel().getColumn(7);
        column.setPreferredWidth(110);
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        CompanyDetails.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        CompanyDetails.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        CompanyDetails.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        CompanyDetails.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        CompanyDetails.setFont(StaticMember.itemfont);
        CompanyDetails.getTableHeader().setFont(StaticMember.headfont);
        CompanyDetails.getTableHeader().setBackground(StaticMember.THBcolor);
        CompanyDetails.getTableHeader().setForeground(StaticMember.THFcolor);
        CompanyDetails.setBackground(StaticMember.TIBcolor);
        CompanyDetails.setForeground(StaticMember.TIFcolor);
        
        CompanyDetails.setRowHeight(22);
        JScrollPane scrl=new JScrollPane(CompanyDetails);
        
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
        
        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                StaticMember.VIEW_COMPANY_DETAILS=false;
                ViewCompanyDetails.this.dispose();
                
            }
        });
        close.addKeyListener(new KeyAdapter(){
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar()=='\n'){
                    StaticMember.VIEW_COMPANY_DETAILS=false;
                ViewCompanyDetails.this.dispose();}
           }     });
        searchtxt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                String text = searchtxt.getText().trim();
                setDataInTabel(text);
            }
        });
        setDataInTabel();
    }
    
    private void setDataInTabel(String text)
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
        try 
        {
           ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from company where (company_name like'%"+text+"%') order by company_name");
            while(rset.next())
            {
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+rset.getString("company_name")+" "," "+rset.getString("mobile_no")+"  "," "+rset.getString("address")+" ",rset.getString("email")," "+rset.getString("dl_no")+" "," "+rset.getString("c_gst")+" "," "+rset.getString("bank_name")+" "});
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
           ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from company order by company_name");
            while(rset.next())
            {
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1," "+rset.getString("company_name")+" "," "+rset.getString("mobile_no")+"  "," "+rset.getString("address")+" ",rset.getString("email")," "+rset.getString("dl_no")+" "," "+rset.getString("c_gst")+" "," "+rset.getString("bank_name")+" "});

            }

            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(MDIMainWindow.self, ex.getMessage(), StaticMember.ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }
}
