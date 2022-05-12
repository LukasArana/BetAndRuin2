package ehu.dataAccess;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


import ehu.domain.*;
import ehu.exceptions.EventAlreadyExists;
import ehu.exceptions.QuestionAlreadyExist;
import ehu.configuration.ConfigXML;
import ehu.configuration.UtilDate;

/**
 * Implements the Data Access utility to the objectDb database
 */
public class DataAccess {

	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML config = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {
		System.out.println("Creating DataAccess instance => isDatabaseLocal: " +
				config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());
		open(initializeMode);
	}

	public DataAccess() {
		this(true);
	}


	/**
	 * This method initializes the database with some trial events and questions.
	 * It is invoked by the business logic when the option "initialize" is used
	 * in the tag dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event("Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event("Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event("Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event("Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event("Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event("Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event("Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event("Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event("Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event("Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));


			Event ev17 = new Event("Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
			Event ev18 = new Event("Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
			Event ev19 = new Event("Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
			Event ev20 = new Event("Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));

			Event deposit = new Event("Deposit money", UtilDate.newDate(2003, 12, 17));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q1.addFee(new Fee((float) 1.9, "Atletico"));
				q1.addFee(new Fee((float) 3.5, "Draw"));
				q1.addFee(new Fee((float) 3.0, "Athletic"));
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
			}

			//Fee f1 = q1.addFee(new Fee(5F,"Atletico"));

			User u2 = new User("user", "user", false);
			User u1 = new User("admin", "admin", true);

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			//db.persist(f1);

			db.persist(u1);
			db.persist(u2);
			db.getTransaction().commit();
			System.out.println("The database has been initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 *
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum)
			throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event = " + event + " question = " +
				question + " minimum bet = " + betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.doesQuestionExist(question)) throw new QuestionAlreadyExist(
				ResourceBundle.getBundle("Etiquetas").getString("ErrorQuestionAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		//db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added 
		// in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;
	}


	/**
	 * This method retrieves from the database the events of a given date
	 *
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {

		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",
				Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(" Eventua: " + ev.toString());
			res.add(ev);
		}
		System.out.println("Eventuak = " + res);
		return res;
	}

	public Date getEventDate(Movement move){
		System.out.println(">> DataAccess: getEventDate");
		Date d = new Date();
		TypedQuery<Event> event = db.createQuery("select ev from Event ev where ev.description=?1", Event.class);
		event.setParameter(1, move.getEvent());
		Event ev2 = event.getSingleResult();

		d = ev2.getEventDate();

		System.out.println("Datak = " + d);
		return d;

	}

	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 *
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);
		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev "
				+ "WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public boolean checkUsername(String username) {
		TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u WHERE u.username = ?1", User.class);
		q1.setParameter(1, username);
		List<User> user = q1.getResultList();
		return user.isEmpty();
	}

	public boolean checkEmail(String email) {
		TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u WHERE u.email = ?1", User.class);
		q1.setParameter(1, email);
		List<User> user = q1.getResultList();
		return user.isEmpty();
	}

	public User register(int age, String username, String password, String name, String surname, String email) {
		db.getTransaction().begin();
		User u = new User(age, username, password, name, surname, email);
		db.persist(u);
		db.getTransaction().commit();
		System.out.println("New user has been registered in the database");
		return u;
	}

	public User register(String username, String password){
		db.getTransaction().begin();
		User u = new User(username, password);
		db.persist(u);
		db.getTransaction().commit();
		System.out.println("New user has been registered in the database");
		return u;
	}

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " +
				config.isDataAccessLocal() + " getDatabBaseOpenMode: " + config.getDataBaseOpenMode());

		String fileName = config.getDataBaseFilename();

		if (Files.exists(Paths.get(System.getProperty("user.home") + "/config/" + fileName))) {
			System.out.println("Opening db from user.home");
			fileName = System.getProperty("user.home") + "/config/" + fileName;
		}
		System.setProperty("objectdb.home", System.getProperty("user.home") + "/config"); // new $objectdb

		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (config.isDataAccessLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", config.getDataBaseUser());
			properties.put("javax.persistence.jdbc.password", config.getDataBasePassword());

			emf = Persistence.createEntityManagerFactory("objectdb://" + config.getDataAccessNode() +
					":" + config.getDataAccessPort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion => event = " + event +
				" question = " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.doesQuestionExist(question);
	}

	public boolean checkLogIn(String username, String password) {
		if (!checkUsername(username)) {
			TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u WHERE u.username = ?1 AND u.password = ?2 ", User.class); //We tried to use the proof in the database for security reasons 
			q1.setParameter(1, username);
			q1.setParameter(2, password);
			List<User> user = q1.getResultList();
			return (!user.isEmpty());
		}
		return false;

		//Not making a difference between a wrong password and a non existing username makes imposible to figure out if that username is registered or not. This is a used practice in order to repect the privacy of users.

	}

	public void close() {
		//db.close();
		System.out.println("DataBase is closed");
	}

	public boolean isAdmin(String username) {
		// TODO Auto-generated method stub
		TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u WHERE u.username = ?1 AND u.admin= true", User.class);
		q1.setParameter(1, username);
		List<User> userList = q1.getResultList(); //user.isEmpty == false
		return (userList.isEmpty() == false);
	}

	public Fee setFee(String result, Float fee, String quest, Event ev) {
		Fee f = new Fee(fee, result);
		TypedQuery<Question> q1 = db.createQuery("SELECT q FROM Question q WHERE q.question = ?1", Question.class);
		q1.setParameter(1, quest);
		List<Question> questList = q1.getResultList();

		Question q = questList.get(0);
		db.getTransaction().begin();
		q.addFee(f);
		db.getTransaction().commit();
		return f;

	}

	public boolean feeExists(String f, String s) {
		TypedQuery<Question> q1 = db.createQuery("SELECT q FROM Question q WHERE q.question = ?1", Question.class);
		q1.setParameter(1, s);
		List<Question> questList = q1.getResultList();
		Question q = questList.get(0);
		for (Fee fe : q.getFeeList()) {
			if (fe.getResult().equals(f)) {
				return true;
			}
		}
		return false;
	}

	public Event createEvent(String description, Date date) throws EventAlreadyExists {
		// TODO Auto-generated method stub
		db.getTransaction().begin();

		Event u = new Event(description, date);
		List<Event> events = getEvents(date);
		for (Event i : events) {
			if (i.equals(u)) {
				throw new EventAlreadyExists("Event already exists");
			}
		}
		db.persist(u);
		db.getTransaction().commit();
		System.out.println("New event has been registered in the database");
		return u;
	}
	public void updateCurrency(Float deposit, String username){
		db.getTransaction().begin();

		TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u WHERE u.username = ?1", User.class);
		q1.setParameter(1, username);
		List<User> userList = q1.getResultList();
		User current = userList.get(0);
		changeMoney(current,deposit,"Deposit money");
		db.persist(current);
		db.getTransaction().commit();

	}

	public float getCurrency(String username) {
		TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u WHERE u.username = ?1", User.class);
		q1.setParameter(1, username);
		List<User> userList = q1.getResultList(); //user.isEmpty == false

		return userList.get(0).getAvailableMoney();
	}

	public User getCurrentUser(String username){
		TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u WHERE u.username = ?1", User.class);
		q1.setParameter(1, username);
		List<User> userList = q1.getResultList(); //user.isEmpty == false
		User current = null;
		if (!userList.isEmpty()){
			current = userList.get(0);
		}
		return current;
	}
	public void placeBet(float stake, String username, Fee f, Question q, Event e){
		db.getTransaction().begin();
		Bet b = new Bet(f, stake);
		TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u WHERE u.username = ?1", User.class);
		q1.setParameter(1,username);
		List<User> userList = q1.getResultList();

		User current = userList.get(0);
		changeMoney(current,-stake,"Bet: " + e.getDescription());
		current.addBet(b);
		db.persist(b);
		db.getTransaction().commit();

	}

	public void payWinners(Question q, Fee f) {
		db.getTransaction().begin();
		TypedQuery<Question> q2 = db.createQuery("SELECT q FROM Question q WHERE q.questionNumber=?1",Question.class);
		q2.setParameter(1,q.getQuestionNumber());
		List<Question> questionList = q2.getResultList();
		Question qu = questionList.get(0);
		qu.setResult(f.getResult());
		TypedQuery<User> q1 = db.createQuery("SELECT u FROM User u", User.class);
		List<User> userList = q1.getResultList();
		for(User u: userList){
			for(Bet b: u.getBetList()){
				if(b.getFee().getResult().equals(f.getResult()) & b.getFee().getFee().equals(f.getFee())){
					changeMoney(u,b.getStake()*f.getFee(),new String("Winnings: " + q.getEvent().getDescription()));
				}
			}
		}
		//db.persist(q);
		db.getTransaction().commit();

	}

	public void changeMoney(User u, Float amount,String eventDescription){
		u.updateAvailableMoney(amount);
		u.addMovement(new Movement(new Date(),eventDescription,amount));
	}

	public String getEmail(String username){
		return this.getCurrentUser(username).getEmail();
	}

	public void changePassword(String user, String pass) {
		System.out.println(user);
		User usr = this.getCurrentUser(user);
		db.getTransaction().begin();
		usr.setPassword(pass);
		db.getTransaction().commit();
	}

	public void removeEvent(Event event){
		db.getTransaction().begin();
		db.remove(event);
		db.getTransaction().commit();
	}




}