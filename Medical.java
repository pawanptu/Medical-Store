import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

 class Main extends JFrame
{
    
    private static final long serialVersionUID = 1L;
    JTabbedPane pane;
  JButton but;
  public Main(String title)
  {
    super(title);

    pane=new JTabbedPane(JTabbedPane.TOP);
    add(pane);
    pane.addTab("BILL GENERATION",new Bill());
    pane.addTab("ADD NEW MEDICINE STOCK",new Add());
    pane.addTab("UPDATE EXISTING STOCK",new Update());
    pane.addTab("REMOVE MEDICINE STOCK",new Remove());
    pane.addTab("LOGOUT",new Log());
  }


}

class Bill extends JPanel implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    // JPanel p1;
  JLabel bl1,bl2;
  JTextField btf1,btf2;
  JButton bb;
  Connection con;
  String med;
  int qty,price;
    
  
  public Bill()
  {
    this.setBackground(Color.PINK);
    this.setLayout(null);

    bl1=new JLabel("Enter Medicine");
    bl1.setBounds(200,100,100,30);
    add(bl1);

    btf1=new JTextField();
    btf1.setBounds(450,100,150,30);
    add(btf1);

    bl2=new JLabel("Enter Quantity");
    bl2.setBounds(200,200,100,30);
    add(bl2);

    btf2=new JTextField();
    btf2.setBounds(450,200,150,30);
    add(btf2);

    bb=new JButton("GENERATE BILL");
    bb.setBounds(280,300,200,30);
    add(bb);
    bb.addActionListener(this);
    
  }

    public void actionPerformed(ActionEvent e) 
    {
        med=btf1.getText();
        qty=Integer.parseInt(btf2.getText());
        
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","paw","Pawan123@");            
            PreparedStatement pstmt=con.prepareStatement("update owner set quantity=quantity-? where name=?");
            pstmt.setInt(1,qty);
            pstmt.setString(2,med);
            pstmt.executeUpdate();
            PreparedStatement stmt=con.prepareStatement("select price from owner where name=?");
            stmt.setString(1, med);
            ResultSet rs=stmt.executeQuery();
            while(rs.next())
                price=rs.getInt("price");
         
            JOptionPane.showMessageDialog(null,"Bill generated");
            
            Billpage bp=new Billpage("Bill"); 
            bp.setVisible(true);
            bp.setSize(400, 400);
            bp.setDefaultCloseOperation(EXIT_ON_CLOSE);
            bp.setLocationRelativeTo(null);
            bp.setResizable(false);
            
            bp.billdisplay(med, qty,price);
        } 
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}

class Add extends JPanel implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel al1, al2, al3, al4;
  JTextField atf1,atf2,atf3,atf4;
  JButton ab;
  String mname,comp;
  int quan,pr;
  Connection con;
  public Add()
  {
    this.setBackground(Color.PINK);
    this.setLayout(null);

    al1=new JLabel("Enter Medicine");
    al1.setBounds(200,70,100,30);
    add(al1);

    atf1=new JTextField();
    atf1.setBounds(500,70,150,30);
    add(atf1);

    al2=new JLabel("Enter Company Name");
    al2.setBounds(200,150,150,30);
    add(al2);

    atf2=new JTextField();
    atf2.setBounds(500,150,150,30);
    add(atf2);

    al3=new JLabel("Enter Quantity");
    al3.setBounds(200,230,100,30);
    add(al3);

    atf3=new JTextField();
    atf3.setBounds(500,230,150,30);
    add(atf3);
    
    al4=new JLabel("Enter Price");
    al4.setBounds(200,310,100,30);
    add(al4);
    
    atf4=new JTextField();
    atf4.setBounds(500,310,150,30);
    add(atf4);

    ab=new JButton("ADD");
    ab.setBounds(310,390,200,30);
    add(ab);
    ab.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) 
  {
      mname=atf1.getText();
      comp=atf2.getText();
      quan=Integer.parseInt(atf3.getText());
      pr=Integer.parseInt(atf4.getText());
      
      try
      {
        Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","paw","Pawan123@");            
        PreparedStatement pstmt=con.prepareStatement("insert into owner(name,company,quantity,price) "
                + "values(?,?,?,?)");
        pstmt.setString(1,mname);
        pstmt.setString(2, comp);
        pstmt.setInt(3,quan);
        pstmt.setInt(4,pr);
        pstmt.executeUpdate();
        JOptionPane.showMessageDialog(null,"New stock inserted successfully");
        con.close();
      }
      catch(Exception ex)
      {
        System.out.println(ex);
      }
  }

}

