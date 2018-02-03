package restaurante.controller;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import restaurante.model.entities.TabCajCajero;
import restaurante.model.manager.ManagerCajaDiaria;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerCajaDiaria {

	private Date fecha_cajero;
	private BigDecimal inicioCajero;
	private BigDecimal cierreCajero;
	private List<TabCajCajero> lista;

	@EJB
	ManagerCajaDiaria managerCajaDiaria;
	
	@PostConstruct
	public void iniciar() {
		lista = managerCajaDiaria.findAllCajas();
	}

	public void AgregarCajaDiaria() {
		try {
			managerCajaDiaria.agregarCajaDiaria(inicioCajero, cierreCajero);
			lista = managerCajaDiaria.findAllCajas();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void CargarCajaDiaria(TabCajCajero cajadiaria) {
		fecha_cajero = cajadiaria.getFechaCajero();
		inicioCajero = cajadiaria.getIniciocajero();
		cierreCajero = cajadiaria.getCierrecajero();
	}

	public void EditarCajaDiaria() {
		try {
			managerCajaDiaria.editarCajaDiaria(fecha_cajero, inicioCajero, cierreCajero);
			lista = managerCajaDiaria.findAllCajas();
			JSFUtil.crearMensajeInfo("Caja Diaria editada correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public Date getFecha_cajero() {
		return fecha_cajero;
	}

	public void setFecha_cajero(Date fecha_cajero) {
		this.fecha_cajero = fecha_cajero;
	}

	public BigDecimal getInicioCajero() {
		return inicioCajero;
	}

	public void setInicioCajero(BigDecimal inicioCajero) {
		this.inicioCajero = inicioCajero;
	}

	public BigDecimal getCierreCajero() {
		return cierreCajero;
	}

	public void setCierreCajero(BigDecimal cierreCajero) {
		this.cierreCajero = cierreCajero;
	}

	public List<TabCajCajero> getLista() {
		return lista;
	}

	public void setLista(List<TabCajCajero> lista) {
		this.lista = lista;
	}
		
}
