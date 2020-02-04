/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import javax.swing.JOptionPane;

/**
 *
 * @author RANDHIR KUMAR
 */
public class PrintOrderPage implements Printable {
    String orderno;
    String items[][];
    String customer[];
    ResultSet order_rset=null,item_rset=null,rset=null;
    public PrintOrderPage(String on)
    {
      orderno=on;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if(pageIndex>0)
                return NO_SUCH_PAGE;
            Graphics2D g=(Graphics2D)graphics;
         
         int x=9,y=10,w=586,hly1=149,hly2=164,h4=hly1+y;
         int l1=x+30,l2=l1+100,l3=l2+200,l4=l3+100,l5=l4+70;
         g.setColor(Color.black);
         g.setFont(new Font("Perpetua Titling MT",Font.BOLD,16));
         
         g.drawLine(x, y+70, x+w,y+70);
         g.setColor(Color.LIGHT_GRAY);
         g.fillRect(x+1, y+131, w-2, 18);
         g.setColor(Color.black);
         g.drawLine(x, y+130, x+w, y+130);
         g.drawLine(x, y+hly2, x+w, y+hly2);
         g.drawLine(x+w/2, y+70, x+w/2, y+130);
         g.drawLine(x, y+hly1, x+w, y+hly1);
         
         
         String shopname=null,address=null,dlno=null,vattin=null,gstin=null,mob=null,pan_no=null,state_name=null;
         try
         {
            rset=StaticMember.con.createStatement().executeQuery("select * from self_company");
            rset.next();
            shopname=rset.getString("shop_name");
            address=rset.getString("shop_address");
            dlno=rset.getString("shop_dl_no");
            gstin=rset.getString("shop_gst_no");
            mob=rset.getString("shop_mob");
            
         }
         catch(SQLException e)
         {
             JOptionPane.showMessageDialog(null, e.getMessage(), "Custemors!", JOptionPane.ERROR_MESSAGE);  
         }
         //g.drawRect(x, y+h+72, w, 20);
         FontMetrics fm=g.getFontMetrics();
         String hstr=shopname;
         
         g.drawString(hstr, x+w/2-fm.stringWidth(hstr)/2, y+27);
         g.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,11));
         FontMetrics fm1=g.getFontMetrics();
         String str1="Pharmaceutical Distributors";
         g.drawString(str1, x+w/2-fm1.stringWidth(str1)/2, y+40);
         g.setFont(new Font("Arial",Font.BOLD,9));
         FontMetrics fm2=g.getFontMetrics();
         String str5="PHONE : "+mob;
         g.drawString(str5, x+w-70-fm2.stringWidth(str5)/2, y+12);
         String str2=address;
         g.drawString(str2, x+w/2-fm2.stringWidth(str2)/2, y+52);
         String str3="D.L.No. : "+dlno;
         g.drawString(str3, x+w/2-fm2.stringWidth(str3)/2, y+64);
         String str4=" GSTTIN No. : "+gstin;
         g.drawString(str4, x+w-500-fm2.stringWidth(str4)/2, y+12);
        
         g.setFont(new Font("",Font.BOLD,11));
         FontMetrics fm3=g.getFontMetrics();
         String str6="ORDER RICIPT";
         g.drawString(str6, x+w/2-fm2.stringWidth(str6)/2, y+12);
         FontMetrics fm5=g.getFontMetrics();
         g.drawString("PRODUCT DESCRIPTION",x+w/2-fm5.stringWidth("PRODUCT DESCRIPTION")/2, y+143);
         g.setFont(new Font("",Font.BOLD,10));  
         String panno=null,gstno=null;
         try
         { 
             order_rset=StaticMember.con.createStatement().executeQuery("select * from create_order co join supplier s on co.supplier_id=s.supplier_id where order_no like'"+orderno+"'");
             order_rset.next();
             Date d;
             d=(order_rset.getDate("order_date"));
             Formatter fmdate=new Formatter();
             fmdate.format("%td-%tm-%tY",d,d,d);
             String date1=fmdate.toString();
             gstno=order_rset.getString("supplier_gst").toUpperCase();
             
             customer=new String[14];
             customer[0]=order_rset.getString("supplier_name").toUpperCase();
             customer[1]=order_rset.getString("supplier_address").toUpperCase();
             customer[2]=order_rset.getString("supplier_mob").toUpperCase();
             customer[3]=order_rset.getString("supplier_dl").toUpperCase();
             customer[4]=order_rset.getString("supplier_gst").toUpperCase();
             customer[5]=StaticMember.getPanno(gstno);
             customer[6]=StaticMember.getStatename(gstno)+", "+StaticMember.getStateCode(gstno);
             customer[7]=date1;
             item_rset=StaticMember.con.createStatement().executeQuery("select oi.qty as qty,p.product_name as product_name,p.product_hsn_code as hsn,a.packing as packing,oi.pack as pack,t.type_name as type_name from order_items oi join products p on oi.product_id=p.product_id join type t on p.type_id=t.type_id join availableitem a on oi.availableitem_id=a.availableitem_id where order_no like'"+orderno+"'");
             item_rset.last();
             items=new String[item_rset.getRow()][6];
             item_rset.beforeFirst();
             int i=0;
             while(item_rset.next())
             {
                 
                items[i][0]=item_rset.getString("product_name")+" "+item_rset.getString("type_name");
                items[i][1]=item_rset.getString("qty");
                items[i][2]=item_rset.getString("hsn");
                items[i][3]=item_rset.getString("packing");
                items[i][4]=item_rset.getString("pack");
                i++;
             }
             
         }
         catch(SQLException ex)
         {
             JOptionPane.showMessageDialog(null, ex.getMessage(), "Items!", JOptionPane.ERROR_MESSAGE); 
         }
         
         g.setFont(new Font("Arial",Font.BOLD,9+1/2));
         g.drawString("ORDER TO", x+5, y+80);
         g.drawString(":", x+70, y+80);
         g.drawString(customer[0], x+80, y+80);
         g.drawString("ADDRESS ", x+5, y+91);
         g.drawString(":", x+70, y+91);
         g.drawString(customer[1], x+80, y+91);
         g.drawString("MOBILE NO. ", x+5, y+102);
         g.drawString(":", x+70, y+102);
         g.drawString(customer[2], x+80, y+102);
         g.drawString("DL. NO.", x+5, y+113);
         g.drawString(":", x+70, y+113);
         g.drawString(customer[3], x+80, y+113);
         g.drawString("GSTIN NO.", x+5, y+124);
         g.drawString(":", x+70, y+124);
         g.drawString(customer[4], x+80, y+124);
         
         g.drawString("ORDER NO.", x+w/2+10, y+80);
         g.drawString(":", x+w/2+90, y+80);
         g.drawString("ORD00"+orderno, x+w/2+100, y+80);
         g.drawString("DATE ", x+w/2+10, y+91);
         g.drawString(":", x+w/2+90, y+91);
         g.drawString(customer[7], x+w/2+100, y+91);
         g.drawString("PAN NO.", x+w/2+10, y+102);
         g.drawString(":", x+w/2+90, y+102);
         g.drawString(customer[5], x+w/2+100, y+102);
         g.drawString("STATE/CODE", x+w/2+10, y+113);
         g.drawString(":", x+w/2+90, y+113);
         g.drawString(customer[6], x+w/2+100, y+113);
         
         int h3=y+160;
         /*
         g.drawLine(l1, h4, l1, y+h);//1
         g.drawLine(l2, h4, l2, y+h);//2
         g.drawLine(l3, h4, l3, y+h);///3/h+
         g.drawLine(l4, h4, l4, y+h);//4
         g.drawLine(l5, h4, l5, y+h);//5
         */
         
         
         g.drawString("SN", x+10, h3);
         g.drawString("HSN CODE", l1+30, h3);
         g.drawString("PRODUCTS", l2+70, h3);
         g.drawString("PACKING", l3+30, h3);
         g.drawString("QUANTITY", l4+15, h3);
         g.drawString("PACK", l5+30, h3);
         
         g.setFont(new Font("",Font.PLAIN,10));
         int h2=y+175,h5=13,h6=h4+28,h7=14;
         for(int j=0;j<items.length;j++)
         { 
            g.drawLine(l1, h4, l1, h6);//1
            g.drawLine(l2, h4, l2, h6);//2
            g.drawLine(l3, h4, l3, h6);///3/h+
            g.drawLine(l4, h4, l4, h6);//4
            g.drawLine(l5, h4, l5, h6);//5
            
            g.setFont(new Font("Arial",Font.PLAIN,10));
            g.drawString(j+1+"", x+5, h2+(j*h5)); //sr no
            g.drawString(items[j][0], l2+5, h2+(j*h5)); //product name
            g.drawString(items[j][1], (l5-10)-g.getFontMetrics().stringWidth(items[j][1]), h2+(j*h5)); //quintity
            g.drawString(items[j][2], l1+10, h2+(j*h5)); //hsn code
            g.drawString(items[j][3], l3+10, h2+(j*h5));//Packing
            g.drawString(items[j][4], l5+10, h2+(j*h5));//Pack
            g.drawLine(x, h2+(j*h5+3), x+w,h2+(j*h5+3));
            h6+=h5;
         } 
         int h8=h6-11;
         
         //g.drawLine(x, h8, x+w,h8);
         g.drawRect(x , y,w,h8+45);
         
         
         g.setFont(new Font("",Font.BOLD,10));
         g.setColor(Color.LIGHT_GRAY);
         g.fillRect(x+1, h8+45, w-1, 10);
         g.setColor(Color.BLACK);
         g.setFont(new Font("Arial",Font.BOLD,8));
         FontMetrics fmm=g.getFontMetrics();
         String strt="! THANK YOU !";
         g.drawString(strt, x+w/2-fmm.stringWidth(strt)/2, h8+53);
         g.setFont(new Font("",Font.BOLD,10));
         g.drawString("FOR, "+shopname.toUpperCase(), x+w-210, h8+15);
         g.setFont(new Font("",Font.BOLD|Font.ITALIC,10));
         g.drawString("Authorised Signatory", x+w-180, h8+30);
         return PAGE_EXISTS;
    }
    
    boolean printBill()
    {
                //step 1
               PrinterJob job=PrinterJob.getPrinterJob();
               //step 2
               job.setPrintable(this);
               //step 3
               boolean ok=job.printDialog();
               //step 4
               if(ok)
               {
                   try
                   {
                       job.print();
                       return true;
                   }catch(PrinterException pe)
                   {
                       return false;
                   }
               }
               return false;
    }
}
