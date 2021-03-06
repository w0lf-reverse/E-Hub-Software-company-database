import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.border.*;

public class programmer implements ActionListener {

	public Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	JFrame frame2;
	JPanel title;
	JLabel header;
    JButton b,b2;

	
	public void readDb() throws Exception {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/ehub","root","darkside");
			statement=connect.createStatement();
			System.out.println("heyyyyyy");
			
		} catch(Exception e) {
			throw e;
		}
	}
	
	@Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Go Back");
        frame2.dispose();
        new leader();         
    }
	
	public void add_bttn(JButton b,int x, int y,String S,int id) {
    	b=new JButton();
        b.setBounds(x,y,150, 50);  
        b.setVisible(true);
        b.setText(S);
        if (id ==5)
            b.addActionListener(this);
        frame2.add(b);
    }
	
	public void disp_table() {
		
		frame2=new JFrame();
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setSize(dim.width,dim.height);
		
		title = new JPanel();
		title.setLayout(new GridLayout(1,2));
		title.setPreferredSize(new Dimension(dim.width, dim.height / 6));
		title.setBackground(new Color(0, 0, 0));
		title.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		header=new JLabel("                          Programmer Info");
		header.setForeground(new Color(255, 192, 203));
		header.setFont(new Font("TimesRoman", Font.BOLD, 72));
		title.add(header);
		
		frame2.add(title, BorderLayout.NORTH);
		
        add_bttn(b, 900, 600, "Go Back",5);

				
		DefaultTableModel model=new DefaultTableModel();
		model.addColumn("prog_id");
		model.addColumn("doj");
		model.addColumn("qualification");
		model.addColumn("experience");
		model.addColumn("salary");
		model.addColumn("specialization");
		JTable table=new JTable(model);
		
		table.setRowHeight(40);
		table.setIntercellSpacing(new Dimension(10,10));
		table.setBackground(new Color(23,23,32));
		table.setForeground(new Color(236, 236, 236));
		table.setGridColor(new Color(34,34,200));
		table.setFont(new Font("Courier", Font.BOLD, 16));
		
		model.addRow(new Object[] {"Prog_id","Doj","Qualification","Experience","Salary","Specialization"});
		String prog_id,qual,exp,sal,specs,date;
		Date dateObj;
		
		try {
			resultSet=statement.executeQuery("SELECT * FROM programmer");
			while(resultSet.next()) {
				prog_id=resultSet.getString("programmer_id");
				dateObj = resultSet.getDate("doj");
				date = dateObj.toString();
				qual=resultSet.getString("qualification");
				exp=resultSet.getString("experience");
				sal=Integer.toString(resultSet.getInt("salary"));
				specs=resultSet.getString("specialization");
				model.addRow(new Object[] {prog_id,date,qual,exp,sal,specs});
			}
			
			frame2.add(table);
			frame2.setVisible(true);

		} catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
		}
	}
	
	
	public static void main(String[] args) {
         programmer p = new programmer();  
        
         try {
				p.readDb();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			p.disp_table();
         
    }
	
}