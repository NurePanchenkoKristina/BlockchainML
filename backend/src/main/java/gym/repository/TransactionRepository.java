package gym.repository;

import gym.entity.EquipmentTransaction;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<EquipmentTransaction, Long> {

    @EntityGraph(attributePaths = {"equipment", "customer", "equipment.equipmentType"})
    @Query("select t from EquipmentTransaction t")
    List<EquipmentTransaction> getTransactions();

    @EntityGraph(attributePaths = {"equipment", "customer", "equipment.equipmentType"})
    List<EquipmentTransaction> getEquipmentTransactionByCustomerLogin(String login);
}
