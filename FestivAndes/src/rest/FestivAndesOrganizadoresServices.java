package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.Funcion;

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
	@Path("/espectaculos/{id}/funciones/{idF}/realizarFuncion")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response realizarFuncion(@javax.ws.rs.PathParam("id") int id, @javax.ws.rs.PathParam("idF") int idF) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		Funcion funcion;
		try {
			funcion = tm.realizarFuncion(id, idF);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funcion).build();
	}
}
