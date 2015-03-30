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
public class Graph11 extends JFrame implements ItemListener,ActionListener//,MouseListener
{
Color colour,colour1;
Container co;
int x,y,clrscr=0,ff1=0,size=0,xx,yy,ff11=0,size1=0;
JPanel p,jp;
int flag1=0;
JComboBox y1,year,jcb;
JButton b;
Font f;
Calendar cal;
int userid;
Connection con;
Statement st;
ResultSet rs;
float total=0,total1=0;
String itemss,itemss1,item1,dt;
float p1[]=new float[12];
float p2[]=new float[12];
float actual[]=new float[5];
float tt[]=new float[5];
String[] name=new String[5];
float actual1[]=new float[5];
float tt1[]=new float[5];
String[] name1=new String[5];

float max1;
String mon[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};





Graph11(int userid)
{
super("Graph");
this.userid=userid;

Toolkit tk = Toolkit.getDefaultToolkit();  
x = ((int) tk.getScreenSize().getWidth());  
y = ((int) tk.getScreenSize().getHeight());  
setSize(x,y);
co=getContentPane();
co.setLayout(null);
//setBounds(x/7,0,6*x/7,y);
co.setBackground(Color.black);


jp=new JPanel();
jp.setLayout(null);
jp.setBounds(0,0,x/7,y);
jp.setBackground(Color.green);

del();

co.add(jp);

}
public void del() 
{
String s2="",date="";
int mm=0,yy=0;
Font f;
JLabel lb1,lb2,lb4,lb5;
Calendar cal=Calendar.getInstance();
mm=cal.get(Calendar.MONTH);
yy=cal.get(Calendar.YEAR);
mm=mm+1;
date=""+mm+"-"+yy;

f=new Font("Arial",Font.BOLD,15);

lb1=new JLabel("Select Year :");
lb1.setFont(f);
lb1.setForeground(Color.blue);
lb1.setBounds(5,50,100,25);



lb4=new JLabel("OR");
lb4.setFont(f);
lb4.setForeground(Color.blue);
lb4.setBounds(50,140,30,25);



lb5=new JLabel("Select Month :");
lb5.setFont(f);
lb5.setForeground(Color.blue);
lb5.setBounds(5,180,120,25);




lb2=new JLabel("Select Item :");
lb2.setFont(f);
lb2.setForeground(Color.blue);
lb2.setBounds(5,420,100,25);

year=new JComboBox();
year.setFont(f);
year.setBounds(30,80,80,25);

y1=new JComboBox();
y1.setFont(f);
y1.setBounds(10,450,120,25);


jcb=new JComboBox();
jcb.setBounds(10,210,110,25);
jcb.addItemListener(this);
jp.add(jcb);




b=new JButton();
b=new JButton("View");
b.setBounds(20,640,80,30);
b.addActionListener(this);
jp.add(b);
jp.add(lb1); jp.add(lb2);  jp.add(lb4); jp.add(lb5);
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

rs=st.executeQuery("select login_dt from user_detail where ID="+userid+"");
String dat="";
int a[]=new int[2];
while(rs.next())
{
dat=rs.getString("login_dt");
}
StringTokenizer vs=new StringTokenizer(dat,"-");
int i=0;
while(vs.hasMoreTokens())
{
a[i]=Integer.parseInt(vs.nextToken());
i++;
}


year.addItem("Year");
for(int k=yy;k>=a[1];k--)
year.addItem(""+k);
year.addItemListener(this);
jp.add(year);

rs=st.executeQuery("select * from Categories where user_id="+userid+" and dt='"+date+"'");
y1.addItem("-select-");
while(rs.next())
{
s2=rs.getString("item_name");
y1.addItem(s2);
}

y1.addItemListener(this);

jp.add(y1);


jcb.addItem("-Month-");
rs=st.executeQuery("select distinct(dt) from Categories where user_id="+userid+" order by dt desc");
while(rs.next())
{
String s=rs.getString("dt");
StringTokenizer v1=new StringTokenizer(s,"-");
i=0;

while(v1.hasMoreTokens())
{
a[i]=Integer.parseInt(v1.nextToken());
i++;
}
jcb.addItem((mon[a[0]-1])+"-"+a[1]);
}
rs.close();
st.close();
con.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }




}

public void itemStateChanged(ItemEvent ie)
{
Object source=ie.getSource();
if(source==y1)
{
itemss=(String)ie.getItem();
}

if(source==year)
{
ff1=0;
itemss1=(String)ie.getItem();
}
if(source==jcb)
{
item1=(String)ie.getItem();
ff1=1;
}
}

