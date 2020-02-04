/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
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
 * @author abc
 */
public class ViewStock extends JInternalFrame {
    private JScrollPane ScrollPane;
    private JTable table;
    JTextField search;
    JLabel stock_value,sel_value,profit_value;
    DefaultTableModel tableModel;
    String product_name,hsn_code,packing,company,mfg,mrp,prate,srate,exp,batch,gst,qty,total_amt,text;
    private JButton close,show_all;
    private ResultSet rset=null;
    private String[][] astr,pstr,cstr;
    int active_column=-1;
    boolean asc_desc=true;
    String now_sorted_field="product_name";
    /*private static class WhiteYellowCellRenderer extends DefaultListCellRenderer {
    public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus ) {
        Component c = super.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
        if ( index % 2 == 0 ) {
            c.setBackground( Color.yellow );
        }
        else {
            c.setBackground( Color.white );
        }
        return c;
    }
}*/
    public ViewStock()
    {
        super("",true,true);
        this.setDefaultCloseOperation(ViewStock.DISPOSE_ON_CLOSE);
        StaticMember.setSize(this);

        this.addInternalFrameListener(new InternalFrameAdapter(){
              public void internalFrameClosing(InternalFrameEvent e) {
                 StaticMember.VIEW_STOCK=false;
               }});
        
         //create Apanel to hold all other Components

        JLabel h=new JLabel(" CURRENT STOCK ",JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        this.add(h,BorderLayout.NORTH);
        
        
        JLabel l2=new JLabel("SEARCH  :  ",JLabel.RIGHT);
        l2.setFont(StaticMember.labelFont);
        JLabel stockvalue=new JLabel("STOCK VALUE  :  ",JLabel.RIGHT);
        stockvalue.setFont(StaticMember.labelFont);
        JLabel selvalue=new JLabel("AVERAGE SALE VALUE  :  ",JLabel.RIGHT);
        selvalue.setFont(StaticMember.labelFont);
        JLabel profitvalue=new JLabel("AVERAGE PROFIT VALUE  :  ",JLabel.RIGHT);
        profitvalue.setFont(StaticMember.labelFont);
        
        stock_value=new JLabel("",JLabel.RIGHT);
        stock_value.setFont(StaticMember.labelFont);
        sel_value=new JLabel("",JLabel.RIGHT);
        sel_value.setFont(StaticMember.labelFont);
        profit_value=new JLabel("",JLabel.RIGHT);
        profit_value.setFont(StaticMember.labelFont);
        search=new JTextField("");
        search.setFont(StaticMember.textFont);
        
        close=new JButton("CLOSE");
        close.setFont(StaticMember.buttonFont);
        close.setForeground(StaticMember.bfcolor);
        
        //Create Colum Names
        String dataName[]={"SR.","HSN","PRODUCT NAME","QTY","PACKING","COMPANY","MAKE.","BATCH NO.","EXP","GST %","MRP","P RATE","S RATE","TOTAL AMT"};
        String data[][]=new String[0][];
         
        table=new JTable(){
                                public boolean isCellEditable(int rowIndex, int vColIndex) {
                                  return false;
                                }
                                
                            };
       
        tableModel = new DefaultTableModel();//create table model
        //tableModel.isCellEditable(ERROR, NORMAL)
        tableModel.setDataVector(data,dataName);
        table.setModel(tableModel);//set table model in table
        table.getTableHeader().setResizingAllowed(false);//stop resizing cell
        table.getTableHeader().setReorderingAllowed(false);//stop moving cell order
        table.getTableHeader().setFont(StaticMember.headfont);
        table.getTableHeader().setBackground(StaticMember.THBcolor);
        table.getTableHeader().setForeground(StaticMember.THFcolor);
        table.setFont(StaticMember.itemfont);
        table.setBackground(StaticMember.TIBcolor);
        table.setForeground(StaticMember.TIFcolor);
        table.setRowHeight(22);
        TableColumn column=null;
        column=table.getColumnModel().getColumn(0);
        column.setPreferredWidth(5);
        column=table.getColumnModel().getColumn(2);
        column.setPreferredWidth(175);
        column=table.getColumnModel().getColumn(1);
        column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(3);
        column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(4);
        column.setPreferredWidth(70);
        column=table.getColumnModel().getColumn(5);
        column.setPreferredWidth(90);
        column=table.getColumnModel().getColumn(6);
        column.setPreferredWidth(70);
        column=table.getColumnModel().getColumn(7);
        column.setPreferredWidth(80);
        column=table.getColumnModel().getColumn(8);
        column.setPreferredWidth(35);
        column=table.getColumnModel().getColumn(9);
        column.setPreferredWidth(30);
        column=table.getColumnModel().getColumn(10);
        column.setPreferredWidth(45);
        column=table.getColumnModel().getColumn(11);
        column.setPreferredWidth(45);
        column=table.getColumnModel().getColumn(12);
        column.setPreferredWidth(45);
        column=table.getColumnModel().getColumn(13);
        column.setPreferredWidth(70);
        
        DefaultTableCellRenderer rightRenderer=new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        DefaultTableCellRenderer centerRenderer=new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        //table.getColumnModel().getColumn(5).setCellRenderer();
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(12).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(11).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(10).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(9).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(8).setCellRenderer(rightRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(13).setCellRenderer(rightRenderer);
        
        for(int i=0;i<tableModel.getRowCount();i++)
        {
            int q=Integer.parseInt(table.getValueAt(i,12).toString().trim());
            if(q<=5)
            {
               
                //table.setBackground(Color.red);
            }
        }
        
       //Add table to a ScrollPane
       ScrollPane=new JScrollPane(table);
       
       JPanel main_panel=new JPanel(new BorderLayout());
       JPanel top_main_panel=new JPanel(new BorderLayout());
       JPanel serch_panel=new JPanel(new BorderLayout());
       JPanel top_panel=new JPanel(new GridLayout(1,3,10,10));
       JPanel foot_panel=new JPanel(new GridLayout(1,4,10,10));
       JPanel foot_main_panel=new JPanel(new BorderLayout());
       JPanel sel_panel=new JPanel(new GridLayout(1,2,5,5));
       JPanel stock_panel=new JPanel(new GridLayout(1,2,5,5));
       JPanel sel_amt_panel=new JPanel(new BorderLayout());
       JPanel stock_amt_panel=new JPanel(new BorderLayout());
       JPanel profit_panel=new JPanel(new GridLayout(1,2,5,5));
       JPanel profit_amt_panel=new JPanel(new BorderLayout());
       
       serch_panel.add(l2,BorderLayout.WEST);serch_panel.add(search,BorderLayout.CENTER);
       top_panel.add(serch_panel);top_panel.add(new JLabel(""));top_panel.add(new JLabel(""));
       top_main_panel.add(top_panel,BorderLayout.CENTER);top_main_panel.add(new JLabel(" "),BorderLayout.SOUTH);
       top_main_panel.add(new JLabel(" "),BorderLayout.NORTH);top_main_panel.add(new JLabel("           "),BorderLayout.WEST);
       main_panel.add(top_main_panel,BorderLayout.NORTH);main_panel.add(ScrollPane,BorderLayout.CENTER);
       
       stock_amt_panel.add(stock_value,BorderLayout.CENTER);stock_amt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
       sel_amt_panel.add(sel_value,BorderLayout.CENTER);sel_amt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
       profit_amt_panel.add(profit_value,BorderLayout.CENTER);profit_amt_panel.add(new JLabel("  \u20B9   "),BorderLayout.EAST);
       sel_panel.add(selvalue);sel_panel.add(sel_amt_panel);
       profit_panel.add(profitvalue);profit_panel.add(profit_amt_panel);
       stock_panel.add(stockvalue);stock_panel.add(stock_amt_panel);
       //foot_main_panel.add(new JLabel());foot_btn_panel.add(close);foot_btn_panel.add(new JLabel());
       foot_panel.add(profit_panel);foot_panel.add(sel_panel);foot_panel.add(stock_panel);
       foot_main_panel.add(close,BorderLayout.EAST);foot_main_panel.add(foot_panel,BorderLayout.CENTER);
       this.add(main_panel,BorderLayout.CENTER);
       this.add(foot_main_panel,BorderLayout.SOUTH);
       
       close.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
            StaticMember.VIEW_STOCK=false;
            ViewStock.this.dispose();
            
          }       });
       close.addKeyListener(new KeyListener(){
           public void keyTyped(KeyEvent e) {
               if(e.getKeyChar()=='\n'){
                   StaticMember.VIEW_STOCK=false;
            ViewStock.this.dispose();}
           }
           public void keyPressed(KeyEvent e) {           }
           public void keyReleased(KeyEvent e) {          }});
       search.addKeyListener(new KeyAdapter(){
            public void keyReleased(KeyEvent e) {
                if(search.getText().trim().equals(""))
                {
                    searchDataInTable();
                   
                }
                searchDataInTable();
            }
        });
       //order by "+now_sorted_field+" "+(asc_desc?"asc":"desc")
       table.getTableHeader().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int col = table.columnAtPoint(e.getPoint());
                        switch(col)
                        {
                            case 2: if(now_sorted_field.equals("product_name"))asc_desc=!asc_desc;
                                    else asc_desc=true;
                                    now_sorted_field="product_name";
                                    searchDataInTable(); 
                                    break; 
                            case 5: if(now_sorted_field.equals("company_name"))asc_desc=!asc_desc;
                                    else asc_desc=true;
                                    now_sorted_field="company_name";
                                    searchDataInTable(); 
                                    break;                                      
                        }
                    }
                });
       StaticMember.SetIconInTableHeader(table, 2, "PRODUCT NAME");
        StaticMember.SetIconInTableHeader(table, 5,"COMPANY");
       searchDataInTable();
    
       
    }
    
    
    public void searchDataInTable()
    {
        resetTable();
        text=search.getText().trim();
        try
        {
            float total_p_amt=0,total_s_amt=0,st_amt=0,profit_amt=0;
            Statement stmt=StaticMember.con.createStatement();
            ResultSet ra=stmt.executeQuery("select a.batch_no,a.mrp,a.import_rate,a.sale_rate,a.expary_date,a.quintity,a.packing,a.product_id product_id,a.manifacture_id manifacture_id,p.product_name pn,p.type_id type_id,p.product_gst product_gst,p.company_id company_id,p.product_hsn_code as hsn_code,c.company_name company_name,m.manifacture_name manifacture_name,t.type_name type_name from  availableitem a inner join products p on a.product_id=p.product_id inner join  manifacture m on a.manifacture_id=m.manifacture_id inner join type t on p.type_id=t.type_id inner join company c on p.company_id=c.company_id and a.quintity>0 and (product_name like'%"+text+"%') order by "+now_sorted_field+" "+(asc_desc?"asc":"desc"));
            while(ra.next())
            {
                product_name=ra.getString("pn").toUpperCase()+" "+ra.getString("type_name").toUpperCase();
                hsn_code=ra.getString("hsn_code").substring(0,4);
                company=ra.getString("company_name").toUpperCase();
                mfg=ra.getString("manifacture_name").toUpperCase();
                batch=ra.getString("batch_no").toUpperCase();
                exp=ra.getString("expary_date");
                gst=String.format("%.02f", Float.parseFloat(ra.getString("product_gst")));
                mrp=String.format("%.02f", Float.parseFloat(ra.getString("mrp")))+" \u20B9 ";
                srate=String.format("%.02f", Float.parseFloat(ra.getString("sale_rate")))+" \u20B9 ";
                prate=String.format("%.02f", Float.parseFloat(ra.getString("import_rate")))+" \u20B9 ";
                qty=ra.getString("quintity");
                packing=ra.getString("packing");
                float tpamt=Float.parseFloat(ra.getString("import_rate"))*Integer.parseInt(ra.getString("quintity"));
                float gstamt=tpamt*Float.parseFloat(gst)/100;
                float t_amount=tpamt+gstamt;
                total_amt=String.format("%.02f", t_amount)+" \u20B9 ";
                float tsamt=Float.parseFloat(ra.getString("sale_rate"))*Integer.parseInt(ra.getString("quintity"));
                st_amt=tsamt+gstamt;
                tableModel.addRow(new Object[]{tableModel.getRowCount()+1,"  "+hsn_code+"  ","  "+product_name+"  ","  "+qty+"  ","  "+packing+"  ","  "+company+"  ","  "+mfg+"  ","  "+batch+"  ","  "+exp+"  ","  "+gst+"  "," "+mrp+" "," "+prate+" "," "+srate+" "," "+total_amt+" "});
                //astr[i++][12]=ra.getString("added_on");
                total_p_amt+=t_amount;
                total_s_amt+=st_amt;
            }
            profit_amt=total_s_amt-total_p_amt;
            stock_value.setText(String.format("%.02f", total_p_amt));
            sel_value.setText(String.format("%.02f", total_s_amt));
            profit_value.setText(String.format("%.02f", profit_amt));       
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "OOPs1!", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void resetTable()
    {
        tableModel.getDataVector().removeAllElements();
        tableModel.addRow(new Object[]{});
        tableModel.removeRow(0);
    }
    
    
    
}
