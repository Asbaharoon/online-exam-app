package App;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Question{
	private int questionId;
	private String question;
	private String[] choix;
	private Color questionColor;
	public JCheckBox[] cbox;
	public JPanel questionPanel;
	
	public Question(int questionId, String question, String[] choix,Color questionColor) {
		this.questionId = questionId;
		this.question = question;
		this.choix = choix;
		this.questionColor=questionColor;
		questionPanel=new JPanel(new GridLayout(2,1));
		questionPanel.setBackground(questionColor);
		JLabel questionLabel=new JLabel(question);
		questionPanel.add(questionLabel);
		JPanel choicesPanel=new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
		choicesPanel.setBackground(questionColor);
		cbox=new JCheckBox[choix.length];
		
		for(int i=0;i<choix.length;i++) {
			cbox[i]=new JCheckBox(choix[i]);
			cbox[i].setBackground(questionColor);
			choicesPanel.add(cbox[i]);
		}
		questionPanel.add(choicesPanel);
	}

	public Color getQuestionColor() {
		return questionColor;
	}

	public void setQuestionColor(Color questionColor) {
		this.questionColor = questionColor;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getChoix() {
		return choix;
	}

	public void setChoix(String[] choix) {
		this.choix = choix;
	}
}
