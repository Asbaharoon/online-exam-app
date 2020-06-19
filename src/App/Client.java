package App;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client{
	String clientName;
	String clientMdp;
	String ipServer;
	int port;
	Socket socketClient;
	DataOutputStream dataOut;
	DataInputStream dataIn;
	ArrayList<Question> questions;
	ArrayList<Reponse> formAnswers;
	ClientExamenForm examenForm;
	String clientResultat;
	boolean clientPasseExamen;
	
	public Client() {
		new ClientConnexionForm(this);
		questions=new ArrayList<Question>();
	}

	public static void main(String[] args) {
		new Client();
	}

	public void openConnexion() {
		try {
			System.out.println("Etape 1 : Se connecter au serveur");
			socketClient=new Socket(ipServer,port);
			System.out.println("Etape 1 -> OK ");
			
			System.out.println("Etape 2 : Creation des cannaux Out et In");
			dataIn=new DataInputStream(socketClient.getInputStream());
			dataOut=new DataOutputStream(socketClient.getOutputStream());
			System.out.println("Etape 2 -> OK ");
			
			System.out.println("Etape 3 : Envoyer le nom et le mot de passe de client au serveur");
			dataOut.writeUTF(clientName);
			dataOut.writeUTF(clientMdp);
			System.out.println("Etape 3 -> OK ");
			
		}  catch (IOException e) {
			System.out.println("Probleme de connexion au serveur");
			System.exit(0);
		}
	}
	
	public void deconnecte() {
		try {
			socketClient.close();
		} catch (IOException e) {
			System.out.println("Probleme de connexion au serveur");
		}
	}

	public void fetchQuestionsFromServer() {
		try {
			for(int i=1;i<=5;i++) {
				Color questionColor;
				if(i%2==0) {
					questionColor=new Color(15,170,200);
				}
				else {
					questionColor=new Color(35,225,255);
				}
				int questionId=dataIn.readInt();
				String questionStr= "Q"+i+" : "+dataIn.readUTF();
				String choix =dataIn.readUTF();
				String[] choixTab=choix.split("-");
				Question question=new Question(questionId, questionStr, choixTab,questionColor);
				questions.add(question);
			}
		} catch (IOException e1) {
			System.out.println("Probleme de connexion au serveur");
		}
		
		for(Question q:questions) {
			examenForm.questionsPanel.add(q.questionPanel);
		}
	}
	
	public void sendAnswersToServer() {
		try {
			for(Reponse a:formAnswers) {
				dataOut.writeInt(a.getQuestionId());
				dataOut.writeUTF(a.getReponses());
			}
			clientPasseExamen=dataIn.readBoolean();
			clientResultat=dataIn.readUTF();
		}catch (IOException e1) {
			System.out.println("Probleme de connexion au serveur");
		}
	}
	
	public void sauveguarderResultat(boolean b) {
		try {
			dataOut.writeBoolean(b);
		} catch (IOException e) {
			System.out.println("Probleme de connexion au serveur");
		}
	}
}
