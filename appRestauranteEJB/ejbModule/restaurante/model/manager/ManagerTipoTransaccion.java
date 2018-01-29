package restaurante.model.manager;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabCajTipoTransaccion;

/**
 * Session Bean implementation class ManagerTipoTransaccion
 */
@Stateless
@LocalBean
public class ManagerTipoTransaccion {

	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerTipoTransaccion() {
		// TODO Auto-generated constructor stub
	}

	public void agregarTipoTransaccion(String nombretipotransaccion, String descripciontransaccion) throws Exception {
		TabCajTipoTransaccion t = new TabCajTipoTransaccion();
		t.setNombretipotransaccion(nombretipotransaccion);
		t.setDescripciontransaccion(descripciontransaccion);
		em.persist(t);
	}

	public TabCajTipoTransaccion findTipoTransByID(int idtipo) throws Exception {
		TabCajTipoTransaccion t = em.find(TabCajTipoTransaccion.class, idtipo);
		return t;
	}

	public void editarTipoTransaccion(int idtipo, String nombretipotransaccion, String descripciontransaccion)
			throws Exception {
		TabCajTipoTransaccion t = findTipoTransByID(idtipo);
		if (t == null)
			throw new Exception("No existe el tipo de transaccion especificada.");
		t.setNombretipotransaccion(nombretipotransaccion);
		t.setDescripciontransaccion(descripciontransaccion);
		em.merge(t);
	}

	public List<TabCajTipoTransaccion> findAllTipos() {
		Query q;
		List<TabCajTipoTransaccion> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT t FROM TabCajTipoTransaccion t ORDER BY t.nombretipotransaccion";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}

}
