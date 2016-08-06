import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by KenanO on 8/2/16.
 */
public class SimpleCalendarUtilitiles {

    /**
     * Represents the dates in a month
     * @param max_dates_in_month  maximum number of days for this month
     * @return An interator which goes through the dates of a specific month.
     */
    public static Iterator<Integer> datesInMonth(int max_dates_in_month){
        return new Iterator<Integer>() {

            int max = max_dates_in_month;
            int i = 0;

            @Override
            public boolean hasNext() {
                if(i < max)
                    return true;
                else
                    return false;
            }

            @Override
            public Integer next() {
                i++;
                return i;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static Iterator<Integer> monthInYear(int initial){
        return new Iterator<Integer>() {

            //set to initial -1 because we get first element only after invoking next.
            int current_month = initial - 1;

            @Override
            public boolean hasNext() {
                if(current_month < Calendar.DECEMBER)
                    return true;
                else
                    return false;
            }

            @Override
            public Integer next() {
                current_month++;
                return new Integer(current_month);
            }
        };
    }
}
