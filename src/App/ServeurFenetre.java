package App;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ServeurFenetre extends JFrame {
	Serveur serveur;
	JPanel mainPanel;
	JPanel northPanel;
	JPanel clientsListPanel;
	JPanel clientInfo;
	JPanel gridPanel;
	JPanel southPanel;
	JLabel resultLabel;
	JTextField clientSelectionne;
	JButton decBtn,infoBtn;
	
	ServeurFenetre(Serveur x){
		serveur=x;
		initFrame();
	}
	
	public void initFrame() {
		this.setSize(800,500);
		this.setLocationRelativeTo(null);
		this.setTitle ("À communiquer aux clients [ IP:"+serveur.ip+" , Port: "+serveur.port+" ]") ;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel=new JPanel(new BorderLayout());
		
		JLabel label=new JLabel("Liste des clients : ");
		label.setFont(new Font("Arial", 0, 24));
		label.setForeground(Color.white);
		northPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
		northPanel.setBackground(new Color(10,10,50));
		northPanel.add(label);
		
		clientInfo= new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		clientInfo.setBackground(new Color(10,10,50));
		clientInfo.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		
		clientsListPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
		clientsListPanel.setBackground(new Color(10,10,50));
		clientsListPanel.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));;

		gridPanel=new JPanel(new GridLayout(1,2));
		gridPanel.setBackground(new Color(10,10,50));
		gridPanel.add(clientsListPanel);
		gridPanel.add(clientInfo);
		
		gridPanel.setBorder(BorderFactory.createLineBorder(new Color(50,150,250),2));
		
		clientSelectionne=new JTextField(20);
		clientSelectionne.setPreferredSize(new Dimension(300,30));
		clientSelectionne.setBorder(BorderFactory.createCompoundBorder(null, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		
		decBtn=new JButton("Déconnecter");
		decBtn.setPreferredSize(new Dimension(150,30));
		decBtn.setBackground(new Color(225, 80, 70));
		decBtn.setForeground(Color.white);
		decBtn.setBorder(null);
		
		decBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				serveur.deconnecteClient(clientSelectionne.getText());
			}
		});
		
		infoBtn=new JButton("Informations");
		infoBtn.setPreferredSize(new Dimension(150,30));
		infoBtn.setBackground(new Color(120, 220, 85));
		infoBtn.setForeground(Color.white);
		infoBtn.setBorder(null);
		
		southPanel=new JPanel(new FlowLayout(FlowLayout.LEFT,20,20));
		southPanel.setBackground(new Color(10,10,50));
		southPanel.add(clientSelectionne);
		southPanel.add(infoBtn);
		southPanel.add(decBtn);
	
		mainPanel.add(northPanel,"North");
		mainPanel.add(gridPanel,"Center");
		mainPanel.add(southPanel,"South");
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}
}
