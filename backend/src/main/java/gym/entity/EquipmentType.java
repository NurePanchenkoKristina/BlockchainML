package gym.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class EquipmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeName;
    private double costPerMinute;
    private double maintenanceCost;

    public EquipmentType(Long id) {
        this.id = id;
    }
}
