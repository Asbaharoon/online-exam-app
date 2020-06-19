package App;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;


public class ClientSpace extends Thread{
	public Serveur serveur;
	Socket clientSocket;
	String clientName, clientMdp;
	boolean status=false;
	int note=0;
	public DataInputStream dataIn;
	public DataOutputStream dataOut;
	ArrayList<Reponse> clientAnswers;

	public ClientSpace(Socket clientSocket, Serveur serveur) {
		this.serveur=serveur;
		this.clientSocket=clientSocket;
		this.clientAnswers=new ArrayList<Reponse>();
		try {
			dataIn=new DataInputStream(this.clientSocket.getInputStream());
			dataOut=new DataOutputStream(this.clientSocket.getOutputStream());
			clientName=dataIn.readUTF();
			clientMdp=dataIn.readUTF();
			Utilisateur user=new Utilisateur();
			try {
				if(user.testUser(clientName, clientMdp)) {
					dataOut.writeBoolean(true);
					int[] questionIds=randomNumbers(5);
					for(int i=0;i<questionIds.length;i++) {
						dataOut.writeInt(questionIds[i]);
						dataOut.writeUTF(user.getQuestionById(questionIds[i]));
						dataOut.writeUTF(user.getChoicesById(questionIds[i]));
					}
					System.out.println("Bonjour "+clientName);
				}
				else {
					dataOut.writeBoolean(false);
					serveur.listClients.remove(this);
				}
			} catch (SQLException e) {
				System.out.println("Problème de connexion au base de données");
			}
		} catch (IOException e) {
			System.out.println("Problème dans le reception du nom d'un client");
			serveur.listClients.remove(this);
		}
	}
	
	public int[] randomNumbers(int N) {
		Random r=new Random();
		ArrayList<Integer> arrayRandom=new ArrayList<Integer>();
		for(int i=0;i<N;i++) {
			int num=r.nextInt(10)+1;
			while(arrayRandom.contains(num)) {
				num=r.nextInt(10)+1;
			}
			arrayRandom.add(num);
		}
		int[] arrayInt=new int[N];
		for(int i=0;i<N;i++) {
			arrayInt[i]=arrayRandom.get(i);
		}
		return arrayInt;
	}
	
	public void run() {
		try {
			while(true) {
				if(status) {
					//sauveguarder le resultat de client
					Utilisateur user=new Utilisateur();
					boolean save=dataIn.readBoolean();
					if(save) {
						try {
							/* Si le client est deja exist dans la base de donnees 
							des client changer son resultat, sinon ajouter le client au base de donnees */
							if(user.testResultName(clientName)) {
								user.editResult(clientName, note);
							}
							else {
								user.insertResult(clientName, note);
							}
						} catch (SQLException e) {
							System.out.println("Problème de connexion au base de données");
						}
						System.out.println("Sauveguarder les donnees");
					}
					else {
						System.out.println("Annuler");
					}
					clientSocket.close();
				}
				else if(clientAnswers.size()==5) {
					//traiter le resultat de client
					String resultat;
					boolean passeExamen;
					Utilisateur user=new Utilisateur();
					try {
						for(Reponse a:clientAnswers) {
							System.out.println(a);
							if(user.testAnswer(a.getQuestionId(), a.getReponses())){
								note+=4;
							}
						}
					} catch (SQLException e) {
						System.out.println("Problème de connexion au base de données");
					}
					if(note>=10) {
						passeExamen=true;
						resultat="Félicitations tu passe l'examen avec "+note+"/20";
					}
					else {
						passeExamen=false;
						resultat="Tu échoué l'examen votre note est "+note+"/20";
					}
					dataOut.writeBoolean(passeExamen);
					dataOut.writeUTF(resultat);
					System.out.println("Client "+clientName+" resultat : "+note+"/20");
					status=true;
				}
				else {
					int questionId=dataIn.readInt();
					String answer=dataIn.readUTF();
					Reponse ans=new Reponse(questionId, answer);
					clientAnswers.add(ans);
				}
			}
		}catch (IOException e) {
			System.out.println("Client deconnecté "+clientName);
			serveur.serveurFenetre.clientsListPanel.remove(serveur.serveurFenetre.clientsListPanel.getComponent(serveur.listClients.indexOf(this)));
			serveur.serveurFenetre.clientsListPanel.repaint();
			serveur.listClients.remove(this);
			System.out.println("Il reste "+serveur.listClients.size()+" clients");
			return;
		}
	}
}
