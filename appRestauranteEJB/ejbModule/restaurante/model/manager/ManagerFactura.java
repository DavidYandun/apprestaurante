package restaurante.model.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabVtsCliente;
import restaurante.model.entities.TabVtsDetalleVenta;
import restaurante.model.entities.TabVtsFacturaVenta;
import restaurante.model.entities.TabVtsPlato;

/**
 * Session Bean implementation class ManagerFactura
 */
@Stateless
@LocalBean
public class ManagerFactura {

	/**
	 * Default constructor.
	 */
	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerFactura() {
		// TODO Auto-generated constructor stub
	}

	// Manejo de Platos
	@SuppressWarnings("unchecked")
	public List<TabVtsPlato> findAllPlatos() {
		Query q;
		List<TabVtsPlato> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT p FROM TabVtsPlato p ORDER BY p.nombreplato";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}

	public TabVtsPlato findPlatoById(int idplato) throws Exception {
		TabVtsPlato p = em.find(TabVtsPlato.class, idplato);
		return p;
	}

	/**
	 * Crea una nueva cabecera de factura temporal, para que desde el programa
	 * cliente pueda manipularla y llenarle con la informacion respectiva. Esta
	 * informacion solo se mantiene en memoria.
	 * 
	 * @return la nueva factura temporal.
	 */
	public TabVtsFacturaVenta crearFacturaVentaTmp() {
		TabVtsFacturaVenta facturaCabTmp = new TabVtsFacturaVenta();
		facturaCabTmp.setFechafacturaventa(new Date());
		facturaCabTmp.setTabVtsDetalleVentas(new ArrayList<TabVtsDetalleVenta>());
		return facturaCabTmp;
	}

	/**
	 * Asigna un cliente a una factura temporal.
	 * 
	 * @param facturaCabTmp
	 *            Factura temporal creada en memoria.
	 * @param cedulaCliente
	 *            codigo del cliente.
	 * @throws Exception
	 */
	public void asignarClienteFacturaTmp(TabVtsFacturaVenta facturaCabTmp, String idcliente) throws Exception {

		TabVtsCliente cliente = null;
		if (idcliente == null || idcliente.length() == 0)
			throw new Exception("Error debe especificar la cedula del cliente.");
		try {
			cliente = findClienteById(idcliente);
			if (cliente == null)
				throw new Exception("Error al asignar cliente.");
			facturaCabTmp.setTabVtsCliente(cliente);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al asignar cliente: " + e.getMessage());
		}
	}
	
	/**
	 * Adiciona un item detalle a una factura temporal. Estos valores permanecen
	 * en memoria. 
	 * @param codigoProducto codigo del producto.
	 * @param cantidad cantidad del producto.
	 * @throws Exception problemas ocurridos al momento de insertar el item detalle.
	 */
	public void agregarDetalleFacturaTmp(TabVtsFacturaVenta facturaCabTmp,Integer idplato,Integer cantidad) throws Exception{
		TabVtsPlato p;
		TabVtsDetalleVenta fd;	
		
		if(facturaCabTmp==null)
			throw new Exception("Error primero debe crear una nueva factura.");
		if(idplato==null||idplato.intValue()<0)
			throw new Exception("Error debe especificar el codigo del producto.");
		if(cantidad==null||cantidad.intValue()<=0)
			throw new Exception("Error debe especificar la cantidad del producto.");
		
		//buscamos el producto:
		p=findPlatoById(idplato);
		//creamos un nuevo detalle y llenamos sus propiedades:
		fd=new TabVtsDetalleVenta();
		fd.setCantidaddetalleventa(cantidad);
		fd.setValorunitarioventa(p.getValorplato());
		fd.setTabVtsPlato(p);
		facturaCabTmp.getTabVtsDetalleVentas().add(fd);
		
	}

	public TabVtsCliente findClienteById(String idcliente) throws Exception {
		TabVtsCliente c = em.find(TabVtsCliente.class, idcliente);
		return c;
	}

	public List<TabVtsCliente> findAllClientes() {
		Query q;
		List<TabVtsCliente> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT c FROM TabVtsCliente c ORDER BY c.apellidocliente";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}
	
}
