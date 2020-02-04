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
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Intel
 */
public class RetunPage implements Printable {
    String sdate,edate;
    String items[][];
  public RetunPage(String s1,String s2)
    {
        sdate=s1;
        edate=s2;
    }
       @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if(pageIndex>0)
                return NO_SUCH_PAGE;
            Graphics2D g=(Graphics2D)graphics;
    
        int x=9,y=10,w=585,h=600,hly1=190,hly2=205,h4=hly1+y;
        //int x=74,y=75,w=447,h=620;
        g.drawRect(x, y, w, h+175);  //big rectangle
        
        ImageIcon icon = StaticMember.getResizeImageIcon("/images/kmc_icon.jpg",StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT);  
         Image image = icon.getImage();
         g.drawImage(image, x+5, y+5, 80, 50, null);
        
        String shopname=null,address=null,mob=null,gstin_no=null;
        try
         {
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from self_company");
            rset.next();
            shopname=rset.getString("shop_name");
            address=rset.getString("shop_address");
            mob=rset.getString("shop_mob");
            gstin_no=rset.getString("shop_gst_no");
         }
         catch(SQLException ex)
         {
             JOptionPane.showMessageDialog(null, ex.getMessage());
         }
        
         g.setFont(new Font("Perpetua Titling MT",Font.BOLD,16));
         FontMetrics fm=g.getFontMetrics();
         String hstr=shopname;
         g.drawString(hstr, x+w/2-fm.stringWidth(hstr)/2, y+20);
         
         g.setFont(new Font("Arial",Font.BOLD|Font.BOLD,10));
         FontMetrics fm2=g.getFontMetrics();
         String str2=address;
         g.drawString(str2, x+w/2-fm2.stringWidth(str2)/2, y+32);
         String str3="GSTIN NO : "+gstin_no;
         g.drawString(str3, x+w/2-fm2.stringWidth(str3)/2, y+44);
         g.setFont(new Font("Arial",Font.BOLD,10));
         FontMetrics fm3=g.getFontMetrics();
         String sy,ey,sm,em,sd,ed,sdmy,edmy;
         sy=sdate.substring(0,4);
         sm=sdate.substring(5,sdate.lastIndexOf("-"));
         sd=sdate.substring(sdate.length()-2);
         sdmy=sd+"-"+sm+"-"+sy;
         ey=edate.substring(0,4);
         em=edate.substring(5,edate.lastIndexOf("-"));
         ed=edate.substring(edate.length()-2);
         edmy=ed+"-"+em+"-"+ey;
         String str4="SALE FROM  "+sdmy+" To  "+edmy;
         g.drawString(str4,x+w/2-fm3.stringWidth(str4)/2, y+56);
         
