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
 * @author RANDHIR KUMAR
 */
public class StimitBillPage implements Printable{
    String rbillno,product_name,juction;
    String pname,addr,dl,mobile,date;
    ResultSet person=null,rsitem=null,dispatch_rset=null,transport_rset=null;
    String items[][];
    String customer[];
    ResultSet bill_rset=null,product_rset=null,type_rset=null,avi_rset=null,m=null,rset=null;
    public StimitBillPage(String sbp)
    {
      rbillno=sbp;
    }
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if(pageIndex>0)
                return NO_SUCH_PAGE;
            Graphics2D g=(Graphics2D)graphics;
         
         int x=9,y=10,w=585,h=600,hly1=83,hly2=96,h4=hly1+y;
         g.setColor(Color.black);
         g.setFont(new Font("Arial",Font.BOLD,16));
         g.drawLine(x, y+32, x+w,y+32);
         g.setColor(Color.LIGHT_GRAY);
         g.fillRect(x+1, y+67, w-2, 15);
         g.setColor(Color.black);
         g.drawLine(x, y+68, x+w, y+68);
         g.drawLine(x, y+hly2, x+w, y+hly2);
         g.drawLine(x+w/2, y+32, x+w/2, y+68);
         g.drawLine(x, y+hly1, x+w, y+hly1);
         
