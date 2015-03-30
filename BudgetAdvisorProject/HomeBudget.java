//package BudgetAdvisor;

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

class HomeBudget extends JFrame implements ActionListener
{

JPanel p,p2,p3,p4a,p6,p7,p5;
JMenuBar mb;
JMenu file,option,summary,help;
int userid;
JToolBar toolbar;
JButton cutbutton,copybutton;
Calendar cal;
String date;
JMenuItem ne,op,co,ex,d1,he,gp,rp;



int mm,yy;

HomeBudget(String s,int userid)
{
super(s);
this.userid=userid;
cal=Calendar.getInstance();
mm=cal.get(Calendar.MONTH);
yy=cal.get(Calendar.YEAR);
mm=mm+1;
date=""+mm+"-"+yy;
setLayout(null);
setResizable(false);
System.out.println("Date in homebudget: "+date);
setLocation(20,20);
setSize(1000,700);

mb=new JMenuBar();
setJMenuBar(mb);

file=new JMenu("File");
option=new JMenu("Option");
summary=new JMenu("Summary");
help=new JMenu("Help");
mb=new JMenuBar();
setJMenuBar(mb);

file=new JMenu("File");
option=new JMenu("Option");
summary=new JMenu("Summary");
help=new JMenu("Help");

ne=new JMenuItem("New");
op=new JMenuItem("Open");
co=new JMenuItem("Copy");
ex=new JMenuItem("Exit");
he=new JMenuItem("Help");
gp=new JMenuItem("Graph");
rp=new JMenuItem("Report");

mb.add(file); mb.add(option); mb.add(summary);
mb.add(help);

file.add(ne); file.add(new JSeparator());file.add(op); file.add(new JSeparator());file.add(co);file.add(new JSeparator());file.add(ex);
help.add(he);
summary.add(gp);
summary.add(new JSeparator());
summary.add(rp);

ne.addActionListener(this);
op.addActionListener(this);
co.addActionListener(this);
ex.addActionListener(this);
he.addActionListener(this);
gp.addActionListener(this);
rp.addActionListener(this);


p2=new ToolBar(this,userid);
p3=new Dates(this,date,userid);

p6=new Footer1(this,userid,date);

add(p2); add(p3); 

add(p6);




addWindowListener(new WindowAdapter()
{
public void windowClosing(WindowEvent we)
{
System.exit(0);
}
});

}

public void actionPerformed(ActionEvent ae)
{
String s=ae.getActionCommand();
if(s.equals("Open") || s.equals("New") || s.equals("Copy"))
{
		Open op=new Open(userid,s);
		op.setVisible(true);
}
if(s.equals("Exit"))
{ System.exit(0); }
if(s.equals("Help"))
{
Help b=new Help(userid,1);
b.setBounds(450,120,650,500);
b.setVisible(true);
}

if(s.equals("Graph"))
{
Graph11 g=new Graph11(userid);
g.setVisible(true);
}
if(s.equals("Report"))
{
Report re=new Report(userid);
re.setVisible(true);
}


}

}



class ToolBar extends JPanel implements ActionListener
{
int id;
JButton b,op,co,sa,pr,gr,bank;
JComboBox y;
Font f;
int userid;
Connection con,con1;
ResultSet rs,rs1;
Statement st,st1;
String s0;


ImageIcon i,i1,i2,i3,i4,i5;
ToolBar(HomeBudget hb1,int userid)
{

this.userid=userid;

setLayout(null);
setBounds(0,0,1000,40);


i=new ImageIcon("new-icon.png"); 
b=new JButton(i); 
b.setActionCommand("New");
b.setBounds(20,3,45,32);
b.addActionListener(this);

i1=new ImageIcon("open-icon.png"); 
op=new JButton(i1);
op.setActionCommand("Open");
op.setBounds(75,3,45,32); 
op.addActionListener(this);

i2=new ImageIcon("rep.jpg");
sa=new JButton(i2); 
sa.setBounds(270,3,45,32);
sa.setActionCommand("Report");


i3=new ImageIcon("print.png"); 
pr=new JButton(i3); 
pr.setBounds(220,3,45,32);

i4=new ImageIcon("copy-icon.png"); 
co=new JButton(i4); 
co.setActionCommand("Copy");
co.setBounds(130,3,45,32); 
co.addActionListener(this);

i5=new ImageIcon("graph-icon.png"); 
gr=new JButton(i5); 
gr.setActionCommand("Graph");
gr.setBounds(330,3,45,32);
gr.addActionListener(this);

bank=new JButton("Bank");
bank.setBounds(900,5,70,30);
setBackground(Color.blue);

add(b); add(op); add(sa); 
add(pr); add(co); add(gr); add(bank);

b.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Click on it to reset<br> actual value of any month.</font></body></html>"); 
op.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Analyse your Budget for perticular date.</font></body></html>"); 
sa.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Analyse your Budget using Report.</font></body></html>");
pr.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Print</font></html>"); 
co.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Copy the Budget value of <br>selected month to another month.</font></body></html>"); 
gr.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Analyse your Budget using Graph.</font></body></html>");

bank.setToolTipText("");
hb1.add(this);
sa.addActionListener(this);
bank.addActionListener(this);
}

public void actionPerformed(ActionEvent ae)
{
String s=ae.getActionCommand();
if(s.equals("Open") || s.equals("New") || s.equals("Copy"))
{
		Open op=new Open(userid,s);
		op.setVisible(true);
}
if(s.equals("Bank")==true)
{
BankInformation bi=new BankInformation("Bank Details",userid,"main");
bi.setVisible(true);
}

if(s.equals("Report"))
{
Report re=new Report(userid);
re.setVisible(true);
}
if(s.equals("Graph"))
{
Graph11 g=new Graph11(userid);
g.setVisible(true);
}
}
}

