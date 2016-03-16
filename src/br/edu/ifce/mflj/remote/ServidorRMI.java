package br.edu.ifce.mflj.remote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

import br.edu.ifce.mflj.dados.Pacote;
import br.edu.ifce.mflj.dados.TipoPacote;

public class ServidorRMI extends UnicastRemoteObject implements ObjetoRemoto {

	protected ServidorRMI() throws RemoteException {
		super();
	}

	private static final long serialVersionUID = -794283732233987094L;

	private Map<String, ObjetoRemoto>	clientesConectados	= new HashMap<String, ObjetoRemoto>();
	private Map<String, String>			clientesAlias		= new HashMap<String, String>();

	@Override
	public void tratarPacote( Pacote pacote ) {
		try {
			do {
				switch( pacote.getTipoPacote().getDescricao() ) {
					case "CHECK_IN":
						ObjetoRemoto clienteNovo = (ObjetoRemoto)Naming.lookup( "rmi://localhost/" + pacote.getDe() );
						broadcastPacote( pacote );
						informarClientesConectadosPara( clienteNovo );
						clientesConectados.put( pacote.getDe(), clienteNovo );
						clientesAlias.put( pacote.getDe(), (String)pacote.getPayload() );
						break;

					case "CHECK_OUT":
						clientesAlias.remove( pacote.getDe() );
						clientesConectados.remove( pacote.getDe() );
						broadcastPacote( pacote );
						break;

					default:
						clientesConectados.get( pacote.getPara() ).tratarPacote( pacote );
						break;
				}
			} while( pacote.getTipoPacote() != TipoPacote.FINALIZAR_CANAL );

		} catch( RemoteException remoteException ){
			System.err.println( "Erro ao se comunicar com clientes: " + remoteException.getMessage() );

		} catch( ClassCastException classCastException ){
			System.err.println( "Pacote inválido recebido: " + classCastException.getMessage() );

		} catch( NotBoundException notBoundException ){
			System.err.println( "NotBoundException no Servidor: " + notBoundException.getMessage() );

		} catch( MalformedURLException malformedURLException ){
			System.err.println( "MalformedURLException - Cliente não registrado: " + malformedURLException.getMessage() );
		}
	}

	/**
	 * Envia o pacote para todos os clientes que já se conectaram
	 * @param pacote
	 * @throws RemoteException 
	 * @throws IOException
	 */
	private void broadcastPacote( Pacote pacote ) throws RemoteException {
		for( Map.Entry<String, ObjetoRemoto> clienteAtual : clientesConectados.entrySet() ){  
			String identificadorAtual = clienteAtual.getKey();  
			clientesConectados.get( identificadorAtual ).tratarPacote( pacote );
		}
	}

	/**
	 * Informa para o clinete novo os clientes que já se encontram conectados
	 * @param objetoRemotoClienteNovo
	 * @throws RemoteException 
	 * @throws IOException
	 */
	private void informarClientesConectadosPara( ObjetoRemoto objetoRemotoClienteNovo ) throws RemoteException {
		for( Map.Entry<String, ObjetoRemoto> objetoRemotoAtual : clientesConectados.entrySet() ){  
			String identificadorAtual = objetoRemotoAtual.getKey();  
			objetoRemotoClienteNovo.tratarPacote( new Pacote( TipoPacote.CHECK_IN, identificadorAtual, null, clientesAlias.get( identificadorAtual ) ) );
		}
	}

	public static void main( String[] args ){
		try {
			ServidorRMI servidor = new ServidorRMI();
			Naming.rebind("rmi://localhost/servidor", servidor);

		} catch( RemoteException remoteException ){
			remoteException.printStackTrace();

		} catch( MalformedURLException MalformedURLException ){
			MalformedURLException.printStackTrace();
		}
	}
}
