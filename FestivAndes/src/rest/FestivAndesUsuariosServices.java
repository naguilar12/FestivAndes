package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.FestivAndesMaster;
import vos.ListaPreferencias;
import vos.Preferencia;

public class FestivAndesUsuariosServices {

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
	
	@GET
	@Path("/clientes/{id}/preferencias")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPreferencias(@javax.ws.rs.PathParam("id") int id) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaPreferencias preferencias;
		try {
			preferencias = tm.buscarPreferenciasPorUsuario(id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencias).build();
	}
	
	@POST
	@Path("/clientes/{id}/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferencia(Preferencia preferencia, @javax.ws.rs.PathParam("id") int id) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.addPreferencia(preferencia, id);;
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}
	
	@DELETE
	@Path("/clientes/{id}/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePreferencia(Preferencia preferencia, @javax.ws.rs.PathParam("id") int id) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			tm.deletePreferencia(preferencia, id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}
}
