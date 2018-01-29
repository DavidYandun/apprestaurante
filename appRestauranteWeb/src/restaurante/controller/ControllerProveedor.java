package restaurante.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import restaurante.model.entities.TabInvProveedor;
import restaurante.model.manager.ManagerProveedor;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerProveedor {

	private Integer idproveedor;
	private String nombreproveedor;
	private String telefonoproveedor;
	private String direccionproveedor;
	private String correoproveedor;
	private List<TabInvProveedor> lista;
	@EJB
	private ManagerProveedor managerProveedor;

	@PostConstruct
	public void iniciar() {
		lista = managerProveedor.findAllProveedor();
	}
	
	public void agregarProveedor() {
		try {
			managerProveedor.agregarproveedor(nombreproveedor, direccionproveedor, telefonoproveedor,
					correoproveedor);
			lista = managerProveedor.findAllProveedor();
			JSFUtil.crearMensajeInfo("Proveedor registrado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		idproveedor = null;
		nombreproveedor = "";
		direccionproveedor = "";
		telefonoproveedor = "";
		correoproveedor = "";
	}

	public void CargarProveedor(TabInvProveedor proveedor) {
		idproveedor = proveedor.getIdproveedor();
		nombreproveedor = proveedor.getNombreproveedor();
		direccionproveedor = proveedor.getDireccionproveedor();
		telefonoproveedor = proveedor.getTelefonoproveedor();
		correoproveedor = proveedor.getCorreoproveedor();
	}

	public void EditarProveedor() {
		try {
			managerProveedor.editarproveedor(idproveedor, nombreproveedor, direccionproveedor, telefonoproveedor,
					correoproveedor);

			lista = managerProveedor.findAllProveedor();
			JSFUtil.crearMensajeInfo("Proveedor con nombre" + nombreproveedor + " editado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		idproveedor = null;
		nombreproveedor = "";
		direccionproveedor = "";
		telefonoproveedor = "";
		correoproveedor = "";
	}

	public Integer getIdproveedor() {
		return idproveedor;
	}

	public void setIdproveedor(Integer idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getNombreproveedor() {
		return nombreproveedor;
	}

	public void setNombreproveedor(String nombreproveedor) {
		this.nombreproveedor = nombreproveedor;
	}

	public String getTelefonoproveedor() {
		return telefonoproveedor;
	}

	public void setTelefonoproveedor(String telefonoproveedor) {
		this.telefonoproveedor = telefonoproveedor;
	}

	public String getDireccionproveedor() {
		return direccionproveedor;
	}

	public void setDireccionproveedor(String direccionproveedor) {
		this.direccionproveedor = direccionproveedor;
	}

	public String getCorreoproveedor() {
		return correoproveedor;
	}

	public void setCorreoproveedor(String correoproveedor) {
		this.correoproveedor = correoproveedor;
	}

	public List<TabInvProveedor> getLista() {
		return lista;
	}

	public void setLista(List<TabInvProveedor> lista) {
		this.lista = lista;
	}

	
	
}
