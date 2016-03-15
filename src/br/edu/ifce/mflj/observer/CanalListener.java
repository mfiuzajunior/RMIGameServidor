package br.edu.ifce.mflj.observer;

import br.edu.ifce.mflj.comunicacao.Pacote;

public interface CanalListener {
	void tratarPacoteRecebido( Pacote pacote );
}
