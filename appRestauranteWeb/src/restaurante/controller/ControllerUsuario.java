package restaurante.controller;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import restaurante.model.entities.TabLogUsuario;
import restaurante.model.manager.ManagerLogin;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerUsuario {
	private String idUsuario;
	private int idTipoUsuario;
	private String nombreUsuario;
	private String correoUsuario;
	private String passwordUsuario;
	private TabLogUsuario u;
	private boolean respuesta;

	@EJB
	private ManagerLogin managerUsuarios;

	public String actionLogin() {
		try {
			FacesContext contex = FacesContext.getCurrentInstance();
			respuesta = managerUsuarios.validarLogin(nombreUsuario, correoUsuario, passwordUsuario);
			u = managerUsuarios.findUsuario(nombreUsuario, correoUsuario);
			JSFUtil.crearMensajeInfo("Login correcto");
			// verificamos si el acceso es con admin:
			if (u.getTabLogTipoUsuario().getTipousuario().equals("admin")) {
				contex.getExternalContext().redirect("Caja diaria/tipotransaccion.xhtml");
			}
			contex.getExternalContext().redirect("Punto de venta/cliente.xhtml");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdTipoUsuario() {
		return idTipoUsuario;
	}

	public void setIdTipoUsuario(int idTipoUsuario) {
		this.idTipoUsuario = idTipoUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreoUsuario() {
		return correoUsuario;
	}

	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}

	public String getPasswordUsuario() {
		return passwordUsuario;
	}

	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}

	public TabLogUsuario getU() {
		return u;
	}

	public void setU(TabLogUsuario u) {
		this.u = u;
	}

	public boolean isRespuesta() {
		return respuesta;
	}

	public void setRespuesta(boolean respuesta) {
		this.respuesta = respuesta;
	}

}
