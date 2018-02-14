package restaurante.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import restaurante.model.entities.TabLogTipoUsuario;
import restaurante.model.entities.TabLogUsuario;
import restaurante.model.manager.ManagerUsuario;
import restaurante.model.util.ModelUtil;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerUsuario {
	private int idUsuario;
	private int idTipoUsuario;
	private String nombreUsuario;
	private String correoUsuario;
	private String passwordUsuario;
	
	private String nombreUsuario1;
	private String correoUsuario1;
	private String passwordUsuario1;
	
	private boolean estadoUsuario=true;
	private TabLogUsuario u;
	private boolean respuesta;
	private List<TabLogUsuario> lista;

	@EJB
	private ManagerUsuario managerUsuarios;

	public String actionLogin() {
		try {
			FacesContext contex = FacesContext.getCurrentInstance();
			respuesta = managerUsuarios.validarLogin(nombreUsuario, correoUsuario, passwordUsuario);
			u = managerUsuarios.findUsuario(nombreUsuario, correoUsuario);
			JSFUtil.crearMensajeInfo("Login correcto");
			// verificamos si el acceso es con admin:
			if (u.getTabLogTipoUsuario().getTipousuario().equals("admin"))
				contex.getExternalContext().redirect("admin/cajadiaria/tipotransaccion.xhtml");
			if (u.getTabLogTipoUsuario().getTipousuario().equals("bodeguero"))
				contex.getExternalContext().redirect("bodeguero/inventario/bodega.xhtml");
			if (u.getTabLogTipoUsuario().getTipousuario().equals("cajero"))
				contex.getExternalContext().redirect("cajero/puntodeventa/pedido.xhtml");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}

	public String actionSalir() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/login?faces-redirect=true";
	}

	public void actionComprobarLogin() {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		try {
			String path = ec.getRequestPathInfo();
			System.out.println("getRequestContextPath(): " + ec.getRequestContextPath());
			System.out.println("getRequestPathInfo(): " + path);
			System.out.println("Id usuario: " + u.getIdusuario());
			if (path.equals("/login.xhtml"))
				return;
			if (ModelUtil.isEmpty(u.getIdusuario() + ""))
				ec.redirect(ec.getRequestContextPath() + "faces/login.xhtml");
			if (!respuesta) {
				ec.redirect(ec.getRequestContextPath() + "faces/login.xhtml");
			} else {
				// si hizo login, verificamos que acceda a paginas permitidas:
				if (u.getTabLogTipoUsuario().getTipousuario().equals("admin")) {
					if (!path.contains("/admin/"))
						ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
					else
						return;
				}
				// caso contrario es un blogger:
				if (u.getTabLogTipoUsuario().getTipousuario().equals("bodeguero")) {
					if (!path.contains("/bodeguero/"))
						ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
					else
						return;
				}
				if (u.getTabLogTipoUsuario().getTipousuario().equals("cajero")) {
					if (!path.contains("/cajero/"))
						ec.redirect(ec.getRequestContextPath() + "/faces/login.xhtml");
					else
						return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostConstruct
	public void iniciar() {
		lista = managerUsuarios.findAllUsuarios();
		
	}

	public void AgregarUsario() {
		try {
			managerUsuarios.agregarusuario(idTipoUsuario, nombreUsuario1, correoUsuario1, passwordUsuario1, estadoUsuario);
			lista = managerUsuarios.findAllUsuarios();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void CargarUsuario(TabLogUsuario usuario) {
		idUsuario = usuario.getIdusuario();
		idTipoUsuario = getIdTipoUsuario();
		nombreUsuario1 = usuario.getNombreusuario();
		correoUsuario1 = usuario.getCorreousuario();
		passwordUsuario1 = usuario.getPasswordusuario();
		estadoUsuario = usuario.getEstadousuario();
	}

	public void EditarUsuario() {
		try {
			managerUsuarios.editarusuario(idUsuario, idTipoUsuario, nombreUsuario1, correoUsuario1, passwordUsuario1,
					estadoUsuario);
			lista = managerUsuarios.findAllUsuarios();
			JSFUtil.crearMensajeInfo("Usuario editado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<SelectItem> getListaUsuariosSI() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<TabLogTipoUsuario> listadoUsuarios = managerUsuarios.findAllTipoUsuarios();

		for (TabLogTipoUsuario c : listadoUsuarios) {
			SelectItem item = new SelectItem(c.getIdtipousuario(), c.getTipousuario());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
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

	public boolean isEstadousuario() {
		return estadoUsuario;
	}

	public void setEstadousuario(boolean estadousuario) {
		this.estadoUsuario = estadousuario;
	}

	public List<TabLogUsuario> getLista() {
		return lista;
	}

	public void setLista(List<TabLogUsuario> lista) {
		this.lista = lista;
	}

	public String getNombreUsuario1() {
		return nombreUsuario1;
	}

	public void setNombreUsuario1(String nombreUsuario1) {
		this.nombreUsuario1 = nombreUsuario1;
	}

	public String getCorreoUsuario1() {
		return correoUsuario1;
	}

	public void setCorreoUsuario1(String correoUsuario1) {
		this.correoUsuario1 = correoUsuario1;
	}

	public String getPasswordUsuario1() {
		return passwordUsuario1;
	}

	public void setPasswordUsuario1(String passwordUsuario1) {
		this.passwordUsuario1 = passwordUsuario1;
	}

	
}