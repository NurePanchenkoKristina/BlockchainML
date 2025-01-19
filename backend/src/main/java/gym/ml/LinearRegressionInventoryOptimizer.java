package gym.ml;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;
import weka.classifiers.functions.LinearRegression;
import weka.core.*;

import weka.core.converters.CSVLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Normalize;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class LinearRegressionInventoryOptimizer {
    public Map<String, Double> optimizeInventoryDistribution() throws Exception {
        CSVLoader loader = new CSVLoader();
        loader.setSource(new File("src/main/resources/inventory_data.csv"));
        Instances data = loader.getDataSet();

        data.setClassIndex(data.numAttributes() - 1);

        Normalize normalize = new Normalize();
        normalize.setInputFormat(data);
        Instances normalizedData = Filter.useFilter(data, normalize);

        LinearRegression linearRegression = new LinearRegression();
        linearRegression.buildClassifier(normalizedData);

        Map<String, Double> optimizedDistribution = new HashMap<>();

        for (int i = 0; i < data.numInstances(); i++) {
            Instance instance = data.instance(i);

            double predictedValue = linearRegression.classifyInstance(instance);

            BigDecimal bd = new BigDecimal(predictedValue);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            double roundedValue = bd.doubleValue();
            optimizedDistribution.put(instance.stringValue(data.attribute("equipmentType")), roundedValue);
        }

        return optimizedDistribution;
    }
}
