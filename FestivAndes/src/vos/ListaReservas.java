package vos;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReservas 
{
	@JsonProperty(value="reservas")
	private ArrayList<Reserva> reservas;
	
	public ListaReservas(@JsonProperty(value="reservas")ArrayList<Reserva> reservas) {
		super();
		this.reservas = reservas;
	}

	public ArrayList<Reserva> getSillas() {
		return reservas;
	}

	public void setSillas(ArrayList<Reserva> reservas) {
		this.reservas = reservas;
	}
}
