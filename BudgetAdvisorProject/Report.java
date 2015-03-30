import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*;
import java.sql.*;
import javax.swing.JTable.*;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;
import javax.swing.table.*;
import java.util.*;
import java.text.ParseException;
import java.text.DateFormat;


public class Report extends JFrame implements ActionListener,ItemListener
{
String mon[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
Connection co,con;
Statement st,st1,st2;
ResultSet rs,rs1;
Calendar cal;
JPanel p1,p2,p3;
JLabel l1,l2,l3,l4,l5;
JRadioButton j1,j2,j3,j4;
Font f1,f2;
String date,dat,dat1,dat11,dat2,dat3;
int userid;
int flag=1,flg=0,mm,yy;
int a[]=new int[2];
String user_name[]=new String[10];
String s1,s2,s3,s4,select,itemss;
JButton view;
JComboBox jcb;
JLabel years,total_bud,total_act;
JTextField jtf1,jtf2;

public Report(int userid)
{
setLayout(null);
setResizable(false);
setTitle("Budget Report");
this.userid=userid;
Toolkit tk = Toolkit.getDefaultToolkit();  
int x = ((int) tk.getScreenSize().getWidth());  
int y = ((int) tk.getScreenSize().getHeight());  

setSize(x,y);
 cal=Calendar.getInstance();
mm=cal.get(Calendar.MONTH);
yy=cal.get(Calendar.YEAR);
mm=mm+1;
date=""+mm+"-"+yy;


p1=new JPanel();
p1.setLayout(null);
p1.setBounds(0,0,x,y/12);
p1.setBackground(Color.black);

l1=new JLabel("BUDGET REPORT");
Font f1=new Font("Arial",Font.BOLD+Font.ITALIC,25);
l1.setBounds(400,20,500,30);
l1.setForeground(Color.red);
l1.setFont(f1);
p1.add(l1);
add(p1);

jcb=new JComboBox();

p2=new JPanel();
p2.setLayout(null);
p2.setBounds(0,y/12,x/5,11*(y/12));
p2.setBackground(Color.green);
add(p2);


p3=new JPanel();
p3.setLayout(null);
p3.setBounds(x/5,y/12,4*(x/5),11*(y/12));
p3.setBackground(Color.white);

add(p3);

designP2();


}

void designP2()
{
f2=new Font("Impact",Font.BOLD,15);
j1=new JRadioButton("Yearly Report");
j2=new JRadioButton("Monthly Report");
j3=new JRadioButton("Daily Report");

j1.setFont(f2); j2.setFont(f2); j3.setFont(f2);

j1.setBounds(20,30,180,20);
j2.setBounds(20,60,180,20);
j3.setBounds(20,90,180,20);


ButtonGroup bg=new ButtonGroup();

bg.add(j1);
bg.add(j2);
bg.add(j3);


j1.addActionListener(this);
j2.addActionListener(this);
j3.addActionListener(this);



l2=new JLabel("Select Year :");
l2.setBounds(40,200,120,30);
l2.setForeground(Color.blue);
l2.setFont(f2);
p2.add(l2);
l2.setVisible(false);

l3=new JLabel("Select Month :");
l3.setBounds(40,200,120,30);
l3.setForeground(Color.blue);
l3.setFont(f2);
p2.add(l3);
l3.setVisible(false);


l4=new JLabel("From Date :");
l4.setBounds(40,200,150,30);
l4.setForeground(Color.blue);
l4.setFont(f2);
p2.add(l4);
l4.setVisible(false);

l5=new JLabel("To Date :");
l5.setBounds(40,300,120,30);
l5.setForeground(Color.blue);
l5.setFont(f2);
p2.add(l5);
l5.setVisible(false);



view=new JButton("View");
view.setBounds(40,420,80,30);
p2.add(view);
view.addActionListener(this);

p2.add(j1); p2.add(j2);
p2.add(j3);

}


public void itemStateChanged(ItemEvent ie)
{
Object source=ie.getSource();
if(source==jcb)
{
itemss=(String)ie.getItem();
}
}





public void actionPerformed(ActionEvent ae)
{
l2.setVisible(false); l3.setVisible(false); l4.setVisible(false); l5.setVisible(false);
select=ae.getActionCommand();

if(select.equals("Yearly Report"))
{
flg=1;
yearly();
}
else if(select.equals("Monthly Report"))
{
flg=2;
monthly();
}
else if(select.equals("Daily Report"))
{
flg=3;
//categoryVice();
daily();
}

if(select.equals("View"))
{
p3.removeAll();
p3.repaint();
p3.revalidate();
MonthlyReport.static_tot_sal=MonthlyReport.static_tot_exp=0;
if(flg==1)
{
years=new JLabel("YEAR :  "+itemss);
years.setBounds(50,5,200,30);
Font g=new Font("Arial",Font.BOLD+Font.ITALIC,25);
years.setFont(g);
years.setForeground(Color.blue);
p3.add(years);
years.setVisible(false);
yearlyReport(itemss);
}
else if(flg==2)
{

int i=0;
String dt="";



String ab[]=new String[2];
int users[]=new int[10];
JTabbedPane jt=new JTabbedPane();

//jt.setBounds((x/5)+20,(y/12)+20,(4*(x/5))-50,(11*(y/12))-50);
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();

int row=0,col=0,po=2,k=0,q=0,tqs=1,z=0,z1=0;


StringTokenizer vs=new StringTokenizer(itemss,"-");

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

  i=0;
rs=st.executeQuery("select * from user_detail");
while(rs.next())
{
users[i]=rs.getInt("ID");
user_name[i]=rs.getString("user_name");

i++;
}

rs.close();
st.close();
con.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }
for(int z=0;z<i;z++)
{
jt.add(user_name[z],new MonthlyReport(users[z],dt));
}


JLabel total_sal=new JLabel("Total Income :   "+MonthlyReport.static_tot_sal);
total_sal.setBounds(600,5,200,15);
total_sal.setForeground(Color.blue);
p3.add(total_sal);

JLabel total_exp=new JLabel("Total Expenditure :   "+MonthlyReport.static_tot_exp);
total_exp.setBounds(600,25,200,15);
total_exp.setForeground(Color.blue);
p3.add(total_exp);

jt.setBounds(5,30,800,625);
jt.setBackground(Color.white);
p3.add(jt);

}
else if(flg==3)
{
int valid=0;
String fd=jtf1.getText();
String td=jtf2.getText();
StringTokenizer vs=new StringTokenizer(fd,"-");
String sa[]=new String[3];
String sa1[]=new String[3];
while(vs.hasMoreTokens())
{
sa[valid++]=vs.nextToken();
}
valid=0;
vs=new StringTokenizer(td,"-");
while(vs.hasMoreTokens())
{
sa1[valid++]=vs.nextToken();
}
String fd1=""+sa[0]+"/"+sa[1]+"/"+sa[2];
String td1=""+sa1[0]+"/"+sa1[1]+"/"+sa1[2];

try
{
DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
df.setLenient(false);

java.util.Date dt2=df.parse(fd1);
dt2=df.parse(td1);

dailyReport(fd,td);
jtf1.setVisible(false);
jtf2.setVisible(false);

}
catch(Exception e)
{
jtf1.setVisible(false);
jtf2.setVisible(false);
JLabel kb=new JLabel("Invalid Date");
kb.setForeground(Color.red);
Font g1=new Font("Arial",Font.BOLD,20);
kb.setFont(g1);
kb.setBounds(100,200,300,20);
p3.add(kb);
}

}
}
}


void yearly()
{
JTable jt;

p2.remove(jcb);
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

l2.setVisible(true);
jcb=new JComboBox();
jcb.addItem("Year");
for(int k=yy;k>=a[1];k--)
jcb.addItem(""+k);
jcb.setBounds(40,250,90,25);
jcb.addItemListener(this);
p2.add(jcb);



rs.close();
st.close();
co.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }

}

