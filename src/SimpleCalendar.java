/**
 * Creates a Simple Calendar by creating a model and attaching the view
 * @author Cameron Chien
 *
 */
public class SimpleCalendar 
{
	public static void main(String[] args) 
	{
		Model model = new Model(); 
		View view = new View(model); 
		model.attach(view);
	}
}
