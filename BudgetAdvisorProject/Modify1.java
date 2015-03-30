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

public class Modify1 extends JFrame implements ItemListener,ActionListener
{
Font f;
String s1,news2,cat;
String del,date;
Connection con;
Statement st;
ResultSet rs,rs1;
HomeBudget hb;
int flag,id;
JTextField tf11,tf1;
JLabel l3,dell1,dell2,dell3,dell4;
int userid;
public Modify1(String s,HomeBudget hb,int userid,int id,String date)
{
this.id=id;
this.userid=userid;
this.date=date;
this.hb=hb;
setLayout(null);

setBounds(300,300,400,250);
setBackground(Color.white);
if(s.equals("s_add"))
{
setTitle("Category Details");
add();
}
if(s.equals("s_delete"))
{
flag=1;
setTitle("Delete Category");
del();
}
if(s.equals("s_modify"))
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

l1=new JLabel("Subcategory Description:");
l1.setFont(f);
l1.setForeground(Color.black);
l1.setBounds(30,30,180,25);
add(l1);

tf11=new JTextField(50);
tf11.setBounds(175,30,150,25);
add(tf11);



l3=new JLabel("This Subcategory is already exist.");
l3.setFont(f);
l3.setForeground(Color.red);
l3.setBounds(80,5,250,20);
add(l3);
l3.setVisible(false);
b=new JButton("Add");
b.setBounds(150,100,70,30);
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

dell2=new JLabel("This item is already in used.");
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
rs=st.executeQuery("select * from Subcategry where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");
y.addItem("-select-");
while(rs.next())
{
//s1=rs.getString("item_id");
s2=rs.getString("subitem_name");
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

public void adding()
{
int incre=0;
int fl=1,fflagg=0;
String dt[]=new String[12];
Vector<String> v=new Vector<String>();
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

String modifi=tf11.getText();

rs=st.executeQuery("select distinct(dt) from Subcategry where user_id="+userid+"");// and item_id="+id+"");
int q=0;
while(rs.next())
{
//dt[q++]=rs.getString("dt");
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



rs=st.executeQuery("select * from Subcategry where user_id="+userid+" and item_id="+id+"");

if(rs.next()==false)
{

incre=1;
for(int r=0;r<q;r++)
{

st.executeUpdate("insert into Subcategry values("+userid+","+id+",'"+dt[r]+"',"+incre+",'"+modifi+"','0','0','n')");
}
while(rs.next())
{
fflagg=1;
incre=rs.getInt("subitem_id");




news2=rs.getString("subitem_name");

if(modifi.equals(news2))
{

l3.setVisible(true);
fl=0;

break;
}
}
}
if(fl==1)
{
incre=incre+1;
for(int r=0;r<q;r++)
{

st.executeUpdate("insert into Subcategry values("+userid+","+id+",'"+dt[r]+"',"+incre+",'"+modifi+"','0','0','n')");
}
}
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
del=ae.getActionCommand();
if(del.equals("Add"))
adding();
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
{st.executeQuery("delete subitem_name from Subcategry where user_id="+userid+" and item_id="+id+" and subitem_name='"+s1+"'");}
if(flag==0)
{
String modifi=tf11.getText();

rs=st.executeQuery("select * from Subcategry where user_id="+userid+" and dt='"+date+"' and item_id="+id+"");
while(rs.next())
{
//s1=rs.getString("item_id");
news2=rs.getString("subitem_name");
if(modifi.equals(news2))
{


dell2.setVisible(true);


fl=0;

break;
}
}
if(fl==1)
{
rs=st.executeQuery("update Subcategry set subitem_name='"+modifi+"' where user_id="+userid+" and item_id="+id+" and subitem_name='"+s1+"'");
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
