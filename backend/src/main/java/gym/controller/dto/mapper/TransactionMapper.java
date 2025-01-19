package gym.controller.dto.mapper;

import gym.controller.dto.TransactionDto;
import gym.entity.Customer;
import gym.entity.Equipment;
import gym.entity.EquipmentTransaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public EquipmentTransaction toEntity(TransactionDto dto) {
        EquipmentTransaction transaction = new EquipmentTransaction();
        transaction.setCustomer(new Customer(dto.getCustomerId()));
        transaction.setEquipment(new Equipment(dto.getEquipmentId()));
        transaction.setStartTime(dto.getStartTime());
        transaction.setEndTime(dto.getEndTime());
        return transaction;
    }
}
