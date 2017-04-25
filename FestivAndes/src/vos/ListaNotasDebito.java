package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaNotasDebito 
{
	@JsonProperty(value="notasDebito")
	private List<NotaDebito> notasDebito;

	public ListaNotasDebito(@JsonProperty(value="notasDebito") List<NotaDebito> notasDebito) 
	{
		super();
		this.notasDebito = notasDebito;
	}

	public List<NotaDebito> getNotasDebito() {
		return notasDebito;
	}

	public void setNotasDebito(List<NotaDebito> notasDebito) {
		this.notasDebito = notasDebito;
	}
	

}
