package gym.ml;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class EquipmentPriceOptimizer {

    public Map<String, Double> predictOptimizedPriceForEachType() throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("src/main/resources/pricing_data.csv")); // Change the file path
        Instances data = loader.getDataSet();

        data.setClassIndex(data.numAttributes() - 1);

        Normalize normalize = new Normalize();
        normalize.setInputFormat(data);
        Instances normalizedData = Filter.useFilter(data, normalize);

        RandomForest randomForest = new RandomForest();
        randomForest.buildClassifier(normalizedData);

        Map<String, Double> predictions = new HashMap<>();

        for (int i = 0; i < normalizedData.numInstances(); i++) {
            Instance instance = normalizedData.instance(i);

            double predictedPrice = randomForest.classifyInstance(instance);

            String equipmentType = instance.stringValue(data.attribute("equipmentType"));

            predictions.put(equipmentType, predictions.getOrDefault(equipmentType, 0.0) + predictedPrice);
        }

        for (Map.Entry<String, Double> entry : predictions.entrySet()) {
            double demandWeight = 1.0;
            double costWeight = 1.0;

            double normalizedPrediction = (entry.getValue() + demandWeight * getDemand(entry.getKey(), data) + costWeight * getCost(entry.getKey(), data)) / normalizedData.numInstances();
            
            BigDecimal bd = new BigDecimal(normalizedPrediction);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            double roundedValue = bd.doubleValue();
            predictions.put(entry.getKey(), roundedValue);
        }

        return predictions;
    }

    private double getDemand(String equipmentType, Instances data) {
        for (int i = 0; i < data.numInstances(); i++) {
            if (data.instance(i).stringValue(data.attribute("equipmentType")).equals(equipmentType)) {
                return data.instance(i).value(data.attribute("equipmentDemand"));
            }
        }
        return 0.0;
    }

    private double getCost(String equipmentType, Instances data) {
        for (int i = 0; i < data.numInstances(); i++) {
            if (data.instance(i).stringValue(data.attribute("equipmentType")).equals(equipmentType)) {
                return data.instance(i).value(data.attribute("currentCost"));
            }
        }
        return 0.0;
    }
}
