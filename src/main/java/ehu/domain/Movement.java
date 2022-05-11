package ehu.domain;

import java.time.LocalDate;
import java.util.Date;

public class Movement {
    private Date date;
    private String event;
    private Float balance;

    public Movement(Date date, String event, Float balance) {
        this.date = date;
        this.event = event;
        this.balance = balance;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "Movement{" +
                "date=" + date +
                ", event='" + event + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
