package restaurante.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import restaurante.model.entities.TabCajTransaccion;
import restaurante.model.entities.TabVtsPedido;
import restaurante.model.manager.ManagerPedido;

@SessionScoped
@ManagedBean

public class ControllerPedido {
	@EJB
	private ManagerPedido managerPedido;

	private int idpedido;
	private int cantidad;
	private int idproducto;
	private int idplato;
	private int mesa;
	private int idusuario;
	private TabVtsPedido pedidoTemp;
	private TabCajTransaccion transTemp;

	public void AgregarDetalle() {
		pedidoTemp = managerPedido.agregarDetallePedido(pedidoTemp, idproducto, idplato, cantidad);
	}

	public void GuardarPedido() {
		try {
			pedidoTemp = managerPedido.guardarPedido(pedidoTemp, mesa, idpedido, idusuario);
			System.out.println("Pedido guardado: " + pedidoTemp.getIdpedido());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getIdpedido() {
		return idpedido;
	}

	public void setIdpedido(int idpedido) {
		this.idpedido = idpedido;
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

	public int getIdplato() {
		return idplato;
	}

	public void setIdplato(int idplato) {
		this.idplato = idplato;
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

	public TabVtsPedido getPedidoTemp() {
		return pedidoTemp;
	}

	public void setPedidoTemp(TabVtsPedido pedidoTemp) {
		this.pedidoTemp = pedidoTemp;
	}

	public TabCajTransaccion getTransTemp() {
		return transTemp;
	}

	public void setTransTemp(TabCajTransaccion transTemp) {
		this.transTemp = transTemp;
	}

}
