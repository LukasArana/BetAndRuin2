package ehu.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User {
	private int age;
	@Id
	private String username;
	private String password;
	private boolean admin;
	private String realName;
	private String surname;
	private String email;
	private Float availableMoney;
	@OneToMany(cascade = CascadeType.PERSIST)
	private ArrayList<Bet> betList = new ArrayList<>();
	private ArrayList<Float> moneyMovements;
	private ArrayList<Date> dateList;
	private ArrayList<String> eventList;



	public User(int age, String username, String password, String realName, String surname, String email) {
		this.age = age;
		this.username = username;
		this.password = password;
		this.realName = realName;
		this.surname = surname;
		this.email = email;
		this.availableMoney = (float) 0.0;
		this.moneyMovements = new ArrayList<>();
		this.dateList = new ArrayList<>();
		this.eventList = new ArrayList<>();
	}

	public User(){
		this.availableMoney = (float) 0.0;
		this.moneyMovements = new ArrayList<>();
		this.dateList = new ArrayList<>();
		this.eventList = new ArrayList<>();
	}

	public User(String username, String password, Boolean admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.availableMoney = (float) 0.0;
		this.moneyMovements = new ArrayList<>();
		this.dateList = new ArrayList<>();
		this.eventList = new ArrayList<>();
	}



	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.admin = true;
		this.availableMoney = (float) 0.0;
		this.moneyMovements = new ArrayList<>();
		this.dateList = new ArrayList<>();
		this.eventList = new ArrayList<>();
	}
	
	public int getAge() {
		return this.age;
	}
	public String getEmail(){return this.email;}
	public String getUsername() {
		return this.username;
	}
	public boolean isAdmin() {
		return this.admin;
	}
	public void updateAvailableMoney(float money){this.availableMoney += money;}

    public float getAvailableMoney() {
		return this.availableMoney;
    }


	public void addMovement(Float f){
		this.moneyMovements.add(f);
	}
	public ArrayList<Float> getMoneyMovements(){return this.moneyMovements;}

	public ArrayList<Date> getDateList() {
		return dateList;
	}

	public void addDate(Date d){
		dateList.add(d);
	}

	public void setEventList(ArrayList<String> eventList) {
		this.eventList = eventList;
	}

	public ArrayList<String> getEventList() {
		return eventList;
	}

	public void addEvent(String e){
		eventList.add(e);
	}

	public void addBet(Bet b) {
		betList.add(b);
	}

	public List<Bet> getBetList(){return this.betList;}

    public void setPassword(String pass) {
		this.password = pass;
    }
}
