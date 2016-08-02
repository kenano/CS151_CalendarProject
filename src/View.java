import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.scene.shape.Box;


/**
 * Creates a view which also includes the controller for the MVC pattern calendar 
 * @author Cameron Chien 
 *
 */
public class View implements ChangeListener {

	private Model model;
	private int lastSelected = -1;
	private int totalDays;
	private DAYS[] dayArray = DAYS.values();
	private MONTHS[] monthArray = MONTHS.values();
	

	private JFrame frame = new JFrame("Chien Simple GUI Calendar");

	//MiniCalendar (Left)
	private JPanel miniCalendarPanel = new JPanel();
	private JLabel monthLabel = new JLabel();
	private JPanel dayButtonPanel = new JPanel();
	private ArrayList<JButton> dayButtons = new ArrayList<JButton>();
	private JButton nextMonthButton = new JButton("Next Month");
	private JButton previousMonthButton = new JButton("Previous Month");
	private JButton todayButton = new JButton("Today's Date");

	//Button Panel 
	private JPanel rightButtonPanel = new JPanel();
	private JButton addEventButton = new JButton("Add Event");
	private JButton quitButton = new JButton("Quit");
	private JButton nextDayButton = new JButton("Next Day");
	private JButton previousDayButton = new JButton("Previous Day");
	

	//View Button Panel 
	private JPanel viewButtonsPanel = new JPanel();
	private Box viewButtonsBox = new Box(); 
	private JButton dayViewButton = new JButton("    Day   ");
	private JButton weekViewButton = new JButton("  View   ");
	private JButton monthViewButton = new JButton(" Month  ");
	private JButton customViewButton = new JButton("Custom");

	//Daily View (Right)
	private JTextPane dailyEvents = new JTextPane();
	private JLabel rightPanelLabel = new JLabel(); 

