package restaurante.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.UploadedFile;

import restaurante.model.entities.TabVtsPlato;
import restaurante.model.manager.ManagerPlato;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerPlato {
	private int idplato;
	private String nombreplato;
	private String descripccionplato;
	private BigDecimal valorplato;
	private UploadedFile fotoplato;
	private List<TabVtsPlato> lista;
	private byte[] foto;
	
	@EJB
	ManagerPlato managerPlato;
	@PostConstruct
	public void iniciar() {
		lista = managerPlato.findAllPlatos();
	}
	
	public void AgregarPlato() {
		try {
			
			byte[] content=fotoplato.getContents();
			setFoto(content);
			
			managerPlato.agregarPlato(nombreplato, descripccionplato, valorplato, foto);
			System.out.println("Imagen subida"+foto);
			
			lista = managerPlato.findAllPlatos();
			JSFUtil.crearMensajeInfo("Plato registrado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		nombreplato = "";
		descripccionplato = "";
		valorplato = null;
		foto = null;

	}
	
	public void CargarPlato(TabVtsPlato plato) {
		idplato = plato.getIdplato();
		nombreplato = plato.getNombreplato();
		descripccionplato = plato.getDescripccionplato();
		valorplato = plato.getValorplato();
		foto = plato.getFotoplato();
	}

	public void EditarPlato() {
		try {
			byte[] content=fotoplato.getContents();
			setFoto(content);
			
			managerPlato.editarPlato(idplato, nombreplato, descripccionplato, valorplato, foto);
			lista = managerPlato.findAllPlatos();
			JSFUtil.crearMensajeInfo("Plato editado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		nombreplato = "";
		descripccionplato = "";
		valorplato = null;
		fotoplato = null;
	}
	
	public int getIdplato() {
		return idplato;
	}
	public void setIdplato(int idplato) {
		this.idplato = idplato;
	}
	public String getNombreplato() {
		return nombreplato;
	}
	public void setNombreplato(String nombreplato) {
		this.nombreplato = nombreplato;
	}
	public String getDescripccionplato() {
		return descripccionplato;
	}
	public void setDescripccionplato(String descripccionplato) {
		this.descripccionplato = descripccionplato;
	}
	public BigDecimal getValorplato() {
		return valorplato;
	}
	public void setValorplato(BigDecimal valorplato) {
		this.valorplato = valorplato;
	}
	public UploadedFile getFotoplato() {
		return fotoplato;
	}
	public void setFotoplato(UploadedFile fotoplato) {
		this.fotoplato = fotoplato;
	}
	public List<TabVtsPlato> getLista() {
		return lista;
	}
	public void setLista(List<TabVtsPlato> lista) {
		this.lista = lista;
	}
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}