         ImageIcon icon = StaticMember.getResizeImageIcon("/images/kmc_icon.jpg",StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT);  
         Image image = icon.getImage();
         g.drawImage(image, x+1, y+1, 55, 30, null);
         String shopname=null,address=null,dlno=null,vattin=null,gstin=null,mob=null,pan_no=null,state_name=null;
         try
         {
            rset=StaticMember.con.createStatement().executeQuery("select * from self_company");
            rset.next();
            String s=(rset.getString("shop_name")).substring(0,(rset.getString("shop_name")).lastIndexOf(" "));
            String s1=(rset.getString("shop_name")).substring((rset.getString("shop_name")).indexOf(" "),(rset.getString("shop_name")).length());
            shopname=s.substring(0,1)+"."+s1;
            
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
         g.setFont(new Font("Arial",Font.BOLD,9));
         FontMetrics fm2=g.getFontMetrics();
         g.setFont(new Font("",Font.BOLD,11));
         FontMetrics fm3=g.getFontMetrics();
         String str6="STEEMIT INVOICE";
         g.drawString(str6, x+w/2-fm2.stringWidth(str6)/2, y+12);
         FontMetrics fm5=g.getFontMetrics();
         g.drawString("PRODUCT DESCRIPTION",x+w/2-fm5.stringWidth("PRODUCT DESCRIPTION")/2, y+79);
         g.setFont(new Font("",Font.BOLD,10));  
         String panno=null,gstno=null;
         try
         { 
             bill_rset=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill where k_retail_bill_no like'"+rbillno+"'");
             bill_rset.next();
             person=StaticMember.con.createStatement().executeQuery("select * from customer where customer_id like'"+bill_rset.getString("customer_id")+"'");
             person.next();
             Date d;
             d=(bill_rset.getDate("k_retail_bill_date"));
             Formatter fmdate=new Formatter();
             fmdate.format("%td-%tm-%tY",d,d,d);
             String date1=fmdate.toString();
             gstno=person.getString("customer_gst");
             panno=StaticMember.getPanno(gstno);
             state_name=StaticMember.getStatename(gstno)+", "+StaticMember.getStateCode(gstno);
             customer=new String[5];
             customer[0]=person.getString("customer_name").toUpperCase();
             customer[1]=person.getString("customer_address").toUpperCase();
             customer[2]=person.getString("customer_mob").toUpperCase();
             customer[3]=date1;
             
             
             rsitem=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill_items where k_retail_bill_no like'"+rbillno+"'");
             rsitem.last();
             items=new String[rsitem.getRow()][14];
             rsitem.beforeFirst();
             int i=0;
             //items=new String[items.length][10];
             while(rsitem.next())
             //while(i<=10)
             {
                product_rset=StaticMember.con.createStatement().executeQuery("select * from products where product_id like'"+rsitem.getString("product_id")+"'");
                product_rset.next();
                type_rset=StaticMember.con.createStatement().executeQuery("select * from type where type_id like'"+product_rset.getString("type_id")+"'");
                type_rset.next();
                avi_rset=StaticMember.con.createStatement().executeQuery("select * from  availableitem where product_id like'"+rsitem.getString("product_id")+"'");
                avi_rset.next();
                product_name=product_rset.getString("product_name")+" "+type_rset.getString("type_name")+" "+avi_rset.getString("packing");
                m=StaticMember.con.createStatement().executeQuery("select * from manifacture where manifacture_id like'"+rsitem.getString("manifacture_id")+"'");
                m.next();
                items[i][0]=product_name.toUpperCase();
                items[i][1]=rsitem.getString("qty");
                Formatter fmrp=new Formatter(); //mrp 
                fmrp.format("%.2f",Float.parseFloat(rsitem.getString("mrp")));
                items[i][2]=fmrp.toString();
                Formatter fprice=new Formatter(); //price
                fprice.format("%.2f",Float.parseFloat(rsitem.getString("kprice")));
                items[i][3]=fprice.toString();
                float disc=Float.parseFloat(rsitem.getString("kdisc"));
                Formatter fdisc=new Formatter(); //disc
                fdisc.format("%.2f",disc);
                items[i][4]=fdisc.toString();
                int qty=Integer.parseInt(rsitem.getString("qty"));
                float rate=Float.parseFloat(rsitem.getString("kprice"));
                float dis_amt=rate*disc/100;
                float gst=Float.parseFloat(product_rset.getString("product_gst"));
                Formatter fgst=new Formatter(); //Net amount one product
                fgst.format("%.2f",gst);
                items[i][5]=fgst.toString();
                int qty_free=rsitem.getInt("free_qty");
                items[i][6]=qty_free+"";
                float t_amount=(Float.parseFloat(rsitem.getString("kprice"))*qty);
                float gstamt=t_amount*gst/100;
                Formatter ftotalamt=new Formatter(); //total amt %
                ftotalamt.format("%.2f",t_amount);
                Formatter fgstamt=new Formatter(); //total amt %
                fgstamt.format("%.2f",gstamt);
                items[i][7]=ftotalamt.toString();
                String hsn=(product_rset.getString("product_hsn_code")).substring(0,4);
                items[i][8]=hsn;
                items[i][9]=rsitem.getString("batch_no");
                String mfg=m.getString("manifacture_name");
                if(mfg.length()>8)
                {
                    mfg=mfg.substring(0,8);
                }
                items[i][10]=mfg;
                items[i][11]=rsitem.getString("exp_date");
                items[i][12]=fgstamt.toString();
                i++;
             }
         }
         catch(SQLException ex)
         {
             JOptionPane.showMessageDialog(null, ex.getMessage(), "Items!", JOptionPane.ERROR_MESSAGE); 
         }
         int hh1=y+42,hh2=hh1+11,hh3=hh2+11;
         g.setFont(new Font("Arial",Font.BOLD,8));
         g.drawString("BILL TO/BUIER", x+5, hh1);
         g.drawString(":", x+70, hh1);
         g.drawString(customer[0], x+80, hh1);
         g.drawString("ADDRESS ", x+5, hh2);
         g.drawString(":", x+70, hh2);
         g.drawString(customer[1], x+80, hh2);
         g.drawString("MOBILE NO. ", x+5, hh3);
         g.drawString(":", x+70, hh3);
         g.drawString(customer[2], x+80, hh3);
         g.drawString("INVOICE NO.", x+w/2+10, hh1);
         g.drawString(":", x+w/2+90, hh1);
         g.drawString("RGST00"+rbillno, x+w/2+100, hh1);
         g.drawString("DATE ", x+w/2+10, hh2);
         g.drawString(":", x+w/2+90, hh2);
         g.drawString(customer[3], x+w/2+100, hh2);
         
         int h3=93;
         /*
         g.drawLine(x+15, h4, x+15, y+h);//1
         g.drawLine(x+40, h4, x+40, y+h);//2
         g.drawLine(x+165, h4, x+165, y+h);///3/h+
         g.drawLine(x+210, h4, x+210, y+h);//4
         g.drawLine(x+255, h4, x+255, y+h);//5
         g.drawLine(x+285, h4, x+285, y+h);//6
         g.drawLine(x+325, h4, x+325, y+h);//7
         g.drawLine(x+365, h4, x+365, y+h);//8
         g.drawLine(x+395, h4, x+395, y+h);//9
         g.drawLine(x+425, h4, x+425, y+h+20);//10
         g.drawLine(x+470, h4, x+470, y+h+20);//11
         g.drawLine(x+497, h4, x+497, y+h+20);//12
         g.drawLine(x+525, h4, x+525, y+h+60);/////////13*/
         
         
         g.drawString("SN", x+1, y+h3);
         g.drawString("HSN", x+19, y+h3);
         g.drawString("PRODUCTS", x+75, y+h3);
         g.drawString("BATCH", x+172, y+h3);
         g.drawString("MAKE", x+216, y+h3);
         g.drawString("EXP", x+259, y+h3);
         g.drawString("M.R.P. \u20B9", x+289, y+h3);
         g.drawString("PRICE \u20B9", x+329, y+h3);
         g.drawString("DISC%", x+368, y+h3);
         g.drawString("GST%", x+398, y+h3);
         g.drawString("GST AMT \u20B9", x+427, y+h3);
         g.drawString("QTY", x+473, y+h3);
         g.drawString("FREE", x+499, y+h3);
         g.drawString("TOTAL AMT \u20B9", x+530, y+h3);
         //g.drawString("TOTAL ", (x+355)-g.getFontMetrics().stringWidth("TOTAL"), y+h+11);
         
         g.setFont(new Font("",Font.PLAIN,8+1/2));
         int h2=y+106,h5=12,h6=h4+25,h7=14;
         float tax_amt=0,sum_total_amt=0,t_taxable_amt=0,pay_amount,due_amount,sum_amt,taxable_amt=0,gst_amt=0,sum_gst_amt=0,dis_amt=0,discauntable_amt=0;
         int total_qty=0,qty,total_free_qty=0,free_qty;
         float total_net_amt=0,net_amt;
         for(int j=0;j<items.length;j++)
         { 
            g.drawString(j+1+"", x+5, h2+(j*h5)); //sr no
            g.setFont(new Font("Arial",Font.PLAIN,7));
            //g.setColor(Color.DARK_GRAY);
            g.drawLine(x+15, h4, x+15, h6);//1
            g.drawLine(x+40, h4, x+40, h6);//2
            g.drawLine(x+165, h4, x+165, h6);///3/h+
            g.drawLine(x+210, h4, x+210, h6);//4
            g.drawLine(x+255, h4, x+255, h6);//5
            g.drawLine(x+285, h4, x+285, h6);//6
            g.drawLine(x+325, h4, x+325, h6);//7
            g.drawLine(x+365, h4, x+365, h6);//8
            g.drawLine(x+395, h4, x+395, h6);//9
            g.drawLine(x+425, h4, x+425, h6);//10
            g.drawLine(x+470, h4, x+470, h6);//11
            g.drawLine(x+497, h4, x+497, h6);//12
            g.drawLine(x+525, h4, x+525, h6);/////////13
            
            g.drawString(items[j][0], x+42, h2+(j*h5)); //product name
            g.drawString(items[j][2]+" \u20B9", (x+322)-g.getFontMetrics().stringWidth(items[j][2]+" \u20B9"), h2+(j*h5)); //mrp
            g.drawString(items[j][3]+" \u20B9", (x+363)-g.getFontMetrics().stringWidth(items[j][3]+" \u20B9"), h2+(j*h5)); //rate
            g.drawString(items[j][4], (x+393)-g.getFontMetrics().stringWidth(items[j][4]), h2+(j*h5)); //disc
            g.drawString(items[j][5], (x+424-3)-g.getFontMetrics().stringWidth(items[j][5]), h2+(j*h5));//gst
            g.drawString(items[j][1], (x+494)-g.getFontMetrics().stringWidth(items[j][1]), h2+(j*h5)); //quintity
            g.drawString(items[j][7]+" \u20B9", (x+w-3)-g.getFontMetrics().stringWidth(items[j][7]+" \u20B9"), h2+(j*h5));//total amount
            g.drawString(items[j][8], x+17, h2+(j*h5)); //hsn code
            g.drawString(items[j][12]+" \u20B9", (x+468)-g.getFontMetrics().stringWidth(items[j][12]+" \u20B9"), h2+(j*h5));//gst amount
            g.drawString(items[j][6], (x+522)-g.getFontMetrics().stringWidth(items[j][6]), h2+(j*h5));//qty free
            g.drawString(items[j][9], x+163, h2+(j*h5));//batch
            g.drawString(items[j][10], x+212, h2+(j*h5));//make
            g.drawString(items[j][11], x+253, h2+(j*h5));//exp date
            h6+=h5;
            float vatt;
            //Calculate vat
            try
            {//JOptionPane.showMessageDialog(null, "hello!");
                float dic=Float.parseFloat(items[j][4]);
                gst_amt=Float.parseFloat(items[j][12]);
                sum_gst_amt+=gst_amt;
                qty=Integer.parseInt(items[j][1]);
                total_qty=total_qty+qty;
                free_qty=Integer.parseInt(items[j][6]);
                total_free_qty=total_free_qty+free_qty;
                sum_amt=Float.parseFloat(items[j][7]);
                sum_total_amt=sum_total_amt+sum_amt;
                t_taxable_amt=t_taxable_amt+sum_amt;
                if(dic!=0)
                {
                    float d=(sum_amt)*dic/100;
                    discauntable_amt=discauntable_amt+d;
                }
            }catch(NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Vatt!", JOptionPane.ERROR_MESSAGE); 
            }
         } 
         
         int h8=h6-11;
         
         float grand_net_amt=0,grand_amt=0;
         t_taxable_amt+=sum_gst_amt;
         grand_amt=t_taxable_amt-discauntable_amt;
         grand_net_amt=Math.round(grand_amt);
         
         //set the formatter
         
         Formatter fmtra=new Formatter(); //round off
         fmtra.format("%.02f",(sum_total_amt));
         Formatter famount=new Formatter(); //amount
         famount.format("%.02f",taxable_amt);
         Formatter ftamount=new Formatter(); //total amount
         ftamount.format("%.02f",sum_total_amt);
         Formatter fmtda=new Formatter(); //disc amount
         fmtda.format("%.2f",discauntable_amt);
         Formatter fmtgrandnet_amt=new Formatter(); //grand total amount
         fmtgrandnet_amt.format("%.2f",grand_net_amt);
         Formatter fmtgst_amt=new Formatter(); //gst amount
         fmtgst_amt.format("%.2f",sum_gst_amt);
         
         g.drawLine(x, h8, x+w,h8);// down line
         g.drawLine(x+425, h8+15, x+w, h8+15);
         g.drawLine(x+525, h8+30, x+w, h8+30);
         g.drawLine(x+525, h8+45, x+w, h8+45);
         g.drawLine(x+w, h8, x+w, h8+45);
         g.drawLine(x+425, h8, x+425, h8+15);//10
         g.drawLine(x+470, h8, x+470, h8+15);//11
         g.drawLine(x+497, h8, x+497, h8+15);//12
         g.drawLine(x+525, h8, x+525, h8+60);//13
         g.drawLine(x, h8+60, x+w, h8+60);
         g.drawRect(x , y,w,h8+60);
         int h9=h8+11;
         
         g.setFont(new Font("Arial",Font.BOLD,8));
         g.drawString("GST AMOUNT ", (x+518)-g.getFontMetrics().stringWidth("GST AMOUNT"), h8+25);
         g.drawString("DISC AMOUNT ", (x+518)-g.getFontMetrics().stringWidth("DISC AMOUNT"), h8+40);
         g.drawString("PAY AMOUNT ", (x+518)-g.getFontMetrics().stringWidth("PAY AMOUNT"), h8+55);
         
         g.setFont(new Font("Arial",Font.PLAIN,7));
         g.drawString(fmtgst_amt+" \u20B9",(x+467)-g.getFontMetrics().stringWidth(fmtgst_amt+" \u20B9"), h9);
         g.drawString(total_qty+"", (x+494)-g.getFontMetrics().stringWidth(total_qty+""), h9);
         g.drawString(total_free_qty+"", (x+522)-g.getFontMetrics().stringWidth(total_free_qty+""), h9);
         g.drawString(ftamount+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(ftamount+" \u20B9"), h9);
         g.drawString(StaticMember.myFormat(sum_gst_amt)+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(StaticMember.myFormat(sum_gst_amt)+" \u20B9"), h8+25);
         g.drawString(fmtda+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(fmtda+" \u20B9"), h8+40);
         g.drawString(fmtgrandnet_amt+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(fmtgrandnet_amt+" \u20B9"), h8+55);
         
         String sgt=fmtgrandnet_amt+"";
         String ssgt=sgt.substring(0,sgt.indexOf("."));
         g.setFont(new Font("",Font.BOLD,9));
         g.drawString("RUPES IN WORDS :", x+7, h8+10);
         g.drawString("Rs.  "+numberToWord(ssgt), x+7, h8+25);
         g.setFont(new Font("",Font.BOLD,8));
         FontMetrics fmm=g.getFontMetrics();
         String strt="THANK YOU VISIT AGAIN !";
         g.setColor(Color.LIGHT_GRAY);
         g.fillRect(x+1, h8+61, w-2, 8);
         g.setColor(Color.BLACK);
         g.drawString(strt, x+w/2-fmm.stringWidth(strt)/2, h8+68);
         
         g.setFont(new Font("",Font.BOLD|Font.ITALIC,10));
         g.drawString("Authorised Signatory", x+w/2-15, h8+50);
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
    public static String only2DigitToWord(String d)
    {
        if(d.length()>3)
            d=d.substring(0,2);
        else if(d.length()<=0)
            d="00";
        
        
        try
        {
            Integer.parseInt(d);
        }catch(NumberFormatException ex)
        {
            return "Not Valid Number";
        }
        if(d.length()==1)d="0"+d;
        String first[]={"ZERO","ONE","TWO","THREE","FOUR","FIVE","SIX","SEVEN","EIGHT","NINE"};
        String tens[]={"","TEN","TWENTY","THIRTY","FORTY","FIFTY","SIXTY","SEVENTY","EIGHTY","NINTY"};
        String number[]={"","ELEVEN","TWELVE","THIRTEEN","FOURTEEN","FIFTEEN","SIXTEEN","SEVENTEE","EIGHTEEN","NINETEEN"};
        //
        //code to convert digit to word
        if(d.charAt(0)=='0')
            return first[Integer.parseInt(d.charAt(1)+"")];
        else if(d.charAt(1)=='0')
            return tens[Integer.parseInt(d.charAt(0)+"")];
        else if(d.charAt(0)=='1')
            return number[Integer.parseInt(d.charAt(1)+"")];
        else 
            return  tens[Integer.parseInt(d.charAt(0)+"")]+" "+first[Integer.parseInt(d.charAt(1)+"")];
        
    }
    public static String numberToWord(String str)
    {
        int s;
        String Result="";
        String str1;
        str1=str;
        str="";
        for(int i=str1.length()-1;i>=0;i--)str+=str1.charAt(i);
        str1="";
        switch(s=str.length())
        {
            case 7:
                    str1=str.charAt(--s)+"";
            case 6:
                    str1+=str.charAt(--s)+"";
                    str1=only2DigitToWord(str1);
                    if(!str1.equals("ZERO"))
                    Result+=str1+" LAKH ";
                    str1="";
            case 5:
                    str1=str.charAt(--s)+"";
            case 4:
                    str1+=str.charAt(--s)+"";
                    str1=only2DigitToWord(str1);
                    if(!str1.equals("ZERO"))
                    Result+=str1+" THOUSANT ";
                    str1="";
            case 3:
                    str1+=str.charAt(--s)+"";
                    str1=only2DigitToWord(str1);
                    if(!str1.equals("ZERO"))
                    Result+=str1+" HUNDRED ";
                    str1="";
            case 2:
                    str1=str.charAt(--s)+"";
            case 1:
                    str1+=str.charAt(--s)+"";
                    str1=only2DigitToWord(str1);
                    if(!str1.equals("ZERO")||Result.equals(""))
                    Result+=str1;
        }
        return Result;
    }
}
