package restaurante.model.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabParametro;
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
	 * Realiza los calculos de subtotales, impuestos y totales.
	 * 
	 * @param facturaCabTmp
	 *            Factura temporal creada en memoria.
	 */
	private void calcularFacturaTmp(TabVtsFacturaVenta facturaCabTmp) {
		double Subtotal, porcentajeIVA, valorIVA, sumaTotales;
		// verificamos los campos calculados:
		sumaTotales = 0;
		for (TabVtsDetalleVenta det : facturaCabTmp.getTabVtsDetalleVentas()) {
			sumaTotales += det.getCantidaddetalleventa().intValue() * det.getValorunitarioventa().intValue();
		}

		porcentajeIVA = getPorcentajeIVA();
		Subtotal = sumaTotales /((100 + porcentajeIVA) / 100);
		valorIVA = sumaTotales - Subtotal;

		facturaCabTmp.setSubtotalfacturaventa(new BigDecimal(Subtotal));
		facturaCabTmp.setIvafacturaventa(new BigDecimal(valorIVA));
		facturaCabTmp.setTotalfacturaventa(new BigDecimal(sumaTotales));
	}

	public double getPorcentajeIVA() {
		TabParametro parametro;
		try {
			parametro = em.find(TabParametro.class, "valor_iva");
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return Double.parseDouble(parametro.getValorParametro());

	}

	/**
	 * Retorna el valor actual del contador de facturas. Este contador es un
	 * parametro del sistema.
	 * 
	 * @return ultimo valor del contador de facturas
	 * @throws Exception
	 */
	private int getContFacturas() throws Exception {
		int contFacturas = 0;
		TabParametro parametro = null;
		try {
			parametro = em.find(TabParametro.class, "cont_factura_venta");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Revise el parametro 'cont_facturas': " + e.getMessage());
		}
		contFacturas = Integer.parseInt(parametro.getValorParametro());
		return contFacturas;
	}

	/**
	 * Actualiza el valor del contador de facturas.
	 * 
	 * @param nuevoContadorFacturas
	 *            nuevo valor del contador.
	 * @throws Exception
	 */
	private void actualizarContFacturas(int nuevoContadorFacturas) throws Exception {
		TabParametro parametro = null;
		try {
			parametro = em.find(TabParametro.class, "cont_factura_venta");
			parametro.setValorParametro(Integer.toString(nuevoContadorFacturas));
			em.merge(parametro);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar el parametro 'cont_facturas': " + e.getMessage());
		}
	}

	/**
	 * Adiciona un item detalle a una factura temporal. Estos valores permanecen en
	 * memoria.
	 * 
	 * @param codigoProducto
	 *            codigo del producto.
	 * @param cantidad
	 *            cantidad del producto.
	 * @throws Exception
	 *             problemas ocurridos al momento de insertar el item detalle.
	 */
	public void agregarDetalleFacturaTmp(TabVtsFacturaVenta facturaCabTmp, Integer idplato, Integer cantidad)
			throws Exception {
		TabVtsPlato p;
		TabVtsDetalleVenta fd;
		double valorTotal;

		if (facturaCabTmp == null)
			throw new Exception("Error primero debe crear una nueva factura.");
		if (idplato == null || idplato.intValue() < 0)
			throw new Exception("Error debe especificar el codigo del producto.");
		if (cantidad == null || cantidad.intValue() <= 0)
			throw new Exception("Error debe especificar la cantidad del producto.");

		// buscamos el producto:
		p = findPlatoById(idplato);
		// creamos un nuevo detalle y llenamos sus propiedades:
		fd = new TabVtsDetalleVenta();
		valorTotal= cantidad * p.getValorplato().intValue();
		
		
		fd.setTabVtsFacturaVenta(facturaCabTmp);		
		fd.setCantidaddetalleventa(cantidad);
		fd.setValorunitarioventa(p.getValorplato());
		fd.setTabVtsPlato(p);
		fd.setValortotalventa(new BigDecimal(valorTotal));
		
		//añadir el detalle dentro de la lista 
		facturaCabTmp.getTabVtsDetalleVentas().add(fd);

		// verificamos los campos calculados:
		calcularFacturaTmp(facturaCabTmp);

	}

	public TabVtsCliente findClienteById(String idcliente) throws Exception {
		TabVtsCliente c = em.find(TabVtsCliente.class, idcliente);
		return c;
	}


	public void guardarFacturaTemporal(TabVtsFacturaVenta facturaCabTmp) throws Exception {

		if (facturaCabTmp == null)
			throw new Exception("Debe crear una factura primero.");
		if (facturaCabTmp.getTabVtsDetalleVentas() == null || facturaCabTmp.getTabVtsDetalleVentas().size() == 0)
			throw new Exception("Debe ingresar los productos en la factura.");
		if (facturaCabTmp.getTabVtsCliente() == null)
			throw new Exception("Debe registrar el cliente.");

		facturaCabTmp.setFechafacturaventa(new Date());

		// obtenemos el numero de la nueva factura:
		int contFacturas;
		contFacturas = getContFacturas();
		contFacturas++;
		facturaCabTmp.setIdfacturaventa(contFacturas);

		// verificamos los campos calculados:
		calcularFacturaTmp(facturaCabTmp);

		// guardamos la factura completa en la bdd:
		em.persist(facturaCabTmp);

		// actualizamos los parametros contadores de facturas:
		actualizarContFacturas(contFacturas);
		facturaCabTmp = null;

	}

}