         g.drawLine(x, y+60, x+w, y+60); //first line
         g.drawLine(x, y+75, x+w, y+75);  //second Line
         g.drawString("BILL NO.", x+2, y+70);
         g.drawString("DATE", x+80, y+70);
         g.drawString("P A R T Y  N A M E", x+150, y+70);
         g.drawString("GST.FREE.", x+320, y+70);
         g.drawString("GSTABLE", x+390, y+70);
         g.drawString("GST", x+470, y+70);
         g.drawString("TOTAL", x+540, y+70);
         g.setFont(new Font("Arial",Font.PLAIN,9));
         int i=0;
         int y1=y+90;
         float sumtaxable=0,sumtax=0,sumamount=0;
         try
         {
            //System.out.printf( "select * from viewsaleretun where bill_date>='"+sdate+"' AND bill_date<='"+edate+"'");
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from retail_bill where (retail_bill_date)>='"+sdate+"' AND (retail_bill_date)<='"+edate+"'");
            rset.next();
            rset.last();
            items=new String[rset.getRow()][8];
            rset.beforeFirst();
            //JOptionPane.showMessageDialog(null,rset.getRow());
            while(rset.next())
            {
                items[i][0]="RGST00"+rset.getString("retail_bill_no");
                items[i][1]=rset.getString("retail_bill_date");
                ResultSet cus_rset=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+rset.getString("customer_id")+"'");
                cus_rset.next();
                items[i][2]=cus_rset.getString("customer_name");
                Formatter ftaxable=new Formatter(); 
                ftaxable.format("%.02f",rset.getFloat("pay_amount"));
                items[i][3]=ftaxable.toString();
                Formatter ftax=new Formatter(); 
                ftax.format("%.02f",rset.getFloat("pay_amount"));
                items[i][4]=ftax.toString();
                Formatter ftotalamt=new Formatter(); 
                ftotalamt.format("%.02f",rset.getFloat("pay_amount"));
                items[i][5]=ftotalamt.toString();
                items[i][6]=rset.getString("pay_mode");
               
               i++;
            }
         }catch(SQLException ex)
         {
             JOptionPane.showMessageDialog(null,ex.getMessage());
         }
         
         for(int j=0;j<items.length;j++)
         {
             String cstr=items[j][2];
             String dstr=items[j][1];
             String dd,mm,yy,ddmmyy;
             yy=dstr.substring(0,4);
             mm=dstr.substring(5,dstr.lastIndexOf("-"));
             dd=dstr.substring(dstr.length()-2);
             ddmmyy=dd+"-"+mm+"-"+yy;
             //String s=dstr.substring(dstr.length()-2);
             //String s1=dstr.substring(5,dstr.lastIndexOf("-"));
             //dstr=s+"-"+s1;
               g.setColor(Color.black);
               g.setFont(new Font("verdana",Font.PLAIN,9));
               g.drawString(items[j][0], x+2, y1);
               g.setFont(new Font("verdana",Font.PLAIN,10));
               g.drawString(ddmmyy, x+80, y1);
               g.setFont(new Font("verdana",Font.PLAIN,9));
               g.drawString(items[j][6]+"  "+cstr, x+150, y1);
               g.setFont(new Font("verdana",Font.PLAIN,10));
               //g.drawString("00.0", x+239, y1);
               //g.drawString("00.00", (x+370)-g.getFontMetrics().stringWidth("00.00"), y1);
               g.drawString(items[j][3], (x+370)-g.getFontMetrics().stringWidth(items[j][3]), y1);
               g.drawString("00.00", (x+440)-g.getFontMetrics().stringWidth("00.00"), y1);
               g.drawString("00.00", (x+500)-g.getFontMetrics().stringWidth("00.00"), y1);
               g.drawString(items[j][5], (x+580)-g.getFontMetrics().stringWidth(items[j][5]), y1);
               
               float staxable=Float.parseFloat(items[j][3]);
               float stax=Float.parseFloat(items[j][4]);
               float stotalamount=Float.parseFloat(items[j][5]);
               sumtaxable+=staxable;
               sumtax+=stax;
               sumamount+=stotalamount;
                y1+=10;
         }
         Formatter fstaxable=new Formatter(); 
         fstaxable.format("%.2f",sumtaxable);
         Formatter fstax=new Formatter(); 
         fstax.format("%.2f",sumtax);
         Formatter fsamount=new Formatter(); 
         fsamount.format("%.2f",sumamount);
         g.drawLine(x, y1, x+w, y1); //first line
         g.drawLine(x, y1+15, x+w, y1+15);  //second Line
         g.drawString("TOTAL", x+5, y1+12);
         //g.drawString("0.00", x+239, y1+12);
         g.drawString(fstaxable+"", (x+370)-g.getFontMetrics().stringWidth(fstaxable+""), y1+12);
         //g.drawString(fstaxable+"", x+275, y1+12);
         g.drawString("00.00", (x+440)-g.getFontMetrics().stringWidth("00.00"), y1+12);
         //g.drawString(fstax+"", x+345, y1+12);
         g.drawString("00.00", (x+500)-g.getFontMetrics().stringWidth("00.00"), y1+12);
         //g.drawString(fsamount+"", x+390, y1+12);
         g.drawString(fsamount+"", (x+580)-g.getFontMetrics().stringWidth(fsamount+""), y1+12);
         return PAGE_EXISTS;
     }
    boolean printRetun()
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
