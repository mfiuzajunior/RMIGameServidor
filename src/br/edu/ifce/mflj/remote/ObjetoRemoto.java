package br.edu.ifce.mflj.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import br.edu.ifce.mflj.dados.Pacote;

public interface ObjetoRemoto extends Remote {
	void tratarPacote( Pacote pacote ) throws RemoteException;
}
