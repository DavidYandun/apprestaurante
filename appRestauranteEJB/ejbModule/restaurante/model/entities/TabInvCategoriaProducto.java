package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tab_inv_categoria_producto database table.
 * 
 */
@Entity
@Table(name="tab_inv_categoria_producto")
@NamedQuery(name="TabInvCategoriaProducto.findAll", query="SELECT t FROM TabInvCategoriaProducto t")
public class TabInvCategoriaProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_INV_CATEGORIA_PRODUCTO_IDCATEGORIA_GENERATOR", sequenceName="TAB_INV_CATEGORIA_PRODUCTO_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_INV_CATEGORIA_PRODUCTO_IDCATEGORIA_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idcategoria;

	@Column(length=200)
	private String descripcioncategoria;

	@Column(length=50)
	private String nombrecategoria;

	//bi-directional many-to-one association to TabInvProducto
	@OneToMany(mappedBy="tabInvCategoriaProducto")
	private List<TabInvProducto> tabInvProductos;

	public TabInvCategoriaProducto() {
	}

	public Integer getIdcategoria() {
		return this.idcategoria;
	}

	public void setIdcategoria(Integer idcategoria) {
		this.idcategoria = idcategoria;
	}

	public String getDescripcioncategoria() {
		return this.descripcioncategoria;
	}

	public void setDescripcioncategoria(String descripcioncategoria) {
		this.descripcioncategoria = descripcioncategoria;
	}

	public String getNombrecategoria() {
		return this.nombrecategoria;
	}

	public void setNombrecategoria(String nombrecategoria) {
		this.nombrecategoria = nombrecategoria;
	}

	public List<TabInvProducto> getTabInvProductos() {
		return this.tabInvProductos;
	}

	public void setTabInvProductos(List<TabInvProducto> tabInvProductos) {
		this.tabInvProductos = tabInvProductos;
	}

	public TabInvProducto addTabInvProducto(TabInvProducto tabInvProducto) {
		getTabInvProductos().add(tabInvProducto);
		tabInvProducto.setTabInvCategoriaProducto(this);

		return tabInvProducto;
	}

	public TabInvProducto removeTabInvProducto(TabInvProducto tabInvProducto) {
		getTabInvProductos().remove(tabInvProducto);
		tabInvProducto.setTabInvCategoriaProducto(null);

		return tabInvProducto;
	}

}