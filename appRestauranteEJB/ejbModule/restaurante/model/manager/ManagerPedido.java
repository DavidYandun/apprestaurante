package restaurante.model.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import restaurante.model.entities.TabCajCajero;
import restaurante.model.entities.TabCajTipoTransaccion;
import restaurante.model.entities.TabCajTransaccion;
import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabLogUsuario;
import restaurante.model.entities.TabParametro;
import restaurante.model.entities.TabVtsCliente;
import restaurante.model.entities.TabVtsDetallePedido;
import restaurante.model.entities.TabVtsDetalleVenta;
import restaurante.model.entities.TabVtsFacturaVenta;
import restaurante.model.entities.TabVtsPedido;
import restaurante.model.entities.TabVtsPlato;

/**
 * Session Bean implementation class ManagerPedido
 */
@Stateless
@LocalBean
public class ManagerPedido {

	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerPedido() {
		// TODO Auto-generated constructor stub
	}

	public TabVtsPlato findPlatoById(int idplato) throws Exception {
		TabVtsPlato p = em.find(TabVtsPlato.class, idplato);
		return p;
	}

	public TabLogUsuario findUsuarioById(Integer idusuario) throws Exception {
		TabLogUsuario usuario = em.find(TabLogUsuario.class, "idusuario");
		return usuario;
	}

	public TabCajTransaccion crearTransaccionTmp() {
		TabLogUsuario usuario = em.find(TabLogUsuario.class, 1);
		TabCajTipoTransaccion tipotransaccion = em.find(TabCajTipoTransaccion.class, 1);
		TabCajTransaccion transaccionTmp = new TabCajTransaccion();
		TabCajCajero cajero = em.find(TabCajCajero.class,new Date());
		
		transaccionTmp.setTabCajTipoTransaccion(tipotransaccion);
		transaccionTmp.setTabVtsPedidos(new ArrayList<TabVtsPedido>());
		transaccionTmp.setDescripciontransaccion("pedido hecho en la fecha "+ new Date());
		transaccionTmp.setTabLogUsuario(usuario);
		transaccionTmp.setTabCajCajero(cajero);
		return transaccionTmp;
	}

	public void asignarUsuarioTransTmp(TabCajTransaccion transaccionTmp, Integer idusuario) throws Exception {

		TabLogUsuario usuario = null;
		if (idusuario == null)
			throw new Exception("Error debe especificar el usuario.");
		try {
			usuario = findUsuarioById(idusuario);
			if (usuario == null)
				throw new Exception("Error al asignar usuario.");
			transaccionTmp.setTabLogUsuario(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al asignar usuario: " + e.getMessage());
		}
	}

	public TabVtsPedido crearPedidoTmp(TabCajTransaccion transaccionTmp) {
		TabVtsPedido pedidoTmp = new TabVtsPedido();
		pedidoTmp.setTabCajTransaccion(transaccionTmp);
		pedidoTmp.setFechapedido(new Date());
		pedidoTmp.setTabVtsDetallePedidos(new ArrayList<TabVtsDetallePedido>());
		transaccionTmp.getTabVtsPedidos().add(pedidoTmp);
		return pedidoTmp;
	}
	public void asignarMesaPedidoTmp(TabVtsPedido pedidoTmp, Integer mesa) throws Exception {
		try {
			if (mesa == null|| mesa==0)
				throw new Exception("Error debe especificar la mesa.");
			pedidoTmp.setMesa(mesa);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al asignar mesa: " + e.getMessage());
		}
	}

	private void calcularPedidoTmp(TabVtsPedido pedidoTmp) {
		double sumaTotales = 0;
		for (TabVtsDetallePedido det : pedidoTmp.getTabVtsDetallePedidos()) {
			sumaTotales += det.getCantidaddetallepedido().intValue() * det.getValorunitariodetallepedido().intValue();
		}
		pedidoTmp.setTotal(new BigDecimal(sumaTotales));
	}

	private int getContPedidos() throws Exception {
		int contFacturas = 0;
		TabParametro parametro = null;
		try {
			parametro = em.find(TabParametro.class, "cont_pedido");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Revise el parametro 'cont_pedido': " + e.getMessage());
		}
		contFacturas = Integer.parseInt(parametro.getValorParametro());
		return contFacturas;
	}

	private void actualizarContPedidos(int nuevoContadorPedidos) throws Exception {
		TabParametro parametro = null;
		try {
			parametro = em.find(TabParametro.class, "cont_pedido");
			parametro.setValorParametro(Integer.toString(nuevoContadorPedidos));
			em.merge(parametro);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar el parametro 'cont_pedido': " + e.getMessage());
		}
	}

	public void agregarDetallePedidoTmp(TabVtsPedido pedidoTmp, Integer idplato, Integer cantidad) throws Exception {
		TabVtsPlato p;
		TabVtsDetallePedido d;
		double valorTotal;

		if (pedidoTmp == null)
			throw new Exception("Error primero debe crear un nuevo Pedido.");
		if (idplato == null || idplato.intValue() < 0)
			throw new Exception("Error debe especificar el codigo del producto.");
		if (cantidad == null || cantidad.intValue() <= 0)
			throw new Exception("Error debe especificar la cantidad del producto.");

		// buscamos el producto:
		p = findPlatoById(idplato);
		// creamos un nuevo detalle y llenamos sus propiedades:
		d = new TabVtsDetallePedido();
		valorTotal = cantidad * p.getValorplato().intValue();
		d.setTabVtsPedido(pedidoTmp);
		d.setCantidaddetallepedido(cantidad);
		d.setValorunitariodetallepedido(p.getValorplato());
		d.setTabVtsPlato(p);
		d.setValorTotaldetallepedido(new BigDecimal(valorTotal));
		pedidoTmp.getTabVtsDetallePedidos().add(d);
		

		// verificamos los campos calculados:
		calcularPedidoTmp(pedidoTmp);

	}

	public void guardarPedidoTemporal(TabCajTransaccion transaccionTmp, TabVtsPedido pedidoTmp) throws Exception {

		if (pedidoTmp == null)
			throw new Exception("Debe crear un pedido primero.");
		if (pedidoTmp.getTabVtsDetallePedidos() == null || pedidoTmp.getTabVtsDetallePedidos().size() == 0)
			throw new Exception("Debe ingresar los productos en el pedido.");
		if (pedidoTmp.getMesa() == 0)
			throw new Exception("Debe registrar la mesa.");

		pedidoTmp.setFechapedido(new Date());
		transaccionTmp.setValortransaccion(pedidoTmp.getTotal());

		// obtenemos el numero del nuevo pedido:
		int contPedidos;
		contPedidos = getContPedidos();
		contPedidos++;
		pedidoTmp.setIdpedido(contPedidos);

		// verificamos los campos calculados:
		calcularPedidoTmp(pedidoTmp);

		// guardamos el pedido completa en la bdd:
		em.persist(transaccionTmp);

		// actualizamos los parametros contadores del pedido:
		actualizarContPedidos(contPedidos);
		pedidoTmp = null;

	}

}
