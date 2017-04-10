package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Boleta {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="ubicacion")
	private String ubicacion;
	
	@JsonProperty(value="estado")
	private int estado;
	
	@JsonProperty(value="costo")
	private double costo;
	
	@JsonProperty(value="localidad")
	private Localidad localidad;
	
	@JsonProperty(value="funcion")
	private Funcion funcion;
	
	@JsonProperty(value="cliente")
	private Cliente cliente;
	
	public Boleta(@JsonProperty(value="id")int id, @JsonProperty(value="ubicacion") String ubicacion, @JsonProperty(value="estado") int estado,
			@JsonProperty(value="costo") double costo, @JsonProperty(value="localidad") Localidad localidad, @JsonProperty(value="funcion")Funcion funcion,
			@JsonProperty(value="cliente") Cliente cliente) {
		
		super();
		this.id = id;
		this.ubicacion = ubicacion;
		this.estado = estado;
		this.costo = costo;
		this.localidad = localidad;
		this.funcion = funcion;
		this.cliente = cliente;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
