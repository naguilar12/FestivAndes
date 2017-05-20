package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class NotaDebito {

	@JsonProperty(value="cliente")
	private Cliente cliente;
	
	@JsonProperty(value="dinero")
	private double dinero;
	
	@JsonProperty(value="funcion")
	private Funcion funcion;
	
	@JsonProperty(value="boleta")
	private Boleta boleta;
	
	

	public NotaDebito(@JsonProperty(value="cliente") Cliente cliente, @JsonProperty(value="dinero") double dinero, @JsonProperty(value="funcion") Funcion funcion, @JsonProperty(value="boleta") Boleta boleta) {
		super();
		this.cliente = cliente;
		this.dinero = dinero;
		this.funcion = funcion;
		this.boleta = boleta;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getDinero() {
		return dinero;
	}

	public void setDinero(double dinero) {
		this.dinero = dinero;
	}

	public Funcion getFuncion() {
		return funcion;
	}

	public void setFuncion(Funcion funcion) {
		this.funcion = funcion;
	}

	public Boleta getBoleta() {
		return boleta;
	}

	public void setBoleta(Boleta boleta) {
		this.boleta = boleta;
	}
	
	
}
