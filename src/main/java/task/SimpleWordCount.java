package task;

import org.apache.wayang.api.JavaPlanBuilder;
import org.apache.wayang.core.api.Configuration;
import org.apache.wayang.core.api.WayangContext;
import org.apache.wayang.java.Java;

import java.util.Arrays;
import java.util.Collection;

public class SimpleWordCount {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        WayangContext wayangContext = new WayangContext(configuration)
                .withPlugin(Java.basicPlugin());

        JavaPlanBuilder planBuilder = new JavaPlanBuilder(wayangContext)
                .withJobName("Simple Wayang Filter Example");

        // Sample data: a list of numbers
        Collection<Integer> numbers = Arrays.asList(1, 3, 5, 7, 9, 2, 4, 6, 8, 10);

        // Define the data processing pipeline
        Collection<Integer> result = planBuilder
                .loadCollection(numbers)           // Read the input collection
                .filter(num -> num > 5)            // Filter numbers greater than 5
                .collect();                        // Collect the results

        System.out.println("Numbers greater than 5: " + result);
    }
}