package ehu.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Vector;
@Entity
public class Fee {
	@Id
	private Float fee;
	@Id
	private String result;

	@OneToMany(cascade = CascadeType.REMOVE)
	private Vector<Bet> betList = new Vector<Bet>();


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

	public Bet addBet(Bet b){
		betList.add(b);
		return b;
	}



	@Override
	public String toString() {
		return result+ ": " + fee;
	}
}
