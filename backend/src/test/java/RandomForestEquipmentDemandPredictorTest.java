import gym.Main;
import gym.ml.RandomForestEquipmentDemandPredictor;
import gym.ml.csv.CsvWriter;
import gym.repository.EquipmentDemandRepository;
import gym.repository.TransactionRepository;
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
class RandomForestEquipmentDemandPredictorTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private EquipmentDemandRepository demandRepository;

    @Mock
    private CsvWriter writer;

    @InjectMocks
    private RandomForestEquipmentDemandPredictor randomForestEquipmentDemandPredictor;

    @Mock
    private CSVLoader csvLoader;

    @Mock
    private Instances instances;
    

    @Test
    void testPredictDemandForEachType() throws Exception {
        Mockito.when(csvLoader.getDataSet()).thenReturn(instances);

        Map<String, Double> result = randomForestEquipmentDemandPredictor.predictDemandForEachType();

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.containsKey("Threadmill"));
        Assertions.assertTrue(result.containsKey("Elliptical"));
        Assertions.assertTrue(result.containsKey("Kettlebell"));
    }
}