void monthly()
{
p2.remove(jcb);
jcb=new JComboBox();
l3.setVisible(true);
jcb.addItem("Month");
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
rs=st.executeQuery("select distinct(dt) from Categories where user_id="+userid+" order by dt desc");
while(rs.next())
{
String s=rs.getString("dt");
StringTokenizer vs=new StringTokenizer(s,"-");
int i=0;

while(vs.hasMoreTokens())
{
a[i]=Integer.parseInt(vs.nextToken());
i++;
}
jcb.addItem((mon[a[0]-1])+"-"+a[1]);
}
rs.close();
st.close();
co.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }

jcb.setBounds(40,250,90,25);
jcb.addItemListener(this);
p2.add(jcb);
}

void daily()
{
l4.setVisible(true);
l5.setVisible(true);
p2.remove(jcb);
jtf1=new JTextField("mm/dd/yyyy");
jtf1.setBounds(40,300,150,30);
add(jtf1);
jtf2=new JTextField("mm/dd/yyyy");
jtf2.setBounds(40,400,150,30);
add(jtf2);
jtf1.addMouseListener(new MouseAdapter()
{
public void mouseClicked(MouseEvent me)
{
if((jtf1.getText()).equals("mm/dd/yyyy"))
jtf1.setText("");
}
}
);
jtf2.addMouseListener(new MouseAdapter()
{
public void mouseClicked(MouseEvent me)
{
if((jtf2.getText()).equals("mm/dd/yyyy"))
jtf2.setText("");
}
}
);

}



