package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tab_inv_bodega database table.
 * 
 */
@Entity
@Table(name="tab_inv_bodega")
@NamedQuery(name="TabInvBodega.findAll", query="SELECT t FROM TabInvBodega t")
public class TabInvBodega implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_INV_BODEGA_IDBODEGA_GENERATOR", sequenceName="TAB_INV_BODEGA_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_INV_BODEGA_IDBODEGA_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idbodega;

	@Column(length=50)
	private String nombrebodega;

	//bi-directional many-to-one association to TabInvProducto
	@OneToMany(mappedBy="tabInvBodega")
	private List<TabInvProducto> tabInvProductos;

	public TabInvBodega() {
	}

	public Integer getIdbodega() {
		return this.idbodega;
	}

	public void setIdbodega(Integer idbodega) {
		this.idbodega = idbodega;
	}

	public String getNombrebodega() {
		return this.nombrebodega;
	}

	public void setNombrebodega(String nombrebodega) {
		this.nombrebodega = nombrebodega;
	}

	public List<TabInvProducto> getTabInvProductos() {
		return this.tabInvProductos;
	}

	public void setTabInvProductos(List<TabInvProducto> tabInvProductos) {
		this.tabInvProductos = tabInvProductos;
	}

	public TabInvProducto addTabInvProducto(TabInvProducto tabInvProducto) {
		getTabInvProductos().add(tabInvProducto);
		tabInvProducto.setTabInvBodega(this);

		return tabInvProducto;
	}

	public TabInvProducto removeTabInvProducto(TabInvProducto tabInvProducto) {
		getTabInvProductos().remove(tabInvProducto);
		tabInvProducto.setTabInvBodega(null);

		return tabInvProducto;
	}

}