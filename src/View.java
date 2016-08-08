import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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


	private JFrame frame = new JFrame("CKR GUI Calendar");

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
	private JButton loadEventsButton = new JButton( "Load Events"); 
	private JButton dayViewButton = new JButton("         Day       ");
	private JButton weekViewButton = new JButton("      Week      ");
	private JButton monthViewButton = new JButton("     Month      ");
	private JButton customViewButton = new JButton("    Custom    ");

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

		//Initial popup dialog for strategy
		
		JDialog strategyDialog = new JDialog(frame, true);
		strategyDialog.setAlwaysOnTop(true);
		
		
		strategyDialog.setLayout(new BorderLayout());
		strategyDialog.add(new JLabel("Please select the style you would like your calendar to have"), BorderLayout.NORTH);

		JPanel strategyButtons = new JPanel(); 
		

		JButton smallBlue = new JButton("Blue: Small");
		smallBlue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.changeStrategy(new strategy1SmallBlue());
				strategyDialog.dispose();
				
			}
		});
		
		JButton bigBlue = new JButton("Blue: Big");
		bigBlue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.changeStrategy(new strategy2BigBlue());
				strategyDialog.dispose();
				
			}
		});
		
		JButton smallRed = new JButton("Red: Small");
		smallRed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.changeStrategy(new strategy3SmallRed());
				strategyDialog.dispose();
				
			}
		});
		
		JButton bigRed = new JButton("Red: Big");
		bigRed.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.changeStrategy(new strategy4BigRed());
				strategyDialog.dispose();
				
			}
		});
		
		JButton smallGreen = new JButton("Green: Small");
		smallGreen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.changeStrategy(new strategy5SmallGreen());
				strategyDialog.dispose();
				
			}
		});
		
		JButton bigGreen = new JButton("Green: Big");
		bigGreen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.changeStrategy(new strategy6BigGreen());
				strategyDialog.dispose();
				
			}
		});
		strategyButtons.add(smallBlue);
		strategyButtons.add(bigBlue);
		strategyButtons.add(smallRed);
		strategyButtons.add(bigRed);
		strategyButtons.add(smallGreen);
		strategyButtons.add(bigGreen);
		strategyButtons.setSize(frame.getSize());
		
		strategyDialog.setLayout(new BorderLayout());
		strategyDialog.add(strategyButtons, BorderLayout.CENTER);
		strategyDialog.pack();
		strategyDialog.setVisible(true);
		
		nextMonthButton.setBackground(model.getStrategy().getButtonColor());
		nextMonthButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.nextMonth();
				updateDayViewText(1);
				highlightSelectedDay(0);

			}
		}
				);
		previousMonthButton.setBackground(model.getStrategy().getButtonColor());
		previousMonthButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.previousMonth();
				updateDayViewText(1);
				highlightSelectedDay(0);

			}
		}
				);
		todayButton.setBackground(model.getStrategy().getButtonColor());
		todayButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				
				model.changeToToday();
				remakeMiniCalendar();
				updateDayViewText(model.getDay());
				highlightSelectedDay(model.getDay() - 1);
				

			}
		}
				);
		
		//Left Panel
		JPanel leftSidePanel = new JPanel(); 
		leftSidePanel.setLayout(new BorderLayout());
		
			//today and month buttons (Left Sub)
			JPanel leftButtonPanel = new JPanel(); 
			leftButtonPanel.setLayout(new BorderLayout());
			leftButtonPanel.add(todayButton, BorderLayout.WEST);
			leftButtonPanel.add(previousMonthButton, BorderLayout.CENTER);
			leftButtonPanel.add(nextMonthButton, BorderLayout.EAST);
			

			//Mini Calendar (LeftSub)
			miniCalendarPanel.setLayout(new BorderLayout());
			monthLabel.setText( monthArray[model.getMonth()] + " " + model.getYear());
			miniCalendarPanel.add(monthLabel, BorderLayout.NORTH); 
			miniCalendarPanel.add(new JLabel(model.getStrategy().getSunMonLabel()), BorderLayout.CENTER);
			dayButtonPanel.setLayout(new GridLayout(0, 7));
			miniCalendarPanel.add(dayButtonPanel, BorderLayout.SOUTH);
			createDayButtons();
			addBlankButtons();
			addDayButtons();
			highlightSelectedDay(model.getDay() - 1);
		
		leftSidePanel.add(leftButtonPanel, BorderLayout.NORTH);
		leftSidePanel.add(miniCalendarPanel, BorderLayout.SOUTH);
		

		//Right Button Panel
		addEventButton.setBackground(model.getStrategy().getButtonColor());
		addEventButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addEventDialog();

			}
		}
				);

		quitButton.setBackground(model.getStrategy().getButtonColor());
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

		nextDayButton.setBackground(model.getStrategy().getButtonColor());
		nextDayButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.nextDay();

			}
		}
				);

		previousDayButton.setBackground(model.getStrategy().getButtonColor());
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


		//View buttons panel
		viewButtonsPanel.setLayout(new BoxLayout(viewButtonsPanel, BoxLayout.Y_AXIS));
		
		loadEventsButton.setBackground(model.getStrategy().getButtonColor());
		loadEventsButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				model.loadRecurringEventsFile(); 
				

			}
		}
				);
		
		dayViewButton.setBackground(model.getStrategy().getButtonColor());
		dayViewButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateDayViewText(model.getDay());

			}
		}
				);

		weekViewButton.setBackground(model.getStrategy().getButtonColor());
		weekViewButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateToWeekView();
				remakeMiniCalendar();

			}
		}
				);

		monthViewButton.setBackground(model.getStrategy().getButtonColor());
		monthViewButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateToMonthView();
				
			}
		}
				);

		customViewButton.setBackground(model.getStrategy().getButtonColor());
		customViewButton.addActionListener( new 
				ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				updateToCustomView();
				remakeMiniCalendar();

			}
		}
				);
		viewButtonsPanel.add(loadEventsButton);
		viewButtonsPanel.add(dayViewButton);
		viewButtonsPanel.add(weekViewButton);
		viewButtonsPanel.add(monthViewButton);
		viewButtonsPanel.add(customViewButton);


		//Right Panel 
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
			//Label describing range of event text 
			rightPanel.add(rightPanelLabel, BorderLayout.NORTH);
		
			//Event text
			dailyEvents.setPreferredSize(model.getStrategy().getTextDimension());
			dailyEvents.setEditable(false);
			JScrollPane scroll = new JScrollPane(dailyEvents);
			scroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			rightPanel.add(scroll, BorderLayout.CENTER);
			
			//Right Button panel
			rightPanel.add(rightButtonPanel, BorderLayout.SOUTH);
			
			updateDayViewText(model.getDay());
			//View Button Panel
			rightPanel.add(viewButtonsPanel, BorderLayout.EAST);


		//Add everything to frame
		frame.add(leftSidePanel);
		frame.add(rightPanel);
		
		
		
		frame.setLayout(new FlowLayout());
		
		changeFont(frame, model.getStrategy().getFont());
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	/**
	 * Changes the font of all components to a certain font (looked it up online and adapted)
	 * @param c the component 
	 * @param strategyFont the font you want to use
	 */
	public void changeFont( Component c, Font strategyFont)
	{
		
		c.setFont(strategyFont);
		if (c instanceof Container)
		{
			for (Component child: ( (Container) c).getComponents())
			{
				changeFont( child, strategyFont);
			}
		}
	}
	
	/**
	 * Remakes the calendar or updates the day when there is a ChangeEvent
	 */
	public void stateChanged(ChangeEvent e) {
	
		if (model.getMonthChange()) 
		{
			remakeMiniCalendar(); 
		}

		else 
		{
			updateDayViewText(model.getDay()); //change events to that day
			highlightSelectedDay(model.getDay() - 1); //change highlight to selected day
			
			
			
		}
	}
	
	/**
	 * Remakes the mini calendar
	 */
	public void remakeMiniCalendar()
	{
		totalDays = model.getTotalDays();
		dayButtons.clear();
		dayButtonPanel.removeAll();
		dayButtonPanel.setLayout(new GridLayout(0, 7));
		miniCalendarPanel.removeAll();
		monthLabel.setFont(model.getStrategy().getFont());
		monthLabel.setText(monthArray[model.getMonth()] + " " + model.getYear());
		changeFont(miniCalendarPanel, model.getStrategy().getFont());
		miniCalendarPanel.add(monthLabel, BorderLayout.NORTH); 
		miniCalendarPanel.add(new JLabel(model.getStrategy().getSunMonLabel()), BorderLayout.CENTER);
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
			blankButton.setSize(model.getStrategy().getTextDimension());
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
		changeFont(frame, model.getStrategy().getFont());
		
		
	}

	/**
	 * Updates the dayView text to a week view by asking user for the start date. If there are less than 7 days before the end of the month, less than 7 days worth of events will be shown
	 */
	private void updateToWeekView()
	{
		int beforeMonth = model.getMonth(); 
		int beforeDay = model.getDay(); 
		JDialog weekViewDialog = new JDialog(); 
	
		weekViewDialog.setLayout(new BorderLayout());
		weekViewDialog.add(new JLabel("Please enter the day you want Week View to start from"), BorderLayout.NORTH);

		JPanel weekFieldPanel = new JPanel(); 
		weekFieldPanel.add(new JLabel ("Start Day")); 
		JTextField weekViewField = new JTextField(); 
		weekViewField.setPreferredSize(new Dimension(30, 20));
		weekFieldPanel.add(weekViewField);


		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				weekViewDialog.dispose();
			}
		});


		JButton okayButton = new JButton("Okay");
		okayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int startDay = Integer.parseInt(weekViewField.getText());
				weekViewDialog.dispose();
				int x; 
				if (model.getTotalDays() - startDay > 7)
				{
					x = startDay + 7 + 1; 
				}
				else 
				{
					x = model.getTotalDays() + 1;
				}
				rightPanelLabel.setText(monthArray[model.getMonth()] + " "+ startDay + " to " + monthArray[model.getMonth()] + " " + (x - 1)  );
				String text = ""; 
				
				for (int i = startDay; i < x; i++)
				{
					model.changeDay(i);
					highlightSelectedDay(i - 1);
					String dateForEvent = dayArray[model.getDayOfWeek(model.getDay()) - 1] + " " + monthArray[model.getMonth()] + " " +  model.getDay() + ", " + model.getYear();  
					if (model.hasEvents(model.dayToString()))
					{
						text+= dateForEvent + "\n" + model.getEvents(model.dayToString()) + "\n";
					}
					dailyEvents.setText(text );
					

				}
			}
		});
		model.changeDay(beforeDay);
		model.changeMonth(beforeMonth + 1);
		

		JPanel buttonPanel = new JPanel(); 
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(okayButton);
		buttonPanel.add(close);

		weekViewDialog.add(weekFieldPanel, BorderLayout.CENTER);
		weekViewDialog.add(buttonPanel, BorderLayout.SOUTH);
		changeFont(weekViewDialog, model.getStrategy().getFont());
		
		weekViewDialog.pack();
		weekViewDialog.setVisible(true);
	}

	/**
	 * Updates the dayView text to a Month view which shows all the events in the month
	 */
	private void updateToMonthView()
	
	{
		int beforeMonth = model.getMonth(); 
		int beforeDay = model.getDay(); 
		rightPanelLabel.setText("" + monthArray[model.getMonth()] + "  " +  model.getYear());
		
		dailyEvents.setText(getMonthText(model.getMonth()));
	
		model.changeDay(beforeDay);
		model.changeMonth(beforeMonth + 1);
		
	}

	/**
	 * Updates the dayView text to a custom range. The user will be asked to enter a start/ end month and day within the same year and events in that range will be shown
	 */
	private void updateToCustomView()
	{

		JDialog customViewDialog = new JDialog(); 
		customViewDialog.setLayout(new BorderLayout());
		customViewDialog.add(new JLabel("Please enter start and end date"), BorderLayout.NORTH);

		JPanel startDatePanel = new JPanel(); 
		JTextField startMonthField = new JTextField(); 
		startMonthField.setPreferredSize(new Dimension(30, 20));

		JTextField startDayField = new JTextField(); 
		startDayField.setPreferredSize(new Dimension(30, 20));

		startDatePanel.add(new JLabel ("Start Month")); 
		startDatePanel.add(startMonthField);
		startDatePanel.add(new JLabel ("Start Day")); 
		startDatePanel.add(startDayField);


		JPanel endDatePanel = new JPanel(); 
		JTextField endMonthField = new JTextField(); 
		endMonthField.setPreferredSize(new Dimension(30, 20));

		JTextField endDayField = new JTextField(); 
		endDayField.setPreferredSize(new Dimension(30, 20));

		endDatePanel.add(new JLabel ("End Month")); 
		endDatePanel.add(endMonthField);
		endDatePanel.add(new JLabel ("End Day")); 
		endDatePanel.add(endDayField);	


		JPanel fieldPanel = new JPanel(); 
		fieldPanel.setLayout(new BorderLayout());
		fieldPanel.add(startDatePanel, BorderLayout.NORTH);
		fieldPanel.add(endDatePanel, BorderLayout.SOUTH);





		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				customViewDialog.dispose();
			}
		});


		JButton okayButton = new JButton("Okay");
		okayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int startMonth = Integer.parseInt(startMonthField.getText() );
				int startDay = Integer.parseInt(startDayField.getText() );
				int endMonth = Integer.parseInt(endMonthField.getText() );
				int endDay = Integer.parseInt(endDayField.getText() );

				customViewDialog.dispose();
				String text = ""; 
				rightPanelLabel.setText(monthArray[startMonth - 1] + " "+ startDay + " to " + monthArray[endMonth - 1] + " " + (endDay)  );
				if (startMonth == endMonth) //same month case
				{
					model.changeMonth(startMonth);
					
					
					for (int i = startDay; i < endDay + 1; i++)
					{
						model.changeDay(i);
						String dateForEvent = dayArray[model.getDayOfWeek(model.getDay()) - 1] + " " + monthArray[model.getMonth()] + " " +  model.getDay() + ", " + model.getYear();  
						if (model.hasEvents(model.dayToString()))
						{
							text+= dateForEvent + "\n" + model.getEvents(model.dayToString()) + "\n";
						}
					}
				}
				
				else //multiple months
				{
					int currentMonth = startMonth; 
					model.changeMonth(startMonth);
					for (int i = model.getDay(); i < model.getTotalDays() + 1; i++)
					{
						model.changeDay(i);
						String dateForEvent = dayArray[model.getDayOfWeek(model.getDay()) - 1] + " " + monthArray[model.getMonth()] + " " +  model.getDay() + ", " + model.getYear();  
						if (model.hasEvents(model.dayToString()))
						{
							text+= dateForEvent + "\n" + model.getEvents(model.dayToString()) + "\n";
						}
					}
					 
					currentMonth++;
					while (currentMonth < endMonth)
						
					{
						model.changeMonth(currentMonth);
						text+= getMonthText(currentMonth - 1);
						currentMonth++;
					}
					
						model.changeMonth(endMonth);
										
					
					for (int i = 1; i < endDay + 1; i++)
					{
						model.changeDay(i);
						String dateForEvent = dayArray[model.getDayOfWeek(model.getDay()) - 1] + " " + monthArray[model.getMonth()] + " " +  model.getDay() + ", " + model.getYear();  
						if (model.hasEvents(model.dayToString()))
						{
							text+= dateForEvent + "\n" + model.getEvents(model.dayToString()) + "\n";
						}
					}
					
				}
				dailyEvents.setText(text );
				model.changeDay(lastSelected + 1);
				
			}
		});
		JPanel buttonPanel = new JPanel(); 
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(okayButton);
		buttonPanel.add(close);

		customViewDialog.add(fieldPanel, BorderLayout.CENTER);
		customViewDialog.add(buttonPanel, BorderLayout.SOUTH);
		changeFont(customViewDialog, model.getStrategy().getFont());
		customViewDialog.pack();
		customViewDialog.setVisible(true);
	}

	/**
	 * Gives the user the text for all the events in a month
	 * @param month is the month you want
	 * @return a String that is formatted events text for the whole month
	 */
	public String getMonthText(int month)
	{
		model.changeMonth(month + 1);
	
		String text = ""; 
		for (int i = 1; i < model.getTotalDays() + 1; i++)
		{
			model.changeDay(i);
			String dateForEvent = dayArray[model.getDayOfWeek(model.getDay()) - 1] + " " + monthArray[model.getMonth()] + " " +  model.getDay() + ", " + model.getYear();  
			if (model.hasEvents(model.dayToString()))
			{
				text+= dateForEvent + "\n" + model.getEvents(model.dayToString()) + "\n";
			}
		}
			
		return text;
 
	}
	/**
	 * Creates a thick border to highlight a certain day
	 * @param x is the day to highLight
	 */
	private void highlightSelectedDay(int x) {

		Color highlightColor = model.getStrategy().getHighLightColor();
		Border border = new LineBorder(highlightColor, 4); 
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