class Dates extends JPanel implements ActionListener
{
String mon[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
JButton months[]=new JButton[12];
JLabel year;
Font f,f1;
int id=1,ii=0;

int a[]=new int[2];
int aa[]=new int[2];
String date,s_itm="Income",date1;
int userid;
HomeBudget hb1;
JPanel p,p5;
Connection con;
Statement st;
ResultSet rs;
Dates(HomeBudget hb1,String date,int userid)
{

this.hb1=hb1;
this.userid=userid;
p=new JPanel();
p.setLayout(null);
p.setBounds(0,90,600,440);
p.setBackground(Color.white);
p5=new JPanel();
p5.setLayout(null);
p5.setBounds(605,90,385,600);
p5.setBackground(Color.white);

Categories c=new Categories(hb1,userid,p,date,p5,id,s_itm);
f1=new Font("Arial",Font.BOLD,20);
setBounds(0,40,1000,50);
setForeground(Color.blue);
setVisible(true);
setLayout(null);
this.date=date;
StringTokenizer vs=new StringTokenizer(date,"-");


int i=0;

while(vs.hasMoreTokens())
{
a[i]=Integer.parseInt(vs.nextToken());
i++;
}

year=new JLabel(""+a[1]);
year.setBounds(940,12,70,25);
add(year);
year.setFont(f1);
year.setForeground(Color.black);
months();

hb1.add(this);
}

void months()
{
int b=-50;
f=new Font("Arial",Font.BOLD,12);
for(int i=0;i<12;i++)
{
months[i]=new JButton(mon[i]);
months[i].setForeground(Color.blue);
months[i].setFont(f);
months[i].setBounds(b+=70,12,65,25);
add(months[i]);
months[i].addActionListener(this); 
}

months[a[0]-1].setEnabled(false);
}

public void en()
{
for(int i=0;i<12;i++)
{
months[i].setEnabled(true);
}
}



public void actionPerformed(ActionEvent ae)
{
String date="";
String s=ae.getActionCommand();
int i=0,flag=0;
for(i=0;i<12;i++)
{
if(s.equals(mon[i]))
{
en();
months[i].setEnabled(false);
break;
}
}
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
rs=st.executeQuery("select login_dt from user_detail where ID="+userid+"");
while(rs.next())
{
date1=rs.getString("login_dt");
}
rs.close();
st.close();
con.close();
}
catch(Exception e)
{
}
StringTokenizer vs1=new StringTokenizer(date1,"-");
ii=0;
while(vs1.hasMoreTokens())
{
aa[ii]=Integer.parseInt(vs1.nextToken());
ii++;
}

/*
if(a[1]==aa[1])
{
if(i<aa[0]-1)
flag=a[1]+1;
else
flag=a[1];
}
else
{
if(i<aa[0]-1)//-1) && i>a[0])
flag=a[1];
else
flag=a[1]-1;
}
*/

if(a[0]<aa[0])
{
if(i+1>=aa[0])
flag=a[1]-1;
else
flag=a[1];
}
else if(a[0]>=aa[0])
{
if(i+1<aa[0])
flag=a[1]+1;
else
flag=a[1];
}

remove(year);
year=new JLabel(""+flag);
year.setBounds(940,12,70,25);
year.setForeground(Color.black);
add(year);
year.setFont(f1);

date=""+(i+1)+"-"+flag;

//p.removeAll();
//p.repaint();
//p.revalidate();
//hb1.add(p);


Categories c=new Categories(hb1,userid,p,date,p5,id,s_itm);

}

}

