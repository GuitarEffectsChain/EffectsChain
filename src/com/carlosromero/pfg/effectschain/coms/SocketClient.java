package com.carlosromero.pfg.effectschain.coms;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class SocketClient extends Socket{

	public Socket miCliente;
	private String ip;
	private int port;
	private boolean connected = false;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public SocketClient(String ip, int port){
		this.ip = ip;
		this.port = port;
		this.connected = false;
	}
	
	public boolean isConnected(){
		return connected;
	}
		
	
	public void Connect() throws UnknownHostException, IOException {
		miCliente = new Socket(ip, port);

		Log.i("CONNECTION", "newcliente");
			if (miCliente.isConnected() == true) {
				connected = true;

				Log.i("CONNECTION", "Conectados");
			} else {
				connected = false;
				Log.e("CONNECTION", "!ERROR!");
			}
	}

	//Metodo de desconexion
	public void Disconnect() {
		try {
			miCliente.close();
			connected = false;
		} catch (Exception e) {
			Log.e("ERROR DISCONNECTING", e.toString());
		}
	}

	public void Snd_txt_Msg(String txt) {

		boolean val_acc = Snd_Msg(txt);
		//error al enviar
		if (!val_acc) {
			Log.e("Snd_txt_Msg() -> ", "!ERROR!");
		}
		if (!miCliente.isConnected())
			Log.e("Snd_txt_Msg() -> ", "NOT CONNECTED");
	}
	
	/*Metodo para enviar mensaje por socket
	 *recibe como parmetro un objeto Mensaje_data
	 *retorna boolean segun si se pudo establecer o no la conexion
	 */
	public boolean Snd_Msg(String msg) {

		try {			
			
			if (miCliente.isConnected())
			{
				DataOutputStream DOS = new DataOutputStream(miCliente.getOutputStream());
				DOS.writeUTF(msg);
				return true;
				
			} else {
				return false;
			}

		} catch (IOException e) {
			Log.e("Snd_Msg() ERROR -> ", "" + e);

			return false;
		}
	}
	
}
