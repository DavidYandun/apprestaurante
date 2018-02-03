package restaurante.model.manager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabCajCajero;

/**
 * Session Bean implementation class ManagerTipoTransaccion
 */
@Stateless
@LocalBean
public class ManagerCajaDiaria {

	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerCajaDiaria() {
		// TODO Auto-generated constructor stub
	}

	public void agregarCajaDiaria(BigDecimal inicioCajero, BigDecimal cierreCajero) throws Exception {
		TabCajCajero t = new TabCajCajero();
		t.setFechaCajero(new Date());
		t.setIniciocajero(inicioCajero);
		t.setCierrecajero(cierreCajero);
		em.persist(t);
	}

	public TabCajCajero findCajaDiariaByID(Date fecha_cajero) throws Exception {
		TabCajCajero t = em.find(TabCajCajero.class, fecha_cajero);
		return t;
	}

	public void editarCajaDiaria(Date fecha_cajero, BigDecimal inicioCajero, BigDecimal cierreCajero)
			throws Exception {
		TabCajCajero t = findCajaDiariaByID(fecha_cajero);
		if (t == null)
			throw new Exception("No existe la caja diaria de dicha fecha.");
		t.setIniciocajero(inicioCajero);
		t.setCierrecajero(cierreCajero);
		em.merge(t);
	}

	public List<TabCajCajero> findAllCajas() {
		Query q;
		List<TabCajCajero> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT t FROM TabCajCajero t ORDER BY t.fechaCajero";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}

}
