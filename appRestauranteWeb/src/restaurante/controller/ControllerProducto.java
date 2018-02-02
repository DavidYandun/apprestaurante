package restaurante.controller;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import restaurante.model.entities.TabInvProducto;
import restaurante.model.entities.TabInvBodega;
import restaurante.model.entities.TabInvCategoriaProducto;
import restaurante.model.manager.ManagerProducto;
import restaurante.view.util.JSFUtil;

@ManagedBean
@SessionScoped
public class ControllerProducto {
	private Integer idproducto;
	private Integer idcategoria;
	private Integer idbodega;
	private String nombreproducto;
	private String descripcionproducto;
	private BigDecimal valorcompra;
	private BigDecimal valorventa;
	private BigDecimal stock;
	private Boolean estado;
	private String unidadmedida;
	private List<TabInvProducto> lista;
	
	@EJB
	private ManagerProducto managerProducto;

	@PostConstruct
	public void iniciar() {
		lista = managerProducto.findAllProductos();
	}

	public List<SelectItem> getListaProductoSI() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<TabInvBodega> listadoProdcutos = managerProducto.findAllBodegas();
		for (TabInvBodega c : listadoProdcutos) {
			SelectItem item = new SelectItem(c.getIdbodega(), c.getNombrebodega());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	public List<SelectItem> getListaUnidadSI() {
		List<SelectItem> listadoSI = new ArrayList<SelectItem>();
		List<TabInvProducto> listadoProdcutos = managerProducto.findAllProductos();
		for (TabInvProducto c : listadoProdcutos) {
			SelectItem item = new SelectItem(c.getUnidadmedida());
			listadoSI.add(item);
		}
		return listadoSI;
	}

	public List<SelectItem> getListaCategoriaSI() {
		List<SelectItem> listadoCSI = new ArrayList<SelectItem>();
		List<TabInvCategoriaProducto> listadoCategoria = managerProducto.findAllCategorias();

		for (TabInvCategoriaProducto c : listadoCategoria) {
			SelectItem item = new SelectItem(c.getIdcategoria(), c.getNombrecategoria());
			listadoCSI.add(item);
		}
		return listadoCSI;
	}

	public void agregarProducto() {
		try {
			managerProducto.agregarproducto(idcategoria, idbodega, nombreproducto, descripcionproducto, valorcompra,
					valorventa, stock, estado, unidadmedida);
			lista = managerProducto.findAllProductos();
			JSFUtil.crearMensajeInfo("Producto registrado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		idcategoria = null;
		idbodega = null;
		nombreproducto = "";
		descripcionproducto = "";
		valorcompra = null;
		valorventa = null;
		stock = null;
		unidadmedida = "";
	}

	public void CargarProducto(TabInvProducto producto) {
		idproducto = producto.getIdproducto();
		idcategoria = producto.getTabInvCategoriaProducto().getIdcategoria();
		idbodega = producto.getTabInvBodega().getIdbodega();
		nombreproducto = producto.getNombreproducto();
		descripcionproducto = producto.getDescripcionproducto();
		valorcompra = producto.getValorcompra();
		valorventa = producto.getValorventa();
		stock = producto.getStock();
		unidadmedida = producto.getUnidadmedida();
		estado = producto.getEstadoproducto();
	}

	public void EditarProveedor() {
		try {
			managerProducto.editarproducto(idproducto, idcategoria, idbodega, nombreproducto, descripcionproducto,
					valorcompra, valorventa, stock, estado, unidadmedida);

			lista = managerProducto.findAllProductos();
			JSFUtil.crearMensajeInfo("Producto con nombre" + nombreproducto + " editado correctamente.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError(e.getMessage());
			e.printStackTrace();
		}
		idcategoria = null;
		idbodega = null;
		nombreproducto = "";
		descripcionproducto = "";
		valorcompra = null;
		valorventa = null;
		stock = null;
		unidadmedida = "";
	}

	public String actionReporte() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		 // parametros.put("idfacturacompra", idfacturacompra);
		//  parametros.put("p_titulo",p_titulo);
		 
		FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("Inventario/Productos.jasper");
		System.out.println(ruta);
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=Prodcutos.pdf");
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

	public Integer getIdproducto() {
		return idproducto;
	}

	public void setIdproducto(Integer idproducto) {
		this.idproducto = idproducto;
	}

	public Integer getIdcategoria() {
		return idcategoria;
	}

	public void setIdcategoria(Integer idcategoria) {
		this.idcategoria = idcategoria;
	}

	public Integer getIdbodega() {
		return idbodega;
	}

	public void setIdbodega(Integer idbodega) {
		this.idbodega = idbodega;
	}

	public String getNombreproducto() {
		return nombreproducto;
	}

	public void setNombreproducto(String nombreproducto) {
		this.nombreproducto = nombreproducto;
	}

	public String getDescripcionproducto() {
		return descripcionproducto;
	}

	public void setDescripcionproducto(String descripcionproducto) {
		this.descripcionproducto = descripcionproducto;
	}

	public BigDecimal getValorcompra() {
		return valorcompra;
	}

	public void setValorcompra(BigDecimal valorcompra) {
		this.valorcompra = valorcompra;
	}

	public BigDecimal getValorventa() {
		return valorventa;
	}

	public void setValorventa(BigDecimal valorventa) {
		this.valorventa = valorventa;
	}

	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getUnidadmedida() {
		return unidadmedida;
	}

	public void setUnidadmedida(String unidadmedida) {
		this.unidadmedida = unidadmedida;
	}

	public List<TabInvProducto> getLista() {
		return lista;
	}

	public void setLista(List<TabInvProducto> lista) {
		this.lista = lista;
	}

	public ManagerProducto getManagerProducto() {
		return managerProducto;
	}

	public void setManagerProducto(ManagerProducto managerProducto) {
		this.managerProducto = managerProducto;
	}
}
