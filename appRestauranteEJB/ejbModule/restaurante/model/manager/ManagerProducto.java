package restaurante.model.manager;

import java.util.List;
import java.math.BigDecimal;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabInvBodega;
import restaurante.model.entities.TabInvCategoriaProducto;
import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabInvProveedor;
import restaurante.model.entities.TabVtsCliente;

/**
 * Session Bean implementation class ManagerProducto
 */
@Stateless
@LocalBean
public class ManagerProducto {

    @PersistenceContext(unitName = "restauranteDS")
    private EntityManager em;
	
    public ManagerProducto() {
        // TODO Auto-generated constructor stub
    }
    public TabInvCategoriaProducto findCategoriaProdByID(int idcategoria) throws Exception {
		TabInvCategoriaProducto c = em.find(TabInvCategoriaProducto.class, idcategoria);
		return c;
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
    
  
    public List<TabInvBodega> findAllBodegas() {
		Query q;
		List<TabInvBodega> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT t FROM TabInvBodega t";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}
    public TabInvProducto findProductoByID(int idproducto) throws Exception {
		TabInvProducto p = em.find(TabInvProducto.class, idproducto);
		return p;
	}
    public TabInvBodega findBodegaByID(int idbodega) throws Exception {
		TabInvBodega b = em.find(TabInvBodega.class, idbodega);
		return b;
	}
    public List<TabInvProducto> findAllProductos() {
		Query q;
		List<TabInvProducto> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT p FROM TabInvProducto p";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}
    
    
    public void agregarproducto(Integer idcategoria, Integer idbodega, String nombreproducto, String descripcionproducto, BigDecimal valorcompra ,
    		BigDecimal valorventa, BigDecimal stock,Boolean estado, String unidadmedida) throws Exception {
    	TabInvCategoriaProducto cp;
    	TabInvBodega b;
    	if (idcategoria == null || idcategoria.equals(0))
			throw new Exception("Debe especificar la categoria.");
    	if (idbodega == null || idbodega.equals(0))
			throw new Exception("Debe especificar la bodega.");
    	
		
		TabInvProducto p = new TabInvProducto();
		cp=findCategoriaProdByID(idcategoria);
		b=findBodegaByID(idbodega);
		
		p.setTabInvCategoriaProducto(cp);
		p.setTabInvBodega(b);
		p.setNombreproducto(nombreproducto);
		p.setDescripcionproducto(descripcionproducto);
		p.setValorcompra(valorcompra);
		p.setValorventa(valorventa);
		p.setStock(stock);
		p.setEstadoproducto(estado);
		p.setUnidadmedida(unidadmedida);				
		em.persist(p);
	}
    public TabInvProducto findproductoByID(Integer idproducto) throws Exception{
    	TabInvProducto p=em.find(TabInvProducto.class, idproducto);
    	return p;
    }
    public void editarproducto(Integer idproducto,Integer idcategoria, Integer idbodega, String nombreproducto, String descripcionproducto, BigDecimal valorcompra ,
    		BigDecimal valorventa, BigDecimal stock,Boolean estado, String unidadmedida) throws Exception{
    	TabInvCategoriaProducto cp=findCategoriaProdByID(idcategoria);
		TabInvBodega b=findBodegaByID(idbodega);
    	TabInvProducto p=findproductoByID(idproducto);
    	if(p == null)
    		throw new Exception("No existe el producto especificado.");
    	cp=findCategoriaProdByID(idcategoria);
		b=findBodegaByID(idbodega);
		
		p.setTabInvCategoriaProducto(cp);
		p.setTabInvBodega(b);
		p.setNombreproducto(nombreproducto);
		p.setDescripcionproducto(descripcionproducto);
		p.setValorcompra(valorcompra);
		p.setValorventa(valorventa);
		p.setStock(stock);
		p.setEstadoproducto(estado);
		p.setUnidadmedida(unidadmedida);				
		em.persist(p);
    	
    }
}
