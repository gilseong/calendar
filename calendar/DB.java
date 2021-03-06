package calendar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DB {
	Schedule sch;
	static Connection conn;
	public static String result;

	public DB() {
		
	}
	public DB(Schedule sch) {
		this.sch = sch;
	}

	public static void getInfo() throws Exception {
		String url = "jdbc:mysql://168.131.153.176:3306/calendar";
		String dbid = "superuser";
		String dbpw = "1234";
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, dbid, dbpw);
	}

	public static int addDaySchedule(Schedule sch) throws Exception {
		getInfo();
		PreparedStatement pst = conn.prepareStatement("insert into Calendar values(?,?,?)");
		pst.setString(1, sch.getTitle());
		pst.setString(2, sch.getDate());
		pst.setString(3, sch.getContents());
		int cnt = pst.executeUpdate();
		return cnt;// 리턴값 int인 이유는 추가 되었을? cnt>0 이상일경우에 디비에 추가가 성공됨을 알 수 있다.
	}

	// 2017.11.14:8.28
	public static ArrayList<Schedule> getDaySchedule() throws Exception {
		getInfo();

		ArrayList<Schedule> sch_list = new ArrayList<>();
		PreparedStatement pst = conn.prepareStatement("select * from Calendar");
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			String title = rs.getString(1);
			String date = rs.getString(2);
			String contents = rs.getString(3);
			sch_list.add(new Schedule(title, date, contents));
		}
		return sch_list;

	}
	public static void deleteSchedule(Schedule sch) throws Exception {
		getInfo();
		ArrayList<Schedule> schedule = new ArrayList<>();
		schedule = getDaySchedule();
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getTitle().equals(sch.getTitle())&&schedule.get(i).getDate().equals(sch.getDate())&&schedule.get(i).getContents().equals(sch.getContents())) {
			
				PreparedStatement pst = conn.prepareStatement("delete from calendar where title=?");
				pst.setString(1, sch.getTitle());
				pst.executeUpdate();
			}
		}
	}
/*
	public static void updateSchedule(Schedule sch) throws Exception {
		getInfo();
		ArrayList<Schedule> schedule = new ArrayList<>();
		schedule = getDaySchedule();
		for (int i = 0; i < schedule.size(); i++) {
			if (schedule.get(i).getTitle().equals(sch.getTitle())||schedule.get(i).getDate().equals(sch.getDate())||schedule.get(i).getContents().equals(sch.getContents())) {
				
				// update SMART_11 set id ='sunshine' where id ='sun';
				PreparedStatement pst = conn.prepareStatement("update calendar set  title=?, day =?, contents=? where title =? and day=? and contents=?");
				pst.setString(1, sch.getTitle());
				pst.setString(2, sch.getDate());
				pst.setString(3, sch.getContents());
				pst.setString(4, schedule.get(i).getTitle());
				pst.setString(5, schedule.get(i).getDate());
				pst.setString(6, schedule.get(i).getContents());
				int cnt = pst.executeUpdate();
			
			}
		}
	}
*/


}
