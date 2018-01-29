package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the tab_vts_platos database table.
 * 
 */
@Entity
@Table(name="tab_vts_platos")
@NamedQuery(name="TabVtsPlato.findAll", query="SELECT t FROM TabVtsPlato t")
public class TabVtsPlato implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_VTS_PLATOS_IDPLATO_GENERATOR", sequenceName="TAB_VTS_PLATOS_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_VTS_PLATOS_IDPLATO_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idplato;

	@Column(length=300)
	private String descripccionplato;

	private byte[] fotoplato;

	@Column(length=50)
	private String nombreplato;

	@Column(precision=10, scale=2)
	private BigDecimal valorplato;

	//bi-directional many-to-one association to TabVtsDetallePedido
	@OneToMany(mappedBy="tabVtsPlato")
	private List<TabVtsDetallePedido> tabVtsDetallePedidos;

	//bi-directional many-to-one association to TabVtsDetalleVenta
	@OneToMany(mappedBy="tabVtsPlato")
	private List<TabVtsDetalleVenta> tabVtsDetalleVentas;

	public TabVtsPlato() {
	}

	public Integer getIdplato() {
		return this.idplato;
	}

	public void setIdplato(Integer idplato) {
		this.idplato = idplato;
	}

	public String getDescripccionplato() {
		return this.descripccionplato;
	}

	public void setDescripccionplato(String descripccionplato) {
		this.descripccionplato = descripccionplato;
	}

	public byte[] getFotoplato() {
		return this.fotoplato;
	}

	public void setFotoplato(byte[] fotoplato) {
		this.fotoplato = fotoplato;
	}

	public String getNombreplato() {
		return this.nombreplato;
	}

	public void setNombreplato(String nombreplato) {
		this.nombreplato = nombreplato;
	}

	public BigDecimal getValorplato() {
		return this.valorplato;
	}

	public void setValorplato(BigDecimal valorplato) {
		this.valorplato = valorplato;
	}

	public List<TabVtsDetallePedido> getTabVtsDetallePedidos() {
		return this.tabVtsDetallePedidos;
	}

	public void setTabVtsDetallePedidos(List<TabVtsDetallePedido> tabVtsDetallePedidos) {
		this.tabVtsDetallePedidos = tabVtsDetallePedidos;
	}

	public TabVtsDetallePedido addTabVtsDetallePedido(TabVtsDetallePedido tabVtsDetallePedido) {
		getTabVtsDetallePedidos().add(tabVtsDetallePedido);
		tabVtsDetallePedido.setTabVtsPlato(this);

		return tabVtsDetallePedido;
	}

	public TabVtsDetallePedido removeTabVtsDetallePedido(TabVtsDetallePedido tabVtsDetallePedido) {
		getTabVtsDetallePedidos().remove(tabVtsDetallePedido);
		tabVtsDetallePedido.setTabVtsPlato(null);

		return tabVtsDetallePedido;
	}

	public List<TabVtsDetalleVenta> getTabVtsDetalleVentas() {
		return this.tabVtsDetalleVentas;
	}

	public void setTabVtsDetalleVentas(List<TabVtsDetalleVenta> tabVtsDetalleVentas) {
		this.tabVtsDetalleVentas = tabVtsDetalleVentas;
	}

	public TabVtsDetalleVenta addTabVtsDetalleVenta(TabVtsDetalleVenta tabVtsDetalleVenta) {
		getTabVtsDetalleVentas().add(tabVtsDetalleVenta);
		tabVtsDetalleVenta.setTabVtsPlato(this);

		return tabVtsDetalleVenta;
	}

	public TabVtsDetalleVenta removeTabVtsDetalleVenta(TabVtsDetalleVenta tabVtsDetalleVenta) {
		getTabVtsDetalleVentas().remove(tabVtsDetalleVenta);
		tabVtsDetalleVenta.setTabVtsPlato(null);

		return tabVtsDetalleVenta;
	}

}