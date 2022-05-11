package ehu.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Fee {
	@Id
	private Float fee;
	@Id
	private String result;

	public Fee(Float fee, String result) {
		this.fee = fee;
		this.result = result;
	}
	
	public String getResult() {
		return this.result;
	}

	public void setFee(Float fee) {
		this.fee = fee;
	}

	public Float getFee() {return this.fee;}

	@Override
	public String toString() {
		return result+ ": " + fee;
	}
}