public void dailyReport(String fd,String td)
{
String item_name="",dt="";
float item_value=0;
int rw=0;
String sd[][]=new String[20][3];
String head[]={"Date","Item Name","Item Value"};

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
rs=st.executeQuery("select count(*) from Daily_report where user_id="+userid+" and dt between #"+fd+"# and #"+td+"#");

while(rs.next())
{
rw=rs.getInt(1);
}

sd=new String[rw][3];
rs=st.executeQuery("select * from Daily_report where user_id="+userid+" and dt between #"+fd+"# and #"+td+"#");
int i=0;
while(rs.next())
{

item_name=rs.getString("sub_name");
item_value=rs.getFloat("sub_value");
dt=rs.getString("dt");
sd[i][0]=dt;
sd[i][1]=item_name;
sd[i++][2]=""+item_value;
}
rs.close();
st.close();
con.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }

final Object data[][]=sd;
JTable t=new JTable(data,head);

int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;


t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
TableColumn column = null;



DefaultTableModel model = new DefaultTableModel(data,head);

     t= new JTable(model){

      public Component prepareRenderer
                  (TableCellRenderer renderer,int Index_row, int Index_col) {
        Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		if(isCellSelected(Index_row, Index_col))
		 {
		 comp.setBackground(Color.red);
		 comp.setForeground(Color.white);
		 }
		 
        else if (Index_row % 2 == 0 ) {
          comp.setBackground(Color.lightGray);
		  comp.setForeground(Color.black);
		  
        } 
        else {
          comp.setBackground(Color.white);
		  comp.setForeground(Color.black);
        }
        return comp;
      }
    };
    JTableHeader header1 = t.getTableHeader();
    header1.setBackground(Color.pink);
	header1.setForeground(Color.black);
	Font f=new Font("Arial",Font.BOLD,18);
	header1.setFont(f);
t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
t.setFillsViewportHeight(true);
t.setRowHeight(22);
Font fg=new Font("Arial",Font.BOLD,12);
t.setFont(fg);
JScrollPane jsp=new JScrollPane(t);
jsp.setBounds(100,180,500,300);
p3.add(jsp);

JLabel to=new JLabel("From :"+fd+"                     To :"+td);
Font font=new Font("Arial",Font.BOLD,20);
to.setFont(font);
to.setForeground(Color.blue);
to.setBounds(150,50,600,50);
p3.add(to);




