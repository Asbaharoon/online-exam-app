package App;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ClientResultat extends JFrame{
	Client client;
	JPanel titlePanel;
	JPanel imagePanel;
	JPanel mainPanel;
	JPanel southPanel;
	JLabel resultLabel;
	JButton sauvegarder, annuler;
	
	ClientResultat(Client x){
		client=x;
		initFrame();
	}
	
	public void initFrame() {
		this.setSize(500,550);
		this.setLocationRelativeTo(null);
		this.setTitle ("Connecté au Serveur [ IP:"+client.ipServer+" , Port: "+client.port+" ]") ;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel=new JPanel(new BorderLayout());
		titlePanel=new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));
		titlePanel.setBackground(new Color(10,10,50));
		resultLabel=new JLabel(client.clientResultat);
		resultLabel.setFont(new Font("Arial", 0, 24));
		resultLabel.setForeground(Color.white);
		titlePanel.add(resultLabel);
		
		ImageIcon img;
		
		//verifier si l'utilisateur passe l'examen ou non pour afficher l'image appropriée (visage triste ou heureux)
		if(client.clientPasseExamen) {
			img=new ImageIcon("./ressources/happy_face.png");
		}
		else {
			img=new ImageIcon("./ressources/sad_face.png");
		}
		imagePanel=new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.drawImage(img.getImage(), 0,0,this.getWidth(),this.getHeight(),this);
			}
		};
		imagePanel.setBackground(new Color(10,10,50));
		
		annuler= new JButton("Annuler");
		annuler.setPreferredSize(new Dimension(120,30));
		annuler.setBackground(new Color(59, 89, 182));
		annuler.setForeground(Color.white);
		annuler.setBorder(null);
		annuler.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sauveguarderResultat(false);
				System.exit(0);
			}
		});
		
		sauvegarder=new JButton("Sauvegarder");
		sauvegarder.setPreferredSize(new Dimension(120,30));
		sauvegarder.setBackground(new Color(59, 89, 182));
		sauvegarder.setForeground(Color.white);
		sauvegarder.setBorder(null);
		sauvegarder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				client.sauveguarderResultat(true);
				System.exit(0);
			}
		});
		
		southPanel=new JPanel(new FlowLayout(1, 10, 10));
		southPanel.setBackground(new Color(10,10,50));
		southPanel.add(sauvegarder);
		southPanel.add(annuler);
		
		mainPanel.add(titlePanel,"North");
		mainPanel.add(imagePanel,"Center");
		mainPanel.add(southPanel,"South");
		this.setContentPane(mainPanel);
		this.setVisible(true);
	}
}
