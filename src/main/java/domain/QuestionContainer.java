package domain;

public class QuestionContainer {
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	Question question;
	Event event;
	public QuestionContainer() {
		question = null;
		event = null;
	}
	
	public QuestionContainer(Question q) {
		this.question = q;
		this.event = q.getEvent();
	}
}
