package restaurante.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tab_log_tipo_usuario database table.
 * 
 */
@Entity
@Table(name="tab_log_tipo_usuario")
@NamedQuery(name="TabLogTipoUsuario.findAll", query="SELECT t FROM TabLogTipoUsuario t")
public class TabLogTipoUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAB_LOG_TIPO_USUARIO_IDTIPOUSUARIO_GENERATOR", sequenceName="TAB_LOG_TIPO_USUARIO_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAB_LOG_TIPO_USUARIO_IDTIPOUSUARIO_GENERATOR")
	@Column(unique=true, nullable=false)
	private Integer idtipousuario;

	@Column(length=100)
	private String descripciontipousuario;

	@Column(length=4)
	private String tipousuario;

	//bi-directional many-to-one association to TabLogUsuario
	@OneToMany(mappedBy="tabLogTipoUsuario")
	private List<TabLogUsuario> tabLogUsuarios;

	public TabLogTipoUsuario() {
	}

	public Integer getIdtipousuario() {
		return this.idtipousuario;
	}

	public void setIdtipousuario(Integer idtipousuario) {
		this.idtipousuario = idtipousuario;
	}

	public String getDescripciontipousuario() {
		return this.descripciontipousuario;
	}

	public void setDescripciontipousuario(String descripciontipousuario) {
		this.descripciontipousuario = descripciontipousuario;
	}

	public String getTipousuario() {
		return this.tipousuario;
	}

	public void setTipousuario(String tipousuario) {
		this.tipousuario = tipousuario;
	}

	public List<TabLogUsuario> getTabLogUsuarios() {
		return this.tabLogUsuarios;
	}

	public void setTabLogUsuarios(List<TabLogUsuario> tabLogUsuarios) {
		this.tabLogUsuarios = tabLogUsuarios;
	}

	public TabLogUsuario addTabLogUsuario(TabLogUsuario tabLogUsuario) {
		getTabLogUsuarios().add(tabLogUsuario);
		tabLogUsuario.setTabLogTipoUsuario(this);

		return tabLogUsuario;
	}

	public TabLogUsuario removeTabLogUsuario(TabLogUsuario tabLogUsuario) {
		getTabLogUsuarios().remove(tabLogUsuario);
		tabLogUsuario.setTabLogTipoUsuario(null);

		return tabLogUsuario;
	}

}