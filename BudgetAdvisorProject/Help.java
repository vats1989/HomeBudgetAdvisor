import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import java.io.*;
import javax.swing.border.*;
import javax.swing.BorderFactory.*;
import java.util.*;

class Help extends JFrame implements ActionListener 
{
JLabel l1,l2,l3,l4,l5,l6;
JPanel p1;
TextArea ta1;
JButton b1;
Font f;
int userid,hmenu;

Help(int userid,int hmenu)
{
setLayout(null);
setTitle("User Help");
//setBounds(250,120,650,500);
this.userid=userid;
f=new Font("Arial",Font.BOLD,25);
l1=new JLabel("Overview of Budget Advisor");
this.hmenu=hmenu;
String s="# Budget Advisor provides an easy way to keep track of your monthly budget."+
"\n\n# Budget Advisor allows you to create a budget for the entire year to cover regular monthly expenses as well as items which are only applicable to particular months."+
"\n\n# You can also enter your income each month.\n\n# The budget is split into categories."+
"\n\n# A budget category can be either an income category or an expense category."+
"\n\n# Each category is made up of budget items.\n\n# You can create your own categories and items."+
"\n\n# You can then enter your estimated budget,as well as your \n   actual costs for each month as they occur."+
"\n\n# This allows you to track how your budgeted costs compare to the actual costs."+
"\n\n# You can compare in total, by Category, or by budget item."+
"\n\n# There is extensive graphical and reporting analysis of your budget data to help you analyse how effective your budgeting is,and to help you adjust your budgets for the future accordingly.";
ta1=new TextArea(s,30,50,1);
l2=new JLabel("Mihir Shah");
l3=new JLabel("Pratik Prajapati");
l4=new JLabel("Vipul Makwana");
l5=new JLabel("Vatsal Sevak");
p1=new JPanel();
b1=new JButton("Next");
p1.setLayout(null);
p1.setBackground(Color.white);
String s1=("Created By");
p1.setBorder(new TitledBorder(new LineBorder(Color.black,2),""+s1));
p1.setVisible(true);
ta1.setEditable(false);
ta1.setBackground(Color.white);
ta1.setForeground(Color.blue);

p1.setBounds(460,70,180,200);
l1.setBounds(10,5,400,50);
ta1.setBounds(10,70,450,390);
l2.setBounds(20,50,150,20);
l3.setBounds(20,80,150,20);
l4.setBounds(20,110,150,20);
l5.setBounds(20,140,150,20);
b1.setBounds(510,400,80,30);

l1.setFont(f);

f=new Font("Arial",Font.BOLD,15);
ta1.setFont(f);l2.setFont(f);l3.setFont(f);
l4.setFont(f);l5.setFont(f);add(l1);add(ta1);
add(p1);add(b1);p1.add(l2);p1.add(l3);
p1.add(l4);p1.add(l5);
b1.addActionListener(this);
}
void budget_details()
{
ta1.setText("");
l1.setText("Details of BudgerAdvisor");
String st="# The first step when running Budget Advisor is to create a new budget. When you first run Budget Advisor you will see a Login screen for Login into account.If you are new user then click on 'new user' and enter the required details.If you are current user then simply enter your username and password and submit it."+
"\n\n # If you are new user then Bankinformation screen will open. Enter required details and then click on submit. This screen can be skipped but the cash information is mandatory."+
"\n\n # On the main program window you will see buttons for each month at the top of the screen. You can choose which month you're working on by selecting the appropriate button."+
"\n\n # On the left hand side of the screen you will see a list of budget categories.A budget category contains various budget items.Each category has a name, and a total next to it for budgeted and actual.The total is the sum of all items in that category.A category can be either an income or an expense.Income categories have a black plus next to them.Expense are shown by a red minus."+
"\n\n # On the right hand of the screen there is a list of budget items.When you select a budget category the item in that category are displayed.Try clicking on different categories to see the items in each."+
"\n\n # At the bottom of the screen are buttons for working with categories and items. You can add, edit or delete both categories and items.When you delete a category all items in the category will be removed."+
"\n\n # When you look at a budget item you will see that there are three columns next to it.The most important are \"Budget\" and \"Actual\".The budget column shows how much money you have budgeted for this item in the current month.You should try and estimate how much you will spend on this item for the month and enter it in advance.At the end of the month you should know how much you actually spent and you can fill this in the actual column.If you're budgeting well then the budgeted amount and the actual amount should be almost the same.You can use the reports and graphs to see how well you're budgeting."+
"\n\n # Try entering some values into the budgeted and actual column for some of the budget items.You will see that the total budgeted and actual amounts of all items in a category is shown next to the category name.The total of all categories is shown below the list of categories."+
"\n\n # The Yearly Budget Report allows you to see a report of your budget for the entire year. Click  on the toolbar to view budget reports. select  labeled \"Yearly Report\"  to view the Yearly Budget Report."+
"\n\n # The Monthly Budget Report provides you with a detailed breakdown of your budget for a given month.Click  on the toolbar to view budget reports. select labeled \"Monthly Report\" to view the Monthly Budget Report."+
"\n\n # The Daily Budget Report provides you with a detailed breakdown of your budget for a given range of dates.Click  on the toolbar to view budget reports. select labeled \"daily Report\" to view the daily Budget Report."+
"\n\n # Graph report shows a graphical breakdown by year of a single category."+
"\n\n # To view this graph first click  on the graph button on toolbar."+
"\n\n # Categories : The yearly graph shows a breakdown of a category for entered year. This allows you to easily see how that category budget and actual costs changed over the year in each month.";

ta1.setText(st);

}
public void actionPerformed(ActionEvent ae)
{
String ss=ae.getActionCommand();
if(ss.equals("Next") && hmenu==0)
{
budget_details();
b1.setText("Ok");

}
 if(ss.equals("Next") && hmenu==1)
{
budget_details();
b1.setText("Exit");
}
if(ss.equals("Ok"))
{

HomeBudget b=new HomeBudget("budget",userid);
b.setVisible(true);
setVisible(false);
}
if(ss.equals("Exit"))
{
setVisible(false);
}

}
}

