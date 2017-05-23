package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCompanias {

	@JsonProperty(value="companias")
	private List<CompaniaTeatro> compa�ias;

	public ListaCompanias(@JsonProperty(value="companias")List<CompaniaTeatro> compa�ias) {
		super();
		this.compa�ias = compa�ias;
	}

	public List<CompaniaTeatro> getCompa�ias() {
		return compa�ias;
	}

	public void setCompa�ias(List<CompaniaTeatro> compa�ias) {
		this.compa�ias = compa�ias;
	}
}
