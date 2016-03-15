package br.edu.ifce.mflj.main;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.edu.ifce.mflj.view.JanelaInicial;

public class IniciarJogo {
	public static void main( String[] args ){
		BeanFactory beanFactoty = new ClassPathXmlApplicationContext( new String[] { "configuracao.xml" } );
		( (JanelaInicial) beanFactoty.getBean( "janelaInicial" ) ).inicializarInterfaceGrafica();
	}
}
