import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KenanO on 7/15/16.
 */
public class ProjectCalendarDataModel {

    private List<String> mEventTitles;
    private List<ChangeListener> mUpdateViewListeners;

    ProjectCalendarDataModel(){
        mEventTitles = new ArrayList<String>();
        mUpdateViewListeners =  new ArrayList<ChangeListener>();

    }

    public List<String> getEventTitles(){
        return mEventTitles;
    }

    public void addTitleToEventList(String title){
        mEventTitles.add(title);

        // Notify all observers of the change to the invoice
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : mUpdateViewListeners)
            listener.stateChanged(event);
    }

    /**
     Adds a change listener to the invoice.

     @param listener the change listener to add
     */
    public void addChangeListener(ChangeListener listener) {
        mUpdateViewListeners.add(listener);
    }

}
