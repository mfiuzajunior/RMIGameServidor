package br.edu.ifce.mflj.dados;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = -8373676768234079923L;

	private String apelido, identificador;

	public Usuario(String apelido, String identificador) {
		super();
		this.apelido = apelido;
		this.identificador = identificador;
	}

	/**
	 * @return the apelido
	 */
	public String getApelido() {
		return apelido;
	}

	/**
	 * @param apelido
	 *            the apelido to set
	 */
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}

	/**
	 * @param identificador
	 *            the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identificador == null) ? 0 : identificador.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		return true;
	}

	public String toString(){
		return this.getApelido();
	}

}
