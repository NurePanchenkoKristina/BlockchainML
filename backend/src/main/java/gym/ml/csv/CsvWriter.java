package gym.ml.csv;

import gym.entity.Demand;
import gym.entity.EquipmentTransaction;
import gym.entity.EquipmentType;
import gym.repository.EquipmentDemandRepository;
import gym.repository.EquipmentTypeRepository;
import java.io.FileWriter;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsvWriter {
    private final EquipmentDemandRepository equipmentDemandRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;

    public void initCSV(List<EquipmentTransaction> transactions) {
        createTransactionData(transactions);
        createPricingData();
        createDistributionData();
    }

    private void createTransactionData(List<EquipmentTransaction> transactions) {
        try (FileWriter fileWriter = new FileWriter("src/main/resources/data.csv");
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("equipmentType", "customer", "duration", "cost"))) {

            for (EquipmentTransaction transaction : transactions) {
                csvPrinter.printRecord(
                    transaction.getEquipment().getEquipmentType().getTypeName(),
                    transaction.getCustomer().getFirstName() + " " +
                        transaction.getCustomer().getLastName(),
                    ChronoUnit.SECONDS.between(transaction.getStartTime(), transaction.getEndTime()),
                    transaction.getCost()
                );
            }

            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double getDemandForEquipmentType(String equipmentType) {
        Optional<Demand> opt = equipmentDemandRepository.getFirstByEquipmentTypeName(equipmentType);
        if (opt.isPresent()) {
            return opt.get().getDemand();
        }

        return 0;
    }

    private void createPricingData() {
        List<EquipmentType> types = equipmentTypeRepository.findAll();
        try (FileWriter fileWriter = new FileWriter("src/main/resources/pricing_data.csv");
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("equipmentType", "equipmentDemand", "currentCost"))) {

            for (EquipmentType type : types) {
                csvPrinter.printRecord(
                    type.getTypeName(),
                    getDemandForEquipmentType(type.getTypeName()),
                    type.getCostPerMinute()
                );
            }

            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDistributionData() {
        List<EquipmentType> types = equipmentTypeRepository.findAll();

        try (FileWriter fileWriter = new FileWriter("src/main/resources/inventory_data.csv");
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader("equipmentType", "maintenanceCost",
                 "equipmentDemand", "desired", "variable"))) {

            for (EquipmentType type : types) {
                csvPrinter.printRecord(
                    type.getTypeName(),
                    type.getMaintenanceCost(),
                    getDemandForEquipmentType(type.getTypeName()),
                    type.getMaintenanceCost(),
                    getDemandForEquipmentType(type.getTypeName()));
            }

            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}