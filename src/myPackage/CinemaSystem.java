package myPackage;

import java.awt.*;
import java.awt.event.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import org.jdesktop.swingx.JXDatePicker;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class CinemaSystem implements ActionListener{
    // Global Variables
    JFrame frame = new JFrame(); // Creates JFrame
    JLabel title;
    JButton l[][], m[][], r[][]; // Names grid of JButtons
    JPanel panel1, panel2, panel3;
    ArrayList<String> bookedSeatsNumber = new ArrayList<String>();
    ArrayList<CinemaSeat> bookedCinemaSeats = new ArrayList<CinemaSeat>();
    JXDatePicker picker;
    JComboBox movieList,timeList;
    Color buttonColor;
    LocalDate bookDate = LocalDate.now();
    String bookMovie = "Avengers: Endgame";
    String bookHour = "21:30";
    String[] movieHours = {"21:00","23:00"};
    String[] movieTitleList = { "Avengers: Endgame", "Shazam!", "Godzilla: King of the Monsters", "Dark Phoenix", "Spider-Man: Far From Home" };
    Connection conn = null;
   	final String USER = "root";
   	final String PASS = "root";
   	Statement stmt = null;
   	ResultSet rs;
   	
    // Constructor
    public void CinemaSystemStart(){
    		
            title = new JLabel("Cinema Booking");
            title.setFont(new Font("Helvetica", Font.BOLD, 30));
            title.setLocation(140,5);
            title.setSize(600, 60);          
            //System.out.print(cineSeat.getMovie().getTitle());
            int seatNumber = 1;
            String seatNum;
            frame.setLayout(null); // Setting Grid Layout        
            // panel1.setLayout(new GridLayout(seat,row));
            l = new JButton[8][4]; // Allocating Size of Grid
            JButton b = new JButton();
            buttonColor = b.getBackground();
            panel1 = new JPanel(new GridLayout(8,4));
            panel1.setBackground(Color.black);
            panel1.setBounds(10, 200, 250, 300);          
                    for(int x = 0; x < 8; x++){
                    	for(int y = 0; y <4 ; y++){
                    	seatNum = Character.toString((char)(65+x))+seatNumber;
                        l[x][y] = new JButton(seatNum); // Creates New JButton
                        l[x][y].setActionCommand(seatNum);                       
                        l[x][y].addActionListener(this);
                        if(checkIfSeatIsBooked(seatNum)) {
                        	l[x][y].setBackground(Color.red);
                        }
                        panel1.add(l[x][y]); //adds button to grid
                        seatNumber ++;
                    }
                    seatNumber=1;
            }
            seatNumber = 5;
            m = new JButton[8][6]; // Allocating Size of Grid
            panel2 = new JPanel(new GridLayout(8,6));
            panel2.setBackground(Color.black);
            panel2.setBounds(300, 200, 400, 300);           
                    for(int x = 0; x < 8; x++){
                    	for(int y = 0; y <6 ; y++){
                    	seatNum = Character.toString((char)(65+x))+seatNumber;
                        m[x][y] = new JButton(seatNum); // Creates New JButton
                        m[x][y].setActionCommand(seatNum);
                        m[x][y].addActionListener(this);
                        if(checkIfSeatIsBooked(seatNum)) {
                        	m[x][y].setBackground(Color.red);
                        }
                        panel2.add(m[x][y]); //adds button to grid
                        seatNumber ++;
                    }
                    seatNumber = 5;
            }
            seatNumber = 11;
            r = new JButton[8][4]; // Allocating Size of Grid
            panel3 = new JPanel(new GridLayout(8,4));
            panel3.setBackground(Color.black);
            panel3.setBounds(740, 200, 250, 300);
            for(int x = 0; x < 8; x++){
            	for(int y = 0; y <4 ; y++){
            			seatNum = Character.toString((char)(65+x))+seatNumber;
                        r[x][y] = new JButton(seatNum); // Creates New JButton
                        r[x][y].setActionCommand(seatNum);
                        r[x][y].addActionListener(this);
                        if(checkIfSeatIsBooked(seatNum)) {
                        	r[x][y].setBackground(Color.red);
                        }
                        panel3.add(r[x][y]); //adds button to grid
                        seatNumber ++;
                    }
            seatNumber = 11;
            }
            
            //Date Picker
            JPanel datePanel = new JPanel(new GridLayout(1,1));
            datePanel.setBackground(Color.black);
            datePanel.setBounds(220, 70, 120, 30);
            
            JLabel dateTitle = new JLabel("Date:");
            dateTitle.setFont(new Font("Helvetica", Font.PLAIN , 20));
            dateTitle.setLocation(140,70);
            dateTitle.setSize(100, 30);
            
            picker = new JXDatePicker();
            picker.setDate(Calendar.getInstance().getTime());
            picker.setFormats(new SimpleDateFormat("dd.MM.yyyy"));
            picker.addActionListener(this);
            datePanel.add(picker);
          
            
            
            //Book now button
            JPanel panel4 = new JPanel(new GridLayout(1,1));
            panel4.setBackground(Color.black);
            panel4.setBounds(20, 550, 200, 60);
            
            JButton butt = new JButton("Book Now");
            butt.setFont(new Font("Arial", Font.PLAIN, 20));
            butt.addActionListener(this);
            butt.setActionCommand("Book Now");         
            panel4.add(butt);
            
            //Movie Title Picker        
            JLabel movieTitle = new JLabel("Movie:");
            movieTitle.setFont(new Font("Helvetica", Font.PLAIN , 20));
            movieTitle.setLocation(140,110);
            movieTitle.setSize(100, 30);           
            movieList = new JComboBox(movieTitleList);
            movieList.addActionListener(this);     
            movieList.setBounds(220, 110, 120, 30);
           
            //Show time picker
            
            JLabel timeTitle = new JLabel("Time:");
            timeTitle.setFont(new Font("Helvetica", Font.PLAIN , 20));
            timeTitle.setLocation(140,150);
            timeTitle.setSize(100, 30);           
            timeList = new JComboBox(movieHours);
            timeList.addActionListener(this);     
            timeList.setBounds(220, 150, 120, 30);
            
            
            //Add everything to JFrame
            JLabel img = new JLabel(new ImageIcon("Images\\cinema.jpg"));
            img.setBounds(0,0, 1200, 700);
            frame.setContentPane(img);
            frame.add(title);
            frame.add(panel1);
            frame.add(panel2);
            frame.add(panel3);
            frame.add(panel4);
            frame.add(datePanel);
            frame.add(dateTitle);
            frame.add(timeTitle);
            frame.add(movieList);
            frame.add(timeList);
            frame.add(movieTitle);
            frame.setPreferredSize(new Dimension(1015, 700));
            frame.setLocation(300, 100);
            frame.setTitle("Cinema Booking");            
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); //sets appropriate size for frame
            frame.setVisible(true); //makes frame visible
            frame.addWindowListener(new WindowAdapter() {
          	  public void windowClosing(WindowEvent e) {
          		  
    		      try {
    		    	//STEP 6: Clean-up environment
					rs.close();
					stmt.close();
	    		    conn.close();
    		      } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
    		      } 		     
          	  }
          	  
          	});
    }
   
    public void ConnectToDatabase() {
    	
		try {
			  //STEP 1: Open a connection
		      System.out.println("Connecting to database...");
			  conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemasystem?autoReconnect=true&useSSL=false",USER,PASS);
			  System.out.println("Connection success");
			
			  //STEP 2: Execute a query
		      System.out.println("Creating statement...");
		      stmt = (Statement) conn.createStatement();
		      String sql;
		      sql = "SELECT * FROM bookedseats";
		      rs = stmt.executeQuery(sql);
	
		      //STEP 3: Extract data from result set
		      Movie movie;
	          CinemaSeat cineSeat;	            	
	          
		      while(rs.next()){
		         //Retrieve by column name
		         
		         String num = rs.getString("SeatNumber");
		         String title = rs.getString("MovieTitle");
		         LocalDate date = LocalDate.parse(rs.getString("Moviedate"));	         
		         String time = rs.getString("MovieTime");
		         
		         movie = new Movie(title,time);
		         cineSeat = new CinemaSeat(num,date,movie);
		         bookedCinemaSeats.add(cineSeat);
	        
		      }
	      
		} catch(Exception e) {
			System.out.println("Connection error: "+e);
		}
		
	}
    
    public void insertToDatabase(String seatNum,String date,String title, String time) {
    	
    	try {   
	        stmt = (Statement) conn.createStatement();
	        String sql;
	        sql ="INSERT INTO bookedseats(SeatNumber,Moviedate,MovieTitle,MovieTime) VALUES(\""+seatNum+"\",\""+date +"\",\""+title +"\",\""+time+"\")";   
	        stmt.executeUpdate(sql);
	        JOptionPane.showMessageDialog(null, "Seats have been booked succesfully");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    public boolean checkIfSeatIsBooked(String seatNum) {
    	//using JAVA 8 Stream to see if seat is booked
    	if (bookedCinemaSeats.stream().anyMatch(id -> id.getSeatNumber().equals(seatNum))) { 
    		return true;
    	} else {
    	   return false;
    	}
    	
    }
    // Main Class
    public static void main(String[] args) {
    	 
    	 CinemaSystem system = new CinemaSystem();  
    	 system.ConnectToDatabase();
         system.CinemaSystemStart();
         
    }
    
    @Override
    public  void actionPerformed(ActionEvent a){
    	if (a.getSource() instanceof JButton) {
            
            if(a.getActionCommand().toString().equals("Book Now")) {
            	Movie movie = new Movie(bookMovie,bookHour);
            	CinemaSeat cineSeat;
            	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            	
            	for(String b : bookedSeatsNumber) {           		
                	cineSeat = new CinemaSeat(b,bookDate,movie);
                	bookedCinemaSeats.add(cineSeat);
                	insertToDatabase(b, bookDate.format(formatter),bookMovie, bookHour);               	
            	}
            	bookedSeatsNumber = new ArrayList<String>();
            }
            else {
	            if(((JButton) a.getSource()).getBackground() == buttonColor) {
	            	((JButton) a.getSource()).setBackground(Color.GREEN);
	            	bookedSeatsNumber.add(a.getActionCommand());
	            }
	            else if(((JButton) a.getSource()).getBackground() == Color.GREEN) {
	            	  ((JButton) a.getSource()).setBackground(buttonColor);
	            	  bookedSeatsNumber.remove(a.getActionCommand());
	            }
	          
	            ((JButton) a.getSource()).setContentAreaFilled(false);
	            ((JButton) a.getSource()).setOpaque(true);
            }
        }
    	
    	if (a.getSource() instanceof JXDatePicker) {
    		bookDate = picker.getDate().toInstant()
    	      .atZone(ZoneId.systemDefault())
    	      .toLocalDate();	 
    	}
    	
    	if (a.getSource() instanceof JComboBox) {   		
    		bookMovie = movieList.getSelectedItem().toString();
    		bookHour = timeList.getSelectedItem().toString(); 		
    	}
    	
    	
    }
    
   
    
}
