package restaurante.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import restaurante.model.entities.TabInvBodega;
import restaurante.model.manager.ManagerBodega;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped

public class ControllerBodega {
	private int idbodega;
	private String nombrebodega;
	private List<TabInvBodega> lista;

	@EJB
	ManagerBodega managerBodega;

	@PostConstruct
	public void iniciar() {
		lista = managerBodega.findAllBodegas();
	}

	public void AgregarBodega() {
		try {
			managerBodega.agregarBodega(nombrebodega);
			lista = managerBodega.findAllBodegas();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void CargarBodega(TabInvBodega bodega) {
		idbodega = bodega.getIdbodega();
		nombrebodega = bodega.getNombrebodega();
	}

	public void EditarBodega() {
		try {
			managerBodega.editarBodega(idbodega, nombrebodega);
			lista = managerBodega.findAllBodegas();
			JSFUtil.crearMensajeInfo("Bodega editada correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
	}

	public int getIdbodega() {
		return idbodega;
	}

	public void setIdbodega(int idbodega) {
		this.idbodega = idbodega;
	}

	public String getNombrebodega() {
		return nombrebodega;
	}

	public void setNombrebodega(String nombrebodega) {
		this.nombrebodega = nombrebodega;
	}

	public List<TabInvBodega> getLista() {
		return lista;
	}

	public void setLista(List<TabInvBodega> lista) {
		this.lista = lista;
	}

}
