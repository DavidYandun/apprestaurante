package restaurante.controller;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabVtsCliente;
import restaurante.model.entities.TabVtsFacturaVenta;
import restaurante.model.entities.TabVtsPlato;
import restaurante.model.manager.ManagerFactura;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped

public class ControllerFacturaVenta {
	private String idcliente;
	@EJB
	private ManagerFactura managerFactura;
	private Integer idplato;
	private Integer cantidadplato;
	private TabVtsFacturaVenta facturaCabTmp;
	private boolean facturaCabTmpGuardada;

	public ControllerFacturaVenta() {

	}

	/**
	 * Action para la creacion de una factura temporal en memoria. Hace uso del
	 * componente {@link facturacion.model.manager.ManagerFacturacion
	 * ManagerFacturacion} de la capa model.
	 * 
	 * @return outcome para la navegacion.
	 */
	public String crearNuevaFactura() {
		facturaCabTmp = managerFactura.crearFacturaVentaTmp();
		idcliente = null;
		idplato = 0;
		cantidadplato = 0;
		facturaCabTmpGuardada = false;
		return "";
	}

	/**
	 * Action para asignar un cliente a la factura temporal actual. Hace uso del
	 * componente {@link facturacion.model.manager.ManagerFacturacion
	 * ManagerFacturacion} de la capa model.
	 * 
	 * @return outcome para la navegacion.
	 */
	public void asignarCliente() {
		if (facturaCabTmpGuardada == true) {
			JSFUtil.crearMensajeWarning("La factura ya fue guardada.");
		}
		try {
			managerFactura.asignarClienteFacturaTmp(facturaCabTmp, idcliente);
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
	}

	/**
	 * Action que adiciona un item a una factura temporal. Hace uso del componente
	 * {@link model.manager.ManagerFacturacion ManagerFacturacion} de la capa model.
	 * 
	 * @return
	 */
	public String insertarDetalle() {
		if (facturaCabTmpGuardada == true) {
			JSFUtil.crearMensajeWarning("La factura ya fue guardada.");
			return "";
		}
		try {
			managerFactura.agregarDetalleFacturaTmp(facturaCabTmp, idplato, cantidadplato);
			idplato = 0;
			cantidadplato = 0;
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
		return "";
	}

	public String getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}

	public Integer getIdplato() {
		return idplato;
	}

	public void setIdplato(Integer idplato) {
		this.idplato = idplato;
	}

	public Integer getCantidadplato() {
		return cantidadplato;
	}

	public void setCantidadplato(Integer cantidadplato) {
		this.cantidadplato = cantidadplato;
	}

	public TabVtsFacturaVenta getFacturaCabTmp() {
		return facturaCabTmp;
	}

	public void setFacturaCabTmp(TabVtsFacturaVenta facturaCabTmp) {
		this.facturaCabTmp = facturaCabTmp;
	}

	public boolean isFacturaCabTmpGuardada() {
		return facturaCabTmpGuardada;
	}

	public void setFacturaCabTmpGuardada(boolean facturaCabTmpGuardada) {
		this.facturaCabTmpGuardada = facturaCabTmpGuardada;
	}

	/**
	 * Devuelve un listado de componentes SelectItem a partir de un listado de
	 * {@link facturacion.model.dao.entities.Cliente Cliente}.
	 * 
	 * @return listado de SelectItems de clientes.
	 */
	public List<SelectItem> getListaClientesSI() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<TabVtsCliente> listadoClientes = managerFactura.findAllClientes();

		for (TabVtsCliente c : listadoClientes) {
			SelectItem item = new SelectItem(c.getIdcliente(), c.getApellidocliente() + " " + c.getNombrecliente());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	/**
	 * Devuelve un listado de componentes SelectItem a partir de un listado de
	 * {@link facturacion.model.dao.entities.Producto Producto}.
	 * 
	 * @return listado de SelectItems de productos.
	 */
	public List<SelectItem> getListaPlatoSI() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<TabVtsPlato> listadoPlatos = managerFactura.findAllPlatos();

		for (TabVtsPlato p : listadoPlatos) {
			SelectItem item = new SelectItem(p.getIdplato(), p.getNombreplato());
			listadoSI.add(item);
		}
		return listadoSI;
	}

}
