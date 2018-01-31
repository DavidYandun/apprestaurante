package restaurante.model.manager;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import restaurante.model.entities.TabLogUsuario;

/**
 * Session Bean implementation class ManagerLogin
 */
@Stateless
@LocalBean
public class ManagerLogin {

	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerLogin() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	public TabLogUsuario findUsuario(String nombreUsuario, String correoUsuario) {
		Query q = em.createQuery("select o from TabLogUsuario o where o.nombreusuario='"+nombreUsuario+"'"
				+ "or o.correousuario='"+correoUsuario+"'");		
		List<TabLogUsuario> lista = q.getResultList();
		if (lista.isEmpty())
			return null;
		TabLogUsuario u = lista.get(0);
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validarLogin(String nombreUsuario, String correoUsuario, String passwordUsuario)
			throws Exception {
		Query q = em.createQuery("select o from TabLogUsuario o where o.nombreusuario='"+nombreUsuario+"'"
				+ "or o.correousuario='"+correoUsuario+"'");
		List<TabLogUsuario> lista = q.getResultList();
		TabLogUsuario u = lista.get(0);
		if (u == null)
			throw new Exception("No existe el usuario " + nombreUsuario+correoUsuario);
		if (!u.getEstadousuario())
			throw new Exception("El usuario no está activo.");
		if (   ( u.getCorreousuario().equals(correoUsuario) || u.getNombreusuario().equals(nombreUsuario)	)	&&
				u.getPasswordusuario().equals(passwordUsuario)   )
			return true;
		throw new Exception("Contraseña no válida.");
	}

}
