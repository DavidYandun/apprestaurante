package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tab_inv_factura_compra database table.
 * 
 */
@Entity
@Table(name="tab_inv_factura_compra")
@NamedQuery(name="TabInvFacturaCompra.findAll", query="SELECT t FROM TabInvFacturaCompra t")
public class TabInvFacturaCompra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idfacturacompra;

	private Boolean estadofacturacompra;

	@Temporal(TemporalType.DATE)
	private Date fechafacturacompra;

	@Column(precision=10, scale=2)
	private BigDecimal ivafacturacompra;

	@Column(precision=10, scale=2)
	private BigDecimal subtotalfacturacompra;

	@Column(precision=10, scale=2)
	private BigDecimal totalfacturacompra;

	//bi-directional many-to-one association to TabInvDetalleCompra
	@OneToMany(mappedBy="tabInvFacturaCompra")
	private List<TabInvDetalleCompra> tabInvDetalleCompras;

	//bi-directional many-to-one association to TabInvProveedor
	@ManyToOne
	@JoinColumn(name="idproveedor")
	private TabInvProveedor tabInvProveedor;

	public TabInvFacturaCompra() {
	}

	public Integer getIdfacturacompra() {
		return this.idfacturacompra;
	}

	public void setIdfacturacompra(Integer idfacturacompra) {
		this.idfacturacompra = idfacturacompra;
	}

	public Boolean getEstadofacturacompra() {
		return this.estadofacturacompra;
	}

	public void setEstadofacturacompra(Boolean estadofacturacompra) {
		this.estadofacturacompra = estadofacturacompra;
	}

	public Date getFechafacturacompra() {
		return this.fechafacturacompra;
	}

	public void setFechafacturacompra(Date fechafacturacompra) {
		this.fechafacturacompra = fechafacturacompra;
	}

	public BigDecimal getIvafacturacompra() {
		return this.ivafacturacompra;
	}

	public void setIvafacturacompra(BigDecimal ivafacturacompra) {
		this.ivafacturacompra = ivafacturacompra;
	}

	public BigDecimal getSubtotalfacturacompra() {
		return this.subtotalfacturacompra;
	}

	public void setSubtotalfacturacompra(BigDecimal subtotalfacturacompra) {
		this.subtotalfacturacompra = subtotalfacturacompra;
	}

	public BigDecimal getTotalfacturacompra() {
		return this.totalfacturacompra;
	}

	public void setTotalfacturacompra(BigDecimal totalfacturacompra) {
		this.totalfacturacompra = totalfacturacompra;
	}

	public List<TabInvDetalleCompra> getTabInvDetalleCompras() {
		return this.tabInvDetalleCompras;
	}

	public void setTabInvDetalleCompras(List<TabInvDetalleCompra> tabInvDetalleCompras) {
		this.tabInvDetalleCompras = tabInvDetalleCompras;
	}

	public TabInvDetalleCompra addTabInvDetalleCompra(TabInvDetalleCompra tabInvDetalleCompra) {
		getTabInvDetalleCompras().add(tabInvDetalleCompra);
		tabInvDetalleCompra.setTabInvFacturaCompra(this);

		return tabInvDetalleCompra;
	}

	public TabInvDetalleCompra removeTabInvDetalleCompra(TabInvDetalleCompra tabInvDetalleCompra) {
		getTabInvDetalleCompras().remove(tabInvDetalleCompra);
		tabInvDetalleCompra.setTabInvFacturaCompra(null);

		return tabInvDetalleCompra;
	}

	public TabInvProveedor getTabInvProveedor() {
		return this.tabInvProveedor;
	}

	public void setTabInvProveedor(TabInvProveedor tabInvProveedor) {
		this.tabInvProveedor = tabInvProveedor;
	}

}