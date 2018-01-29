package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the tab_caj_cajero database table.
 * 
 */
@Entity
@Table(name="tab_caj_cajero")
@NamedQuery(name="TabCajCajero.findAll", query="SELECT t FROM TabCajCajero t")
public class TabCajCajero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Temporal(TemporalType.DATE)
	@Column(name="fecha_cajero", unique=true, nullable=false)
	private Date fechaCajero;

	@Column(precision=10, scale=2)
	private BigDecimal cierrecajero;

	@Column(precision=10, scale=2)
	private BigDecimal iniciocajero;

	//bi-directional many-to-one association to TabCajTransaccion
	@OneToMany(mappedBy="tabCajCajero")
	private List<TabCajTransaccion> tabCajTransaccions;

	public TabCajCajero() {
	}

	public Date getFechaCajero() {
		return this.fechaCajero;
	}

	public void setFechaCajero(Date fechaCajero) {
		this.fechaCajero = fechaCajero;
	}

	public BigDecimal getCierrecajero() {
		return this.cierrecajero;
	}

	public void setCierrecajero(BigDecimal cierrecajero) {
		this.cierrecajero = cierrecajero;
	}

	public BigDecimal getIniciocajero() {
		return this.iniciocajero;
	}

	public void setIniciocajero(BigDecimal iniciocajero) {
		this.iniciocajero = iniciocajero;
	}

	public List<TabCajTransaccion> getTabCajTransaccions() {
		return this.tabCajTransaccions;
	}

	public void setTabCajTransaccions(List<TabCajTransaccion> tabCajTransaccions) {
		this.tabCajTransaccions = tabCajTransaccions;
	}

	public TabCajTransaccion addTabCajTransaccion(TabCajTransaccion tabCajTransaccion) {
		getTabCajTransaccions().add(tabCajTransaccion);
		tabCajTransaccion.setTabCajCajero(this);

		return tabCajTransaccion;
	}

	public TabCajTransaccion removeTabCajTransaccion(TabCajTransaccion tabCajTransaccion) {
		getTabCajTransaccions().remove(tabCajTransaccion);
		tabCajTransaccion.setTabCajCajero(null);

		return tabCajTransaccion;
	}

}