package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario 
{
////Atributos

	/**
	 * Id del video
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre del video
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Duración en minutos del video
	 */
	@JsonProperty(value="mail")
	private String mail;
	
	/**
	 * Rol
	 */
	@JsonProperty(value="rol")
	private String rol;

	/**
	 * Método constructor de la clase video
	 * <b>post: </b> Crea el video con los valores que entran como parámetro
	 * @param id - Id del video.
	 * @param name - Nombre del video. name != null
	 * @param duration - Duración en minutos del video.
	 */
	public Usuario(@JsonProperty(value="id")int id, @JsonProperty(value="nombre")String nombre,@JsonProperty(value="mail") String mail,@JsonProperty(value="rol") String rol ) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.mail = mail;
		this.rol = rol;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}
	
	
}
