package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCompa�ias {

	@JsonProperty(value="companias")
	private List<Compa�iaTeatro> compa�ias;

	public ListaCompa�ias(@JsonProperty(value="companias")List<Compa�iaTeatro> compa�ias) {
		super();
		this.compa�ias = compa�ias;
	}

	public List<Compa�iaTeatro> getCompa�ias() {
		return compa�ias;
	}

	public void setCompa�ias(List<Compa�iaTeatro> compa�ias) {
		this.compa�ias = compa�ias;
	}
}
