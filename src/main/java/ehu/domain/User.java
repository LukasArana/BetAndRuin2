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
	@OneToMany(cascade = CascadeType.PERSIST)
	private ArrayList<Movement> movements;


	public User(int age, String username, String password, String realName, String surname, String email) {
		this.age = age;
		this.username = username;
		this.password = password;
		this.realName = realName;
		this.surname = surname;
		this.email = email;
		this.availableMoney = (float) 0.0;
		this.movements = new ArrayList<>();
	}

	public User(){
		this.availableMoney = (float) 0.0;
		this.movements = new ArrayList<>();
	}

	public User(String username, String password, Boolean admin) {
		this.username = username;
		this.password = password;
		this.admin = admin;
		this.availableMoney = (float) 0.0;
		this.movements = new ArrayList<>();
	}



	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.admin = true;
		this.availableMoney = (float) 0.0;
		this.movements = new ArrayList<>();
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


	public void addMovement(Movement m){
		this.movements.add(m);
	}
	public ArrayList<Movement> getMovements(){return this.movements;}

	public void addBet(Bet b) {
		betList.add(b);
	}

	public List<Bet> getBetList(){return this.betList;}

    public void setPassword(String pass) {
		this.password = pass;
    }
}
