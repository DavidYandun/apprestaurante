package restaurante.model.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import restaurante.model.entities.TabCajCajero;
import restaurante.model.entities.TabCajTransaccion;
import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabLogUsuario;
import restaurante.model.entities.TabVtsDetallePedido;
import restaurante.model.entities.TabVtsPedido;
import restaurante.model.entities.TabVtsPlato;

/**
 * Session Bean implementation class ManagerPedido
 */
@Stateless
@LocalBean
public class ManagerPedido {

	@PersistenceContext(unitName = "restauranteDS")
	private EntityManager em;

	public ManagerPedido() {
		// TODO Auto-generated constructor stub
	}

	public TabVtsPedido agregarDetallePedido(TabVtsPedido ped, int idproducto, int idplato, int cantidaddetallepedido) {
		if (ped == null) {
			ped = new TabVtsPedido();
			ped.setTabVtsDetallePedidos(new ArrayList<TabVtsDetallePedido>());
		}
		// recuperar datos necesarios
		TabInvProducto prod = em.find(TabInvProducto.class, idproducto);
		TabVtsPlato plato = em.find(TabVtsPlato.class, idplato);
		BigDecimal valorTotal;

		// llenar datos del nuevo detalle
		TabVtsDetallePedido det = new TabVtsDetallePedido();
		det.setCantidaddetallepedido(cantidaddetallepedido);
		det.setTabInvProducto(prod);
		det.setTabVtsPlato(plato);
		if (prod != null) {
			valorTotal = prod.getValorventa().multiply(BigDecimal.valueOf(cantidaddetallepedido));
			det.setValorunitariodetallepedido(prod.getValorventa());
			det.setValorTotaldetallepedido(valorTotal);
		} else if (plato != null) {
			valorTotal = plato.getValorplato().multiply(BigDecimal.valueOf(cantidaddetallepedido));
			det.setValorunitariodetallepedido(plato.getValorplato());
			det.setValorTotaldetallepedido(valorTotal);
		}
		det.setTabVtsPedido(ped);
		return ped;
	}

	public TabVtsPedido guardarPedido(TabVtsPedido ped, int mesa, int idpedido, int idusuario) throws Exception {
		// guardar transaccion
		TabCajCajero cajero;
		cajero = em.find(TabCajCajero.class, new Date());
		TabLogUsuario usuario;
		usuario = em.find(TabLogUsuario.class, idusuario);

		TabCajTransaccion tra = new TabCajTransaccion();
		tra.setTabCajCajero(cajero);
		tra.setIdtransaccion(1);
		tra.setTabLogUsuario(usuario);
		tra.setValortransaccion(new BigDecimal(100));
		tra.setDescripciontransaccion("pedido");
		////////////////////

		if (ped == null)
			throw new Exception("Se debe almacenar al menos un plato");
		if (mesa == 0)
			throw new Exception("Se debe registrar la mesa");

		ped.setFechapedido(new Date());
		ped.setIdpedido(idpedido);
		ped.setMesa(mesa);
		ped.setTotal(new BigDecimal(100));
		// falta guardar en la transacción
		em.persist(ped);
		return ped;
	}

}
