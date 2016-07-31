import java.util.Calendar;

/**
 * The Appointment class represents some sort of obligation the user has entered into the calendar. It has a name, as well as a start and end time.
 * @author Cameron Chien 
 *
 */
public class Event implements java.io.Serializable{
	private String name; 
	private String start;
	private String end; 

	/** 
	 * Constructs an Appointment
	 * @param name is the what the user has decided to call this Appointment
	 * @param start is when the Appointment starts
	 * @param end is when the Appointment ends
	 */
	public Event (String name, String start, String end)
	{
		this.name = name; 
		this.start = start; 
		this.end = end; 
	}

	/**
	 * 
	 * @return the name of the Appointment
	 */
	public String getName()
	{
		return name; 
	}

	/**
	 * 
	 * @return the start time of the Appointment
	 */
	public String getStart()
	{
		return start;
	}
	
	/**
	 * 
	 * @return the end time of the Appointment
	 */
	public String getEnd()
	{
		return end; 
	}
	
	/**
	 * @return a string in the form of "(startTime) - (endTime) : 	(name of event)"
	 */
	public String toString()
	{
		return start + " - " + end + " : \t " + name; 
	}

	

}


