package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the tab_inv_detalle_compra database table.
 * 
 */
@Entity
@Table(name="tab_inv_detalle_compra")
@NamedQuery(name="TabInvDetalleCompra.findAll", query="SELECT t FROM TabInvDetalleCompra t")
public class TabInvDetalleCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_INV_DETALLE_COMPRA_IDDETALLECOMPRA_GENERATOR", sequenceName="TAB_INV_DETALLE_COMPRA_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_INV_DETALLE_COMPRA_IDDETALLECOMPRA_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer iddetallecompra;

	private Integer cantidaddetalle;

	@Column(precision=10, scale=2)
	private BigDecimal valortotalcompra;

	@Column(precision=10, scale=2)
	private BigDecimal valorunitariocompra;

	//bi-directional many-to-one association to TabInvFacturaCompra
	@ManyToOne
	@JoinColumn(name="idfacturacompra")
	private TabInvFacturaCompra tabInvFacturaCompra;

	//bi-directional many-to-one association to TabInvProducto
	@ManyToOne
	@JoinColumn(name="idproducto")
	private TabInvProducto tabInvProducto;

	public TabInvDetalleCompra() {
	}

	public Integer getIddetallecompra() {
		return this.iddetallecompra;
	}

	public void setIddetallecompra(Integer iddetallecompra) {
		this.iddetallecompra = iddetallecompra;
	}

	public Integer getCantidaddetalle() {
		return this.cantidaddetalle;
	}

	public void setCantidaddetalle(Integer cantidaddetalle) {
		this.cantidaddetalle = cantidaddetalle;
	}

	public BigDecimal getValortotalcompra() {
		return this.valortotalcompra;
	}

	public void setValortotalcompra(BigDecimal valortotalcompra) {
		this.valortotalcompra = valortotalcompra;
	}

	public BigDecimal getValorunitariocompra() {
		return this.valorunitariocompra;
	}

	public void setValorunitariocompra(BigDecimal valorunitariocompra) {
		this.valorunitariocompra = valorunitariocompra;
	}

	public TabInvFacturaCompra getTabInvFacturaCompra() {
		return this.tabInvFacturaCompra;
	}

	public void setTabInvFacturaCompra(TabInvFacturaCompra tabInvFacturaCompra) {
		this.tabInvFacturaCompra = tabInvFacturaCompra;
	}

	public TabInvProducto getTabInvProducto() {
		return this.tabInvProducto;
	}

	public void setTabInvProducto(TabInvProducto tabInvProducto) {
		this.tabInvProducto = tabInvProducto;
	}

}