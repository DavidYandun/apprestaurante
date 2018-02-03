package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the tab_inv_producto database table.
 * 
 */
@Entity
@Table(name="tab_inv_producto")
@NamedQuery(name="TabInvProducto.findAll", query="SELECT t FROM TabInvProducto t")
public class TabInvProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_INV_PRODUCTO_IDPRODUCTO_GENERATOR", sequenceName="TAB_INV_PRODUCTO_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_INV_PRODUCTO_IDPRODUCTO_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idproducto;

	@Column(length=200)
	private String descripcionproducto;

	private Boolean estadoproducto;

	@Column(length=50)
	private String nombreproducto;

	@Column(precision=5, scale=2)
	private BigDecimal stock;

	@Column(length=10)
	private String unidadmedida;

	@Column(precision=10, scale=2)
	private BigDecimal valorcompra;

	@Column(precision=10, scale=2)
	private BigDecimal valorventa;

	//bi-directional many-to-one association to TabInvDetalleCompra
	@OneToMany(mappedBy="tabInvProducto")
	private List<TabInvDetalleCompra> tabInvDetalleCompras;

	//bi-directional many-to-one association to TabInvBodega
	@ManyToOne
	@JoinColumn(name="idbodega")
	private TabInvBodega tabInvBodega;

	//bi-directional many-to-one association to TabInvCategoriaProducto
	@ManyToOne
	@JoinColumn(name="idcategoria")
	private TabInvCategoriaProducto tabInvCategoriaProducto;

	//bi-directional many-to-one association to TabVtsDetallePedido
	@OneToMany(mappedBy="tabInvProducto")
	private List<TabVtsDetallePedido> tabVtsDetallePedidos;

	//bi-directional many-to-one association to TabVtsDetalleVenta
	@OneToMany(mappedBy="tabInvProducto")
	private List<TabVtsDetalleVenta> tabVtsDetalleVentas;

	public TabInvProducto() {
	}

	public Integer getIdproducto() {
		return this.idproducto;
	}

	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}

	public String getDescripcionproducto() {
		return this.descripcionproducto;
	}

	public void setDescripcionproducto(String descripcionproducto) {
		this.descripcionproducto = descripcionproducto;
	}

	public Boolean getEstadoproducto() {
		return this.estadoproducto;
	}

	public void setEstadoproducto(Boolean estadoproducto) {
		this.estadoproducto = estadoproducto;
	}

	public String getNombreproducto() {
		return this.nombreproducto;
	}

	public void setNombreproducto(String nombreproducto) {
		this.nombreproducto = nombreproducto;
	}

	public BigDecimal getStock() {
		return this.stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public String getUnidadmedida() {
		return this.unidadmedida;
	}

	public void setUnidadmedida(String unidadmedida) {
		this.unidadmedida = unidadmedida;
	}

	public BigDecimal getValorcompra() {
		return this.valorcompra;
	}

	public void setValorcompra(BigDecimal valorcompra) {
		this.valorcompra = valorcompra;
	}

	public BigDecimal getValorventa() {
		return this.valorventa;
	}

	public void setValorventa(BigDecimal valorventa) {
		this.valorventa = valorventa;
	}

	public List<TabInvDetalleCompra> getTabInvDetalleCompras() {
		return this.tabInvDetalleCompras;
	}

	public void setTabInvDetalleCompras(List<TabInvDetalleCompra> tabInvDetalleCompras) {
		this.tabInvDetalleCompras = tabInvDetalleCompras;
	}

	public TabInvDetalleCompra addTabInvDetalleCompra(TabInvDetalleCompra tabInvDetalleCompra) {
		getTabInvDetalleCompras().add(tabInvDetalleCompra);
		tabInvDetalleCompra.setTabInvProducto(this);

		return tabInvDetalleCompra;
	}

	public TabInvDetalleCompra removeTabInvDetalleCompra(TabInvDetalleCompra tabInvDetalleCompra) {
		getTabInvDetalleCompras().remove(tabInvDetalleCompra);
		tabInvDetalleCompra.setTabInvProducto(null);

		return tabInvDetalleCompra;
	}

	public TabInvBodega getTabInvBodega() {
		return this.tabInvBodega;
	}

	public void setTabInvBodega(TabInvBodega tabInvBodega) {
		this.tabInvBodega = tabInvBodega;
	}

	public TabInvCategoriaProducto getTabInvCategoriaProducto() {
		return this.tabInvCategoriaProducto;
	}

	public void setTabInvCategoriaProducto(TabInvCategoriaProducto tabInvCategoriaProducto) {
		this.tabInvCategoriaProducto = tabInvCategoriaProducto;
	}

	public List<TabVtsDetallePedido> getTabVtsDetallePedidos() {
		return this.tabVtsDetallePedidos;
	}

	public void setTabVtsDetallePedidos(List<TabVtsDetallePedido> tabVtsDetallePedidos) {
		this.tabVtsDetallePedidos = tabVtsDetallePedidos;
	}

	public TabVtsDetallePedido addTabVtsDetallePedido(TabVtsDetallePedido tabVtsDetallePedido) {
		getTabVtsDetallePedidos().add(tabVtsDetallePedido);
		tabVtsDetallePedido.setTabInvProducto(this);

		return tabVtsDetallePedido;
	}

	public TabVtsDetallePedido removeTabVtsDetallePedido(TabVtsDetallePedido tabVtsDetallePedido) {
		getTabVtsDetallePedidos().remove(tabVtsDetallePedido);
		tabVtsDetallePedido.setTabInvProducto(null);

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
		tabVtsDetalleVenta.setTabInvProducto(this);

		return tabVtsDetalleVenta;
	}

	public TabVtsDetalleVenta removeTabVtsDetalleVenta(TabVtsDetalleVenta tabVtsDetalleVenta) {
		getTabVtsDetalleVentas().remove(tabVtsDetalleVenta);
		tabVtsDetalleVenta.setTabInvProducto(null);

		return tabVtsDetalleVenta;
	}

}