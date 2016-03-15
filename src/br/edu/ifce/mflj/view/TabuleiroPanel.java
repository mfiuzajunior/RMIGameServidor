package br.edu.ifce.mflj.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.edu.ifce.mflj.comunicacao.Pacote;
import br.edu.ifce.mflj.comunicacao.TipoPacote;
import br.edu.ifce.mflj.dados.Casa;
import br.edu.ifce.mflj.dados.DimensoesTabuleiro;
import br.edu.ifce.mflj.dados.Peca;
import br.edu.ifce.mflj.jogo.Regulamento;
import br.edu.ifce.mflj.observer.CanalListener;
import br.edu.ifce.mflj.observer.MovimentoListener;

public class TabuleiroPanel extends JPanel implements MouseMotionListener, MouseListener, CanalListener {

	private static final long serialVersionUID = -8383511424909321696L;

	private Graphics2D				graphics;
	private Integer					ultimoXMouse,
									ultimoYMouse;
	private Peca					pecaSelecionada;
	private Regulamento				regulamento;
	private	List<Casa>				tabuleiroDasPecas;
	private List<MovimentoListener>	listenersDeMovimento;
	private Boolean					movimentoLocal = true;

	private JLabel					coordenadaX,
									coordenadaY;

	public TabuleiroPanel(){
		setLayout( null );

		listenersDeMovimento	= new ArrayList<MovimentoListener>();

		criarCasas();

		addMouseMotionListener( this );
		addMouseListener( this );

	}

	private Component getCoordenadaX() {
		if( coordenadaX == null ){
			coordenadaX = new JLabel("x");
			coordenadaX.setBounds( this.getWidth() - 80, this.getHeight() - 50, 50, 25 );
			coordenadaX.setForeground( new Color( 255, 0, 0 ) );
		}
		return coordenadaX;
	}

	private Component getCoordenadaY() {
		if( coordenadaY == null ){
			coordenadaY = new JLabel("y");
			coordenadaY.setBounds( this.getWidth() - 45, this.getHeight() - 50, 50, 25 );
			coordenadaY.setForeground( new Color( 255, 0, 0 ) );
		}
		return coordenadaY;
	}

	public void addMovimentoListener( MovimentoListener movimentoListener ){
		this.listenersDeMovimento.add( movimentoListener );
	}

	public void removeMovimentoListener( MovimentoListener movimentoListener ){
		this.listenersDeMovimento.remove( movimentoListener );
	}

	public void notifyMovimento( TipoPacote tipoPacote, MouseEvent mouseEvent ){
		// Só irá transmitir o movimento se ele tiver sido gerado localmente
		if( movimentoLocal ){
			for( MovimentoListener movimentoListener : this.listenersDeMovimento ){
				movimentoListener.movimentoRealizado( tipoPacote, mouseEvent  );
			}
		}
	}

	public Regulamento getRegulamento() {
		return regulamento;
	}

	public void setRegulamento(Regulamento regulamento) {
		this.regulamento = regulamento;
	}

	private void criarCasa( int idCasa, int coordenadaX, int coordenadaY, Integer tipoPeca ){
		Casa casa = new Casa( idCasa, coordenadaX, coordenadaY );

		if( tipoPeca != 0 ){
			casa.adicionarPeca( new Peca( casa, tipoPeca ) );
		}

		tabuleiroDasPecas.add( casa );
	}

	private void criarCasas(){
		int id = 0;
		tabuleiroDasPecas = new ArrayList<Casa>();

		criarCasa( id++, 0, 0, Peca.AMARELA );
		criarCasa( id++, 165, 0, Peca.AZUL );
		criarCasa( id++, 330, 0, 0 );

		criarCasa( id++, 55, 55, 0 );
		criarCasa( id++, 164, 55, 0 );
		criarCasa( id++, 275, 55, 0 );

		criarCasa( id++, 110, 108, 0 );
		criarCasa( id++, 164, 108, 0 );
		criarCasa( id++, 219, 108, 0 );

		criarCasa( id++, 0, 164, 0 );
		criarCasa( id++, 55, 164, 0 );
		criarCasa( id++, 110, 164, 0 );
		criarCasa( id++, 219, 164, 0 );
		criarCasa( id++, 275, 164, 0 );
		criarCasa( id++, 330, 164, 0 );

		criarCasa( id++, 110, 221, 0 );
		criarCasa( id++, 164, 221, 0 );
		criarCasa( id++, 219, 221, 0 );

		criarCasa( id++, 55, 275,	0 );
		criarCasa( id++, 164, 275, 0 );
		criarCasa( id++, 275, 275, 0 );

		criarCasa( id++, 0, 331, 0 );
		criarCasa( id++, 164, 331, 0 );
		criarCasa( id++, 330, 331, 0 );
	}

	@Override
	public void paintComponent( Graphics graphics ){
		super.paintComponent( graphics );
		this.graphics = ( Graphics2D )graphics.create();

		redesenharTabuleiro();
		desenharGrade();
		desenharPecas();

		this.graphics.dispose();
	}

