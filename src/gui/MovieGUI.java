package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.MaskFormatter;

import db.DBConn;
import javafx.util.Pair;

public class MovieGUI {

	private JFrame mainFrame;
	static DBConn conn = null;
	private JTextField custNameField;
	private JTextField custPassField;
	private JTextField custMailField;
	private JFormattedTextField custPhoneField;
	private Random random = new Random();

	/**
	 * Create the application.
	 */
	public MovieGUI() {
		initialize();
	}

	public void addToComboBox(JComboBox<String> box, ResultSet rs) {
		box.removeAllItems();
		try {
			while(rs.next()) {
				box.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			ErrorDialog err = new ErrorDialog("Add to ComboBox Fail",e.getMessage(),false);
			err.setVisible(true);
			e.printStackTrace();
		}
	}
	
	
	public void printToArea(JTextArea area, ResultSet rs) {
		area.setText("");
		
		try {
			
			if(rs == null) {
				area.append("Update successful\n");
				// area.append("update count is: "+updateCount);
			}else {
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();
				
				// Print Column Names
				for(int i = 1; i <= numCols; i++) {
					area.append("|"+rsmd.getColumnName(i)+"|\t");
				}
				area.append("\n");
			
				while(rs.next()) {
					// Print Data
					for(int i = 1; i <= numCols; i++) {
						String temp = rs.getString(i);
						if(temp.equals("0")) {
							temp = "Vacant";
						}
						area.append(temp+"\t");
					}
					area.append("\n");
				}
			}
			
		} catch (SQLException e1) {
			ErrorDialog err = new ErrorDialog("Print to TextArea Fail",e1.getMessage(),false);
			err.setVisible(true);
			e1.printStackTrace();
		}
	}
		
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setTitle("Movie Ticket Booking System                  -- made with ❤️ by Ujwal Kundur");
		mainFrame.setBounds(250, 25, 1024, 768);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		mainFrame.getContentPane().add(tabbedPane);
		
		
		JPanel bookTicketPanel = new JPanel();
		tabbedPane.addTab("Book a Ticket", null, bookTicketPanel, null);
		
		JLabel movieLabel1 = new JLabel("1) Choose a Movie");
		movieLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		JScrollPane moviePanel = new JScrollPane();
		
		JLabel screeningLabel1 = new JLabel("2) Choose Screening and Theatre");
		screeningLabel1.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		JPanel panel = new JPanel();
		
		JScrollPane seatingPanel = new JScrollPane();
		
		JLabel lblChooseSeating = new JLabel("3) Choose Seating");
		lblChooseSeating.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		JLabel lblEnterCustomer = new JLabel("4) Enter Customer Details");
		lblEnterCustomer.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
		
		JLabel custNamelbl = new JLabel("Customer Name");
		
		custNameField = new JTextField();
		custNamelbl.setLabelFor(custNameField);
		custNameField.setColumns(10);
		
		JLabel custPasslbl = new JLabel("Customer Password");
		
		custPassField = new JTextField();
		custPasslbl.setLabelFor(custPassField);
		custPassField.setColumns(10);
		
		JLabel custMaillbl = new JLabel("Customer Email");
		
		custMailField = new JTextField();
		custMaillbl.setLabelFor(custMailField);
		custMailField.setColumns(10);
		
		JLabel custPhonelbl = new JLabel("Customer Phone number");
		try {
			custPhoneField = new JFormattedTextField(new MaskFormatter("##########"));
			custPhonelbl.setLabelFor(custPhoneField);
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		
		JButton bookTicketButton = new JButton("Book Ticket");
		GroupLayout gl_bookTicketPanel = new GroupLayout(bookTicketPanel);
		gl_bookTicketPanel.setHorizontalGroup(
			gl_bookTicketPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_bookTicketPanel.createSequentialGroup()
					.addContainerGap(28, Short.MAX_VALUE)
					.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_bookTicketPanel.createSequentialGroup()
							.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(moviePanel, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE)
								.addComponent(movieLabel1, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblChooseSeating, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
								.addComponent(seatingPanel, GroupLayout.PREFERRED_SIZE, 364, GroupLayout.PREFERRED_SIZE))
							.addGap(64)
							.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 525, GroupLayout.PREFERRED_SIZE)
								.addComponent(screeningLabel1, GroupLayout.PREFERRED_SIZE, 374, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_bookTicketPanel.createSequentialGroup()
									.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(custPhonelbl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(custMaillbl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(custPasslbl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
										.addComponent(custNamelbl, Alignment.LEADING))
									.addGap(43)
									.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(custPhoneField)
										.addComponent(custMailField)
										.addComponent(custPassField)
										.addComponent(custNameField, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)))
								.addComponent(lblEnterCustomer, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE))
							.addGap(34))
						.addGroup(gl_bookTicketPanel.createSequentialGroup()
							.addComponent(bookTicketButton, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
							.addGap(268))))
		);
		gl_bookTicketPanel.setVerticalGroup(
			gl_bookTicketPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bookTicketPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(movieLabel1)
						.addComponent(screeningLabel1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(moviePanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblChooseSeating, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_bookTicketPanel.createSequentialGroup()
							.addGap(28)
							.addComponent(seatingPanel, GroupLayout.PREFERRED_SIZE, 258, GroupLayout.PREFERRED_SIZE)
							.addGap(18))
						.addGroup(Alignment.TRAILING, gl_bookTicketPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
							.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(custNamelbl)
								.addComponent(custNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(34)
							.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(custPasslbl)
								.addComponent(custPassField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(49)
							.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(custMaillbl)
								.addComponent(custMailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(48)
							.addGroup(gl_bookTicketPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(custPhonelbl)
								.addComponent(custPhoneField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(42)))
					.addComponent(bookTicketButton)
					.addGap(234))
				.addGroup(gl_bookTicketPanel.createSequentialGroup()
					.addGap(335)
					.addComponent(lblEnterCustomer, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(547, Short.MAX_VALUE))
		);
		
		JComboBox seatingBox = new JComboBox();
		seatingPanel.setColumnHeaderView(seatingBox);
		
		JTextArea seatingInfoArea = new JTextArea();
		seatingInfoArea.setEditable(false);
		seatingPanel.setViewportView(seatingInfoArea);
		panel.setLayout(new BorderLayout(0, 0));
		
		JComboBox screeningBox = new JComboBox();
		panel.add(screeningBox, BorderLayout.NORTH);
		
		JComboBox theatreBox = new JComboBox();
		panel.add(theatreBox, BorderLayout.SOUTH);
		
		JTextArea screeningEndTimeArea = new JTextArea();
		screeningEndTimeArea.setEditable(false);
		panel.add(screeningEndTimeArea, BorderLayout.CENTER);
		
		JComboBox movieBox = new JComboBox();
		moviePanel.setColumnHeaderView(movieBox);
		
		JTextArea movieInfoArea = new JTextArea();
		movieInfoArea.setEditable(false);
		moviePanel.setViewportView(movieInfoArea);
		bookTicketPanel.setLayout(gl_bookTicketPanel);
		
		/* Initialize First Tab when Tab is clicked */
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(e.getSource() instanceof JTabbedPane) {
					JTabbedPane pane = (JTabbedPane) e.getSource();
					if(pane.getSelectedIndex() == 0) {
						// Update Movie Box
						Pair<ResultSet, Integer> resultPair = conn.executeQuery("select movie_name from movie;");
						ResultSet rs = resultPair.getKey();
						addToComboBox(movieBox, rs);
						// Update Theater Box
						resultPair = conn.executeQuery("select theatre_name from theatre;");
						rs = resultPair.getKey();
						addToComboBox(theatreBox, rs);
					}
				}
			}
		});
		
		/* Initialize First Tab when application starts */
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				// Update Movie Box
				Pair<ResultSet, Integer> resultPair = conn.executeQuery("select movie_name from movie;");
				ResultSet rs = resultPair.getKey();
				addToComboBox(movieBox, rs);
				// Update Theater Box
				resultPair = conn.executeQuery("select theatre_name from theatre;");
				rs = resultPair.getKey();
				addToComboBox(theatreBox, rs);
			}
		});
		
		/* Display Movie Info when clicked + update screening box */
		movieBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String mname = (String) movieBox.getItemAt(movieBox.getSelectedIndex());
				String query = "SELECT * FROM movie WHERE movie_name='"+mname+"'";
				Pair<ResultSet, Integer> resultPair = conn.executeQuery(query);
				ResultSet rs = resultPair.getKey();
				printToArea(movieInfoArea, rs);
				
				// Update Screening Boxes
				query = "SELECT start_time FROM screening s JOIN screening_movie_lookup sml"
						+ " ON s.screening_id = sml.movie_id JOIN movie m"
						+ " ON m.movie_id = sml.movie_id WHERE movie_name='"
						+ mname+"'";
				resultPair = conn.executeQuery(query);
				rs = resultPair.getKey();
				addToComboBox(screeningBox, rs);
				query = "SELECT end_time FROM screening s JOIN screening_movie_lookup sml"
						+ " ON s.screening_id = sml.movie_id JOIN movie m"
						+ " ON m.movie_id = sml.movie_id WHERE movie_name='"
						+ mname+"'";
				resultPair = conn.executeQuery(query);
				rs = resultPair.getKey();
				printToArea(screeningEndTimeArea, rs);
			}
		});
		
		// Update Seating
		theatreBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String query = "SELECT seat_name FROM seats WHERE booked=false;";
				Pair<ResultSet, Integer> resultPair = conn.executeQuery(query);
				ResultSet rs = resultPair.getKey();
				addToComboBox(seatingBox, rs);
			}
		});
		
		// Update Seating Info
		seatingBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String query = "SELECT * FROM seats WHERE booked=false;";
				Pair<ResultSet, Integer> resultPair = conn.executeQuery(query);
				ResultSet rs = resultPair.getKey();
				printToArea(seatingInfoArea, rs);
			}
		});
		
		// Book the Ticket
		bookTicketButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cname = custNameField.getText().strip();
				String cpass = custPassField.getText().strip();
				String cmail = custMailField.getText().strip();
				String cphone = custPhoneField.getText().strip();
				int cid = 500 + random.nextInt(10000);
				
				String seatName = (String) seatingBox.getItemAt(seatingBox.getSelectedIndex());
				String query = "SELECT seat_id FROM seats WHERE seat_name='"+seatName+"'";
				Pair<ResultSet, Integer> resultPair = conn.executeQuery(query);
				int seat_id = 0;
				try {
					ResultSet rs = resultPair.getKey();
					rs.next();
					seat_id = rs.getInt(1);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				// Insert customer data
				query = "INSERT INTO customer(cust_id,cust_name,"
						+ "pwd,email_id) VALUES("
						+ cid+",'"+cname+"','"+cpass+"','"+cmail+"');";
				resultPair = conn.executeQuery(query);
				int upCountCust = resultPair.getValue();
				
				// Insert Phone number
				query = "INSERT INTO phone_no VALUES("+cid+",'"+cphone+"');";
				resultPair = conn.executeQuery(query);
				int upCountPhone = resultPair.getValue();
				
				if(upCountCust != 0 && upCountPhone != 0) {
					System.out.println("Ticket Booked Succesfully.");
					System.out.println("Phone inserts: "+upCountPhone);
					System.out.println("Customer inserts: "+upCountCust);
					//resultPair = conn.executeQuery("commit;");
				} else {
					//resultPair = conn.executeQuery("rollback;");
				}
				
				// Update Seats Data
				query = "UPDATE seats SET booked=true WHERE seat_id="+seat_id;
				resultPair = conn.executeQuery(query);
				int upCountSeats = resultPair.getValue();
				if(upCountSeats != 0) {
					System.out.println("Seats inserts: "+upCountSeats);
				}
				
				// Insert Ticket Data
				int tid = 500 + random.nextInt(10000);
				double price = random.nextDouble()*100;
				query = "INSERT INTO tickets VALUES("+tid+","+seat_id+",8, 8,"+price+");";
				resultPair = conn.executeQuery(query);
				int upCountTicket = resultPair.getValue();
				if(upCountTicket != 0) {
					System.out.println("Ticket inserts: "+upCountTicket);
				}
				
				// Insert Booking Data
				int bid = 500 + random.nextInt(10000);
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
				LocalDateTime now = LocalDateTime.now();
				String bdate = dtf.format(now);
				query = "INSERT INTO booking VALUES("+cid+","+tid+","+bid+",'"+bdate+"');";
				resultPair = conn.executeQuery(query);
				int upCountBook = resultPair.getValue();
				if(upCountBook != 0) {
					System.out.println("Booking inserts: "+upCountBook);
				}
				
				custNameField.setText("");
				custPassField.setText("");
				custMailField.setText("");
				custPhoneField.setText("");
				movieInfoArea.setText("");
				seatingInfoArea.setText("");
				screeningEndTimeArea.setText("");
			}
		});
		
		// -------------------------------------------------
		/* Customer Lookup */
		
		JPanel customerLookupPanel = new JPanel();
		tabbedPane.addTab("Customer Lookup", null, customerLookupPanel, null);
		
		JPanel customerLookupPanel1 = new JPanel();
		customerLookupPanel.add(customerLookupPanel1);
		
		JButton findUserButton = new JButton("Find User");
		
		JScrollPane scrollPane_1_1 = new JScrollPane();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JLabel clLbl = new JLabel("Enter Customer Name to Query");
		clLbl.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_customerLookupPanel1 = new GroupLayout(customerLookupPanel1);
		gl_customerLookupPanel1.setHorizontalGroup(
			gl_customerLookupPanel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_customerLookupPanel1.createSequentialGroup()
					.addGroup(gl_customerLookupPanel1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_customerLookupPanel1.createSequentialGroup()
							.addGap(280)
							.addComponent(clLbl, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_customerLookupPanel1.createSequentialGroup()
							.addGap(460)
							.addComponent(findUserButton))
						.addGroup(gl_customerLookupPanel1.createSequentialGroup()
							.addGap(45)
							.addGroup(gl_customerLookupPanel1.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPane_1_1)
								.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE))))
					.addContainerGap(58, Short.MAX_VALUE))
		);
		gl_customerLookupPanel1.setVerticalGroup(
			gl_customerLookupPanel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_customerLookupPanel1.createSequentialGroup()
					.addComponent(clLbl)
					.addGap(18)
					.addComponent(scrollPane_1_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(findUserButton)
					.addGap(40)
					.addComponent(scrollPane_2, GroupLayout.PREFERRED_SIZE, 484, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(57, Short.MAX_VALUE))
		);
		
		JTextArea clResultArea = new JTextArea();
		scrollPane_2.setViewportView(clResultArea);
		
		JTextArea clQueryArea = new JTextArea();
		clQueryArea.setWrapStyleWord(true);
		clQueryArea.setLineWrap(true);
		scrollPane_1_1.setViewportView(clQueryArea);
		customerLookupPanel1.setLayout(gl_customerLookupPanel1);
		
		// Customer Lookups
		findUserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String cname = clQueryArea.getText();
					String findUserInfo = "select cust_name, email_id, b_date, seat_name "
							+ "from customer c join booking b "
							+ "on c.cust_id = b.cust_id join tickets t "
							+ "on b.ticket_id = t.ticket_id join seats s "
							+ "on s.seat_id = t.seat_id "
							+ "where cust_name like ?;";
					PreparedStatement pstmt = DBConn.con.prepareStatement(findUserInfo);
					pstmt.setString(1, cname);
					ResultSet rs = pstmt.executeQuery();
					printToArea(clResultArea, rs);
				} catch (SQLException e1) {
					ErrorDialog err = new ErrorDialog("Prepared Statement Creation Fail",e1.getMessage(),false);
					err.setVisible(true);
					e1.printStackTrace();
				}
			}
		});
		
		// --------------------------------------------------------
		/* Custom Queries */
		
		JPanel customQueryPanel = new JPanel();
		tabbedPane.addTab("Custom Query", null, customQueryPanel, null);
		
		JLabel cqLabel = new JLabel("Enter a Custom Query to Execute");
		cqLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JTextArea cqQueryArea = new JTextArea();
		JTextArea cqResultArea = new JTextArea();
		
		JButton executeButton = new JButton("Execute Query");
		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cqResultArea.setText("");	// Clear the results of previous query
				Pair<ResultSet, Integer> resultPair = conn.executeQuery(cqQueryArea.getText());
				ResultSet rs = resultPair.getKey();
				int updateCount = resultPair.getValue();
				
				try {
					
					if(rs == null) {
						cqResultArea.append("Update successful\n");
						cqResultArea.append(updateCount+" Rows Affected");
					}else {
						ResultSetMetaData rsmd = rs.getMetaData();
						int numCols = rsmd.getColumnCount();
						
						// Print Column Names
						for(int i = 1; i <= numCols; i++) {
							cqResultArea.append(rsmd.getColumnName(i)+"\t");
						}
						cqResultArea.append("\n");
					
						while(rs.next()) {
							// Print Data
							for(int i = 1; i <= numCols; i++) {
								cqResultArea.append(rs.getString(i)+"\t");
							}
							cqResultArea.append("\n");
						}
					}
					
				} catch (SQLException e1) {
					ErrorDialog err = new ErrorDialog("Custom Query Execution Fail",e1.getMessage(),false);
					err.setVisible(true);
					e1.printStackTrace();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_customQueryPanel = new GroupLayout(customQueryPanel);
		gl_customQueryPanel.setHorizontalGroup(
			gl_customQueryPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_customQueryPanel.createSequentialGroup()
					.addGroup(gl_customQueryPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_customQueryPanel.createSequentialGroup()
							.addGap(444)
							.addComponent(executeButton))
						.addGroup(gl_customQueryPanel.createSequentialGroup()
							.addGap(45)
							.addGroup(gl_customQueryPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPane_1, Alignment.LEADING)
								.addComponent(scrollPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 912, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(48, Short.MAX_VALUE))
				.addGroup(gl_customQueryPanel.createSequentialGroup()
					.addGap(280)
					.addComponent(cqLabel, GroupLayout.PREFERRED_SIZE, 436, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(289, Short.MAX_VALUE))
		);
		gl_customQueryPanel.setVerticalGroup(
			gl_customQueryPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_customQueryPanel.createSequentialGroup()
					.addComponent(cqLabel)
					.addGap(18)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addComponent(executeButton)
					.addGap(42)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 484, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(48, Short.MAX_VALUE))
		);
			
		scrollPane_1.setViewportView(cqQueryArea);
		cqQueryArea.setWrapStyleWord(true);
		cqQueryArea.setLineWrap(true);
		
		cqResultArea.setEditable(false);
		cqResultArea.setWrapStyleWord(true);
		cqResultArea.setLineWrap(true);
		scrollPane.setViewportView(cqResultArea);
		customQueryPanel.setLayout(gl_customQueryPanel);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MovieGUI window = new MovieGUI();
		
					/* Pop-up for Connection Information */
					ConnectDialog dialog = new ConnectDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					conn = dialog.conn;
					
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
