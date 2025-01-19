package gym.service;

import gym.entity.Equipment;
import gym.entity.EquipmentTransaction;
import gym.exception.BlockchainException;
import gym.repository.EquipmentRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import gym.repository.TransactionRepository;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BlockService blockService;
    private final EquipmentRepository equipmentRepository;
    private static final int PENDING_TRANSACTIONS_THRESHOLD = 1;

    public void addTransaction(EquipmentTransaction transaction) {
        transaction.setEquipment(equipmentRepository.getReferenceById(transaction.getEquipment().getId()));

        double costPerMinute = transaction.getEquipment().getEquipmentType().getCostPerMinute();
        long transactionLengthInSeconds = ChronoUnit.MINUTES.between(transaction.getStartTime(), transaction.getEndTime());
        transactionLengthInSeconds = Math.abs(transactionLengthInSeconds);
        double cost = transactionLengthInSeconds * costPerMinute;
        BigDecimal bd = new BigDecimal(cost);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        double roundedValue = bd.doubleValue();
        transaction.setCost(roundedValue);

        if (blockService.isBlockchainValid()) {
            transactionRepository.save(transaction);
            blockService.addTransaction(transaction);

            if (blockService.getBlockchainSize() >= PENDING_TRANSACTIONS_THRESHOLD) {
                blockService.minePendingTransactions();
            }
        } else {
            throw new BlockchainException("Invalid transaction. Transaction not added to the gym.blockchain.");
        }
    }

    public List<EquipmentTransaction> getAll() {
        return transactionRepository.getTransactions();
    }

    public List<EquipmentTransaction> getTransactionsByLogin(String login) {
        return transactionRepository.getEquipmentTransactionByCustomerLogin(login);
    }
}
