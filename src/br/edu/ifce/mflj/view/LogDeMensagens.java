package br.edu.ifce.mflj.view;

import javax.swing.JTextArea;

import br.edu.ifce.mflj.comunicacao.Pacote;
import br.edu.ifce.mflj.comunicacao.TipoPacote;
import br.edu.ifce.mflj.observer.CanalListener;

public class LogDeMensagens extends JTextArea implements CanalListener {

	private static final long serialVersionUID = -7439495040989084726L;

	@Override
	public void tratarPacoteRecebido( Pacote pacote ){
		if( pacote.getTipoPacote().equals( TipoPacote.CHAT ) ){
			this.append( String.format( "%s\n", (String)pacote.getPayload() ) );
		}
	}
}