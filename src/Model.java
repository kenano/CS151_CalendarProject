import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Creates the model for calendar
 * @author Cameron Chien 
 *
 */
public class Model
{

	private GregorianCalendar calendar = new GregorianCalendar();
	private ArrayList<ChangeListener> listeners = new ArrayList<>();
	private HashMap<String, ArrayList<Event>> eventMap = new HashMap<>();
	private int totalDays;
	
	private boolean monthChange = false;
	
	private int selectedDay;
	private int selectedMonth;
	private int selectedYear; 
	private final int todaysDay;
	private final int todaysMonth; 
	private final int todaysYear; 
	
	private Strategy selectedStrategy; 

	/**
	 * Constructor for the model which initiates totalDays, selectedDay, and loads events that have been added if they exist
	 */
	public Model() 
	{
		totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		selectedDay = calendar.get(Calendar.DATE);
		selectedMonth = calendar.get(Calendar.MONTH); 
		selectedYear = calendar.get(Calendar.YEAR); 
		
		todaysDay = calendar.get(Calendar.DATE);
		todaysMonth = calendar.get(Calendar.MONTH); 
		todaysYear = calendar.get(Calendar.YEAR); 
		selectedStrategy = new strategy1SmallBlue(); 
		
		
		load();
	}

	/**
	 * Adds a changeListener the the arrayList of changeListeners
	 * @param listener is the changeListener being added 
	 */
	public void attach(ChangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Updates the arrayList of changeListeners
	 */
	public void update()
	{
		for (ChangeListener listener: listeners)
		{
			listener.stateChanged(new ChangeEvent(this));
		}
	}
	
	public void changeToToday()
	{
		
		changeDay(todaysDay);
		changeMonth(todaysMonth);
		changeYear(todaysYear);
		
		calendar.set(Calendar.DATE, todaysDay);
		calendar.set(Calendar.MONTH, todaysMonth);
		calendar.set(Calendar.YEAR, todaysYear);
		
		/*
		System.out.println(todaysDay);
		System.out.println(todaysMonth);
		System.out.println(todaysYear);
		*/
		update(); 
	}

	/**
	 * 
	 * @return the strategy of the model
	 */
	public Strategy getStrategy()
	{
		return selectedStrategy;
	}
	/**
	 * Tells user the year
	 * @return the year the calendar is at
	 */
	
	/**
	 * Changes the model's strategy
	 * @param s is what you are changing it to
	 */
	public void changeStrategy(Strategy s)
	{
		selectedStrategy = s; 
	}
	public int getYear()
	{
		return calendar.get(Calendar.YEAR); 
	}

	/**
	 * Tells user the month
	 * @return the month the calendar is at
	 */
	public int getMonth()
	{
		return calendar.get(Calendar.MONTH);
	}

	/**
	 * Tells user the day
	 * @return the day the calendar is at
	 */
	public int getDay()
	{
		return selectedDay; 
		//return calendar.get(Calendar.DATE);
	}

	
	/**
	 * Changes the day
	 * @param x is the day selectedDay is being changed to
	 */
	public void changeDay(int x)
	{
		selectedDay = x; 
		calendar.set(Calendar.DATE, x);
	}
	
	/**
	 * Changes the month
	 * @param x is the month selectedMonth is being changed to
	 */
	public void changeMonth(int x)
	{
		selectedMonth = x; 
		calendar.set(Calendar.MONTH, x - 1);
		 /*
		int difference = calendar.get(Calendar.MONTH - x);
		if (difference > 0)
		{
			for (int i = 0; i < difference ; i ++)
			{
				previousMonth();
			}
		}
		else
		{
			for (int i = 0; i < difference - 1; i ++)
			{
				nextMonth();
			}
		}
		*/
		
		
		
	}
	
	public void changeYear(int x)
	{
		selectedYear = x; 
		calendar.set(Calendar.YEAR, x);
	}

	/**
	 * Tells the user the day of the week in the form of a number 1-7
	 * @param x is the day of the month you are checking
	 * @return a number from 1-7 
	 */
	public int getDayOfWeek(int x) {
		calendar.set(Calendar.DAY_OF_MONTH, x);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Tells the user the number of days in the month
	 * @return an int that represents the number of days in the month
	 */
	public int getTotalDays() {
		return totalDays;
	}

	/**
	 * Turns the day into a string (this will also serve as the key
	 * @return a string that in the form of month day year concatenated 
	 */
	public String dayToString()
	{
		return Integer.toString(getMonth()) + Integer.toString(getDay()) + Integer.toString(getYear());
	}

	/**
	 * Moves the calendar to the next month 
	 */
	public void nextMonth() {
		selectedMonth++; 
		calendar.add(Calendar.MONTH, 1);
		totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		monthChange = true;
		update();
	}

	/**
	 * Moves the calendar to the previous month
	 */
	public void previousMonth() {
		selectedMonth--; 
		calendar.add(Calendar.MONTH, -1);
		totalDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		monthChange = true;
		update();
	}

	/**
	 * Moves the calendar to the next day. If it is already the last day, it will change months as well
	 */

	public void nextDay() {
		selectedDay++;
		if (selectedDay > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			nextMonth();
			selectedDay = 1;
		}
		update();
	}

	/**
	 * Moves the calendar to the previous day. If it is already the first day of the month, it will change to the previous month
	 */
	public void previousDay() {
		selectedDay--;
		if (selectedDay < 1) {
			previousMonth();
			selectedDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		}
		update();
	}

	/**
	 * Tells the user whether or not the month has changed
	 * @return the current value of boolean monthChange
	 */
	public boolean getMonthChange() {
		return monthChange;
	}

	/**
	 * Changes the value of boolean monthChange back to false
	 */
	public void resetMonthChange() {
		monthChange = false;
	}

	/**
	 * Adds an event to the map
	 * @param name is the description given to the event
	 * @param start is the start time in the form of 00:00
	 * @param end is the end time in the form of 24:00
	 */
	public void addEvent(String name, String start, String end)
	{

		Event newEvent = new Event(name, start, end);  // create event with given values
		if (hasEvents(dayToString())) // if there are already events that day
		{
			ArrayList<Event>updated = eventMap.get(dayToString()); //copy the arraylist for that day
			updated.add(newEvent); //add newEventt to the arrayList
			Collections.sort(updated, sortTimeComparator());
			eventMap.put(dayToString(), updated); //update arrayList in map
			System.out.println("Event ArrayList appended and eventMap updated");
		}
		else
		{
			ArrayList<Event> events = new ArrayList<>(); //make a new arrayList
			events.add(newEvent); // add the event
			eventMap.put(dayToString(), events); //update the eventMap
			System.out.println("Event ArrayList created and eventMap updated");
		}
		
	}
	
	/**
	 * Comparator that sorts the events from earliest time to latest time 
	 * @return
	 */
	private static Comparator<Event> sortTimeComparator() 
	{
		return new 
				Comparator<Event>() 
		{
			@Override
			public int compare(Event event1, Event event2) {
				if (event1.getStart().substring(0, 2).equals(event2.getStart().substring(0, 2))) {
					return Integer.parseInt(event1.getStart().substring(3, 5)) - Integer.parseInt(event2.getStart().substring(3, 5));
				}
				return Integer.parseInt(event1.getStart().substring(0, 2)) - Integer.parseInt(event2.getStart().substring(0, 2));
			}
		};
	}
	
	/**
	 * Checks whether an event conflicts with any of the existing events
	 * @param newEvent is the event you are comparing with existing events
	 * @return a boolean representing whether or not there is a conflict
	 */
	public boolean hasEventConflict(Event newEvent)
	{

		if (hasEvents(dayToString()) == false) // if no events that day, no conflict
		{
			return false; 
		}

		ArrayList<Event>events = eventMap.get(dayToString());  // retrieve arrayList for the day
		

		int startTime1 = timeToMinutes(newEvent.getStart()); //convert start and end times to minutes
		int endTime1 = timeToMinutes(newEvent.getEnd());

		for (Event event : events)  //for every event in the arraylist
		{
			int startTime2 = timeToMinutes(event.getStart()); //convert start/end times to minutes
			int endTime2 = timeToMinutes(event.getEnd());

			if (startTime1 >= startTime2 && startTime1 < endTime2 ) //event1 (the one being added) starts after event2, but before event2 ends
			{
				return true; 
			}
			else if (startTime1 <= startTime2 && endTime1 > startTime2) //event1 starts before event2, but ends after event2 begins
			{
				return true; 
			}
		}
		return false;
	}
	
	/**
	 * Saves the eventMap using serialization
	 */
	public void save()
	{

		if (eventMap.isEmpty()){return;}

		//looked up from here: http://www.tutorialspoint.com/java/java_serialization.htm
		try
		{
			FileOutputStream fileOut = new FileOutputStream("events.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(eventMap);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in events.ser");
		}catch(IOException i)
		{
			i.printStackTrace();
		}
	}

	/**
	 * Loads the eventMap using deserialization
	 */
	public void load()
	{
		try
		{
			FileInputStream fileIn = new FileInputStream("events.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			HashMap<String, ArrayList<Event>> loadingEvents  = (HashMap<String, ArrayList<Event>>) in.readObject();
			for (String s : loadingEvents.keySet())
			{
				if (hasEvents(s))
				{
					ArrayList<Event> events = eventMap.get(s);
					events.addAll(loadingEvents.get(s));
				}
				else
				{
					eventMap.put(s, loadingEvents.get(s));
				}
			}
			in.close();
			fileIn.close();
		}catch(IOException i)
		{
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c)
		{
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}
	}


	/**
	 * Checks whether there are already events or not on a certain day
	 * @param x should be the dayToString() value of a certain day
	 * @return a boolean representing whether or not there are events on that day
	 */
	public boolean hasEvents( String x)
	{
		return eventMap.containsKey(x);
	}

	/**
	 * Gets the events from a certain day and turns it into the text that will display on the dayView
	 * @param x is a string that should be the dayToString() value of the day you are checking
	 * @return a String for the dayView
	 */
	public String getEvents( String x)
	{
		String text = "";
		ArrayList<Event> events = eventMap.get(x); //get arrayList for that day
		

		for (Event event: events) //for each event
		{
			text += event.toString() + "\n"; //append with new line
		}
		return text; 
	}

	/**
	 * Turns a certain time into minutes
	 * @param time is a String representing time between 00:00 and 24:00
	 * @return an int value that represents how many minutes a certain time has
	 */
	public int timeToMinutes(String time)
	{
		return ( Integer.valueOf(time.substring(0,2)) * 60 ) + Integer.valueOf(time.substring(3)); 
	}

	






}