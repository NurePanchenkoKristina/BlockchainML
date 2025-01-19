package gym.controller;

import gym.ml.EquipmentPriceOptimizer;
import gym.ml.LinearRegressionInventoryOptimizer;
import gym.ml.RandomForestEquipmentDemandPredictor;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ml")
@RequiredArgsConstructor
public class MLController {
    private final EquipmentPriceOptimizer priceOptimizer;
    private final LinearRegressionInventoryOptimizer linearRegressionInventoryOptimizer;
    private final RandomForestEquipmentDemandPredictor randomForestEquipmentDemandPredictor;


    @GetMapping("/optimize-price")
    public Map<String, Double> optimizeEquipmentPrice() throws Exception {
        return priceOptimizer.predictOptimizedPriceForEachType();
    }

    @GetMapping("/optimize-inventory")
    public Map<String, Double> optimizeEquipmentInventory() throws Exception {
        return linearRegressionInventoryOptimizer.optimizeInventoryDistribution();
    }

    @GetMapping("/predict-demand-random-forest")
    public Map<String, Double> predictEquipmentDemandRandomForest() throws Exception {
        return randomForestEquipmentDemandPredictor.predictDemandForEachType();
    }
}

