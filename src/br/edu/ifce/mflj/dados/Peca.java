package br.edu.ifce.mflj.dados;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Peca {

	public static final int	AMARELA	= 1,
							AZUL	= 2;

	private Image	imagem;

	private Integer	tipoPeca,
					extensao,
					coordenadaX,
					coordenadaY;

	private Casa	container;

	private boolean	selecionada;

	public Peca( Casa container, Integer tipoPeca ){
		String nomeImagem	= "";

		this.container		= container;
		this.tipoPeca		= tipoPeca;
		extensao			= 20;

		switch( this.tipoPeca ){
			case Peca.AZUL:
				nomeImagem = "imagens/azul.png";
				break;

			case Peca.AMARELA:
				nomeImagem = "imagens/amarela.png";
				break;

			default:
				break;
		}

		imagem = new ImageIcon( nomeImagem ).getImage();
	}

	public boolean isSelecionada() {
		return selecionada;
	}

	public void setSelecionada(boolean selecionada) {
		this.selecionada = selecionada;
	}

	public Casa getContainer() {
		return container;
	}

	public void setContainer(Casa container) {
		this.container = container;
	}

	public Integer getExtensao() {
		return extensao;
	}

	public void setExtensao(Integer extensao) {
		this.extensao = extensao;
	}

	public Image getImagem() {
		return imagem;
	}

	public void setImagem(Image imagem) {
		this.imagem = imagem;
	}

	public Integer getTipoPeca() {
		return tipoPeca;
	}

	public void setTipoPeca(Integer tipoPeca) {
		this.tipoPeca = tipoPeca;
	}

	public Integer getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(Integer coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public Integer getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(Integer coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public void resetarPosicaoInicial(){
		coordenadaX = getContainer().getCoordenadaX();
		coordenadaY = getContainer().getCoordenadaY();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tipoPeca == null) ? 0 : tipoPeca.hashCode());
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
		Peca other = (Peca) obj;
		if (tipoPeca == null) {
			if (other.tipoPeca != null)
				return false;
		} else if (!tipoPeca.equals(other.tipoPeca))
			return false;
		return true;
	}

	public String toString(){
		switch( this.tipoPeca ){
			case Peca.AZUL:
				return "azul";

			case Peca.AMARELA:
				return "amarela";

			default:
				return "";
		}
	}
}