	/**
	 * Constructs the view for the Calendar.
	 * @param model is what holds all the data for the calendar 
	 */
	public View (Model model)
	{
		this.model = model;
		totalDays = model.getTotalDays();


		nextMonthButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.nextMonth();

			}
		}
				);

		previousMonthButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.previousMonth();

			}
		}
				);
		
		todayButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.previousMonth();

			}
		}
				);
		
		JPanel leftButtonPanel = new JPanel(); 
		leftButtonPanel.setLayout(new BorderLayout());
		leftButtonPanel.add(todayButton, BorderLayout.WEST);
		leftButtonPanel.add(previousMonthButton, BorderLayout.CENTER);
		leftButtonPanel.add(nextMonthButton, BorderLayout.EAST);
		
		//Mini Calendar (Left)
		miniCalendarPanel.setLayout(new BorderLayout());
		monthLabel.setText("                                            " + monthArray[model.getMonth()] + " " + model.getYear());
		miniCalendarPanel.add(monthLabel, BorderLayout.NORTH); 
		miniCalendarPanel.add(new JLabel("    Sun         Mon        Tue         Wed       Thu           Fri          Sat"), BorderLayout.CENTER);
		dayButtonPanel.setLayout(new GridLayout(0, 7));
		miniCalendarPanel.add(dayButtonPanel, BorderLayout.SOUTH);
		
		
		createDayButtons();
		addBlankButtons();
		addDayButtons();
		highlightSelectedDay(model.getDay() - 1);

		JPanel leftSidePanel = new JPanel(); 
		leftSidePanel.setLayout(new BorderLayout());
		leftSidePanel.add(leftButtonPanel, BorderLayout.NORTH);
		leftSidePanel.add(miniCalendarPanel, BorderLayout.SOUTH);

		//Button Panel
		addEventButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addEventDialog();

			}
		}
				);

		quitButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.save(); 

				System.exit(0);
			}
		}
				);

		nextDayButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.nextDay();

			}
		}
				);

		previousDayButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.previousDay();

			}
		}
				);

		rightButtonPanel.add(addEventButton);
		rightButtonPanel.add(previousDayButton);
		rightButtonPanel.add(nextDayButton);
		rightButtonPanel.add(quitButton);
		
		
		
		viewButtonsPanel.setLayout(new BoxLayout(viewButtonsPanel, BoxLayout.Y_AXIS));
		dayViewButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateDayViewText(model.getDay());

			}
		}
				);

		weekViewButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateToWeekView();

			}
		}
				);
		
		monthViewButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateToMonthView();

			}
		}
				);
		
		customViewButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateToCustomView();

			}
		}
				);
		viewButtonsPanel.add(dayViewButton);
		viewButtonsPanel.add(weekViewButton);
		viewButtonsPanel.add(monthViewButton);
		viewButtonsPanel.add(customViewButton);


		//Daily View (Right)
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(rightPanelLabel, BorderLayout.NORTH);
		JScrollPane scroll = new JScrollPane(dailyEvents);
		scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPanel.add(scroll, BorderLayout.CENTER);
		rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);
		dailyEvents.setPreferredSize(new Dimension(600, 155));
		dailyEvents.setEditable(false);
		updateDayViewText(model.getDay());
		rightPanel.add(viewButtonsPanel, BorderLayout.EAST);


		//Add everything to frame
		frame.add(leftSidePanel);
		frame.add(rightPanel);
		frame.setLayout(new FlowLayout());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		if (model.getMonthChange()) 
		{
			totalDays = model.getTotalDays();
			dayButtons.clear();
			dayButtonPanel.removeAll();
			dayButtonPanel.setLayout(new GridLayout(0, 7));
			miniCalendarPanel.removeAll();
			monthLabel.setText("                                            " + monthArray[model.getMonth()] + " " + model.getYear());
			miniCalendarPanel.add(monthLabel, BorderLayout.NORTH); 
			miniCalendarPanel.add(new JLabel("    Sun         Mon        Tue         Wed       Thu           Fri          Sat"), BorderLayout.CENTER);
			miniCalendarPanel.add(dayButtonPanel, BorderLayout.SOUTH);
			monthLabel.setText(monthArray[model.getMonth()] + " " + model.getYear());
			createDayButtons();
			addBlankButtons();
			addDayButtons();
			lastSelected = -1;
			model.resetMonthChange();
			frame.pack();
			frame.repaint();
		}
		
		else 
		{
			updateDayViewText(model.getDay());
			highlightSelectedDay(model.getDay() - 1);
		}
	}

	/**
	 * Creates the buttons for each day on the mini calendar 
	 */
	private void createDayButtons() {
		for (int i = 1; i <= totalDays; i++) {
			final int x = i;
			JButton dayButton = new JButton(Integer.toString(i));
			dayButton.setBackground(Color.WHITE);

			dayButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					updateDayViewText(x);
					highlightSelectedDay(x - 1);

				}
			});
			
			dayButtons.add(dayButton);
		}
	}

	/**
	 * Adds all of the buttons in the button arrayList to the buttonPanel
	 */
	private void addDayButtons() {
		for (JButton button : dayButtons) {
			dayButtonPanel.add(button);
		}
	}

	/**
	 * Adds blank buttons to fill spaces from previous month
	 */
	private void addBlankButtons() {
		for (int i = 1; i < model.getDayOfWeek(1); i++) {
			JButton blankButton = new JButton();
			blankButton.setBackground(Color.WHITE);
			dayButtonPanel.add(blankButton);
		}
	}

	/**
	 * Updates the dayView text with the events of the new day
	 * @param x
	 */
	private void updateDayViewText(int x) {


		model.changeDay(x);
		
		String dateForEvent = dayArray[model.getDayOfWeek(model.getDay()) - 1] + " " + monthArray[model.getMonth()] + " " +  model.getDay() + ", " + model.getYear();  
		rightPanelLabel.setText(dateForEvent);

		String text = "";
		if (model.hasEvents(model.dayToString())) {
			text += model.getEvents(model.dayToString());
		}
		dailyEvents.setText(text);
	}
	
	private void updateToWeekView()
	{
		//fill in 
	}
	
	private void updateToMonthView()
	{
		rightPanelLabel.setText("" + monthArray[model.getMonth()] + model.getYear());
		String text = ""; 
		for (int i = 0; i < model.getTotalDays(); i++)
		{
			model.changeDay(i);
			String dateForEvent = dayArray[model.getDayOfWeek(model.getDay()) - 1] + " " + monthArray[model.getMonth()] + " " +  model.getDay() + ", " + model.getYear();  
			if (model.hasEvents(model.dayToString()))
			{
				text+= dateForEvent + "\n" + model.getEvents(model.dayToString());
			}
			dailyEvents.setText(text + "NOT WORKING");
			
			
		}
	}
	
	private void updateToCustomView()
	{
		
	}



	/**
	 * Creates a thick border to highlight a certain day
	 * @param x is the day to highLight
	 */
	private void highlightSelectedDay(int x) {

		Color AirForceBlue = new Color(93, 138, 168);
		Border border = new LineBorder(AirForceBlue, 4); 
		dayButtons.get(x).setBorder(border);
		if (lastSelected != -1) {
			dayButtons.get(lastSelected).setBorder(new JButton().getBorder());
		}
		lastSelected = x;
	}

	/**
	 * Days of the week
	 * 
	 *
	 */
	public enum DAYS {
		Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday;
	}

	/**
	 * Months of the year
	 * 
	 *
	 */
	public enum MONTHS {
		January, February, March, April, May, June, July, August, September, October, November, December;
	}


	
	/**
	 * Creates the eventDialog that pops up when you hit the addEvent button 
	 */
	private void addEventDialog() {
		JDialog eventDialog = new JDialog();
		eventDialog.setTitle("Create event");

		JTextField name = new JTextField(50);
		JTextField start = new JTextField();
		JTextField end = new JTextField();

		JButton exitButton = new JButton("Exit");

		exitButton.addActionListener( new 
				ActionListener() {

			public void actionPerformed(ActionEvent e) {

				eventDialog.dispose();

			}
		});

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new 
				ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				if (name.getText().isEmpty()) { //if no name entered
					JDialog emptyNameDialog = new JDialog(); 
					emptyNameDialog.setLayout(new BorderLayout());
					emptyNameDialog.add(new JLabel("Please enter a name for this event."), BorderLayout.NORTH);
					JButton close = new JButton("Close");
					close.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							emptyNameDialog.dispose();
						}
					});
					emptyNameDialog.add(close, BorderLayout.SOUTH);
					emptyNameDialog.pack();
					emptyNameDialog.setVisible(true);
				}

				else if (!name.getText().equals("")) { //if name isn't empty 
					Event testEvent = new Event(name.getText(), start.getText(), end.getText());
					if (model.hasEventConflict(testEvent)) { //if time conflicts
						JDialog conflictingTimeDialog = new JDialog();
						conflictingTimeDialog.setLayout(new BorderLayout());
						conflictingTimeDialog.add(new JLabel("This event conflicts with another schedule"), BorderLayout.NORTH);
						JButton close = new JButton("Close");
						close.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								conflictingTimeDialog.dispose();
							}
						});
						conflictingTimeDialog.add(close, BorderLayout.SOUTH);
						conflictingTimeDialog.pack();
						conflictingTimeDialog.setVisible(true);
					} 
					else 
					{
						eventDialog.dispose();

						model.addEvent(name.getText(), start.getText(), end.getText());
						updateDayViewText(model.getDay());
						
					}
				}
			}
		});

		eventDialog.setLayout(new BorderLayout());
		String dateForEvent = dayArray[model.getDayOfWeek(model.getDay()) - 1] + " " + monthArray[model.getMonth()] + " " +  model.getDay() + ", " + model.getYear();  
		JLabel dayForEventLabel = new JLabel(dateForEvent);
		eventDialog.add(dayForEventLabel, BorderLayout.NORTH);


		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new BorderLayout());
		descriptionPanel.add(new JLabel("Description"), BorderLayout.NORTH);
		descriptionPanel.add(name, BorderLayout.SOUTH);

		JPanel startPanel = new JPanel();
		startPanel.setLayout(new BorderLayout());
		startPanel.add(new JLabel("Start Time (00:00)"), BorderLayout.NORTH);
		startPanel.add(start, BorderLayout.SOUTH);

		JPanel endPanel = new JPanel();
		endPanel.setLayout(new BorderLayout());
		endPanel.add(new JLabel("End Time (24:00)"), BorderLayout.NORTH);
		endPanel.add(end, BorderLayout.SOUTH);

		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new BorderLayout());
		fieldPanel.add(descriptionPanel, BorderLayout.NORTH);
		fieldPanel.add(startPanel, BorderLayout.CENTER);
		fieldPanel.add(endPanel, BorderLayout.SOUTH);

		JPanel buttonPanel = new JPanel(); 
		buttonPanel.add(addButton);
		buttonPanel.add(exitButton);
		
		eventDialog.add(buttonPanel);
		eventDialog.add(fieldPanel, BorderLayout.WEST);
		eventDialog.add(buttonPanel, BorderLayout.EAST);
		eventDialog.pack();
		eventDialog.setVisible(true);
	}


}