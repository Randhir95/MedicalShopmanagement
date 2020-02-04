/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Formatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Intel
 */
public class SStatementPage implements Printable{
    String items[][];
    String sdate,edate;
    public SStatementPage(String s1,String s2)
    {
      
        sdate=s1;
        edate=s2;
    }
      @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if(pageIndex>0)
                return NO_SUCH_PAGE;
            Graphics2D g=(Graphics2D)graphics;
        int x=74,y=75,w=447,h=620;
        //g.drawRect(x, y, w, h);  //big rectangle
        String shopname=null,address=null,vattin=null,mob=null,gstin_no=null;
        try
         {
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from self_company");
            rset.next();
            shopname=rset.getString("shop_name");
            address=rset.getString("shop_address");
            vattin=rset.getString("shop_tin_no");
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
         
         g.setFont(new Font("Perpetua Titling MT",Font.BOLD,9));
         FontMetrics fm2=g.getFontMetrics();
         String str2=address;
         g.drawString(str2, x+w/2-fm2.stringWidth(str2)/2, y+32);
         String str3="GSTIN NO : "+gstin_no+"  TIN NO : "+vattin;
         g.drawString(str3, x+w/2-fm2.stringWidth(str3)/2, y+44);
         g.setFont(new Font("Perpetua Titling MT",Font.BOLD,10));
         FontMetrics fm3=g.getFontMetrics();
         String str4="SALES STATEMENT FROM  "+sdate+"  To  "+edate;
         g.drawString(str4,x+w/2-fm3.stringWidth(str4)/2, y+56);
         
         g.drawLine(x, y+60, x+w, y+60); //first line
         g.drawLine(x, y+75, x+w, y+75);  //second Line
         g.drawString("DATE", x+2, y+70);
         g.drawString("BILL NO.", x+55, y+70);
         g.drawString("PARTY NAME", x+110, y+70);
         g.drawString("ADDRESS", x+270, y+70);
         g.drawString("CASH", x+360, y+70);
         g.drawString("CREDIT", x+405, y+70);
         g.setFont(new Font("Perpetua Titling MT",Font.BOLD,9));
         int b=0;
         int y1=y+90;
         int i=0;
         float sumtaxable=0,sumtax=0,sumamount=0;
         try
         {
            
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from retail_bill where date(retail_bill_date)>='"+sdate+"' AND date(retail_bill_date)<='"+edate+"'");
            rset.next();
            rset.last();
            items=new String[rset.getRow()][7];
            rset.beforeFirst();
            
            //JOptionPane.showMessageDialog(null,rset.getRow());
            while(rset.next())
            {
                items[i][0]=rset.getString("retail_bill_no");
                items[i][1]=rset.getString("retail_bill_date");
                ResultSet cu_rset=StaticMember.con.createStatement().executeQuery("select * from clients where client_id like'"+rset.getInt("client_id")+"'");
                cu_rset.next();
                items[i][2]=cu_rset.getString("client_name");
                items[i][3]=cu_rset.getString("client_address")+"";
                Formatter ftotalamt=new Formatter(); 
                ftotalamt.format("%.02f",rset.getFloat("pay_amount"));
                items[i][4]=ftotalamt+"";
               
               i++;
            }
         }catch(SQLException ex)
         {
             JOptionPane.showMessageDialog(null,ex.getMessage());
         }
         
         for(int j=0;j<items.length;j++)
         {
             /*String cstr=items[j][2];
             String custr=cstr.substring(0, 14);
             String dstr=items[j][1];
             String datestr=dstr.substring(0, 2);*/
             g.drawString(items[j][1], x+2, y1);
             g.drawString(items[j][0], x+55, y1);
             g.drawString(items[j][2], x+110, y1);
             g.drawString(items[j][3], x+270, y1);
             g.drawString(items[j][4], (x+390)-g.getFontMetrics().stringWidth(items[j][4]), y1);
             //g.drawString(items[j][4], x+360, y1);
            
             float stotalamount=Float.parseFloat(items[j][4]);
             sumamount+=stotalamount;
             y1+=10;
         }
         
         Formatter fsamount=new Formatter(); 
         fsamount.format("%.2f",sumamount);
         g.drawLine(x, y1, x+w, y1); //first line
         g.drawLine(x, y1+15, x+w, y1+15);  //second Line
         
         g.drawString("No. of BILLS  :  "+items.length, x+5, y1+12);
         g.drawString("Total Amount :  "+fsamount, x+145, y1+12);
         g.drawString(fsamount+"", (x+390)-g.getFontMetrics().stringWidth(fsamount+""), y1+12);
         //g.drawString(fsamount+"", x+360, y1+12);
         return PAGE_EXISTS;
     }
    
     boolean printStatement()
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
