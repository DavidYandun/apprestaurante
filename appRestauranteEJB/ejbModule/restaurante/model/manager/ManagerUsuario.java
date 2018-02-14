package restaurante.model.manager;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabLogTipoUsuario;
import restaurante.model.entities.TabLogUsuario;

/**
 * Session Bean implementation class ManagerProveedor
 */
@Stateless
@LocalBean
public class ManagerUsuario {

	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerUsuario() {
		// TODO Auto-generated constructor stub
	}

	public void agregarusuario(int idtipousuario, String nombreusuario, String correousuario, String passwordusuario,
			boolean estadousuario) throws Exception {
		
		TabLogUsuario u = new TabLogUsuario();
		u.setIdusuario(idtipousuario);
		u.setNombreusuario(nombreusuario);
		u.setCorreousuario(correousuario);
		u.setEstadousuario(estadousuario);
		u.setEstadousuario(estadousuario);
		em.persist(u);
	}

	public TabLogUsuario findusuarioByID(int idusuario) throws Exception {
		TabLogUsuario u = em.find(TabLogUsuario.class, idusuario);
		return u;
	}

	public void editarusuario(int idusuario, int idtipousuario, String nombreusuario, String correousuario, String passwordusuario,
			boolean estadousuario) throws Exception {
		TabLogUsuario u = findusuarioByID(idusuario);
		TabLogTipoUsuario tipou = em.find(TabLogTipoUsuario.class, idtipousuario); 
		if (u == null)
			throw new Exception("No existe el usuario especificado.");
		u.setTabLogTipoUsuario(tipou);
		u.setNombreusuario(nombreusuario);
		u.setCorreousuario(correousuario);
		u.setEstadousuario(estadousuario);
		u.setEstadousuario(estadousuario);
		em.merge(u);
	}

	public List<TabLogUsuario> findAllUsuarios() {
		Query q;
		List<TabLogUsuario> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT c FROM TabLogUsuario c ORDER BY c.idusuario";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}
	
	public List<TabLogTipoUsuario> findAllTipoUsuarios() {
		Query q;
		List<TabLogTipoUsuario> listado;
		String sentenciaSQL;
		sentenciaSQL = "SELECT c FROM TabLogTipoUsuario c ORDER BY c.idtipousuario";
		q = em.createQuery(sentenciaSQL);
		listado = q.getResultList();
		return listado;
	}
	@SuppressWarnings("unchecked")
	public TabLogUsuario findUsuario(String nombreUsuario, String correoUsuario) {
		Query q = em.createQuery("select o from TabLogUsuario o where o.nombreusuario='" + nombreUsuario + "'"
				+ "or o.correousuario='" + correoUsuario + "'");
		List<TabLogUsuario> lista = q.getResultList();
		if (lista.isEmpty())
			return null;
		TabLogUsuario u = lista.get(0);
		return u;
	}

	@SuppressWarnings("unchecked")
	public boolean validarLogin(String nombreUsuario, String correoUsuario, String passwordUsuario) throws Exception {
		Query q = em.createQuery("select o from TabLogUsuario o where o.nombreusuario='" + nombreUsuario + "'"
				+ "or o.correousuario='" + correoUsuario + "'");
		List<TabLogUsuario> lista = q.getResultList();
		TabLogUsuario u = lista.get(0);
		if (u == null)
			throw new Exception("No existe el usuario " + nombreUsuario + correoUsuario);
		if (!u.getEstadousuario())
			throw new Exception("El usuario no está activo.");
		if ((u.getCorreousuario().equals(correoUsuario) || u.getNombreusuario().equals(nombreUsuario))
				&& u.getPasswordusuario().equals(passwordUsuario))
			return true;
		throw new Exception("Contraseña no válida.");
	}
	
	
}
