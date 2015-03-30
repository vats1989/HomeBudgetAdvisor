import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;
import javax.swing.JTable.*;
import javax.swing.table.*;

import javax.swing.event.*;

import javax.swing.table.*;


class Open extends JFrame implements ActionListener,ItemListener
{
Calendar cal;
JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9,new1,new21,new22,new23,d1,d2,d3,newlabel;
Font f,f1;
JComboBox jcb,jcb1;
JTextField tf1,tf2,tf3,change;
JButton b,yes1,no1,yes2,change1;
JTable t;
JPanel p;

Connection co;
Statement st,st1,st2;
ResultSet rs,rs1;
String dat,dat1,dat11,dat2,dat3,newchange,s_date,opneco,date;
int userid;
int flag=1,mm,yy;
float total_inc,total_exp;
float s22=0,indexno;
String dead_stock_no="",new_itm_na="",dt11;
String mon[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
Open(int userid,String s)
{
opneco=s;
this.userid=userid;
setLayout(null);



f=new Font("Arial",Font.BOLD,16);


if(opneco.equals("Open"))
{
setBounds(300,200,500,400);
setTitle("Daily Report");
newOpen();
}

if(opneco.equals("New"))
{
setBounds(275,200,480,300);
setTitle("New");
newNew();
}

if(opneco.equals("Copy")||opneco.equals("Duplicate"))
{
setTitle("Duplicate");
setBounds(275,200,450,250);
newCopy();
}

}

void newOpen()
{
l1=new JLabel("Enter Date:");
l1.setFont(f);
l1.setForeground(Color.black);
l1.setBounds(50,30,100,25);
add(l1);

newlabel=new JLabel("Enter New Value :");
newlabel.setBounds(80,310,100,25);
l1.setFont(f);
add(newlabel);
newlabel.setVisible(false);

change=new JTextField(10);
change.setBounds(200,313,80,25);
add(change);
change.setVisible(false);

change1=new JButton("change");
change1.setBounds(300,310,90,30);
add(change1);
change1.setVisible(false);
change1.addActionListener(this);

l6=new JLabel("Total Income:");
l6.setFont(f);
l6.setForeground(Color.blue);
l6.setBounds(250,80,120,25);
//add(l6);

l7=new JLabel("Total Exp . :");
l7.setFont(f);
l7.setForeground(Color.blue);
l7.setBounds(250,110,120,25);
//add(l7);

tf1=new JTextField(2);
tf1.setBounds(175,30,30,25);
add(tf1);
l2=new JLabel("-");
l2.setFont(f);
l2.setForeground(Color.black);
l2.setBounds(210,30,5,25);
add(l2);
tf2=new JTextField(2);
tf2.setBounds(220,30,30,25);
add(tf2);
l3=new JLabel("-");
l3.setFont(f);
l3.setForeground(Color.black);
l3.setBounds(255,30,5,25);
add(l3);
tf3=new JTextField(4);
tf3.setBounds(265,30,50,25);
add(tf3);

l4=new JLabel("mm       dd     yyyy");
l4.setFont(f);
l4.setForeground(Color.blue);
l4.setBounds(175,10,150,15);
add(l4);
b=new JButton("OK");
b.setBounds(350,30,70,25);
add(b);
b.addActionListener(this);



p=new JPanel();
p.setBounds(50,80,400,200);
//p.setBackground(Color.white);
add(p);


l5=new JLabel("No Record Found.");
l5.setFont(f);
l5.setForeground(Color.red);
l5.setBounds(0,1,100,23);
l5.setVisible(false);
p.add(l5);


}


void newNew()
{
new23=new JLabel("select the month which you want to reset:");
new23.setBounds(20,20,300,20);
add(new23);

jcb=new JComboBox();
jcb.addItem("-select-");
jcb.setBounds(160,50,100,25);


cal=Calendar.getInstance();
mm=cal.get(Calendar.MONTH);
yy=cal.get(Calendar.YEAR);
mm=mm+1;
date=""+mm+"-"+yy;

int a[]=new int[2];

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
co=DriverManager.getConnection("jdbc:odbc:budget");
st=co.createStatement();
rs=st.executeQuery("select login_dt from user_detail where ID="+userid+"");
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
if(a[0]==1)
{
a[0]=12;
}
else
{
a[0]=a[0]-1;
a[1]=a[1]+1;
}
int kk=mm-1;
for(int j=0;j<12;j++)
{
jcb.addItem(mon[kk]+"-"+""+yy);
if(kk==11)
{
kk=-1;
yy++;
}

if(kk==a[0]-1)
break;
kk++;
}

rs.close();
st.close();
co.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }


jcb.addItemListener(this);
add(jcb);
new1=new JLabel("Warning :");
new1.setFont(f);
new1.setForeground(Color.blue);
new1.setBounds(5,150,75,20);
add(new1);

new21=new JLabel("All the values of budget and actual of this month will be reset. It also");

new21.setBounds(80,150,400,20);
add(new21);


new22=new JLabel("changes your balance. Are you sure you want to reset all the values?");

new22.setBounds(5,170,475,25);
add(new22);

yes1=new JButton("Yes");
yes1.setBounds(140,220,70,25);

yes1.addActionListener(this);
add(yes1);
no1=new JButton("No");
no1.setBounds(220,220,70,25);
add(no1);

no1.addActionListener(this);
}

void newCopy()
{
jcb=new JComboBox();
jcb.addItem("-select-");
jcb.setBounds(75,50,100,25);
jcb1=new JComboBox();
jcb1.addItem("-select-");
jcb1.setBounds(250,50,100,25);
d1=new JLabel("Copy of :");
d1.setBounds(90,20,100,20);

d1.setForeground(Color.black);
d2=new JLabel("Copy to :");
d2.setBounds(270,20,100,20);

d2.setForeground(Color.black);

l3=new JLabel("This will duplicate(copy) only budget values.");
l3.setBounds(10,100,350,25);
l3.setFont(f);
l3.setForeground(Color.blue);

yes2=new JButton("Copy");
yes2.setBounds(165,170,80,30);
cal=Calendar.getInstance();
mm=cal.get(Calendar.MONTH);
yy=cal.get(Calendar.YEAR);
mm=mm+1;
date=""+mm+"-"+yy;

int a[]=new int[2];
 add(d1); add(d2); add(l3); add(yes2);
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
co=DriverManager.getConnection("jdbc:odbc:budget");
st=co.createStatement();
rs=st.executeQuery("select login_dt from user_detail where ID="+userid+"");

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
int log_mon=(a[1]*12)+a[0];
int cur_mon=(yy*12)+mm;
int o=cur_mon-log_mon+1;

int k=a[0]-1;

for(int p=0;p<o;p++)
{

jcb.addItem(mon[k]+"-"+""+a[1]);

if(k==11)
{
k=0;
a[1]++;
}
else
{
k++;
}

}




if(a[0]==1)
{
a[0]=12;
}
else
{
a[0]=a[0]-1;
a[1]=a[1]+1;
}
int kk=mm-1;
for(int j=0;j<12;j++)
{
jcb1.addItem(mon[kk]+"-"+""+yy);
if(kk==11)
{
kk=-1;
yy++;
}

if(kk==a[0]-1)
break;
kk++;
}




rs.close();
st.close();
co.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }
jcb.addItemListener(this);
add(jcb);
jcb1.addItemListener(this);
add(jcb1);
yes2.addActionListener(this);
}
/*
public void keyTyped(KeyEvent ke)
{
char ch=ke.getKeyChar();
if(ch=='\n');
report();
}

public void keyPressed(KeyEvent ke)
{}
public void keyReleased(KeyEvent ke)
{}
*/

