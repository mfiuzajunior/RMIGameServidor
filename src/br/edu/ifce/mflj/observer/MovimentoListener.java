package br.edu.ifce.mflj.observer;

import java.awt.event.MouseEvent;

import br.edu.ifce.mflj.comunicacao.TipoPacote;

public interface MovimentoListener {
	void movimentoRealizado( TipoPacote tipoPacote, MouseEvent mouseEvent );
}