class Categories extends JPanel implements ActionListener
{
static int done=1;
int s1;
JPanel p,p5,p7;
String s2,s_itm;
int a=-25;
HomeBudget hb;
JScrollPane jsp;
JLabel exp,lb1,tot_inc1,tot_exp1,net_inc1,tot_inc2,tot_exp2,net_inc2,l11,l12,l13;
JButton tot_inc,tot_exp,net_inc;
Border raisedBorder,etchedBorder,lineBorder;
Font f,f1;
Connection con;
Statement st;
ResultSet rs;

JButton cat[]=new JButton[11];
JLabel lab[]=new JLabel[11];
JLabel act[]=new JLabel[11];
JLabel status[]=new JLabel[11];
JButton s_cate,s_budget,s_actual;
ImageIcon img1,img2,img3;
int i=0,id,statusflag=1;
float b_tot1,b_tot2,b_tot3,a_tot1,a_tot2,a_tot3;
String yn;
int userid;
JButton cate,budget,actual,stat;
Color c3;

String date;


Categories(HomeBudget hb1,int userid,JPanel p,String date,JPanel p5,int id,String s_itm)
{
hb=hb1;
this.date=date;
this.userid=userid;
this.p=p;

this.p5=p5;


Color c3=new Color(100,20,20);
SubCategory c1=new SubCategory(hb,p5,id,s_itm,p,userid,date);
s_cate=new JButton("Item");
s_cate.setBounds(5,0,152,20);
s_budget=new JButton("Budget");
s_actual=new JButton("Actual");

s_budget.setBounds(158,0,110,20);
s_actual.setBounds(269,0,114,20);


lb1=new JLabel("Value Updated..");
lb1.setBounds(100,160,300,30);
lb1.setForeground(Color.red);

lb1.setFont(f1);
lb1.setVisible(true);

p5.add(lb1); p5.add(s_budget); p5.add(s_actual); p5.add(s_cate);
hb.add(p5);
lineBorder = new LineBorder(Color.gray,2);

p.setBorder(lineBorder);


p.removeAll();
p.repaint();
//p.revalidate();
setConnection();
hb.add(p);
}

public void setConnection()
{
float s3,s4;
JPanel jp=new JPanel();
jp.setLayout(null);
jp.setBackground(Color.white);
Border border = LineBorder.createGrayLineBorder();

tot_inc=new JButton("TOTAL INCOME");
tot_exp=new JButton("TOTAL EXPENDITURE");
net_inc=new JButton("NET INCOME");




cate=new JButton("Categories");
stat=new JButton("");
budget=new JButton("Budget");
actual=new JButton("Actual");



tot_inc.setForeground(Color.black);
tot_exp.setForeground(Color.black);
net_inc.setForeground(Color.black);


tot_inc.setBounds(5,341,222,30);
tot_exp.setBounds(5,373,222,30);
net_inc.setBounds(5,405,222,30);



cate.setBounds(5,0,223,20);
stat.setBounds(230,0,39,20);
budget.setBounds(270,0,164,20);
actual.setBounds(435,0,165,20);
p.add(tot_inc); p.add(tot_exp); p.add(net_inc);

p.add(cate); p.add(budget); p.add(actual); p.add(stat);

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
rs=st.executeQuery("select count(*) from Categories where user_id="+userid+" and dt='"+date+"'");
while(rs.next())
{
int i=rs.getInt(1);
cat=new JButton[i];
lab=new JLabel[i];
act=new JLabel[i];
status=new JLabel[i];
}
rs=st.executeQuery("select * from Categories where user_id="+userid+" and dt='"+date+"'");

	while(rs.next())
	{
	s1=rs.getInt("item_id");
	s2=rs.getString("item_name");
	s3=rs.getFloat(5);
	s4=rs.getFloat(6);
    yn=rs.getString("status");

	
	JLabel exp=new JLabel(" "+yn);
	cat[i]=new JButton(s2);
	cat[i].setBounds(25,a+=26,197,25);
	cat[i].setForeground(Color.blue);
	exp.setBounds(1,a,22,25);
	exp.setOpaque(true);
	f1=new Font("Arial",Font.BOLD,20);
	exp.setBackground(Color.white);
	//exp.setColor(Color.red);
	exp.setFont(f1);
	if(yn.equals("+"))
	{
	statusflag=1;
	exp.setForeground(Color.black);
	exp.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>This is an Income category.</font></body></html>");
	b_tot1=b_tot1+s3;
	a_tot1=a_tot1+s4;
	}
	if(yn.equals("-"))
	{
	statusflag=0;
	b_tot2=b_tot2+s3;
	a_tot2=a_tot2+s4;
	exp.setForeground(Color.red);
	}
	exp.setBorder(border);
			if((s3>s4 && statusflag==0)|| (s3<s4 && statusflag==1))
			{
			img1=new ImageIcon("up-icon.png");
			status[i]=new JLabel(img1);
			status[i].setBounds(224,a,39,25);
			status[i].setBorder(border);
			}
			else if((s3<s4 && statusflag==0)||(s3>s4 && statusflag==1))
			{
			img2=new ImageIcon("down-icon.png");
			status[i]=new JLabel(img2);
			status[i].setBounds(224,a,39,25);
			status[i].setBorder(border);
			}
			else
			{
			//img2=new ImageIcon("up.png");
			status[i]=new JLabel();
			status[i].setBounds(224,a,39,25);
			status[i].setBorder(border);
			}
	lab[i]=new JLabel(""+s3);
	lab[i].setBounds(265,a,163,25);
	lab[i].setForeground(Color.blue);
	lab[i].setBorder(border);
	act[i]=new JLabel(""+s4);
	act[i].setBounds(430,a,150,25);
	act[i].setForeground(Color.blue);
	act[i].setBorder(border);

	cat[i].addActionListener(this);

	jp.add(cat[i]);
	jp.add(exp);
	jp.add(status[i]);
	jp.add(lab[i]);
	jp.add(act[i++]);
	}

b_tot3=b_tot1-b_tot2;
a_tot3=a_tot1-a_tot2;

tot_inc1=new JLabel(""+b_tot1);
tot_exp1=new JLabel(""+b_tot2);
net_inc1=new JLabel(""+b_tot3);

tot_inc1.setBorder(border);
tot_exp1.setBorder(border);
net_inc1.setBorder(border);

if(b_tot1<a_tot1)
img1=new ImageIcon("up-icon.png");
else if(b_tot1>a_tot1)
img1=new ImageIcon("down-icon.png");
else
img1=new ImageIcon(" ");

if(b_tot2>a_tot2)
img2=new ImageIcon("up-icon.png");
else if(b_tot2<a_tot2)
img2=new ImageIcon("down-icon.png");
else
img2=new ImageIcon(" ");

if(b_tot3<a_tot3)
img3=new ImageIcon("up-icon.png");
else if(b_tot3>a_tot3)
img3=new ImageIcon("down-icon.png");
else
img3=new ImageIcon(" ");

tot_inc2=new JLabel(""+a_tot1);
tot_exp2=new JLabel(""+a_tot2);
net_inc2=new JLabel(""+a_tot3);

l11=new JLabel(img1);
l12=new JLabel(img2);
l13=new JLabel(img3);
l11.setBounds(230,341,39,30);
l11.setBorder(border);
l12.setBounds(230,373,39,30);
l12.setBorder(border);
l13.setBounds(230,405,39,30);
l13.setBorder(border);
tot_inc2.setBorder(border);
tot_exp2.setBorder(border);
net_inc2.setBorder(border);

tot_inc1.setBounds(272,341,162,30);
tot_exp1.setBounds(272,373,162,30);
net_inc1.setBounds(272,405,162,30);
tot_inc2.setBounds(436,341,160,30);
tot_exp2.setBounds(436,373,160,30);
net_inc2.setBounds(436,405,160,30);

tot_inc1.setForeground(Color.blue);
tot_exp1.setForeground(Color.blue);

if(b_tot3<0)
net_inc1.setForeground(Color.red);

else
net_inc1.setForeground(Color.blue);

tot_inc2.setForeground(Color.blue);
tot_exp2.setForeground(Color.blue);

if(a_tot3<0)
net_inc2.setForeground(Color.red);

else
net_inc2.setForeground(Color.blue);

p.add(tot_inc1); p.add(tot_exp1); p.add(net_inc1); p.add(l11); p.add(l12); p.add(l13);
p.add(tot_inc2); p.add(tot_exp2); p.add(net_inc2);

jsp=new JScrollPane(jp);
jp.setPreferredSize(new Dimension(500,a+30));
jsp.setBounds(5,20,595,290);

p.add(jsp);

rs.close();
st.close();
con.close();
}

catch(Exception e)
{
System.out.println("\n"+e.getMessage());
}
}