public void itemStateChanged(ItemEvent ie)
{
Object source=ie.getSource();
if(source==jcb)
{
dat1=(String)ie.getItem();
}

if(source==jcb1)
{
dat11=(String)ie.getItem();
}

}
public void actionPerformed(ActionEvent ae)
{
String del=ae.getActionCommand();
if(del.equals("change"))
{
newchange=change.getText();
changeDailyReport(newchange);
}
if(del.equals("Yes"))
{
reset();
}
if(del.equals("No"))
{
dispose();
}
if(del.equals("OK"))
{

report();
}

if(del.equals("Copy"))
{
duplicate();
}
}

void reset()
{
String sub[]=new String[2];

StringTokenizer vs=new StringTokenizer(dat1,"-");
int l=0;
while(vs.hasMoreTokens())
{
sub[l]=vs.nextToken();
l++;
}
int j=0;
for(j=0;j<12;j++)
{

if(mon[j].equals(sub[0]))
break;
}

dat1=""+(j+1)+"-"+sub[1];
dat2=(j+1)+"/1/"+sub[1];
if(j>=11)
j=-1;
dat3=(j+2)+"/1/"+(Integer.parseInt(sub[1])+1);




try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
co=DriverManager.getConnection("jdbc:odbc:budget");
st=co.createStatement();
st1=co.createStatement();
st2=co.createStatement();
int i=0;
float sub_value=0,sum1=0,sum2=0,totalcash=0;
String status1="",paytype1="";

st.executeUpdate("update Categories set budget='0',actual='0' where user_id="+userid+" and dt='"+dat1+"'");
st.executeUpdate("update Subcategry set s_budget='0',s_actual='0' where user_id="+userid+" and dt='"+dat1+"'");
rs=st.executeQuery("select cash from user_detail where ID="+userid+"");
while(rs.next())
{totalcash=rs.getFloat("cash");}
System.out.println("dat2dat3"+totalcash);
///////java.sql.Date temp=new java.sql.Date(new java.util.Date().getTime());
System.out.println("dat2dat3"+dat2+"   "+dat3);
//String s1="Insert into holiday values ("+recNum+", '"+name+"',"+include+", #"+ temp+"#)";
rs=st.executeQuery("select * from daily_report where user_id="+userid+" and dt>=#"+dat2+"# and dt<#"+dat3+"#");
while(rs.next())
{
status1=rs.getString("status");
paytype1=rs.getString("paytype");
sub_value=rs.getFloat("sub_value");
System.out.println("status11111"+status1+"    "+paytype1);
String ds[]=new String[2];
StringTokenizer vs1=new StringTokenizer(status1,"-");
						int ii=0;
						while(vs1.hasMoreTokens())
						{
						ds[ii]=(vs1.nextToken());
						ii++;
						}
						status1=ds[0];
	if(status1.equals("n") && paytype1.equals("-"))
	{

	sum1=sum1+sub_value;
	}
	if(status1.equals("n") && paytype1.equals("+"))
	{
	sum1=sum1-sub_value;

	}
	if(!(status1.equals("n")) && paytype1.equals("-"))
	{
	float tc=0;


	
	rs1=st1.executeQuery("select current from Bank where user_id="+userid+" and ac_number='"+status1+"'");
	while(rs1.next())
	{
	tc=rs1.getFloat("current");
	}
	tc=tc+sub_value;
	st1.executeUpdate("update Bank set current='"+tc+"' where user_id="+userid+" and ac_number='"+status1+"'");

	}
	if(!(status1.equals("n")) && paytype1.equals("+"))
	{
	float tc=0;
	rs1=st2.executeQuery("select current from Bank where user_id="+userid+" and ac_number='"+status1+"'");
	while(rs1.next())
	{
	tc=rs1.getFloat("current");
	}
	tc=tc-sub_value;
	
	st2.executeUpdate("update Bank set current='"+tc+"' where user_id="+userid+" and ac_number='"+status1+"'");
	}

}

st.executeUpdate("delete from daily_report where user_id="+userid+" and dt>=#"+dat2+"# and dt<#"+dat3+"#");

totalcash=totalcash+sum1;
System.out.println("cashhhhhhhh"+totalcash);
st.executeUpdate("update user_detail set cash='"+totalcash+"' where ID="+userid+"");


rs1.close();
rs.close();
st2.close();
st1.close();
st.close();
co.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }
dispose();
}