int sss;
for (int i = 0; i < head.length; i++) 
{
    column = t.getColumnModel().getColumn(i);

   if(i==2)
   {
   sss=100;

   }
   else if(i==0)
   sss=160;
   else
   sss=220;
   column.setPreferredWidth(sss); 
 
}
}


public void yearlyReport(String aa)
{
JLabel lb;

String sql;
String c_name;
String s_name,y_status;
float s_bud=0;
float s_act=0;
float bud=0,act=0;
float y_bud_in=0,y_bud_ex=0,y_act_in=0,y_act_ex=0;
Calendar cal;
String date="";

String sd[][]=new String[1][26];
String head[]={"Category","Item","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual","Budget","Actual"};
try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
st1=con.createStatement();
int row=0,col=0,po=2,k=0,q=0,tqs=1,z=0,z1=0;
if(a[1]==Integer.parseInt(aa))
{

tqs=a[0];
po=2*(a[0]-1)+2;
}
else
tqs=1;
int kk=0,kk2=0;
cal=Calendar.getInstance();
mm=cal.get(Calendar.MONTH);
yy=cal.get(Calendar.YEAR);
mm=mm+1;
date=""+mm+"-"+yy;


rs=st.executeQuery("select count(*),max(item_id) from Subcategry where user_id="+userid+" and dt='"+date+"'");
while(rs.next())
{
kk=rs.getInt(1);
kk2=rs.getInt(2);
}
sd=new String[kk+(2*kk2)+5][26];

for(int i=1;i<=12;i++)
{
sd[0][(z+=2)]="          "+mon[z1++];
}
int ff=0;
for(int i=tqs;i<=12;i++)
{
row=2;
String dt=""+i+"-"+aa;

rs=st.executeQuery("select item_id,item_name,budget,actual,status from Categories where user_id="+userid+" and dt='"+dt+"'");

while(rs.next())
{
int id=rs.getInt("item_id");
bud=rs.getFloat("budget");
act=rs.getFloat("actual");
y_status=rs.getString("status");
String itmname=rs.getString("item_name");
if(ff==0)
{
sd[row][0]=itmname;
}
rs1=st1.executeQuery("select subitem_name,s_budget,s_actual from Subcategry where item_id="+id+" and user_id="+userid+" and dt='"+dt+"'");
while(rs1.next())
{
k=po;
s_name=rs1.getString("subitem_name");
s_bud=rs1.getFloat("s_budget");
s_act=rs1.getFloat("s_actual");
if(ff==0)
sd[row][1]=s_name; 
sd[row][k++]=""+s_bud; sd[row][k]=""+s_act;
row++;

}


sd[row][k-1]=""+bud;
if(y_status.equals("+"))
{
y_bud_in=y_bud_in+bud;
y_act_in=y_act_in+act;
}
else
{
y_bud_ex=y_bud_ex+bud;
y_act_ex=y_act_ex+act;
}
sd[row][k]=""+act;
if(ff==0)
sd[row][0]="Total :";
row=row+2;

}

po=k+1;
ff=1;
}
rs1.close();
st1.close();
rs.close();
st.close();
con.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }

final Object data[][]=sd;
JTable t=new JTable(data,head);

int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;


t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
TableColumn column = null;



DefaultTableModel model = new DefaultTableModel(data,head);

     t= new JTable(model){

      public Component prepareRenderer
                  (TableCellRenderer renderer,int Index_row, int Index_col) {
        Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		if(isCellSelected(Index_row, Index_col))
		 {
		 comp.setBackground(Color.red);
		 comp.setForeground(Color.white);
		 }
		 else if(Index_row==0)
		 {
		  comp.setBackground(Color.pink);
		  comp.setForeground(Color.black);
		  	Font f=new Font("Arial",Font.BOLD,14);
			comp.setFont(f);
		  }
        else if (Index_row % 2 == 0 ) {
          comp.setBackground(Color.lightGray);
		  comp.setForeground(Color.black);
		  
        } 
        else {
          comp.setBackground(Color.white);
		  comp.setForeground(Color.black);
        }
        return comp;
      }
    };
    JTableHeader header1 = t.getTableHeader();
    header1.setBackground(Color.pink);
	header1.setForeground(Color.black);
	Font f=new Font("Arial",Font.BOLD,18);
	header1.setFont(f);
