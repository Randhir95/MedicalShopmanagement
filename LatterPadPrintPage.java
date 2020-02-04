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
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author RANDHIR KUMAR
 */
public class LatterPadPrintPage implements Printable{
    String some_text,lr_no,adhar_no;
    String pname,addr,dl,mobile,date;
    ResultSet person=null,rsitem=null,dispatch_rset=null,transport_rset=null;
    String items[][];
    String customer[];
    ResultSet bill_rset=null,product_rset=null,type_rset=null,avi_rset=null,m=null,rset=null;
    public LatterPadPrintPage(String sometext,String lrno,String adharno)
    {
      some_text=sometext;
      lr_no=lrno;
      adhar_no=adharno;
    }
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if(pageIndex>0)
                return NO_SUCH_PAGE;
            Graphics2D g=(Graphics2D)graphics;
            
        int x=9,y=10,w=586,h=600,hly1=190,hly2=205,h4=hly1+y;
         g.setColor(Color.black);
         g.setFont(new Font("Perpetua Titling MT",Font.BOLD,16));
         g.drawRect(x , y,w,h+175);
         g.drawLine(x, y+68, x+w,y+68);
         
         
         
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
            //juction=rset.getString("shop_juction");
            
         }
         catch(SQLException e)
         {
             JOptionPane.showMessageDialog(null, e.getMessage(), "Custemors!", JOptionPane.ERROR_MESSAGE);  
         }
         //g.drawRect(x, y+h+72, w, 20);
         FontMetrics fm=g.getFontMetrics();
         String hstr=shopname;
         
         g.drawString(hstr, x+w/2-fm.stringWidth(hstr)/2, y+13);
         g.setFont(new Font("Arial",Font.BOLD|Font.ITALIC,11));
         FontMetrics fm1=g.getFontMetrics();
         String str1="Pharmaceutical Distributors";
         g.drawString(str1, x+w/2-fm1.stringWidth(str1)/2, y+26);
         g.setFont(new Font("Arial",Font.BOLD,9));
         FontMetrics fm2=g.getFontMetrics();
         String str5="PHONE : "+mob;
         g.drawString(str5, x+w-70-fm2.stringWidth(str5)/2, y+62);
         String str2=address;
         g.drawString(str2, x+w/2-fm2.stringWidth(str2)/2, y+38);
         String str3="D.L.No. : "+dlno;
         g.drawString(str3, x+w/2-fm2.stringWidth(str3)/2, y+50);
         String str4=" GSTTIN No. : "+gstin;
         g.drawString(str4, x+w/2-fm2.stringWidth(str4)/2, y+62);
         g.setFont(new Font("",Font.BOLD,11));
         FontMetrics fm3=g.getFontMetrics();
         g.drawString("DATE :  "+StaticMember.getDate(), x+w-130, y+90);
        if(!adhar_no.equals(""))
        {
            g.drawString("AADHAR NO. :  "+adhar_no, x+50, y+90);
        } 
        if(!lr_no.equals(""))
        {
            g.drawString("LR NO. :  "+lr_no, x+50, y+110);
        }
        if(!lr_no.equals(""))
        {
            g.drawString(some_text, x+40, y+125);
        }
         ImageIcon icon = StaticMember.getResizeImageIcon("/images/kmc_icon.jpg",StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT);  
         Image image = icon.getImage();
         g.drawImage(image, x+5, y+5, 90, 60, null);
         g.drawString("FOR, "+shopname.toUpperCase(), x+w-210, y+h+75);
         g.setFont(new Font("",Font.BOLD,10));
         //g.drawString(location, x+118, y+h+117);
         //g.drawString(shopname.toUpperCase(), x+x+w-210, y+h+108);
         g.setFont(new Font("",Font.BOLD|Font.ITALIC,10));
         g.drawString("Authorised Signatory", x+w-180, y+h+100);
         return PAGE_EXISTS;
    }
    
    
    boolean printLatterPad()
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