void duplicate()
{
float bud=0;
String sub[]=new String[2];
String sub1[]=new String[2];

StringTokenizer vs=new StringTokenizer(dat1,"-");
StringTokenizer vs1=new StringTokenizer(dat11,"-");
int l=0,j=0,k=0,ll=0,b=0;
while(vs.hasMoreTokens())
{
sub[l]=vs.nextToken();
l++;
}

for(j=0;j<12;j++)
{
if(mon[j].equals(sub[0]))
break;
}
dat1=""+(j+1)+"-"+sub[1];



while(vs1.hasMoreTokens())
{
sub1[ll]=vs1.nextToken();
ll++;
}

for(k=0;k<12;k++)
{

if(mon[k].equals(sub1[0]))
break;
}
dat11=""+(k+1)+"-"+sub1[1];


try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
co=DriverManager.getConnection("jdbc:odbc:budget");
st=co.createStatement();
st1=co.createStatement();
rs=st.executeQuery("select budget,item_id from Categories where user_id="+userid+" and dt='"+dat1+"'");
while(rs.next())
{
bud=rs.getFloat("budget");
int itmid=rs.getInt("item_id");
st1.executeUpdate("update Categories set budget='"+bud+"' where user_id="+userid+" and dt='"+dat11+"' and item_id="+itmid+"");
}

rs=st.executeQuery("select item_id,subitem_id,s_budget from Subcategry where user_id="+userid+" and dt='"+dat1+"'");
while(rs.next())
{
int s1=rs.getInt("item_id");
int s2=rs.getInt("subitem_id");
float s3=rs.getFloat("s_budget");
st1.executeUpdate("update SubCategry set s_budget='"+s3+"' where user_id="+userid+" and dt='"+dat11+"' and item_id="+s1+" and subitem_id="+s2+"");
}




rs.close();
st1.close();
st.close();
co.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }
dispose();
}