t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
t.setFillsViewportHeight(true);
t.setRowHeight(22);
Font fg=new Font("Arial",Font.BOLD,12);
t.setFont(fg);
JScrollPane jsp=new JScrollPane(t);
jsp.setBounds(10,60,770,580);
p3.add(jsp);

total_bud=new JLabel("Total Salary  :     Budget:   "+y_bud_in+"     Actual:   "+y_act_in);
total_bud.setBounds(350,5,500,20);
Font g1=new Font("Arial",Font.BOLD,15);
total_bud.setFont(g1);
total_bud.setForeground(Color.blue);
p3.add(total_bud);

total_act=new JLabel("Total Exp.    :     Budget:   "+y_bud_ex+"     Actual:   "+y_act_ex);
total_act.setBounds(350,25,500,20);

total_act.setFont(g1);
total_act.setForeground(Color.blue);
p3.add(total_act);
years.setVisible(true);



int sss;
for (int i = 0; i < head.length; i++) 
{
    column = t.getColumnModel().getColumn(i);

   if(i>=2)
   {
   sss=80;

   }
   else if(i==0)
   sss=120;
   else
   sss=160;
   column.setPreferredWidth(sss); 
 
}
}
}



class MonthlyReport extends JPanel
{
static float static_tot_sal,static_tot_exp;
String mon[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
Connection co,con;
Statement st,st1,st2;
ResultSet rs,rs1;
JLabel lb;
Font f1,f2;
String date,dat,dat1,dat11,dat2,dat3;
int userid;
int flag=1,flg=0,mm,yy;
int a[]=new int[2];
String user_name[]=new String[10];
String s1,s2,s3,s4,select,itemss;
JButton view;
JComboBox jcb;
JLabel years,total_bud,total_act;
String sql;
String ab[]=new String[2];
int users[]=new int[10];
String c_name;
String s_name,y_status;
float s_bud=0;
float s_act=0;
float bud=0,act=0;
float y_bud_in=0,y_bud_ex=0,y_act_in=0,y_act_ex=0;
int kk=0,kk2=0;
String sd[][]=new String[1][4];
String head[]={"Category","Item","Budget","Actual"};
MonthlyReport(int userid,String aa)
{

setLayout(null);
years=new JLabel("MONTH :  "+aa);
years.setBounds(50,5,200,30);
Font g=new Font("Arial",Font.BOLD+Font.ITALIC,20);
years.setFont(g);
years.setForeground(Color.blue);
add(years);
years.setVisible(false);

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
st1=con.createStatement();
int row=0,col=0,po=2,k=0,q=0,tqs=1,z=0,z1=0;
rs=st.executeQuery("select count(*),max(item_id) from Subcategry where user_id="+userid+" and dt='"+aa+"'");
while(rs.next())
{
kk=rs.getInt(1);
kk2=rs.getInt(2);
}
sd=new String[kk+(2*kk2)+5][4];
row=3;
rs=st.executeQuery("select item_id,item_name,budget,actual,status from Categories where user_id="+userid+" and dt='"+aa+"'");
while(rs.next())
{
int id=rs.getInt("item_id");
bud=rs.getFloat("budget");
act=rs.getFloat("actual");
y_status=rs.getString("status");
String itmname=rs.getString("item_name");
sd[row][0]=itmname;
rs1=st1.executeQuery("select subitem_name,s_budget,s_actual from Subcategry where item_id="+id+" and user_id="+userid+" and dt='"+aa+"'");

while(rs1.next())
{

k=po;
s_name=rs1.getString("subitem_name");
s_bud=rs1.getFloat("s_budget");
s_act=rs1.getFloat("s_actual");
sd[row][1]=s_name; sd[row][k++]=""+s_bud; sd[row][k]=""+s_act;
row++;
}
//row=row+1;
sd[row][k-1]=""+bud;
if(y_status.equals("+"))
{
y_bud_in=y_bud_in+bud;
y_act_in=y_act_in+act;

}
else
{
y_bud_ex=y_bud_ex+bud;
y_act_ex=y_act_ex+act;
}
sd[row][k]=""+act;

sd[row][0]="Total :";
row=row+2;

}


rs1.close();
st1.close();
rs.close();
st.close();
con.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }

final Object data[][]=sd;
JTable t=new JTable(data,head);

int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;


t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
TableColumn column = null;



DefaultTableModel model = new DefaultTableModel(data,head);

     t= new JTable(model){

      public Component prepareRenderer
                  (TableCellRenderer renderer,int Index_row, int Index_col) {
        Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		if(isCellSelected(Index_row, Index_col))
		 {
		 comp.setBackground(Color.red);
		 comp.setForeground(Color.white);
		 }
		 
        else if (Index_row % 2 == 0 ) {
          comp.setBackground(Color.lightGray);
		  comp.setForeground(Color.black);
		  
        } 
        else {
          comp.setBackground(Color.white);
		  comp.setForeground(Color.black);
        }
        return comp;
      }
    };
    JTableHeader header1 = t.getTableHeader();
    header1.setBackground(Color.pink);
	header1.setForeground(Color.black);
	Font f=new Font("Arial",Font.BOLD,18);
	header1.setFont(f);
t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
t.setFillsViewportHeight(true);
t.setRowHeight(22);
Font fg=new Font("Arial",Font.BOLD,12);
t.setFont(fg);
JScrollPane jsp=new JScrollPane(t);
jsp.setBounds(100,70,540,470);
add(jsp);

total_bud=new JLabel("Total Salary  :     Budget:   "+y_bud_in+"     Actual:   "+y_act_in);
total_bud.setBounds(350,5,500,20);
Font g1=new Font("Arial",Font.BOLD,15);
total_bud.setFont(g1);
total_bud.setForeground(Color.blue);
add(total_bud);

total_act=new JLabel("Total Exp.    :     Budget:   "+y_bud_ex+"     Actual:   "+y_act_ex);
total_act.setBounds(350,25,500,20);
static_tot_sal=static_tot_sal+y_act_in;
static_tot_exp=static_tot_exp+y_act_ex;

total_act.setFont(g1);
total_act.setForeground(Color.blue);
add(total_act);
years.setVisible(true);



int sss;
for (int i = 0; i < head.length; i++) 
{
    column = t.getColumnModel().getColumn(i);

   if(i>=2)
   {
   sss=130;

   }
   else if(i==0)
   sss=120;
   else
   sss=160;
   column.setPreferredWidth(sss); 
 
}
}


}