public void actionPerformed(ActionEvent ae)
{
lb1.setVisible(false);

int a=0;
s_itm=ae.getActionCommand();
//String s1;

//int count=0;
		try
		{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		con=DriverManager.getConnection("jdbc:odbc:budget");
		st=con.createStatement();

		rs=st.executeQuery("select item_id from Categories where user_id="+userid+" and item_name='"+s_itm+"' and dt='"+date+"'");
					while(rs.next())
					{
					id=rs.getInt("item_id");
					}
		}


		catch(Exception e)
		{
	
		System.out.println("\n "+e.getMessage());
		}

SubCategory c1=new SubCategory(hb,p5,id,s_itm,p,userid,date);

}
}


class SubCategory extends JPanel implements ItemListener,ActionListener
{
String l1[]=new String[5];
JComboBox list;
Calendar cal;
String s_itm,s_catname;
int row,col,id,flag=0;
Enumeration e;
HomeBudget hb1;
JScrollPane jsp1;
JLabel lb,lb1,lb2,err;
JPanel p,p5,jp1,p7;
Font f1,f2;
int isless500=0;
int nobankflag=1;
	Connection con;
	Statement st;
	ResultSet rs;
int fla=0;
int f01=0;	
int userid;
JCheckBox s_cat1[]=new JCheckBox[2];
//Vector <Object>v=new Vector<Object>();
//Vector s3=new Vecor();
JTextField s_act1[]=new JTextField[2];
JTextField s_lab1[]=new JTextField[2];
JTextField bud;
JRadioButton j1,j2;
JTextField checkno;
JButton s_cate,s_ok,s_budget,s_actual;
JButton ad,del,mod;
ImageIcon i1,i2,i3;
String s22[]=new String[2];
//float s3[]=new float[20];
float s4[]=new float[2];
String s5[]=new String[2];
String s6[]=new String[2];
String checkstatus;
int checknostatus=1;
String sous;

Vector <String>v1=new Vector<String>();
Vector <String>v2=new Vector<String>();
float s3,add1=0,add2=0,sele=0,tota=0,tota1=0;
int i;
double sous1;
String date;
int dd,mm,yy;

SubCategory(HomeBudget hb,JPanel p5,int id,String s_itm,JPanel p,int userid,String date)
{
this.date=date;


hb1=hb;
this.p=p;
this.userid=userid;
this.s_itm=s_itm;
this.p5=p5;

this.id=id;
setConnection0();

layOut1();
hb1.add(p5);

}


void layOut1()
{
Border border = LineBorder.createGrayLineBorder();
Font fj1=new Font("Arial",Font.BOLD,15);
JLabel jj4=new JLabel("|");
jj4.setBounds(6,462,2,86);
jj4.setBorder(border);
jj4.setBackground(Color.black);
p5.add(jj4);

lb2=new JLabel("ENTER VALID DATA");

JLabel jj5=new JLabel("|");
jj5.setBounds(382,462,2,86);
jj5.setBorder(border);
jj5.setBackground(Color.black);
p5.add(jj5);

JLabel jj2=new JLabel("Budget Subcategories");
Font fj2=new Font("Arial",Font.BOLD,12);
jj2.setBounds(11,450,150,20);
jj2.setFont(fj2);
JLabel jj1=new JLabel("_______________________________");

jj1.setFont(fj1);
jj1.setBounds(140,444,250,20);
p5.add(jj2);
p5.add(jj1);
JLabel jj3=new JLabel("_______________________________________________");

jj3.setFont(fj1);
jj3.setBounds(7,530,390,20);
p5.add(jj3);
i1=new ImageIcon("add-icon.png");


ad=new JButton(i1);
ad.setBounds(50,488,70,32);
String s="s_add"+id;

ad.setActionCommand("s_add");

ad.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Add SubCategory</font></body></html>");
i2=new ImageIcon("recycle-icon.png");
del=new JButton(i2);
del.setBounds(150,488,70,32);
del.setActionCommand("s_delete");
del.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Delete SubCategory</font></body></html>");
i3=new ImageIcon("rename-icon.png");

mod=new JButton(i3);
mod.setBounds(250,488,70,32);
mod.setActionCommand("s_modify");
mod.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Rename</font></body></html>");
p5.setVisible(true);
p5.add(ad); p5.add(del); p5.add(mod);
//hb1.add(p7);

ad.addActionListener(this);
del.addActionListener(this); 
mod.addActionListener(this);

}



public void setConnection0()
{
hb1.add(p5);
p5.removeAll();
p5.repaint();
p5.revalidate();
int c=-23;
//String s2,s3,s4;
jp1=new JPanel();
jp1.setLayout(null);
jp1.setBackground(Color.white);



s_ok=new JButton("OK");
j1=new JRadioButton("By Cheque",true);
j2=new JRadioButton("By Credit Card");

f1=new Font("Arial",Font.BOLD+Font.ITALIC,16);
f2=new Font("Arial",Font.BOLD+Font.ITALIC,14);
Color c1=new Color(110,100,100);

ButtonGroup bg=new ButtonGroup();
bg.add(j1);
bg.add(j2);
s_cate=new JButton(s_itm);
s_cate.setBounds(5,0,152,20);

s_budget=new JButton("Budget");
s_actual=new JButton("Actual");

s_budget.setBounds(158,0,110,20);
s_actual.setBounds(269,0,114,20);

s_ok.setBounds(150,410,70,25);
lb=new JLabel("Please enter correct value...");
lb.setBounds(100,360,300,20);
lb.setForeground(Color.red);
p5.add(lb);
lb.setFont(f1);
lb.setVisible(false);

err=new JLabel("You don't have any Bank Account.");
err.setBounds(100,360,300,20);
err.setForeground(Color.red);
p5.add(err);
err.setFont(f1);
err.setVisible(false);

list=new JComboBox();
checkno=new JTextField(30);
checkno.setBounds(250,320,100,20);
checkno.setText("Enter cheque no.");
checkno.setVisible(false);
checkno.addMouseListener(new MouseAdapter()
{
public void mouseClicked(MouseEvent me)
{
if((checkno.getText()).equals("Enter cheque no."))
checkno.setText("");
}
}
);

j1.setBounds(70,380,100,20);
j1.setBackground(Color.white);
j2.setBounds(210,380,130,20);
j2.setBackground(Color.white);


p5.add(s_cate); p5.add(s_budget); p5.add(s_actual); 
p5.add(s_ok);
j1.addActionListener(this);
j2.addActionListener(this);
j1.setVisible(false);
j2.setVisible(false);
p5.add(j1); p5.add(j2); p5.add(checkno);

try
{
	
	Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	con=DriverManager.getConnection("jdbc:odbc:budget");
	st=con.createStatement();

	rs=st.executeQuery("select ac_number from Bank where user_id="+userid+"");
	list.addItem("-Select A/C no.-");
	while(rs.next())
	{
	list.addItem(rs.getString("ac_number"));
	}
	rs=st.executeQuery("select count(*) from Subcategry where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");
	while(rs.next())
	{
	int i=rs.getInt(1);

	s22=new String[i];
	s4=new float[i];
	s5=new String[i];
    s6=new String[i];
	s_cat1=new JCheckBox[i];
    s_act1=new JTextField[i];
    s_lab1=new JTextField[i];
	}
	rs=st.executeQuery("select * from Subcategry where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");

				while(rs.next())
				{

				s22[i]=rs.getString("subitem_name");
				s3=rs.getFloat(6);
				s4[i]=rs.getFloat(7);

				s_cat1[i]=new JCheckBox(s22[i]);
				s_cat1[i].setBounds(2,c+=26,150,25);

				s_lab1[i]=new JTextField(""+s3);
				//v.add(s_lab1);
				s_lab1[i].setBounds(153,c,110,25);
				s_lab1[i].setFont(f1);
				s_lab1[i].setBackground(Color.white);


				s_act1[i]=new JTextField("0");
				s_act1[i].setBounds(264,c,110,25);
				s_act1[i].setFont(f2);
				s_act1[i].setBackground(Color.white);

				s_cat1[i].addItemListener(this);
				jp1.add(s_cat1[i]);
				jp1.add(s_lab1[i]);
				jp1.add(s_act1[i++]);

				}
				
list.setBounds(100,320,120,20);
list.setVisible(false);
				p5.add(list);
//e=v.elements();
list.addItemListener(this);
s_ok.addActionListener(this);

	int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
	int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

	jsp1=new JScrollPane(jp1,v,h);
	jp1.setPreferredSize(new Dimension(360,c+30));
	jsp1.setBounds(5,20,380,290);
	p5.add(jsp1);
	hb1.add(p5);

	rs.close();
	st.close();
	con.close();

}


	catch(Exception e)
	{
	System.out.println("\n "+e.getMessage());
	}

}





public void itemStateChanged(ItemEvent ie)
{
Object source=ie.getSource();
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
if(source==list)
{
sous=(String)ie.getItem();

}
else
{

JCheckBox s_cat1=(JCheckBox)ie.getItem();
int states=ie.getStateChange();

			if(states==ItemEvent.SELECTED)
			{
			fla++;
			
			j1.setVisible(true);
			j2.setVisible(true);
			

			if(checknostatus==1)
			{
			checkno.setText("Enter cheque no.");
			checkno.setVisible(true);
			}
			list.setVisible(true);
			String sel=s_cat1.getText();
			
			rs=st.executeQuery("select user_id from Bank where user_id="+userid+"");
			if(rs.next()==false)
			{
			nobankflag=1;
			s_ok.setEnabled(false);
			j1.setVisible(false);
			j2.setVisible(false);
			err.setVisible(true);
			checkno.setVisible(false);
			list.setVisible(false);
			
			}
			else
			nobankflag=0;
			st.executeUpdate("update Subcategry set pay_type='y' where user_id="+userid+" and item_id="+id+" and subitem_name='"+sel+"' and dt='"+date+"'");
			
			}
			else
			{
			fla--;
			
			if(fla<=0)
			{
			nobankflag=1;
			j1.setVisible(false);
			j2.setVisible(false);
			checkno.setVisible(false);
			//lb.setVisible(true);
			list.setVisible(false);
			err.setVisible(false);
			s_ok.setEnabled(true);
			
			}

			String sel=s_cat1.getText();
			st.executeUpdate("update Subcategry set pay_type='n' where user_id="+userid+" and item_id="+id+" and subitem_name='"+sel+"' and dt='"+date+"'");
			}
		}
	rs.close();
	st.close();
    con.close();
}

catch(Exception e)
{

System.out.println("\n "+e.getMessage());
}
}

