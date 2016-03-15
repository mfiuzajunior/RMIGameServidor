package br.edu.ifce.mflj.comunicacao;

import java.io.Serializable;

public class Pacote implements Serializable {

	private static final long serialVersionUID = 5110207657333808602L;

	private TipoPacote	tipoPacote;
	private	Object		payload;
	private String		de,
						para;

	public Pacote(){}

	public Pacote( TipoPacote tipoPacote, String de, String para, Object payload ){
		this.setTipoPacote( tipoPacote );
		this.setDe( de );
		this.setPara( para );
		this.setPayload( payload );
	}

	public TipoPacote getTipoPacote() {
		return tipoPacote;
	}

	public void setTipoPacote(TipoPacote tipoPacote) {
		this.tipoPacote = tipoPacote;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public String getDe() {
		return de;
	}

	public void setDe(String de) {
		this.de = de;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}
}
