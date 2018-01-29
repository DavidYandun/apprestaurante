package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tab_caj_tipo_transaccion database table.
 * 
 */
@Entity
@Table(name="tab_caj_tipo_transaccion")
@NamedQuery(name="TabCajTipoTransaccion.findAll", query="SELECT t FROM TabCajTipoTransaccion t")
public class TabCajTipoTransaccion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_CAJ_TIPO_TRANSACCION_IDTIPOTRANSACCION_GENERATOR", sequenceName="TAB_CAJ_TIPO_TRANSACCION_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_CAJ_TIPO_TRANSACCION_IDTIPOTRANSACCION_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idtipotransaccion;

	@Column(length=300)
	private String descripciontransaccion;

	@Column(length=20)
	private String nombretipotransaccion;

	//bi-directional many-to-one association to TabCajTransaccion
	@OneToMany(mappedBy="tabCajTipoTransaccion")
	private List<TabCajTransaccion> tabCajTransaccions;

	public TabCajTipoTransaccion() {
	}

	public Integer getIdtipotransaccion() {
		return this.idtipotransaccion;
	}

	public void setIdtipotransaccion(Integer idtipotransaccion) {
		this.idtipotransaccion = idtipotransaccion;
	}

	public String getDescripciontransaccion() {
		return this.descripciontransaccion;
	}

	public void setDescripciontransaccion(String descripciontransaccion) {
		this.descripciontransaccion = descripciontransaccion;
	}

	public String getNombretipotransaccion() {
		return this.nombretipotransaccion;
	}

	public void setNombretipotransaccion(String nombretipotransaccion) {
		this.nombretipotransaccion = nombretipotransaccion;
	}

	public List<TabCajTransaccion> getTabCajTransaccions() {
		return this.tabCajTransaccions;
	}

	public void setTabCajTransaccions(List<TabCajTransaccion> tabCajTransaccions) {
		this.tabCajTransaccions = tabCajTransaccions;
	}

	public TabCajTransaccion addTabCajTransaccion(TabCajTransaccion tabCajTransaccion) {
		getTabCajTransaccions().add(tabCajTransaccion);
		tabCajTransaccion.setTabCajTipoTransaccion(this);

		return tabCajTransaccion;
	}

	public TabCajTransaccion removeTabCajTransaccion(TabCajTransaccion tabCajTransaccion) {
		getTabCajTransaccions().remove(tabCajTransaccion);
		tabCajTransaccion.setTabCajTipoTransaccion(null);

		return tabCajTransaccion;
	}

}