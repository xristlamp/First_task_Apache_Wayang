package task;

import org.tensorflow.Tensor;
import org.tensorflow.types.TString;

public class TensorFlowHello {
    public static void main(String[] args) {
        try (Tensor tensor = TString.scalarOf("Hello, TensorFlow!")) {
            TString value = (TString) tensor;
            System.out.println(value.getObject());
        }
    }
}


//test if tensorflow works in my pc
