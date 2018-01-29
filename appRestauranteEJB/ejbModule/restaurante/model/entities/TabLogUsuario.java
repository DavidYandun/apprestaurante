package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tab_log_usuario database table.
 * 
 */
@Entity
@Table(name="tab_log_usuario")
@NamedQuery(name="TabLogUsuario.findAll", query="SELECT t FROM TabLogUsuario t")
public class TabLogUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_LOG_USUARIO_IDUSUARIO_GENERATOR", sequenceName="TAB_LOG_USUARIO_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_LOG_USUARIO_IDUSUARIO_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idusuario;

	@Column(length=50)
	private String correousuario;

	private Boolean estadousuario;

	@Column(length=50)
	private String nombreusuario;

	@Column(length=50)
	private String passwordusuario;

	//bi-directional many-to-one association to TabCajTransaccion
	@OneToMany(mappedBy="tabLogUsuario")
	private List<TabCajTransaccion> tabCajTransaccions;

	//bi-directional many-to-one association to TabLogTipoUsuario
	@ManyToOne
	@JoinColumn(name="idtipousuario")
	private TabLogTipoUsuario tabLogTipoUsuario;

	public TabLogUsuario() {
	}

	public Integer getIdusuario() {
		return this.idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getCorreousuario() {
		return this.correousuario;
	}

	public void setCorreousuario(String correousuario) {
		this.correousuario = correousuario;
	}

	public Boolean getEstadousuario() {
		return this.estadousuario;
	}

	public void setEstadousuario(Boolean estadousuario) {
		this.estadousuario = estadousuario;
	}

	public String getNombreusuario() {
		return this.nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getPasswordusuario() {
		return this.passwordusuario;
	}

	public void setPasswordusuario(String passwordusuario) {
		this.passwordusuario = passwordusuario;
	}

	public List<TabCajTransaccion> getTabCajTransaccions() {
		return this.tabCajTransaccions;
	}

	public void setTabCajTransaccions(List<TabCajTransaccion> tabCajTransaccions) {
		this.tabCajTransaccions = tabCajTransaccions;
	}

	public TabCajTransaccion addTabCajTransaccion(TabCajTransaccion tabCajTransaccion) {
		getTabCajTransaccions().add(tabCajTransaccion);
		tabCajTransaccion.setTabLogUsuario(this);

		return tabCajTransaccion;
	}

	public TabCajTransaccion removeTabCajTransaccion(TabCajTransaccion tabCajTransaccion) {
		getTabCajTransaccions().remove(tabCajTransaccion);
		tabCajTransaccion.setTabLogUsuario(null);

		return tabCajTransaccion;
	}

	public TabLogTipoUsuario getTabLogTipoUsuario() {
		return this.tabLogTipoUsuario;
	}

	public void setTabLogTipoUsuario(TabLogTipoUsuario tabLogTipoUsuario) {
		this.tabLogTipoUsuario = tabLogTipoUsuario;
	}

}