/*

class MonthlyTotal extends JPanel
{

String mon[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
Connection co,con;
Statement st,st1,st2;
ResultSet rs,rs1;
JLabel lb;
Font f1,f2;
String date,dat,dat1,dat11,dat2,dat3;
int userid;
int flag=1,flg=0,mm,yy;
int a[]=new int[2];
String user_name[]=new String[10];
String s1,s2,s3,s4,select,itemss;
JButton view;
JComboBox jcb;
JLabel years,total_bud,total_act;
String sql;
String ab[]=new String[2];
int users[]=new int[10];
String c_name;
String s_name,y_status;
float s_bud=0;
float s_act=0;
float bud=0,act=0;
float y_bud_in=0,y_bud_ex=0,y_act_in=0,y_act_ex=0;

String sd[][]=new String[150][4];
String head[]={"Category","Item","Budget","Actual"};

MonthlyTotal(String aa)
{
setLayout(null);
years=new JLabel("MONTH :  "+aa);
years.setBounds(50,5,200,30);
Font g=new Font("Arial",Font.BOLD+Font.ITALIC,20);
years.setFont(g);
years.setForeground(Color.blue);
add(years);
years.setVisible(false);

try
{
Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
con=DriverManager.getConnection("jdbc:odbc:budget");
st=con.createStatement();
st1=con.createStatement();
int row=0,col=0,po=2,k=0,q=0,tqs=1,z=0,z1=0;

row=3;
rs=st.executeQuery("select item_id,item_name,budget,actual,status from Categories where dt='"+aa+"'");
while(rs.next())
{
int id=rs.getInt("item_id");
bud=rs.getFloat("budget");
act=rs.getFloat("actual");
y_status=rs.getString("status");
String itmname=rs.getString("item_name");
sd[row][0]=itmname;
rs1=st1.executeQuery("select subitem_name,s_budget,s_actual from Subcategry where item_id="+id+" and dt='"+aa+"'");
while(rs1.next())
{
k=po;
s_name=rs1.getString("subitem_name");
s_bud=rs1.getFloat("s_budget");
s_act=rs1.getFloat("s_actual");
sd[row][1]=s_name; sd[row][k++]=""+s_bud; sd[row][k]=""+s_act;
row++;
}
//row=row+1;
sd[row][k-1]=""+bud;
if(y_status.equals("+"))
{
y_bud_in=y_bud_in+bud;
y_act_in=y_act_in+act;
}
else
{
y_bud_ex=y_bud_ex+bud;
y_act_ex=y_act_ex+act;
}
sd[row][k]=""+act;

sd[row][0]="Total :";
row=row+2;

}


rs1.close();
st1.close();
rs.close();
st.close();
con.close();
}
catch(Exception e)
{ System.out.println(e.getMessage()); }

final Object data[][]=sd;
JTable t=new JTable(data,head);

int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;


t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
TableColumn column = null;



DefaultTableModel model = new DefaultTableModel(data,head);

     t= new JTable(model){

      public Component prepareRenderer
                  (TableCellRenderer renderer,int Index_row, int Index_col) {
        Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
		if(isCellSelected(Index_row, Index_col))
		 {
		 comp.setBackground(Color.red);
		 comp.setForeground(Color.white);
		 }
		 
        else if (Index_row % 2 == 0 ) {
          comp.setBackground(Color.lightGray);
		  comp.setForeground(Color.black);
		  
        } 
        else {
          comp.setBackground(Color.white);
		  comp.setForeground(Color.black);
        }
        return comp;
      }
    };
    JTableHeader header1 = t.getTableHeader();
    header1.setBackground(Color.pink);
	header1.setForeground(Color.black);
	Font f=new Font("Arial",Font.BOLD,18);
	header1.setFont(f);
t.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
t.setFillsViewportHeight(true);
t.setRowHeight(22);
Font fg=new Font("Arial",Font.BOLD,12);
t.setFont(fg);
JScrollPane jsp=new JScrollPane(t);
jsp.setBounds(100,70,540,470);
add(jsp);

total_bud=new JLabel("Total Salary  :     Budget:   "+y_bud_in+"     Actual:   "+y_act_in);
total_bud.setBounds(350,5,500,20);
Font g1=new Font("Arial",Font.BOLD,15);
total_bud.setFont(g1);
total_bud.setForeground(Color.blue);
add(total_bud);

total_act=new JLabel("Total Exp.    :     Budget:   "+y_bud_ex+"     Actual:   "+y_act_ex);
total_act.setBounds(350,25,500,20);
total_act.setFont(g1);
total_act.setForeground(Color.blue);
add(total_act);
years.setVisible(true);







int sss;
for (int i = 0; i < head.length; i++) 
{
    column = t.getColumnModel().getColumn(i);

   if(i>=2)
   {
   sss=130;

   }
   else if(i==0)
   sss=120;
   else
   sss=160;
   column.setPreferredWidth(sss); 
 
}
}


}

*/




