package restaurante.model.manager;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabInvBodega;

/**
 * Session Bean implementation class ManagerBodega
 */
@Stateless
@LocalBean
public class ManagerBodega {

@PersistenceContext(unitName="restauranteDS")
private EntityManager em;
    public ManagerBodega() {
        // TODO Auto-generated constructor stub
    }
    public void agregarBodega(String nombrebodega) throws Exception {
		TabInvBodega b = new TabInvBodega();
		b.setNombrebodega(nombrebodega);
		em.persist(b);
	}
    public TabInvBodega findBodegaByID(int idbodega) throws Exception {
		TabInvBodega b = em.find(TabInvBodega.class, idbodega);
		return b;
	}

	public void editarBodega(int idbodega, String nombrebodega) throws Exception {
		TabInvBodega b = findBodegaByID(idbodega);
		if (b == null)
			throw new Exception("No existe la bodega especificada.");
		b.setNombrebodega(nombrebodega);
		em.merge(b);
	}

	public List<TabInvBodega> findAllBodegas() {
		Query q;
		List<TabInvBodega> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT t FROM TabInvBodega t";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}
}
