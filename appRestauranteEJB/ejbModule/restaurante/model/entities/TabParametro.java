package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tab_parametros database table.
 * 
 */
@Entity
@Table(name="tab_parametros")
@NamedQuery(name="TabParametro.findAll", query="SELECT t FROM TabParametro t")
public class TabParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="nombre_parametro", unique=true, nullable=false, length=50)
	private String nombreParametro;

	@Column(name="valor_parametro", nullable=false, length=250)
	private String valorParametro;

	public TabParametro() {
	}

	public String getNombreParametro() {
		return this.nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	public String getValorParametro() {
		return this.valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
	}

}