	private void desenharGrade() {
		this.add( getCoordenadaX() );
		this.add( getCoordenadaY() );

		// Linhas verticais
		for( int linha = 0; linha < DimensoesTabuleiro.LINHAS; linha++ ){
			this.graphics.drawLine( 0, linha * DimensoesTabuleiro.LADO_CASA, DimensoesTabuleiro.LIMITE_FINAL_X, linha * DimensoesTabuleiro.LADO_CASA);
		}

		// Linhas horizontais
		for( int linha = 0; linha < DimensoesTabuleiro.COLUNAS; linha++ ){
			this.graphics.drawLine( linha * DimensoesTabuleiro.LADO_CASA, 0, linha * DimensoesTabuleiro.LADO_CASA, DimensoesTabuleiro.LIMITE_FINAL_Y );
		}
	}

	private void redesenharTabuleiro() {
		graphics.drawImage( new ImageIcon( "imagens/tabuleiro.jpg" ).getImage(), 0, 0, DimensoesTabuleiro.LIMITE_FINAL_X, DimensoesTabuleiro.LIMITE_FINAL_Y, null );
	}

	private void desenharPecas(){
		// Desenha todas as peças, menos a peça que estiver selecionada para movimento.
		for( Casa casaAtual : tabuleiroDasPecas ){
			casaAtual.desenharPeca( graphics );
		}

		// Desenhar peça selecionada por último, para que fique por cima das demais.
		if( pecaSelecionada != null ){
			graphics.drawImage(	pecaSelecionada.getImagem(),
								pecaSelecionada.getCoordenadaX(),
								pecaSelecionada.getCoordenadaY(),
								pecaSelecionada.getExtensao(),
								pecaSelecionada.getExtensao(),
								this );
		}
	}

	@Override
	public void mousePressed( MouseEvent mouseEvent ){
		for( Casa casaAtual : tabuleiroDasPecas ){
			if( casaAtual.temFocoDoMouse( mouseEvent.getX(), mouseEvent.getY() ) ){
				try {
					casaAtual.getPeca().setSelecionada( true );
					pecaSelecionada = casaAtual.getPeca();

				} catch( NullPointerException nullPointerException ){
					System.err.println( "mousePressed" );
				}
			}
		}

		ultimoXMouse = mouseEvent.getX();
		ultimoYMouse = mouseEvent.getY();

		notifyMovimento( TipoPacote.MOUSE_PRESSIONADO, mouseEvent );
	}

	@Override
	public void mouseReleased( MouseEvent mouseEvent ){
		for( Casa casaAtual : tabuleiroDasPecas ){
			if( casaAtual.temFocoDoMouse( mouseEvent.getX(), mouseEvent.getY() ) &&
				!casaAtual.isOcupada() ){
				try {
					if( !casaAtual.equals( pecaSelecionada.getContainer() ) ){
						pecaSelecionada.getContainer().removerPeca();
						casaAtual.adicionarPeca( pecaSelecionada );
					}
				} catch( NullPointerException nullPointerException ){
					System.err.println( "mousePressed" );
				}
			}
		}

		redefinirPosicaoPecaSelecionada();
		pecaSelecionada = null;

		notifyMovimento( TipoPacote.MOUSE_LIBERADO, mouseEvent );
		super.repaint();
	}

	private void redefinirPosicaoPecaSelecionada() {
		pecaSelecionada.setCoordenadaX( pecaSelecionada.getContainer().getCoordenadaX() );
		pecaSelecionada.setCoordenadaY( pecaSelecionada.getContainer().getCoordenadaY() );
		pecaSelecionada.setSelecionada( false );
	}

	@Override
	public void mouseDragged( MouseEvent mouseEvent ){
		if( pecaSelecionada != null && DimensoesTabuleiro.mouseDentroDoTabuleiro( mouseEvent.getX(), mouseEvent.getY() ) ){
			int		deslocamentoEmX = mouseEvent.getX() - ultimoXMouse,
					deslocamentoEmY	= mouseEvent.getY() - ultimoYMouse;

			pecaSelecionada.setCoordenadaX( pecaSelecionada.getCoordenadaX() + deslocamentoEmX );
			pecaSelecionada.setCoordenadaY( pecaSelecionada.getCoordenadaY() + deslocamentoEmY );

			ultimoXMouse = mouseEvent.getX();
			ultimoYMouse = mouseEvent.getY();

			notifyMovimento( TipoPacote.MOUSE_ARRASTADO, mouseEvent );
			super.repaint();
		}
	}

	
	public void mouseMoved( MouseEvent mouseEvent ){
		this.coordenadaX.setText( "" + mouseEvent.getX() );
		this.coordenadaY.setText( "" + mouseEvent.getY() );
	}

	public void mouseClicked( MouseEvent mouseEvent ){}
	public void mouseEntered( MouseEvent mouseEvent ){}
	public void mouseExited( MouseEvent mouseEvent ){}

	@Override
	public void tratarPacoteRecebido( Pacote pacote ){
		movimentoLocal = false;
		switch( pacote.getTipoPacote().getDescricao() ){
			case "MOUSE_PRESSIONADO":
				this.mousePressed( (MouseEvent)pacote.getPayload() );
				break;

			case "MOUSE_ARRASTADO":
				this.mouseDragged( (MouseEvent)pacote.getPayload() );
				break;

			case "MOUSE_LIBERADO":
				this.mouseReleased( (MouseEvent)pacote.getPayload() );
				break;

			default:
				break;
		}
		movimentoLocal = true;
	}
}