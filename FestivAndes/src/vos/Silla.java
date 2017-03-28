package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Silla {
	
	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="fila")
	private int fila;
	
	@JsonProperty(value="columna")
	private int columna;
	
	@JsonProperty(value="localidad")
	private Localidad localidad;
	
	@JsonProperty(value="reserva")
	private Reserva reserva;	

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public Silla(@JsonProperty(value="id")int id,@JsonProperty(value="fila") int fila, @JsonProperty(value="columna")int columna,@JsonProperty(value="localidad")Localidad localidad) {
		super();
		this.id = id;
		this.fila = fila;
		this.columna = columna;
		this.localidad = localidad;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}

	
}