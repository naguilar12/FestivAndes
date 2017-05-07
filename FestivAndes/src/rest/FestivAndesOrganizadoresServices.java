package rest;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.ConsultaFuncion;
import vos.FiltroConsultaCompraBoletas;
import vos.Funcion;
import vos.ListaClientes;
import vos.ListaNotasDebito;
import vos.RespuestaConsultaCompraBoletas;
import vos.Resultado;

@Path("organizadores")
public class FestivAndesOrganizadoresServices {

	@Context
	private ServletContext context;

	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}


	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}

	/////////////////////////////////////////RF9/////////////////////////////////////////////////////////////////
	@GET
	@Path("{idO}/espectaculos/{id}/funciones/{idF}/realizarFuncion")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response realizarFuncion(@javax.ws.rs.PathParam("idO") int idO,@javax.ws.rs.PathParam("id") int id, @javax.ws.rs.PathParam("idF") int idF) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		Funcion funcion;
		try {
			funcion = tm.realizarFuncion(idO,id, idF);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funcion).build();
	}
	/////////////////////////////////////////RFC7/////////////////////////////////////////////////////////////////
	@GET
	@Path("{idO}/consultarAsistenciaAlFestival/{idC}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response consultarAsistenciaAlFestival(@javax.ws.rs.PathParam("idO") int idO,@javax.ws.rs.PathParam("idC") int idC) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<ConsultaFuncion> resultado;
		try {
			if(tm.darOrganizador(idO)!=null)
				resultado = tm.consultarAsistenciaAlFestival(idC);
			else
				resultado = null;
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resultado).build();
	}

	/////////////////////////////////////////RF14/////////////////////////////////////////////////////////////////

	@GET
	@Path("{idO}/cancelarFuncion/{idF}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response cancelarFuncion(@javax.ws.rs.PathParam("idO") int idO,@javax.ws.rs.PathParam("idF") int idF) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaNotasDebito resultado;
		try {
			if(tm.darOrganizador(idO)!=null)
				resultado = tm.cancelarFuncion(idF);
			else
				resultado = null;
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resultado).build();
	}

	/////////////////////////////////////////RFC11/////////////////////////////////////////////////////////////////

	@PUT
	@Path("{idO}/consultarCompraBoletas/")
	@Produces (MediaType.APPLICATION_JSON )
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consultarCompraBoletas(FiltroConsultaCompraBoletas filtro, @javax.ws.rs.PathParam("idO") int idO)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ArrayList<RespuestaConsultaCompraBoletas> resultado;
		try {
			if(tm.esGerenteGeneral(idO))
				resultado = tm.consultarCompraBoletas(filtro);
			else
				resultado = null;
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resultado).build();
	}

	/////////////////////////////////////////RFC12/////////////////////////////////////////////////////////////////
	
	@PUT
	@Path("{idO}/consultarBuenosClientes/{n}")
	@Produces (MediaType.APPLICATION_JSON )
	@Consumes(MediaType.APPLICATION_JSON)
	public Response consultarBuenosClientes(@javax.ws.rs.PathParam("idO") int idO, @javax.ws.rs.PathParam("n") int n)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaClientes resultado;
		try {
			if(tm.esGerenteGeneral(idO))
				resultado = tm.consultarBuenosClientes(n);
			else
				resultado = null;
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resultado).build();
	}
	
	
}
