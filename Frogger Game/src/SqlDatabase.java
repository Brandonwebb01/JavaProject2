import java.sql.Connection;
//import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDatabase {
	
	public int DisplayRecords(ResultSet rs) throws SQLException {
		 int score = 0;
		 while ( rs.next() ) {
		 score = rs.getInt("score");
		 
		 System.out.println("Score = " + score);
		 System.out.println();
		 }
		 return score;
	}
	
	public int getScore() {
		//declare a connection
				Connection conn = null;
				Statement stmt = null;
				int score = 0;
				//database commands within try/catch
				try {
					//load driver
					Class.forName("org.sqlite.JDBC");
					System.out.println("Driver Loaded");
					
					//create a connection string
					String dbURL = "jdbc:sqlite:scoreData.db";
					conn = DriverManager.getConnection(dbURL);
					
					if (conn != null) {
						System.out.println("Connected to database");
						conn.setAutoCommit(false);										
						
						//Create Statement to execute
						stmt = conn.createStatement();
						
						String sql = "Select * from scoretable where id = 1;";
						
						ResultSet rs = stmt.executeQuery(sql);
						score = DisplayRecords(rs);
						rs.close();
						
//						stmt.execute(sql);
						conn.commit();
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return score;
	}
	
	public void setScore(String newScore) {
		Connection conn = null;
		Statement stmt = null;
		//database commands within try/catch
		try {
			//load driver
			Class.forName("org.sqlite.JDBC");
			System.out.println("Driver Loaded");
			
			//create a connection string
			String dbURL = "jdbc:sqlite:scoreData.db";
			conn = DriverManager.getConnection(dbURL);
			
			if (conn != null) {
				System.out.println("Connected to database");
				conn.setAutoCommit(false);										
				
				//Create Statement to execute
				stmt = conn.createStatement();
				
				String sql = "update scoretable SET score = " + newScore + " where id=1;";
				stmt.executeUpdate(sql);
				
				
				conn.commit();
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
