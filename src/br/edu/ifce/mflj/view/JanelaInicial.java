package br.edu.ifce.mflj.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.edu.ifce.mflj.comunicacao.Pacote;
import br.edu.ifce.mflj.comunicacao.TipoPacote;
import br.edu.ifce.mflj.conectividade.CanalDeComunicacao;
import br.edu.ifce.mflj.dados.DimensoesTabuleiro;
import br.edu.ifce.mflj.observer.MovimentoListener;

public class JanelaInicial implements MovimentoListener {

	private JFrame				janelaPrincipal;
	private	JScrollPane			scrollLogDeMensagens,
								scrollEditorDeMensagens,
								scrollListaDeUsuarios;
	private	JButton				botaoEnviarMensagem;
	private JTextArea			textEditorDeMensagens;
	private TabuleiroPanel		panelTabuleiro;

	private	ListaDeUsuarios		listaDeUsuarios;
	private	LogDeMensagens		logDeMensagens;

	private CanalDeComunicacao	canalDeComunicacao;

	private String				apelido;

	public TabuleiroPanel getPanelTabuleiro() {
		return panelTabuleiro;
	}

	public void setPanelTabuleiro(TabuleiroPanel panelTabuleiro) {
		this.panelTabuleiro = panelTabuleiro;
	}

	public JFrame getJanelaPrincipal() {
		return janelaPrincipal;
	}

	public void setJanelaPrincipal( JFrame janelaPrincipal ){
		this.janelaPrincipal = janelaPrincipal;
	}

	public JScrollPane getScrollLogDeMensagens() {
		return scrollLogDeMensagens;
	}

	public void setScrollLogDeMensagens(JScrollPane scrollLogDeMensagens) {
		this.scrollLogDeMensagens = scrollLogDeMensagens;
	}

	public JScrollPane getScrollEditorDeMensagens() {
		return scrollEditorDeMensagens;
	}

	public void setScrollEditorDeMensagens(JScrollPane scrollEditorDeMensagens) {
		this.scrollEditorDeMensagens = scrollEditorDeMensagens;
	}

	public LogDeMensagens getLogDeMensagens() {
		return logDeMensagens;
	}

	public void setLogDeMensagens(LogDeMensagens logDeMensagens) {
		this.logDeMensagens = logDeMensagens;
	}

	public JButton getBotaoEnviarMensagem() {
		return botaoEnviarMensagem;
	}

	public void setBotaoEnviarMensagem(JButton botaoEnviarMensagem) {
		this.botaoEnviarMensagem = botaoEnviarMensagem;
	}

	public JTextArea getTextEditorDeMensagens() {
		return textEditorDeMensagens;
	}

	public void setTextEditorDeMensagens(JTextArea textEditorDeMensagens) {
		this.textEditorDeMensagens = textEditorDeMensagens;
	}

	public ListaDeUsuarios getListaDeUsuarios() {
		return listaDeUsuarios;
	}

	public void setListaDeUsuarios(ListaDeUsuarios listaDeUsuarios) {
		this.listaDeUsuarios = listaDeUsuarios;
	}

	public CanalDeComunicacao getCanalDeComunicacao() {
		return canalDeComunicacao;
	}

	public void setCanalDeComunicacao(CanalDeComunicacao canalDeComunicacao) {
		this.canalDeComunicacao = canalDeComunicacao;
	}

	public JScrollPane getScrollListaDeUsuarios() {
		return scrollListaDeUsuarios;
	}

	public void setScrollListaDeUsuarios(JScrollPane scrollListaDeUsuarios) {
		this.scrollListaDeUsuarios = scrollListaDeUsuarios;
	}

