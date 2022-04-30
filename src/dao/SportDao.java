package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Sport;

public class SportDao {

	private Connection connection;
	private final String GET_SPORTS_QUERY = "SELECT * FROM sports"; 
	//private final String GET_SPORT_BY_ID_QUERY = "SELECT * FROM sports WHERE id = ?";
	private final String CREATE_NEW_SPORT_QUERY = "INSERT INTO sports(name) VALUES(?)";
	private final String DELETE_SPORT_BY_ID_QUERY = "DELETE FROM sports WHERE id = ?";
	private final String UPDATE_SPORT_BY_ID_QUERY = "UPDATE sports SET name = ? WHERE id = ?";

	public SportDao() {
		connection = DBConnection.getConnection();
	}

	public List<Sport> getSports() throws SQLException {
		ResultSet rs = connection.prepareStatement(GET_SPORTS_QUERY).executeQuery();
		List<Sport> sports = new ArrayList<Sport>();

		while (rs.next()) {
			sports.add(populateSport(rs.getInt(1), rs.getString(2)));
		}
		return sports;
	}
	public void updateSportById(String sportName, int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(UPDATE_SPORT_BY_ID_QUERY);
		ps.setString(1, sportName);
		ps.setInt(2, id);
		ps.executeUpdate();
	}

	/*
	 * public Sport getSportById(int id) throws SQLException { PreparedStatement ps
	 * = connection.prepareStatement(GET_SPORT_BY_ID_QUERY); ps.setInt(1, id);
	 * ResultSet rs = ps.executeQuery(); rs.next(); return
	 * populateSport(rs.getInt(1), rs.getString(2)); }
	 */

	public void createNewSport(String sportName) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(CREATE_NEW_SPORT_QUERY);
		ps.setString(1, sportName);
		ps.executeUpdate();
	}

	public void deleteSportById(int id) throws SQLException {
		PreparedStatement ps = connection.prepareStatement(DELETE_SPORT_BY_ID_QUERY);
		ps.setInt(1, id);
		ps.executeUpdate();
	}

	private Sport populateSport(int id, String name) {
		return new Sport(id, name);
	}
}
