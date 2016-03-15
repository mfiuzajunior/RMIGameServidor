package br.edu.ifce.mflj.jogo;

import java.util.List;

import br.edu.ifce.mflj.dados.Casa;
import br.edu.ifce.mflj.dados.Peca;
import br.edu.ifce.mflj.jogo.excecoes.MovimentoException;

public class Regulamento {

	private List<Regra> regras;

	public List<Regra> getRegras() {
		return regras;
	}

	public void setRegras( List<Regra> regras ){
		this.regras = regras;
	}

	public Boolean movimentoPermitido( Casa[][] tabuleiro, Casa casaDestino, Peca pecaEmMovimento ) throws MovimentoException {
		for( Regra regraAtual : this.getRegras() ){
			if( !regraAtual.movimentoValido( tabuleiro, casaDestino, pecaEmMovimento ) ){
				return false;
			}
		}

		return true;
	}
}
