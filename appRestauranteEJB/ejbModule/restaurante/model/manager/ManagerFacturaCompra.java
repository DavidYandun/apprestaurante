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

import restaurante.model.entities.TabInvDetalleCompra;
import restaurante.model.entities.TabInvFacturaCompra;
import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabInvProveedor;
import restaurante.model.entities.TabParametro;
import restaurante.model.entities.TabInvBodega;

/**
 * Session Bean implementation class ManagerFacturaCompra
 */
@Stateless
@LocalBean
public class ManagerFacturaCompra {

	/**
	 * Default constructor.
	 */
	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerFacturaCompra() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public TabInvFacturaCompra crearFacturaCompraTmp(Date fecha) {
		TabInvFacturaCompra facturaCabTmp = new TabInvFacturaCompra();
		facturaCabTmp.setFechafacturacompra(fecha);
		facturaCabTmp.setTabInvDetalleCompras(new ArrayList<TabInvDetalleCompra>());
		return facturaCabTmp;
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
	public void asignarProveedorFacturaTmp(TabInvFacturaCompra facturaCabTmp, Integer idproveedor) throws Exception {
		TabInvProveedor proveedor = null;
		if (idproveedor == null || idproveedor.equals(0))
			throw new Exception("Error debe especificar el id del proveedor.");
		try {
			proveedor = findproveedorByID(idproveedor);
			if (proveedor == null)
				throw new Exception("Error al asignar proveedor.");
			facturaCabTmp.setTabInvProveedor(proveedor);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al asignar proveedor: " + e.getMessage());
		}
	}
	private void calcularFacturaTmp(TabInvFacturaCompra facturaCabTmp) {
		double Subtotal, porcentajeIVA, valorIVA, sumaTotales, stock;
		// verificamos los campos calculados:
		sumaTotales = 0;
		TabInvProducto pr;
		for (TabInvDetalleCompra det : facturaCabTmp.getTabInvDetalleCompras()) {
			sumaTotales += det.getCantidaddetalle().intValue() * det.getValorunitariocompra().intValue();			
		}

		porcentajeIVA = getPorcentajeIVA();
		Subtotal = sumaTotales *((+ porcentajeIVA) / 100);
		valorIVA = sumaTotales - Subtotal;

		facturaCabTmp.setSubtotalfacturacompra(new BigDecimal(Subtotal));
		facturaCabTmp.setIvafacturacompra(new BigDecimal(valorIVA));
		facturaCabTmp.setTotalfacturacompra(new BigDecimal(sumaTotales));
	}
	
	
	private int getContFacturasCompras() throws Exception {
		int contFacturasCompras = 0;
		TabParametro parametro = null;
		try {
			parametro = em.find(TabParametro.class, "cont_factura_compra");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Revise el parametro 'cont_facturas_compra': " + e.getMessage());
		}
		contFacturasCompras = Integer.parseInt(parametro.getValorParametro());
		return contFacturasCompras;
	}
	private void actualizarContFacturasCompras(int nuevoContadorFacturasCompras) throws Exception {
		TabParametro parametro = null;
		try {
			parametro = em.find(TabParametro.class, "cont_factura_compra");
			parametro.setValorParametro(Integer.toString(nuevoContadorFacturasCompras));
			em.merge(parametro);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar el parametro 'cont_facturas_compras': " + e.getMessage());
		}
	}
	public void agregarDetalleFacturaTmp(TabInvFacturaCompra facturaCabTmp, Integer idproducto, Integer cantidad,
			BigDecimal valorunitariocompra) throws Exception {
		TabInvProducto p;
		TabInvDetalleCompra fd;
		BigDecimal valortotalcompra;
		if (facturaCabTmp == null)
			throw new Exception("Error primero debe crear una nueva factura.");
		if (idproducto == null || idproducto.intValue() < 0)
			throw new Exception("Error debe especificar el codigo del producto.");
		if (cantidad == null || cantidad.intValue() <= 0)
			throw new Exception("Error debe especificar la cantidad del producto.");
		// buscamos el producto:
		p = findProductoByID(idproducto);
		
		p.setStock(p.getStock().add(new BigDecimal(cantidad)));
		
		
		// creamos un nuevo detalle y llenamos sus propiedades:
		fd = new TabInvDetalleCompra();
		valortotalcompra = (valorunitariocompra.multiply(new BigDecimal(cantidad)));
		fd.setTabInvFacturaCompra(facturaCabTmp);
		fd.setTabInvProducto(p);
		fd.setCantidaddetalle(cantidad);
		fd.setValorunitariocompra(valorunitariocompra);
		fd.setValortotalcompra(valortotalcompra);
		facturaCabTmp.getTabInvDetalleCompras().add(fd);
		calcularFacturaTmp(facturaCabTmp);
	}
	public void guardarFacturaTemporal(TabInvFacturaCompra facturaCabTmp) throws Exception {
		if (facturaCabTmp == null)
			throw new Exception("Debe crear una factura primero.");
		if (facturaCabTmp.getTabInvDetalleCompras() == null || facturaCabTmp.getTabInvDetalleCompras().size() == 0)
			throw new Exception("Debe ingresar los productos en la factura.");
		if (facturaCabTmp.getTabInvProveedor() == null)
			throw new Exception("Debe registrar el Proveedor.");
		facturaCabTmp.getFechafacturacompra();
		// obtenemos el numero de la nueva factura:
		int contFacturas;
		contFacturas = getContFacturasCompras();
		contFacturas++;
		facturaCabTmp.setIdfacturacompra(contFacturas);
		// verificamos los campos calculados:
		calcularFacturaTmp(facturaCabTmp);
		// guardamos la factura completa en la bdd:
		em.persist(facturaCabTmp);
		// actualizamos los parametros contadores de facturas:
		actualizarContFacturasCompras(contFacturas);
		facturaCabTmp = null;
	}
	public TabInvProducto findProductoByID(Integer idproducto) throws Exception {
		TabInvProducto pr = em.find(TabInvProducto.class, idproducto);
		return pr;
	}	
		
	public TabInvProveedor findproveedorByID(Integer idproveedor) throws Exception {
		TabInvProveedor p = em.find(TabInvProveedor.class, idproveedor);
		return p;
	}
	public TabInvBodega findBodegaByID(Integer idbodega) throws Exception {
		TabInvBodega b = em.find(TabInvBodega.class, idbodega);
		return b;
	}
}