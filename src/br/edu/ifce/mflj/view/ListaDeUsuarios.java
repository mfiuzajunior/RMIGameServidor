package br.edu.ifce.mflj.view;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import br.edu.ifce.mflj.comunicacao.Pacote;
import br.edu.ifce.mflj.comunicacao.TipoPacote;
import br.edu.ifce.mflj.dados.Usuario;
import br.edu.ifce.mflj.observer.CanalListener;

public class ListaDeUsuarios extends JList<Usuario> implements CanalListener {

	private static final long serialVersionUID = 5963633108831408666L;

	private DefaultListModel<Usuario> usuarios;

	public ListaDeUsuarios(){
		this.usuarios = new DefaultListModel<Usuario>();
		this.setModel( usuarios );
	}

	@Override
	public void tratarPacoteRecebido( Pacote pacote ) {
		if( pacote.getTipoPacote().equals( TipoPacote.CHECK_IN ) ){
			Usuario usuario = new Usuario( (String)pacote.getPayload(), pacote.getDe() );
			if( !usuarios.contains( usuario ) ){
				usuarios.addElement( usuario );
			}
		}
	}
}
