import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*;
import java.sql.*;
import javax.swing.JTable.*;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;
import javax.swing.table.TableModel;
import java.util.*;

public class Modify extends JFrame implements ItemListener,ActionListener
{
String status="-";
Font f;
String s1,news2,cat;
String del,date;
Connection con;
Statement st;
ResultSet rs,rs1;
HomeBudget hb;
int flag;
JTextField tf11,tf1;
JLabel l3,dell1,dell2,dell3,dell4;
int userid;
public Modify(String s,HomeBudget hb,int userid,String date)
{
this.date=date;
this.userid=userid;
this.hb=hb;
setLayout(null);

setBounds(300,300,400,250);
setBackground(Color.white);
if(s.equals("add"))
{
setTitle("Category Details");
add();
}
if(s.equals("delete"))
{
flag=1;
setTitle("Delete Category");
del();
}
if(s.equals("modify"))
{
flag=0;
setTitle("Modify Category");
del();
}
}

public void add()
{
JTextField tf2,tf3;
JButton b,c;
JRadioButton j1,j2;
JLabel l1,l2,l4;
Font f;

f=new Font("Arial",Font.BOLD,12);

l1=new JLabel("Category Description:");
l1.setFont(f);
l1.setForeground(Color.black);
l1.setBounds(30,30,180,25);
add(l1);

tf11=new JTextField(50);
tf11.setBounds(175,30,150,25);
add(tf11);

l2=new JLabel("Is this category an Income or an Expence category?");
l2.setFont(f);
l2.setForeground(Color.black);
l2.setBounds(30,80,350,25);
add(l2);

j1=new JRadioButton("Salary");
j2=new JRadioButton("Expenditure",true);

j1.setBounds(80,110,100,40);
j2.setBounds(220,110,100,40);
j1.addActionListener(this);
j2.addActionListener(this);
ButtonGroup bg=new ButtonGroup();
bg.add(j1);
bg.add(j2);
add(j1); add(j2);

l3=new JLabel("This category is already exist.");
l3.setFont(f);
l3.setForeground(Color.red);
l3.setBounds(80,5,250,20);
add(l3);
l3.setVisible(false);
b=new JButton("Add");
b.setBounds(150,170,70,30);
add(b);
b.addActionListener(this);
}



public void del() 
{

String s2;
JButton b=new JButton();
Font f;
JComboBox y;

y=new JComboBox();
f=new Font("Arial",Font.BOLD,14);
y.setFont(f);
y.setBounds(130,40,150,25);
if(flag==1)
{
b=new JButton("Delete");
}

if(flag==0)
{
dell1=new JLabel("New Name:");
dell1.setFont(f);
dell1.setForeground(Color.black);
dell1.setBounds(40,90,100,25);
add(dell1);
tf11=new JTextField(50);
tf11.setBounds(130,90,150,25);
add(tf11);

dell2=new JLabel("This category is already in used.");
f=new Font("Arial",Font.BOLD,15);
dell2.setFont(f);dell2.setForeground(Color.red);dell2.setBounds(50,2,250,25);
add(dell2);
dell2.setVisible(false);
b=new JButton("Update");
}
b.setBounds(150,140,100,30);
b.addActionListener(this);
add(b);
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
rs=st.executeQuery("select * from Categories where user_id="+userid+" and dt='"+date+"'");
y.addItem("-select-");
while(rs.next())
{
//s1=rs.getString("item_id");
s2=rs.getString("item_name");
if(s2.equals("Income"))
continue;
y.addItem(s2);
}

y.addItemListener(this);

add(y);

rs.close();
st.close();
con.close();
}

catch(Exception e)
{
System.out.println("\n"+e.getMessage());
}

}

public void itemStateChanged(ItemEvent ie)
{
s1=(String)ie.getItem();

}

public void adding(String status)
{
int fl=1;
int incre=0;
String dt[]=new String[12];
Vector<String> v=new Vector<String>();
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

String modifi=tf11.getText();
rs=st.executeQuery("select distinct(dt) from Categories where user_id="+userid+"");
int q=0;
while(rs.next())
{
v.add(rs.getString("dt"));
//dt[q++]=rs.getString("dt");
}
int sizearray=v.size();
dt=new String[sizearray];
Enumeration e1=v.elements();
while(e1.hasMoreElements())
{
dt[q++]=(String)e1.nextElement();
}
rs=st.executeQuery("select * from Categories where user_id="+userid+" and dt='"+date+"'");

while(rs.next())
{

incre=rs.getInt("item_id");


news2=rs.getString("item_name");
String sta=rs.getString("status");

if(modifi.equalsIgnoreCase(news2) && status.equals(sta))
{
l3.setVisible(true);
fl=0;

break;
}
}
if(fl==1)
{
incre=incre+1;

for(int r=0;r<q;r++)
st.executeUpdate("insert into Categories values("+userid+",'"+dt[r]+"',"+incre+",'"+modifi+"','0','0','"+status+"')");}

st.close();
rs1.close();
rs.close();
con.close();
}


catch(Exception e)
{
System.out.println("\n"+e.getMessage());
}
if(fl==1)
{
setVisible(false);
hb.dispose();
HomeBudget hb=new HomeBudget("Budget",userid);
hb.setVisible(true);
}
}


public void actionPerformed(ActionEvent ae)
{
//String inc=ae.getActionCommand();
del=ae.getActionCommand();

if(del.equals("Expenditure"))
{
status="-";

}
else if(del.equals("Salary"))
{

status="+";

}

else if(del.equals("Add"))
adding(status);
else
deleting();
}





public void deleting()
{
int fl=1,fk=1;
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
if(flag==1)
{


st.executeQuery("delete * from Categories where user_id="+userid+" and item_name='"+s1+"'");
}
if(flag==0)
{
String modifi=tf11.getText();

rs=st.executeQuery("select * from Categories where user_id="+userid+" and dt='"+date+"'");
while(rs.next())
{

news2=rs.getString("item_name");
if(modifi.equalsIgnoreCase(news2))
{


dell2.setVisible(true);


fl=0;

break;
}
}
if(fl==1)
{
rs=st.executeQuery("update Categories set item_name='"+modifi+"' where user_id="+userid+" and item_name='"+s1+"'");
}

rs.close();
st.close();
con.close();
}
}

catch(Exception e)
{
System.out.println("\n"+e.getMessage());
}
if((del.equals("Delete")==true) || ((del.equals("Update")==true) && fl==1) )
{
setVisible(false);
hb.dispose();
HomeBudget hb=new HomeBudget("Budget Adviser",userid);
hb.setVisible(true);
}
}
}
