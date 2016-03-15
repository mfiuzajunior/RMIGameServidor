package br.edu.ifce.mflj.conectividade.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import br.edu.ifce.mflj.comunicacao.Pacote;
import br.edu.ifce.mflj.comunicacao.TipoPacote;
import br.edu.ifce.mflj.conectividade.CanalDeComunicacao;

public class ClienteSocket extends CanalDeComunicacao implements Runnable {

	private ObjectOutputStream	streamDeSaidaParaServidor;
	private ObjectInputStream	streamDeEntradaDoServidor;
	private Socket				socket;

	public ClienteSocket(){
		try {
			socket = new Socket( "localhost", 9000 );

			streamDeSaidaParaServidor	= new ObjectOutputStream( socket.getOutputStream() );
			streamDeEntradaDoServidor	= new ObjectInputStream( socket.getInputStream() );

			new Thread( this ).start();

		} catch( UnknownHostException unknownHostException ){
			unknownHostException.printStackTrace();

		} catch( IOException ioException ){
			ioException.printStackTrace();
		}
	}

	@Override
	public void run() {
		Pacote pacote;

		try {
			do {
				pacote = (Pacote) streamDeEntradaDoServidor.readObject();
				notifyPacoteRecebido( pacote );
			} while( pacote.getTipoPacote() != TipoPacote.FINALIZAR_CANAL );

		} catch( ClassNotFoundException classNotFoundException ){
			classNotFoundException.printStackTrace();

		} catch( IOException ioException ){
			ioException.printStackTrace();

		} finally {
			try {
				streamDeEntradaDoServidor.close();
			} catch( IOException ioException ){}

			try {
				streamDeSaidaParaServidor.close();
			} catch( IOException ioException ){}

			try {
				socket.close();
			} catch( IOException ioException ){}
		}
	}

	@Override
	public void enviarPacote( Pacote pacote ){
		try {
			streamDeSaidaParaServidor.writeObject( pacote );

		} catch( IOException ioExeception ){
			System.err.println( "Erro ao enviar pacote ao servidor" );
		}
	}
}
