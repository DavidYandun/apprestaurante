package restaurante.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import restaurante.model.entities.TabInvCategoriaProducto;
import restaurante.model.manager.ManagerCategoriaProducto;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped

public class ControllerCategoriaProducto {
	private int idcategoria;
	private String nombrecategoria;
	private String descripcioncategoria;
	private List<TabInvCategoriaProducto> lista;

	@EJB
	private ManagerCategoriaProducto managerCategoria;

	@PostConstruct
	public void iniciar() {
		lista = managerCategoria.findAllCategorias();
	}

	public void AgregarCategoria() {
		try {
			managerCategoria.AgregarCategoriaProductos(nombrecategoria, descripcioncategoria);
			lista = managerCategoria.findAllCategorias();
			JSFUtil.crearMensajeInfo("Categoría registrada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void CargarCategoria(TabInvCategoriaProducto categoria) {
		idcategoria = categoria.getIdcategoria();
		nombrecategoria = categoria.getNombrecategoria();
		descripcioncategoria = categoria.getDescripcioncategoria();
	}

	public void EditarCategoria() {
		try {
			managerCategoria.editarCategoriaPlato(idcategoria, nombrecategoria, descripcioncategoria);
			lista = managerCategoria.findAllCategorias();
			JSFUtil.crearMensajeInfo("Categoria" + nombrecategoria + " editado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}

	}

	public int getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(int idcategoria) {
		this.idcategoria = idcategoria;
	}

	public String getNombrecategoria() {
		return nombrecategoria;
	}

	public void setNombrecategoria(String nombrecategoria) {
		this.nombrecategoria = nombrecategoria;
	}

	public String getDescripcioncategoria() {
		return descripcioncategoria;
	}

	public void setDescripcioncategoria(String descripcioncategoria) {
		this.descripcioncategoria = descripcioncategoria;
	}

	public List<TabInvCategoriaProducto> getLista() {
		return lista;
	}

	public void setLista(List<TabInvCategoriaProducto> lista) {
		this.lista = lista;
	}

	
}
