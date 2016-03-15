package br.edu.ifce.mflj.conectividade.sockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import br.edu.ifce.mflj.comunicacao.Pacote;
import br.edu.ifce.mflj.comunicacao.TipoPacote;
import br.edu.ifce.mflj.conectividade.CanalDeComunicacao;

public class ServidorSocket extends CanalDeComunicacao implements Runnable {

	private Map<String, ObjectOutputStream>	saidasParaClientes	= new HashMap<String, ObjectOutputStream>();
	private Map<String, String>				clientesAlias		= new HashMap<String, String>();
	private ServerSocket					serverSocket;

	@Override
	public void enviarPacote( Pacote pacote ) {
	}

	@Override
	public void run() {
		ObjectOutputStream	saidaParaCliente	= null;
		ObjectInputStream	streamDeEntrada		= null;
		Socket				clienteSocket		= null;
		Pacote				pacote				= null;

		try {
			if( this.serverSocket == null ){
				serverSocket = new ServerSocket( 9000 );
			}

			clienteSocket	= serverSocket.accept();

			new Thread( this ).start();

			saidaParaCliente	= new ObjectOutputStream( clienteSocket.getOutputStream() );
			streamDeEntrada		= new ObjectInputStream( clienteSocket.getInputStream() );

			do {
				pacote = (Pacote) streamDeEntrada.readObject();

				switch( pacote.getTipoPacote().getDescricao() ) {
					case "CHECK_IN":
						broadcastPacote( pacote );
						informarClientesConectados( saidaParaCliente );
						saidasParaClientes.put( pacote.getDe(), saidaParaCliente );
						clientesAlias.put( pacote.getDe(), (String)pacote.getPayload() );
						break;

					case "CHECK_OUT":
						clientesAlias.remove( pacote.getDe() );
						saidasParaClientes.get( pacote.getDe() ).close();
						saidasParaClientes.remove( pacote.getDe() );
						broadcastPacote( pacote );
						break;

					default:
						saidasParaClientes.get( pacote.getPara() ).writeObject( pacote );
						break;
				}
			} while( pacote.getTipoPacote() != TipoPacote.FINALIZAR_CANAL );

		} catch( IOException ioException ){
			System.err.println( "Erro ao criar socket no servidor: " + ioException.getMessage() );

		} catch( ClassNotFoundException classNotFoundException ){
			System.err.println( "Erro ao ler pacote do cliente: " + classNotFoundException.getMessage() );

		} catch( ClassCastException classCastException ){
			System.err.println( "Pacote inválido recebido: " + classCastException.getMessage() );

		} finally {
			try {
				streamDeEntrada.close();
			} catch( IOException | NullPointerException exception ){}

			try {
				saidaParaCliente.close();
			} catch( IOException | NullPointerException exception ){}

			try {
				clienteSocket.close();
			} catch( IOException | NullPointerException exception ){}
		}
	}

	private void broadcastPacote( Pacote pacote ) throws IOException {
		for( Map.Entry<String, ObjectOutputStream> streamAtual : saidasParaClientes.entrySet() ){  
			String				identificadorAtual	= streamAtual.getKey();  
			ObjectOutputStream	streamDeSaida		= saidasParaClientes.get( identificadorAtual );

			streamDeSaida.writeObject( pacote );
		}
	}

	private void informarClientesConectados( ObjectOutputStream saidaParaCliente ) throws IOException {
		for( Map.Entry<String, ObjectOutputStream> streamAtual : saidasParaClientes.entrySet() ){  
			String identificadorAtual = streamAtual.getKey();  
			saidaParaCliente.writeObject( new Pacote( TipoPacote.CHECK_IN, identificadorAtual, null, clientesAlias.get( identificadorAtual ) ) );
		}
	}

	public static void main(String[] args) {
		new Thread( new ServidorSocket() ).start();
	}
}
