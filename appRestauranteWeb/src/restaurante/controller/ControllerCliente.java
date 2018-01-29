package restaurante.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import restaurante.model.entities.TabVtsCliente;
import restaurante.model.manager.ManagerCliente;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerCliente {
	private String idcliente;
	private String nombrecliente;
	private String apellidocliente;
	private String direccioncliente;
	private String telefonocliente;
	private String correocliente;
	private List<TabVtsCliente> lista;
	
	@EJB
	private ManagerCliente managerCliente;
	
	@PostConstruct
	public void iniciar() {
		lista = managerCliente.findAllClientes();
	}

	public void AgregarCliente() {
		try {
			managerCliente.agregarCliente(idcliente, nombrecliente, apellidocliente, direccioncliente, telefonocliente,
					correocliente);
			lista = managerCliente.findAllClientes();
			JSFUtil.crearMensajeInfo("Cliente registrado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		idcliente = "";
		nombrecliente = "";
		apellidocliente = "";
		direccioncliente = "";
		telefonocliente = "";
		correocliente = "";
	}
	
	public void CargarCliente(TabVtsCliente cliente) {
		idcliente = cliente.getIdcliente();
		nombrecliente = cliente.getNombrecliente();
		apellidocliente = cliente.getApellidocliente();
		direccioncliente = cliente.getDireccioncliente();
		telefonocliente = cliente.getTelefonocliente();
		correocliente = cliente.getCorreocliente();
	}

	public void EditarCliente() {
		try {
			managerCliente.editarCliente(idcliente, nombrecliente, apellidocliente, direccioncliente, telefonocliente,
					correocliente);
			lista=managerCliente.findAllClientes();
			JSFUtil.crearMensajeInfo("Cliente con cédula" + idcliente + " editado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		idcliente = "";
		nombrecliente = "";
		apellidocliente = "";
		direccioncliente = "";
		telefonocliente = "";
		correocliente = "";
	}

	public String getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}

	public String getNombrecliente() {
		return nombrecliente;
	}

	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}

	public String getApellidocliente() {
		return apellidocliente;
	}

	public void setApellidocliente(String apellidocliente) {
		this.apellidocliente = apellidocliente;
	}

	public String getDireccioncliente() {
		return direccioncliente;
	}

	public void setDireccioncliente(String direccioncliente) {
		this.direccioncliente = direccioncliente;
	}

	public String getTelefonocliente() {
		return telefonocliente;
	}

	public void setTelefonocliente(String telefonocliente) {
		this.telefonocliente = telefonocliente;
	}

	public String getCorreocliente() {
		return correocliente;
	}

	public void setCorreocliente(String correocliente) {
		this.correocliente = correocliente;
	}

	public List<TabVtsCliente> getLista() {
		return lista;
	}

	public void setLista(List<TabVtsCliente> lista) {
		this.lista = lista;
	}
	
	
	
}
