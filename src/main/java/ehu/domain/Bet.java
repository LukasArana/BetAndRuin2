package ehu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bet {
    private Fee fee;
    private Float stake;
    @Id
    @GeneratedValue
    private Integer id;

    public Bet(Fee fee, Float stake) {
        this.fee = fee;
        this.stake = stake;
    }

    public Fee getFee(){
        return this.fee;
    }

    public float getStake(){
        return this.stake;
    }

}
