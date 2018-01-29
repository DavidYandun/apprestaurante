package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tab_inv_proveedor database table.
 * 
 */
@Entity
@Table(name="tab_inv_proveedor")
@NamedQuery(name="TabInvProveedor.findAll", query="SELECT t FROM TabInvProveedor t")
public class TabInvProveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_INV_PROVEEDOR_IDPROVEEDOR_GENERATOR", sequenceName="TAB_INV_PROVEEDOR_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_INV_PROVEEDOR_IDPROVEEDOR_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idproveedor;

	@Column(length=50)
	private String correoproveedor;

	@Column(length=100)
	private String direccionproveedor;

	@Column(length=50)
	private String nombreproveedor;

	@Column(length=15)
	private String telefonoproveedor;

	//bi-directional many-to-one association to TabInvFacturaCompra
	@OneToMany(mappedBy="tabInvProveedor")
	private List<TabInvFacturaCompra> tabInvFacturaCompras;

	public TabInvProveedor() {
	}

	public Integer getIdproveedor() {
		return this.idproveedor;
	}

	public void setIdproveedor(Integer idproveedor) {
		this.idproveedor = idproveedor;
	}

	public String getCorreoproveedor() {
		return this.correoproveedor;
	}

	public void setCorreoproveedor(String correoproveedor) {
		this.correoproveedor = correoproveedor;
	}

	public String getDireccionproveedor() {
		return this.direccionproveedor;
	}

	public void setDireccionproveedor(String direccionproveedor) {
		this.direccionproveedor = direccionproveedor;
	}

	public String getNombreproveedor() {
		return this.nombreproveedor;
	}

	public void setNombreproveedor(String nombreproveedor) {
		this.nombreproveedor = nombreproveedor;
	}

	public String getTelefonoproveedor() {
		return this.telefonoproveedor;
	}

	public void setTelefonoproveedor(String telefonoproveedor) {
		this.telefonoproveedor = telefonoproveedor;
	}

	public List<TabInvFacturaCompra> getTabInvFacturaCompras() {
		return this.tabInvFacturaCompras;
	}

	public void setTabInvFacturaCompras(List<TabInvFacturaCompra> tabInvFacturaCompras) {
		this.tabInvFacturaCompras = tabInvFacturaCompras;
	}

	public TabInvFacturaCompra addTabInvFacturaCompra(TabInvFacturaCompra tabInvFacturaCompra) {
		getTabInvFacturaCompras().add(tabInvFacturaCompra);
		tabInvFacturaCompra.setTabInvProveedor(this);

		return tabInvFacturaCompra;
	}

	public TabInvFacturaCompra removeTabInvFacturaCompra(TabInvFacturaCompra tabInvFacturaCompra) {
		getTabInvFacturaCompras().remove(tabInvFacturaCompra);
		tabInvFacturaCompra.setTabInvProveedor(null);

		return tabInvFacturaCompra;
	}

}