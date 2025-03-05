package task;

import org.apache.wayang.api.JavaPlanBuilder;
import org.apache.wayang.core.api.WayangContext;
import org.apache.wayang.core.api.Configuration;
import org.apache.wayang.java.Java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class WayangWordCount {

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.err.println("Usage: WayangWordCount <file>");
            System.exit(1);
        }

        // Read file content into a list of lines
        Collection<String> lines = Files.readAllLines(Paths.get(args[0]));

        
        WayangContext wayangContext = new WayangContext(new Configuration())
                .withPlugin(Java.basicPlugin());

        JavaPlanBuilder planBuilder = new JavaPlanBuilder(wayangContext)
                .withJobName("Wayang Word Count");

        // Run the word count pipeline in Wayang
        planBuilder
                .loadCollection(lines)
                .flatMap(line -> Arrays.asList(line.split("\\s+")))

                .map(word -> new Tuple<>(word, 1))
                .reduceByKey(
                        Tuple::getField0,
                        (t1, t2) -> new Tuple<>(t1.field0, t1.field1 + t2.field1)
                )
                .collect()
                .forEach(result -> System.out.println(result.field0 + ": " + result.field1));
    }

   
    public static class Tuple<T0, T1> {
        public final T0 field0;
        public final T1 field1;

        public Tuple(T0 field0, T1 field1) {
            this.field0 = field0;
            this.field1 = field1;
        }

        public T0 getField0() {
            return field0;
        }

        public T1 getField1() {
            return field1;
        }
    }
}
