package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
public List<ArtObject> getSecondObject(ArtObject object) {
		
		String sql = "Select distinct * " + 
				"From objects " + 
				"Where object_id in (Select e1.object_id " + 
				"					From exhibition_objects e1, exhibition_objects e2 " + 
				"					Where e1.`exhibition_id` = e2.`exhibition_id` " + 
				"					and e2.`object_id` = ?)";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, object.getId());
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
public int getWeight(ArtObject object1, ArtObject object2) {
	
	String sql = "Select Count(*) as count " + 
			"From exhibition_objects e1, exhibition_objects e2 " + 
			"Where e1.`exhibition_id` = e2.exhibition_id " + 
			"and e1.`object_id` = ? " + 
			"and e2.`object_id` = ?";
	int result = 0;
	Connection conn = DBConnect.getConnection();

	try {
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, object1.getId());
		st.setInt(2, object2.getId());
		ResultSet res = st.executeQuery();
		if (res.next()) {

			result = res.getInt("count");
		}
		conn.close();
		return result;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return 0;
	}
}
	
}