public void actionPerformed(ActionEvent ae)
{

String paytype="";
String ok=ae.getActionCommand();

if((ok.equals("s_add")) || (ok.equals("s_delete")) || (ok.equals("s_modify")))
{

Modify1 mf1=new Modify1(ok,hb1,userid,id,date);
mf1.setVisible(true);
}


if(ok.equals("By Credit Card"))
{
checkno.setVisible(false);
checknostatus=0;
}


if(ok.equals("By Cheque"))
{
checkno.setVisible(true);
checknostatus=1;
checkno.setText("Enter cheque no.");
}


if(ok.equals("OK"))
{
int lessthan500flag=0;
float add_bud=0,add_act=0,result;
try
{

Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();


rs=st.executeQuery("select status from Categories where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");
while(rs.next())
{
checkstatus=rs.getString("status");

}


if(fla>0)
{

rs=st.executeQuery("select * from Bank where user_id="+userid+" and ac_number='"+sous+"'");

while(rs.next())
{
tota=rs.getFloat("current");


}
}

rs=st.executeQuery("select * from user_detail where ID="+userid+"");

while(rs.next())
{
tota1=rs.getFloat("cash");

}


rs=st.executeQuery("select pay_type from Subcategry where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");
int j=0;

while(rs.next())
{
paytype=rs.getString("pay_type");

try
{
f01=0;
s5[j]=s_lab1[j].getText();
s6[j]=s_act1[j].getText();


add_bud=add_bud+Float.parseFloat(s5[j]);
add_act=add_act+Float.parseFloat(s6[j]);

if(Float.parseFloat(s5[j])<0 || Float.parseFloat(s6[j])<0)
{
f01=1;
DialogBox dc=new DialogBox(hb1,"Error Message",1);
dc.setVisible(true);
break;
}

}
catch(NumberFormatException nfe)
{
f01=1;
lb.setVisible(true);
}



if(paytype.equals("y"))
{
add1=add1+Float.parseFloat(s6[j]);
}
else
{
add2=add2+Float.parseFloat(s6[j]);
}
j++;
}

if(checkstatus.equals("-"))
result=tota-add1;
else
result=tota+add1;



if(f01==0)
{
st.executeUpdate("update Bank set current="+result+" where ac_number='"+sous+"'");
if(result<500 && nobankflag==0)
{
isless500=1;
st.executeUpdate("update Bank set current="+tota+" where ac_number='"+sous+"'");
DialogBox dc=new DialogBox(hb1,"Error Message",2);
dc.setVisible(true);
}

result=0;
if(isless500==0)
{

if(checkstatus.equals("-"))
result=tota1-add2;
else
result=tota1+add2;

st.executeUpdate("update user_detail set cash="+result+" where ID="+userid+"");

rs=st.executeQuery("select actual from Categories where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");
float ac=0;
while(rs.next())
{
ac=rs.getFloat("actual");
}
add_act=add_act+ac;

st.executeUpdate("update Categories set budget='"+add_bud+"' where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");
st.executeUpdate("update Categories set actual='"+add_act+"' where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");


int m=0;
			for(j=0;j<i;j++)
			{

			float f=Float.parseFloat(s5[j]);
			String s=s_cat1[j].getText();
	
			st.executeUpdate("update Subcategry set s_budget='"+f+"' where user_id="+userid+" and subitem_name='"+s+"' and item_id="+id+" and dt='"+date+"'");
			float f2=0;
			rs=st.executeQuery("select * from Subcategry where user_id="+userid+" and subitem_name='"+s+"' and item_id="+id+" and dt='"+date+"'");
			String statusofpaytype="";
						while(rs.next())
						{
						f2=rs.getFloat("s_actual");
						statusofpaytype=rs.getString("pay_type");
						

						}
						float f1=Float.parseFloat(s6[j]);
						if(f1!=0)
						{
						int ds[]=new int[3];
						StringTokenizer vs1=new StringTokenizer(date,"-");
						int ii=0;
						while(vs1.hasMoreTokens())
						{
						ds[ii]=Integer.parseInt(vs1.nextToken());
						ii++;
						}
						cal=Calendar.getInstance();
						dd=cal.get(Calendar.DATE);
						String sqlDate=ds[0]+"/"+dd+"/"+ds[1];
						System.out.println("sqldateeeee"+sqlDate);
						java.sql.Date sqlDate1 = new java.sql.Date(new java.util.Date().getTime());
						if(statusofpaytype.equals("y"))
						{
						String sous10="";
						if(checknostatus==1)
						sous10=sous+" - "+checkno.getText();
						else
						sous10=sous;
						st.executeUpdate("insert into Daily_report values("+userid+",'"+s+"',"+f1+",'"+sqlDate+"','"+sous10+"','"+checkstatus+"')");
						}
						else
						st.executeUpdate("insert into Daily_report values("+userid+",'"+s+"',"+f1+",'"+sqlDate+"','"+statusofpaytype+"','"+checkstatus+"')");
						//subcategory_names[m]=s;
						//subcategory_values[m++]=s6[j];
						//v1.add(s);
						//v2.add(s6[j]);
						}
						f2=f1+f2;
						st.executeUpdate("update Subcategry set s_actual='"+f2+"' where user_id="+userid+" and subitem_name='"+s+"' and item_id="+id+" and dt='"+date+"'");
			}


rs=st.executeQuery("select cash from user_detail where ID="+userid+"");
while(rs.next())
{
float f=rs.getFloat("cash");
if(f<0)
{
DialogBox dc=new DialogBox(hb1,"Error Message",0);
dc.setVisible(true);
}
}
}

p.removeAll();
p.repaint();
p.revalidate();
p5.removeAll();
p5.repaint();
p5.revalidate();

Categories ca=new Categories(hb1,userid,p,date,p5,id,s_itm);



st.executeUpdate("update Subcategry set pay_type='n' where user_id="+userid+" and item_id="+id+" and dt='"+date+"'");
}
st.close();
con.close();
}

catch(Exception e)
{

System.out.println("\n "+e.getMessage());
}

}
}

}
class DataStorage
{
static String s1[]=new String[1];
static String s2[]=new String[1];
//DataStorage(String[] s_names,String[] s_values)
DataStorage(Vector v1,Vector v2)
{
int size=v1.size();
Enumeration e1=v1.elements();
Enumeration e2=v2.elements();
s1=new String[size];
s2=new String[size];
int i=0;


//for(int i=0;i<s_names.length;i++)
while(e1.hasMoreElements())
{
s1[i]=(String)e1.nextElement();
s2[i++]=(String)e2.nextElement();


}

}
/*
void inputInFile() throws IOException
{

File f=new File
FileWriter fw=new FileWriter("Store1.txt",true);
for(int j=0;j<s1.length;j++)
{
fw.write(s1+"   "+s2);
}
fw.close();

Filewriter fo=new Filewriter("Store1.txt");
for(int j=0;j<s1.length;j++)
{
fo.write(s1[j]+"          "+s2[j]+"\n");
}
fo.close();

}
*/
}