public void actionPerformed(ActionEvent ae)
{
String ss=ae.getActionCommand();
if(ss.equals("View"))
{
if(ff1==1)
{
int i=0;
String ab[]=new String[2];
StringTokenizer vs=new StringTokenizer(item1,"-");

while(vs.hasMoreTokens())
{
ab[i]=vs.nextToken();
i++;
}
for(i=0;i<12;i++)
{
if(mon[i].equals(ab[0]))
break;
}

dt=""+(i+1)+"-"+ab[1];

designGraph1();
flag1=5;
repaint();

}
else
{
designGraph();
flag1=1;
repaint();
}
//addMouseListener(this);
}
}


void designGraph1()
{
Connection con;
Statement st;
ResultSet rs;


try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

int i=0;


rs=st.executeQuery("select count(*),sum(actual) as total from Categories where user_id="+userid+" and dt='"+dt+"' and status='-' and actual>0");
while(rs.next())
{
size=Integer.parseInt(rs.getString(1));
tt=new float[size];
actual=new float[size];
name=new String[size];
total=rs.getFloat("total");
}

rs=st.executeQuery("select item_id,item_name,actual from Categories where user_id="+userid+" and dt='"+dt+"' and status='-' and actual>0");
while(rs.next())
{
name[i]=rs.getString("item_name");
actual[i]=rs.getFloat("actual");
tt[i++]=(float)(actual[i-1]/total)*360;

}

i=0;

rs=st.executeQuery("select count(*),sum(s_actual) as total1 from Subcategry where user_id="+userid+" and dt='"+dt+"' and s_actual>0 and item_id in(select item_id from Categories where user_id="+userid+" and dt='"+dt+"' and item_name='"+itemss+"')");
while(rs.next())
{
size1=Integer.parseInt(rs.getString(1));
tt1=new float[size1];
actual1=new float[size1];
name1=new String[size1];
total1=rs.getFloat("total1");
}

rs=st.executeQuery("select subitem_name,s_actual from Subcategry where user_id="+userid+" and dt='"+dt+"' and s_actual>0 and item_id in(select item_id from Categories where user_id="+userid+" and dt='"+dt+"' and item_name='"+itemss+"')");
while(rs.next())
{
name1[i]=rs.getString("subitem_name");
actual1[i]=rs.getFloat("s_actual");
tt1[i++]=(float)(actual1[i-1]/total1)*360;

}


rs.close();
st.close();
con.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }

}




void designGraph()
{
Connection con;
Statement st;
ResultSet rs;
int a[]=new int[2];
int tqs,po;
float b_max,a_max;

Font f;
JLabel l1,l2;

int x,y;


try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

rs=st.executeQuery("select login_dt from user_detail where ID="+userid+"");
String dat="";
while(rs.next())
{
dat=rs.getString("login_dt");
}
StringTokenizer vs=new StringTokenizer(dat,"-");
int i=0;
while(vs.hasMoreTokens())
{
a[i]=Integer.parseInt(vs.nextToken());
i++;
}


if(a[1]==Integer.parseInt(itemss1))
{
tqs=a[0];
po=a[0]+3;
}
else
tqs=1;

for(int loo=0;loo<12;loo++)
{
p1[loo]=p2[loo]=0;
}
for(i=tqs;i<=12;i++)
{
String dt=""+i+"-"+itemss1;

rs=st.executeQuery("select budget,actual from Categories where item_name='"+itemss+"' and user_id="+userid+" and dt='"+dt+"'");
//int j=0;
while(rs.next())
{
p1[i-1]=rs.getFloat("budget");
p2[i-1]=rs.getFloat("actual");

}
}
b_max=findMax(p1);
a_max=findMax(p2);

if(a_max>b_max)
max1=a_max;
else
max1=b_max;
rs.close();
st.close();
con.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }

}

float findMax(float a[])
{
float max=a[0];
for(int i=0;i<a.length;i++)
{
if(a[i]>max)
max=a[i];
}
return max;
}
/*

public void mouseEntered(MouseEvent me)
{

}
public void mouseExited(MouseEvent me)
{
}
public void mousePressed(MouseEvent me)
{
}
public void mouseReleased(MouseEvent me)
{

}
public void mouseClicked(MouseEvent me)
{
xx=me.getX();
yy=me.getY();
repaint();
}
*/





