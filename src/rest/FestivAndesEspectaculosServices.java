package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import tm.VideoAndesMaster;
import vos.Funcion;
import vos.ListaEspectaculos;
import vos.ListaPreferencias;
import vos.ListaVideos;

@Path("espectaculos")
public class FestivAndesEspectaculosServices {

	
	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
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

//	@GET
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getEspectaculos() {
//		FestivAndesMaster tm = new FestivAndesMaster(getPath());
//		ListaEspectaculos espectaculos;
//		try {
//			espectaculos = tm.darEspectaculos();
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(espectaculos).build();
//	}
	

	////////////////////////////////////////RFC1////////////////////////////////////////////////////////////////
//
//	@GET
//	@Path("/{id}/funciones/idioma/{idioma}")
//	@Produces({ MediaType.APPLICATION_JSON })
//	public Response getEspectaculosI(@javax.ws.rs.PathParam("id") int id, @javax.ws.rs.PathParam("idioma") String idioma) {
//		FestivAndesMaster tm = new FestivAndesMaster(getPath());
//		ListaEspectaculos espectaculos;
//		try {
//			espectaculos = tm.darEspectaculosIdioma(idioma);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(espectaculos).build();
//	}
	
}
