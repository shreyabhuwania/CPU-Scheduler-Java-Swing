import java.util.ArrayList;
import java.util.List;

public class Utility {
    /**
     * Performs a deep copy of a list of Row objects.
     *
     * @param oldList The list to be copied.
     * @return A new list containing copies of the Row objects.
     */
    public static List<Row> deepCopy(List<Row> oldList) {
        List<Row> newList = new ArrayList<>();

        for (Row row : oldList) {
            // Create a new Row object with the same attributes
            newList.add(new Row(row.getProcessName(), row.getArrivalTime(), row.getBurstTime(), row.getPriorityLevel()));
        }

        return newList;
    }
}
