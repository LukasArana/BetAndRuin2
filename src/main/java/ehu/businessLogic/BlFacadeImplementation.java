package ehu.businessLogic;

import ehu.configuration.ConfigXML;
import ehu.dataAccess.DataAccess;
import ehu.domain.*;
import ehu.exceptions.EventAlreadyExists;
import ehu.exceptions.EventFinished;
import ehu.exceptions.QuestionAlreadyExist;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;


/**
 * Implements the business logic as a web service.
 */
//@WebService(endpointInterface = "businessLogic.BlFacade")
public class BlFacadeImplementation implements BlFacade {
	String username;
	private static BlFacadeImplementation bl = new BlFacadeImplementation();

	DataAccess dbManager;
	ConfigXML config = ConfigXML.getInstance();

	private BlFacadeImplementation()  {
		System.out.println("Creating BlFacadeImplementation instance");
		boolean initialize = config.getDataBaseOpenMode().equals("initialize");
		dbManager = new DataAccess(initialize);
		if (initialize)
			dbManager.initializeDB();
		dbManager.close();
	}
	public static BlFacadeImplementation getInstance(){
		return bl;
	}

	
	public boolean feeExists(String f, String s) {
		return dbManager.feeExists(f,s);
	}

	@Override
	public void placeBet(float stake, String username, Fee f, Question q, Event e) {
		//dbManager.updateCurrency(stake*(-1), username);
		dbManager.placeBet(stake,username,f,q,e);

	}

	@Override
	public void updateCurrency(float deposit, String username) {
		dbManager.updateCurrency(deposit, username);
	}

	@Override
	public Float getCurrency(String username) {
		return dbManager.getCurrency(username);
	}

	public Fee setFee(String result, Float fee, String quest, Event ev) {
		return dbManager.setFee(result,fee,quest,ev);
	}


	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@Override
	//@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum) 
			throws EventFinished, QuestionAlreadyExist {

		//The minimum bid must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").
					getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);		
		dbManager.close();
		return qry;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@Override
	//@WebMethod
	public Vector<Event> getEvents(Date date)  {
		dbManager.open(false);
		Vector<Event>  events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}


	/**
	 * This method invokes the data access to retrieve the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@Override
	//@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date>  dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	@Override
	public Date getEventDate(Movement move) {
		dbManager.open(false);
		Date d = dbManager.getEventDate(move);
		dbManager.close();
		return d;
	}


	public void close() {
		dbManager.close();
	}

	/**
	 * This method invokes the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	//@WebMethod
	public void initializeBD(){
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	//@WebMethod
	public User registerNewUser(int age, String username, String password, String name, String surname, String email) {
		dbManager.open(false);
		User e =  dbManager.register(age,username,password,name,surname,email);
		dbManager.close();
		return e;
	}

	//@WebMethod
	public User registerNewAdmin(String username, String password){
		return dbManager.register(username,password);
	}
	
	//@WebMethod
	public boolean usernameIsFree(String username) {
		dbManager.open(false);
		boolean e = dbManager.checkUsername(username);
		dbManager.close();
		return e;
	}
	
	//@WebMethod
	public boolean emailIsFree(String email) {
		dbManager.open(false);
		boolean e =  dbManager.checkEmail(email);
		dbManager.close();
		return e;
	}
	//@WebMethod
	public boolean checkLogIn(String username, String password) {
		dbManager.open(false);
		boolean e =  dbManager.checkLogIn(username, password);
		dbManager.close();
		return e;
	}
	
	//@WebMethod
	public boolean isAdmin() {
		dbManager.open(false);
		boolean e =  dbManager.isAdmin(this.username);
		dbManager.close();
		return e;
	}
	@Override
	public Event createEvent(String description, Date date) throws EventAlreadyExists {
		// TODO Auto-generated method stub
		dbManager.open(false);
		Event e = dbManager.createEvent(description, date);
		dbManager.close();
		return e;
	}
	public void setUser(String username){
		this.username = username;
	}

	public User getCurrentUser(){
		return dbManager.getCurrentUser(this.username);
	}

	@Override
	public void payWinners(Question q, Fee f) {
		dbManager.open(false);
		dbManager.payWinners(q,f);
		dbManager.close();
	}

	@Override
	public String getEmail(String username) {
		return dbManager.getEmail(username);
	}



	public void setPassword(String user, String pass){
		dbManager.changePassword(user, pass);
	}

	@Override
	public void removeEvent(Event event) {dbManager.removeEvent(event);
	}


}