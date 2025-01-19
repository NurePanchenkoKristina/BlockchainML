import gym.Main;
import gym.ml.EquipmentPriceOptimizer;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class)
class EquipmentPriceOptimizerTest {

    @InjectMocks
    private EquipmentPriceOptimizer equipmentPriceOptimizer;

    @Mock
    private CSVLoader csvLoader;

    @Mock
    private Instances instances;


    @Test
    void testPredictOptimizedPriceForEachType() throws Exception {
        Mockito.when(csvLoader.getDataSet()).thenReturn(instances);

        Map<String, Double> result = equipmentPriceOptimizer.predictOptimizedPriceForEachType();

        Assertions.assertTrue(result.containsKey("Threadmill"));
        Assertions.assertTrue(result.containsKey("Elliptical"));
        Assertions.assertTrue(result.containsKey("Kettlebell"));
    }
}