void report()
{
boolean DEBUG=false;
boolean ALLOW_ROW_SELECTION=true;
int row=2,row1=0;
float s2=0;

float total_inc=0,total_exp=0;
String t1=tf1.getText();
String t2=tf2.getText();
String t3=tf3.getText();
s_date=t1+"/"+t2+"/"+t3;
dt11=t1+"-"+t3;
String sd[][]=new String[1][3];
String head[]={"ITEM NAME","EXP / INC","STATUS"};
String s4="";
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
co=DriverManager.getConnection("jdbc:odbc:budget");
st=co.createStatement();

int i=0;
rs=st.executeQuery("select count(*) from Daily_report where user_id="+userid+" and dt=#"+s_date+"#");
while(rs.next())
{
int s=rs.getInt(1);
sd=new String[s+3][3];
}

rs=st.executeQuery("select * from Daily_report where user_id="+userid+" and dt=#"+s_date+"#");

while(rs.next())
{
String s1=rs.getString("sub_name");
 s2=rs.getFloat("sub_value");
String s3=rs.getString("status");
String s6=rs.getString("paytype");

if(s6.equals("-"))
total_exp=total_exp+s2;
else
total_inc=total_inc+s2;


if(s3.equals("n"))
s4="cash";
else
s4="check";sd[row][0]=s1;  sd[row][1]=""+s2; 
sd[row][2]=s4;
row++;
}

sd[0][0]="  TOTAL EXPENDITURE :";
sd[0][1]=""+total_exp;
sd[0][2]="   -";

if(row==0)
{
add(p);
p.removeAll();
p.repaint();
p.revalidate();
l5.setVisible(true);
flag=0;
p.setVisible(false);
p.add(l5);
add(p);


}
else
{
l5.setVisible(false);
p.removeAll();
flag=1;

l5.setVisible(false);
add(p);
}




rs.close();
st.close();
co.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }
if(flag==1)
{
final Object data[][]=sd;
t=new JTable(data,head);

int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;

t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 






DefaultTableModel model = new DefaultTableModel(data,head);
    //Setting the sadding in rows
    JTable t= new JTable(model){
      public Component prepareRenderer
                  (TableCellRenderer renderer,int Index_row, int Index_col) {
        Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
        //even index, selected or not selected
		//if(Index_row==0)
		 //comp.setBackground(Color.red);
		 if(isCellSelected(Index_row, Index_col))
		 {
		 comp.setBackground(Color.green);
		 }
		 else if(Index_row==0)
		 {
		 comp.setBackground(Color.blue);
		 comp.setForeground(Color.white);
		 }
		 
       else if (Index_row % 2 == 0)   {
	   comp.setForeground(Color.black);
          comp.setBackground(Color.lightGray);
        } 
        else {
		comp.setForeground(Color.black);
          comp.setBackground(Color.white);
        }
        return comp;
      }
    };
    JTableHeader header1 = t.getTableHeader();
    header1.setBackground(Color.yellow);
	header1.setForeground(Color.red);

//if (DEBUG) {
//t.addMouseListener(new MouseAdapter() {
//public void mouseClicked(MouseEvent e) {
//printDebugData(t);
//}
//});
//}
t.setFillsViewportHeight(true);
t.setRowHeight(20);
JScrollPane jsp=new JScrollPane(t);
//jsp.setBounds(20,30,370,180);
jsp.setBounds(0,0,400,200);
p.add(jsp);

t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


TableColumn column = null;


int sss;
for (int i = 0; i < head.length; i++) 
{
    column = t.getColumnModel().getColumn(i);

   if(i==0)
   sss=210;
   else
        sss=92;
    column.setPreferredWidth(sss); 
}


t.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        if (ALLOW_ROW_SELECTION) 
        { 
            ListSelectionModel rowSM = t.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() 
            {
                public void valueChanged(ListSelectionEvent e) {

                    if (e.getValueIsAdjusting()) return;

                    ListSelectionModel lsm = (ListSelectionModel)e.getSource();
                    if (lsm.isSelectionEmpty() || lsm==null) {

                    } else 
                    {

                        int selectedRow = lsm.getMinSelectionIndex();

                    
                    indexno=0;
                    dead_stock_no="";new_itm_na="";
                    indexno=Float.parseFloat(data[selectedRow][1].toString());
                    dead_stock_no=data[selectedRow][2].toString();
					new_itm_na=data[selectedRow][0].toString();
                    
					 change.setVisible(true);
					 change1.setVisible(true);
					 newlabel.setVisible(true);
			                          
                    }
                }
            });
        } else {
            t.setRowSelectionAllowed(false);
        }

        if (DEBUG) {
            t.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    //printDebugData(t);
                }
            });
        }







}
}

