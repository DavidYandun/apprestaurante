package restaurante.model.manager;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import restaurante.model.entities.TabInvCategoriaProducto;

/**
 * Session Bean implementation class ManagerCategoriaProducto
 */
@Stateless
@LocalBean
public class ManagerCategoriaProducto {

	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerCategoriaProducto() {
		// TODO Auto-generated constructor stub
	}

	public void AgregarCategoriaProductos(String nombrecategoria, String descripcioncategoria) throws Exception {
		TabInvCategoriaProducto c = new TabInvCategoriaProducto();
		c.setNombrecategoria(nombrecategoria);
		c.setDescripcioncategoria(descripcioncategoria);
		em.persist(c);
	}

	public TabInvCategoriaProducto findCategoriaProdByID(int idcategoria) throws Exception {
		TabInvCategoriaProducto c = em.find(TabInvCategoriaProducto.class, idcategoria);
		return c;
	}

	public void editarCategoriaPlato(int idcategoria, String nombrecategoria, String descripcioncategoria)
			throws Exception {
		TabInvCategoriaProducto c = findCategoriaProdByID(idcategoria);
		if (c == null)
			throw new Exception("No existe la categoria especificado.");
		c.setNombrecategoria(nombrecategoria);
		c.setDescripcioncategoria(descripcioncategoria);
		em.merge(c);
	}

	public List<TabInvCategoriaProducto> findAllCategorias() {
		Query q;
		List<TabInvCategoriaProducto> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT c FROM TabInvCategoriaProducto c ORDER BY c.nombrecategoria";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}
}
