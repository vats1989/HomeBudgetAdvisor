import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*;
import java.sql.*;
import java.util.*;

class BankInformation extends JFrame implements ActionListener,KeyListener
{
JLabel b1,b2,b3,b4,b5,b6,b8,b9,exc,errr;
int i=1,b,ff=0,fff=0,row;
Font f;
double ca;
JTextField t1,t2,t3,t4;
JButton ok,show,back,skip;
JLabel sr[]=new JLabel[20];
JLabel b_name[]=new JLabel[20];
JLabel acc_no[]=new JLabel[20];
JLabel curr_bal[]=new JLabel[20];

String m,s1,s2,s3,s4;
String s6[]=new String[1];
String s7[]=new String[1];
String s8[]=new String[1];
String s9[]=new String[1];
int userid;
BankInformation(String s,int userid,String m)
{
super(s);
this.m=m;
this.userid=userid;
setLayout(null);
setBounds(450,120,400,450);
setVisible(true);
f=new Font("Arial",Font.BOLD,20);
errr=new JLabel("Cash is Mandatory.");
errr.setFont(f);
errr.setForeground(Color.red);
errr.setBounds(150,300,200,25);
errr.setVisible(false);
b1=new JLabel("Bank Name ");
b2=new JLabel("A/C No. ");
b3=new JLabel("Bank Balance ");
b4=new JLabel("Cash :");
b5=new JLabel("Sr.No");
b6=new JLabel("ACCOUNT DETAILS");
b6.setFont(f);
b6.setVisible(false);

skip=new JButton("Skip");
skip.setBounds(285,350,100,30);
skip.setVisible(true);
add(skip);
ok=new JButton("Submit");
ok=new JButton("Submit");
show=new JButton("Show");
exc=new JLabel("Invalid No..");
back=new JButton("Back");
back.setBounds(165,300,100,30);
back.setVisible(false);

t1=new JTextField(50);
t2=new JTextField(15);
t3=new JTextField(10);
t4=new JTextField(10);

show.setBounds(165,30,100,30);
b1.setBounds(20,90,100,30);
b2.setBounds(20,140,100,30);
b3.setBounds(20,190,100,30);
b4.setBounds(20,240,100,30);


t1.setBounds(140,90,150,30);
t2.setBounds(140,140,150,30);
t3.setBounds(140,190,150,30);
t4.setBounds(140,240,150,30);

ok.setBounds(165,350,100,30);
exc.setBounds(150,280,150,25);
exc.setVisible(false);
add(b1);  add(b2);  add(b3);  add(b4); 
add(t1);  add(t2);  add(t3);  add(t4);
add(ok);  add(show);add(back);add(b5); add(errr);
add(b6);add(exc);
ok.addActionListener(this);
ok.addKeyListener(this);
show.addActionListener(this);
back.addActionListener(this);
skip.addActionListener(this);

if(m.equals("")==true)
{
show.setVisible(false);

}
if(m.equals("main"))
{
ff=1;
b4.setVisible(false);
t4.setVisible(false);
skip.setVisible(false);
}
}


public void keyTyped(KeyEvent ke)
{
char ch=ke.getKeyChar();
if(ch=='\n')
submit();
}
public void keyPressed(KeyEvent ke)
{}
public void keyReleased(KeyEvent ke)
{}


public void actionPerformed(ActionEvent ae)
{

String ss=ae.getActionCommand();

if(ss.equals("Submit"))
{ submit(); }
if(ss.equals("Skip"))
{
int ff=0,fff=0;
float ff1=0;
String s=t4.getText();
if(s.equals(""))
{

errr.setVisible(true);
ff=1;
}
else
{
Connection con;
Statement st;
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
try
{
fff=0;
ff1=Float.parseFloat(s);
if(ff1<0)
{ fff=1; exc.setVisible(true); }
}
catch(NumberFormatException nfe)
{ fff=1; exc.setVisible(true); }

if(fff==0)
{
st.executeUpdate("update user_detail set cash="+ff1+" where ID="+userid+"");
setVisible(false);
Help b=new Help(userid,0);
b.setBounds(450,120,650,500);
b.setVisible(true);
}

st.close();
con.close();

}
catch(Exception e)
{ System.out.print("vatsal"+e.getMessage()); }
}
}

if(ss.equals("Show"))
{
setLayout(null);
setBounds(450,120,520,550);
JLabel b7;
b1.setBounds(60,110,175,30);  b2.setBounds(250,110,100,30);
b3.setBounds(385,110,100,30); b4.setBounds(25,70,50,30);
b5.setBounds(5,110,35,30);  b6.setBounds(160,20,300,30);
b6.setVisible(true);b4.setVisible(true);back.setVisible(true); 
ok.setBounds(200,300,100,30); back.setBounds(400,70,80,25);
t1.setVisible(false);t2.setVisible(false);
t3.setVisible(false);t4.setVisible(false);ok.setVisible(false);

show.setVisible(false);b5.setVisible(true);b6.setVisible(true);

Connection con;
Statement st;
ResultSet rs;

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
rs=st.executeQuery("select cash from user_detail where ID="+userid+"");

while(rs.next())
{ ca=Double.parseDouble(rs.getString("cash")); }

b7=new JLabel(""+ca);
b7.setBounds(70,70,250,30);
b7.setVisible(true);
add(b7);

rs=st.executeQuery("select count(*) from bank where user_id="+userid+"");
while(rs.next())
{
row=rs.getInt(1);
s6=new String[row+1];
s7=new String[row+1];
s8=new String[row+1];
}
rs=st.executeQuery("select * from Bank where user_id="+userid+"");
while(rs.next())
{
s6[i]=rs.getString("bank_name");
s7[i]=rs.getString("ac_number");
s8[i]=rs.getString("current");
i++;
}

int a=110,b=110,d=110,e=110,f=110;

for(int j=1;j<i;j++)
{
sr[j]=new JLabel(""+j);
sr[j].setBounds(12,a+=26,15,30);

b_name[j]=new JLabel(s6[j]);
b_name[j].setBounds(60,b+=26,130,30);

acc_no[j]=new JLabel(s7[j]);
acc_no[j].setBounds(250,d+=26,150,30);

curr_bal[j]=new JLabel(s8[j]);
curr_bal[j].setBounds(385,e+=26,150,30);

add(sr[j]);
add(b_name[j]);
add(acc_no[j]);
add(curr_bal[j]);
}

st.close();
con.close();
rs.close();
}
catch(Exception e)
{ System.out.print("vatsal"+e.getMessage()); }
}

