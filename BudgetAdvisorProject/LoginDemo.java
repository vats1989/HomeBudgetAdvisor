import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*;
import java.sql.*;
import java.util.*;

class Login extends JFrame implements ActionListener,KeyListener
{
int id;
Font f;
JTextField tf1,tf2,tf3;
JButton b,c,d,back;
JLabel l1,l2,l3,l4,l5,l6,l7;
StringTokenizer vs;
Calendar cal;
String dat;
Connection con;
Statement st,st1;
ResultSet rs,rs1;
int mm,yy,dd,loginflag=0;

Login()
{
cal=Calendar.getInstance();
dd=cal.get(Calendar.DATE);
mm=cal.get(Calendar.MONTH); 
mm=mm+1;
yy=cal.get(Calendar.YEAR);
dat=""+mm+"-"+yy;
setLayout(null);
f=new Font("Arial",Font.BOLD,15);

l1=new JLabel("UserName :");
l1.setFont(f);
l1.setBounds(50,50,90,25);
add(l1);


l7=new JLabel("Already Exist");
l7.setFont(f);
l7.setBounds(290,50,400,20);
l7.setForeground(Color.red);
add(l7);
l7.setVisible(false);

tf1=new JTextField(15);
tf1.setBounds(160,50,120,25);
tf1.setText("mihir");
add(tf1);

l2=new JLabel("PassWord :");
l2.setFont(f);
l2.setBounds(50,90,80,25);
add(l2);

tf2=new JPasswordField(15);
tf2.setBounds(160,90,120,25);
tf2.setText("shah");
add(tf2);

l6=new JLabel("Login Failed");
l6.setFont(f);
l6.setBounds(290,90,400,20);
l6.setForeground(Color.red);
add(l6);
l6.setVisible(false);

l3=new JLabel("Birth Date:");
l3.setFont(f);
l3.setBounds(50,140,90,20);
l3.setForeground(Color.black);
add(l3);
l3.setVisible(false);

tf3=new JTextField(15);
tf3.setBounds(160,140,120,25);
add(tf3);
tf3.setVisible(false);

l5=new JLabel("Invalid date");
l5.setFont(f);
l5.setBounds(290,140,350,20);
l5.setForeground(Color.red);
add(l5);
l5.setVisible(false);


l4=new JLabel("(dd-mm-yyyy)");
l4.setFont(f);
l4.setBounds(45,160,100,25);
l4.setForeground(Color.red);
add(l4);
l4.setVisible(false);

b=new JButton("New User");
b.setBounds(40,200,115,40);
add(b);
 
c=new JButton("Ok");
c.setBounds(175,200,70,40); 
add(c);

back=new JButton("BACK");
back.setBounds(175,200,70,40); 
add(back);
back.setVisible(false);

d=new JButton("SUBMIT");
d.setBounds(265,200,90,40); 
d.setVisible(false);

add(d);

b.addActionListener(this);
c.addActionListener(this);
d.addActionListener(this);
back.addActionListener(this);
d.addKeyListener(this);


addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent we)
{
System.exit(0);
}
});

}

public void keyTyped(KeyEvent ke)
{
char ch=ke.getKeyChar();
if(ch=='\n');
submit();
}
public void keyPressed(KeyEvent ke)
{}
public void keyReleased(KeyEvent ke)
{}


public void actionPerformed(ActionEvent ae)
{
int i=0,flag=0,f=0,fo=0;
String s1="";
String s2="";
String s3="";
String s4="";
int s5=0;
String s7="";
int a[]=new int[3];
String s6="";
String ss=ae.getActionCommand();

if(ss.equals("Ok")==true)
{

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
st1=con.createStatement();
rs=st.executeQuery("select * from user_detail");
s1=tf1.getText();
s2=tf2.getText();
int fflagg=0;
if(true)
{
while(rs.next())
{
fflagg=1;
s5=rs.getInt("ID");
s3=rs.getString("user_name");
s4=rs.getString("password");


	if(s2.equals(s4)==false)
	{
	l6.setVisible(true);
	f=1;

	}
	
	
	if(s1.equals(s3)==true && s2.equals(s4)==true)
	{

	int flagnew=0;
	String newdate="";

	rs1=st1.executeQuery("select distinct(dt) from Categories where user_id="+s5+"");

	while(rs1.next())
	{
	newdate=rs1.getString("dt");
	if(dat.equals(newdate))
	{flagnew=1;
	System.out.println("date found..!");
	break;
	}
	}
	if(flagnew==0)
	{
	id=s5;
	loginflag=1;
	newEntry();
	}
	
setVisible(false);	
System.out.println("id :"+s5);
HomeBudget b=new HomeBudget("Budget",s5);
b.setVisible(true);
	}

}
	if(fflagg==0)
	{
	l6.setVisible(true);
	f=1;
	
	}
	}
rs.close();
rs1.close();
st.close();
st1.close();
con.close();
}
catch(Exception e)
{  System.out.println(e.getMessage()); }

if(s1.equals("")==false && s2.equals("")==false && (f==0))
{ setVisible(false); }
}

if(ss.equals("New User")==true)
{
l3.setVisible(true);
l4.setVisible(true);
d.setVisible(true);
tf3.setVisible(true);
c.setVisible(false);
back.setVisible(true);
}

if(ss.equals("BACK"))
{
l3.setVisible(false);
l4.setVisible(false);
d.setVisible(false);
tf3.setVisible(false);
c.setVisible(true);
back.setVisible(false);
l7.setVisible(false);
l5.setVisible(false);
}

if(ss.equals("SUBMIT"))
{
submit();
}
}

