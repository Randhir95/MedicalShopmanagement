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
public class KBillPage implements Printable{
    String rbillno,product_name,juction;
    String pname,addr,dl,mobile,date;
    ResultSet person=null,rsitem=null,dispatch_rset=null,transport_rset=null;
    String items[][];
    String customer[];
    ResultSet bill_rset=null,product_rset=null,type_rset=null,avi_rset=null,m=null,rset=null;
    public KBillPage(String krb)
    {
      rbillno=krb;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
            if(pageIndex>0)
                return NO_SUCH_PAGE;
            Graphics2D g=(Graphics2D)graphics;
            
        int x=9,y=10,w=586,h=600,hly1=190,hly2=205,h4=hly1+y;
         g.setColor(Color.black);
         g.setFont(new Font("Perpetua Titling MT",Font.BOLD,16));
         //g.drawRect(x , y,w,h);
         //g.drawLine(x, y+70, x+w,y+70);
         g.drawLine(x, y+h, x+w,y+h);
         g.drawRect(x , y,w,h+175);
         g.drawLine(x, y+70, x+w,y+70);
         g.setColor(Color.LIGHT_GRAY);
         g.fillRect(x+1, y+171, w-1, 18);
         g.setColor(Color.black);
         g.drawLine(x, y+170, x+w, y+170);
         g.drawLine(x, y+hly2, x+w, y+hly2);
         g.drawLine(x+w/2, y+70, x+w/2, y+170);
         g.drawLine(x, y+hly1, x+w, y+hly1);
         g.drawLine(x+15, h4, x+15, y+h);//1
         g.drawLine(x+35, h4, x+35, y+h);//2
         g.drawLine(x+155, h4, x+155, y+h);///3/h+
         g.drawLine(x+200, h4, x+200, y+h);//4
         g.drawLine(x+235, h4, x+235, y+h);//5
         g.drawLine(x+260, h4, x+260, y+h);//6
         g.drawLine(x+295, h4, x+295, y+h);//7
         g.drawLine(x+330, h4, x+330, y+h);//8
         g.drawLine(x+360, h4, x+360, y+h);//9
         g.drawLine(x+390, h4, x+390, y+h+20);//10
         g.drawLine(x+425, h4, x+425, y+h+20);//11
         g.drawLine(x+455, h4, x+455, y+h+20);//12
         g.drawLine(x+490, h4, x+490, y+h+20);//13
         g.drawLine(x+512, h4, x+512, y+h+20);//14
         g.drawLine(x+535, h4, x+535, y+h+80);/////////15
         g.drawLine(x+390, y+h+20, x+w, y+h+20);
         g.drawLine(x+535, y+h+40, x+w, y+h+40);
         g.drawLine(x+535, y+h+60, x+w, y+h+60);
         g.drawLine(x+535, y+h+80, x+w, y+h+80);
         g.drawLine(x+w, y+h, x+w, y+h+80);
         
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
            juction=rset.getString("shop_juction");
            
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
         g.drawString("PRODUCT DESCRIPTION",x+w/2-fm5.stringWidth("PRODUCT DESCRIPTION")/2, y+183);
         g.setFont(new Font("",Font.BOLD,10));  
         String gstno=null;
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
             state_name=StaticMember.getStatename(gstno)+", "+StaticMember.getStateCode(gstno);
             customer=new String[14];
             customer[0]=person.getString("customer_name").toUpperCase();
             customer[1]=person.getString("customer_address").toUpperCase();
             customer[2]=person.getString("customer_mob").toUpperCase();
             customer[3]=person.getString("customer_dl");
             customer[4]=person.getString("customer_gst");
             customer[5]=date1;
             customer[6]=bill_rset.getString("kpay_mode").toUpperCase();
             customer[7]=bill_rset.getString("kpay_stetus").toUpperCase();
             customer[8]=rset.getString("shop_account_no");
             customer[9]=rset.getString("ac_ifac_code");
             customer[10]=rset.getString("bank_name");
             
             rsitem=StaticMember.con.createStatement().executeQuery("select * from k_retail_bill_items where k_retail_bill_no like'"+rbillno+"'");
             rsitem.last();
             items=new String[rsitem.getRow()][15];
             rsitem.beforeFirst();
             int i=0;
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
                 items[i][2]=StaticMember.myFormat(rsitem.getString("mrp"));
                 items[i][3]=StaticMember.myFormat(rsitem.getString("kprice"));
                 items[i][4]=StaticMember.myFormat(rsitem.getString("kdisc"));
                 float sgst_amt,gst_amt,pgst,psgst;
                 
                 pgst=Float.parseFloat(product_rset.getString("product_gst"));
                 psgst=pgst/2;
                 int qty=Integer.parseInt(rsitem.getString("qty"));
                 float rate=Float.parseFloat(rsitem.getString("kprice"));
                 float t_amount=(rate*qty);
                 gst_amt=t_amount*pgst/100;
                 sgst_amt=gst_amt/2;
                 items[i][5]=StaticMember.myFormat(psgst);
                 items[i][6]=StaticMember.myFormat(sgst_amt);
                 items[i][7]=StaticMember.myFormat(t_amount);
                 items[i][8]=(product_rset.getString("product_hsn_code")).substring(0,4);
                 items[i][9]=rsitem.getString("batch_no");
                 String mfg1=m.getString("manifacture_name");
                 if(mfg1.length()>7)
                 {
                     mfg1=mfg1.substring(0,7);
                 }
                 items[i][10]=mfg1;
                 items[i][11]=rsitem.getString("exp_date");
                 items[i][12]=rsitem.getString("free_qty");
                 i++;
             }
         }
         catch(SQLException ex)
         {
             JOptionPane.showMessageDialog(null, ex.getMessage(), "Items!", JOptionPane.ERROR_MESSAGE); 
         }
         
         g.setFont(new Font("Arial",Font.BOLD,8));
         g.drawString("BILL TO/BUIER", x+5, y+80);
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
         g.drawString("PAN NO.", x+5, y+135);
         g.drawString(":", x+70, y+135);
         g.drawString(StaticMember.getPanno(gstno), x+80, y+135);
         g.drawString("AADHAR NO.", x+5, y+146);
         g.drawString(":", x+70, y+146);
         g.drawString("", x+80, y+146);
         g.drawString("STATE/CODE", x+5, y+157);
         g.drawString(":", x+70, y+157);
         g.drawString(state_name, x+80, y+157);
         g.drawString("INVOICE NO.", x+w/2+10, y+80);
         g.drawString(":", x+w/2+90, y+80);
         g.drawString("RGST00"+rbillno, x+w/2+100, y+80);
         g.drawString("DATE ", x+w/2+10, y+91);
         g.drawString(":", x+w/2+90, y+91);
         g.drawString(customer[5], x+w/2+100, y+91);
         g.drawString("PAYMENT MODE", x+w/2+10, y+102);
         g.drawString(":", x+w/2+90, y+102);
         g.drawString(customer[6], x+w/2+100, y+102);
         g.drawString("PAYMENY STATUS ", x+w/2+10, y+113);
         g.drawString(":", x+w/2+90, y+113);
         g.drawString(customer[7], x+w/2+100, y+113);
         g.drawString("TRANSPORT ", x+w/2+10, y+124);
         g.drawString(":", x+w/2+90, y+124);
         g.drawString("", x+w/2+100, y+124);
         g.drawString("DISPATCH TO ", x+w/2+10, y+135);
         g.drawString(":", x+w/2+90, y+135);
         g.drawString("", x+w/2+100, y+135);
         g.drawString("ACCOUNT NO ", x+w/2+10, y+146);
         g.drawString(":", x+w/2+90, y+146);
         g.drawString(customer[8], x+w/2+100, y+146);
         g.drawString("IFAC CODE", x+w/2+10, y+157);
         g.drawString(":", x+w/2+90, y+157);
         g.drawString(customer[9], x+w/2+100, y+157);
         g.setFont(new Font("",Font.BOLD,8));
         g.drawString("BANK NAME", x+w/2+10, y+168);
         g.drawString(":", x+w/2+90, y+168);
         g.drawString(customer[10], x+w/2+100, y+168);
         int h3=200;
         /*
         g.drawLine(x+15, h4, x+15, y+h);//1
         g.drawLine(x+35, h4, x+35, y+h);//2
         g.drawLine(x+155, h4, x+155, y+h);///3/h+
         g.drawLine(x+200, h4, x+200, y+h);//4
         g.drawLine(x+235, h4, x+235, y+h);//5
         g.drawLine(x+260, h4, x+260, y+h);//6
         g.drawLine(x+295, h4, x+295, y+h);//7
         g.drawLine(x+330, h4, x+330, y+h);//8
         g.drawLine(x+360, h4, x+360, y+h);//9
         g.drawLine(x+390, h4, x+390, y+h+20);//10
         g.drawLine(x+425, h4, x+425, y+h+20);//11
         g.drawLine(x+455, h4, x+455, y+h+20);//12
         g.drawLine(x+490, h4, x+490, y+h+20);//13
         g.drawLine(x+512, h4, x+512, y+h+20);//14
         g.drawLine(x+535, h4, x+535, y+h+80);/////////15*/
         
         
         g.drawString("SN", x+1, y+h3);
         g.drawString("HSN", x+17, y+h3);
         g.drawString("PRODUCTS", x+65, y+h3);
         g.drawString("BATCH", x+162, y+h3);
         g.drawString("MAKE", x+206, y+h3);
         g.drawString("EXP", x+238, y+h3);
         g.drawString("M.R.P.", x+263, y+h3);
         g.drawString("PRICE", x+298, y+h3);
         g.drawString("DISC%", x+331, y+h3);
         g.drawString("CGST%", x+361, y+h3);
         g.drawString("CGST \u20B9", x+391, y+h3);
         g.drawString("QTY", x+492, y+h3);
         g.drawString("FREE", x+513, y+h3);
         g.drawString("SGST%", x+426, y+h3);
         g.drawString("SGST \u20B9", x+457, y+h3);
         g.drawString("TOTAL AMT", x+537, y+h3);
         //g.drawString("TOTAL ", (x+355)-g.getFontMetrics().stringWidth("TOTAL"), y+h+11);
         g.drawString("DISC AMOUNT ", (x+527)-g.getFontMetrics().stringWidth("DISC AMOUNT"), y+h+33);
         g.drawString("GST AMOUNT ", (x+527)-g.getFontMetrics().stringWidth("PAY AMOUNT"), y+h+53);
         g.drawString("PAY AMOUNT ", (x+527)-g.getFontMetrics().stringWidth("PAY AMOUNT"), y+h+73);
         g.setFont(new Font("",Font.PLAIN,8+1/2));
         int h2=215;
         float tax_amt=0,sum_total_amt=0,pay_amount,due_amount,sum_amt,taxable_amt=0,cgst_amt=0,sum_cgst_amt=0,dis_amt=0,discauntable_amt=0;
         int total_qty=0,qty,fqty,total_fqty=0;
         for(int j=0;j<items.length;j++)
         { 
            g.drawString(j+1+"", x+5, y+h2+(j*15)); //sr no
            g.setFont(new Font("Arial",Font.PLAIN,7));
            //g.setColor(Color.DARK_GRAY);
            /* g.drawLine(x+15, h4, x+15, y+h);//1
         g.drawLine(x+35, h4, x+35, y+h);//2
         g.drawLine(x+155, h4, x+155, y+h);///3/h+
         g.drawLine(x+200, h4, x+200, y+h);//4
         g.drawLine(x+235, h4, x+235, y+h);//5
         g.drawLine(x+260, h4, x+260, y+h);//6
         g.drawLine(x+295, h4, x+295, y+h);//7
         g.drawLine(x+330, h4, x+330, y+h);//8
         g.drawLine(x+360, h4, x+360, y+h);//9
         g.drawLine(x+390, h4, x+390, y+h+20);//10
         g.drawLine(x+425, h4, x+425, y+h+20);//11
         g.drawLine(x+455, h4, x+455, y+h+20);//12
         g.drawLine(x+490, h4, x+490, y+h+20);//13
         g.drawLine(x+512, h4, x+512, y+h+20);//14
         g.drawLine(x+535, h4, x+535, y+h+80);/////////15*/
            g.drawString(items[j][0], x+37, y+h2+(j*15)); //product name
            g.drawString(items[j][2]+" \u20B9", (x+294)-g.getFontMetrics().stringWidth(items[j][2]+" \u20B9"), y+h2+(j*15)); //mrp
            g.drawString(items[j][3]+" \u20B9", (x+329)-g.getFontMetrics().stringWidth(items[j][3]+" \u20B9"), y+h2+(j*15)); //rate
            g.drawString(items[j][4], (x+359)-g.getFontMetrics().stringWidth(items[j][4]), y+h2+(j*15)); //disc
            g.drawString(items[j][5], (x+389)-g.getFontMetrics().stringWidth(items[j][5]), y+h2+(j*15));//cgst %
            g.drawString(items[j][5], (x+454)-g.getFontMetrics().stringWidth(items[j][5]), y+h2+(j*15));//sgst %
            g.drawString(items[j][1], (x+510)-g.getFontMetrics().stringWidth(items[j][1]), y+h2+(j*15)); //quintity
            g.drawString(items[j][12], (x+532)-g.getFontMetrics().stringWidth(items[j][12]), y+h2+(j*15)); //Free quintity
            g.drawString(items[j][7]+" \u20B9", (x+w-1)-g.getFontMetrics().stringWidth(items[j][7]+" \u20B9"), y+h2+(j*15));//total amount
            g.drawString(items[j][8], x+17, y+h2+(j*15)); //hsn code
            g.drawString(items[j][6]+" \u20B9", (x+489)-g.getFontMetrics().stringWidth(items[j][6]+" \u20B9"), y+h2+(j*15));//sgst amount
            g.drawString(items[j][6]+" \u20B9", (x+424)-g.getFontMetrics().stringWidth(items[j][6]+" \u20B9"), y+h2+(j*15));//cgst amount
            g.drawString(items[j][9], x+153, y+h2+(j*15));//batch
            g.drawString(items[j][10], x+200, y+h2+(j*15));//make
            g.drawString(items[j][11], x+233, y+h2+(j*15));//exp date
            
            //Calculate vat
            try
            {//JOptionPane.showMessageDialog(null, "hello!");
                float dic=Float.parseFloat(items[j][4]);
                qty=Integer.parseInt(items[j][1]);
                fqty=Integer.parseInt(items[j][12]);
                tax_amt=Float.parseFloat(items[j][6])*2;
                taxable_amt=taxable_amt+tax_amt;
                cgst_amt=Float.parseFloat(items[j][6]);
                sum_cgst_amt=sum_cgst_amt+cgst_amt;
                total_qty=total_qty+qty;
                total_fqty=total_fqty+fqty;
                sum_amt=Float.parseFloat(items[j][7]);
                sum_total_amt=sum_total_amt+sum_amt;
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
         
         float net_amt=0;
         float net_a=sum_total_amt+(sum_cgst_amt*2);
         float na=net_a-discauntable_amt;
         net_amt=Math.round(na);
         
         //g.drawString(famount+"  \u20B9",(x+408)-g.getFontMetrics().stringWidth(famount+"  \u20B9"), y+h+11);
         g.drawString(total_fqty+"", (x+534)-g.getFontMetrics().stringWidth(total_fqty+""), y+h+11);
         g.drawString(total_qty+"", (x+510)-g.getFontMetrics().stringWidth(total_qty+" \u20B9"), y+h+11);
         g.drawString(StaticMember.myFormat(sum_cgst_amt)+" \u20B9", (x+423)-g.getFontMetrics().stringWidth(StaticMember.myFormat(sum_cgst_amt)+" \u20B9"), y+h+11);
         g.drawString(StaticMember.myFormat(sum_cgst_amt)+" \u20B9", (x+488)-g.getFontMetrics().stringWidth(StaticMember.myFormat(sum_cgst_amt)+" \u20B9"), y+h+11);
         g.drawString(StaticMember.myFormat(sum_total_amt)+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(StaticMember.myFormat(sum_total_amt)+" \u20B9"), y+h+11);
         g.drawString(StaticMember.myFormat(discauntable_amt)+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(StaticMember.myFormat(discauntable_amt)+" \u20B9"), y+h+33);
         g.drawString(StaticMember.myFormat(sum_cgst_amt*2)+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(StaticMember.myFormat(sum_cgst_amt*2)+" \u20B9"), y+h+53);
         g.drawString(StaticMember.myFormat(net_amt)+" \u20B9",(x+w-2)-g.getFontMetrics().stringWidth(StaticMember.myFormat(net_amt)+" \u20B9"), y+h+73);
         
         String sgt=StaticMember.myFormat(net_amt)+"";
         String ssgt=sgt.substring(0,sgt.indexOf("."));
         g.setFont(new Font("",Font.BOLD,10));
         g.drawString("RUPES IN WORDS :", x+10, y+h+20);
         g.setFont(new Font("",Font.BOLD,10));
         g.drawString("Rs.  "+StaticMember.numberToWord(ssgt), x+10, y+h+40);
         g.setFont(new Font("",Font.BOLD,10));
         g.setColor(Color.LIGHT_GRAY);
         g.fillRect(x+8, y+h+57, 130, 20);
         g.setColor(Color.BLACK);
         g.drawString("TERMS & CONDITION :", x+10, y+h+70);
         g.drawString("1. Intrest @24% p.a. will be charged after 30 days.", x+7, y+h+90);
         g.drawString("2. Goods onces sold will not be taken back or exchange.", x+7, y+h+100);
         g.drawString("3. Our responsibility ceases as goods leave our place.", x+7, y+h+110);
         g.drawString("Subject to "+juction+" Juurisdiction only.", x+7, y+h+135);
         String strt="THANK YOU VISIT AGAIN !";
         g.drawString(strt, x+w/2-fm1.stringWidth(strt)/2, y+h+155);
         g.drawString("FOR, "+shopname.toUpperCase(), x+w-210, y+h+108);
         g.setFont(new Font("",Font.BOLD,10));
         //g.drawString(location, x+118, y+h+117);
         //g.drawString(shopname.toUpperCase(), x+x+w-210, y+h+108);
         g.setFont(new Font("",Font.BOLD|Font.ITALIC,10));
         g.drawString("Authorised Signatory", x+w-180, y+h+130);
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