class Update extends JPanel implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel ul1, ul2;
  JTextField utf1,utf2;
  JButton ub;
  Connection con;
  String med;
  int qty;
  public Update()
  {
    this.setBackground(Color.PINK);
    this.setLayout(null);

    ul1=new JLabel("Enter Medicine to Update");
    ul1.setBounds(200,100,150,30);
    add(ul1);

    utf1=new JTextField();
    utf1.setBounds(500,100,150,30);
    add(utf1);

    ul2=new JLabel("Enter Quantity to Update");
    ul2.setBounds(200,200,150,30);
    add(ul2);

    utf2=new JTextField();
    utf2.setBounds(500,200,150,30);
    add(utf2);

    ub=new JButton("UPDATE");
    ub.setBounds(310,300,200,30);
    add(ub);
    ub.addActionListener(this);
  }

  public void actionPerformed(ActionEvent e) 
  {
    med=utf1.getText();
    qty=Integer.parseInt(utf2.getText());
    try 
    {
       Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","paw","Pawan123@");            
       PreparedStatement pstmt=con.prepareStatement("update owner set quantity=quantity+? where name=?");
       pstmt.setInt(1, qty);
       pstmt.setString(2, med);
       pstmt.executeUpdate();
       JOptionPane.showMessageDialog(null,"Stock Updated Successfully");
    } 
    catch (Exception ex) 
    {
        System.out.println("ex");
    }
  }
}

class Remove extends JPanel implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JLabel rl1;
  JTextField rtf1;
  JButton rb;
  Connection con;
  String med;
  public Remove()
  {
    this.setBackground(Color.PINK);
    this.setLayout(null);

    rl1=new JLabel("Enter Medicine to Remove Stock");
    rl1.setBounds(180,120,200,30);
    add(rl1);

    rtf1=new JTextField();
    rtf1.setBounds(500,120,150,30);
    add(rtf1);

    rb=new JButton("REMOVE");
    rb.setBounds(320,220,200,30);
    add(rb);
    rb.addActionListener(this);
  }

    public void actionPerformed(ActionEvent e) 
    {
       med=rtf1.getText();
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","paw","Pawan123@");            
            PreparedStatement pstmt=con.prepareStatement("delete from owner where name=?");
            pstmt.setString(1, med);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Stock removed successfully");
        } 
        catch (Exception ex) 
        {
            System.out.println("ex");
        }
    }
}

