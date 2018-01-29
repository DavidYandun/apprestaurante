package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the tab_vts_detalle_pedido database table.
 * 
 */
@Entity
@Table(name="tab_vts_detalle_pedido")
@NamedQuery(name="TabVtsDetallePedido.findAll", query="SELECT t FROM TabVtsDetallePedido t")
public class TabVtsDetallePedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_VTS_DETALLE_PEDIDO_IDDETALLEPEDIDO_GENERATOR", sequenceName="TAB_VTS_DETALLE_PEDIDO_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_VTS_DETALLE_PEDIDO_IDDETALLEPEDIDO_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer iddetallepedido;

	private Integer cantidaddetallepedido;

	@Column(name="valor_totaldetallepedido", precision=10, scale=2)
	private BigDecimal valorTotaldetallepedido;

	@Column(precision=10, scale=2)
	private BigDecimal valorunitariodetallepedido;

	//bi-directional many-to-one association to TabInvProducto
	@ManyToOne
	@JoinColumn(name="idproducto")
	private TabInvProducto tabInvProducto;

	//bi-directional many-to-one association to TabVtsPedido
	@ManyToOne
	@JoinColumn(name="idpedido")
	private TabVtsPedido tabVtsPedido;

	//bi-directional many-to-one association to TabVtsPlato
	@ManyToOne
	@JoinColumn(name="idplato")
	private TabVtsPlato tabVtsPlato;

	public TabVtsDetallePedido() {
	}

	public Integer getIddetallepedido() {
		return this.iddetallepedido;
	}

	public void setIddetallepedido(Integer iddetallepedido) {
		this.iddetallepedido = iddetallepedido;
	}

	public Integer getCantidaddetallepedido() {
		return this.cantidaddetallepedido;
	}

	public void setCantidaddetallepedido(Integer cantidaddetallepedido) {
		this.cantidaddetallepedido = cantidaddetallepedido;
	}

	public BigDecimal getValorTotaldetallepedido() {
		return this.valorTotaldetallepedido;
	}

	public void setValorTotaldetallepedido(BigDecimal valorTotaldetallepedido) {
		this.valorTotaldetallepedido = valorTotaldetallepedido;
	}

	public BigDecimal getValorunitariodetallepedido() {
		return this.valorunitariodetallepedido;
	}

	public void setValorunitariodetallepedido(BigDecimal valorunitariodetallepedido) {
		this.valorunitariodetallepedido = valorunitariodetallepedido;
	}

	public TabInvProducto getTabInvProducto() {
		return this.tabInvProducto;
	}

	public void setTabInvProducto(TabInvProducto tabInvProducto) {
		this.tabInvProducto = tabInvProducto;
	}

	public TabVtsPedido getTabVtsPedido() {
		return this.tabVtsPedido;
	}

	public void setTabVtsPedido(TabVtsPedido tabVtsPedido) {
		this.tabVtsPedido = tabVtsPedido;
	}

	public TabVtsPlato getTabVtsPlato() {
		return this.tabVtsPlato;
	}

	public void setTabVtsPlato(TabVtsPlato tabVtsPlato) {
		this.tabVtsPlato = tabVtsPlato;
	}

}