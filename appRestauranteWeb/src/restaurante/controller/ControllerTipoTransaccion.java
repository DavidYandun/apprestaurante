package restaurante.controller;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import restaurante.model.entities.TabCajTipoTransaccion;
import restaurante.model.manager.ManagerTipoTransaccion;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerTipoTransaccion {

	private int idtipo;
	private String nombretipo;
	private String descripciontipo;
	private List<TabCajTipoTransaccion> lista;

	@EJB
	ManagerTipoTransaccion managerTipoTransaccion;
	
	@PostConstruct
	public void iniciar() {
		lista = managerTipoTransaccion.findAllTipos();
	}

	public void AgregarTipo() {
		try {
			managerTipoTransaccion.agregarTipoTransaccion(nombretipo, descripciontipo);
			lista = managerTipoTransaccion.findAllTipos();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void CargarTipo(TabCajTipoTransaccion tipotransaccion) {
		idtipo = tipotransaccion.getIdtipotransaccion();
		nombretipo = tipotransaccion.getNombretipotransaccion();
		descripciontipo = tipotransaccion.getDescripciontransaccion();
	}

	public void EditarTipo() {
		try {
			managerTipoTransaccion.editarTipoTransaccion(idtipo, nombretipo, descripciontipo);
			lista = managerTipoTransaccion.findAllTipos();
			JSFUtil.crearMensajeInfo("Tipo editado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public int getIdtipo() {
		return idtipo;
	}

	public void setIdtipo(int idtipo) {
		this.idtipo = idtipo;
	}

	public String getNombretipo() {
		return nombretipo;
	}

	public void setNombretipo(String nombretipo) {
		this.nombretipo = nombretipo;
	}

	public String getDescripciontipo() {
		return descripciontipo;
	}

	public void setDescripciontipo(String descripciontipo) {
		this.descripciontipo = descripciontipo;
	}

	public List<TabCajTipoTransaccion> getLista() {
		return lista;
	}

	public void setLista(List<TabCajTipoTransaccion> lista) {
		this.lista = lista;
	}
	
	
}
