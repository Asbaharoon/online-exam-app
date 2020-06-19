package App;

public class Reponse {
	private int questionId;
	private String reponses;
	
	public Reponse(int questionId, String reponses) {
		this.questionId = questionId;
		this.reponses = reponses;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getReponses() {
		return reponses;
	}

	public void setReponses(String reponses) {
		this.reponses = reponses;
	}

	@Override
	public String toString() {
		return "Reponse [questionId=" + questionId + ", reponses=" + reponses + "]";
	}
}
