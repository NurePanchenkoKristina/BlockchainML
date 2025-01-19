package gym.controller;

import gym.controller.dto.EquipmentDto;
import gym.controller.dto.TransactionDto;
import gym.controller.dto.mapper.EquipmentMapper;
import gym.controller.dto.mapper.TransactionMapper;
import gym.entity.Customer;
import gym.entity.Equipment;
import gym.entity.EquipmentTransaction;
import gym.entity.EquipmentType;
import gym.repository.CustomerRepository;
import gym.repository.EquipmentRepository;
import gym.repository.EquipmentTypeRepository;
import gym.service.TransactionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {
    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;
    private final EquipmentRepository equipmentRepository;
    private final CustomerRepository customerRepository;
    private final EquipmentTypeRepository equipmentTypeRepository;
    private final EquipmentMapper equipmentMapper;

    @GetMapping("/get-equipment")
    public List<Equipment> getEquipment() {
        return equipmentRepository.findAll();
    }


    @GetMapping("/get-equipment-types")
    public List<EquipmentType> getEquipmentTypes() {
        return equipmentTypeRepository.findAll();
    }


    @GetMapping("/get-customers")
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }


    @GetMapping("/get-transactions")
    public List<EquipmentTransaction> getTransactions() {
        return transactionService.getAll();
    }

    @GetMapping("/get-transactions/{login}")
    public List<EquipmentTransaction> getTransactionsByLogin(@PathVariable String login) {
        return transactionService.getTransactionsByLogin(login);
    }

    @PostMapping("/add-transaction")
    public void addTransaction(@RequestBody TransactionDto transaction) {
        transactionService.addTransaction(transactionMapper.toEntity(transaction));
    }

    @PostMapping("/add-equipment-type")
    public void addEquipmentType(@RequestBody EquipmentType equipmentType) {
        equipmentTypeRepository.save(equipmentType);
    }

    @PostMapping("/add-equipment")
    public void addEquipment(@RequestBody EquipmentDto equipmentDto) {
        equipmentRepository.save(equipmentMapper.toEntity(equipmentDto));
    }
}
