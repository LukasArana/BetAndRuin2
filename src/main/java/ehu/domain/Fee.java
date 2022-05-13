package ehu.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Fee {
	@Id
	private Float fee;
	@Id
	private String result;
	@OneToMany(cascade = CascadeType.PERSIST)
	private Question question;



	public Fee(Float fee, String result, Question question) {
		this.fee = fee;
		this.result = result;
		this.question = question;
	}
	
	public String getResult() {
		return this.result;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public Float getFee() {return this.fee;}

	public Question getQuestion() {return question;}


	@Override
	public String toString() {
		return result+ ": " + fee;
	}
}
