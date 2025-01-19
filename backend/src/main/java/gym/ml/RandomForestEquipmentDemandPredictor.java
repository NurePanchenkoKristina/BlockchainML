package gym.ml;

import gym.entity.Demand;
import gym.ml.csv.CsvWriter;
import gym.repository.EquipmentDemandRepository;
import gym.repository.TransactionRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import weka.classifiers.trees.RandomForest;
import weka.core.*;

import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RandomForestEquipmentDemandPredictor {
    private final EquipmentDemandRepository demandRepository;
    private final CsvWriter writer;
    private final TransactionRepository transactionRepository;

    public Map<String, Double> predictDemandForEachType() throws Exception {
        writer.initCSV(transactionRepository.getTransactions());

        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("src/main/resources/data.csv"));
        Instances data = loader.getDataSet();

        data.setClassIndex(data.numAttributes() - 1);

        Normalize normalize = new Normalize();
        normalize.setInputFormat(data);
        Instances normalizedData = Filter.useFilter(data, normalize);

        RandomForest randomForest = new RandomForest();
        randomForest.buildClassifier(normalizedData);

        Map<String, Double> predictions = new HashMap<>();

        Attribute equipmentTypeAttribute = data.attribute("equipmentType");

        Instances instancesForPrediction = new Instances(data, 0);
        instancesForPrediction.setClassIndex(data.classIndex());

        for (int i = 0; i < data.numInstances(); i++) {
            Instance originalInstance = data.instance(i);

            for (int duration = 30; duration <= 75; duration += 15) {
                Instance instance = new DenseInstance(data.numAttributes());
                instance.setDataset(instancesForPrediction);

                instance.setValue(data.attribute("equipmentType"), originalInstance.stringValue(data.attribute("equipmentType")));
                instance.setValue(data.attribute("duration"), duration);

                instance.setMissing(data.classIndex());

               instancesForPrediction.add(instance);
            }
        }

        for (int i = 0; i < instancesForPrediction.numInstances(); i++) {
            double predictedCost = randomForest.classifyInstance(instancesForPrediction.instance(i));
            String equipmentType = instancesForPrediction.instance(i).stringValue(equipmentTypeAttribute);
            double currentPrediction = predictions.getOrDefault(equipmentType, 0.0);
            predictions.put(equipmentType, currentPrediction + predictedCost);
        }

        Map<String, Double> result = new HashMap<>();
        for (Map.Entry<String, Double> entry : predictions.entrySet()) {
            double averagePrediction = entry.getValue() / (75 - 30) / 15;
            Demand demand = new Demand();
            demand.setEquipmentTypeName(entry.getKey());
            demand.setDemand(averagePrediction);
            demandRepository.save(demand);

            BigDecimal bd = new BigDecimal(averagePrediction * 10);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            double roundedValue = bd.doubleValue();

            result.put(entry.getKey(), roundedValue);
        }

        return result;
    }
}
