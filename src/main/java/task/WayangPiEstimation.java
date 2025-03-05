package task;

import org.apache.wayang.api.JavaPlanBuilder;
import org.apache.wayang.core.api.WayangContext;
import org.apache.wayang.core.api.Configuration;
import org.apache.wayang.java.Java;

import java.util.ArrayList;
import java.util.List;

public class WayangPiEstimation {
    public static void main(String[] args) {
        // Initialize WayangContext with Java backend
        WayangContext wayangContext = new WayangContext(new Configuration())
                .withPlugin(Java.basicPlugin());

        JavaPlanBuilder planBuilder = new JavaPlanBuilder(wayangContext)
                .withJobName("Wayang Pi Estimation");

        // Read partitions argument (default = 2)
        int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2;
        int n = 100000 * slices; // Total points

        // Create dataset of numbers from 0 to n
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            numbers.add(i);
        }

        // Wayang data pipeline
        long count = planBuilder
                .loadCollection(numbers)
                .map(i -> {
                    double x = Math.random() * 2 - 1;
                    double y = Math.random() * 2 - 1;
                    return (x * x + y * y <= 1) ? 1 : 0;
                })
                .reduce(Integer::sum)
                .collect()
                .iterator().next();

        // Estimate Pi
        double pi = 4.0 * count / n;
        System.out.println("Pi is roughly: " + pi);
    }
}
