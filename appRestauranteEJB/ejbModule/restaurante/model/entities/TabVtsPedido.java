package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tab_vts_pedido database table.
 * 
 */
@Entity
@Table(name="tab_vts_pedido")
@NamedQuery(name="TabVtsPedido.findAll", query="SELECT t FROM TabVtsPedido t")
public class TabVtsPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private Integer idpedido;

	@Temporal(TemporalType.DATE)
	private Date fechapedido;

	private Integer mesa;

	@Column(precision=10, scale=2)
	private BigDecimal total;

	//bi-directional many-to-one association to TabVtsDetallePedido
	@OneToMany(mappedBy="tabVtsPedido",cascade=CascadeType.ALL)
	private List<TabVtsDetallePedido> tabVtsDetallePedidos;

	//bi-directional many-to-one association to TabCajTransaccion
	@ManyToOne
	@JoinColumn(name="idtransaccion")
	private TabCajTransaccion tabCajTransaccion;

	public TabVtsPedido() {
	}

	public Integer getIdpedido() {
		return this.idpedido;
	}

	public void setIdpedido(Integer idpedido) {
		this.idpedido = idpedido;
	}

	public Date getFechapedido() {
		return this.fechapedido;
	}

	public void setFechapedido(Date fechapedido) {
		this.fechapedido = fechapedido;
	}

	public Integer getMesa() {
		return this.mesa;
	}

	public void setMesa(Integer mesa) {
		this.mesa = mesa;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<TabVtsDetallePedido> getTabVtsDetallePedidos() {
		return this.tabVtsDetallePedidos;
	}

	public void setTabVtsDetallePedidos(List<TabVtsDetallePedido> tabVtsDetallePedidos) {
		this.tabVtsDetallePedidos = tabVtsDetallePedidos;
	}

	public TabVtsDetallePedido addTabVtsDetallePedido(TabVtsDetallePedido tabVtsDetallePedido) {
		getTabVtsDetallePedidos().add(tabVtsDetallePedido);
		tabVtsDetallePedido.setTabVtsPedido(this);

		return tabVtsDetallePedido;
	}

	public TabVtsDetallePedido removeTabVtsDetallePedido(TabVtsDetallePedido tabVtsDetallePedido) {
		getTabVtsDetallePedidos().remove(tabVtsDetallePedido);
		tabVtsDetallePedido.setTabVtsPedido(null);

		return tabVtsDetallePedido;
	}

	public TabCajTransaccion getTabCajTransaccion() {
		return this.tabCajTransaccion;
	}

	public void setTabCajTransaccion(TabCajTransaccion tabCajTransaccion) {
		this.tabCajTransaccion = tabCajTransaccion;
	}

}