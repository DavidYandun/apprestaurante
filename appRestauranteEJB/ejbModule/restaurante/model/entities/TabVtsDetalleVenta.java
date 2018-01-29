package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the tab_vts_detalle_venta database table.
 * 
 */
@Entity
@Table(name="tab_vts_detalle_venta")
@NamedQuery(name="TabVtsDetalleVenta.findAll", query="SELECT t FROM TabVtsDetalleVenta t")
public class TabVtsDetalleVenta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_VTS_DETALLE_VENTA_IDDETALLEVENTA_GENERATOR", sequenceName="TAB_VTS_DETALLE_VENTA_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_VTS_DETALLE_VENTA_IDDETALLEVENTA_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer iddetalleventa;

	private Integer cantidaddetalleventa;

	@Column(precision=10, scale=2)
	private BigDecimal valortotalventa;

	@Column(precision=10, scale=2)
	private BigDecimal valorunitarioventa;

	//bi-directional many-to-one association to TabInvProducto
	@ManyToOne
	@JoinColumn(name="idproducto")
	private TabInvProducto tabInvProducto;

	//bi-directional many-to-one association to TabVtsFacturaVenta
	@ManyToOne
	@JoinColumn(name="idfacturaventa")
	private TabVtsFacturaVenta tabVtsFacturaVenta;

	//bi-directional many-to-one association to TabVtsPlato
	@ManyToOne
	@JoinColumn(name="idplato")
	private TabVtsPlato tabVtsPlato;

	public TabVtsDetalleVenta() {
	}

	public Integer getIddetalleventa() {
		return this.iddetalleventa;
	}

	public void setIddetalleventa(Integer iddetalleventa) {
		this.iddetalleventa = iddetalleventa;
	}

	public Integer getCantidaddetalleventa() {
		return this.cantidaddetalleventa;
	}

	public void setCantidaddetalleventa(Integer cantidaddetalleventa) {
		this.cantidaddetalleventa = cantidaddetalleventa;
	}

	public BigDecimal getValortotalventa() {
		return this.valortotalventa;
	}

	public void setValortotalventa(BigDecimal valortotalventa) {
		this.valortotalventa = valortotalventa;
	}

	public BigDecimal getValorunitarioventa() {
		return this.valorunitarioventa;
	}

	public void setValorunitarioventa(BigDecimal valorunitarioventa) {
		this.valorunitarioventa = valorunitarioventa;
	}

	public TabInvProducto getTabInvProducto() {
		return this.tabInvProducto;
	}

	public void setTabInvProducto(TabInvProducto tabInvProducto) {
		this.tabInvProducto = tabInvProducto;
	}

	public TabVtsFacturaVenta getTabVtsFacturaVenta() {
		return this.tabVtsFacturaVenta;
	}

	public void setTabVtsFacturaVenta(TabVtsFacturaVenta tabVtsFacturaVenta) {
		this.tabVtsFacturaVenta = tabVtsFacturaVenta;
	}

	public TabVtsPlato getTabVtsPlato() {
		return this.tabVtsPlato;
	}

	public void setTabVtsPlato(TabVtsPlato tabVtsPlato) {
		this.tabVtsPlato = tabVtsPlato;
	}

}