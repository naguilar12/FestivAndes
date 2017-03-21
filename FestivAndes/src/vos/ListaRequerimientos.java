package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRequerimientos {
	
	@JsonProperty(value="requerimientos")
	private List<Categoria> requerimientos;

	public ListaRequerimientos(	@JsonProperty(value="requerimientos")List<Categoria> requerimientos) {
		super();
		this.requerimientos = requerimientos;
	}

	public List<Categoria> getRequerimientos() {
		return requerimientos;
	}

	public void setRequerimientos(List<Categoria> requerimientos) {
		this.requerimientos = requerimientos;
	}
}
