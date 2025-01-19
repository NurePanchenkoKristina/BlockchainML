import gym.Main;
import gym.ml.LinearRegressionInventoryOptimizer;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import weka.core.Instances;

import java.util.Map;
import weka.core.converters.CSVLoader;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
class LinearRegressionInventoryOptimizerTest {

    @InjectMocks
    private LinearRegressionInventoryOptimizer linearRegressionInventoryOptimizer;

    @Mock
    private CSVLoader csvLoader;

    @Mock
    private Instances instances;


    @Test
    void testOptimizeInventoryDistribution() throws Exception {
        Mockito.when(csvLoader.getDataSet()).thenReturn(instances);

        Map<String, Double> result = linearRegressionInventoryOptimizer.optimizeInventoryDistribution();


        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.containsKey("Threadmill"));
        Assertions.assertTrue(result.containsKey("Elliptical"));
        Assertions.assertTrue(result.containsKey("Kettlebell"));

    }
}
