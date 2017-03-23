package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva {

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="calorAPagar")
	private double valorAPagar;
	
	@JsonProperty(value="cantidadPersonas")
	private int cantidadPersonas;
	
	@JsonProperty(value="pagada")
	private int pagada;
	
	@JsonProperty(value="funcion")
	private Funcion funcion;
	
	@JsonProperty(value="sillas")
	private ListaSillas sillas;

	public Reserva(@JsonProperty(value="id")int id, @JsonProperty(value="calorAPagar")double valorAPagar, @JsonProperty(value="cantidadPersonas")int cantidadPersonas, @JsonProperty(value="pagada")int pagada, @JsonProperty(value="funcion")Funcion funcion, @JsonProperty(value="sillas")ListaSillas sillas) {
		super();
		this.id = id;
		this.valorAPagar = valorAPagar;
		this.cantidadPersonas = cantidadPersonas;
		this.pagada = pagada;
		this.funcion = funcion;
		this.sillas = sillas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValorAPagar() {
		return valorAPagar;
	}

	public void setValorAPagar(double valorAPagar) {
		this.valorAPagar = valorAPagar;
	}

	public int getCantidadPersonas() {
		return cantidadPersonas;
	}

	public void setCantidadPersonas(int cantidadPersonas) {
		this.cantidadPersonas = cantidadPersonas;
	}

	public int getPagada() {
		return pagada;
	}

	public void setPagada(int pagada) {
		this.pagada = pagada;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public ListaSillas getSillas() {
		return sillas;
	}

	public void setSillas(ListaSillas sillas) {
		this.sillas = sillas;
	}		

}
