package br.edu.ifce.mflj.conectividade;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifce.mflj.comunicacao.Pacote;
import br.edu.ifce.mflj.observer.CanalListener;

public abstract class CanalDeComunicacao {

	private List<CanalListener> canalListeners;

	public CanalDeComunicacao(){
		canalListeners = new ArrayList<CanalListener>();
	}

	public void addCanalListener( CanalListener canalListener ){
		this.canalListeners.add( canalListener );
	}

	public void removeCanalListener( CanalListener canlaListener ){
		this.removeCanalListener( canlaListener );
	}

	protected void notifyPacoteRecebido( Pacote pacote ){
		for( CanalListener listenerAtual : this.canalListeners ){
			listenerAtual.tratarPacoteRecebido( pacote );
		}
	}

	public abstract void enviarPacote( Pacote pacote );
}
