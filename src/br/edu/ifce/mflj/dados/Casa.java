package br.edu.ifce.mflj.dados;

import java.awt.Graphics2D;

public class Casa {

	private Integer		id;
	private Boolean		ocupada;
	private Peca		peca;

	private Integer		coordenadaX,
						coordenadaY;

	public Casa(){}

	public Casa( Integer id, Integer coordenadaX, Integer coordenadaY ){
		this.id				= id;
		this.coordenadaX	= coordenadaX;
		this.coordenadaY	= coordenadaY;
		ocupada				= false;
	}

	public Casa( Integer id, Integer coordenadaX, Integer coordenadaY, Peca peca ){
		this( id, coordenadaX, coordenadaY );
		adicionarPeca( peca );
	}

	public Integer getId(){
		return id;
	}

	public void setId( Integer id ){
		this.id = id;
	}

	public Peca getPeca() {
		return peca;
	}
	
	public void setPeca(Peca peca) {
		this.peca = peca;
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

	public Boolean isOcupada(){
		return ocupada;
	}

	public void setOcupada( Boolean ocupada ){
		this.ocupada = ocupada;
	}

	public void adicionarPeca( Peca peca ){
		ocupada		= true;
		this.peca	= peca;

		this.peca.setContainer( this );
	}

	public void removerPeca(){
		getPeca().setContainer( null );
		setPeca( null );
		setOcupada( false );
	}

	public void desenharPeca( Graphics2D graphics ){
		if( peca != null ){
			if( !peca.isSelecionada() ){
				graphics.drawImage(	peca.getImagem(),
									getCoordenadaX(),
									getCoordenadaY(),
									peca.getExtensao(),
									peca.getExtensao(),
									null );
			}
		}
	}

	private boolean cliqueDentroDoIntervaloVertical(Integer coordenadaY) {
		return ( getCoordenadaY() <= coordenadaY ) && ( coordenadaY <= ( getCoordenadaY() + 20 ) );
	}

	private boolean cliqueDentroDoIntervaloHorizontal(Integer coordenadaX) {
		return ( getCoordenadaX() <= coordenadaX ) && ( coordenadaX <= ( getCoordenadaX() + 20 ) );
	}

	public boolean temFocoDoMouse( int xDoClique, int yDoClique) {
		return	cliqueDentroDoIntervaloHorizontal( xDoClique ) &&
				cliqueDentroDoIntervaloVertical( yDoClique );
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Casa other = (Casa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString(){
		return "" + id;
	}
}