class DialogBox extends Dialog implements ActionListener
{
JLabel b;
DialogBox(Frame parent,String title,int flag)
{
super(parent,title,false);
setLayout(null);
setBounds(350,300,400,130);
Font f=new Font("Arial",Font.BOLD,15);

if(flag==2)
{b=new JLabel("Access denied! Your Bank balance is below 500.");}
else if(flag==1)
{
 b=new JLabel("           Enter valid Number");
}
else
{
b=new JLabel("            You cash amount is below 0.");
}
b.setFont(f);
b.setForeground(Color.red);
b.setBounds(20,50,350,20);
add(b);

JButton jb=new JButton("Ok");
jb.setBounds(150,90,70,25);
add(jb);
jb.addActionListener(this);
}

public void actionPerformed(ActionEvent ae)
{
dispose();
}


}



class Footer1 extends JPanel implements ActionListener
{
HomeBudget hb;
JButton ad,del,mod,duplicate;
ImageIcon i1,i2,i3,i4;
String date;
int userid;
String s="Budget categories";
Footer1(HomeBudget hb1,int userid,String date)
{
hb=hb1;
this.date=date;
this.userid=userid;
setLayout(null);
setBounds(0,540,597,100);
setBackground(Color.white);
setBorder(new TitledBorder(new LineBorder(Color.black,2),""+s));
i1=new ImageIcon("add-icon.png");
layOut();
}

public void layOut()
{
ad=new JButton(i1);
ad.setBounds(50,35,70,32);
ad.setActionCommand("add");
ad.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Add Category</font></body></html>");
i2=new ImageIcon("recycle-icon.png");
del=new JButton(i2);
del.setBounds(150,35,70,32);
del.setActionCommand("delete");
del.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Delete Category</font></body></html>");
i3=new ImageIcon("rename-icon.png");
//i3=new ImageIcon("new-icon.png");
mod=new JButton(i3);
mod.setBounds(250,35,70,32);
mod.setActionCommand("modify");
mod.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5> Rename </font></body></html>");

i4=new ImageIcon("copy-icon1.png"); 
duplicate=new JButton(i4); 
duplicate.setBounds(505,25,60,55);
duplicate.setActionCommand("Duplicate");
duplicate.setToolTipText("<html><body style=\"background-color:#FFFFCC\"><font color=black size=5>Duplicate</font></body></html>");
setVisible(true);
add(ad); add(del); add(mod); add(duplicate);
hb.add(this); 
ad.addActionListener(this);
duplicate.addActionListener(this);
del.addActionListener(this); 
mod.addActionListener(this);
}

public void actionPerformed(ActionEvent ae)
{
String ss=ae.getActionCommand();

if(ss.equals("Duplicate"))
{
	Open op=new Open(userid,ss);
	op.setVisible(true);
}
else
{
Modify mf=new Modify(ss,hb,userid,date);
mf.setVisible(true);
}
}
}


/*
class BudgetAdvisor
{
public static void main(String[] st)throws Exception
{
int id=2;

HomeBudget hb=new HomeBudget("BUDGET",id);
hb.setVisible(true);
hb.setTitle("Budget Advisor");
}
}
*/