class Log extends JPanel implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JButton lb1, lb2;
  JTextArea ta;
  Connection con;
        
  public Log()
  {
    this.setBackground(Color.PINK);
    this.setLayout(null);

    lb1=new JButton("SHOW STOCK");
    lb1.setBounds(180,120,200,30);
    add(lb1);
    lb1.addActionListener(this);

    lb2=new JButton("LOGOUT");
    lb2.setBounds(500,120,200,30);
    add(lb2);
    lb2.addActionListener(this);
    
    
  }

  public void actionPerformed(ActionEvent e)
  {
    if(e.getSource()==lb1)
    {
     ta=new JTextArea(20,10);
    ta.setBounds(50, 240, 800, 150);
    add(ta);
    ta.setBackground(Color.PINK);
      try 
      {
          Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","paw","Pawan123@");            
          Statement stmt=con.createStatement();
          ResultSet rs=stmt.executeQuery("select * from owner");
          ta.append("MEDICINE\t\tCOMPANY\t\tQUANTITY\t\tPRICE\n\n");
          while(rs.next())
          {
                ta.append(rs.getString(1)+"\t\t"
                      +rs.getString(2)+"\t\t"+rs.getInt(3)+"\t\t"
                      +rs.getInt(4)+"\n");
          }
          con.close();
            
      } 
      catch (Exception ex)
      {
          System.out.println(ex);
      }  
    }
    if (e.getSource()==lb2)
    {
        System.exit(0);
    }
  }
}

 class Billpage extends JFrame implements ActionListener
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JButton back;
    JTextArea text;
    JPanel p;
    String med;
   
    public Billpage(String title)
    {
        super(title);
        
        back=new JButton("BACK");
        back.setBounds(10, 10, 80, 25);
        add(back);
        back.addActionListener(this);
        
        p=new JPanel();
        add(p);
        p.setBackground(Color.cyan);
        
        text=new JTextArea(40,40);
        text.setBounds(50, 50, 10,10);
        p.add(text);
        text.setBackground(Color.cyan);     
    }
    
    public void billdisplay(String med,int qty, int price)
    {
        int totrs=qty*price;
        this.med=med;
        String q=Integer.toString(qty);
        String pr=Integer.toString(price);
        
        text.append("\n\n\n\n\t\tBILL\n\n\n");
        text.append("\tMEDICINE\t\t"+med+"\n\n");
        text.append("\tQUANTITY\t\t"+q+"\n\n");
        text.append("\tPRICE PER PIECE\t"+pr+"\n\n");
        text.append("\tTOTAL PRICE\t\t"+totrs+"\n\n");
    }
 
    public void actionPerformed(ActionEvent e) 
    {
        this.dispose();
    }
}

public class Medical extends JFrame implements ActionListener
{
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  JLabel lblu, lblp, image;
  JTextField txtu;
  JPasswordField pwd;
  JButton LogIn,Reset;
  JPanel pan;
  Connection con;

  public Medical(String title)
  {
    super(title);

    pan=new JPanel();
    pan.setLayout(null);
    pan.setBackground(Color.PINK);
    add(pan);

    lblu=new JLabel("UserName");
    lblu.setBounds(50,40,80,25);
    pan.add(lblu);

    txtu=new JTextField(10);
    txtu.setBounds(190,40,165,25);
    pan.add(txtu);

    lblp=new JLabel("Password");
    lblp.setBounds(50,80,80,25);
    pan.add(lblp);

    pwd=new JPasswordField(10);
    pwd.setBounds(190,80,165,25);
    pwd.setEchoChar('*');
    pan.add(pwd);

    LogIn=new JButton("LogIn");
    LogIn.setBounds(40,130,100,25);
    pan.add(LogIn);
    LogIn.addActionListener(this);

    Reset=new JButton("Reset");
    Reset.setBounds(200,130,100,25);
    pan.add(Reset);
    Reset.addActionListener(this);

  }

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource()==LogIn)
    {
        
        try 
        {
          Class.forName("com.mysql.cj.jdbc.Driver");
          con=DriverManager.getConnection("jdbc:mysql://localhost:3306/test","paw","Pawan123@"); 
          Statement stmt=con.createStatement();  
          String sql = "CREATE TABLE owner"+"(name VARCHAR(200),"+"company VARCHAR(200),"+"quantity INTEGER not Null ," + "price INTEGER not null)";
          stmt.executeUpdate(sql);         
          
              this.dispose();
              Main mn=new Main("MEDICAL STORE MANAGEMENT");
              mn.setVisible(true);
              mn.setSize(900,600);
              mn.setDefaultCloseOperation(EXIT_ON_CLOSE);
              mn.setLocationRelativeTo(null);
              mn.setResizable(false);
            
      } 
      catch (Exception ex)
      {
          System.out.println(ex);
      }  
    }

    if (e.getSource()==Reset)
    {
      txtu.setText("");
      pwd.setText("");
    }
  }

  public static void main(String[] args)
  {
    Medical m=new Medical("LOGIN");
    m.setVisible(true);
   
    m.setSize(450,240);
    m.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }
}
