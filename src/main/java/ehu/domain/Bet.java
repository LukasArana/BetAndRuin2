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
    //private String username;

    //@Id
    //private Question betEvent;

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

    @Override
    public String toString(){
        return "Stake: " + this.stake;
    }

    /**
    public Bet(Fee Fee, Float stake, String username, Question betEvent) {
        this.Fee = Fee;
        this.stake = stake;
        this.username = username;
        this.betEvent = betEvent;
    }
     */
}
