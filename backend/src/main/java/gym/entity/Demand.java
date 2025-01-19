package gym.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Demand {
    private Long id;
    @Id
    private String equipmentTypeName;
    private double demand;
}
