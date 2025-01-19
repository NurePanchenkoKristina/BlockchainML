package gym.repository;

import gym.entity.Demand;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentDemandRepository extends JpaRepository<Demand, Long> {
    Optional<Demand> getFirstByEquipmentTypeName(String name);
}
