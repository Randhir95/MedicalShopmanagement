/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komalhealthcare;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Randhir
 */
public class StaticMember {
   public static Font buttonFont=new Font("Arial",Font.BOLD,17);
   public static Font labelFont=new Font("Arial",Font.BOLD,15);
   public static Font labelFont1=new Font("Arial",Font.BOLD,14);
   public static Font textFont1=new Font("Arial",Font.BOLD,14);
   public static Font textFont=new Font("Arial",Font.PLAIN,15);
   public static Font menufont=new Font("Arial",Font.BOLD,14);
   public static Font headFont=new Font("Arial",Font.BOLD,20);
   /*public static Font dtextFont=new Font("Cambria",Font.BOLD,20);
   
   public static Font labelfont3=new Font("Perpetua Titling MT",Font.BOLD,13);
   public static Font textfont3=new Font("Perpetua Titling MT",Font.BOLD,14);
   public static Font digitfont3=new Font("Cambria",Font.BOLD,14);
   public static Font slabelfont=new Font("Perpetua Titling MT",Font.BOLD,15);*/
   public static Font itemfont=new Font("Arial",Font.BOLD,14);  //table Item Font
   public static Font headfont=new Font("Arial",Font.BOLD,15);  //table Heading Font 
   public static Font HEAD_W_FONT=new Font("VERDANA",Font.BOLD,20);  //Window Heading Font 
   //public static Color bcolor = new Color(225,250,255); // Background color
   public static Color bcolor = new Color(240,240,240); // Background color
   public static Color bcolor1 = new Color(240,240,240); // Background color
   public static Color flcolor = new Color(0,0,0);  //Label foreground color
   public static Color bfcolor=new Color(0,0,0);   //Button Forgraund Color
   public static Color mcolor=new Color(0,0,0);  //Menu Forgroung Color
   public static Color THBcolor=new Color(255,210,255);  //Table Header Bacground Color 
   public static Color THFcolor=new Color(229,20,0);  //Table header Forground Color 
   public static Color TIFcolor=new Color(0,0,0);  //Table Item Forground Color 
   public static Color TIBcolor=new Color(235,255,221);  //Table Item Background Color
   public static Color WIN_BODER_COLOR=new Color(176,237,116);  //Window Border Color
   public static Color hc=new Color(142,0,255);
   public static Color HEAD_BG_COLOR1=new Color(0,128,64);
   public static Color DEACTIVE_MENU_FG_COLOR=new Color(0,0,0);
   public static Color HEAD_FG_COLOR1=new Color(255,255,255);
   public static Color BORDER_COLOR=new Color(186,210,235);
   public static Color IN_COLOR=new Color(62,62,247);
   public static Color OUT_COLOR=new Color(142,142,251);
   public static Color CURRENT_COLOR=new Color(62,62,247);
   public final static int SCREEN_WIDTH=((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth());
   public final static int SCREEN_HEIGHT=((int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
   public static Connection con=null;
   
   
    public final static String SHOP_NAME="SAN DISTRIBUTORS";
    public final static String SHOP_ADDRESS="Ganga Mahal, Near Titiwari More Tituwari (823001)";
    public final static String SHOP_GST_NUMBER="10DMNPK8153E1Z5";
    public final static String SHOP_WEBSITE="";
    public final static String SHOP_EMAIL="";
    public final static String SHOP_CONTACT_NUMBER="";
    
    public final static String[] PRODUCT_UNIT_TYPE={"Pcs"};
    public final static String GST[]={"   0 %","   5 %","  12 %","  18 %","  28 %"};
    public final static String BANK_NAME[]={"Allahabad Bank","Andhra Bank","Axis Bank","Bank of Bahrain and Kuwait","Bank of Baroda - Corporate Banking","Bank of Baroda - Retail Banking","Bank of India","Bank of Maharashtra","Canara Bank","Central Bank of India","City Union Bank","Corporation Bank","Deutsche Bank","Development Credit Bank","Dhanlaxmi Bank","Federal Bank","ICICI Bank","IDBI Bank","Indian Bank","Indian Overseas Bank","IndusInd Bank","ING Vysya Bank","Jammu and Kashmir Bank","Karnataka Bank Ltd","Karur Vysya Bank","Kotak Bank","Laxmi Vilas Bank","Oriental Bank of Commerce","Punjab National Bank - Corporate Banking","Punjab National Bank - Retail Banking","Punjab & Sind Bank","Shamrao Vitthal Co-operative Bank","South Indian Bank","State Bank of Bikaner & Jaipur","State Bank of Hyderabad","State Bank of India","State Bank of Mysore","State Bank of Patiala","State Bank of Travancore","Syndicate Bank","Tamilnad Mercantile Bank Ltd.","UCO Bank","Union Bank of India","United Bank of India","Vijaya Bank","Yes Bank Ltd"};
    public final static String[] gst_item={"0 %","5 %","12 %","18 %","28 %"};
    
    public final static String DB_NAME="sandistributors";
    final static String SAVED_TITLE = "Saved";
    final static String ERROR_TITLE = "Error!";
    final static String LOGIN_TITLE = "Login!";
    final static String INVALID_USER_TITLE = "Invalid User";
    
    static boolean ADD_MANUFACTURE=false;
    static boolean ADD_NEW_COMPANY=false;
    static boolean ADD_NEW_CUSTOMER=false;
    static boolean UPDATE_CUSTOMER=false;
    static boolean DELETE_CUSTOMER=false;
    static boolean ADD_NEW_EMPLOYEE=false;
    static boolean UPDATE_EMPLOYEE=false;
    static boolean DELETE_EMPLOYEE=false;
    static boolean ADD_NEW_PRODUCT=false;
    static boolean ADD_PRODUCT_TYPE=false;
    static boolean ADD_STOCK=false;
    static boolean STOCK_MODIFY=false;
    static boolean ADD_SUPPLIER=false;
    static boolean CREATE_NEW_USER=false;
    static boolean CREATE_SELF_COMPANY=false;
    static boolean DELETE_COMPANY=false;
    static boolean DELETE_MANUFACTURE=false;
    static boolean DELETE_TYPE=false;
    static boolean DELETE_PRODUCT=false;
    static boolean DELETE_SUPPLIER=false;
    static boolean EMPLOYEE_PAYMENT=false;
    static boolean SALE_RETUN=false;
    static boolean SALE_STATMENT=false;
    static boolean UPDATE_COMPANY=false;
    static boolean UPDATE_MANUFACTURE=false;
    static boolean UPDATE_PRODUCT=false;
    static boolean UPDATE_TYPE=false;
    static boolean UPDATE_SUPPLIER=false;
    static boolean VIEW_BILL=false;
    static boolean BILLING=false;
    static boolean K_BILLING=false;
    static boolean K_BILLING_M=false;
    static boolean VIEW_STOCK=false;
    static boolean VIEW_COMPANY_DETAILS=false;
    static boolean VIEW_CUSTOMER_DETAILS=false;
    static boolean VIEW_K_BILL=false;
    static boolean VIEW_MANIFACTURE_DETAILS=false;
    static boolean VIEW_STOCK_INVOICE=false;
    static boolean VIEW_TYPE_DETAILS=false;
    static boolean ADMIN_LOGIN_WINDOW=false;
    static boolean VIEW_SUPPLIER_DETAILS=false;
    static boolean VIEW_ALL_PRODUCT=false;
    static boolean DAILY_SALE_REPORT=false;    
    static boolean VIEW_BILL_WINDOW=false;
    static boolean BILL_WINDOW=false;
    static boolean SALES_REPORT=false;
    static boolean SALES_LEDGER=false;
    static boolean STOCK_REPORT=false;
    static boolean DISPATCH_TO=false;
    static boolean BILL_MODIFY=false;
    static boolean CREATE_ORDER=false;
    static boolean MODIFY_ORDER=false;
    static boolean ORDER_WINDOW=false;
    static boolean PAYMENT_SUBMISSION=false;
    static boolean CUSTOMER_LEDGER=false;
    static boolean LATTER_PAD=false;
    
    
    
    final public static Font MAIN_HEAD_FONT = new Font("Arial", Font.BOLD, 20), LABEL_FONT = new Font("Arial", Font.BOLD, 14), INPUT_FONT = new Font("Arial", Font.PLAIN, 15), HEAD_FONT = new Font("Arial", Font.BOLD, 16);
    final public static Color MAIN_HEAD_COLOR = new Color(255, 255, 255), LABEL_COLOR = new Color(0, 0, 0), INPUT_COLOR = new Color(0, 0, 0), HEAD_COLOR = new Color(68, 114, 196);
    final public static Color MAIN_HEAD_BG_COLOR = new Color(110, 143, 248), LABEL_BG_COLOR = new Color(0, 0, 0),L_BG_COLOR = new Color(242, 242, 242), INPUT_BG_COLOR = new Color(242, 242, 242), HEAD_BG_COLOR = new Color(218, 227, 243), WINDOW_BG_COLOR =Color.LIGHT_GRAY, PANEL_BG_COLOR = new Color(231, 230, 230);
    static boolean ACTIVE_WINDOW = false;
    
    final static int INTEGER_TYPE=-1073741824;
    final static int FLOAT_TYPE=-536870912;
    final static int STRING_TYPE=-268435456;
    final static int ALFABET_TYPE=-134217728;
    final static int LOWER_CASE=-16384;
    final static int TITTAL_CASE=-8192;
    final static int UPPER_CASE=-4096;
    
    static public void lookAndFeel()
    {
        try
        {
            for(UIManager.LookAndFeelInfo info:UIManager.getInstalledLookAndFeels())
            {
                if("Nimbus".equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylButtonUI");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylIconFactory");
        }catch(Exception e){}
        
         /*   try
        {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylButtonUI");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylIconFactory");
            UIManager.setLookAndFeel("com.jtattoo.plaf.BaseDesktopPaneUI");
        }
        catch(Exception ex)
        {
            
        }
        */
    }
    
    static void setLocation(JDialog jd)
    {
        jd.setLocation(0, 82);
    }
    
    static void setSize(JDialog jd)
    {
        jd.setSize(StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT-155);
    }
    static void setSize(JInternalFrame jif)
    {
        jif.setSize(StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT-155);
    }
    static void setSize(JFrame jf)
    {
        jf.setSize(StaticMember.SCREEN_WIDTH,StaticMember.SCREEN_HEIGHT-155);
    }
    
    public static void SetIconInTableHeader(JTable table, int colIndex,String tittleName)
    {
        table.getTableHeader().getColumnModel().getColumn(colIndex).setHeaderRenderer(new iconRenderer());
        table.getColumnModel().getColumn(colIndex).setHeaderValue(new TxtIcon(tittleName));
    }
    static class iconRenderer extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table,Object obj,boolean isSelected, boolean hasFocus, int row,int column) 
        {
            TxtIcon i = (TxtIcon)obj;
            this.setFont(LABEL_FONT);
            if (obj == i) 
            {
                setIcon(i.imageIcon);
                setText(i.txt);
            }
            setBorder(UIManager.getBorder("TableHeader.cellBorder"));
            setHorizontalAlignment(JLabel.CENTER);
            return this;
        }
    }
    static class TxtIcon
    {
        String txt;
        ImageIcon imageIcon;
        public TxtIcon(String text) 
        {
            txt = text;
            imageIcon =StaticMember.getResizeImageIcon("/images/sort.png", 30,30);
        }
    }
    
    static JLabel MyHead(String str) {
        JLabel label = new JLabel(str, JLabel.CENTER);
        label.setBackground(StaticMember.HEAD_BG_COLOR1);
        label.setOpaque(true);
        label.setForeground(StaticMember.HEAD_FG_COLOR1);
        label.setFont(StaticMember.HEAD_W_FONT);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        label.setPreferredSize(new Dimension(20,40));
        return label;
    }
    static JLabel MyMainHead(String str) {
        JLabel label = new JLabel(str, JLabel.CENTER);
        label.setBackground(StaticMember.MAIN_HEAD_BG_COLOR);
        label.setOpaque(true);
        label.setForeground(StaticMember.MAIN_HEAD_COLOR);
        label.setFont(StaticMember.MAIN_HEAD_FONT);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        label.setPreferredSize(new Dimension(20,50));
        return label;
    }
    
    static JDateChooser dateChooser()
    {
        JDateChooser dateChooser=new JDateChooser();
        dateChooser.setFont(StaticMember.labelFont1);
        dateChooser.setBackground(StaticMember.bcolor);
        dateChooser.setOpaque(true);
        return dateChooser;
    }
    
    static JLabel MyLabel(String str) {
        JLabel label = MyLabel(str,JLabel.RIGHT);
        label.setFont(StaticMember.labelFont);
        return label;
    }
    //setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
    static JLabel MyLabel(String str,int aligment) {
        JLabel label = new JLabel(str,aligment);
        label.setBackground(StaticMember.LABEL_BG_COLOR);
        label.setForeground(StaticMember.LABEL_COLOR);
        label.setFont(StaticMember.LABEL_FONT);
        return label;
    }
    static JLabel MyJLabel(String str,String title)
    {
        JLabel label=new JLabel(str);
        label.setFont(StaticMember.textFont);
        label.setBackground(StaticMember.L_BG_COLOR);
        label.setOpaque(true);
        label.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
        return label;
    }
   static JTextField MyInputBox(String str) {
        JTextField input = new JTextField(str);
        input.setBackground(StaticMember.INPUT_BG_COLOR);
        input.setForeground(StaticMember.INPUT_COLOR);
        input.setFont(StaticMember.INPUT_FONT);
        return input;
    }
    static JTextField MyInputBox(String str,String  hintsText) {
        JTextField input=MyInputBox(str);
        input.setUI(new JTextFieldPlaceHolder(hintsText));
        input.setToolTipText(hintsText.trim());
        return input;
    }
    static JTextField MyInputBox(String str,boolean f) {
        JTextField input=MyInputBox(str);
        input.setEditable(f);
        return input;
    }
    static JTextField MyInputBox(String str,boolean f,String  hintsText) {
        JTextField input=MyInputBox(str,hintsText);
        input.setEditable(f);
        return input;
    }
    static JTextField MyInputBox(String str,int caseORtypeORmaxsize,String  hintsText)
    {
        JTextField input=MyInputBox(str,hintsText);
        if(caseORtypeORmaxsize>0)
            input.addKeyListener(new SetInputBoxType(caseORtypeORmaxsize,0,0));
        else
            input.addKeyListener(new SetInputBoxType(0,caseORtypeORmaxsize,caseORtypeORmaxsize));
        return input;
    }
    static JTextField MyInputBox(String str,int maxsize,int typeORcase,String  hintsText)
    {
        JTextField input=MyInputBox(str,hintsText);
        input.addKeyListener(new SetInputBoxType(maxsize,typeORcase,typeORcase));
        return input;
    }
    static JTextField MyInputBox(String str,int maxsize,int typeORcase,boolean b,String  hintsText)
    {
        JTextField input=MyInputBox(str,b,hintsText);
        input.addKeyListener(new SetInputBoxType(maxsize,typeORcase,typeORcase));
        return input;
    }
    static JTextField MyInputBox(String str,int maxsize,int type,int cases,String  hintsText)
    {
        JTextField input=MyInputBox(str,hintsText);
        input.addKeyListener(new SetInputBoxType(maxsize,type,cases));
        return input;
    }
    
    static JTextField MyInputBox(String str,int maxsize,int typeORcase,String  hintsText, String title)
    {
        JTextField input=MyInputBox(str,hintsText);
        input.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
        
        input.addKeyListener(new SetInputBoxType(maxsize,typeORcase,typeORcase));
        return input;
    }
    static JTextField MyInputBox(String str,int maxsize,int typeORcase,boolean b,String  hintsText, String title)
    {
        JTextField input=MyInputBox(str,b,hintsText);
        input.addKeyListener(new SetInputBoxType(maxsize,typeORcase,typeORcase));
        input.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
        return input;
    }
    //customerdetailpanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,1, true),"Personal Detail"));
    static JTextField MyInputBox(String str,int maxsize,int type,int cases,String  hintsText, String title)
    {
        JTextField input=MyInputBox(str,hintsText);
        input.addKeyListener(new SetInputBoxType(maxsize,type,cases));
        input.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
        return input;
    }
     static JTextField MyInputBox(String str,int maxsize,int type,int cases,String  hintsText, String title,int b)
    {
        JTextField input=MyInputBox(str,hintsText);
        input.addKeyListener(new SetInputBoxType(maxsize,type,cases));
        input.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, b, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
        return input;
    }
    
    
    static JPasswordField MyPasswordBox(String str,int maxsize,String hintsText)
    {
        JPasswordField pass=new JPasswordField(str);
        pass.setBackground(StaticMember.INPUT_BG_COLOR);
        pass.setForeground(StaticMember.INPUT_COLOR);
        pass.setFont(StaticMember.INPUT_FONT);
        pass.setToolTipText(hintsText.trim());
        pass.addKeyListener(new SetInputBoxType(maxsize,0,0));
        return pass;
    }
    static JPasswordField MyPasswordBox(String str,int maxsize,String hintsText,String title,int b)
    {
        JPasswordField pass=MyPasswordBox(str,maxsize,hintsText);
        pass.addKeyListener(new SetInputBoxType(maxsize,0,0));
        pass.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, b, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
        return pass;
    }
    static JButton MyButton(String str,String tooltips)
    {
        JButton b=new JButton(str);
        b.setFont(StaticMember.buttonFont);
        b.setToolTipText(tooltips);
        return b;
    }
    static JButton AddButton(String fileName, int newWidth, int newHeight, String tooltips)
    {
        JButton b=new JButton("");
        b.setIcon(StaticMember.getResizeImageIcon(fileName,newWidth,newHeight));
        b.setToolTipText(tooltips);
        b.setBorder(null);
        return b;
    }
    static JComboBox MyComboBox(String c_list[])
    {
        return MyComboBox(c_list,"");
    }
    static JComboBox MyComboBox(String c_list[],String hintStr)
    {
        JComboBox c=new JComboBox();
        c.setRenderer(new JComboBoxPlaceHolder(hintStr));
        c.setFont(StaticMember.INPUT_FONT);
        c.setToolTipText(hintStr.trim());
        for(String item : c_list)
            c.addItem(item);
        c.setSelectedIndex(-1);
        return c;
    }
    static JComboBox MyComboBox(String c_list[],String hintStr ,String title)
    {
        JComboBox c=new JComboBox();
        c.setRenderer(new JComboBoxPlaceHolder(hintStr));
        c.setFont(StaticMember.INPUT_FONT);
        c.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
        c.setToolTipText(hintStr.trim());
        for(String item : c_list)
            c.addItem(item);
        c.setSelectedIndex(-1);
        return c;
    }
    public static ImageIcon getResizeImageIcon(String fileName, int newWidth, int newHeight) 
    {
        BufferedImage img,dimg;
        try {
            img = ImageIO.read(new StaticMember().getClass().getResource(fileName));
            int w = img.getWidth();
        int h = img.getHeight();
        dimg = new BufferedImage(newWidth, newHeight, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newWidth, newHeight, 0, 0, w, h, null);
        g.dispose();
        } catch (IOException ex) {
            img=null;
            dimg=null;
        }
        return new ImageIcon(dimg);
    }
    public static Image getResizeImage(String fileName, int newWidth, int newHeight) 
    {
        return getResizeImageIcon(fileName,newWidth,newHeight).getImage();
    }
    public static int calculateScreenWidthPercentage(int p)
    {
        return StaticMember.SCREEN_WIDTH*p/100;
    }
    static Font MENU_FONT=new Font("Arial", Font.BOLD,13);
    public static JButton MyMenuButton(String str,String fileName,int size)
    {
        JButton b=new JButton(str);
        b.setFont(MENU_FONT);
        b.setIcon(StaticMember.getResizeImageIcon(fileName,size,size));
        b.setBorder(null);
        b.setBackground(null);
        b.setToolTipText(str.trim());
        b.setDisabledIcon(StaticMember.getResizeImageIcon("/images/menuicon/disabled.png",size,size));
        return b;
    }
   
   
    public static JMenuItem MyMenuItem(String str)
    {
        JMenuItem mi=new JMenuItem(str);
        mi.setFont(MENU_FONT);
        mi.setDisabledIcon(StaticMember.getResizeImageIcon("/images/menuicon/disabled.png",20,20));
        return mi;
    }
    public static JMenu MyMenu(String str,String fileName,int size)
    {
        JMenu m=new JMenu(str);
        m.setFont(MENU_FONT);
        if(!fileName.equals(""))m.setIcon(StaticMember.getResizeImageIcon(fileName,size,size));
        return m;
    }
    
    
   
    static boolean writeInExcellFile(String[] title,Object[][]bookData,String path,String sheetName)
    {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        
        XSSFFont font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);

        Row row1 = sheet.createRow(0);
        for(int j=0;j<title.length;j++)
        {
            Cell cellTitle = row1.createCell(j);
            cellTitle.setCellStyle(cellStyle);
            cellTitle.setCellValue(title[j]);
        }
        int rowCount = 0;
        for (Object[] aBook : bookData) 
        {
            Row row = sheet.createRow(++rowCount);
            int columnCount = -1;
            for (Object field : aBook) 
            {
                Cell cell = row.createCell(++columnCount);
                if (field instanceof String)
                    cell.setCellValue((String) field);
                else if (field instanceof Integer)
                    cell.setCellValue((Integer) field);
            }
        }
        JFileChooser chooser = new JFileChooser(); 
        //chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(MDIMainWindow.self) == JFileChooser.APPROVE_OPTION) 
        { 
           try
            {
                FileOutputStream outputStream = new FileOutputStream(chooser.getSelectedFile()+"/"+path);
                workbook.write(outputStream);
            } catch (IOException ex) {
                return false;
            } 
        }
        else 
            return false;
        return true;
    }
    
    static String getPanno(String gst)
    {
        String pan="";
        if(gst.equals(""))return pan;
        pan=gst.substring(2,12);
       return pan;
    }
    static String getStateCode(String gst)
    {
        String s_code="";
        if(gst.equals(""))return s_code;
        s_code=gst.substring(0,2);
       return s_code;
    }
    static String getStatename(String gst)
    {
        String statename="";
        if(gst.equals(""))return statename;
        try
        {
            String s_code=gst.substring(0,2);
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT  * FROM state where state_tin like'"+s_code+"'");
            rset.next();
            statename=rset.getString("state_name");
        }
        catch(SQLException ex){}
        return statename; 
    }
    
    static public JDateChooser MyDateChooser(String tooltips,String title,int b)
    {
        JDateChooser chooser=new JDateChooser();
        chooser.setFont(StaticMember.labelFont1);
        chooser.setOpaque(true);
        chooser.setToolTipText(tooltips);
        chooser.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0, 0, b, 0, Color.BLACK),title,TitledBorder.LEFT,TitledBorder.TOP,StaticMember.LABEL_FONT));
        return chooser;
    }
    
    
    public static void setToEmployee(JTextField emp_name, ArrayList<String> emp_id,ArrayList<String> emp_item)
    {
        try
        {
            emp_id.clear();
            emp_item.clear();
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct * FROM employee");
            while(rset.next())
            {
                emp_id.add((rset.getString("employee_id")));//.substring(0, 5)+(rset.getString("emp_mob")).substring(6, 10)+(rset.getString("employee_id")).toUpperCase());
                emp_item.add((rset.getString("employee_name")));
            }
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(emp_name, emp_item,false); 
    }
    
    public static void setToBankName(JTextField bankname , ArrayList<String> bank_item)
    {
        for(int i=0;i<StaticMember.BANK_NAME.length;i++)
        {
            bank_item.add((StaticMember.BANK_NAME[i]).toUpperCase());
        }
        JTextFieldAutoComplete.setupAutoComplete(bankname, bank_item,false);
    }
    
    public static void setToManufacture(JTextField mfgname , ArrayList<String>mfgid  ,  ArrayList<String>mfgitem)
    {
        try
        {
            mfgid.clear();
            mfgitem.clear();
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct * FROM manifacture");
            while(rset.next())
            {
                mfgid.add(rset.getString("manifacture_id"));
                mfgitem.add((rset.getString("manifacture_name")).toUpperCase());
            }
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(mfgname, mfgitem,false);
    }
    
    public static void setToCompany(JTextField compname , ArrayList<String>compid  ,  ArrayList<String>compitem)
    {
        try
        {
            compid.clear();
            compitem.clear();
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct * FROM company");
            while(rset.next())
            {
                compid.add(rset.getString("company_id"));
                compitem.add((rset.getString("company_name")).toUpperCase());
            }
            }catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
            }
            JTextFieldAutoComplete.setupAutoComplete(compname, compitem,false);
    }
    
    public static void setCustomerdata(JTextField custname , ArrayList<String>custid  ,  ArrayList<String>custitem)
    {
        try
        {
            custid.clear();
            custitem.clear();
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select distinct * from customer");
            while(rset.next())
                {
                    custid.add(rset.getString("customer_id"));
                    custitem.add((rset.getString("customer_name")).toUpperCase());
                }
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(custname, custitem,false);
    }
    
    static public void setToType(JTextField typename , ArrayList<String>typeid  ,  ArrayList<String>typeitem)
    {
       try
       {
           typeid.clear();
           typeitem.clear();
           ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct * FROM type");
           while(rset.next())
           {
               typeid.add(rset.getString("type_id"));
               typeitem.add((rset.getString("type_name")).toUpperCase());
               }
       }catch(SQLException ex)
       {
           JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
       }
       JTextFieldAutoComplete.setupAutoComplete(typename, typeitem,false);
    }
    
    static public void setToSupplier(JTextField suppliername,ArrayList<String>supplierid,ArrayList<String>supplieritem)
    {
        try
        {
            supplierid.clear();
            supplieritem.clear();
            ResultSet srset=StaticMember.con.createStatement().executeQuery("SELECT * FROM supplier ");
            while(srset.next())
            {
                supplierid.add(srset.getString("supplier_id"));
                supplieritem.add((srset.getString("supplier_name")).toUpperCase());
            }
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(suppliername, supplieritem,false);
    }
    
    static public void setToProducts(JTextField productname,ArrayList<String>productid,ArrayList<String>productitem)
    {
        try
        {
            productid.clear();
            productitem.clear();
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct * FROM products");
            while(rset.next())
            {
                productid.add(rset.getString("product_id"));
                productitem.add((rset.getString("product_name")).toUpperCase());
            }
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(productname, productitem,false);
    }
    
    static public void setToHSNCode(JTextField hsncode,ArrayList<String>hsnitems)
    {
        try
        {
            hsnitems.clear();
            ResultSet rset=StaticMember.con.createStatement().executeQuery("SELECT distinct hsncode FROM hsncode_and_gst");
            while(rset.next())
                hsnitems.add(rset.getString("hsncode"));
        }catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(MDIMainWindow.self,ex.getMessage(),StaticMember.ERROR_TITLE,JOptionPane.ERROR_MESSAGE);
        }
        JTextFieldAutoComplete.setupAutoComplete(hsncode, hsnitems,false);
    }
    
    public static void updateQuintity(String batch, int qty,boolean f)
   {
       try
       {
            int avlqty=0,nqty=0;
            ResultSet rset=StaticMember.con.createStatement().executeQuery("select * from availableitem where batch_no like'"+batch+"'");
            rset.next();
            avlqty=rset.getInt("quintity");
            if(f==true)
                nqty=avlqty-qty;
            else
                nqty=avlqty+qty;
            StaticMember.con.createStatement().execute("update availableitem set quintity="+nqty+" where batch_no like'"+batch+"'");
        }
       catch(SQLException ex){}
   }
    
    static public void printStockBill(String bill)
    {
         if(bill.equals(""))
             JOptionPane.showMessageDialog(null, "Fistly Save Bill Then Print.");
         else
         {
             InvoicePrintPage rbp=new InvoicePrintPage(bill);
             if(rbp.printBill())
                 JOptionPane.showMessageDialog(null, "Bill Printed!");

         }
    }
    
    static public void printSelBill(String bill)
    {
         if(bill.equals(""))
             JOptionPane.showMessageDialog(null, "Fistly Save Bill Then Print.");
         else
         {
             RetailBillPage rbp=new RetailBillPage(bill);
            if(rbp.printBill())
                JOptionPane.showMessageDialog(null, "Bill Printed!");

         }
    }
    static public void printStemitBill(String bill)
    {
         if(bill.equals(""))
             JOptionPane.showMessageDialog(null, "Fistly Save Bill Then Print.");
         else
         {
             StimitBillPage rbp=new StimitBillPage(bill);
            if(rbp.printBill())
                JOptionPane.showMessageDialog(null, "Bill Printed!");

         }
    }
    static public void printKBill(String bill)
    {
         if(bill.equals(""))
             JOptionPane.showMessageDialog(null, "Fistly Save Bill Then Print.");
         else
         {
             KBillPage rbp=new KBillPage(bill);
            if(rbp.printBill())
                JOptionPane.showMessageDialog(null, "Bill Printed!");

         }
    }
    static public void printOrderRisipt(String bill)
    {
         if(bill.equals(""))
             JOptionPane.showMessageDialog(null, "Fistly Save Bill Then Print.");
         else
         {
             PrintOrderPage rbp=new PrintOrderPage(bill);
            if(rbp.printBill())
                JOptionPane.showMessageDialog(null, "Bill Printed!");

         }
    }
    
    static public void setVisibleFrame(JInternalFrame jif,boolean flag)
    {
        if(flag==false)
        {
            MDIMainWindow.desktop.add(jif);
            jif.setVisible(true);
            jif.setResizable(false);
            flag=true;
        }
    }
    static public void setVisibleFrame(JFrame jf,boolean flag)
    {
        jf.setVisible(true);
    }
    static public void setVisibleFrame(JDialog jd)
    {
        jd.setVisible(true);
    }
    static public JLabel label1(String caption)
    {
        JLabel h=new JLabel(caption,JLabel.CENTER);
        h.setFont(StaticMember.HEAD_W_FONT);
        h.setPreferredSize(new Dimension(StaticMember.SCREEN_WIDTH,30));
        h.setForeground(StaticMember.HEAD_FG_COLOR1);
        h.setOpaque(true);
        h.setBackground(StaticMember.HEAD_BG_COLOR1);
        return h;
    }
    
    static public void labelHeding(String caption,JFrame f1)
    {
        f1.add(label1(caption),BorderLayout.NORTH);
    }
    static public void labelHeding(String caption,JInternalFrame f1)
    {
        f1.add(label1(caption),BorderLayout.NORTH);
    }
    static public void labelHeding(String caption,JDialog f1)
    {
        f1.add(label1(caption),BorderLayout.NORTH);
    }
    
    static public JButton button(String caption)
    {
        JButton b1=new JButton(caption);
        b1.setFont(StaticMember.buttonFont);
        b1.setToolTipText(caption);
        return b1;
    }
    
    static public String getDate(JDateChooser date1)
    {
        String date;
        Calendar cl;
        cl=date1.getCalendar();
        Formatter fmt=new Formatter();
        fmt.format("%tY-%tm-%td",cl,cl,cl);
        date=fmt+"";
        return date;
    }
    
    static public String getDate(String date)
    {
        String dd,mm,yy,ddmmyy;
        if(date.equals(null))
        {
            ddmmyy=date;
            return ddmmyy;
        }
        yy=date.substring(0,4);
        mm=date.substring(5,date.lastIndexOf("-"));
        dd=date.substring(date.length()-2);
        ddmmyy=dd+"-"+mm+"-"+yy;
        return ddmmyy;
    }
    
    static public String getDate()
    {
        String date;
        Calendar cl=Calendar.getInstance();
        Formatter fmt=new Formatter();
        fmt.format("%tY-%tm-%td",cl,cl,cl);
        date=fmt+"";
        return date;
    }
    
    static public String getTime()
    {
        String t="";
        Calendar cl=Calendar.getInstance();
        Formatter fmtt=new Formatter();
        fmtt.format("%tH:%tM:%tS",cl,cl,cl);
        t=fmtt+"";
        return t;
    }
    
    static public String myFormat(float value)
    {
        Formatter formt=new Formatter(); 
        formt.format("%.02f",value);
        String str=formt+"";
        return str;
    }
    static public String myFormat(String value)
    {
        Formatter formt=new Formatter(); 
        formt.format("%.02f",Float.parseFloat(value));
        String str=formt+"";
        return str;
    }
    
    static public JButton resizedButton(String caption,String fileName,int size)
    {
        JButton b=new JButton(caption);
        b.setFont(MENU_FONT);
        b.setIcon(StaticMember.getResizeImageIcon(fileName,size,size));
        b.setBorder(null);
        b.setBackground(null);
        b.setToolTipText(caption.trim());
        return b;
    }
    
    static public  String only2DigitToWord(String d)
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
    static public  String numberToWord(String str)
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

        

//In TextField Capitalize Automatically in Input time
/*jTextField.addKeyListener(new KeyAdapter() {

  public void keyTyped(KeyEvent e) {
    char keyChar = e.getKeyChar();
    if (Character.isLowerCase(keyChar)) {
      e.setKeyChar(Character.toUpperCase(keyChar));
    }
  }

});
label.setPreferredSize(new Dimension(500, this.gethight()-300));
Color bc=new Color(255,210,255);
        Color hc=new Color(142,0,255);
        Color hmc=new Color(255,56,149);
Color THBcolor=new Color(162,0,255);  //Table Header Bacground Color
A4 width=8.27" and hight=11.69"
width=793.93px,hight=1122.24 px
*/