package restaurante.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import restaurante.model.entities.TabCajTransaccion;
import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabVtsPedido;
import restaurante.model.entities.TabVtsPlato;
import restaurante.model.manager.ManagerPedido;
import restaurante.model.manager.ManagerPlato;
import restaurante.model.manager.ManagerProducto;
import restaurante.view.util.JSFUtil;

@SessionScoped
@ManagedBean

public class ControllerPedido {
	@EJB
	private ManagerPedido managerPedido;
	@EJB
	private ManagerProducto managerProducto;

	private int cantidad;

	private int idproducto;
	private int mesa;
	private int idusuario;
	private TabVtsPedido pedidoTmp;
	private TabCajTransaccion transTemp;
	private boolean transaccionTmpGuardada;
	private boolean pedidoTmpGuardada;

	@PostConstruct
	public void iniciar() {
		transTemp = managerPedido.crearTransaccionTmp();
		pedidoTmp = managerPedido.crearPedidoTmp(transTemp);
		cantidad = 1;
		idproducto = 0;
		mesa = 0;
		transaccionTmpGuardada = false;
		pedidoTmpGuardada = false;

	}

	public void crearNuevoPedido() {
		transTemp = managerPedido.crearTransaccionTmp();
		pedidoTmp = managerPedido.crearPedidoTmp(transTemp);
		cantidad = 1;
		idproducto = 0;
		mesa = 0;
		transaccionTmpGuardada = false;
		pedidoTmpGuardada = false;

	}
	public String insertarDetalle() {
		if (transaccionTmpGuardada == true && pedidoTmpGuardada == true) {
			JSFUtil.crearMensajeWarning("La factura ya fue guardada.");
			return "";
		}
		try {
			managerPedido.agregarDetallePedidoTmp(pedidoTmp, idproducto, cantidad);
			idproducto = 0;
			cantidad = 1;
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
		return "";
	}

	public void asignarMesa() {
		if (pedidoTmpGuardada == true) {
			JSFUtil.crearMensajeWarning("El pedido ya fue guardado.");
		}
		try {
			managerPedido.asignarMesaPedidoTmp(pedidoTmp, mesa);
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Por favor asigne una mesa");
			
		}
	}

	public String guardarPedido() {
		if (transaccionTmpGuardada == true && pedidoTmpGuardada == true) {
			JSFUtil.crearMensajeWarning("El pedido ya fue guardada.");
			return "";
		}
		try {
			managerPedido.guardarPedidoTemporal(transTemp, pedidoTmp);

			transaccionTmpGuardada = true;
			pedidoTmpGuardada = true;
			JSFUtil.crearMensajeInfo("Pedido guardado exitosamente");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}

		return "";
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public int getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(int idproducto) {
		this.idproducto = idproducto;
	}

	public int getMesa() {
		return mesa;
	}

	public void setMesa(int mesa) {
		this.mesa = mesa;
	}

	public int getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(int idusuario) {
		this.idusuario = idusuario;
	}

	public TabVtsPedido getPedidoTmp() {
		return pedidoTmp;
	}

	public void setPedidoTmp(TabVtsPedido pedidoTmp) {
		this.pedidoTmp = pedidoTmp;
	}

	public TabCajTransaccion getTransTemp() {
		return transTemp;
	}

	public void setTransTemp(TabCajTransaccion transTemp) {
		this.transTemp = transTemp;
	}
	

	public boolean isTransaccionTmpGuardada() {
		return transaccionTmpGuardada;
	}

	public void setTransaccionTmpGuardada(boolean transaccionTmpGuardada) {
		this.transaccionTmpGuardada = transaccionTmpGuardada;
	}

	public boolean isPedidoTmpGuardada() {
		return pedidoTmpGuardada;
	}

	public void setPedidoTmpGuardada(boolean pedidoTmpGuardada) {
		this.pedidoTmpGuardada = pedidoTmpGuardada;
	}

	public List<SelectItem> getListaPlatoSI() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<TabInvProducto> listadoPlatos = managerProducto.findAllProductos();

		for (TabInvProducto p : listadoPlatos) {
			SelectItem item = new SelectItem(p.getIdproducto(), p.getNombreproducto());
			listadoSI.add(item);
		}
		return listadoSI;
	}
	

}
