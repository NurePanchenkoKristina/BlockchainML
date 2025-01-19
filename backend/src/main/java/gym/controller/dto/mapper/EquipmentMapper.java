package gym.controller.dto.mapper;

import gym.controller.dto.EquipmentDto;
import gym.entity.Customer;
import gym.entity.Equipment;
import gym.entity.EquipmentTransaction;
import gym.entity.EquipmentType;
import org.springframework.stereotype.Component;

@Component
public class EquipmentMapper {
    public Equipment toEntity(EquipmentDto dto) {
       Equipment equipment = new Equipment();
       equipment.setEquipmentType(new EquipmentType(dto.getEquipmentTypeId()));
       equipment.setSerialNumber(dto.getSerialNumber());

       return equipment;
    }
}
