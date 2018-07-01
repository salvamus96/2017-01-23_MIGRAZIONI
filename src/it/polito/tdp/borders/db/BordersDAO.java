package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Arco;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryIdMap;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BordersDAO {
	
	/**
	 * Dato un anno, restituisce tutti gli stati
	 * @param anno
	 * @param map 
	 * @return
	 */
	public List <Country> getAllCountry (int anno, CountryIdMap map){
		String sql = "SELECT * " + 
					 "FROM country " + 
					 "WHERE CCode IN ( " + 
					 "				SELECT state1no " + 
					 "				FROM contiguity " + 
					 "				WHERE year <= ? " + 
					 "				AND conttype = 1) " + 
					 "ORDER BY StateAbb";

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(map.getCountry(c)) ;
			}
			
			conn.close() ;
			return list ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	
	public List<Arco> getAllEdges(int anno, CountryIdMap map) {
		String sql = "SELECT c.state1no, c.state2no " + 
					 "FROM contiguity AS c " + 
					 "WHERE c.year <= ? " + 
					 "		AND c.conttype = 1 " + 
					 "		AND c.state1no < c.state2no";

		try {
			Connection conn = DBConnect.getConnection() ;
	
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery() ;
			
			List<Arco> list = new LinkedList<>() ;
			
			while( rs.next() ) {
				
				Country partenza = map.getCountry(rs.getInt("c.state1no"));
				Country arrivo = map.getCountry(rs.getInt("c.state2no"));
				
				if (partenza != null && arrivo != null)
					list.add(new Arco (partenza, arrivo)) ;
			}
			
			conn.close() ;
			return list ;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
