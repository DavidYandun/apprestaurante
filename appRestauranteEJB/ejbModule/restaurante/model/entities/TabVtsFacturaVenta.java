package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tab_vts_factura_venta database table.
 * 
 */
@Entity
@Table(name="tab_vts_factura_venta")
@NamedQuery(name="TabVtsFacturaVenta.findAll", query="SELECT t FROM TabVtsFacturaVenta t")
public class TabVtsFacturaVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idfacturaventa;

	@Temporal(TemporalType.DATE)
	private Date fechafacturaventa;

	@Column(precision=10, scale=2)
	private BigDecimal ivafacturaventa;

	@Column(precision=10, scale=2)
	private BigDecimal subtotalfacturaventa;

	@Column(precision=10, scale=2)
	private BigDecimal totalfacturaventa;

	//bi-directional many-to-one association to TabVtsDetalleVenta
	@OneToMany(mappedBy="tabVtsFacturaVenta")
	private List<TabVtsDetalleVenta> tabVtsDetalleVentas;

	//bi-directional many-to-one association to TabVtsCliente
	@ManyToOne
	@JoinColumn(name="idcliente")
	private TabVtsCliente tabVtsCliente;

	public TabVtsFacturaVenta() {
	}

	public Integer getIdfacturaventa() {
		return this.idfacturaventa;
	}

	public void setIdfacturaventa(Integer idfacturaventa) {
		this.idfacturaventa = idfacturaventa;
	}

	public Date getFechafacturaventa() {
		return this.fechafacturaventa;
	}

	public void setFechafacturaventa(Date fechafacturaventa) {
		this.fechafacturaventa = fechafacturaventa;
	}

	public BigDecimal getIvafacturaventa() {
		return this.ivafacturaventa;
	}

	public void setIvafacturaventa(BigDecimal ivafacturaventa) {
		this.ivafacturaventa = ivafacturaventa;
	}

	public BigDecimal getSubtotalfacturaventa() {
		return this.subtotalfacturaventa;
	}

	public void setSubtotalfacturaventa(BigDecimal subtotalfacturaventa) {
		this.subtotalfacturaventa = subtotalfacturaventa;
	}

	public BigDecimal getTotalfacturaventa() {
		return this.totalfacturaventa;
	}

	public void setTotalfacturaventa(BigDecimal totalfacturaventa) {
		this.totalfacturaventa = totalfacturaventa;
	}

	public List<TabVtsDetalleVenta> getTabVtsDetalleVentas() {
		return this.tabVtsDetalleVentas;
	}

	public void setTabVtsDetalleVentas(List<TabVtsDetalleVenta> tabVtsDetalleVentas) {
		this.tabVtsDetalleVentas = tabVtsDetalleVentas;
	}

	public TabVtsDetalleVenta addTabVtsDetalleVenta(TabVtsDetalleVenta tabVtsDetalleVenta) {
		getTabVtsDetalleVentas().add(tabVtsDetalleVenta);
		tabVtsDetalleVenta.setTabVtsFacturaVenta(this);

		return tabVtsDetalleVenta;
	}

	public TabVtsDetalleVenta removeTabVtsDetalleVenta(TabVtsDetalleVenta tabVtsDetalleVenta) {
		getTabVtsDetalleVentas().remove(tabVtsDetalleVenta);
		tabVtsDetalleVenta.setTabVtsFacturaVenta(null);

		return tabVtsDetalleVenta;
	}

	public TabVtsCliente getTabVtsCliente() {
		return this.tabVtsCliente;
	}

	public void setTabVtsCliente(TabVtsCliente tabVtsCliente) {
		this.tabVtsCliente = tabVtsCliente;
	}

}