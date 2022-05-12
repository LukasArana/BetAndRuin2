package ehu.businessLogic;


import ehu.domain.*;
import ehu.exceptions.EventAlreadyExists;
import ehu.exceptions.EventFinished;
import ehu.exceptions.QuestionAlreadyExist;

import java.util.Date;
import java.util.Vector;

/**
 * Interface that specifies the business logic.
 */
//@WebService
public interface BlFacade  {

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
	//@WebMethod
	Question createQuestion(Event event, String question, float betMinimum) 
			throws EventFinished, QuestionAlreadyExist;
		
	/**
	 * This method retrieves all the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	//@WebMethod
	public Vector<Event> getEvents(Date date);
	
	/**
	 * This method retrieves from the database the dates in a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	//@WebMethod
	public Vector<Date> getEventsMonth(Date date);
	
	//@WebMethod
	public boolean usernameIsFree(String username);
	
	//@WebMethod
	public User registerNewUser(int age, String username, String password, String name, String surname, String email);

	//@WebMethod
	public User registerNewAdmin(String username, String password);
	
	//@WebMethod
	public boolean emailIsFree(String email);
	//@WebMethod
	public boolean checkLogIn(String username, String password);
	//@WebMethod
	public boolean isAdmin();
	
	//@WebMethod
	public Event createEvent(String description,Date date) throws EventAlreadyExists;
	
	//@WebMethod
	public Fee setFee(String result, Float fee, String quest, Event ev);
	
	//@WebMethod
	public boolean feeExists(String f, String s);

	//@WebMethod
	public void placeBet(float stake, String username, Fee f, Question q, Event e);

	//@WebMethod
	public void updateCurrency(float deposit, String username);

   // @WebMethod
	Float getCurrency(String username);

	void setUser(String username);
	User getCurrentUser();

    //@WebMethod
	void payWinners(Question q, Fee f);
	String getEmail(String username);

    void setPassword(String user, String code);

	void removeEvent(Event event);

	//Date getEventDate(Movement move);
}