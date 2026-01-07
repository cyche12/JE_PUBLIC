/*********************************************************************************************************
 * File:  PhysicianDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 * @author Hanna Felix updated the document
 */
package databank.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import javax.sql.DataSource;

import databank.model.PhysicianPojo;
import java.sql.Timestamp;
import java.time.LocalDateTime;


@SuppressWarnings("unused")
/**
 * Description:  Implements the C-R-U-D API for the database
 */

@Named 
@ApplicationScoped
public class PhysicianDaoImpl implements PhysicianDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	private static final String DATABANK_DS_JNDI = 
			"java:app/jdbc/databank";
	
	private static final String READ_ALL = 
			"SELECT * FROM databank.physician";
	
	private static final String READ_PHYSICIAN_BY_ID = 
			"SELECT * FROM databank.physician WHERE id = ?";
	
	private static final String INSERT_PHYSICIAN = 
			"INSERT INTO databank.physician (last_name, first_name, email, phone, specialty, created) VALUES (?, ?, ?, ?, ?, now())";
	
	private static final String UPDATE_PHYSICIAN_ALL_FIELDS = 
			"UPDATE databank.physician SET last_name = ?, first_name = ?, email = ?, phone = ?, specialty = ?, created = now() WHERE id = ?";

	private static final String DELETE_PHYSICIAN_BY_ID = 
			"DELETE FROM databank.physician WHERE id = ?";
	

	
	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@Resource(lookup = "java:app/jdbc/databank")
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllPstmt;
	protected PreparedStatement readByIdPstmt;
	protected PreparedStatement createPstmt;
	protected PreparedStatement updatePstmt;
	protected PreparedStatement deleteByIdPstmt;
	protected PreparedStatement readAllSpecialtiesPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			conn = databankDS.getConnection();
			readAllPstmt = conn.prepareStatement(READ_ALL);
			createPstmt = conn.prepareStatement(INSERT_PHYSICIAN, RETURN_GENERATED_KEYS);
			readByIdPstmt = conn.prepareStatement(READ_PHYSICIAN_BY_ID);
			updatePstmt = conn.prepareStatement(UPDATE_PHYSICIAN_ALL_FIELDS);
			deleteByIdPstmt = conn.prepareStatement(DELETE_PHYSICIAN_BY_ID);
			
			
		} catch (Exception e) {
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			readAllPstmt.close();
			createPstmt.close();
			//TODO Close other PreparedStatements here
			readByIdPstmt.close();
			updatePstmt.close();
			deleteByIdPstmt.close();
			readAllSpecialtiesPstmt.close();
			conn.close();
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<PhysicianPojo> readAllPhysicians() {
		logMsg("reading all physicians");
		List<PhysicianPojo> physicians = new ArrayList<>();
		try (ResultSet rs = readAllPstmt.executeQuery();) {

			while (rs.next()) {
				PhysicianPojo newPhysician = new PhysicianPojo();
				newPhysician.setId(rs.getInt("id"));
				newPhysician.setLastName(rs.getString("last_name"));
				//TODO Complete the physician initialization here
				newPhysician.setFirstName(rs.getString("first_name"));
				newPhysician.setEmail(rs.getString("email"));
				newPhysician.setPhoneNumber(rs.getString("phone"));
				newPhysician.setSpecialty(rs.getString("specialty"));
				
				Timestamp timestamp = rs.getTimestamp("created");
				if (timestamp != null) {
					newPhysician.setCreated(timestamp.toLocalDateTime());
				}
				physicians.add(newPhysician);
			}
			
		} catch (SQLException e) {
			logMsg("something went wrong accessing database:  " + e.getLocalizedMessage());
		}
		
		return physicians;

	}

	@Override
	public PhysicianPojo createPhysician(PhysicianPojo physician) {
		logMsg("creating a physician");
		
		try {
			 createPstmt.setString(1, physician.getLastName());
		     createPstmt.setString(2, physician.getFirstName());
		     createPstmt.setString(3, physician.getEmail());
		     createPstmt.setString(4, physician.getPhoneNumber());
		     createPstmt.setString(5, physician.getSpecialty());
		       
		        createPstmt.executeUpdate();
		        
		        try (ResultSet generatedKeys = createPstmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                physician.setId(generatedKeys.getInt(1));
		            }
		        }
		        logMsg("Physician created with ID: " + physician.getId());
		    
		}catch (SQLException e) {
			logMsg("something went wrong creating physician: " + e.getLocalizedMessage());
		}
		return null;
	}

	@Override
	public PhysicianPojo readPhysicianById(int physicianId) {
		logMsg("read a specific physician");
		
		PhysicianPojo newPhysician = new PhysicianPojo();
		try {
			readByIdPstmt.setInt(1,physicianId);
			ResultSet rs = readByIdPstmt.executeQuery();
			if(rs.next()) {
				newPhysician.setId(rs.getInt("id"));
				newPhysician.setLastName(rs.getString("last_name"));
				newPhysician.setFirstName(rs.getString("first_name"));	
				newPhysician.setEmail(rs.getString("email"));
				newPhysician.setPhoneNumber(rs.getString("phone"));
				newPhysician.setSpecialty(rs.getString("specialty"));
				
			}
			
		} catch(Exception e){
			logMsg("something went wrong accesing database: " + e.getLocalizedMessage());
			
		}
		return newPhysician;
	}

	@Override
	public void updatePhysician(PhysicianPojo physician) {
		logMsg("updating a specific physician");
		
		try {
			updatePstmt.setString(1,physician.getLastName());
			updatePstmt.setString(2,physician.getFirstName());
			updatePstmt.setString(3,physician.getEmail());
			updatePstmt.setString(4,physician.getPhoneNumber());
			updatePstmt.setString(5,physician.getSpecialty());
			updatePstmt.setInt(6,physician.getId());
			updatePstmt.executeUpdate();
			
		}catch(SQLException e){
			logMsg("something went wrong can't update data: " + e.getLocalizedMessage());
		}
	}

	@Override
	public void deletePhysicianById(int physicianId) {
		logMsg("deleting a specific physician");
		
		try {
			deleteByIdPstmt.setLong(1, physicianId);
			deleteByIdPstmt.execute();
		}catch(SQLException e){
			logMsg("Something went wrong can't delete data: " + e.getLocalizedMessage());
		}
	}
	

}