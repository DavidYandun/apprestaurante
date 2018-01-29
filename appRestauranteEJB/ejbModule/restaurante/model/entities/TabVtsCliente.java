package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tab_vts_cliente database table.
 * 
 */
@Entity
@Table(name="tab_vts_cliente")
@NamedQuery(name="TabVtsCliente.findAll", query="SELECT t FROM TabVtsCliente t")
public class TabVtsCliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false, length=20)
	private String idcliente;

	@Column(length=50)
	private String apellidocliente;

	@Column(length=50)
	private String correocliente;

	@Column(length=300)
	private String direccioncliente;

	@Column(length=50)
	private String nombrecliente;

	@Column(length=20)
	private String telefonocliente;

	//bi-directional many-to-one association to TabVtsFacturaVenta
	@OneToMany(mappedBy="tabVtsCliente")
	private List<TabVtsFacturaVenta> tabVtsFacturaVentas;

	public TabVtsCliente() {
	}

	public String getIdcliente() {
		return this.idcliente;
	}

	public void setIdcliente(String idcliente) {
		this.idcliente = idcliente;
	}

	public String getApellidocliente() {
		return this.apellidocliente;
	}

	public void setApellidocliente(String apellidocliente) {
		this.apellidocliente = apellidocliente;
	}

	public String getCorreocliente() {
		return this.correocliente;
	}

	public void setCorreocliente(String correocliente) {
		this.correocliente = correocliente;
	}

	public String getDireccioncliente() {
		return this.direccioncliente;
	}

	public void setDireccioncliente(String direccioncliente) {
		this.direccioncliente = direccioncliente;
	}

	public String getNombrecliente() {
		return this.nombrecliente;
	}

	public void setNombrecliente(String nombrecliente) {
		this.nombrecliente = nombrecliente;
	}

	public String getTelefonocliente() {
		return this.telefonocliente;
	}

	public void setTelefonocliente(String telefonocliente) {
		this.telefonocliente = telefonocliente;
	}

	public List<TabVtsFacturaVenta> getTabVtsFacturaVentas() {
		return this.tabVtsFacturaVentas;
	}

	public void setTabVtsFacturaVentas(List<TabVtsFacturaVenta> tabVtsFacturaVentas) {
		this.tabVtsFacturaVentas = tabVtsFacturaVentas;
	}

	public TabVtsFacturaVenta addTabVtsFacturaVenta(TabVtsFacturaVenta tabVtsFacturaVenta) {
		getTabVtsFacturaVentas().add(tabVtsFacturaVenta);
		tabVtsFacturaVenta.setTabVtsCliente(this);

		return tabVtsFacturaVenta;
	}

	public TabVtsFacturaVenta removeTabVtsFacturaVenta(TabVtsFacturaVenta tabVtsFacturaVenta) {
		getTabVtsFacturaVentas().remove(tabVtsFacturaVenta);
		tabVtsFacturaVenta.setTabVtsCliente(null);

		return tabVtsFacturaVenta;
	}

}