package br.edu.ifce.mflj.comunicacao;

public enum TipoPacote {
	CHAT(				"CHAT"				),
	CHECK_IN(			"CHECK_IN"			),
	CHECK_OUT(			"CHECK_OUT"			),
	FINALIZAR_CANAL(	"FINALIZAR_CANAL"	),
	MOUSE_PRESSIONADO(	"MOUSE_PRESSIONADO"	),
	MOUSE_ARRASTADO(	"MOUSE_ARRASTADO"	),
	MOUSE_LIBERADO(		"MOUSE_LIBERADO"	);

	private String descricao;

	private TipoPacote( String descricao ){
		this.descricao = descricao;
	}

	public String getDescricao(){
		return descricao;
	}
}