	public void inicializarInterfaceGrafica(){
		apelido = JOptionPane.showInputDialog( janelaPrincipal, "Como deseja ser chamado?" );

		janelaPrincipal.setResizable( false );
		janelaPrincipal.setBounds( 100, 100, 950, 550 );
		janelaPrincipal.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		janelaPrincipal.getContentPane().setLayout( null );
		janelaPrincipal.setVisible( true );
		janelaPrincipal.setTitle( "Seja bem vindo " + apelido );

		scrollLogDeMensagens.setViewportView( logDeMensagens );
		logDeMensagens.setEditable( false );

		scrollEditorDeMensagens.setViewportView( textEditorDeMensagens );

		scrollListaDeUsuarios.setViewportView( listaDeUsuarios );

		botaoEnviarMensagem.addActionListener( new ActionListener() {
			@Override
			public void actionPerformed( ActionEvent actionEvent ){
				enviarMensagem();
			}
		});

		scrollLogDeMensagens.setBounds( 5, 5, 160, 0 );
		panelTabuleiro.setBounds(	DimensoesTabuleiro.LIMITE_INICIAL_X + scrollLogDeMensagens.getWidth() + 10,
									DimensoesTabuleiro.LIMITE_INICIAL_Y + 5,
									DimensoesTabuleiro.LIMITE_FINAL_X + 5,
									DimensoesTabuleiro.LIMITE_FINAL_Y + 5 );
		scrollLogDeMensagens.setSize(	scrollLogDeMensagens.getWidth(),
										panelTabuleiro.getHeight() );
		scrollListaDeUsuarios.setBounds(	panelTabuleiro.getX() + panelTabuleiro.getWidth() + 5, 5,
											160, panelTabuleiro.getHeight() );
		scrollEditorDeMensagens.setBounds(	5, panelTabuleiro.getHeight() + 10,
											scrollLogDeMensagens.getWidth() + panelTabuleiro.getWidth() + scrollListaDeUsuarios.getWidth() + 10, 40 );
		botaoEnviarMensagem.setBounds(	( scrollEditorDeMensagens.getX() + scrollEditorDeMensagens.getWidth() ) - 117, 
										scrollEditorDeMensagens.getY() + scrollEditorDeMensagens.getHeight() + 5,
										117, 25 );

		janelaPrincipal.getContentPane().add( scrollLogDeMensagens );
		janelaPrincipal.getContentPane().add( panelTabuleiro );
		janelaPrincipal.getContentPane().add( scrollListaDeUsuarios );
		janelaPrincipal.getContentPane().add( botaoEnviarMensagem );
		janelaPrincipal.getContentPane().add( scrollEditorDeMensagens );

		panelTabuleiro.addMovimentoListener( this );
		canalDeComunicacao.addCanalListener( logDeMensagens );
		canalDeComunicacao.addCanalListener( listaDeUsuarios );
		canalDeComunicacao.addCanalListener( panelTabuleiro );

		canalDeComunicacao.enviarPacote( new Pacote( TipoPacote.CHECK_IN, UUID.randomUUID().toString(), null, apelido ) );

	}

	private void enviarMensagem(){
		String mensagem = "";
		if( listaDeUsuarios.getSelectedValue() == null ){
			JOptionPane.showMessageDialog( null, "Selecione um advers\u00E1rio", "Aten\u00E7\u00E3o", JOptionPane.WARNING_MESSAGE );

		} else if( !textEditorDeMensagens.getText().equals( "" ) ){
			mensagem = String.format( "%s: %s", apelido, textEditorDeMensagens.getText() );
			canalDeComunicacao.enviarPacote(	new Pacote(	TipoPacote.CHAT,
															null,
															listaDeUsuarios.getSelectedValue().getIdentificador(),
															mensagem ) );
			textEditorDeMensagens.setText( "" );
			logDeMensagens.append( String.format( "%s\n", mensagem ) );
		}
	}

	@Override
	public void movimentoRealizado( TipoPacote tipoPacote, MouseEvent mouseEvent ){
		if( listaDeUsuarios.getSelectedValue() != null ){
			canalDeComunicacao.enviarPacote(	new Pacote(	tipoPacote,
															null,
															listaDeUsuarios.getSelectedValue().getIdentificador(),
															mouseEvent ) );
		}
	}
}
