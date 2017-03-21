package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente extends Usuario
{

	@JsonProperty(value="contrasena")
	private String contrasena;
	
	public Cliente(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre,@JsonProperty(value="mail") String mail,@JsonProperty(value="rol") String rol, @JsonProperty(value="nombre")String contrasena) {
		super(id, nombre, mail, rol);
		this.contrasena = contrasena;
		// TODO Auto-generated constructor stub
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	

}