if(ss.equals("Back")==true)
{ setVisible(false); }

}
void submit()
{
String s1=t1.getText();
String s2=t2.getText();
String s3=t3.getText();
String s4="";
float ff1=0,ff2=0;
if(ff==0)
{
s4=t4.getText();
}
try
{
fff=0;
ff1=Float.parseFloat(s4);
if(ff1<0)
{ fff=1; exc.setVisible(true); }
}
catch(NumberFormatException nfe)
{ fff=1; exc.setVisible(true); }


try
{
fff=0;
ff2=Float.parseFloat(s3);
if(ff2<0)
{ fff=1; exc.setVisible(true); }
}
catch(NumberFormatException nfe)
{ fff=1; exc.setVisible(true); }

Connection con;
Statement st;

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

if(s1.equals("")==false && s2.equals("")==false && s3.equals("")==false && s4.equals("")==false && ff==0 && fff==0)
{
st.executeUpdate("insert into Bank values("+userid+",'"+s1+"','"+s2+"',"+ff2+")");
st.executeUpdate("update user_detail set cash="+ff1+" where ID="+userid+"");
}

if(s1.equals("")==false && s2.equals("")==false && s3.equals("")==false && ff==1 && fff==0)
{
st.executeUpdate("insert into Bank values("+userid+",'"+s1+"','"+s2+"',"+ff2+")");
}
st.close();
con.close();
}

catch(Exception e)
{ System.out.println("\n"+e.getMessage()); }

if(s1.equals("")==false && s2.equals("")==false && s3.equals("")==false && s4.equals("")==false && ff==0 && fff==0) 
{ 
setVisible(false);
Help b=new Help(userid,0);
b.setBounds(450,120,650,500);
b.setVisible(true);
}
if(s1.equals("")==false && s2.equals("")==false && s3.equals("")==false && ff==1 && fff==0)
{
setVisible(false);
}
}
}