public void paint(Graphics g)
{

g.setColor(Color.black);
g.fillRect(x/7,0,6*x/7,y);
if(flag1==1)
{
g.setColor(Color.yellow);
g.drawString(itemss,x/2,y/9);
g.setColor(Color.red);
g.fillRect(6*x/7,y/9,20,15);

g.setColor(Color.white);
g.fillRect(6*x/7,y/7,20,15);
g.setColor(Color.yellow);
g.drawString("Budget Value",(6*x/7)+30,y/8);
g.drawString("Actual Value",(6*x/7)+30,(y/6)-5);
g.setColor(Color.blue);
g.drawLine(x/4,5*y/6,x/4,y/6);
g.drawLine(x/4,5*y/6,(14*x/15),5*y/6);
int a=2,total=0;
int aa=0;
int scale=(int)Math.ceil((double)max1/10);
for(int i=0;i<=10;i++)
{
g.setColor(Color.red);
g.drawString(""+total,(x/4)-50,(5*y/6)+aa);
if(i!=0)
g.drawLine((x/4)-2,(5*y/6)+aa-2,(x/4)+700,(5*y/6)+aa-2);
total=total+scale;

aa-=51;
}

for(int i=0;i<12;i++)
{
g.setColor(Color.red);
g.fillRect((x/4)+a,(5*y/6)-(int)(p1[i]*2*y/(3*max1)),17,(int)(p1[i]*2*y/(3*max1)));
if(p1[i]!=0)
g.drawString(""+(int)p1[i],(x/4)+a-18,(5*y/6)-(int)(p1[i]*2*y/(3*max1))-10);

g.setColor(Color.white);
g.fillRect((x/4)+a+17,(5*y/6)-(int)(p2[i]*2*y/(3*max1)),17,(int)(p2[i]*2*y/(3*max1)));
if(p2[i]!=0)
g.drawString(""+(int)p2[i],(x/4)+a+17,(6*y/7));
g.setColor(Color.yellow);
//g.setFont(f);
g.drawString(mon[i],(x/4)+a,(6*y/7)+17);

a+=60;
}
g.drawString("Months",x/2,12*y/13);

g.drawString("Value",x/6,y/2);
}

else if(flag1==5)
{
boolean flag=true;
long duplicate[]=new long[size];
Font f=new Font("Arial",Font.BOLD,20);
Random r;
int ang=0,rr=1,gg=1,bb=1;
g.setColor(Color.red);
g.setFont(f);
g.drawString("Expenditure of",(x/4),(y/10)-5);
g.drawString(item1,x/4,y/10+20);
g.setColor(Color.green);
g.drawString(" -   "+(int)total,x/3+15,y/10+20);
g.setColor(Color.red);
f=new Font("Arial",Font.PLAIN,12);
g.setFont(f);
r=new Random();
/*g.fillArc(x/4,y/4,300,200,0,80);
g.setColor(Color.blue);
g.fillArc(x/4,y/4,300,200,80,180);
g.setColor(Color.green);
g.fillArc(x/4,y/4,300,200,260,100);
*/
int sum=0,a=0;



//if(tt[0]>=180)
//g.fillArc(x/4,y/6,350,265,180,(int)((tt[0])-180));

g.fillArc(x/4,y/6,350,250,0,(int)(tt[0]));
g.setColor(Color.pink);
g.fillArc(x/4,y/6,350,265,180,180);

g.setColor(Color.red);
g.fillRect(5*x/6,(y/8),20,10);
g.drawString(name[0],(5*x/6)+30,(y/7)-3);
g.drawString("( "+((int)(tt[0]/3.6))+" % )",3*x/4,(y/7)-3);


for(int i=1;i<tt.length;i++)
{
flag=true;
while(flag)
{
int count=0;
rr=r.nextInt(255);
gg=r.nextInt(255);
bb=r.nextInt(255);
String e=rr+""+gg+""+bb;
duplicate[i-1]=Long.parseLong(e);

for(int k=0;k<i-1;k++)
{
if(duplicate[i-1]==duplicate[k])
{
count=1;
break;
}
}
if(count==0)
flag=false;
}








sum=sum+(int)(tt[i-1]);
float ma=sum+(tt[i]/2);


colour=new Color(rr,gg,bb);





if(name[0].equals(itemss))
{
System.out.println(y+"yyyyyyy");
g.setColor(Color.red);
//g.drawOval((x/7)+5,(int)(2*y/3.2),350,250);
g.fillArc((x/7)+5,(int)(2*y/3.2),350,250,0,(int)(tt[0]));
}

else if(name[i].equals(itemss))
{
g.setColor(colour);

//g.drawOval((x/7)+5,(int)(2*y/3.2),350,250);
g.fillArc((x/7)+5,(int)(2*y/3.2),350,250,sum,(int)(tt[i]));
}

g.setColor(colour);


a+=20;




g.fillRect(5*x/6,(y/8)+a,20,10);
g.drawString(name[i],(5*x/6)+30,(y/7)-3+a);

if(sum>=180)
{
//g.setColor(Color.green);
//g.fillArc(x/4,y/6,350,265,sum+5,(int)(tt[i]));
}

else if((sum+tt[i])>=180)
{
//g.setColor(Color.red);
//g.fillArc(x/4,y/6,350,265,180,(int)(tt[i])+sum-180+5);
}
g.setColor(colour);
g.fillArc(x/4,y/6,350,250,sum,(int)(tt[i]));
g.drawString("( "+((int)(tt[i]/3.6))+" % )",3*x/4,(y/7)-3+a);
//int newx=(int)(Math.cos((float)(ma))*175);
//int nneewwxx=(Math.abs(newx));
//System.out.print(newx+"newx"+"    -    ");
//int newy=(int)(Math.sin((float)(ma))*125);
//int nneewwyy=(Math.abs(newx));
//g.drawLine(x/4+175,y/4+125,newx,newy);
//g.drawString(""+((int)(tt[i]/3.6)),(x/4)+125-(nneewwxx/2),(y/4)+175-(nneewwyy));
}
/*
if(xx>=(x/4) && yy>=(y/4) && xx<=((x/4)+350) && yy<=((y/4)+250))
{
Color c=g.getColor();
g.setColor(c);
g.drawString("mihiriririr",500,700);
}*/
g.setColor(colour);
int s=360-(sum+(int)(tt[tt.length-1]));
System.out.println("sssssssss"+s);
g.fillArc(x/4,y/6,350,250,(int)(sum+tt[tt.length-1]),s);
g.setColor(Color.red);
g.drawString("Error : "+(float)(s/3.6)+" %",x/5,y/2-5);
g.setColor(Color.red);
g.drawOval(x/4,y/6,350,250);
g.drawArc(x/4,y/6,350,266,180,180);


duplicate=new long[size1];
sum=0;  a=0;
f=new Font("Arial",Font.BOLD,18);
g.setFont(f);
g.setColor(Color.red);
g.drawString(itemss+"  ("+(int)total1+")",x/2,2*y/3-20);
f=new Font("Arial",Font.PLAIN,12);
g.setFont(f);
g.drawOval(x/2+10,2*y/3,200,200);
g.fillArc(x/2+10,2*y/3,200,200,0,(int)(tt1[0]));
g.fillRect((5*x/6)-20,(2*y/3)-12,20,10);
g.drawString(name1[0],(5*x/6)+10,(2*y/3)-3);
g.drawString("( "+((int)(tt1[0]/3.6))+" % )",3*x/4,(2*y/3)-3);

for(int i=1;i<tt1.length;i++)
{
flag=true;
while(flag)
{
int count=0;
rr=r.nextInt(255);
gg=r.nextInt(255);
bb=r.nextInt(255);
String e=rr+""+gg+""+bb;
duplicate[i-1]=Long.parseLong(e);

for(int k=0;k<i-1;k++)
{
if(duplicate[i-1]==duplicate[k])
{
count=1;
break;
}
}
if(count==0)
{
flag=false;
if(tt1.length>=11)
a=a-5;
}
}
sum=sum+(int)(tt1[i-1]);
colour=new Color(rr,gg,bb);
g.setColor(colour);
a+=20;

g.fillRect((5*x/6)-20,(2*y/3)+a-12,20,10);
g.drawString(name1[i],(5*x/6)+10,(2*y/3)-3+a);
g.fillArc(x/2+10,2*y/3,200,200,sum,(int)(tt1[i]));
g.drawString("( "+((int)(tt1[i]/3.6))+" % )",3*x/4,(2*y/3)-3+a);
}
/*
if(xx>=(x/4) && yy>=(y/4) && xx<=((x/4)+350) && yy<=((y/4)+250))
{
Color c=g.getColor();
g.setColor(c);
g.drawString("mihiriririr",500,700);
}*/
s=360-(sum+(int)(tt1[tt1.length-1]));
g.fillArc(x/2+10,2*y/3,200,200,(int)(sum+tt1[tt1.length-1]),s);
g.setColor(Color.red);
g.drawString("Error : "+(int)(s/3.6)+" %",x/2+20,18*y/19);
}
}
}






//9638142536
