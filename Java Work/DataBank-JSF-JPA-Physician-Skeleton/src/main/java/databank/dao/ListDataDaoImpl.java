/********************************************************************************************************2*4*w*
 * File:  ListDataDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
 */
package databank.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletContext;


/**
 * Description: API for reading list data from the database
 */
@Named
@ApplicationScoped
public class ListDataDaoImpl implements ListDataDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	// TODO 01 - Set the value of this string constant properly. This is the query
	// to retrieve the list of specialties from the database.
	private static final String READ_ALL_SPECIALTIES = "SELECT * FROM databank.specialty;";

	@PersistenceContext(name = "PU_DataBank")
	protected EntityManager em;

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> readAllSpecialties() {
		logMsg("reading all specialties");
		List<String> specialties = new ArrayList<>();
		try {
			specialties = em.createNativeQuery(READ_ALL_SPECIALTIES).getResultList();
		} catch (Exception e) {
			logMsg("something went wrong:  " + e.getLocalizedMessage());
		}
		return specialties;
	}

}
