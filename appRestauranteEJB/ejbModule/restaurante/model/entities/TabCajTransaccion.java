package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the tab_caj_transaccion database table.
 * 
 */
@Entity
@Table(name="tab_caj_transaccion")
@NamedQuery(name="TabCajTransaccion.findAll", query="SELECT t FROM TabCajTransaccion t")
public class TabCajTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_CAJ_TRANSACCION_IDTRANSACCION_GENERATOR", sequenceName="TAB_CAJ_TRANSACCION_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_CAJ_TRANSACCION_IDTRANSACCION_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idtransaccion;

	@Column(length=300)
	private String descripciontransaccion;

	@Column(precision=10, scale=2)
	private BigDecimal valortransaccion;

	//bi-directional many-to-one association to TabCajCajero
	@ManyToOne
	@JoinColumn(name="fecha_cajero")
	private TabCajCajero tabCajCajero;

	//bi-directional many-to-one association to TabCajTipoTransaccion
	@ManyToOne
	@JoinColumn(name="idtipotransaccion")
	private TabCajTipoTransaccion tabCajTipoTransaccion;

	//bi-directional many-to-one association to TabLogUsuario
	@ManyToOne
	@JoinColumn(name="idusuario")
	private TabLogUsuario tabLogUsuario;

	//bi-directional many-to-one association to TabVtsPedido
	@OneToMany(mappedBy="tabCajTransaccion",cascade=CascadeType.ALL)
	private List<TabVtsPedido> tabVtsPedidos;

	public TabCajTransaccion() {
	}

	public Integer getIdtransaccion() {
		return this.idtransaccion;
	}

	public void setIdtransaccion(Integer idtransaccion) {
		this.idtransaccion = idtransaccion;
	}

	public String getDescripciontransaccion() {
		return this.descripciontransaccion;
	}

	public void setDescripciontransaccion(String descripciontransaccion) {
		this.descripciontransaccion = descripciontransaccion;
	}

	public BigDecimal getValortransaccion() {
		return this.valortransaccion;
	}

	public void setValortransaccion(BigDecimal valortransaccion) {
		this.valortransaccion = valortransaccion;
	}

	public TabCajCajero getTabCajCajero() {
		return this.tabCajCajero;
	}

	public void setTabCajCajero(TabCajCajero tabCajCajero) {
		this.tabCajCajero = tabCajCajero;
	}

	public TabCajTipoTransaccion getTabCajTipoTransaccion() {
		return this.tabCajTipoTransaccion;
	}

	public void setTabCajTipoTransaccion(TabCajTipoTransaccion tabCajTipoTransaccion) {
		this.tabCajTipoTransaccion = tabCajTipoTransaccion;
	}

	public TabLogUsuario getTabLogUsuario() {
		return this.tabLogUsuario;
	}

	public void setTabLogUsuario(TabLogUsuario tabLogUsuario) {
		this.tabLogUsuario = tabLogUsuario;
	}

	public List<TabVtsPedido> getTabVtsPedidos() {
		return this.tabVtsPedidos;
	}

	public void setTabVtsPedidos(List<TabVtsPedido> tabVtsPedidos) {
		this.tabVtsPedidos = tabVtsPedidos;
	}

	public TabVtsPedido addTabVtsPedido(TabVtsPedido tabVtsPedido) {
		getTabVtsPedidos().add(tabVtsPedido);
		tabVtsPedido.setTabCajTransaccion(this);

		return tabVtsPedido;
	}

	public TabVtsPedido removeTabVtsPedido(TabVtsPedido tabVtsPedido) {
		getTabVtsPedidos().remove(tabVtsPedido);
		tabVtsPedido.setTabCajTransaccion(null);

		return tabVtsPedido;
	}

}