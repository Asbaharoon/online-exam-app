package App;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class ClientExamenForm extends JFrame 
{
	JScrollPane defil; 
	JLabel formTitle;
	JLabel questionLabel;
	JButton submit;
	JPanel borderPanel,flowPanel1, flowPanel2,questionsPanel;
	Client client;
	
	public ClientExamenForm(Client x) 
	{
		client=x;
		initFrame();
		
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.formAnswers=new ArrayList<Reponse>();
				for(Question q:client.questions) {
					String answersList="";
					for(JCheckBox cb:q.cbox) {
						if(cb.isSelected()) {
							if(answersList.contentEquals("")) {
								answersList+=cb.getText();
							}
							else {
								answersList+="-"+cb.getText();
							}
						}
					}
					if(answersList.equals("")) {
						JOptionPane.showMessageDialog(null, "Question "+q.getQuestion().substring(0, 2)+" n'est pas sélectionnée!", "Warning",JOptionPane.WARNING_MESSAGE);
					}
					else {
						Reponse ans=new Reponse(q.getQuestionId(), answersList);
						client.formAnswers.add(ans);
					}
				}
				if(client.formAnswers.size()==5) {
					client.sendAnswersToServer();//envoyer les reponses au serveur
					dispose();//fermer le formulaire d'examen
					new ClientResultat(client);//afficher le resultat d'utilisateur
				}
			}
		});
		
		setVisible(true);
	}
	
	public void initFrame()
	{
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setTitle ("Connecté au Serveur [ IP:"+client.ipServer+" , Port: "+client.port+" ]") ;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(150,30));
		submit.setBackground(new Color(59, 89, 182));
		submit.setForeground(Color.white);
		submit.setBorder(null);
		
		formTitle = new JLabel(client.clientName+" - Examen Form");
		formTitle.setFont(new Font("Georgia", 0, 22));
		formTitle.setForeground(new Color(35,185,185));

		questionsPanel = new JPanel();
		questionsPanel.setBackground(Color.cyan);
		questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
		
		JScrollPane defil = new JScrollPane(questionsPanel);
		defil.setBorder(null);
		
		flowPanel1=new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
		flowPanel1.add(submit);
		flowPanel1.setBackground(new Color(10,10,50));
		flowPanel2=new JPanel(new FlowLayout(FlowLayout.CENTER));
		flowPanel2.add(formTitle);
		flowPanel2.setBackground(new Color(10,10,50));
		
		borderPanel = new JPanel(new BorderLayout());
		borderPanel.add(defil, "Center");
		borderPanel.add(flowPanel1, "South");
		borderPanel.add(flowPanel2, "North");
		
		this.setContentPane(borderPanel);
	}		
}
