package restaurante.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import restaurante.model.entities.TabInvFacturaCompra;
import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabInvProveedor;
import restaurante.model.entities.TabVtsPlato;
import restaurante.model.manager.ManagerFacturaCompra;
import restaurante.model.manager.ManagerProducto;
import restaurante.model.manager.ManagerProveedor;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerFacturaCompra {
	private Integer idproveedor;
	private Date fechafacturacompra;
	private Integer cantidadcompra;
	private BigDecimal valorunitariocompra;
	private Integer idproducto;
	private boolean facturaCabTmpGuardada;
	private TabInvFacturaCompra facturaCabTmp;
	private Integer idfacturacompra;
	

	
	@EJB
	private ManagerFacturaCompra managerFacturaCompra;
	@EJB
	private ManagerProveedor managerProveedor;
	@EJB
	private ManagerProducto managerProducto;
	
	@PostConstruct
	public void inicar() {
		facturaCabTmp = managerFacturaCompra.crearFacturaCompraTmp(fechafacturacompra);
		idproveedor = 0;
		idproducto = 0;
		cantidadcompra = 0;
		valorunitariocompra =new BigDecimal("0");
		facturaCabTmpGuardada = false;		
	}
	public String crearNuevaFacturaCompra() {
		facturaCabTmp =managerFacturaCompra.crearFacturaCompraTmp(fechafacturacompra);
		idproveedor = 0;
		idproducto = 0;
		cantidadcompra = 0;
		valorunitariocompra = new BigDecimal("0");
		facturaCabTmpGuardada = false;		
		return "";
	}
	public void asignarProveedor() {
		if(facturaCabTmpGuardada == true) {
			JSFUtil.crearMensajeWarning("La factura ya fue guardada");			
		}
		try {
			managerFacturaCompra.asignarProveedorFacturaTmp(facturaCabTmp, idproveedor);
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
	}
	public String insertarDetalleCompra() {
		if(facturaCabTmpGuardada == true) {
			JSFUtil.crearMensajeWarning("La factura ya fue guarda");
		}
		try {
			managerFacturaCompra.agregarDetalleFacturaTmp(facturaCabTmp, idproducto, cantidadcompra, valorunitariocompra);
			idproducto=0;			
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
		return "";
	}
	public String guardarFacturaCompra() {
		
		if(facturaCabTmpGuardada ==true ) {
			JSFUtil.crearMensajeWarning("La factura ya fue creda");
			return "";
		}
		try {
			managerFacturaCompra.guardarFacturaTemporal(facturaCabTmp);
			managerProducto.findAllProductos();
			facturaCabTmpGuardada = true;			
		}catch(Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
		}
		return "";
	}
	public List<SelectItem> getListaProveedoreSI() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<TabInvProveedor> listadoProveedores = managerProveedor.findAllProveedor();

		for (TabInvProveedor c : listadoProveedores) {
			SelectItem item = new SelectItem(c.getIdproveedor(), c.getNombreproveedor());
			listadoSI.add(item);
		}
		return listadoSI;
	}
	
	public List<SelectItem> getListaProductosSI() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<TabInvProducto> listadoProductos = managerProducto.findAllProductos();

		for (TabInvProducto p : listadoProductos) {
			SelectItem item = new SelectItem(p.getIdproducto(), p.getNombreproducto());
			listadoSI.add(item);
		}
		return listadoSI;
	}		
	public String actionReporte() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		 parametros.put("idfacturacompra", idfacturacompra);
		// parametros.put("p_titulo",p_titulo);
		
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("bodeguero/inventario/facturacompr.jasper");
		System.out.println(idfacturacompra);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=facturacompr.pdf");
		response.setContentType("application/pdf");
		try {
			Class.forName("org.postgresql.Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/restaurante", "postgres",
					"12345");
			JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
			JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
			context.getApplication().getStateManager().saveView(context);
			System.out.println("reporte generado.");
			context.responseComplete();
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}


	public Integer getIdfacturacompra() {
		return idfacturacompra;
	}

	public void setIdfacturacompra(Integer idfacturacompra) {
		this.idfacturacompra = idfacturacompra;
	}
	public Integer getIdproveedor() {
		return idproveedor;
	}
	public void setIdproveedor(Integer idproveedor) {
		this.idproveedor = idproveedor;
	}
	public Date getFechafacturacompra() {
		return fechafacturacompra;
	}
	public void setFechafacturacompra(Date fechafacturacompra) {
		this.fechafacturacompra = fechafacturacompra;
	}
	public Integer getCantidadcompra() {
		return cantidadcompra;
	}	
	public void setCantidadcompra(Integer cantidadcompra) {
		this.cantidadcompra = cantidadcompra;
	}	
	public TabInvFacturaCompra getFacturaCabTmp() {
		return facturaCabTmp;
	}
	public void setFacturaCabTmp(TabInvFacturaCompra facturaCabTmp) {
		this.facturaCabTmp = facturaCabTmp;
	}
	public BigDecimal getValorunitariocompra() {
		return valorunitariocompra;
	}
	public void setValorunitariocompra(BigDecimal valorunitariocompra) {
		this.valorunitariocompra = valorunitariocompra;
	}
	public Integer getIdproducto() {
		return idproducto;
	}
	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}
	public boolean isFacturaCabTmpGuardada() {
		return facturaCabTmpGuardada;
	}
	public void setFacturaCabTmpGuardada(boolean facturaCabTmpGuardada) {
		this.facturaCabTmpGuardada = facturaCabTmpGuardada;
	}	
}