private void printDebugData(JTable table) {
int numRows = table.getRowCount();
int numCols = table.getColumnCount();
javax.swing.table.TableModel model = table.getModel();


for (int i=0; i < numRows; i++) {

for (int j=0; j < numCols; j++) {
System.out.print(" " + model.getValueAt(i, j));
}

}

}






public void changeDailyReport(String newchange)
{
float newchange1=Float.parseFloat(newchange);


try
{
int it_id=0;
String sta="";
float ov=0,nv=0,tv=0;
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
co=DriverManager.getConnection("jdbc:odbc:budget");
st=co.createStatement();

st.executeUpdate("update daily_report set sub_value="+newchange1+" where user_id="+userid+"  and sub_name='"+new_itm_na+"' and sub_value="+indexno+" and dt=#"+s_date+"#");

rs=st.executeQuery("select item_id,status from Categories where item_id =(select distinct(item_id) from Subcategry where subitem_name='"+new_itm_na+"' and user_id="+userid+" and dt='"+dt11+"')");
while(rs.next())
{
it_id=rs.getInt("item_id");
sta=rs.getString("status");
}
ov=newchange1-indexno;

st.executeUpdate("update Subcategry set s_actual=s_actual+"+ov+" where dt='"+dt11+"' and subitem_name='"+new_itm_na+"' and user_id="+userid+" and item_id="+it_id+"");
st.executeUpdate("update Categories set actual=actual+"+ov+" where dt='"+dt11+"' and user_id="+userid+" and item_id="+it_id+"");
if(dead_stock_no.equals("cash"))
{
if(sta.equals("-"))
ov=-ov;
st.executeUpdate("update user_detail set cash=cash+"+ov+" where ID="+userid+"");
}
else
{


String acno="";
rs=st.executeQuery("select status from Daily_report where user_id="+userid+" and sub_name='"+new_itm_na+"' and sub_value="+newchange1+" and dt=#"+s_date+"#");
while(rs.next())
{
acno=rs.getString("status");
}
StringTokenizer st1=new StringTokenizer(acno,"-");
String ac[]=new String[2];
int iii=0;
while(st1.hasMoreTokens())
{
ac[iii++]=st1.nextToken();

}
if(sta.equals("-"))
ov=-ov;
st.executeUpdate("update Bank set current=current+"+ov+" where user_id="+userid+" and ac_number='"+ac[0]+"'");
}
p.removeAll();
//p.repaint();
//p.revalidate();
add(p);
report();

change.setVisible(false);
change1.setVisible(false);
newlabel.setVisible(false);
st.close();
co.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }
}
}




