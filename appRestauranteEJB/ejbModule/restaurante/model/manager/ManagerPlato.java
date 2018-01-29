package restaurante.model.manager;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabVtsPlato;

/**
 * Session Bean implementation class ManagerPlato
 */
@Stateless
@LocalBean
public class ManagerPlato {

	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;
    public ManagerPlato() {
        // TODO Auto-generated constructor stub
    }
   
    public void agregarPlato(String nombreplato, String descripccionplato, BigDecimal valorplato, byte[] fotoplato)
			throws Exception {

		TabVtsPlato p = new TabVtsPlato();
		p.setNombreplato(nombreplato);
		p.setDescripccionplato(descripccionplato);
		p.setValorplato(valorplato);
		p.setFotoplato(fotoplato);
		em.persist(p);
	}
    
    public TabVtsPlato findPlatoByID(int idplato) throws Exception {
		TabVtsPlato p = em.find(TabVtsPlato.class, idplato);
		return p;
	}

	public void editarPlato(int idplato, String nombreplato, String descripccionplato, BigDecimal valorplato,
			byte[] fotoplato) throws Exception {
		TabVtsPlato p = findPlatoByID(idplato);
		if (p == null)
			throw new Exception("No existe el plato especificado.");
		p.setNombreplato(nombreplato);
		p.setDescripccionplato(descripccionplato);
		p.setValorplato(valorplato);
		p.setFotoplato(fotoplato);
		em.merge(p);
	}
	

	public List<TabVtsPlato> findAllPlatos() {
		Query q;
		List<TabVtsPlato> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT p FROM TabVtsPlato p ORDER BY p.nombreplato";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}
}
