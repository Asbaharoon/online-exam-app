package App;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Serveur {
	public ServerSocket socketServer;
	String ip;
	int port=3000;
	public ArrayList<ClientSpace> listClients;
	ServeurFenetre serveurFenetre;

	public static void main(String[] args) {
		new Serveur();
	}
	
	public Serveur() {
		listClients=new ArrayList<ClientSpace>();
		exec();
	}
	

	public void exec() {
		try {
			ip=InetAddress.getLocalHost().getHostAddress().toString();
			socketServer=new ServerSocket(port);
			serveurFenetre=new ServeurFenetre(this);
	
			while(true) {
				Socket clientSocket=socketServer.accept();
				ClientSpace c=new ClientSpace(clientSocket,this);
				c.start();
				this.listClients.add(c);
				
				//Ajouter client au fenetre du Serveur
				JButton clientBtn=new JButton(c.clientName);
				clientBtn.setPreferredSize(new Dimension(115,30));
				clientBtn.setBackground(new Color(59, 89, 182));
				clientBtn.setForeground(Color.white);
				clientBtn.setBorder(null);
				
				clientBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						serveurFenetre.clientSelectionne.setText(c.clientName);
					}
				});
				serveurFenetre.clientsListPanel.add(clientBtn);
				serveurFenetre.validate();
				
				System.out.println("Server : nouveau client connecte il y a "+this.listClients.size()+" clients");
			}
		} catch (UnknownHostException e) {
			System.out.println("Impossible de resoudre l'adresse du serveur!");
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Impossible de crée serveur!");
			System.exit(0);
		}
		
	}

	public void deconnecteClient(String username) {
		for(ClientSpace c:listClients) {
			if(c.clientName.equals(username)) {
				try {
					c.clientSocket.close();
				} catch (IOException e) {
					System.out.println("Probleme de connexion au serveur");
				}
			}
		}
	}
}
