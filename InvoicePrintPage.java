/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author Randhir
 */
public class InvoicePrintPage implements Printable{
    String rbillno,product_name,juction;
    String pname,addr,dl,mobile,date;
    ResultSet rsitem=null,bill_rset=null,rset=null;
    String items[][];
    String customer[];
    //ResultSet bill_rset=null,product_rset=null,type_rset=null,avi_rset=null,m=null,;
    public InvoicePrintPage(String krb)
    {
      rbillno=krb;
    }
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if(pageIndex>0)
                return NO_SUCH_PAGE;
            Graphics2D g=(Graphics2D)graphics;
            
        int x=9,y=10,w=586,h=660,hly1=157,hly2=172,h4=hly1+y,fh=775;
        g.setColor(Color.black);
        g.setFont(new Font("Perpetua Titling MT",Font.BOLD,16));
        
        g.drawLine(x, y+70, x+w,y+70);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x+1, y+140, w-1, 16);
        g.setColor(Color.black);
        g.drawLine(x, y+139, x+w, y+139);
        g.drawLine(x, y+hly2, x+w, y+hly2);
        g.drawLine(x+w/2, y+70, x+w/2, y+139);
        g.drawLine(x, y+hly1, x+w, y+hly1);
        
        ImageIcon icon = StaticMember.getResizeImageIcon("/images/kmc_icon.jpg",StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT);  
         Image image = icon.getImage();
         g.drawImage(image, x+5, y+15, 80, 50, null);
        

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
        String str6="GST INVOICE";
        g.drawString(str6, x+w/2-fm2.stringWidth(str6)/2, y+12);
        FontMetrics fm5=g.getFontMetrics();
        g.drawString("PRODUCT DESCRIPTION",x+w/2-fm5.stringWidth("PRODUCT DESCRIPTION")/2, y+152);
        g.setFont(new Font("",Font.BOLD,10));  
        String panno=null,gstno=null;
        try
        { 
            bill_rset=StaticMember.con.createStatement().executeQuery("select * from invoice i join supplier s on i.supplier_id=s.supplier_id where inv_no like'"+rbillno+"'");
            bill_rset.next();
            Date d;
            d=(bill_rset.getDate("inv_date"));
            Formatter fmdate=new Formatter();
            fmdate.format("%td-%tm-%tY",d,d,d);
            String date1=fmdate.toString();
            gstno=bill_rset.getString("supplier_gst");
            customer=new String[14];
            customer[0]=bill_rset.getString("supplier_name").toUpperCase();
            customer[1]=bill_rset.getString("supplier_address").toUpperCase();
            customer[2]=bill_rset.getString("supplier_mob").toUpperCase();
            customer[3]=bill_rset.getString("supplier_dl");
            customer[4]=StaticMember.getPanno(gstno);
            customer[5]=date1;
            customer[6]=gstno;
            customer[7]=StaticMember.getStatename(gstno);
            customer[8]=StaticMember.getStateCode(gstno);
            customer[9]=bill_rset.getString("supplier_ac");
            customer[10]=bill_rset.getString("supplier_ifac");
            //customer[11]=bill_rset.getString("supplier_bank_name");

            rsitem=StaticMember.con.createStatement().executeQuery("select * from stock_item si join products p on si.product_id=p.product_id join manifacture m on si.manifacture_id=m.manifacture_id join type t on p.type_id=t.type_id where inv_no like'"+rbillno+"'");
            rsitem.last();
            items=new String[rsitem.getRow()][15];
            rsitem.beforeFirst();
            int i=0;
            float gstamt=0,amt;
            //items=new String[items.length][10];
            while(rsitem.next())
            //while(i<=10)
            {
                
                product_name=rsitem.getString("product_name")+" "+rsitem.getString("type_name");
                items[i][0]=product_name.toUpperCase();
                items[i][1]=rsitem.getString("qty");
                items[i][2]=String.format("%.02f", Float.parseFloat(rsitem.getString("mrp")));
                items[i][3]=String.format("%.02f", Float.parseFloat(rsitem.getString("prate")));
                items[i][4]=String.format("%.02f", Float.parseFloat(rsitem.getString("disc")));
                float pgst=Float.parseFloat(rsitem.getString("product_gst"));
                int qty=Integer.parseInt(rsitem.getString("qty"));
                float rate=Float.parseFloat(rsitem.getString("prate"));
                gstamt=rate*pgst/100;
                amt=(rate+gstamt);
                items[i][5]=String.format("%.02f", pgst);
                items[i][6]=String.format("%.02f", amt);
                float t_amount=rate*qty;
                items[i][7]=String.format("%.02f", t_amount);
                items[i][8]=(rsitem.getString("product_hsn_code")).substring(0,4);
                items[i][9]=rsitem.getString("batch_no");
                String mfg1=rsitem.getString("manifacture_name");
                
                if(mfg1.length()>7)
                {
                    mfg1=mfg1.substring(0,7);
                }
                items[i][10]=mfg1;
                items[i][11]=rsitem.getString("exp");
                items[i][12]=rsitem.getString("free_qty");
                items[i][13]=rsitem.getString("packing");
                i++;
             }
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Items!", JOptionPane.ERROR_MESSAGE); 
        }
         
        g.setFont(new Font("Arial",Font.BOLD,8));
        g.drawString("BILL FROM", x+5, y+80);
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
        g.drawString(customer[6], x+80, y+124);
        g.drawString("PAN NO.", x+5, y+135);
        g.drawString(":", x+70, y+135);
        g.drawString(customer[4], x+80, y+135);
        g.drawString("INVOICE NO.", x+w/2+10, y+80);
        g.drawString(":", x+w/2+90, y+80);
        g.drawString(rbillno, x+w/2+100, y+80);
        g.drawString("DATE ", x+w/2+10, y+91);
        g.drawString(":", x+w/2+90, y+91);
        g.drawString(customer[5], x+w/2+100, y+91);
        g.drawString("ACCOUNT NO ", x+w/2+10, y+102);
        g.drawString(":", x+w/2+90, y+102);
        g.drawString(customer[9], x+w/2+100, y+102);
        g.drawString("IFAC CODE", x+w/2+10, y+113);
        g.drawString(":", x+w/2+90, y+113);
        g.drawString(customer[10], x+w/2+100, y+113);
        //g.setFont(new Font("",Font.BOLD,8));
        g.drawString("BANK NAME", x+w/2+10, y+124);
        g.drawString(":", x+w/2+90, y+124);
        //g.drawString(customer[11], x+w/2+100, y+124);
        g.drawString("STATE/CODE", x+w/2+10, y+135);
        g.drawString(":", x+w/2+90, y+135);
        g.drawString(customer[7]+", "+customer[8], x+w/2+100, y+135);
        int h3=167;
       
        g.drawString("SN", x+1, y+h3);
        g.drawString("HSN", x+17, y+h3);
        g.drawString("PRODUCTS", x+65, y+h3);
        g.drawString("PACK", x+162, y+h3);
        g.drawString("BATCH", x+208, y+h3);
        g.drawString("MAKE", x+256, y+h3);
        g.drawString("EXP", x+298, y+h3);
        g.drawString("M.R.P.", x+325, y+h3);
        g.drawString("P.RATE", x+358, y+h3);
        g.drawString("GST%", x+392, y+h3);
        g.drawString("QTY", x+492, y+h3);
        g.drawString("FREE", x+513, y+h3);
        g.drawString("DISC%", x+423, y+h3);
        g.drawString("VALUE", x+457, y+h3);
        g.drawString("TOTAL AMT", x+537, y+h3);
        //g.drawString("TOTAL ", (x+355)-g.getFontMetrics().stringWidth("TOTAL"), y+h+11);
        
        g.setFont(new Font("",Font.PLAIN,8+1/2));
        //int h2=182;
        float tax_amt=0,sum_total_amt=0,pay_amount,due_amount,sum_amt,taxable_amt=0,gst_amt=0,sum_gst_amt=0,dis_amt=0,discauntable_amt=0;
        int total_qty=0,qty,fqty,total_fqty=0;
        int h2=y+182,h5=12,h6=h4+28,h7=14;
        for(int j=0;j<items.length;j++)
        { 
            g.drawString(j+1+"", x+5, h2+(j*h5)); //sr no
            g.setFont(new Font("Arial",Font.PLAIN,7));
            g.drawLine(x+15, h4, x+15, h6);//1
            g.drawLine(x+35, h4, x+35, h6);//2
            g.drawLine(x+155, h4, x+155, h6);///3/h+
            g.drawLine(x+200, h4, x+200, h6);//4
            g.drawLine(x+248, h4, x+248, h6);//5
            g.drawLine(x+295, h4, x+295, h6);//6
            g.drawLine(x+320, h4, x+320, h6);//7
            g.drawLine(x+355, h4, x+355, h6);//8
            g.drawLine(x+390, h4, x+390, h6);//9
            g.drawLine(x+420, h4, x+420, h6);//10
            g.drawLine(x+450, h4, x+450, h6);//11
            g.drawLine(x+490, h4, x+490, h6);//12
            g.drawLine(x+512, h4, x+512, h6);//13
            g.drawLine(x+535, h4, x+535, h6);/////////14
            //pn=0,qty=1,mrp=2,prate=3,disc=4,gst=5,value=6,tamt=7,hsn=8,batch=9,mfg=10,exp=11,qf=12,packing=13
            g.drawString(items[j][8], x+17, h2+(j*h5)); //hsn code
            g.drawString(items[j][0], x+39, h2+(j*h5)); //product name
            g.drawString(items[j][13], x+157, h2+(j*h5)); //packing
            g.drawString(items[j][9], x+202, h2+(j*h5));//batch
            g.drawString(items[j][10], x+252, h2+(j*h5));//make
            g.drawString(items[j][11], x+297, h2+(j*h5));//exp date
            g.drawString(items[j][2]+" \u20B9", (x+353)-g.getFontMetrics().stringWidth(items[j][2]+" \u20B9"), h2+(j*h5)); //mrp
            g.drawString(items[j][3]+" \u20B9", (x+388)-g.getFontMetrics().stringWidth(items[j][3]+" \u20B9"), h2+(j*h5)); //rate
            g.drawString(items[j][5]+" %", (x+418)-g.getFontMetrics().stringWidth(items[j][5]+" %"), h2+(j*h5));//gst %
            g.drawString(items[j][4]+" %", (x+448)-g.getFontMetrics().stringWidth(items[j][4]+" %"), h2+(j*h5)); //disc
            g.drawString(items[j][6]+" \u20B9", (x+488)-g.getFontMetrics().stringWidth(items[j][6]+" \u20B9"), h2+(j*h5));//value
            g.drawString(items[j][1], (x+510)-g.getFontMetrics().stringWidth(items[j][1]), h2+(j*h5)); //quintity
            g.drawString(items[j][12], (x+532)-g.getFontMetrics().stringWidth(items[j][12]), h2+(j*h5)); //Free quintity
            g.drawString(items[j][7]+" \u20B9", (x+w-1)-g.getFontMetrics().stringWidth(items[j][7]+" \u20B9"), h2+(j*h5));//total amount
            h6+=h5;
            float vatt;
            //Calculate vat
            try
            {//JOptionPane.showMessageDialog(null, "hello!");
                float dic=Float.parseFloat(items[j][4]);
                float gst=Float.parseFloat(items[j][5]);
                sum_amt=Float.parseFloat(items[j][7]);
                qty=Integer.parseInt(items[j][1]);
                fqty=Integer.parseInt(items[j][12]);
                tax_amt=Float.parseFloat(items[j][6]);
                taxable_amt+=tax_amt;
                gst_amt=sum_amt*gst/100;
                sum_gst_amt+=gst_amt;
                total_qty+=qty;
                total_fqty+=fqty;
                sum_total_amt+=sum_amt;
                if(dic!=0)
                {
                    float d=(sum_amt)*dic/100;
                    discauntable_amt+=d;
                }
            }catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Vatt!", JOptionPane.ERROR_MESSAGE); 
            }
        } 
         int h8=h6-11;
        float net_amt=0;
        float net_a=sum_total_amt+sum_gst_amt;
        float na=net_a-discauntable_amt;
        net_amt=Math.round(na);

        //set the formatter

        Formatter fmtra=new Formatter(); //round off
        fmtra.format("%.02f",(sum_total_amt));
        Formatter famount=new Formatter(); //amount
        famount.format("%.02f",taxable_amt);
        Formatter ftamount=new Formatter(); //total amount
        ftamount.format("%.02f",sum_total_amt);
        Formatter fmtda=new Formatter(); //disc amount
        fmtda.format("%.2f",discauntable_amt);
        Formatter fmtnet_amt=new Formatter(); //net total amount
        fmtnet_amt.format("%.2f",net_amt);
        Formatter fmtgst_amt=new Formatter(); //cgst  amount
        fmtgst_amt.format("%.2f",sum_gst_amt);

        g.drawLine(x+450, h8, x+450, h8+15);//11
        g.drawLine(x+490, h8, x+490, h8+15);//12
        g.drawLine(x+512, h8, x+512, h8+15);//13
        g.drawLine(x+535, h8, x+535, h8+45);/////////14
        g.drawLine(x, h8, x+w,h8);
        g.drawRect(x , y,w,h8+100);
        g.drawLine(x+450, h8+15, x+w, h8+15);
        g.drawLine(x+535, h8+30, x+w, h8+30);
        g.drawLine(x+535, h8+45, x+w, h8+45);
        
        int h9=h8+11;
        
        g.setFont(new Font("Arial",Font.BOLD,8));
        g.drawString("GST AMOUNT ", (x+527)-g.getFontMetrics().stringWidth("GST AMOUNT "), h8+25);
        g.drawString("PAY AMOUNT ", (x+527)-g.getFontMetrics().stringWidth("PAY AMOUNT "), h8+40);
        
        g.setFont(new Font("Arial",Font.PLAIN,7));
        g.drawString(famount+"  \u20B9",(x+488)-g.getFontMetrics().stringWidth(famount+"  \u20B9"), h9);
        g.drawString(total_fqty+"", (x+532)-g.getFontMetrics().stringWidth(total_fqty+""), h9);
        g.drawString(total_qty+"", (x+510)-g.getFontMetrics().stringWidth(total_qty+""), h9);
        g.drawString(ftamount+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(ftamount+" \u20B9"), h9);
        //g.drawString(fmtda+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(fmtda+" \u20B9"), y+h+33);
        g.drawString(fmtgst_amt+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(fmtgst_amt+" \u20B9"), h8+25);
        g.drawString(fmtnet_amt+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(fmtnet_amt+" \u20B9"), h8+40);

        String sgt=fmtnet_amt+"";
        String ssgt=sgt.substring(0,sgt.indexOf("."));
        g.setFont(new Font("",Font.BOLD,10));
        g.drawString("RUPES IN WORDS :", x+10, h8+10);
        g.setFont(new Font("",Font.BOLD,9));
        g.drawString("Rs.  "+StaticMember.numberToWord(ssgt), x+10, h8+25);
        g.setFont(new Font("",Font.BOLD,10));
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x+8, h8+30, 130, 16);
        g.setColor(Color.BLACK);
        g.drawString("TERMS & CONDITION :", x+10, h8+42);
        g.drawString("1. Intrest @24% p.a. will be charged after 30 days.", x+7, h8+60);
        g.drawString("2. Goods onces sold will not be taken back or exchange.", x+7, h8+70);
        g.drawString("3. Our responsibility ceases as goods leave our place.", x+7, h8+80);
        g.drawString("Subject to "+juction+" Juurisdiction only.", x+7, h8+95);
        
        g.drawString("FOR, "+shopname.toUpperCase(), x+w-210, h8+65);
        g.setFont(new Font("",Font.BOLD,8));
        FontMetrics fmm=g.getFontMetrics();
         String strt="THANK YOU VISIT AGAIN !";
         g.drawLine(x, h8+100, x+w, h8+100);
         g.setColor(Color.LIGHT_GRAY);
         g.fillRect(x+1, h8+100, w-1, 10);
         g.setColor(Color.BLACK);
         g.drawString(strt, x+w/2-fmm.stringWidth(strt)/2, h8+108);
        
        g.setFont(new Font("",Font.BOLD|Font.ITALIC,10));
        g.drawString("Authorised Signatory", x+w-180, h8+80);
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