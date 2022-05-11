package ehu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bet {
    private fee fee;
    private Float stake;
    @Id
    @GeneratedValue
    private Integer id;
    //private String username;

    //@Id
    //private Question betEvent;

    public Bet(ehu.domain.fee fee, Float stake) {
        this.fee = fee;
        this.stake = stake;
    }

    public fee getFee(){
        return this.fee;
    }

    public float getStake(){
        return this.stake;
    }


    /**
    public Bet(fee fee, Float stake, String username, Question betEvent) {
        this.fee = fee;
        this.stake = stake;
        this.username = username;
        this.betEvent = betEvent;
    }
     */
}
