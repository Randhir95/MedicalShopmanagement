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
public class CustomerLedgerPrintPage implements Printable{
    String sdate,edate;
    int cust_id;
    String items[][];
    ResultSet rset=null;
    public CustomerLedgerPrintPage(String s1,String s2,int c_id)
    {
        sdate=s1;
        edate=s2;
        cust_id=c_id;
    }
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if(pageIndex>0)
                return NO_SUCH_PAGE;
            Graphics2D g=(Graphics2D)graphics;
            
        int x=9,y=10,w=586,h=600,hly1=190,hly2=205,h4=hly1+y;
        //int x=74,y=75,w=447,h=620;
        g.drawRect(x, y, w, h+175);  //big rectangle
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
        int i=0;
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
        g.setFont(new Font("",Font.BOLD,9));
        FontMetrics fm3=g.getFontMetrics();
        String sy,ey,sm,em,sd,ed,sdmy,edmy;
        sdmy=StaticMember.getDate(sdate);
        edmy=StaticMember.getDate(edate);
        String str4="CUSTOMER LEDGER FROM  "+sdmy+" To  "+edmy;
        g.drawString(str4,x+w/2-fm3.stringWidth(str4)/2, y+56);
        g.drawString("PRINT DATE  :  "+StaticMember.getDate(), x+468, y+56);
        
        
        g.drawLine(x, y+60, x+w, y+60); //first line
        g.drawLine(x, y+75, x+w, y+75);  //second Line
        g.drawString("BILL NO.", x+2, y+70);
        g.drawString("DATE", x+60, y+70);
        g.drawString("P A R T Y  N A M E", x+130, y+70);
        g.drawString("BILL AMT", x+275, y+70);
        g.drawString("MODE", x+335, y+70);
        g.drawString("PAYMENT", x+390, y+70);
        g.drawString("PAY DATE", x+450, y+70);
        g.drawString("DUES AMT", x+535, y+70);
        g.setFont(new Font("Arial",Font.PLAIN,9));
        
        int y1=y+90;
        float sumbillamt=0,sumpayamt=0,sumduesamt=0;
        try
        {
            //System.out.printf( "select * from viewsaleretun where bill_date>='"+sdate+"' AND bill_date<='"+edate+"'");
            rset=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill where customer_id="+cust_id+" and (k_retail_bill_date)>='"+sdate+"' AND (k_retail_bill_date)<='"+edate+"'");
            rset.last();
            items=new String[rset.getRow()][8];
            rset.beforeFirst();
            //JOptionPane.showMessageDialog(null,rset.getRow());
            while(rset.next())
            {
                String cust_name="";
                Date pyd;
                items[i][0]="RGST00"+rset.getString("k_retail_bill_no");
                items[i][1]=rset.getString("k_retail_bill_date");
                ResultSet cus_rset=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+cust_id+"'");
                cus_rset.next();
                cust_name=cus_rset.getString("customer_name")+" - "+cus_rset.getString("customer_address");
                if(cust_name.length()>25)
                {
                    cust_name=cust_name.substring(0,25);
                }
                items[i][2]=cust_name;
                items[i][3]=String.format("%.02f", rset.getFloat("kpay_amount"));
                items[i][4]=String.format("%.02f", rset.getFloat("payment"));
                items[i][5]=String.format("%.02f", rset.getFloat("dues"));
                items[i][6]=rset.getString("kpay_mode");
                pyd=rset.getDate("payment_date");
                JOptionPane.showMessageDialog(null,pyd);
                if(pyd.equals("null-null-null"))
                {
                    items[i][7]="NA";
                }
                else
                {
                    items[i][7]=pyd+"";
                }
                
               i++;
            }
            
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        
        for(int j=0;j<items.length;j++)
        {
            String dstr,pdate="",ppd;
            g.setColor(Color.black);
            g.setFont(new Font("Arial",Font.PLAIN,8));
            g.drawString(items[j][0], x+2, y1);
            dstr=StaticMember.getDate(items[j][1]);
            g.drawString(dstr, x+60, y1);
            g.drawString(items[j][2], x+130, y1);
            g.drawString(items[j][3], (x+317)-g.getFontMetrics().stringWidth(items[j][3]), y1);
            g.drawString(items[j][4], (x+433)-g.getFontMetrics().stringWidth(items[j][4]), y1);
            g.drawString(items[j][5], (x+581)-g.getFontMetrics().stringWidth(items[j][5]), y1);
            g.drawString(items[j][6], x+335, y1);
            if(items[j][7].equals("NA"))
            {
                pdate="NA";
            }
            else
            {
                pdate=StaticMember.getDate(items[j][7]);
            }
            g.drawString(pdate, x+450, y1);
            float tbillamt=Float.parseFloat(items[j][3]);
            float tpayamt=Float.parseFloat(items[j][4]);
            float tduesamt=Float.parseFloat(items[j][5]);
            sumbillamt+=tbillamt;
            sumpayamt+=tpayamt;
            sumduesamt+=tduesamt;
            y1+=10;
        }
        g.drawLine(x, y1, x+w, y1); //first line
        g.drawLine(x, y1+15, x+w, y1+15);  //second Line
        g.setFont(new Font("Arial",Font.BOLD,9));
        g.drawString("NO. OF BILLS  :  "+i+"", x+2, y1+12);
        g.drawString("TOTAL", x+180, y1+12);
        g.drawString(String.format("%.02f", sumbillamt), (x+317)-g.getFontMetrics().stringWidth(String.format("%.02f", sumbillamt)), y1+12);
        g.drawString(String.format("%.02f", sumpayamt), (x+433)-g.getFontMetrics().stringWidth(String.format("%.02f", sumpayamt)), y1+12);
        g.drawString(String.format("%.02f", sumduesamt), (x+581)-g.getFontMetrics().stringWidth(String.format("%.02f", sumduesamt)), y1+12);
        return PAGE_EXISTS;
    }
    boolean printCustLedger()
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
