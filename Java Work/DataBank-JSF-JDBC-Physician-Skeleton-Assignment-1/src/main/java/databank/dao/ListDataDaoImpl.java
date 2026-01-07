/*********************************************************************************************************
 * File:  ListDataDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Hanna felix updated the document.
 */
package databank.dao;

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

@SuppressWarnings("unused")
/**
 * Description:  API for reading list data from the database
 */

@Named
@ApplicationScoped
public class ListDataDaoImpl implements ListDataDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	private static final String DATABANK_DS_JNDI = 
			"java:app/jdbc/databank";
	
	private static final String READ_ALL_SPECIALTIES = 
			"SELECT name FROM databank.specialty";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@Resource(lookup = DATABANK_DS_JNDI)
	protected DataSource databankDS;

	protected Connection conn;
	protected PreparedStatement readAllSpecialtiesPstmt;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			conn = databankDS.getConnection();
			//TODO Initialize PreparedStatement here
			readAllSpecialtiesPstmt = conn.prepareStatement(READ_ALL_SPECIALTIES);
		} catch (Exception e) {
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			//TODO Close PreparedStatement here
			if(readAllSpecialtiesPstmt != null) {
				readAllSpecialtiesPstmt.close();
			}if(conn != null){
				conn.close();
			}
		} catch (Exception e) {
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<String> readAllSpecialties() {
		logMsg("reading all specialties");
		List<String> specialties = new ArrayList<>();
		try(ResultSet rs = readAllSpecialtiesPstmt.executeQuery()){
			while (rs.next()) {
				specialties.add(rs.getString("name"));
			}
		}catch(SQLException e) {
			logMsg("can't access database:  " + e.getLocalizedMessage());
		}
		return specialties;

	}

}