void submit()
{
int i=0,flag=0,dd=0,f=0,fo=0;
String s1="";
String s2="";
String s3="";
String s4="";
int s5=0;
String s7="";
int a[]=new int[3];
String s6="";


Connection con;
Statement st,st1;
ResultSet rs;

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

rs=st.executeQuery("select * from user_detail");


s1=tf1.getText();
s2=tf2.getText(); 
s3=tf3.getText();

while(rs.next())
{
id=rs.getInt("ID");
s7=rs.getString("user_name");



	if(s1.equals(s7)==true)
	{
	l7.setVisible(true);
	fo=1;
	}
	
}
++id;

vs=new StringTokenizer(s3,"-");
while(vs.hasMoreTokens())
{
try
{
a[i]=Integer.parseInt(vs.nextToken());

i++;
}
catch(Exception e)
{ l5.setVisible(true); }
}
 
if(a[0]<=0 || a[0]>=32)
{
flag=1;
l5.setVisible(true);
}
if(a[1]<=0 || a[1]>=13)
{
flag=1;
l5.setVisible(true);
}


if(a[0]>dd && a[1]>=mm && a[2]>=yy )
{ 
flag=1;
l5.setVisible(true);
}
if(a[1]>mm && a[2]>=yy)
{
flag=1;
l5.setVisible(true);
}

if(a[2]>yy)
{
flag=1;
l5.setVisible(true);
}

if((yy-100)>a[2])
{
flag=1;
l5.setVisible(true);
}

if(((a[2]%4!=0) && (a[2]%100!=0)) || (a[2]%400==0))
{
if(a[1]==2 && a[0]>28) 
{

flag=1;
l5.setVisible(true);
}
}

if(((a[2]%4==0) && (a[2]%100==0)) || a[2]%400!=0)
{
if(a[1]==2 && a[0]>29)
{
flag=1;
l5.setVisible(true);
}
}

if(s1.equals("")==false && s2.equals("")==false && s3.equals("")==false && (flag==0) && (fo==0))
{

st.executeUpdate("insert into user_detail values("+id+",'"+s1+"','"+s2+"','"+s3+"','0','"+dat+"')");

loginflag=0;
newEntry();

if(s1.equals("")==false && s2.equals("")==false && s3.equals("")==false && (flag==0) && (fo==0))
{
setVisible(false); 

  
  BankInformation bi=new BankInformation("Bank Details",id,"");
  bi.setVisible(true);


}
}
}

catch(Exception e)
{  System.out.println(e.getMessage()); }
}

public void newEntry()
{
Connection con;
Statement st;
ResultSet rs;

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

String items[][]={
{"Income","Salary","Interest"},
{"Clothing","Laundry","Purchases"},
{"Insurance","Homeowners Insurance","HouseHold contents insurance","Life Insurance"},
{"Medical","Dentist","Doctor","Hospital","Medical Aid Payments","Pharmacy/Chemist","Specialist","Sudry"},
{"Entertainment","Compact Discs","Computer","Dining Out","Internet","Magazines","Memberships","Movies","Other","TV/Cable/Satellite"},
{"Food","Milk","Vegetables","Farshan","Sweets","Pet Food"},
{"Groceries","Groceries","Toiletries"},
{"HouseHold","Appliances","Electricity","Furnishing","Garden Maintenance","Home Maintenance","Insurace","Mortgage","Rates and Taxes","Repairs","Security","Sudry","Telephone","Water"},
{"Miscellaneous","Allowances","Cell Phone","Cosmetics","Donations","Education","Gifts","Other","Pets"},
{"Savings","Banking Fees","Endowment Policies","I R A","Mutual Funds/Unit Trusts","Other","Retirement Annuities","Savings","Stocks/Shares"},
{"Transportation","Car repayments","Insurance","Licences","Maintenance","Petrol/CNG/Diesel/Oil","Sundry"}};

int h=0;
int login[]=new int[2];
String months="",ld="";

if(loginflag==1)
{
loginflag=0;
rs=st.executeQuery("select login_dt from user_detail where ID="+id+"");
while(rs.next())
{
ld=rs.getString("login_dt");
}
StringTokenizer vs=new StringTokenizer(ld,"-");
int ii=0;
while(vs.hasMoreTokens())
{
login[ii++]=Integer.parseInt(vs.nextToken());
}
System.out.println("login dt:"+login[0]+"   "+login[1]);
int syear=login[1];

if(login[0]>mm)
yy=yy-1;

mm=login[0];

}

System.out.println("final date..: "+mm+"   "+yy);
while(h<12)
{


if(mm>12)
{
mm=1;
yy=yy+1;

}

months=""+mm+"-"+yy;

for(int k=1;k<=11;k++)
{
if(k==1)
st.executeUpdate("insert into Categories values("+id+",'"+months+"',"+k+",'"+items[k-1][0]+"','0','0','+')");
else
st.executeUpdate("insert into Categories values("+id+",'"+months+"',"+k+",'"+items[k-1][0]+"','0','0','-')");

for(int j=1;j<items[k-1].length;j++)
{

st.executeUpdate("insert into Subcategry values("+id+","+k+",'"+months+"',"+j+",'"+items[k-1][j]+"','0','0','n')");

}
}
mm++;
h++;
}

st.close();
con.close();
}

catch(Exception e)
{  System.out.println(e.getMessage()); }
}




}



public class LoginDemo
{
	public static void main(String arg[])
	{
		try
		{
		Login frame=new Login();
		frame.setBounds(450,120,430,350);
		frame.setVisible(true);
		frame.setTitle("User Login");
		}
	catch(Exception e)
		{JOptionPane.showMessageDialog(null, e.getMessage());}
	}
}
 