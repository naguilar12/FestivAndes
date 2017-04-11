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
import tm.VideoAndesMaster;
import vos.Boleta;
import vos.ListaBoletas;
import vos.ListaPreferencias;
import vos.Preferencia;
import vos.Video;

@Path("clientes")
public class FestivAndesClienteServices {

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

	////////////////////////////////////////RF7///////////////////////////////////////////

	@GET
	@Path("/preferencias")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPreferencias() {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaPreferencias preferencias;
		try {
			preferencias = tm.darPreferencias();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencias).build();
	}

	@GET
	@Path("{id}/preferencias")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPreferenciasUsuario(@javax.ws.rs.PathParam("id") int id) {
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
	@Path("{id}/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferencia(Preferencia preferencia, @javax.ws.rs.PathParam("id") int id) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			if(preferencia.getId()==id)
				tm.addPreferencia(preferencia, id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}

	@DELETE
	@Path("{id}/preferencias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePreferencia(Preferencia preferencia, @javax.ws.rs.PathParam("id") int id) {
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		try {
			if(id == preferencia.getId())
				tm.deletePreferencia(preferencia, id);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}

	////////////////////////////////////////RF10///////////////////////////////////////////

	@POST
	@Path("{id}/compraMultipleBoletas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response compraMultipleBoletas(ListaBoletas boletas,@javax.ws.rs.PathParam("id") int id)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaBoletas resultado;
		try{
			resultado = tm.comprarMultiplesBoletas(boletas, id);					

		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resultado).build();
	}
	
	////////////////////////////////////////RF11///////////////////////////////////////////

	@POST
	@Path("{id}/registrarCompraAbonamiento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarCompraAbonamiento(ListaBoletas boletas,@javax.ws.rs.PathParam("id") int id)
	{
		FestivAndesMaster tm = new FestivAndesMaster(getPath());
		ListaBoletas resultado;
		try{
			resultado = tm.registrarCompraAbonamiento(boletas, id);					

		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(resultado).build();
	}

}
