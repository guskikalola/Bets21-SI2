package domain;

public class KuotaContainer {
	Kuota kuota;
	QuestionContainer question;
	
	public KuotaContainer() {
		kuota = null;
		question = null;
	}
	
	public KuotaContainer(Kuota k) {
		kuota = k;
		question = new QuestionContainer(k.getQuestion());
	}

	public Kuota getKuota() {
		return kuota;
	}

	public void setKuota(Kuota kuota) {
		this.kuota = kuota;
	}

	public QuestionContainer getQuestion() {
		return question;
	}

	public void setQuestion(QuestionContainer question) {
		this.question = question;
	}
}
